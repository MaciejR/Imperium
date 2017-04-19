/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imperium;


import java.awt.image.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.ArrayList;
import javax.swing.*;
import java.util.Random;
import javax.imageio.ImageIO;



/**
 *
 * głowna klasa Imperium
 * 
 */
public class Imperium {
    //ładowanie obrazków
private static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
private static URL resource1 = classLoader.getResource("rzym.jpg");
private static ImageIcon stolicaIkona = new ImageIcon(resource1);

private static URL resource2 = classLoader.getResource("kartagina.jpg");
   private static ImageIcon osadaIkona = new ImageIcon(resource2);
   
   private static URL resource3 = classLoader.getResource("handlowiec21.png");
   private static ImageIcon handlowiecIkona = new ImageIcon(resource3);
   
   private static URL resource4 = classLoader.getResource("barbarzynca.png");
   private static ImageIcon barbarzyncaIkona = new ImageIcon(resource4);  
   
   private static URL resource5 = classLoader.getResource("zolnierz.png");
   private static ImageIcon zolnierzIkona = new ImageIcon(resource5); 
  
   //Panel Sterowania Imperium
    private static PanelSterowania info= new PanelSterowania();
   //Listy 
    private static ArrayList<Osada> listaOsad;
    private static HashSet<Handlowiec> zbiorHandlowcow;
    private static ArrayList<String> listaImion;
    private static ArrayList<String> listaNazwisk;
    private static ArrayList<Surowce> listaSurowcow;
    private static ArrayList<String> listaTypowSurowcow;
    private static ArrayList<Integer> listaIDSurowcow;
    private static ArrayList<Barbarzynca> listaBarbarzyncow;
    private static ArrayList<Zolnierz> listaZolnierzy;
    private static ArrayList<String> listaBroni;
    private static ArrayList<String> listaNazwBarbarzyncow;
    //deklaracja okna dla Imperium
    private static JFrame okno;
    //deklaracja osad
    private static Stolica rzym ;
    private static Osada kartagina ;
    private static Osada bizancjum ;
    private static Osada pompeje ;
    private static Osada aquincum ;
    private static Osada syrakuzy ;
    private static Osada ostia ;
    private static Osada volubilis ;
    private static Osada traiana ;
    private static Osada herkulanum ;
    //deklaracja klasy random do losowania 
    private static Random random;
    //deklaracja zmiennej określającej koniec gry
    private static boolean koniec=false;
    //mapa Imperium
    private static Mapa panelImperium;
    //Surowce
    private static Surowce wino ;
    private static Surowce chleb ;
    private static Surowce owoce ;
    private static Surowce drewno ;
    private static Surowce miedz ;
    private static Surowce ruda ;
    
    private static Zegar zegar;//służąca do odmierzania dni
    private static Zapis zapis;//zapisuje a także wyświetla top5 wyników
    
    
    
