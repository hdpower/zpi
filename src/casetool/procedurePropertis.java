/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package casetool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ramyi
 */
public class procedurePropertis extends JDialog{
    private Container content;
    private JPanel MainPanel;
    private JPanel ParamPanel;
    private JTable ParamTable;
   private DBDiagram dbdiagram;
    private DefaultTableModel tablemodel;
    private JScrollPane scrolpane;
    private JComboBox typesList;
    private JComboBox InOutList;
    private String[] typesTab={"INT","DOUBLE","VARCHAR","VARCHAR2","CHAR","DATE","TIMESTAMP"};
    private String[] InOutTab={"In","Out","In Out"};
    private Tab t;
    private JTabbedPane tabs;
    private int licznik=0;
    String readParams="";  

    public procedurePropertis(DBDiagram value) {
        
        content = getContentPane();
        setSize(500,650);
        setTitle("Nowa Procedura");
        setVisible(true);
        dbdiagram = value;
        typesList = new JComboBox(typesTab);
        InOutList = new JComboBox(InOutTab); 
        t=new Tab();
        tabs = t.addTabs("Procedura","Parametry");
       
    }
    
    public void ShowPanel()
    {
        MainPanel = new JPanel(new GridBagLayout());
        final   JTextField proc_name = new JTextField();
        
        final JLabel paramLabel = new JLabel("( )");
        
        final   JTextArea proc_body = new JTextArea();
                         
                JScrollPane textScroll_begin = new JScrollPane();
                            textScroll_begin.setViewportView(proc_body);  
                            textScroll_begin.setPreferredSize(new Dimension(460,200));
        
        final   JTextArea declare_body = new JTextArea(); 
       
                JScrollPane textScroll_declare = new JScrollPane();
                            textScroll_declare.setViewportView(declare_body);
                            textScroll_declare.setPreferredSize(new Dimension(460,100));
        
                 JButton addProc = new JButton("Dodaj procedure");
                         addProc.setSize(100,30);
                         addProc.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if(!proc_name.getText().isEmpty())
                {
                    dbdiagram.addChildToNode(proc_name.getText(), 2);
                    setVisible(false);
                }
                else
                {
                    JOptionPane.showMessageDialog(content, "Procedura musi mieć nazwę!");
                }
            }
        });
                            
        GridBagConstraints c = new GridBagConstraints();                        //do rozmieszczania w gridbag potrzebne
        c.fill = GridBagConstraints.HORIZONTAL;
        
        MainPanel.setSize(new Dimension(500, 600));
        
        c.gridx=0;c.gridy=0;c.insets = new Insets(10,0,0,0);
        MainPanel.add(new JLabel("Podaj nazwę procedury "),c);
        c.gridx=1;c.gridy=0;c.insets = new Insets(10,0,0,0);c.gridwidth=3;
        MainPanel.add(proc_name,c);
        c.gridx=0;c.gridy=1;
        MainPanel.add(new JLabel("Parametry: "),c);
        c.gridx=1;c.gridy=1;
        MainPanel.add(paramLabel,c);
        c.gridx=0;c.gridy=2;
        MainPanel.add(new JLabel("IS"),c);
        c.gridx=0;c.gridy=3;
        MainPanel.add(new JLabel("DECLARE"),c);
        c.gridx=0;c.gridy=4;c.gridwidth=4;
        MainPanel.add(textScroll_declare,c);
        c.gridx=0;c.gridy=5;
        MainPanel.add(new JLabel("BEGIN"),c);
        c.gridx=0;c.gridy=6;c.gridwidth=4;
        MainPanel.add(textScroll_begin,c);
        c.gridx=0;c.gridy=7;
        MainPanel.add(new JLabel("END"),c);
        c.gridx=0;c.gridy=8;
        MainPanel.add(addProc,c);
        t.addToMain(MainPanel);
        
