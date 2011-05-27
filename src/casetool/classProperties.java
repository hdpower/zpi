/*
 * Piotr Rutkowski
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package casetool;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;


/**
 *
 * @author Piotr
 */
public class classProperties extends JDialog {
    
    private ClassDiagram classDiagram;
    private HashMap<String, Color> classColors;
    private Container content;
    private JPanel panel;
    private JTextField className;
    private JTextArea classDocumentation;
    private JTable classContainer;
    private DefaultTableModel classContainerModel;
    private JTable classMethodsContainer;
    private JScrollPane scrollPanelMethodsContainer;
    private DefaultTableModel classMethodsContainerModel;
    private JScrollPane scrollPaneClassContainer;
    private JComboBox classColorsCombo;   
    private JComboBox classVisibilityCombo;
    private JTabbedPane classTabbs;
    private JPanel classMainPanel;
    private JPanel classAtributesPanel;
    private JCheckBox classAbstractCheckBox;
    private JCheckBox classStaticCheckBox;
    private JComboBox classAtributesTypesComboBox;
    private JComboBox classMethodsTypesComboBox;
    private JPanel classMethodsPanel;
    private ArrayList<Object> atributesTypes = new ArrayList<Object>();
    private JCheckBox check;
    private HashMap<Integer, JTextArea> metodParameters;
    private HashMap<Integer, MethodsParameters> metodParameters2;
    private JPanel parametersCardLayoutPanel;
    private Class classData;
    
    public classProperties(ClassDiagram cd) {
        
        this.classDiagram = cd;
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setTitle("Tworzenie nowej KLASY [Diagram klas - UML]");
        this.setSize(new Dimension(880, 740));
        this.content = this.getContentPane();
        this.panel = new JPanel();
        this.panel.setSize(new Dimension(600, 700)); 
        initializeClassColors();        
        initializeClassVisibility();
        initializeClassAtributesTypes();
        classTabbs = new JTabbedPane();
        initializeClassContainer();
        initializeClassMethodsContainter();
        metodParameters = new HashMap<Integer, JTextArea>();
        metodParameters2 = new HashMap<Integer, MethodsParameters>();
        
        Object columnNames[] = {"Nazwa parametru", "Typ parametru", "Typ przekazywania"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable tabela = new JTable(model);
        JScrollPane panelPoczat = new JScrollPane(tabela);
        parametersCardLayoutPanel.add(panelPoczat, "0");
        panelPoczat.setPreferredSize(new Dimension(600, 200));
        tabela.getTableHeader().setReorderingAllowed(false);
    }
    
    public void setClass(Class value) {
        this.classData = value;
    }
    
    private class MethodsParameters {
        
        public JScrollPane panel;
        public JTable parametry;
        public DefaultTableModel modelParametrow;               
    }
    
    private class CheckBoxCellEditor extends AbstractCellEditor implements TableCellEditor {  
        
        private CheckBoxRenderer renderer;
          
        public CheckBoxCellEditor() {  
            renderer = new CheckBoxRenderer();
        }  
          
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,  int row, int column) {  
   
            return renderer.getTableCellRendererComponent(table, value, isSelected, row, column); 
        }
        
        public Object getCellEditorValue() {  
            if(renderer.isSelected()) return true;
            return false;  
        }  
        
    }  
      
    private class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {  

