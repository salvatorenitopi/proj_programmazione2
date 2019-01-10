

package utility;

import java.io.Serializable;




public class Dati implements Serializable  {

        private boolean accensione;
        private boolean allarme;
        private boolean blocco;
        
        private int kmTotali;
        private int carburante;
        private int gomme;
        private int olio;
        private int batteria;
        
        private String latitudine;
        private String longitudine;
     
        public Dati () {}

        public boolean getAccensione() {
           return accensione;
        }

        public void setAccensione(boolean Accensione) {
           accensione = Accensione;
        }
        
        public boolean getAllarme() {
           return allarme;
        }

        public void setAllarme(boolean Allarme) {
           allarme = Allarme;
        }
        
        public boolean getBlocco() {
           return blocco;
        }

        public void setBlocco(boolean Blocco) {
           blocco = Blocco;
        }
        
        public int getKmTotali() {
           return kmTotali;
        }

        public void setKmTotali (int KmTotali) {
           kmTotali = KmTotali;
        }
        
        public int getCarburante() {
           return carburante;
        }

        public void setCarburante (int Carburante) {
           carburante = Carburante;
        }
        
        public int getGomme() {
           return gomme;
        }

        public void setGomme (int Gomme) {
           gomme = Gomme;
        }
        
        public int getOlio() {
           return olio;
        }

        public void setOlio (int Olio) {
           olio = Olio;
        }
        
        public int getBatteria() {
           return batteria;
        }

        public void setBatteria (int Batteria) {
           batteria = Batteria;
        }
        
        public String getLatitudine() {
           return latitudine;
        }

        public void setLatitudine (String Latitudine) {
           latitudine = Latitudine;
        }
        
        public String getLongitudine() {
           return longitudine;
        }

        public void setLongitudine (String Longitudine) {
           longitudine = Longitudine;
        }
        
        
    }