/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imperium;



import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
/**
 *  Klasa tworząca ikonki postaci i osad a jednocześnie tworząca ich wątki
 * 
 */

public class Ikonka extends JLabel implements MouseListener,Runnable  {
    private final PanelSterowania ps =Imperium.getInfo();//panel sterowania
    private final Lokalizacja lok;//zmienna określająca lokalizacje obiektu do którego jest to ikonka
    private int i;// współrzędna x na mapie imperium
    private int j;//współrzędna y na mapie imperium
    private int predkosc;//prędkość ikonki
   
    private Lokalizacja celPodrozy;
    private final int szerokosc,wysokosc;//wymiary ikony
    private Handlowiec h;//3 zmienne wykorzystywane przy rzutowaniu lokalizacji podczas rysowania panelu sterowania i poruszania się ikonki
    private Barbarzynca b;
    private Zolnierz z;
    public Ikonka(Lokalizacja lok){
//        o13=(Osada)obecnaLokalizacja();
         switch (lok.getClass().toString()) {//ustalanie prędkości
            case "class imperium.Handlowiec":
                h =(Handlowiec)lok;
                this.predkosc = h.getPredkoscWozu();
                
                break;
            case "class imperium.Barbarzynca":
               
                this.predkosc = 30;
                break;
            
            case "class imperium.Zolnierz":
                
              
                this.predkosc = 15;
                break;
            default:
                
                break;
        }
        this.i = lok.getWartoscX();
        this.j = lok.getWartoscY();
    this.lok=lok;
    
    this.setVisible(true);
    
    
   ImageIcon ikona1 = new ImageIcon(lok.getObraz());//tworzenie ikonki
   this.setIcon(ikona1);
   if(lok instanceof Handlowiec){
        Handlowiec h = (Handlowiec)lok;
        szerokosc=20;
        wysokosc=20;
   } else{
    szerokosc=ikona1.getIconWidth();
   wysokosc=ikona1.getIconHeight();
   }
   
  
   this.setBounds(lok.getWartoscX(),lok.getWartoscY(),szerokosc,wysokosc);//ustalanielokalizacji na mapie
   Imperium.getPanelImperium().getMatrix()[lok.getWartoscX()][lok.getWartoscY()]=2; 
    Imperium.getPanelImperium().repaint();
    this.addMouseListener(this);//dodawanie listenera
    
     
    
    }
      

