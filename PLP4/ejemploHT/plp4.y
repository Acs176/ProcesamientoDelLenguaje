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


string operador, s1, s2;  // string auxiliares
TablaSimbolos *tsa;
%}

%%

S       : {std::cout<< "YEPA" << std::endl;tsa = new TablaSimbolos(NULL);} algoritmo id pyc {$$.isMain = true; $$.esFunc = true; $$.pre=""; $$.tipo=1; $$.nombre="main";} Vsp Bloque {
        string l3($3.lexema);
        $$.trad = "//algoritmo " + l3 + "\n\n" + $6.trad + "\n" + $7.trad; 
        int token = yylex();
        if (token == 0) // si es fin de fichero, yylex() devuelve 0
        {
            std::cout << $$.trad << std::endl;
        }
        else{
            std::cout << "yepa" << std::endl;
            yyerror("");
        } }
        ;

Vsp     : Vsp {$$.isMain = $0.isMain; $$.pre = $0.pre;} Unsp {$$.trad = $1.trad + $3.trad;} // ESTA MAL
        | {} {$$.pre = $0.pre; $$.isMain = $0.isMain;} Unsp {$$.trad = $3.trad;}
        ;

Unsp    : funcion id dosp Tipo pyc { Simbolo s;
                                     s.nombre = $2.lexema;
                                     s.tipo = traducirTipo($4.trad);
                                     s.nomtrad = $0.pre + $2.lexema;
                                     tsa->nuevoSimbolo(s);
                                     tsa = new TablaSimbolos(tsa);
                                     $$.pre = $0.pre + $2.lexema + "_";
                                     $$.tipo = traducirTipo($4.trad);
                                     $$.nombre = $0.pre + $2.lexema;
                                     $$.esFunc = true;
                                     cout << "FUNCION NUEVA: " + s.nomtrad << endl;
                                    } Vsp Bloque pyc {
                                        $$.trad = $7.trad + $8.trad;
                                        tsa = tsa->getAmbitoAnterior();
                                    }
        | var {$$.pre = $0.isMain ? "main_" : $0.pre;} LV {$$.trad = $3.trad + "\n"; cout << "LA TRADUCCION DE UNSP: " + $$.trad;}
        ;

LV      : LV {$$.pre = $0.pre;} V {$$.trad = $1.trad + $3.trad;} // ESTA MAL
        | {} {$$.pre = $0.pre;} V {$$.trad = $3.trad;}
        ;

V       : {$$.pre = $0.pre;} Lid dosp Tipo {} pyc {
            $$.trad = $4.trad + " " + $2.trad + ";";
        }
        ; 

Lid     : Lid coma id{  Simbolo s;
                        s.nombre = $3.lexema;
                        s.tipo = -1;
                        s.nomtrad = $1.pre + $3.lexema;
                        tsa->nuevoSimbolo(s);
                        $$.trad = $1.trad + "," + $0.pre + $3.lexema;
                        
                    }
        | id {  Simbolo s;
                s.nombre = $1.lexema;
                s.tipo = -1;
                s.nomtrad = $0.pre + $1.lexema;
                tsa->nuevoSimbolo(s);
                $$.trad = $0.pre + $1.lexema;
             }
        ;

Tipo    : entero {$$.trad = "int";}
        | real   {$$.trad = "double";}
        ;

Bloque  : blq SInstr fblq   {   $$.esFunc=$-1.esFunc; $$.nombre = $-1.nombre; $$.tipo = $-1.tipo;}
                            {$$.trad = $4.esFunc ? traducirTipo($4.tipo) + " " + $4.nombre + "(){\n" + $2.trad + "\n}\n"
                                                 : "{\n" + $2.trad + "\n}\n";                        
                            }
        ;

SInstr  : SInstr pyc Instr {$$.trad = $1.trad + $3.trad;}
        | Instr {$$.trad = $1.trad;}
        ;

Instr   : id asig   {
                        Simbolo s = *tsa->buscar($1.lexema);
                        $$.tipo = s.tipo;
                    } E {string l1($1.lexema); string l2($2.lexema); $$.trad = l1 + l2 + $4.trad + "\n";}
        | {$$.esFunc=false; $$.nombre = "";} {} Bloque {$$.trad = $3.trad;}
        | si {$$.tipo = -1;} E entonces Instr ColaIf { $$.trad = "if (" + $3.trad + ")\n" + $5.trad + "\n" + $6.trad; }
        | mientras {$$.tipo=-1;} E hacer Instr {$$.trad="while( " + $3.trad + ")\n" + $5.trad;}
        | escribir pari {$$.tipo = -1;} E pard {$$.trad = $4.tipo == 1 ? "printf(\"%d\\n\"," + $4.trad + ");"
                                                                         : "printf(\"%g\\n\"," + $4.trad + ");";}
        ;

ColaIf  : fsi {$$.trad = "";}
        | sino Instr fsi {$$.trad = "else\n" + $2.trad;}
        ;

E       : Expr oprel Expr {$$.trad = $1.trad + $2.lexema + $3.trad;}
        | Expr {$$.trad = $1.trad;}
        ;

Expr    : Expr opas Term {$$.trad = $1.trad + $2.lexema + $3.trad;}
        | Term {$$.trad = $1.trad;}

Term    : Term opmd Factor {$$.trad = $1.trad + $2.lexema + $3.trad;}
        | Factor {$$.trad = $1.trad;}

Factor  : id { 
                Simbolo s = *tsa->buscar($1.lexema);
                $$.nombre = s.nomtrad;
                $$.tipo = s.tipo;
                $$.trad = s.nomtrad;
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

void msgError(int nerror,int nlin,int ncol,const char *s)
{
     switch (nerror) {
         case ERRLEXICO: fprintf(stderr,"Error lexico (%d,%d): caracter '%s' incorrecto\n",nlin,ncol,s);
            break;
         case ERRSINT: fprintf(stderr,"TETORRAS Error sintactico (%d,%d): en '%s'\n",nlin,ncol,s);
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

