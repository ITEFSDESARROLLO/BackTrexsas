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
@Table(name = "marca")
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_marca")
    private Integer idMarca;
    @NotNull(message = "error, campo marca obligatorio")
    @Size(max = 30, message = "error, campo marca maximo de 30 digitos")
    @Column(name = "marca")
    private String marca;
    @NotNull(message = "error, campo estadoMarca obligatorio")
    @Column(name = "estado_marca")
    private Integer estadoMarca;
    @Column(name = "fecha_registro_marca")
	private String fechaRegistroMarca;
	@Column(name = "fecha_actualizacion_marca")
	private String fechaActualizacionMarca;
	@ManyToOne
    @JoinColumn(name = "registrado_por_marca", referencedColumnName = "id_persona")
	private PersonaAutor registradoPorMarca;
	@ManyToOne
    @JoinColumn(name = "actualizado_por_marca", referencedColumnName = "id_persona")
	private PersonaAutor actualizadoPorMarca;

    public Marca() {
    	super();
    }

	public Integer getIdMarca() {
		return idMarca;
	}

	public void setIdMarca(Integer idMarca) {
		this.idMarca = idMarca;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Integer getEstadoMarca() {
		return estadoMarca;
	}

	public void setEstadoMarca(Integer estadoMarca) {
		this.estadoMarca = estadoMarca;
	}

	public String getFechaRegistroMarca() {
		return fechaRegistroMarca;
	}

	public void setFechaRegistroMarca(String fechaRegistroMarca) {
		this.fechaRegistroMarca = fechaRegistroMarca;
	}

	public String getFechaActualizacionMarca() {
		return fechaActualizacionMarca;
	}

	public void setFechaActualizacionMarca(String fechaActualizacionMarca) {
		this.fechaActualizacionMarca = fechaActualizacionMarca;
	}

	public PersonaAutor getRegistradoPorMarca() {
		return registradoPorMarca;
	}

	public void setRegistradoPorMarca(PersonaAutor registradoPorMarca) {
		this.registradoPorMarca = registradoPorMarca;
	}

	public PersonaAutor getActualizadoPorMarca() {
		return actualizadoPorMarca;
	}

	public void setActualizadoPorMarca(PersonaAutor actualizadoPorMarca) {
		this.actualizadoPorMarca = actualizadoPorMarca;
	}
	
	
    
}
