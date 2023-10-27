package persistance;

import model.*;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptySubscriptionList() {
        try {
            SubscriptionList sl = new SubscriptionList(0,0,0);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptySubscriptionList.json");
            writer.open();
            writer.write(sl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptySubscriptionList.json");
            sl = reader.read();
            ArrayList<Subscription> expected = new ArrayList<>();
            assertEquals(expected, sl.getListOfEntSubs());
            assertEquals(expected, sl.getListOfLivSubs());
            assertEquals(expected, sl.getListOfAcSubs());
            assertEquals(0, sl.getEntBudget());
            assertEquals(0, sl.getLivBudget());
            assertEquals(0, sl.getAcBudget());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            SubscriptionList sl = new SubscriptionList(500,10000,500);
            sl.addEntSub(new Subscription("Netflix", 28.99, 28.99, false));
            sl.addLivSub(new Subscription("Rent", 1237.99, 1237.99, false));
            sl.addAcSub(new Subscription("Textbook", 22, 22, false));
            JsonWriter writer = new JsonWriter("./data/testReaderGeneralSubscriptionList.json");
            writer.open();
            writer.write(sl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testReaderGeneralSubscriptionList.json");
            sl = reader.read();
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
            fail("Exception should not have been thrown");
        }
    }
}
