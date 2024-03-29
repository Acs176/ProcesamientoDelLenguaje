
public class Tabla {
    public static final int
    ALGORITMO = 0,
    ID = 1,
    PYC = 2,
    VAR = 3,
    DOSP = 4,
    ENTERO = 5,
    REAL = 6,
    BLQ = 7,
    FBLQ = 8,
    ENTONCES = 9,
    SI = 10,
    FSI = 11,
    MIENTRAS = 12,
    HACER = 13,
    ESCRIBIR = 14,
    ASIG = 15,
    PARI = 16,
    PARD = 17,
    OPAS = 18,
    NENTERO = 19,
    NREAL = 20,
    $ = 21,
    S = 22,
    Vsp = 23,
    Unsp = 24,
    LV = 25,
    V = 26,
    Tipo = 27,
    Bloque = 28,
    SInstr = 29,
    Instr = 30,
    E = 31,
    Factor = 32;

    public static String Accion[][];
    public static int Ir_A[][];

    public static int longitudParteDerecha(int regla){
       switch(regla){
        case 1:
            return 5;
        case 2:
            return 2;
        case 3:
            return 1;
        case 4:
            return 2;
        case 5:
            return 2;
        case 6:
            return 1;
        case 7:
            return 4;
        case 8:
            return 1;
        case 9:
            return 1;
        case 10:
            return 3;
        case 11:
            return 3;
        case 12:
            return 1;
        case 13:
            return 1;
        case 14:
            return 3;
        case 15:
            return 5;
        case 16:
            return 4;
        case 17:
            return 4;
        case 18:
            return 3;
        case 19:
            return 1;
        case 20:
            return 1;
        case 21:
            return 1;
        case 22:
            return 1;
        default:
            return -1;
       } 
    }

    public static int parteIzq(int regla){
        switch(regla){
            case 1:
                return S;
            case 2:
                return Vsp;
            case 3:
                return Vsp;
            case 4:
                return Unsp;
            case 5:
                return LV;
            case 6:
                return LV;
            case 7:
                return V;
            case 8:
                return Tipo;
            case 9:
                return Tipo;
            case 10:
                return Bloque;
            case 11:
                return SInstr;
            case 12:
                return SInstr;
            case 13:
                return Instr;
            case 14:
                return Instr;
            case 15:
                return Instr;
            case 16:
                return Instr;
            case 17:
                return Instr;
            case 18:
                return E;
            case 19:
                return E;
            case 20:
                return Factor;
            case 21:
                return Factor;
            case 22:
                return Factor;
            default:
                return 0;
        }
    }
}
