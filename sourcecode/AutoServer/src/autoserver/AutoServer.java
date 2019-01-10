/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package autoserver;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import utility.Dati;
import utility.Pacchetto;
import utility.Tools;

public class AutoServer {

   
    private static boolean accensione = false;
    private static boolean allarme = false;
    private static boolean blocco = false;
        
    private static int kmTotali = 0;
    private static int carburante = 0;
    private static int gomme = 0;
    private static int olio = 0;
    private static int batteria = 0;
        
    private static String latitudine = "0";
    private static String longitudine = "0";
    
    private static int numeroClient = 0;

    
    ServerSocket serverSocket;
    Socket socket;
    
    
    
    Thread insersciCronologiaSQL = new InsersciCronologiaSQL();

    
    GraficaServer grafica = new GraficaServer();
    
    public static void main(String[] args) {
        
        AutoServer server = new AutoServer();
        
        server.connetti ();
        
    }
    
    public static boolean getAccensione (){
        return accensione;
    }
    
    public static boolean getAllarme (){
        return allarme;
    }
    
    public static boolean getBlocco (){
        return blocco;
    }
    
    public static int getKmTotali (){
        return kmTotali;
    }
    
    public static int getCarburante (){
        return carburante;
    }
    
    public static int getGomme (){
        return gomme;
    }
    
    public static int getOlio (){
        return olio;
    }
    
    public static int getBatteria (){
        return batteria;
    }
 
    public static String getLatitudine (){
        return latitudine;
    }
    
    public static String getLongitudine (){
        return longitudine;
    }
    
    public static int getNumeroClient (){
        return numeroClient;
    }
    
    
    private void connetti (){
        try{
            
            insersciCronologiaSQL.start ();
            serverSocket=new ServerSocket(11111);
           
            System.out.println ("In attesa di un client sulla porta: 6789...");
            
            grafica.setVisible (true);
            grafica.setConsole ("In attesa di un client sulla porta: 6789...");
            
            while (true){
                socket=serverSocket.accept();
                Thread connesso = new Connesso(socket);
                connesso.start();
            }
            
        }catch(Exception e){
            System.out.println (e);
            grafica.setConsole ("Errore nell'hosting del server.");
        }
    }
    
    private class Connesso extends Thread{
        
        private String identità = "";
          
