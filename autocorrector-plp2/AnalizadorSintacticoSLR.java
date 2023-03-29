import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Arrays;


public class AnalizadorSintacticoSLR {
    private AnalizadorLexico al;
    List<Integer> reglas_aplicadas = new ArrayList<Integer>();
    Stack<Integer> pila_estados = new Stack<Integer>();
    public AnalizadorSintacticoSLR(AnalizadorLexico al) {
        this.al = al;
        Arrays.stream(Tabla.Accion).forEach(row -> Arrays.fill(row, "o0"));
        rellenarIr_A();
        rellenarTablaAccion();
    }

    public void analizar(){
        Token a = al.siguienteToken();
        boolean fin = false;
        do{
            int estado = 1;
            System.out.println(estado + " " + a.tipo);
            char accion = Tabla.Accion[estado][a.tipo].charAt(0);
            int estado_regla = Character.getNumericValue(Tabla.Accion[estado][a.tipo].charAt(1));
            System.out.println("accion " + accion + " estado_regla " + estado_regla);
            if(accion == 'd'){
                pila_estados.push(estado_regla);
                a = al.siguienteToken();
            }
            else if(accion == 'r'){
                reglas_aplicadas.add(estado_regla);
                for(int i=1; i< Tabla.longitudParteDerecha(estado_regla); i++){
                    pila_estados.pop();
                }
                int p = pila_estados.lastElement();
                int izq = Tabla.parteIzq(estado_regla);
                pila_estados.push(Tabla.Ir_A[p][izq]);
            }
            else if(Tabla.Accion[estado][a.tipo] == "aceptar")
                fin = true;
            else{
                // ERRROR
                System.exit(-1);
            }
        } while(!fin);

        imprimirSalida();
    }

    public void imprimirSalida(){
        for(int i = reglas_aplicadas.size()-1; i>=0; i--){
            System.out.print(reglas_aplicadas.get(i) + ' ');
        }
    }
    public void rellenarIr_A(){
        Tabla.Ir_A[1][Tabla.S]      =  2;
        Tabla.Ir_A[5][Tabla.Vsp]    =  6;
        Tabla.Ir_A[5][Tabla.Unsp]   =  7;
        Tabla.Ir_A[6][Tabla.Unsp]   = 48;
        Tabla.Ir_A[6][Tabla.Bloque] =  9;
        Tabla.Ir_A[8][Tabla.LV]     = 45;
        Tabla.Ir_A[8][Tabla.V]      = 20;
        Tabla.Ir_A[10][Tabla.Bloque]= 14;
        Tabla.Ir_A[10][Tabla.SInstr]= 11;
        Tabla.Ir_A[10][Tabla.Instr] = 13;
        Tabla.Ir_A[16][Tabla.E]     = 36;
        Tabla.Ir_A[16][Tabla.Factor]= 29;
        Tabla.Ir_A[17][Tabla.E]     = 40;
        Tabla.Ir_A[17][Tabla.Factor]= 29;
        Tabla.Ir_A[18][Tabla.Bloque]= 14;
        Tabla.Ir_A[18][Tabla.Instr] = 35;
        Tabla.Ir_A[22][Tabla.Tipo]  = 23;
        Tabla.Ir_A[27][Tabla.E]     = 28;
        Tabla.Ir_A[27][Tabla.Factor]= 29;
        Tabla.Ir_A[31][Tabla.Factor]= 46;
        Tabla.Ir_A[37][Tabla.Bloque]= 14;
        Tabla.Ir_A[37][Tabla.Instr] = 38;
        Tabla.Ir_A[41][Tabla.Bloque]= 14;
        Tabla.Ir_A[41][Tabla.Instr] = 42;
        Tabla.Ir_A[43][Tabla.Factor]= 29;
        Tabla.Ir_A[45][Tabla.V]     = 47; // jjuraria que esta mal
    }

