package com.itefs.trexsas.servicio;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.Soat;
import com.itefs.trexsas.repositorio.SoatRepositorio;

@Service
public class SoatServicio {
	
	@Autowired
	private SoatRepositorio soatRepositorio;
	
	public void crear(Soat soat) {
		soat.setIdSoat(null);
		soatRepositorio.save(soat);
	}
	
	public void actualizar(Soat soat) {
		soatRepositorio.save(soat);
	}
	
	public List<Soat> obtenerTodos() {
		return soatRepositorio.findAll();
	}
	
	public void eliminar(Soat soat) {
		soatRepositorio.delete(soat);
	}
	
	public Soat obtenerPorId(int id) {
		Optional<Soat> opPropietario=soatRepositorio.findById(id);
		if(opPropietario.isPresent()) {
			return opPropietario.get();
		}
		return null;
	}

}
