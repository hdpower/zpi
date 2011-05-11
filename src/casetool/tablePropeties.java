

package casetool;

import java.awt.Checkbox;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.plaf.basic.BasicTreeUI.SelectionModelPropertyChangeHandler;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import sun.misc.FpUtils;

/**
 *
 * @author Patryk
 */
public class tablePropeties extends JDialog
{
    HashMap<String,Color> colorsDictionary;
    HashMap<String,Boolean> visibleDictionary;
    Container content;
    JPanel panel;
    DefaultTableModel tableModel;
    JTable table;
    JTextField tableName;
    JComboBox tableColor;
    JComboBox tableVisible;
    Object [] typesTab;
    DBDiagram diagramP;
    Table tableData;

public void setTable(Table value)    
{
    this.tableData=value;
    System.out.println("Udalo sie");
}
    
public tablePropeties(DBDiagram value)
    {
    this.diagramP=value;
    setModalityType(ModalityType.APPLICATION_MODAL);    
    setTitle("Tworzenie nowej tabeli");
    setSize(640,740);
    content=getContentPane();
    panel=new JPanel();
    panel.setSize(600, 700);
    }

public void showWindow()
{

    JLabel tableNameLabel=new JLabel("Nazwa tabeli");
    tableName=new JTextField();
    tableName.setPreferredSize(new Dimension(170,20));
    JLabel tableColorLabel=new JLabel("Kolor elementu na diagramie");
    colorsDictionary=new HashMap<String, Color>();
    colorsDictionary.put("Czarny", Color.black);
    colorsDictionary.put("Zielony",Color.green);
    colorsDictionary.put("Niebieski",Color.blue);
    colorsDictionary.put("Czerwony",Color.red);
    colorsDictionary.put("Szary",Color.gray);
    colorsDictionary.put("Żółty",Color.yellow);

    //Color [] colors={Color.black, Color.GREEN, Color.BLUE, Color.RED, Color.gray, Color.yellow, Color.DARK_GRAY, Color.LIGHT_GRAY};
    tableColor=new JComboBox(colorsDictionary.keySet().toArray());
    tableColor.setPreferredSize(new Dimension(170,20));
    //tableColor.setSelectedItem(Color.black);
    visibleDictionary=new HashMap<String, Boolean>();
    visibleDictionary.put("tak",true);
    visibleDictionary.put("nie",false);

    JLabel tableVisibleLabel=new JLabel("Pokaż element");
    tableVisible=new JComboBox(visibleDictionary.keySet().toArray());
    tableVisible.setPreferredSize(new Dimension(170,20));
    
    JButton OK=new JButton("OK");
   

tableModel=new DefaultTableModel();
tableModel.addColumn("name");
tableModel.addColumn("type");
tableModel.addColumn("not_null");
tableModel.addColumn("unique");
tableModel.addColumn("pk");
tableModel.addRow(new Object[]{"Nazwa pola","Typ pola","NOT NULL","UNIQUE","PK"});
    if(tableData!=null) 
            {
                tableName.setText(tableData.toString());
                for(int i=0;i<tableData.fields.size();i++)
                    tableModel.addRow(new Object[]{tableData.fields.get(i).getName(),tableData.fields.get(i).getType(),null,null,null});
            }
table = new JTable(tableModel);
typesTab=new Object[] {"CHAR","VARCHAR","TEXT","INT","FLOAT","TIMESTAMP","TINYINT","SMALLINT","MEIUMINT","INT","BIGINT"};
JComboBox typesComboBox=new JComboBox(typesTab);
typesComboBox.setEditable(true);
typesComboBox.setPreferredSize(new Dimension(90,20));
table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(typesComboBox));
table.getColumnModel().getColumn(0).setMinWidth(270);
table.getColumnModel().getColumn(1).setMinWidth(90);
class checkboxRenderer extends JPanel implements TableCellRenderer
{
    private JPanel content=new JPanel();
    public JCheckBox value=new JCheckBox();
    public checkboxRenderer(Boolean newValue)
    {
        content.add(value);
        value.setSelected(newValue);
    }

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                return this.value;
            }
    
}

