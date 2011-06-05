package casetool;

import com.sun.org.apache.xerces.internal.impl.xs.opti.DefaultNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileWriter;
import javax.swing.filechooser.FileFilter;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

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
    
    //-------------------------------------------------------------------------- pola
    private String projectFileExtension="ctp";
    private FileFilter filesFilter;
    private JMenuBar mainMenu;
    private DefaultMutableTreeNode projectManagerElements;
    private JTree projectManager;
    private JPopupMenu contextMenuTree;
    private JPopupMenu contextMenu;
    
    private Integer szerokoscPM = new Integer(180);
    private Integer szerokoscEkranu = 1280;
    private Integer wysokoscEkranu = 720;
    private JTabbedPane zakladki = new JTabbedPane();
    private ArrayList<Diagram> diagramy = new ArrayList<Diagram>();
    public Plotno tempCanvas;

    //-------------------------------------------------------------------------- konstruktor główny
    public MainWindow() {
        
        // operacje ustawiające wielkość i inne właściwości okienka
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
    
        // jeżeli wykryte zostanie zdarzenie przełączenia zkładki to
        // załadowane zostanie odpowiednie menu i płutno
        ChangeListener changeTab = new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                int tabNumber=zakladki.getSelectedIndex();
                JMenu tempMenu=mainMenu.getMenu(0);
                if(tabNumber>-1)
                {
                tempCanvas=(Plotno)diagramy.get(tabNumber).panel.getComponent(1);
                tempCanvas.setCurrentDiagram(diagramy.get(tabNumber));
                String temp=diagramy.get(tabNumber).getType();
                //temp=temp.toLowerCase();
                initializeDiagramPM(diagramy.get(tabNumber));
                if(temp.equalsIgnoreCase("database"))
                {
                    initializeDBMenu();
                }
                if(temp.equalsIgnoreCase("case"))
                {
                    initializeCaseDiagramMenu();
                }
                if(temp.equalsIgnoreCase("class"))
                {
                    initializeClassMenu();
                }

                if(diagramy.get(tabNumber).isProjectWasSaved()) tempMenu.getItem(2).setEnabled(true);
                else tempMenu.getItem(2).setEnabled(false);
                tempMenu.getItem(3).setEnabled(true);
                tempMenu.getItem(4).setEnabled(true);
                }
                else
                {
                tempMenu.getItem(2).setEnabled(false);
                tempMenu.getItem(3).setEnabled(false);
                tempMenu.getItem(4).setEnabled(false);
                }
            }
        };
        
        zakladki.addChangeListener(changeTab);

        setJMenuBar(mainMenu);
        initializeMainMenu();
        setVisible(true);
    }
    
    //-------------------------------------------------------------------------- konfiguracja Menu głównego
    public void initializeMainMenu() {
        
        ActionListener createCasesDiagramClick = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                
                initializeCaseDiagramMenu();

                Diagram temp = new CaseDiagram(getInstance());
                setTabLayout(temp.panel);

                diagramy.add(temp);
                zakladki.add("Diagram przypadków " + diagramy.size(), diagramy.get(diagramy.size() - 1).panel);
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
                zakladki.add("Diagram bazy danych " + diagramy.size(), diagramy.get(diagramy.size() - 1).panel);
                initializeDiagramPM(temp);
            }
        };
        
        // ustaw elementy Menu głównego
        JMenuItem createProject = new JMenuItem("Stwórz projekt");    
        JMenu createProjectMenu = new JMenu("Stwórz projekt");
        
        // podmenu z diagramem baz danych
        JMenuItem createDBDiagram = new JMenuItem("Stwórz diagram tabel bazy danych");
        createDBDiagram.addActionListener(createDBDiagramClick);
        
        // podmenu z diagramem klas
        JMenuItem createClassDiagram = new JMenuItem("Stwórz diagram klas");
        createClassDiagram.addActionListener(createClassDiagramClick);

        // podmenu z diagramem przypadków użycia
        JMenuItem createCasesDiagram = new JMenuItem("Stwórz diagram przypadków");
        createCasesDiagram.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        createCasesDiagram.addActionListener(createCasesDiagramClick);
        createCasesDiagram.setIcon(null);
        
        // reszta konfiguracj menu głównego
        JMenuItem loadProject = new JMenuItem("Wczytaj projekt");
        JMenuItem saveProject = new JMenuItem("Zapisz projekt");
        JMenuItem saveAsProject = new JMenuItem("Zapisz projekt jako ...");
        JMenuItem CloseProject = new JMenuItem("Zamknij projekt");
        JMenuItem CloseProgram = new JMenuItem("Zamknij program");
        saveProject.setEnabled(false);
        saveAsProject.setEnabled(false);
        CloseProject.setEnabled(false);
        JMenu menuProjekt = new JMenu("Projekt");
        JMenu menuElementy = new JMenu("Elementy");
        menuElementy.setVisible(false);
        createProjectMenu.add(createCasesDiagram);
        createProjectMenu.add(createDBDiagram);
        createProjectMenu.add(createClassDiagram);
        createProject.add(createProjectMenu);
        menuProjekt.add(createProjectMenu);
        menuProjekt.add(loadProject);
        menuProjekt.add(saveProject);
        menuProjekt.add(saveAsProject);
        menuProjekt.add(CloseProject);
        menuProjekt.add(CloseProgram);
        menuProjekt.add(menuProjekt);
        mainMenu.add(menuProjekt);
        mainMenu.add(menuElementy);
        //initializeCaseDiagramMenu();
    
            
    CloseProgram.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(diagramy.size()>0)
                {
                    if(JOptionPane.showConfirmDialog(rootPane, "Obecnie otwarte jest kilka projektów, czy na pewno chcesz zamknąć program ?", "Ostrzeżenie", 1)==0)
                            dispose();
                }
                else dispose();
            }
        });
    
    saveAsProject.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int tabNumber=zakladki.getSelectedIndex();
                JFileChooser chooseFileDialog=new JFileChooser();
                filesFilter=new FileFilter() {

                    @Override
                    public boolean accept(File f) {
                        return f.getName().endsWith(projectFileExtension);
                    }

                    @Override
                    public String getDescription() {
                       return "Plik projektu programu CaseTool (*."+projectFileExtension+")";
                    }
                };

               chooseFileDialog.setFileFilter(filesFilter);
               chooseFileDialog.showSaveDialog(mainMenu);
               File file=chooseFileDialog.getSelectedFile();
               diagramy.get(tabNumber).setName(file.getName());
               saveProject(chooseFileDialog.getSelectedFile().getPath()+"."+projectFileExtension);
                
            }
        });
    
    saveProject.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int tabNumber=zakladki.getSelectedIndex();
               
                saveProject(diagramy.get(tabNumber).getSource());
            }
        });
    
    loadProject.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser=new JFileChooser();
                
                
                    filesFilter=new FileFilter() {

                    @Override
                    public boolean accept(File f) {
                        return f.getName().endsWith(projectFileExtension);
                    }

                    @Override
                    public String getDescription() {
                       return "Plik projektu programu CaseTool (*."+projectFileExtension+")";
                    }
                };
                    
                fileChooser.setFileFilter(filesFilter);
                fileChooser.showOpenDialog(mainMenu);
                File file=fileChooser.getSelectedFile();
                DocumentBuilderFactory XMLfactory=DocumentBuilderFactory.newInstance();
                try
                {
                    DocumentBuilder documentBuilder=XMLfactory.newDocumentBuilder();
                    Document XMLdocument=documentBuilder.parse(file);
                    org.w3c.dom.Element XMLroot=XMLdocument.getDocumentElement();
                    String type=XMLroot.getAttribute("type");
                    if(type.equalsIgnoreCase("database")) createDBDiagram(file.getName(),XMLroot);
                    if(type.equalsIgnoreCase("class")) createClassDiagram(file.getName(),XMLroot);
                    if(type.equalsIgnoreCase("case")) createCaseDiagram(file.getName(),XMLroot);
                    diagramy.get(diagramy.size()-1).setSource(file.getPath());
                    JMenu tempMenu=mainMenu.getMenu(0);
                    tempMenu.getItem(2).setEnabled(true);
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(fileChooser, "Projekt "+file.getName()+" jest uszkodzony");
                }
            }
        });
    
    CloseProject.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int tabNumber=zakladki.getSelectedIndex();
                if(JOptionPane.showConfirmDialog(rootPane, "Czy na pewno chcesz zamknąć projekt "+zakladki.getSelectedComponent().getName()+" ?","Ostrzeżenie", 1)==0)
                {
                diagramy.remove(tabNumber);
                zakladki.remove(tabNumber);
                }
            }
        });

    }

    public void createDBDiagram(String title, org.w3c.dom.Element XMLroot)
{
    if(title==null) title="Diagram bazy danych "+diagramy.size()+1;
    initializeDBMenu();
    Diagram temp;
    if(XMLroot==null) temp=new DBDiagram();
    else temp=new DBDiagram(XMLroot);
    setTabLayout(temp.panel);
    //temp.setName(title);
    diagramy.add(temp);
    zakladki.add(title,diagramy.get(diagramy.size()-1).panel);
    zakladki.setSelectedIndex(zakladki.getTabCount()-1);
    initializeDiagramPM(temp);
}

