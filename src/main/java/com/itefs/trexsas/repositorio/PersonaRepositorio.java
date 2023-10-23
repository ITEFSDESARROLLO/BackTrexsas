package com.itefs.trexsas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itefs.trexsas.modelo.Persona;

public interface PersonaRepositorio extends JpaRepository <Persona, Long> 
{
	Persona findByNombrePersonaAndApellidoPersona(String nombrePersona, String apellidoPersona);
	Persona findByDocumentoPersona(String id);

}
