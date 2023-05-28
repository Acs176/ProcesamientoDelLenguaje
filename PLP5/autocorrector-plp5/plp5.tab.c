/* A Bison parser, made by GNU Bison 3.5.1.  */

/* Bison implementation for Yacc-like parsers in C

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

/* C LALR(1) parser skeleton written by Richard Stallman, by
   simplifying the original so-called "semantic" parser.  */

/* All symbols defined below should begin with yy or YY, to avoid
   infringing on user name space.  This should be done even for local
   variables, as they might otherwise be expanded by user macros.
   There are some unavoidable exceptions within include files to
   define necessary library symbols; they are noted "INFRINGES ON
   USER NAME SPACE" below.  */

/* Undocumented macros, especially those whose name start with YY_,
   are private implementation details.  Do not rely on them.  */

/* Identify Bison output.  */
#define YYBISON 1

/* Bison version.  */
#define YYBISON_VERSION "3.5.1"

/* Skeleton name.  */
#define YYSKELETON_NAME "yacc.c"

/* Pure parsers.  */
#define YYPURE 0

/* Push parsers.  */
#define YYPUSH 0

/* Pull parsers.  */
#define YYPULL 1




/* First part of user prologue.  */
#line 11 "plp5.y"


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
string getLetraTipo(int tipo);

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

#line 124 "plp5.tab.c"

# ifndef YY_CAST
#  ifdef __cplusplus
#   define YY_CAST(Type, Val) static_cast<Type> (Val)
#   define YY_REINTERPRET_CAST(Type, Val) reinterpret_cast<Type> (Val)
#  else
#   define YY_CAST(Type, Val) ((Type) (Val))
#   define YY_REINTERPRET_CAST(Type, Val) ((Type) (Val))
#  endif
# endif
# ifndef YY_NULLPTR
#  if defined __cplusplus
#   if 201103L <= __cplusplus
#    define YY_NULLPTR nullptr
#   else
#    define YY_NULLPTR 0
#   endif
#  else
#   define YY_NULLPTR ((void*)0)
#  endif
# endif

/* Enabling verbose error messages.  */
#ifdef YYERROR_VERBOSE
# undef YYERROR_VERBOSE
# define YYERROR_VERBOSE 1
#else
# define YYERROR_VERBOSE 0
#endif

/* Use api.header.include to #include this header
   instead of duplicating it here.  */
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



#ifdef short
# undef short
#endif

/* On compilers that do not define __PTRDIFF_MAX__ etc., make sure
   <limits.h> and (if available) <stdint.h> are included
   so that the code can choose integer types of a good width.  */

#ifndef __PTRDIFF_MAX__
# include <limits.h> /* INFRINGES ON USER NAME SPACE */
# if defined __STDC_VERSION__ && 199901 <= __STDC_VERSION__
#  include <stdint.h> /* INFRINGES ON USER NAME SPACE */
#  define YY_STDINT_H
# endif
#endif

/* Narrow types that promote to a signed type and that can represent a
   signed or unsigned integer of at least N bits.  In tables they can
   save space and decrease cache pressure.  Promoting to a signed type
   helps avoid bugs in integer arithmetic.  */

#ifdef __INT_LEAST8_MAX__
typedef __INT_LEAST8_TYPE__ yytype_int8;
#elif defined YY_STDINT_H
typedef int_least8_t yytype_int8;
#else
typedef signed char yytype_int8;
#endif

#ifdef __INT_LEAST16_MAX__
typedef __INT_LEAST16_TYPE__ yytype_int16;
#elif defined YY_STDINT_H
typedef int_least16_t yytype_int16;
#else
typedef short yytype_int16;
#endif

#if defined __UINT_LEAST8_MAX__ && __UINT_LEAST8_MAX__ <= __INT_MAX__
typedef __UINT_LEAST8_TYPE__ yytype_uint8;
#elif (!defined __UINT_LEAST8_MAX__ && defined YY_STDINT_H \
       && UINT_LEAST8_MAX <= INT_MAX)
typedef uint_least8_t yytype_uint8;
#elif !defined __UINT_LEAST8_MAX__ && UCHAR_MAX <= INT_MAX
typedef unsigned char yytype_uint8;
#else
typedef short yytype_uint8;
#endif

#if defined __UINT_LEAST16_MAX__ && __UINT_LEAST16_MAX__ <= __INT_MAX__
typedef __UINT_LEAST16_TYPE__ yytype_uint16;
#elif (!defined __UINT_LEAST16_MAX__ && defined YY_STDINT_H \
       && UINT_LEAST16_MAX <= INT_MAX)
typedef uint_least16_t yytype_uint16;
#elif !defined __UINT_LEAST16_MAX__ && USHRT_MAX <= INT_MAX
typedef unsigned short yytype_uint16;
#else
typedef int yytype_uint16;
#endif

#ifndef YYPTRDIFF_T
# if defined __PTRDIFF_TYPE__ && defined __PTRDIFF_MAX__
#  define YYPTRDIFF_T __PTRDIFF_TYPE__
#  define YYPTRDIFF_MAXIMUM __PTRDIFF_MAX__
# elif defined PTRDIFF_MAX
#  ifndef ptrdiff_t
#   include <stddef.h> /* INFRINGES ON USER NAME SPACE */
#  endif
#  define YYPTRDIFF_T ptrdiff_t
#  define YYPTRDIFF_MAXIMUM PTRDIFF_MAX
# else
#  define YYPTRDIFF_T long
#  define YYPTRDIFF_MAXIMUM LONG_MAX
# endif
#endif

#ifndef YYSIZE_T
# ifdef __SIZE_TYPE__
#  define YYSIZE_T __SIZE_TYPE__
# elif defined size_t
#  define YYSIZE_T size_t
# elif defined __STDC_VERSION__ && 199901 <= __STDC_VERSION__
#  include <stddef.h> /* INFRINGES ON USER NAME SPACE */
#  define YYSIZE_T size_t
# else
#  define YYSIZE_T unsigned
# endif
#endif

#define YYSIZE_MAXIMUM                                  \
  YY_CAST (YYPTRDIFF_T,                                 \
           (YYPTRDIFF_MAXIMUM < YY_CAST (YYSIZE_T, -1)  \
            ? YYPTRDIFF_MAXIMUM                         \
            : YY_CAST (YYSIZE_T, -1)))

#define YYSIZEOF(X) YY_CAST (YYPTRDIFF_T, sizeof (X))

/* Stored state numbers (used for stacks). */
typedef yytype_int8 yy_state_t;

/* State numbers in computations.  */
typedef int yy_state_fast_t;

#ifndef YY_
# if defined YYENABLE_NLS && YYENABLE_NLS
#  if ENABLE_NLS
#   include <libintl.h> /* INFRINGES ON USER NAME SPACE */
#   define YY_(Msgid) dgettext ("bison-runtime", Msgid)
#  endif
# endif
# ifndef YY_
#  define YY_(Msgid) Msgid
# endif
#endif

#ifndef YY_ATTRIBUTE_PURE
# if defined __GNUC__ && 2 < __GNUC__ + (96 <= __GNUC_MINOR__)
#  define YY_ATTRIBUTE_PURE __attribute__ ((__pure__))
# else
#  define YY_ATTRIBUTE_PURE
# endif
#endif

#ifndef YY_ATTRIBUTE_UNUSED
# if defined __GNUC__ && 2 < __GNUC__ + (7 <= __GNUC_MINOR__)
#  define YY_ATTRIBUTE_UNUSED __attribute__ ((__unused__))
# else
#  define YY_ATTRIBUTE_UNUSED
# endif
#endif

/* Suppress unused-variable warnings by "using" E.  */
#if ! defined lint || defined __GNUC__
# define YYUSE(E) ((void) (E))
#else
# define YYUSE(E) /* empty */
#endif

#if defined __GNUC__ && ! defined __ICC && 407 <= __GNUC__ * 100 + __GNUC_MINOR__
/* Suppress an incorrect diagnostic about yylval being uninitialized.  */
# define YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN                            \
    _Pragma ("GCC diagnostic push")                                     \
    _Pragma ("GCC diagnostic ignored \"-Wuninitialized\"")              \
    _Pragma ("GCC diagnostic ignored \"-Wmaybe-uninitialized\"")
# define YY_IGNORE_MAYBE_UNINITIALIZED_END      \
    _Pragma ("GCC diagnostic pop")
#else
# define YY_INITIAL_VALUE(Value) Value
#endif
#ifndef YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
# define YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
# define YY_IGNORE_MAYBE_UNINITIALIZED_END
#endif
#ifndef YY_INITIAL_VALUE
# define YY_INITIAL_VALUE(Value) /* Nothing. */
#endif

#if defined __cplusplus && defined __GNUC__ && ! defined __ICC && 6 <= __GNUC__
# define YY_IGNORE_USELESS_CAST_BEGIN                          \
    _Pragma ("GCC diagnostic push")                            \
    _Pragma ("GCC diagnostic ignored \"-Wuseless-cast\"")
# define YY_IGNORE_USELESS_CAST_END            \
    _Pragma ("GCC diagnostic pop")
#endif
#ifndef YY_IGNORE_USELESS_CAST_BEGIN
# define YY_IGNORE_USELESS_CAST_BEGIN
# define YY_IGNORE_USELESS_CAST_END
#endif


#define YY_ASSERT(E) ((void) (0 && (E)))

#if ! defined yyoverflow || YYERROR_VERBOSE

/* The parser invokes alloca or malloc; define the necessary symbols.  */

