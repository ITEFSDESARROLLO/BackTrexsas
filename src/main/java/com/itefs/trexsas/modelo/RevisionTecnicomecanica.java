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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "revision_tecnicomecanica")
@JsonIgnoreProperties(value={ "vehiculo" }, allowSetters= true)
public class RevisionTecnicomecanica {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_revision_tecnicomecanica")
    private Integer idRevisionTecnicomecanica;
	
	
	@Column(name="numero_revision_tecnicomecanica")
	private Long numeroRevisionTecnicoMecanica;
	
    @Column(name = "fecha_revision_tecnicomecanica")
    
    private String fechaRevisionTecnicomecanica;
    @Column(name = "fecha_vencimiento_revision_tecnicomecanica")
	
    private String fechaVencimientoRevisionTecnicomecanica;
    
    @Column(name = "revision_tecnicomecanica")
    private String revisionTecnicomecanica;
    
    
    @JsonProperty("vehiculo")
    @JoinColumn(name = "vehiculo_id_vehiculo", referencedColumnName = "id_vehiculo")
    @OneToOne(optional = false)
    private Vehiculo vehiculo;
    
    

    public RevisionTecnicomecanica() {
    	super();
    }

	public Integer getIdRevisionTecnicomecanica() {
		return idRevisionTecnicomecanica;
	}

	public void setIdRevisionTecnicomecanica(Integer idRevisionTecnicomecanica) {
		this.idRevisionTecnicomecanica = idRevisionTecnicomecanica;
	}

	public String getFechaRevisionTecnicomecanica() {
		return fechaRevisionTecnicomecanica;
	}

	public void setFechaRevisionTecnicomecanica(String fechaRevisionTecnicomecanica) {
		this.fechaRevisionTecnicomecanica = fechaRevisionTecnicomecanica;
	}

	public String getFechaVencimientoRevisionTecnicomecanica() {
		return fechaVencimientoRevisionTecnicomecanica;
	}

	public void setFechaVencimientoRevisionTecnicomecanica(String fechaVencimientoRevisionTecnicomecanica) {
		this.fechaVencimientoRevisionTecnicomecanica = fechaVencimientoRevisionTecnicomecanica;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public String getRevisionTecnicomecanica() {
		return revisionTecnicomecanica;
	}

	public void setRevisionTecnicomecanica(String revisionTecnicomecanica) {
		this.revisionTecnicomecanica = revisionTecnicomecanica;
	}

	public Long getNumeroRevisionTecnicoMecanica() {
		return numeroRevisionTecnicoMecanica;
	}

	public void setNumeroRevisionTecnicoMecanica(Long numeroRevisionTecnicoMecanica) {
		this.numeroRevisionTecnicoMecanica = numeroRevisionTecnicoMecanica;
	}
	
	
    
}
