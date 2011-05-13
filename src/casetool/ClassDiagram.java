package casetool;

import java.awt.Graphics;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;

public class ClassDiagram extends Diagram
{
    private Vector<Element> classes;
    
    public ClassDiagram() {
        this.typ = "Class";
        elementsTree = new DefaultMutableTreeNode("UML");
        classes = new Vector<Element>();
        
        //Stworzenie drzewa opcji
        DefaultMutableTreeNode classNode = new DefaultMutableTreeNode("Klasa");
        DefaultMutableTreeNode interfaceNode = new DefaultMutableTreeNode("Interfejs");
        DefaultMutableTreeNode generalizationNode = new DefaultMutableTreeNode("Generalizacja");
        DefaultMutableTreeNode assemblingNode = new DefaultMutableTreeNode("Złożenie");
        DefaultMutableTreeNode noteNode = new DefaultMutableTreeNode("Notatka");
        
        elementsTree.add(classNode);
        elementsTree.add(interfaceNode);
        elementsTree.add(generalizationNode);
        elementsTree.add(assemblingNode);
        elementsTree.add(noteNode);
    }
    
    public ClassDiagram getInstance() {
        return this;
    }

    public void setContextMenuOptions(DefaultMutableTreeNode selectedNode, JPopupMenu contextMenu) { 
        
        contextMenu.removeAll();
        
        if(selectedNode.toString().equals("Klasa")) {
            
            JMenuItem addClass = new JMenuItem("Dodaj klasę");
            contextMenu.add(addClass);
            
        }
    }
    
    public void drawElements(Graphics g) { }
    public void setPosition(Vector<Element> tables, int index, int canvasWidth, int canvasHeight) { }
    public void modifyElement(Diagram diagram, Element element) { }
    public void deleteElement(Diagram diagram, Element element) { }
    
    @Override
    public Vector<Element> getMousableElements() {
        return classes;
    }
}

class Class {
    
}