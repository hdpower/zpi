package casetool;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;

public abstract class Diagram
{
    public JPanel panel=new JPanel();

protected DefaultMutableTreeNode elementsTree=new DefaultMutableTreeNode("Elementy");
protected String typ;
protected String source;

abstract public void drawElements(Graphics g);
abstract public void setContextMenuOptions(DefaultMutableTreeNode selectedNode, JPopupMenu contextMenu);
public String getType() { return this.typ; }
public DefaultMutableTreeNode getElementsTree() { return this.elementsTree; }
public void setElementsTree(DefaultMutableTreeNode elementsTree) { this.elementsTree=elementsTree; };
public JPanel getPanel() { return this.panel; }
public Vector<Element> getMousableElements() { return null; }
}

abstract class Element
{
    protected Boolean visible=new Boolean(true);
    protected String name;
    protected String comment;
    protected Boolean autoLocated=new Boolean(true);
    protected int stroke=1;
    protected int x=5;
    protected int y=5;
    protected Color color=Color.BLACK;
    protected int margin=5;
    protected int fontSize=10;
    public int getWidth() { return 0; }
    public int getHeight() { return 0; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getStroke() { return stroke; }
    public void setStroke(int stroke) { this.stroke=stroke; }
    public void setX(int x) { this.x=x; }
    public void setY(int y) { this.y=y; }
    public abstract String toString();
    public abstract String toXML();
    public abstract void draw(Graphics g);
    public abstract void setPosition(Vector<Element> tables, int index, int canvasWidth, int canvasHeight);
    public Boolean isAutoLocated() { return autoLocated; }
    public abstract void modifyElement(Diagram diagram, Element element);
    public abstract void deleteElement(Diagram diagram, Element element);
    public void setAutolocated() { this.autoLocated=true; }
    public void setName(String name) { this.name=name; }
    public String getName() { return name; }
    public void setColor(Color color) { this.color=color; }
    public Color getColor() {  return color;}
    public void setVisible(Boolean visible) { this.visible=visible; }
    public Boolean getVisible() { return visible; }
    public void setPosition(int x, int y)
    {
        this.x=x;
        this.y=y;
        autoLocated=false;
    }
    public Boolean isMouseOverElement(int mouseX, int mouseY) { return false; }
}