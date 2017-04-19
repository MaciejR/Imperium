
package imperium;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * Mapa Imperium, tło symulacji
 */




public class Mapa extends JPanel{
   /**
   * mapa, potrzebna do odpowiedniego narysowania tła
   */
     private int[][] matrix;
     
   /**
   * mapa kierunków, potrzebna do odpowiedniego poruszania się
   */
     private Kierunki[][] matrixKierunkow;
   /**
   * szerokość pasa na trasach dwukierunkowych
   */  
   private int pas=30;
     /**
   * szerokość mapy
   */
  private int rozmiarx;
  /**
   * wysokość mapy
   */
  private int rozmiary;
   /**
   * zmienna określająca kolor mapy
   */
  private Color kolor;
   /**
   * wielkość kwadrata będącego najmniejszą częśćią kolorowanej mapy
   */
  private int square = 1 ;
  
     public Mapa(int x, int y) {
      this.matrix = new int[x][y];
      this.matrixKierunkow= new Kierunki[x][y];
      this.rozmiarx=x;
      this.rozmiary=y;
      
      
      
      
  }
    
     
     
    @Override
     protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        for (int i=0;i<getRozmiarx();i++) {
          for (int j=0;j<getRozmiary();j++) {
              switch (getMatrix()[i][j]) {
                  case 0: setKolor(Color.green); break;//tło
                  case 1: setKolor(Color.black); break;//trasy 2kierunkowe
                  case 2: setKolor(Color.RED); break;//trasy 1kierunkowe
              }
             
              g.setColor(getKolor());
              g.fillRect(getSquare()*i, getSquare()*j, getSquare(), getSquare());
          }
        }
    }
     /**
   * rysowanie tras dwukierunkowych
   */
    public void rysujTrasyDwukierunkowe(Lokalizacja lok,Lokalizacja cel){
        int a = lok.getWartoscX();
        int b =lok.getWartoscY();
      
        
        
        while(a <cel.getWartoscX()){
            getMatrix()[a][b]=1;
            getMatrixKierunkow()[a][b]=Kierunki.prawo;
            getMatrix()[a][b+getPas()]=1;
            getMatrixKierunkow()[a][b+getPas()]=Kierunki.lewo;
       a+=1;
      
     
      }
            while(a>cel.getWartoscX()){
      
            getMatrix()[a][b]=1;
            getMatrixKierunkow()[a][b]=Kierunki.lewo;
            getMatrix()[a][b+getPas()]=1;
            getMatrixKierunkow()[a][b+getPas()]=Kierunki.prawo; 
      a-=1;
      
     
      }
       while(b <cel.getWartoscY()){
 
            getMatrix()[a][b]=1;
            getMatrixKierunkow()[a][b]=Kierunki.dół;
            getMatrix()[a+getPas()][b]=1;
            getMatrixKierunkow()[a+getPas()][b]=Kierunki.góra;
       b+=1;
 
       
     
      }
       while(b >cel.getWartoscY()){
            getMatrix()[a+getPas()][b]=1;
            getMatrixKierunkow()[a+getPas()][b]=Kierunki.góra;
            getMatrix()[a][b]=1;
            getMatrixKierunkow()[a][b]=Kierunki.dół;
       b-=1;
       
     
      }
       
      this.repaint();   
         
            
      }
   
    /**
   *rysowanie tras jednokierunkowych
   */
    
    
     public void rysujTrasyJednokierunkowe(Lokalizacja lok,Lokalizacja cel){
      int a = lok.getWartoscY();
      int b = cel.getWartoscY();
      for(int i=a;i<b;i++){
            getMatrixKierunkow()[lok.getWartoscX()][i]=Kierunki.dół;
            getMatrix()[lok.getWartoscX()][i]=2;
      };
         
         
      this.repaint();   
         
            
      }
     /**
   * manualne wprowadzenie obsługi skrzyżowań w danych miejscach
   */
     public void Skrzyzowania(){
         
     
         Imperium.getPanelImperium().getMatrixKierunkow()[440][610]=Kierunki.skrzyzowanie;
        Imperium.getPanelImperium().getMatrixKierunkow()[810][40]=Kierunki.skrzyzowanie;
        Imperium.getPanelImperium().getMatrixKierunkow()[10][390]=Kierunki.skrzyzowanie;
        Imperium.getPanelImperium().getMatrixKierunkow()[510][640]=Kierunki.skrzyzowanie;
        Imperium.getPanelImperium().getMatrixKierunkow()[840][360]=Kierunki.skrzyzowanie;
        Imperium.getPanelImperium().getMatrixKierunkow()[10][640]=Kierunki.skrzyzowanie;
        Imperium.getPanelImperium().getMatrixKierunkow()[Imperium.getRzym().getWartoscX()][Imperium.getBizancjum().getWartoscY()]=Kierunki.skrzyzowanie;
        Imperium.getPanelImperium().getMatrixKierunkow()[Imperium.getRzym().getWartoscX()][Imperium.getBizancjum().getWartoscY()+Imperium.getPanelImperium().getPas()]=Kierunki.skrzyzowanie;
        Imperium.getPanelImperium().getMatrixKierunkow()[Imperium.getRzym().getWartoscX()+Imperium.getPanelImperium().getPas()][Imperium.getBizancjum().getWartoscY()]=Kierunki.skrzyzowanie;
        Imperium.getPanelImperium().getMatrixKierunkow()[Imperium.getRzym().getWartoscX()+Imperium.getPanelImperium().getPas()][Imperium.getBizancjum().getWartoscY()+Imperium.getPanelImperium().getPas()]=Kierunki.skrzyzowanie;
        Imperium.getPanelImperium().getMatrixKierunkow()[Imperium.getRzym().getWartoscX()][Imperium.getHerkulanum().getWartoscY()]=Kierunki.skrzyzowanie;
        Imperium.getPanelImperium().getMatrixKierunkow()[Imperium.getRzym().getWartoscX()][Imperium.getHerkulanum().getWartoscY()+Imperium.getPanelImperium().getPas()]=Kierunki.skrzyzowanie;
        Imperium.getPanelImperium().getMatrixKierunkow()[Imperium.getRzym().getWartoscX()+Imperium.getPanelImperium().getPas()][Imperium.getHerkulanum().getWartoscY()]=Kierunki.skrzyzowanie;
        Imperium.getPanelImperium().getMatrixKierunkow()[Imperium.getKartagina().getWartoscX()][Imperium.getKartagina().getWartoscY()+Imperium.getPanelImperium().getPas()]=Kierunki.skrzyzowanie;
        Imperium.getPanelImperium().getMatrixKierunkow()[Imperium.getRzym().getWartoscX()+Imperium.getPanelImperium().getPas()][Imperium.getOstia().getWartoscY()+Imperium.getPanelImperium().getPas()]=Kierunki.skrzyzowanie;
        Imperium.getPanelImperium().getMatrixKierunkow()[Imperium.getOstia().getWartoscX()][Imperium.getOstia().getWartoscY()+Imperium.getPanelImperium().getPas()]=Kierunki.skrzyzowanie;
        Imperium.getPanelImperium().getMatrixKierunkow()[Imperium.getHerkulanum().getWartoscX()][Imperium.getHerkulanum().getWartoscY()+Imperium.getPanelImperium().getPas()]=Kierunki.skrzyzowanie;
        Imperium.getPanelImperium().getMatrixKierunkow()[Imperium.getOstia().getWartoscX()+Imperium.getPanelImperium().getPas()][Imperium.getOstia().getWartoscY()]=Kierunki.skrzyzowanie;
        Imperium.getPanelImperium().getMatrixKierunkow()[Imperium.getRzym().getWartoscX()+Imperium.getPanelImperium().getPas()][Imperium.getRzym().getWartoscY()]=Kierunki.skrzyzowanie;
        Imperium.getPanelImperium().getMatrixKierunkow()[Imperium.getRzym().getWartoscX()+Imperium.getPanelImperium().getPas()][Imperium.getAquincum().getWartoscY()+Imperium.getPanelImperium().getPas()]=Kierunki.skrzyzowanie;
     }
     /**
   * manualne wprowadzenie obsługi miast w danych miejscach, w przypadku gdy nie są celami Podróży
   */
     public void Miasta(){
         for (Osada o : Imperium.getListaOsad()) {
             Imperium.getPanelImperium().getMatrixKierunkow()[o.getWartoscX()][o.getWartoscY()]=Kierunki.miasto;
         }
     
     
     }

    /**
     * @return the matrix
     */
    public int[][] getMatrix() {
        return matrix;
    }

    /**
     * @param matrix the matrix to set
     */
    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    /**
     * @return the matrixKierunkow
     */
    public Kierunki[][] getMatrixKierunkow() {
        return matrixKierunkow;
    }

    /**
     * @param matrixKierunkow the matrixKierunkow to set
     */
    public void setMatrixKierunkow(Kierunki[][] matrixKierunkow) {
        this.matrixKierunkow = matrixKierunkow;
    }

    /**
     * @return the pas
     */
    public int getPas() {
        return pas;
    }

    /**
     * @param pas the pas to set
     */
    public void setPas(int pas) {
        this.pas = pas;
    }

    /**
     * @return the rozmiarx
     */
    public int getRozmiarx() {
        return rozmiarx;
    }

    /**
     * @param rozmiarx the rozmiarx to set
     */
    public void setRozmiarx(int rozmiarx) {
        this.rozmiarx = rozmiarx;
    }

    /**
     * @return the rozmiary
     */
    public int getRozmiary() {
        return rozmiary;
    }

    /**
     * @param rozmiary the rozmiary to set
     */
    public void setRozmiary(int rozmiary) {
        this.rozmiary = rozmiary;
    }

    /**
     * @return the kolor
     */
    public Color getKolor() {
        return kolor;
    }

    /**
     * @param kolor the kolor to set
     */
    public void setKolor(Color kolor) {
        this.kolor = kolor;
    }

    /**
     * @return the square
     */
    public int getSquare() {
        return square;
    }

    /**
     * @param square the square to set
     */
    public void setSquare(int square) {
        this.square = square;
    }
     
     
    };
    
    

