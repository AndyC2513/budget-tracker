package persistance;

import model.*;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    protected void checkSub(String name, double price, double apparentPrice, boolean paid, Subscription sub) {
        assertEquals(name, sub.getName());
        assertEquals(price, sub.getPrice());
        assertEquals(apparentPrice, sub.getApparentPrice());
        assertEquals(paid, sub.isPaid());
    }
}
