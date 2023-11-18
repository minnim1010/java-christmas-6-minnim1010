package christmas.stub;


import christmas.view.io.writer.Writer;

public class StubWriter implements Writer {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private final StringBuilder output = new StringBuilder();

    @Override
    public void writeLine(String message) {
        output.append(message).append(LINE_SEPARATOR);
    }

    public String getOutput() {
        return output.toString();
    }
}