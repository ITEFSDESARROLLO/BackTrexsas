package com.itefs.trexsas.servicio;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.PolizaExtracontractual;
import com.itefs.trexsas.repositorio.PolizaExtracontractualRepositorio;

@Service
public class PolizaExtracontractualServicio {
	
	@Autowired
	private PolizaExtracontractualRepositorio polizaExtracontractualRepositorio;
	
	public void crear(PolizaExtracontractual polizaExtracontractual) {
		polizaExtracontractual.setIdPolizaExtracontractual(null);
		polizaExtracontractualRepositorio.save(polizaExtracontractual);
	}
	
	public void actualizar(PolizaExtracontractual polizaExtracontractual) {
		polizaExtracontractualRepositorio.save(polizaExtracontractual);
	}
	
	public List<PolizaExtracontractual> obtenerTodos() {
		return polizaExtracontractualRepositorio.findAll();
	}
	
	public void eliminar(PolizaExtracontractual polizaExtracontractual) {
		polizaExtracontractualRepositorio.delete(polizaExtracontractual);
	}
	
	public PolizaExtracontractual obtenerPorId(int id) {
		Optional<PolizaExtracontractual> opPropietario=polizaExtracontractualRepositorio.findById(id);
		if(opPropietario.isPresent()) {
			return opPropietario.get();
		}
		return null;
	}

}
