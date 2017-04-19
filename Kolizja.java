/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package imperium;

import java.util.*;
/**
 *
 * sprawdzanie czy Barbarzynca jest w pobliżu Handlowca lub Zołnierza
 */

public class Kolizja implements Runnable {
    
    public Kolizja ()  {
        
    new Thread(this).start();
    
    }
    
    
    @Override
    public void run() {
       //
        while(true){
            try{
            Object[] listab=Imperium.getListaBarbarzyncow().toArray();
           
            for(Object b:listab){
            //Kolizje z Handlowcami i usuwanie ich
            Object[] listah=Imperium.getZbiorHandlowcow().toArray();
            for(Object h:listah){
                Barbarzynca b1=(Barbarzynca)b;
                Handlowiec  h1=(Handlowiec)h;
                try{
                if(b1.Odleglosc(b1, h1)<31){
                    h1.usun();
                    Imperium.getZbiorHandlowcow().remove(h1);
                }
                }catch(NullPointerException e){};
            
            } 
            //Kolizje z Żołnierzami i usuwanie obu stron konfliktu
            Object[] listaz=Imperium.getListaZolnierzy().toArray(); 
            for(Object z:listaz){
                Barbarzynca b1=(Barbarzynca)b;
                Zolnierz z1=(Zolnierz)z;
                try{
                if(b1.Odleglosc(b1, z1)<31){
                b1.usun();
                Imperium.getListaBarbarzyncow().remove(b1);
                z1.usun();
                Imperium.getListaZolnierzy().remove(z1);
                }
                }catch(NullPointerException e){};
            
            }
        
        
            }
      }catch(ConcurrentModificationException e){}
        }

    }
}