public void createClassDiagram(String title, org.w3c.dom.Element XMLroot)
{
    if(title==null) title="Diagram bazy danych "+diagramy.size()+1;
    initializeClassMenu();
    Diagram temp;
    if(XMLroot==null) temp=new ClassDiagram();
    else temp=new ClassDiagram(XMLroot);
    setTabLayout(temp.panel);
    //temp.setName(title);
    diagramy.add(temp);
    zakladki.add(title,diagramy.get(diagramy.size()-1).panel);
    zakladki.setSelectedIndex(zakladki.getTabCount()-1);
    initializeDiagramPM(temp);
}

public void createCaseDiagram(String title, org.w3c.dom.Element XMLroot)
{
    if(title==null) title="Diagram bazy danych "+diagramy.size()+1;
    initializeCaseDiagramMenu();

    Diagram temp;
    if(XMLroot==null) temp=new CaseDiagram();
    else temp=new CaseDiagram(XMLroot);
    setTabLayout(temp.panel);
    //temp.setName(title);
    diagramy.add(temp);
    zakladki.add("Diagram przypadków "+diagramy.size(),diagramy.get(diagramy.size()-1).panel);
    zakladki.setSelectedIndex(zakladki.getTabCount()-1);
    initializeDiagramPM(temp);
}

    //-------------------------------------------------------------------------- ustawienie drzewa elementów i plótna
    public void setTabLayout(JPanel panel) {
        
        // ustaw panel z drzewem elementów wstawionych
        panel.setLayout(null);        
        JPanel listaElementow = new JPanel();
        listaElementow.setBounds(0,0,szerokoscPM, wysokoscEkranu);
        
        // ustaw plótno do rozmieszczania elementow
        Plotno tempCanvas = new Plotno();        
        tempCanvas.setBackground(Color.WHITE);
        tempCanvas.setBounds(szerokoscPM, 0, szerokoscEkranu-szerokoscPM, wysokoscEkranu);
        panel.add(listaElementow);
        panel.add(tempCanvas);
        
    }

    public void saveProject(String path)
{
    int tabNumber=zakladki.getSelectedIndex();
    File file=new File(path);
    try
    {
    FileWriter stream=new FileWriter(file);
    stream.write(diagramy.get(tabNumber).toXML());
    stream.close();
    diagramy.get(tabNumber).setSource(file.getPath());
    zakladki.getSelectedComponent().setName(path);
    JMenu tempMenu=mainMenu.getMenu(0);
    tempMenu.getItem(2).setEnabled(true);
    }
    catch(Exception ex)
    {
        JOptionPane.showMessageDialog(rootPane, "Nie można zapisać projektu");
    }
}

    //-------------------------------------------------------------------------- ustaw drzewo elementów dla diagramu
    public void initializeDiagramPM(Diagram diagram) {
        
        JPanel panel = (JPanel)diagram.panel.getComponent(0);
        
        panel.setLayout(new FlowLayout());
        
        projectManagerElements = diagram.getElementsTree();
        projectManager = new JTree(projectManagerElements);
        contextMenuTree = new JPopupMenu("Menu");
        
        // MouseListener na drzewie elementów
        MouseListener contextMenuAction = new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            public void mousePressed(MouseEvent e) {
                
                // wyświetl menu jeżeli naciśniemy prawym przyciskiem
                try {
                    if(e.BUTTON3 == e.getButton()) {
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

    //-------------------------------------------------------------------------- inicjuj menu Elementy dla CaseDiagram
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
        JMenuItem mE = new JMenuItem("Związek \"Include\"");
        JMenuItem mF = new JMenuItem("Związek \"Extend\"");
        JMenuItem mG = new JMenuItem("Obszar Podsystemu");

        // dodaj skróty klawiaturowe
        mA.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.CTRL_MASK));
        mB.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.CTRL_MASK));
        mC.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_3, java.awt.event.InputEvent.CTRL_MASK));
        mE.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_5, java.awt.event.InputEvent.CTRL_MASK));
        mF.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_6, java.awt.event.InputEvent.CTRL_MASK));
        mG.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_7, java.awt.event.InputEvent.CTRL_MASK));
        
        // obsługa zdarzeń kliknięcia na pozycje w menu
        mA.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
