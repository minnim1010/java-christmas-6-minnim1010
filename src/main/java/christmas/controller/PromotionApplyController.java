package christmas.controller;

import christmas.domain.base.Money;
import christmas.domain.base.ReservationDate;
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
import christmas.service.ChristmasPromotionApplyService;
import christmas.view.PromotionApplyResultView;
import java.util.EnumMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class PromotionApplyController {
    private final PromotionApplyResultView promotionApplyResultView;
    private final ChristmasPromotionApplyService christmasPromotionApplyService;

    public PromotionApplyController(PromotionApplyResultView promotionApplyResultView,
                                    ChristmasPromotionApplyService christmasPromotionApplyService) {
        this.promotionApplyResultView = promotionApplyResultView;
        this.christmasPromotionApplyService = christmasPromotionApplyService;
    }

    public void applyPromotion(Reservation reservation) {
        PromotionAppliedResult promotionAppliedResult = christmasPromotionApplyService.applyPromotion(reservation);
        EventBadge receivedBadge = christmasPromotionApplyService.getEventBadge(reservation,
                promotionAppliedResult.getTotalBenefitPrice());

        printPromotionBenefitPreviewStart(reservation.getReservationDate());

        printOrderMenu(reservation.getOrderMenu());

        printTotalOrderPrice(reservation.getTotalPrice());

        printGiveaway(promotionAppliedResult.getGiveaway());

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

    private void printGiveaway(Menu giveaway) {
        GiveawayOutputDto giveawayOutputDto = new GiveawayOutputDto(giveaway, 1);
        promotionApplyResultView.outputGiveaway(giveawayOutputDto);
    }

    private void printAppliedPromotionBenefits(PromotionAppliedResult promotionAppliedResult) {
        EnumMap<ChristmasPromotionBenefit, Integer> convertedDiscountBenefits = convertDiscountBenefits(
                (EnumMap<ChristmasPromotionBenefit, Money>) promotionAppliedResult.getDiscountBenefits(),
                promotionAppliedResult.getGiveaway());
        PromotionBenefitOutputDto promotionBenefitOutputDto = new PromotionBenefitOutputDto(convertedDiscountBenefits);
        promotionApplyResultView.outputPromotionBenefitList(promotionBenefitOutputDto);
    }

    private EnumMap<ChristmasPromotionBenefit, Integer> convertDiscountBenefits(
            EnumMap<ChristmasPromotionBenefit, Money> discountBenefits, Menu giveaway) {
        EnumMap<ChristmasPromotionBenefit, Integer> convertedDiscountBenefits = discountBenefits.entrySet().stream()
                .collect(Collectors.toMap(
                        Entry::getKey,
                        entry -> entry.getValue().getValue(),
                        (existing, replacement) -> existing,
                        () -> new EnumMap<>(ChristmasPromotionBenefit.class)));

        if (giveaway != null) {
            convertedDiscountBenefits.put(ChristmasPromotionBenefit.GIVEAWAY, giveaway.getPrice().getValue());
        }
        return convertedDiscountBenefits;
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
