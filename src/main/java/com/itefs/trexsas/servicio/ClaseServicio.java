package com.itefs.trexsas.servicio;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.Clase;
import com.itefs.trexsas.repositorio.ClaseRepositorio;

@Service
public class ClaseServicio {
	
	@Autowired
	private ClaseRepositorio claseRepositorio;
	
	public void crear(Clase clase) {
		clase.setIdClase(null);
		claseRepositorio.save(clase);
	}
	
	public void actualizar(Clase clase) {
		claseRepositorio.save(clase);
	}
	
	public Clase obtenerPorId(int id) {
		Optional<Clase> op=claseRepositorio.findById(id);
		if(op.isPresent()) {
			return op.get();
		}
		return null;
	}
	
	public Page<Object[]> listar(int inicio) {
		Page<Object[]> page= claseRepositorio.listarPaginado(PageRequest.of(inicio, 10));
		return page;
	}
	
	public List<Clase> obtenerTodos() {
		return claseRepositorio.findAll();
	}
	
	public void eliminar(Clase clase) {
		claseRepositorio.delete(clase);
	}
	
	public HashMap<String, Object> filtrarPorCodigo(Integer codigo)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Page<Object[]> page=claseRepositorio.listarPaginadoCodigo(PageRequest.of(0, 10),codigo);
		List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> objeto = new HashMap<String, Object>();
			objeto.put("idClase", o[0]);
			objeto.put("clase", o[1]);
			objeto.put("estadoClase", o[2]);
			lista.add(objeto);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("clases", lista);
		return response;
	}
	
	public HashMap<String, Object> filtrarPorEstado(Integer estado)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Page<Object[]> page=claseRepositorio.listarPaginadoEstado(PageRequest.of(0, 10),estado);
		List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> objeto = new HashMap<String, Object>();
			objeto.put("idClase", o[0]);
			objeto.put("clase", o[1]);
			objeto.put("estadoClase", o[2]);
			lista.add(objeto);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("clases", lista);
		return response;
	}

}
