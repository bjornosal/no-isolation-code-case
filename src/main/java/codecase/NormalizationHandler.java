package codecase;

public class NormalizationHandler {

    public NormalizationHandler() {
    }

    private boolean phoneNumberNeedsNormalization(String phoneNumber) {
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

        if(getCountryCodeFromPhoneNumber(tempNumber).equals("47")) {
            country = Country.NOR;
            if(tempNumber.length() != 14){
                return true;
            }
        } else if(getCountryCodeFromPhoneNumber(tempNumber).equals("46")) {
            country = Country.SWE;
            if(tempNumber.length() != 21) {
                return true;
            }
            if(!(tempNumber.substring(4,5).equals("(") && tempNumber.substring(9,10).equals(")"))) {
                return true;
            } else {
                //TODO test this
                tempNumber = tempNumber.replaceAll("\\(|\\)+", "");
            }
        } else if(getCountryCodeFromPhoneNumber(tempNumber).equals("45")) {
            country = Country.DEN;
            if(tempNumber.length() != 15) {
                return true;
            }
        } else {
            return true;
        }

        if(!phoneNumberIsOnlyNumbers(tempNumber, country)) {
            return true;
        }

        return false;
    }

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
            return normalizeNorwegianPhoneNumber(tempNumber);
        } else if(country == Country.SWE) {
            return normalizeSwedishPhoneNumber(tempNumber);
        } else if(country == Country.DEN) {
            return normalizeDanishPhoneNumber(tempNumber);
        }

        return phoneNumber;

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
    protected boolean lengthOfPhoneNumberIsCorrect(String phoneNumber, Country country) {

        switch (country) {
            case NOR:
                return phoneNumber.length() == 14;
            case SWE:
                return phoneNumber.length() == 21;
            case DEN:
                return phoneNumber.length() == 15;
            default:
                return false;
        }
    }

    private boolean lengthOfPhoneNumberIsWithinLengthRequirements(String phoneNumber, Country country) {
        String tempNumber = removeWhiteSpaces(phoneNumber);
        switch (country) {
            case NOR:
                return (tempNumber.length() == 11) || (tempNumber.length() == 12);
            case SWE:
                return (tempNumber.length() >= 12) && (tempNumber.length() <= 21);
            case DEN:
                return (tempNumber.length() == 11) || (tempNumber.length() == 12);
            default:
                return false;
        }
    }

    protected boolean countryCodeIndicatorIsPlusCharacter(String phoneNumber) {
        return phoneNumber.charAt(0) == '+';
    }

    protected String refactorCountryCodeIndicator(String phoneNumber) {
        if(phoneNumber.length() > 0) {
            return "+" + phoneNumber.substring(2, phoneNumber.length());
        }
        return "";
    }

    private boolean phoneNumberIsEmpty(String phoneNumber) {
        return phoneNumber.replaceAll("\\s+", "").isEmpty();
    }

    protected boolean phoneNumberIsOnlyNumbers(String phoneNumber, Country country) {
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

    protected String getCountryCodeFromPhoneNumber(String phoneNumber) {
        return phoneNumber.substring(1,3);
    }

    protected boolean isCountryCodeCorrect(String countryCode) {
        return countryCode.equals("47") || countryCode.equals("46") || countryCode.equals("45");
    }

    private String removeWhiteSpaces(String phoneNumber) {
        return phoneNumber.replaceAll("\\s+","");
    }

    //TODO needs refactoring to work
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
