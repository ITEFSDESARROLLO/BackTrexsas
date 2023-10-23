package com.itefs.trexsas.servicio;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.Configuraciones;
import com.itefs.trexsas.repositorio.ConfiguracionesRepositorio;

@Service
public class ConfiguracionesServicio {
	
	@Autowired
	private ConfiguracionesRepositorio configuracionesRepositorio;
	
	
	public void actualizar(Configuraciones configuraciones) {
		configuracionesRepositorio.save(configuraciones);
	}
	
	public Configuraciones obtenerPorId(int id) {
		Optional<Configuraciones> op=configuracionesRepositorio.findById(id);
		if(op.isPresent()) {
			return op.get();
		}
		return null;
	}
	
	public Page<Object[]> listar(int inicio) {
		Page<Object[]> page= configuracionesRepositorio.listarPaginado(PageRequest.of(inicio, 10));
		return page;
	}
	
	public List<Configuraciones> obtenerTodos() {
		return configuracionesRepositorio.findAll();
	}
	
	public void eliminar(int id) {
		configuracionesRepositorio.deleteById(id);
	}

}
