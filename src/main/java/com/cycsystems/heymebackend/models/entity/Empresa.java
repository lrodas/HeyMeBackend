package com.cycsystems.heymebackend.models.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name="empresa")
public class Empresa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEmpresa;
    private String nombreEmpresa;
    private String direccion;
    private String telefono;
    private BigDecimal tarifa;
    private String logo;
}
