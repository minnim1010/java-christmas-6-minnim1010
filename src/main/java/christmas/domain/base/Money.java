package christmas.domain.base;

import static christmas.common.exception.ErrorMessage.INVALID_MONEY_RANGE;

public class Money {
    public static final Money ZERO = Money.valueOf(0);
    private static final int MAX_AMOUNT = 2_000_000_000;
    private final int value;

    protected Money(int value) {
        validateRange(value);
        this.value = value;
    }

    private static void validateRange(int value) {
        if (isValidMoneyRange(value)) {
            throw new IllegalArgumentException(INVALID_MONEY_RANGE.getMessage(value));
        }
    }

    private static boolean isValidMoneyRange(int value) {
        return value < 0 || value > MAX_AMOUNT;
    }

    public static Money valueOf(int value) {
        return new Money(value);
    }

    public int getValue() {
        return value;
    }

    public boolean isZero() {
        return value == 0;
    }

    public Money times(int count) {
        return Money.valueOf(value * count);
    }

    public Money add(Money money) {
        return Money.valueOf(value + money.value);
    }

    public Money sub(Money money) {
        int amount = value - money.value;
        if (amount < 0) {
            amount = 0;
        }
        return Money.valueOf(amount);
    }

    public boolean isGreaterOrEqual(Money money) {
        return value >= money.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Money money = (Money) o;

        return value == money.value;
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public String toString() {
        return "Money{" +
                "value=" + value +
                '}';
    }
}
