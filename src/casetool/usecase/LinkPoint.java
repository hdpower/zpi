package casetool.usecase;

import casetool.CaseDiagram;
import casetool.Diagram;
import casetool.Element;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.util.Vector;
import javax.swing.*;

/**
 *
 *      obiekt tej klasy umożliwia obiektom Line zarządzanie ich położeniem
 * 
 */
//------------------------------------------------------------------------------ klasa LinkPoint 
public class LinkPoint extends Element {
    
    //-------------------------------------------------------------------------- pola
    Rectangle r;   
    Graphics2D g;
    Color col = Color.BLUE;
    Color st;
    Stroke str = new BasicStroke(2);
    boolean widoczny = true;
    Point buffP = new Point();
    
    Link line;
    SystemBox systembox;
    boolean lin;
    boolean from;
    boolean lock;
    JMenu menu;
    
    //-------------------------------------------------------------------------- konstruktor główny
    public LinkPoint(Point p) {

        this.x = p.x + 10;
        this.y = p.y + 10;

    }

    //-------------------------------------------------------------------------- rysuj element
    @Override
    public void draw(Graphics g) {
        
        Graphics2D ga = (Graphics2D)g;
        
        ga.setColor(color);
        ga.setStroke(new BasicStroke((int)stroke));
        
        
        if(widoczny == true) {
            r = new Rectangle(2+x,2+y,4,4);
            ga.draw(r);
        } 
               
    }
    
    //-------------------------------------------------------------------------- podłącz link
    public void setLink(Link l) {

        line = l;
        lin = true;

    }
    
    public void setBox(SystemBox s) {

        systembox = s;
        lin = false;
        
    }
    
    //-------------------------------------------------------------------------- czy kursor jest nad obiektem
    @Override
    public Boolean isMouseOverElement(int mouseX, int mouseY) {
        int x1 = getX();
        int x2 = x + getWidth();
        int y1 = getY();
        int y2 = y + getHeight();
        
        if(mouseX > x1 && mouseX < x2 && mouseY > y1 && mouseY < y2) {
            return true;   
        } else {
            return false;
        }            
    }
    
    //-------------------------------------------------------------------------- pobierz szerokość
    public int getWidth() {
        return 6;
    }
    
    //-------------------------------------------------------------------------- pobierz wysokość
    public int getHeight() {
        return 6;
    }

    //-------------------------------------------------------------------------- ustaw element
    @Override
    public void setPosition(int x, int y) {
        
        this.x = x;
        this.y = y;
        
    }
    
    //-------------------------------------------------------------------------- nie wiem co to jest 
    @Override
    public void setPosition(Vector<Element> tables, int index, int canvasWidth, int canvasHeight) {
        
    }

    //-------------------------------------------------------------------------- modyfikuj element
    @Override
    public void modifyElement(Diagram diagram, Element element) {
        JOptionPane.showMessageDialog(null, "Tego elementu nie da sie edytować");
    }

    //-------------------------------------------------------------------------- usuń element
    @Override
    public void deleteElement(Diagram diagram, Element element) {
        
        CaseDiagram diagramT=(CaseDiagram)diagram;
        
        // usuń linie i drugi punkt
        
        System.out.println("usuwam pingt Start");
        
        if(lin) {
            diagramT.elements.remove(((LinkPoint)element).line.from);
            diagramT.elements.remove(((LinkPoint)element).line.to);
            diagramT.elements.remove(((LinkPoint)element).line);
        } else {
            diagramT.elements.remove(((LinkPoint)element).systembox.from);
            diagramT.elements.remove(((LinkPoint)element).systembox.to);
            diagramT.elements.remove(((LinkPoint)element).systembox);
        }
        
        diagramT.elements.indexOf(x);
        
    }
    
    public void setAutolocated() {                
        
        JOptionPane.showMessageDialog(null, "Ta opcja jest nie aktywna");
        
    }
    
    public void changeDisplayMode() {
        
        JOptionPane.showMessageDialog(null, "Ta opcja jest nie aktywna");
        
    }
    
    //-------------------------------------------------------------------------- do String
    @Override
    public String toString() {
        return "To jest LinkPoint";
    }

    //-------------------------------------------------------------------------- do XML
    @Override
    public String toXML() {
        return "<element>To jest LinkPoint</element<";
    }
}
