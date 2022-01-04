package com.example.demoSpring.repository;

import com.example.demoSpring.model.Voce;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

//CRUD repository
//Tipo (Voce) tipo di key utilizzata (Long)
public interface VociRepo extends CrudRepository<Voce, Long> {
    ArrayList<Voce> findByNomeContainingOrCognomeContainingOrTelefonoContaining(String nome, String cognome, String telefono);

}
