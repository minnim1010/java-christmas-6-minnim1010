package christmas.domain.base;

public class Money {
    public static final int ZERO = 0;

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

    public boolean isZero(){
        return this.getValue() == ZERO;
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
}
