/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utility;

import java.io.Serializable;


public class Pacchetto implements Serializable {

    
    private int tipo;
    private Object oggetto;
    
    public Pacchetto () {}
    
    public int getTipo() {
           return tipo;
        }
    
    public void setTipo (int Tipo) {
           tipo = Tipo;
        }
    
    public Object getOggetto() {
           return oggetto;
        }
    
    public void setOggetto (Object obj) {
           oggetto = obj;
        }
   
    
}
