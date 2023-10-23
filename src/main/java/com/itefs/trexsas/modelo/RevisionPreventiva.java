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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "revision_preventiva")
@JsonIgnoreProperties(value={ "vehiculo" }, allowSetters= true)
public class RevisionPreventiva {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_revision_preventiva")
    private Integer idRevisionPreventiva;
	
	@Column(name = "fecha_inicio_revision_preventiva")
    private String fechaInicioRevisionPreventiva;
	
	@Column(name = "fecha_vencimiento_revision_preventiva")
    private String fechaVencimientoRevisionPreventiva;
	
	@Column(name = "revision_preventiva")
    private String revisionPreventiva;
	
    @JsonProperty("vehiculo")
    @JoinColumn(name = "vehiculo_id_vehiculo", referencedColumnName = "id_vehiculo")
    @OneToOne(optional = false)
    private Vehiculo vehiculo;

    public RevisionPreventiva() {
    	super();
    }

	public Integer getIdRevisionPreventiva() {
		return idRevisionPreventiva;
	}

	public void setIdRevisionPreventiva(Integer idRevisionPreventiva) {
		this.idRevisionPreventiva = idRevisionPreventiva;
	}

	public String getFechaInicioRevisionPreventiva() {
		return fechaInicioRevisionPreventiva;
	}

	public void setFechaInicioRevisionPreventiva(String fechaInicioRevisionPreventiva) {
		this.fechaInicioRevisionPreventiva = fechaInicioRevisionPreventiva;
	}

	public String getFechaVencimientoRevisionPreventiva() {
		return fechaVencimientoRevisionPreventiva;
	}

	public void setFechaVencimientoRevisionPreventiva(String fechaVencimientoRevisionPreventiva) {
		this.fechaVencimientoRevisionPreventiva = fechaVencimientoRevisionPreventiva;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public String getRevisionPreventiva() {
		return revisionPreventiva;
	}

	public void setRevisionPreventiva(String revisionPreventiva) {
		this.revisionPreventiva = revisionPreventiva;
	}
    
    
}