        public CheckBoxRenderer() {  
            super();    
            setHorizontalAlignment(SwingConstants.CENTER);  
        }  

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,  int row, int column) {  
 
            if (isSelected)
            {
                    this.setBackground(classMethodsContainer.getSelectionBackground());
                    this.setForeground(classMethodsContainer.getSelectionForeground());
            }
            else
            {
                    this.setBackground(classMethodsContainer.getBackground());
                    this.setForeground(classMethodsContainer.getForeground());
            } 
            
            setSelected((value != null && ((Boolean) value).booleanValue()));
            return this;   
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            
            if (isSelected)
            {
                    this.setBackground(classMethodsContainer.getSelectionBackground());
                    this.setForeground(classMethodsContainer.getSelectionForeground());
            }
            else
            {
                    this.setBackground(classMethodsContainer.getBackground());
                    this.setForeground(classMethodsContainer.getForeground());
            } 
            
            setSelected((value != null && ((Boolean) value).booleanValue()));
            return this; 
        }
    }
    
    Action action = new AbstractAction("CheckBox") {
    
        public void actionPerformed(ActionEvent evt) {

            JCheckBox cb = (JCheckBox)evt.getSource();

            boolean isSel = cb.isSelected();
            if (isSel) {
                System.out.println("true");
            } else {
                System.out.println("false");
            }
        }
    };
    
    private void initializeClassColors() {
        
        classColors = new HashMap<String, Color>();
        classColors.put("Czarny", Color.black);
        classColors.put("Zielony",Color.green);
        classColors.put("Niebieski",Color.blue);
        classColors.put("Czerwony",Color.red);
        classColors.put("Szary",Color.gray);
        classColors.put("Żółty",Color.yellow);
        
        classColorsCombo = new JComboBox(classColors.keySet().toArray());
        classColorsCombo.setPreferredSize(new Dimension(600, 30));
        //classColorsCombo.setSelectedIndex(2);
        
    }
    
    private void initializeClassContainer() {
        
        Object columnNames[] = {"Nazwa atrybutu", "Typ", "Widoczność", "Liczebność", "Wartość początkowa"};
        Object atributesVisibility[] = {"private", "protected", "public"};
        Object atributesCount[] = {"1", "*", "0..1", "0..*", "1..1", "1..*"};
        
        classContainerModel = new DefaultTableModel(columnNames, 0);
        classContainer = new JTable(classContainerModel);        
        scrollPaneClassContainer = new JScrollPane(classContainer);
        scrollPaneClassContainer.setPreferredSize(new Dimension(600, 600));
        classContainer.getTableHeader().setReorderingAllowed(false);
        
        JComboBox classAtributesVisibility = new JComboBox(atributesVisibility);
        JComboBox classAtributesCount = new JComboBox(atributesCount);
        
        classContainer.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(classAtributesTypesComboBox));
        classContainer.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(classAtributesVisibility));
        classContainer.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(classAtributesCount));
    }
    
    private void initializeClassMethodsContainter() {
        Object columnNames[] = {"Nazwa metody", "Typ zwracany", "Widoczność", "Polimorficzna"};
        Object atributesVisibility[] = {"private", "protected", "public"};
        
        classMethodsContainerModel = new DefaultTableModel(columnNames, 0);
        classMethodsContainer = new JTable(classMethodsContainerModel);
        scrollPanelMethodsContainer = new JScrollPane(classMethodsContainer);
        scrollPanelMethodsContainer.setPreferredSize(new Dimension(600, 350));
        classMethodsContainer.getTableHeader().setReorderingAllowed(false);
        
        JComboBox classMethodsVisibility = new JComboBox(atributesVisibility);
        
        classMethodsContainer.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(classMethodsTypesComboBox));
        classMethodsContainer.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(classMethodsVisibility));
        check = new JCheckBox(action);
        classMethodsContainer.getColumnModel().getColumn(3).setCellEditor(new CheckBoxCellEditor());
        classMethodsContainer.getColumnModel().getColumn(3).setCellRenderer(new CheckBoxRenderer());
        
        parametersCardLayoutPanel = new JPanel(new CardLayout());
                
        
