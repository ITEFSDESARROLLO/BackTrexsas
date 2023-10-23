package com.itefs.trexsas.modelo;


import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "configuraciones")
public class Configuraciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_configuraciones")
    private Integer idConfiguraciones;
    @NotNull(message = "error, campo maximoDuracionContrato obligatorio")
    @Column(name = "maximo_duracion_contrato")
    private Integer maximoDuracionContrato;
    @NotNull(message = "error, campo maximoInicioContrato obligatorio")
    @Column(name = "maximo_inicio_contrato")
    private Integer maximoInicioContrato;
    @OneToMany(mappedBy = "configuraciones")
	private List<Correo> correoList;
    
    public Configuraciones() {
    	super();
    }

	public Integer getIdConfiguraciones() {
		return idConfiguraciones;
	}

	public void setIdConfiguraciones(Integer idConfiguraciones) {
		this.idConfiguraciones = idConfiguraciones;
	}

	public Integer getMaximoDuracionContrato() {
		return maximoDuracionContrato;
	}

	public void setMaximoDuracionContrato(Integer maximoDuracionContrato) {
		this.maximoDuracionContrato = maximoDuracionContrato;
	}

	public Integer getMaximoInicioContrato() {
		return maximoInicioContrato;
	}

	public void setMaximoInicioContrato(Integer maximoInicioContrato) {
		this.maximoInicioContrato = maximoInicioContrato;
	}

	public List<Correo> getCorreoList() {
		return correoList;
	}

	public void setCorreoList(List<Correo> correoList) {
		this.correoList = correoList;
	}
	
}
