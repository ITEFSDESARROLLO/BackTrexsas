package com.itefs.trexsas.modelo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "uri_padre")
public class UriPadre {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_uri_padre")
    private Integer idUriPadre;
    @Size(max = 45, message = "error, campo uriPadre maximo de 45 digitos")
    @Column(name = "uri_padre")
    private String uriPadre;
    @Size(max = 500, message = "error, campo descripcionUriPadre maximo de 500 digitos")
    @Column(name = "descripcion_uri_padre")
    private String descripcionUriPadre;

    public UriPadre() {
    	super();
    }

	public Integer getIdUriPadre() {
		return idUriPadre;
	}

	public void setIdUriPadre(Integer idUriPadre) {
		this.idUriPadre = idUriPadre;
	}

	public String getUriPadre() {
		return uriPadre;
	}

	public void setUriPadre(String uriPadre) {
		this.uriPadre = uriPadre;
	}

	public String getDescripcionUriPadre() {
		return descripcionUriPadre;
	}

	public void setDescripcionUriPadre(String descripcionUriPadre) {
		this.descripcionUriPadre = descripcionUriPadre;
	}
    
    

}