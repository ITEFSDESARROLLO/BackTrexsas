package com.itefs.trexsas.servicio;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.Persona;
import com.itefs.trexsas.modelo.PersonaAutor;
import com.itefs.trexsas.repositorio.PersonaAutorRepositorio;
import com.itefs.trexsas.repositorio.PersonaRepositorio;

@Service
public class PersonaServicio {
	
	@Autowired
	private PersonaRepositorio personaRepositorio;
	@Autowired
	private PersonaAutorRepositorio personaAutorRepositorio;
	
	public Persona crear(Persona persona) {
		persona.setIdPersona(null);
		return personaRepositorio.save(persona);
	}
	
	public void actualizar(Persona persona) {
		personaRepositorio.save(persona);
	}
	
	public List<Persona> obtenerTodos() {
		return personaRepositorio.findAll();
	}
	
	public Persona obtenerPorId(Long id) {
		Optional<Persona> opPersona=personaRepositorio.findById(id);
		if(opPersona.isPresent()) {
			return opPersona.get();
		}
		return null;
	}
	
	public PersonaAutor obtenerAutorPorId(Long id) {
		Optional<PersonaAutor> opPersona=personaAutorRepositorio.findById(id);
		if(opPersona.isPresent()) {
			return opPersona.get();
		}
		return null;
	}
	
	public void eliminar(Persona persona) {
		personaRepositorio.delete(persona);
	}

}
