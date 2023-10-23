package com.itefs.trexsas.servicio;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.TipoVehiculo;
import com.itefs.trexsas.repositorio.TipoVehiculoRepositorio;

@Service
public class TipoVehiculoServicio {
	
	@Autowired
	private TipoVehiculoRepositorio tipoVehiculoRepositorio;
	
	public void crear(TipoVehiculo tipoVehiculo) {
		tipoVehiculo.setIdTipoVehiculo(null);
		tipoVehiculoRepositorio.save(tipoVehiculo);
	}
	
	public void actualizar(TipoVehiculo tipoVehiculo) {
		tipoVehiculoRepositorio.save(tipoVehiculo);
	}
	
	public Page<Object[]> listar(int inicio) {
		Page<Object[]> page= tipoVehiculoRepositorio.listarPaginado(PageRequest.of(inicio, 10));
		return page;
	}
	
	public List<Object[]> listarFiltro()
	{
		List<Object[]> lista = tipoVehiculoRepositorio.listarFiltro();
		return lista;
	}
	
	public TipoVehiculo obtenerPorId(int id) {
		Optional<TipoVehiculo> op=tipoVehiculoRepositorio.findById(id);
		if(op.isPresent()) {
			return op.get();
		}
		return null;
	}
	
	public List<TipoVehiculo> obtenerTodos() {
		return tipoVehiculoRepositorio.findAll();
	}
	
	public HashMap<String, Object> filtrarPorCodigo(Integer codigo)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Page<Object[]> page=tipoVehiculoRepositorio.listarPaginadoCodigoVehiculo(PageRequest.of(0, 10),codigo);
		List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> objeto = new HashMap<String, Object>();
			objeto.put("idTipoVehiculo", o[0]);
			objeto.put("tipoVehiculo", o[1]);
			objeto.put("estadoTipoVehiculo", o[2]);
			lista.add(objeto);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("tiposVehiculo", lista);
		return response;
	}
	
	public HashMap<String, Object> filtrarPorEstado(Integer estado)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Page<Object[]> page=tipoVehiculoRepositorio.listarPaginadoEstadoVehiculo(PageRequest.of(0, 10),estado);
		List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> objeto = new HashMap<String, Object>();
			objeto.put("idTipoVehiculo", o[0]);
			objeto.put("tipoVehiculo", o[1]);
			objeto.put("estadoTipoVehiculo", o[2]);
			lista.add(objeto);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("tiposVehiculo", lista);
		return response;
	}
	
}