//        Object dane[] = {"1", "int", "private", true}; 
//        Object dane2[] = {"2", "int", "private", false}; 
//        classMethodsContainerModel.addRow(dane);
//        classMethodsContainerModel.addRow(dane2);
//        System.out.print(classMethodsContainerModel.getValueAt(0, 3));
        
    }
    
    private void initializeClassVisibility() {
        
        Object classVisibility[] = {"private", "protected", "public"};
        classVisibilityCombo = new JComboBox(classVisibility);
    }
    
    private void initializeClassAtributesTypes() {
        
        atributesTypes.add("byte"); atributesTypes.add("short"); atributesTypes.add("int");
        atributesTypes.add("long"); atributesTypes.add("float"); atributesTypes.add("double");
        atributesTypes.add("char"); atributesTypes.add("string"); atributesTypes.add("boolean");
        classAtributesTypesComboBox = new JComboBox(atributesTypes.toArray());
        //classAtributesTypesComboBox.setEditable(true);
        
        classMethodsTypesComboBox = new JComboBox(atributesTypes.toArray());
        //classMethodsTypesComboBox.setEditable(true);
    }
    
    public void showWindow() {
       
        
        //Tabs classMain
        JLabel labelClassName = new JLabel("Nazwa klasy: ");
        className = new JTextField();
        className.setPreferredSize(new Dimension(600, 30));       
        
        labelClassName.setPreferredSize(new Dimension(600, 30));
        
        JLabel labelClassColors = new JLabel("Kolor elementu UML na diagramie: ");
        labelClassColors.setPreferredSize(new Dimension(600, 30));       
        
        JButton addRowContainerModelAtributes = new JButton("Dodaj wiersz");
        addRowContainerModelAtributes.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                int lp = classContainer.getRowCount() + 1;
                Object column[] = {"Atrybut " + lp, "int", "private", "1..*", ""};
                classContainerModel.addRow(column);
            }
        });
        
        JButton deleteRowContainerModelAtributes = new JButton("Usuń wiersz");
        deleteRowContainerModelAtributes.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int selectedRows[] = classContainer.getSelectedRows();
                
                if(selectedRows.length > 0) {
                    for(int i = selectedRows.length-1; i >= 0; i--)
                        classContainerModel.removeRow(selectedRows[i]);
                }
            }
        });
        
        JButton addAtributesType = new JButton("Nowy typ danych");
        addAtributesType.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String nowyTyp = JOptionPane.showInputDialog(null, "Podaj nazwę nowego typu danych:", "Dodawanie nowego typu danych", JOptionPane.INFORMATION_MESSAGE);
                
                if(!nowyTyp.isEmpty()) {
                    classAtributesTypesComboBox.addItem(nowyTyp);
                    classAtributesTypesComboBox.repaint();
                    
                    classMethodsTypesComboBox.addItem(nowyTyp);
                    classMethodsTypesComboBox.repaint();
                }
            }
        });
        
        JButton cloneRowContainerModelAtributes = new JButton("Duplikuj");
        cloneRowContainerModelAtributes.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) { 
                
                classContainer.editCellAt(-1, -1);
                
                if(classContainer.getSelectedRow() >= 0) {
                    
                    Object kopiaWiersza[] = new Object[5];
                    
                    for(int i=0; i < 5; i++) {

                        kopiaWiersza[i] = classContainer.getValueAt(classContainer.getSelectedRow(), i);
                    }

                    classContainerModel.addRow(kopiaWiersza);
                }
            }
        });
        
        JButton addRowContainerModelMethods = new JButton("Dodaj wiersz");
        addRowContainerModelMethods.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int lp = classMethodsContainer.getRowCount() + 1;
                Object column[] = {"Metoda " + lp, "int", "private", true};
                classMethodsContainerModel.addRow(column);
                
                JTextArea nowa = new JTextArea("Parametr 1 [Metody " + lp + "]");

                Object columnNames[] = {"Nazwa parametru", "Typ parametru", "Typ przekazywania"};
                //Object atributesVisibility[] = {"private" + lp, "protected", "public"};
                
                Object trybPrzekazywania[] = {"* - wskaźnik", "& - referencja", "wartość"};
                JComboBox typyZwracane = new JComboBox(atributesTypes.toArray());
                JComboBox trybPrzekazywaniaJCB   = new JComboBox(trybPrzekazywania);     
                
                MethodsParameters parameters = new MethodsParameters();
                parameters.modelParametrow = new DefaultTableModel(columnNames, 0);                
                parameters.parametry = new JTable(parameters.modelParametrow);
                parameters.panel = new JScrollPane(parameters.parametry);
                parameters.parametry.getTableHeader().setReorderingAllowed(false);
                //parameters.modelParametrow.addRow(atributesVisibility);
                parameters.panel.setPreferredSize(new Dimension(600, 200));
                parameters.parametry.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(typyZwracane));
                parameters.parametry.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(trybPrzekazywaniaJCB));
                typyZwracane.setEditable(true);
                //trybPrzekazywaniaJCB.setEditable(true);
                metodParameters2.put(lp, parameters);
                
                metodParameters.put(lp, nowa);
                //System.out.print(classMethodsContainerModel.getValueAt(0, 3));
                parametersCardLayoutPanel.add(metodParameters2.get(lp).panel, String.valueOf(lp));            
                
                CardLayout cl = (CardLayout)parametersCardLayoutPanel.getLayout();
                cl.show(parametersCardLayoutPanel, String.valueOf(lp));
                //System.out.println("Dodałej JTextArea nr: " + lp);
            }
        });
        
        JButton deleteRowContainerModelMethods = new JButton("Usuń wiersz");
        deleteRowContainerModelMethods.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int selectedRows = classMethodsContainer.getRowCount()-1;               
                
                //classMethodsContainer.clearSelection();
                
                if(selectedRows >= 0) {
                    classMethodsContainerModel.removeRow(selectedRows);
                    int lp = selectedRows+1;
                    CardLayout cl = (CardLayout)parametersCardLayoutPanel.getLayout();
                    cl.removeLayoutComponent(metodParameters2.get(lp).panel);
                    cl.show(parametersCardLayoutPanel, String.valueOf(lp-1));
                    metodParameters2.remove(lp);
                    //System.out.println("Usunąłem JTextArea nr: " + lp);                   
                }
            }
        });
        
        JButton addMethodsTypes = new JButton("Nowy typ danych");
        addMethodsTypes.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String nowyTyp = JOptionPane.showInputDialog(null, "Podaj nazwę nowego typu danych:", "Dodawanie nowego typu danych", JOptionPane.INFORMATION_MESSAGE);
                
                if(!nowyTyp.isEmpty()) {
                    classAtributesTypesComboBox.addItem(nowyTyp);
                    classAtributesTypesComboBox.repaint();
                    
                    classMethodsTypesComboBox.addItem(nowyTyp);
                    classMethodsTypesComboBox.repaint();
                }
            }
        });
        
        classMethodsContainer.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                int lp = classMethodsContainer.getSelectedRow() + 1;
                CardLayout cl = (CardLayout)parametersCardLayoutPanel.getLayout();
                cl.show(parametersCardLayoutPanel, String.valueOf(lp));
                //System.out.println("Zaznaczyłem wiersz :" + classMethodsContainer.getSelectedRow());               
            }
        });
        
        JButton cloneRowContainerModelMethods = new JButton("Duplikuj");
        cloneRowContainerModelMethods.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if(classMethodsContainer.getSelectedRow() >= 0) {
                    
                    classMethodsContainer.editCellAt(-1, -1);
                    
                    Object kopiaWiersza[] = new Object[4];
                    
                    for(int i=0; i < 4; i++) {

                        kopiaWiersza[i] = classMethodsContainer.getValueAt(classMethodsContainer.getSelectedRow(), i);
                    }
                    
                    int lp = classMethodsContainer.getRowCount() + 1;
                    
                    classMethodsContainerModel.addRow(kopiaWiersza);                                        

//                    JTextArea nowa = new JTextArea(metodParameters.get(lp-1).getText());
//                    metodParameters.put(lp, nowa);
//                    //System.out.print(classMethodsContainerModel.getValueAt(0, 3));
//                    parametersCardLayoutPanel.add(metodParameters.get(lp), String.valueOf(lp)); 
                    
                    Object columnNames[] = {"Nazwa parametru", "Typ parametru", "Typ przekazywania"};
                    Object trybPrzekazywania[] = {"* - wskaźnik", "& - referencja", "wartość"};
                    JComboBox typyZwracane = new JComboBox(atributesTypes.toArray());
                    JComboBox trybPrzekazywaniaJCB   = new JComboBox(trybPrzekazywania);

                    MethodsParameters parameters = new MethodsParameters();
                    parameters.modelParametrow = new DefaultTableModel(columnNames, 0);                
                    parameters.parametry = new JTable(parameters.modelParametrow);
                    parameters.panel = new JScrollPane(parameters.parametry);
                    parameters.parametry.getTableHeader().setReorderingAllowed(false); 
                    parameters.panel.setPreferredSize(new Dimension(600, 200));
                    parameters.parametry.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(typyZwracane));
                    parameters.parametry.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(trybPrzekazywaniaJCB));
                    metodParameters2.get(classMethodsContainer.getSelectedRow()+1).parametry.editCellAt(-1, -1);
                    int iw = metodParameters2.get(classMethodsContainer.getSelectedRow()+1).modelParametrow.getRowCount();
                    //System.out.println("Liczba do skopiowania: " + iw);
                    for(int i=0; i<iw; i++) {
                        Object wiersz[] = new Object[3];
                        wiersz[0] = metodParameters2.get(classMethodsContainer.getSelectedRow()+1).modelParametrow.getValueAt(i, 0);
                        wiersz[1] = metodParameters2.get(classMethodsContainer.getSelectedRow()+1).modelParametrow.getValueAt(i, 1);
                        wiersz[2] = metodParameters2.get(classMethodsContainer.getSelectedRow()+1).modelParametrow.getValueAt(i, 2);
                        //System.out.println(wiersz[0]);
                        //System.out.println(wiersz[1]);
                        //System.out.println(wiersz[2]);
                        parameters.modelParametrow.addRow(wiersz);
                    }
                    metodParameters2.put(lp, parameters);   
                    parametersCardLayoutPanel.add(metodParameters2.get(lp).panel, String.valueOf(lp));      

                    CardLayout cl = (CardLayout)parametersCardLayoutPanel.getLayout();
                    cl.show(parametersCardLayoutPanel, String.valueOf(lp));
                    //System.out.println("Dodałej JTextArea nr: " + lp);
                }
            }
        });
        
        JButton addMethodParameters = new JButton("Dodaj parametr");
        addMethodParameters.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if(classMethodsContainer.getSelectedRow() >= 0) {
                    int iloscParametrow = metodParameters2.get(classMethodsContainer.getSelectedRow()+1).modelParametrow.getRowCount() + 1;
                    int ktoraMetoda = classMethodsContainer.getSelectedRow() + 1;
                    Object wiersz[] = {"Parametr " + iloscParametrow + " [Metody " + ktoraMetoda + "]" , "int", "wartość"};               
                    metodParameters2.get(classMethodsContainer.getSelectedRow()+1).modelParametrow.addRow(wiersz);
                }
            }
        });
        
        JButton deleteMethodParameters = new JButton("Usuń parametr");
        deleteMethodParameters.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if(classMethodsContainer.getSelectedRow() >= 0) {          
                    int usuwany = metodParameters2.get(classMethodsContainer.getSelectedRow()+1).modelParametrow.getRowCount()-1;
                    if(usuwany >= 0) metodParameters2.get(classMethodsContainer.getSelectedRow()+1).modelParametrow.removeRow(usuwany);
                }
            }
        });        
        
        classMainPanel = new JPanel();
        classMainPanel.setLayout(new FlowLayout());
        classMainPanel.setPreferredSize(new Dimension(600, 600));
        classMainPanel.add(labelClassName);
        classMainPanel.add(className);
        classMainPanel.add(labelClassColors);
        classMainPanel.add(classColorsCombo);
        
        JLabel classVisibilityLabel = new JLabel("Widoczność: ");
        classVisibilityLabel.setPreferredSize(new Dimension(600, 30));
        classMainPanel.add(classVisibilityLabel);
        classMainPanel.add(classVisibilityCombo);
        classVisibilityCombo.setPreferredSize(new Dimension(600, 30));
        
        JLabel classDocumentationLabel = new JLabel("Dokumentacja: ");
        classDocumentationLabel.setPreferredSize(new Dimension(600, 30));
        classMainPanel.add(classDocumentationLabel);
        classDocumentation = new JTextArea();
        classDocumentation.setPreferredSize(new Dimension(600, 250));
        classMainPanel.add(classDocumentation);
        
        JLabel odstep = new JLabel("");
        odstep.setPreferredSize(new Dimension(800, 30));
        classMainPanel.add(odstep);
        
        classAbstractCheckBox = new JCheckBox("Klasa Abstrakcyjna");
        classMainPanel.add(classAbstractCheckBox);
        
        classStaticCheckBox = new JCheckBox("Klasa Statyczna");
        classMainPanel.add(classStaticCheckBox);
        
        classTabbs.add("Klasa", classMainPanel);
        
        //Tabs classAtributes        
        classAtributesPanel = new JPanel();  
        classAtributesPanel.setLayout(new BoxLayout(classAtributesPanel, BoxLayout.X_AXIS));
        classAtributesPanel.add(scrollPaneClassContainer);
        JPanel atributesButtonsPanel = new JPanel();
        atributesButtonsPanel.setLayout(new FlowLayout());
        atributesButtonsPanel.setPreferredSize(new Dimension(200, 600));
        atributesButtonsPanel.add(addRowContainerModelAtributes);
        atributesButtonsPanel.add(deleteRowContainerModelAtributes);
        atributesButtonsPanel.add(addAtributesType);
        atributesButtonsPanel.add(cloneRowContainerModelAtributes);
        addRowContainerModelAtributes.setPreferredSize(new Dimension(150, 30));
        deleteRowContainerModelAtributes.setPreferredSize(new Dimension(150, 30));
        addAtributesType.setPreferredSize(new Dimension(150, 30));
        cloneRowContainerModelAtributes.setPreferredSize(new Dimension(150, 30));
        classAtributesPanel.add(atributesButtonsPanel);
        
        classTabbs.add("Atrybuty", classAtributesPanel); 
        
        //Tabs classMethods
        classMethodsPanel = new JPanel();
        classMethodsPanel.setLayout(new BoxLayout(classMethodsPanel, BoxLayout.X_AXIS));
        JPanel metpar = new JPanel();
        metpar.setLayout(new BoxLayout(metpar, BoxLayout.Y_AXIS));
        metpar.add(scrollPanelMethodsContainer);
        JLabel parametry = new JLabel("Parametry: ");
        parametry.setAlignmentX(CENTER_ALIGNMENT);
        parametry.setPreferredSize(new Dimension(100, 30));
        metpar.add(parametry);
        metpar.add(parametersCardLayoutPanel);
        classMethodsPanel.add(metpar);
        JPanel methodsButtonPanel = new JPanel();
        methodsButtonPanel.setLayout(new FlowLayout());
        methodsButtonPanel.setPreferredSize(new Dimension(200, 600));
        methodsButtonPanel.add(addRowContainerModelMethods);
        methodsButtonPanel.add(deleteRowContainerModelMethods);
        methodsButtonPanel.add(addMethodsTypes);
        methodsButtonPanel.add(cloneRowContainerModelMethods);
        JPanel odstepButt = new JPanel();
        methodsButtonPanel.add(odstepButt);
        odstepButt.setPreferredSize(new Dimension(150, 230));
        methodsButtonPanel.add(addMethodParameters);
        methodsButtonPanel.add(deleteMethodParameters);
        addRowContainerModelMethods.setPreferredSize(new Dimension(150, 30));
        deleteRowContainerModelMethods.setPreferredSize(new Dimension(150, 30));
        addMethodsTypes.setPreferredSize(new Dimension(150, 30));
        cloneRowContainerModelMethods.setPreferredSize(new Dimension(150, 30));
        addMethodParameters.setPreferredSize(new Dimension(150, 30));
        deleteMethodParameters.setPreferredSize(new Dimension(150, 30));
        classMethodsPanel.add(methodsButtonPanel);
        
        classTabbs.add("Metody", classMethodsPanel);        
        
        //Create view
        content.setLayout(new FlowLayout());
        content.add(classTabbs);
        
        //Main Button Menu
        JButton buttonOK = new JButton("OK");
        buttonOK.setPreferredSize(new Dimension(100, 30));
        JButton buttonAnuluj = new JButton("Anuluj");
        buttonAnuluj.setPreferredSize(new Dimension(100, 30));
        JPanel mainButtonPanel = new JPanel();
        mainButtonPanel.add(buttonOK);
        mainButtonPanel.add(buttonAnuluj);
        
        buttonOK.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                
                boolean isCorrect = true;
                String  error = "";
                classContainer.editCellAt(-1, -1);
                classMethodsContainer.editCellAt(-1, -1);
                
                if(className.getText().isEmpty()) {                    
                    error += " - Podaj nazwę klasy! \n";
                    isCorrect = false;
                }
                
                for(int i=0; i<classContainer.getRowCount(); i++) {
                    String nazwa = classContainer.getValueAt(i, 0).toString();
                    if(nazwa.isEmpty()) {
                        error += " - Podaj nazwę atrybutu: " + (i+1) + "\n";
                        isCorrect = false;
                    }
                }
                
                for(int i=0; i<classMethodsContainer.getRowCount(); i++) {
                    String nazwa = classMethodsContainer.getValueAt(i, 0).toString();
                    if(nazwa.isEmpty()) {
                        error += " - Podaj nazwę metody: " + (i+1) + "\n";                                               
                        isCorrect = false;
                    }
                    
                    int iloscParametrow = metodParameters2.get(i+1).modelParametrow.getRowCount();
                    for(int j=0; j<iloscParametrow; j++) {
                        metodParameters2.get(i+1).parametry.editCellAt(-1, -1);
                        String nazwaParametru = metodParameters2.get(i+1).parametry.getValueAt(j, 0).toString();
                        if(nazwaParametru.isEmpty()) {
                            error += " - Podaj nazwę parametru: " + (j+1) + " Metody: " + (i+1) + "\n";
                            isCorrect = false;
                        }
                    }
                }
                
                if(isCorrect) {
                    Class tempClass;
                    tempClass = new Class(className.getText(), classColors.get(classColorsCombo.getSelectedItem().toString()));
                    tempClass.setAbstract(classAbstractCheckBox.isSelected());
                    tempClass.setStatic(classStaticCheckBox.isSelected());
                    tempClass.setDocumentation(classDocumentation.getText());
                    tempClass.setVisibility(classVisibilityCombo.getSelectedItem().toString());
                    
                    for(int i=0; i<classContainer.getRowCount(); i++) {
                        String nazwa = classContainer.getValueAt(i, 0).toString();
                        String typ   = classContainer.getValueAt(i, 1).toString();
                        String widocznosc = classContainer.getValueAt(i, 2).toString();
                        String liczebnosc = classContainer.getValueAt(i, 3).toString();
                        String poczatkowa = classContainer.getValueAt(i, 4).toString();
                        
                        tempClass.dodajAtrybut(nazwa, typ, widocznosc, liczebnosc, poczatkowa);
                    }
                    
                    for(int i=0; i<classMethodsContainer.getRowCount(); i++) {
                        String nazwa = classMethodsContainer.getValueAt(i, 0).toString();
                        String typZw = classMethodsContainer.getValueAt(i, 1).toString();
                        String widoc = classMethodsContainer.getValueAt(i, 2).toString();
                        boolean poli = false;//(boolean)classMethodsContainer.getValueAt(i, 3);
                        
                        Class klasa = new Class();
                        Class.ClassMethod metoda = klasa.new ClassMethod(nazwa, typZw, widoc, poli);                        
                        
                        int iloscParametrow = metodParameters2.get(i+1).modelParametrow.getRowCount();
                        for(int j=0; j<iloscParametrow; j++) {
                            metodParameters2.get(i+1).parametry.editCellAt(-1, -1);
                            String nazwaParametru = metodParameters2.get(i+1).parametry.getValueAt(j, 0).toString();
                            String typParametru   = metodParameters2.get(i+1).parametry.getValueAt(j, 1).toString();
                            String trybParametru  = metodParameters2.get(i+1).parametry.getValueAt(j, 2).toString();
                            
                            Class.ClassMethod.Parametr parametr = metoda.new Parametr(nazwaParametru, typParametru, trybParametru);
                            metoda.dodajParametr(parametr);
                        }
                        
                        tempClass.dodajMetode(metoda);
                    }

                    if(classData == null) classDiagram.classes.add(tempClass);
                    else classDiagram.classes.set(classDiagram.classes.indexOf(classData), tempClass);

                    //classDiagram.refreshTables();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, error, "BŁĄD!!!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        buttonAnuluj.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        content.add(mainButtonPanel);
        
        /*
         * Ustawienie wartości istniejących
         */
        
        if(classData != null) {
            
            //Ustawienie nazwy klasy
            className.setText(classData.toString());
            //Ustawienie nazwy klasy
            
            //Ustawienie zaznaczonego koloru
            int selectedColor = 0;
            for(int i=0; i<classColorsCombo.getItemCount(); i++) {
                //System.out.println(classData.color);
                //System.out.println(classColors.get(classColorsCombo.getItemAt(i).toString()));
                if(classData.color == classColors.get(classColorsCombo.getItemAt(i).toString())) {                   
                    selectedColor = i;
                }
            }
            classColorsCombo.setSelectedIndex(selectedColor);
            //Ustawienie zaznaczonego koloru
            
            //Ustawienie widoczności klasy
            int selectedVisibility = 0;
            for(int i=0; i<classVisibilityCombo.getItemCount(); i++) {
                if(classData.getVisibility().equals(classVisibilityCombo.getItemAt(i))) {
                    selectedVisibility = i;
                }
            }
            classVisibilityCombo.setSelectedIndex(selectedVisibility);
            //Ustawienie widoczności klasy
            
            //Ustawienie dokumentacji klasy
            classDocumentation.setText(classData.getDocumentation());
            //Ustawienie dokumentacji klasy
            
            classAbstractCheckBox.setSelected(classData.getAbstract());
            classStaticCheckBox.setSelected(classData.getStatic());
            
            for(int i=0; i<classData.getClassAtributes().size(); i++) {
                Object wiersz[] = new Object[5];
                wiersz[0] = classData.getNazwa(classData.getClassAtributes().get(i));
                wiersz[1] = classData.getTyp(classData.getClassAtributes().get(i));
                wiersz[2] = classData.getWidocznosc(classData.getClassAtributes().get(i));
                wiersz[3] = classData.getLiczebnosc(classData.getClassAtributes().get(i));
                wiersz[4] = classData.getPoczatkowa(classData.getClassAtributes().get(i));
                
                classContainerModel.addRow(wiersz);
            }
            
            for(int i=0; i<classData.getClassMethods().size(); i++) {
                int lp = classMethodsContainer.getRowCount() + 1;
                
                Object wiersz[] = new Object[4];
                wiersz[0] = classData.getNazwa(classData.getClassMethods().get(i));
                wiersz[1] = classData.getTyp(classData.getClassMethods().get(i));
                wiersz[2] = classData.getWidocznosc(classData.getClassMethods().get(i));
                wiersz[3] = classData.getPolimorfizm(classData.getClassMethods().get(i));
                
                classMethodsContainerModel.addRow(wiersz);
                
                Object columnNames[] = {"Nazwa parametru", "Typ parametru", "Typ przekazywania"};
                
                Object trybPrzekazywania[] = {"* - wskaźnik", "& - referencja", "wartość"};
                JComboBox typyZwracane = new JComboBox(atributesTypes.toArray());
                JComboBox trybPrzekazywaniaJCB   = new JComboBox(trybPrzekazywania);     
                
                MethodsParameters parameters = new MethodsParameters();
                parameters.modelParametrow = new DefaultTableModel(columnNames, 0);                
                parameters.parametry = new JTable(parameters.modelParametrow);
                parameters.panel = new JScrollPane(parameters.parametry);
                parameters.parametry.getTableHeader().setReorderingAllowed(false);
                parameters.panel.setPreferredSize(new Dimension(600, 200));
                parameters.parametry.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(typyZwracane));
                parameters.parametry.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(trybPrzekazywaniaJCB));
                typyZwracane.setEditable(true);
                //trybPrzekazywaniaJCB.setEditable(true);
                metodParameters2.put(lp, parameters);
                
                for(int j=0; j<classData.getClassMethods().get(i).getParametry().size(); j++) {
                    Object parametr[] = new Object[3];
                    parametr[0] = classData.getClassMethods().get(i).getParametry().get(j).getNazwa();
                    parametr[1] = classData.getClassMethods().get(i).getParametry().get(j).getTyp();
                    parametr[2] = classData.getClassMethods().get(i).getParametry().get(j).getPrzekazywanie();
                    
                    parameters.modelParametrow.addRow(parametr);
                }

                parametersCardLayoutPanel.add(metodParameters2.get(lp).panel, String.valueOf(lp));            
                
                CardLayout cl = (CardLayout)parametersCardLayoutPanel.getLayout();
                cl.show(parametersCardLayoutPanel, String.valueOf(lp));
                //System.out.println("Dodałej JTextArea nr: " + lp);
                
            }
        }
        
        this.setVisible(true);        
    }
}