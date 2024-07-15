package com.example.hospital.Models;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class CitaModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Integer idcita;
    private Integer idconsultorio;
    private Integer iddoctor;
    private String horario;
    private Integer idpaciente;
    private String fechacita;
}
