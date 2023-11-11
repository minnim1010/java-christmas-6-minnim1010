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
import static christmas.view.constants.NoticeMessage.NO_BENEFIT_PRICE;
import static christmas.view.constants.NoticeMessage.ORDER_MENU_MESSAGE;
import static christmas.view.constants.NoticeMessage.OUTPUT_GIVEAWAY_MENU_MESSAGE;
import static christmas.view.constants.NoticeMessage.PROMOTION_BENEFIT_PREVIEW_START_MESSAGE;
import static christmas.view.constants.NoticeMessage.TOTAL_ORDER_PRICE_MESSAGE;

import christmas.domain.base.Money;
import christmas.domain.constants.ChristmasPromotionEvent;
import christmas.domain.constants.EventBadge;
import christmas.domain.constants.MenuItem;
import christmas.dto.output.BenefitAppliedPriceOutputDto;
import christmas.dto.output.BenefitPriceOutputDto;
import christmas.dto.output.EventBadgeOutputDto;
import christmas.dto.output.GiveawayMenuOutputDto;
import christmas.dto.output.OrderMenuOutputDto;
import christmas.dto.output.OrderPriceOutputDto;
import christmas.dto.output.PromotionBenefitOutputDto;
import christmas.dto.output.ReservedVisitDateOutputDto;
import christmas.view.io.writer.Writer;
import java.util.EnumMap;
import java.util.stream.Collectors;

public class PromotionApplyResultView {
    private final Writer writer;

    public PromotionApplyResultView(Writer writer) {
        this.writer = writer;
    }

    public void outputPromotionBenefitPreviewStart(ReservedVisitDateOutputDto reservedVisitDateDto) {
        writer.writeLine(String.format(PROMOTION_BENEFIT_PREVIEW_START_MESSAGE.value,
                reservedVisitDateDto.reservedVisitDate().getMonth().getValue(),
                reservedVisitDateDto.reservedVisitDate().getDayOfMonth()));
    }

    public void outputOrderMenu(OrderMenuOutputDto orderMenuOutputDto) {
        EnumMap<MenuItem, Integer> orderMenu = orderMenuOutputDto.orderMenu();
        String orderMenuMessage = orderMenu.entrySet().stream()
                .map(entry ->
                        String.format(MENU_ITEM.value, entry.getKey().getName(), entry.getValue()))
                .collect(Collectors.joining(LINE_SEPARATOR.value));

        String resultMessage = getResultMessage(ORDER_MENU_MESSAGE.value, orderMenuMessage);
        writer.writeLine(resultMessage);
    }

    private String getResultMessage(String title, String value) {
        return LINE_SEPARATOR.value + String.join(LINE_SEPARATOR.value, title, value);
    }

    public void outputOrderPrice(OrderPriceOutputDto orderPriceOutputDto) {
        Money orderPrice = orderPriceOutputDto.orderPrice();
        String orderPriceMessage = String.format(PRICE.value, orderPrice.getValue());
        String resultMessage = getResultMessage(TOTAL_ORDER_PRICE_MESSAGE.value, orderPriceMessage);
        writer.writeLine(resultMessage);
    }

    public void outputGiveawayMenu(GiveawayMenuOutputDto giveawayMenuOutputDto) {
        MenuItem giveaway = giveawayMenuOutputDto.giveaway();
        int count = giveawayMenuOutputDto.count();
        String giveawayMenuMessage = String.format(MENU_ITEM.value, giveaway.getName(), count);
        String resultMessage = getResultMessage(OUTPUT_GIVEAWAY_MENU_MESSAGE.value, giveawayMenuMessage);
        writer.writeLine(resultMessage);
    }

    private String getPromotionBenefitMessage(EnumMap<ChristmasPromotionEvent, Money> promotionBenefit) {
        if (promotionBenefit.isEmpty()) {
            return NOT_APPLICABLE.value;
        }
        return promotionBenefit.entrySet().stream()
                .map(entry ->
                        String.format(PROMOTION_BENEFIT_ITEM.value,
                                entry.getKey().getName(), entry.getValue().getValue()))
                .collect(Collectors.joining(LINE_SEPARATOR.value));
    }

    public void outputPromotionBenefitList(PromotionBenefitOutputDto promotionBenefitOutputDto) {
        EnumMap<ChristmasPromotionEvent, Money> promotionBenefit = promotionBenefitOutputDto.promotionBenefit();
        String promotionBenefitMessage = getPromotionBenefitMessage(promotionBenefit);
        String resultMessage = getResultMessage(BENEFIT_LIST_MESSAGE.value, promotionBenefitMessage);
        writer.writeLine(resultMessage);
    }

    private String getBenefitPriceMessage(String benefitPriceFormat, Money benefitPrice) {
        if (benefitPrice.isZero()) {
            return NO_BENEFIT_PRICE.value;
        }
        return String.format(benefitPriceFormat, benefitPrice.getValue());
    }

    public void outputBenefitPrice(BenefitPriceOutputDto benefitPriceOutputDto) {
        Money benefitPrice = benefitPriceOutputDto.benefitAppliedPrice();
        String benefitPriceMessage = getBenefitPriceMessage(DISCOUNT_PRICE.value, benefitPrice);
        String resultMessage = getResultMessage(BENEFIT_PRICE_MESSAGE.value, benefitPriceMessage);
        writer.writeLine(resultMessage);
    }

    public void outputBenefitAppliedPrice(BenefitAppliedPriceOutputDto benefitAppliedPriceOutputDto) {
        Money benefitAppliedPrice = benefitAppliedPriceOutputDto.benefitAppliedPrice();
        String orderPriceMessage = String.format(PRICE.value, benefitAppliedPrice.getValue());
        String resultMessage = getResultMessage(BENEFIT_APPLIED_PRICE_MESSAGE.value, orderPriceMessage);
        writer.writeLine(resultMessage);
    }

    private String getEventBadgeMessage(EventBadge eventBadge) {
        if (eventBadge == null) {
            return NOT_APPLICABLE.value;
        }
        return eventBadge.getName();
    }

    public void outputEventBadge(EventBadgeOutputDto eventBadgeOutputDto) {
        EventBadge eventBadge = eventBadgeOutputDto.eventBadge();
        String eventBadgeMessage = getEventBadgeMessage(eventBadge);
        String resultMessage = getResultMessage(EVENT_BADGE_MESSAGE.value, eventBadgeMessage);
        writer.writeLine(resultMessage);
    }
}
