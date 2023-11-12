package christmas.dto.output;

import christmas.domain.base.Money;
import christmas.domain.promotion.constants.ChristmasPromotionBenefit;
import java.util.EnumMap;

public record PromotionBenefitOutputDto(EnumMap<ChristmasPromotionBenefit, Money> promotionBenefit) {
}
