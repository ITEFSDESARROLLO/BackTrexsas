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
import javax.validation.constraints.Size;


@Entity
@Table(name = "uri_hija")
public class UriHija {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_uri_hija")
    private Integer idUriHija;
    @Size(max = 45, message = "error, campo uriHija maximo de 45 digitos")
    @Column(name = "uri_hija")
    private String uriHija;
    @Size(max = 200, message = "error, campo descripcionUriHija maximo de 200 digitos")
    @Column(name = "descripcion_uri_hija")
    private String descripcionUriHija;
    @ManyToOne
    @JoinColumn(name = "uri_padre_id_uri_padre", nullable = false, updatable = false)
    private UriPadre uriPadre;

    public UriHija() {
    	super();
    }

	public Integer getIdUriHija() {
		return idUriHija;
	}

	public void setIdUriHija(Integer idUriHija) {
		this.idUriHija = idUriHija;
	}

	public String getUriHija() {
		return uriHija;
	}

	public void setUriHija(String uriHija) {
		this.uriHija = uriHija;
	}

	public String getDescripcionUriHija() {
		return descripcionUriHija;
	}

	public void setDescripcionUriHija(String descripcionUriHija) {
		this.descripcionUriHija = descripcionUriHija;
	}

	public UriPadre getUriPadre() {
		return uriPadre;
	}

	public void setUriPadre(UriPadre uriPadre) {
		this.uriPadre = uriPadre;
	}
	
    
}