# ifdef YYSTACK_USE_ALLOCA
#  if YYSTACK_USE_ALLOCA
#   ifdef __GNUC__
#    define YYSTACK_ALLOC __builtin_alloca
#   elif defined __BUILTIN_VA_ARG_INCR
#    include <alloca.h> /* INFRINGES ON USER NAME SPACE */
#   elif defined _AIX
#    define YYSTACK_ALLOC __alloca
#   elif defined _MSC_VER
#    include <malloc.h> /* INFRINGES ON USER NAME SPACE */
#    define alloca _alloca
#   else
#    define YYSTACK_ALLOC alloca
#    if ! defined _ALLOCA_H && ! defined EXIT_SUCCESS
#     include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
      /* Use EXIT_SUCCESS as a witness for stdlib.h.  */
#     ifndef EXIT_SUCCESS
#      define EXIT_SUCCESS 0
#     endif
#    endif
#   endif
#  endif
# endif

# ifdef YYSTACK_ALLOC
   /* Pacify GCC's 'empty if-body' warning.  */
#  define YYSTACK_FREE(Ptr) do { /* empty */; } while (0)
#  ifndef YYSTACK_ALLOC_MAXIMUM
    /* The OS might guarantee only one guard page at the bottom of the stack,
       and a page size can be as small as 4096 bytes.  So we cannot safely
       invoke alloca (N) if N exceeds 4096.  Use a slightly smaller number
       to allow for a few compiler-allocated temporary stack slots.  */
#   define YYSTACK_ALLOC_MAXIMUM 4032 /* reasonable circa 2006 */
#  endif
# else
#  define YYSTACK_ALLOC YYMALLOC
#  define YYSTACK_FREE YYFREE
#  ifndef YYSTACK_ALLOC_MAXIMUM
#   define YYSTACK_ALLOC_MAXIMUM YYSIZE_MAXIMUM
#  endif
#  if (defined __cplusplus && ! defined EXIT_SUCCESS \
       && ! ((defined YYMALLOC || defined malloc) \
             && (defined YYFREE || defined free)))
#   include <stdlib.h> /* INFRINGES ON USER NAME SPACE */
#   ifndef EXIT_SUCCESS
#    define EXIT_SUCCESS 0
#   endif
#  endif
#  ifndef YYMALLOC
#   define YYMALLOC malloc
#   if ! defined malloc && ! defined EXIT_SUCCESS
void *malloc (YYSIZE_T); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
#  ifndef YYFREE
#   define YYFREE free
#   if ! defined free && ! defined EXIT_SUCCESS
void free (void *); /* INFRINGES ON USER NAME SPACE */
#   endif
#  endif
# endif
#endif /* ! defined yyoverflow || YYERROR_VERBOSE */


#if (! defined yyoverflow \
     && (! defined __cplusplus \
         || (defined YYSTYPE_IS_TRIVIAL && YYSTYPE_IS_TRIVIAL)))

/* A type that is properly aligned for any stack member.  */
union yyalloc
{
  yy_state_t yyss_alloc;
  YYSTYPE yyvs_alloc;
};

/* The size of the maximum gap between one aligned stack and the next.  */
# define YYSTACK_GAP_MAXIMUM (YYSIZEOF (union yyalloc) - 1)

/* The size of an array large to enough to hold all stacks, each with
   N elements.  */
# define YYSTACK_BYTES(N) \
     ((N) * (YYSIZEOF (yy_state_t) + YYSIZEOF (YYSTYPE)) \
      + YYSTACK_GAP_MAXIMUM)

# define YYCOPY_NEEDED 1

/* Relocate STACK from its old location to the new one.  The
   local variables YYSIZE and YYSTACKSIZE give the old and new number of
   elements in the stack, and YYPTR gives the new location of the
   stack.  Advance YYPTR to a properly aligned location for the next
   stack.  */
# define YYSTACK_RELOCATE(Stack_alloc, Stack)                           \
    do                                                                  \
      {                                                                 \
        YYPTRDIFF_T yynewbytes;                                         \
        YYCOPY (&yyptr->Stack_alloc, Stack, yysize);                    \
        Stack = &yyptr->Stack_alloc;                                    \
        yynewbytes = yystacksize * YYSIZEOF (*Stack) + YYSTACK_GAP_MAXIMUM; \
        yyptr += yynewbytes / YYSIZEOF (*yyptr);                        \
      }                                                                 \
    while (0)

#endif

#if defined YYCOPY_NEEDED && YYCOPY_NEEDED
/* Copy COUNT objects from SRC to DST.  The source and destination do
   not overlap.  */
# ifndef YYCOPY
#  if defined __GNUC__ && 1 < __GNUC__
#   define YYCOPY(Dst, Src, Count) \
      __builtin_memcpy (Dst, Src, YY_CAST (YYSIZE_T, (Count)) * sizeof (*(Src)))
#  else
#   define YYCOPY(Dst, Src, Count)              \
      do                                        \
        {                                       \
          YYPTRDIFF_T yyi;                      \
          for (yyi = 0; yyi < (Count); yyi++)   \
            (Dst)[yyi] = (Src)[yyi];            \
        }                                       \
      while (0)
#  endif
# endif
#endif /* !YYCOPY_NEEDED */

/* YYFINAL -- State number of the termination state.  */
#define YYFINAL  3
/* YYLAST -- Last index in YYTABLE.  */
#define YYLAST   95

/* YYNTOKENS -- Number of terminals.  */
#define YYNTOKENS  45
/* YYNNTS -- Number of nonterminals.  */
#define YYNNTS  24
/* YYNRULES -- Number of rules.  */
#define YYNRULES  51
/* YYNSTATES -- Number of states.  */
#define YYNSTATES  97

#define YYUNDEFTOK  2
#define YYMAXUTOK   299


/* YYTRANSLATE(TOKEN-NUM) -- Symbol number corresponding to TOKEN-NUM
   as returned by yylex, with out-of-bounds checking.  */
#define YYTRANSLATE(YYX)                                                \
  (0 <= (YYX) && (YYX) <= YYMAXUTOK ? yytranslate[YYX] : YYUNDEFTOK)

/* YYTRANSLATE[TOKEN-NUM] -- Symbol number corresponding to TOKEN-NUM
   as returned by yylex.  */
static const yytype_int8 yytranslate[] =
{
       0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    38,    39,    40,    41,    42,    43,    44
};

#if YYDEBUG
  /* YYRLINE[YYN] -- Source line where rule number YYN was defined.  */
static const yytype_int16 yyrline[] =
{
       0,    67,    67,    67,    71,    72,    75,    78,    78,    89,
      90,    93,    93,   102,   105,   106,   107,   108,   111,   111,
     112,   112,   115,   116,   117,   128,   139,   148,   156,   160,
     160,   163,   177,   180,   193,   196,   232,   235,   271,   272,
     283,   321,   324,   325,   329,   334,   335,   341,   344,   349,
     360,   360
};
#endif

#if YYDEBUG || YYERROR_VERBOSE || 0
/* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
   First, the terminals, then, starting at YYNTOKENS, nonterminals.  */
static const char *const yytname[] =
{
  "$end", "error", "$undefined", "print", "id", "opas", "opmd", "oprel",
  "opasig", "cierto", "falso", "nentero", "nreal", "pari", "pard", "pyc",
  "coma", "dospto", "var", "fvar", "real", "entero", "logico", "tabla",
  "de", "algoritmo", "falgoritmo", "blq", "fblq", "funcion", "si",
  "entonces", "sino", "fsi", "mientras", "hacer", "escribe", "lee",
  "repetir", "hasta", "cori", "cord", "ybool", "obool", "nobool",
  "$accept", "S", "$@1", "SDec", "Dec", "DVar", "@2", "MDVar", "Lid", "@3",
  "Tipo", "SInstr", "@4", "@5", "Instr", "$@6", "Expr", "Econj", "Ecomp",
  "Esimple", "Term", "Factor", "Ref", "$@7", YY_NULLPTR
};
#endif

# ifdef YYPRINT
/* YYTOKNUM[NUM] -- (External) token number corresponding to the
   (internal) symbol number NUM (which must be that of a token).  */
static const yytype_int16 yytoknum[] =
{
       0,   256,   257,   258,   259,   260,   261,   262,   263,   264,
     265,   266,   267,   268,   269,   270,   271,   272,   273,   274,
     275,   276,   277,   278,   279,   280,   281,   282,   283,   284,
     285,   286,   287,   288,   289,   290,   291,   292,   293,   294,
     295,   296,   297,   298,   299
};
# endif

#define YYPACT_NINF (-65)

#define yypact_value_is_default(Yyn) \
  ((Yyn) == YYPACT_NINF)

#define YYTABLE_NINF (-1)

#define yytable_value_is_error(Yyn) \
  0

  /* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
     STATE-NUM.  */
static const yytype_int8 yypact[] =
{
     -65,     7,   -15,   -65,    -3,    24,    11,    47,   -65,   -65,
     -65,   -65,   -65,    19,    47,    17,    36,    23,    30,    47,
      28,    60,   -65,   -65,   -65,   -65,    12,    12,    12,    61,
      23,   -65,    -4,    47,   -65,   -65,   -65,    23,    11,    29,
     -65,   -65,   -65,   -65,    12,    29,    15,    35,   -65,    71,
      65,   -65,    32,    31,    37,    32,    40,    12,   -65,   -65,
      66,   -65,   -65,    65,     1,   -65,    23,    12,    12,    29,
      12,    29,    23,    12,    37,    12,    77,    68,     3,   -65,
      52,    35,   -65,    65,    80,   -65,   -65,    37,     4,   -65,
     -65,   -65,    23,   -65,    66,   -65,   -65
};

  /* YYDEFACT[STATE-NUM] -- Default reduction number in state STATE-NUM.
     Performed when YYTABLE does not specify something else to do.  Zero
     means the default is an error.  */
