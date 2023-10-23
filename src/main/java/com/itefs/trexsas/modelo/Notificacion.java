package com.itefs.trexsas.modelo;

import java.util.List;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="notificacion")
public class Notificacion 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_notificacion")
	private Long id;
	@NotNull
	@Column(name="titulo_notificacion")
	private String tituloNotificacion;
	@NotNull
	@Column(name="descripcion_notificacion")
	private String descripcionNotificacion;
	@NotNull
	@Column(name="tipo")
	private long tipoNotificacion;
	@NotNull
	@Column(name="estado")
	private Long estadoNotificacion;
	
	
	@OneToMany(mappedBy="notificacion")
	private List<NotificacionPerfil> perfilesNotificacion;
	
	//@JsonIgnore
	/*@ManyToMany
	@JoinTable(name = "notificacion_perfil", joinColumns = {@JoinColumn(name = "id_notificacion",referencedColumnName = "id_notificacion")},
	inverseJoinColumns = {@JoinColumn(name = "id_perfil", referencedColumnName = "id_perfil")})
	private List<Perfil> perfilesNotificacion;*/
	
	//private Long persona;

	public List<NotificacionPerfil> getPerfilesNotificacion() {
		return perfilesNotificacion;
	}

	public void setPerfilesNotificacion(List<NotificacionPerfil> perfilesNotificacion) {
		this.perfilesNotificacion = perfilesNotificacion;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTituloNotificacion() {
		return tituloNotificacion;
	}

	public void setTituloNotificacion(String tituloNotificacion) {
		this.tituloNotificacion = tituloNotificacion;
	}

	public String getDescripcionNotificacion() {
		return descripcionNotificacion;
	}

	public void setDescripcionNotificacion(String descripcionNotificacion) {
		this.descripcionNotificacion = descripcionNotificacion;
	}

	public long getTipoNotificacion() {
		return tipoNotificacion;
	}

	public void setTipoNotificacion(long tipoNotificacion) {
		this.tipoNotificacion = tipoNotificacion;
	}

	public Long getEstadoNotificacion() {
		return estadoNotificacion;
	}

	public void setEstadoNotificacion(Long estadoNotificacion) {
		this.estadoNotificacion = estadoNotificacion;
	}

	@Override
	public String toString() {
		return "Notificacion [id=" + id + ", tituloNotificacion=" + tituloNotificacion + ", descripcionNotificacion="
				+ descripcionNotificacion + ", tipoNotificacion=" + tipoNotificacion + ", estadoNotificacion="
				+ estadoNotificacion + ", perfilesNotificacion=" + perfilesNotificacion + "]";
	}

	/*
	public List<Perfil> getPerfilesNotificacion() {
		return perfilesNotificacion;
	}

	public void setPerfilesNotificacion(List<Perfil> perfilesNotificacion) {
		this.perfilesNotificacion = perfilesNotificacion;
	}*/

	/*public Long getPersona() {
		return persona;
	}

	public void setPersona(Long persona) {
		this.persona = persona;
	}*/
	
	

}
