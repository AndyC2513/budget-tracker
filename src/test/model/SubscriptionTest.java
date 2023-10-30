package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SubscriptionTest {
    private Subscription testSub;
    private Subscription testSub2;

    @BeforeEach
    void runBefore() {
        testSub = new Subscription("Netflix", 28.99, 28.99, false);
        testSub2 = new Subscription("Spotify", 18.99, 0, true);
    }

    @Test
    void testSubscription() {
        assertEquals(28.99, testSub.getApparentPrice());
        assertEquals(28.99, testSub.getPrice());
        assertEquals("Netflix", testSub.getName());
        assertFalse(testSub.isPaid());
        assertEquals(0, testSub2.getApparentPrice());
        assertEquals(18.99, testSub2.getPrice());
        assertEquals("Spotify", testSub2.getName());
        assertTrue(testSub2.isPaid());
        testSub2.resetPrice();
        assertFalse(testSub2.isPaid());
    }
}
