/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package imperium;

import java.beans.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 *Klasa zapisująca i odczytująca top5 wyników
 */
public class Zapis {
    private ArrayList<Wynik> listaWynikow;
   
    private final String plikWynikow="wynik.xml";//plik XML do którego zapisywane są wyniki
    public Zapis(){
     
    
    }
    public void Zapisz() throws IOException{
        XMLDecoder d = null;
        //otwieranie pliku
        try {
            d = new XMLDecoder(new BufferedInputStream(new FileInputStream(plikWynikow)));
        } catch (FileNotFoundException e) {
            File f = new File(plikWynikow);
            try {
                f.createNewFile();
            } catch (IOException e2) {
            }
        }
        //tworzenie listy na podstawie pliku
        listaWynikow = new ArrayList<>();
        if(d != null) {
            listaWynikow = (ArrayList<Wynik>) d.readObject();
        }
        //zapisywanie do pliku
        XMLEncoder en = null;
        try {
            en = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(plikWynikow)));
            
        } catch (FileNotFoundException e3) {
        }
        if(en != null) {
            //dodawanie obecnego wyniku
            listaWynikow.add(new Wynik(Imperium.getZegar().getCzas(),Imperium.getInfo().jTextField1.getText()));
            //porównywanie wyników
            Collections.sort(listaWynikow);
           
            //usuwanie nadmiarowych danych
            if(listaWynikow.size() > 5)
                listaWynikow.remove(5);
            
            en.writeObject(listaWynikow);
            
            en.close();
        }
    
        }
    
    public void Otworz() throws IOException{
       //metoda otwiera i wypisuje listę top5 w panelu sterowania
       
        XMLDecoder d = null;
        try {
            d = new XMLDecoder(new BufferedInputStream(new FileInputStream(plikWynikow)));
        } catch (FileNotFoundException e) {
            File f = new File(plikWynikow);
            try {
                f.createNewFile();
            } catch (IOException e2) {
            }
        }
        
        listaWynikow = new ArrayList<>();
        if(d != null) {
            listaWynikow = (ArrayList<Wynik>) d.readObject();
        }
        try{
        Imperium.getInfo().setTekst2(" 1 " + listaWynikow.get(0).getImie() + " " + listaWynikow.get(0).getCzasGry());
        Imperium.getInfo().setTekst3(" 2 " + listaWynikow.get(1).getImie() + " " + listaWynikow.get(1).getCzasGry());
        Imperium.getInfo().setTekst3(" 3 " + listaWynikow.get(2).getImie() + " " + listaWynikow.get(2).getCzasGry());
        Imperium.getInfo().setTekst4(" 4 " + listaWynikow.get(3).getImie() + " " + listaWynikow.get(3).getCzasGry());
        Imperium.getInfo().setTekst5(" 5 " + listaWynikow.get(4).getImie() + " " + listaWynikow.get(4).getCzasGry());
        }catch(NullPointerException|IndexOutOfBoundsException e){};
        }

    /**
     * @return the listaWynikow
     */
    public ArrayList<Wynik> getListaWynikow() {
        return listaWynikow;
    }

    /**
     * @param listaWynikow the listaWynikow to set
     */
    public void setListaWynikow(ArrayList<Wynik> listaWynikow) {
        this.listaWynikow = listaWynikow;
    }
    
    
}