    /**
 * Głowna metoda symulacji
 */
    public static void main(String[] args)   {
        inicjalizujGUI();//zainicjalizowanie GUI
        inicjalizuj();//zainicjalizowanie list, osad
         for (int i = 0; i < 5; i++) {
         dodajHandlowca();   //dodawanie 5 losowych handlowców
           
        }
      
         Kolizja kolizja = new Kolizja();//uruchomienie wątku sprawdzającego kolizje między Barbarzyncami a innymi postaciami
         KolizjeHandlowcow k =new KolizjeHandlowcow();//uruchomienie wątku sprawdzającego kolizje między Handlowcami
         
    };
    /**
 * losowanie liczby z zakresu(a,b)
 */
 public static int losowa(int b,int c){//metoda do losowania liczb z zakresu między a i b
     int a=0;
      Random gen=new Random();
    while(a<b){
    a=gen.nextInt(c);
    }
     
     return a;
}
 /**
 * dodawanie Handlowca do symulacji
 */
 public static void dodajHandlowca(){
      Osada o=getListaOsad().get(getRandom().nextInt(getListaOsad().size()));//losowanie osady początkowej
    
        Handlowiec h = new Handlowiec();
        h.setX(o.getWartoscX());
        
        h.setY(o.getWartoscY());
        h.setObraz(getHandlowiecIkona().getImage());
        
        Ikonka handlowiecIkonka = new Ikonka(h);//tworzenie klasy Ikonka dla Handlowca,czyli jego grafiki i wątku
        h.setIkonka(handlowiecIkonka);
        Osada o2=getListaOsad().get(getRandom().nextInt(getListaOsad().size()));
        getZbiorHandlowcow().add(h);
        while(o.equals(o2))o2=getListaOsad().get(getRandom().nextInt(getListaOsad().size()));//losowanie celu Podróży
        handlowiecIkonka.setCelPodrozy(o2);
        getPanelImperium().add(handlowiecIkonka);
        Thread watekHandlowiec = new Thread(handlowiecIkonka);//startowanie wątku
        watekHandlowiec.start();
}
 /**
 * dodawanie Barbarzyncy do symulacji
 */
  public static void atakBarbarzyncow(){
     
     Barbarzynca b = new Barbarzynca();
     int i=Imperium.losowa(1, 5);
     switch (i){    //losowanie miejsca pojawienia się Barbarzyńcy
         case 1:b.setX(410);
                b.setY(10);
                break;
         case 2:b.setX(10);
                b.setY(410);
                break;
         case 3:b.setX(410);
                b.setY(610);
                break;
         default:
             b.setX(840);
                b.setY(550);
                break;
     }
            
        b.setObraz(getBarbarzyncaIkona().getImage());
        Ikonka barbarzyncaIkonka = new Ikonka(b);//przypisywanie Ikonki czyli grafiki i wątku
        b.setIkonka(barbarzyncaIkonka);
       
        Lokalizacja o2=b.najblizszaOsada(b);//sprawdzanie jaka jest najbliższa osada i ustalanie jej za celPodróży
      
        barbarzyncaIkonka.setCelPodrozy(o2);
        getPanelImperium().add(barbarzyncaIkonka);
        getListaBarbarzyncow().add(b);
        Thread watekBarbarzynca = new Thread(barbarzyncaIkonka);//startowanie wątku
        watekBarbarzynca.start();
        
         
}
  /**
 * dodawanie Żołnierzy do symulacji
 */
   public static void wyslijLegion(){//tworzenie Legionu, nie zadziała jeśli nie ma Barbarzyńcy
    
       Barbarzynca b;
       Lokalizacja o2;
        Zolnierz z = new Zolnierz();
        z.setX(Imperium.getRzym().getWartoscX());//Rzym jako lokalizacja początkowa
        
        z.setY(Imperium.getRzym().getWartoscY());
        z.setObraz(getZolnierzIkona().getImage());
        Ikonka zolnierzIkonka = new Ikonka(z);//przypisywanie Ikonki czyli grafiki i wątku
        z.setIkonka(zolnierzIkonka);
        getListaZolnierzy().add(z);
        //sprawdzanie lokalizacji Barbarzyncy i ustawianie najbliżsżą mu osadę na cel Podróży, jeśli nie ma już Barbarzyncow celPodróży to rzym
        try{
        b=  getListaBarbarzyncow().get(getListaBarbarzyncow().size()-1);
        o2=b.najblizszaOsada(b);
        }catch(ArrayIndexOutOfBoundsException e){
        o2= Imperium.getRzym();
        }
        zolnierzIkonka.setCelPodrozy(o2);
      
        getPanelImperium().add(zolnierzIkonka);
        Thread watekZolnierz = new Thread(zolnierzIkonka);//start wątku
        watekZolnierz.start();
}
   /**
 * metoda inicjalizująca surowce
 */
 public static void inicjalizujSurowce(){
      Surowce s=new Surowce();
        getListaSurowcow().add(s);
      
}
 /**
 * metoda inicjalizująca m.in. listy,osady i inne zasoby
 */
  public static void inicjalizuj(){
        setListaSurowcow(new ArrayList<Surowce>());
        setZegar(new Zegar());
        zapis = new Zapis();
        setRandom(new Random());
        setListaBarbarzyncow(new ArrayList<Barbarzynca>());
        setListaZolnierzy(new ArrayList<Zolnierz>());
        setListaIDSurowcow(new ArrayList<Integer>());
        setZbiorHandlowcow(new HashSet<Handlowiec>());
        setListaImion(new ArrayList<String>());
        
        //losowe imiona
        getListaImion().add("Adam ");
        getListaImion().add("Maciej ");
        getListaImion().add("Piotr ");
        getListaImion().add("Mariusz ");
        getListaImion().add("Dariusz ");
        getListaImion().add("Jacek "); 
        //losowe nazwiska
        setListaNazwisk(new ArrayList<String>());
        getListaNazwisk().add("Kowalski");
        getListaNazwisk().add("Nowak");
        getListaNazwisk().add("Kaczmarek");
        getListaNazwisk().add("Smith");
        getListaNazwisk().add("Davies");
        getListaNazwisk().add("Mukulele");
   
        setListaTypowSurowcow(new ArrayList<String>());
        //typy surowcow
        getListaTypowSurowcow().add("wino");
        getListaTypowSurowcow().add("chleb");
        getListaTypowSurowcow().add("owoce");
        getListaTypowSurowcow().add("drewno");
        getListaTypowSurowcow().add("miedz");
        getListaTypowSurowcow().add("ruda");
        //Bronie
        setListaBroni(new ArrayList<String>());
        getListaBroni().add("miecz");
        getListaBroni().add("łuk");
        getListaBroni().add("kopia");
        getListaBroni().add("topór");
        //NazwyBarbarzyncow
        setListaNazwBarbarzyncow(new ArrayList<String>());
        getListaNazwBarbarzyncow().add("Wizygoci");
        getListaNazwBarbarzyncow().add("Słowianie");
        getListaNazwBarbarzyncow().add("Germanie");
        //inicjalizowanie Surowców dla Handlowców, są one losowane Handlowcom przy ich tworzeniu
   for (int i = 0; i < getZbiorHandlowcow().size(); i++) {
           inicjalizujSurowce();
           
        }
        //dodawanie informacji o osadach
        setListaOsad(new ArrayList<Osada>());
        setRzym(new Stolica());
        setKartagina(new Osada());
        getKartagina().setNazwa("Kartagina");
        setBizancjum(new Osada());
        getBizancjum().setNazwa("Bizancjum");   
        setPompeje(new Osada());
        getPompeje().setNazwa("Pompeje");
        setAquincum(new Osada());
        getAquincum().setNazwa("Aquincum");
        setSyrakuzy(new Osada());
        getSyrakuzy().setNazwa("Syrakuzy");
        setOstia(new Osada());
        getOstia().setNazwa("Ostia");
        setVolubilis(new Osada());
        getVolubilis().setNazwa("Volubilis");
    
        setTraiana(new Osada());
        getTraiana().setNazwa("Colonia Ulpia Traiana");
        setHerkulanum(new Osada());
        getHerkulanum().setNazwa("Herkulanum");
    
    //dodawanie zdjęć i lokalizacji osad w imperium
        getRzym().setX(440);
        getRzym().setY(270);
        getRzym().setObraz(getStolicaIkona().getImage());
        Ikonka rzymIkonka = new Ikonka(getRzym());
        getPanelImperium().add(rzymIkonka);
        getListaOsad().add(getRzym());
        
        getKartagina().setX(10);
        getKartagina().setY(10);
        getKartagina().setObraz(getOsadaIkona().getImage());
        Ikonka kartaginaIkonka = new Ikonka(getKartagina());
        getPanelImperium().add( kartaginaIkonka);
        getListaOsad().add(getKartagina());
        
        
        getBizancjum().setX(810);
        getBizancjum().setY(10);
        getBizancjum().setObraz(getOsadaIkona().getImage());
        Ikonka bizancjumIkonka = new Ikonka(getBizancjum());
        getPanelImperium().add(bizancjumIkonka);
        getListaOsad().add(getBizancjum());
        
        getPompeje().setX(810);
        getPompeje().setY(610);
        getPompeje().setObraz(getOsadaIkona().getImage());
        Ikonka pompejeIkonka = new Ikonka(getPompeje());
        getPanelImperium().add(pompejeIkonka);
        getListaOsad().add(getPompeje());
        
        getAquincum().setX(10);
        getAquincum().setY(610);
        getAquincum().setObraz(getOsadaIkona().getImage());
        Ikonka acquincumIkonka = new Ikonka(getAquincum());
        getPanelImperium().add(acquincumIkonka);
        getListaOsad().add(getAquincum());
        
        getSyrakuzy().setX(510);
        getSyrakuzy().setY(610);
        getSyrakuzy().setObraz(getOsadaIkona().getImage());
        Ikonka syrakuzyIkonka = new Ikonka(getSyrakuzy());
        getPanelImperium().add(syrakuzyIkonka);
        getListaOsad().add(getSyrakuzy()); 
        
        getOstia().setX(810);
        getOstia().setY(360);
        getOstia().setObraz(getOsadaIkona().getImage());
        Ikonka ostiaIkonka = new Ikonka(getOstia());
        getPanelImperium().add(ostiaIkonka);
        getListaOsad().add(getOstia());
        
        getVolubilis().setX(210);
        getVolubilis().setY(610);
        getVolubilis().setObraz(getOsadaIkona().getImage());
        Ikonka vIkonka = new Ikonka(getVolubilis());
        getPanelImperium().add(vIkonka);
        getListaOsad().add(getVolubilis());
        
        getTraiana().setX(440);
        getTraiana().setY(60);
        getTraiana().setObraz(getOsadaIkona().getImage());
        Ikonka tIkonka = new Ikonka(getTraiana());
        getPanelImperium().add(tIkonka);
        getListaOsad().add(getTraiana());
        
        
        getHerkulanum().setX(10);
        getHerkulanum().setY(360);
        getHerkulanum().setObraz(getOsadaIkona().getImage());
        Ikonka hIkonka = new Ikonka(getHerkulanum());
        getPanelImperium().add(hIkonka);
        getListaOsad().add(getHerkulanum());
        
        //rysowanie tras między osadami
        getPanelImperium().rysujTrasyJednokierunkowe(getKartagina(), getHerkulanum());
        getPanelImperium().rysujTrasyJednokierunkowe(getHerkulanum(), getAquincum());
        getPanelImperium().rysujTrasyJednokierunkowe(getBizancjum(), getOstia());
        getPanelImperium().rysujTrasyDwukierunkowe(getPompeje(), getOstia());
         getPanelImperium().rysujTrasyDwukierunkowe(getOstia(), getRzym()); 
        getPanelImperium().rysujTrasyDwukierunkowe(getVolubilis(), getRzym());  
        getPanelImperium().rysujTrasyDwukierunkowe(getTraiana(), getRzym());
        getPanelImperium().rysujTrasyDwukierunkowe(getKartagina(), getTraiana());
        getPanelImperium().rysujTrasyDwukierunkowe(getBizancjum(), getKartagina());
        getPanelImperium().rysujTrasyDwukierunkowe(getSyrakuzy(), getPompeje());
        getPanelImperium().rysujTrasyDwukierunkowe(getSyrakuzy(), getVolubilis());
        getPanelImperium().rysujTrasyDwukierunkowe(getAquincum(), getVolubilis());
        getPanelImperium().rysujTrasyDwukierunkowe(getHerkulanum(), getRzym());
        
        getPanelImperium().Skrzyzowania();//skrzyzowania na trasach
        getPanelImperium().Miasta();//lokalizacja miast
      
  
  }
  /**
 * metoda inicjalizująca GUI
 */
  public static void inicjalizujGUI(){
 
 //inicjowanie grafiki
        //panel sterowania
        
        getInfo().setSize(300,300);
        getInfo().setLocation(1043, 0);
        getInfo().setVisible(true);
       
        
        //okno dla panelu sterowania
        
        JFrame panelS=new JFrame();
        panelS.setSize(600,300);
        panelS.setAlwaysOnTop(true);
        panelS.setVisible(true);
        panelS.setResizable(true);
        panelS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panelS.add(getInfo());
         
        //okno dla obrazu symulacji        
        setOkno(new JFrame());
        getOkno().setMinimumSize(new Dimension(900,900));
        getOkno().setMaximumSize(new Dimension(900,900));
        
        getOkno().setBackground(Color.yellow);
        getOkno().setResizable(false);
        getOkno().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getOkno().setVisible(true);
          
        setPanelImperium(new Mapa(900,900));
        getPanelImperium().setLayout(null);
        getPanelImperium().setOpaque(true);
        getPanelImperium().setLocation(1300,500);
        getOkno().add(getPanelImperium());
   
   
 };
  /**
 * metoda działająca na zakończenie symulacji
 */
  public static void koniecGry(){
        setKoniec(true);
        getPanelImperium().setVisible(false);
        getOkno().setVisible(false);
        getOkno().dispose();
        getInfo().zerujTeksty();
        getInfo().jButton6.setVisible(true);
        getInfo().setTekst2("Koniec gry Twój wynik to" );
        getInfo().setTekst3(new Integer(getZegar().getCzas()).toString());
        getInfo().jTextField1.setVisible(true);
      System.out.println("Koniec");
  
  
  
  }
   /**
 * sprawdza  czy należy już zakończyć grę
 */
 public static int lacznaLiczbaLudnosci(){
  int i=0;
  for(Osada o :getListaOsad()){
  i=i+o.getLudnosc();
  }
  return i;
 
 }
//Poniżej już tylko gettery i settery
    /**
     * @return the stolicaIkona
     */
    public static ImageIcon getStolicaIkona() {
        return stolicaIkona;
    }

