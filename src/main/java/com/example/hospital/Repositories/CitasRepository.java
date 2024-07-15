package com.example.hospital.Repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.hospital.Models.CitaModel;

import jakarta.persistence.EntityManager;

@SuppressWarnings("unchecked")
@Repository
public class CitasRepository {
    @Autowired
    EntityManager entityManager;

    public List<CitaModel> obtenerTodas() {
        return entityManager.createNativeQuery("SELECT * FROM citas", CitaModel.class).getResultList();
    }

    public void guardarCita(CitaModel cita) {
        //idcita va con autoincrement
        entityManager.createNativeQuery(
                "INSERT INTO citas (idcita, idconsultorio, iddoctor, horario, idpaciente, fechacita) VALUES (null, :idconsultorio, :iddoctor, :horario, :idpaciente, :fechacita)")
                .setParameter("idconsultorio", cita.getIdconsultorio())
                .setParameter("iddoctor", cita.getIddoctor())
                .setParameter("horario", cita.getHorario())
                .setParameter("idpaciente", cita.getIdpaciente())
                .setParameter("fechacita", cita.getFechacita())
                .executeUpdate();
    }

    public List<CitaModel> buscarCitasPorFecha(String fecha) {
        return entityManager.createNativeQuery("SELECT * FROM citas WHERE fechacita = :fecha", CitaModel.class)
                .setParameter("fecha", fecha).getResultList();
    }

    public List<CitaModel> buscarCitasPorConsultorio(Integer consultorio) {
        return entityManager
                .createNativeQuery("SELECT * FROM citas WHERE idconsultorio = :consultorio", CitaModel.class)
                .setParameter("consultorio", consultorio).getResultList();
    }

    public List<CitaModel> buscarCitasPorFechaConsultorioDoctor(String fecha, Integer consultorio, Integer doctor) {
        return entityManager.createNativeQuery(
                "SELECT * FROM citas WHERE fechacita = :fechacita AND idconsultorio = :idconsultorio AND iddoctor = :iddoctor",
                CitaModel.class)
                .setParameter("fechacita", fecha)
                .setParameter("idconsultorio", consultorio)
                .setParameter("iddoctor", doctor)
                .getResultList();
    }

    public List<CitaModel> buscarCitasPorFechaDoctor(String fecha, Integer idDoctor) {
        return entityManager
                .createNativeQuery("SELECT * FROM citas WHERE fechacita = :fecha AND iddoctor = :iddoctor",
                        CitaModel.class)
                .setParameter("fecha", fecha)
                .setParameter("iddoctor", idDoctor).getResultList();
    }

    public List<CitaModel> buscarCitasPorConsultorioFecha(Integer consultorio, String fecha) {
        return entityManager
                .createNativeQuery("SELECT * FROM citas WHERE idconsultorio = :consultorio AND fechacita = :fecha",
                        CitaModel.class)
                .setParameter("consultorio", consultorio).setParameter("fecha", fecha).getResultList();
    }
}
