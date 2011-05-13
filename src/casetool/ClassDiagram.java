package casetool;

import java.awt.Graphics;
import java.util.Vector;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;

public class ClassDiagram extends Diagram
{
public ClassDiagram()
    {
    this.typ="Class";
    elementsTree=new DefaultMutableTreeNode("Klasy");
    }

public void setContextMenuOptions(DefaultMutableTreeNode selectedNode, JPopupMenu contextMenu) { }
public void drawElements(Graphics g) { }
public void setPosition(Vector<Element> tables, int index, int canvasWidth, int canvasHeight) { }
public void modifyElement(Diagram diagram, Element element) { }
public void deleteElement(Diagram diagram, Element element) { }
}