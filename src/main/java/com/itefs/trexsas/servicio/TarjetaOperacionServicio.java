package com.itefs.trexsas.servicio;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.TarjetaOperacion;
import com.itefs.trexsas.repositorio.TarjetaOperacionRepositorio;

@Service
public class TarjetaOperacionServicio {
	
	@Autowired
	private TarjetaOperacionRepositorio tarjetaOperacionRepositorio;
	
	public void crear(TarjetaOperacion tarjetaOperacion) {
		tarjetaOperacion.setIdTarjetaOperacion(null);
		tarjetaOperacionRepositorio.save(tarjetaOperacion);
	}
	
	public void actualizar(TarjetaOperacion tarjetaOperacion) {
		tarjetaOperacionRepositorio.save(tarjetaOperacion);
	}
	
	public List<TarjetaOperacion> obtenerTodos() {
		return tarjetaOperacionRepositorio.findAll();
	}
	
	public void eliminar(TarjetaOperacion tarjetaOperacion) {
		tarjetaOperacionRepositorio.delete(tarjetaOperacion);
	}
	public TarjetaOperacion obtenerPorId(int id) {
		Optional<TarjetaOperacion> opPropietario=tarjetaOperacionRepositorio.findById(id);
		if(opPropietario.isPresent()) {
			return opPropietario.get();
		}
		return null;
	}

}
