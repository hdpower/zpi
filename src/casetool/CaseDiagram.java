package casetool;

import casetool.usecase.Actor;
import casetool.usecase.ExtendLink;
import casetool.usecase.IncludeLink;
import casetool.usecase.InheritLink;
import casetool.usecase.Link;
import casetool.usecase.LinkPoint;
import casetool.usecase.SimpleLink;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;

/*
 *  Główna klasa Diagramu Przypadków Użycia
 * 
 */

//------------------------------------------------------------------------------ główna klasa diagramu przypadków ----
public class CaseDiagram extends Diagram {
    
    //-------------------------------------------------------------------------- pola
    public enum CaseDiagramTypes {
        ACTOR, USECASE, SIMPLELINK, INHERITLINK, EXTENDLINK, INCLUDELINK, SYSTEMBOX
    };
    
    public ArrayList<Element> elements = new ArrayList<Element>();
    Plotno pl;
    MainWindow mw;
    Point frameLag;
    
    
    //-------------------------------------------------------------------------- konstruktor domyślny diagramu
    public CaseDiagram(MainWindow pmw) {
        
        // status: narzazie ok
        
        mw = pmw;
        
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
    public void setContextMenuOptions(DefaultMutableTreeNode selectedNode, 
                                      JPopupMenu contextMenu) {
        
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
        
        String buff = "";
        String req = "";
        String wynA = " ";
        String wynB = " ";
        
        if(type == CaseDiagramTypes.ACTOR) {
            req = "Podaj nazwę Aktora (max. 20 znaków)";
        } else {
            req = "Podaj nazwę Przypadku Użycia (max. 20 znaków)";
        }
        
        do {
            // pobierz nazwę wstawianego aktora
            buff = JOptionPane.showInputDialog(req);

            if(buff == null) {
               break;
            } else {
                
                if(buff.length() <= 10) {
                    
                    int xx = 10 - buff.length();
                    
                    for(int i = 0; i < xx; ++i) {
                        wynA += " ";
                    }
                    
                    wynA += buff.substring(0, buff.length());
                } else if(buff.length() < 20) {
                    wynA = buff.substring(0, 10);     
                    
                    
                    int xx = 20 - buff.length();
                    
                    for(int i = 0; i < xx; ++i) {
                        wynB += " ";
                    }
                    
                    wynB += buff.substring(10, buff.length());
                } else {
                    wynA = buff.substring(0, 10);                    
                    wynB = buff.substring(10, 20);
                }           
            }
            
        } while(buff == null || buff.length() == 0);        
        
        // stwórz obiekt i wstaw
        if(buff != null) {
            elements.add(new Actor(randomPoint(), wynA, wynB));
        }
                
        mw.tempCanvas.repaint();
    }
    
    //-------------------------------------------------------------------------- dodaj nowy Link(prosty,inherit,include,extend)
    public void addLink(CaseDiagramTypes type) {
        
        // status: 
        
//        JOptionPane.showMessageDialog(null, "testMenu B", "testMenu B", JOptionPane.ERROR_MESSAGE);
        
        LinkPoint from = new LinkPoint(randomPoint());
        LinkPoint to = new LinkPoint(randomPoint());
        elements.add(from);
        elements.add(to);
        
        // psprawdzamy który rodzaj linku chcemy dodać
        switch(type) {
            case SIMPLELINK:
                elements.add(new SimpleLink(from, to)); 
            break;
            case INHERITLINK:
                elements.add(new InheritLink(from, to)); 
            break;
            case INCLUDELINK:
                elements.add(new IncludeLink(from, to)); 
            break;
            case EXTENDLINK:    
                elements.add(new ExtendLink(from, to)); 
            break;
            default:
                
            break;
        }      
        
        mw.tempCanvas.repaint();        
    }
    
    //-------------------------------------------------------------------------- dodaj nowy Obszar Podsystemu
    public void addSystemBox() {
        
        // status: 
        
//        JOptionPane.showMessageDialog(null, "testMenu C", "testMenu C", JOptionPane.ERROR_MESSAGE);
        
        mw.tempCanvas.repaint();
    }
    
    //-------------------------------------------------------------------------- rysuje wszystkie elementy diagramu
    public void drawElements(Graphics g) {
        
        // status: 
        
        for(Element el: elements) {
            el.draw(g);
        }     

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
    
    
    //-------------------------------------------------------------------------- pobierz elementy które można przesówać
    public Vector<Element> getMousableElements() {
        return new Vector<Element>(elements);
    }
    
    //-------------------------------------------------------------------------- funkcja losująca punkt wstawiania
    private Point randomPoint() {
        
        Random r = new Random();
        
        return new Point(r.nextInt(200), r.nextInt(200));
        
    }
  
    
}