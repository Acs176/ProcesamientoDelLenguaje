/*------------------------------ plp4.y -------------------------------*/
%token print id 
%token opas opmd oprel asig
%token nentero nreal pari pard
%token pyc coma dosp
%token var real entero algoritmo blq fblq funcion si entonces sino fsi mientras hacer escribir


%{

#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <string>
#include <iostream>
#include "TablaSimbolos.h"

using namespace std;

#include "comun.h"

// variables y funciones del A. Léxico
extern int ncol,nlin,findefichero;


extern int yylex();
extern char *yytext;
extern FILE *yyin;


int yyerror(char *s);
int traducirTipo(string tipo);
string traducirTipo(int tipo);
string tipoAsig(int tipo);
MITIPO opera(string op, MITIPO izq, MITIPO der);
void rellenarTipos(int tipoSimbolo);
void errorSemantico(int nerr,int fila,int columna,char *lexema);

const int ERR_YA_EXISTE=1,
          ERR_NO_VARIABLE=2,
          ERR_NO_DECL=3,
          ERR_NO_BOOL=4,
          ERR_ASIG_REAL=5,
          ERR_SIMIENTRAS=6,
          ERR_DIVENTERA=7;
string operador, s1, s2;  // string auxiliares
TablaSimbolos *tsa;
%}

%%

S       : {tsa = new TablaSimbolos(NULL);} algoritmo id pyc {$$.isMain = true; $$.esFunc = true; $$.pre=""; $$.tipo=1; $$.nombre="main";} Vsp Bloque {
        string l3($3.lexema);
        $$.trad = "//algoritmo " + l3 + "\n\n" + $6.trad + $7.trad; 
        int token = yylex();
        if (token == 0) // si es fin de fichero, yylex() devuelve 0
        {
            std::cout << $$.trad << std::endl;
        }
        else{
            yyerror("");
        } }
        ;

Vsp     : Vsp {$$.isMain = $0.isMain; $$.pre = $0.pre;} Unsp {$$.trad = $1.trad + $3.trad;} // ESTA MAL
        | {} {$$.pre = $0.pre; $$.isMain = $0.isMain;} Unsp {$$.trad = $3.trad;}
        ;

Unsp    : funcion id dosp Tipo pyc { Simbolo s;
                                     s.nombre = $2.lexema;
                                     s.tipo = FUNCION;
                                     s.nomtrad = $0.pre + $2.lexema;
                                     if(!tsa->nuevoSimbolo(s))
                                        errorSemantico(ERR_YA_EXISTE, $2.nlin, $2.ncol, $2.lexema);                                     tsa = new TablaSimbolos(tsa);
                                     $$.pre = $0.pre + $2.lexema + "_";
                                     $$.tipo = traducirTipo($4.trad);
                                     $$.nombre = $0.pre + $2.lexema;
                                     $$.esFunc = true;
                                     //cout << "FUNCION NUEVA: " + s.nomtrad << endl;
                                    } Vsp Bloque pyc {
                                        $$.trad = $7.trad + $8.trad;
                                        tsa = tsa->getAmbitoAnterior();
                                    }
        | var {$$.pre = $0.isMain ? "main_" : $0.pre;} LV {$$.trad = $3.trad + "\n"; }
        ;

LV      : LV {$$.pre = $0.pre;} V {$$.trad = $1.trad + "\n" + $3.trad;} // ESTA MAL
        | {} {$$.pre = $0.pre;} V {$$.trad = $3.trad;}
        ;

V       : {$$.pre = $0.pre;} Lid dosp Tipo {rellenarTipos(traducirTipo($4.trad));} pyc {
            $$.trad = $4.trad + " " + $2.trad + ";";
        }
        ; 

