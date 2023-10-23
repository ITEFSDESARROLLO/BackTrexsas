package com.itefs.trexsas.servicio;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.PolizaContractual;
import com.itefs.trexsas.repositorio.PolizaContractualRepositorio;

@Service
public class PolizaContractualServicio {
	
	@Autowired
	private PolizaContractualRepositorio polizaContractualRepositorio;
	
	public void crear(PolizaContractual polizaContractual) {
		polizaContractual.setIdPolizaContractual(null);
		polizaContractualRepositorio.save(polizaContractual);
	}
	
	public void actualizar(PolizaContractual polizaContractual) {
		polizaContractualRepositorio.save(polizaContractual);
	}
	
	public List<PolizaContractual> obtenerTodos() {
		return polizaContractualRepositorio.findAll();
	}
	
	public void eliminar(PolizaContractual polizaContractual) {
		polizaContractualRepositorio.delete(polizaContractual);
	}

	public PolizaContractual obtenerPorId(int id) {
		Optional<PolizaContractual> opPropietario=polizaContractualRepositorio.findById(id);
		if(opPropietario.isPresent()) {
			return opPropietario.get();
		}
		return null;
	}
}
