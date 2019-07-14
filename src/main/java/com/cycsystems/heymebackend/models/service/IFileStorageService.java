package com.cycsystems.heymebackend.models.service;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {

	public abstract String storeFile(MultipartFile file, Integer IdUsuario) throws IOException;
	
	public Resource loadFileAsResource(String nombre);
}
