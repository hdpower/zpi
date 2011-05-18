package casetool;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;

//------------------------------------------------------------------------------ główna klasa diagramu przypadków użycia ------
public class CaseDiagram extends Diagram {
    
    //-------------------------------------------------------------------------- pola
    
    
    
    //-------------------------------------------------------------------------- konstruktor domyślny
    public CaseDiagram() {
        this.typ="Database";
        elementsTree=new DefaultMutableTreeNode("Objekty");
        
        
    }

    //-------------------------------------------------------------------------- menu do dodanego elementu
    public void setContextMenuOptions(DefaultMutableTreeNode selectedNode, JPopupMenu contextMenu) {
        
        contextMenu.removeAll();
        
        if(selectedNode.toString().equals("Aktor")) {
            // jeżeli chcemy dodać Aktora
            
            JMenuItem addTable = new JMenuItem("Dodaj aktora");
            ActionListener addTableClick = new ActionListener() {
                
                // po kliknięciu...
                public void actionPerformed(ActionEvent e)
                {
//                    tablePropeties dialogWindow = new tablePropeties(getInstance());
//                    dialogWindow.showWindow();
                    
                    System.out.println(JOptionPane.showInputDialog("Podaj nazwę aktora"));
                    
                    
                    
                }
            };
            addTable.addActionListener(addTableClick);
            contextMenu.add(addTable);
            
        } else {
            
        }
    }

    // rysuje element
    public void drawElements(Graphics g) {
        
        g.drawRect(100, 100, 200, 200);
        g.drawString("jest nieźle", 300, 300);        

    }
     
    public void setPosition(Vector<Element> tables, int index, int canvasWidth, int canvasHeight) {
    
        
        
    
    }
    
    public void modifyElement(Diagram diagram, Element element) {
    
        
        
        
        
    }
    
    public void deleteElement(Diagram diagram, Element element) {
    
        
        
        
    }
    
}