    @Override
    public void mousePressed(MouseEvent me) {
        
     
    } 
    /**
 *
 * reagowanie na kliknięcie na ikonkę
 */
    @Override
    public void mouseClicked(MouseEvent me) {
        Imperium.getInfo().zerujTeksty();
        //rozpoznawanie klasy i rysowanie panelu informacyjnego w zależności od klasy
      if(getLok() instanceof Handlowiec){
          
        Handlowiec h = (Handlowiec)getLok();
            getPs().setTekst2("Obiekt");
            getPs().setTekst3("Handlowiec");
            getPs().setTekst4("Imie i nazwisko");
            getPs().setTekst5(h.getImie()+" " +h.getNazwisko());
            getPs().setTekst6("Predkosc wozu");
            getPs().setTekst7(new Integer(h.getPredkoscWozu()).toString());
            getPs().setTekst8("Maksymalna pojemnosc");
            getPs().setTekst9(new Integer(h.getMaxPojemnoscWozu()).toString());
            getPs().setTekst10("Przewożone surowce");
            ArrayList<Surowce> lista=h.getListaPrzewozonychSurowcow();
            for (Surowce s : lista) {
                getPs().setTekst11(s.getTyp());
                getPs().setTekst12(new Integer(s.getID()).toString());
                getPs().setTekst13(new Integer(s.getWaga()).toString());
          }
            
            getPs().jButton2.setVisible(true);
            getPs().jButton5.setVisible(true);
            getPs().jButton1.setText("Usuń");
            getPs().jButton5.setText("Zepsuj wóz");
            getPs().jButton2.setText("Napraw wóz");
            getPs().jButton1.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    if(getLok() instanceof Handlowiec){
                        getLok().usun();//usuwanie ikony
                }
                }
            });
            getPs().jButton5.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    if(getLok() instanceof Handlowiec){
                        ((Handlowiec)getLok()).setStanWozu(false);//zepsucie wozu
                        repaint();
                }
                }
            });
             getPs().jButton2.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    if(getLok() instanceof Handlowiec){
                        ((Handlowiec)getLok()).setStanWozu(true);//naprawienie wozu
                        repaint();
                }
                }
            });
            getPs().jButton1.setVisible(true);
        
       } else if(getLok() instanceof Osada){
           rysujOsada();//rysowanie Osady w oddzielnej metodzie
           
    }
      else if(getLok() instanceof Barbarzynca){
        Barbarzynca h = (Barbarzynca)getLok();
            getPs().setTekst2("Obiekt");
            getPs().setTekst3("Barbarzyńca");
            getPs().setTekst4("Nazwa");
            getPs().setTekst5(h.getNazwa());
            getPs().setTekst6("Liczba osób");
            getPs().setTekst7(new Integer(h.getLiczbaOsob()).toString());
            
            getPs().setTekst8("Typ uzbrojenia");
            getPs().setTekst9(h.getBron());
            getPs().setTekst10("");
            getPs().setTekst11("");
            getPs().jButton1.setVisible(false);
             getPs().jButton2.setVisible(true);
               getPs().jButton2.setText("Usuń");
            getPs().jButton2.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    
                    getLok().usun();
                
                }
            });
            
          
    } else if(getLok() instanceof Zolnierz){
        Zolnierz z = (Zolnierz)getLok();
            getPs().setTekst2("Obiekt");
            getPs().setTekst3("Zolnierz");
            getPs().setTekst4("Cel ");
            getPs().setTekst5(((Osada)celPodrozy).getNazwa());
            getPs().setTekst6("");
            getPs().setTekst7("");
            getPs().setTekst8("");
            getPs().setTekst9("");
            getPs().setTekst10("");
            
           
             getPs().setTekst11("");
             getPs().setTekst12("");
             getPs().setTekst13("");
              getPs().jButton2.setText("Usuń");
            getPs().jButton2.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    
                    getLok().usun();
                
                }
            });
        
            
            getPs().jButton2.setVisible(true);  
         
            getPs().jButton1.setVisible(false);
        
       }
           
    }
   
    

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        
    }

    @Override
    public void mouseExited(MouseEvent me) {
         
    }
