package com.itefs.trexsas.servicio;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.TipoCombustible;
import com.itefs.trexsas.repositorio.TipoCombustibleRepositorio;

@Service
public class TipoCombustibleServicio {
	
	@Autowired
	private TipoCombustibleRepositorio tipoCombustibleRepositorio;
	
	public void crear(TipoCombustible tipoCombustible) {
		tipoCombustible.setIdTipoCombustible(null);
		tipoCombustibleRepositorio.save(tipoCombustible);
	}
	
	public void actualizar(TipoCombustible tipoCombustible) {
		tipoCombustibleRepositorio.save(tipoCombustible);
	}
	
	public List<TipoCombustible> obtenerTodos() {
		return tipoCombustibleRepositorio.findAll();
	}
	
	public Page<Object[]> listar(int inicio) {
		Page<Object[]> page= tipoCombustibleRepositorio.listarPaginado(PageRequest.of(inicio, 10));
		return page;
	}
	
	public TipoCombustible obtenerPorId(int id) {
		Optional<TipoCombustible> op=tipoCombustibleRepositorio.findById(id);
		if(op.isPresent()) {
			return op.get();
		}
		return null;
	}
	
	public void eliminar(TipoCombustible tipoCombustible) {
		tipoCombustibleRepositorio.delete(tipoCombustible);
	}
	
	public HashMap<String, Object> filtrarPorCodigo(Integer codigo)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Page<Object[]> page=tipoCombustibleRepositorio.listarPaginadoCodigo(PageRequest.of(0, 10),codigo);
		List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> objeto = new HashMap<String, Object>();
			objeto.put("idTipoCombustible", o[0]);
			objeto.put("tipoCombustible", o[1]);
			objeto.put("estadoTipoCombustible", o[2]);
			lista.add(objeto);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("tiposCombustible", lista);
		return response;
	}
	
	public HashMap<String, Object> filtrarPorEstado(Integer estado)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Page<Object[]> page=tipoCombustibleRepositorio.listarPaginadoEstado(PageRequest.of(0, 10),estado);
		List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> objeto = new HashMap<String, Object>();
			objeto.put("idTipoCombustible", o[0]);
			objeto.put("tipoCombustible", o[1]);
			objeto.put("estadoTipoCombustible", o[2]);
			lista.add(objeto);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("tiposCombustible", lista);
		return response;
	}

}
