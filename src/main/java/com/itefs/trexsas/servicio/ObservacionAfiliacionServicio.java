package com.itefs.trexsas.servicio;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.ObservacionAfiliacion;
import com.itefs.trexsas.repositorio.ObservacionAfiliacionRepositorio;

@Service
public class ObservacionAfiliacionServicio {
	
	@Autowired
	private ObservacionAfiliacionRepositorio observacionAfiliacionRepositorio;
	
	public void crear(ObservacionAfiliacion observacionAfiliacion) {
		observacionAfiliacion.setIdObservacionAfiliacion(null);
		observacionAfiliacionRepositorio.save(observacionAfiliacion);
	}
	
	public void actualizar(ObservacionAfiliacion observacionAfiliacion) {
		observacionAfiliacionRepositorio.save(observacionAfiliacion);
	}
	
	public ObservacionAfiliacion obtenerPorid(Long id) {
		return observacionAfiliacionRepositorio.findById(id).get();
	}
	
	public List<Object[]> obtenerPorIdAfiliacion(long id) {
		return observacionAfiliacionRepositorio.obtenerPorIdAfiliacion(id);
	}

	public String ultimaObservacionPorIdAfiliacion(long id) {
		return observacionAfiliacionRepositorio.ultimaObservacionPorIdAfiliacion(id).get();
	}
	
	
}
