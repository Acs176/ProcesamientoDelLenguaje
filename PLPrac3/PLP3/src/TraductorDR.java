import java.util.TreeSet;

public class TraductorDR {
    
    AnalizadorLexico lexico;
    Token token;
    TablaSimbolos TS;
    StringBuilder reglas;
    boolean flag;

    public TraductorDR(AnalizadorLexico al) {
        lexico = al;
        reglas = new StringBuilder();
        token = al.siguienteToken();
    }

    public void emparejar(int tokEsperado){
        if(token.tipo == tokEsperado)
            token = lexico.siguienteToken();
        else{
            errorSintaxis(new TreeSet<Integer>(){{
                add(tokEsperado);
            }});
        }            
    }

    public void addRegla(String regla){
        //System.out.println("ESTOY EN REGLA " + regla);
        if(regla != "1")
            reglas.append(" " + regla);
        else
            reglas.append(regla);
    }

    public void comprobarFinFichero(){
        //System.out.println("EYEEEEE");
        if(token.tipo != Token.EOF){
            errorSintaxis(new TreeSet<Integer>(){{
                add(Token.EOF);
            }});
        }
        else{
            System.out.println(reglas);
        }
    }

    public void errorSintaxis(TreeSet<Integer> esperado){
        String esperado_string = "";
        for(Integer i : esperado){
            esperado_string += Token.nombreToken.get(i);
        }
        if(token.tipo == Token.EOF){
            System.err.println("Error sintactico: encontrado fin de fichero, esperaba " + esperado_string);
        }
        else{
            System.err.println("Error sintactico (" + token.fila + "," + token.columna + "): encontrado \'" + token.lexema + "\', esperaba " + esperado_string);
        }
        
        System.exit(-1);
    }

    public String S(){
        System.out.println("TETORRAS ENORMES");
        if(token.tipo == Token.ALGORITMO){
            TS = new TablaSimbolos(null);
            TS.nuevoSimbolo(new Simbolo("main", "main", Simbolo.FUNCION));
            System.out.println("NUEVA FUNCION MAIN");

            addRegla("1");
            emparejar(Token.ALGORITMO);
            String nombre_alg = token.lexema;
            emparejar(Token.ID);
            emparejar(Token.PYC);
            String vsp_trad = Vsp(true, "");
            String bloque_trad = Bloque(true, "main");
            return "// algoritmo " + nombre_alg + '\n' + vsp_trad + bloque_trad; 
        }
        else{
            errorSintaxis(new TreeSet<Integer>(){{add(Token.ALGORITMO);}});
        }
        return "";
    }
    public String Vsp(String prefix){
        return Vsp(false, prefix);
    }

    public String Vsp(boolean isMain, String prefix){
        if(token.tipo == Token.FUNCION || token.tipo == Token.VAR){
            addRegla("2");
            String unsp_trad = Unsp(isMain, prefix);
            String vspp_trad = Vspp(isMain, prefix);
            return unsp_trad + vspp_trad;
        }
        else{
            errorSintaxis(new TreeSet<Integer>(){
                {add(Token.FUNCION);
                 add(Token.VAR);
                }
            });
        }
        return "";
    }
    public String Vspp(boolean isMain, String prefix){
        if(token.tipo == Token.FUNCION || token.tipo == Token.VAR){
            addRegla("3");
            String unsp_trad = Unsp(isMain, prefix);
            String vspp_trad = Vspp(isMain, prefix);
            return unsp_trad + vspp_trad;
        }
        else if(token.tipo == Token.BLQ){
            // epsilon
            addRegla("4");
        }
        else{
            errorSintaxis(new TreeSet<Integer>(){
                {add(Token.FUNCION);
                 add(Token.VAR);
                 add(Token.BLQ);
                }
            });
        }
        return "";
    }

    public String Unsp(String prefix){
        return Unsp(false, prefix);
    }