static const yytype_int8 yydefact[] =
{
       2,     0,     0,     1,     0,     0,     5,     0,    20,     4,
      15,    14,    16,     0,    10,     0,     0,     0,     0,    10,
       0,     0,    18,     3,    49,    29,     0,     0,     0,     0,
       0,    21,     0,     0,     9,     6,     7,     0,     5,     0,
      47,    48,    43,    44,     0,     0,     0,    32,    34,    36,
      38,    41,    42,     0,    22,    23,     0,     0,    50,    17,
      13,    19,    20,    39,     0,    46,     0,     0,     0,     0,
       0,     0,     0,     0,    28,     0,     0,     0,     0,    45,
      24,    31,    33,    37,    35,    40,    26,    27,     0,    11,
       8,    30,     0,    51,    13,    25,    12
};

  /* YYPGOTO[NTERM-NUM].  */
static const yytype_int8 yypgoto[] =
{
     -65,   -65,   -65,    48,   -65,    81,   -65,    70,    -7,   -65,
      57,    33,   -65,   -65,   -29,   -65,   -25,    25,    26,   -64,
     -34,   -19,   -17,   -65
};

  /* YYDEFGOTO[NTERM-NUM].  */
static const yytype_int8 yydefgoto[] =
{
      -1,     1,     2,     8,     9,    19,    60,    20,    77,    94,
      15,    16,    37,    17,    31,    38,    46,    47,    48,    49,
      50,    51,    52,    75
};

  /* YYTABLE[YYPACT[STATE-NUM]] -- What to do in state STATE-NUM.  If
     positive, shift that token.  If negative, reduce the rule whose
     number is the opposite.  If YYTABLE_NINF, syntax error.  */
static const yytype_int8 yytable[] =
{
      32,    56,    53,    54,    57,    63,    84,     3,    61,    69,
       4,    88,    55,    32,     5,    79,    24,    39,    22,    64,
      32,    40,    41,    42,    43,    44,    65,    24,     6,     7,
      18,    91,    74,    24,    21,    83,    58,    80,    40,    41,
      42,    43,    44,    86,    67,    93,    66,    35,    87,    32,
      25,    22,    85,    26,    33,    32,    45,    27,    67,    28,
      29,    30,    23,    95,    36,    24,    72,    10,    11,    12,
      13,    71,    58,    45,    67,    32,    69,    68,    70,    73,
      67,    89,    76,    90,    92,    69,    62,    96,    14,    34,
      59,     0,    81,     0,    82,    78
};

static const yytype_int8 yycheck[] =
{
      17,    30,    27,    28,     8,    39,    70,     0,    37,     5,
      25,    75,    29,    30,    17,    14,     4,     5,    15,    44,
      37,     9,    10,    11,    12,    13,    45,     4,     4,    18,
      11,    28,    57,     4,    17,    69,    40,    66,     9,    10,
      11,    12,    13,    72,    43,    41,    31,    19,    73,    66,
      27,    15,    71,    30,    24,    72,    44,    34,    43,    36,
      37,    38,    26,    92,     4,     4,    35,    20,    21,    22,
      23,     6,    40,    44,    43,    92,     5,    42,     7,    39,
      43,     4,    16,    15,    32,     5,    38,    94,     7,    19,
      33,    -1,    67,    -1,    68,    62
};

  /* YYSTOS[STATE-NUM] -- The (internal number of the) accessing
     symbol of state STATE-NUM.  */
static const yytype_int8 yystos[] =
{
       0,    46,    47,     0,    25,    17,     4,    18,    48,    49,
      20,    21,    22,    23,    50,    55,    56,    58,    11,    50,
      52,    17,    15,    26,     4,    27,    30,    34,    36,    37,
      38,    59,    67,    24,    52,    19,     4,    57,    60,     5,
       9,    10,    11,    12,    13,    44,    61,    62,    63,    64,
      65,    66,    67,    61,    61,    67,    59,     8,    40,    55,
      51,    59,    48,    65,    61,    66,    31,    43,    42,     5,
       7,     6,    35,    39,    61,    68,    16,    53,    56,    14,
      59,    62,    63,    65,    64,    66,    59,    61,    64,     4,
      15,    28,    32,    41,    54,    59,    53
};

  /* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */
static const yytype_int8 yyr1[] =
{
       0,    45,    47,    46,    48,    48,    49,    51,    50,    52,
      52,    54,    53,    53,    55,    55,    55,    55,    57,    56,
      58,    56,    59,    59,    59,    59,    59,    59,    59,    60,
      59,    61,    61,    62,    62,    63,    63,    64,    64,    64,
      65,    65,    66,    66,    66,    66,    66,    66,    66,    67,
      68,    67
};

  /* YYR2[YYN] -- Number of symbols on the right hand side of rule YYN.  */
static const yytype_int8 yyr2[] =
{
       0,     2,     0,     7,     1,     0,     4,     0,     6,     2,
       0,     0,     4,     0,     1,     1,     1,     4,     0,     4,
       0,     2,     2,     2,     4,     6,     4,     4,     3,     0,
       5,     3,     1,     3,     1,     3,     1,     3,     1,     2,
       3,     1,     1,     1,     1,     3,     2,     1,     1,     1,
       0,     5
};


#define yyerrok         (yyerrstatus = 0)
#define yyclearin       (yychar = YYEMPTY)
#define YYEMPTY         (-2)
#define YYEOF           0

#define YYACCEPT        goto yyacceptlab
#define YYABORT         goto yyabortlab
#define YYERROR         goto yyerrorlab


#define YYRECOVERING()  (!!yyerrstatus)

#define YYBACKUP(Token, Value)                                    \
  do                                                              \
    if (yychar == YYEMPTY)                                        \
      {                                                           \
        yychar = (Token);                                         \
        yylval = (Value);                                         \
        YYPOPSTACK (yylen);                                       \
        yystate = *yyssp;                                         \
        goto yybackup;                                            \
      }                                                           \
    else                                                          \
      {                                                           \
        yyerror (YY_("syntax error: cannot back up")); \
        YYERROR;                                                  \
      }                                                           \
  while (0)

/* Error token number */
#define YYTERROR        1
#define YYERRCODE       256



/* Enable debugging if requested.  */
#if YYDEBUG

# ifndef YYFPRINTF
#  include <stdio.h> /* INFRINGES ON USER NAME SPACE */
#  define YYFPRINTF fprintf
# endif

# define YYDPRINTF(Args)                        \
do {                                            \
  if (yydebug)                                  \
    YYFPRINTF Args;                             \
} while (0)

/* This macro is provided for backward compatibility. */
#ifndef YY_LOCATION_PRINT
# define YY_LOCATION_PRINT(File, Loc) ((void) 0)
#endif


# define YY_SYMBOL_PRINT(Title, Type, Value, Location)                    \
do {                                                                      \
  if (yydebug)                                                            \
    {                                                                     \
      YYFPRINTF (stderr, "%s ", Title);                                   \
      yy_symbol_print (stderr,                                            \
                  Type, Value); \
      YYFPRINTF (stderr, "\n");                                           \
    }                                                                     \
} while (0)


/*-----------------------------------.
| Print this symbol's value on YYO.  |
`-----------------------------------*/

static void
yy_symbol_value_print (FILE *yyo, int yytype, YYSTYPE const * const yyvaluep)
{
  FILE *yyoutput = yyo;
  YYUSE (yyoutput);
  if (!yyvaluep)
    return;
# ifdef YYPRINT
  if (yytype < YYNTOKENS)
    YYPRINT (yyo, yytoknum[yytype], *yyvaluep);
# endif
  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  YYUSE (yytype);
  YY_IGNORE_MAYBE_UNINITIALIZED_END
}


/*---------------------------.
| Print this symbol on YYO.  |
`---------------------------*/

static void
yy_symbol_print (FILE *yyo, int yytype, YYSTYPE const * const yyvaluep)
{
  YYFPRINTF (yyo, "%s %s (",
             yytype < YYNTOKENS ? "token" : "nterm", yytname[yytype]);

  yy_symbol_value_print (yyo, yytype, yyvaluep);
  YYFPRINTF (yyo, ")");
}

/*------------------------------------------------------------------.
| yy_stack_print -- Print the state stack from its BOTTOM up to its |
| TOP (included).                                                   |
`------------------------------------------------------------------*/

static void
yy_stack_print (yy_state_t *yybottom, yy_state_t *yytop)
{
  YYFPRINTF (stderr, "Stack now");
  for (; yybottom <= yytop; yybottom++)
    {
      int yybot = *yybottom;
      YYFPRINTF (stderr, " %d", yybot);
    }
  YYFPRINTF (stderr, "\n");
}

# define YY_STACK_PRINT(Bottom, Top)                            \
do {                                                            \
  if (yydebug)                                                  \
    yy_stack_print ((Bottom), (Top));                           \
} while (0)


/*------------------------------------------------.
| Report that the YYRULE is going to be reduced.  |
`------------------------------------------------*/

static void
yy_reduce_print (yy_state_t *yyssp, YYSTYPE *yyvsp, int yyrule)
{
  int yylno = yyrline[yyrule];
  int yynrhs = yyr2[yyrule];
  int yyi;
  YYFPRINTF (stderr, "Reducing stack by rule %d (line %d):\n",
             yyrule - 1, yylno);
  /* The symbols being reduced.  */
  for (yyi = 0; yyi < yynrhs; yyi++)
    {
      YYFPRINTF (stderr, "   $%d = ", yyi + 1);
      yy_symbol_print (stderr,
                       yystos[+yyssp[yyi + 1 - yynrhs]],
                       &yyvsp[(yyi + 1) - (yynrhs)]
                                              );
      YYFPRINTF (stderr, "\n");
    }
}

# define YY_REDUCE_PRINT(Rule)          \
do {                                    \
  if (yydebug)                          \
    yy_reduce_print (yyssp, yyvsp, Rule); \
} while (0)

/* Nonzero means print parse trace.  It is left uninitialized so that
   multiple parsers can coexist.  */
