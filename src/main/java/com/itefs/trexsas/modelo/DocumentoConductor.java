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
@Table(name = "documento_conductor")
@JsonIgnoreProperties(value={ "conductor" }, allowSetters= true)
public class DocumentoConductor {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_documento_conductor")
    private Integer idDocumentoConductor;
    @Column(name = "en_regla_documento_conductor")
    private Integer enReglaDocumentoConductor;
    @Column(name = "eps_documento_conductor")
    private Integer epsDocumentoConductor;
    @Column(name = "arl_documento_conductor")
    private Integer arlDocumentoConductor;
    @Column(name = "licencia_documento_conductor")
    private Integer licenciaDocumentoConductor;
    @JoinColumn(name = "conductor_id_conductor", referencedColumnName = "id_conductor")
    @OneToOne(optional = false)
    @JsonProperty("conductor")
    private Conductor conductor;

    public DocumentoConductor() {
    	super();
    }

	public Integer getIdDocumentoConductor() {
		return idDocumentoConductor;
	}

	public void setIdDocumentoConductor(Integer idDocumentoConductor) {
		this.idDocumentoConductor = idDocumentoConductor;
	}

	public Integer getEnReglaDocumentoConductor() {
		return enReglaDocumentoConductor;
	}

	public void setEnReglaDocumentoConductor(Integer enReglaDocumentoConductor) {
		this.enReglaDocumentoConductor = enReglaDocumentoConductor;
	}

	public Integer getEpsDocumentoConductor() {
		return epsDocumentoConductor;
	}

	public void setEpsDocumentoConductor(Integer epsDocumentoConductor) {
		this.epsDocumentoConductor = epsDocumentoConductor;
	}

	public Integer getArlDocumentoConductor() {
		return arlDocumentoConductor;
	}

	public void setArlDocumentoConductor(Integer arlDocumentoConductor) {
		this.arlDocumentoConductor = arlDocumentoConductor;
	}

	public Integer getLicenciaDocumentoConductor() {
		return licenciaDocumentoConductor;
	}

	public void setLicenciaDocumentoConductor(Integer licenciaDocumentoConductor) {
		this.licenciaDocumentoConductor = licenciaDocumentoConductor;
	}

	public Conductor getConductor() {
		return conductor;
	}

	public void setConductor(Conductor conductor) {
		this.conductor = conductor;
	}
    
    
}
