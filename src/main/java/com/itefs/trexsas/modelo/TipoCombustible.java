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
@Table(name = "tipo_combustible")
public class TipoCombustible {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_combustible")
    private Integer idTipoCombustible;
    @NotNull(message = "error, campo tipoCombustible obligatorio")
    @Size(max = 30, message = "error, campo marca maximo de 30 digitos")
    @Column(name = "tipo_combustible")
    private String tipoCombustible;
    @NotNull(message = "error, campo estadoTipoCombustible obligatorio")
    @Column(name = "estado_tipo_combustible")
    private Integer estadoTipoCombustible;
    @Column(name = "fecha_registro_tipo_combustible")
   	private String fechaRegistroTipoCombustible;
   	@Column(name = "fecha_actualizacion_tipo_combustible")
   	private String fechaActualizacionTipoCombustible;
   	@ManyToOne
    @JoinColumn(name = "registrado_por_tipo_combustible", referencedColumnName = "id_persona")
   	private PersonaAutor registradoPorTipoCombustible;
   	@ManyToOne
    @JoinColumn(name = "actualizado_por_tipo_combustible", referencedColumnName = "id_persona")
   	private PersonaAutor actualizadoPorTipoCombustible;

    public TipoCombustible() {
    	super();
    }

	public Integer getIdTipoCombustible() {
		return idTipoCombustible;
	}

	public void setIdTipoCombustible(Integer idTipoCombustible) {
		this.idTipoCombustible = idTipoCombustible;
	}

	public String getTipoCombustible() {
		return tipoCombustible;
	}

	public void setTipoCombustible(String tipoCombustible) {
		this.tipoCombustible = tipoCombustible;
	}

	public Integer getEstadoTipoCombustible() {
		return estadoTipoCombustible;
	}

	public void setEstadoTipoCombustible(Integer estadoTipoCombustible) {
		this.estadoTipoCombustible = estadoTipoCombustible;
	}

	public String getFechaRegistroTipoCombustible() {
		return fechaRegistroTipoCombustible;
	}

	public void setFechaRegistroTipoCombustible(String fechaRegistroTipoCombustible) {
		this.fechaRegistroTipoCombustible = fechaRegistroTipoCombustible;
	}

	public String getFechaActualizacionTipoCombustible() {
		return fechaActualizacionTipoCombustible;
	}

	public void setFechaActualizacionTipoCombustible(String fechaActualizacionTipoCombustible) {
		this.fechaActualizacionTipoCombustible = fechaActualizacionTipoCombustible;
	}

	public PersonaAutor getRegistradoPorTipoCombustible() {
		return registradoPorTipoCombustible;
	}

	public void setRegistradoPorTipoCombustible(PersonaAutor registradoPorTipoCombustible) {
		this.registradoPorTipoCombustible = registradoPorTipoCombustible;
	}

	public PersonaAutor getActualizadoPorTipoCombustible() {
		return actualizadoPorTipoCombustible;
	}

	public void setActualizadoPorTipoCombustible(PersonaAutor actualizadoPorTipoCombustible) {
		this.actualizadoPorTipoCombustible = actualizadoPorTipoCombustible;
	}

	
    
}
