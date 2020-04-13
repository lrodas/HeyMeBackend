package com.cycsystems.heymebackend.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "estadoUsuario")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EstadoUsuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEstadoUsuario;
    private String descripcion;

    public EstadoUsuario(Integer idEstadoUsuario) {
        this.idEstadoUsuario = idEstadoUsuario;
    }

    private static final long serialVersionUID = 1L;
}
