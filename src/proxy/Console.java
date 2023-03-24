package proxy;

public class Console {
    public void run() {
        executeCommand(new BlockURL("https://www.nadav.co.il/"));
        executeCommand(new PrintList());
        System.out.println("After print");
        executeCommand(new UnblockURL("https://www.nadav.co.il/"));
        executeCommand(new PrintList());
        this.exit();
    }

    private void executeCommand(Command command) {
        command.execute();
    }

    private void exit() {
        System.exit(0);
    }
}