    /**
     * @param aStolicaIkona the stolicaIkona to set
     */
    public static void setStolicaIkona(ImageIcon aStolicaIkona) {
        stolicaIkona = aStolicaIkona;
    }

    /**
     * @return the osadaIkona
     */
    public static ImageIcon getOsadaIkona() {
        return osadaIkona;
    }

    /**
     * @param aOsadaIkona the osadaIkona to set
     */
    public static void setOsadaIkona(ImageIcon aOsadaIkona) {
        osadaIkona = aOsadaIkona;
    }

    /**
     * @return the handlowiecIkona
     */
    public static ImageIcon getHandlowiecIkona() {
        return handlowiecIkona;
    }

    /**
     * @param aHandlowiecIkona the handlowiecIkona to set
     */
    public static void setHandlowiecIkona(ImageIcon aHandlowiecIkona) {
        handlowiecIkona = aHandlowiecIkona;
    }

    /**
     * @return the barbarzyncaIkona
     */
    public static ImageIcon getBarbarzyncaIkona() {
        return barbarzyncaIkona;
    }

    /**
     * @param aBarbarzyncaIkona the barbarzyncaIkona to set
     */
    public static void setBarbarzyncaIkona(ImageIcon aBarbarzyncaIkona) {
        barbarzyncaIkona = aBarbarzyncaIkona;
    }

