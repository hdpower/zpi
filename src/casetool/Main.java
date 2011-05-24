package casetool;

import com.sun.org.apache.xerces.internal.impl.xs.opti.DefaultNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/*
 *==============================================================================
 * 
 *              BARDZO WAŻNA INFORMACJA
 * 
 *  Składania kodu znajdująca się w tym dokumencie została sformatowana
 *  zgodnie z Java Code Conventions
 *  (http://java.sun.com/docs/codeconv/CodeConventions.pdf)
 * 
 *  oraz w oparciu o NAJCZĘŚCIEJ stosowane zwyczaje programistyczne.
 * 
 *  Więc jeżeli nie potraficie przeczytać tak sformatowanego kodu to znaczy że...
 *  
 *      NIE ZNACIE SIĘ!!!
 * 
 * 
 *==============================================================================
 */



//------------------------------------------------------------------------------ klasa startowa aplikacji ------------
/* 
 *
 * 
 * 
 *      ta klasa jest zupełnie NIE POTRZEBNA!!!
 * 
 *      jaką rolę ona odgrywa w naszej aplikacji???????????????
 */
public class Main {

    public static void main(String[] args) {
        
        MainWindow okno = new MainWindow();
        
    }

}

//------------------------------------------------------------------------------ klasa głównego okna aplikacji -------
class MainWindow extends JFrame {
    
    private Integer szerokoscPM=new Integer(180);
    private Integer szerokoscEkranu=1280;
    private Integer wysokoscEkranu=720;
    private JMenuBar mainMenu;
    private JTabbedPane zakladki=new JTabbedPane();
    private ArrayList<Diagram> diagramy=new ArrayList<Diagram>();
    private DefaultMutableTreeNode projectManagerElements;
    private JTree projectManager;
    private JPopupMenu contextMenuTree;
    private JPopupMenu contextMenu;

