package com.cycsystems.heymebackend.models.service.impl;

import com.cycsystems.heymebackend.config.JschConfig;
import com.cycsystems.heymebackend.models.service.IFileStorageService;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Service
public class FileStorageServiceImpl implements IFileStorageService {

	private Logger LOG = LogManager.getLogger(FileStorageServiceImpl.class);

//	@Autowired
//	private ResourceLoader resourceLoader;
//	@Autowired
//	private IParametroService parametroService;
//	@Autowired
//	private IUsuarioService usuarioService;
//	@Autowired
//	private IEmpresaService empresaService;

	@Value("${mail.template.url}")
	private String MAIL_TEMPLATE_URL;
	@Value("${storage.user}")
	private String storageUser;
	@Value("${storage.company}")
	private String storageCompany;

	private JschConfig jschConfig;

	public FileStorageServiceImpl(JschConfig jschConfig) {
		this.jschConfig = jschConfig;
	}

	@Override
	public String storeFile(MultipartFile file, Integer id, String tipo)
			throws IOException, JSchException, SftpException {

		LOG.info("METHOD: storeFile() --PARAMS: id: " + id + ", tipo: " + tipo);

//		Path directorioRecursos = null;
		String extension = "";
		String imagen = "";

		extension = FilenameUtils.getExtension(file.getOriginalFilename());
		LOG.info("Obtenemos la extension del archivo: " + extension);

		imagen = id + "-" + System.currentTimeMillis() % 1000 + "." + extension;
		LOG.info("Creamos el nuevo nombre de la imagen: " + imagen);

		if (tipo.equalsIgnoreCase("empresa")) {
//			Empresa empresa = this.empresaService.findById(id);
//			directorioRecursos = Paths.get(this.parametroService.findParameterByEmpresaAndName(
//					empresa.getIdEmpresa(),
//					Constants.IMAGES_URL).getValor());
			jschConfig.saveFile(storageCompany, file.getInputStream(), imagen);
		} else {
//			Usuario usuario = this.usuarioService.findById(id);
//			directorioRecursos = Paths.get(this.parametroService.findParameterByEmpresaAndName(
//					usuario.getEmpresa().getIdEmpresa(),
//					Constants.IMAGES_URL).getValor());
			jschConfig.saveFile(storageUser, file.getInputStream(), imagen);
		}

//		String rootPath = directorioRecursos.toFile().getAbsolutePath();
//		LOG.info("Posterior a crear la ruta obtener el path absoluto: " + rootPath);
//
//		File theDir = new File(rootPath);
//
//		LOG.info("Verificamos si el directorio generado existe: " + theDir.exists());
//		if (!theDir.exists()) {
//			LOG.info("creating directory: " + theDir.getName());
//			boolean result = false;
//
//			try {
//				theDir.mkdir();
//				result = true;
//			} catch (SecurityException se) {
//				LOG.info("Error al crear directorio: " + se);
//			}
//			if (result) {
//				LOG.info("DIR created");
//			}
//		}
//
//		byte[] bytes = file.getBytes();
//
//		Path rutaCompleta = Paths.get(rootPath + "//" + imagen);
//		LOG.info("Generamos la ruta completa que incluye el nombre del archivo: " + rutaCompleta);
//
//		LOG.info("Escribimos el archivo");
//		Files.write(rutaCompleta, bytes);

		LOG.info("Devolvemos el nombre del archivo");
		return imagen;

	}

	@Override
	public byte[] loadFileAsResource(String nombre, Integer id, String tipo) throws JSchException, SftpException {

		LOG.info("METHOD: storeFile()");

		byte[] response = null;

		if (jschConfig.obtainFile(nombre) != null) {

			ByteArrayOutputStream a = jschConfig.obtainFile(nombre);

			response = a.toByteArray();
		} else {

			String img = "data:image/png;base64,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";

			response = img.getBytes();

		}
		return response;
//
//		LOG.info("METHOD: loadFileAsResource() --PARAMS: nombre: " + nombre + ", id: " + id + ", tipo: " + tipo);
//
//		String path = "";
//		String rutaBasica = "";
//		if (tipo.equalsIgnoreCase("usuario")) {
//			Usuario usuario = this.usuarioService.findById(id);
//			rutaBasica = this.parametroService
//					.findParameterByEmpresaAndName(usuario.getEmpresa().getIdEmpresa(), Constants.IMAGES_URL)
//					.getValor();
//		} else {
//			Empresa empresa = this.empresaService.findById(id);
//			rutaBasica = this.parametroService
//					.findParameterByEmpresaAndName(empresa.getIdEmpresa(), Constants.IMAGES_URL).getValor();
//		}
//
//		path = Paths.get(rutaBasica, nombre).toString();
//
//		File imagen = new File(path);
//		if (!imagen.exists()) {
//			path = Paths.get(rutaBasica, "no-img.png").toString();
//		}
//
//		LOG.info("Ruta cargada: " + path);
//
//		Resource loader = resourceLoader.getResource("file:" + path);
//
//		LOG.info("Recurso cargado, retornando imagen");
//		return loader;
	}

	@Override
	public String loadFileAsString(String name) throws IOException {
		LOG.info("loadFileAsString() --PARAMS: " + name);

		File file = new File(this.MAIL_TEMPLATE_URL + name);
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(file));
		String completeText = "";
		String line;
		while ((line = br.readLine()) != null) {
			LOG.info(line);
			completeText += line;
		}

		return completeText;
	}

}
