package casetool;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class DBDiagram extends Diagram
{
    public Vector<Element> tables=new Vector<Element>();
    DefaultMutableTreeNode tablesNode;
    DefaultMutableTreeNode functionsNode;
    
public DBDiagram getInstance()
{
    return this;
}
    
public DBDiagram()
    {
    this.typ="Database";
    elementsTree=new DefaultMutableTreeNode("Objekty");
    tablesNode=new DefaultMutableTreeNode("Tabele");

    DefaultMutableTreeNode viewsNode=new DefaultMutableTreeNode("Perspektywy");
    DefaultMutableTreeNode triggersNode=new DefaultMutableTreeNode("Wyzwalacze");
    functionsNode=new DefaultMutableTreeNode("Funkcje");
    DefaultMutableTreeNode proceduresNode=new DefaultMutableTreeNode("Procedury");
    DefaultMutableTreeNode usersNode=new DefaultMutableTreeNode("Użytkownicy");

    elementsTree.add(tablesNode);
    elementsTree.add(viewsNode);
    elementsTree.add(triggersNode);
    elementsTree.add(functionsNode);
    elementsTree.add(proceduresNode);
    elementsTree.add(usersNode);
    }

public DBDiagram(org.w3c.dom.Element XMLroot)
{
    this();
    NodeList tablesXML=XMLroot.getChildNodes();
    for(int i=0;i<tablesXML.getLength();i++)
        if(tablesXML.item(i) instanceof org.w3c.dom.Element)
        {
            addTable(tablesXML.item(i));
        }
    refreshTables();
}

public void addTable(Node XMLtable)
{
    NodeList tableAttributesXML=XMLtable.getChildNodes();
    String tempName="READ_ERROR";
    Color tempColor=Color.GREEN;
    int tempX=5, tempY=5, tempDisplayMode=1;
    NodeList fieldsTemp=null;
    Boolean tempIAL=true;
    for(int i=0;i<tableAttributesXML.getLength();i++)
        if(tableAttributesXML.item(i) instanceof org.w3c.dom.Element)
        {
            String propety=tableAttributesXML.item(i).getNodeName();
            String value=tableAttributesXML.item(i).getTextContent();
            if(propety.equals("name")) tempName=value;
            try
            {
            if(propety.equals("color")) tempColor=new Color(Integer.parseInt(value));
            if(propety.equals("x")) tempX=Integer.parseInt(value);
            if(propety.equals("y")) tempY=Integer.parseInt(value);
            if(propety.equals("displayMode")) tempDisplayMode=Integer.parseInt(value);
            if(propety.equals("isAutoLocated")) tempIAL=Boolean.valueOf(value);
            if(propety.equals("fields")) fieldsTemp=tableAttributesXML.item(i).getChildNodes();
            }
            catch(Exception ex) { }
        }
        Table tempTable=new Table(tempName, tempColor);
        if (fieldsTemp!=null) tempTable.addFields(fieldsTemp);
        tempTable.setX(tempX);
        tempTable.setY(tempY);
        tempTable.setDisplayMode(tempDisplayMode);
        tempTable.autoLocated=tempIAL;
        tables.add(tempTable);
}

public void setContextMenuOptions(DefaultMutableTreeNode selectedNode, JPopupMenu contextMenu)
    {
    contextMenu.removeAll();
    if(selectedNode.toString().equals("Tabele"))
    {
        JMenuItem addTable=new JMenuItem("Dodaj tabelę");
        ActionListener addTableClick=new ActionListener()
        {
                public void actionPerformed(ActionEvent e)
                {
                tablePropeties dialogWindow=new tablePropeties(getInstance());
                dialogWindow.showWindow();
                }
        };
        addTable.addActionListener(addTableClick);
        contextMenu.add(addTable);
    }
    else if(selectedNode.toString().equals("Perspektywy")) ;
    else if(selectedNode.toString().equals("Funkcje")) 
    {
        JMenuItem addFun = new JMenuItem("Dodaj funkcje");
        addFun.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    functionProperties fc = new functionProperties(getInstance());
                    fc.ShowPanel();
                }
            });        
        contextMenu.add(addFun);
    }
    else if(selectedNode.toString().equals("Perspektywy"))
    {
        JMenuItem addView = new JMenuItem("Dodaj Perspektywe");
        contextMenu.add(addView);
    }
    else if(selectedNode.toString().equals("Wyzwalacze")) 
    {
        JMenuItem addTrigger = new JMenuItem("Dodaj Wyzwalacz");
        contextMenu.add(addTrigger);
    }
    else if(selectedNode.toString().equals("Procedury")) 
    {
        JMenuItem addProcedure = new JMenuItem("Dodaj Procedure");
        addProcedure.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                     procedurePropertis pp = new procedurePropertis();
                     pp.ShowPanel();
                }
            });
        contextMenu.add(addProcedure);
    }
    else if(selectedNode.toString().equals("Użytkownicy")) 
    {
        JMenuItem addUser = new JMenuItem("Dodaj Użytkownika");
        contextMenu.add(addUser);
    }/*
    else
        {
            
        if(selectedNode.getParent().toString()=="Tabele") setContextMenuOptionsTables(selectedNode,contextMenu);
        }*/
    }

