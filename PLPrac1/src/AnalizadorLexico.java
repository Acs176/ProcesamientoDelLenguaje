import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AnalizadorLexico {
    public static int ERROR = -2;
    public static int FINAL = -1;

    public static Map<Integer, Integer> bufferTable = new HashMap<>();
    public static Map<Integer, Integer> typeTable = new HashMap<>();

    RandomAccessFile entrada;
    List<Character> buffer;
    int columna_actual;
    int fila_actual;
    
    private void fillBufferTable(){
        bufferTable.put(5, 1);
        bufferTable.put(11, 1);
        bufferTable.put(15, 1);
        bufferTable.put(18, 1);
        bufferTable.put(22, 1);
        bufferTable.put(24, 1);
        bufferTable.put(29, 2);
        bufferTable.put(30, 3);
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
        typeTable.put(29,24);
        typeTable.put(30,24);
        typeTable.put(22,25);
        // FALTA EOF
    }

    public AnalizadorLexico(RandomAccessFile entrada) {
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
            if(buffer.size() > 0){
                currentChar = buffer.get(0);
                buffer.remove(0);
                return currentChar;
            }

            currentChar = (char)entrada.readByte();
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

    private void actualizarFilasCols(char c){
        if(c == '\n'){
            fila_actual++;
            columna_actual = 1;
        }
        else{
            columna_actual++;
        }
    }

    public Token siguienteToken(RandomAccessFile entrada){

        int estado = 0;
        String current_token = "";

        char c = leerCaracter(entrada);
        System.out.println("CHAR LEIDO: " + c);
        actualizarFilasCols(c); //TODO: QUE NO ACTUALICE AL LEER DE BUFFER
        if(c == Token.EOF)
            return null;

        while(true){
                
            int nuevoEstado = delta(estado, c);
            System.out.println("Estado: " + estado + " | Nuevo estado: " + nuevoEstado);

            if(nuevoEstado == ERROR){
                errorLexico();
                return null;
            }
            if(nuevoEstado == FINAL){
                
                Token toReturn = new Token();
                String lexema = devolverChars(estado, current_token);
                toReturn.lexema = lexema;
                toReturn.fila = fila_actual;
                toReturn.columna = columna_actual;
                toReturn.tipo = getTipo(estado);
                

                buffer.add(c); // para que se tenga en cuenta el que acaba de leer
                //TODO: FILA Y COLUMNAS CON LAS QUE EMPIEZA EL TOKEN
                return toReturn;
            }
            else{
                if(c != ' ' && c != '\n' && c != '\t')
                    current_token += c;

                estado = nuevoEstado;
                c = leerCaracter(entrada);
                System.out.println("CHAR LEIDO: " + c);
                actualizarFilasCols(c);
                
                // TODO: Que ante un EOF trate los chars que lleva leidos
                // SI TERMINAS EN ESTADO DE TRATAR COMENTARIO: ERROR
                // DEFINIR ACCION PARA CADA ESTADO
                if(c == Token.EOF)
                    break;

            }
        }

        return null;

    }

    private int getTipo(int estado) {
        if(typeTable.containsKey(estado)){
            return typeTable.get(estado);
        }
        else{
            // TODO: CAMBIAR ESTO
            return 9;
        }
    }

    private String devolverChars(int nuevoEstado, String current_token) {
        if(!bufferTable.containsKey(nuevoEstado))   return current_token;
        else{
            
            int chars_to_buffer = bufferTable.get(nuevoEstado);
            String resultString = current_token.substring(0, current_token.length()-chars_to_buffer);

            System.out.println("UNA POLLA: " + current_token);
            for(int i=chars_to_buffer; i>0; i--){
                char charPalBuffer = current_token.charAt(current_token.length()-i);
                buffer.add(charPalBuffer);
                
                System.out.println("CHAR PAL BUFFER: " + charPalBuffer);
            }
            
            return resultString;
        }

    }

    private void errorLexico() {
        System.out.println("ERROR LEXICO (FALTA TERMINAR)");
    }

    private int delta(int estado, char c) {
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
                if(c == '\\')   return 19;              // COMPROBAR PORQUE PINTA RARO
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
