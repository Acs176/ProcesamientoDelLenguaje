/*------------------------------ plp4.y -------------------------------*/
%token print id 
%token opas opmd oprel opasig
%token nentero nreal pari pard
%token pyc coma dosp
%token var real entero algoritmo blq fblq funcion si entonces sino fsi mientras hacer escribe lee
%token cori cord ybool obool nobool

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
int newTemp();
int newVar();
int traducirTipo(string tipo);
string traducirTipo(int tipo);
string tipoAsig(int tipo);
MITIPO opera(string op, MITIPO izq, MITIPO der);
void rellenarTipos(int tipoSimbolo);
void errorSemantico(int nerr,int fila,int columna,char *lexema);

int ctemp = 16000;
int cvars = 0;

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
          { $$.cod = $6.cod;}
        ;

SDec    : Dec
        |
        ;

Dec     : var DVar MDVar fvar
        ;

DVar    : Tipo dospto id {
                            Simbolo s = new Simbolo();
                            s.nombre = $3.lexema;
                            s.tipo = $1.trad;
                            s.dir = newVar();
                            s.tam = $1.tam;
                            if(!tsa->newSymb(s))    //ERROR
                            $$.tipo = $1;} Lid pyc
        ;

MDVar   : DVar MDVar
        |
        ;

Lid     : coma id {
                    Simbolo s = new Simbolo();
                    s.nombre = $2.lexema;
                    s.tipo = $0.tipo.trad;
                    s.dir = newVar();
                    s.tam = tt->getTipo($0.tipo.tipoPos).tam;
                    if(!tsa->newSymb(s))    //ERROR
                    $$.tipo = $0.tipo;
                  } Lid
        |
        ;

Tipo    : entero {$$.trad="entero"; $$.tipoPos=0; $$.lexema="i"; }
        | real   {$$.trad="real"; $$.tipoPos=1; $$.lexema="r"; }
        | logico {$$.trad="logico"; $$.tipoPos=2; $$.lexema="l"; }
        | tabla nentero de Tipo {tt.nuevoTipoArray(stoi($2.lexema), $4.tipoPos);}
        ;

SInstr  : SInstr pyc {$$.dir=ctemp;} Instr {ctemp = $3.dir; $$.cod = $1.cod + $4.cod;}
        | {$$.dir=ctemp;} Instr {ctemp=$1.dir; $$.cod = $2.cod;}
        ;

Instr   : escribe Expr {$$.cod = $2.cod + $1.lexema + Expr.tipo.lexema + " " + Expr.dir;}
        | lee Ref {}
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

