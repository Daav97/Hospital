package com.example.hospital.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hospital.Models.CitaModel;
import com.example.hospital.Repositories.CitasRepository;

@RestController
@RequestMapping("/citas")
public class CitasController {

    @Autowired
    CitasRepository citasRepository;

    @GetMapping("/todas")
    public List<CitaModel> obtenerTodas() {
        return citasRepository.obtenerTodas();
    }

}
