/* A Bison parser, made by GNU Bison 3.5.1.  */

/* Bison interface for Yacc-like parsers in C

   Copyright (C) 1984, 1989-1990, 2000-2015, 2018-2020 Free Software Foundation,
   Inc.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* Undocumented macros, especially those whose name start with YY_,
   are private implementation details.  Do not rely on them.  */

#ifndef YY_YY_PLP5_TAB_H_INCLUDED
# define YY_YY_PLP5_TAB_H_INCLUDED
/* Debug traces.  */
#ifndef YYDEBUG
# define YYDEBUG 0
#endif
#if YYDEBUG
extern int yydebug;
#endif

/* Token type.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
  enum yytokentype
  {
    print = 258,
    id = 259,
    opas = 260,
    opmd = 261,
    oprel = 262,
    opasig = 263,
    cierto = 264,
    falso = 265,
    nentero = 266,
    nreal = 267,
    pari = 268,
    pard = 269,
    pyc = 270,
    coma = 271,
    dospto = 272,
    var = 273,
    fvar = 274,
    real = 275,
    entero = 276,
    logico = 277,
    tabla = 278,
    de = 279,
    algoritmo = 280,
    falgoritmo = 281,
    blq = 282,
    fblq = 283,
    funcion = 284,
    si = 285,
    entonces = 286,
    sino = 287,
    fsi = 288,
    mientras = 289,
    hacer = 290,
    escribe = 291,
    lee = 292,
    repetir = 293,
    hasta = 294,
    cori = 295,
    cord = 296,
    ybool = 297,
    obool = 298,
    nobool = 299
  };
#endif

/* Value type.  */
#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef int YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define YYSTYPE_IS_DECLARED 1
#endif


extern YYSTYPE yylval;

int yyparse (void);

#endif /* !YY_YY_PLP5_TAB_H_INCLUDED  */
