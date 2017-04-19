package imperium;

import java.util.ArrayList;
import java.util.Random;

/**
 * Klasa Handlowca
 */
public class Handlowiec extends Lokalizacja {

 

  private String imie;
  private String nazwisko;
  private int maxPojemnoscWozu;
  private int aktPojemnoscWozu;
  //Lista Surowców jest to lista różnych typów surowców o różnych ID
  private ArrayList<Surowce> listaPrzewozonychSurowcow;
  private boolean stanWozu;
  private int predkoscWozu;
  private final Random random2;//zmienna losująca
 // private ArrayList<Surowce> lista1,lista2,lista3;
  

  public Handlowiec () {
  random2 = new Random();
  //losowanie danychs
   this.imie=Imperium.getListaImion().get(random2.nextInt(Imperium.getListaImion().size()));
   this.nazwisko=Imperium.getListaNazwisk().get(random2.nextInt(Imperium.getListaNazwisk().size()));
   this.maxPojemnoscWozu=50;
   listaPrzewozonychSurowcow=new ArrayList<>();
   //losowanie surowca z listy z klasy Imperium
   Surowce s=Imperium.getListaSurowcow().get(random2.nextInt(Imperium.getListaSurowcow().size()));
   this.listaPrzewozonychSurowcow.add(s);
   s.setMiejscePrzechowania(this);
   
   this.stanWozu=true;
   
   aktualnaPojWozu();//ustalanie prędkości na podstawie aktualnej pojemności wozu
 // if(aktualnaPojWozu()<20)this.predkoscWozu=20;
 // else 
   this.predkoscWozu=aktualnaPojWozu()+20;
  };
   
  
  /**
   * Set the value of imie
   * @param newVar the new value of imie
   */
  public void setImie ( String newVar ) {
    imie = newVar;
  }

  /**
   * Get the value of imie
   * @return the value of imie
   */
  public String getImie ( ) {
    return imie;
  }

  /**
   * Set the value of nazwisko
   * @param newVar the new value of nazwisko
   */
  public void setNazwisko ( String newVar ) {
    nazwisko = newVar;
  }

  /**
   * Get the value of nazwisko
   * @return the value of nazwisko
   */
  public String getNazwisko ( ) {
    return nazwisko;
  }

  /**
   * Set the value of maxPojemnoscWozu
   * @param newVar the new value of maxPojemnoscWozu
   */
  public void setMaxPojemnoscWozu ( int newVar ) {
    maxPojemnoscWozu = newVar;
  }

  /**
   * Get the value of maxPojemnoscWozu
   * @return the value of maxPojemnoscWozu
   */
  public int getMaxPojemnoscWozu ( ) {
    return maxPojemnoscWozu;
  }

  /**
   * Set the value of listaPrzewozonychSurowcow
   * @param newVar the new value of listaPrzewozonychSurowcow
   */
  public void setListaPrzewozonychSurowcow ( ArrayList<Surowce> newVar ) {
    listaPrzewozonychSurowcow = newVar;
  }

  /**
   * Get the value of listaPrzewozonychSurowcow
   * @return the value of listaPrzewozonychSurowcow
   */
  public ArrayList<Surowce> getListaPrzewozonychSurowcow ( ) {
    return listaPrzewozonychSurowcow;
  }

  /**
   * Set the value of stanWozu
   * @param newVar the new value of stanWozu
   */
  public void setStanWozu ( boolean newVar ) {
    stanWozu = newVar;
  }

  /**
   * Get the value of stanWozu
   * @return the value of stanWozu
   */
  public boolean getStanWozu ( ) {
    return stanWozu;
  }

  /**
   * Set the value of predkoscWozu
   * @param newVar the new value of predkoscWozu
   */
  public void setPredkoscWozu ( int newVar ) {
      
    predkoscWozu = newVar;
  }

  /**
   * Get the value of predkoscWozu
   * @return the value of predkoscWozu
   */
  public int getPredkoscWozu ( ) {
    return predkoscWozu;
  }

  //
  // Other methods
  //

  /**
     * sprzedawanie surowców od handlowca do osady
     * @param p
   */
  public void sprzedaj( Osada p )
  {   
      
     String typ=p.getTypSurowcaProdukowanego();
     Object[] lista5=this.getListaPrzewozonychSurowcow().toArray();
     
     while(p.aktualnyStanMagazynu()<p.getMaxStanMagazynow()-4){
        for(Object s:lista5){
            Surowce s1=(Surowce)s;
            if(s1.getTyp().equals(typ)){
             p.getKupowanySurowiec().add(s1);
             p.setStanKonta(p.getStanKonta()-500);
             getListaPrzewozonychSurowcow().remove(s1);
            }
     
         }
         
     }
  
  }

  /**
     * kupowanie surowców z osady przez handlowca
     * @param p
   */
public void kupuj(  Osada p)
  { 
    
   ArrayList<Surowce> lista37=p.getProdukowanySurowiec();
   String typ=p.getTypSurowcaProdukowanego();
   while(lista37.size()>0&&aktualnaPojWozu()<maxPojemnoscWozu){
   
       Surowce s=lista37.get(0);
       listaPrzewozonychSurowcow.add(s);
       lista37.remove(s);
       p.setStanKonta(p.getStanKonta()+500);
   
   
   
   }
   p.setProdukowanySurowiec(lista37);

  }
  /**
     * metoda sprawdza aktualną ilość surowca określonego typu
     * 
   */
 
  int aktualnaIloscSurowca(  String t)
  {
      int i =0;
      
      for(Surowce s:listaPrzewozonychSurowcow){
        if(s.getTyp().equals(t))i=i+s.getObjetosc();
      
      }
       return i;
              
  }
    /**
     * metoda zwraca aktualną pojemność wozu
     * 
   */
  public int aktualnaPojWozu()
  {
      int i=0;
      for (Surowce s : listaPrzewozonychSurowcow) {
          i+=s.getObjetosc();
      }
      aktPojemnoscWozu=i;
       return i;
       
              
  }

    /**
     * @return the random2
     */
    public Random getRandom2() {
        return random2;
    }
  

}
