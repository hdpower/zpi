package casetool.usecase;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 *      obiekt tej klasy umożliwia tworzenie obiektów ExtendLink
 * 
 */
//------------------------------------------------------------------------------ klasa ExtendLink 
public class ExtendLink extends Link {
    
    //-------------------------------------------------------------------------- pola
    String str = "<<extend>>";
    Graphics2D ga;
    
    //-------------------------------------------------------------------------- konstruktor główny
    public ExtendLink(LinkPoint pfrom, LinkPoint pto) {

        super(pfrom, pto);

    }

    //-------------------------------------------------------------------------- konstruktor z definiowanym napisem
    public ExtendLink(LinkPoint pfrom, LinkPoint pto, String pstr) {

        super(pfrom, pto);
        
        str = pstr;

    }

    @Override
    public void draw(Graphics g) {
        
        ga = (Graphics2D)g;
        
        ga.setColor(color);
        ga.setStroke(new BasicStroke(stroke));
        
        ga.drawLine(from.getX()+4, from.getY()+4, to.getX()+4, to.getY()+4);
        ga.drawString(str, ((to.getX()-from.getX())/2)+from.getX(), ((to.getY()-from.getY())/2)+from.getY());
        
    }

}