//------------------------------------------------------------------------------drugi panel        
        ParamPanel = new JPanel(new BorderLayout(20, 5));
        
        Object columnNames[] = {"", "Nazwa","Jak","Typ","Rozmiar"};
        tablemodel=new DefaultTableModel(columnNames, 0)
                {
                  @Override
                  public boolean isCellEditable(int row, int column) 
                     {
                         return column!=0;
                     }
                };
        ParamTable = new JTable(tablemodel);
        
        scrolpane = new JScrollPane(ParamTable);
        scrolpane.setPreferredSize(new Dimension(300,300));
        ParamTable.getTableHeader().setReorderingAllowed(false);
        
        ParamTable.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(typesList));
        ParamTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(InOutList));
        //----------------------------------------------------------------------Table end
        
        final   JLabel errorLabel = new JLabel();
                       errorLabel.setOpaque(true);
                       errorLabel.setBackground(new Color(255, 0, 0,100));
                       errorLabel.setMaximumSize(new Dimension(300,100));
        
        JButton akcept = new JButton("Akceptuj");
        akcept.addActionListener(new ActionListener() {
           
            public void actionPerformed(ActionEvent e) {
                
                String errormsg="";
                for(int i=0;i<tablemodel.getRowCount();i++)
                {
                   try
                   {
                        ParamTable.editCellAt(-1, -1);
                        readParams += tablemodel.getValueAt(i, 1)+" "+tablemodel.getValueAt(i, 2)+" "+tablemodel.getValueAt(i, 3)+"("+Integer.parseInt(tablemodel.getValueAt(i, 4).toString()) + ")" +",";
                        
                   }
                           catch( NullPointerException nullex)
                           {
                               errorLabel.setText(errorLabel.getText()+"\n"+"Komórka: "+tablemodel.getValueAt(i, 0)+" jest pusta");
                           }
                           catch( NumberFormatException numex)
                           {
                               //errormsg+=" Rozmiar w wierszu "+(i+1) + " musi być liczbą!\n";
                               JOptionPane.showMessageDialog(ParamPanel, " Rozmiar w wierszu "+(i+1) + " musi być liczbą!\n");
                           }                   
                }
                try
                {
                    readParams="( "+readParams.substring( 0, readParams.length()-1)+" )";
                    paramLabel.setText("( Dodałeś: "+ tablemodel.getRowCount() + " )");
                }
                catch(StringIndexOutOfBoundsException strex)
                {
                    
                }
                if(!"".equals(errormsg))
                {
                    JOptionPane.showMessageDialog(ParamPanel,errormsg);
                    errormsg="";
                }
                else
                {
                    tabs.setSelectedIndex(0) ;
                }
            }
        });
        
        JButton addrow = new JButton("Dodaj parametr");
        addrow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tablemodel.addRow(new Object[5]);
                tablemodel.setValueAt(licznik+1, licznik, 0);
                licznik++;
                
            }
        });
        
        JButton removerow = new JButton("Usuń parametr");
        removerow.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
               int tmp =  ParamTable.getSelectedRow();
                tablemodel.removeRow( ParamTable.getSelectedRow());
                licznik--;
                for(int i=tmp;i<licznik;i++)                                    //for to change numbers describing row 
                    tablemodel.setValueAt(i+1, i, 0);
            }
        });
        
        JPanel northP = new JPanel();
               northP.add(errorLabel);
        JPanel centerP = new JPanel();
               centerP.add(scrolpane);
        JPanel eastP = new JPanel();
               eastP.setLayout(new BoxLayout(eastP, BoxLayout.Y_AXIS));
               eastP.add(addrow);
               eastP.add(removerow);
        JPanel southP = new JPanel();
               southP.add(akcept); 
        //----------------------------------------------------------------------additional panels for layout end

        t.addToSecendary(centerP, BorderLayout.CENTER);
        t.addToSecendary(southP,BorderLayout.SOUTH);
        t.addToSecendary(eastP,BorderLayout.EAST);
        t.addToSecendary(northP,BorderLayout.NORTH) ;
        
        content.add(tabs);
    }
            
}
