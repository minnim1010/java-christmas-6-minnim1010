package christmas.domain.benefit.policy;

import christmas.domain.benefit.Benefit;
import christmas.domain.benefit.rule.Rule;
import christmas.domain.benefit.type.GiveawayType;

public interface GiveawayPolicy extends Rule, GiveawayType, Benefit {
}
