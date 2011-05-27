
package casetool.usecase;

import casetool.CaseDiagram;
import casetool.Diagram;
import casetool.Element;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Vector;
import javax.swing.*;

/*
 *  klasa abstrakcyjna która przechowuje wspólne pola i zachowania dla
 *  elementów aktora i przypadku uzycia
 * 
 */

//------------------------------------------------------------------------------ klasa abstrakcyjna UseCaseElement ---
public class UseCaseElement extends Element {

    //-------------------------------------------------------------------------- pola
    
    //-------------------------------------------------------------------------- rysuj element
    @Override
    public void draw(Graphics g) {
        
    }

    public void changeDisplayMode() { }

    //-------------------------------------------------------------------------- czy kursor jest nad obiektem
    @Override
    public Boolean isMouseOverElement(int mouseX, int mouseY) {
        int x1 = getX();
        int x2 = x + getWidth();
        int y1 = getY();
        int y2 = y + getHeight();
        
        if(mouseX > x1 && mouseX < x2 && mouseY > y1 && mouseY < y2) {
            return true;   
        } else {
            return false;
        }            
    }
    
    //-------------------------------------------------------------------------- pobierz szerokość
    public int getWidth() {
        return 0;
    }
    
    //-------------------------------------------------------------------------- pobierz wysokość
    public int getHeight() {
        return 0;
    }
    
    //-------------------------------------------------------------------------- ustaw element
    @Override
    public void setPosition(int x, int y) {
        
        this.x = x;
        this.y = y;
        
    }

    @Override
    public void setPosition(Vector<Element> tables, int index, int canvasWidth, int canvasHeight) {
        
    }

    @Override
    public void modifyElement(Diagram diagram, Element element) {
        
    }

    @Override
    public void deleteElement(Diagram diagram, Element element) {
        
        CaseDiagram diagramT = (CaseDiagram)diagram;
        diagramT.elements.remove((UseCaseElement)element);
        
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public String toXML() {
        return "";
    }
    
    
    
}
