/*------------------------------ plp4.y -------------------------------*/
%token print id 
%token opas opmd oprel opasig cierto falso
%token nentero nreal pari pard
%token pyc coma dospto
%token var fvar real entero logico tabla de
%token algoritmo falgoritmo blq fblq funcion 
%token si entonces sino fsi mientras hacer escribe lee repetir hasta
%token cori cord ybool obool nobool

%{

#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <string>
#include <iostream>
#include "TablaSimbolos.h"
#include "TablaTipos.h"

using namespace std;

#include "comun.h"

// variables y funciones del A. Léxico
extern int ncol,nlin,findefichero;


extern int yylex();
extern char *yytext;
extern FILE *yyin;


int yyerror(char *s);
int newTemp();
int newVar();
string newLabel();
void comprobarTipos(MITIPO& t1, MITIPO& t2);
int traducirTipo(string tipo);
string traducirTipo(int tipo);
string tipoAsig(int tipo);
MITIPO opera(string op, MITIPO izq, MITIPO der);
void rellenarTipos(int tipoSimbolo);
void errorSemantico(int nerr,int fila,int columna,const char *lexema);
MITIPO recuperarTipo(int posTipo);
string getOperator(string op);

int ctemp = 16000;
int cvars = 0;
int clabel = 0;

const int ERR_YA_EXISTE=1,
          ERR_NO_VARIABLE=2,
          ERR_NO_DECL=3,
          ERR_NO_BOOL=4,
          ERR_ASIG_REAL=5,
          ERR_SIMIENTRAS=6,
          ERR_DIVENTERA=7;
string operador, s1, s2;  // string auxiliares
TablaSimbolos *tsa;
TablaTipos *tt;
%}

%%

S       : {tsa = new TablaSimbolos(NULL); tt = new TablaTipos();} algoritmo dospto id SDec SInstr falgoritmo
          { $$.cod = $6.cod + "halt"; cout << $$.cod << endl; }
        ;

SDec    : Dec 
        |
        ;

Dec     : var DVar MDVar fvar 
        ;

DVar    : Tipo dospto id {
                            Simbolo s;
                            s.nombre = $3.lexema;
                            s.tipo = $1.tipoPos;
                            s.dir = newVar();
                            s.tam = tt->getTipo($1.tipoPos).tamanyo; // es objeto Tipo
                            $$.tipo = &($1); if(!tsa->newSymb(s)){errorSemantico(ERR_YADECL, $3.nlin, $3.ncol, $3.lexema);}    //ERROR

                            } Lid pyc
        ;

MDVar   : DVar MDVar 
        |
        ;

Lid     : coma id {
                    Simbolo s;
                    s.nombre = $2.lexema;
                    s.tipo = 1;
                    s.dir = newVar();
                    s.tam = tt->getTipo(1).tamanyo; // hereda tipo en DVar
                    if(!tsa->newSymb(s)){errorSemantico(ERR_YADECL, $2.nlin, $2.ncol, $2.lexema);}    //ERROR
                    $$.tipo = $0.tipo;
                  } Lid 
        |
        ;

Tipo    : entero {$$.trad="entero"; $$.tipoPos=0; $$.lexema="i"; }
        | real   {$$.trad="real"; $$.tipoPos=1; $$.lexema="r"; }
        | logico {$$.trad="logico"; $$.tipoPos=2; $$.lexema="l"; }
        | tabla nentero de Tipo {tt->nuevoTipoArray(stoi($2.lexema), $4.tipoPos); }
        ;

SInstr  : SInstr pyc {$$.dir=ctemp; } Instr {ctemp = $3.dir; $$.cod = $1.cod + $4.cod;}
        | {$$.dir=ctemp;} Instr {ctemp=$1.dir; $$.cod = $2.cod;}
        ;

