package codecase;

import java.util.ArrayList;

public class ExecutionHandler {

    private FileHandler fileHandler;
    private NormalizationHandler normalizationHandler;
    private ArrayList<String> phoneNumbersToNormalize;
    private ArrayList<String> normalizedPhoneNumbers;
    private ArrayList<String> phoneNumbersUnableToParse;
    private String printPath;

    public ExecutionHandler(String filePath, String printPath) {
        fileHandler = new FileHandler();
        normalizationHandler = new NormalizationHandler();
        phoneNumbersToNormalize = fileHandler.getPhoneNumbersFromFile(filePath);
        normalizedPhoneNumbers = new ArrayList<>();
        phoneNumbersUnableToParse = new ArrayList<>();
        this.printPath = printPath;
    }

    public void runApplication() {
        normalizeAllPhoneNumbers();
        fileHandler.printArrayListToFile(normalizedPhoneNumbers, printPath);
    }

    private void normalizeAllPhoneNumbers() {

        for(int i = 0; i < phoneNumbersToNormalize.size(); i++) {
            String normalizedPhoneNumber = normalizationHandler.normalizePhoneNumber(phoneNumbersToNormalize.get(i));
            if(phoneNumbersToNormalize.get(i).equals(normalizedPhoneNumber)) {
                phoneNumbersUnableToParse.add("Line: " + (i+1) +" => "+ normalizedPhoneNumber);
            }
            normalizedPhoneNumbers.add(normalizedPhoneNumber);
        }

        normalizedPhoneNumbers.addAll(phoneNumbersUnableToParse);
    }

}