    public String Unsp(boolean isMain, String prefix){
        if(token.tipo == Token.FUNCION){
            addRegla("5");
            emparejar(Token.FUNCION);
            String func_nombre = token.lexema;
            emparejar(Token.ID);
            emparejar(Token.DOSP);

            String tipo_trad = Tipo();
            int tipoSimbolo;
            switch(tipo_trad){
                case "int":
                    tipoSimbolo = 1;
                    break;
                case "double":
                    tipoSimbolo = 2;
                    break;
                default:
                    // ERROR
                    return "";
            }
            Simbolo nuevoSimb = new Simbolo(func_nombre, prefix + func_nombre, Simbolo.FUNCION);
            TS.nuevoSimbolo(nuevoSimb);
            System.out.println("NUEVO SIMBOLO " + nuevoSimb);
            TS = new TablaSimbolos(TS);

            emparejar(Token.PYC);
            Vsp(prefix + func_nombre + "_");
            Bloque();
            emparejar(Token.PYC);

        }
        else if(token.tipo == Token.VAR){
            addRegla("6");
            emparejar(Token.VAR);
            if(isMain){
                return LV("main_");
            }
            else{
                return LV(prefix);
            }
            
        }
        else{
            errorSintaxis(new TreeSet<Integer>(){
                {add(Token.FUNCION);
                 add(Token.VAR);
                }
            }); 
        }
        return "";
    }
    public String LV(String prefix){
        if(token.tipo == Token.ID){
            addRegla("7");
            String v_trad = V(prefix);
            String lvp_trad = LVp(prefix);

            return v_trad + "\n" + lvp_trad;
        }
        else{
            errorSintaxis(new TreeSet<Integer>(){
                {add(Token.ID);
                }
            });
        }
        return "";
    }
    public String LVp(String prefix){
        if(token.tipo == Token.ID){
            addRegla("8");
            String v_trad = V(prefix);
            String lvp_trad = LVp(prefix);

            return v_trad + '\n' + lvp_trad;
        }
        else if(token.tipo == Token.FUNCION || token.tipo == Token.VAR || token.tipo == Token.BLQ){
            // epsilon
            addRegla("9");
        }
        else{
            errorSintaxis(new TreeSet<Integer>(){
                {add(Token.ID);
                 add(Token.FUNCION);
                 add(Token.VAR);
                 add(Token.BLQ);
                }
            }); 
        }
        return "";
    }
    public String V(String prefix){
        if(token.tipo == Token.ID){
            addRegla("10");
            String id = token.lexema;
            emparejar(Token.ID);
            String lid_trad = Lid(prefix);
            emparejar(Token.DOSP);
            String tipo_trad = Tipo();
            emparejar(Token.PYC);

            int tipoSimbolo;
            switch(tipo_trad){
                case "int":
                    tipoSimbolo = 1;
                    break;
                case "double":
                    tipoSimbolo = 2;
                    break;
                default:
                    // ERROR
                    return "";
            }
            Simbolo nuevoSimb = new Simbolo(id, prefix + id, tipoSimbolo);
            TS.nuevoSimbolo(nuevoSimb);
            System.out.println("NUEVO SIMBOLO " + nuevoSimb);
            // BUSCAR SIMBOLOS CON -1 DE TIPO EN EL AMBITO ACTUAL Y CAMBIARLO A tipoSimbolo
            for(int i=0; i < TS.simbolos.size(); i++){
                Simbolo s = TS.simbolos.get(i);
                if(s.tipo == -1){
                    s.tipo = tipoSimbolo;
                    TS.simbolos.set(i, s);
                }
            }

            return tipo_trad + " " + nuevoSimb.nombreCompleto + lid_trad + ";";
        }
        else{
            errorSintaxis(new TreeSet<Integer>(){
                {add(Token.ID);
                }
            });
        }
        return "";
    }
    public String Lid(String prefix){
        if(token.tipo == Token.COMA){
            addRegla("11");
            emparejar(Token.COMA);
            String id = token.lexema;
            Simbolo nuevoSimb = new Simbolo(id, prefix + id, -1);
            TS.nuevoSimbolo(nuevoSimb);
            System.out.println("NUEVO SIMBOLO " + nuevoSimb);
            emparejar(Token.ID);
            String lid_trad = Lid(prefix);
            return "," + nuevoSimb.nombreCompleto + lid_trad; 
        }
        else if(token.tipo == Token.DOSP){
            // epsilon
            addRegla("12");
            return "";
        }
        else{
            errorSintaxis(new TreeSet<Integer>(){
                {add(Token.COMA);
                 add(Token.DOSP);
                }
            });
        }
        return "";
    }
    public String Tipo(){
        if(token.tipo == Token.ENTERO){
            addRegla("13");
            emparejar(Token.ENTERO);
            return "int";
        }
        else if(token.tipo == Token.REAL){
            addRegla("14");
            emparejar(Token.REAL);
            return "double";
        }
        else{
            errorSintaxis(new TreeSet<Integer>(){
                {add(Token.ENTERO);
                 add(Token.REAL);
                }
            });
        }
        return "";
    }
    public String Bloque(){
        return Bloque(false, "");
    }

