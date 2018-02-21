package codecase;

import java.io.File;
import java.util.ArrayList;

public class ExecutionHandler {

    private FileHandler fileHandler;
    private NormalizationHandler normalizationHandler;
    private PhoneNumberGenerator phoneNumberGenerator;

    private ArrayList<String> phoneNumbersToNormalize;
    private ArrayList<String> normalizedPhoneNumbers;
    private ArrayList<String> phoneNumbersUnableToNormalize;

    private String outputFilePath;
    private String inputFilePath;

    public ExecutionHandler(String inputFilePath, String outputFilePath) {
        fileHandler = new FileHandler();
        normalizationHandler = new NormalizationHandler();
        phoneNumberGenerator= new PhoneNumberGenerator();
        phoneNumbersToNormalize = null;
        normalizedPhoneNumbers = new ArrayList<>();
        phoneNumbersUnableToNormalize = new ArrayList<>();
        this.outputFilePath = outputFilePath;
        this.inputFilePath = inputFilePath;
    }

    /**
     * Method for running all logic in application.
     */
    public void runApplication() {
        File file = new File(inputFilePath);
        if(!file.exists() && !file.isDirectory()) {
            phoneNumberGenerator.generateSetAmountOfPhoneNumbers(1000);
            fileHandler.printSetToFile(phoneNumberGenerator.getPhoneNumbers(), inputFilePath);
        }
        phoneNumbersToNormalize = fileHandler.getPhoneNumbersFromFile(inputFilePath);
        normalizeAllPhoneNumbers();
        fileHandler.printArrayListToFile(normalizedPhoneNumbers, outputFilePath);

        if(phoneNumbersUnableToNormalize.size() != 0) {
            normalizedPhoneNumbers.addAll(phoneNumbersUnableToNormalize);

            fileHandler.printArrayListToFile(normalizedPhoneNumbers, revisePrintPathFileNameWithLineNumbers(outputFilePath));
        }
    }

    /**
     * Creates a revised path, to create secondary result file.
     * @param printPath to alter
     * @return print path with added ending.
     */
    private String revisePrintPathFileNameWithLineNumbers(String printPath) {
        return printPath.substring(0, printPath.lastIndexOf('.')-1) + "-with-line-numbers.txt";
    }

    /**
     * Normalizes all phone numbers.
     */
    private void normalizeAllPhoneNumbers() {

        for(int i = 0; i < phoneNumbersToNormalize.size(); i++) {
            String tempNumber = phoneNumbersToNormalize.get(i);
            if (normalizationHandler.phoneNumberNeedsNormalization(tempNumber)) {
                tempNumber = normalizationHandler.normalizePhoneNumber(tempNumber);

                if (phoneNumbersToNormalize.get(i).equals(tempNumber)) {
                    phoneNumbersUnableToNormalize.add("Line: " + (i + 1) + " => " + tempNumber);
                }
                normalizedPhoneNumbers.add(tempNumber);
            } else {
                normalizedPhoneNumbers.add(tempNumber);
            }
        }
    }

}