public void setContextMenuOptionsTables(DefaultMutableTreeNode selectedNode, JPopupMenu contextMenu)
{
    JMenuItem modifyTable=new JMenuItem("Modyfikuj tabelę");
    JMenuItem deleteTable=new JMenuItem("Usuń tabelę");
    contextMenu.add(modifyTable);
    contextMenu.add(deleteTable);
}

public void refreshTables()
{
    tablesNode.removeAllChildren();
    for(Element element : tables)
        tablesNode.add(new DefaultMutableTreeNode(element));
}

public void drawElements(Graphics g)
{
    for(int i=0;i<tables.size();i++)
    {
        tables.get(i).setPosition(tables,i,panel.getComponent(1).getWidth(), panel.getHeight());
        tables.get(i).draw(g);
    }
    //if(tables.size()>=2) drawConnections(g);
}

public void drawConnections(Graphics g)
{
    Boolean dec=false;
    Integer center,tempCenter;
    int k=0;
    Table one=(Table)tables.firstElement();
    Table two=(Table)tables.lastElement();
    Table temp;
    
    if(one.getX()>one.getY())
    {
        temp=one;
        one=two;
        two=temp;
    }
    if(one!=two) drawLine(g,one.getX()+(one.getWidth()/2),two.getX()+(two.getWidth()/2),one.getY()+(one.getHeight()/2),two.getY()+(two.getHeight()/2), one,two,0);
}

public void drawLine(Graphics g, int x1, int x2, int y1, int y2, Table one, Table two, int shift)
    {
    Boolean dec=false;
    Integer plus=0,minus=0;
    for(int e=0;e<tables.size();e++)
    {
        
        if(tables.get(e)!=one && tables.get(e)!=two)
        {
            if(tables.get(e).getX()>x1 && tables.get(e).getX()+tables.get(e).getWidth()<x2)
                if(tables.get(e).getY()<y1+shift && tables.get(e).getY()+tables.get(e).getHeight()>y1+shift)
                {
                     dec = true;
                     continue;
                }
                     else
                {
                     plus = shift;
                     break;
                }

            if(tables.get(e).getX()>x1 && tables.get(e).getX()+tables.get(e).getWidth()<x2)
                if(tables.get(e).getY()<y1-shift && tables.get(e).getY()+tables.get(e).getHeight()>y1-shift)
                {
                     dec = true;
                     continue;
                }
                     else
                {
                     plus = shift;
                     break;
                }
        }
    }

    if(dec)
    //System.out.println("Zasłania element");
        drawLine(g,x1,x2,y1,y2,one,two,shift+5);
    else
        {
        if(minus>=plus) shift=minus;
        else shift=plus;
        g.drawLine(x1, y1 + shift, x2, y1 + shift);
        }
    
    }