//                JOptionPane.showMessageDialog(null, "Aktor", "testMenu 1", JOptionPane.ERROR_MESSAGE);
                ((CaseDiagram)tempCanvas.currentDiagram).addUseCaseElement(CaseDiagram.CaseDiagramTypes.ACTOR);
            }
            
        });
        
        mB.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
//                JOptionPane.showMessageDialog(null, "testMenu", "testMenu 2", JOptionPane.ERROR_MESSAGE);
                System.out.println("jest ok");
                ((CaseDiagram)tempCanvas.currentDiagram).addUseCaseElement(CaseDiagram.CaseDiagramTypes.USECASE);
            }
            
        });
        
        mC.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
//                JOptionPane.showMessageDialog(null, "testMenu", "testMenu 3", JOptionPane.ERROR_MESSAGE);
                ((CaseDiagram)tempCanvas.currentDiagram).addLink(CaseDiagram.CaseDiagramTypes.SIMPLELINK);
            }
            
        });
                
        mE.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
//                JOptionPane.showMessageDialog(null, "testMenu", "testMenu 5", JOptionPane.ERROR_MESSAGE);
                ((CaseDiagram)tempCanvas.currentDiagram).addLink(CaseDiagram.CaseDiagramTypes.INCLUDELINK);
            }
            
        });
        
        mF.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
              //  JOptionPane.showMessageDialog(null, "testMenu", "testMenu 6", JOptionPane.ERROR_MESSAGE);
                ((CaseDiagram)tempCanvas.currentDiagram).addLink(CaseDiagram.CaseDiagramTypes.EXTENDLINK);
            }
            
        });
        
        mG.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
              //  JOptionPane.showMessageDialog(null, "testMenu", "testMenu 6", JOptionPane.ERROR_MESSAGE);
                ((CaseDiagram)tempCanvas.currentDiagram).addSystemBox();
            }
            
        });
                
        // dodaj elementy menu do głównego korzenia Menu
        menuElementy.add(mA);
        menuElementy.add(mB);
        menuElementy.add(mC);
        menuElementy.add(mE);
        menuElementy.add(mF);
    }

    //-------------------------------------------------------------------------- inicjuj menu Elementy dla ClassDiagram
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

    //-------------------------------------------------------------------------- inicjuj menu Elementy dla DbDiagram
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
    
    //-------------------------------------------------------------------------- pobierz instancje tej klasy
    public MainWindow getInstance() {
        return this;
    }
}


