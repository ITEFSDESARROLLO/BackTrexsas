package com.itefs.trexsas.servicio;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.EmpresaConvenio;
import com.itefs.trexsas.repositorio.EmpresaConvenioRepositorio;

@Service
public class EmpresaConvenioServicio {
	
	@Autowired
	private EmpresaConvenioRepositorio empresaConvenioRepositorio;
	
	public void crear(EmpresaConvenio empresaConvenio) {
		empresaConvenio.setIdEmpresaConvenio(null);
		empresaConvenioRepositorio.save(empresaConvenio);
	}
	
	public void actualizar(EmpresaConvenio empresaConvenio) {
		empresaConvenioRepositorio.save(empresaConvenio);
	}
	
	public EmpresaConvenio obtenerPorId(int id) {
		Optional<EmpresaConvenio> op=empresaConvenioRepositorio.findById(id);
		if(op.isPresent()) {
			return op.get();
		}
		return null;
	}
	
	public Page<Object[]> listar(int inicio) {
		Page<Object[]> page= empresaConvenioRepositorio.listarEmpresasPaginado(PageRequest.of(inicio, 10));
		return page;
	}
	
	public List<EmpresaConvenio> obtenerTodos() {
		return empresaConvenioRepositorio.findAll();
	}
	
	public void eliminar(EmpresaConvenio empresaConvenio) {
		empresaConvenioRepositorio.delete(empresaConvenio);
	}   
	
	public HashMap<String, Object> filtrarPorCodigo(Integer codigo)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Page<Object[]> page=empresaConvenioRepositorio.listarEmpresasPaginadoCodigo(PageRequest.of(0, 10),codigo);
		List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> objeto = new HashMap<String, Object>();
			objeto.put("idEmpresaConvenio", o[0]);
			objeto.put("nombreEmpresaConvenio", o[1]);
			objeto.put("nitEmpresaConvenio", o[2]);
			objeto.put("estadoEmpresaConvenio", o[3]);
			lista.add(objeto);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("empresasConvenio", lista);
		return response;
	}
	
	public HashMap<String, Object> filtrarPorNit(String codigo)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Page<Object[]> page=empresaConvenioRepositorio.listarEmpresasPaginadoNit(PageRequest.of(0, 10),codigo);
		List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> objeto = new HashMap<String, Object>();
			objeto.put("idEmpresaConvenio", o[0]);
			objeto.put("nombreEmpresaConvenio", o[1]);
			objeto.put("nitEmpresaConvenio", o[2]);
			objeto.put("estadoEmpresaConvenio", o[3]);
			lista.add(objeto);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("empresasConvenio", lista);
		return response;
	}
	
	public HashMap<String, Object> filtrarPorEstado(Integer estado)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Page<Object[]> page=empresaConvenioRepositorio.listarEmpresasPaginadoEstado(PageRequest.of(0, 10),estado);
		List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> objeto = new HashMap<String, Object>();
			objeto.put("idEmpresaConvenio", o[0]);
			objeto.put("nombreEmpresaConvenio", o[1]);
			objeto.put("nitEmpresaConvenio", o[2]);
			objeto.put("estadoEmpresaConvenio", o[3]);
			lista.add(objeto);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("empresasConvenio", lista);
		return response;
	}

}
