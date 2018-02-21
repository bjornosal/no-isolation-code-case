package codecase;



public class App {


    public static void main(String[] args) {
        App app = new App();
        app.runApp("./phoneNumbers.txt", "./normalizedPhoneNumbers.txt");
    }

    private void runApp(String filePath, String printPath) {

        ExecutionHandler executionHandler = new ExecutionHandler(filePath, printPath);
        executionHandler.runApplication();

    }

}