Instr   : escribe Expr {$$.cod = $2.cod + getOperator($1.lexema) + $2.tipo->lexema + " " + std::to_string($2.dir) + "\n";}
        | lee Ref {$$.cod = $2.cod + getOperator($1.lexema) + $2.tipo->lexema + " " + std::to_string($2.dir) + "\n"; }
        | si Expr entonces Instr {if($2.tipo->trad != "logico") errorSemantico(ERR_EXP_LOG, $1.nlin, $1.ncol, $1.lexema);
                                    string l1 = newLabel(); 
                                    $$.cod = $2.cod + "mov " + std::to_string($2.dir) + " A\n"
                                            + "jz L" + l1 + "\n"
                                            + $4.cod + "\n L" + l1 + ":\n";;
                                            
                                 }
        | si Expr entonces Instr sino Instr {if($2.tipo->trad != "logico") errorSemantico(ERR_EXP_LOG, $1.nlin, $1.ncol, $1.lexema);
                                            string l1 = newLabel();
                                            string l2 = newLabel(); 
                                            $$.cod = $2.cod + "mov " + std::to_string($2.dir) + " A\n"
                                                    + "jz L" + l1 + "\n"
                                                    + $4.cod + "\njmp L" + l2 +"\nL" + l1 + ":\n"
                                                    + $6.cod + "\nL" + l2 + ":\n";;
                                        }
        | mientras Expr hacer Instr {
                                        if($2.tipo->trad != "logico") errorSemantico(ERR_EXP_LOG, $1.nlin, $1.ncol, $1.lexema);
                                        string l1 = newLabel();
                                        string l2 = newLabel();
                                        $$.cod = "L" + l1 + ":\n" + $2.cod + "mov " + std::to_string($2.dir) + " A\n"
                                                + "jz L" + l2 + "\n"
                                                + $4.cod + "jmp L" + l1 + "\n";
                                                + "L" + l2 + ":\n";
                                    }
        | repetir Instr hasta Expr {
                                        if($4.tipo->trad != "logico") errorSemantico(ERR_EXP_LOG, $1.nlin, $1.ncol, $1.lexema);
                                        string l1 = newLabel();
                                        
                                        $$.cod = "L" + l1 + ":\n" + $2.cod + "\n" 
                                                + $4.cod +"mov " + std::to_string($4.dir) + " A\n"
                                                + "jz L" + l1 + "\n";
                                    }
        | Ref opasig Expr {comprobarTipos(*$1.tipo, *$3.tipo);
                            $$.cod = $1.cod + $3.cod + "mov " + std::to_string($3.dir) + " A\n";
                                    + "mov A " + std::to_string($1.dir) + "\n";
                            }
        | blq SDec SInstr fblq {$$.cod = $3.cod;}
        ;

Expr    : Expr obool Econj {int tmp = newTemp(); $$.dir = tmp;
                            $$.cod = $1.cod + $3.cod + 
                            "mov " + std::to_string($1.dir) + " A\n" +
                            getOperator($2.lexema) + $1.tipo->trad + " " + std::to_string($3.dir) + // OJO CON YEPA EYYY (ARREGLAR TEMA TIPOS)
                            "mov A " + std::to_string(tmp) + "\n";
                            $$.tipo = $1.tipo;}
        | Econj {$$.dir = $1.dir; $$.cod = $1.cod; $$.tipo = $1.tipo;}
        ;

Econj   : Econj ybool Ecomp {int tmp = newTemp(); $$.dir = tmp;
                             $$.cod = $1.cod + $3.cod + 
                            "mov " + std::to_string($1.dir) + " A\n" +
                            getOperator($2.lexema) + $1.tipo->trad + " " + std::to_string($3.dir) + // OJO CON YEPA EYYY (ARREGLAR TEMA TIPOS)
                            "mov A " + std::to_string(tmp) + "\n";}
        | Ecomp {$$.dir = $1.dir; $$.cod = $1.cod; $$.tipo = $1.tipo;}
        ;

