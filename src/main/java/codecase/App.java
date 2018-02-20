package codecase;


public class App {

    public static void main(String[] args) {
        PhoneNumberGenerator phoneNumberGenerator = new PhoneNumberGenerator();
        PrintHandler printHandler = new PrintHandler();

        phoneNumberGenerator.generateSetAmountOfPhoneNumbers(1000);

        printHandler.printToFile(phoneNumberGenerator.getPhoneNumbers(), "./phoneNumbers.txt");
    }

}
