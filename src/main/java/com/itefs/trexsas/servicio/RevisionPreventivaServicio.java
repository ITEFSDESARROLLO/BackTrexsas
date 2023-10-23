package com.itefs.trexsas.servicio;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.RevisionPreventiva;
import com.itefs.trexsas.repositorio.RevisionPreventivaRepositorio;

@Service
public class RevisionPreventivaServicio {
	
	@Autowired
	private RevisionPreventivaRepositorio revisionPreventivaRepositorio;
	
	public void crear(RevisionPreventiva revisionPreventiva) {
		revisionPreventiva.setIdRevisionPreventiva(null);
		revisionPreventivaRepositorio.save(revisionPreventiva);
	}
	
	public void actualizar(RevisionPreventiva revisionPreventiva) {
		revisionPreventivaRepositorio.save(revisionPreventiva);
	}
	
	public List<RevisionPreventiva> obtenerTodos() {
		return revisionPreventivaRepositorio.findAll();
	}
	
	public void eliminar(RevisionPreventiva revisionPreventiva) {
		revisionPreventivaRepositorio.delete(revisionPreventiva);
	}
	
	public RevisionPreventiva obtenerPorId(int id) {
		Optional<RevisionPreventiva> opPropietario=revisionPreventivaRepositorio.findById(id);
		if(opPropietario.isPresent()) {
			return opPropietario.get();
		}
		return null;
	}

}
