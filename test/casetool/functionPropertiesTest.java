/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package casetool;

import java.awt.Dimension;
import javax.management.loading.PrivateClassLoader;
import javax.swing.ActionMap;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ramyi
 */
public class functionPropertiesTest {
    functionProperties instance;
    DBDiagram tBDiagram;
    
    public functionPropertiesTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of showFun method, of class functionProperties.
     */
    @Test
    public void testShowFun() {
        System.out.println("showFun");
        instance = new functionProperties(tBDiagram);
        String sTest="test";
        assertTrue(instance.rdyFun.isEmpty());
        instance.showFun(sTest);
        assertEquals(instance.status, 1);
        instance.rdyFun.put(sTest, sTest);
        instance.showFun(sTest);
        assertEquals(instance.status, 0);
    }

    /**
     * prawidlowo zainicjalizowane objekty
     */
    @Test
    public void testInitiation() {
        instance = new functionProperties(tBDiagram);
        instance.ShowPanel();
        assertNotNull(instance.InOutList);
        assertNotNull(instance.typesList);
        assertNotNull(instance.MainPanel);
        assertNotNull(instance.ParamTable);
        assertNotNull(instance.t);
        assertNotNull(instance.tabs);
        assertEquals(instance.readParams, "");
        assertEquals(instance.licznik, 0);
        //String[] typesTab={"INT","DOUBLE","VARCHAR","VARCHAR2","CHAR","DATE","TIMESTAMP"};
        //String[] InOutTab={"In","Out","In Out"};
        assertEquals(instance.typesList.getItemCount(),7 );
        assertEquals(instance.InOutList.getItemCount(),3);
    }
    
    /*
     * Wlasciwości okienka
     */
    @Test
    public void testWindowPropertis()
    {
        instance = new functionProperties(tBDiagram);
        assertEquals(instance.getTitle(), "Nowa Funkcja");
        assertEquals(instance.getSize(),new Dimension(500,650));
        assertTrue(instance.content.isDisplayable());

    }
    
    
     /**
     * Test of ShowPanel method, of class functionProperties.
     */
    @Test
    public void testShowPanel() {
        System.out.println("ShowPanel");
        DBDiagram db = new DBDiagram();
        instance = new functionProperties(db);
        instance.ShowPanel();
        //test liczby paneli i ich nazw
        assertEquals(instance.tabs.getTabCount(), 2);
        assertEquals(instance.tabs.getTitleAt(0), "Fukcja");
        assertEquals(instance.tabs.getTitleAt(1), "Parametry");
        assertEquals(instance.MainPanel.getComponentCount(), 12);
        
        
  
        //test z 2 zakladki
        assertEquals(instance.t.ParamPanel.getComponentCount(), 4);             //4 panele 
        JPanel pierwszy = (JPanel)instance.t.ParamPanel.getComponent(0);        //z tabelka
        JPanel trzeci = (JPanel)instance.t.ParamPanel.getComponent(2);          //z przyciskami
        JPanel czwarty = (JPanel)instance.t.ParamPanel.getComponent(1);         //z akceptuj
        //czy sa wiersze
        assertEquals(trzeci.getComponentCount(), 2);
        JButton tDodaj = (JButton)trzeci.getComponent(0);
        JButton tUsun = (JButton)trzeci.getComponent(1);
        assertEquals(instance.licznik, 0);
        tDodaj.doClick();
        assertEquals(instance.licznik, 1);
        tDodaj.doClick();
        tUsun.doClick();
        assertEquals(instance.licznik, 1);
        tDodaj.doClick();
        tDodaj.doClick();
        tDodaj.doClick();
        tDodaj.doClick();
        assertEquals(instance.licznik, 5);
        assertEquals(instance.tablemodel.getRowCount(),5);
        instance.ParamTable.setEditingRow(2);
        assertEquals(instance.ParamTable.getValueAt(3, 0),4);
        tUsun.doClick();
        assertEquals(instance.ParamTable.getValueAt(3, 0),4);
        assertEquals(instance.ParamTable.getRowCount(),4);
        tUsun.doClick();
        tUsun.doClick();
        tUsun.doClick();
        
        
        //test do readparams
        instance.ParamTable.setValueAt("test", 0, 1);
        instance.ParamTable.setValueAt(instance.InOutList.getItemAt(0) , 0, 2);
        instance.ParamTable.setValueAt(instance.typesList.getItemAt(0) , 0, 3);
        instance.ParamTable.setValueAt(5 , 0, 4);
        JButton tAkceptujParametry =(JButton)czwarty.getComponent(0);
        assertEquals(instance.readParams, "");
        tAkceptujParametry.doClick();
        assertEquals(instance.readParams, "( test In INT(5) )");
        
        //test przycisku z 1
        JButton bMain = (JButton)instance.MainPanel.getComponent(11);
        assertEquals (bMain.getText(),"Dodaj funkcję");
        
        assertEquals(instance.status, 0);
        bMain.doClick();
        assertEquals(instance.status, 1);
        JTextField tNazwaF = (JTextField)instance.MainPanel.getComponent(1);
        tNazwaF.setText("TestNazwa");
        assertTrue(db.elementsTree.isRoot());
        assertEquals(db.elementsTree.getChildCount(),6);
        assertTrue(db.functionsNode.isLeaf());
        assertTrue(db.functionsNode.isNodeAncestor(db.elementsTree));
        assertEquals(db.functionsNode.getChildCount(), 0);
        bMain.doClick();
        assertEquals(instance.status, 0);
        assertTrue(instance.rdyFun.containsKey("TestNazwa"));
        assertEquals(db.functionsNode.getChildCount(), 1);
        tNazwaF.setText("TestNazwa1");
        bMain.doClick();
        assertEquals(db.functionsNode.getChildCount(), 2);
        
        assertTrue(instance.rdyFun.containsKey("TestNazwa1"));
        System.out.println();
 
    }
}
