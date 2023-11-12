package christmas.integration;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import christmas.config.IntegrationTestConfig;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

class SuccessTest extends IntegrationTestConfig {
    private static final Pattern SPACES_PATTERN = Pattern.compile("\\s+");

    private void inputLogicTest(String... args) {
        assertSimpleTest(() -> assertThatCode(() -> run(args)));
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

    private void csvFileSourceTest(String day, String menu, String expected) {
        //given
        String expectedWithoutSpaces = SPACES_PATTERN.matcher(expected).replaceAll("");
        //when then
        assertSimpleTest(() -> {
            run(day, menu);
            String output = output();
            String outputWithoutSpaces = SPACES_PATTERN.matcher(output).replaceAll("");
            assertThat(outputWithoutSpaces).contains(expectedWithoutSpaces);
        });
    }

    @CsvFileSource(resources = "/SuccessTest/복합혜택_테스트.csv",
            delimiter = ';', lineSeparator = "/", numLinesToSkip = 1)
    @ParameterizedTest(name = "12월 {0}일 메뉴: {1}")
    void 복합혜택_테스트(String day, String menu, String expected) {
        csvFileSourceTest(day, menu, expected);
    }
}
