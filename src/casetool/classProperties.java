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
public class classProperties extends JDialog {
    
    public classProperties(ClassDiagram cd) {
        
    }
    
    public void showWindow() {
        JOptionPane.showMessageDialog(null, "Udało się!", "Nowa klasa", JOptionPane.INFORMATION_MESSAGE);
    }
}