package codecase;

public class NormalizationHandler {

    public NormalizationHandler() {
    }

    private boolean phoneNumberNeedsNormalization(String phoneNumber) {
        String tempNumber = phoneNumber;
        Country country = null;
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

    //TODO add check if line is empty
    //TODO need to add check if phone number was correct
    public String normalizePhoneNumber(String phoneNumber) {

        if(phoneNumberIsEmpty(phoneNumber)) {
            return phoneNumber;
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
        return phoneNumber.length() > 0 && phoneNumber.charAt(0) == '+';
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

    //TODO Requires fixing
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
}
