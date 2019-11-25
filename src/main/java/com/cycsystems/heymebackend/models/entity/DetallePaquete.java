package com.cycsystems.heymebackend.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "detallePaquete")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DetallePaquete implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idDetallePaquete;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCanal")
	private Canal canal;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPaquete")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Paquete paquete;
	
	private Integer cuota;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
