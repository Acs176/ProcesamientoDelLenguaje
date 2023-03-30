import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class AnalizadorLexico {
    public static int ERROR = -2;
    public static int FINAL = -1;
    public static int ESTADO_KEYWORD = -3;
    public static int ESTADO_ID_ESPACIO = -4;

    public static Map<Integer, Integer> bufferTable = new HashMap<>();
    public static Map<Integer, Integer> typeTable = new HashMap<>();

    RandomAccessFile entrada;
    String current_token;
    List<Character> buffer;
    int columna_actual;
    LinkedList<Integer> previous_cols;
    int fila_actual;
    
    private void fillBufferTable(){
        bufferTable.put(5, 1);
        bufferTable.put(11, 1);
        bufferTable.put(15, 1);
        bufferTable.put(18, 1);
        bufferTable.put(22, 1);
        bufferTable.put(24, 1);
        bufferTable.put(25, 1);
        bufferTable.put(29, 2);
        bufferTable.put(30, 2);
    }

    private void fillTypeTable(){
        typeTable.put(25,0);
        typeTable.put(1,1);
        typeTable.put(5,2); // ojo
        typeTable.put(4,2); // ojo
        typeTable.put(6,2);
        typeTable.put(9,4); // ojo
        typeTable.put(10,4);// ojo
        typeTable.put(11,4);// ojo
        typeTable.put(12,3);
        typeTable.put(15,4); // ojo
        typeTable.put(13,7); // ojo
        typeTable.put(16,5);
        //typeTable.put(X, 9); FALTAN PALABRAS CLAVE
        typeTable.put(24,23);
        typeTable.put(ESTADO_ID_ESPACIO, 23);
        typeTable.put(18,24);
        typeTable.put(28, 28);
        typeTable.put(29,24);
        typeTable.put(30,24);
        typeTable.put(22,25);
    }

    public AnalizadorLexico(RandomAccessFile entrada) {
        this.entrada = entrada;
        buffer = new ArrayList<Character>();
        previous_cols = new LinkedList<>();
        fillBufferTable();
        columna_actual = 1;
        fila_actual = 1;
        fillTypeTable();
    }

    public char leerCaracter(RandomAccessFile entrada) {
        char currentChar;
        try {
            
            currentChar = (char)entrada.readByte();
            actualizarFilasCols(currentChar);
            return currentChar;
        }
        catch (EOFException e) {
            //TODO: ASEGURAR QUE DEVUELVE ALGO CON SENTIDO
            return Token.EOF;
        }
        catch (IOException e) {
        }
        return ' ';
    }

    //TODO: Tratar casos de token en buffer
    // metes char en el buffer, tienes que guardar su posicion para el token al que pertenece no?
    private void actualizarFilasCols(char c){
        if(c == '\n'){
            fila_actual++;
            previous_cols.addLast(columna_actual);
            columna_actual = 1;
            
        }
        else{
            columna_actual++;
        }
    }

    public Token siguienteToken(){
        int estado = 0;
        current_token = "";
        char c;
        int fila_token;
        int col_token;
        do{
            fila_token = fila_actual;
            col_token = columna_actual;

            c = leerCaracter(entrada);
        }while(c == ' ' || c == '\n' || c == '\t');
        //System.out.println("CHAR LEIDO: " + c);
        
        
        if(c == Token.EOF){
            Token eof = new Token();
            eof.lexema = "";
            eof.tipo = Token.EOF;
            return eof;
        }


        while(true){
                
            int nuevoEstado = delta(estado, c, current_token);
            if(nuevoEstado == ESTADO_KEYWORD){
                nuevoEstado = -1;
                estado = ESTADO_KEYWORD;        
            }
            //System.out.println("Estado: " + estado + " | Nuevo estado: " + nuevoEstado);

            if(nuevoEstado == ERROR){
                errorLexico(c, fila_token, col_token);
                return null;
            }
            if(nuevoEstado == FINAL){
                
                if(estado == 28){      // COMENTARIO
                    estado = 0;
                    current_token = "";
                    volverAtras();
                    fila_token = fila_actual;
                    col_token = columna_actual;
                    c = leerCaracter(entrada);
                    //System.out.println("CHAR LEIDO: " + c);
                    if(c == Token.EOF){
                        Token eof = new Token();
                        eof.lexema = "";
                        eof.tipo = Token.EOF;
                        return eof;
                    }
                }
                else{
                    Token toReturn = new Token();
                    String lexema;
                    if(estado == ESTADO_KEYWORD || estado == ESTADO_ID_ESPACIO){
                        lexema = current_token;
                        estado = 24; // FIN DE IDENTIFICADOR PARA QUE COMPRUEBE PALABRAS CLAVE
                        // cambia para el getTipo
                    }
                    else{
                        lexema = devolverChars(estado, current_token);
                    }

                    
                    toReturn.lexema = lexema;
                    toReturn.fila = fila_token;
                    toReturn.columna = col_token;
                    toReturn.tipo = getTipo(estado, lexema);
                    if(toReturn.tipo == 23){
                        toReturn.tipo = 18;
                    }
    
                    volverAtras(); // para que se tenga en cuenta el que acaba de leer
                    
                    return toReturn;
                }

            }
            else{
                if(c != ' ' && c != '\n' && c != '\t')
                    current_token += c;

                estado = nuevoEstado;
                c = leerCaracter(entrada);
                //System.out.println("CHAR LEIDO: " + c);
                
                // HECHO: Que ante un EOF trate los chars que lleva leidos
                // SI TERMINAS EN ESTADO DE TRATAR COMENTARIO: ERROR
                // DEFINIR ACCION PARA CADA ESTADO
                if(c == Token.EOF){
                    //System.out.println("END OF FILE HEHE");
                    if(estado == 26 || estado == 27){
                        // estamos en comentario
                        // LANZAR ERROR
                        System.err.println("Error lexico: fin de fichero inesperado");
                        System.exit(-1);
                    }
                    if(current_token.length() > 0 && estado != 28){
                        Token toReturn = new Token();
                        String lexema = devolverChars(estado, current_token);
                        toReturn.lexema = lexema;
                        toReturn.fila = fila_token;
                        toReturn.columna = col_token;
                        //System.out.println("ESTADO AQUI " + estado);
                        if(estado == ESTADO_KEYWORD || estado == ESTADO_ID_ESPACIO){
                            estado = 24;
                            // para el getTipo
                        }
                        toReturn.tipo = getTipo(estado, lexema);
                        if(toReturn.tipo == 23){
                            toReturn.tipo = 18;
                        }
                        //System.out.println("TIPO DESPUES " + toReturn.tipo);
                        return toReturn;
                    }
                    else{
                        Token eof = new Token();
                        eof.lexema = "";
                        eof.tipo = Token.EOF;
                        return eof;
                    }
                }

            }
        }



    }


    private int getTipo(int estado, String lexema) {
        if(typeTable.containsKey(estado)){
            if(estado == 24){
                switch(lexema){
                    case "var":
                        return 6;
                    case "real":
                        return 7;
                    case "entero":
                        return 8;
                    case "algoritmo":
                        return 9;
                    case "blq":
                        return 10;
                    case "fblq":
                        return 11;
                    case "si":
                        return 12;
                    case "entonces":
                        return 13;
                    case "fsi":
                        return 14;
                    case "mientras":
                        return 15;
                    case "hacer":
                        return 16;
                    case "escribir":
                        return 17;
                    default:
                        return 18;
                }
            }
            return typeTable.get(estado);
        }
        else{
            // HECHO: CAMBIAR ESTO
            //AHORA DEVUELVE EOF
            return 21;
        }
    }

    private String devolverChars(int nuevoEstado, String current_token) {
        if(!bufferTable.containsKey(nuevoEstado))   return current_token;
        else{
            
            int chars_to_buffer = bufferTable.get(nuevoEstado);
            String resultString = current_token.substring(0, current_token.length()-chars_to_buffer);

            //System.out.println("current_token antes de quitar chars: " + current_token);
                
                for(int i=chars_to_buffer; i>0; i--){
                    volverAtras();

                }
            return resultString;
        }

    }

private void volverAtras(){
    char prevChar;
    do{
        prevChar = fileSeekBack();
        //System.out.println("TIRA PATRAS");
    }while( prevChar == ' ' || prevChar == '\t' || prevChar == '\n' );
}

private char fileSeekBack(){
    try {
        long file_pointer = entrada.getFilePointer();
        entrada.seek(file_pointer - 1);
        char prevChar = (char)entrada.readByte();
        //System.out.println("PREV CHAR: " + prevChar);
        if(prevChar == ' ' || prevChar == '\t'){
            
            columna_actual -= 1;
            entrada.seek(file_pointer-1);
        }
        else if(prevChar == '\n'){
            
            fila_actual -= 1;
            columna_actual = previous_cols.getLast();
            previous_cols.removeLast();
            entrada.seek(file_pointer-1);
        }
        else{
            columna_actual--;
            entrada.seek(file_pointer-1);
        }
        return prevChar;
    } catch (IOException e) {
        e.printStackTrace();
        return 'F';
    }
}


private void errorLexico(char c, int fila_token, int col_token) {
    System.err.println("Error lexico (" + fila_token + "," + col_token + "): caracter \'" + c + "\' incorrecto");
    System.exit(-1);
}


    private int delta(int estado, char c, String lexema) {
        if( c == ' ' || c == '\n' || c == '\t'){
            if(estado != 23)
                return estado; // quizas hay que usar el estado ESPACIO_ID 
            else{
                return ESTADO_ID_ESPACIO;
            }
        }


        switch(estado){
            case 0:
                if(c == ')')    return 1;
                if(c == '(')    return 2;
                if(c == '*')    return 4;
                if(c == '/')    return 3;
                if(c == '<')    return 7;
                if(c == '>')    return 8;
                if(c == '+')    return 6;
                if(c == '-')    return 6;
                if(c == '=')    return 9;
                if(c == ';')    return 12;
                if(c == ',')    return 13;
                if(c == ':')    return 14;
                if(Character.isDigit(c))        return 17;
                if(Character.isAlphabetic(c))   return 23; // estado 23
                break;
            case 1: return -1; // final
            case 2:
                if(c == '*')    return 26;
                else            return 25;
            case 3:
                if(c == '/')    return 4;
                else            return 5;
            case 4: return -1;
            case 5: return -1;
            case 6: return -1;
            case 7:
                if(c == '>' || c == '=')    return 10;
                else            return 11;
            case 8:
                if(c == '=')    return 10;
                else            return 11;
            case 9: return -1;
            case 10: return -1;
            case 11: return -1;
            case 12: return -1;
            case 13: return -1;
            case 14:
                if(c == '=')    return 16;
                else            return 15;
            case 15: return -1;
            case 16: return -1;
            case 17:
                if(Character.isDigit(c))    return 17;
                if(c == '.')   return 20;              // ELIMINADO EL ESTADO 19
                else            return 18;
            case 18: return -1;
            case 19:
                if(c == '.')    return 20;
                else            return 29; // ERROR
            case 20:
                if(Character.isDigit(c))    return 21;
                else                        return 30;
            case 21:
                if(Character.isDigit(c))    return 21;
                else                        return 22;
            case 22: return -1;
            case 23:
                // checkear si tenemos keyword
                int tipo = checkKeyword(24, lexema, c);
                if(tipo != 23){
                    return tipo;                              // ESTADO DE KEYWORD
                }
                if(Character.isAlphabetic(c) || Character.isDigit(c))
                    return 23;
                else
                    return 24;
            case 24: return -1;
            case 25: return -1;
            case 26:
                if(c == '*')    return 27;
                else            return 26;
            case 27:
                if(c == ')')    return 28;
                if(c == '*')    return 27;
                else            return 26;
            case 28: return -1;
            case 29: return -1;
            case 30: return -1;
            case -4: return -1;
            default:
                return -2;
        
            
        }
        return -2;
    }

    public int checkKeyword(int estado, String lexema, char char_leido){
        int contador_chars = lexema.length();
        int tipo = 23;
        for(int i=0; i<contador_chars; i++){
            String posible_keyword = lexema.substring(i, lexema.length());
            tipo = getTipo(estado, posible_keyword);
            if(tipo != 23){
                // caso especial SI
                if(tipo == 16){
                    //System.out.println("TENEMOS UN SI: LEXEMA " + lexema);
                    // leer 2 chars más
                    char c1 = leerCaracter(entrada);
                    //System.out.println("c1 " + char_leido + " c2 " + c1);
                    boolean sino = false;
                    if(char_leido == 'n' && c1 == 'o'){
                        // NO HACEMOS NADA
                        sino = true;
                    }
                    fileSeekBack();

                    if(!sino){
                        return ESTADO_KEYWORD;
                    }
                    else{
                        // HAY UN SINO, POR LO QUE SEGUIMOS LEYENDO
                        return 23;
                    }
                }

                if(i==0)
                    return ESTADO_KEYWORD;
                else{
                    //tirar atras y devolver estado de ID
                    int chars_atras = posible_keyword.length();

                    for(int j=0; j<chars_atras; j++){
                        volverAtras();
                    }
                    current_token = current_token.substring(0, current_token.length()-posible_keyword.length());
                    //System.out.println("ELIMINADA LA KEYWORD: " + current_token);
                    return 24; // Tenemos el identificador
                }
            }
        }

        return tipo;
    }
}