public Vector<Element> getMousableElements()
{
    return tables;
}

public String toXML()
{
    String XML="";
    XML+="<diagram type='"+getType()+"'>\n";
    for(int i=0;i<tables.size();i++)
        XML+=tables.get(i).toXML();
    XML+="</diagram>\n";
    return XML;
}   

public void addChildToNode(String name)
{       
        DefaultMutableTreeNode temp = new DefaultMutableTreeNode(name);
        functionsNode.add(temp);
}


}

class Table extends Element
{
    public Vector<Line> lines=new Vector<Line>();
    public Vector<Field> fields=new Vector<Field>();
    
    public Table(String name, Color color)
    {
        this.name=name;
        this.color=color;
    }

     public void addFields(NodeList fieldsXML)
    {
        for(int i=0;i<fieldsXML.getLength();i++)
            if(fieldsXML.item(i) instanceof org.w3c.dom.Element)
            {
                String tempName="", tempType="";
                String tempNotNull="", tempUnique="", tempPK="";
                NodeList fieldPropeties=fieldsXML.item(i).getChildNodes();
                for(int j=0;j<fieldPropeties.getLength();j++)
                    if(fieldPropeties.item(j) instanceof org.w3c.dom.Element)
                    {
                    String propety=fieldPropeties.item(j).getNodeName();
                    String value=fieldPropeties.item(j).getTextContent();
                    if(propety.equals("name"))  tempName=value;
                    if(propety.equals("type"))  tempType=value;

                    if(propety.equals("notnull"))  tempNotNull=value;
                    if(propety.equals("unique"))  tempUnique=value;
                    if(propety.equals("primarykey"))  tempPK=value;
                    }
                fields.add(new Field(tempName, tempType, tempNotNull, tempUnique, tempPK));
            }
    }

    public Boolean isMouseOverElement(int mouseX, int mouseY)
    {
        int x1=getX();
        int x2=x+getWidth();
        int y1=getY();
        int y2;
        if(displayMode==1)
            y2=y+getHeight();
        else
            y2=y+fontSize+(2*margin);
        if(mouseX>x1 && mouseX<x2 && mouseY>y1 && mouseY<y2)
            return true;
        else
            return false;
    }
    
    public String toString()
    {
        return this.name;
    }
    
    public int getWidth()
    {
        int width=name.length()*10;
        int tmp=0;
        for(int i=0;i<fields.size();i++)
            {
            tmp=fields.get(i).toString().length()*fontSize;
            if(tmp>width) width=tmp;
            }
        return width;
    }
    
    public int getHeight()
    {
        return (margin+10)*fields.size()+(3*margin)+fontSize;
    }
    
    public void setPosition(Vector<Element> tables, int index, int canvasWidth, int canvasHeight)
    {
    int criticalX=canvasWidth;
    if(autoLocated)
    for(int i=index-1;i>=0;i--)
        if(tables.get(i).isAutoLocated())
        {
            criticalX=tables.get(i).getX()+tables.get(i).getWidth()+margin;
            if((canvasWidth-criticalX)>=getWidth())
            {
                 x=criticalX;
                y=tables.get(i).getY();
            }
            else
            {
                x=margin;
                y=tables.get(i).getY()+tables.get(i).getHeight();
            }
            break;
        }
    }
    
    
    public void draw(Graphics g)
    {
        Graphics2D ct=(Graphics2D)g;
        ct.setStroke(new BasicStroke(stroke));
        String label;
        int width=getWidth();
        int height=getHeight();
        if(visible)
        {
        Color backgroundColor = new Color(color.getRed() ,color.getGreen() , color.getBlue(), 60);
        if(displayMode==1)
        {
        ct.setColor(Color.WHITE);
        ct.fillRect(x, y+fontSize+margin*2, width, height);
        ct.setColor(Color.BLACK);
        for(int i=0;i<fields.size();i++)
        {
            label=fields.get(i).getName()+" : "+fields.get(i).getType();
            ct.drawString(label, x+margin, y+(2*fontSize)+(3*margin)+((fontSize+margin)*i));
        }
        }
        ct.setColor(backgroundColor);
        ct.fillRect(x, y, width,fontSize+margin*2 );

        if(displayMode==1) ct.drawRect(x, y, width, height);
        ct.setColor(Color.BLACK);
        ct.drawString(name,x+margin,y+margin+fontSize);
        }
    }
    
