package christmas.view;

import static christmas.view.constants.NoticeMessage.GREET_MESSAGE;
import static christmas.view.constants.NoticeMessage.INPUT_ORDER_MENU_MESSAGE;
import static christmas.view.constants.NoticeMessage.INPUT_RESERVED_VISIT_DATE_MESSAGE;

import christmas.dto.input.OrderMenuInputDto;
import christmas.dto.input.ReservationDayInputDto;
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

    public ReservationDayInputDto inputReservationDay() {
        writer.writeLine(INPUT_RESERVED_VISIT_DATE_MESSAGE.value);
        return new ReservationDayInputDto(reader.readLine());
    }

    public OrderMenuInputDto inputOrderMenu() {
        writer.writeLine(INPUT_ORDER_MENU_MESSAGE.value);
        return new OrderMenuInputDto(reader.readLine());
    }
}
