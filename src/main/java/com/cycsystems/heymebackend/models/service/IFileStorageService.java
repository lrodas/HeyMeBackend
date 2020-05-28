package com.cycsystems.heymebackend.models.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IFileStorageService {

	public abstract String storeFile(MultipartFile file, Integer IdUsuario) throws IOException;
	
	public Resource loadFileAsResource(String nombre, Integer idUsuario);

	public abstract String loadFileAsString(String name) throws IOException;
}