Lid     : Lid coma id{  Simbolo s;
                        s.nombre = $3.lexema;
                        s.tipo = -1;
                        s.nomtrad = $0.pre + $3.lexema;
                        if(!tsa->nuevoSimbolo(s))
                            errorSemantico(ERR_YA_EXISTE, $3.nlin, $3.ncol, $3.lexema);
                        //cout << "NUEVA VARIABLE: " + s.nomtrad + "\n";
                        $$.trad = $1.trad + "," + $0.pre + $3.lexema;
                        
                    }
        | id {  Simbolo s;
                s.nombre = $1.lexema;
                s.tipo = -1;
                s.nomtrad = $0.pre + $1.lexema;
                if(!tsa->nuevoSimbolo(s))
                    errorSemantico(ERR_YA_EXISTE, $1.nlin, $1.ncol, $1.lexema);
                //cout << "NUEVA VARIABLE: " + s.nomtrad + "\n";
                $$.trad = $0.pre + $1.lexema;
             }
        ;

Tipo    : entero {$$.trad = "int";}
        | real   {$$.trad = "double";}
        ;

Bloque  : blq SInstr fblq   {   $$.esFunc=$-1.esFunc; $$.nombre = $-1.nombre; $$.tipo = $-1.tipo;}
                            {$$.trad = $4.esFunc ? traducirTipo($-1.tipo) + " " + $4.nombre + "(){\n" + $2.trad + "}\n"
                                                 : "{\n" + $2.trad + "}\n";                        
                            }
        ;

SInstr  : SInstr pyc Instr {$$.trad = $1.trad + $3.trad + "\n" ;}
        | Instr {$$.trad = $1.trad + "\n"; }
        ;

Instr   : id asig   {
                        Simbolo* s = tsa->buscar($1.lexema);
                        if(s == NULL){
                            errorSemantico(ERR_NO_DECL, $1.nlin, $1.ncol, $1.lexema);
                        }
                        if(s->tipo == FUNCION)
                            errorSemantico(ERR_NO_VARIABLE, $1.nlin, $1.ncol, $1.lexema);
                        $$.nombre = s->nomtrad;
                        $$.tipo = s->tipo;
                    } E { if($4.esBool) errorSemantico(ERR_NO_BOOL, $2.nlin, $2.ncol, $2.lexema);
                          if($3.tipo == 1 && $4.tipo == 2)  errorSemantico(ERR_ASIG_REAL, $1.nlin, $1.ncol, $1.lexema);
                        MITIPO simbolo; simbolo.trad = $3.nombre; simbolo.tipo=$3.tipo;
                        MITIPO result = opera("=", simbolo, $4); $$.trad = result.trad + ";";}
        | {$$.esFunc=false; $$.nombre = "";} {} Bloque {$$.trad = $3.trad;}
        | si {$$.tipo = -1;} E entonces Instr ColaIf {  if(!$3.esBool)  errorSemantico(ERR_SIMIENTRAS, $1.nlin, $1.ncol, $1.lexema);
                                                        $$.trad = "if (" + $3.trad + ")\n" + $5.trad + "\n" + $6.trad; }
        | mientras {$$.tipo=-1;} E hacer Instr {    if(!$3.esBool)  errorSemantico(ERR_SIMIENTRAS, $1.nlin, $1.ncol, $1.lexema);
                                                    $$.trad="while( " + $3.trad + ")\n" + $5.trad;}
        | escribir pari {$$.tipo = -1;} E {if($4.esBool)    errorSemantico(ERR_NO_BOOL, $1.nlin, $1.ncol, $1.lexema);} pard {$$.trad = $4.tipo == 1 ? "printf(\"%d\\n\"," + $4.trad + ");"
                                                                         : "printf(\"%g\\n\"," + $4.trad + ");";}
        ;

ColaIf  : fsi {$$.trad = "";}
        | sino Instr fsi {$$.trad = "else\n" + $2.trad;}
        ;

E       : Expr oprel Expr { string op($2.lexema);
			     if(op == "=")    op = "==";
                            else if(op == "<>")   op = "!=";
                            else	op = $2.lexema;
                            MITIPO result = opera(op, $1, $3); $$.tipo = result.tipo; $$.trad = result.trad; $$.esBool = true;}
        | Expr {$$.tipo = $1.tipo; $$.trad = $1.trad;}
        ;

Expr    : Expr opas Term {MITIPO result = opera($2.lexema, $1, $3); $$.tipo = result.tipo; $$.trad = result.trad;}
        | Term {$$.tipo = $1.tipo; $$.trad = $1.trad;}

