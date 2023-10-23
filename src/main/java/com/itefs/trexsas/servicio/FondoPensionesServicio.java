package com.itefs.trexsas.servicio;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.FondoPensiones;
import com.itefs.trexsas.repositorio.FondoPensionesRepositorio;

@Service
public class FondoPensionesServicio {
	
	@Autowired
	private FondoPensionesRepositorio fondoPensionesRepositorio;
	
	public void crear(FondoPensiones fondoPensiones) {
		fondoPensiones.setIdFondoPensiones(null);
		fondoPensionesRepositorio.save(fondoPensiones);
	}
	
	public void actualizar(FondoPensiones fondoPensiones) {
		fondoPensionesRepositorio.save(fondoPensiones);
	}
	
	public List<FondoPensiones> obtenerTodos() {
		return fondoPensionesRepositorio.findAll();
	}
	
	public Page<Object[]> listar(int inicio) {
		Page<Object[]> page= fondoPensionesRepositorio.listarPaginado(PageRequest.of(inicio, 10));
		return page;
	}
	
	public FondoPensiones obtenerPorId(int id) {
		Optional<FondoPensiones> op=fondoPensionesRepositorio.findById(id);
		if(op.isPresent()) {
			return op.get();
		}
		return null;
	}
	
	public void eliminar(FondoPensiones fondoPensiones) {
		fondoPensionesRepositorio.delete(fondoPensiones);
	}
	
	public HashMap<String, Object> filtrarPorCodigo(Integer codigo)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Page<Object[]> page=fondoPensionesRepositorio.listarPaginadoCodigo(PageRequest.of(0, 10),codigo);
		List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> objeto = new HashMap<String, Object>();
			objeto.put("idFondoPensiones", o[0]);
			objeto.put("fondoPensiones", o[1]);
			objeto.put("estadoFondoPensiones", o[2]);
			lista.add(objeto);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("fondosPensiones", lista);
		return response;
	}
	
	public HashMap<String, Object> filtrarPorEstado(Integer estado)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Page<Object[]> page=fondoPensionesRepositorio.listarPaginadoEstado(PageRequest.of(0, 10),estado);
		List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> objeto = new HashMap<String, Object>();
			objeto.put("idFondoPensiones", o[0]);
			objeto.put("fondoPensiones", o[1]);
			objeto.put("estadoFondoPensiones", o[2]);
			lista.add(objeto);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("fondosPensiones", lista);
		return response;
	}

}
