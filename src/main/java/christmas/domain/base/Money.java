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
}
