package christmas.domain.base;

public class Money {
    public static final Money ZERO = Money.valueOf(0);

    private final int value;

    protected Money(int value) {
        this.value = value;
    }

    public static Money valueOf(int value) {
        return new Money(value);
    }

    public int getValue() {
        return value;
    }

    public boolean isZero() {
        return this.equals(ZERO);
    }

    public Money times(int count) {
        return Money.valueOf(value * count);
    }

    public Money add(Money money) {
        return Money.valueOf(value + money.value);
    }

    public Money sub(Money money) {
        return Money.valueOf(value - money.value);
    }

    public boolean isGreaterThan(Money money) {
        return value > money.value;
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
