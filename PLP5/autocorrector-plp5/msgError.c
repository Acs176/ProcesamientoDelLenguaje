
/**--------------------- en comun.h -------------------------

#define ERRLEXICO             1
#define ERRSINT               2
#define ERREOF                3

#define ERR_YADECL            4
#define ERR_NODECL            5
#define ERR_DIM               6
#define ERR_FALTAN            7
#define ERR_SOBRAN            8
#define ERR_INDICE_ENTERO     9
#define ERR_EXP_LOG          10
#define ERR_EXDER_LOG        11
#define ERR_EXDER_ENT        12
#define ERR_EXDER_RE         13
#define ERR_EXIZQ_LOG        14
#define ERR_EXIZQ_RE         15

#define ERR_NOCABE           16
#define ERR_MAXTMP           17

void msgError(int nerror,int nlin,int ncol,const char *s);



/**---------------------- en plp5.y -----------------------

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



