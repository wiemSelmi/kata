package com.tennisKata.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tennisKata.models.MyEntity;

@Repository
public abstract interface AbstractDAO<T extends MyEntity> extends JpaRepository<T, Serializable> {
	T findById(Integer id);
}
