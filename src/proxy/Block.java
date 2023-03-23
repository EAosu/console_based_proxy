package proxy;

public class ConcreteComponent implements Component {
    String data;
    ArrayList<String> content;
    public ConcreteComponent(String data, ArrayList<String> content) {
        // in a real world application, this would be for example a file name
        // and data would be the contents of the file
        this.data = data;
        this.content = content;
    }
    @Override
    public String operation() {
        return data;
    }
}