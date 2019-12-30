package com.cycsystems.heymebackend.models.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="bitacora")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Bitacora implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idBitacora;
	
	private Date fecha;
	
	@Column(name="pagina", nullable=false)
	private String pagina;
	
	@Column(name="metodo", nullable=false)
	private String metodo;
	
	@Column(name="json", nullable=false, columnDefinition="TEXT")
	private String json;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idTipoOperacion", nullable=false)
	private TipoOperacion tipoOperacion;
	
	@Column(name="error", nullable=false)
	private String error;
	
	@PrePersist
	public void prePersist() {
		this.fecha = new Date();
	}

	private static final long serialVersionUID = 1L;

}

