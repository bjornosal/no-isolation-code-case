package codecase;


public class App {


    public static void main(String[] args) {
        PhoneNumberGenerator phoneNumberGenerator = new PhoneNumberGenerator();
        phoneNumberGenerator.generateSetAmountOfPhoneNumbers(1000);

        FileHandler fileHandler = new FileHandler();
//        fileHandler.printSetToFile(phoneNumberGenerator.getPhoneNumbers(), "./phoneNumbers.txt");

        ExecutionHandler executionHandler = new ExecutionHandler("./phoneNumbers.txt", "./normalizedPhoneNumbers.txt");
        executionHandler.runApplication();


        //fileHandler.printToFile(phoneNumberGenerator.getPhoneNumbers(), "./phoneNumbers.txt");
    }

}
