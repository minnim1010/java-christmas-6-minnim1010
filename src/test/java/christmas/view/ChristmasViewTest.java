package christmas.view;

import static christmas.view.constants.NoticeMessage.GREET_MESSAGE;
import static christmas.view.constants.NoticeMessage.INPUT_RESERVED_VISIT_DATE_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;

import christmas.stub.StubReader;
import christmas.stub.StubWriter;
import org.junit.jupiter.api.Test;

class ChristmasViewTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();

    private final StubReader reader = new StubReader();
    private final StubWriter writer = new StubWriter();
    private final ChristmasView christmasView = new ChristmasView(reader, writer);

    @Test
    void 이벤트_플래너_인사_출력_테스트() {
        //given
        //when
        christmasView.greet();
        //then
        assertThat(writer.getOutput()).isEqualTo(GREET_MESSAGE.value + LINE_SEPARATOR);
    }

    @Test
    void 예약방문날짜_입력_테스트() {
        //given
        String day = "1";
        reader.setInput(day);
        //when
        christmasView.inputReservedVisitDay();
        //then
        assertThat(writer.getOutput()).isEqualTo(INPUT_RESERVED_VISIT_DATE_MESSAGE.value + LINE_SEPARATOR);
    }
}