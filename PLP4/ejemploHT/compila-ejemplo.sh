#!/bin/bash

flex ejemplo.l
bison -d plp4.y 
g++ -o plp4 plp4.tab.c lex.yy.c TablaSimbolos.cc