    public MainWindow() {
        
        Toolkit tools = Toolkit.getDefaultToolkit();
        szerokoscEkranu = tools.getScreenSize().width;
        wysokoscEkranu = tools.getScreenSize().height - 40;
        mainMenu = new JMenuBar();
        Container content = getContentPane();
        
        content.add(zakladki);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(this.szerokoscEkranu, this.wysokoscEkranu);
        setResizable(false);
        setTitle("CaseTool 1.0");
    
        ChangeListener changeTab = new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                
                Plotno tempCanvas = (Plotno)diagramy.get(zakladki.getSelectedIndex()).panel.getComponent(1);
                tempCanvas.setCurrentDiagram(diagramy.get(zakladki.getSelectedIndex()));
                String temp = diagramy.get(zakladki.getSelectedIndex()).getType();
                initializeDiagramPM(diagramy.get(zakladki.getSelectedIndex()));
                
                if(temp.equals("database")) {
                    initializeDBMenu();
                }
                
                if(temp.equals("case")) {
                    initializeCaseDiagramMenu();
                }
                
                if(temp.equals("class")) {
                    initializeClassMenu();
                }
            }
        };
        
        zakladki.addChangeListener(changeTab);

        setJMenuBar(mainMenu);
        initializeMainMenu();
        setVisible(true);
    }

    public void initializeMainMenu() {
        
        ActionListener createCasesDiagramClick = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                
                initializeCaseDiagramMenu();

                Diagram temp=new CaseDiagram();
                setTabLayout(temp.panel);

                diagramy.add(temp);
                zakladki.add("Diagram przypadków "+diagramy.size(),diagramy.get(diagramy.size()-1).panel);
                initializeDiagramPM(temp);
            }
        };

        ActionListener createClassDiagramClick = new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                initializeClassMenu();

                Diagram temp = new ClassDiagram();
                setTabLayout(temp.panel);

                diagramy.add(temp);
                zakladki.add("Diagram klas " + diagramy.size(), diagramy.get(diagramy.size() - 1).panel);
                initializeDiagramPM(temp);
            }
        };

        ActionListener createDBDiagramClick=new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                initializeDBMenu();
                Diagram temp = new DBDiagram();
                setTabLayout(temp.panel);

                diagramy.add(temp);
                zakladki.add("Diagram bazy danych "+diagramy.size(),diagramy.get(diagramy.size()-1).panel);
                initializeDiagramPM(temp);
            }
        };
        
        JMenuItem createProject=new JMenuItem("Stwórz projekt");    
        JMenu createProjectMenu=new JMenu("Stwórz projekt");
        JMenuItem createDBDiagram=new JMenuItem("Stwórz diagram tabel bazy danych");
        createDBDiagram.addActionListener(createDBDiagramClick);
        JMenuItem createClassDiagram=new JMenuItem("Stwórz diagram klas");
        createClassDiagram.addActionListener(createClassDiagramClick);

        // podmenu z diagramem przypadków użycia
        JMenuItem createCasesDiagram=new JMenuItem("Stwórz diagram przypadków");
        createCasesDiagram.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));

        createCasesDiagram.addActionListener(createCasesDiagramClick);
        createCasesDiagram.setIcon(null);

        JMenuItem loadProject=new JMenuItem("Wczytaj projekt");
        JMenuItem saveProject=new JMenuItem("Zapisz projekt");
        JMenuItem CloseProject=new JMenuItem("Zamknij projekt");
        JMenuItem CloseProgram=new JMenuItem("Zamknij program");

        JMenu menuProjekt=new JMenu("Projekt");
        JMenu menuElementy=new JMenu("Elementy");
        menuElementy.setVisible(false);
        createProjectMenu.add(createCasesDiagram);
        createProjectMenu.add(createDBDiagram);
        createProjectMenu.add(createClassDiagram);
        createProject.add(createProjectMenu);
        menuProjekt.add(createProjectMenu);
        menuProjekt.add(loadProject);
        menuProjekt.add(saveProject);
        menuProjekt.add(CloseProject);
        menuProjekt.add(CloseProgram);
        menuProjekt.add(menuProjekt);
        mainMenu.add(menuProjekt);
        mainMenu.add(menuElementy);
        //initializeCaseDiagramMenu();
    
   }

    public void setTabLayout(JPanel panel) {
        panel.setLayout(null);
        JPanel listaElementow=new JPanel();
        listaElementow.setBounds(0,0,szerokoscPM,wysokoscEkranu);
        Plotno tempCanvas =new Plotno();
        tempCanvas.setBackground(Color.WHITE);
        tempCanvas.setBounds(szerokoscPM,0,szerokoscEkranu-szerokoscPM,wysokoscEkranu);
        panel.add(listaElementow);
        panel.add(tempCanvas);
    }

    public void initializeDiagramPM(Diagram diagram) {
        
        JPanel panel = (JPanel)diagram.panel.getComponent(0);
        panel.setLayout(new FlowLayout());
        projectManagerElements = diagram.getElementsTree();
        projectManager = new JTree(projectManagerElements);
        contextMenuTree = new JPopupMenu("Menu");
        
        MouseListener contextMenuAction = new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            public void mousePressed(MouseEvent e) {
                try {
                    if(e.BUTTON3==e.getButton())
                        {
                        TreePath selectedPath=(TreePath)projectManager.getPathForLocation(e.getX(), e.getY());
                        DefaultMutableTreeNode selectedNode=(DefaultMutableTreeNode)selectedPath.getLastPathComponent();
                        diagramy.get(zakladki.getSelectedIndex()).setContextMenuOptions(selectedNode, contextMenuTree);
                        contextMenuTree.show(zakladki, e.getX(), e.getY());
                    }
                }
                catch(Exception ex) { }

            }

            public void mouseReleased(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            public void mouseEntered(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            public void mouseExited(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    
        projectManager.addMouseListener(contextMenuAction);

        projectManager.setPreferredSize(new Dimension(szerokoscPM,wysokoscEkranu));
        panel.add(projectManager);
    }

    //-------------------------------------------------------------------------- inicjuj menu Elementy
    public void initializeCaseDiagramMenu() {
        
        // status: 100%
        
        // pobierz referencje do menu do zmiennej menuElementy
        JMenu menuElementy = mainMenu.getMenu(1);
        
        // wyczyść i pokaż
        menuElementy.setVisible(true);               
        menuElementy.removeAll();
        
        // dodaj do menu wszystkie możliwe rodzaje elementów
        JMenuItem mA = new JMenuItem("Aktor");
        JMenuItem mB = new JMenuItem("Przypadek Użycia");
        JMenuItem mC = new JMenuItem("Związek Prosty");
        JMenuItem mD = new JMenuItem("Związek Dziedziczenia");
        JMenuItem mE = new JMenuItem("Związek \"Include\"");
        JMenuItem mF = new JMenuItem("Związek \"Extend\"");
        JMenuItem mG = new JMenuItem("Obszar Podsystemu");

        // dodaj skróty klawiaturowe
        mA.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.CTRL_MASK));
        mB.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.CTRL_MASK));
        mC.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_3, java.awt.event.InputEvent.CTRL_MASK));
        mD.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_4, java.awt.event.InputEvent.CTRL_MASK));
        mE.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_5, java.awt.event.InputEvent.CTRL_MASK));
        mF.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_6, java.awt.event.InputEvent.CTRL_MASK));
        mG.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_6, java.awt.event.InputEvent.CTRL_MASK));
        
        // obsługa zdarzeń kliknięcia na pozycje w menu
        mA.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "testMenu", "testMenu 1", JOptionPane.ERROR_MESSAGE);
            }
            
        });
        
        mB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "testMenu", "testMenu 2", JOptionPane.ERROR_MESSAGE);
            }
            
        });
        
        mC.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "testMenu", "testMenu 3", JOptionPane.ERROR_MESSAGE);
            }
            
        });
        
        mD.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "testMenu", "testMenu 4", JOptionPane.ERROR_MESSAGE);
            }
            
        });
        
        mE.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "testMenu", "testMenu 5", JOptionPane.ERROR_MESSAGE);
            }
            
        });
        
        mF.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "testMenu", "testMenu 6", JOptionPane.ERROR_MESSAGE);
            }
            
        });
                
        // dodaj elementy menu do głównego korzenia Menu
        menuElementy.add(mA);
        menuElementy.add(mB);
        menuElementy.add(mC);
        menuElementy.add(mD);
        menuElementy.add(mE);
        menuElementy.add(mF);
    }

    public void initializeClassMenu() {
        
        JMenu menuElementy = mainMenu.getMenu(1);
        menuElementy.setVisible(true);
        JMenuItem addClass = new JMenuItem("Dodaj klasę");
        JMenuItem addInterface = new JMenuItem("Dodaj interfejs");
        JMenuItem addStaticClass = new JMenuItem("Dodaj klasę statyczną");
        JMenuItem addAbstractClass = new JMenuItem("Dodaj klasę abstrakcyjna");
        
        menuElementy.removeAll();
        menuElementy.add(addClass);
        menuElementy.add(addInterface);
        menuElementy.add(addStaticClass);
        menuElementy.add(addAbstractClass);
    }

    public void initializeDBMenu() {
        
        JMenu menuElementy = mainMenu.getMenu(1);
        menuElementy.setVisible(true);
        JMenuItem addTable = new JMenuItem("Dodaj tabelę");
        JMenuItem addView = new JMenuItem("Dodaj perspektywę");
        JMenuItem addFunction = new JMenuItem("Dodaj funkcję");
        JMenuItem addProcedure = new JMenuItem("Dodaj procedurę");
        JMenuItem addTrigger = new JMenuItem("Dodaj wyzwalacz");
        
        menuElementy.removeAll();
        menuElementy.add(addTable);
        menuElementy.add(addView);
        menuElementy.add(addFunction);
        menuElementy.add(addProcedure);
        menuElementy.add(addTrigger);
    }
}


