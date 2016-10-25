package com.tennisKata.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.tennisKata.dao.GameDAOTest;
import com.tennisKata.dao.MatchDAOTest;
import com.tennisKata.dao.PlayerDAOTest;
import com.tennisKata.dao.SetDAOTest;

@RunWith(Suite.class)
@SuiteClasses({
	
	PlayerDAOTest.class, 
	GameDAOTest.class,
	SetDAOTest.class,
	MatchDAOTest.class
	
	})

public class SuiteDAOTest {

}
