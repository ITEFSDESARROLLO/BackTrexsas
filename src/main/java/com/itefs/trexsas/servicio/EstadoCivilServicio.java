package com.itefs.trexsas.servicio;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.EstadoCivil;
import com.itefs.trexsas.repositorio.EstadoCivilRepositorio;

@Service
public class EstadoCivilServicio {
	
	@Autowired
	private EstadoCivilRepositorio estadoCivilRepositorio;
	
	public void crear(EstadoCivil estadoCivil) {
		estadoCivil.setIdEstadoCivil(null);
		estadoCivilRepositorio.save(estadoCivil);
	}
	
	public void actualizar(EstadoCivil estadoCivil) {
		estadoCivilRepositorio.save(estadoCivil);
	}
	
	public List<EstadoCivil> obtenerTodos() {
		return estadoCivilRepositorio.findAll();
	}
	
	public void eliminar(EstadoCivil estadoCivil) {
		estadoCivilRepositorio.delete(estadoCivil);
	}

}
