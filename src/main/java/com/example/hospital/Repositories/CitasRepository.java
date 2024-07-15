package com.example.hospital.Repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.hospital.Models.CitaModel;

import jakarta.persistence.EntityManager;

@Repository
public class CitasRepository {
    @Autowired
    EntityManager entityManager;

    public List<CitaModel> obtenerTodas() {
        return entityManager.createNativeQuery("SELECT * FROM citas", CitaModel.class).getResultList();
    }

}
