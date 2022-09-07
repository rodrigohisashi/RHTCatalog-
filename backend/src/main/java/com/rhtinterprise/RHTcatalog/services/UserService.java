package com.rhtinterprise.RHTcatalog.services;

import com.rhtinterprise.RHTcatalog.dto.RoleDTO;
import com.rhtinterprise.RHTcatalog.dto.UserDTO;
import com.rhtinterprise.RHTcatalog.dto.UserInsertDTO;
import com.rhtinterprise.RHTcatalog.dto.UserUpdateDTO;
import com.rhtinterprise.RHTcatalog.entities.Role;
import com.rhtinterprise.RHTcatalog.entities.User;
import com.rhtinterprise.RHTcatalog.exceptions.DataBaseException;
import com.rhtinterprise.RHTcatalog.exceptions.ResourceNotFoundException;
import com.rhtinterprise.RHTcatalog.repositories.RoleRepository;
import com.rhtinterprise.RHTcatalog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public Page<UserDTO> findAllPaged(Pageable pageable) {
		Page<User> list = repository.findAll(pageable);
		
		return list.map(x -> new UserDTO(x));
		
	}
	
	@Transactional(readOnly = true)
	public UserDTO findById(long id) {
		Optional<User> obj = repository.findById(id);
		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entidade não encontrada!"));
		return new UserDTO(entity);
	}

	@Transactional
	public UserDTO insert(UserInsertDTO dto) {
		User entity = new User();
		setUser(dto, entity);
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		entity = repository.save(entity);
		return new UserDTO(entity);
	}
	
	@Transactional
	public UserDTO update(Long id, UserUpdateDTO dto) {
		try {
			User entity = repository.getById(id);
			setUser(dto, entity);
			entity = repository.save(entity);
			return new UserDTO(entity);
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

	private void setUser(UserDTO userDTO, User entity) {
		entity.setFirstName(userDTO.getFirstName());
		entity.setLastName(userDTO.getLastName());
		entity.setEmail(userDTO.getEmail());

		entity.getRoles().clear();
		for (RoleDTO roleDTO : userDTO.getRoles()) {
			Role roles = roleRepository.getById(roleDTO.getId());
			entity.getRoles().add(roles);
		}
	}

}
