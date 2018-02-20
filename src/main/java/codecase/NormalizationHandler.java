package codecase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class NormalizationHandler {

    public NormalizationHandler() {
    }

    public String normalizePhoneNumber(String phoneNumber) {
        String normalizedPhoneNumber = phoneNumber;
        if(lengthOfPhoneNumberIsHighEnough(normalizedPhoneNumber)){
            if(!countryCodeIndicatorIsPlusCharacter(normalizedPhoneNumber)) {
                normalizedPhoneNumber = refactorCountryCodeIndicator(normalizedPhoneNumber);
            }

            if(!phoneNumberIsOnlyNumbers(normalizedPhoneNumber)) {
                return phoneNumber;
            }

            if(!isCountryCodeCorrect(getCountryCodeFromPhoneNumber(normalizedPhoneNumber))) {
                return phoneNumber;
            }

            if(getCountryCodeFromPhoneNumber(normalizedPhoneNumber).equals("47")) {
                return normalizeNorwegianPhoneNumber(normalizedPhoneNumber);
            } else if(getCountryCodeFromPhoneNumber(normalizedPhoneNumber).equals("46")) {
                return normalizeSwedishPhoneNumber(normalizedPhoneNumber);
            } else if(getCountryCodeFromPhoneNumber(normalizedPhoneNumber).equals("45")) {
                return normalizeDanishPhoneNumber(normalizedPhoneNumber);
            }
        }
        return phoneNumber;

        /*
        Rules:
        has to have length above 8(phonenumber) + 2(countrycode) + 1(Least size of indicator) = 11
        If 00 -> +
        substring(1,length) should be able to parse to number, else, not number

        Check that it has country code of either country
        --> When finished checking this do as follows
        Check which country it is.
        Normalize as per country
         */
    }



    protected String normalizeNorwegianPhoneNumber(String phoneNumber) {
        return new StringBuilder(phoneNumber).insert(3, " ").insert(7, " ").insert(10, " ").toString();
    }

    protected String normalizeSwedishPhoneNumber(String phoneNumber) {

        if(swedishRegionalCodePaddingRequired(phoneNumber) == 4){
            return new StringBuilder(phoneNumber).insert(3, " (0) ").toString();
        }

        String result = addSwedishRegionalCode(phoneNumber);
        return new StringBuilder(result).insert(3," (").insert(9, ") ").toString();
    }

    protected String normalizeDanishPhoneNumber(String phoneNumber) {
        return new StringBuilder(phoneNumber).insert(3, " ").insert(6, " ").insert(9, " ").insert(12, " ").toString();
    }
    /**
     * Checks if length of number is larger than or equal to 11. (8 + 2 + 1)
     * 8: Phone number
     * 2: Country code
     * 1: Least size of country code indicator
     *
     * @param phoneNumber to check
     * @return if big enough number.
     */
    protected boolean lengthOfPhoneNumberIsHighEnough(String phoneNumber) {
        return phoneNumber.length() >= 11;
    }

    protected boolean countryCodeIndicatorIsPlusCharacter(String phoneNumber) {
        return phoneNumber.charAt(0) == '+';
    }

    protected String refactorCountryCodeIndicator(String phoneNumber) {
        return "+" + phoneNumber.substring(2, phoneNumber.length());
    }

    protected boolean phoneNumberIsOnlyNumbers(String phoneNumber) {
        String phoneNumberWithoutSpaces = phoneNumber.substring(1,phoneNumber.length()).replaceAll("\\s+","");
        try {
            Integer.parseInt(phoneNumberWithoutSpaces);
        } catch(NumberFormatException e) {
            return false;
        }
        return true;
    }

    protected String getCountryCodeFromPhoneNumber(String phoneNumber) {
        return phoneNumber.substring(1,3);
    }

    protected boolean isCountryCodeCorrect(String countryCode) {
        return countryCode.equals("47") || countryCode.equals("46") || countryCode.equals("45");
    }

    private int swedishRegionalCodePaddingRequired(String phoneNumber) {
        return 15 - phoneNumber.length();
    }

    private String addSwedishRegionalCode(String phoneNumber) {
        StringBuilder stringBuilder = new StringBuilder(phoneNumber);
        for(int i = 0; i < swedishRegionalCodePaddingRequired(phoneNumber); i++) {
            stringBuilder.insert(3, "0");
        }
        return stringBuilder.toString();
    }
}
