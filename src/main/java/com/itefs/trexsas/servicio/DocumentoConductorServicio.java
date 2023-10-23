package com.itefs.trexsas.servicio;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.DocumentoConductor;
import com.itefs.trexsas.repositorio.DocumentoConductorRepositorio;

@Service
public class DocumentoConductorServicio {
	
	@Autowired
	private DocumentoConductorRepositorio documentoConductorRepositorio;
	
	public void crear(DocumentoConductor documentoConductor) {
		documentoConductor.setIdDocumentoConductor(null);
		documentoConductorRepositorio.save(documentoConductor);
	}
	
	public void actualizar(DocumentoConductor documentoConductor) {
		documentoConductorRepositorio.save(documentoConductor);
	}
	
	public List<DocumentoConductor> obtenerTodos() {
		return documentoConductorRepositorio.findAll();
	}
	
	public void eliminar(DocumentoConductor documentoConductor) {
		documentoConductorRepositorio.delete(documentoConductor);
	}
	
	public void eliminarPorConductor(Integer conductor) {
		DocumentoConductor doc = documentoConductorRepositorio.traerDocumento(conductor);
		documentoConductorRepositorio.delete(doc);
	}

}
