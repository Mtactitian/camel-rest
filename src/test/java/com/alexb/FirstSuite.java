package com.alexb;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.runner.RunWith;

@Ru nWith(JUnitPlatform.class)
@SuiteDisplayName("First Pack of Tests")
@SelectClasses(value = RouteTest.class)
public class FirstSuite {
}
