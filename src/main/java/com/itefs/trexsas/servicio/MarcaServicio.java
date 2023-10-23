package com.itefs.trexsas.servicio;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.Marca;
import com.itefs.trexsas.repositorio.MarcaRepositorio;

@Service
public class MarcaServicio {
	
	@Autowired
	private MarcaRepositorio marcaRepositorio;
	
	public void crear(Marca marca) {
		marca.setIdMarca(null);
		marcaRepositorio.save(marca);
	}
	
	public void actualizar(Marca marca) {
		marcaRepositorio.save(marca);
	}
	
	public Page<Object[]> listar(int inicio) {
		Page<Object[]> page= marcaRepositorio.listarPaginado(PageRequest.of(inicio, 10));
		return page;
	}
	
	public Marca obtenerPorId(int id) {
		Optional<Marca> op=marcaRepositorio.findById(id);
		if(op.isPresent()) {
			return op.get();
		}
		return null;
	}
	
	public List<Marca> obtenerTodos() {
		return marcaRepositorio.findAll();
	}
	
	public void eliminar(Marca marca) {
		marcaRepositorio.delete(marca);
	}
	
	public HashMap<String, Object> filtrarPorCodigo(Integer codigo)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Page<Object[]> page=marcaRepositorio.listarPaginadoCodigo(PageRequest.of(0, 10),codigo);
		List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> objeto = new HashMap<String, Object>();
			objeto.put("idMarca", o[0]);
			objeto.put("marca", o[1]);
			objeto.put("estadoMarca", o[2]);
			lista.add(objeto);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("marcas", lista);
		return response;
	}
	
	public HashMap<String, Object> filtrarPorEstado(Integer estado)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Page<Object[]> page=marcaRepositorio.listarPaginadoEstado(PageRequest.of(0, 10),estado);
		List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> objeto = new HashMap<String, Object>();
			objeto.put("idMarca", o[0]);
			objeto.put("marca", o[1]);
			objeto.put("estadoMarca", o[2]);
			lista.add(objeto);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("marcas", lista);
		return response;
	}

}
