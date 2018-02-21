package codecase;

public class NormalizationHandler {

    public NormalizationHandler() {
    }

    /**
     * Checks if a phone number needs to be normalized
     * @param phoneNumber phone number to check
     * @return boolean if it needs to be normalized
     */
    public boolean phoneNumberNeedsNormalization(String phoneNumber) {
        String tempNumber = phoneNumber;
        Country country;

        if(phoneNumberIsEmpty(tempNumber)) {
            return true;
        }

        if(!countryCodeIndicatorIsPlusCharacter(tempNumber)) {
            return true;
        }

        if(!isCountryCodeCorrect(getCountryCodeFromPhoneNumber(tempNumber))) {
            return true;
        }

        switch (getCountryCodeFromPhoneNumber(tempNumber)) {
            case "47":
                country = Country.NOR;
                if (!lengthOfPhoneNumberIsCorrect(tempNumber, country)) {
                    return true;
                }
                break;
            case "46":
                country = Country.SWE;
                if (!lengthOfPhoneNumberIsCorrect(tempNumber, country)) {
                    return true;
                }
                if (!(tempNumber.substring(4, 5).equals("(") && tempNumber.substring(9, 10).equals(")"))) {
                    return true;
                } else {
                    tempNumber = tempNumber.replaceAll("\\(|\\)+", "");
                }
                break;
            case "45":
                country = Country.DEN;
                if (!lengthOfPhoneNumberIsCorrect(tempNumber, country)) {
                    return true;
                }
                break;
            default:
                return true;
        }

        return !phoneNumberIsOnlyNumbers(tempNumber, country);

    }


    /**
     * Normalizes a phone number
     * @param phoneNumber phone number to normalize
     * @return normalized phone number
     */
    public String normalizePhoneNumber(String phoneNumber) {

        String tempNumber = phoneNumber;

        if(phoneNumberIsEmpty(tempNumber)) {
            return phoneNumber;
        }

        if(!countryCodeIndicatorIsPlusCharacter(tempNumber)) {
            tempNumber = refactorCountryCodeIndicator(tempNumber);
        }

        Country country = getCountryCode(getCountryCodeFromPhoneNumber(tempNumber));

        if(country == null) {
            return phoneNumber;
        }

        if(!lengthOfPhoneNumberIsWithinLengthRequirements(tempNumber, country)) {
            return phoneNumber;
        }

        if(!phoneNumberIsOnlyNumbers(tempNumber, country)) {
            return phoneNumber;
        }

        tempNumber = removeWhiteSpaces(tempNumber);

        if(country == Country.NOR) {
            return addFormattingToNorwegianPhoneNumber(tempNumber);
        } else if(country == Country.SWE) {
            return addFormattingToSwedishPhoneNumber(tempNumber);
        } else if(country == Country.DEN) {
            return addFormattingToDanishPhoneNumber(tempNumber);
        }

        return phoneNumber;

    }


    /**
     * Formats a norwegian phone number
     * @param phoneNumber to format
     * @return formatted phone number
     */
    private String addFormattingToNorwegianPhoneNumber(String phoneNumber) {
        return new StringBuilder(phoneNumber).insert(3, " ").insert(7, " ").insert(10, " ").toString();
    }

    /**
     * Formats a swedish phone number
     * @param phoneNumber to format
     * @return formatted phone number
     */
    private String addFormattingToSwedishPhoneNumber(String phoneNumber) {

        if(swedishRegionalCodePaddingRequired(phoneNumber) == 4){
            return new StringBuilder(phoneNumber).insert(3, " (0) ").toString();
        }

        String result = addSwedishRegionalCode(phoneNumber);
        return new StringBuilder(result).insert(3," (").insert(9, ") ").toString();
    }

    /**
     * Formats a danish phone number
     * @param phoneNumber to format
     * @return formatted phone number
     */
    private String addFormattingToDanishPhoneNumber(String phoneNumber) {
        return new StringBuilder(phoneNumber).insert(3, " ").insert(6, " ").insert(9, " ").insert(12, " ").toString();
    }

    /**
     * Checks if the length of a phone number is correct
     * @param phoneNumber to check
     * @param country code to check for
     * @return if length is correct
     */
    private boolean lengthOfPhoneNumberIsCorrect(String phoneNumber, Country country) {

        switch (country) {
            case NOR:
                return phoneNumber.length() == 14;
            case SWE:
                return phoneNumber.length() == 19;
            case DEN:
                return phoneNumber.length() == 15;
            default:
                return false;
        }
    }