class checkboxEditor implements TableCellEditor
{
            JCheckBox tempValue=tempValue=new JCheckBox();
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                
                return tempValue;
            }

            public Object getCellEditorValue() {
                return 2;
            }

            public boolean isCellEditable(EventObject anEvent) {
                return true;
            }

            public boolean shouldSelectCell(EventObject anEvent) {
                return true;
            }

            public boolean stopCellEditing() {
                return true;
            }

            public void cancelCellEditing() {
            }

            public void addCellEditorListener(CellEditorListener l) {
            }

            public void removeCellEditorListener(CellEditorListener l) {
            }
    
}
table.getColumnModel().getColumn(2).setMinWidth(80);
//table.getColumnModel().getColumn(2).setCellRenderer(new checkboxRenderer(true));
//table.getColumnModel().getColumn(2).setCellEditor(new checkboxEditor());
table.getColumnModel().getColumn(3).setMinWidth(80);
JButton addRow=new JButton("Dodaj pole");
addRow.setPreferredSize(new Dimension(100,20));
JButton deleteRow=new JButton("Usuń pole");
deleteRow.setPreferredSize(new Dimension(100,20));
addRow.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                tableModel.addRow(new Object[]{null,null,null,null,null});
            }   
        });
deleteRow.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int selectedRows[]=table.getSelectedRows();
 
                for(int i=selectedRows.length-1;i>=0;i--)
                    if(selectedRows[i]>0) tableModel.removeRow(selectedRows[i]);
            }
        });

table.setPreferredSize(new Dimension(600,600));
    
    OK.setPreferredSize(new Dimension(100,20));
    OK.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Boolean isValid=true;
                System.out.println(table.getCellEditor(0, 3));
                Vector<String> errorMessage=new Vector<String>();//String errorMessage=new String();
                String tempName,tempType;
                if(tableName.getText().isEmpty())
                {
                    isValid=false;
                    errorMessage.add("Wprowadź nazwę tabeli");
                }

                    Table tempTable;
                    if(tableData==null)tempTable=new Table(tableName.getText(),colorsDictionary.get(tableColor.getSelectedItem()));
                    else
                    {
                        tempTable = tableData;
                        tempTable.setName(tableName.getText());
                        tempTable.setColor(colorsDictionary.get(tableColor.getSelectedItem()));
                        //tempTable.setVisible(visibleDictionary.get(tableVisible.getSelectedItem()));
                    }
                    for(int i=1;i<tableModel.getRowCount();i++)
                    {
                        tempName=tableModel.getValueAt(i, 0).toString();
                        tempType=tableModel.getValueAt(i, 1).toString();
                        if(!tempName.isEmpty() && !tempType.isEmpty())
                            tempTable.fields.add(new Field(tempName,tempType,false,null,null));
                        else if(tempName.isEmpty() || tempType.isEmpty())
                        {
                            errorMessage.add("Podaj poprawnę nazwę i typ pola w wierszu nr. "+i);
                            isValid=false;
                        }
                            
                    }
                if(isValid)
                {
                    if(tableData==null)diagramP.tables.add(tempTable);
                    else diagramP.tables.set(diagramP.tables.indexOf(tableData),tempTable);
                    diagramP.refreshTables();
                    setVisible(false);
                }
                else
                    JOptionPane.showMessageDialog(content, errorMessage, "Błąd wprowdzania danych", JOptionPane.WARNING_MESSAGE);
            }
        });
    JButton Cancel=new JButton("Anuluj");
    Cancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    Cancel.setPreferredSize(new Dimension(100,20));
    panel.add(tableNameLabel);
    panel.add(tableName);
    panel.add(table);
    panel.add(tableColorLabel);
    panel.add(tableColor);
    //panel.add(tableVisibleLabel);
    //panel.add(tableVisible);
    JPanel buttonsPanel=new JPanel();
    buttonsPanel.setLayout(new FlowLayout());
    
    buttonsPanel.add(addRow);
    buttonsPanel.add(deleteRow);
    buttonsPanel.add(Box.createRigidArea(new Dimension(180,20)));
    buttonsPanel.add(OK);
    buttonsPanel.add(Cancel);
    panel.add(buttonsPanel);
    content.add(panel);
    setVisible(true);
    }
}
