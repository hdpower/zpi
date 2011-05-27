/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package casetool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
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
public class functionProperties extends JDialog{
    
   private Container content;
   private JPanel MainPanel;
   private JPanel ParamPanel;
   private JTable ParamTable;
   private DefaultTableModel tablemodel;
   private JScrollPane scrolpane;
   private JComboBox typesList;
   private JComboBox InOutList;
   private String[] typesTab={"INT","DOUBLE","VARCHAR","VARCHAR2","CHAR","DATE","TIMESTAMP"};
   private String[] InOutTab={"In","Out","In Out"};
   private JTabbedPane tabs;
   private int licznik=0;
   
   
    public functionProperties()
    {
        content = getContentPane();
        setTitle("Nowa Funkcja");
        setSize(500, 600);
        setVisible(true);
        MainPanel = new JPanel();
        typesList = new JComboBox(typesTab);
        InOutList = new JComboBox(InOutTab);
        tabs = new JTabbedPane();
    }
    public void ShowPanel()
    {                                                   
                                                                                //MainPanel section obj declaration + addition to panel
        JLabel fun_name_lab = new JLabel("Podaj nazwę funkcji");
        final   JTextField fun_name = new JTextField();
        fun_name.setPreferredSize(new Dimension(100, 28));
        final   JLabel paramLabel = new JLabel("( )");
        final   JTextArea fun_body = new JTextArea();
                          fun_body.setPreferredSize(new Dimension(480,300));
                          
                  
        JButton finish = new JButton("Dodaj funkcję");  
        finish.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
               if(fun_name.getText().isEmpty()==false)
               {
                   System.out.println("CREATE OR REPLACE FUNCTION "+ fun_name.getText() + paramLabel.getText()+
                                   "\n RETURN "+typesList.getSelectedItem() +
                                   "\n\u0009 BEGIN" +
                                   "\n\u0009\u0009 "+fun_body.getText()+
                                   "\n\u0009 END "+ fun_name.getText() +";");
               }
               else
               {
                   JOptionPane.showMessageDialog(MainPanel, "Funkcja musi mieć nazwę!");
               }
            }
        });
                                                                                
        MainPanel.add(fun_name_lab);
        MainPanel.add(fun_name);
        MainPanel.add(new JLabel("Parametry: "));
        MainPanel.add(paramLabel);
        MainPanel.add(new JLabel(" RETURN "));
        MainPanel.add(typesList);
        MainPanel.add(new JLabel("Begin"));
        MainPanel.add(fun_body);
        MainPanel.add(new JLabel("END "));
        MainPanel.add(finish);
        
                                                                                //MainPanel section end
        
                                                                                //ParamPanel section
        ParamPanel = new JPanel(new BorderLayout(20, 5));
        Object columnNames[] = {"", "Nazwa","Jak","Typ","Rozmiar"};
        tablemodel=new DefaultTableModel(columnNames, 0);
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
        //----------------------------------------------------------------------error label end
        JButton akcept = new JButton("Akceptuj");
        akcept.addActionListener(new ActionListener() {
           
            public void actionPerformed(ActionEvent e) {
                String readParams="";
                
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
                               errorLabel.setText(errorLabel.getText()+ " Rozmiar w wierszu "+(i+1) + " musi być liczbą!\n");
                           }                   
                }
                try
                {
                    paramLabel.setText("( "+readParams.substring( 0, readParams.length()-1)+" )");
                }
                catch(StringIndexOutOfBoundsException strex)
                {
                    
                }
                tabs.setSelectedIndex(0) ;
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
                for(int i=tmp;i<licznik-1;i++)                                  //for to change numbers describing row 
                    tablemodel.setValueAt(i+1, i, 0);
            }
        });
        //----------------------------------------------------------------------JButtons end
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
        ParamPanel.add(centerP,BorderLayout.CENTER); 
        ParamPanel.add(southP,BorderLayout.SOUTH);
        ParamPanel.add(eastP,BorderLayout.EAST);
        ParamPanel.add(northP,BorderLayout.NORTH) ;
        
                                                                                 //add to JTabbedPane tabs
        tabs.addTab("Funkcja", MainPanel);
        tabs.addTab("Parametry", ParamPanel);
        content.add(tabs);
    }
}
