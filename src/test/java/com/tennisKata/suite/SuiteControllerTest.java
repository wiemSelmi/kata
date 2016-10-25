package com.tennisKata.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.tennisKata.controllers.PlayerControllerTest;

@RunWith(Suite.class)
@SuiteClasses({ PlayerControllerTest.class })
public class SuiteControllerTest {

}
