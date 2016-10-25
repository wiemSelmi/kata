package com.tennisKata.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.tennisKata.services.GameServiceTest;
import com.tennisKata.services.MatchServiceTest;
import com.tennisKata.services.PlayerServiceTest;
import com.tennisKata.services.SetServiceTest;

@RunWith(Suite.class)
@SuiteClasses({
	
	PlayerServiceTest.class, 
	GameServiceTest.class,
	SetServiceTest.class,
	MatchServiceTest.class
	
	})

public class SuiteServiceTest {

}