    public void rellenarTablaAccion(){
        Tabla.Accion[1][Tabla.ALGORITMO]    = "d3";
        Tabla.Accion[2][Tabla.$]            = "aceptar";
        Tabla.Accion[3][Tabla.ID]           = "d4";
        Tabla.Accion[4][Tabla.PYC]          = "d5";
        Tabla.Accion[5][Tabla.VAR]          = "d8";
        Tabla.Accion[6][Tabla.VAR]          = "d8";
        Tabla.Accion[6][Tabla.BLQ]          = "d10";
        Tabla.Accion[7][Tabla.VAR]          = "r3";
        Tabla.Accion[7][Tabla.BLQ]          = "r3";
        Tabla.Accion[8][Tabla.ID]           = "d21";
        Tabla.Accion[9][Tabla.$]            = "r1";
        Tabla.Accion[10][Tabla.ID]          = "d15";
        Tabla.Accion[10][Tabla.BLQ]         = "d10";
        Tabla.Accion[10][Tabla.SI]          = "d16";
        Tabla.Accion[10][Tabla.MIENTRAS]    = "d17";
        Tabla.Accion[10][Tabla.ESCRIBIR]    = "d19";
        Tabla.Accion[11][Tabla.PYC]         = "d18";
        Tabla.Accion[11][Tabla.FBLQ]        = "d12";
        Tabla.Accion[12][Tabla.PYC]         = "r10";
        Tabla.Accion[12][Tabla.FBLQ]        = "r10";
        Tabla.Accion[12][Tabla.FSI]         = "r10";
        Tabla.Accion[13][Tabla.PYC]         = "r12";
        Tabla.Accion[13][Tabla.FBLQ]        = "r12";
        Tabla.Accion[14][Tabla.PYC]         = "r13";
        Tabla.Accion[14][Tabla.FBLQ]        = "r13";
        Tabla.Accion[14][Tabla.FSI]         = "r13";
        Tabla.Accion[15][Tabla.ASIG]        = "d43";
        Tabla.Accion[16][Tabla.ID]          = "d32";
        Tabla.Accion[16][Tabla.NENTERO]     = "d33";
        Tabla.Accion[16][Tabla.NREAL]       = "d34";
        Tabla.Accion[17][Tabla.ID]          = "d32";
        Tabla.Accion[17][Tabla.NENTERO]     = "d33";
        Tabla.Accion[17][Tabla.NREAL]       = "d34";
        Tabla.Accion[18][Tabla.ID]          = "d15";
        Tabla.Accion[18][Tabla.BLQ]         = "d10";
        Tabla.Accion[18][Tabla.SI]          = "d16";
        Tabla.Accion[18][Tabla.MIENTRAS]    = "d17";

        Tabla.Accion[19][Tabla.PARI]        = "d27";
        Tabla.Accion[19][Tabla.$]           = "r1";
        Tabla.Accion[20][Tabla.ID]          = "r6";
        Tabla.Accion[20][Tabla.VAR]         = "r6";
        Tabla.Accion[20][Tabla.BLQ]         = "r6";
        Tabla.Accion[21][Tabla.DOSP]        = "d22";
        Tabla.Accion[22][Tabla.ENTERO]      = "d25";
        Tabla.Accion[22][Tabla.REAL]        = "d26";
        Tabla.Accion[23][Tabla.PYC]         = "d24";
        Tabla.Accion[24][Tabla.ID]          = "r7";
        Tabla.Accion[24][Tabla.VAR]         = "r7";
        Tabla.Accion[24][Tabla.BLQ]         = "r7";
        Tabla.Accion[25][Tabla.PYC]         = "r8";
        Tabla.Accion[26][Tabla.PYC]         = "r8";
        Tabla.Accion[27][Tabla.ID]          = "d32";
        Tabla.Accion[27][Tabla.NENTERO]     = "d33";
        Tabla.Accion[27][Tabla.NREAL]       = "d34";
        Tabla.Accion[28][Tabla.PARD]        = "d30";
        Tabla.Accion[28][Tabla.OPAS]        = "d31";
        Tabla.Accion[29][Tabla.PYC]         = "r19";
        Tabla.Accion[29][Tabla.BLQ]         = "r19";
        Tabla.Accion[29][Tabla.ENTONCES]    = "r19";
        Tabla.Accion[29][Tabla.FSI]         = "r19";
        Tabla.Accion[29][Tabla.HACER]       = "r19";
        Tabla.Accion[29][Tabla.PARD]        = "r19";
        Tabla.Accion[29][Tabla.OPAS]        = "r19";

        Tabla.Accion[30][Tabla.PYC]         = "r17";
        Tabla.Accion[30][Tabla.FBLQ]        = "r17";
        Tabla.Accion[30][Tabla.FSI]         = "r17";
        Tabla.Accion[31][Tabla.ID]          = "d32";
        Tabla.Accion[31][Tabla.NENTERO]     = "d33";
        Tabla.Accion[31][Tabla.NREAL]       = "d34";
        Tabla.Accion[32][Tabla.PYC]         = "r20";
        Tabla.Accion[32][Tabla.BLQ]         = "r20";
        Tabla.Accion[32][Tabla.ENTONCES]    = "r20";
        Tabla.Accion[32][Tabla.FSI]         = "r20";
        Tabla.Accion[32][Tabla.HACER]       = "r20";
        Tabla.Accion[32][Tabla.PARD]        = "r20";
        Tabla.Accion[32][Tabla.OPAS]        = "r20";
        Tabla.Accion[33][Tabla.PYC]         = "r21";
        Tabla.Accion[33][Tabla.BLQ]         = "r21";
        Tabla.Accion[33][Tabla.ENTONCES]    = "r21";
        Tabla.Accion[33][Tabla.FSI]         = "r21";
        Tabla.Accion[33][Tabla.HACER]       = "r21";
        Tabla.Accion[33][Tabla.PARD]        = "r21";
        Tabla.Accion[33][Tabla.OPAS]        = "r21";
        Tabla.Accion[34][Tabla.PYC]         = "r22";
        Tabla.Accion[34][Tabla.BLQ]         = "r22";
        Tabla.Accion[34][Tabla.ENTONCES]    = "r22";
        Tabla.Accion[34][Tabla.FSI]         = "r22";
        Tabla.Accion[34][Tabla.HACER]       = "r22";
        Tabla.Accion[34][Tabla.PARD]        = "r22";
        Tabla.Accion[34][Tabla.OPAS]        = "r22";
        Tabla.Accion[35][Tabla.PYC]         = "r11";
        Tabla.Accion[35][Tabla.FBLQ]        = "r11";
        Tabla.Accion[36][Tabla.ENTONCES]    = "d37";
        Tabla.Accion[36][Tabla.OPAS]        = "d31";
        Tabla.Accion[37][Tabla.ID]          = "d15";
        Tabla.Accion[37][Tabla.BLQ]         = "d10";
        Tabla.Accion[37][Tabla.SI]          = "d16";
        Tabla.Accion[37][Tabla.MIENTRAS]    = "d17";
        Tabla.Accion[37][Tabla.ESCRIBIR]    = "d19";
        Tabla.Accion[38][Tabla.FSI]         = "d39";
        Tabla.Accion[39][Tabla.PYC]         = "r15";
        Tabla.Accion[39][Tabla.FBLQ]        = "r15";
        Tabla.Accion[39][Tabla.FSI]         = "r15";
        Tabla.Accion[40][Tabla.HACER]       = "d41";
        Tabla.Accion[40][Tabla.OPAS]        = "d31";
        Tabla.Accion[41][Tabla.ID]          = "d15";
        Tabla.Accion[41][Tabla.BLQ]         = "d10";
        Tabla.Accion[41][Tabla.SI]          = "d16";
        Tabla.Accion[41][Tabla.MIENTRAS]    = "d17";
        Tabla.Accion[41][Tabla.ESCRIBIR]    = "d19";
        Tabla.Accion[42][Tabla.PYC]         = "r16";
        Tabla.Accion[42][Tabla.FBLQ]        = "r16";
        Tabla.Accion[42][Tabla.FSI]         = "r16";
        Tabla.Accion[43][Tabla.ID]          = "d32";
        Tabla.Accion[43][Tabla.NENTERO]     = "d33";
        Tabla.Accion[43][Tabla.NREAL]       = "d34";
        Tabla.Accion[44][Tabla.PYC]         = "r14";
        Tabla.Accion[44][Tabla.FBLQ]        = "r14";
        Tabla.Accion[44][Tabla.FSI]         = "r14";
        Tabla.Accion[44][Tabla.OPAS]        = "d31";
        Tabla.Accion[45][Tabla.ID]          = "d21";
        Tabla.Accion[46][Tabla.PYC]         = "r22";
        Tabla.Accion[46][Tabla.BLQ]         = "r22";
        Tabla.Accion[46][Tabla.ENTONCES]    = "r22";
        Tabla.Accion[46][Tabla.FSI]         = "r22";
        Tabla.Accion[46][Tabla.HACER]       = "r22";
        Tabla.Accion[46][Tabla.PARD]        = "r22";
        Tabla.Accion[46][Tabla.OPAS]        = "r22";
        Tabla.Accion[47][Tabla.ID]          = "r5";
        Tabla.Accion[47][Tabla.VAR]         = "r5";
        Tabla.Accion[47][Tabla.BLQ]         = "r5";
        Tabla.Accion[48][Tabla.VAR]         = "r2";
        Tabla.Accion[48][Tabla.BLQ]         = "r2";

    }
}
