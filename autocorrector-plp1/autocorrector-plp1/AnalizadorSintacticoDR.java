import java.util.TreeSet;

public class AnalizadorSintacticoDR {
    AnalizadorLexico lexico;
    Token token;
    
    StringBuilder reglas;
    boolean flag;

    public AnalizadorSintacticoDR(AnalizadorLexico al) {
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
        if(regla != "1")
            reglas.append(" " + regla);
        else
            reglas.append(regla);
    }

    public void comprobarFinFichero(){
        System.out.println(reglas);
    }

    public void errorSintaxis(TreeSet<Integer> esperado){
        String esperado_string = "";
        for(Integer i : esperado){
            esperado_string += Token.nombreToken.get(i);
        }
        System.err.println("Error sintactico (" + token.fila + "," + token.columna + "): encontrado \'" + token.lexema + "\', esperaba " + esperado_string);
        System.exit(-1);
    }

    public void S(){
        
        if(token.tipo == Token.ALGORITMO){
            addRegla("1");
            emparejar(Token.ALGORITMO);
            emparejar(Token.ID);
            emparejar(Token.PYC);
            Vsp();
            Bloque();
        }
        else{
            errorSintaxis(new TreeSet<Integer>(){{add(Token.ALGORITMO);}});
        }
    }
    public void Vsp(){
        if(token.tipo == Token.FUNCION || token.tipo == Token.VAR){
            addRegla("2");
            Unsp();
            Vspp();
        }
        else{
            errorSintaxis(new TreeSet<Integer>(){
                {add(Token.FUNCION);
                 add(Token.VAR);
                }
            });
        }
    }
    public void Vspp(){
        if(token.tipo == Token.FUNCION || token.tipo == Token.VAR){
            addRegla("3");
            Unsp();
            Vspp();
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
    }
    public void Unsp(){
        if(token.tipo == Token.FUNCION){
            addRegla("5");
            emparejar(Token.FUNCION);
            emparejar(Token.ID);
            emparejar(Token.DOSP);
            Tipo();
            emparejar(Token.PYC);
            Vsp();
            Bloque();
            emparejar(Token.PYC);

        }
        else if(token.tipo == Token.VAR){
            addRegla("6");
            emparejar(Token.VAR);
            LV();
        }
        else{
            errorSintaxis(new TreeSet<Integer>(){
                {add(Token.FUNCION);
                 add(Token.VAR);
                }
            }); 
        }
    }
    public void LV(){
        if(token.tipo == Token.ID){
            addRegla("7");
            V();
            LVp();
        }
        else{
            errorSintaxis(new TreeSet<Integer>(){
                {add(Token.ID);
                }
            });
        }
    }
    public void LVp(){
        if(token.tipo == Token.ID){
            addRegla("8");
            V();
            LVp();
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
    }
    public void V(){
        if(token.tipo == Token.ID){
            addRegla("10");
            emparejar(Token.ID);
            Lid();
            emparejar(Token.DOSP);
            Tipo();
            emparejar(Token.PYC);
        }
        else{
            errorSintaxis(new TreeSet<Integer>(){
                {add(Token.ID);
                }
            });
        }
    }
    public void Lid(){
        if(token.tipo == Token.COMA){
            addRegla("11");
            emparejar(Token.COMA);
            emparejar(Token.ID);
            Lid();
        }
        else if(token.tipo == Token.DOSP){
            // epsilon
            addRegla("12");
        }
        else{
            errorSintaxis(new TreeSet<Integer>(){
                {add(Token.COMA);
                 add(Token.DOSP);
                }
            });
        }
    }
    public void Tipo(){
        if(token.tipo == Token.ENTERO){
            addRegla("13");
            emparejar(Token.ENTERO);
        }
        else if(token.tipo == Token.REAL){
            addRegla("14");
            emparejar(Token.REAL);
        }
        else{
            errorSintaxis(new TreeSet<Integer>(){
                {add(Token.ENTERO);
                 add(Token.REAL);
                }
            });
        }
    }
    public void Bloque(){
        if(token.tipo == Token.BLQ){
            addRegla("15");
            emparejar(Token.BLQ);
            SInstr();
            emparejar(Token.FBLQ);
        }
        else{
            errorSintaxis(new TreeSet<Integer>(){
                {add(Token.BLQ);
                }
            });
        }
    }
    public void SInstr(){
        if(token.tipo == Token.ID || token.tipo == Token.SI || token.tipo == Token.MIENTRAS || token.tipo == Token.ESCRIBIR || token.tipo == Token.BLQ){
            addRegla("16");
            Instr();
            SInstrp();
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
        }    
    }
    public void SInstrp(){
        if(token.tipo == Token.PYC){
            addRegla("17");
            emparejar(Token.PYC);
            Instr();
            SInstrp();
        }
        else if(token.tipo == Token.FBLQ){
            // epsilon
            addRegla("18");
        }
        else{
            errorSintaxis(new TreeSet<Integer>(){
                {add(Token.PYC);
                 add(Token.FBLQ);
                }
            });
        }
    }
    public void Instr(){
        if(token.tipo == Token.BLQ){
            addRegla("19");
            Bloque();
        }
        else if(token.tipo == Token.ID){
            addRegla("20");
            emparejar(Token.ID);
            emparejar(Token.ASIG);
            E();
        }
        else if(token.tipo == Token.SI){
            addRegla("21");
            emparejar(Token.SI);
            E();
            emparejar(Token.ENTONCES);
            Instr();
            Instrp();
        }
        else if(token.tipo == Token.MIENTRAS){
            addRegla("24");
            emparejar(Token.MIENTRAS);
            E();
            emparejar(Token.HACER);
            Instr();
        }
        else if(token.tipo == Token.ESCRIBIR){
            addRegla("25");
            emparejar(Token.ESCRIBIR);
            emparejar(Token.PARI);
            E();
            emparejar(Token.PARD);
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
    }
    public void Instrp(){
        if(token.tipo == Token.FSI){
            addRegla("22");
            emparejar(Token.FSI);
        }
        else if(token.tipo == Token.SINO){
            addRegla("23");
            emparejar(Token.SINO);
            Instr();
            emparejar(Token.FSI);
        }
        else{
            errorSintaxis(new TreeSet<Integer>(){
                {add(Token.FSI);
                 add(Token.SINO);
                }
            });
        }
    }
    public void E(){
        if(token.tipo == Token.ID || token.tipo == Token.NENTERO || token.tipo == Token.NREAL || token.tipo == Token.PARI){
            addRegla("26");
            Expr();
            Ep();
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
    }
    public void Ep(){
        if(token.tipo == Token.OPREL){
            addRegla("27");
            emparejar(Token.OPREL);
            Expr();
        }
        else if(token.tipo == Token.ENTONCES || token.tipo == Token.HACER || token.tipo == Token.PARD || token.tipo == Token.FSI){
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
                }
            });
        }
    }
    public void Expr(){
        if(token.tipo == Token.ID || token.tipo == Token.NENTERO || token.tipo == Token.NREAL || token.tipo == Token.PARI){
            addRegla("29");
            Term();
            Exprp();
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
    }
    public void Exprp(){
        if(token.tipo == Token.OPAS){
            addRegla("30");
            emparejar(Token.OPAS);
            Termp();
            Exprp();
        }
        else if(token.tipo == Token.ENTONCES || token.tipo == Token.HACER || token.tipo == Token.PARD || token.tipo == Token.FSI || token.tipo == Token.OPREL){
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
                }
            });
        }
    }
    public void Term(){
        if(token.tipo == Token.ID || token.tipo == Token.NENTERO || token.tipo == Token.NREAL || token.tipo == Token.PARI){
            addRegla("32");
            Factor();
            Termp();
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
    }
    public void Termp(){
        if(token.tipo == Token.OPMD){
            addRegla("33");
            emparejar(Token.OPMD);
            Factor();
            Termp();
        }
        else if(token.tipo == Token.ENTONCES || token.tipo == Token.HACER || 
        token.tipo == Token.PARD || token.tipo == Token.FSI || token.tipo == Token.OPREL || token.tipo == Token.OPAS){
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
                }
            });
        }
    }
    public void Factor(){
        if(token.tipo == Token.ID){
            addRegla("35");
            emparejar(Token.ID);
        }
        else if(token.tipo == Token.NENTERO){
            addRegla("36");
            emparejar(Token.NENTERO);
        }
        else if(token.tipo == Token.NREAL){
            addRegla("37");
            emparejar(Token.NREAL);
        }
        else if(token.tipo == Token.PARI){
            addRegla("38");
            emparejar(Token.PARI);
            Expr();
            emparejar(Token.PARD);
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
    }

}
