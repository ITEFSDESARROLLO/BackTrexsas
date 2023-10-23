package com.itefs.trexsas.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GeneratorType;

@Entity
@Table(name="vehiculo_conductor")
public class VehiculoConductor 
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_vehiculo_conductor")
	private Integer id;
	
	@NotNull
	@Column(name="conductor_id_conductor")
	private Integer conductor;
	
	@NotNull
	@Column(name="vehiculo_id_vehiculo")
	private Integer vehiculo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getConductor() {
		return conductor;
	}

	public void setConductor(Integer conductor) {
		this.conductor = conductor;
	}

	public Integer getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Integer vehiculo) {
		this.vehiculo = vehiculo;
	}
	
	
}
