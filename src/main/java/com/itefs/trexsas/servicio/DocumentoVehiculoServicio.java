package com.itefs.trexsas.servicio;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.DocumentoVehiculo;
import com.itefs.trexsas.repositorio.DocumentoVehiculoRepositorio;

@Service
public class DocumentoVehiculoServicio {
	
	@Autowired
	private DocumentoVehiculoRepositorio documentoVehiculoRepositorio;
	
	public void crear(DocumentoVehiculo documentoVehiculo) {
		documentoVehiculo.setIdDocumentoVehiculo(null);
		documentoVehiculoRepositorio.save(documentoVehiculo);
	}
	
	public void actualizar(DocumentoVehiculo documentoVehiculo) {
		documentoVehiculoRepositorio.save(documentoVehiculo);
	}
	
	public List<DocumentoVehiculo> obtenerTodos() {
		return documentoVehiculoRepositorio.findAll();
	}
	
	public void eliminar(DocumentoVehiculo documentoVehiculo) {
		documentoVehiculoRepositorio.delete(documentoVehiculo);
	}

}
