package com.newtech.clientRegister.repository;

import com.newtech.clientRegister.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRespository extends JpaRepository<Client,Long> {
}
