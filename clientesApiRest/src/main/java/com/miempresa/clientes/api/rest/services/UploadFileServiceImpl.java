package com.miempresa.clientes.api.rest.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service //Con esta anotacion, queda guardada la instancia en el contenedor de spring para poder inyectarla mas adelante
public class UploadFileServiceImpl implements IUploadFileService {
	
	private final Logger log =  LoggerFactory.getLogger(UploadFileServiceImpl.class);
	private final static String DIRECTORY_UPLOAD = "uploads";
	
	@Override
	public Resource cargar(String nombreFoto) throws MalformedURLException {
		
		Path rutaArchivo = getPath(nombreFoto);
		log.info(rutaArchivo.toString());
		
		Resource recurso = new UrlResource(rutaArchivo.toUri());
				
		//Validamos que el recurso exista y sea leible
		if(!recurso.exists() && !recurso.isReadable()) {
			//En caso de que no exista la imagen, cargara por defecto la imagen noUsuario
			rutaArchivo = Paths.get("src/main/resources/static/images").resolve("noUsuario.png").toAbsolutePath();
			
			recurso = new UrlResource(rutaArchivo.toUri());
			
			log.error("Error no se pudo cargar la imagen: " + nombreFoto);
		}
		
		return recurso;
	}

	@Override
	public String copiar(MultipartFile archivo) throws IOException {
		
		//Genera el nombre del archivo
		String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename().replace("  ", "");
		Path rutaArchivo = getPath(nombreArchivo);
		log.info(rutaArchivo.toString());
		
		Files.copy(archivo.getInputStream(), rutaArchivo);
		
		return nombreArchivo;
	}

	@Override
	public boolean eliminar(String nombreFoto) {
		
		//Se pregunta si el nombre de la foto no es null, ademas por seguridad se pregunta si el nombre tiene caracteres
		if(nombreFoto !=null && nombreFoto.length() > 0) {
			Path rutaFotoAnterior = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();//Obtenemos el path absoluto.
			//Convierte la ruta en un archivo 
			File archivoFotoAnterior = rutaFotoAnterior.toFile();
			if(archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()){
				archivoFotoAnterior.delete();
				return true;
			}
		}
		
		return false;
	}

	@Override
	public Path getPath(String nombreFoto) {
		
		return Paths.get(DIRECTORY_UPLOAD).resolve(nombreFoto).toAbsolutePath();
	}
	
}
