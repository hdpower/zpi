/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package casetool;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
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
                                                                                //MainPanel section
        JLabel fun_name_lab = new JLabel("Podaj nazwę funkcji");
        JTextField fun_name = new JTextField();
        fun_name.setPreferredSize(new Dimension(100, 28));
        final   JButton addparameter=new JButton("+");
        final   JLabel paramLabel = new JLabel();
        addparameter.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                tabs.addTab("Parametry", ParamPanel);
            }
        });
                
                                                                                
        MainPanel.add(fun_name_lab);
        MainPanel.add(fun_name);
        MainPanel.add(new JLabel("Parametry: ("));
        MainPanel.add(addparameter);
        MainPanel.add(paramLabel);
        MainPanel.add(new JLabel(")"));
        MainPanel.add(new JLabel(" RETURN "));
        MainPanel.add(typesList);
                                                                                //MainPanel section end
        
                                                                                //ParamPanel section
        ParamPanel = new JPanel(new FlowLayout());
        Object columnNames[] = {"Nazwa","Jak","Typ","Rozmiar"};
        tablemodel=new DefaultTableModel(columnNames, 1);
        ParamTable = new JTable(tablemodel);
        
        scrolpane = new JScrollPane(ParamTable);
        scrolpane.setPreferredSize(new Dimension(300,300));
        ParamTable.getTableHeader().setReorderingAllowed(false);
        
        ParamTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(typesList));
        ParamTable.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(InOutList));
       
        
        final   JLabel errorLabel = new JLabel();
                       errorLabel.setBackground(Color.red);
        
        JButton akcept = new JButton("Akceptuj");
        akcept.setPreferredSize(new Dimension(100, 20));
        akcept.addActionListener(new ActionListener() {
           
            public void actionPerformed(ActionEvent e) {
                String readParams="";
                
                for(int i=0;i<tablemodel.getRowCount();i++)
                {
                  // try
                  // {
                        ParamTable.editCellAt(-1, -1);
                        readParams += tablemodel.getValueAt(i, 0)+" "+tablemodel.getValueAt(i, 1)+" "+tablemodel.getValueAt(i, 2)+"("+Integer.parseInt(tablemodel.getValueAt(i, 3).toString()) + ")" +",";
                        //System.out.println(tablemodel.getValueAt(0, 3));
                        
                 //  }
                  //         catch( NullPointerException nex)
                   //        {
                            //   errorLabel.setText(errorLabel.getText()+"\n");
                   //        }
                   
                }
                addparameter.setVisible(false);
                //MainPanel.remove(addparameter);
                paramLabel.setText(readParams.substring(0, readParams.length()-1));
            }
        });
        JButton addrow = new JButton("Dodaj parametr");
        addrow.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                tablemodel.addRow(new Object[4]);
            }
        });
        ParamPanel.add(scrolpane); 
        ParamPanel.add(akcept);
        ParamPanel.add(addrow);
        ParamPanel.add(errorLabel) ;
        
                                                                                 //add to JTabbedPane tabs
        tabs.addTab("Funkcja", MainPanel);
        content.add(tabs);
    }
}
