package codecase;


public class App {

    public static void main(String[] args) {
        PhoneNumberGenerator phoneNumberGenerator = new PhoneNumberGenerator();
        FileHandler fileHandler = new FileHandler();
        NormalizationHandler normalizationHandler = new NormalizationHandler();
        phoneNumberGenerator.generateSetAmountOfPhoneNumbers(1000);

        //fileHandler.printToFile(phoneNumberGenerator.getPhoneNumbers(), "./phoneNumbers.txt");
        System.out.println(normalizationHandler.normalizeSwedishPhoneNumber("+4612345678"));
    }

}
