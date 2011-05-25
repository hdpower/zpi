package casetool;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;

/*
 *  jeżeli mamy wykorzystywać wcześniej utworzone klasy to muszą one być 
 *  
 *      PUBLICZNE!!
 * 
 */

//------------------------------------------------------------------------------ abstrakcyjna klasa elementu ---------
public abstract class Element {
    
    //-------------------------------------------------------------------------- metody abstrakcyjne
    public abstract String toString();
    public abstract String toXML();
    public abstract void draw(Graphics g);
    public abstract void setPosition(Vector<Element> tables, int index, int canvasWidth, int canvasHeight);    
    public abstract void modifyElement(Diagram diagram, Element element);
    public abstract void deleteElement(Diagram diagram, Element element);
   
    
    //-------------------------------------------------------------------------- pola
    protected Boolean visible = new Boolean(true);
    protected String name;
    protected String comment;
    protected Boolean autoLocated = new Boolean(true);
    protected int stroke = 1;
    protected int x = 5;
    protected int y = 5;
    protected Color color = Color.BLACK;
    protected int margin = 5;
    protected int fontSize = 10;
    
    //-------------------------------------------------------------------------- metody
    
    public int getWidth() { return 0; }                                         // pobierz wymiary
    public int getHeight() { return 0; }
    
    public int getX() { return x; }                                             // położenie
    public int getY() { return y; }    
    public void setX(int x) { this.x=x; }
    public void setY(int y) { this.y=y; }
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        autoLocated = false;
    }
    
    public int getStroke() { return stroke; }                                   // obramowanie
    public void setStroke(int stroke) { this.stroke = stroke; }
    
    public Boolean isAutoLocated() { return autoLocated; }                      // autorozmieszczanie
    public void setAutolocated() { this.autoLocated = true; }
    
    public void setName(String name) { this.name = name; }                      // nazwa
    public String getName() { return name; }
    
    public void setColor(Color color) { this.color = color; }                   // kolor
    public Color getColor() {  return color;}
    
    public void setVisible(Boolean visible) { this.visible = visible; }         // widoczność
    public Boolean getVisible() { return visible; }
    
    
    public Boolean isMouseOverElement(int mouseX, int mouseY) { return false; } // czy mysz znajduje się nad elementem
}