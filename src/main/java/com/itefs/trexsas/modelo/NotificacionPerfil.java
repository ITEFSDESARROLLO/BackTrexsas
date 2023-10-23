package com.itefs.trexsas.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="notificacion_perfil")
public class NotificacionPerfil 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_notificacion_perfil")
	private Long id;
	@NotNull
	@Column(name="id_perfil")
	private Long perfil;
	@JsonIgnore
	@ManyToOne
	@NotNull
	@JoinColumn(name="id_notificacion")
	private Notificacion notificacion;
	
	@Column(name="id_persona")
	private Long persona;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPerfil() {
		return perfil;
	}
	public void setPerfil(Long perfil) {
		this.perfil = perfil;
	}
	public Notificacion getNotificacion() {
		return notificacion;
	}
	public void setNotificacion(Notificacion notificacion) {
		this.notificacion = notificacion;
	}
	public Long getPersona() {
		return persona;
	}
	public void setPersona(Long persona) {
		this.persona = persona;
	}
	
	
	
	
}
