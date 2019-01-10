package utility;


import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;




/**
 *
 * @author salvatorenitopi
 */
public class Tools {

    
    public static void scrivi (javax.swing.JTextArea text, String data){
        
        text.append("\n-");
        text.append(data);
        
        text.setCaretPosition(text.getDocument().getLength());
        
    }
    
    public static void colora (javax.swing.JLabel etichetta, int r, int g, int b){
        
        etichetta.setForeground(new java.awt.Color(r, g, b));
 
    }

    
    public static String getData () {
        DateFormat formatodata = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date data = new Date();
        return formatodata.format(data);
    }
     
    public static int boolToInt (boolean valore){
        
        int ritorno = valore ? 1 : 0;
        return ritorno;
        
    }
    
    public static boolean stringaNumero(String stringa) {
        try {
            Integer.parseInt(stringa);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
   

     
}

