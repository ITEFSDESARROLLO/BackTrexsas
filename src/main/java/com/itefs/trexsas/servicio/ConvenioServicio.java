package com.itefs.trexsas.servicio;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.Convenio;
import com.itefs.trexsas.repositorio.ConvenioRepositorio;

@Service
public class ConvenioServicio {
	
	@Autowired
	private ConvenioRepositorio convenioRepositorio;
	
	public void crear(Convenio convenio) {
		convenio.setIdConvenio(null);
		convenioRepositorio.save(convenio);
	}
	
	public void actualizar(Convenio convenio) {
		convenioRepositorio.save(convenio);
	}
	
	public List<Convenio> obtenerTodos() {
		return convenioRepositorio.findAll();
	}
	
	public void eliminar(Convenio convenio) {
		convenioRepositorio.delete(convenio);
	}
	public Convenio obtenerPorId(int id) {
		Optional<Convenio> opPropietario=convenioRepositorio.findById(id);
		if(opPropietario.isPresent()) {
			return opPropietario.get();
		}
		return null;
	}

}
