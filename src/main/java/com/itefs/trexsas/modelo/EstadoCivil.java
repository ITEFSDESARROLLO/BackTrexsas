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
@Table(name = "estado_civil")
public class EstadoCivil {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_estado_civil")
    private Integer idEstadoCivil;
	@Size(max = 30, message = "error, campo maximo de 30 digitos")
    @Column(name = "estado_civil")
    private String estadoCivil;
    
    
	public EstadoCivil() {
		super();
	}
	
	public Integer getIdEstadoCivil() {
		return idEstadoCivil;
	}
	
	public void setIdEstadoCivil(Integer idEstadoCivil) {
		this.idEstadoCivil = idEstadoCivil;
	}
	
	public String getEstadoCivil() {
		return estadoCivil;
	}
	
	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
    
    
}
