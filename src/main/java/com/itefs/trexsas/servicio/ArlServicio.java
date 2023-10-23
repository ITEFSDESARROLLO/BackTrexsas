package com.itefs.trexsas.servicio;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.Arl;
import com.itefs.trexsas.repositorio.ArlRepositorio;

@Service
public class ArlServicio {
	
	@Autowired
	private ArlRepositorio arlRepositorio;
	
	public void crear(Arl arl) {
		arl.setIdArl(null);
		arlRepositorio.save(arl);
	}
	
	public void actualizar(Arl arl) {
		arlRepositorio.save(arl);
	}
	
	public List<Arl> obtenerTodos() {
		return arlRepositorio.findAll();
	}
	
	public Page<Object[]> listar(int inicio) {
		Page<Object[]> page= arlRepositorio.listarPaginado(PageRequest.of(inicio, 10));
		return page;
	}
	
	public Arl obtenerPorId(int id) {
		Optional<Arl> op=arlRepositorio.findById(id);
		if(op.isPresent()) {
			return op.get();
		}
		return null;
	}
	
	public void eliminar(Arl arl) {
		arlRepositorio.delete(arl);
	}
	
	public HashMap<String, Object> filtrarPorCodigo(Integer codigo)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Page<Object[]> page=arlRepositorio.listarPaginadoCodigo(PageRequest.of(0, 10),codigo);
		List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> objeto = new HashMap<String, Object>();
			objeto.put("idArl", o[0]);
			objeto.put("arl", o[1]);
			objeto.put("estadoArl", o[2]);
			lista.add(objeto);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("arls", lista);
		return response;
	}
	
	public HashMap<String, Object> filtrarPorEstado(Integer estado)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Page<Object[]> page=arlRepositorio.listarPaginadoEstado(PageRequest.of(0, 10),estado);
		List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> objeto = new HashMap<String, Object>();
			objeto.put("idArl", o[0]);
			objeto.put("arl", o[1]);
			objeto.put("estadoArl", o[2]);
			lista.add(objeto);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("arls", lista);
		return response;
	}

}
