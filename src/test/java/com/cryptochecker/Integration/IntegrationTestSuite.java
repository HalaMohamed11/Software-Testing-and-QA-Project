package com.cryptochecker.integration;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Phase 3 - Integration Testing Suite")
@SelectClasses({
    APIToWebDataIntegrationTest.class,
    DataToConverterIntegrationTest.class,
    SearchToDataIntegrationTest.class
})
public class IntegrationTestSuite {
}
