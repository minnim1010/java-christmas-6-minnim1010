package christmas.domain.base;

import static christmas.common.exception.ErrorMessage.INVALID_DATE;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class ReservationDate {
    protected final LocalDate date;

    protected ReservationDate(int year, int month, int day) {
        try {
            date = LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(INVALID_DATE.getMessage(year, month, day));
        }
    }

    public static ReservationDate valueOf(int year, int month, int day) {
        return new ReservationDate(year, month, day);
    }

    public int getDay() {
        return date.getDayOfMonth();
    }

    public boolean isSameDay(LocalDate compare) {
        return date.equals(compare);
    }

    public boolean isSameDayOfWeek(DayOfWeek dayOfWeek) {
        return date.getDayOfWeek().equals(dayOfWeek);
    }

    public boolean isWeekend() {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY;
    }

    public boolean isBetween(LocalDate startDate, LocalDate endDate) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    public int getDaysBetween(LocalDate target) {
        if (target.isAfter(date)) {
            return date.until(target).getDays();
        }
        return target.until(date).getDays();
    }

    @Override
    public String toString() {
        return "ReservationDate{" +
                "date=" + date +
                '}';
    }
}
