package com.tennisKata.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame ;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tennisKata.models.MyEntity;

public abstract class AbstractDAOTest<T extends MyEntity, K extends AbstractDAO<T>> {
	
	@Autowired
	protected K dao;
	
	@Test
	public void testSaveEntity(){
		
		T entity = buildEntity();
		T newEntity = dao.save(entity);
		assertNotNull(newEntity);
		assertNotNull(newEntity.getId());
		assertNotSame(0, newEntity.getId());
	
	}
	
	@Test
	public void testFindEntityById(){
		
		T entity = buildEntity();
		T newEntity = dao.save(entity);
		
		T found = dao.findById(entity.getId());
		assertNotNull(found);
		assertEquals(newEntity.getId(), found.getId());
	
	}
	
	//test
	
	//Api
	protected abstract T buildEntity();
	

}