    /**
     * @return the zolnierzIkona
     */
    public static ImageIcon getZolnierzIkona() {
        return zolnierzIkona;
    }

    /**
     * @param aZolnierzIkona the zolnierzIkona to set
     */
    public static void setZolnierzIkona(ImageIcon aZolnierzIkona) {
        zolnierzIkona = aZolnierzIkona;
    }

    /**
     * @return the info
     */
    public static PanelSterowania getInfo() {
        return info;
    }

    /**
     * @param aInfo the info to set
     */
    public static void setInfo(PanelSterowania aInfo) {
        info = aInfo;
    }

    /**
     * @return the listaOsad
     */
    public static ArrayList<Osada> getListaOsad() {
        return listaOsad;
    }

    /**
     * @param aListaOsad the listaOsad to set
     */
    public static void setListaOsad(ArrayList<Osada> aListaOsad) {
        listaOsad = aListaOsad;
    }

    /**
     * @return the zbiorHandlowcow
     */
    public static HashSet<Handlowiec> getZbiorHandlowcow() {
        return zbiorHandlowcow;
    }

    /**
     * @param aZbiorHandlowcow the zbiorHandlowcow to set
     */
    public static void setZbiorHandlowcow(HashSet<Handlowiec> aZbiorHandlowcow) {
        zbiorHandlowcow = aZbiorHandlowcow;
    }

