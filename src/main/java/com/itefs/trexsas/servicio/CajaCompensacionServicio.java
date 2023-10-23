package com.itefs.trexsas.servicio;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.CajaCompensacion;
import com.itefs.trexsas.repositorio.CajaCompensacionRepositorio;

@Service
public class CajaCompensacionServicio {
	
	@Autowired
	private CajaCompensacionRepositorio cajaCompensacionRepositorio;
	
	public void crear(CajaCompensacion cajaCompensacion) {
		cajaCompensacion.setIdCajaCompensacion(null);
		cajaCompensacionRepositorio.save(cajaCompensacion);
	}
	
	public void actualizar(CajaCompensacion cajaCompensacion) {
		cajaCompensacionRepositorio.save(cajaCompensacion);
	}
	
	public List<CajaCompensacion> obtenerTodos() {
		return cajaCompensacionRepositorio.findAll();
	}
	
	public CajaCompensacion obtenerPorId(int id) {
		Optional<CajaCompensacion> op=cajaCompensacionRepositorio.findById(id);
		if(op.isPresent()) {
			return op.get();
		}
		return null;
	}
	
	public Page<Object[]> listar(int inicio) {
		Page<Object[]> page= cajaCompensacionRepositorio.listarPaginado(PageRequest.of(inicio, 10));
		return page;
	}
	
	public void eliminar(CajaCompensacion cajaCompensacion) {
		cajaCompensacionRepositorio.delete(cajaCompensacion);
	}
	
	public HashMap<String, Object> filtrarPorCodigo(Integer codigo)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Page<Object[]> page=cajaCompensacionRepositorio.listarPaginadoCodigo(PageRequest.of(0, 10),codigo);
		List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> objeto = new HashMap<String, Object>();
			objeto.put("idCajaCompensacion", o[0]);
			objeto.put("cajaCompensacion", o[1]);
			objeto.put("estadoCajaCompensacion", o[2]);
			lista.add(objeto);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("cajasCompensacion", lista);
		return response;
	}
	
	public HashMap<String, Object> filtrarPorEstado(Integer estado)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Page<Object[]> page=cajaCompensacionRepositorio.listarPaginadoEstado(PageRequest.of(0, 10),estado);
		List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> objeto = new HashMap<String, Object>();
			objeto.put("idCajaCompensacion", o[0]);
			objeto.put("cajaCompensacion", o[1]);
			objeto.put("estadoCajaCompensacion", o[2]);
			lista.add(objeto);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("cajasCompensacion", lista);
		return response;
	}

}
