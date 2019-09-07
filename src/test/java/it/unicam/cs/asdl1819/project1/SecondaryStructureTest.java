package it.unicam.cs.asdl1819.project1;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SecondaryStructureTest {
    private SecondaryStructure test;
    private NussinovFolder set;

    @Before
    public void setUp() throws Exception {
        test = new SecondaryStructure("GGGAAAUCC");
        set = new NussinovFolder("GGGAAAUCC");

        set.fold();
        set.traceback(1,9);
        set.getOneOptimalStructure();
    }

    @Test(timeout = 10000)
    public final void testHashCode() {
        SecondaryStructure s = set.getOneOptimalStructure();
        assertEquals(216526887,s.hashCode());
    }

    @Test(timeout = 10000, expected = NullPointerException.class)   //test del costruttore con insieme vuoto di legami, per stringa nulla
    public final void testSecondaryStructureStringNull() {
        String stringa = null;

        SecondaryStructure s = new SecondaryStructure(stringa);
    }

    @Test(timeout = 10000, expected = IllegalArgumentException.class)    //test del costruttore con insieme vuoto di legami, per caratteri non validi
    public final void testSecondaryStructureString(){
        SecondaryStructure s1 = new SecondaryStructure("GGGSAAUCC");
    }


    @Test(timeout = 10000, expected = NullPointerException.class)   //test del costruttore con insieme dato di legami, per stringa nulla
    public final void testSecondaryStructureStringSetOfWeakBondNull() {
        String stringa = null;

        SecondaryStructure s = new SecondaryStructure(stringa);
    }

    @Test(timeout = 10000, expected = IllegalArgumentException.class)   //test del costruttore con insieme dato di legami, per stringa nulla
    public final void testSecondaryStructureStringSetOfWeakBond(){
        SecondaryStructure s1 = new SecondaryStructure("GGGSAAUCC");
    }

    @Test(timeout = 10000)
    public final void testGetPrimarySequence() {
        assertEquals("GGGAAAUCC", test.getPrimarySequence());
    }

    @Test(timeout = 10000)
    public final void testGetBonds() {
        SecondaryStructure s = set.getOneOptimalStructure();
        String arr = "[(3, 7), (2, 8), (1, 9)]";
        assertEquals(arr, s.getBonds().toString());
    }

    @Test(timeout = 10000)
    public final void testIsPseudoknotted() {
        SecondaryStructure s = set.getOneOptimalStructure();
        boolean x = false;

        assertEquals(x,s.isPseudoknotted());
    }

    @Test(timeout = 10000, expected = NullPointerException.class)   //test addBond per legame nullo
    public final void testAddBondNull() {
        boolean s = test.addBond(null);
    }

    @Test(timeout = 10000, expected = IndexOutOfBoundsException.class)  //test addBond per legame che esce fuori dai limiti
    public final void testAddBondOutOfBound() {
        boolean s = test.addBond(new WeakBond(1,11));
    }

    @Test(timeout = 10000, expected = IllegalArgumentException.class)   //test addBond per legame non possibile
    public final void testAddBond() {
        boolean s = test.addBond(new WeakBond(4,5));
        boolean s1 = test.addBond(new WeakBond(4,6));
        boolean s2 = test.addBond(new WeakBond(3,5));
    }


    @Test(timeout = 10000)
    public final void testGetCardinality() {
        SecondaryStructure s = set.getOneOptimalStructure();
        int x = 3;

        assertEquals(x,s.getCardinality());
    }

    @Test(timeout = 10000)
    public final void testGetDotBracketNotation() {
        SecondaryStructure s = set.getOneOptimalStructure();

        assertEquals("GGGAAAUCC\n" + "(((...)))",s.getDotBracketNotation());
    }

    @Test(timeout = 10000)
    public final void testEqualsObject() {
        SecondaryStructure s = new SecondaryStructure("GGGAAAUCC");
        SecondaryStructure s1 = new SecondaryStructure("GGGAAAUCC");

        assertEquals(s,s1);
    }

}
