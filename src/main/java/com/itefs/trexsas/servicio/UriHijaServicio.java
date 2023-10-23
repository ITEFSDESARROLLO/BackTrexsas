package com.itefs.trexsas.servicio;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.UriHija;
import com.itefs.trexsas.repositorio.UriHijaRepositorio;

@Service
public class UriHijaServicio {
	
	@Autowired
	private UriHijaRepositorio uriHijaRepositorio;
	
	public void crear(UriHija uriHija) {
		uriHija.setIdUriHija(null);
		uriHijaRepositorio.save(uriHija);
	}
	
	public void actualizar(UriHija uriHija) {
		uriHijaRepositorio.save(uriHija);
	}
	
	public List<UriHija> obtenerTodos() {
		return uriHijaRepositorio.findAll();
	}
	
	public void eliminar(UriHija uriHija) {
		uriHijaRepositorio.delete(uriHija);
	}

}
