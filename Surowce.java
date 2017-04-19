package imperium;


import java.util.ArrayList;
import java.util.Random;
/**
 * Klasa Surowce
 */
public class Surowce extends Lokalizacja  {

 
  private int ID;
  private int waga;
  private int objetosc;
  private String typ;
  private Lokalizacja miejscePrzechowania;
  private ArrayList<Osada> listaOsadKupujacych;
  private ArrayList<Osada> listaOsadProdukujacych;
  private int dataProdukcji; 
  private Random random;
  
  
  public Surowce ()  {
  listaOsadKupujacych=new ArrayList<>();
  listaOsadProdukujacych=new ArrayList<>();
  dataProdukcji=Imperium.getZegar().getCzas();
  random = new Random();
  int i=Imperium.losowa(1000,9999);
  while(Imperium.getListaIDSurowcow().contains(i))i=Imperium.losowa(1000,9999);
  ID=i;
  waga=random.nextInt(3);
  objetosc=5;
  typ=Imperium.getListaTypowSurowcow().get(random.nextInt(Imperium.getListaTypowSurowcow().size()));
  
  };
   public Surowce (String t) {
  listaOsadKupujacych=new ArrayList<>();
  listaOsadProdukujacych=new ArrayList<>();
  random = new Random();
  int i=Imperium.losowa(1000,9999);
  while(Imperium.getListaIDSurowcow().contains(i))i=Imperium.losowa(1000,9999);
  ID=i;
  waga=random.nextInt(3);
  objetosc=5;
  typ=t;
  
  };
  


  /**
   * Set the value of ID
   * @param newVar the new value of ID
   */
  public void setID ( int newVar ) {
    ID = newVar;
  }

  /**
   * Get the value of ID
   * @return the value of ID
   */
  public int getID ( ) {
    return ID;
  }

  /**
   * Set the value of waga
   * @param newVar the new value of waga
   */
  public void setWaga ( int newVar ) {
    waga = newVar;
  }

  /**
   * Get the value of waga
   * @return the value of waga
   */
  public int getWaga ( ) {
    return waga;
  }

  /**
   * Set the value of objetosc
   * @param newVar the new value of objetosc
   */
  public void setObjetosc ( int newVar ) {
    objetosc = newVar;
  }

  /**
   * Get the value of objetosc
   * @return the value of objetosc
   */
  public int getObjetosc ( ) {
    return objetosc;
  }

  /**
   * Set the value of typ
   * @param newVar the new value of typ
   */
  public void setTyp ( String newVar ) {
    typ = newVar;
  }

  /**
   * Get the value of typ
   * @return the value of typ
   */
  public String getTyp ( ) {
    return typ;
  }

  /**
   * Set the value of listaOsadKupujacych
   * @param newVar the new value of listaOsadKupujacych
   */
  public void setListaOsadKupujacych ( ArrayList newVar ) {
    listaOsadKupujacych = newVar;
  }

  /**
   * Get the value of listaOsadKupujacych
   * @return the value of listaOsadKupujacych
   */
  public ArrayList getListaOsadKupujacych ( ) {
    return listaOsadKupujacych;
  }

  /**
   * Set the value of listaOsadProdukujacych
   * @param newVar the new value of listaOsadProdukujacych
   */
  public void setListaOsadProdukujacych ( ArrayList newVar ) {
    listaOsadProdukujacych = newVar;
  }

  /**
   * Get the value of listaOsadProdukujacych
   * @return the value of listaOsadProdukujacych
   */
  public ArrayList getListaOsadProdukujacych ( ) {
    return listaOsadProdukujacych;
  }


    /**
     * @return the miejscePrzechowania
     */
    public Lokalizacja getMiejscePrzechowania() {
        return miejscePrzechowania;
    }

    /**
     * @param miejscePrzechowania the miejscePrzechowania to set
     */
    public void setMiejscePrzechowania(Lokalizacja miejscePrzechowania) {
        this.miejscePrzechowania = miejscePrzechowania;
    }
  @Override
   /**
     * usuwanie w zależności od miejsca przechowania surowca
     */
    public void usun(){
        try{
        if(miejscePrzechowania instanceof Handlowiec){
            Handlowiec h=(Handlowiec)miejscePrzechowania;
            h.getListaPrzewozonychSurowcow().remove(this);
        }
        else if(miejscePrzechowania instanceof Osada){
            Osada o =(Osada)miejscePrzechowania;
            if("ruda".equals(this.getTyp())||"miedz".equals(this.getTyp())||"drewno".equals(this.getTyp())){o.setStanKonta(o.getStanKonta()+500);}
            if(o.getKupowanySurowiec().contains(this))o.getKupowanySurowiec().remove(this);
            else o.getProdukowanySurowiec().remove(this);
        
        
        }
        }catch(NullPointerException e){};
         
    }

    /**
     * @return the dataProdukcji
     */
    public int getDataProdukcji() {
        return dataProdukcji;
    }

    /**
     * @param dataProdukcji the dataProdukcji to set
     */
    public void setDataProdukcji(int dataProdukcji) {
        this.dataProdukcji = dataProdukcji;
    }
    
}