    /**
     * Checks if the length is within the requirements for a phone number (Not too short/too long).
     * @param phoneNumber to check
     * @param country to check for
     * @return if length is within parameters
     */
    private boolean lengthOfPhoneNumberIsWithinLengthRequirements(String phoneNumber, Country country) {
        String tempNumber = removeWhiteSpaces(phoneNumber);
        switch (country) {
            case NOR:
                return (tempNumber.length() == 11) || (tempNumber.length() == 12);
            case SWE:
                return (tempNumber.length() >= 11) && (tempNumber.length() <= 21);
            case DEN:
                return (tempNumber.length() == 11) || (tempNumber.length() == 12);
            default:
                return false;
        }
    }

    /**
     * Checks if country code indicator is plus character
     * @param phoneNumber to check
     * @return if country code is '+'
     */
    private boolean countryCodeIndicatorIsPlusCharacter(String phoneNumber) {
        return phoneNumber.charAt(0) == '+';
    }

    /**
     * Refactors country code indicator
     * @param phoneNumber to refactor
     * @return phone number with '+' instead of '00'
     */
    private String refactorCountryCodeIndicator(String phoneNumber) {
        if(phoneNumber.length() > 0) {
            return "+" + phoneNumber.substring(2, phoneNumber.length());
        }
        return "";
    }

    /**
     * Checks if phone Number is empty
     * @param phoneNumber to check
     * @return if number is empty
     */
    private boolean phoneNumberIsEmpty(String phoneNumber) {
        return phoneNumber.replaceAll("\\s+", "").isEmpty();
    }

    /**
     * checks if phone number is only numbers
     * @param phoneNumber to check
     * @param country to check for
     * @return if phone number is only numbers
     */
    private boolean phoneNumberIsOnlyNumbers(String phoneNumber, Country country) {
        String tempNumber = removeWhiteSpaces(phoneNumber);
        if(country == Country.SWE) {
            if (tempNumber.chars().filter(ch -> ch == '(').count() > 1) {
                return false;
            } else if (tempNumber.chars().filter(ch -> ch == ')').count() > 1) {
                return false;
            } else {
                tempNumber = tempNumber.replaceAll("\\(|\\)+", "");
            }
        }

        return tempNumber.substring(1).matches("[0-9]+");
    }

    /**
     * Gets a country code from a given phone number
     * @param phoneNumber to get from
     * @return country code
     */
    private String getCountryCodeFromPhoneNumber(String phoneNumber) {
        return phoneNumber.substring(1,3);
    }

    /**
     * Checks if country code is within the parameters
     * @param countryCode code to check
     * @return if country code is correct
     */
    private boolean isCountryCodeCorrect(String countryCode) {
        return countryCode.equals("47") || countryCode.equals("46") || countryCode.equals("45");
    }

    /**
     * Removes all whitespaces in a phone number.
     * @param phoneNumber to check
     * @return phone number without white spaces.
     */
    private String removeWhiteSpaces(String phoneNumber) {
        return phoneNumber.replaceAll("\\s+","");
    }

    /**
     * Checks how much padding is required for a swedish number.
     * @param phoneNumber to check
     * @return amount of padding
     */
    private int swedishRegionalCodePaddingRequired(String phoneNumber) {
        String tempNumber = removeWhiteSpaces(phoneNumber.replaceAll("\\(|\\)+", "").substring(1));

        return 14 - tempNumber.length();
    }

    /**
     * Adds a regional code to a swedish number
     * @param phoneNumber to add to
     * @return phone number with regional code
     */
    private String addSwedishRegionalCode(String phoneNumber) {
        StringBuilder stringBuilder = new StringBuilder(phoneNumber);
        for(int i = 0; i < swedishRegionalCodePaddingRequired(phoneNumber); i++) {
            stringBuilder.insert(3, "0");
        }
        return stringBuilder.toString();
    }

    /**
     * Enum country based on country code
     * @param countryCode to get from
     * @return Enum Country specified for code.
     */
    private Country getCountryCode(String countryCode) {
        switch (countryCode) {
            case "47":
                return Country.NOR;
            case "46":
                return Country.SWE;
            case "45":
                return Country.DEN;
            default:
                return null;
        }
    }
}