    /**
     * @return the listaImion
     */
    public static ArrayList<String> getListaImion() {
        return listaImion;
    }

    /**
     * @param aListaImion the listaImion to set
     */
    public static void setListaImion(ArrayList<String> aListaImion) {
        listaImion = aListaImion;
    }

    /**
     * @return the listaNazwisk
     */
    public static ArrayList<String> getListaNazwisk() {
        return listaNazwisk;
    }

    /**
     * @param aListaNazwisk the listaNazwisk to set
     */
    public static void setListaNazwisk(ArrayList<String> aListaNazwisk) {
        listaNazwisk = aListaNazwisk;
    }

    /**
     * @return the listaSurowcow
     */
    public static ArrayList<Surowce> getListaSurowcow() {
        return listaSurowcow;
    }

    /**
     * @param aListaSurowcow the listaSurowcow to set
     */
    public static void setListaSurowcow(ArrayList<Surowce> aListaSurowcow) {
        listaSurowcow = aListaSurowcow;
    }

    /**
     * @return the listaTypowSurowcow
     */
    public static ArrayList<String> getListaTypowSurowcow() {
        return listaTypowSurowcow;
    }

    /**
     * @param aListaTypowSurowcow the listaTypowSurowcow to set
     */
    public static void setListaTypowSurowcow(ArrayList<String> aListaTypowSurowcow) {
        listaTypowSurowcow = aListaTypowSurowcow;
    }

