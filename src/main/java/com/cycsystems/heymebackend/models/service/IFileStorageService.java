package com.cycsystems.heymebackend.models.service;

import org.springframework.web.multipart.MultipartFile;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import java.io.IOException;

public interface IFileStorageService {

	public abstract String storeFile(MultipartFile file, Integer id, String tipo)
			throws IOException, JSchException, SftpException;

	public byte[] loadFileAsResource(String nombre, Integer idUsuario, String tipo) throws JSchException, SftpException;

	public abstract String loadFileAsString(String name) throws IOException;
}
