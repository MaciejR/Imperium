package imperium;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Klasa licząca czas rozgrywki, również wątek
 */
public class Zegar implements Runnable {
private int czas;

  public Zegar () { 
      
      
      
      new Thread(this).start();
      
  };
  


    @Override
    public void run() {
       ArrayList<Surowce> Sur=Imperium.getListaSurowcow();
        while(!Imperium.isKoniec()){//dopóki nie ma konca gry liczony jest czas
        czas++;
        Imperium.getInfo().setTekst18("Dzien "+ czas);
        if(czas%10==Imperium.losowa(0, 10))Imperium.atakBarbarzyncow();//co losowy czas atakują barbarzyncy
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Zegar.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        try{    
        for(Surowce s:Sur){//aktualizowanie listy surowców poprzez usuwanie zbyt starych
        if(czas-s.getDataProdukcji()>20)s.usun();
        }
        
         Imperium.setListaSurowcow(Sur);
        }catch(ConcurrentModificationException e){
        }catch(NullPointerException e){};
        
        //Konczenie gry gdy liczba ludnosci wyniesie 0
        if(Imperium.lacznaLiczbaLudnosci()==0){Imperium.koniecGry();}
        
        }
        
    }

    /**
     * @return the czas
     */
    public int getCzas() {
        return czas;
    }

    /**
     * @param czas the czas to set
     */
    public void setCzas(int czas) {
        this.czas = czas;
    }
    
  

}