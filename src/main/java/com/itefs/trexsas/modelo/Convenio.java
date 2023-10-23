package com.itefs.trexsas.modelo;


import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "convenio")
@JsonIgnoreProperties(value={ "vehiculo" }, allowSetters= true)
public class Convenio {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_convenio")
    private Integer idConvenio;
	
    @Column(name = "fecha_inicio_convenio")
    private String fechaInicioConvenio;
	
    @Column(name = "fecha_fin_convenio")
    private String fechaFinConvenio;
    @Size(max = 45, message = "error, campo licenciaConvenio maximo de 45 digitos")
    @Column(name = "licencia_convenio")
    private String licenciaConvenio;
    
    @Size(max = 200, message = "error, campo convenio maximo de 200 digitos")
    @Column(name = "convenio")
    private String convenio;
    @Column(name = "fecha_registro_convenio")
	private String fechaRegistroConvenio;
	@Column(name = "fecha_actualizacion_convenio")
	private String fechaActualizacionConvenio;
	@ManyToOne
    @JoinColumn(name = "registrado_por_convenio", referencedColumnName = "id_persona")
	private Persona registradoPorConvenio;
	@ManyToOne
    @JoinColumn(name = "actualizado_por_convenio", referencedColumnName = "id_persona")
	private Persona actualizadoPorConvenio;
    
    @JoinColumn(name = "empresa_convenio_id_empresa_convenio", referencedColumnName = "id_empresa_convenio")
    @ManyToOne
    private EmpresaConvenio empresaConvenio;
    @JsonProperty("vehiculo")
    @JoinColumn(name = "vehiculo_id_vehiculo", referencedColumnName = "id_vehiculo")
    @OneToOne
    private Vehiculo vehiculo;
    
    public Convenio() {
    	super();
    }

	public Integer getIdConvenio() {
		return idConvenio;
	}

	public void setIdConvenio(Integer idConvenio) {
		this.idConvenio = idConvenio;
	}

	public String getFechaInicioConvenio() {
		return fechaInicioConvenio;
	}

	public void setFechaInicioConvenio(String fechaInicioConvenio) {
		this.fechaInicioConvenio = fechaInicioConvenio;
	}

	public String getFechaFinConvenio() {
		return fechaFinConvenio;
	}

	public void setFechaFinConvenio(String fechaFinConvenio) {
		this.fechaFinConvenio = fechaFinConvenio;
	}

	public String getLicenciaConvenio() {
		return licenciaConvenio;
	}

	public void setLicenciaConvenio(String licenciaConvenio) {
		this.licenciaConvenio = licenciaConvenio;
	}

	public EmpresaConvenio getEmpresaConvenio() {
		return empresaConvenio;
	}

	public void setEmpresaConvenio(EmpresaConvenio empresaConvenio) {
		this.empresaConvenio = empresaConvenio;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public String getConvenio() {
		return convenio;
	}

	public void setConvenio(String convenio) {
		this.convenio = convenio;
	}

	public String getFechaRegistroConvenio() {
		return fechaRegistroConvenio;
	}

	public void setFechaRegistroConvenio(String fechaRegistroConvenio) {
		this.fechaRegistroConvenio = fechaRegistroConvenio;
	}

	public String getFechaActualizacionConvenio() {
		return fechaActualizacionConvenio;
	}

	public void setFechaActualizacionConvenio(String fechaActualizacionConvenio) {
		this.fechaActualizacionConvenio = fechaActualizacionConvenio;
	}

	public Persona getRegistradoPorConvenio() {
		return registradoPorConvenio;
	}

	public void setRegistradoPorConvenio(Persona registradoPorConvenio) {
		this.registradoPorConvenio = registradoPorConvenio;
	}

	public Persona getActualizadoPorConvenio() {
		return actualizadoPorConvenio;
	}

	public void setActualizadoPorConvenio(Persona actualizadoPorConvenio) {
		this.actualizadoPorConvenio = actualizadoPorConvenio;
	}
    
    
}
