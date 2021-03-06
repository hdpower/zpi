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

