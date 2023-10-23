package com.itefs.trexsas.servicio;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.Correo;
import com.itefs.trexsas.modelo.CorreoPQR;
import com.itefs.trexsas.repositorio.CorreoPQRRepositorio;
import com.itefs.trexsas.repositorio.CorreoRepositorio;

@Service
public class CorreoServicio {
	
	@Autowired
	private CorreoRepositorio correoRepositorio;
	
	@Autowired
	private CorreoPQRRepositorio correoPQRRepositorio;
	
	
	public void crear(Correo correo) {
		correo.setIdCorreo(null);
		correoRepositorio.save(correo);
	}
	
	public void crear(CorreoPQR correo)
	{
		correo.setIdCorreo(null);
		correoPQRRepositorio.save(correo);
	}
	
	public void actualizar(Correo correo) {
		correoRepositorio.save(correo);
	}
	
	public void actualizar(CorreoPQR correo) {
		correoPQRRepositorio.save(correo);
	}
	
	public Correo obtenerPorId(int id) {
		Optional<Correo> op=correoRepositorio.findById(id);
		if(op.isPresent()) {
			return op.get();
		}
		return null;
	}
	
	public CorreoPQR obtenerPorIdPQR(int id) {
		Optional<CorreoPQR> op=correoPQRRepositorio.findById(id);
		if(op.isPresent()) {
			return op.get();
		}
		return null;
	}
	
	public Page<Object[]> listar(int inicio) {
		Page<Object[]> page= correoRepositorio.listarPaginado(PageRequest.of(inicio, 10));
		return page;
	}
	
	public List<Correo> obtenerTodos() {
		return correoRepositorio.findAll();
	}
	
	public List<CorreoPQR> obtenerTodosQPR() {
		return correoPQRRepositorio.findAll();
	}
	
	public void eliminar(int id) {
		correoRepositorio.deleteById(id);
	}
	
	public void eliminarPQR(int id) {
		correoPQRRepositorio.deleteById(id);
	}

}
