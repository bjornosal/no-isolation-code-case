package codecase;

import java.util.ArrayList;

public class ExecutionHandler {

    private FileHandler fileHandler;
    private NormalizationHandler normalizationHandler;
    private ArrayList<String> phoneNumbersToNormalize;
    private ArrayList<String> normalizedPhoneNumbers;

    public ExecutionHandler() {
        fileHandler = new FileHandler();
        normalizationHandler = new NormalizationHandler();
        phoneNumbersToNormalize = new ArrayList<>();
        normalizedPhoneNumbers = new ArrayList<>();
    }

    public void runApplication() {

    }
}