    public String Bloque(boolean esFunc, String nombreSimbolo){
        if(token.tipo == Token.BLQ){
            addRegla("15");
            emparejar(Token.BLQ);
            String sinstr_trad = SInstr();
            emparejar(Token.FBLQ);
            String parteFuncion = "";
            if(esFunc){
                // ES POSIBLE QUE NO ESTE
                Simbolo funcion = TS.buscar(nombreSimbolo);
                parteFuncion = funcion.tipo + " " + funcion.nombre + "()\n";
            }
            return parteFuncion + "{\n\t" + sinstr_trad + "\n}";
            
        }
        else{
            errorSintaxis(new TreeSet<Integer>(){
                {add(Token.BLQ);
                }
            });
        }
        return "";
    }
    public String SInstr(){
        if(token.tipo == Token.ID || token.tipo == Token.SI || token.tipo == Token.MIENTRAS || token.tipo == Token.ESCRIBIR || token.tipo == Token.BLQ){
            addRegla("16");
            Instr();
            SInstrp();
            String instr_trad = Instr();
            String sinstrp_trad = SInstrp();
            return instr_trad + sinstrp_trad;
        }
        else{
            errorSintaxis(new TreeSet<Integer>(){
                {add(Token.ID);
                 add(Token.SI);
                 add(Token.MIENTRAS);
                 add(Token.ESCRIBIR);
                 add(Token.BLQ);
                }
            });
            return "";
        }    
    }
    public String SInstrp(){
        if(token.tipo == Token.PYC){
            addRegla("17");
            emparejar(Token.PYC);
            String instr_trad = Instr();
            String sinstrp_trad = SInstrp();
            return instr_trad + sinstrp_trad;
        }
        else if(token.tipo == Token.FBLQ){
            // epsilon
            addRegla("18");
            return "";
        }
        else{
            errorSintaxis(new TreeSet<Integer>(){
                {add(Token.PYC);
                 add(Token.FBLQ);
                }
            });
        }
        return "";
    }
    public String Instr(){
        if(token.tipo == Token.BLQ){
            addRegla("19");
            return Bloque();
        }
        else if(token.tipo == Token.ID){
            //check ID existe en ambito
            Token id = token;
            Simbolo simbolo = TS.buscarAmbito(token.lexema);
            if(simbolo != null){
                addRegla("20");
                emparejar(Token.ID);
                String asig = id.lexema;
                emparejar(Token.ASIG);
                String e_trad;
                switch(simbolo.tipo){
                    case 1:
                        e_trad = E("i");
                        break;
                    case 2:
                        e_trad = E("r");
                        break;
                    default:
                        //error
                        return "";
                }
                return simbolo.nombreCompleto + " " + asig + " " + e_trad; 

            }
            else{
                //error
                errorSemantico(ERR_NO_DECL, id.fila, id.columna, id.lexema);
                System.exit(-1);
            }

        }
        else if(token.tipo == Token.SI){
            addRegla("21");
            emparejar(Token.SI);
            String e_trad = E("");
            emparejar(Token.ENTONCES);
            String instr_trad = Instr();
            String instrp_trad = Instrp();

            return "if ( " + e_trad + " )\n\t" + instr_trad + instrp_trad + '\n';
        }
        else if(token.tipo == Token.MIENTRAS){
            addRegla("24");
            emparejar(Token.MIENTRAS);
            String e_trad = E("");
            emparejar(Token.HACER);
            String instr_trad = Instr();

            return "while ( " + e_trad + " )\n" + instr_trad + "\n";
        }
        else if(token.tipo == Token.ESCRIBIR){
            addRegla("25");
            emparejar(Token.ESCRIBIR);
            emparejar(Token.PARI);
            E("");
            emparejar(Token.PARD);
            return "EL ESCRIBIR AUN TENGO QUE MIRARLO";
        }
        else{
            errorSintaxis(new TreeSet<Integer>(){
                {add(Token.BLQ);
                 add(Token.ID);
                 add(Token.SI);
                 add(Token.MIENTRAS);
                 add(Token.ESCRIBIR);
                }
            });
        }
        return "";
    }
    public String Instrp(){
        if(token.tipo == Token.FSI){
            addRegla("22");
            emparejar(Token.FSI);
            return "";
        }
        else if(token.tipo == Token.SINO){
            addRegla("23");
            emparejar(Token.SINO);
            String instr_trad = Instr();
            emparejar(Token.FSI);
            return "else " + instr_trad;
        }
        else{
            errorSintaxis(new TreeSet<Integer>(){
                {add(Token.FSI);
                 add(Token.SINO);
                }
            });
        }
        return "";
    }
    // METHOD OVERLOAD
    public String E(String tipo){
        return E(tipo, false);
    }
    public String E(String tipo, boolean isPrint){
        if(token.tipo == Token.ID || token.tipo == Token.NENTERO || token.tipo == Token.NREAL || token.tipo == Token.PARI){
            addRegla("26");
            String expr_trad = Expr(tipo);
            String ep_trad = Ep(tipo);
            return expr_trad + " " + ep_trad;
        }
        else{
            errorSintaxis(new TreeSet<Integer>(){
                {add(Token.ID);
                 add(Token.NENTERO);
                 add(Token.NREAL);
                 add(Token.PARI);
                }
            });
        }
        return "";
    }
    public String Ep(String tipo){
        if(token.tipo == Token.OPREL){
            addRegla("27");
            String oprel = token.lexema;
            emparejar(Token.OPREL);
            String expr_trad = Expr(tipo);
            return oprel + " " + tipo + " " + expr_trad; 
        }
        else if(token.tipo == Token.ENTONCES || token.tipo == Token.HACER || token.tipo == Token.PARD || token.tipo == Token.FSI
                || token.tipo == Token.PYC || token.tipo == Token.FBLQ || token.tipo == Token.SINO
        ){
            // epsilon
            addRegla("28");
        }
        else{
            errorSintaxis(new TreeSet<Integer>(){
                {add(Token.OPREL);
                 add(Token.ENTONCES);
                 add(Token.HACER);
                 add(Token.PARD);
                 add(Token.FSI);
                 add(Token.PYC);
                 add(Token.FBLQ);
                 add(Token.SINO);
                }
            });
        }
        return "";
    }
    public String Expr(String tipo){
        if(token.tipo == Token.ID || token.tipo == Token.NENTERO || token.tipo == Token.NREAL || token.tipo == Token.PARI){
            addRegla("29");
            String term_trad = Term(tipo);
            String exprp_trad = Exprp(tipo);
            return term_trad + exprp_trad;
        }
        else{
            errorSintaxis(new TreeSet<Integer>(){
                {add(Token.ID);
                 add(Token.NENTERO);
                 add(Token.NREAL);
                 add(Token.PARI);
                }
            });
        }
        return "";
    }
    public String Exprp(String tipo){
        if(token.tipo == Token.OPAS){
            addRegla("30");
            String opas = token.lexema;
            emparejar(Token.OPAS);
            String term_trad = Term(tipo);
            String exprp_trad = Exprp(tipo);
            return opas + " " + tipo + " " + term_trad + exprp_trad;

        }
        else if(token.tipo == Token.ENTONCES || token.tipo == Token.HACER || token.tipo == Token.PARD || token.tipo == Token.FSI || token.tipo == Token.OPREL
                || token.tipo == Token.PYC || token.tipo == Token.FBLQ || token.tipo == Token.SINO){
            // epsilon
            addRegla("31");
            return "";
        }
        else{
            errorSintaxis(new TreeSet<Integer>(){
                {add(Token.OPAS);
                 add(Token.ENTONCES);
                 add(Token.HACER);
                 add(Token.PARD);
                 add(Token.FSI);
                 add(Token.OPREL);
                 add(Token.PYC);
                 add(Token.FBLQ);
                 add(Token.SINO);
                }
            });
        }
        return "";
    }
    public String Term(String tipo){
        if(token.tipo == Token.ID || token.tipo == Token.NENTERO || token.tipo == Token.NREAL || token.tipo == Token.PARI){
            addRegla("32");
            String factor_trad = Factor(tipo);
            String termp_trad = Termp(tipo);
            return factor_trad + " " + termp_trad;
        }
        else{
            errorSintaxis(new TreeSet<Integer>(){
                {add(Token.ID);
                 add(Token.NENTERO);
                 add(Token.NREAL);
                 add(Token.PARI);
                }
            });
        }
        return "";
    }
    public String Termp(String tipo){
        if(token.tipo == Token.OPMD){
            addRegla("33");
            String opmd = token.lexema;
            emparejar(Token.OPMD);
            String factor_trad = Factor(tipo);
            String termp_trad = Termp(tipo);

            return opmd + " " + tipo + " " + termp_trad + " " + factor_trad;
        }
        else if(token.tipo == Token.ENTONCES || token.tipo == Token.HACER || 
        token.tipo == Token.PARD || token.tipo == Token.FSI || token.tipo == Token.OPREL 
        || token.tipo == Token.OPAS || token.tipo == Token.PYC || token.tipo == Token.FBLQ 
        || token.tipo == Token.SINO){
            // epsilon
            addRegla("34");
        }
        else{
            errorSintaxis(new TreeSet<Integer>(){
                {add(Token.OPMD);
                 add(Token.ENTONCES);
                 add(Token.HACER);
                 add(Token.PARD);
                 add(Token.FSI);
                 add(Token.OPREL);
                 add(Token.OPAS);
                 add(Token.PYC);
                 add(Token.FBLQ);
                 add(Token.SINO);
                }
            });
        }
        return "";
    }
    public String Factor(String tipo){
        if(token.tipo == Token.ID){
            addRegla("35");
            emparejar(Token.ID);
            return token.lexema;
        }
        else if(token.tipo == Token.NENTERO){
            addRegla("36");
            emparejar(Token.NENTERO);
            return token.lexema;
        }
        else if(token.tipo == Token.NREAL){
            addRegla("37");
            emparejar(Token.NREAL);
            return token.lexema;
        }
        else if(token.tipo == Token.PARI){
            addRegla("38");
            emparejar(Token.PARI);
            String trad_expr = Expr(tipo);
            emparejar(Token.PARD);

            return "(" + trad_expr  + ")";
        }
        else{
            errorSintaxis(new TreeSet<Integer>(){
                {add(Token.ID);
                 add(Token.NENTERO);
                 add(Token.NREAL);
                 add(Token.PARI);
                }
            });
        }
        return "";
    }