    public String toXML()
    {
        String XML="";
        XML+="  <table>\n";
        XML+="    <name>"+name+"</name>\n";
        XML+="    <color>"+color.getRGB()+"</color>\n";
        XML+="    <x>"+x+"</x>\n";
        XML+="    <y>"+y+"</y>\n";
        XML+="    <isAutoLocated>"+autoLocated+"</isAutoLocated>\n";
        XML+="    <displayMode>"+displayMode+"</displayMode>\n";
        XML+="    <comment>"+comment+"</comment>\n";
        XML+="    <fields>\n";
        for(int i=0;i<fields.size();i++)
            XML+=fields.get(i).toXML();
        XML+="    </fields>\n";
        XML+="  </table>\n";
        return XML;
    }

    public void modifyElement(Diagram diagram, Element element)
    {
        tablePropeties tp=new tablePropeties((DBDiagram)diagram);
        System.out.println("OK1");
        tp.setTable((Table)element);
        tp.showWindow();
    }
    
    public void deleteElement(Diagram diagram, Element element)
    {
        DBDiagram diagramT=(DBDiagram)diagram;
        diagramT.tables.remove((Table)element);
    }

    public void changeDisplayMode()
    {
       if(displayMode==0) displayMode=1;
       else displayMode=0;
    }
}

class Field
{
    private String name;
    private String type;
    private Boolean not_null;
    private Boolean unique;
    private Boolean primary_key;
    
    public Field(String name, String type, String not_null, String unique, String primary_key)
    {
        setName(name);
        setType(type);
        setNotNull(not_null);
        setUnique(unique);
        setPrimaryKey(primary_key);
    }
    
    public void setName(String name)
    {
        this.name=name;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public void setType(String type)
    {
        this.type=type;
    }
    
    public String getType()
    {
        return this.type;
    }

    public String getNotNull()
    {
        if(this.not_null.booleanValue()) return "tak";
        else return "nie";
    }

    public void setNotNull(String not_null)
    {
        if(not_null.toLowerCase().equals("tak")) this.not_null=new Boolean(true);
        else this.not_null=new Boolean(false);
    }

    public String getUnique()
    {
        if(this.unique.booleanValue()) return "tak";
        else return "nie";
    }

    public void setUnique(String unique)
    {
        if(unique.toLowerCase().equals("tak")) this.unique=true;
        else this.unique=false;
    }

    public String getPrimaryKey()
    {
        if(this.primary_key.booleanValue()) return "tak";
        else return "nie";
    }

    public void setPrimaryKey(String primary_key)
    {
        if(primary_key.toLowerCase().equals("tak")) this.primary_key=true;
        else this.primary_key=false;
    }

    public String toXML()
    {
        String XML="";
        XML+="      <field>\n";
        XML+="        <name>"+getName()+"</name>\n";
        XML+="        <type>"+getType()+"</type>\n";
        XML+="        <notnull>"+getNotNull()+"</notnull>\n";
        XML+="        <unique>"+getUnique()+"</unique>\n";
        XML+="        <primarykey>"+getPrimaryKey()+"</primarykey>\n";
        XML+="      </field>\n";
        return XML;
    }
}

class Line
{
public int x1,x2,y1,y2;
}
