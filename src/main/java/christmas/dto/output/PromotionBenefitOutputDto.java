package christmas.dto.output;

import christmas.domain.base.Money;
import christmas.domain.constants.ChristmasPromotionEvent;
import java.util.EnumMap;

public record PromotionBenefitOutputDto(EnumMap<ChristmasPromotionEvent, Money> promotionBenefit) {
}
