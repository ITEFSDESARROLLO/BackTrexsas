package com.itefs.trexsas.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name="color_vehiculo")
public class ColorVehiculo{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_color")
	private Long id;
	
	@NotNull
	@Column(name="descripcion_color")
	private String descripcionColor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcionColor() {
		return descripcionColor;
	}

	public void setDescripcionColor(String descripcionColor) {
		this.descripcionColor = descripcionColor;
	}

}
