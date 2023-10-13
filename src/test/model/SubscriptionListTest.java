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

    @BeforeEach
    void runBefore() {
        testSubList = new SubscriptionList();
        testSub1 = new Subscription("Netflix", 28.99);
        testSub2 = new Subscription("Youtube", 18.99);
    }

    @Test
    void testConstructor() {
        List<Subscription> expected = new ArrayList<>();
        assertEquals(0, testSubList.getNumSubs());
        assertEquals(1000.0, testSubList.getBudget());
        assertEquals(expected, testSubList.getListOfSubs());
    }

    @Test
    void testAddSub(){
        assertEquals(0, testSubList.getNumSubs());
        testSubList.addSub(testSub1);
        List<Subscription> expected = new ArrayList<>();
        expected.add(testSub1);
        assertEquals(expected, testSubList.getListOfSubs());
        assertEquals(1, testSubList.getNumSubs());
        testSubList.addSub(testSub2);
        expected.add(testSub2);
        assertEquals(expected, testSubList.getListOfSubs());
        assertEquals(2,testSubList.getNumSubs());
    }

    @Test
    void testRemoveSub() {
        assertEquals(0, testSubList.getNumSubs());
        testSubList.addSub(testSub1);
        List<Subscription> expected = new ArrayList<>();
        expected.add(testSub1);
        assertEquals(expected, testSubList.getListOfSubs());
        assertEquals(1, testSubList.getNumSubs());
        testSubList.removeSub(testSub1);
        expected.remove(testSub1);
        assertEquals(0, testSubList.getNumSubs());
        assertEquals(expected, testSubList.getListOfSubs());
    }

    @Test
    void testPayForStub() {
        assertEquals(0, testSubList.getNumSubs());
        testSubList.addSub(testSub1);
        List<Subscription> expected = new ArrayList<>();
        expected.add(testSub1);
        assertEquals(expected, testSubList.getListOfSubs());
        assertEquals(1, testSubList.getNumSubs());
        testSubList.payForSub(testSub1);
        testSubList.payForSub(testSub2);
        assertEquals(971.01, testSubList.getBudget());
        assertEquals(18.99, testSub2.getApparentPrice());
        assertEquals(18.99, testSub2.getPrice());
        assertFalse(testSub2.isPaid());
        assertEquals(0, testSub1.getApparentPrice());
        assertEquals(28.99, testSub1.getPrice());
        assertTrue(testSub1.isPaid());
        testSubList.addSub(testSub2);
        testSubList.payForSub(testSub2);
        assertEquals(0, testSub2.getApparentPrice());
        assertEquals(18.99, testSub2.getPrice());
    }

    @Test
    void testMultipleDeposits() {
        assertEquals(1000, testSubList.getBudget());
        testSubList.addBudget(250.8);
        testSubList.addBudget(22.8);
        assertEquals(1273.6, testSubList.getBudget());
    }
}