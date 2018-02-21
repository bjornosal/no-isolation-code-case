package codecase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NormalizationHandlerTest {

    private NormalizationHandler nh;

    @Before
    public void setUp() {
        nh = new NormalizationHandler();
    }

    @Test
    public void testPhoneNumberContainingDotIsReturnedAsIs() {
        String phoneNumber = "+479176.1030";

        assertTrue(nh.phoneNumberNeedsNormalization(phoneNumber));
        assertEquals(phoneNumber, nh.normalizePhoneNumber(phoneNumber));
    }

    @Test
    public void testPhoneNumberContainingWrongCountryCodeIsReturnedAsIs() {
        String phoneNumber = "+4491761030";

        assertTrue(nh.phoneNumberNeedsNormalization(phoneNumber));
        assertEquals(phoneNumber, nh.normalizePhoneNumber(phoneNumber));
    }

    @Test
    public void testPhoneNumberContainingExtraWhiteSpacesIsNormalizedCorrectly() {
        String phoneNumber = "+47 91   7 6   1   0 3    0  ";
        String expectedResult = "+47 917 61 030";

        assertTrue(nh.phoneNumberNeedsNormalization(phoneNumber));
        assertEquals(expectedResult, nh.normalizePhoneNumber(phoneNumber));
    }

    @Test
    public void testPhoneNumberContainingLettersIsReturnedAsIs() {
        String phoneNumber = "+4L9IL6l0E0";

        assertTrue(nh.phoneNumberNeedsNormalization(phoneNumber));
        assertEquals(phoneNumber, nh.normalizePhoneNumber(phoneNumber));
    }

    @Test
    public void testPhoneNumberContainingNoCountryCodeIndicatorIsReturnedAsIs() {
        String phoneNumber = "4791761030";

        assertTrue(nh.phoneNumberNeedsNormalization(phoneNumber));
        assertEquals(phoneNumber, nh.normalizePhoneNumber(phoneNumber));
    }

    @Test
    public void testPhoneNumberContainingNoCountryCodeIsReturnedAsIs() {
        String phoneNumber = "91761030";

        assertTrue(nh.phoneNumberNeedsNormalization(phoneNumber));
        assertEquals(phoneNumber, nh.normalizePhoneNumber(phoneNumber));
    }

    @Test
    public void testPhoneNumberNotContainingEnoughNumbersIsReturnedAsIs() {
        String phoneNumber = "+4761030";

        assertTrue(nh.phoneNumberNeedsNormalization(phoneNumber));
        assertEquals(phoneNumber, nh.normalizePhoneNumber(phoneNumber));
    }

    @Test
    public void testPhoneNumberContainingTooManyNumbersIsReturnedAsIs() {
        String phoneNumber = "+4512345678987654321234567890";

        assertTrue(nh.phoneNumberNeedsNormalization(phoneNumber));
        assertEquals(phoneNumber, nh.normalizePhoneNumber(phoneNumber));
    }

    @Test
    public void testSwedishPhoneNumberWithNoRegionalCodeIsReturnedWithZeroAsRegionalCode() {
        String phoneNumber = "+4612345678";
        String expectedResult = "+46 (0) 12345678";

        assertTrue(nh.phoneNumberNeedsNormalization(phoneNumber));
        assertEquals(expectedResult, nh.normalizePhoneNumber(phoneNumber));
    }

    @Test
    public void testSwedishPhoneNumberWithTwoDigitsInRegionalCodeIsReturnedWithPadding() {
        String phoneNumber = "+467812345678";
        String expectedResult = "+46 (0078) 12345678";

        assertTrue(nh.phoneNumberNeedsNormalization(phoneNumber));
        assertEquals(expectedResult, nh.normalizePhoneNumber(phoneNumber));
    }


    @Test
    public void testSwedishNumberWithAllPaddingPresentIsNormalizedAsShould() {
        String phoneNumber = "+46117812345678";
        String expectedResult = "+46 (1178) 12345678";

        assertTrue(nh.phoneNumberNeedsNormalization(phoneNumber));
        assertEquals(expectedResult, nh.normalizePhoneNumber(phoneNumber));
    }

    @Test
    public void testCorrectDanishNumberIsNotInNeedOfNormalization() {
        String phoneNumber = "+45 22 44 66 88";

        assertFalse(nh.phoneNumberNeedsNormalization(phoneNumber));
    }

    @Test
    public void testCorrectSwedishNumberIsNotInNeedOfNormalization() {
        String phoneNumber = "+46 (0078) 12345678";

        assertFalse(nh.phoneNumberNeedsNormalization(phoneNumber));
    }

    @Test
    public void testCorrectNorwegianNumberIsNotInNeedOfNormalization() {
        String phoneNumber = "+47 911 45 225";

        assertFalse(nh.phoneNumberNeedsNormalization(phoneNumber));
    }

    @After
    public void tearDown() {
        nh = null;
    }

}
