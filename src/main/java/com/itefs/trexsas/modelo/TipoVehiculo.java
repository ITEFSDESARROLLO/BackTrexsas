package com.itefs.trexsas.modelo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tipo_vehiculo")
public class TipoVehiculo {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_vehiculo")
    private Integer idTipoVehiculo;
	@NotNull(message = "error, campo tipoVehiculo obligatorio")
    @Size(max = 45, message = "error, campo marca maximo de 30 digitos")
    @Column(name = "tipo_vehiculo")
    private String tipoVehiculo;
	@NotNull(message = "error, campo estadoTipoVehiculo obligatorio")
    @Column(name = "estado_tipo_vehiculo")
    private Integer estadoTipoVehiculo;
    @Column(name = "fecha_registro_tipo_vehiculo")
	private String fechaRegistroTipoVehiculo;
	@Column(name = "fecha_actualizacion_tipo_vehiculo")
	private String fechaActualizacionTipoVehiculo;
	@ManyToOne
    @JoinColumn(name = "registrado_por_tipo_vehiculo", referencedColumnName = "id_persona")
	private PersonaAutor registradoPorTipoVehiculo;
	@ManyToOne
    @JoinColumn(name = "actualizado_por_tipo_vehiculo", referencedColumnName = "id_persona")
	private PersonaAutor actualizadoPorTipoVehiculo;

    public TipoVehiculo() {
    	super();
    }

	public Integer getIdTipoVehiculo() {
		return idTipoVehiculo;
	}

	public void setIdTipoVehiculo(Integer idTipoVehiculo) {
		this.idTipoVehiculo = idTipoVehiculo;
	}

	public String getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public Integer getEstadoTipoVehiculo() {
		return estadoTipoVehiculo;
	}

	public void setEstadoTipoVehiculo(Integer estadoTipoVehiculo) {
		this.estadoTipoVehiculo = estadoTipoVehiculo;
	}

	public String getFechaRegistroTipoVehiculo() {
		return fechaRegistroTipoVehiculo;
	}

	public void setFechaRegistroTipoVehiculo(String fechaRegistroTipoVehiculo) {
		this.fechaRegistroTipoVehiculo = fechaRegistroTipoVehiculo;
	}

	public String getFechaActualizacionTipoVehiculo() {
		return fechaActualizacionTipoVehiculo;
	}

	public void setFechaActualizacionTipoVehiculo(String fechaActualizacionTipoVehiculo) {
		this.fechaActualizacionTipoVehiculo = fechaActualizacionTipoVehiculo;
	}

	public PersonaAutor getRegistradoPorTipoVehiculo() {
		return registradoPorTipoVehiculo;
	}

	public void setRegistradoPorTipoVehiculo(PersonaAutor registradoPorTipoVehiculo) {
		this.registradoPorTipoVehiculo = registradoPorTipoVehiculo;
	}

	public PersonaAutor getActualizadoPorTipoVehiculo() {
		return actualizadoPorTipoVehiculo;
	}

	public void setActualizadoPorTipoVehiculo(PersonaAutor actualizadoPorTipoVehiculo) {
		this.actualizadoPorTipoVehiculo = actualizadoPorTipoVehiculo;
	}
    
    
}