        private Socket s;
        private Connesso(Socket s) {
            this.s = s;
        }

        
        @Override
        public void run(){
            Pacchetto ricevuto = new Pacchetto ();
            try{
                
                grafica.setConsole ("Client connesso su -> 11111.");
                numeroClient ++;
                
                ObjectInputStream inOBJ;
                ObjectOutputStream outOBJ;
                

                inOBJ = new ObjectInputStream(s.getInputStream());
                outOBJ = new ObjectOutputStream(s.getOutputStream());
                
                messaggioServer ("Benvenuto.", outOBJ,1);
                
                boolean connected = true;

                while (connected){

                    ricevuto = (Pacchetto)inOBJ.readObject();
                    
                    int tipoPacchetto = ricevuto.getTipo();
                    // 0 = Disconnetti
                    // 1 = Messaggio
                    // 2 = Dati
                    
                    // 9 = Identità
                    
                    switch (tipoPacchetto) {
                    
                        case 0: // Testo

                                connected = false;
                                grafica.setConsole (identità+": Client disconnesso.");
                                numeroClient --;
                            break;


                        case 1:  // Testo

                                String mess = "";

                                mess = (String) ricevuto.getOggetto();

                                if (mess.equals("CS01")){
                                    grafica.setConsole (identità+": Richiesta aggiornamento.");
                                    oggettoServer (outOBJ);
                                    
                                } else
                                    if (mess.equals("CS02")){
                                        grafica.setConsole (identità+": Richiesta blocco.");
                                        blocco = true;

                                    } else
                                        if (mess.equals("CS03")){
                                            grafica.setConsole (identità+": Richiesta sblocco.");
                                            blocco = false;


                                        } else
                                            if (mess.equals("CS04")){
                                                grafica.setConsole (identità+": Richiesta spegnimento.");
                                                accensione = false;
                                                
                                            } else {
                                                    grafica.setDisplay (identità+": "+mess);
                                                }
                                
                                if (allarme){
                                    messaggioServer ("TENTATIVO DI FURTO", outOBJ,1);
                                }
                                
                            break;



                        case 2:  // Dati
                                

                                Dati dati = new Dati ();

                                dati = (Dati) ricevuto.getOggetto();

                                accensione = dati.getAccensione();
                                allarme = dati.getAllarme();
                                blocco = dati.getBlocco();

                                kmTotali = dati.getKmTotali();
                                carburante = dati.getCarburante();
                                gomme = dati.getGomme();
                                olio = dati.getOlio();
                                batteria = dati.getBatteria();

                                latitudine = dati.getLatitudine();
                                longitudine = dati.getLongitudine();

                                grafica.setConsole (identità+": Dati ricevuti.");

                            break;
                            
                         
                        case 9: // Identità
                                
                            identità = (String) ricevuto.getOggetto();
                            
                            break;
                            
                            
                                
                    }

                }
                
                
            } catch(Exception e){
                    System.out.println (e);
                    grafica.setConsole (identità+": Errore CLIENT.");
                }
            
        }
    }
    

 
    private void messaggioServer (String mess, ObjectOutputStream out, int tipo) {

        try
        {
            Pacchetto invia = new Pacchetto ();
            
            invia.setTipo(tipo);
            invia.setOggetto (mess);
            
            
            out.writeObject(invia);
            out.flush();
            grafica.setConsole ("Messaggio Inviato ("+mess+")");
         } catch(Exception e){
             System.out.println (e);
            grafica.setConsole ("Errore invio messaggio.");
         }
        
    }
    
   
    
    
    private void oggettoServer (ObjectOutputStream out) {

        try
        {
            

            Dati dati = new Dati ();

            dati.setAccensione(accensione);
            dati.setAllarme(allarme);
            dati.setBlocco(blocco);
            
            dati.setKmTotali(kmTotali);
            dati.setCarburante(carburante);
            dati.setGomme(gomme);
            dati.setOlio(olio);
            dati.setBatteria(batteria);
            
            dati.setLatitudine(latitudine);
            dati.setLongitudine(longitudine);
            
            
            
            Pacchetto invia = new Pacchetto ();
            
            invia.setTipo(2);
            invia.setOggetto (dati);
            
            
            out.writeObject(invia);
            out.flush();
            
                
                grafica.setConsole ("Aggiornamento inviato.");
         } catch(Exception e){
             System.out.println (e);
         }
        
    }
    

    
    
    private class InsersciCronologiaSQL extends Thread{
        @Override
        public void run (){
            
            while (true){
         
                
                    try {
                            Class.forName("com.mysql.jdbc.Driver");
                            Connection conn = DriverManager.getConnection("jdbc:mysql://"+grafica.getIp()+"/AutoMonitoring?" + 
                                    "user="+grafica.getUsername()+"&"+grafica.getPassword()+"=");
                           
                            PreparedStatement pstmt;
                           


                            // inserisco i record
                            pstmt = conn.prepareStatement("INSERT INTO Cronologia " +
                                    "(data, avvioMotore, blocco, allarme, latitudine, longitudine) values (?,?,?,?,?,?)");


                            pstmt.setString(1, Tools.getData());

                            pstmt.setString(2, Integer.toString(Tools.boolToInt(accensione)));
                            pstmt.setString(3, Integer.toString(Tools.boolToInt(blocco)));
                            pstmt.setString(4, Integer.toString(Tools.boolToInt(allarme)));
                            pstmt.setString(5, latitudine);
                            pstmt.setString(6, longitudine);

                            pstmt.execute();


                            pstmt.close(); // rilascio le risorse
                                        
                            conn.close(); // termino la connessione
                            grafica.setConsole ("InserimentoSQL eseguito ("+Integer.toString(Tools.boolToInt(accensione))
                                    +","+Integer.toString(Tools.boolToInt(blocco))+
                                    ","+Integer.toString(Tools.boolToInt(allarme))+ ").");
                        }
                        catch(ClassNotFoundException e)
                        {
                            grafica.setConsole ("ERRORE INSERIMENTO SQL.");
                            System.out.println (e);
                        }
                        catch(SQLException e)
                        {
                            grafica.setConsole ("ERRORE INSERIMENTO SQL.");
                            System.out.println (e);
                        
                        }


                    try {
                        Thread.sleep (5000);
                    } catch (Exception e) {
                        System.out.println (e);
                    }
                
                
            }
        
        }

    }

 
}

