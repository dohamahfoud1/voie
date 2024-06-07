package com.voie.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.voie.project.models.SpecificPermissions;


public interface specificPermissionRepository extends JpaRepository<SpecificPermissions, Long> {
    List<SpecificPermissions> findByPermissionId(Long permissionId);
}
