package christmas.controller;

import static christmas.fixture.ChristmasFixture.createReservation;
import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.menu.constants.Menu;
import christmas.domain.promotion.ChristmasPromotion;
import christmas.domain.reservation.Reservation;
import christmas.service.ChristmasPromotionApplyService;
import christmas.stub.StubWriter;
import christmas.view.PromotionApplyResultView;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

@DisplayName("프로모션 적용 결과 미리 보기")
class PromotionApplyControllerTest {
    private final StubWriter writer = new StubWriter();
    private final PromotionApplyResultView promotionApplyResultView = new PromotionApplyResultView(writer);

    private final ChristmasPromotion christmasPromotion = new ChristmasPromotion();
    private final ChristmasPromotionApplyService christmasPromotionApplyService = new ChristmasPromotionApplyService(
            christmasPromotion);

    private final PromotionApplyController promotionApplyController = new PromotionApplyController(
            promotionApplyResultView, christmasPromotionApplyService);

    private void csvFileSourceTest(int day, String menu, String expected) {
        //given
        Reservation reservation = createReservation(day, menu);
        //then
        promotionApplyController.applyPromotion(reservation);
        //then
        assertThat(writer.getOutput()).contains(expected.split("\n"));
    }

    @CsvFileSource(resources = "/PromotionApplyControllerTest/프로모션_적용최소금액미만_테스트.csv", delimiter = ';', lineSeparator = "/", numLinesToSkip = 1)
    @ParameterizedTest(name = "day={0}, menu={1}")
    void 프로모션_적용최소금액미만이라면_프로모션을_적용하지않는다(int day, String menu, String expected) {
        csvFileSourceTest(day, menu, expected);
    }

    @CsvFileSource(resources = "/PromotionApplyControllerTest/특별할인_테스트.csv", delimiter = ';', lineSeparator = "/", numLinesToSkip = 1)
    @ParameterizedTest(name = "day={0}, menu={1}")
    void 일요일과_크리스마스날에는_특별할인을_적용한다(int day, String menu, String expected) {
        csvFileSourceTest(day, menu, expected);
    }

    @CsvFileSource(resources = "/PromotionApplyControllerTest/평일할인_테스트.csv", delimiter = ';', lineSeparator = "/", numLinesToSkip = 1)
    @ParameterizedTest(name = "day={0}, menu={1}")
    void 평일에는_평일할인을_적용한다(int day, String menu, String expected) {
        csvFileSourceTest(day, menu, expected);
    }

    @CsvFileSource(resources = "/PromotionApplyControllerTest/주말할인_테스트.csv", delimiter = ';', lineSeparator = "/", numLinesToSkip = 1)
    @ParameterizedTest(name = "day={0}, menu={1}")
    void 주말에는_주말할인을_적용한다(int day, String menu, String expected) {
        csvFileSourceTest(day, menu, expected);
    }

    @CsvFileSource(resources = "/PromotionApplyControllerTest/크리스마스디데이할인_테스트.csv", delimiter = ';', lineSeparator = "/", numLinesToSkip = 1)
    @ParameterizedTest(name = "day={0}, menu={1}")
    void 크리스마스당일까지_크리스마스디데이할인을_적용한다(int day, String menu, String expected) {
        csvFileSourceTest(day, menu, expected);
    }

    @CsvFileSource(resources = "/PromotionApplyControllerTest/샴페인증정_테스트.csv", delimiter = ';', lineSeparator = "/", numLinesToSkip = 1)
    @ParameterizedTest(name = "day={0}, menu={1}")
    void 혜택금액이_12만원이상이라면_샴페인을_증정한다(int day, String menu, String expected) {
        csvFileSourceTest(day, menu, expected);
    }

    @CsvFileSource(resources = "/PromotionApplyControllerTest/이벤트뱃지부여_테스트.csv", delimiter = ';', lineSeparator = "/", numLinesToSkip = 1)
    @ParameterizedTest(name = "day={0}, menu={1}")
    void 혜택금액에따라_이벤트배지를_부여한다(int day, String menu, String expected) {
        csvFileSourceTest(day, menu, expected);
    }

    @Test
    void 예시() {
        //given
        Reservation reservation = createReservation(3, List.of(
                        Menu.T_BONE_STEAK, Menu.BBQ_RIB, Menu.CHOCO_CAKE, Menu.ZERO_COLA),
                List.of(1, 1, 2, 1));
        //when
        promotionApplyController.applyPromotion(reservation);
        //then
        assertThat(writer.getOutput()).contains(
                """
                        티본스테이크 1개
                        바비큐립 1개
                        초코케이크 2개
                        제로콜라 1개
                        """,
                "142,000원",
                "샴페인 1개",
                """
                        크리스마스 디데이 할인: -1,200원
                        평일 할인: -4,046원
                        특별 할인: -1,000원
                        증정 이벤트: -25,000원
                        """,
                "-31,246원",
                "135,754원",
                "산타"
        );
    }
}