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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import sun.swing.table.DefaultTableCellHeaderRenderer;

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
    private JPanel parametersCardLayoutPanel;
    
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
        parametersCardLayoutPanel.add(new JTextArea("Parametry metod..."), "0");
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
    }
    
    private void initializeClassContainer() {
        
        Object columnNames[] = {"Nazwa atrybutu", "Typ", "Widoczność", "Liczebność", "Wartość początkowa"};
        Object atributesVisibility[] = {"private", "protected", "public"};
        
        classContainerModel = new DefaultTableModel(columnNames, 0);
        classContainer = new JTable(classContainerModel);        
        scrollPaneClassContainer = new JScrollPane(classContainer);
        scrollPaneClassContainer.setPreferredSize(new Dimension(600, 600));
        classContainer.getTableHeader().setReorderingAllowed(false);
        
        JComboBox classAtributesVisibility = new JComboBox(atributesVisibility);
        
        classContainer.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(classAtributesTypesComboBox));
        classContainer.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(classAtributesVisibility));
    }
    
    private void initializeClassMethodsContainter() {
        Object columnNames[] = {"Nazwa metody", "Typ zwracany", "Widoczność", "Polimorficzna"};
        Object atributesVisibility[] = {"private", "protected", "public"};
        
        classMethodsContainerModel = new DefaultTableModel(columnNames, 0);
        classMethodsContainer = new JTable(classMethodsContainerModel);
        scrollPanelMethodsContainer = new JScrollPane(classMethodsContainer);
        scrollPanelMethodsContainer.setPreferredSize(new Dimension(600, 450));
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
        classAtributesTypesComboBox.setEditable(true);
        
        classMethodsTypesComboBox = new JComboBox(atributesTypes.toArray());
        classMethodsTypesComboBox.setEditable(true);
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
                Object column[] = {"Atrybut " + lp, "int", "private", "1..*", "null"};
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
        
        JButton addRowContainerModelMethods = new JButton("Dodaj wiersz");
        addRowContainerModelMethods.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int lp = classMethodsContainer.getRowCount() + 1;
                Object column[] = {"Metoda " + lp, "int", "private", true};
                classMethodsContainerModel.addRow(column);
                
                JTextArea nowa = new JTextArea("Parametr 1 [Metody " + lp + "]");
                metodParameters.put(lp, nowa);
                //System.out.print(classMethodsContainerModel.getValueAt(0, 3));
                parametersCardLayoutPanel.add(metodParameters.get(lp), String.valueOf(lp));            
                
                CardLayout cl = (CardLayout)parametersCardLayoutPanel.getLayout();
                cl.show(parametersCardLayoutPanel, String.valueOf(lp));
                //System.out.println("Dodałej JTextArea nr: " + lp);
            }
        });
        
        JButton deleteRowContainerModelMethods = new JButton("Usuń wiersz");
        deleteRowContainerModelMethods.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int selectedRows = classMethodsContainer.getRowCount()-1;
                
                if(selectedRows >= 0) {
                    classMethodsContainerModel.removeRow(selectedRows);
                    int lp = selectedRows+1;
                    CardLayout cl = (CardLayout)parametersCardLayoutPanel.getLayout();
                    cl.removeLayoutComponent(metodParameters.get(lp));
                    cl.show(parametersCardLayoutPanel, String.valueOf(lp-1));
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
        addRowContainerModelAtributes.setPreferredSize(new Dimension(150, 30));
        deleteRowContainerModelAtributes.setPreferredSize(new Dimension(150, 30));
        addAtributesType.setPreferredSize(new Dimension(150, 30));
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
        addRowContainerModelMethods.setPreferredSize(new Dimension(150, 30));
        deleteRowContainerModelMethods.setPreferredSize(new Dimension(150, 30));
        addMethodsTypes.setPreferredSize(new Dimension(150, 30));
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
        
        buttonAnuluj.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        content.add(mainButtonPanel);
        this.setVisible(true);
    }
}