package casetool.usecase;

import casetool.CaseDiagram;
import casetool.Diagram;
import casetool.Element;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Random;
import java.util.Vector;

/**
 *
 *      obiekt tej klasy obsługuje linki wszystkich rodzajów
 * 
 */
//------------------------------------------------------------------------------ klasa LinkPoint 
public class Link extends Element {

    //-------------------------------------------------------------------------- pola
    
  //  public Okienko frame;
    Graphics2D ga;

    //    Color st;
    boolean podswietlony = false;
   
    
    public LinkPoint from;
    public LinkPoint to;
    
    
    //-------------------------------------------------------------------------- konstruktor domyślny
    public Link() {
        
        super();
        
    }
    
    //-------------------------------------------------------------------------- konstruktor główny
    public Link(LinkPoint pfrom, LinkPoint pto) {
        
        from = pfrom;
        to = pto;

    }

    public void changeDisplayMode() { }
    
    @Override
    public String toString() {
        return "";
    }

    @Override
    public String toXML() {
        return "";
    }

    @Override
    public void draw(Graphics g) {
        
        ga = (Graphics2D)g;
        
        ga.setColor(color);
        ga.setStroke(new BasicStroke(stroke));
        
        ga.drawLine(from.getX()+4, from.getY()+4, to.getX()+4, to.getY()+4);
    }

    @Override
    public void setPosition(Vector<Element> tables, int index, int canvasWidth, int canvasHeight) {
        
    }

    @Override
    public void modifyElement(Diagram diagram, Element element) {
        
    }

    @Override
    public void deleteElement(Diagram diagram, Element element) {
        CaseDiagram diagramT = (CaseDiagram)diagram;
        //diagramT.elements.remove((Link)element);
        
        System.out.println(" -> Ten element tam jest: " + diagramT.elements.contains(element));
    }
    
    //-------------------------------------------------------------------------- funkcja losująca punkt wstawiania
    private Point randomPoint() {
        
        Random r = new Random();
        
        return new Point(r.nextInt(200), r.nextInt(200));
        
    }
    
         
} 