Term    : Term opmd Factor {string op($2.lexema); if(op == "//" && ($1.tipo == 2 || $3.tipo == 2)) errorSemantico(ERR_DIVENTERA, $2.nlin, $2.ncol, $2.lexema);
                            MITIPO result = opera($2.lexema, $1, $3); $$.tipo = result.tipo; $$.trad = result.trad;}
        | Factor {$$.tipo = $1.tipo; $$.trad = $1.trad;}

Factor  : id { 
                Simbolo* s = tsa->buscar($1.lexema);
                if(s == NULL){
                            errorSemantico(ERR_NO_DECL, $1.nlin, $1.ncol, $1.lexema);
                }
                if(s->tipo == FUNCION)
                    errorSemantico(ERR_NO_VARIABLE, $1.nlin, $1.ncol, $1.lexema);
                $$.nombre = s->nomtrad;
                $$.tipo = s->tipo;
                $$.trad = s->nomtrad;
             }
        | nentero {
            $$.nombre = $1.lexema;
            $$.tipo = 1;
            $$.trad = $1.lexema;
            }
        | nreal {
            $$.nombre = $1.lexema;
            $$.tipo = 2;
            $$.trad = $1.lexema;
        }
        | pari Expr pard {$$.tipo = $2.tipo; $$.trad = "(" + $2.trad + ")";}
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
                          
void errorSemantico(int nerr,int fila,int columna,char *lexema) 
{
        fprintf(stderr,"Error semantico (%d,%d): ",fila,columna);
        switch (nerr) {
	        	case ERR_YA_EXISTE:
	        		fprintf(stderr,"'%s' ya existe en este ambito\n",lexema);
	        		break;
			case ERR_NO_VARIABLE:
			        fprintf(stderr,"'%s' no es una variable\n",lexema);
				break;
			case ERR_NO_DECL:
				fprintf(stderr,"'%s' no ha sido declarado\n",lexema);
				break;
			case ERR_NO_BOOL:
			    	fprintf(stderr,"'%s' no admite expresiones booleanas\n",lexema);
				break;
			case ERR_ASIG_REAL:
			    	fprintf(stderr,"'%s' debe ser de tipo real\n",lexema);
				break;
			case ERR_SIMIENTRAS:
			    	fprintf(stderr,"en la instruccion '%s' la expresion debe ser relacional\n",lexema);
				break;
			case ERR_DIVENTERA:
			    	fprintf(stderr,"los dos operandos de '%s' deben ser enteros\n",lexema);
				break;
        }
	exit(-1);
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

MITIPO opera(string op, MITIPO izq, MITIPO der) {
    int tipo = 0;
    string trad = "";
    bool eraDobleBarra = false;
    if (op == "//") {
        op = "/";
        eraDobleBarra = true;
    }
    if (izq.tipo == 1 && der.tipo == 1) {
        if (op == "/" && !eraDobleBarra) {
            tipo = 2;
            trad = "itor(" + izq.trad + ") " + op + tipoAsig(tipo) + " itor(" + der.trad + ")";
        }
        else {
            tipo = 1;
            trad = izq.trad + " " + op + tipoAsig(tipo) + " " + der.trad;
        }
    }
    else if (izq.tipo == 1 && der.tipo == 2) {
        tipo = 2;
        trad = "itor(" + izq.trad + ") " + op + tipoAsig(tipo) + " " + der.trad;
    }
    else if (izq.tipo == 2 && der.tipo == 1) {
        tipo = 2;
        trad = izq.trad + " " + op + tipoAsig(tipo) + " itor(" + der.trad + ")";
    }
    else if (izq.tipo == 2 && der.tipo == 2) {
        tipo = 2;
        trad = izq.trad + " " + op + tipoAsig(tipo) + " " + der.trad;
    }
    else{
        //cout << "CHEEE QUE PELOTUDO!" << endl;
    }

    // falta arreglar esto
    MITIPO toReturn;
    toReturn.trad = trad;
    toReturn.tipo = tipo;
    return toReturn;
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
         case ERRLEXEOF: fprintf(stderr,"Error lexico: fin de fichero inesperado\n");
            break;
     }
        
     exit(1);
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

