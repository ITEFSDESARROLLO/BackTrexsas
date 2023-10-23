package com.itefs.trexsas.servicio;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.Eps;
import com.itefs.trexsas.repositorio.EpsRepositorio;

@Service
public class EpsServicio {
	
	@Autowired
	private EpsRepositorio epsRepositorio;
	
	public void crear(Eps eps) {
		eps.setIdEps(null);
		epsRepositorio.save(eps);
	}
	
	public void actualizar(Eps eps) {
		epsRepositorio.save(eps);
	}
	
	public List<Eps> obtenerTodos() {
		return epsRepositorio.findAll();
	}
	
	public Page<Object[]> listar(int inicio) {
		Page<Object[]> page= epsRepositorio.listarPaginado(PageRequest.of(inicio, 10));
		return page;
	}
	
	public Eps obtenerPorId(int id) {
		Optional<Eps> op=epsRepositorio.findById(id);
		if(op.isPresent()) {
			return op.get();
		}
		return null;
	}
	
	public void eliminar(Eps eps) {
		epsRepositorio.delete(eps);
	}
	
	public HashMap<String, Object> filtrarPorCodigo(Integer codigo)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Page<Object[]> page=epsRepositorio.listarPaginadoCodigo(PageRequest.of(0, 10),codigo);
		List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> objeto = new HashMap<String, Object>();
			objeto.put("idEps", o[0]);
			objeto.put("eps", o[1]);
			objeto.put("estadoEps", o[2]);
			lista.add(objeto);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("epss", lista);
		return response;
	}
	
	public HashMap<String, Object> filtrarPorEstado(Integer estado)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Page<Object[]> page=epsRepositorio.listarPaginadoEstado(PageRequest.of(0, 10),estado);
		List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> objeto = new HashMap<String, Object>();
			objeto.put("idEps", o[0]);
			objeto.put("eps", o[1]);
			objeto.put("estadoEps", o[2]);
			lista.add(objeto);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("epss", lista);
		return response;
	}

}
