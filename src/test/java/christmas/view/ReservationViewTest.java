package christmas.view;

import static christmas.view.constants.NoticeMessage.GREET_MESSAGE;
import static christmas.view.constants.NoticeMessage.INPUT_ORDER_MENU_MESSAGE;
import static christmas.view.constants.NoticeMessage.INPUT_RESERVED_VISIT_DATE_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;

import christmas.stub.StubReader;
import christmas.stub.StubWriter;
import org.junit.jupiter.api.Test;

class ReservationViewTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();

    private final StubReader reader = new StubReader();
    private final StubWriter writer = new StubWriter();
    private final ReservationView reservationView = new ReservationView(reader, writer);

    @Test
    void 이벤트_플래너_인사_출력_테스트() {
        //given
        //when
        reservationView.greet();
        //then
        assertThat(writer.getOutput()).isEqualTo(GREET_MESSAGE.value + LINE_SEPARATOR);
    }

    @Test
    void 예약방문날짜_입력_테스트() {
        //given
        String day = "1";
        reader.setInput(day);
        //when
        String reservationDayInput = reservationView.inputReservationDay();
        //then
        assertThat(writer.getOutput()).isEqualTo(INPUT_RESERVED_VISIT_DATE_MESSAGE.value + LINE_SEPARATOR);
        assertThat(reservationDayInput).isEqualTo(day);
    }

    @Test
    void 주문할메뉴_입력_테스트() {
        //given
        String orderMenu = "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1";
        reader.setInput(orderMenu);
        //when
        String orderMenuInput = reservationView.inputOrderMenu();
        //then
        assertThat(writer.getOutput()).isEqualTo(INPUT_ORDER_MENU_MESSAGE.value + LINE_SEPARATOR);
        assertThat(orderMenuInput).isEqualTo(orderMenu);
    }
}