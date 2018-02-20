package codecase;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static codecase.CountryCode.*;

public class PhoneNumberGenerator {

    private Set<String> phoneNumbers;
    private Random random;
    private final int MAX_AMOUNT_OF_SPACES;

    public PhoneNumberGenerator() {
        phoneNumbers = new HashSet<>();
        random = new Random();
        MAX_AMOUNT_OF_SPACES = 3;
    }

    public void generateSetAmountOfPhoneNumbers(int amountOfPhoneNumbers) {
        while(phoneNumbers.size() < amountOfPhoneNumbers) {

            int typeOfPhoneNumber = random.nextInt(4);
            //TODO add counters for each type added.
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

    private String generateNorwegianPhoneNumber() {
        return addCountryCodeToPhoneNumber(NOR, generatePhoneNumber());
    }

    private String generateSwedishPhoneNumber() {
        return addCountryCodeToPhoneNumber(SWE, addSwedishRegionalCode(generatePhoneNumber()));
    }

    private String generateDanishPhoneNumber() {
        return addCountryCodeToPhoneNumber(DEN, generatePhoneNumber());
    }

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

    private String generatePhoneNumber() {
        StringBuilder sb = new StringBuilder();
        while(sb.length() < 8) {
            sb.append(random.nextInt(10));
        }

        return addRandomSpacesToPhoneNumber(sb.toString());
    }

    private String addSwedishRegionalCode(String phoneNumber) {
        int amountOfRegionalNumbers = random.nextInt(5);

        StringBuilder sb = new StringBuilder();
        while(sb.length() < amountOfRegionalNumbers) {
            sb.append(random.nextInt(10));
        }

        return sb.append(phoneNumber).toString();
    }

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

    private String addCountryCodeToPhoneNumber(CountryCode countryCode, String phoneNumber) {

        switch (countryCode) {
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
