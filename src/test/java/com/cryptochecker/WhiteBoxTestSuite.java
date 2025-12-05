package com.cryptochecker;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("White Box Testing Suite - All Core Modules")
@SelectClasses({
    APIServiceTest.class,
    ConversionUtilTest.class,
    SearchUtilTest.class,
    GUITest.class,
    PanelPortfolioTest.class
})
public class WhiteBoxTestSuite {
}
