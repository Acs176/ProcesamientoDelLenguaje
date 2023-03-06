import java.util.TreeSet;

public class AnalizadorSintacticoDR {
    AnalizadorLexico lexico;
    Token token;
    public AnalizadorSintacticoDR(AnalizadorLexico al) {
        lexico = al;
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

    public void errorSintaxis(TreeSet<Integer> esperado){
        String esperado_string = "";
        for(Integer i : esperado){
            esperado_string += i.toString();
        }
        System.out.println("Error sintactico (" + token.fila + "," + token.columna + "): encontrado \'" + token.lexema + "\', esperaba " + esperado_string);
        System.exit(-1);
    }

    public void S(){
        if(token.tipo == Token.ALGORITMO){
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
            Unsp();
            Vspp();
        }
        else if(token.tipo == Token.BLQ){
            // epsilon
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
            V();
            LVp();
        }
        else if(token.tipo == Token.FUNCION || token.tipo == Token.VAR || token.tipo == Token.BLQ){
            // epsilon
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
            emparejar(Token.COMA);
            emparejar(Token.ID);
            Lid();
        }
        else if(token.tipo == Token.DOSP){
            // epsilon
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
            emparejar(Token.ENTERO);
        }
        else if(token.tipo == Token.REAL){
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
            emparejar(Token.PYC);
            Instr();
            SInstrp();
        }
        else if(token.tipo == Token.FBLQ){
            // epsilon
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
            Bloque();
        }
        else if(token.tipo == Token.ID){
            emparejar(Token.ID);
            emparejar(Token.ASIG);
            E();
        }
        else if(token.tipo == Token.SI){
            emparejar(Token.SI);
            E();
            emparejar(Token.ENTONCES);
            Instr();
            Instrp();
        }
        else if(token.tipo == Token.MIENTRAS){
            emparejar(Token.MIENTRAS);
            E();
            emparejar(Token.HACER);
            Instr();
        }
        else if(token.tipo == Token.ESCRIBIR){
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
            emparejar(Token.FSI);
        }
        else if(token.tipo == Token.SINO){
            emparejar(Token.SINO);
            Instr();
            emparejar(Token.FSI);
        }
    }
    public void E(){
        if(token.tipo == Token.ID || token.tipo == Token.NENTERO || token.tipo == Token.NREAL || token.tipo == Token.PARI){
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
            emparejar(Token.OPREL);
            Expr();
        }
        else if(token.tipo == Token.ENTONCES || token.tipo == Token.HACER || token.tipo == Token.PARD || token.tipo == Token.FSI){
            // epsilon
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
            emparejar(Token.OPAS);
            Termp();
            Exprp();
        }
        else if(token.tipo == Token.ENTONCES || token.tipo == Token.HACER || token.tipo == Token.PARD || token.tipo == Token.FSI || token.tipo == Token.OPREL){
            // epsilon
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
            emparejar(Token.OPMD);
            Factor();
            Termp();
        }
        else if(token.tipo == Token.ENTONCES || token.tipo == Token.HACER || 
        token.tipo == Token.PARD || token.tipo == Token.FSI || token.tipo == Token.OPREL || token.tipo == Token.OPAS){
            // epsilon
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
            emparejar(Token.ID);
        }
        else if(token.tipo == Token.NENTERO){
            emparejar(Token.NENTERO);
        }
        else if(token.tipo == Token.NREAL){
            emparejar(Token.NREAL);
        }
        else if(token.tipo == Token.PARI){
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
