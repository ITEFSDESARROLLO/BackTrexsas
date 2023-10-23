package com.itefs.trexsas.servicio;


import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.Perfil;
import com.itefs.trexsas.repositorio.PerfilRepositorio;

@Service
public class PerfilServicio {
	
	@Autowired
	private PerfilRepositorio perfilRepositorio;
	
	public void crear(Perfil perfil) {
		perfil.setIdPerfil(null);
		perfilRepositorio.save(perfil);
	}
	
	public void actualizar(Perfil perfil) {
		perfilRepositorio.save(perfil);
	}
	
	public List<Perfil> obtenerTodos() {
		return perfilRepositorio.findAll();
	}
	
	public void eliminar(Perfil perfil) {
		perfilRepositorio.delete(perfil);
	}
	
	public Page<Object[]> listar(int inicio) {
		Page<Object[]> page= perfilRepositorio.listarPerfilesPaginado(PageRequest.of(inicio, 10));
		return page;
	}
	
	public Perfil obtenerPorId(int id) {
		Optional<Perfil> opPerfil=perfilRepositorio.findById(id);
		if(opPerfil.isPresent()) {
			return opPerfil.get();
		}
		return null;
	}
	
	public List<Object[]> obtenerBasicoPorIdCuenta(int id) {
		return perfilRepositorio.encontrarPerfilesDeCuenta(id);
	}
	
	public List<Object[]> obtenerAccesosPorToken(String token) {
		return perfilRepositorio.encontrarAccesosDeCuenta(token);
	}
	
	public List<Object[]> infoBasica() {
		List<Object[]> list= perfilRepositorio.obtenerInfoBasica();
		return list;
	}
	
	public List<Object[]> traerPerfiles()
	{
		List<Object[]> rutasSistema = perfilRepositorio.traerRutasPagina();
		Integer idRuta = Integer.valueOf(rutasSistema.get(0)[0].toString());
		for (Object[] objects : rutasSistema)
		{
			if(objects[0] != idRuta)
			{
				HashMap<String, Object> rutaPadre = new HashMap<String, Object>();
				
			}
		}
		return null;
	}
	
	

}