    /**
     * @return the listaIDSurowcow
     */
    public static ArrayList<Integer> getListaIDSurowcow() {
        return listaIDSurowcow;
    }

    /**
     * @param aListaIDSurowcow the listaIDSurowcow to set
     */
    public static void setListaIDSurowcow(ArrayList<Integer> aListaIDSurowcow) {
        listaIDSurowcow = aListaIDSurowcow;
    }

    /**
     * @return the listaBarbarzyncow
     */
    public static ArrayList<Barbarzynca> getListaBarbarzyncow() {
        return listaBarbarzyncow;
    }

    /**
     * @param aListaBarbarzyncow the listaBarbarzyncow to set
     */
    public static void setListaBarbarzyncow(ArrayList<Barbarzynca> aListaBarbarzyncow) {
        listaBarbarzyncow = aListaBarbarzyncow;
    }

    /**
     * @return the listaZolnierzy
     */
    public static ArrayList<Zolnierz> getListaZolnierzy() {
        return listaZolnierzy;
    }

    /**
     * @param aListaZolnierzy the listaZolnierzy to set
     */
    public static void setListaZolnierzy(ArrayList<Zolnierz> aListaZolnierzy) {
        listaZolnierzy = aListaZolnierzy;
    }

    /**
     * @return the listaBroni
     */
    public static ArrayList<String> getListaBroni() {
        return listaBroni;
    }

    /**
     * @param aListaBroni the listaBroni to set
     */
    public static void setListaBroni(ArrayList<String> aListaBroni) {
        listaBroni = aListaBroni;
    }

    /**
     * @return the listaNazwBarbarzyncow
     */
    public static ArrayList<String> getListaNazwBarbarzyncow() {
        return listaNazwBarbarzyncow;
    }

    /**
     * @param aListaNazwBarbarzyncow the listaNazwBarbarzyncow to set
     */
    public static void setListaNazwBarbarzyncow(ArrayList<String> aListaNazwBarbarzyncow) {
        listaNazwBarbarzyncow = aListaNazwBarbarzyncow;
    }

    /**
     * @return the okno
     */
    public static JFrame getOkno() {
        return okno;
    }

    /**
     * @param aOkno the okno to set
     */
    public static void setOkno(JFrame aOkno) {
        okno = aOkno;
    }

    /**
     * @return the rzym
     */
    public static Stolica getRzym() {
        return rzym;
    }

    /**
     * @param aRzym the rzym to set
     */
    public static void setRzym(Stolica aRzym) {
        rzym = aRzym;
    }

    /**
     * @return the kartagina
     */
    public static Osada getKartagina() {
        return kartagina;
    }

    /**
     * @param aKartagina the kartagina to set
     */
    public static void setKartagina(Osada aKartagina) {
        kartagina = aKartagina;
    }

    /**
     * @return the bizancjum
     */
    public static Osada getBizancjum() {
        return bizancjum;
    }

    /**
     * @param aBizancjum the bizancjum to set
     */
    public static void setBizancjum(Osada aBizancjum) {
        bizancjum = aBizancjum;
    }

    /**
     * @return the pompeje
     */
    public static Osada getPompeje() {
        return pompeje;
    }

    /**
     * @param aPompeje the pompeje to set
     */
    public static void setPompeje(Osada aPompeje) {
        pompeje = aPompeje;
    }

    /**
     * @return the aquincum
     */
    public static Osada getAquincum() {
        return aquincum;
    }

