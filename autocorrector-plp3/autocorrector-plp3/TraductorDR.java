import java.util.TreeSet;

class TipoCompuesto{
    public String trad;
    public String tipo;
    public boolean isBool=false;
    public TipoCompuesto(String trad, String tipo) {
        this.trad = trad;
        this.tipo = tipo;
    }
    public void setBool() {isBool=true;}

}

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

    public void debug(String s){
        //System.out.println("entra en " + s);
    }

    public void debug2(String s){
        //System.out.println("Sale de " + s);
    }

    public int traducirTipo(String tipo){
        int tipoSimbolo;
        switch(tipo){
            case "int":
                tipoSimbolo = 1;
                break;
            case "double":
                tipoSimbolo = 2;
                break;
            default:
                // ERROR
                return -1;
            
        }
        return tipoSimbolo;
    }

    public String traducirTipo(int tipo){
        switch(tipo){
            case 1:
                return "int";
            case 2:
                return "double";
            default:
                return "";
        }
    }

    public String comprobarTipos(String t1, String t2){
        if(t1 == t2){
            return t1;
        }
        else if(t2 == ""){
            return t1;
        }
        else if(t1 == ""){
            return t2;
        }
        else{
            return "r";
        }
    }

    public String S(){
        debug("s");
        if(token.tipo == Token.ALGORITMO){
            TS = new TablaSimbolos(null);
            TS.nuevoSimbolo(new Simbolo("main", "main", Simbolo.ENTERO));
            

            addRegla("1");
            emparejar(Token.ALGORITMO);
            String nombre_alg = token.lexema;
            emparejar(Token.ID);
            emparejar(Token.PYC);
            String vsp_trad = Vsp(true, "");
            String bloque_trad = Bloque(true, "main", "int");
            return "// algoritmo " + nombre_alg + "\n\n" + vsp_trad + bloque_trad; 
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
        debug("Vsp");
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
        debug("Vspp");
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
        debug("Unsp");
        if(token.tipo == Token.FUNCION){
            addRegla("5");
            emparejar(Token.FUNCION);
            String func_nombre = token.lexema;
            Token infoToken = token;
            emparejar(Token.ID);
            emparejar(Token.DOSP);

            String trad_tipo = Tipo();
            Simbolo nuevoSimb = new Simbolo(func_nombre, prefix + func_nombre, Simbolo.FUNCION);
            if(!TS.nuevoSimbolo(nuevoSimb)){
                System.err.println("Error semantico (" + infoToken.fila + "," + infoToken.columna + "): \'" + infoToken.lexema + "\' ya existe en este ambito");
                System.exit(-1);
            }
            TS = new TablaSimbolos(TS);

            emparejar(Token.PYC);
            String vsp_trad = Vsp(prefix + func_nombre + "_");
            String bloque_trad = Bloque(true, nuevoSimb.nombreCompleto, trad_tipo);
            emparejar(Token.PYC);
            TS = TS.getAmbitoAnterior();
            return vsp_trad + bloque_trad;
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
        debug("LV");
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
        debug("LVp");
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
        debug("V");
        if(token.tipo == Token.ID){
            addRegla("10");
            String id = token.lexema;
            Token infoToken = token;
            emparejar(Token.ID);
            String lid_trad = Lid(prefix);
            emparejar(Token.DOSP);
            String tipo_trad = Tipo();
            emparejar(Token.PYC);

            int tipoSimbolo = traducirTipo(tipo_trad);

            Simbolo nuevoSimb = new Simbolo(id, prefix + id, tipoSimbolo);
            if(!TS.nuevoSimbolo(nuevoSimb)){
                errorSemantico(ERR_YA_EXISTE, infoToken.fila, infoToken.columna, infoToken.lexema);
                System.exit(-1);
            }
            
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
        debug("Lid");
        if(token.tipo == Token.COMA){
            addRegla("11");
            emparejar(Token.COMA);
            String id = token.lexema;
            
            Token infoToken = token;
            Simbolo nuevoSimb = new Simbolo(id, prefix + id, -1);
            if(!TS.nuevoSimbolo(nuevoSimb)){
                errorSemantico(ERR_YA_EXISTE, infoToken.fila, infoToken.columna, infoToken.lexema);
                //System.err.println("Error semantico (" + infoToken.fila + "," + infoToken.columna + "): \'" + infoToken.lexema + "\' ya existe en este ambito");
                System.exit(-1);
            }
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
        debug("Tipo");
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
        return Bloque(false, "", "");
    }

    public String Bloque(boolean esFunc, String nombreSimbolo, String tipo_trad){
        debug("Bloque");
        if(token.tipo == Token.BLQ){
            addRegla("15");
            emparejar(Token.BLQ);
            String sinstr_trad = SInstr();
            emparejar(Token.FBLQ);
            String parteFuncion = "";
            if(esFunc){
                // ES POSIBLE QUE NO ESTE
                //Simbolo funcion = TS.buscar(nombreSimbolo);
                parteFuncion = tipo_trad + " " + nombreSimbolo + "() ";
            }
            return parteFuncion + "{\n" + sinstr_trad + "}\n";
            
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
        debug("SInstr");
        if(token.tipo == Token.ID || token.tipo == Token.SI || token.tipo == Token.MIENTRAS || token.tipo == Token.ESCRIBIR || token.tipo == Token.BLQ){
            addRegla("16");
            String instr_trad = Instr();
            String sinstrp_trad = SInstrp();
            return instr_trad + '\n' + sinstrp_trad;
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
        debug("SInstrp");
        if(token.tipo == Token.PYC){
            addRegla("17");
            emparejar(Token.PYC);
            String instr_trad = Instr();
            String sinstrp_trad = SInstrp();
            return instr_trad + '\n' + sinstrp_trad;
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
        debug("Instr");
        if(token.tipo == Token.BLQ){
            addRegla("19");
            return Bloque();
        }
        else if(token.tipo == Token.ID){
            //check ID existe en ambito
            Token id = token;
            Simbolo simbolo = TS.buscar(token.lexema);
            if(simbolo != null){
                if(simbolo.tipo == Simbolo.FUNCION){
                    errorSemantico(ERR_NO_VARIABLE, id.fila, id.columna, id.lexema);
                    System.exit(-1);
                }
                addRegla("20");
                emparejar(Token.ID);
                Token infoTokenAsig = token;
                emparejar(Token.ASIG);
                TipoCompuesto e_trad;
                String tipo_asignacion;
                String tipo_variable;
                switch(simbolo.tipo){
                    case 1:
                        tipo_variable = "i";
                        e_trad = E("i");
                        tipo_asignacion = "=i";
                        break;
                    case 2:
                        tipo_variable = "r";
                        e_trad = E("r");
                        tipo_asignacion = "=r";
                        break;
                    default:
                        //error
                        return "";
                }

                if(e_trad.isBool){
                    errorSemantico(ERR_NO_BOOL, infoTokenAsig.fila, infoTokenAsig.columna, infoTokenAsig.lexema);
                }
                if(tipo_variable == "i" && e_trad.tipo == "r"){
                    errorSemantico(ERR_ASIG_REAL, token.fila, token.columna, token.lexema);
                }
                return simbolo.nombreCompleto + " " + tipo_asignacion + " " + e_trad.trad + ';'; 

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
            TipoCompuesto e_trad = E("");
            if(!e_trad.isBool){
                errorSemantico(ERR_SIMIENTRAS, token.fila, token.columna, e_trad.trad);
            }
            emparejar(Token.ENTONCES);
            String instr_trad = Instr();
            String instrp_trad = Instrp();

            return "if (" + e_trad.trad + ")\n" + instr_trad + "\n" + instrp_trad ;
        }
        else if(token.tipo == Token.MIENTRAS){
            addRegla("24");
            Token infoTokenMientras = token;
            emparejar(Token.MIENTRAS);
            TipoCompuesto e_trad = E("");
            if(!e_trad.isBool){
                errorSemantico(ERR_SIMIENTRAS, infoTokenMientras.fila, infoTokenMientras.columna, infoTokenMientras.lexema);
            }
            emparejar(Token.HACER);
            String instr_trad = Instr();

            return "while ( " + e_trad.trad + ")\n" + instr_trad;
        }
        else if(token.tipo == Token.ESCRIBIR){
            addRegla("25");
            Token infoToken = token;
            emparejar(Token.ESCRIBIR);
            emparejar(Token.PARI);
            TipoCompuesto e_trad = E("", true);

            if(e_trad.isBool){
                errorSemantico(ERR_NO_BOOL, infoToken.fila, infoToken.columna, infoToken.lexema);
            }

            emparejar(Token.PARD);
            if(e_trad.tipo == "i"){
                return "printf(\"%d\\n\"," + e_trad.trad + ");";
            }
            else{
                return "printf(\"%g\\n\"," + e_trad.trad + ");";
            }
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
        debug("Instrp");
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
            return "else\n" + instr_trad;
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
    public TipoCompuesto E(String tipo){
        return E(tipo, false);
    }
    public TipoCompuesto E(String tipo, boolean isPrint){
        TipoCompuesto toReturn = new TipoCompuesto("", "");
        //debug("E");
        if(token.tipo == Token.ID || token.tipo == Token.NENTERO || token.tipo == Token.NREAL || token.tipo == Token.PARI){
            addRegla("26");
            TipoCompuesto expr_trad = Expr(tipo);
            TipoCompuesto ep_trad = Ep(expr_trad.tipo);

            if(!ep_trad.trad.equals("")){
                toReturn.setBool();
               
            }
                
            String tipoFinal = expr_trad.tipo;
            if(expr_trad.tipo != ep_trad.tipo && ep_trad.trad != ""){
                expr_trad.trad = "itor(" + expr_trad.trad + ")";
                tipoFinal = "r";
            }
            String traduccion = expr_trad.trad + ep_trad.trad;
            toReturn.tipo = tipoFinal;
            toReturn.trad = traduccion;
            return toReturn;
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
        return new TipoCompuesto("", "");
    }
    public TipoCompuesto Ep(String tipo_anterior){
        //debug("Ep");
        if(token.tipo == Token.OPREL){
            addRegla("27");
            String oprel = token.lexema;
            emparejar(Token.OPREL);
            TipoCompuesto expr_trad = Expr(tipo_anterior);
            String tipoFinal = comprobarTipos(tipo_anterior, expr_trad.tipo);
            if(expr_trad.tipo != tipoFinal){
                expr_trad.trad = "itor(" + expr_trad.trad + ")";
            }

            if(oprel.equals("=")){
                oprel = "==";
            }
            else if(oprel.equals("<>")){
                oprel = "!=";
            }

            String traduccion = " " + oprel + tipoFinal + " " + expr_trad.trad;
            return new TipoCompuesto(traduccion, tipoFinal); 
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
        return new TipoCompuesto("", "");
    }
    public TipoCompuesto Expr(String tipo_variable){
        //debug("Expr");
        if(token.tipo == Token.ID || token.tipo == Token.NENTERO || token.tipo == Token.NREAL || token.tipo == Token.PARI){
            addRegla("29");
            TipoCompuesto term_trad = Term(tipo_variable);
            String tipoFinal = comprobarTipos(tipo_variable, term_trad.tipo);
            TipoCompuesto exprp_trad = Exprp(tipoFinal);

            tipoFinal = comprobarTipos(term_trad.tipo, exprp_trad.tipo);
            if(term_trad.tipo != tipoFinal){
                term_trad.trad = "itor(" + term_trad.trad + ")";
            }
            return new TipoCompuesto(term_trad.trad + exprp_trad.trad, tipoFinal);
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
        return new TipoCompuesto("", "");
    }
    public TipoCompuesto Exprp(String tipoAnterior){
        //debug("Exprp");
        if(token.tipo == Token.OPAS){
            addRegla("30");
            String opas = token.lexema;
            emparejar(Token.OPAS);
            TipoCompuesto term_trad = Term(tipoAnterior);
            String tipoFinal = comprobarTipos(tipoAnterior, term_trad.tipo);

            TipoCompuesto exprp_trad = Exprp(tipoFinal);
            tipoFinal = comprobarTipos(tipoFinal, exprp_trad.tipo);
            
            if(term_trad.tipo != tipoFinal){
                // SI ESTO SE CUMPLE, METER PARENTESIS ) EN LA TRAD DE EXPRP Y SUBIR HASTA E
                term_trad.trad = "itor(" + term_trad.trad + ")";
            }

            String traduccion = ' ' + opas + tipoFinal + " " + term_trad.trad + exprp_trad.trad;
            return new TipoCompuesto(traduccion, tipoFinal);
        }
        else if(token.tipo == Token.ENTONCES || token.tipo == Token.HACER || token.tipo == Token.PARD || token.tipo == Token.FSI || token.tipo == Token.OPREL
                || token.tipo == Token.PYC || token.tipo == Token.FBLQ || token.tipo == Token.SINO){
            // epsilon
            addRegla("31");

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
        return new TipoCompuesto("", "");
    }
    public TipoCompuesto Term(String tipo_variable){
        //debug("Term");
        if(token.tipo == Token.ID || token.tipo == Token.NENTERO || token.tipo == Token.NREAL || token.tipo == Token.PARI){
            addRegla("32");
            TipoCompuesto factor_trad = Factor(tipo_variable);
            String tipoFinal = comprobarTipos(tipo_variable, factor_trad.tipo);
            //System.out.println("DEBUG: " + factor_trad.trad + " " + factor_trad.tipo + " TIPO FINAL>" + tipoFinal);
            TipoCompuesto termp_trad = Termp(tipoFinal, factor_trad.tipo);
            
            tipoFinal = comprobarTipos(tipoFinal, termp_trad.tipo);
            if(factor_trad.tipo != tipoFinal){
                factor_trad.trad = "itor(" + factor_trad.trad + ")";
            }

            return new TipoCompuesto(factor_trad.trad + termp_trad.trad, tipoFinal);
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
        return new TipoCompuesto("", "");
    }
    public TipoCompuesto Termp(String tipo_anterior){
        return Termp(tipo_anterior, "");
    }

    public TipoCompuesto Termp(String tipo_anterior, String tipo_fact_anterior){
        //debug("Termp");
        if(token.tipo == Token.OPMD){
            addRegla("33");
            String opmd = token.lexema;
            Token infoToken = token;
            emparejar(Token.OPMD);
            TipoCompuesto factor_trad = Factor(tipo_anterior);

            //COMPROBAR TIPOS
            String tipo_factor = factor_trad.tipo;

            if(opmd.equals("//")){
                opmd = "/"; // cambiamos al lenguaje objeto
                if(tipo_fact_anterior.equals("r") || tipo_factor.equals("r")){
                    //ERROR
                    errorSemantico(ERR_DIVENTERA, infoToken.fila, infoToken.columna, infoToken.lexema);
                }
            }

            String tipo_final = comprobarTipos(tipo_anterior, tipo_factor);

            TipoCompuesto termp_trad = Termp(tipo_final);
            tipo_final = comprobarTipos(tipo_final, termp_trad.tipo);
            if(tipo_final != tipo_factor){
                factor_trad.trad = "itor(" + factor_trad.trad + ")";
            }

            String traduccion = ' ' + opmd + tipo_final + " " + factor_trad.trad + termp_trad.trad;
            return new TipoCompuesto(traduccion, tipo_final);
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
        return new TipoCompuesto("", "");
    }
    public TipoCompuesto Factor(String tipo){
        //debug("Factor");
        if(token.tipo == Token.ID){
            
            Simbolo s = TS.buscar(token.lexema);
            if(s==null){
                errorSemantico(ERR_NO_DECL, token.fila, token.columna, token.lexema);
            }
            if(s.tipo == 3){
                errorSemantico(ERR_NO_VARIABLE, token.fila, token.columna, token.lexema);
            }
            String nombreSimbolo = s.nombreCompleto;
            String tipoSimbolo;
            switch(s.tipo){
                case 1:
                    tipoSimbolo = "i";
                    break;
                case 2:
                    tipoSimbolo = "r";
                    break;
                default:
                    //error
                    tipoSimbolo = "";
            }
            addRegla("35");
            emparejar(Token.ID);
            return new TipoCompuesto(nombreSimbolo, tipoSimbolo);
        }
        else if(token.tipo == Token.NENTERO){
            addRegla("36");
            String nentero = token.lexema;
            String tipoString = "i";
            emparejar(Token.NENTERO);
            return new TipoCompuesto(nentero, tipoString);
        }
        else if(token.tipo == Token.NREAL){
            addRegla("37");
            String nreal = token.lexema;
            String tipoString = "r";
            emparejar(Token.NREAL);
            return new TipoCompuesto(nreal, tipoString);
        }
        else if(token.tipo == Token.PARI){
            addRegla("38");
            emparejar(Token.PARI);
            TipoCompuesto trad_expr = Expr(tipo);
            emparejar(Token.PARD);
            String traduccion = "(" + trad_expr.trad  + ")";
            
            return new TipoCompuesto(traduccion, trad_expr.tipo) ;
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
        return new TipoCompuesto("", "");
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
