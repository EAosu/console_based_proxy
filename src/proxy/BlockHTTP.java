package proxy;

public class CompressorDecorator extends Decorator {

    public CompressorDecorator(Component component) {
        super(component);
    }

    @Override
    public String operation() {
        return compress(super.operation());
    }

    private String compress(String data) {
        return data.replace(" ", "");
    }
}