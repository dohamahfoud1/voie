package com.voie.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.voie.project.models.Client;

public interface ClientRepository  extends JpaRepository<Client, Long> {

}
