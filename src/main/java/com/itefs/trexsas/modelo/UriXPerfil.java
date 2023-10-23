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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "uri_x_perfil")
@JsonIgnoreProperties(value={ "perfil" }, allowSetters= true)
public class UriXPerfil {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_uri_x_perfil")
    private Long idUriXPerfil;
    @Basic(optional = false)
    @NotNull
    @Column(name = "acceso_uri_x_perfil")
    private Integer accesoUriXPerfil;
    @ManyToOne
    @JoinColumn(name = "uri_hija_id_uri_hija", nullable = false)
    private UriHija uriHija;
    @JsonProperty("perfil")
    @ManyToOne
    @JoinColumn(name = "perfil_id_perfil", nullable = false, updatable = false)
    private Perfil perfil;

    public UriXPerfil() {
    	super();
    }

	public Long getIdUriXPerfil() {
		return idUriXPerfil;
	}

	public void setIdUriXPerfil(Long idUriXPerfil) {
		this.idUriXPerfil = idUriXPerfil;
	}

	public Integer getAccesoUriXPerfil() {
		return accesoUriXPerfil;
	}

	public void setAccesoUriXPerfil(Integer accesoUriXPerfil) {
		this.accesoUriXPerfil = accesoUriXPerfil;
	}

	public UriHija getUriHija() {
		return uriHija;
	}

	public void setUriHija(UriHija uriHija) {
		this.uriHija = uriHija;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
    
    
	
}