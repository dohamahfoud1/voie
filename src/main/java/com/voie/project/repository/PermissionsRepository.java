package com.voie.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voie.project.models.Permissions;

public interface PermissionsRepository extends JpaRepository<Permissions, Long> {
    List<Permissions> findByIdIn(List<Long> id);

}