    private final int ERR_YA_EXISTE=1,
    ERR_NO_VARIABLE=2,
    ERR_NO_DECL=3,
    ERR_NO_BOOL=4,
    ERR_ASIG_REAL=5,
    ERR_SIMIENTRAS=6,
    ERR_DIVENTERA=7;
    
    private void errorSemantico(int nerr,int fila,int columna,String lexema) {
    System.err.print("Error semantico ("+fila+","+columna+"): ");
    switch (nerr) {
    case ERR_YA_EXISTE:
    System.err.println("'"+lexema+"' ya existe en este ambito");
    break;
    case ERR_NO_VARIABLE:
    System.err.println("'"+lexema+"' no es una variable");
    break;
    case ERR_NO_DECL:
    System.err.println("'"+lexema+"' no ha sido declarado");
    break;
    case ERR_NO_BOOL:
    System.err.println("'"+lexema+"' no admite expresiones booleanas");
    break;
    case ERR_ASIG_REAL:
    System.err.println("'"+lexema+"' debe ser de tipo real");
    break;
    case ERR_SIMIENTRAS:
    System.err.println("en la instruccion '"+lexema+"' la expresion debe ser relacional");
    break;
    case ERR_DIVENTERA:
    System.err.println("los dos operandos de '"+lexema+"' deben ser enteros");
    break;
    }
    System.exit(-1);
    }
}
