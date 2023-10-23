package com.itefs.trexsas.modelo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pasajero")
public class Pasajero {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_pasajero")
    private Integer idPasajero;
	@Column(name = "estado_pasajero")
	private Integer estadoPasajero;
	@Column(name = "fecha_registro_pasajero")
	private String fechaRegistroPasajero;
	@Column(name = "fecha_actualizacion_pasajero")
	private String fechaActualizacionPasajero;
	@OneToOne
    @JoinColumn(name = "registrado_por_pasajero", referencedColumnName = "id_persona")
	private PersonaAutor registradoPorPasajero;
	@OneToOne
    @JoinColumn(name = "actualizado_por_pasajero", referencedColumnName = "id_persona")
	private PersonaAutor actualizadoPorPasajero;
	@OneToOne
    @JoinColumn(name = "persona_id_persona", nullable = false, updatable = false,referencedColumnName = "id_persona")
    private Persona persona;
	@ManyToOne
    @JoinColumn(name = "cliente_id_cliente", nullable = true,referencedColumnName = "id_cliente")
    private Cliente cliente;
	
	public Pasajero(){
		super();
	}

	public Integer getIdPasajero() {
		return idPasajero;
	}

	public void setIdPasajero(Integer idPasajero) {
		this.idPasajero = idPasajero;
	}

	public Integer getEstadoPasajero() {
		return estadoPasajero;
	}

	public void setEstadoPasajero(Integer estadoPasajero) {
		this.estadoPasajero = estadoPasajero;
	}

	public String getFechaRegistroPasajero() {
		return fechaRegistroPasajero;
	}

	public void setFechaRegistroPasajero(String fechaRegistroPasajero) {
		this.fechaRegistroPasajero = fechaRegistroPasajero;
	}

	public String getFechaActualizacionPasajero() {
		return fechaActualizacionPasajero;
	}

	public void setFechaActualizacionPasajero(String fechaActualizacionPasajero) {
		this.fechaActualizacionPasajero = fechaActualizacionPasajero;
	}

	public PersonaAutor getRegistradoPorPasajero() {
		return registradoPorPasajero;
	}

	public void setRegistradoPorPasajero(PersonaAutor registradoPorPasajero) {
		this.registradoPorPasajero = registradoPorPasajero;
	}

	public PersonaAutor getActualizadoPorPasajero() {
		return actualizadoPorPasajero;
	}

	public void setActualizadoPorPasajero(PersonaAutor actualizadoPorPasajero) {
		this.actualizadoPorPasajero = actualizadoPorPasajero;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	
	
	
}
