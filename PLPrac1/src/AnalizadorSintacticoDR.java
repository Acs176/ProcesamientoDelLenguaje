

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
            // ERROR
        }            
    }

    public void S(){

    }
    public void Vsp(){

    }
    public void Vspp(){

    }
    public void Unsp(){

    }
    public void LV(){

    }
    public void LVp(){

    }
    public void V(){

    }
    public void Lid(){

    }
    public void Tipo(){

    }
    public void Bloque(){

    }
    public void SInstr(){

    }
    public void SInstrp(){

    }
    public void Instr(){

    }
    public void Instrp(){

    }
    public void E(){

    }
    public void Ep(){

    }
    public void Expr(){

    }
    public void Exprp(){

    }
    public void Term(){

    }
    public void Termp(){

    }
    public void Factor(){

    }

}
