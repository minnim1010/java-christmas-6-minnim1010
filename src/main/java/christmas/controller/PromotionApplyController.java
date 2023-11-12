package christmas.controller;

import christmas.domain.base.Money;
import christmas.domain.base.ReservationDate;
import christmas.domain.menu.OrderMenu;
import christmas.domain.menu.constants.MenuItem;
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
                (EnumMap<MenuItem, Integer>) orderMenu.getItems());
        promotionApplyResultView.outputOrderMenu(orderMenuOutputDto);
    }

    private void printTotalOrderPrice(Money totalPrice) {
        TotalOrderPriceOutputDto totalOrderPriceOutputDto = new TotalOrderPriceOutputDto(totalPrice);
        promotionApplyResultView.outputTotalOrderPrice(totalOrderPriceOutputDto);
    }

    private void printGiveaway(MenuItem giveaway) {
        GiveawayOutputDto giveawayOutputDto = new GiveawayOutputDto(giveaway, 1);
        promotionApplyResultView.outputGiveaway(giveawayOutputDto);
    }

    private void printAppliedPromotionBenefits(PromotionAppliedResult promotionAppliedResult) {
        EnumMap<ChristmasPromotionBenefit, Money> discountBenefits =
                (EnumMap<ChristmasPromotionBenefit, Money>) promotionAppliedResult.getDiscountBenefits();
        MenuItem giveaway = promotionAppliedResult.getGiveaway();

        EnumMap<ChristmasPromotionBenefit, Money> clone = discountBenefits.clone();
        if (giveaway != null) {
            clone.put(ChristmasPromotionBenefit.GIVEAWAY, giveaway.getPrice());
        }

        PromotionBenefitOutputDto promotionBenefitOutputDto = new PromotionBenefitOutputDto(clone);
        promotionApplyResultView.outputPromotionBenefitList(promotionBenefitOutputDto);
    }

    private void printBenefitPrice(Money totalBenefitPrice) {
        BenefitPriceOutputDto benefitPriceOutputDto = new BenefitPriceOutputDto(totalBenefitPrice);
        promotionApplyResultView.outputBenefitPrice(benefitPriceOutputDto);
    }

    private void printBenefitAppliedPrice(Money totalPrice, Money totalBenefitPrice) {
        BenefitAppliedPriceOutputDto benefitAppliedPriceOutputDto = new BenefitAppliedPriceOutputDto(
                totalPrice.sub(totalBenefitPrice));
        promotionApplyResultView.outputBenefitAppliedPrice(benefitAppliedPriceOutputDto);
    }

    private void printReceivedEventBadge(EventBadge receivedBadge) {
        EventBadgeOutputDto eventBadgeOutputDto = new EventBadgeOutputDto(receivedBadge);
        promotionApplyResultView.outputReceivedEventBadge(eventBadgeOutputDto);
    }
}
