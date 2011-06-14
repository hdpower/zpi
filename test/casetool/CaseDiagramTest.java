package casetool;

import casetool.CaseDiagram.CaseDiagramTypes;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Albert Leśnikowski
 */
public class CaseDiagramTest {
    
    //-------------------------------------------------------------------------- pola
    CaseDiagram cd;
    
    //-------------------------------------------------------------------------- konstruktor testu
    public CaseDiagramTest() {
        
        System.out.println(" -> Start testów");
        
        cd = new CaseDiagram(new MainWindow());
    }

    //-------------------------------------------------------------------------- inicjalizacja obiektu głównego
    @Test
    public void initTest() {
        
        System.out.println(" -> Inicjalizacja zmiennych");
        
        // stwórz obiekt diagramu
        cd = new CaseDiagram(new MainWindow());
        
        // czy główny obiekt diagramu jest zainicjalizowany
        assertNotNull(cd);
        
        // czy główne drzewo diagramu jest zainicjowane
        assertNotNull(cd.elements);
        
        // czy płutno nie zostało jeszcze zainicjowane
        assertNull(cd.mw.tempCanvas);
        
        // czy instancja okna już istnieje
        assertNotNull(cd.mw.getInstance());
                
    }
    
    //-------------------------------------------------------------------------- startowe wartości zmiennych
    @Test
    public void startValuesTest() {
        
        System.out.println(" -> Startowe wartości zmiennych");
        
        // czy lista obiektów jest pusta
        assertEquals(cd.elements, new ArrayList<Element>());
        
        // czy typ diagramu jest poprawny
        assertEquals(cd.typ, "CaseUse");
        
        // czy projekt nie został zapisany
        assertFalse(cd.isProjectWasSaved());
        
        // czy główne okno jest zainicjowane
        assertTrue(cd.mw.isVisible());

    }
    
    //-------------------------------------------------------------------------- wstawianie aktora na diagram
    @Test
    public void addActorTest() {
        
        System.out.println(" -> Dodaj Aktora");
        
        // dodaj nowego Aktora
        cd.addUseCaseElement(CaseDiagramTypes.ACTOR);
        
        // sprawdź czy dodano poprawnie
        assertEquals(cd.elements.toArray().length,1);
        
        // sprawdź kolor aktora
        assertEquals(cd.elements.get(0).color, new Color(0, 0, 0));
        
        // sprawdź rozmiar czcionki
        assertEquals(cd.elements.get(0).fontSize,10);

        // sprawdź grubość lini
        assertEquals(cd.elements.get(0).stroke,1);
           
        // sprawdź rozmiar czcionki
        assertEquals(cd.elements.get(0).fontSize,10);
        
        
    }   
    
    //-------------------------------------------------------------------------- wstawienie linka extend na diagram
    @Test
    public void addExtendLink() {
        
        System.out.println(" -> Dodaj ExtendLink");
        
        // dodaj nowego ExtendLinka
        cd.addLink(CaseDiagramTypes.EXTENDLINK);
        
        // sprawdź czy dodano poprawnie
        assertEquals(cd.elements.toArray().length,3);
        
        // sprawdź kolor extendlinka
        assertEquals(cd.elements.get(0).color, new Color(0, 0, 0));
        
        // sprawdź rozmiar czcionki
        assertEquals(cd.elements.get(0).fontSize,10);

        // sprawdź grubość lini
        assertEquals(cd.elements.get(0).stroke,1);
           
        // sprawdź rozmiar czcionki
        assertEquals(cd.elements.get(0).fontSize,10);
        
        
    }
    
    //-------------------------------------------------------------------------- wstawienie linka extend na diagram
    @Test
    public void addSystemBox() {
        
        System.out.println(" -> Dodaj SystemBox");
        
        // dodaj nowego ExtendLinka
        cd.addSystemBox();
        
        // sprawdź czy dodano poprawnie
        assertEquals(cd.elements.toArray().length,3);
        
        // sprawdź kolor extendlinka
        assertEquals(cd.elements.get(0).color, new Color(0, 0, 0));
        
        // sprawdź rozmiar czcionki
        assertEquals(cd.elements.get(0).fontSize,10);

        // sprawdź grubość lini
        assertEquals(cd.elements.get(0).stroke,1);
           
        // sprawdź rozmiar czcionki
        assertEquals(cd.elements.get(0).fontSize,10);
        
    }
    
    
    @BeforeClass
    public static void setUpClass() throws Exception {
       
        //...
        
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        
        //...
        
    }
    
    @Before
    public void setUp() {
        
        //...
        
    }
    
    @After
    public void tearDown() {
        
        //...
        
    }
    
  
}
