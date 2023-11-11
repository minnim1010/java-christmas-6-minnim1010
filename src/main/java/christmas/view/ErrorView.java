package christmas.view;

import christmas.view.io.writer.Writer;

public class ErrorView {
    private final Writer writer;

    public ErrorView(Writer writer) {
        this.writer = writer;
    }

    public void outputError(String errorMessage) {
        writer.writeLine(errorMessage);
    }
}
