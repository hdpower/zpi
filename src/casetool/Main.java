
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

//------------------------------------------------------------------------------ klasa startowa aplikacji ---------------------
public class Main {

    public static void main(String[] args) {
        MainWindow okno = new MainWindow();
    }

}

class MainWindow extends JFrame {
    
    private Integer szerokoscPM=new Integer(160);
    private Integer szerokoscEkranu=1280;
    private Integer wysokoscEkranu=720;
    private JMenuBar mainMenu;
    private JTabbedPane zakladki=new JTabbedPane();
    private ArrayList<Diagram> diagramy=new ArrayList<Diagram>();
    private DefaultMutableTreeNode projectManagerElements;
    private JTree projectManager;
    private JPopupMenu contextMenuTree;
    private JPopupMenu contextMenu;

public MainWindow()
    {
    Toolkit tools=Toolkit.getDefaultToolkit();
    szerokoscEkranu=tools.getScreenSize().width;
    wysokoscEkranu=tools.getScreenSize().height-40;
    setResizable(false);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(this.szerokoscEkranu,this.wysokoscEkranu);
    setTitle("CaseTool 1.0");
    mainMenu=new JMenuBar();
    Container content=getContentPane();
    content.add(zakladki);
    
    ChangeListener changeTab=new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                Plotno tempCanvas=(Plotno)diagramy.get(zakladki.getSelectedIndex()).panel.getComponent(1);
                tempCanvas.setCurrentDiagram(diagramy.get(zakladki.getSelectedIndex()));
                String temp=diagramy.get(zakladki.getSelectedIndex()).getType();
                initializeDiagramPM(diagramy.get(zakladki.getSelectedIndex()));
                if(temp.equals("database"))
                {
                    initializeDBMenu();
                }
                if(temp.equals("case"))
                {
                    initializeCaseDiagramMenu();
                }
                if(temp.equals("class"))
                {
                    initializeClassMenu();
                }
            }
        };
    zakladki.addChangeListener(changeTab);

    setJMenuBar(mainMenu);
    initializeMainMenu();
    setVisible(true);

    }

