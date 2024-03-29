package com.rhtinterprise.RHTcatalog.services;

import com.rhtinterprise.RHTcatalog.dto.CategoryDTO;
import com.rhtinterprise.RHTcatalog.entities.Category;
import com.rhtinterprise.RHTcatalog.exceptions.DataBaseException;
import com.rhtinterprise.RHTcatalog.exceptions.ResourceNotFoundException;
import com.rhtinterprise.RHTcatalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(Pageable pageable) {
		Page<Category> list = repository.findAll(pageable);
		
		return list.map(x -> new CategoryDTO(x));
		
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
			Category entity = repository.findById(id).get();
			entity.setName(dto.getName());
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
