package com.rhtinterprise.RHTcatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rhtinterprise.RHTcatalog.dto.CategoryDTO;
import com.rhtinterprise.RHTcatalog.entities.Category;
import com.rhtinterprise.RHTcatalog.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {
		List<Category> list = repository.findAll(); 
		
		return list.stream().map(x -> new CategoryDTO(x)).toList();
		
		
	}

}
