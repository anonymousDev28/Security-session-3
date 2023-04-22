package com.techmaster.securitysession3.repository;

import com.techmaster.securitysession3.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}