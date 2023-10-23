package com.itefs.trexsas.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="notificacion_individual")
public class NotificacionIndividual 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_notificacion_individual")
	private Long idNotificacionIndividual;
	@NotNull
	@Column(name="id_notificacion")
	private Long idNotificacion;
	@NotNull
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona")
	private Persona persona;
	public Long getIdNotificacionIndividual() {
		return idNotificacionIndividual;
	}
	public void setIdNotificacionIndividual(Long idNotificacionIndividual) {
		this.idNotificacionIndividual = idNotificacionIndividual;
	}
	public Long getIdNotificacion() {
		return idNotificacion;
	}
	public void setIdNotificacion(Long idNotificacion) {
		this.idNotificacion = idNotificacion;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona idPersona) {
		this.persona = idPersona;
	}
	
	

}
