package com.example.hospital.Controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hospital.Models.CitaModel;
import com.example.hospital.Repositories.CitasRepository;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/citas")
public class CitasController {
    private final Integer MAX_CITAS_DIA = 8;

    @Autowired
    CitasRepository citasRepository;

    @GetMapping("/todas")
    public List<CitaModel> obtenerTodas() {
        return citasRepository.obtenerTodas();
    }

    @Transactional
    @PostMapping
    public ResponseEntity<String> registrarCita(@RequestBody CitaModel cita) {
        //Validamos la cantidad máxima de citas por día del doctor:
        List<CitaModel> citasPorFechaYDoctor = citasRepository.buscarCitasPorFechaDoctor(cita.getFechacita(),
                cita.getIddoctor());

        if (citasPorFechaYDoctor.size() >= MAX_CITAS_DIA) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Este médico ha llegado a su cantidad máxima de citas para este día");
        }

        //Validamos que el doctor no tenga otra cita a la misma fecha y hora:
        List<CitaModel> citasMismaHora = citasPorFechaYDoctor.stream()
                .filter(c -> c.getHorario().equals(cita.getHorario())).collect(Collectors.toList());

        if (citasMismaHora.size() >= 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Este médico ya tiene cita para esta fecha y hora");
        }

        //Validar que no haya cita en este consultorio a esta hora:
        List<CitaModel> citasConsultorioFecha = citasRepository.buscarCitasPorConsultorioFecha(cita.getIdconsultorio(),
                cita.getFechacita());
        if (citasConsultorioFecha.stream().filter(c -> c.getHorario().equals(cita.getHorario()))
                .collect(Collectors.toList()).size() >= 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Este consultorio ya tiene cita para esta hora");
        }

        //Validar que no haya cita para el paciente a esta hora o en menos de 2 horas

        citasRepository.guardarCita(cita);
        return ResponseEntity.ok("Cita creada correctamente");
    }

    //Buscar citas por fecha, consultorio y doctor
    @GetMapping
    public List<CitaModel> consultarCitaPorFechaConsultorioDoctor(@RequestParam("fecha") String fecha,
            @RequestParam("consultorio") Integer consultorio, @RequestParam("doctor") Integer doctor) {
        return citasRepository.buscarCitasPorFechaConsultorioDoctor(fecha, consultorio, doctor);
    }
}