int yydebug;
#else /* !YYDEBUG */
# define YYDPRINTF(Args)
# define YY_SYMBOL_PRINT(Title, Type, Value, Location)
# define YY_STACK_PRINT(Bottom, Top)
# define YY_REDUCE_PRINT(Rule)
#endif /* !YYDEBUG */


/* YYINITDEPTH -- initial size of the parser's stacks.  */
#ifndef YYINITDEPTH
# define YYINITDEPTH 200
#endif

/* YYMAXDEPTH -- maximum size the stacks can grow to (effective only
   if the built-in stack extension method is used).

   Do not make this value too large; the results are undefined if
   YYSTACK_ALLOC_MAXIMUM < YYSTACK_BYTES (YYMAXDEPTH)
   evaluated with infinite-precision integer arithmetic.  */

#ifndef YYMAXDEPTH
# define YYMAXDEPTH 10000
#endif


#if YYERROR_VERBOSE

# ifndef yystrlen
#  if defined __GLIBC__ && defined _STRING_H
#   define yystrlen(S) (YY_CAST (YYPTRDIFF_T, strlen (S)))
#  else
/* Return the length of YYSTR.  */
static YYPTRDIFF_T
yystrlen (const char *yystr)
{
  YYPTRDIFF_T yylen;
  for (yylen = 0; yystr[yylen]; yylen++)
    continue;
  return yylen;
}
#  endif
# endif

# ifndef yystpcpy
#  if defined __GLIBC__ && defined _STRING_H && defined _GNU_SOURCE
#   define yystpcpy stpcpy
#  else
/* Copy YYSRC to YYDEST, returning the address of the terminating '\0' in
   YYDEST.  */
static char *
yystpcpy (char *yydest, const char *yysrc)
{
  char *yyd = yydest;
  const char *yys = yysrc;

  while ((*yyd++ = *yys++) != '\0')
    continue;

  return yyd - 1;
}
#  endif
# endif

# ifndef yytnamerr
/* Copy to YYRES the contents of YYSTR after stripping away unnecessary
   quotes and backslashes, so that it's suitable for yyerror.  The
   heuristic is that double-quoting is unnecessary unless the string
   contains an apostrophe, a comma, or backslash (other than
   backslash-backslash).  YYSTR is taken from yytname.  If YYRES is
   null, do not copy; instead, return the length of what the result
   would have been.  */
static YYPTRDIFF_T
yytnamerr (char *yyres, const char *yystr)
{
  if (*yystr == '"')
    {
      YYPTRDIFF_T yyn = 0;
      char const *yyp = yystr;

      for (;;)
        switch (*++yyp)
          {
          case '\'':
          case ',':
            goto do_not_strip_quotes;

          case '\\':
            if (*++yyp != '\\')
              goto do_not_strip_quotes;
            else
              goto append;

          append:
          default:
            if (yyres)
              yyres[yyn] = *yyp;
            yyn++;
            break;

          case '"':
            if (yyres)
              yyres[yyn] = '\0';
            return yyn;
          }
    do_not_strip_quotes: ;
    }

  if (yyres)
    return yystpcpy (yyres, yystr) - yyres;
  else
    return yystrlen (yystr);
}
# endif

/* Copy into *YYMSG, which is of size *YYMSG_ALLOC, an error message
   about the unexpected token YYTOKEN for the state stack whose top is
   YYSSP.

   Return 0 if *YYMSG was successfully written.  Return 1 if *YYMSG is
   not large enough to hold the message.  In that case, also set
   *YYMSG_ALLOC to the required number of bytes.  Return 2 if the
   required number of bytes is too large to store.  */
static int
yysyntax_error (YYPTRDIFF_T *yymsg_alloc, char **yymsg,
                yy_state_t *yyssp, int yytoken)
{
  enum { YYERROR_VERBOSE_ARGS_MAXIMUM = 5 };
  /* Internationalized format string. */
  const char *yyformat = YY_NULLPTR;
  /* Arguments of yyformat: reported tokens (one for the "unexpected",
     one per "expected"). */
  char const *yyarg[YYERROR_VERBOSE_ARGS_MAXIMUM];
  /* Actual size of YYARG. */
  int yycount = 0;
  /* Cumulated lengths of YYARG.  */
  YYPTRDIFF_T yysize = 0;

  /* There are many possibilities here to consider:
     - If this state is a consistent state with a default action, then
       the only way this function was invoked is if the default action
       is an error action.  In that case, don't check for expected
       tokens because there are none.
     - The only way there can be no lookahead present (in yychar) is if
       this state is a consistent state with a default action.  Thus,
       detecting the absence of a lookahead is sufficient to determine
       that there is no unexpected or expected token to report.  In that
       case, just report a simple "syntax error".
     - Don't assume there isn't a lookahead just because this state is a
       consistent state with a default action.  There might have been a
       previous inconsistent state, consistent state with a non-default
       action, or user semantic action that manipulated yychar.
     - Of course, the expected token list depends on states to have
       correct lookahead information, and it depends on the parser not
       to perform extra reductions after fetching a lookahead from the
       scanner and before detecting a syntax error.  Thus, state merging
       (from LALR or IELR) and default reductions corrupt the expected
       token list.  However, the list is correct for canonical LR with
       one exception: it will still contain any token that will not be
       accepted due to an error action in a later state.
  */
  if (yytoken != YYEMPTY)
    {
      int yyn = yypact[+*yyssp];
      YYPTRDIFF_T yysize0 = yytnamerr (YY_NULLPTR, yytname[yytoken]);
      yysize = yysize0;
      yyarg[yycount++] = yytname[yytoken];
      if (!yypact_value_is_default (yyn))
        {
          /* Start YYX at -YYN if negative to avoid negative indexes in
             YYCHECK.  In other words, skip the first -YYN actions for
             this state because they are default actions.  */
          int yyxbegin = yyn < 0 ? -yyn : 0;
          /* Stay within bounds of both yycheck and yytname.  */
          int yychecklim = YYLAST - yyn + 1;
          int yyxend = yychecklim < YYNTOKENS ? yychecklim : YYNTOKENS;
          int yyx;

          for (yyx = yyxbegin; yyx < yyxend; ++yyx)
            if (yycheck[yyx + yyn] == yyx && yyx != YYTERROR
                && !yytable_value_is_error (yytable[yyx + yyn]))
              {
                if (yycount == YYERROR_VERBOSE_ARGS_MAXIMUM)
                  {
                    yycount = 1;
                    yysize = yysize0;
                    break;
                  }
                yyarg[yycount++] = yytname[yyx];
                {
                  YYPTRDIFF_T yysize1
                    = yysize + yytnamerr (YY_NULLPTR, yytname[yyx]);
                  if (yysize <= yysize1 && yysize1 <= YYSTACK_ALLOC_MAXIMUM)
                    yysize = yysize1;
                  else
                    return 2;
                }
              }
        }
    }

  switch (yycount)
    {
# define YYCASE_(N, S)                      \
      case N:                               \
        yyformat = S;                       \
      break
    default: /* Avoid compiler warnings. */
      YYCASE_(0, YY_("syntax error"));
      YYCASE_(1, YY_("syntax error, unexpected %s"));
      YYCASE_(2, YY_("syntax error, unexpected %s, expecting %s"));
      YYCASE_(3, YY_("syntax error, unexpected %s, expecting %s or %s"));
      YYCASE_(4, YY_("syntax error, unexpected %s, expecting %s or %s or %s"));
      YYCASE_(5, YY_("syntax error, unexpected %s, expecting %s or %s or %s or %s"));
# undef YYCASE_
    }

  {
    /* Don't count the "%s"s in the final size, but reserve room for
       the terminator.  */
    YYPTRDIFF_T yysize1 = yysize + (yystrlen (yyformat) - 2 * yycount) + 1;
    if (yysize <= yysize1 && yysize1 <= YYSTACK_ALLOC_MAXIMUM)
      yysize = yysize1;
    else
      return 2;
  }

  if (*yymsg_alloc < yysize)
    {
      *yymsg_alloc = 2 * yysize;
      if (! (yysize <= *yymsg_alloc
             && *yymsg_alloc <= YYSTACK_ALLOC_MAXIMUM))
        *yymsg_alloc = YYSTACK_ALLOC_MAXIMUM;
      return 1;
    }

  /* Avoid sprintf, as that infringes on the user's name space.
     Don't have undefined behavior even if the translation
     produced a string with the wrong number of "%s"s.  */
  {
    char *yyp = *yymsg;
    int yyi = 0;
    while ((*yyp = *yyformat) != '\0')
      if (*yyp == '%' && yyformat[1] == 's' && yyi < yycount)
        {
          yyp += yytnamerr (yyp, yyarg[yyi++]);
          yyformat += 2;
        }
      else
        {
          ++yyp;
          ++yyformat;
        }
  }
  return 0;
}
#endif /* YYERROR_VERBOSE */

/*-----------------------------------------------.
| Release the memory associated to this symbol.  |
`-----------------------------------------------*/

static void
yydestruct (const char *yymsg, int yytype, YYSTYPE *yyvaluep)
{
  YYUSE (yyvaluep);
  if (!yymsg)
    yymsg = "Deleting";
  YY_SYMBOL_PRINT (yymsg, yytype, yyvaluep, yylocationp);

  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  YYUSE (yytype);
  YY_IGNORE_MAYBE_UNINITIALIZED_END
}




/* The lookahead symbol.  */
int yychar;

/* The semantic value of the lookahead symbol.  */
YYSTYPE yylval;
/* Number of syntax errors so far.  */
int yynerrs;


/*----------.
| yyparse.  |
`----------*/

