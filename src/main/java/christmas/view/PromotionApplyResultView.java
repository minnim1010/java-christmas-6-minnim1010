package christmas.view;

import static christmas.view.constants.MessageFormat.DISCOUNT_PRICE;
import static christmas.view.constants.MessageFormat.LINE_SEPARATOR;
import static christmas.view.constants.MessageFormat.MENU_ITEM;
import static christmas.view.constants.MessageFormat.PRICE;
import static christmas.view.constants.MessageFormat.PROMOTION_BENEFIT_ITEM;
import static christmas.view.constants.NoticeMessage.BENEFIT_APPLIED_PRICE_MESSAGE;
import static christmas.view.constants.NoticeMessage.BENEFIT_LIST_MESSAGE;
import static christmas.view.constants.NoticeMessage.BENEFIT_PRICE_MESSAGE;
import static christmas.view.constants.NoticeMessage.EVENT_BADGE_MESSAGE;
import static christmas.view.constants.NoticeMessage.NOT_APPLICABLE;
import static christmas.view.constants.NoticeMessage.ORDER_MENU_MESSAGE;
import static christmas.view.constants.NoticeMessage.OUTPUT_GIVEAWAY_MENU_MESSAGE;
import static christmas.view.constants.NoticeMessage.PROMOTION_BENEFIT_PREVIEW_START_MESSAGE;
import static christmas.view.constants.NoticeMessage.TOTAL_ORDER_PRICE_MESSAGE;
import static christmas.view.constants.NoticeMessage.ZERO_AMOUNT;

import christmas.domain.menu.constants.Menu;
import christmas.domain.promotion.constants.EventBadge;
import christmas.dto.BenefitAppliedPriceOutputDto;
import christmas.dto.BenefitPriceOutputDto;
import christmas.dto.EventBadgeOutputDto;
import christmas.dto.GiveawayOutputDto;
import christmas.dto.OrderMenuOutputDto;
import christmas.dto.PromotionBenefitOutputDto;
import christmas.dto.ReservationDateOutputDto;
import christmas.dto.TotalOrderPriceOutputDto;
import christmas.view.io.writer.Writer;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PromotionApplyResultView {
    private final Writer writer;

    public PromotionApplyResultView(Writer writer) {
        this.writer = writer;
    }

    public void outputPromotionBenefitPreviewStart(ReservationDateOutputDto reservedVisitDateDto) {
        writer.writeLine(String.format(PROMOTION_BENEFIT_PREVIEW_START_MESSAGE.value, reservedVisitDateDto.day()));
    }

    public void outputOrderMenu(OrderMenuOutputDto orderMenuOutputDto) {
        EnumMap<Menu, Integer> orderMenu = orderMenuOutputDto.orderMenu();
        String orderMenuMessage = orderMenu.entrySet().stream()
                .map(entry -> String.format(MENU_ITEM.value, entry.getKey().getName(), entry.getValue()))
                .collect(Collectors.joining(LINE_SEPARATOR.value));

        String resultMessage = getResultMessage(ORDER_MENU_MESSAGE.value, orderMenuMessage);
        writer.writeLine(resultMessage);
    }

    private String getResultMessage(String title, String value) {
        return LINE_SEPARATOR.value + String.join(LINE_SEPARATOR.value, title, value);
    }

    public void outputTotalOrderPrice(TotalOrderPriceOutputDto totalOrderPriceOutputDto) {
        int orderPrice = totalOrderPriceOutputDto.orderPrice();
        String orderPriceMessage = String.format(PRICE.value, orderPrice);
        String resultMessage = getResultMessage(TOTAL_ORDER_PRICE_MESSAGE.value, orderPriceMessage);
        writer.writeLine(resultMessage);
    }

    public void outputGiveaway(GiveawayOutputDto giveawayOutputDto) {
        String giveawayMessage = getGiveawayMessage(giveawayOutputDto);
        String resultMessage = getResultMessage(OUTPUT_GIVEAWAY_MENU_MESSAGE.value, giveawayMessage);
        writer.writeLine(resultMessage);
    }

    private String getGiveawayMessage(GiveawayOutputDto giveawayOutputDto) {
        Map<Menu, Integer> giveaways = giveawayOutputDto.giveaways();
        if (giveaways.isEmpty()) {
            return NOT_APPLICABLE.value;
        }

        return giveaways.entrySet().stream()
                .map(entry -> String.format(MENU_ITEM.value, entry.getKey().getName(), entry.getValue()))
                .collect(Collectors.joining(LINE_SEPARATOR.value));
    }

    public void outputPromotionBenefitList(PromotionBenefitOutputDto promotionBenefitOutputDto) {
        String promotionBenefitMessage = getPromotionBenefitMessage(promotionBenefitOutputDto);
        String resultMessage = getResultMessage(BENEFIT_LIST_MESSAGE.value, promotionBenefitMessage);
        writer.writeLine(resultMessage);
    }

    private String getPromotionBenefitMessage(PromotionBenefitOutputDto promotionBenefitOutputDto) {
        Map<String, Integer> promotionBenefits = promotionBenefitOutputDto.promotionBenefit();
        if (promotionBenefits.isEmpty()) {
            return NOT_APPLICABLE.value;
        }
        return promotionBenefits.entrySet().stream()
                .map(entry -> String.format(PROMOTION_BENEFIT_ITEM.value,
                        entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(LINE_SEPARATOR.value));
    }

    public void outputBenefitPrice(BenefitPriceOutputDto benefitPriceOutputDto) {
        int benefitPrice = benefitPriceOutputDto.benefitAppliedPrice();
        String benefitPriceMessage = getBenefitPriceMessage(DISCOUNT_PRICE.value, benefitPrice);
        String resultMessage = getResultMessage(BENEFIT_PRICE_MESSAGE.value, benefitPriceMessage);
        writer.writeLine(resultMessage);
    }

    private String getBenefitPriceMessage(String benefitPriceFormat, int benefitPrice) {
        if (benefitPrice == 0) {
            return ZERO_AMOUNT.value;
        }
        return String.format(benefitPriceFormat, benefitPrice);
    }

    public void outputBenefitAppliedPrice(BenefitAppliedPriceOutputDto benefitAppliedPriceOutputDto) {
        int benefitAppliedPrice = benefitAppliedPriceOutputDto.benefitAppliedPrice();
        String orderPriceMessage = String.format(PRICE.value, benefitAppliedPrice);
        String resultMessage = getResultMessage(BENEFIT_APPLIED_PRICE_MESSAGE.value, orderPriceMessage);
        writer.writeLine(resultMessage);
    }

    public void outputReceivedEventBadge(EventBadgeOutputDto eventBadgeOutputDto) {
        EventBadge eventBadge = eventBadgeOutputDto.eventBadge();
        String eventBadgeMessage = eventBadge.getName();
        String resultMessage = getResultMessage(EVENT_BADGE_MESSAGE.value, eventBadgeMessage);
        writer.writeLine(resultMessage);
    }
}
