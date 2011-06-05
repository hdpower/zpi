package casetool.usecase;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

/*
 *  Klasa opowiadająca za rysowanie i zachowanie elementu Actor
 * 
 */

//------------------------------------------------------------------------------ klasa aktora ------------------------
public class Actor extends UseCaseElement {
    
    //-------------------------------------------------------------------------- pola
   
    
    //-------------------------------------------------------------------------- konstruktor główny
    public Actor(Point p, String pstrA, String pstrB) {
        
        strA = pstrA;
        strB = pstrB;
        x = p.x;
        y = p.y;
        
    }
    
    //-------------------------------------------------------------------------- rysuj Aktora
    @Override
    public void draw(Graphics g) {
        
        Graphics2D ga = (Graphics2D)g;
        ga.setStroke(new BasicStroke(stroke));
        ga.setColor(color);
                
        // budowanie Aktora
        ga.drawOval(x + 40, y + 10, 20, 20);
        ga.drawLine(x + 50, y + 30,x + 50, y + 55);
        ga.drawLine(x + 50, y + 30,x + 30, y + 55);
        ga.drawLine(x + 50, y + 30,x + 70, y + 55);
        ga.drawLine(x + 50, y + 55,x + 30, y + 80);
        ga.drawLine(x + 50, y + 55,x + 70, y + 80);
        ga.drawString(strA, x + 5, y + 95);
        ga.drawString(strB, x + 5, y + 110);
        
    }
    
    //-------------------------------------------------------------------------- pobierz szerokość
    @Override
    public int getWidth() {
        
        return 100;
        
    }
    
    //-------------------------------------------------------------------------- pobierz wysokość
    @Override
    public int getHeight() {
        
        return 130;
        
    }
        
}