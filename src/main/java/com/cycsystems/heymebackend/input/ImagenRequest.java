package com.cycsystems.heymebackend.input;

import org.springframework.web.multipart.MultipartFile;

import com.cycsystems.heymebackend.common.BaseInput;

public class ImagenRequest extends BaseInput {

	private MultipartFile imagen;
	private Integer idUsuario;

	public MultipartFile getImagen() {
		return imagen;
	}

	public void setImagen(MultipartFile imagen) {
		this.imagen = imagen;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Override
	public String toString() {
		return "ImagenRequest [imagen=" + imagen + ", idUsuario=" + idUsuario + ", toString()=" + super.toString()
				+ "]";
	}
}
