package christmas.view;

import static christmas.view.constants.NoticeMessage.INPUT_RESERVED_VISIT_DATE_MESSAGE;

import christmas.dto.input.ReservedVisitDateDto;
import christmas.view.io.reader.Reader;
import christmas.view.io.writer.Writer;

public class ChristmasView {
    private final Reader reader;
    private final Writer writer;

    public ChristmasView(Reader reader, Writer writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public ReservedVisitDateDto inputReservedVisitDay() {
        writer.writeLine(INPUT_RESERVED_VISIT_DATE_MESSAGE.value);
        return new ReservedVisitDateDto(reader.readLine());
    }

}