Ecomp   : Esimple oprel Esimple {int tmp = newTemp(); $$.dir = tmp;
                             $$.cod = $1.cod + $3.cod + 
                            "mov " + std::to_string($1.dir) + " A\n" +
                            getOperator($2.lexema) + $1.tipo->lexema + " " + std::to_string($3.dir) + // OJO CON YEPA EYYY (ARREGLAR TEMA TIPOS)
                            "mov A " + std::to_string(tmp) + "\n";}
        | Esimple {$$.dir = $1.dir; $$.cod = $1.cod;}
        ;

Esimple : Esimple opas Term {int tmp = newTemp(); $$.dir = tmp;
                             $$.cod = $1.cod + $3.cod + 
                            "mov " + std::to_string($1.dir) + " A\n" +
                            getOperator($2.lexema) + $1.tipo->lexema + " " + std::to_string($3.dir) + "\n" + // OJO CON YEPA EYYY (ARREGLAR TEMA TIPOS)
                            "mov A " + std::to_string(tmp) + "\n";
                            $$.tipo = $1.tipo;}
        | Term {$$.dir = $1.dir; $$.cod = $1.cod; $$.tipo = $1.tipo;}
        | opas Term {int tmp = newTemp(); $$.dir = tmp;
                                $$.cod = $2.cod +
                                "mov #0 A\n" +
                                getOperator($1.lexema) + $2.tipo->lexema + " " + std::to_string($2.dir) +
                                "mov A " + std::to_string(tmp) + "\n";
                                $$.tipo = $2.tipo;
                            }
        ;

Term    : Term opmd Factor {int tmp = newTemp(); $$.dir = tmp;
                             $$.cod = $1.cod + $3.cod + 
                            "mov " + std::to_string($1.dir) + " A\n" +
                            $2.lexema + $1.tipo->lexema + " " + std::to_string($3.dir) + // OJO CON YEPA EYYY (ARREGLAR TEMA TIPOS)
                            "mov A " + std::to_string(tmp) + "\n"; $$.tipo = $1.tipo;}
        | Factor {$$.dir = $1.dir; $$.cod = $1.cod; $$.tipo = $1.tipo;}
        ;

Factor  : Ref {$$.dir = $1.dir; $$.tipo = $1.tipo; $$.cod = $1.cod;}
        | nentero {int tmp = newTemp(); $$.dir = tmp;
                   MITIPO tipo = recuperarTipo(0);
                   $$.tipo = &tipo;
                   string entero_string($1.lexema);
                   $$.cod = "mov #" + entero_string + " " + std::to_string(tmp) + "\n";}
        | nreal {int tmp = newTemp(); $$.dir = tmp;
                   MITIPO tipo = recuperarTipo(1);
                   $$.tipo = &tipo;
                   string real_string($1.lexema);
                   $$.cod = "mov #" + real_string + " " + std::to_string(tmp) + "\n";}
        | pari Expr pard {$$.dir = $2.dir; $$.cod = $2.cod; $$.tipo = $2.tipo;}
        | nobool Factor {
                            $$.tipo = $2.tipo; int tmp = newTemp(); $$.dir = tmp;
                            $$.cod = "mov " + std::to_string($2.dir) + "A\n"
                                   + "not" + $2.tipo->lexema + "\n"
                                   + "mov A " + std::to_string(tmp) + "\n";
                        }
        | cierto {int tmp = newTemp(); $$.dir = tmp;
                 MITIPO tipo = recuperarTipo(2);
                 $$.tipo = &tipo;
                 $$.cod = "mov #1 " + std::to_string(tmp) + "\n";}
        | falso {int tmp = newTemp(); $$.dir = tmp;
                 MITIPO tipo = recuperarTipo(2);
                 $$.tipo = &tipo;
                 $$.cod = "mov #0 " + std::to_string(tmp) + "\n";}
        ;

