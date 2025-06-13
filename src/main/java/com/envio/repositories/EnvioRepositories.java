package com.envio.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.envio.models.EnvioModels;

@Repository
public interface EnvioRepositories extends JpaRepository<EnvioModels, Integer> {

    

}
