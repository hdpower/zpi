/*
 *  Ta klasa dostarcza tylko zakładek 
 */
package casetool;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.LayoutManager2;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
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
    
    public JTabbedPane addTabs(String firstTabName,String secondTabName)
    {
        JTabbedPane tabs= new JTabbedPane();

        tabs.addTab(firstTabName, MainPanel);
        tabs.addTab(secondTabName, ParamPanel);
        return tabs;
    }
    public void addToMain(Component c)
    {
        MainPanel.add(c);
    }
     public void addToSecendary(Component c, String where)
    {
        ParamPanel.add(c,where);
    }
}
