package christmas.service;

import static christmas.fixture.ChristmasFixture.createReservation;
import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.base.Money;
import christmas.domain.menu.constants.Menu;
import christmas.domain.promotion.ChristmasPromotion;
import christmas.domain.promotion.PromotionAppliedResult;
import christmas.domain.promotion.constants.ChristmasPromotionBenefit;
import christmas.domain.promotion.constants.EventBadge;
import christmas.domain.reservation.Reservation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

class ChristmasPromotionApplyServiceTest {
    private final ChristmasPromotion christmasPromotion = new ChristmasPromotion();
    private final ChristmasPromotionApplyService service = new ChristmasPromotionApplyService(christmasPromotion);

    @CsvFileSource(resources = "/ChristmasPromotionApplyServiceTest/프로모션_적용최소금액미만_테스트.csv",
            delimiter = ';', lineSeparator = "/", numLinesToSkip = 1)
    @ParameterizedTest(name = "day={0}, menu={1}")
    void 프로모션_적용최소금액미만이라면_프로모션을_적용하지않는다(int day, String menu) {
        //given
        Reservation reservation = createReservation(day, menu);
        //when
        PromotionAppliedResult promotionAppliedResult = service.applyPromotion(reservation);
        //then
        assertThat(promotionAppliedResult.getDiscountBenefits()).isEmpty();
        assertThat(promotionAppliedResult.getGiveawayBenefit()).isEmpty();
    }

    @CsvFileSource(resources = "/ChristmasPromotionApplyServiceTest/특별할인_테스트.csv", delimiter = ';', lineSeparator = "/", numLinesToSkip = 1)
    @ParameterizedTest(name = "day={0}, menu={1}")
    void 일요일과_크리스마스날에는_특별할인을_적용한다(int day, String menu) {
        //given
        Reservation reservation = createReservation(day, menu);
        //when
        PromotionAppliedResult promotionAppliedResult = service.applyPromotion(reservation);
        //then
        assertThat(promotionAppliedResult.getDiscountBenefits())
                .containsKey(ChristmasPromotionBenefit.SPECIAL_DISCOUNT);
    }

    @CsvFileSource(resources = "/ChristmasPromotionApplyServiceTest/평일할인_테스트.csv", delimiter = ';', lineSeparator = "/", numLinesToSkip = 1)
    @ParameterizedTest(name = "day={0}, menu={1}")
    void 평일에는_평일할인을_적용한다(int day, String menu) {
        //given
        Reservation reservation = createReservation(day, menu);
        //when
        PromotionAppliedResult promotionAppliedResult = service.applyPromotion(reservation);
        //then
        assertThat(promotionAppliedResult.getDiscountBenefits())
                .containsKey(ChristmasPromotionBenefit.WEEKDAY_DISCOUNT);
    }

    @CsvFileSource(resources = "/ChristmasPromotionApplyServiceTest/주말할인_테스트.csv", delimiter = ';', lineSeparator = "/", numLinesToSkip = 1)
    @ParameterizedTest(name = "day={0}, menu={1}")
    void 주말에는_주말할인을_적용한다(int day, String menu) {
        //given
        Reservation reservation = createReservation(day, menu);
        //when
        PromotionAppliedResult promotionAppliedResult = service.applyPromotion(reservation);
        //then
        assertThat(promotionAppliedResult.getDiscountBenefits())
                .containsKey(ChristmasPromotionBenefit.WEEKEND_DISCOUNT);
    }

    @CsvFileSource(resources = "/ChristmasPromotionApplyServiceTest/크리스마스디데이할인_테스트.csv", delimiter = ';', lineSeparator = "/", numLinesToSkip = 1)
    @ParameterizedTest(name = "day={0}, menu={1}")
    void 크리스마스당일까지_크리스마스디데이할인을_적용한다(int day, String menu) {
        //given
        Reservation reservation = createReservation(day, menu);
        //when
        PromotionAppliedResult promotionAppliedResult = service.applyPromotion(reservation);
        //then
        assertThat(promotionAppliedResult.getDiscountBenefits())
                .containsKey(ChristmasPromotionBenefit.CHRISTMAS_D_DAY_DISCOUNT);
    }

    @CsvFileSource(resources = "/ChristmasPromotionApplyServiceTest/샴페인증정_테스트.csv", delimiter = ';', lineSeparator = "/", numLinesToSkip = 1)
    @ParameterizedTest(name = "day={0}, menu={1}")
    void 혜택금액이_12만원이상이라면_샴페인을_증정한다(int day, String menu) {
        //given
        Reservation reservation = createReservation(day, menu);
        //when
        PromotionAppliedResult promotionAppliedResult = service.applyPromotion(reservation);
        //then
        assertThat(promotionAppliedResult.getGiveawayBenefit()).isPresent();
        assertThat(promotionAppliedResult.getGiveawayBenefit().get().menu()).isEqualTo(Menu.CHAMPAGNE);
        assertThat(promotionAppliedResult.getGiveawayBenefit().get().count()).isEqualTo(1);
    }

    @CsvFileSource(resources = "/ChristmasPromotionApplyServiceTest/이벤트뱃지부여_테스트.csv", delimiter = ';', lineSeparator = "/", numLinesToSkip = 1)
    @ParameterizedTest(name = "day={0}, price={2}")
    void 혜택금액에따라_이벤트배지를_부여한다(int day, String menu, int totalBenefitPriceAmount, String badgeName) {
        //given
        Reservation reservation = createReservation(day, menu);
        Money totalBenefitPrice = Money.valueOf(totalBenefitPriceAmount);
        //when
        EventBadge eventBadge = service.getEventBadge(reservation, totalBenefitPrice);
        //then
        assertThat(eventBadge.getName()).isEqualTo(badgeName);
    }
}