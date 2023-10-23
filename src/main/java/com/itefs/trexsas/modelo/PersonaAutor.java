package com.itefs.trexsas.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "persona")
public class PersonaAutor {
	
	@Id
    @Column(name = "id_persona")
    private Long idPersona;
    @Column(name = "nombre_persona")
    private String nombrePersona;
    @Column(name = "apellido_persona")
    private String apellidoPersona;
    @Column(name = "documento_persona")
    private String documentoPersona;
	
    
	public PersonaAutor() {
		super();
	}


	public Long getIdPersona() {
		return idPersona;
	}


	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}


	public String getNombrePersona() {
		return nombrePersona;
	}


	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
	}


	public String getApellidoPersona() {
		return apellidoPersona;
	}


	public void setApellidoPersona(String apellidoPersona) {
		this.apellidoPersona = apellidoPersona;
	}


	


	public String getDocumentoPersona() {
		return documentoPersona;
	}


	public void setDocumentoPersona(String documentoPersona) {
		this.documentoPersona = documentoPersona;
	}



}
