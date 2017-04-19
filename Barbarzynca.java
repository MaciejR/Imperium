package imperium;



/**
 * Klasa Barbarzyncy
 */
public class Barbarzynca extends Lokalizacja {

 
  private String nazwa; // nazwa barbarzyncy
  private int liczbaOsob; //liczba barbarzyncow
  private  String bron;//typ broni

 
  public Barbarzynca () { 
      //losowanie z list zainicjalizowanych w klasie Imperium
   this.nazwa=Imperium.getListaNazwBarbarzyncow().get(Imperium.getRandom().nextInt(Imperium.getListaNazwBarbarzyncow().size()));;
   this.liczbaOsob=500;
   this.bron=Imperium.getListaBroni().get(Imperium.getRandom().nextInt(Imperium.getListaBroni().size()));;
  
  };
  
 
  /**
   * Set the value of liczbaOsob
   * @param newVar the new value of liczbaOsob
   */
  public void setLiczbaOsob ( int newVar ) {
    liczbaOsob = newVar;
  }

  /**
   * Get the value of liczbaOsob
   * @return the value of liczbaOsob
   */
  public int getLiczbaOsob ( ) {
    return liczbaOsob;
  }

  //
  // Other methods
  //
    /**
     * @return the nazwa
     */
    public String getNazwa() {
        return nazwa;
    }

    /**
     * @param nazwa the nazwa to set
     */
    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    /**
     * @return the bron
     */
    public String getBron() {
        return bron;
    }

    /**
     * @param bron the bron to set
     */
    public void setBron(String bron) {
        this.bron = bron;
    }
    
    

}
