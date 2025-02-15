package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionListTest {
    private SubscriptionList testSubList;
    private Subscription testSub1;
    private Subscription testSub2;
    private Subscription testSub3;
    private Subscription testSub4;

    @BeforeEach
    void runBefore() {
        testSubList = new SubscriptionList(1000,1000,1000);
        testSub1 = new Subscription("Netflix", 28.99, 28.99, false);
        testSub2 = new Subscription("Youtube", 18.99, 18.99, false);
        testSub3 = new Subscription("Big", 100000, 100000, false);
        testSub4 = new Subscription("Small", 0.1, 0.1, false);
    }

    @Test
    void testConstructor() {
        List<Subscription> expected = new ArrayList<>();
        assertEquals(0, testSubList.getNumEntSubs());
        assertEquals(0, testSubList.getNumLivSubs());
        assertEquals(0, testSubList.getNumAcSubs());
        assertEquals(1000.0, testSubList.getEntBudget());
        assertEquals(1000.0, testSubList.getLivBudget());
        assertEquals(1000.0, testSubList.getAcBudget());
        assertEquals(expected, testSubList.getListOfEntSubs());
        assertEquals(expected, testSubList.getListOfLivSubs());
        assertEquals(expected, testSubList.getListOfAcSubs());
    }

    @Test
    void testAddSub(){
        assertEquals(0, testSubList.getNumEntSubs());
        assertEquals(0, testSubList.getNumLivSubs());
        assertEquals(0, testSubList.getNumAcSubs());

        List<Subscription> expected = new ArrayList<>();
        expected.add(testSub1);

        testSubList.addEntSub(testSub1);
        assertEquals(expected, testSubList.getListOfEntSubs());
        assertEquals(1, testSubList.getNumEntSubs());

        testSubList.addLivSub(testSub1);
        assertEquals(expected, testSubList.getListOfLivSubs());
        assertEquals(1, testSubList.getNumLivSubs());

        testSubList.addAcSub(testSub1);
        assertEquals(expected, testSubList.getListOfAcSubs());
        assertEquals(1, testSubList.getNumAcSubs());

        expected.add(testSub2);

        testSubList.addEntSub(testSub2);
        assertEquals(expected, testSubList.getListOfEntSubs());
        assertEquals(2,testSubList.getNumEntSubs());

        testSubList.addLivSub(testSub2);
        assertEquals(expected, testSubList.getListOfLivSubs());
        assertEquals(2,testSubList.getNumLivSubs());

        testSubList.addAcSub(testSub2);
        assertEquals(expected, testSubList.getListOfAcSubs());
        assertEquals(2,testSubList.getNumAcSubs());
    }

    @Test
    void testRemoveSub() {
        assertEquals(0, testSubList.getNumEntSubs());
        assertEquals(0, testSubList.getNumLivSubs());
        assertEquals(0, testSubList.getNumAcSubs());

        List<Subscription> expected = new ArrayList<>();
        expected.add(testSub1);

        testSubList.addEntSub(testSub1);
        assertEquals(expected, testSubList.getListOfEntSubs());
        assertEquals(1, testSubList.getNumEntSubs());

        testSubList.addLivSub(testSub1);
        assertEquals(expected, testSubList.getListOfLivSubs());
        assertEquals(1, testSubList.getNumLivSubs());

        testSubList.addAcSub(testSub1);
        assertEquals(expected, testSubList.getListOfAcSubs());
        assertEquals(1, testSubList.getNumAcSubs());

        expected.remove(testSub1);

        testSubList.removeEntSub(testSub1);
        assertEquals(0, testSubList.getNumEntSubs());
        assertEquals(expected, testSubList.getListOfEntSubs());

        testSubList.removeLivSub(testSub1);
        assertEquals(0, testSubList.getNumLivSubs());
        assertEquals(expected, testSubList.getListOfLivSubs());

        testSubList.removeAcSub(testSub1);
        assertEquals(0, testSubList.getNumAcSubs());
        assertEquals(expected, testSubList.getListOfAcSubs());
    }

    @Test
    void testPayForStub() {
        assertEquals(0, testSubList.getNumEntSubs());
        assertEquals(0, testSubList.getNumLivSubs());
        assertEquals(0, testSubList.getNumAcSubs());

        testSubList.addEntSub(testSub1);
        assertEquals(1, testSubList.getNumEntSubs());

        testSubList.addLivSub(testSub2);
        assertEquals(1, testSubList.getNumLivSubs());

        testSubList.addAcSub(testSub3);
        assertEquals(1, testSubList.getNumAcSubs());
        testSubList.addAcSub(testSub2);
        assertEquals(2, testSubList.getNumAcSubs());

        assertTrue(testSubList.payForEntSub(testSub1));
        assertFalse(testSubList.payForEntSub(testSub1));
        assertTrue(testSubList.payForLivSub(testSub2));
        assertFalse(testSubList.payForLivSub(testSub2));
        assertFalse(testSubList.payForAcSub(testSub3));
        assertFalse(testSubList.payForLivSub(testSub1));
        assertFalse(testSubList.payForAcSub(testSub1));

        assertFalse(testSubList.payForEntSub(testSub2));
        assertFalse(testSubList.payForLivSub(testSub2));
        assertFalse(testSubList.payForAcSub(testSub2));

        assertEquals(971.01, testSubList.getEntBudget());
        assertEquals(0, testSub2.getApparentPrice());
        assertEquals(18.99, testSub2.getPrice());
        assertTrue(testSub2.isPaid());
        assertEquals(0, testSub1.getApparentPrice());
        assertEquals(28.99, testSub1.getPrice());
        assertTrue(testSub1.isPaid());

        assertEquals(981.01, testSubList.getLivBudget());
        assertEquals(0, testSub2.getApparentPrice());
        assertEquals(18.99, testSub2.getPrice());
        assertTrue(testSub2.isPaid());
        assertEquals(0, testSub1.getApparentPrice());
        assertEquals(28.99, testSub1.getPrice());
        assertTrue(testSub1.isPaid());

        assertEquals(1000.0, testSubList.getAcBudget());
        assertEquals(0, testSub2.getApparentPrice());
        assertEquals(18.99, testSub2.getPrice());
        assertTrue(testSub2.isPaid());
        assertEquals(0, testSub1.getApparentPrice());
        assertEquals(28.99, testSub1.getPrice());
        assertTrue(testSub1.isPaid());

        testSubList.addEntSub(testSub2);
        assertFalse(testSubList.payForEntSub(testSub2));
        assertEquals(0, testSub2.getApparentPrice());
        assertEquals(18.99, testSub2.getPrice());

        testSubList.addLivSub(testSub2);
        assertFalse(testSubList.payForLivSub(testSub2));
        assertEquals(0, testSub2.getApparentPrice());
        assertEquals(18.99, testSub2.getPrice());

        testSubList.addAcSub(testSub2);
        assertFalse(testSubList.payForAcSub(testSub2));
        assertEquals(0, testSub2.getApparentPrice());
        assertEquals(18.99, testSub2.getPrice());

        testSubList.addEntSub(testSub3);
        testSubList.addLivSub(testSub3);
        testSubList.addAcSub(testSub3);

        assertFalse(testSubList.payForEntSub(testSub3));
        assertFalse(testSubList.payForLivSub(testSub3));
        assertFalse(testSubList.payForAcSub(testSub3));

        testSubList.addAcSub(testSub4);
        assertTrue(testSubList.payForAcSub(testSub4));
        assertEquals(0, testSub4.getApparentPrice());
        assertEquals(0.1, testSub4.getPrice());
    }

    @Test
    void testMultipleDeposits() {
        assertEquals(1000, testSubList.getEntBudget());
        testSubList.addEntBudget(250.8);
        testSubList.addEntBudget(22.8);
        assertEquals(1273.6, testSubList.getEntBudget());

        assertEquals(1000, testSubList.getLivBudget());
        testSubList.addLivBudget(250.8);
        testSubList.addLivBudget(22.8);
        assertEquals(1273.6, testSubList.getLivBudget());

        assertEquals(1000, testSubList.getAcBudget());
        testSubList.addAcBudget(250.8);
        testSubList.addAcBudget(22.8);
        assertEquals(1273.6, testSubList.getAcBudget());
    }
}