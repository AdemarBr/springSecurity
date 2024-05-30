package com.brad.jwt.repositories;

import com.brad.jwt.models.ERole;
import com.brad.jwt.models.RoleEntity;
import com.brad.jwt.models.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(ERole name);
}
