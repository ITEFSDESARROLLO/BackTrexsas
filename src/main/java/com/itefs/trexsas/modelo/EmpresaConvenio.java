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
@Table(name = "empresa_convenio")
public class EmpresaConvenio {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_empresa_convenio")
    private Integer idEmpresaConvenio;
	@NotNull(message = "error, campo nombreEmpresaConvenio obligatorio")
    @Size(max = 80, message = "error, campo nombreEmpresaConvenio maximo de 80 digitos")
    @Column(name = "nombre_empresa_convenio")
    private String nombreEmpresaConvenio;
    @NotNull(message = "error, campo nitEmpresaConvenio obligatorio")
    @Size(max = 20, message = "error, campo nitEmpresaConvenio maximo de 20 digitos")
    @Column(name = "nit_empresa_convenio")
    private String nitEmpresaConvenio;
    @NotNull(message = "error, campo estadoEmpresaConvenio obligatorio")
    @Column(name = "estado_empresa_convenio")
    private Integer estadoEmpresaConvenio;
    @Column(name = "fecha_registro_empresa_convenio")
	private String fechaRegistroEmpresaConvenio;
	@Column(name = "fecha_actualizacion_empresa_convenio")
	private String fechaActualizacionEmpresaConvenio;
	@ManyToOne
    @JoinColumn(name = "registrado_por_empresa_convenio", referencedColumnName = "id_persona")
	private PersonaAutor registradoPorEmpresaConvenio;
	@ManyToOne
    @JoinColumn(name = "actualizado_por_empresa_convenio", referencedColumnName = "id_persona")
	private PersonaAutor actualizadoPorEmpresaConvenio;

    public EmpresaConvenio() {
    	super();
    }

	public Integer getIdEmpresaConvenio() {
		return idEmpresaConvenio;
	}

	public void setIdEmpresaConvenio(Integer idEmpresaConvenio) {
		this.idEmpresaConvenio = idEmpresaConvenio;
	}

	public String getNombreEmpresaConvenio() {
		return nombreEmpresaConvenio;
	}

	public void setNombreEmpresaConvenio(String nombreEmpresaConvenio) {
		this.nombreEmpresaConvenio = nombreEmpresaConvenio;
	}

	public String getNitEmpresaConvenio() {
		return nitEmpresaConvenio;
	}

	public void setNitEmpresaConvenio(String nitEmpresaConvenio) {
		this.nitEmpresaConvenio = nitEmpresaConvenio;
	}

	public Integer getEstadoEmpresaConvenio() {
		return estadoEmpresaConvenio;
	}

	public void setEstadoEmpresaConvenio(Integer estadoEmpresaConvenio) {
		this.estadoEmpresaConvenio = estadoEmpresaConvenio;
	}

	public String getFechaRegistroEmpresaConvenio() {
		return fechaRegistroEmpresaConvenio;
	}

	public void setFechaRegistroEmpresaConvenio(String fechaRegistroEmpresaConvenio) {
		this.fechaRegistroEmpresaConvenio = fechaRegistroEmpresaConvenio;
	}

	public String getFechaActualizacionEmpresaConvenio() {
		return fechaActualizacionEmpresaConvenio;
	}

	public void setFechaActualizacionEmpresaConvenio(String fechaActualizacionEmpresaConvenio) {
		this.fechaActualizacionEmpresaConvenio = fechaActualizacionEmpresaConvenio;
	}

	public PersonaAutor getRegistradoPorEmpresaConvenio() {
		return registradoPorEmpresaConvenio;
	}

	public void setRegistradoPorEmpresaConvenio(PersonaAutor registradoPorEmpresaConvenio) {
		this.registradoPorEmpresaConvenio = registradoPorEmpresaConvenio;
	}

	public PersonaAutor getActualizadoPorEmpresaConvenio() {
		return actualizadoPorEmpresaConvenio;
	}

	public void setActualizadoPorEmpresaConvenio(PersonaAutor actualizadoPorEmpresaConvenio) {
		this.actualizadoPorEmpresaConvenio = actualizadoPorEmpresaConvenio;
	}
    
	
}