int
yyparse (void)
{
    yy_state_fast_t yystate;
    /* Number of tokens to shift before error messages enabled.  */
    int yyerrstatus;

    /* The stacks and their tools:
       'yyss': related to states.
       'yyvs': related to semantic values.

       Refer to the stacks through separate pointers, to allow yyoverflow
       to reallocate them elsewhere.  */

    /* The state stack.  */
    yy_state_t yyssa[YYINITDEPTH];
    yy_state_t *yyss;
    yy_state_t *yyssp;

    /* The semantic value stack.  */
    YYSTYPE yyvsa[YYINITDEPTH];
    YYSTYPE *yyvs;
    YYSTYPE *yyvsp;

    YYPTRDIFF_T yystacksize;

  int yyn;
  int yyresult;
  /* Lookahead token as an internal (translated) token number.  */
  int yytoken = 0;
  /* The variables used to return semantic value and location from the
     action routines.  */
  YYSTYPE yyval;

#if YYERROR_VERBOSE
  /* Buffer for error messages, and its allocated size.  */
  char yymsgbuf[128];
  char *yymsg = yymsgbuf;
  YYPTRDIFF_T yymsg_alloc = sizeof yymsgbuf;
#endif

#define YYPOPSTACK(N)   (yyvsp -= (N), yyssp -= (N))

  /* The number of symbols on the RHS of the reduced rule.
     Keep to zero when no symbol should be popped.  */
  int yylen = 0;

  yyssp = yyss = yyssa;
  yyvsp = yyvs = yyvsa;
  yystacksize = YYINITDEPTH;

  YYDPRINTF ((stderr, "Starting parse\n"));

  yystate = 0;
  yyerrstatus = 0;
  yynerrs = 0;
  yychar = YYEMPTY; /* Cause a token to be read.  */
  goto yysetstate;


/*------------------------------------------------------------.
| yynewstate -- push a new state, which is found in yystate.  |
`------------------------------------------------------------*/
yynewstate:
  /* In all cases, when you get here, the value and location stacks
     have just been pushed.  So pushing a state here evens the stacks.  */
  yyssp++;


/*--------------------------------------------------------------------.
| yysetstate -- set current state (the top of the stack) to yystate.  |
`--------------------------------------------------------------------*/
yysetstate:
  YYDPRINTF ((stderr, "Entering state %d\n", yystate));
  YY_ASSERT (0 <= yystate && yystate < YYNSTATES);
  YY_IGNORE_USELESS_CAST_BEGIN
  *yyssp = YY_CAST (yy_state_t, yystate);
  YY_IGNORE_USELESS_CAST_END

  if (yyss + yystacksize - 1 <= yyssp)
#if !defined yyoverflow && !defined YYSTACK_RELOCATE
    goto yyexhaustedlab;
#else
    {
      /* Get the current used size of the three stacks, in elements.  */
      YYPTRDIFF_T yysize = yyssp - yyss + 1;

# if defined yyoverflow
      {
        /* Give user a chance to reallocate the stack.  Use copies of
           these so that the &'s don't force the real ones into
           memory.  */
        yy_state_t *yyss1 = yyss;
        YYSTYPE *yyvs1 = yyvs;

        /* Each stack pointer address is followed by the size of the
           data in use in that stack, in bytes.  This used to be a
           conditional around just the two extra args, but that might
           be undefined if yyoverflow is a macro.  */
        yyoverflow (YY_("memory exhausted"),
                    &yyss1, yysize * YYSIZEOF (*yyssp),
                    &yyvs1, yysize * YYSIZEOF (*yyvsp),
                    &yystacksize);
        yyss = yyss1;
        yyvs = yyvs1;
      }
# else /* defined YYSTACK_RELOCATE */
      /* Extend the stack our own way.  */
      if (YYMAXDEPTH <= yystacksize)
        goto yyexhaustedlab;
      yystacksize *= 2;
      if (YYMAXDEPTH < yystacksize)
        yystacksize = YYMAXDEPTH;

      {
        yy_state_t *yyss1 = yyss;
        union yyalloc *yyptr =
          YY_CAST (union yyalloc *,
                   YYSTACK_ALLOC (YY_CAST (YYSIZE_T, YYSTACK_BYTES (yystacksize))));
        if (! yyptr)
          goto yyexhaustedlab;
        YYSTACK_RELOCATE (yyss_alloc, yyss);
        YYSTACK_RELOCATE (yyvs_alloc, yyvs);
# undef YYSTACK_RELOCATE
        if (yyss1 != yyssa)
          YYSTACK_FREE (yyss1);
      }
# endif

      yyssp = yyss + yysize - 1;
      yyvsp = yyvs + yysize - 1;

      YY_IGNORE_USELESS_CAST_BEGIN
      YYDPRINTF ((stderr, "Stack size increased to %ld\n",
                  YY_CAST (long, yystacksize)));
      YY_IGNORE_USELESS_CAST_END

      if (yyss + yystacksize - 1 <= yyssp)
        YYABORT;
    }
#endif /* !defined yyoverflow && !defined YYSTACK_RELOCATE */

  if (yystate == YYFINAL)
    YYACCEPT;

  goto yybackup;


/*-----------.
| yybackup.  |
`-----------*/
yybackup:
  /* Do appropriate processing given the current state.  Read a
     lookahead token if we need one and don't already have one.  */

  /* First try to decide what to do without reference to lookahead token.  */
  yyn = yypact[yystate];
  if (yypact_value_is_default (yyn))
    goto yydefault;

  /* Not known => get a lookahead token if don't already have one.  */

  /* YYCHAR is either YYEMPTY or YYEOF or a valid lookahead symbol.  */
  if (yychar == YYEMPTY)
    {
      YYDPRINTF ((stderr, "Reading a token: "));
      yychar = yylex ();
    }

  if (yychar <= YYEOF)
    {
      yychar = yytoken = YYEOF;
      YYDPRINTF ((stderr, "Now at end of input.\n"));
    }
  else
    {
      yytoken = YYTRANSLATE (yychar);
      YY_SYMBOL_PRINT ("Next token is", yytoken, &yylval, &yylloc);
    }

  /* If the proper action on seeing token YYTOKEN is to reduce or to
     detect an error, take that action.  */
  yyn += yytoken;
  if (yyn < 0 || YYLAST < yyn || yycheck[yyn] != yytoken)
    goto yydefault;
  yyn = yytable[yyn];
  if (yyn <= 0)
    {
      if (yytable_value_is_error (yyn))
        goto yyerrlab;
      yyn = -yyn;
      goto yyreduce;
    }

  /* Count tokens shifted since error; after three, turn off error
     status.  */
  if (yyerrstatus)
    yyerrstatus--;

  /* Shift the lookahead token.  */
  YY_SYMBOL_PRINT ("Shifting", yytoken, &yylval, &yylloc);
  yystate = yyn;
  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  *++yyvsp = yylval;
  YY_IGNORE_MAYBE_UNINITIALIZED_END

  /* Discard the shifted token.  */
  yychar = YYEMPTY;
  goto yynewstate;


/*-----------------------------------------------------------.
| yydefault -- do the default action for the current state.  |
`-----------------------------------------------------------*/
yydefault:
  yyn = yydefact[yystate];
  if (yyn == 0)
    goto yyerrlab;
  goto yyreduce;


/*-----------------------------.
| yyreduce -- do a reduction.  |
`-----------------------------*/
yyreduce:
  /* yyn is the number of a rule to reduce with.  */
  yylen = yyr2[yyn];

  /* If YYLEN is nonzero, implement the default value of the action:
     '$$ = $1'.

     Otherwise, the following line sets YYVAL to garbage.
     This behavior is undocumented and Bison
     users should not rely upon it.  Assigning to YYVAL
     unconditionally makes the parser a bit smaller, and it avoids a
     GCC warning that YYVAL may be used uninitialized.  */
  yyval = yyvsp[1-yylen];


  YY_REDUCE_PRINT (yyn);
  switch (yyn)
    {
  case 2:
#line 67 "plp5.y"
          {tsa = new TablaSimbolos(NULL); tt = new TablaTipos();}
#line 1457 "plp5.tab.c"
    break;

  case 3:
#line 68 "plp5.y"
          { yyval.cod = yyvsp[-1].cod + "halt"; cout << yyval.cod << endl; }
#line 1463 "plp5.tab.c"
    break;

  case 7:
#line 78 "plp5.y"
                         {
                            Simbolo s;
                            s.nombre = yyvsp[0].lexema;
                            s.tipo = yyvsp[-2].tipoPos;
                            s.dir = newVar();
                            s.tam = tt->getTipo(yyvsp[-2].tipoPos).tamanyo; // es objeto Tipo
                            yyval.tipoPos = yyvsp[-2].tipoPos; if(!tsa->newSymb(s)){errorSemantico(ERR_YADECL, yyvsp[0].nlin, yyvsp[0].ncol, yyvsp[0].lexema);}    //ERROR

                            }
#line 1477 "plp5.tab.c"
    break;

  case 11:
#line 93 "plp5.y"
                  {
                    Simbolo s;
                    s.nombre = yyvsp[0].lexema;
                    s.tipo = 1;
                    s.dir = newVar();
                    s.tam = tt->getTipo(1).tamanyo; // hereda tipo en DVar
                    if(!tsa->newSymb(s)){errorSemantico(ERR_YADECL, yyvsp[0].nlin, yyvsp[0].ncol, yyvsp[0].lexema);}    //ERROR
                    yyval.tipoPos = yyvsp[-2].tipoPos;
                  }
#line 1491 "plp5.tab.c"
    break;

  case 14:
#line 105 "plp5.y"
                 {yyval.trad="entero"; yyval.tipoPos=0; yyval.lexema="i"; }
#line 1497 "plp5.tab.c"
    break;

  case 15:
#line 106 "plp5.y"
                 {yyval.trad="real"; yyval.tipoPos=1; yyval.lexema="r"; }
#line 1503 "plp5.tab.c"
    break;

  case 16:
#line 107 "plp5.y"
                 {yyval.trad="logico"; yyval.tipoPos=2; yyval.lexema="l"; }
#line 1509 "plp5.tab.c"
    break;

  case 17:
#line 108 "plp5.y"
                                {tt->nuevoTipoArray(stoi(yyvsp[-2].lexema), yyvsp[0].tipoPos); }
#line 1515 "plp5.tab.c"
    break;

  case 18:
#line 111 "plp5.y"
                     {yyval.dir=ctemp; }
#line 1521 "plp5.tab.c"
    break;

  case 19:
#line 111 "plp5.y"
                                            {ctemp = yyvsp[-1].dir; yyval.cod = yyvsp[-3].cod + yyvsp[0].cod;}
#line 1527 "plp5.tab.c"
    break;

  case 20:
#line 112 "plp5.y"
          {yyval.dir=ctemp;}
#line 1533 "plp5.tab.c"
    break;

  case 21:
#line 112 "plp5.y"
                                {ctemp=yyvsp[-1].dir; yyval.cod = yyvsp[0].cod;}
#line 1539 "plp5.tab.c"
    break;

  case 22:
#line 115 "plp5.y"
                       {yyval.cod = yyvsp[0].cod + getOperator(yyvsp[-1].lexema) + getLetraTipo(yyvsp[0].tipoPos) + " " + std::to_string(yyvsp[0].dir) + "\n";}
#line 1545 "plp5.tab.c"
    break;

  case 23:
#line 116 "plp5.y"
                  {yyval.cod = yyvsp[0].cod + getOperator(yyvsp[-1].lexema) + getLetraTipo(yyvsp[0].tipoPos) + " " + std::to_string(yyvsp[0].dir) + "\n"; }
#line 1551 "plp5.tab.c"
    break;

  case 24:
#line 117 "plp5.y"
                                 {if(yyvsp[-2].tipoPos != 2){
                                    cerr << yyvsp[-2].tipoPos << endl;
                                    errorSemantico(ERR_EXP_LOG, yyvsp[-3].nlin, yyvsp[-3].ncol, yyvsp[-3].lexema);
                                    
                                 }
                                    string l1 = newLabel(); 
                                    yyval.cod = yyvsp[-2].cod + "mov " + std::to_string(yyvsp[-2].dir) + " A\n"
                                            + "jz L" + l1 + "\n"
                                            + yyvsp[0].cod + "\n L" + l1 + ":\n";;
                                            
                                 }
#line 1567 "plp5.tab.c"
    break;

  case 25:
#line 128 "plp5.y"
                                            {if(yyvsp[-4].tipoPos != 2){
                                            cerr << yyvsp[-4].tipoPos << endl;
                                            errorSemantico(ERR_EXP_LOG, yyvsp[-5].nlin, yyvsp[-5].ncol, yyvsp[-5].lexema);
                                        } 
                                            string l1 = newLabel();
                                            string l2 = newLabel(); 
                                            yyval.cod = yyvsp[-4].cod + "mov " + std::to_string(yyvsp[-4].dir) + " A\n"
                                                    + "jz L" + l1 + "\n"
                                                    + yyvsp[-2].cod + "\njmp L" + l2 +"\nL" + l1 + ":\n"
                                                    + yyvsp[0].cod + "\nL" + l2 + ":\n";;
                                        }
#line 1583 "plp5.tab.c"
    break;

  case 26:
#line 139 "plp5.y"
                                    {
                                        if(yyvsp[-2].tipoPos != 2) errorSemantico(ERR_EXP_LOG, yyvsp[-3].nlin, yyvsp[-3].ncol, yyvsp[-3].lexema);
                                        string l1 = newLabel();
                                        string l2 = newLabel();
                                        yyval.cod = "L" + l1 + ":\n" + yyvsp[-2].cod + "mov " + std::to_string(yyvsp[-2].dir) + " A\n"
                                                + "jz L" + l2 + "\n"
                                                + yyvsp[0].cod + "jmp L" + l1 + "\n";
                                                + "L" + l2 + ":\n";
                                    }
#line 1597 "plp5.tab.c"
    break;

  case 27:
#line 148 "plp5.y"
                                   {
                                        if(yyvsp[0].tipoPos != 2) errorSemantico(ERR_EXP_LOG, yyvsp[-3].nlin, yyvsp[-3].ncol, yyvsp[-3].lexema);
                                        string l1 = newLabel();
                                        
                                        yyval.cod = "L" + l1 + ":\n" + yyvsp[-2].cod + "\n" 
                                                + yyvsp[0].cod +"mov " + std::to_string(yyvsp[0].dir) + " A\n"
                                                + "jz L" + l1 + "\n";
                                    }
#line 1610 "plp5.tab.c"
    break;

  case 28:
#line 156 "plp5.y"
                          {//comprobarTipos(*$1.tipo, *$3.tipo);
                            yyval.cod = yyvsp[-2].cod + yyvsp[0].cod + "mov " + std::to_string(yyvsp[0].dir) + " A\n";
                                    + "mov A " + std::to_string(yyvsp[-2].dir) + "\n";
                            }
#line 1619 "plp5.tab.c"
    break;

  case 29:
#line 160 "plp5.y"
              {tsa = new TablaSimbolos(tsa);}
#line 1625 "plp5.tab.c"
    break;

  case 30:
#line 160 "plp5.y"
                                                               {yyval.cod = yyvsp[-1].cod; tsa = tsa->padre;}
#line 1631 "plp5.tab.c"
    break;

  case 31:
#line 163 "plp5.y"
                           {int tmp = newTemp(); yyval.dir = tmp;
                             if(yyvsp[-2].tipoPos != 2){
                                errorSemantico(ERR_EXIZQ_LOG, yyvsp[-1].nlin, yyvsp[-1].ncol, "||");
                             }
                             else if(yyvsp[0].tipoPos != 2){
                                errorSemantico(ERR_EXDER_LOG, yyvsp[-1].nlin, yyvsp[-1].ncol, "||");
                             }
                            yyval.cod = yyvsp[-2].cod + yyvsp[0].cod + 
                            "mov " + std::to_string(yyvsp[-2].dir) + " A\n" +
                            getOperator(yyvsp[-1].lexema) + getLetraTipo(yyvsp[-2].tipoPos) + " " + std::to_string(yyvsp[0].dir) + // OJO CON YEPA EYYY (ARREGLAR TEMA TIPOS)
                            "mov A " + std::to_string(tmp) + "\n";

                            yyval.tipoPos = 2;
                            cout << "TIPO EXPR " << yyval.tipoPos << endl;}
#line 1650 "plp5.tab.c"
    break;

  case 32:
#line 177 "plp5.y"
                {yyval.dir = yyvsp[0].dir; yyval.cod = yyvsp[0].cod; yyval.tipoPos = yyvsp[0].tipoPos; cout << "TIPO EXPR " << yyval.tipoPos << endl;}
#line 1656 "plp5.tab.c"
    break;

  case 33:
#line 180 "plp5.y"
                            {int tmp = newTemp(); yyval.dir = tmp;
                             if(yyvsp[-2].tipoPos != 2){
                                errorSemantico(ERR_EXIZQ_LOG, yyvsp[-1].nlin, yyvsp[-1].ncol, "&&");
                             }
                             else if(yyvsp[0].tipoPos != 2){
                                errorSemantico(ERR_EXDER_LOG, yyvsp[-1].nlin, yyvsp[-1].ncol, "&&");
                             }
                             yyval.cod = yyvsp[-2].cod + yyvsp[0].cod + 
                            "mov " + std::to_string(yyvsp[-2].dir) + " A\n" +
                            getOperator(yyvsp[-1].lexema)  + "i " + std::to_string(yyvsp[0].dir) + // OJO CON YEPA EYYY (ARREGLAR TEMA TIPOS)
                            "mov A " + std::to_string(tmp) + "\n";
                            yyval.tipoPos = 2;
                            cout << "TIPO ECONJ " << yyval.tipoPos << endl;}
#line 1674 "plp5.tab.c"
    break;

  case 34:
#line 193 "plp5.y"
                {yyval.dir = yyvsp[0].dir; yyval.cod = yyvsp[0].cod; yyval.tipoPos = yyvsp[0].tipoPos;cout << "TIPO ECONJ " << yyval.tipoPos << endl;}
#line 1680 "plp5.tab.c"
    break;

  case 35:
#line 196 "plp5.y"
                                {int tmp = newTemp(); yyval.dir = tmp;
                                cout << "OPREL\nCOD1\n" << yyvsp[-2].cod << "COD3\n" << yyvsp[0].cod << endl;
                                cout << yyvsp[-2].tipoPos << "||" << yyvsp[0].tipoPos << endl;
                             if(yyvsp[-2].tipoPos == 0 && yyvsp[0].tipoPos == 1){
                                yyval.cod = yyvsp[-2].cod + yyvsp[0].cod + 
                                "mov " + std::to_string(yyvsp[-2].dir) + " A\n" +
                                "itor\n" +
                                getOperator(yyvsp[-1].lexema) + "r " + std::to_string(yyvsp[0].dir) + "\n" + 
                                "mov A " + std::to_string(tmp) + "\n";

                             }
                             else if(yyvsp[-2].tipoPos == 1 && yyvsp[0].tipoPos == 0){
                                yyval.cod = yyvsp[-2].cod + yyvsp[0].cod + 
                                "mov " + std::to_string(yyvsp[0].dir) + " A\n" +
                                "itor\n" +
                                "mov A " + std::to_string(tmp) + "\n" +
                                "mov " + std::to_string(yyvsp[-2].dir) + "\n" +
                                getOperator(yyvsp[-1].lexema) + "r " + std::to_string(tmp) + "\n" + 
                                "mov A " + std::to_string(tmp) + "\n";

                             }
                             else if(yyvsp[-2].tipoPos == 2){
                                errorSemantico(ERR_EXIZQ_RE, yyvsp[-1].nlin, yyvsp[-1].ncol, yyvsp[-1].lexema);
                             }
                             else if(yyvsp[0].tipoPos == 2){
                                errorSemantico(ERR_EXDER_RE, yyvsp[-1].nlin, yyvsp[-1].ncol, yyvsp[-1].lexema);
                             }
                             else{
                                yyval.cod = yyvsp[-2].cod + yyvsp[0].cod + 
                                "mov " + std::to_string(yyvsp[-2].dir) + " A\n" +
                                getOperator(yyvsp[-1].lexema) + getLetraTipo(yyvsp[-2].tipoPos) + " " + std::to_string(yyvsp[0].dir) + "\n" + 
                                "mov A " + std::to_string(tmp) + "\n";

                             }
                             yyval.tipoPos = 2;
                             cout << "TIPO ECOMP NORMAL" << yyval.tipoPos << endl;}
#line 1721 "plp5.tab.c"
    break;

  case 36:
#line 232 "plp5.y"
                  {yyval.dir = yyvsp[0].dir; yyval.cod = yyvsp[0].cod; yyval.tipoPos = yyvsp[0].tipoPos;cout << "TIPO ECOMP(ESIMPLE)" << yyval.tipoPos << endl;}
#line 1727 "plp5.tab.c"
    break;

  case 37:
#line 235 "plp5.y"
                            {int tmp = newTemp(); yyval.dir = tmp;

                             if(yyvsp[-2].tipoPos == 0 && yyvsp[0].tipoPos == 1){
                                yyval.cod = yyvsp[-2].cod + yyvsp[0].cod + 
                                "mov " + std::to_string(yyvsp[-2].dir) + " A\n" +
                                "itor\n" +
                                getOperator(yyvsp[-1].lexema) + "r " + std::to_string(yyvsp[0].dir) + "\n" + 
                                "mov A " + std::to_string(tmp) + "\n";
                                yyval.tipoPos = 1;
                             }
                             else if(yyvsp[-2].tipoPos == 1 && yyvsp[0].tipoPos == 0){
                                yyval.cod = yyvsp[-2].cod + yyvsp[0].cod + 
                                "mov " + std::to_string(yyvsp[0].dir) + " A\n" +
                                "itor\n" +
                                "mov A " + std::to_string(tmp) + "\n" +
                                "mov " + std::to_string(yyvsp[-2].dir) + "\n" +
                                getOperator(yyvsp[-1].lexema) + "r " + std::to_string(tmp) + "\n" + 
                                "mov A " + std::to_string(tmp) + "\n";
                                yyval.tipoPos = 1;
                             }
                             else if(yyvsp[-2].tipoPos == 2){
                                errorSemantico(ERR_EXIZQ_RE, yyvsp[-1].nlin, yyvsp[-1].ncol, yyvsp[-1].lexema);
                             }
                             else if(yyvsp[0].tipoPos == 2){
                                errorSemantico(ERR_EXDER_RE, yyvsp[-1].nlin, yyvsp[-1].ncol, yyvsp[-1].lexema);
                             }
                             else{
                                yyval.cod = yyvsp[-2].cod + yyvsp[0].cod + 
                                "mov " + std::to_string(yyvsp[-2].dir) + " A\n" +
                                getOperator(yyvsp[-1].lexema) + getLetraTipo(yyvsp[-2].tipoPos) + " " + std::to_string(yyvsp[0].dir) + "\n" + 
                                "mov A " + std::to_string(tmp) + "\n";
                                yyval.tipoPos = yyvsp[-2].tipoPos;
                                cout << "TIPO ESIMPLE " << yyval.tipoPos << endl;
                             }
                             
                            }
#line 1768 "plp5.tab.c"
    break;

  case 38:
#line 271 "plp5.y"
               {yyval.dir = yyvsp[0].dir; yyval.cod = yyvsp[0].cod; yyval.tipoPos = yyvsp[0].tipoPos; cout << "TIPO ESIMPLE (TERM) " << yyval.tipoPos << endl;}
#line 1774 "plp5.tab.c"
    break;

  case 39:
#line 272 "plp5.y"
                    {           if(yyvsp[0].tipoPos == 2)   errorSemantico(ERR_EXDER_RE, yyvsp[0].nlin, yyvsp[0].ncol, yyvsp[0].lexema);
                                int tmp = newTemp(); yyval.dir = tmp;
                                yyval.cod = yyvsp[0].cod +
                                "mov " + std::to_string(yyvsp[0].dir) + " A\n" +
                                getOperator(yyvsp[-1].lexema) + getLetraTipo(yyvsp[0].tipoPos) + " " + std::to_string(yyvsp[0].dir) +
                                "mov A " + std::to_string(tmp) + "\n";
                                yyval.tipoPos = yyvsp[0].tipoPos;
                                cout << "TIPO ESIMPLE (OPAS TERM) " << yyval.tipoPos << endl;
                            }
#line 1788 "plp5.tab.c"
    break;

  case 40:
#line 283 "plp5.y"
                           {int tmp = newTemp(); yyval.dir = tmp;
                             cout << "COD1\n" << yyvsp[-2].cod << "COD3\n" << yyvsp[0].cod << endl;
                             if(yyvsp[-2].tipoPos == 0 && yyvsp[0].tipoPos == 1){
                                yyval.cod = yyvsp[-2].cod + yyvsp[0].cod + 
                                "mov " + std::to_string(yyvsp[-2].dir) + " A\n" +
                                "itor\n" +
                                getOperator(yyvsp[-1].lexema) + "r " + std::to_string(yyvsp[0].dir) + "\n" + 
                                "mov A " + std::to_string(tmp) + "\n";
                                
                                yyval.tipoPos = 1;
                             }
                             else if(yyvsp[-2].tipoPos == 1 && yyvsp[0].tipoPos == 0){
                                yyval.cod = yyvsp[-2].cod + yyvsp[0].cod + 
                                "mov " + std::to_string(yyvsp[0].dir) + " A\n" +
                                "itor\n" +
                                "mov A " + std::to_string(tmp) + "\n" +
                                "mov " + std::to_string(yyvsp[-2].dir) + "\n" +
                                getOperator(yyvsp[-1].lexema) + "r " + std::to_string(tmp) + "\n" + 
                                "mov A " + std::to_string(tmp) + "\n";

                                yyval.tipoPos = 1;
                             }
                             else if(yyvsp[-2].tipoPos == 2){
                                cout << yyvsp[-2].tipoPos << " " << yyvsp[0].tipoPos << endl;
                                errorSemantico(ERR_EXIZQ_RE, yyvsp[-1].nlin, yyvsp[-1].ncol, yyvsp[-1].lexema);
                             }
                             else if(yyvsp[0].tipoPos == 2){
                                errorSemantico(ERR_EXDER_RE, yyvsp[-1].nlin, yyvsp[-1].ncol, yyvsp[-1].lexema);
                             }
                             else{
                                yyval.cod = yyvsp[-2].cod + yyvsp[0].cod + 
                                "mov " + std::to_string(yyvsp[-2].dir) + " A\n" +
                                getOperator(yyvsp[-1].lexema) + getLetraTipo(yyvsp[-2].tipoPos) + " " + std::to_string(yyvsp[0].dir) + "\n" +// OJO CON YEPA EYYY (ARREGLAR TEMA TIPOS)
                                "mov A " + std::to_string(tmp) + "\n"; yyval.tipoPos = yyvsp[-2].tipoPos;
                                }
                                cout << "TIPO TERM " << yyval.tipoPos << endl;
                             }
#line 1830 "plp5.tab.c"
    break;

  case 41:
#line 321 "plp5.y"
                 {yyval.dir = yyvsp[0].dir; yyval.cod = yyvsp[0].cod; yyval.tipoPos = yyvsp[0].tipoPos; cout << "TIPO TERM (FACTOR)" << yyval.tipoPos << endl;}
#line 1836 "plp5.tab.c"
    break;

  case 42:
#line 324 "plp5.y"
              {yyval.dir = yyvsp[0].dir; yyval.cod = yyvsp[0].cod;}
#line 1842 "plp5.tab.c"
    break;

  case 43:
#line 325 "plp5.y"
                  {int tmp = newTemp(); yyval.dir = tmp;
                   yyval.tipoPos = 0;
                   string entero_string(yyvsp[0].lexema);
                   yyval.cod = "mov #" + entero_string + " " + std::to_string(tmp) + "\n";}
#line 1851 "plp5.tab.c"
    break;

  case 44:
#line 329 "plp5.y"
                {int tmp = newTemp(); yyval.dir = tmp;
                   
                   yyval.tipoPos = 1;
                   string real_string(yyvsp[0].lexema);
                   yyval.cod = "mov $" + real_string + " " + std::to_string(tmp) + "\n";}
#line 1861 "plp5.tab.c"
    break;

  case 45:
#line 334 "plp5.y"
                         {yyval.dir = yyvsp[-1].dir; yyval.cod = yyvsp[-1].cod; yyval.tipoPos = yyvsp[-1].tipoPos;}
#line 1867 "plp5.tab.c"
    break;

  case 46:
#line 335 "plp5.y"
                        {
                            yyval.tipoPos = yyvsp[0].tipoPos; int tmp = newTemp(); yyval.dir = tmp;
                            yyval.cod = "mov " + std::to_string(yyvsp[0].dir) + "A\n"
                                   + "not" + getLetraTipo(yyvsp[0].tipoPos) + "\n"
                                   + "mov A " + std::to_string(tmp) + "\n";
                        }
#line 1878 "plp5.tab.c"
    break;

  case 47:
#line 341 "plp5.y"
                 {int tmp = newTemp(); yyval.dir = tmp;
                 yyval.tipoPos = 2;
                 yyval.cod = "mov #1 " + std::to_string(tmp) + "\n";}
#line 1886 "plp5.tab.c"
    break;

  case 48:
#line 344 "plp5.y"
                {int tmp = newTemp(); yyval.dir = tmp;
                 yyval.tipoPos = 2;
                 yyval.cod = "mov #0 " + std::to_string(tmp) + "\n";}
#line 1894 "plp5.tab.c"
    break;

  case 49:
#line 349 "plp5.y"
             {
                
                Simbolo* s = tsa->searchSymb(yyvsp[0].lexema);
                if( s == NULL )
                    errorSemantico(ERR_NODECL, yyvsp[0].nlin, yyvsp[0].ncol, yyvsp[0].lexema);
                int tmp = newTemp(); yyval.dir = tmp;
                yyval.cod = "mov #0 " + std::to_string(tmp) + "\n";
                
                yyval.tipoPos = s->tipo;
                yyval.dbase = s->dir;
            }
#line 1910 "plp5.tab.c"
    break;

  case 50:
#line 360 "plp5.y"
                   {
                        if(yyvsp[-1].tipoPos<=2)
                            errorSemantico(ERR_SOBRAN, yyvsp[0].nlin, yyvsp[0].ncol, yyvsp[0].lexema);
                    }
#line 1919 "plp5.tab.c"
    break;

  case 51:
#line 363 "plp5.y"
                                   {
                                        if(yyvsp[-1].tipoPos!=0)
                                            errorSemantico(ERR_INDICE_ENTERO, yyvsp[-1].nlin, yyvsp[-1].ncol, yyvsp[-1].lexema);
                                        yyval.tipoPos = tt->getTipo(yyvsp[-4].tipoPos).tipoBase; // tipoBase es igual que tipoPos???
                                        yyval.dbase = yyvsp[-4].dbase;
                                        int tmp = newTemp(); yyval.dir = tmp;
                                        yyval.cod = yyvsp[-4].cod + yyvsp[-1].cod +
                                                "mov " + std::to_string(yyvsp[-4].dir) + " A\n" +
                                                "muli #" + std::to_string(tt->getTipo(yyvsp[-4].tipoPos).tamanyo) + "\n" +
                                                "addi " + std::to_string(yyvsp[-1].dir) + "\n" +
                                                "mov A " + std::to_string(tmp) + "\n";
                                                
                                    }
#line 1937 "plp5.tab.c"
    break;


#line 1941 "plp5.tab.c"

      default: break;
    }
  /* User semantic actions sometimes alter yychar, and that requires
     that yytoken be updated with the new translation.  We take the
     approach of translating immediately before every use of yytoken.
     One alternative is translating here after every semantic action,
     but that translation would be missed if the semantic action invokes
     YYABORT, YYACCEPT, or YYERROR immediately after altering yychar or
     if it invokes YYBACKUP.  In the case of YYABORT or YYACCEPT, an
     incorrect destructor might then be invoked immediately.  In the
     case of YYERROR or YYBACKUP, subsequent parser actions might lead
     to an incorrect destructor call or verbose syntax error message
     before the lookahead is translated.  */
  YY_SYMBOL_PRINT ("-> $$ =", yyr1[yyn], &yyval, &yyloc);

  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);

  *++yyvsp = yyval;

  /* Now 'shift' the result of the reduction.  Determine what state
     that goes to, based on the state we popped back to and the rule
     number reduced by.  */
  {
    const int yylhs = yyr1[yyn] - YYNTOKENS;
    const int yyi = yypgoto[yylhs] + *yyssp;
    yystate = (0 <= yyi && yyi <= YYLAST && yycheck[yyi] == *yyssp
               ? yytable[yyi]
               : yydefgoto[yylhs]);
  }

  goto yynewstate;


