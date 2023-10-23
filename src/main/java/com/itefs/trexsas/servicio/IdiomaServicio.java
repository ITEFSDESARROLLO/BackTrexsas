package com.itefs.trexsas.servicio;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.Idioma;
import com.itefs.trexsas.repositorio.IdiomaRepositorio;

@Service
public class IdiomaServicio {
	
	@Autowired
	private IdiomaRepositorio idiomaRepositorio;
	
	public void crear(Idioma idioma) {
		idioma.setIdIdioma(null);
		idiomaRepositorio.save(idioma);
	}
	
	public void actualizar(Idioma idioma) {
		idiomaRepositorio.save(idioma);
	}
	
	public List<Idioma> obtenerTodos() {
		return idiomaRepositorio.findAll();
	}
	
	public void eliminar(Idioma idioma) {
		idiomaRepositorio.delete(idioma);
	}

}
