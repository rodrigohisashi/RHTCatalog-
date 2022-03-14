package com.rhtinterprise.RHTcatalog.repositories;

import com.rhtinterprise.RHTcatalog.entities.Role;
import com.rhtinterprise.RHTcatalog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
