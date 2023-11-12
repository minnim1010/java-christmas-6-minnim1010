package christmas.view;

import static christmas.view.constants.NoticeMessage.PROMOTION_BENEFIT_PREVIEW_START_MESSAGE;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.menu.constants.MenuItem;
import christmas.domain.promotion.constants.ChristmasPromotionBenefit;
import christmas.domain.promotion.constants.EventBadge;
import christmas.dto.output.BenefitAppliedPriceOutputDto;
import christmas.dto.output.BenefitPriceOutputDto;
import christmas.dto.output.EventBadgeOutputDto;
import christmas.dto.output.GiveawayOutputDto;
import christmas.dto.output.OrderMenuOutputDto;
import christmas.dto.output.PromotionBenefitOutputDto;
import christmas.dto.output.ReservationDateOutputDto;
import christmas.dto.output.TotalOrderPriceOutputDto;
import christmas.stub.StubWriter;
import java.util.EnumMap;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PromotionApplyResultViewTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();

    private final StubWriter writer = new StubWriter();
    private final PromotionApplyResultView promotionApplyResultView = new PromotionApplyResultView(writer);

    private static Stream<Arguments> getEventBadgeArgument() {
        return Stream.of(
                Arguments.of(EventBadge.SANTA,
                        String.format("%s<12월 이벤트 배지>%s산타%s", LINE_SEPARATOR, LINE_SEPARATOR, LINE_SEPARATOR)),
                Arguments.of(EventBadge.NOT_APPLICABLE,
                        String.format("%s<12월 이벤트 배지>%s없음%s", LINE_SEPARATOR, LINE_SEPARATOR, LINE_SEPARATOR))
        );
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
        promotionApplyResultView.outputOrderMenu(orderMenuOutputDto);
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
    void 이벤트혜택_미리보기_시작_출력_테스트() {
        //given
        ReservationDateOutputDto reservationDateOutputDto = new ReservationDateOutputDto(12, 1);
        //when
        promotionApplyResultView.outputPromotionBenefitPreviewStart(reservationDateOutputDto);
        //then
        assertThat(writer.getOutput()).isEqualTo(
                format(PROMOTION_BENEFIT_PREVIEW_START_MESSAGE.value, 12, 1) + LINE_SEPARATOR);
    }

    @Test
    void 할인전_총주문금액_출력_테스트() {
        //given
        int price = 142_000;
        TotalOrderPriceOutputDto totalOrderPriceOutputDto = new TotalOrderPriceOutputDto(price);
        //when
        promotionApplyResultView.outputTotalOrderPrice(totalOrderPriceOutputDto);
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
        GiveawayOutputDto giveawayOutputDto = new GiveawayOutputDto(giveaway, 1);
        //when
        promotionApplyResultView.outputGiveaway(giveawayOutputDto);
        //then
        String expected = """
                                
                <증정 메뉴>
                샴페인 1개
                """;
        assertThat(writer.getOutput()).isEqualTo(expected);
    }

    private static Stream<Arguments> getBenefitPriceArgument() {
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
        BenefitPriceOutputDto benefitPriceOutputDto = new BenefitPriceOutputDto(price);
        //when
        promotionApplyResultView.outputBenefitPrice(benefitPriceOutputDto);
        //then
        assertThat(writer.getOutput()).isEqualTo(expected);
    }

    @Test
    void 할인후_예상결제금액_출력_테스트() {
        //given
        int price = 135_754;
        BenefitAppliedPriceOutputDto benefitAppliedPriceOutputDto = new BenefitAppliedPriceOutputDto(price);
        //when
        promotionApplyResultView.outputBenefitAppliedPrice(benefitAppliedPriceOutputDto);
        //then
        String expected = """    
                                
                <할인 후 예상 결제 금액>
                135,754원
                """;
        assertThat(writer.getOutput()).isEqualTo(expected);
    }

    @Test
    void 혜택내역_출력_테스트() {
        //given
        EnumMap<ChristmasPromotionBenefit, Integer> promotionBenefit = new EnumMap<>(ChristmasPromotionBenefit.class);
        promotionBenefit.put(ChristmasPromotionBenefit.CHRISTMAS_D_DAY_DISCOUNT, 1200);
        promotionBenefit.put(ChristmasPromotionBenefit.WEEKDAY_DISCOUNT, 4046);
        promotionBenefit.put(ChristmasPromotionBenefit.SPECIAL_DISCOUNT, 1000);
        promotionBenefit.put(ChristmasPromotionBenefit.GIVEAWAY, 25000);
        PromotionBenefitOutputDto promotionBenefitOutputDto = new PromotionBenefitOutputDto(promotionBenefit);
        //when
        promotionApplyResultView.outputPromotionBenefitList(promotionBenefitOutputDto);
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

    @MethodSource("getEventBadgeArgument")
    @ParameterizedTest
    void 이벤트뱃지_출력_테스트(EventBadge badge, String expected) {
        //given
        EventBadgeOutputDto eventBadgeOutputDto = new EventBadgeOutputDto(badge);
        //when
        promotionApplyResultView.outputReceivedEventBadge(eventBadgeOutputDto);
        //then
        assertThat(writer.getOutput()).isEqualTo(expected);
    }
}