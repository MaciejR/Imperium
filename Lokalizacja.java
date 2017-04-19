package imperium;

import java.awt.Image;

/**
 * Klasa Lokalizacja
 */
public class Lokalizacja  {

 
//współrzędne na mapie
  private int x;
  private int y;
  private Image obraz;//grafika
  private Ikonka ikonka;
  private boolean doUsuniecia=false;

  
  
  //
  // Constructors
  //
  public Lokalizacja ()  {
   while(doUsuniecia)usun();
  Image obrazwk;
 
  };
  /**
 * Obliczenie odległości pomiędzy lokalizacjami
 */
  
    public double Odleglosc(Lokalizacja l1,Lokalizacja l2){
   
   try{
    double x = l1.getWartoscX()-l2.getWartoscX(), y = l1.getWartoscY()-l2.getWartoscY();
    return Math.sqrt(x*x+y*y);
    }catch(Exception e){//wyjątek występował gdy 2 żołnierze jednocześnie byli blisko jednego barbarzyncy i obliczali odległość do niego
   return 100;
    }
    /**
 * sprawdza jaka jest najbliższa Osada od danego miejsca, 
 * musiałęm tą funkcję zastosować przy ściganiu Barbarzynców 
 * gdyż przez manualną obsługę skrzyżowań w innym wypadku często 
 * zawieszał się wątek Zołnierza
 */
  
  }
  public Lokalizacja najblizszaOsada(Lokalizacja l){
    Lokalizacja m;
      try{
    m=Imperium.getListaOsad().get(Imperium.getRandom().nextInt(Imperium.getListaOsad().size()));
    
    while((m.getWartoscX()==l.getWartoscX())&&(m.getWartoscY()==l.getWartoscY())) m=Imperium.getListaOsad().get(Imperium.getRandom().nextInt(Imperium.getListaOsad().size()-1));  
    for(Osada o:Imperium.getListaOsad()){
         // if(o.equals(l)) continue;
          while((Odleglosc(o,this)<Odleglosc(m, this))&&((o.getWartoscX()!=l.getWartoscX())&&(o.getWartoscY()!=l.getWartoscY()))) m=o;
      }
    }catch(NullPointerException e){
    m=Imperium.getListaOsad().get(Imperium.getRandom().nextInt(Imperium.getListaOsad().size()));
    for(Osada o:Imperium.getListaOsad()){
         // if(o.equals(l)) continue;
          while((Odleglosc(o,this)<Odleglosc(m, this))) m=o;
      }
    }
    
    
    return m;
    
  }
  /**
 *wyszukuje najbliższego rywala dla żołnierza
 */
   public Lokalizacja najblizszyBarbarzynca(){
    Lokalizacja m=Imperium.getListaBarbarzyncow().get(0);
      for(Barbarzynca o:Imperium.getListaBarbarzyncow()){
          while(Odleglosc(o,this)<Odleglosc(m, this)) m=o;
      }
    return m;
    
  }
  
 
  public void setX ( int newVar ) {
    x = newVar;
  }

  /**
   * Get the value of x
   * @return the value of x
   */
  public int getWartoscX ( ) {
    return x;
  }

  /**
   * Set the value of y
   * @param newVar the new value of y
   */
  public void setY ( int newVar ) {
    y = newVar;
  }

  /**
   * Get the value of y
   * @return the value of y
   */
  public int getWartoscY ( ) {
    return y;
  }

  /**
   * Set the value of obraz
   * @param newVar the new value of obraz
   */
  public void setObraz (Image newVar ) {
    obraz = newVar;
  }

  /**
   * Get the value of obraz
   * @return the value of obraz
   */
  public Image getObraz ( ) {
    return obraz;
  } 
 /* public void rysuj(Tlo tloImperialne){
      
        //tloImperialne.add(obraz.getGraphics());
  
  }*/

    /**
     * @return the ikonka
     */
    public Ikonka getIkonka() {
        return ikonka;
    }

    /**
     * @param ikonka the ikonka to set
     */
    public void setIkonka(Ikonka ikonka) {
        this.ikonka = ikonka;
    }

    /**
     * @return the doUsuniecia
     */
    public boolean isDoUsuniecia() {
        return doUsuniecia;
    }

    /**
     * @param doUsuniecia the doUsuniecia to set
     */
    public void setDoUsuniecia(boolean doUsuniecia) {
        this.doUsuniecia = doUsuniecia;
    }
 /**
  * usuwawnie ikonki
  */
     public void usun(){
     getIkonka().setVisible(false);
     Imperium.getPanelImperium().remove(getIkonka());
     
     
    
    };
}
