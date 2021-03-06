

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
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.DimensionUIResource;
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
    JTabbedPane zakladki;
    JPanel panel;
    JPanel fieldsPanel;
    DefaultTableModel tableModel;
    JTable table;
    JTextField tableName;
    JTextArea tableComment;
    JComboBox tableColor;
    JComboBox tableVisible;
    Object [] typesTab;
    DBDiagram diagramP;
    Table tableData;

public void setTable(Table value)    
{
    this.tableData=value;
}
    
public tablePropeties(DBDiagram value)
    {
    this.diagramP=value;
    setModalityType(ModalityType.APPLICATION_MODAL);    
    setTitle("Tworzenie nowej tabeli");
    setSize(640,740);
    content=getContentPane();
    panel=new JPanel();
    panel.setLayout(new FlowLayout());
    fieldsPanel=new JPanel();
    zakladki=new JTabbedPane();
    zakladki.add("Tabela",panel);
    zakladki.add("Kolumny",fieldsPanel);
    panel.setSize(600, 700);
    }

public void showWindow()
{

    JLabel tableNameLabel=new JLabel("Nazwa tabeli");
    tableName=new JTextField();
    tableName.setPreferredSize(new Dimension(600,30));
    JLabel tableCommentLabel=new JLabel("Dokumentacja");
    tableComment=new JTextArea();
    tableComment.setPreferredSize(new Dimension(600,300));
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
    tableColor.setPreferredSize(new Dimension(600,30));
    //tableColor.setSelectedItem(Color.black);
    visibleDictionary=new HashMap<String, Boolean>();
    visibleDictionary.put("tak",true);
    visibleDictionary.put("nie",false);

    JLabel tableVisibleLabel=new JLabel("Pokaż element");
    tableVisible=new JComboBox(visibleDictionary.keySet().toArray());
    tableVisible.setPreferredSize(new Dimension(600,30));
    
    JButton OK=new JButton("OK");
   

tableModel=new DefaultTableModel();
tableModel.addColumn("Nazwa pola");
tableModel.addColumn("Typ pola");
tableModel.addColumn("NOT NULL");
tableModel.addColumn("UNIQUE");
tableModel.addColumn("PRIMARY KEY");
    if(tableData!=null) 
            {
                tableName.setText(tableData.toString());
                tableComment.setText(tableData.getComment());
                for(int i=0;i<tableData.fields.size();i++)
                   tableModel.addRow(new Object[]{tableData.fields.get(i).getName(),tableData.fields.get(i).getType(),tableData.fields.get(i).getNotNull(),tableData.fields.get(i).getUnique(),tableData.fields.get(i).getPrimaryKey()});
            }
table = new JTable(tableModel);
typesTab=new Object[] {"CHAR","VARCHAR","TEXT","INT","FLOAT","TIMESTAMP","TINYINT","SMALLINT","MEIUMINT","INT","BIGINT"};
JComboBox typesComboBox=new JComboBox(typesTab);
typesComboBox.setEditable(true);
typesComboBox.setPreferredSize(new Dimension(90,20));
JComboBox notNullComboBox=new JComboBox(new Object[] {"tak","nie"});
JComboBox uniqueComboBox=new JComboBox(new Object[] {"tak","nie"});
JComboBox PrimaryKeyComboBox=new JComboBox(new Object[] {"tak","nie"});
table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(typesComboBox));
table.getColumnModel().getColumn(0).setMinWidth(270);
table.getColumnModel().getColumn(1).setMinWidth(90);
table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(notNullComboBox));
table.getColumnModel().getColumn(2).setMinWidth(80);
table.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(uniqueComboBox));
table.getColumnModel().getColumn(3).setMinWidth(80);
table.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(PrimaryKeyComboBox));
table.getColumnModel().getColumn(4).setMinWidth(80);
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

    OK.setPreferredSize(new Dimension(100,20));
    OK.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                 Boolean isValid=true;
                System.out.println(table.getCellEditor(0, 3));
                Vector<String> errorMessage=new Vector<String>();//String errorMessage=new String();
                String tempName="",tempType="", tempNotNull="", tempUnique="", tempPK="";
                if(tableName.getText().isEmpty())
                {
                    isValid=false;
                    errorMessage.add("Wprowadź nazwę tabeli.\n");
                }

                    Table tempTable=new Table(tableName.getText(),colorsDictionary.get(tableColor.getSelectedItem()));
                    tempTable.setComment(tableComment.getText());

                    for(int i=0;i<tableModel.getRowCount();i++)
                    {
                        tempName=tempType="";
                        try
                        {
                        tempName=tableModel.getValueAt(i, 0).toString();
                        tempType=tableModel.getValueAt(i, 1).toString();
                        tempNotNull=tableModel.getValueAt(i, 2).toString();
                        tempUnique=tableModel.getValueAt(i, 3).toString();
                        tempPK=tableModel.getValueAt(i, 4).toString();
                        }
                        catch(Exception ex) { }
                                                    System.out.println("Wiersz "+i+", nazwa: "+tempName+", wartosc: "+tempType);
                        if(!tempName.isEmpty() && !tempType.isEmpty())
                            tempTable.fields.add(new Field(tempName,tempType,tempNotNull,tempUnique,tempPK));
                        else
                        {
                        if(tempName.isEmpty() && tempType.isEmpty()) continue;
                        if (tempName.isEmpty())
                        {
                            errorMessage.add("Podaj nazwę kolumny w wierszu nr. "+(i+1)+".\n");
                            isValid=false;
                        }
                        if (tempType.isEmpty())
                        {
                            errorMessage.add("Podaj typ kolumny w wierszu nr. "+(i+1)+".\n");
                            isValid=false;
                        }
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
    Integer vMargin=600,hMargin=20;
    Integer vMargin2=600,hMargin2=5;
    panel.add(Box.createRigidArea(new Dimension(600,15)));
    panel.add(tableNameLabel);
    panel.add(Box.createRigidArea(new Dimension(vMargin2,hMargin2)));
    panel.add(tableName);
    panel.add(Box.createRigidArea(new Dimension(vMargin,hMargin)));
    panel.add(tableCommentLabel);
    panel.add(Box.createRigidArea(new Dimension(vMargin2,hMargin2)));
    panel.add(tableComment);
    panel.add(Box.createRigidArea(new Dimension(vMargin,hMargin)));
    panel.add(tableColorLabel);
    panel.add(Box.createRigidArea(new Dimension(vMargin2,hMargin2)));
    panel.add(tableColor);
    panel.add(Box.createRigidArea(new Dimension(600,100)));

    JScrollPane tableContainer=new JScrollPane(table);
    tableContainer.setPreferredSize(new Dimension(600,600));
    fieldsPanel.add(tableContainer);
    //panel.add(tableVisibleLabel);
    //panel.add(tableVisible);
    final JPanel buttonsPanel=new JPanel();
    buttonsPanel.setLayout(new FlowLayout());
    JPanel buttonsPanel2=new JPanel();
    buttonsPanel.setLayout(new FlowLayout());
    
    buttonsPanel2.add(addRow);
    buttonsPanel2.add(Box.createRigidArea(new Dimension(180,20)));
    buttonsPanel2.add(deleteRow);
    
    buttonsPanel.add(OK);
    buttonsPanel.add(Box.createRigidArea(new Dimension(180,20)));
    buttonsPanel.add(Cancel);
    panel.add(buttonsPanel);
    fieldsPanel.add(buttonsPanel2);
    zakladki.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                int selectedTab;
                selectedTab=zakladki.getSelectedIndex();
                if(selectedTab==0) panel.add(buttonsPanel);
                else fieldsPanel.add(buttonsPanel);
            }
        });

    content.add(zakladki);
    //content.add(buttonsPanel);
    setVisible(true);
    }
}
