import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.io.IOException;


public class mainPruebas {
    public static void main(String[] args){
        if (args.length == 1)
        {
          try { //TODO: Cuando llega a EOF que guarde lo que lleva como token
            RandomAccessFile entrada = new RandomAccessFile(args[0],"r");
            AnalizadorLexico al = new AnalizadorLexico(entrada);
            Token t;
            do{
            t = al.siguienteToken(entrada);
            System.out.println("Token extraido: " + t.lexema);
            System.out.println("Fila, columna: " + t.fila + ", " + t.columna);
            System.out.println("TIPO TOKEN: " + Token.nombreToken.get(t.tipo));
            }while(t != null);
          }
          catch (FileNotFoundException e) {
            System.out.println("Error, fichero no encontrado: " + args[0]);
          }
        } 
        else System.out.println("Error, uso: java plp1 <nomfichero>");
    }
}
