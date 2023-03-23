package proxy;

public class Main {

    public static void main(String[] args) {

        System.out.println("The original component:");
        System.out.println(new ConcreteComponent("Hello World!").operation()); // Hello World!

        System.out.println("Decorating the component: ");

        // compose your decorators as you want
        Decorator decorator = new CompressorDecorator(new LowerCaseDecorator(new ConcreteComponent("Hello World!")));
        System.out.println(decorator.operation()); // helloworld!
    }
}
