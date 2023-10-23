package com.itefs.trexsas.servicio;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.Aseguradora;
import com.itefs.trexsas.repositorio.AseguradoraRepositorio;

@Service
public class AseguradoraServicio {
	
	@Autowired
	private AseguradoraRepositorio aseguradoraRepositorio;
	
	public void crear(Aseguradora aseguradora) {
		aseguradora.setIdAseguradora(null);
		aseguradoraRepositorio.save(aseguradora);
	}
	
	public void actualizar(Aseguradora aseguradora) {
		aseguradoraRepositorio.save(aseguradora);
	}
	
	public Page<Object[]> listar(int inicio) {
		Page<Object[]> page= aseguradoraRepositorio.listarPaginado(PageRequest.of(inicio, 10));
		return page;
	}
	
	public Aseguradora obtenerPorId(int id) {
		Optional<Aseguradora> op=aseguradoraRepositorio.findById(id);
		if(op.isPresent()) {
			return op.get();
		}
		return null;
	}
	
	public List<Aseguradora> obtenerTodos() {
		return aseguradoraRepositorio.findAll();
	}
	
	public void eliminar(Aseguradora aseguradora) {
		aseguradoraRepositorio.delete(aseguradora);
	}

	public HashMap<String, Object> filtrarPorCodigo(Integer codigo)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Page<Object[]> page=aseguradoraRepositorio.listarPaginadoCodigo(PageRequest.of(0, 10),codigo);
		List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> objeto = new HashMap<String, Object>();
			objeto.put("idAseguradora", o[0]);
			objeto.put("nombreAseguradora", o[1]);
			objeto.put("estadoAseguradora", o[2]);
			lista.add(objeto);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("aseguradoras", lista);
		return response;
	}
	
	public HashMap<String, Object> filtrarPorEstado(Integer estado)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Page<Object[]> page=aseguradoraRepositorio.listarPaginadoEstado(PageRequest.of(0, 10),estado);
		List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> objeto = new HashMap<String, Object>();
			objeto.put("idAseguradora", o[0]);
			objeto.put("nombreAseguradora", o[1]);
			objeto.put("estadoAseguradora", o[2]);
			lista.add(objeto);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("aseguradoras", lista);
		return response;
	}
	
}
