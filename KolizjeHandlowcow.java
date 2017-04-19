

package imperium;

import java.util.ConcurrentModificationException;

/**
 *
 *sprawdza czy handlowcy nie są zbyt blisko siebie i zmniejsza ich prędkość tak żeby nie dochodziło do kolizji

 */
public class KolizjeHandlowcow implements Runnable {
    
    public KolizjeHandlowcow ()  {
        
    new Thread(this).start();
    
    }

    @Override
    public void run() {
        while(true){
            try{
            
            
            Object[] listah=Imperium.getZbiorHandlowcow().toArray();
            for(Object h:listah){
                Handlowiec h2=(Handlowiec)h;
                for(Object h1:listah){
                
                Handlowiec h3=(Handlowiec)h1;
                if(h1.equals(h)){}else
                try{
                    
                if(h2.Odleglosc(h2, h3)<21){
                    if(h2.getPredkoscWozu()==h3.getPredkoscWozu()){}
                    else if(h2.getPredkoscWozu()>h3.getPredkoscWozu()){
                   // System.out.println(h3.getPredkoscWozu() +  " powinno zamienic się na " +h2.getPredkoscWozu()); 
                        h3.setPredkoscWozu(h2.getPredkoscWozu());
                   // System.out.println(h3.getPredkoscWozu());
                    }
                    else{
                     //   System.out.println(h2.getPredkoscWozu());    
                        h2.setPredkoscWozu(h3.getPredkoscWozu());
                       //System.out.println(h2.getPredkoscWozu());
                    }
                    
                    
                }
                }catch(NullPointerException e){};
                }
        
            }
            
      }catch(ConcurrentModificationException e){}
        }

    }
    
    
}
