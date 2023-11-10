package christmas.view;

import static christmas.view.constants.NoticeMessage.GREET_MESSAGE;
import static christmas.view.constants.NoticeMessage.INPUT_ORDER_MENU_MESSAGE;
import static christmas.view.constants.NoticeMessage.INPUT_RESERVED_VISIT_DATE_MESSAGE;
import static christmas.view.constants.NoticeMessage.PROMOTION_BENEFIT_PREVIEW_START_MESSAGE;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.base.Money;
import christmas.domain.constants.ChristmasPromotionEvent;
import christmas.domain.constants.MenuItem;
import christmas.dto.input.OrderMenuInputDto;
import christmas.dto.input.ReservedVisitDateInputDto;
import christmas.dto.output.BenefitAppliedPriceOutputDto;
import christmas.dto.output.BenefitPriceOutputDto;
import christmas.dto.output.GiveawayMenuOutputDto;
import christmas.dto.output.OrderMenuOutputDto;
import christmas.dto.output.OrderPriceOutputDto;
import christmas.dto.output.PromotionBenefitOutputDto;
import christmas.dto.output.ReservedVisitDateOutputDto;
import christmas.stub.StubReader;
import christmas.stub.StubWriter;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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

    @Test
    void 증정메뉴_출력_테스트() {
        //given
        MenuItem giveaway = MenuItem.CHAMPAGNE;
        GiveawayMenuOutputDto giveawayMenuOutputDto = new GiveawayMenuOutputDto(giveaway, 1);
        //when
        christmasView.outputGiveawayMenu(giveawayMenuOutputDto);
        //then
        String expected = """
                                
                <증정 메뉴>
                샴페인 1개
                """;
        assertThat(writer.getOutput()).isEqualTo(expected);
    }

    @Test
    void 혜택내역_출력_테스트() {
        //given
        EnumMap<ChristmasPromotionEvent, Money> promotionBenefit = new EnumMap<>(ChristmasPromotionEvent.class);
        promotionBenefit.put(ChristmasPromotionEvent.CHRISTMAS_D_DAY_DISCOUNT, Money.valueOf(1200));
        promotionBenefit.put(ChristmasPromotionEvent.WEEKDAY_DISCOUNT, Money.valueOf(4046));
        promotionBenefit.put(ChristmasPromotionEvent.SPECIAL_DISCOUNT, Money.valueOf(1000));
        promotionBenefit.put(ChristmasPromotionEvent.GIVEAWAY, Money.valueOf(25000));
        PromotionBenefitOutputDto promotionBenefitOutputDto = new PromotionBenefitOutputDto(promotionBenefit);
        //when
        christmasView.outputPromotionBenefitList(promotionBenefitOutputDto);
        //then
        String expected = """
                         
                <혜택 내역>
                크리스마스 디데이 할인: -1,200원
                평일 할인: -4,046원
                특별 할인: -1,000원
                증정 이벤트: -25,000원
                """;
        assertThat(writer.getOutput()).isEqualTo(expected);
    }


    static Stream<Arguments> getBenefitPriceArgument() {
        return Stream.of(
                Arguments.of(31246,
                        String.format("%s<총혜택 금액>%s-31,246원%s", LINE_SEPARATOR, LINE_SEPARATOR, LINE_SEPARATOR)),
                Arguments.of(0,
                        String.format("%s<총혜택 금액>%s0원%s", LINE_SEPARATOR, LINE_SEPARATOR, LINE_SEPARATOR))
        );
    }

    @MethodSource("getBenefitPriceArgument")
    @ParameterizedTest
    void 혜택금액_출력_테스트(int price, String expected) {
        //given
        BenefitPriceOutputDto benefitPriceOutputDto = new BenefitPriceOutputDto(Money.valueOf(price));
        //when
        christmasView.outputBenefitPrice(benefitPriceOutputDto);
        //then
        assertThat(writer.getOutput()).isEqualTo(expected);
    }

    @Test
    void 할인후_예상결제금액_출력_테스트() {
        //given
        int price = 135_754;
        BenefitAppliedPriceOutputDto benefitAppliedPriceOutputDto = new BenefitAppliedPriceOutputDto(
                Money.valueOf(price));
        //when
        christmasView.outputBenefitAppliedPrice(benefitAppliedPriceOutputDto);
        //then
        String expected = """    
                                
                <할인 후 예상 결제 금액>
                135,754원
                """;
        assertThat(writer.getOutput()).isEqualTo(expected);
    }
}