/*--------------------------------------.
| yyerrlab -- here on detecting error.  |
`--------------------------------------*/
yyerrlab:
  /* Make sure we have latest lookahead translation.  See comments at
     user semantic actions for why this is necessary.  */
  yytoken = yychar == YYEMPTY ? YYEMPTY : YYTRANSLATE (yychar);

  /* If not already recovering from an error, report this error.  */
  if (!yyerrstatus)
    {
      ++yynerrs;
#if ! YYERROR_VERBOSE
      yyerror (YY_("syntax error"));
#else
# define YYSYNTAX_ERROR yysyntax_error (&yymsg_alloc, &yymsg, \
                                        yyssp, yytoken)
      {
        char const *yymsgp = YY_("syntax error");
        int yysyntax_error_status;
        yysyntax_error_status = YYSYNTAX_ERROR;
        if (yysyntax_error_status == 0)
          yymsgp = yymsg;
        else if (yysyntax_error_status == 1)
          {
            if (yymsg != yymsgbuf)
              YYSTACK_FREE (yymsg);
            yymsg = YY_CAST (char *, YYSTACK_ALLOC (YY_CAST (YYSIZE_T, yymsg_alloc)));
            if (!yymsg)
              {
                yymsg = yymsgbuf;
                yymsg_alloc = sizeof yymsgbuf;
                yysyntax_error_status = 2;
              }
            else
              {
                yysyntax_error_status = YYSYNTAX_ERROR;
                yymsgp = yymsg;
              }
          }
        yyerror (yymsgp);
        if (yysyntax_error_status == 2)
          goto yyexhaustedlab;
      }
