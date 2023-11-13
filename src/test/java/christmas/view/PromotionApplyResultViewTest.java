package christmas.view;

import static christmas.view.constants.NoticeMessage.PROMOTION_BENEFIT_PREVIEW_START_MESSAGE;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.menu.constants.Menu;
import christmas.domain.promotion.constants.EventBadge;
import christmas.dto.BenefitAmountOutputDto;
import christmas.dto.BenefitAppliedPriceOutputDto;
import christmas.dto.EventBadgeOutputDto;
import christmas.dto.GiveawayOutputDto;
import christmas.dto.OrderMenuOutputDto;
import christmas.dto.PromotionBenefitOutputDto;
import christmas.dto.ReservationDateOutputDto;
import christmas.dto.TotalOrderPriceOutputDto;
import christmas.stub.StubWriter;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;
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
        EnumMap<Menu, Integer> orderMenu = new EnumMap<>(Menu.class);
        orderMenu.put(Menu.T_BONE_STEAK, 1);
        orderMenu.put(Menu.BBQ_RIB, 1);
        orderMenu.put(Menu.CHOCO_CAKE, 2);
        orderMenu.put(Menu.ZERO_COLA, 1);
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
        ReservationDateOutputDto reservationDateOutputDto = new ReservationDateOutputDto(1);
        //when
        promotionApplyResultView.outputPromotionBenefitPreviewStart(reservationDateOutputDto);
        //then
        assertThat(writer.getOutput()).isEqualTo(
                format(PROMOTION_BENEFIT_PREVIEW_START_MESSAGE.value, 1) + LINE_SEPARATOR);
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
        Map<Menu, Integer> giveaways = new LinkedHashMap<>();
        giveaways.put(Menu.CHAMPAGNE, 1);
        GiveawayOutputDto giveawayOutputDto = new GiveawayOutputDto(giveaways);
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
        BenefitAmountOutputDto benefitAmountOutputDto = new BenefitAmountOutputDto(price);
        //when
        promotionApplyResultView.outputBenefitAmount(benefitAmountOutputDto);
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
        Map<String, Integer> promotionBenefit = new LinkedHashMap<>();
        promotionBenefit.put("크리스마스 디데이 할인", 1200);
        promotionBenefit.put("평일 할인", 4046);
        promotionBenefit.put("특별 할인", 1000);
        promotionBenefit.put("증정 이벤트", 25000);
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