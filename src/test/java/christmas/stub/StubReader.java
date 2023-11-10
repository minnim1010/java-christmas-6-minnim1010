package christmas.stub;


import christmas.view.io.reader.Reader;

public class StubReader implements Reader {
    private String input;

    @Override
    public String readLine() {
        return input;
    }

    @Override
    public void close() {
    }

    public void setInput(String input) {
        this.input = input;
    }
}