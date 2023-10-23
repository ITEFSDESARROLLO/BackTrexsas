package com.itefs.trexsas.servicio;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.RevisionTecnicomecanica;
import com.itefs.trexsas.repositorio.RevisionTecnicomecanicaRepositorio;

@Service
public class RevisionTecnicomecanicaServicio {
	
	@Autowired
	private RevisionTecnicomecanicaRepositorio revisionTecnicomecanicaRepositorio;
	
	public void crear(RevisionTecnicomecanica revisionTecnicomecanica) {
		revisionTecnicomecanica.setIdRevisionTecnicomecanica(null);
		revisionTecnicomecanicaRepositorio.save(revisionTecnicomecanica);
	}
	
	public void actualizar(RevisionTecnicomecanica revisionTecnicomecanica) {
		revisionTecnicomecanicaRepositorio.save(revisionTecnicomecanica);
	}
	
	public List<RevisionTecnicomecanica> obtenerTodos() {
		return revisionTecnicomecanicaRepositorio.findAll();
	}
	
	public void eliminar(RevisionTecnicomecanica revisionTecnicomecanica) {
		revisionTecnicomecanicaRepositorio.delete(revisionTecnicomecanica);
	}

	public RevisionTecnicomecanica obtenerPorId(int id) {
		Optional<RevisionTecnicomecanica> opPropietario=revisionTecnicomecanicaRepositorio.findById(id);
		if(opPropietario.isPresent()) {
			return opPropietario.get();
		}
		return null;
	}
}
