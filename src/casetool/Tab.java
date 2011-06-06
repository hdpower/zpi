/*
 *  Ta klasa dostarcza tylko zakładek 
 */
package casetool;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Klasa dostarczająca dwóch zakładek 
 * @author ramyi
 */
public class Tab extends JTabbedPane{
    private JPanel MainPanel ;
    private JPanel ParamPanel ;
    
    Tab()
    {
        MainPanel = new JPanel();
        ParamPanel = new JPanel(new BorderLayout(20, 5));
    }
    /**
     * @see JTabbedPane 
     * @param firstTabName nazwa pierwszej zakladki
     * @param secondTabName nazwa drugiej zakladki
     * @return JTabbedPane z dwoma zakładkami
     */
    public JTabbedPane addTabs(String firstTabName,String secondTabName)
    {
        JTabbedPane tabs= new JTabbedPane();

        tabs.addTab(firstTabName, MainPanel);
        tabs.addTab(secondTabName, ParamPanel);
        return tabs;
    }
    /**
     * 
     * @param c elemnty dodawane do pierwszego panelu z zarządcą układy FlowLayaout
     */
    public void addToMain(Component c)
    {
        MainPanel.add(c);
    }
   
    /**
     * @see BorderLayout
     * @param c elemnty dodawane do pierwszego panelu z zarządcą układy BorderLayout 
     * @param where w które miejsce układu ma być wstawiony component, 
     * możliwości do wykorzystania: 
     * 
     * <table>
     * <tr><td>BorderLayout.CENTER</td></tr>
     * <tr><td>BorderLayout.EAST</td><td>or</td><td>BorderLayout.LINE_START</td></tr>
     * <tr><td>BorderLayout.WEST</td><td>or</td><td>BorderLayout.LINE_END</td></tr>
     * <tr><td>BorderLayout.NORTH</td><td>or</td><td>BorderLayout.PAGE_START</td></tr>
     * <tr><td>BorderLayout.SOUTH</td><td>or</td><td>BorderLayout.PAGE_END</td></tr>
     * </table>
     */
     public void addToSecendary(Component c, String where)
    {
        ParamPanel.add(c,where);
    } 
}
