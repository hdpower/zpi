package casetool.usecase;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 *      obiekt tej klasy umożliwia tworzenie obiektów InheritLink
 * 
 */
//------------------------------------------------------------------------------ klasa InheritLink 
public class InheritLink extends Link {
    
    //-------------------------------------------------------------------------- pola
    String str = "<<inlclude>>";
    Graphics2D ga;
    
    //-------------------------------------------------------------------------- konstruktor główny
    public InheritLink(LinkPoint pfrom, LinkPoint pto) {

        super(pfrom, pto);

    }

    @Override
    public void draw(Graphics g) {
        
        ga = (Graphics2D)g;
        
        ga.setColor(color);
        ga.setStroke(new BasicStroke(stroke));
        
        ga.drawLine(from.getX()+4, from.getY()+4, to.getX()+4, to.getY()+4);
        
        // obliczanie kierunku
    }


}
