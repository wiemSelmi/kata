package com.tennisKata.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	SuiteDAOTest.class,
	SuiteServiceTest.class,
	SuiteControllerTest.class
})
public class IntegrationSuiteTest {

}
