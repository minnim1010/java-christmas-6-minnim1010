package christmas.integration;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThatCode;

import christmas.config.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SuccessTest extends IntegrationTest {

    private void inputLogicTest(String... args) {
        assertSimpleTest(() -> {
            assertThatCode(() -> run(args));
        });
    }

    @Test
    void 재입력_없는_테스트() {
        inputLogicTest("3", "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1");
    }

    @ValueSource(strings = {" ", "a", "0", "32"})
    @ParameterizedTest
    void 예약날짜_재입력_테스트(String invalidInput) {
        inputLogicTest(invalidInput, "3", "티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1");
    }

    @ValueSource(strings = {" ", "a", "티본스테이크-,바비큐립-1,초코케이크-2,제로콜라-1",
            "티본스테이크-17,바비큐립-1,초코케이크-2,제로콜라-1",
            "제로콜라-1",
            "티본스테이크-17,티본스테이크-1",
            "없는메뉴-1,제로콜라-3"})
    @ParameterizedTest
    void 주문메뉴_재입력_테스트(String invalidInput) {
        inputLogicTest("3", invalidInput, "티본스테이크-3,바비큐립-1,초코케이크-2,제로콜라-1");
    }

}
