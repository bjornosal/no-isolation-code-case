package codecase;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static codecase.Country.*;

public class PhoneNumberGenerator {

    private Set<String> phoneNumbers;
    private Random random;
    private final int MAX_AMOUNT_OF_SPACES;

    public PhoneNumberGenerator() {
        phoneNumbers = new HashSet<>();
        random = new Random();
        MAX_AMOUNT_OF_SPACES = 3;
    }


    /**
     * Generates given amount of phone numbers
     * @param amountOfPhoneNumbers to produce
     */
    public void generateSetAmountOfPhoneNumbers(int amountOfPhoneNumbers) {
        while(phoneNumbers.size() < amountOfPhoneNumbers) {

            int typeOfPhoneNumber = random.nextInt(4);

            switch (typeOfPhoneNumber) {
                case 0:
                    phoneNumbers.add(generateNorwegianPhoneNumber());
                    break;
                case 1:
                    phoneNumbers.add(generateSwedishPhoneNumber());
                    break;
                case 3:
                    phoneNumbers.add(generateDanishPhoneNumber());
                    break;
                case 4:
                    phoneNumbers.add(generatePhoneNumberWhichShouldProduceErrors());
                    break;
                default:
                    phoneNumbers.add(generateNorwegianPhoneNumber());
                    break;
            }
        }
    }

    /**
     * Generates norwegian phone number
     * @return phone number
     */
    private String generateNorwegianPhoneNumber() {
        return addCountryCodeToPhoneNumber(NOR, generatePhoneNumber());
    }

    /**
     * Generates swedish phone number
     * @return phone number
     */
    private String generateSwedishPhoneNumber() {
        return addCountryCodeToPhoneNumber(SWE, addSwedishRegionalCode(generatePhoneNumber()));
    }

    /**
     * Generates danish phone number
     * @return phone number
     */
    private String generateDanishPhoneNumber() {
        return addCountryCodeToPhoneNumber(DEN, generatePhoneNumber());
    }

    /**
     * Generating phone number which should not be normalized
     * @return phone number generated
     */
    private String generatePhoneNumberWhichShouldProduceErrors() {
        String result = generatePhoneNumber();
        result = result.substring(random.nextInt(result.length()), result.length());

        int addCountryCodeOrNot = random.nextInt(2);

        switch (addCountryCodeOrNot) {
            case 0:
                return result;
            case 1:
                return addCountryCodeToPhoneNumber(NOR, result);
            default:
                return addCountryCodeToPhoneNumber(SWE, result);
        }
    }

    /**
     * Generates a random phone number
     * @return returns phone number generated
     */
    private String generatePhoneNumber() {
        StringBuilder sb = new StringBuilder();
        while(sb.length() < 8) {
            sb.append(random.nextInt(10));
        }

        return addRandomSpacesToPhoneNumber(sb.toString());
    }

    /**
     * Adds a swedish regional code to a number
     * @param phoneNumber to add to
     * @return phonenumber with added regional code
     */
    private String addSwedishRegionalCode(String phoneNumber) {
        int amountOfRegionalNumbers = random.nextInt(5);

        StringBuilder sb = new StringBuilder();
        while(sb.length() < amountOfRegionalNumbers) {
            sb.append(random.nextInt(10));
        }

        return sb.append(phoneNumber).toString();
    }

    /**
     * Adds random spaces to a phone number
     * @param phoneNumber to add to
     * @return phone number with added spaces
     */
    private String addRandomSpacesToPhoneNumber(String phoneNumber) {
        int amountOfSpacesToBeAdded = random.nextInt(MAX_AMOUNT_OF_SPACES);

        String result = phoneNumber;
        int amountOfSpacesAdded = 0;

        for(int i = 2; i < result.length(); i+=2) {

            if(amountOfSpacesAdded < amountOfSpacesToBeAdded) {
                result = result.substring(0, i) + " " + result.substring(i, phoneNumber.length());
                amountOfSpacesAdded++;
            } else {
                return result;
            }
        }
        return result;
    }

    /**
     * Adds country code to phone number
     * @param country which countrycode to add
     * @param phoneNumber to add to
     * @return phone number with country code
     */
    private String addCountryCodeToPhoneNumber(Country country, String phoneNumber) {

        switch (country) {
            case NOR:
                return addCountryCodeIndicatorToPhoneNumber("47"+phoneNumber);
            case SWE:
                return addCountryCodeIndicatorToPhoneNumber("46"+phoneNumber);
            case DEN:
                return addCountryCodeIndicatorToPhoneNumber("45"+phoneNumber);
            default:
                return phoneNumber;
        }
    }

    /**
     * Add country code indicator (+ or 00) to phone number
     * @param phoneNumber to add to
     * @return phone number with added country code indicator
     */
    private String addCountryCodeIndicatorToPhoneNumber(String phoneNumber) {
        int type = random.nextInt(2);

        switch (type) {
            case 0:
                return "00"+phoneNumber;
            case 1:
                return "+"+phoneNumber;

            default:
                return phoneNumber;
        }
    }

    public Set<String> getPhoneNumbers() {
        return phoneNumbers;
    }
}
