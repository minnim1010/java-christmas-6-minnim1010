package christmas.controller;

import christmas.domain.base.Money;
import christmas.domain.base.ReservationDate;
import christmas.domain.menu.MenuItem;
import christmas.domain.menu.OrderMenu;
import christmas.domain.menu.constants.Menu;
import christmas.domain.promotion.PromotionAppliedResult;
import christmas.domain.promotion.constants.ChristmasPromotionBenefit;
import christmas.domain.promotion.constants.EventBadge;
import christmas.domain.reservation.Reservation;
import christmas.dto.output.BenefitAppliedPriceOutputDto;
import christmas.dto.output.BenefitPriceOutputDto;
import christmas.dto.output.EventBadgeOutputDto;
import christmas.dto.output.GiveawayOutputDto;
import christmas.dto.output.OrderMenuOutputDto;
import christmas.dto.output.PromotionBenefitOutputDto;
import christmas.dto.output.ReservationDateOutputDto;
import christmas.dto.output.TotalOrderPriceOutputDto;
import christmas.service.ChristmasPromotionService;
import christmas.view.PromotionApplyResultView;
import java.util.EnumMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class PromotionApplyController {
    private final PromotionApplyResultView promotionApplyResultView;
    private final ChristmasPromotionService christmasPromotionService;

    public PromotionApplyController(PromotionApplyResultView promotionApplyResultView,
                                    ChristmasPromotionService christmasPromotionService) {
        this.promotionApplyResultView = promotionApplyResultView;
        this.christmasPromotionService = christmasPromotionService;
    }

    public void applyPromotion(Reservation reservation) {
        PromotionAppliedResult promotionAppliedResult = christmasPromotionService.applyPromotion(reservation);
        EventBadge receivedBadge = christmasPromotionService.getEventBadge(reservation,
                promotionAppliedResult.getTotalBenefitPrice());

        printPromotionBenefitPreviewStart(reservation.getReservationDate());

        printOrderMenu(reservation.getOrderMenu());

        printTotalOrderPrice(reservation.getTotalPrice());

        printGiveaway(promotionAppliedResult.getGiveawayBenefit());

        printAppliedPromotionBenefits(promotionAppliedResult);

        printBenefitPrice(promotionAppliedResult.getTotalBenefitPrice());

        printBenefitAppliedPrice(reservation.getTotalPrice(), promotionAppliedResult.getTotalDiscountPrice());

        printReceivedEventBadge(receivedBadge);
    }

    private void printPromotionBenefitPreviewStart(ReservationDate reservationDate) {
        ReservationDateOutputDto reservationDateOutputDto = new ReservationDateOutputDto(reservationDate.getMonth(),
                reservationDate.getDay());
        promotionApplyResultView.outputPromotionBenefitPreviewStart(reservationDateOutputDto);
    }

    private void printOrderMenu(OrderMenu orderMenu) {
        OrderMenuOutputDto orderMenuOutputDto = new OrderMenuOutputDto(
                (EnumMap<Menu, Integer>) orderMenu.getItems());
        promotionApplyResultView.outputOrderMenu(orderMenuOutputDto);
    }

    private void printTotalOrderPrice(Money totalPrice) {
        TotalOrderPriceOutputDto totalOrderPriceOutputDto = new TotalOrderPriceOutputDto(totalPrice.getValue());
        promotionApplyResultView.outputTotalOrderPrice(totalOrderPriceOutputDto);
    }

    private void printGiveaway(MenuItem giveaway) {
        GiveawayOutputDto giveawayOutputDto = createGiveawayOutputDto(giveaway);
        promotionApplyResultView.outputGiveaway(giveawayOutputDto);
    }

    private GiveawayOutputDto createGiveawayOutputDto(MenuItem giveaway) {
        if (giveaway == null) {
            return new GiveawayOutputDto(null, 0);
        }
        return new GiveawayOutputDto(giveaway.menu(), giveaway.count());
    }

    private void printAppliedPromotionBenefits(PromotionAppliedResult promotionAppliedResult) {
        EnumMap<ChristmasPromotionBenefit, Integer> appliedBenefits = getAppliedBenefits(
                (EnumMap<ChristmasPromotionBenefit, Money>) promotionAppliedResult.getDiscountBenefits(),
                promotionAppliedResult.getGiveawayBenefit());
        PromotionBenefitOutputDto promotionBenefitOutputDto = new PromotionBenefitOutputDto(appliedBenefits);
        promotionApplyResultView.outputPromotionBenefitList(promotionBenefitOutputDto);
    }

    private EnumMap<ChristmasPromotionBenefit, Integer> getAppliedBenefits(
            EnumMap<ChristmasPromotionBenefit, Money> discountBenefits, MenuItem giveawayBenefit) {
        EnumMap<ChristmasPromotionBenefit, Integer> appliedBenefits = discountBenefits.entrySet().stream()
                .collect(Collectors.toMap(
                        Entry::getKey,
                        entry -> entry.getValue().getValue(),
                        (existing, replacement) -> existing,
                        () -> new EnumMap<>(ChristmasPromotionBenefit.class)));

        if (giveawayBenefit == null) {
            return appliedBenefits;
        }
        Money giveawayBenefitPrice = giveawayBenefit.menu().getPrice().times(giveawayBenefit.count());
        appliedBenefits.put(ChristmasPromotionBenefit.GIVEAWAY, giveawayBenefitPrice.getValue());

        return appliedBenefits;
    }

    private void printBenefitPrice(Money totalBenefitPrice) {
        BenefitPriceOutputDto benefitPriceOutputDto = new BenefitPriceOutputDto(totalBenefitPrice.getValue());
        promotionApplyResultView.outputBenefitPrice(benefitPriceOutputDto);
    }

    private void printBenefitAppliedPrice(Money totalPrice, Money totalBenefitPrice) {
        int benefitAppliedPrice = totalPrice.sub(totalBenefitPrice).getValue();
        BenefitAppliedPriceOutputDto benefitAppliedPriceOutputDto = new BenefitAppliedPriceOutputDto(
                benefitAppliedPrice);
        promotionApplyResultView.outputBenefitAppliedPrice(benefitAppliedPriceOutputDto);
    }

    private void printReceivedEventBadge(EventBadge receivedBadge) {
        EventBadgeOutputDto eventBadgeOutputDto = new EventBadgeOutputDto(receivedBadge);
        promotionApplyResultView.outputReceivedEventBadge(eventBadgeOutputDto);
    }
}
