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

public class DBDiagram extends Diagram
{
    public Vector<Element> tables=new Vector<Element>();
    DefaultMutableTreeNode tablesNode;
    
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
    DefaultMutableTreeNode functionsNode=new DefaultMutableTreeNode("Funkcje");
    DefaultMutableTreeNode proceduresNode=new DefaultMutableTreeNode("Procedury");
    DefaultMutableTreeNode usersNode=new DefaultMutableTreeNode("Użytkownicy");

    elementsTree.add(tablesNode);
    elementsTree.add(viewsNode);
    elementsTree.add(triggersNode);
    elementsTree.add(functionsNode);
    elementsTree.add(proceduresNode);
    elementsTree.add(usersNode);
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
    /*else if(selectedNode.toString().equals("Wyzwalacze")) ;  
    else if(selectedNode.toString().equals("Funkcje")) ;
    else if(selectedNode.toString().equals("Procedury")) ;
    else if(selectedNode.toString().equals("Użytkownicy")) ;
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
    if(tables.size()>=2) drawConnections(g);
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
    
    public Boolean isMouseOverElement(int mouseX, int mouseY)
    {
        int x1=getX();
        int x2=x+getWidth();
        int y1=getY();
        int y2=y+getHeight();
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
        ct.setColor(Color.white);
        ct.fillRect(x, y, width, height);
        ct.setColor(color);
        for(int i=0;i<fields.size();i++)
        {
            label=fields.get(i).getName()+" : "+fields.get(i).getType();
            ct.drawString(label, x+margin, y+(2*fontSize)+(3*margin)+((fontSize+margin)*i));
        }
        ct.drawRect(x, y, width, height);
        ct.drawString(name,x+margin,y+margin+fontSize);
        ct.drawLine(x, y+fontSize+(2*margin), width+x, y+fontSize+(2*margin));
        }
    }
    
    public String toXML() { return "<table>"+name+"</table>"; }
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
}

class Field
{
    private String name;
    private String type;
    private Boolean not_null;
    private Boolean unique;
    private Boolean primary_key;
    
    public Field(String name, String type, Boolean not_null, Boolean unique, Boolean primary_key)
    {
        this.name=name;
        this.type=type;
        this.not_null=not_null;
        this.unique=unique;
        this.primary_key=primary_key;
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
}

class Line
{
public int x1,x2,y1,y2;
}
