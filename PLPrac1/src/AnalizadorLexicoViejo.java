import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AnalizadorLexicoViejo {
    public static int ERROR = -2;
    public static int FINAL = -1;
    public static int ESTADO_KEYWORD = -3;

    public static Map<Integer, Integer> bufferTable = new HashMap<>();
    public static Map<Integer, Integer> typeTable = new HashMap<>();

    RandomAccessFile entrada;
    List<Character> buffer;
    int columna_actual;
    int previous_col;
    int fila_actual;
    
    private void fillBufferTable(){
        bufferTable.put(5, 1);
        bufferTable.put(11, 1);
        bufferTable.put(15, 1);
        bufferTable.put(18, 1);
        bufferTable.put(22, 1);
        bufferTable.put(24, 1);
        bufferTable.put(29, 2);
        bufferTable.put(30, 2);
    }

    private void fillTypeTable(){
        typeTable.put(25,0);
        typeTable.put(1,1);
        typeTable.put(5,2);
        typeTable.put(4,2);
        typeTable.put(6,3);
        typeTable.put(9,4);
        typeTable.put(10,4);
        typeTable.put(11,4);
        typeTable.put(12,5);
        typeTable.put(15,6);
        typeTable.put(13,7);
        typeTable.put(16,8);
        //typeTable.put(X, 9); FALTAN PALABRAS CLAVE
        typeTable.put(24,23);
        typeTable.put(18,24);
        typeTable.put(28, 28);
        typeTable.put(29,24);
        typeTable.put(30,24);
        typeTable.put(22,25);
    }

    public AnalizadorLexicoViejo(RandomAccessFile entrada) {
        this.entrada = entrada;
        buffer = new ArrayList<Character>();
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
            previous_col = columna_actual;
            columna_actual = 1;
            
        }
        else{
            columna_actual++;
        }
    }

    public Token siguienteToken(){
        int estado = 0;
        String current_token = "";
        int fila_token = fila_actual;
        int col_token = columna_actual;
        char c = leerCaracter(entrada);
        System.out.println("CHAR LEIDO: " + c);
        
        
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
            System.out.println("Estado: " + estado + " | Nuevo estado: " + nuevoEstado);

            if(nuevoEstado == ERROR){
                errorLexico(c);
                return null;
            }
            if(nuevoEstado == FINAL){
                
                if(estado == 28){      // COMENTARIO
                    estado = 0;
                    current_token = "";
                    fileSeekBack();
                    fila_token = fila_actual;
                    col_token = columna_actual;
                    c = leerCaracter(entrada);
                    System.out.println("CHAR LEIDO: " + c);
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
                    if(estado == ESTADO_KEYWORD){
                        lexema = current_token;
                        estado = 24; // FIN DE IDENTIFICADOR PARA QUE COMPRUEBE PALABRAS CLAVE
                    }
                    else{
                        lexema = devolverChars(estado, current_token);
                    }

                    
                    toReturn.lexema = lexema;
                    toReturn.fila = fila_token;
                    toReturn.columna = col_token;
                    toReturn.tipo = getTipo(estado, lexema);
                    
    
                    fileSeekBack(); // para que se tenga en cuenta el que acaba de leer
                    
                    return toReturn;
                }

            }
            else{
                if(c != ' ' && c != '\n' && c != '\t')
                    current_token += c;

                estado = nuevoEstado;
                c = leerCaracter(entrada);
                System.out.println("CHAR LEIDO: " + c);
                
                // HECHO: Que ante un EOF trate los chars que lleva leidos
                // SI TERMINAS EN ESTADO DE TRATAR COMENTARIO: ERROR
                // DEFINIR ACCION PARA CADA ESTADO
                if(c == Token.EOF){
                    System.out.println("END OF FILE HEHE");
                    if(current_token.length() > 0){
                        Token toReturn = new Token();
                        String lexema = devolverChars(estado, current_token);
                        toReturn.lexema = lexema;
                        toReturn.fila = fila_token;
                        toReturn.columna = col_token;
                        toReturn.tipo = getTipo(estado, lexema);
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
                        return 9;
                    case "real":
                        return 10;
                    case "entero":
                        return 11;
                    case "algoritmo":
                        return 12;
                    case "blq":
                        return 13;
                    case "fblq":
                        return 14;
                    case "funcion":
                        return 15;
                    case "si":
                        return 16;
                    case "entonces":
                        return 17;
                    case "sino":
                        return 18;
                    case "fsi":
                        return 19;
                    case "mientras":
                        return 20;
                    case "hacer":
                        return 21;
                    case "escribir":
                        return 22;
                    default:
                        return 23;
                }
            }
            return typeTable.get(estado);
        }
        else{
            // HECHO: CAMBIAR ESTO
            //AHORA DEVUELVE EOF
            return 26;
        }
    }

    private String devolverChars(int nuevoEstado, String current_token) {
        if(!bufferTable.containsKey(nuevoEstado))   return current_token;
        else{
            
            int chars_to_buffer = bufferTable.get(nuevoEstado);
            String resultString = current_token.substring(0, current_token.length()-chars_to_buffer);

            System.out.println("current_token antes de quitar chars: " + current_token);
                
                for(int i=chars_to_buffer; i>0; i--){
                    fileSeekBack();
                    
                }
            return resultString;
        }

    }

private void fileSeekBack(){
    try {
        long file_pointer = entrada.getFilePointer();
        entrada.seek(file_pointer - 1);
        char prevChar = (char)entrada.read();
        System.out.println("PREV CHAR: " + prevChar);
        if(prevChar == ' ' || prevChar == '\t'){
            columna_actual -= 2;
            entrada.seek(file_pointer-2);
        }
        else if(prevChar == '\n'){
            fila_actual -= 1;
            columna_actual = previous_col-1;
            entrada.seek(file_pointer-2);
        }
        else{
            columna_actual--;
            entrada.seek(file_pointer-1);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}


private void errorLexico(char c) {
    System.out.println("Error lexico (" + fila_actual + "," + columna_actual + "): caracter \'" + c + "\' incorrecto");
    System.exit(-1);
}


    private int delta(int estado, char c, String lexema) {
        if( c == ' ' || c == '\n' || c == '\t')
            return estado; 

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
                if(Character.isAlphabetic(c))   return 23;
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
                int tipo = getTipo(24, lexema);
                if(tipo != 23){
                    return ESTADO_KEYWORD;                              // ESTADO DE KEYWORD
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
                else            return 26;
            case 28: return -1;
            case 29: return -1;
            case 30: return -1;
            default:
                return -2;
        
            
        }
        return -2;
    }
}