# undef YYSYNTAX_ERROR
#endif
    }



  if (yyerrstatus == 3)
    {
      /* If just tried and failed to reuse lookahead token after an
         error, discard it.  */

      if (yychar <= YYEOF)
        {
          /* Return failure if at end of input.  */
          if (yychar == YYEOF)
            YYABORT;
        }
      else
        {
          yydestruct ("Error: discarding",
                      yytoken, &yylval);
          yychar = YYEMPTY;
        }
    }

  /* Else will try to reuse lookahead token after shifting the error
     token.  */
  goto yyerrlab1;


/*---------------------------------------------------.
| yyerrorlab -- error raised explicitly by YYERROR.  |
`---------------------------------------------------*/
yyerrorlab:
  /* Pacify compilers when the user code never invokes YYERROR and the
     label yyerrorlab therefore never appears in user code.  */
  if (0)
    YYERROR;

  /* Do not reclaim the symbols of the rule whose action triggered
     this YYERROR.  */
  YYPOPSTACK (yylen);
  yylen = 0;
  YY_STACK_PRINT (yyss, yyssp);
  yystate = *yyssp;
  goto yyerrlab1;


/*-------------------------------------------------------------.
| yyerrlab1 -- common code for both syntax error and YYERROR.  |
`-------------------------------------------------------------*/
yyerrlab1:
  yyerrstatus = 3;      /* Each real token shifted decrements this.  */

  for (;;)
    {
      yyn = yypact[yystate];
      if (!yypact_value_is_default (yyn))
        {
          yyn += YYTERROR;
          if (0 <= yyn && yyn <= YYLAST && yycheck[yyn] == YYTERROR)
            {
              yyn = yytable[yyn];
              if (0 < yyn)
                break;
            }
        }

      /* Pop the current state because it cannot handle the error token.  */
      if (yyssp == yyss)
        YYABORT;


      yydestruct ("Error: popping",
                  yystos[yystate], yyvsp);
      YYPOPSTACK (1);
      yystate = *yyssp;
      YY_STACK_PRINT (yyss, yyssp);
    }

  YY_IGNORE_MAYBE_UNINITIALIZED_BEGIN
  *++yyvsp = yylval;
  YY_IGNORE_MAYBE_UNINITIALIZED_END


  /* Shift the error token.  */
  YY_SYMBOL_PRINT ("Shifting", yystos[yyn], yyvsp, yylsp);

  yystate = yyn;
  goto yynewstate;


