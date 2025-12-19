package com.cryptochecker.Integration;

import com.cryptochecker.Integration.APIToWebDataIntegrationTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Phase 3 - Integration Testing Suite")
@SelectClasses({
    APIToWebDataIntegrationTest.class,
    com.cryptochecker.Integration.DataToConverterIntegrationTest.class,
    com.cryptochecker.Integration.SearchToDataIntegrationTest.class
})
public class IntegrationTestSuite {
}