Ref     : id {
                
                Simbolo* s = tsa->searchSymb($1.lexema);
                if( s == NULL )
                    errorSemantico(ERR_NO_DECL, $1.nlin, $1.ncol, $1.lexema);
                int tmp = newTemp(); $$.dir = tmp;
                $$.cod = "mov #0 " + std::to_string(tmp) + "\n";
                
                MITIPO tipo = recuperarTipo(s->tipo);
                $$.tipo = &tipo;
                $$.dbase = s->dir;
            }
        | Ref cori {
                        if($1.tipo->tipoPos<=2)
                            errorSemantico(ERR_SOBRAN, $2.nlin, $2.ncol, $2.lexema);
                    } Esimple cord {
                                        if($4.tipo->tipoPos!=0)
                                            errorSemantico(ERR_INDICE_ENTERO, $4.nlin, $4.ncol, $4.lexema);
                                        MITIPO tipo = recuperarTipo(tt->getTipo($1.tipo->tipoPos).tipoBase); // tipoBase es igual que tipoPos???
                                        $$.tipo = &tipo;
                                        $$.dbase = $1.dbase;
                                        int tmp = newTemp(); $$.dir = tmp;
                                        $$.cod = $1.cod + $4.cod +
                                                "mov " + std::to_string($1.dir) + " A\n" +
                                                "muli #" + std::to_string(tt->getTipo($1.tipo->tipoPos).tamanyo) + "\n" +
                                                "addi " + std::to_string($4.dir) + "\n" +
                                                "mov A " + std::to_string(tmp) + "\n";
                                                
                                    }
        ;


%%

int traducirTipo(string tipo){
    int tipoSimbolo;
    if(tipo == "int"){
        tipoSimbolo = 1;
    }
    else if(tipo == "double"){
        tipoSimbolo = 2;
    }
    else{
        return -1;
    }
    return tipoSimbolo;
}

string traducirTipo(int tipo){
    string tipoSimbolo;
    if(tipo == 1){
        tipoSimbolo = "int";
    }
    else if(tipo == 2){
        tipoSimbolo = "double";
    }
    else{
        return "";
    }
    return tipoSimbolo;
}
                          
void errorSemantico(int nerror,int nlin,int ncol,const char *s)
{
   fprintf(stderr,"Error semantico (%d,%d): ", nlin,ncol);
   switch(nerror) {
         case ERR_YADECL: fprintf(stderr,"variable '%s' ya declarada\n",s);
            break;
         case ERR_NODECL: fprintf(stderr,"variable '%s' no declarada\n",s);
            break;
         case ERR_DIM: fprintf(stderr,"la dimension debe ser mayor que cero\n");
            break;
         case ERR_FALTAN: fprintf(stderr,"faltan indices\n");
            break;
         case ERR_SOBRAN: fprintf(stderr,"sobran indices\n");
            break;
         case ERR_INDICE_ENTERO: fprintf(stderr,"la expresion entre corchetes debe ser de tipo entero\n");
            break;
         case ERR_EXP_LOG: fprintf(stderr,"la expresion debe ser de tipo logico\n");
            break;
         case ERR_EXDER_LOG: fprintf(stderr,"la expresion a la derecha de '%s' debe ser de tipo logico\n",s);
            break;
         case ERR_EXDER_ENT: fprintf(stderr,"la expresion a la derecha de '%s' debe ser de tipo entero\n",s);
            break;
         case ERR_EXDER_RE:fprintf(stderr,"la expresion a la derecha de '%s' debe ser de tipo real o entero\n",s);
            break;        
         case ERR_EXIZQ_LOG:fprintf(stderr,"la expresion a la izquierda de '%s' debe ser de tipo logico\n",s);
            break;       
         case ERR_EXIZQ_RE:fprintf(stderr,"la expresion a la izquierda de '%s' debe ser de tipo real o entero\n",s);
            break;       
         case ERR_NOCABE:fprintf(stderr,"la variable '%s' ya no cabe en memoria\n",s);
            break;
         case ERR_MAXTMP:fprintf(stderr,"no hay espacio para variables temporales\n");
            break;
   }
   exit(-1);
}

