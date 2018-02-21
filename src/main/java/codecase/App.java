package codecase;



public class App {


    public static void main(String[] args) {
        App app = new App();
        app.runApp("./phoneNumbers.txt", "./normalizedPhoneNumbers.txt");
    }

    /**
     * Runs the application
     * @param inputFilePath inputfile filepath
     * @param outputFilePath outputfile filepath
     */
    private void runApp(String inputFilePath, String outputFilePath) {

        ExecutionHandler executionHandler = new ExecutionHandler(inputFilePath, outputFilePath);
        executionHandler.runApplication();

    }

}