//------------------------------------------------------------------------------ klasa kontenera do rysowania --------
class Plotno extends JPanel {
    
    private Boolean lock=new Boolean(true);
    private Diagram currentDiagram;
    private int movedElement=-1;
    private int currentElement=-1;
    private  JPopupMenu contextMenu=new JPopupMenu();
             JMenuItem modifyElement=new JMenuItem("Modyfikuj element");
             JMenuItem deleteElement=new JMenuItem("Usuń element");
             JMenuItem setAutolocated=new JMenuItem("Ustaw pozycję automatycznie");

    
    public void setCurrentDiagram(Diagram currentDiagram) {
        this.currentDiagram=currentDiagram;
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(lock) currentDiagram.drawElements(g);
    }

    public Plotno() {
        
        contextMenu.add(modifyElement);
        contextMenu.add(deleteElement);
        contextMenu.add(setAutolocated);
        
        modifyElement.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                
                Vector<Element> elements = currentDiagram.getMousableElements();
                elements.get(currentElement).modifyElement(currentDiagram, elements.get(currentElement));
                
            }
            
        });
        
        deleteElement.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                
                Vector<Element> elements = currentDiagram.getMousableElements();
                
                if(JOptionPane.showConfirmDialog(deleteElement, "Czy na pewno chcesz usunąć element "+elements.get(currentElement).toString()+" ?", TOOL_TIP_TEXT_KEY, 1)==0) {
                    elements.get(currentElement).deleteElement(currentDiagram,elements.get(currentElement));
                }
                
            }
        });

        setAutolocated.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                
                Vector<Element> elements = currentDiagram.getMousableElements();
                elements.get(currentElement).setAutolocated();
                
            }
            
        });

        addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            public void mousePressed(MouseEvent e) {
                
                currentElement=-1;
                //if(contextMenu.isShowing()) context
                
                Vector<Element> elements = currentDiagram.getMousableElements();
                
                for(int i = 0; i < elements.size(); i++) {
                    
                    if(elements.get(i).isMouseOverElement(e.getX(), e.getY())) {
                       currentElement = i;
                       
                       if(e.getButton()==1) {
                           movedElement=i;
                       }
                       
                       if(e.getButton()==3) {
                           contextMenu.show(currentDiagram.panel, e.getX(), e.getY());
                       }
                       
                       break;
                    }
                    
                }
            }

            public void mouseReleased(MouseEvent e) {
                if(movedElement>-1)
                    movedElement=-1;
            }

            public void mouseEntered(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            public void mouseExited(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        
        addMouseMotionListener(new MouseMotionListener() {

            public void mouseDragged(MouseEvent e) {
                Vector<Element> elements = currentDiagram.getMousableElements();
                
                if(movedElement>-1) {
                    
                    elements.get(movedElement).setPosition(e.getX(), e.getY());
                    repaint();
                    
                }
            }

            public void mouseMoved(MouseEvent e) {

                
                Vector<Element> elements=currentDiagram.getMousableElements();

                for(int i=0; i < elements.size(); i++) {
                    
                    if(elements.get(i).isMouseOverElement(e.getX(), e.getY())) {
                        elements.get(i).setStroke(2);
                        
                    } else {
                        elements.get(i).setStroke(1); 
                    }
                }

                repaint();
            }
        });
    }
}
