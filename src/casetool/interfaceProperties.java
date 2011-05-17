/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package casetool;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author Piotr
 */
public class interfaceProperties extends JDialog {
    
    public interfaceProperties(ClassDiagram cd) {
        
    }
    
    public void showWindow() {
        JOptionPane.showMessageDialog(null, "Udało się!", "Nowy interfejs", JOptionPane.INFORMATION_MESSAGE);
    }
}
