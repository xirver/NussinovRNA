package it.unicam.cs.asdl1819.project1;

import static org.junit.Assert.*;

import jdk.internal.org.objectweb.asm.tree.LineNumberNode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class NussinovFolderTest {
    private NussinovFolder test;

    private int[][] matrix;
    @Before
    public void setUp() throws Exception {
        test = new NussinovFolder("GGGAAAUCC");
        matrix = new int[][]{
                {0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,1,2,3,0},
                {0,0,0,0,0,0,0,1,2,3,0},
                {0,0,0,0,0,0,0,1,2,2,0},
                {0,0,0,0,0,0,0,1,1,1,0},
                {0,0,0,0,0,0,0,1,1,1,0},
                {0,0,0,0,0,0,0,1,1,1,0},
                {0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0},
        };
        test.fold();
        test.traceback(1,9);
    }

    @Test(timeout = 10000, expected = NullPointerException.class)
    public final void testNussinovFolderNull() {    //test costruttore per stringa nulla
        String primarySequence = null;

        NussinovFolder n = new NussinovFolder(primarySequence);
    }

    @Test(timeout = 10000, expected = IllegalArgumentException.class)    //test costruttore per caratteri non validi
    public final void testNussinovFolderString(){
        NussinovFolder n1 = new NussinovFolder("GGGAASUCC");
    }

    @Test(timeout = 10000)
    public final void testGetName() {
        assertEquals("NussinovFolder",test.getName());
    }

    @Test(timeout = 10000)
    public final void testGetSequence() {
        assertEquals("GGGAAAUCC",test.getSequence());
    }

    @Test(timeout = 10000, expected = IllegalStateException.class)
    public final void testGetOneOptimalStructure() {
        //test getoneoptimalstructure
        NussinovFolder prova = new NussinovFolder("GGGAAAUCC");
        prova.fold();
        prova.traceback(1,9);
        SecondaryStructure s = prova.getOneOptimalStructure();
        assertEquals("{(3, 7), (2, 8), (1, 9)}", s.toString());

        //test eccezione
        NussinovFolder test_eccezione = new NussinovFolder("GGGAAAUCC");
        SecondaryStructure test = test_eccezione.getOneOptimalStructure();

    }

    @Test(timeout = 10000)
    public final void testFold() {
        assertArrayEquals(matrix,test.getDp());
    }


    @Test(timeout = 10000)
    public final void testIsFolded() {
        assertTrue(test.isFolded());
    }

}
