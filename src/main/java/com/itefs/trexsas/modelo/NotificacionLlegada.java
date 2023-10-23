package com.itefs.trexsas.modelo;

import java.util.HashMap;
import java.util.List;



public class NotificacionLlegada 
{
	
	
	private Long id;
	
	private String tituloNotificacion;
	
	private String descripcionNotificacion;
	
	private long tipoNotificacion;
	
	private Long estadoNotificacion;

	private HashMap<Long,String> perfilesNotificacion;
	private List<Integer> perfiles;

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

	public HashMap<Long, String> getPerfilesNotificacion() {
		return perfilesNotificacion;
	}

	public void setPerfilesNotificacion(HashMap<Long, String> perfilesNotificacion) {
		this.perfilesNotificacion = perfilesNotificacion;
	}

	public List<Integer> getPerfiles() {
		return perfiles;
	}

	public void setPerfiles(List<Integer> perfiles) {
		this.perfiles = perfiles;
	}

	

}
