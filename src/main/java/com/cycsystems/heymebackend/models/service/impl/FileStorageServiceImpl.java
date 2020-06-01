package com.cycsystems.heymebackend.models.service.impl;

import com.cycsystems.heymebackend.models.entity.Empresa;
import com.cycsystems.heymebackend.models.entity.Usuario;
import com.cycsystems.heymebackend.models.service.IEmpresaService;
import com.cycsystems.heymebackend.models.service.IFileStorageService;
import com.cycsystems.heymebackend.models.service.IParametroService;
import com.cycsystems.heymebackend.models.service.IUsuarioService;
import com.cycsystems.heymebackend.util.Constants;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageServiceImpl implements IFileStorageService {

	private Logger LOG = LogManager.getLogger(FileStorageServiceImpl.class);
		
	@Autowired
	private ResourceLoader resourceLoader;
	@Autowired
	private IParametroService parametroService;
	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private IEmpresaService empresaService;

	@Value("${mail.template.url}")
	private String MAIL_TEMPLATE_URL;
	
	@Override
	public String storeFile(MultipartFile file, Integer id, String tipo) throws IOException {
		
		LOG.info("METHOD: storeFile() --PARAMS: id: " + id + ", tipo: " + tipo);
		
		Path directorioRecursos = null;
		String extension = "";
		String imagen = "";
		
		extension = FilenameUtils.getExtension(file.getOriginalFilename());
		LOG.info("Obtenemos la extension del archivo: " + extension);
		
		imagen = id + "-" + System.currentTimeMillis() % 1000 + "." + extension;
		LOG.info("Creamos el nuevo nombre de la imagen: " + imagen);

		if (tipo.equalsIgnoreCase("empresa")) {
			Empresa empresa = this.empresaService.findById(id);
			directorioRecursos = Paths.get(this.parametroService.findParameterByEmpresaAndName(
					empresa.getIdEmpresa(),
					Constants.IMAGES_URL).getValor());
		} else {
			Usuario usuario = this.usuarioService.findById(id);
			directorioRecursos = Paths.get(this.parametroService.findParameterByEmpresaAndName(
					usuario.getEmpresa().getIdEmpresa(),
					Constants.IMAGES_URL).getValor());
		}

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
	public Resource loadFileAsResource(String nombre, Integer id, String tipo) {
		
		LOG.info("METHOD: loadFileAsResource() --PARAMS: nombre: " + nombre + ", id: " + id + ", tipo: " + tipo);

		String path = "";
		String rutaBasica = "";
		if (tipo.equalsIgnoreCase("usuario")) {
			Usuario usuario = this.usuarioService.findById(id);
			rutaBasica = this.parametroService.findParameterByEmpresaAndName(
					usuario.getEmpresa().getIdEmpresa(),
					Constants.IMAGES_URL).getValor();
		} else {
			Empresa empresa = this.empresaService.findById(id);
			rutaBasica = this.parametroService.findParameterByEmpresaAndName(
					empresa.getIdEmpresa(),
					Constants.IMAGES_URL).getValor();
		}

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

	@Override
	public String loadFileAsString(String name) throws IOException {
		LOG.info("loadFileAsString() --PARAMS: " + name);

		File file = new File(this.MAIL_TEMPLATE_URL + name);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String completeText = "";
		String line;
		while((line = br.readLine()) != null) {
			LOG.info(line);
			completeText += line;
		}

		return completeText;
	}

}