    /**
     * @param aAquincum the aquincum to set
     */
    public static void setAquincum(Osada aAquincum) {
        aquincum = aAquincum;
    }

    /**
     * @return the syrakuzy
     */
    public static Osada getSyrakuzy() {
        return syrakuzy;
    }

    /**
     * @param aSyrakuzy the syrakuzy to set
     */
    public static void setSyrakuzy(Osada aSyrakuzy) {
        syrakuzy = aSyrakuzy;
    }

    /**
     * @return the ostia
     */
    public static Osada getOstia() {
        return ostia;
    }

    /**
     * @param aOstia the ostia to set
     */
    public static void setOstia(Osada aOstia) {
        ostia = aOstia;
    }

    /**
     * @return the volubilis
     */
    public static Osada getVolubilis() {
        return volubilis;
    }

    /**
     * @param aVolubilis the volubilis to set
     */
    public static void setVolubilis(Osada aVolubilis) {
        volubilis = aVolubilis;
    }

    /**
     * @return the traiana
     */
    public static Osada getTraiana() {
        return traiana;
    }

    /**
     * @param aTraiana the traiana to set
     */
    public static void setTraiana(Osada aTraiana) {
        traiana = aTraiana;
    }

    /**
     * @return the herkulanum
     */
    public static Osada getHerkulanum() {
        return herkulanum;
    }

    /**
     * @param aHerkulanum the herkulanum to set
     */
    public static void setHerkulanum(Osada aHerkulanum) {
        herkulanum = aHerkulanum;
    }

    /**
     * @return the random
     */
    public static Random getRandom() {
        return random;
    }

    /**
     * @param aRandom the random to set
     */
    public static void setRandom(Random aRandom) {
        random = aRandom;
    }

    /**
     * @return the koniec
     */
    public static boolean isKoniec() {
        return koniec;
    }

    /**
     * @param aKoniec the koniec to set
     */
    public static void setKoniec(boolean aKoniec) {
        koniec = aKoniec;
    }

    /**
     * @return the panelImperium
     */
    public static Mapa getPanelImperium() {
        return panelImperium;
    }

    /**
     * @param aPanelImperium the panelImperium to set
     */
    public static void setPanelImperium(Mapa aPanelImperium) {
        panelImperium = aPanelImperium;
    }

    /**
     * @return the wino
     */
    public static Surowce getWino() {
        return wino;
    }

    /**
     * @param aWino the wino to set
     */
    public static void setWino(Surowce aWino) {
        wino = aWino;
    }

    /**
     * @return the chleb
     */
    public static Surowce getChleb() {
        return chleb;
    }

    /**
     * @param aChleb the chleb to set
     */
    public static void setChleb(Surowce aChleb) {
        chleb = aChleb;
    }

    /**
     * @return the owoce
     */
    public static Surowce getOwoce() {
        return owoce;
    }

    /**
     * @param aOwoce the owoce to set
     */
    public static void setOwoce(Surowce aOwoce) {
        owoce = aOwoce;
    }

    /**
     * @return the drewno
     */
    public static Surowce getDrewno() {
        return drewno;
    }

    /**
     * @param aDrewno the drewno to set
     */
    public static void setDrewno(Surowce aDrewno) {
        drewno = aDrewno;
    }

    /**
     * @return the miedz
     */
    public static Surowce getMiedz() {
        return miedz;
    }

    /**
     * @param aMiedz the miedz to set
     */
    public static void setMiedz(Surowce aMiedz) {
        miedz = aMiedz;
    }

    /**
     * @return the ruda
     */
    public static Surowce getRuda() {
        return ruda;
    }

    /**
     * @param aRuda the ruda to set
     */
    public static void setRuda(Surowce aRuda) {
        ruda = aRuda;
    }

    /**
     * @return the zegar
     */
    public static Zegar getZegar() {
        return zegar;
    }

    /**
     * @param aZegar the zegar to set
     */
    public static void setZegar(Zegar aZegar) {
        zegar = aZegar;
    }

    /**
     * @return the zapis
     */
    public static Zapis getZapis() {
        return zapis;
    }

    /**
     * @param aZapis the zapis to set
     */
    public static void setZapis(Zapis aZapis) {
        zapis = aZapis;
    }

      
}