public void initializeMainMenu()
   {
    ActionListener createCasesDiagramClick=new ActionListener()
        {

        public void actionPerformed(ActionEvent e)
            {
            initializeCaseDiagramMenu();

            Diagram temp=new CaseDiagram();
            setTabLayout(temp.panel);

            diagramy.add(temp);
            zakladki.add("Diagram przypadków "+diagramy.size(),diagramy.get(diagramy.size()-1).panel);
            initializeDiagramPM(temp);
            }
        };

        ActionListener createClassDiagramClick=new ActionListener()
        {

        public void actionPerformed(ActionEvent e)
            {
            initializeClassMenu();

            Diagram temp=new ClassDiagram();
            setTabLayout(temp.panel);

            diagramy.add(temp);
            zakladki.add("Diagram klas "+diagramy.size(),diagramy.get(diagramy.size()-1).panel);
            initializeDiagramPM(temp);
            }
        };

        ActionListener createDBDiagramClick=new ActionListener()
        {

        public void actionPerformed(ActionEvent e)
            {
            initializeDBMenu();
            Diagram temp=new DBDiagram();
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
    JMenuItem createCasesDiagram=new JMenuItem("Stwórz diagram przypadków");
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

public void setTabLayout(JPanel panel)
    {
    panel.setLayout(null);
    JPanel listaElementow=new JPanel();
    listaElementow.setBounds(0,0,szerokoscPM,wysokoscEkranu);
    Plotno tempCanvas =new Plotno();
    tempCanvas.setBackground(Color.WHITE);
    tempCanvas.setBounds(szerokoscPM,0,szerokoscEkranu-szerokoscPM,wysokoscEkranu);
    panel.add(listaElementow);
    panel.add(tempCanvas);
    }

public void initializeDiagramPM(Diagram diagram)
    {
    JPanel panel=(JPanel)diagram.panel.getComponent(0);
    panel.setLayout(new FlowLayout());
    projectManagerElements=diagram.getElementsTree();
    projectManager=new JTree(projectManagerElements);
    contextMenuTree=new JPopupMenu("Menu");
    MouseListener contextMenuAction=new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet.");
            }

            public void mousePressed(MouseEvent e) {
                try
                {
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
        
        JMenu menuElementy = mainMenu.getMenu(1);
        
        menuElementy.setVisible(true);               
        menuElementy.removeAll();
        
        menuElementy.add(new JMenuItem("Aktor"));
        menuElementy.add(new JMenuItem("Przypadek Użycia"));
        menuElementy.add(new JMenuItem("Związek Prosty"));
        menuElementy.add(new JMenuItem("Związek \"Include\""));
        menuElementy.add(new JMenuItem("Związek \"Extends\""));
        menuElementy.add(new JMenuItem("Podsystem"));
    }

public void initializeClassMenu()
    {
    JMenu menuElementy=mainMenu.getMenu(1);
    menuElementy.setVisible(true);
    JMenuItem addClass=new JMenuItem("Dodaj klasę");
    JMenuItem addInterface=new JMenuItem("Dodaj interfejs");
    JMenuItem addStaticClass=new JMenuItem("Dodaj klasę statyczną");
    JMenuItem addAbstractClass=new JMenuItem("Dodaj klasę abstrakcyjna");
    menuElementy.removeAll();
    menuElementy.add(addClass);
    menuElementy.add(addInterface);
    menuElementy.add(addStaticClass);
    menuElementy.add(addAbstractClass);
    }

public void initializeDBMenu()
    {
    JMenu menuElementy=mainMenu.getMenu(1);
    menuElementy.setVisible(true);
    JMenuItem addTable=new JMenuItem("Dodaj tabelę");
    JMenuItem addView=new JMenuItem("Dodaj perspektywę");
    JMenuItem addFunction=new JMenuItem("Dodaj funkcję");
    JMenuItem addProcedure=new JMenuItem("Dodaj procedurę");
    JMenuItem addTrigger=new JMenuItem("Dodaj wyzwalacz");
    menuElementy.removeAll();
    menuElementy.add(addTable);
    menuElementy.add(addView);
    menuElementy.add(addFunction);
    menuElementy.add(addProcedure);
    menuElementy.add(addTrigger);
    }
}

class Plotno extends JPanel
{
    private Boolean lock=new Boolean(true);
    private Diagram currentDiagram;
    private int movedElement=-1;
    private int currentElement=-1;
    private  JPopupMenu contextMenu=new JPopupMenu();
                JMenuItem modifyElement=new JMenuItem("Modyfikuj element");
                JMenuItem deleteElement=new JMenuItem("Usuń element");
                JMenuItem setAutolocated=new JMenuItem("Ustaw pozycję automatycznie");

    
    public void setCurrentDiagram(Diagram currentDiagram)
    {
        this.currentDiagram=currentDiagram;
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(lock) currentDiagram.drawElements(g);
    }

    public Plotno()
    {
                contextMenu.add(modifyElement);
                contextMenu.add(deleteElement);
                contextMenu.add(setAutolocated);
                modifyElement.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Vector<Element> elements=currentDiagram.getMousableElements();
                elements.get(currentElement).modifyElement(currentDiagram,elements.get(currentElement));
            }
        });
                deleteElement.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Vector<Element> elements=currentDiagram.getMousableElements();
                if(JOptionPane.showConfirmDialog(deleteElement, "Czy na pewno chcesz usunąć element "+elements.get(currentElement).toString()+" ?", TOOL_TIP_TEXT_KEY, 1)==0)
                    elements.get(currentElement).deleteElement(currentDiagram,elements.get(currentElement));
            }
        });

                setAutolocated.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Vector<Element> elements=currentDiagram.getMousableElements();
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
                Vector<Element> elements=currentDiagram.getMousableElements();
                for(int i=0;i<elements.size();i++)
                    if(elements.get(i).isMouseOverElement(e.getX(), e.getY()))
                    {
                        currentElement=i;
                       if(e.getButton()==1)
                           movedElement=i;
                       if(e.getButton()==3)
                       {
                           contextMenu.show(currentDiagram.panel, e.getX(), e.getY());
                       }
                       break;
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
                Vector<Element> elements=currentDiagram.getMousableElements();
                if(movedElement>-1)
                {
                    elements.get(movedElement).setPosition(e.getX(), e.getY());
                    repaint();
                }
            }

            public void mouseMoved(MouseEvent e) {

                
                Vector<Element> elements=currentDiagram.getMousableElements();

                for(int i=0;i<elements.size();i++)
                    if(elements.get(i).isMouseOverElement(e.getX(), e.getY()))
                        elements.get(i).setStroke(2);
                    else
                        elements.get(i).setStroke(1);     

                repaint();
            }
        });
    }
}
