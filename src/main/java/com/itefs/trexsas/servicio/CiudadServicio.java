package com.itefs.trexsas.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.Ciudad;
import com.itefs.trexsas.repositorio.CiudadRepositorio;

@Service
public class CiudadServicio {

	@Autowired
	private CiudadRepositorio ciudadRepositorio;
	
	public void crearCiudad(Ciudad ciudad) {
		ciudad.setIdCiudad(null);
		ciudadRepositorio.save(ciudad);
	}
	
	public void actualizarCiudad(Ciudad ciudad) {
		ciudadRepositorio.save(ciudad);
	}
	
	public List<Ciudad> obtenerTodosCiudad() {
		return ciudadRepositorio.findAll();
	}
	
	public void eliminarCiudad(Ciudad ciudad) {
		ciudadRepositorio.delete(ciudad);
	}
}
