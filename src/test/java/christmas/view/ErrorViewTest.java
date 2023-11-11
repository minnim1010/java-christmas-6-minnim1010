package christmas.view;

import static christmas.view.constants.MessageFormat.LINE_SEPARATOR;
import static org.assertj.core.api.Assertions.assertThat;

import christmas.stub.StubWriter;
import org.junit.jupiter.api.Test;

class ErrorViewTest {
    private final StubWriter writer = new StubWriter();
    private final ErrorView errorView = new ErrorView(writer);

    @Test
    void 에러메시지_출력_테스트() {
        //given
        String errorMessage = "에러 발생";
        //when
        errorView.outputError(errorMessage);
        //then
        assertThat(writer.getOutput()).isEqualTo(errorMessage + LINE_SEPARATOR.value);
    }
}