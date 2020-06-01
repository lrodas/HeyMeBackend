package com.cycsystems.heymebackend.models.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IFileStorageService {

	public abstract String storeFile(MultipartFile file, Integer id, String tipo) throws IOException;
	
	public Resource loadFileAsResource(String nombre, Integer idUsuario, String tipo);

	public abstract String loadFileAsString(String name) throws IOException;
}
