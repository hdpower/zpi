package casetool;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;

public class ClassDiagram extends Diagram
{
    public Vector<Element> classes;
    public DefaultMutableTreeNode classNode;
    
    public ClassDiagram() {
        this.typ = "Class";
        elementsTree = new DefaultMutableTreeNode("UML");
        classes = new Vector<Element>();
        
        //Stworzenie drzewa opcji
        classNode = new DefaultMutableTreeNode("Klasa");
        DefaultMutableTreeNode interfaceNode = new DefaultMutableTreeNode("Interfejs");
        DefaultMutableTreeNode generalizationNode = new DefaultMutableTreeNode("Generalizacja");
        DefaultMutableTreeNode assemblingNode = new DefaultMutableTreeNode("Złożenie");
        DefaultMutableTreeNode noteNode = new DefaultMutableTreeNode("Notatka");
        
        elementsTree.add(classNode);
        elementsTree.add(interfaceNode);
        elementsTree.add(generalizationNode);
        elementsTree.add(assemblingNode);
        elementsTree.add(noteNode);
    }
    
    public ClassDiagram getInstance() {
        return this;
    }

    public void setContextMenuOptions(DefaultMutableTreeNode selectedNode, JPopupMenu contextMenu) { 
        
        contextMenu.removeAll();
        
        if(selectedNode.toString().equals("Klasa")) {
            
            JMenuItem addClass = new JMenuItem("Dodaj klasę");
            addClass.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    classProperties dialogWindow=new classProperties(getInstance());
                    dialogWindow.showWindow();
                }
            });
            contextMenu.add(addClass);
            
        } else if(selectedNode.toString().equals("Interfejs")) {
            
            JMenuItem addInterface = new JMenuItem("Dodaj interfejs");
            addInterface.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    interfaceProperties dialogWindow = new interfaceProperties(getInstance());
                    dialogWindow.showWindow();
                }
            });
            contextMenu.add(addInterface);
        }
    }
    
    public void drawElements(Graphics g) {
        
        for(int i=0; i<classes.size(); i++) {
            classes.get(i).setPosition(classes, i, panel.getComponent(1).getWidth(), panel.getHeight());
            classes.get(i).draw(g);
        }
    }
    
    public void setPosition(Vector<Element> tables, int index, int canvasWidth, int canvasHeight) { }
    public void modifyElement(Diagram diagram, Element element) { }
    public void deleteElement(Diagram diagram, Element element) { }
    
    @Override
    public Vector<Element> getMousableElements() {
        return classes;
    }
    
    public void refreshTables() {
        classNode.removeAllChildren();
        for(Element element : classes)
            classNode.add(new DefaultMutableTreeNode(element));
    }
}

class Class extends Element {
    
    public Vector<ClassField> fields = new Vector<ClassField>();
    private String documentation;

    public Class(String name, Color color, String doc) {
        
        this.name  = name;
        this.color = color;
        this.documentation = doc;
    }
    
    public void setDocumentation(String doc) 
    {
        this.documentation = doc;
    }
    
    public String getDocumentation() 
    {
        return documentation;                
    }
    
    @Override
    public Boolean isMouseOverElement(int mouseX, int mouseY) { 
        
        int x_1 = getX();
        int x_2 = x + getWidth();
        int y_1 = getY();
        int y_2 = y + getHeight();
        
        if(mouseX > x_1 && mouseX < x_2 && mouseY > y_1 && mouseY < y_2) 
            return true;
        else 
            return false;
    }
    
    @Override
    public int getWidth() {
                
        int width = name.length() * 10;
        int tmp   = 0;
        
        for(int i = 0; i < fields.size(); i++) {
            tmp = fields.get(i).toString().length() * fontSize;
            if(tmp > width) width=tmp;
        }
        
        return width;
    }
    
    @Override
    public int getHeight() {
        return (margin + 10) * fields.size() + (3 * margin) + fontSize;
    }
    
    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String toXML() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D ct = (Graphics2D)g;
        ct.setStroke(new BasicStroke(stroke));
        String label;
        int width=getWidth();
        int height=getHeight();
        
        if(visible) {
            
            ct.setColor(Color.WHITE);
            Color d = new Color(color.getRed() ,color.getGreen() , color.getBlue(), 60);
            ct.fillRect(x, fontSize+margin*2, width, height);
            ct.setColor(Color.BLACK);
            
            for(int i=0;i<fields.size();i++) {
                label=fields.get(i).getName()+" : "+fields.get(i).getType();
                ct.drawString(label, x+margin, y+(2*fontSize)+(3*margin)+((fontSize+margin)*i));
            }
            
            ct.setColor(d);
            ct.fillRect(x, y, width,fontSize+margin*2 );

            ct.drawRect(x, y, width, height);
            ct.setColor(Color.BLACK);
            ct.drawString(name,x+margin,y+margin+fontSize);
        }
    }

    @Override
    public void setPosition(Vector<Element> tables, int index, int canvasWidth, int canvasHeight) {
        
        int criticalX = canvasWidth;
        if(autoLocated) {
            for(int i = index-1; i >= 0; i--) {            
                if(tables.get(i).isAutoLocated())
                {
                    criticalX = tables.get(i).getX() + tables.get(i).getWidth() + margin;
                    if((canvasWidth - criticalX) >= getWidth()) {
                        x = criticalX;
                        y = tables.get(i).getY();
                    }
                    else {
                        x = margin;
                        y = tables.get(i).getY() + tables.get(i).getHeight();
                    }
                    
                    break;
                }
            }
        }
    }

    @Override
    public void modifyElement(Diagram diagram, Element element) {
        classProperties cp = new classProperties((ClassDiagram)diagram);
        System.out.println("OK1");
        cp.setClass((Class)element);
        cp.showWindow();
    }

    @Override
    public void deleteElement(Diagram diagram, Element element) {
        ClassDiagram diagramC = (ClassDiagram)diagram;
        diagramC.classes.remove((Class)element);
    }    
}

class ClassField
{
    private String name;
    private String type;
    private Boolean not_null;
    private Boolean unique;
    private Boolean primary_key;
    
    public ClassField(String name, String type, Boolean not_null, Boolean unique, Boolean primary_key)
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