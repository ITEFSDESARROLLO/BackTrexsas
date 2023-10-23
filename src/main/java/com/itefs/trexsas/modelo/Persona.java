package com.itefs.trexsas.modelo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "persona")
public class Persona {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_persona")
    private Long idPersona;
	@NotNull(message = "error, campo nombrePersona obligatorio")
    
    @Column(name = "nombre_persona")
    private String nombrePersona;
	@NotNull(message = "error, campo apellidoPersona obligatorio")
    
    @Column(name = "apellido_persona")
    private String apellidoPersona;
    
    @Column(name = "tipo_doc_persona")
    private String tipoDocPersona;
	
    @Column(name = "documento_persona")
    private String documentoPersona;
	
    @OneToOne
    @JoinColumn(name = "ciudad_persona", referencedColumnName = "id_ciudad")
    private Ciudad ciudadPersona;

    @Size(max = 150, message = "error, campo direccionPersona maximo de 150 digitos")
    @Column(name = "direccion_persona")
    private String direccionPersona;
    @Size(max = 100, message = "error, campo observacionesPersona maximo de 100 digitos")
    @Column(name = "observaciones_persona")
    private String observacionesPersona;
    @Column(name = "fecha_nacimiento_persona", columnDefinition = "TIMESTAMP")
    private String fechaNacimientoPersona;
    
    @Email(message = "error, el campo correoPersona no corresponde a una direccion email valida")
    @Size(max = 100, message = "error, campo correoPersona maximo de 100 digitos")
    @Column(name = "correo_persona")
    private String correoPersona;
    
    @Column(name = "celular_uno_persona")
    private String celularUnoPersona;
    @Column(name = "celular_dos_persona")
    private String celularDosPersona;
    @Column(name = "telefono_persona")
    private String telefonoPersona;
    @ManyToOne
    @JoinColumn(name = "ciudad_expedicion_persona", referencedColumnName = "id_ciudad")
    private Ciudad ciudadExpedicionPersona;
    @Size(max = 200, message = "error, campo documentoUnoPersona maximo de 200 digitos")
    @Column(name = "documento_uno_persona")
    private String documentoUnoPersona;
    @Size(max = 200, message = "error, campo fotoPersona maximo de 200 digitos")
    @Column(name = "foto_persona")
    private String fotoPersona;
    @JsonIgnore
    @OneToOne(mappedBy = "persona", orphanRemoval = true, fetch = FetchType.LAZY)
    private Cuenta cuenta;
    @JsonIgnore
    @OneToOne(mappedBy = "persona", fetch = FetchType.LAZY)
    private Propietario propietario;
    @JsonIgnore
    @OneToOne(mappedBy = "persona", fetch = FetchType.LAZY)
    private Conductor conductor;
    
    @NotNull(message = "indicar el tipo de persona")
    @Column(name="tipo_persona")
    private Boolean tipoPersona;
    
    
    
	public Persona() {
		super();
	}


	@Override
	public String toString() {
		return "Persona [idPersona=" + idPersona + ", nombrePersona=" + nombrePersona + ", apellidoPersona="
				+ apellidoPersona + ", tipoDocPersona=" + tipoDocPersona + ", documentoPersona=" + documentoPersona
				+ ", direccionPersona=" + direccionPersona
				+ ", observacionesPersona=" + observacionesPersona + ", fechaNacimientoPersona="
				+ fechaNacimientoPersona + ", correoPersona=" + correoPersona + ", celularUnoPersona="
				+ celularUnoPersona + ", celularDosPersona=" + celularDosPersona + ", telefonoPersona="
				+ telefonoPersona +  ", documentoUnoPersona="
				+ documentoUnoPersona + ", fotoPersona=" + fotoPersona + ", cuenta=" + cuenta+"]";
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


	public String getTipoDocPersona() {
		return tipoDocPersona;
	}


	public void setTipoDocPersona(String tipoDocPersona) {
		this.tipoDocPersona = tipoDocPersona;
	}


	public String getDocumentoPersona() {
		return documentoPersona;
	}


	public void setDocumentoPersona(String documentoPersona) {
		this.documentoPersona = documentoPersona;
	}


	public Ciudad getCiudadPersona() {
		return ciudadPersona;
	}


	public void setCiudadPersona(Ciudad ciudadPersona) {
		this.ciudadPersona = ciudadPersona;
	}


	public String getDireccionPersona() {
		return direccionPersona;
	}


	public void setDireccionPersona(String direccionPersona) {
		this.direccionPersona = direccionPersona;
	}


	public String getObservacionesPersona() {
		return observacionesPersona;
	}


	public void setObservacionesPersona(String observacionesPersona) {
		this.observacionesPersona = observacionesPersona;
	}


	public String getFechaNacimientoPersona() {
		return fechaNacimientoPersona;
	}


	public void setFechaNacimientoPersona(String fechaNacimientoPersona) {
		this.fechaNacimientoPersona = fechaNacimientoPersona;
	}


	public String getCorreoPersona() {
		return correoPersona;
	}


	public void setCorreoPersona(String correoPersona) {
		this.correoPersona = correoPersona;
	}


	public String getCelularUnoPersona() {
		return celularUnoPersona;
	}


	public void setCelularUnoPersona(String celularUnoPersona) {
		this.celularUnoPersona = celularUnoPersona;
	}


	public String getCelularDosPersona() {
		return celularDosPersona;
	}


	public void setCelularDosPersona(String celularDosPersona) {
		this.celularDosPersona = celularDosPersona;
	}


	public String getTelefonoPersona() {
		return telefonoPersona;
	}


	public void setTelefonoPersona(String telefonoPersona) {
		this.telefonoPersona = telefonoPersona;
	}


	public Ciudad getCiudadExpedicionPersona() {
		return ciudadExpedicionPersona;
	}


	public void setCiudadExpedicionPersona(Ciudad ciudadExpedicionPersona) {
		this.ciudadExpedicionPersona = ciudadExpedicionPersona;
	}


	public String getDocumentoUnoPersona() {
		return documentoUnoPersona;
	}


	public void setDocumentoUnoPersona(String documentoUnoPersona) {
		this.documentoUnoPersona = documentoUnoPersona;
	}


	public String getFotoPersona() {
		return fotoPersona;
	}


	public void setFotoPersona(String fotoPersona) {
		this.fotoPersona = fotoPersona;
	}


	public Cuenta getCuenta() {
		return cuenta;
	}


	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}


	public Propietario getPropietario() {
		return propietario;
	}


	public void setPropietario(Propietario propietario) {
		this.propietario = propietario;
	}


	public Conductor getConductor() {
		return conductor;
	}


	public void setConductor(Conductor conductor) {
		this.conductor = conductor;
	}


	public Boolean getTipoPersona() {
		return tipoPersona;
	}


	public void setTipoPersona(Boolean tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
	
	

}
