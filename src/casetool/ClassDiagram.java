package casetool;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Stack;
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

    public ClassDiagram(org.w3c.dom.Element XMLroot)
    {
        this();
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
    
    private Vector<ClassAtribute> fields = new Vector<ClassAtribute>();
    private Vector<ClassMethod> methods = new Vector<ClassMethod>();
    private String documentation;
    private boolean isAbstract;
    private boolean isStatic;
    private String visibility;
    
    public Class() {
        
    }

    public Class(String name, Color color) {
        
        this.name  = name;
        this.color = color;
    }

    public void changeDisplayMode() { }

    public class ClassAtribute {
        
        private String nazwa;
        private String typ;
        private String widocznosc;
        private String liczebnosc;
        private String wartoscPoczatkowa;
        
        public ClassAtribute() {}
        
        public ClassAtribute(String n, String t, String w, String l, String p) {
            this.nazwa = n;
            this.typ = t;
            this.widocznosc = w;
            this.liczebnosc = l;
            this.wartoscPoczatkowa = p;
        }
        
        public void setNazwa(String n)      { this.nazwa = n; }
        public void setTyp(String t)        { this.typ = t;   }
        public void setWidocznosc(String w) { this.widocznosc = w; }
        public void setLiczebnosc(String l) { this.liczebnosc = l; }
        public void setPoczatkowa(String p) { this.wartoscPoczatkowa = p; }
        
        public String getNazwa()        { return this.nazwa; }
        public String getTyp()          { return this.typ;   }
        public String getWidocznosc()   { return this.widocznosc; }
        public String getLiczebnosc()   { return this.liczebnosc; }
        public String getPoczatkowa()   { return this.wartoscPoczatkowa; }  
        
    }
    
    public void setDocumentation(String doc) { this.documentation = doc; }  
    public String getDocumentation() { return this.documentation; }
    
    public void setAbstract(boolean abs) { this.isAbstract = abs; }
    public boolean getAbstract() { return this.isAbstract; }
    
    public void setStatic(boolean sta) { this.isStatic = sta; }
    public boolean getStatic() { return this.isStatic; }
    
    public void setVisibility(String v) { this.visibility = v; }
    public String getVisibility() { return this.visibility; }
    
    public void dodajAtrybut(String n, String t, String w, String l, String p) { 
        ClassAtribute atrybut = new ClassAtribute(n, t, w, l, p);
        fields.add(atrybut);        
    }
    
    public Vector<ClassAtribute> getClassAtributes() { return fields; }
    
    public String getNazwa(ClassAtribute ca)        { return ca.getNazwa(); }
    public String getTyp(ClassAtribute ca)          { return ca.getTyp();   }
    public String getWidocznosc(ClassAtribute ca)   { return ca.getWidocznosc(); }
    public String getLiczebnosc(ClassAtribute ca)   { return ca.getLiczebnosc(); }
    public String getPoczatkowa(ClassAtribute ca)   { return ca.getPoczatkowa(); } 
    
    public class ClassMethod {
        private String nazwa;
        private String typ;
        private String widocznosc;
        private boolean polimorfizm;
        private Vector<Parametr> parametry = new Vector<Parametr>();
        
        public ClassMethod() {}
        
        public ClassMethod(String n, String t, String w, boolean p) {
            this.nazwa = n;
            this.typ = t;
            this.widocznosc = w;
            this.polimorfizm = p;
        }
        
        protected class Parametr {
            private String nazwaP;
            private String typP;
            private String przekazywanie;
            
            public Parametr(String n, String t, String p) {
                this.nazwaP = n;
                this.typP = t;
                this.przekazywanie = p;
            }
            
            public void setNazwa(String n)      { this.nazwaP = n; }
            public void setTyp(String t)        { this.typP = t;   }
            public void setPrzekazywanie(String p) { this.przekazywanie = p; }

            public String getNazwa()        { return this.nazwaP; }
            public String getTyp()          { return this.typP;   }
            public String getPrzekazywanie()   { return this.przekazywanie; }

        }
        
        public void dodajParametr(Parametr p) {
            parametry.add(p);
        }
        
        public void setNazwa(String n)        { this.nazwa = n; }
        public void setTyp(String t)          { this.typ = t;   }
        public void setWidocznosc(String w)   { this.widocznosc = w; }
        public void setPolimorfizm(boolean p) { this.polimorfizm = p; }
        
        public String getNazwa()          { return this.nazwa; }
        public String getTyp()            { return this.typ;   }
        public String getWidocznosc()     { return this.widocznosc; }
        public boolean getPolimorfizm()   { return this.polimorfizm; } 
        public Vector<Parametr> getParametry() { return this.parametry; }
    }
    
    public void dodajMetode(String n, String t, String w, boolean p) {
        ClassMethod metoda = new ClassMethod(n, t, w, p);
        methods.add(metoda);
    }
    
    public void dodajMetode(ClassMethod m) {
        methods.add(m);
    }
    
    public void dodajParametr(ClassMethod cm, String n, String t, String p) {
        ClassMethod kz = new ClassMethod();
        ClassMethod.Parametr param = kz.new Parametr(n, t, p);
        cm.dodajParametr(param);
    }
    
    public Vector<ClassMethod> getClassMethods() { return methods; }
    
    public String  getNazwa      ( ClassMethod cm ) { return cm.getNazwa();         }
    public String  getTyp        ( ClassMethod cm ) { return cm.getTyp();           }
    public String  getWidocznosc ( ClassMethod cm ) { return cm.getWidocznosc();    }
    public boolean getPolimorfizm( ClassMethod cm ) { return cm.getPolimorfizm();   }
    
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
            tmp = (fields.get(i).getNazwa().length() + 
                   fields.get(i).getTyp().length() +
                   fields.get(i).getPoczatkowa().length() + 3) * fontSize;
            
            if(tmp > width) width=tmp;
        }
        
        for(int i=0; i < methods.size(); i++) {
            tmp = (methods.get(i).getNazwa().length() + 
                   methods.get(i).getWidocznosc().length() + 
                   methods.get(i).getWidocznosc().length()) * fontSize;
            
            if(tmp > width) width=tmp;
        }
        
        return width;
    }
    
    @Override
    public int getHeight() {
        return (margin + 10) * (fields.size()+methods.size()) + (3 * margin) + fontSize;
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
                String widocznosc_ustawiona = fields.get(i).getWidocznosc();
                String widocznosc_wyswietl = "";
                if(widocznosc_ustawiona.equals("private")) widocznosc_wyswietl = " - ";
                else if(widocznosc_ustawiona.equals("protected")) widocznosc_wyswietl = " # ";
                else if(widocznosc_ustawiona.equals("public")) widocznosc_wyswietl = " + ";
                else widocznosc_wyswietl = " ? ";
                
                String poczatkowa = fields.get(i).getPoczatkowa();
                String poczatkowa_wyswietl = "";
                if(!poczatkowa.isEmpty()) {
                    poczatkowa_wyswietl = " = " + poczatkowa;
                }

                label = widocznosc_wyswietl + fields.get(i).getNazwa() + " : " + fields.get(i).getTyp() + poczatkowa_wyswietl;
                ct.drawString(label, x+margin, y+(2*fontSize)+(3*margin)+((fontSize+margin)*i));
            }
            
            if(fields.size() > 0 && methods.size() > 0) {
                ct.setColor(d);
                ct.drawLine(x, y+(2*fontSize)+(3*margin)+((fontSize+margin) * (fields.size()-1)) + 5, x + width, y+(2*fontSize)+(3*margin)+((fontSize+margin) * (fields.size()-1)) + 5);
            }
            
            for(int i=0; i < methods.size(); i++) {
                
                ct.setColor(Color.BLACK);
                String widocznosc_ustawiona = methods.get(i).getWidocznosc();
                String widocznosc_wyswietl  = "";
                if(widocznosc_ustawiona.equals("private")) widocznosc_wyswietl = " - ";
                else if(widocznosc_ustawiona.equals("protected")) widocznosc_wyswietl = " # ";
                else if(widocznosc_ustawiona.equals("public")) widocznosc_wyswietl = " + ";
                else widocznosc_wyswietl = " ? ";
                
                String parametry = "( )";
                if(methods.get(i).getParametry().size() > 0) {
                    parametry = "( ... )";
                }
                
                label = widocznosc_wyswietl + methods.get(i).getNazwa() + parametry + " : " + methods.get(i).getTyp();
                ct.drawString(label, x + margin, y+(2*fontSize)+(3*margin)+((fontSize+margin) * i) + ((fontSize+margin) * (fields.size()-1)) + 20);
            }
            
            ct.setColor(d);
            ct.fillRect(x, y, width,fontSize+margin*2 );

            ct.drawRect(x, y, width, height+5);
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
