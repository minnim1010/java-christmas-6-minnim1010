package christmas.controller;

import static christmas.exception.ErrorMessage.INVALID_DATE;
import static christmas.exception.ErrorMessage.INVALID_NUMERIC_INPUT;
import static christmas.exception.ErrorMessage.INVALID_ORDER;
import static christmas.exception.ErrorMessage.INVALID_ORDER_TOTAL_COUNT_RANGE;
import static christmas.exception.ErrorMessage.ORDERED_ONLY_BEVERAGE;
import static christmas.fixture.ChristmasFixture.calculatePrice;
import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.base.Money;
import christmas.domain.menu.constants.MenuItem;
import christmas.domain.reservation.Reservation;
import christmas.exception.ErrorMessage;
import christmas.stub.StubReader;
import christmas.stub.StubWriter;
import christmas.view.ErrorView;
import christmas.view.ReservationView;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("12월 프로모션 기간 중 식당 예약")
class ReservationControllerTest {
    private final StubReader reader = new StubReader();
    private final StubWriter writer = new StubWriter();
    private final ReservationView reservationView = new ReservationView(reader, writer);
    private final ErrorView errorView = new ErrorView(writer);
    private final ReservationController reservationController = new ReservationController(reservationView, errorView);

    @Test
    void 재입력없이_예약한다() {
        //given
        reader.setInput("31", "시저샐러드-13,바비큐립-2,해산물파스타-1,초코케이크-3,레드와인-1");
        //when
        Reservation reservation = reservationController.makeReservation();
        //then
        assertThat(reservation.getReservationDate().getDay()).isEqualTo(31);
        assertThat(reservation.getOrderMenu().toString())
                .contains(MenuItem.CAESAR_SALAD.name(), "13",
                        MenuItem.BBQ_RIB.name(), "2",
                        MenuItem.SEAFOOD_PASTA.name(), "1",
                        MenuItem.CHOCO_CAKE.name(), "3",
                        MenuItem.RED_WINE.name(), "1");
        Money expected = calculatePrice(
                List.of(MenuItem.CAESAR_SALAD, MenuItem.BBQ_RIB, MenuItem.SEAFOOD_PASTA, MenuItem.CHOCO_CAKE,
                        MenuItem.RED_WINE),
                List.of(13, 2, 1, 3, 1));
        assertThat(reservation.getTotalPrice()).isEqualTo(expected);
    }

    private void reinputTest(ErrorMessage errorMessage, String... args) {
        //given
        reader.setInput(args);
        //when
        reservationController.makeReservation();
        //then
        assertThat(writer.getOutput())
                .contains(errorMessage.getMessage());
    }

    @Nested
    class 방문날짜_재입력후_예약한다 {

        @ValueSource(strings = {"a", "ㅏ", "_", "!!!"})
        @ParameterizedTest
        void 방문날짜입력값이_숫자형식이_아닌_경우(String invalidInput) {
            reinputTest(INVALID_NUMERIC_INPUT, invalidInput, "31", "시저샐러드-1");
        }

        @ValueSource(strings = {"0", "32", "2147483647"})
        @ParameterizedTest
        void 방문날짜입력값이_1이상_31이하가_아닌_경우(String invalidInput) {
            reinputTest(INVALID_DATE, invalidInput, "31", "시저샐러드-1");
        }
    }

    @Nested
    class 주문메뉴_재입력후_예약한다 {

        @ValueSource(strings = {"시저샐러드 1",
                "티본스테이크",
                "티본스테이크 a",
                "12345",
                "시저샐러드-1/티본스테이크-5/시저샐러드-3",
                "바비큐립-3 크리스마스파스타-1 초코케이크-10"})
        @ParameterizedTest
        void 주문메뉴입력형식과_다른_경우(String invalidInput) {
            reinputTest(INVALID_ORDER, "31", invalidInput, "시저샐러드-1");
        }

        @ValueSource(strings = {"없는메뉴-1", "noMenu-3"})
        @ParameterizedTest
        void 메뉴판에_없는메뉴인_경우(String invalidInput) {
            reinputTest(INVALID_ORDER, "31", invalidInput, "시저샐러드-1");
        }

        @ValueSource(strings = {"시저샐러드-0,티본스테이크-1",
                "시저샐러드-1,티본스테이크-21",
                "시저샐러드--1,티본스테이크-3"})
        @ParameterizedTest
        void 메뉴개수가_1개이상_20개이하가_아닌_경우(String invalidInput) {
            reinputTest(INVALID_ORDER, "31", invalidInput, "시저샐러드-1");
        }

        @ValueSource(strings = {"시저샐러드-0,티본스테이크-1",
                "시저샐러드-1,티본스테이크-21",
                "시저샐러드--1,티본스테이크-3"})
        @ParameterizedTest
        void 메뉴를_중복해_주문한_경우(String invalidInput) {
            reinputTest(INVALID_ORDER, "31", invalidInput, "시저샐러드-1");
        }

        @ValueSource(strings = {"시저샐러드-15,티본스테이크-6",
                "시저샐러드-1,티본스테이크-20",
                "시저샐러드-15,티본스테이크-15"})
        @ParameterizedTest
        void 총메뉴개수가_20개가_넘는_경우(String invalidInput) {
            reinputTest(INVALID_ORDER_TOTAL_COUNT_RANGE, "31", invalidInput, "시저샐러드-1");
        }

        @ValueSource(strings = {"제로콜라-1,레드와인-13",
                "샴페인-14",
                "제로콜라-1,레드와인-13,샴페인-3",})
        @ParameterizedTest
        void 음료만_주문한_경우(String invalidInput) {
            reinputTest(ORDERED_ONLY_BEVERAGE, "31", invalidInput, "시저샐러드-1");
        }
    }
}