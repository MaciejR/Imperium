package imperium;


/**
 * Klasa Stolica
 */
public class Stolica extends Osada {
  // Fields
  
  
 
 public Stolica (){
     setNazwa("Rzym");
     //losowanie danych
  setLudnosc(Imperium.losowa(11,20)*1000);
  setStanKonta(Imperium.losowa(11,20)*100);
  setMaxStanMagazynow(Imperium.losowa(7,9)*100);
 
 
  };
 


}
