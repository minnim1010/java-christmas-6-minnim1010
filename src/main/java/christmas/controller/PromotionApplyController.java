package christmas.controller;

import christmas.domain.base.Money;
import christmas.domain.base.ReservationDate;
import christmas.domain.menu.MenuItem;
import christmas.domain.menu.OrderMenu;
import christmas.domain.menu.constants.Menu;
import christmas.domain.promotion.PromotionAppliedResult;
import christmas.domain.promotion.constants.EventBadge;
import christmas.domain.reservation.Reservation;
import christmas.dto.BenefitAmountOutputDto;
import christmas.dto.BenefitAppliedPriceOutputDto;
import christmas.dto.EventBadgeOutputDto;
import christmas.dto.GiveawayOutputDto;
import christmas.dto.OrderMenuOutputDto;
import christmas.dto.PromotionBenefitOutputDto;
import christmas.dto.ReservationDateOutputDto;
import christmas.dto.TotalOrderPriceOutputDto;
import christmas.service.ChristmasPromotionService;
import christmas.view.PromotionApplyResultView;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;
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
        PromotionAppliedResult promotionAppliedResult = christmasPromotionService.apply(reservation);
        EventBadge receivedBadge = christmasPromotionService.getEventBadge(reservation,
                promotionAppliedResult.getTotalBenefitPrice());

        printBenefitPreviewStart(reservation.getReservationDate());
        printOrderMenu(reservation.getOrderMenu());
        printTotalOrderPrice(reservation.getTotalPrice());
        printGiveaway(promotionAppliedResult.getGiveawayBenefits());
        printAppliedBenefits(promotionAppliedResult);
        printBenefitAmount(promotionAppliedResult.getTotalBenefitPrice());
        printBenefitAppliedPrice(reservation.getTotalPrice(), promotionAppliedResult.getTotalDiscountPrice());
        printReceivedEventBadge(receivedBadge);
    }

    private void printBenefitPreviewStart(ReservationDate reservationDate) {
        ReservationDateOutputDto reservationDateOutputDto = new ReservationDateOutputDto(reservationDate.getDay());
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

    private void printGiveaway(Map<String, MenuItem> giveawayBenefits) {
        EnumMap<Menu, Integer> giveaways = giveawayBenefits.values().stream()
                .collect(Collectors.toMap(
                        MenuItem::menu,
                        MenuItem::count,
                        (existing, replacement) -> existing,
                        () -> new EnumMap<>(Menu.class)));

        GiveawayOutputDto giveawayOutputDto = new GiveawayOutputDto(giveaways);
        promotionApplyResultView.outputGiveaway(giveawayOutputDto);
    }

    private void printAppliedBenefits(PromotionAppliedResult promotionAppliedResult) {
        Map<String, Integer> appliedBenefits = getAppliedBenefits(promotionAppliedResult);
        PromotionBenefitOutputDto promotionBenefitOutputDto = new PromotionBenefitOutputDto(appliedBenefits);
        promotionApplyResultView.outputPromotionBenefitList(promotionBenefitOutputDto);
    }

    private Map<String, Integer> getAppliedBenefits(PromotionAppliedResult promotionAppliedResult) {
        Map<String, Money> discountBenefits = promotionAppliedResult.getDiscountBenefits();
        Map<String, MenuItem> giveawayBenefits = promotionAppliedResult.getGiveawayBenefits();

        Map<String, Integer> appliedBenefits = discountBenefits.entrySet().stream()
                .collect(Collectors.toMap(
                        Entry::getKey,
                        entry -> entry.getValue().getValue(),
                        (existing, replacement) -> existing,
                        LinkedHashMap::new));

        giveawayBenefits.forEach((key, value) -> appliedBenefits.put(key, value.menu().getPrice().getValue()));
        return appliedBenefits;
    }

    private void printBenefitAmount(Money totalBenefitAmount) {
        BenefitAmountOutputDto benefitAmountOutputDto = new BenefitAmountOutputDto(totalBenefitAmount.getValue());
        promotionApplyResultView.outputBenefitAmount(benefitAmountOutputDto);
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
