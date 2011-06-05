package casetool;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;

//------------------------------------------------------------------------------ abstrakcyjna klasa diagramu ------------------
public abstract class Diagram {
    
    //-------------------------------------------------------------------------- metody abstrakcyjne
    abstract public void drawElements(Graphics g);    
    abstract public void setContextMenuOptions(DefaultMutableTreeNode selectedNode, JPopupMenu contextMenu);
    
    //-------------------------------------------------------------------------- pola
    public JPanel panel = new JPanel();
    protected DefaultMutableTreeNode elementsTree = new DefaultMutableTreeNode("Elementy");
    protected String typ;
    protected String source="";
    protected String name="";

    //-------------------------------------------------------------------------- metody
    
    public String getType() {                                                   // pobierz typ
        return this.typ;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name=name;
    }

    public DefaultMutableTreeNode getElementsTree() {                           // pobierz drzewo elementów
        return this.elementsTree;
    }
    
    public void setElementsTree(DefaultMutableTreeNode elementsTree) {          // ustaw drzewo elementów
        this.elementsTree = elementsTree;
    };
    
    public JPanel getPanel() {                                                  // pobierz panel
        return this.panel;
    }
    
    public Vector<Element> getMousableElements() {                              // pobierz elementy które można przesówać
        return null;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source=source;
    }

    public boolean isProjectWasSaved()
    {
        return !source.isEmpty();
    }

    public String toXML()
    {
        return "<diagram type='"+getType()+"'></diagram>";
    }
}










//
//     po wykonaniu kilku testów skasujcie ponizej zakomentowana klase Element
//      została ona przeniesiona do innego pliku
//
//
////------------------------------------------------------------------------------ abstrakcyjna klasa elementu diagramu ---------
//abstract class Element
//{
//    protected Boolean visible=new Boolean(true);
//    protected String name;
//    protected String comment;
//    protected Boolean autoLocated=new Boolean(true);
//    protected int stroke=1;
//    protected int x=5;
//    protected int y=5;
//    protected Color color=Color.BLACK;
//    protected int margin=5;
//    protected int fontSize=10;
//    public int getWidth() { return 0; }
//    public int getHeight() { return 0; }
//    public int getX() { return x; }
//    public int getY() { return y; }
//    public int getStroke() { return stroke; }
//    public void setStroke(int stroke) { this.stroke=stroke; }
//    public void setX(int x) { this.x=x; }
//    public void setY(int y) { this.y=y; }
//    public abstract String toString();
//    public abstract String toXML();
//    public abstract void draw(Graphics g);
//    public abstract void setPosition(Vector<Element> tables, int index, int canvasWidth, int canvasHeight);
//    public Boolean isAutoLocated() { return autoLocated; }
//    public abstract void modifyElement(Diagram diagram, Element element);
//    public abstract void deleteElement(Diagram diagram, Element element);
//    public void setAutolocated() { this.autoLocated=true; }
//    public void setName(String name) { this.name=name; }
//    public String getName() { return name; }
//    public void setColor(Color color) { this.color=color; }
//    public Color getColor() {  return color;}
//    public void setVisible(Boolean visible) { this.visible=visible; }
//    public Boolean getVisible() { return visible; }
//    public void setPosition(int x, int y)
//    {
//        this.x=x;
//        this.y=y;
//        autoLocated=false;
//    }
//    public Boolean isMouseOverElement(int mouseX, int mouseY) { return false; }
//}