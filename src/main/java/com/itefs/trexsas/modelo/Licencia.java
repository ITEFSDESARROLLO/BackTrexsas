package com.itefs.trexsas.modelo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "licencia")
@JsonIgnoreProperties(value={ "conductor" }, allowSetters= true)
public class Licencia {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_licencia")
    private Long idLicencia;
	@NotNull(message = "error, campo obligatorio")
    @Column(name = "fecha_expedicion_licencia")
    private String fechaExpedicionLicencia;
	@NotNull(message = "error, campo obligatorio")
    @Column(name = "fecha_vencimiento_licencia")
    private String fechaVencimientoLicencia;
	@Size(max = 30, message = "error, campo maximo de 30 digitos")
    @Column(name = "numero_licencia")
    private String numeroLicencia;
	@Size(max = 2, message = "error, campo maximo de 2 digitos")
    @Column(name = "categoria_licencia")
    private String categoriaLicencia;
	@Size(max = 200, message = "error, campo maximo de 200 digitos")
    @Column(name = "documento_uno_licencia")
    private String documentoUnoLicencia;
	@Size(max = 200, message = "error, campo maximo de 200 digitos")
    @Column(name = "documento_dos_licencia")
    private String documentoDosLicencia;
    @JoinColumn(name = "conductor_id_conductor", referencedColumnName = "id_conductor")
    @OneToOne(optional = false)
    @JsonProperty("conductor")
    private Conductor conductor;

    public Licencia() {
    	super();
    }

	public Long getIdLicencia() {
		return idLicencia;
	}

	public void setIdLicencia(Long idLicencia) {
		this.idLicencia = idLicencia;
	}

	public String getFechaExpedicionLicencia() {
		return fechaExpedicionLicencia;
	}

	public void setFechaExpedicionLicencia(String fechaExpedicionLicencia) {
		this.fechaExpedicionLicencia = fechaExpedicionLicencia;
	}

	public String getFechaVencimientoLicencia() {
		return fechaVencimientoLicencia;
	}

	public void setFechaVencimientoLicencia(String fechaVencimientoLicencia) {
		this.fechaVencimientoLicencia = fechaVencimientoLicencia;
	}

	public String getNumeroLicencia() {
		return numeroLicencia;
	}

	public void setNumeroLicencia(String numeroLicencia) {
		this.numeroLicencia = numeroLicencia;
	}

	public String getCategoriaLicencia() {
		return categoriaLicencia;
	}

	public void setCategoriaLicencia(String categoriaLicencia) {
		this.categoriaLicencia = categoriaLicencia;
	}

	public Conductor getConductor() {
		return conductor;
	}

	public void setConductor(Conductor conductor) {
		this.conductor = conductor;
	}

	public String getDocumentoUnoLicencia() {
		return documentoUnoLicencia;
	}

	public void setDocumentoUnoLicencia(String documentoUnoLicencia) {
		this.documentoUnoLicencia = documentoUnoLicencia;
	}

	public String getDocumentoDosLicencia() {
		return documentoDosLicencia;
	}

	public void setDocumentoDosLicencia(String documentoDosLicencia) {
		this.documentoDosLicencia = documentoDosLicencia;
	}
    
    
}
