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
import java.util.HashMap;
import javax.net.ssl.SSLEngineResult.Status;
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
    
   public Container content;
   public DBDiagram dbdiagram;
   public JPanel MainPanel;
   public JPanel ParamPanel;
   public JTable ParamTable;
   public DefaultTableModel tablemodel;
   public JScrollPane scrolpane;
   public JComboBox typesList;
   public JComboBox InOutList;
   private String[] typesTab={"INT","DOUBLE","VARCHAR","VARCHAR2","CHAR","DATE","TIMESTAMP"};
   private String[] InOutTab={"In","Out","In Out"};
   public JTabbedPane tabs;
   public HashMap<String,String> rdyFun;
   public Tab t;
   /**
    * Zmienna wykorzystywana do numerowania wierszy w tabelce parametrów
    */
   public int licznik=0;
   /**
    * Zmienna, do której będzie wpisana cała gotowa funkcja wykorzystywana do dodania value w hash mapie
    */
   public String readParams="";                                                      
   /**
    * pomocnicza zmienna do określenia czy poszło ok =0,nie=1;
    */
   public int status=0;
   
    public functionProperties(DBDiagram value)
    {
        content = getContentPane();
        setTitle("Nowa Funkcja");
        setSize(500, 650);
        setVisible(true);
        dbdiagram = value;
        initFun();
    }
    
    private void initFun()
    {
        typesList = new JComboBox(typesTab);
        InOutList = new JComboBox(InOutTab);
        rdyFun = new HashMap<String, String>();
        t = new Tab();
        tabs = t.addTabs("Fukcja","Parametry");
    }
    
    public void showFun(String funName)
    {
        
        if(rdyFun.containsKey(funName))
        {
            System.out.println(rdyFun.get(funName));
            status=0;
        }
        else
        {
            //JOptionPane.showMessageDialog(content, "Coś poszło nie tak");
            status=1;
        }
    }
    public void ShowPanel()
    {                                                   
       MainPanel = new JPanel(new GridBagLayout());                             //MainPanel section obj declaration + addition to panel
                
                JLabel fun_name_lab = new JLabel("Podaj nazwę funkcji");
                
        final   JTextField fun_name = new JTextField();
                           fun_name.setPreferredSize(new Dimension(100, 28));           
                           
        final   JLabel paramLabel = new JLabel("( )");
        final   JTextArea fun_body = new JTextArea();
                         
                JScrollPane textScroll_begin = new JScrollPane();
                            textScroll_begin.setViewportView(fun_body);  
                            textScroll_begin.setPreferredSize(new Dimension(460,200));
        
       final    JTextArea declare_body = new JTextArea(); 
       
                JScrollPane textScroll_declare = new JScrollPane();
                            textScroll_declare.setViewportView(declare_body);
                            textScroll_declare.setPreferredSize(new Dimension(460,100));
                        
                JButton finish = new JButton("Dodaj funkcję");  
                finish.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
               if(fun_name.getText().isEmpty()==false)
               {
                   rdyFun.put(fun_name.getText(), "CREATE OR REPLACE FUNCTION "+ fun_name.getText() + readParams +
                                   "\n RETURN "+typesList.getSelectedItem() +
                                   "\n"+declare_body.getText()+
                                   "\n\u0009 BEGIN" +
                                   "\n\u0009\u0009 "+fun_body.getText().replace("\n", "\n\u0009\u0009")+
                                   "\n\u0009 END "+ fun_name.getText() +";");
                   System.out.println("CREATE OR REPLACE FUNCTION "+ fun_name.getText() + readParams +
                                   "\n RETURN "+typesList.getSelectedItem() +
                                   "\n"+declare_body.getText()+
                                   "\n\u0009 BEGIN" +
                                   "\n\u0009\u0009 "+fun_body.getText().replace("\n", "\n\u0009\u0009")+
                                   "\n\u0009 END "+ fun_name.getText() +";");
                   dbdiagram.addChildToNode(fun_name.getText(),1);
                   status=0;
                   setVisible(false);
               }
               else
               {
                   status=1;
                   //JOptionPane.showMessageDialog(MainPanel, "Funkcja musi mieć nazwę!");
               }
            }
        });
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        MainPanel.setSize(new Dimension(500, 600));
        c.gridx=0;c.gridy=0;c.insets = new Insets(10,0,0,0);
        MainPanel.add(fun_name_lab,c);
        c.gridx=1;c.gridy=0;c.insets = new Insets(10,10,0,0);
        MainPanel.add(fun_name,c);
        c.gridx=2;c.gridy=0;c.insets = new Insets(10,10,0,0);
        MainPanel.add(new JLabel("Parametry: "),c);
        c.gridx=3;c.gridy=0;c.insets = new Insets(10,0,0,0);
        MainPanel.add(paramLabel,c);
        c.gridx=0;c.gridy=1;
        MainPanel.add(new JLabel(" RETURN "),c);
        c.gridx=1;c.gridy=1;
        MainPanel.add(typesList,c);
        c.gridx=0;c.gridy=2;c.weightx=0.0;
        MainPanel.add(new JLabel("DECLARE"),c);
        c.gridx=0;c.gridy=3;c.gridwidth=4;
        MainPanel.add(textScroll_declare,c);c.gridx=0;c.gridy=4;c.weightx=0.0;
        MainPanel.add(new JLabel("Begin"),c);
        c.gridx=0;c.gridy=5;c.gridwidth=4;
        MainPanel.add(textScroll_begin,c);
        c.gridx=0;c.gridy=6;
        MainPanel.add(new JLabel("END "),c);
        c.gridx=0;c.gridy=7;   
        MainPanel.add(finish,c);   
        t.addToMain(MainPanel);
                                                                                //MainPanel section end
        
                                                                                //ParamPanel section
        //ParamPanel = new JPanel(new BorderLayout(20, 5));
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
        //----------------------------------------------------------------------error label end
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
                               errormsg+="\n"+"Komórka: "+tablemodel.getValueAt(i, 0)+" jest pusta ";
                               //errorLabel.setText(errorLabel.getText()+"\n"+"Komórka: "+tablemodel.getValueAt(i, 0)+" jest pusta ");
                           }
                           catch( NumberFormatException numex)
                           {
                               errormsg+=" Rozmiar w wierszu "+(i+1) + " musi być liczbą!\n";
                               //JOptionPane.showMessageDialog(ParamPanel, " Rozmiar w wierszu "+(i+1) + " musi być liczbą!\n");
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
                    JOptionPane.showMessageDialog(MainPanel,errormsg);
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
                
                if(ParamTable.getSelectedRow()!= -1)
                {
                int tmp =  ParamTable.getSelectedRow();
                tablemodel.removeRow( ParamTable.getSelectedRow());
                licznik--;
                for(int i=tmp;i<licznik;i++)                                    //for to change numbers describing row 
                    tablemodel.setValueAt(i+1, i, 0);
                }
                else
                    tablemodel.removeRow(licznik-1);licznik--;
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

        t.addToSecendary(centerP, BorderLayout.CENTER);
        t.addToSecendary(southP,BorderLayout.SOUTH);
        t.addToSecendary(eastP,BorderLayout.EAST);
        t.addToSecendary(northP,BorderLayout.NORTH) ;
                                                                                //add to JTabbedPane tabs
        //tabs.addTab("Funkcja", MainPanel);
        //tabs.addTab("Parametry", ParamPanel);
        
        content.add(tabs);
    }
}
