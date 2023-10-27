package persistance;

import model.Subscription;
import model.SubscriptionList;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            SubscriptionList sl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptySubscriptionList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptySubscriptionList.json");
        try {
            SubscriptionList sl = reader.read();
            ArrayList<Subscription> expected = new ArrayList<>();
            assertEquals(expected, sl.getListOfEntSubs());
            assertEquals(expected, sl.getListOfLivSubs());
            assertEquals(expected, sl.getListOfAcSubs());
            assertEquals(0, sl.getEntBudget());
            assertEquals(0, sl.getLivBudget());
            assertEquals(0, sl.getAcBudget());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralSubscriptionList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralSubscriptionList.json");
        try {
            SubscriptionList sl = reader.read();
            ArrayList<Subscription> ent = sl.getListOfEntSubs();
            ArrayList<Subscription> liv = sl.getListOfLivSubs();
            ArrayList<Subscription> ac = sl.getListOfAcSubs();
            checkSub("Netflix", 28.99, 28.99, false, ent.get(0));
            checkSub("Rent", 1237.99, 1237.99, false, liv.get(0));
            checkSub("Textbook", 22, 22, false, ac.get(0));
            assertEquals(1, sl.getNumEntSubs());
            assertEquals(1, sl.getNumLivSubs());
            assertEquals(1, sl.getNumAcSubs());
            assertEquals(500, sl.getEntBudget());
            assertEquals(10000, sl.getLivBudget());
            assertEquals(500, sl.getAcBudget());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
