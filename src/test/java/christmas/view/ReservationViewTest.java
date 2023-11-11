package christmas.view;

import static christmas.view.constants.NoticeMessage.GREET_MESSAGE;
import static christmas.view.constants.NoticeMessage.INPUT_ORDER_MENU_MESSAGE;
import static christmas.view.constants.NoticeMessage.INPUT_RESERVED_VISIT_DATE_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;

import christmas.dto.input.OrderMenuInputDto;
import christmas.dto.input.ReservationDayInputDto;
import christmas.stub.StubReader;
import christmas.stub.StubWriter;
import org.junit.jupiter.api.Test;

class ReservationViewTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();

    private final StubReader reader = new StubReader();
    private final StubWriter writer = new StubWriter();
    private final ReservationView christmasView = new ReservationView(reader, writer);

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
        ReservationDayInputDto reservedVisitDateDto = christmasView.inputReservationDay();
        //then
        assertThat(writer.getOutput()).isEqualTo(INPUT_RESERVED_VISIT_DATE_MESSAGE.value + LINE_SEPARATOR);
        assertThat(reservedVisitDateDto.reservationDay()).isEqualTo(day);
    }

    @Test
    void 주문할메뉴_입력_테스트() {
        //given
        String orderMenu = "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1";
        reader.setInput(orderMenu);
        //when
        OrderMenuInputDto orderMenuInputDto = christmasView.inputOrderMenu();
        //then
        assertThat(writer.getOutput()).isEqualTo(INPUT_ORDER_MENU_MESSAGE.value + LINE_SEPARATOR);
        assertThat(orderMenuInputDto.orderMenu()).isEqualTo(orderMenu);
    }
}