

package imperium;

/**
 *
 * Klasa przechowująca wynik rozgrywki
 * 
 */

public class Wynik implements Comparable<Wynik>{
   private Integer czasGry;
   private String imie;
    
    public Wynik(){
   } 
    public Wynik(int c,String s){
    this.czasGry=c;
    this.imie=s;
}

    /**
     * @return the czasGry
     */
    public Integer getCzasGry() {
        return czasGry;
    }

    /**
     * @param czasGry the czasGry to set
     */
    public void setCzasGry(Integer czasGry) {
        this.czasGry = czasGry;
    }

    /**
     * @return the imie
     */
    public String getImie() {
        return imie;
    }

    /**
     * @param imie the imie to set
     */
    public void setImie(String imie) {
        this.imie = imie;
    }

    @Override
    public int compareTo(Wynik t) {
     //sortowanie na podstawie czasu gry
    int porownanieWynikow = czasGry.compareTo(t.czasGry);
   
    return porownanieWynikow;
         
    }
   
}
