package com.itefs.trexsas.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name = "tipo_carroceria")
public class TipoCarroceria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_tipo_carroceria")
	private Long id;
	
	@NotNull
	@Column(name="descripcion_carroceria")
	private String descripcionCarroceria;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcionCarroceria() {
		return descripcionCarroceria;
	}

	public void setDescripcionCarroceria(String descripcionCarroceria) {
		this.descripcionCarroceria = descripcionCarroceria;
	}

	
	
}