/*-------------------------------------.
| yyacceptlab -- YYACCEPT comes here.  |
`-------------------------------------*/
yyacceptlab:
  yyresult = 0;
  goto yyreturn;


/*-----------------------------------.
| yyabortlab -- YYABORT comes here.  |
`-----------------------------------*/
yyabortlab:
  yyresult = 1;
  goto yyreturn;


#if !defined yyoverflow || YYERROR_VERBOSE
/*-------------------------------------------------.
| yyexhaustedlab -- memory exhaustion comes here.  |
`-------------------------------------------------*/
yyexhaustedlab:
  yyerror (YY_("memory exhausted"));
  yyresult = 2;
  /* Fall through.  */
#endif


/*-----------------------------------------------------.
| yyreturn -- parsing is finished, return the result.  |
`-----------------------------------------------------*/
yyreturn:
  if (yychar != YYEMPTY)
    {
      /* Make sure we have latest lookahead translation.  See comments at
         user semantic actions for why this is necessary.  */
      yytoken = YYTRANSLATE (yychar);
      yydestruct ("Cleanup: discarding lookahead",
                  yytoken, &yylval);
    }
  /* Do not reclaim the symbols of the rule whose action triggered
     this YYABORT or YYACCEPT.  */
  YYPOPSTACK (yylen);
  YY_STACK_PRINT (yyss, yyssp);
  while (yyssp != yyss)
    {
      yydestruct ("Cleanup: popping",
                  yystos[+*yyssp], yyvsp);
      YYPOPSTACK (1);
    }
#ifndef yyoverflow
  if (yyss != yyssa)
    YYSTACK_FREE (yyss);
#endif
#if YYERROR_VERBOSE
  if (yymsg != yymsgbuf)
    YYSTACK_FREE (yymsg);
#endif
  return yyresult;
}
#line 379 "plp5.y"


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
    if(t1.tipoPos == 1 && t2.tipoPos == 0){

    }
}

string getLetraTipo(int tipo){
    if(tipo == 0)
        return "i";
    else if(tipo == 1)
        return "r";
    else if(tipo == 2)
        return "l";
    else
        return "error";
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

