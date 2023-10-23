package com.itefs.trexsas.servicio;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.Licencia;
import com.itefs.trexsas.repositorio.LicenciaRepositorio;

@Service
public class LicenciaServicio {
	
	@Autowired
	private LicenciaRepositorio licenciaRepositorio;
	
	public void crear(Licencia licencia) {
		licencia.setIdLicencia(null);
		licenciaRepositorio.save(licencia);
	}
	
	public void actualizar(Licencia licencia) {
		licenciaRepositorio.save(licencia);
	}
	
	public List<Licencia> obtenerTodos() {
		return licenciaRepositorio.findAll();
	}
	
	public void eliminar(Licencia licencia) {
		licenciaRepositorio.delete(licencia);
	}
	
	public void eliminar(Integer conductor) {
		Licencia licencia = licenciaRepositorio.traerLicenciaConductor(conductor);
		if(licencia!=null)
		{
			licenciaRepositorio.delete(licencia);
		}
		
	}
	
	public Licencia obtenerPorId(long id) {
		Optional<Licencia> opPropietario=licenciaRepositorio.findById(id);
		if(opPropietario.isPresent()) {
			return opPropietario.get();
		}
		return null;
	}

}
