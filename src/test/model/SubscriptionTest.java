package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SubscriptionTest {
    private Subscription testSub;

    @BeforeEach
    void runBefore() {
        testSub = new Subscription("Netflix", 28.99, 28.99, false);
    }

    @Test
    void testConstructor() {
        assertEquals(28.99, testSub.getApparentPrice());
        assertEquals(28.99, testSub.getPrice());
        assertEquals("Netflix", testSub.getName());
        assertFalse(testSub.isPaid());
    }
}
