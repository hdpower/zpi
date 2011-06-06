package casetool.usecase;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.*;

/*
 *  Klasa opowiadająca za rysowanie i zachowanie elementu UseCase
 * 
 */

//------------------------------------------------------------------------------ klasa aktora ------------------------
public class UseCase extends UseCaseElement {
    
    //-------------------------------------------------------------------------- pola

    
    
    //-------------------------------------------------------------------------- konstruktor główny
    public UseCase(Point p, String pstrA, String pstrB) {
        
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
                
        // budowanie UseCase
        ga.drawOval(x, y, 200, 50);
        ga.drawString(strA, x + 70, y + 20);
        ga.drawString(strB, x + 70, y + 40);
    }
    
    //-------------------------------------------------------------------------- pobierz szerokość
    @Override
    public int getWidth() {
        return 200;
    }
    
    //-------------------------------------------------------------------------- pobierz wysokość
    @Override
    public int getHeight() {
        return 50;
    }
        
}