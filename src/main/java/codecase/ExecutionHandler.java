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
    private String printPath;
    private String filePath;

    public ExecutionHandler(String filePath, String printPath) {
        fileHandler = new FileHandler();
        normalizationHandler = new NormalizationHandler();
        phoneNumberGenerator= new PhoneNumberGenerator();
        phoneNumbersToNormalize = null;
        normalizedPhoneNumbers = new ArrayList<>();
        phoneNumbersUnableToNormalize = new ArrayList<>();
        this.printPath = printPath;
        this.filePath = filePath;
    }

    public void runApplication() {
        File file = new File(filePath);
        if(!file.exists() && !file.isDirectory()) {
            phoneNumberGenerator.generateSetAmountOfPhoneNumbers(1000);
            fileHandler.printSetToFile(phoneNumberGenerator.getPhoneNumbers(), filePath);
        }
        phoneNumbersToNormalize = fileHandler.getPhoneNumbersFromFile(filePath);
        normalizeAllPhoneNumbers();
        fileHandler.printArrayListToFile(normalizedPhoneNumbers, printPath);

        if(phoneNumbersUnableToNormalize.size() != 0) {
            normalizedPhoneNumbers.addAll(phoneNumbersUnableToNormalize);

            fileHandler.printArrayListToFile(normalizedPhoneNumbers, revisePrintPathFileNameWithLineNumbers(printPath));
        }
    }

    private String revisePrintPathFileNameWithLineNumbers(String printPath) {
        return printPath.substring(0, printPath.lastIndexOf('.')-1) + "-with-line-numbers.txt";
    }

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
