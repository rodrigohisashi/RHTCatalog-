package com.rhtinterprise.RHTcatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rhtinterprise.RHTcatalog.dto.CategoryDTO;
import com.rhtinterprise.RHTcatalog.entities.Category;
import com.rhtinterprise.RHTcatalog.exceptions.DataBaseException;
import com.rhtinterprise.RHTcatalog.exceptions.ResourceNotFoundException;
import com.rhtinterprise.RHTcatalog.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {
		List<Category> list = repository.findAll(); 
		
		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
		
	}
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(long id) {
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entidade não encontrada!"));
		return new CategoryDTO(entity);		
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CategoryDTO(entity);
	}
	
	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			Category entity = repository.getById(id);
			entity.setName(dto.getName());;
			entity = repository.save(entity);
			return new CategoryDTO(entity);
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("O id " + id + " não foi encontrado." );
		}

	}

	public void delete(long id) {
		try {
			repository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("O id " + id + " não foi encontrado." );
			
		} catch(DataIntegrityViolationException e) { 
			throw new DataBaseException("Violação de integridade no banco de dados");
		}
	}

}
