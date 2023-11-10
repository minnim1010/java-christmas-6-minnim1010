package christmas.view;

import static christmas.view.constants.NoticeMessage.GREET_MESSAGE;
import static christmas.view.constants.NoticeMessage.INPUT_ORDER_MENU_MESSAGE;
import static christmas.view.constants.NoticeMessage.INPUT_RESERVED_VISIT_DATE_MESSAGE;
import static christmas.view.constants.NoticeMessage.PROMOTION_BENEFIT_PREVIEW_START_MESSAGE;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.base.Money;
import christmas.domain.constants.MenuItem;
import christmas.dto.input.OrderMenuInputDto;
import christmas.dto.input.ReservedVisitDateInputDto;
import christmas.dto.output.OrderMenuOutputDto;
import christmas.dto.output.OrderPriceOutputDto;
import christmas.dto.output.ReservedVisitDateOutputDto;
import christmas.stub.StubReader;
import christmas.stub.StubWriter;
import java.time.LocalDate;
import java.util.EnumMap;
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
        ReservedVisitDateInputDto reservedVisitDateDto = christmasView.inputReservedVisitDay();
        //then
        assertThat(writer.getOutput()).isEqualTo(INPUT_RESERVED_VISIT_DATE_MESSAGE.value + LINE_SEPARATOR);
        assertThat(reservedVisitDateDto.reservedVisitDate()).isEqualTo(day);
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

    @Test
    void 이벤트혜택_미리보기_시작_출력_테스트() {
        //given
        ReservedVisitDateOutputDto reservedVisitDateOutputDto = new ReservedVisitDateOutputDto(
                LocalDate.of(2023, 12, 1));
        //when
        christmasView.outputPromotionBenefitPreviewStart(reservedVisitDateOutputDto);
        //then
        assertThat(writer.getOutput()).isEqualTo(
                format(PROMOTION_BENEFIT_PREVIEW_START_MESSAGE.value, 12, 1) + LINE_SEPARATOR);
    }

    @Test
    void 주문한_메뉴_출력_테스트() {
        //given
        EnumMap<MenuItem, Integer> orderMenu = new EnumMap<>(MenuItem.class);
        orderMenu.put(MenuItem.T_BONE_STEAK, 1);
        orderMenu.put(MenuItem.BBQ_RIB, 1);
        orderMenu.put(MenuItem.CHOCO_CAKE, 2);
        orderMenu.put(MenuItem.ZERO_COLA, 1);
        OrderMenuOutputDto orderMenuOutputDto = new OrderMenuOutputDto(orderMenu);
        //when
        christmasView.outputOrderMenu(orderMenuOutputDto);
        //then
        String expected = """    
                                
                <주문 메뉴>
                티본스테이크 1개
                바비큐립 1개
                초코케이크 2개
                제로콜라 1개
                """;
        assertThat(writer.getOutput()).isEqualTo(expected);
    }

    @Test
    void 할인전_총주문금액_출력_테스트() {
        //given
        int price = 142_000;
        OrderPriceOutputDto orderPriceOutputDto = new OrderPriceOutputDto(Money.valueOf(price));
        //when
        christmasView.outputOrderPrice(orderPriceOutputDto);
        //then
        String expected = """    
                                
                <할인 전 총주문 금액>
                142,000원
                """;
        assertThat(writer.getOutput()).isEqualTo(expected);
    }
}