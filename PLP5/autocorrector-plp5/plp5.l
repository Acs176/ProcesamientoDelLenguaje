/*------------------------------ ejemplo.l -------------------------------*/
D    [0-9]
L    [a-zA-Z]
LD   [0-9a-zA-Z]

%{
#include <string.h> 
#include <string>
#include <iostream>

using namespace std;

#include "comun.h"
#include "TablaSimbolos.h"
#include "plp5.tab.h"

int ncol = 1,
    nlin = 1;

int findefichero = 0;

int ret(int token);  
// función que actualiza 'nlin' y 'ncol' y devuelve el token

void msgError(int nerror, int nlin,int ncol,const char *s);
// función para producir mensajes de error

%}

%x COMENTARIO

%%
%{
/* codigo local */
%}

" "                {ncol++;}
[\t]               {ncol++;}
[\n]               {nlin++;ncol=1;}
"//"(.)*                    {;}               
"var"                   {return ret(var);}
"fvar"                  {return ret(fvar);}
"real"                  {return ret(real);}
"entero"                {return ret(entero);}
"logico"                {return ret(logico);}
"tabla"                 {return ret(tabla);}
"de"                    {return ret(de);}
"lee"                   {return ret(lee);}
"repetir"               {return ret(repetir);}
"hasta"                 {return ret(hasta);}
"cierto"                {return ret(cierto);}
"falso"                 {return ret(falso);}
"algoritmo"             {return ret(algoritmo);}
"falgoritmo"            {return ret(falgoritmo);}
"blq"                   {return ret(blq);}
"fblq"                  {return ret(fblq);}
"si"                    {return ret(si);}
"entonces"              {return ret(entonces);}
"sino"                  {return ret(sino);}
"mientras"              {return ret(mientras);}
"hacer"                 {return ret(hacer);}
"escribe"              {return ret(escribe);}

        /* Las palabras reservadas deben aparecer antes de la regla que
          reconoce los identificadores, para evitar que sean reconocidas
          como identificadores en lugar de como palabras reservadas */            
{L}({LD})*         {return ret(id);}
{D}+               {return ret(nentero);}
{D}+(\.){D}+       {return ret(nreal);}
"("                {return ret(pari);}
")"                {return ret(pard);}
"*"                {return ret(opmd);}
"/"                {return ret(opmd);}
"+"                {return ret(opas);}
"-"                {return ret(opas);}
"<"                {return ret(oprel);}
">"                {return ret(oprel);}
"<="               {return ret(oprel);}
">="               {return ret(oprel);}
"="                {return ret(oprel);}
"<>"               {return ret(oprel);}
";"                {return ret(pyc);}
":"                {return ret(dospto);}
","                {return ret(coma);}
":="               {return ret(opasig);}
"["                {return ret(cori);}
"]"                {return ret(cord);}
"&&"                {return ret(ybool);}
"||"                {return ret(obool);}
"!"                 {return ret(nobool);}
.                  {msgError(ERRLEXICO,nlin,ncol,yytext);}

%%

int yywrap(void) {findefichero=1; return 1;} /* para no tener que linkar con la
                                libreria del lex */


int ret(int token)
{
   yylval.lexema=strdup(yytext);
   yylval.nlin=nlin;
   yylval.ncol=ncol;
   ncol+=(strlen(yytext));
   return(token);
}
