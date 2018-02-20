package codecase;


public class App {

    public static void main(String[] args) {
        ExecutionHandler executionHandler = new ExecutionHandler("./phoneNumbers.txt", "./normalizedPhoneNumbers.txt");
        executionHandler.runApplication();
        //fileHandler.printToFile(phoneNumberGenerator.getPhoneNumbers(), "./phoneNumbers.txt");
    }

}
