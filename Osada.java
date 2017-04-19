package imperium;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Klasa Osady, będąca jednocześnie wątkiem
 */
public class Osada extends Lokalizacja implements Runnable{

  private String nazwa;
  private int ludnosc=2000;
  private int stanKonta=5000;
 private ArrayList<Surowce>  kupowanySurowiec;//jest to lista surówców jednego typu ale o różnych ID
 private  ArrayList<Surowce> produkowanySurowiec;//jest to lista surówców jednego typu ale o różnych ID

  private  int maxStanMagazynow;
  private  int aktualnyStanMagazynow;
   private final Random random;
   private boolean podbite=false;
   private final Surowce s,s1;
    private String typSurowcaProdukowanego;
    private String typSurowcaKupowanego;
  
  
   public Osada (){
  random=new Random();
  //losowanie danych
  this.ludnosc=Imperium.losowa(5,10)*1000;
  this.stanKonta=Imperium.losowa(5,10)*100;
  kupowanySurowiec=new ArrayList<>();
//losowanie kupowanego surowca
  produkowanySurowiec=new ArrayList<>();
   s=new Surowce();
  kupowanySurowiec.add(s);
  s.setMiejscePrzechowania(this);

  
 typSurowcaProdukowanego=Imperium.getListaTypowSurowcow().get(random.nextInt(Imperium.getListaTypowSurowcow().size()-1));
 while(typSurowcaProdukowanego.equals(s.getTyp())) typSurowcaProdukowanego=Imperium.getListaTypowSurowcow().get(random.nextInt(Imperium.getListaTypowSurowcow().size()-1));
 //losowanie produkowanego surowca, powyżej jest sprawdzanie czy typy są inne 
 s1=new Surowce(typSurowcaProdukowanego);
 s1.setMiejscePrzechowania(this);
  
  produkowanySurowiec.add(s1);
  
  
  this.maxStanMagazynow=Imperium.losowa(3,6)*100;
  this.aktualnyStanMagazynow=aktualnyStanMagazynu();
    s.getListaOsadKupujacych().add(this);
   s1.getListaOsadProdukujacych().add(this);
   
   new Thread(this).start();
  };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of nazwa
   * @param newVar the new value of nazwa
   */
  public void setNazwa ( String newVar ) {
    nazwa = newVar;
  }

  /**
   * Get the value of nazwa
   * @return the value of nazwa
   */
  public String getNazwa ( ) {
    return nazwa;
  }

  /**
   * Set the value of ludnosc
   * @param newVar the new value of ludnosc
   */
  public void setLudnosc ( int newVar ) {
    ludnosc = newVar;
  }

  /**
   * Get the value of ludnosc
   * @return the value of ludnosc
   */
  public int getLudnosc ( ) {
    return ludnosc;
  }

  /**
   * Set the value of stanKonta
   * @param newVar the new value of stanKonta
   */
  public void setStanKonta ( int newVar ) {
    stanKonta = newVar;
  }

  /**
   * Get the value of stanKonta
   * @return the value of stanKonta
   */
  public int getStanKonta ( ) {
    return stanKonta;
  }

  

    /**
     * @return the maxStanMagazynow
     */
    public int getMaxStanMagazynow() {
        return maxStanMagazynow;
    }

    /**
     * @param maxStanMagazynow the maxStanMagazynow to set
     */
    public void setMaxStanMagazynow(int maxStanMagazynow) {
        this.maxStanMagazynow = maxStanMagazynow;
    }

    /**
     * @return the aktualnyStanMagazynow
     */
    public int getAktualnyStanMagazynow() {
        return aktualnyStanMagazynow;
    }

    /**
     * @param aktualnyStanMagazynow the aktualnyStanMagazynow to set
     */
    public void setAktualnyStanMagazynow(int aktualnyStanMagazynow) {
        this.aktualnyStanMagazynow = aktualnyStanMagazynow;
    }

    /**
     * @return the podbite
     */
    public boolean isPodbite() {
        return podbite;
    }

    /**
     * @param podbite the podbite to set
     */
    public void setPodbite(boolean podbite) {
        this.podbite = podbite;
    }

   public ArrayList<Surowce> getKupowanySurowiec() {
        return kupowanySurowiec;
    }

    /**
     * @param kupowanySurowiec the listaKupowanychSurowcow to set
     */
    public void setKupowanySurowiec(ArrayList<Surowce> kupowanySurowiec) {
        this.kupowanySurowiec = kupowanySurowiec;
    }

    /**
     * @return the listaProdukowanychSurowcow
     */
    public ArrayList<Surowce> getProdukowanySurowiec() {
        return produkowanySurowiec;
    }

    /**
     * @param produkowanySurowiec the listaProdukowanychSurowcow to set
     */
    public void setProdukowanySurowiec(ArrayList<Surowce> produkowanySurowiec) {
        this.produkowanySurowiec = produkowanySurowiec;
    }
    
    /**
     * produkowanie nowych surowcow w osadzie
     */
   public void produkujSurowce(){
       
    try{
         Surowce s=new Surowce(getTypSurowcaProdukowanego());
       produkowanySurowiec.add(s);
       s.setMiejscePrzechowania(this);
       Imperium.getListaSurowcow().add(s);
       
       Imperium.getInfo().repaint();
    }catch(ArrayIndexOutOfBoundsException e){};
      
}

    @Override
    public void run() {//wątek produkuje surowce i zwiększa ludność gdy osada nie jest podbita, zmniejsza ludność gdy jest podbita
        while(!Imperium.isKoniec()){
        if(isPodbite()==false) {
            produkujSurowce();
         
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Osada.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(getLudnosc()<5000)setLudnosc(ludnosc+250);
        }
        else {
            while(!Imperium.isKoniec()){
                if(ludnosc==0)break;
                setLudnosc(ludnosc-500);
                Imperium.getInfo().repaint();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Osada.class.getName()).log(Level.SEVERE, null, ex);
            }
        
            }
        }
        }
    }

    /**
     * @return the typSurowcaProdukowanego
     */
    public String getTypSurowcaProdukowanego() {
        return typSurowcaProdukowanego;
    }
    /**
     * liczy aktualną ilość kupowanego surowca
     */
     public int aktualnaIloscSurowcaKupowanego(  )
  {
    int i=0;
      
      for(Surowce s:kupowanySurowiec){
        i=i+s.getObjetosc();
      
      }
       return i;
              
  }
      /**
     * liczy aktualną ilość produkowanego surowca
     */
    public int aktualnaIloscSurowcaProdukowanego( )
  {
     int j=0;
      
  try{    for(Surowce s:produkowanySurowiec){
          j=j+s.getObjetosc();
      
      }
  }catch(NullPointerException e){}
       return j;
              
  }
     /**
     * liczy aktualny stan magazynu
     */
    public int aktualnyStanMagazynu(){
      int  k=0;
     
      k=kupowanySurowiec.size()*5+produkowanySurowiec.size()*5;
      
       return k;
    
    
    
    }

    /**
     * @param typSurowcaProdukowanego the typSurowcaProdukowanego to set
     */
    public void setTypSurowcaProdukowanego(String typSurowcaProdukowanego) {
        this.typSurowcaProdukowanego = typSurowcaProdukowanego;
    }

    /**
     * @return the typSurowcaKupowanego
     */
    public String getTypSurowcaKupowanego() {
        return typSurowcaKupowanego;
    }

    /**
     * @param typSurowcaKupowanego the typSurowcaKupowanego to set
     */
    public void setTypSurowcaKupowanego(String typSurowcaKupowanego) {
        this.typSurowcaKupowanego = typSurowcaKupowanego;
    }

 

}
