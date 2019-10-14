package com.cycsystems.heymebackend.models.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cycsystems.heymebackend.models.service.IFileStorageService;

@Service
public class FileStorageServiceImpl implements IFileStorageService {

	private Logger LOG = LogManager.getLogger(FileStorageServiceImpl.class);
		
	@Autowired
	private ResourceLoader resourceLoader;
	
	@Value("${images.url}")
	private String URL;	
	
	@Override
	public String storeFile(MultipartFile file, Integer idUsuario) throws IOException {
		
		LOG.info("METHOD: storeFile()");
		
		Path directorioRecursos;
		String extension = "";
		String imagen = "";
		
		extension = FilenameUtils.getExtension(file.getOriginalFilename());
		LOG.info("Obtenemos la extension del archivo: " + extension);
		
		imagen = idUsuario + "-" + System.currentTimeMillis() % 1000 + "." + extension;
		LOG.info("Creamos el nuevo nombre de la imagen: " + imagen);
		
		directorioRecursos = Paths.get(this.URL);
		
		String rootPath = directorioRecursos.toFile().getAbsolutePath();
		LOG.info("Posterior a crear la ruta obtener el path absoluto: " + rootPath);
		
		File theDir = new File(rootPath);

		LOG.info("Verificamos si el directorio generado existe: " + theDir.exists());
		if (!theDir.exists()) {
		    LOG.info("creating directory: " + theDir.getName());
		    boolean result = false;

		    try{
		        theDir.mkdir();
		        result = true;
		    } 
		    catch(SecurityException se){
		        LOG.info("Error al crear directorio: " + se);
		    }        
		    if(result) {    
		        LOG.info("DIR created");  
		    }
		}

		byte[] bytes = file.getBytes();

		Path rutaCompleta = Paths.get(rootPath + "//" + imagen);
		LOG.info("Generamos la ruta completa que incluye el nombre del archivo: " + rutaCompleta);

		LOG.info("Escribimos el archivo");
		Files.write(rutaCompleta, bytes);
		
		LOG.info("Devolvemos el nombre del archivo");
		return imagen;
		
	}

	@Override
	public Resource loadFileAsResource(String nombre) {
		
		LOG.info("METHOD: loadFileAsResource() --PARAMS: nombre: " + nombre);
		
		String path = "";
		String rutaBasica = this.URL;
		
		path = Paths.get(rutaBasica, nombre).toString();
		
		File imagen = new File(path);			
		if (!imagen.exists()) {
			path = Paths.get(rutaBasica, "no-img.png").toString();
		}
		
		LOG.info("Ruta cargada: " + path);
		
		Resource loader = resourceLoader.getResource("file:" + path);
		
		LOG.info("Recurso cargado, retornando imagen");
		return loader;
	}

}
