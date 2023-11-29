package ui;

import model.Event;
import model.EventLog;

public class StringPrinter implements LogPrinter {
    private StringBuilder sw;

    // EFFECTS: creates a new string builder
    public StringPrinter() {
        sw = new StringBuilder();
    }

    @Override
    // EFFECTS: converts all the events to string
    public void printLog(EventLog el) {
        for (Event next : el) {
            sw.append(next.toString());
            sw.append("\n\n");
        }
        System.out.println(sw.toString());
    }
}
