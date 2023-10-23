package com.itefs.trexsas.modelo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "documento_vehiculo")
@JsonIgnoreProperties(value={ "vehiculo" }, allowSetters= true)
public class DocumentoVehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_documento_vehiculo")
    private Integer idDocumentoVehiculo;
	@Column(name = "en_regla_documento_vehiculo")
    private Integer enReglaDocumentoVehiculo;
    @Column(name = "soat_documento_vehiculo")
    private Integer soatDocumentoVehiculo;
    @Column(name = "poliza_extra_documento_vehiculo")
    private Integer polizaExtraDocumentoVehiculo;
    @Column(name = "poliza_documento_vehiculo")
    private Integer polizaDocumentoVehiculo;
    @Column(name = "tecnicomecanica_documento_vehiculo")
    private Integer tecnicomecanicaDocumentoVehiculo;
    @Column(name = "preventiva_documento_vehiculo")
    private Integer preventivaDocumentoVehiculo;
    
    
    @JsonProperty("vehiculo")
    @JoinColumn(name = "vehiculo_id_vehiculo", referencedColumnName = "id_vehiculo")
    @OneToOne(optional = false)
    private Vehiculo vehiculo;

    public DocumentoVehiculo() {
    	super();
    }

	public Integer getIdDocumentoVehiculo() {
		return idDocumentoVehiculo;
	}

	public void setIdDocumentoVehiculo(Integer idDocumentoVehiculo) {
		this.idDocumentoVehiculo = idDocumentoVehiculo;
	}

	public Integer getEnReglaDocumentoVehiculo() {
		return enReglaDocumentoVehiculo;
	}

	public void setEnReglaDocumentoVehiculo(Integer enReglaDocumentoVehiculo) {
		this.enReglaDocumentoVehiculo = enReglaDocumentoVehiculo;
	}

	public Integer getSoatDocumentoVehiculo() {
		return soatDocumentoVehiculo;
	}

	public void setSoatDocumentoVehiculo(Integer soatDocumentoVehiculo) {
		this.soatDocumentoVehiculo = soatDocumentoVehiculo;
	}

	public Integer getPolizaExtraDocumentoVehiculo() {
		return polizaExtraDocumentoVehiculo;
	}

	public void setPolizaExtraDocumentoVehiculo(Integer polizaExtraDocumentoVehiculo) {
		this.polizaExtraDocumentoVehiculo = polizaExtraDocumentoVehiculo;
	}

	public Integer getPolizaDocumentoVehiculo() {
		return polizaDocumentoVehiculo;
	}

	public void setPolizaDocumentoVehiculo(Integer polizaDocumentoVehiculo) {
		this.polizaDocumentoVehiculo = polizaDocumentoVehiculo;
	}

	public Integer getTecnicomecanicaDocumentoVehiculo() {
		return tecnicomecanicaDocumentoVehiculo;
	}

	public void setTecnicomecanicaDocumentoVehiculo(Integer tecnicomecanicaDocumentoVehiculo) {
		this.tecnicomecanicaDocumentoVehiculo = tecnicomecanicaDocumentoVehiculo;
	}

	public Integer getPreventivaDocumentoVehiculo() {
		return preventivaDocumentoVehiculo;
	}

	public void setPreventivaDocumentoVehiculo(Integer preventivaDocumentoVehiculo) {
		this.preventivaDocumentoVehiculo = preventivaDocumentoVehiculo;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}
    
    
}
