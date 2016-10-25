package com.tennisKata.dao;

import com.tennisKata.models.Set;


public interface SetDAO extends AbstractDAO<Set> {

	Set findById(Integer id);
}
