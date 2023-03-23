package proxy;

public class LowerCaseDecorator extends Decorator {

    public LowerCaseDecorator(Component component) {
        super(component);
    }

    @Override
    public String operation() {
        return super.operation().toLowerCase();
    }
}