//------------------------------------------------------------------------------ klasa kontenera do rysowania --------
class Plotno extends JPanel {
    
    //-------------------------------------------------------------------------- pola
    Diagram currentDiagram;
    private Boolean lock = new Boolean(true);
    private int movedElement = -1;
    private int currentElement = -1;
    private  JPopupMenu contextMenu=new JPopupMenu();
    private JMenuItem modifyElement=new JMenuItem("Modyfikuj element");
    private JMenuItem deleteElement=new JMenuItem("Usuń element");
    private JMenuItem setAutolocated=new JMenuItem("Ustaw pozycję automatycznie");
    private JMenuItem changeDisplayMode=new JMenuItem("Zwiń / Rozwiń element");
   
    //-------------------------------------------------------------------------- konstruktor główny         
    public Plotno() {
        
        // dodaj trzy elementy do menu 
        contextMenu.add(modifyElement);
        contextMenu.add(deleteElement);
        contextMenu.add(setAutolocated);
        contextMenu.add(setAutolocated);
        contextMenu.add(changeDisplayMode);
        // oraz dodaj zdarzenia kliknięcia dla nich
        modifyElement.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                
                // wyświetl okienko do modyfikacji elementu
                Vector<Element> elements = currentDiagram.getMousableElements();
                elements.get(currentElement).modifyElement(currentDiagram, elements.get(currentElement));

            }
            
        });
        
        deleteElement.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                
                // usuń element
                Vector<Element> elements = currentDiagram.getMousableElements();
                
                if(JOptionPane.showConfirmDialog(deleteElement, "Czy na pewno chcesz usunąć element "+elements.get(currentElement).toString()+" ?", TOOL_TIP_TEXT_KEY, 1)==0) {
                    elements.get(currentElement).deleteElement(currentDiagram,elements.get(currentElement));
                }
                
            }
        });

        setAutolocated.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                
                // autorozmieść element na płótnie
                Vector<Element> elements = currentDiagram.getMousableElements();
                elements.get(currentElement).setAutolocated();
                
            }
            
        });

        changeDisplayMode.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Vector<Element> elements=currentDiagram.getMousableElements();
                elements.get(currentElement).changeDisplayMode();
            }
        });
        
        // listener MouseMotionListener płótna
        addMouseListener(new MouseListener() {

            // kilknięcie na płótnie
            public void mouseClicked(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            // naciśnięcie na płótnie
            public void mousePressed(MouseEvent e) {

                currentElement = -1;
                //if(contextMenu.isShowing()) context
                
                // pobierz wszystkie elementy które można przesówac z diagramu
                Vector<Element> elements = currentDiagram.getMousableElements();
                
                // dla każdego elementu z tej listy...
                for(int i = 0; i < elements.size(); i++) {
                    
                    // sprawdź czy akurat nad tym elementym jest kursor myszy
                    if(elements.get(i).isMouseOverElement(e.getX(), e.getY())) {
                        
                        // ustaw ten element jako aktywny
                        currentElement = i;

                        // jeżeli kliknięto lewym przyciskiem to ustawiamy ten element jako przesówany element
                        if(e.getButton() == 1) {
                           movedElement = i;
                        }

                        // jeżeli kliknięto prawym to wyświetl menu
                        if(e.getButton() == 3) {
                           contextMenu.show(currentDiagram.panel, e.getX(), e.getY());
                        }
                        
                        // w danej chwili może być kilnięty jeden element tylko 
                        // dlatego nie trzeba już dalej sprawdzać innych
                        break;
                    }
                    
                }
            }

            // pusczenie przycisku myszy z płótna
            public void mouseReleased(MouseEvent e) {
                
                // zresetuj przesówany element
                if(movedElement > -1) {
                    movedElement = -1;
                }
                
            }

            // wejście myszy na płótno
            public void mouseEntered(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }
            
            // zejście myszy z płótna
            public void mouseExited(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        
        // listener MouseMotionListener płótna        
        addMouseMotionListener(new MouseMotionListener() {

            public void mouseDragged(MouseEvent e) {
                
                Vector<Element> elements = currentDiagram.getMousableElements();
                
                if(movedElement > -1) {
                    
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
    
    //-------------------------------------------------------------------------- ustaw domyślny diagram płótna
    public void setCurrentDiagram(Diagram currentDiagram) {
        this.currentDiagram = currentDiagram;
    }
    
    //-------------------------------------------------------------------------- odśwież zawartość diagramu
    @Override
    public void paint(Graphics g) {
        
        super.paint(g);
        
        if(lock){
            currentDiagram.drawElements(g);
        }
    }
    
}