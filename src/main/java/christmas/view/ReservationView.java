package christmas.view;

import static christmas.view.constants.NoticeMessage.GREET_MESSAGE;
import static christmas.view.constants.NoticeMessage.INPUT_ORDER_MENU_MESSAGE;
import static christmas.view.constants.NoticeMessage.INPUT_RESERVED_VISIT_DATE_MESSAGE;

import christmas.view.io.reader.Reader;
import christmas.view.io.writer.Writer;

public class ReservationView {
    private final Reader reader;
    private final Writer writer;

    public ReservationView(Reader reader, Writer writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public void greet() {
        writer.writeLine(GREET_MESSAGE.value);
    }

    public String inputReservationDay() {
        writer.writeLine(INPUT_RESERVED_VISIT_DATE_MESSAGE.value);
        return reader.readLine();
    }

    public String inputOrderMenu() {
        writer.writeLine(INPUT_ORDER_MENU_MESSAGE.value);
        return reader.readLine();
    }
}