/**
 *
 * działanie wątku
 */
    @Override
    public void run() {
       
        while(!lok.isDoUsuniecia()){
        jedz(celPodrozy);
//            
          switch (getLok().getClass().toString()) {//obsługa postaci gdy już dotrą do lokalizacji docelowej
            case "class imperium.Handlowiec":
                setH((Handlowiec) getLok());
                 getH().kupuj((Osada)obecnaLokalizacja());
                
                getH().sprzedaj((Osada)obecnaLokalizacja());
                
                getH().aktualnaPojWozu();
                getH().setPredkoscWozu(getH().aktualnaPojWozu()+20);
               
                //losowanie kolejnego celu podróży 
                Osada osadaLosowa=Imperium.getListaOsad().get(Imperium.getRandom().nextInt(Imperium.getListaOsad().size()));
                while(osadaLosowa.equals((Osada)obecnaLokalizacja())) osadaLosowa=Imperium.getListaOsad().get(Imperium.getRandom().nextInt(Imperium.getListaOsad().size()));
                setCelPodrozy(osadaLosowa);
                
                break;
            case "class imperium.Barbarzynca":
             setB((Barbarzynca) getLok());  
           ((Osada)obecnaLokalizacja()).setPodbite(true);//pokazuje że miasto zostaje podbite,więc przestaje produkować surowce i zaczyna tracic ludnosc
           
           Lokalizacja o2=getB().najblizszaOsada(obecnaLokalizacja());
           //ustalanie kolejnego celuPodróży
           
           if(((Osada)o2).isPodbite()){
           o2=Imperium.getListaOsad().get(Imperium.getRandom().nextInt(Imperium.getListaOsad().size()));
         
           }
           setCelPodrozy(o2);
                break;
            
            case "class imperium.Zolnierz":
             setZ((Zolnierz) getLok());
             if((obecnaLokalizacja() instanceof Osada)&&((Osada)obecnaLokalizacja()).isPodbite())((Osada)obecnaLokalizacja()).setPodbite(false);//odbijanie miasta od barbarzyncow
            try{ 
                //ustalanie lokalizacji docelowej jako najbliższą osadę barbarzyncy
                Lokalizacja o3=getZ().najblizszyBarbarzynca();
                Lokalizacja o4=o3.najblizszaOsada(o3);
                if(o4==obecnaLokalizacja())setCelPodrozy(o3);
                        else setCelPodrozy(o4);
             
// setCelPodrozy(o3.najblizszaOsada(obecnaLokalizacja()));
                }catch(IndexOutOfBoundsException e){//w przypadku gdy nie ma barbarzyncy cel podróży to podbita osada, a jeśli takowych nie ma to stolica
                    for(Osada o:Imperium.getListaOsad()){
                    if(o.isPodbite()){setCelPodrozy(o);break;}
                    else setCelPodrozy(Imperium.getRzym());
               
                    }
                   
                }
             //  System.out.println(((Osada)celPodrozy).getNazwa());
                            
                break;
            default:
                
                break;
        } 
        
         
       
        
        
       
        }    
        
        
    }
    /**
     * @return the celPodrozy
     */
    public Lokalizacja getCelPodrozy() {
        return celPodrozy;
    }

    /**
     * @param celPodrozy the celPodrozy to set
     */
    public void setCelPodrozy(Lokalizacja celPodrozy) {
        this.celPodrozy = celPodrozy;
        
        
       
        
        
    }    
 /**
 *poruszanie się ikony na mapie
 * 
 */
    public void jedz(Lokalizacja celTrasy){
        celTrasy=celPodrozy;
       
       
        
         
        while(!lok.isDoUsuniecia()){
        
        
        
       if(obecnaLokalizacja()==celTrasy)break;//przerwanie pętli po dojechaniu do celu
       if(lok instanceof Handlowiec){
           while(((Handlowiec)lok).getStanWozu()==false){//wstrzymywanie handlowca gdy ma zepsuty wóz
          ((Handlowiec)lok).setPredkoscWozu(100000000);
               try {
               Thread.sleep(10);
              
               
           } catch (InterruptedException ex) {
               Logger.getLogger(Ikonka.class.getName()).log(Level.SEVERE, null, ex);
           }
           }
           ((Handlowiec)lok).setPredkoscWozu(((Handlowiec)lok).aktualnaPojWozu()+20);
         };
      
        try{//już włąściwa metoda
           //sprawdzanie czy narysowana jest trasa którą możnaby jechać
            if(Imperium.getPanelImperium().getMatrixKierunkow()[getI()][getJ()]==Kierunki.prawo){
                jedzPrawo();
            }
            else if(Imperium.getPanelImperium().getMatrixKierunkow()[getI()][getJ()]==Kierunki.lewo){
            jedzLewo();
            }
            else if(Imperium.getPanelImperium().getMatrixKierunkow()[getI()][getJ()]==Kierunki.góra){
                
            jedzGora();}
                
                
            else if(Imperium.getPanelImperium().getMatrixKierunkow()[getI()][getJ()]==Kierunki.dół){
            
                jedzDol();
                }
            else if(Imperium.getPanelImperium().getMatrixKierunkow()[getI()][getJ()]==Kierunki.skrzyzowanie){obslugaSkrzyzowan();}
            else if(Imperium.getPanelImperium().getMatrixKierunkow()[getI()][getJ()]==Kierunki.miasto){
              //zachowanie w przypadku przejazdu przez miasto które nie jest ostatecznym celemPodróży
                if( getLok() instanceof Handlowiec){
              
                obslugaSkrzyzowan();
                }else 
                    if(getLok() instanceof Barbarzynca){
                ((Osada)obecnaLokalizacja()).setPodbite(true);
        
                obslugaSkrzyzowan();
                }else  if(getLok() instanceof Zolnierz){
                ((Osada)obecnaLokalizacja()).setPodbite(false);
                obslugaSkrzyzowan();
            }}
             
        }catch(NullPointerException e){
           
        //w tym jednym miejscu pojawiał mi się często wyjątek dla barbarzyńcy stąd manualne rozwiązanie
        if((    getLok() instanceof Barbarzynca)&&(getI()==810)&&(getJ()==610)){
            if(celPodrozy==Imperium.getOstia()){setI(840);setJ(609);}
            
            else {      setI(809);setJ(640);}
            Imperium.getPompeje().setPodbite(true);
        }

        }
        
       
        repaint();
        }
            
              
            
            
            
            
            
            
        
    
    
    }
    /**
 *zwraca Osadę będąca obecną lokalizacją ikony
 * 
 */
    public Lokalizacja obecnaLokalizacja(){
   
    if( getI()==810&&getJ()==10)return Imperium.getBizancjum();
    if( getI()==10&&getJ()==10)return Imperium.getKartagina();
    if( getI()==810&&getJ()==10)return Imperium.getPompeje();
    if( getI()==510&&getJ()==610)return Imperium.getSyrakuzy();
    if( getI()==440&&getJ()==270)return Imperium.getRzym();
    if( getI()==440&&getJ()==60)return Imperium.getTraiana();
    if( getI()==10&&getJ()==610)return Imperium.getAquincum();
    if( getI()==810&&getJ()==360)return Imperium.getOstia();
    if( getI()==210&&getJ()==610)return Imperium.getVolubilis();
    if( getI()==10&&getJ()==360)return Imperium.getHerkulanum();
    if(getI()==Imperium.getSyrakuzy().getWartoscX()&&getJ()==Imperium.getSyrakuzy().getWartoscY())return Imperium.getSyrakuzy();
    if(getI()==Imperium.getOstia().getWartoscX()&&getJ()==Imperium.getOstia().getWartoscY())return Imperium.getOstia();
    if(getI()==Imperium.getPompeje().getWartoscX()&&getJ()==Imperium.getPompeje().getWartoscY())return Imperium.getPompeje();
    else return null;
    };
     /**
 *manualnie wprowadzone rozwiązania dla skrzyżowań w zależności od celuPodróży postaci
 * 
 */
    public void obslugaSkrzyzowan(){
        Lokalizacja celTrasy=celPodrozy;
     
        
       if((getI()==10&getJ()==610)||(getI()==10&getJ()==640)){
         
           
          if (celTrasy==Imperium.getAquincum()) {setI(10);setJ(610);}
          else{ setI(11);setJ(610);}
         
      } 
      if((getI()==470&getJ()==40)||(getI()==440&getJ()==40)||(getI()==470&getJ()==10)){
          if(celTrasy==Imperium.getBizancjum()){setI(471);setJ(40);}
          else if (celTrasy==Imperium.getKartagina()) {setI(439);setJ(10);}else{setI(440);setJ(41);};
      }
      if((getI()==440&getJ()==60)||(getI()==470&getJ()==60)){
          if(celTrasy==Imperium.getBizancjum()||celTrasy==Imperium.getKartagina()){setI(470);setJ(59);}
          else if (celTrasy==Imperium.getTraiana()) {setI(440);setJ(60);}else{setI(440);setJ(61);};
      }
      if((getI()==440&getJ()==270)||(getI()==470&getJ()==270)){
          if(celTrasy==Imperium.getBizancjum()||celTrasy==Imperium.getTraiana()||celTrasy==Imperium.getKartagina()){setI(470);setJ(269);}
          else if (celTrasy==Imperium.getRzym()) {setI(440);setJ(270);}else{setI(440);setJ(271);}
      }
      if((getI()==440&getJ()==360)||(getI()==470&getJ()==390)||(getI()==470&getJ()==360)){
          if(celTrasy==Imperium.getHerkulanum()||celTrasy==Imperium.getAquincum()){setI(439);setJ(390);}
          else if (celTrasy==Imperium.getOstia()||celTrasy==Imperium.getPompeje()) {setI(471);setJ(390);}
          else if (celTrasy==Imperium.getBizancjum()||celTrasy==Imperium.getTraiana()||celTrasy==Imperium.getKartagina()||celTrasy==Imperium.getRzym()){setI(470);setJ(359);}
          else {setI(440);setJ(391);};
      }
   
      
       if(getI()==210&getJ()==610){
          if(celTrasy==Imperium.getAquincum()){setI(209);setJ(640);}
          else{ setI(211);setJ(640);}
         
      }
        if((getI()==470&getJ()==640)||(getI()==440&getJ()==610)){
          if(celTrasy==Imperium.getVolubilis()||celTrasy==Imperium.getAquincum()){setI(439);setJ(610);}
          else if (celTrasy==Imperium.getSyrakuzy()||celTrasy==Imperium.getPompeje()) {setI(471);setJ(640);}
          else  {setI(470);setJ(609);};
      }
        if((getI()==510&getJ()==640)||(getI()==510&getJ()==610)){
          if(celTrasy==Imperium.getPompeje()||celTrasy==Imperium.getOstia()){setI(511);setJ(610);}
          else if(celTrasy==Imperium.getSyrakuzy())setJ(610);
          else{ setI(509);setJ(610);}
         
      }
         if((getI()==810&getJ()==390)||(getI()==810&getJ()==360)||(getI()==840&getJ()==360)){
          if(celTrasy==Imperium.getOstia()){setI(810);setJ(360);}
          else if (celTrasy==Imperium.getSyrakuzy()||celTrasy==Imperium.getPompeje()) {setI(810);setJ(391);}
          else{ setI(809);setJ(360);}
         
      }
         if((getI()==10&getJ()==10)||(getI()==10&getJ()==40)){
         
             if(celTrasy==Imperium.getBizancjum()||celTrasy==Imperium.getTraiana()||celTrasy==Imperium.getRzym()){setI(11);setJ(40);}
          else if (celTrasy==Imperium.getKartagina()) {setI(10);setJ(10);}
          else{ setI(10);setJ(41);}
         
      } 
         if((getI()==10&getJ()==390)||(getI()==10&getJ()==360)){
         
             if(celTrasy==Imperium.getHerkulanum()){setI(10);setJ(359);}
          else if (celTrasy==Imperium.getAquincum()) {setI(10);setJ(391);}
          else{ setI(11);setJ(360);}
         
      } 
         if((getI()==810&getJ()==10)||(getI()==810&getJ()==40))
         {
             if(celTrasy==Imperium.getKartagina()||celTrasy==Imperium.getTraiana()||celTrasy==Imperium.getRzym()){setI(809);setJ(10);}
          else if (celTrasy==Imperium.getBizancjum()) {setI(810);setJ(10);}
          else{ setI(810);setJ(41);}
         
      }  if((getI()==810&getJ()==610))//||(i==810&j==360)){
         {
             
          if (celTrasy==Imperium.getPompeje()) {setI(810);setJ(610);}
          else if (celTrasy==Imperium.getOstia()) {setI(840);setJ(609);}
          else{ setI(809);setJ(640);}
         
      }
         
        
        
        
    }
    /**
    *Funkcje do jazdy w górę, dół, prawo i lewo
    *dla jazdy w lewo sprawdzam czy współrzędna x pomniejszona o 1 ma zapisaną trasę w lewo dla współrzędnej y lub współrzędnej y + pas drogi
    *dla pozostałych poniższych metod analogicznie, czas Thread.sleep zależy od prędkości
    */ 
    public void jedzLewo(){
    if(Imperium.getPanelImperium().getMatrixKierunkow()[getI()-1][getJ()]==Kierunki.lewo||Imperium.getPanelImperium().getMatrixKierunkow()[getI()-1][getJ()]==Kierunki.skrzyzowanie||Imperium.getPanelImperium().getMatrixKierunkow()[getI()-1][getJ()]==Kierunki.miasto){
                    // System.out.println("lewo");                  
                  setI(getI() - 1);
                  getLok().setX(getI());
                  this.setBounds(getI(), getLok().getWartoscY(), getSzerokosc(), getWysokosc());
                  try {
                  Thread.sleep(getPredkosc());
                  } catch (InterruptedException ex) {
                  Logger.getLogger(Ikonka.class.getName()).log(Level.SEVERE, null, ex);
                  }}
     else if(Imperium.getPanelImperium().getMatrixKierunkow()[getI()-1][getJ()+Imperium.getPanelImperium().getPas()]==Kierunki.lewo||Imperium.getPanelImperium().getMatrixKierunkow()[getI()-1][getJ()+Imperium.getPanelImperium().getPas()]==Kierunki.skrzyzowanie||Imperium.getPanelImperium().getMatrixKierunkow()[getI()-1][getJ()+Imperium.getPanelImperium().getPas()]==Kierunki.miasto){
                  setJ(getJ() + Imperium.getPanelImperium().getPas());
                  setI(getI() - 1);
                     
                  getLok().setX(getI());
                  this.setBounds(getI(), getLok().getWartoscY(), getSzerokosc(), getWysokosc());
                   try {
                Thread.sleep(getPredkosc());
            } catch (InterruptedException ex) {
                Logger.getLogger(Ikonka.class.getName()).log(Level.SEVERE, null, ex);
            }}
        
      
    
    }
    public void jedzPrawo(){
        
        if(Imperium.getPanelImperium().getMatrixKierunkow()[getI()+1][getJ()]==Kierunki.prawo||Imperium.getPanelImperium().getMatrixKierunkow()[getI()+1][getJ()]==Kierunki.skrzyzowanie||Imperium.getPanelImperium().getMatrixKierunkow()[getI()+1][getJ()]==Kierunki.miasto){
               //    System.out.println("prawo");
                  setI(getI() + 1);
                  getLok().setX(getI());
                  this.setBounds(getI(),getLok().getWartoscY()+Imperium.getPanelImperium().getPas()/2, getSzerokosc(), getWysokosc());
                  repaint();
                   try {
                Thread.sleep(getPredkosc());
            } catch (InterruptedException ex) {
                Logger.getLogger(Ikonka.class.getName()).log(Level.SEVERE, null, ex);
            }}
                else if(Imperium.getPanelImperium().getMatrixKierunkow()[getI()+1][getJ()+Imperium.getPanelImperium().getPas()]==Kierunki.prawo||Imperium.getPanelImperium().getMatrixKierunkow()[getI()+1][getJ()+Imperium.getPanelImperium().getPas()]==Kierunki.skrzyzowanie||Imperium.getPanelImperium().getMatrixKierunkow()[getI()+1][getJ()+Imperium.getPanelImperium().getPas()]==Kierunki.miasto){
                    setJ(getJ() + Imperium.getPanelImperium().getPas());
                    setI(getI() + 1);
                //     System.out.println("prawo");
                 
                  getLok().setX(getI());
                  this.setBounds(getI(),getLok().getWartoscY()+Imperium.getPanelImperium().getPas()/2, getSzerokosc(), getWysokosc());
                  repaint();
                   try {
                Thread.sleep(getPredkosc());
            } catch (InterruptedException ex) {
                Logger.getLogger(Ikonka.class.getName()).log(Level.SEVERE, null, ex);
            }}}
         
    public void jedzGora(){  
         if(Imperium.getPanelImperium().getMatrixKierunkow()[getI()][getJ()-1]==Kierunki.góra||Imperium.getPanelImperium().getMatrixKierunkow()[getI()][getJ()-1]==Kierunki.skrzyzowanie||Imperium.getPanelImperium().getMatrixKierunkow()[getI()][getJ()-1]==Kierunki.miasto){
                   //System.out.println("góra");
                  setJ(getJ() - 1);
                  getLok().setY(getJ());
                  this.setBounds(getLok().getWartoscX()+Imperium.getPanelImperium().getPas()/2, getJ(), getSzerokosc(), getWysokosc());
                   try {
                Thread.sleep(getPredkosc());
            } catch (InterruptedException ex) {
                Logger.getLogger(Ikonka.class.getName()).log(Level.SEVERE, null, ex);
            }}
                else if(Imperium.getPanelImperium().getMatrixKierunkow()[getI()+Imperium.getPanelImperium().getPas()][getJ()-1]==Kierunki.góra||Imperium.getPanelImperium().getMatrixKierunkow()[getI()+Imperium.getPanelImperium().getPas()][getJ()-1]==Kierunki.skrzyzowanie||Imperium.getPanelImperium().getMatrixKierunkow()[getI()+Imperium.getPanelImperium().getPas()][getJ()-1]==Kierunki.miasto){
                    setI(getI() + Imperium.getPanelImperium().getPas());
                    setJ(getJ() - 1);
                   //System.out.println("góra");
                  setJ(getJ() - 1);
                  getLok().setY(getJ());
                  this.setBounds(getLok().getWartoscX()+Imperium.getPanelImperium().getPas()/2, getJ(), getSzerokosc(), getWysokosc());
                   try {
                Thread.sleep(getPredkosc());
            } catch (InterruptedException ex) {
                Logger.getLogger(Ikonka.class.getName()).log(Level.SEVERE, null, ex);
            }}
        
        
    } 
    public void jedzDol(){
              if(Imperium.getPanelImperium().getMatrixKierunkow()[getI()][getJ()+1]==Kierunki.dół||Imperium.getPanelImperium().getMatrixKierunkow()[getI()][getJ()+1]==Kierunki.skrzyzowanie||Imperium.getPanelImperium().getMatrixKierunkow()[getI()][getJ()+1]==Kierunki.miasto){
                    // System.out.println("dół");
                  setJ(getJ() + 1);
                  getLok().setY(getJ());
                  this.setBounds(getLok().getWartoscX(), getJ(), getSzerokosc(), getWysokosc());
                   try {
                Thread.sleep(getPredkosc());
            } catch (InterruptedException ex) {
                Logger.getLogger(Ikonka.class.getName()).log(Level.SEVERE, null, ex);
            }}
              else if(getI()>Imperium.getPanelImperium().getPas()+1){ 
                  
                  
                  if(Imperium.getPanelImperium().getMatrixKierunkow()[getI()-Imperium.getPanelImperium().getPas()][getJ()+1]==Kierunki.dół||Imperium.getPanelImperium().getMatrixKierunkow()[getI()-Imperium.getPanelImperium().getPas()][getJ()+1]==Kierunki.skrzyzowanie||Imperium.getPanelImperium().getMatrixKierunkow()[getI()-Imperium.getPanelImperium().getPas()][getJ()+1]==Kierunki.miasto){
                    setI(getI() - Imperium.getPanelImperium().getPas());
                    setJ(getJ() + 1);
                 //    System.out.println("dół");
                  setJ(getJ() + 1);
                  getLok().setY(getJ());
                  this.setBounds(getLok().getWartoscX(), getJ(), getSzerokosc(), getWysokosc());
                   try {
                Thread.sleep(getPredkosc());
            } catch (InterruptedException ex) {
                Logger.getLogger(Ikonka.class.getName()).log(Level.SEVERE, null, ex);
            }}
              
              
              
              
              
              }
       
    
    
    }
 /**
 *metoda do rysowania w panelu informacyjnym informacji o osadzie
 * 
 */
  
    public void rysujOsada(){
    
    Osada h = (Osada)getLok(); 
    try{
           Surowce s=h.getProdukowanySurowiec().get(0);
            getPs().setTekst12("typ surowca kupowanego " + s.getTyp());
             getPs().setTekst13("ID "+s.getID());
    }   catch(NullPointerException|IndexOutOfBoundsException e){
            getPs().setTekst12("brak surowca");
        }
    try{
           Surowce s=h.getKupowanySurowiec().get(0);
            getPs().setTekst15("typ surowca produkowanego " + s.getTyp());
             getPs().setTekst16("ID "+s.getID());
    }   catch(NullPointerException|IndexOutOfBoundsException e){
            getPs().setTekst15("brak surowca");
        }
        
           Integer ilosc1=0;
           //for (Surowce s : lista) {
            ilosc1=h.aktualnaIloscSurowcaProdukowanego();
           Integer ilosc=0;
           //for (Surowce s : lista) {
            ilosc=h.aktualnaIloscSurowcaKupowanego();
        
            getPs().setTekst2("Obiekt");
            if(lok instanceof Stolica)getPs().setTekst3("Stolica");
            else getPs().setTekst3("Osada");
            getPs().setTekst4("Nazwa");
            if(h.isPodbite())getPs().setTekst5(h.getNazwa()+ " podbite przez Barbarzyncow");
            else getPs().setTekst5(h.getNazwa());
            getPs().setTekst6("Ludność");
            getPs().setTekst7(new Integer(h.getLudnosc()).toString());
            getPs().setTekst8("Maksymalna pojemnosc magazynów");
            getPs().setTekst9(new Integer(h.getMaxStanMagazynow()).toString());
            getPs().setTekst10("Aktualna pojemność magazynów");
            getPs().setTekst11(new Integer(h.aktualnyStanMagazynu()).toString());
           
            //for (Surowce s : lista) {
             
             getPs().setTekst14("objetosc "+ilosc);
             getPs().setTekst17("objetosc "+ilosc1);
          //}
             
            getPs().jButton1.setVisible(false);
           getPs().jButton2.setVisible(false);
           getPs().jButton7.setText("Produkuj");
           getPs().jButton7.setVisible(true);
           getPs().jButton7.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {
                    if(getLok() instanceof Osada){
                       
                        ((Osada)getLok()).produkujSurowce();
                        Imperium.getInfo().repaint();
                }
                }
            });
    
    
    
    }

    /**
     * @return the ps
     */
    public PanelSterowania getPs() {
        return ps;
    }

    /**
     * @return the lok
     */
    public Lokalizacja getLok() {
        return lok;
    }

    /**
     * @return the i
     */
    public int getI() {
        return i;
    }

    /**
     * @param i the i to set
     */
    public void setI(int i) {
        this.i = i;
    }

    /**
     * @return the j
     */
    public int getJ() {
        return j;
    }

    /**
     * @param j the j to set
     */
    public void setJ(int j) {
        this.j = j;
    }

    /**
     * @return the predkosc
     */
    public int getPredkosc() {
        return predkosc;
    }

    /**
     * @param predkosc the predkosc to set
     */
    public void setPredkosc(int predkosc) {
        this.predkosc = predkosc;
    }

    /**
     * @return the szerokosc
     */
    public int getSzerokosc() {
        return szerokosc;
    }

    /**
     * @return the wysokosc
     */
    public int getWysokosc() {
        return wysokosc;
    }

    /**
     * @return the h
     */
    public Handlowiec getH() {
        return h;
    }

    /**
     * @param h the h to set
     */
    public void setH(Handlowiec h) {
        this.h = h;
    }

    /**
     * @return the b
     */
    public Barbarzynca getB() {
        return b;
    }

    /**
     * @param b the b to set
     */
    public void setB(Barbarzynca b) {
        this.b = b;
    }

    /**
     * @return the z
     */
    public Zolnierz getZ() {
        return z;
    }

    /**
     * @param z the z to set
     */
    public void setZ(Zolnierz z) {
        this.z = z;
    }
} 
     
    
    
   
    

