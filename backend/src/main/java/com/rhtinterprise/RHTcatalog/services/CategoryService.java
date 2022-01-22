package com.rhtinterprise.RHTcatalog.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhtinterprise.RHTcatalog.entities.Category;
import com.rhtinterprise.RHTcatalog.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	
	public List<Category> findAll() {
		return repository.findAll();
		
	}

}