void msgError(int nerror,int nlin,int ncol,const char *s)
{
    switch (nerror) {
         case ERRLEXICO: fprintf(stderr,"Error lexico (%d,%d): caracter '%s' incorrecto\n",nlin,ncol,s);
            break;
         case ERRSINT: fprintf(stderr,"Error sintactico (%d,%d): en '%s'\n",nlin,ncol,s);
            break;
         case ERREOF: fprintf(stderr,"Error sintactico: fin de fichero inesperado\n");
            break;
     }

     exit(1);
}


int newTemp(){
    ctemp++;
    if(ctemp > 16383){
        // ERROR()
    }
    return ctemp;
}

int newVar(){
    cvars++;
    if(cvars > 16000){
        // ERROR();
    }
    return cvars;
}

string newLabel(){
    clabel++;
    return std::to_string(clabel); 
}

MITIPO recuperarTipo(int posTipo){
    MITIPO tipo;
    if(posTipo == 1){
        tipo.trad = "real"; tipo.tipoPos=1; tipo.lexema="r";
    }
    else if(posTipo == 0){
        tipo.trad = "entero"; tipo.tipoPos=0; tipo.lexema="i";
    }
    else if(posTipo == 2){
        tipo.trad = "logico"; tipo.tipoPos=2; tipo.lexema="l";
    }
    else if(posTipo > 2){
        tipo.trad = "array"; tipo.tipoPos=posTipo; tipo.lexema="a";
    }
    return tipo;
    
}

void comprobarTipos(MITIPO& t1, MITIPO& t2){
    // POR IMPLEMENTAR
}

string getOperator(string op){
    string oper = op;
    if(op == "+"){
        oper = "add";
    }
    else if(op == "-"){
        oper = "sub";
    }
    else if(op == "*"){
        oper = "mul";
    }
    else if(op == "/"){
        oper = "div";
    }
    else if(op == "&&"){
        oper == "and";
    }
    else if(op == "||"){
        oper = "or";
    }
    else if(op == "!"){
        oper = "not";
    }
    else if(op == "="){
        oper = "eql";
    }
    else if(op == "<>"){
        oper = "neq";
    }
    else if(op == ">"){
        oper = "gtr";
    }
    else if(op == "<"){
        oper = "lss";
    }
    else if(op == "escribe"){
        oper = "wr";
    }
    else if(op == "lee"){
        oper = "rd";
    }
    return oper;
}

string tipoAsig(int tipo){
    string tipoSimbolo;
    if(tipo == 1){
        tipoSimbolo = "i";
    }
    else if(tipo == 2){
        tipoSimbolo = "r";
    }
    else{
        return "";
    }
    return tipoSimbolo;
}



void rellenarTipos(int tipoSimbolo){
    for(int i=0; i < tsa->simbolos.size(); i++){
        Simbolo s = tsa->simbolos[i];
        if(s.tipo == -1){
            s.tipo = tipoSimbolo;
            tsa->simbolos[i] = s;
        }
    }
}



int yyerror(char *s)
{
    if (findefichero) 
    {
       msgError(ERREOF,0,0,"");
    }
    else
    {  
       msgError(ERRSINT,nlin,ncol-strlen(yytext),yytext);
    }
}

int main(int argc,char *argv[])
{
    FILE *fent;

    if (argc==2)
    {
        fent = fopen(argv[1],"rt");
        if (fent)
        {
            yyin = fent;
            yyparse();
            fclose(fent);
        }
        else
            fprintf(stderr,"No puedo abrir el fichero\n");
    }
    else
        fprintf(stderr,"Uso: ejemplo <nombre de fichero>\n");
}

