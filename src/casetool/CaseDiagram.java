package casetool;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;

/*
 *  Główna klasa Diagramu Przypadków Użycia
 * 
 */

//------------------------------------------------------------------------------ główna klasa diagramu przypadków użycia ------
public class CaseDiagram extends Diagram {
    
    //-------------------------------------------------------------------------- pola
    public enum CaseDiagramTypes {
        ACTOR, USECASE, SIMPLELINK, INHERITLINK, EXTENDLINK, INCLUDELINK, SYSTEMBOX
    };
    
    
    //-------------------------------------------------------------------------- konstruktor domyślny diagramu
    public CaseDiagram() {
        
        // status: narzazie ok
        
        // tworzenie menu w liście obiektów
        this.typ = "CaseUse";
        elementsTree = new DefaultMutableTreeNode("Dostępne obiekty:");

        elementsTree.add(new DefaultMutableTreeNode("Aktor"));
        elementsTree.add(new DefaultMutableTreeNode("Przypadek Użycia"));
        elementsTree.add(new DefaultMutableTreeNode("Związek Prosty"));
        elementsTree.add(new DefaultMutableTreeNode("Związek Dziedziczenia"));
        elementsTree.add(new DefaultMutableTreeNode("Związek \"Include\""));
        elementsTree.add(new DefaultMutableTreeNode("Związek \"Extend\""));        
        elementsTree.add(new DefaultMutableTreeNode("Obszar Podsystemu"));
        
    }

    //-------------------------------------------------------------------------- podmenu dla każdego dodanego elementu
    public void setContextMenuOptions(DefaultMutableTreeNode selectedNode, JPopupMenu contextMenu) {
        
        // status: 
        
        contextMenu.removeAll();
        
        if(selectedNode.toString().equals("Aktor")) {
            // jeżeli chcemy dodać Aktora
            
            JMenuItem addTable = new JMenuItem("Dodaj aktora");
            ActionListener addTableClick = new ActionListener() {
                
                public void actionPerformed(ActionEvent e)
                {
                    addUseCaseElement(CaseDiagramTypes.ACTOR);
                }
            };
            addTable.addActionListener(addTableClick);
            contextMenu.add(addTable);
            
        } if(selectedNode.toString().equals("Przypadek Użycia")) {
            // jeżeli chcemy dodać Przypadek użycia
            
            JMenuItem addTable = new JMenuItem("Dodaj Przypadek Użycia");
            ActionListener addTableClick = new ActionListener() {
                
                public void actionPerformed(ActionEvent e)
                {
                    addUseCaseElement(CaseDiagramTypes.USECASE);
                }
            };
            addTable.addActionListener(addTableClick);
            contextMenu.add(addTable);
            
        } if(selectedNode.toString().equals("Związek Prosty")) {
            // jeżeli chcemy dodać Aktora
            
            JMenuItem addTable = new JMenuItem("Dodaj Związek Prosty");
            ActionListener addTableClick = new ActionListener() {
                
                public void actionPerformed(ActionEvent e)
                {
                    addLink(CaseDiagramTypes.SIMPLELINK);
                }
            };
            addTable.addActionListener(addTableClick);
            contextMenu.add(addTable);
            
        } if(selectedNode.toString().equals("Związek Dziedziczenia")) {
            // jeżeli chcemy dodać Aktora
            
            JMenuItem addTable = new JMenuItem("Dodaj Związek Dziedziczenia");
            ActionListener addTableClick = new ActionListener() {
                
                public void actionPerformed(ActionEvent e)
                {
                    addLink(CaseDiagramTypes.INHERITLINK);
                }
            };
            addTable.addActionListener(addTableClick);
            contextMenu.add(addTable);
            

        } if(selectedNode.toString().equals("Związek \"Include\"")) {
            // jeżeli chcemy dodać Aktora
            
            JMenuItem addTable = new JMenuItem("Dodaj Związek \"Include\"");
            ActionListener addTableClick = new ActionListener() {
                
                public void actionPerformed(ActionEvent e)
                {
                    addLink(CaseDiagramTypes.INCLUDELINK);
                }
            };
            addTable.addActionListener(addTableClick);
            contextMenu.add(addTable);
            
        } if(selectedNode.toString().equals("Związek \"Extend\"")) {
            // jeżeli chcemy dodać Aktora
            
            JMenuItem addTable = new JMenuItem("Dodaj Związek \"Extend\"");
            ActionListener addTableClick = new ActionListener() {
                
                public void actionPerformed(ActionEvent e)
                {
                    addLink(CaseDiagramTypes.EXTENDLINK);
                }
            };
            addTable.addActionListener(addTableClick);
            contextMenu.add(addTable);
        } if(selectedNode.toString().equals("Obszar Podsystemu")) {
            
            // jeżeli chcemy dodać Aktora
            
            JMenuItem addTable = new JMenuItem("Dodaj Obszar Podystemu");
            ActionListener addTableClick = new ActionListener() {
                
                public void actionPerformed(ActionEvent e)
                {
                    addSystemBox();
                }
            };
            addTable.addActionListener(addTableClick);
            contextMenu.add(addTable);
            
        }
    }

    //-------------------------------------------------------------------------- dodaj nowego Aktora lub Przypadek użycia
    public void addUseCaseElement(CaseDiagramTypes type) {
        
        // status: 
        JOptionPane.showMessageDialog(null, "testMenu A", "testMenu A", JOptionPane.ERROR_MESSAGE);
        
        
        
    }
    
    //-------------------------------------------------------------------------- dodaj nowy Link(prosty,inherit,include,extend)
    public void addLink(CaseDiagramTypes type) {
        
        // status: 
        
        JOptionPane.showMessageDialog(null, "testMenu B", "testMenu B", JOptionPane.ERROR_MESSAGE);
        
        
    }
    
    //-------------------------------------------------------------------------- dodaj nowy Obszar Podsystemu
    public void addSystemBox() {
        
        // status: 
        
        JOptionPane.showMessageDialog(null, "testMenu C", "testMenu C", JOptionPane.ERROR_MESSAGE);
        
        
    }
    
    //-------------------------------------------------------------------------- rysuje wszystkie elementy diagramu
    public void drawElements(Graphics g) {
        
        // status: 
        
        g.drawRect(100, 100, 200, 200);
        g.drawString("jest nieźle", 300, 300);        

    }
    
    //-------------------------------------------------------------------------- 
    public void setPosition(Vector<Element> tables, int index, int canvasWidth, int canvasHeight) {
    
        // status: 
        
    
    }
    
    //-------------------------------------------------------------------------- modyfikuj element
    public void modifyElement(Diagram diagram, Element element) {
    
        // status: 
        
        
        
    }
    
    //-------------------------------------------------------------------------- usuń element
    public void deleteElement(Diagram diagram, Element element) {
    
        // status: 
        
        
    }
    
}