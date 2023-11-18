package christmas.domain.benefit.rule;

import christmas.domain.reservation.Reservation;
import java.util.Arrays;

public class AnyMatchRule implements Rule {
    private final Rule[] rules;

    public AnyMatchRule(Rule... rules) {
        this.rules = rules;
    }

    @Override
    public boolean isSatisfiedBy(Reservation reservation) {
        return Arrays.stream(rules).anyMatch(rule -> rule.isSatisfiedBy(reservation));
    }
}
