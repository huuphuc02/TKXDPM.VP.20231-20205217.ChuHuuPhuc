import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ 
    ValidatePhoneNumberTest.class, 
    ValidateNameTest.class, 
    ValidateAddressTest.class,
    ValidatePlaceRushAddressTest.class,
    ValidateMediaPlaceRushOrderTest.class,
        getSubTotalTest.class,
        getAmountTest.class,
        getTotalMediaTest.class,
        checkMediaInCartTest.class,
        calSubtotalTest.class
})
public class AllTests {

}
