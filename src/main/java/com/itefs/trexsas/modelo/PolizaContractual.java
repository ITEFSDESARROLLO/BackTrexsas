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
@Table(name = "poliza_contractual")
@JsonIgnoreProperties(value={ "vehiculo" }, allowSetters= true)
public class PolizaContractual {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_poliza_contractual")
    private Integer idPolizaContractual;
	
	@Column(name = "fecha_inicio_poliza_contractual")
    private String fechaInicioPolizaContractual;
	
    @Column(name = "fecha_vencimiento_poliza_contractual")
    private String fechaVencimientoPolizaContractual;
	
    @Size(max = 16, message = "error, campo numeroPolizaContractual maximo de 16 digitos")
    @Column(name = "numero_poliza_contractual")
    private String numeroPolizaContractual;
    @Column(name = "poliza_contractual")
    private String polizaContractual;
    
    
    @JsonProperty("vehiculo")
    @JoinColumn(name = "vehiculo_id_vehiculo", referencedColumnName = "id_vehiculo")
    @OneToOne(optional = false)
    private Vehiculo vehiculo;
    
    @JoinColumn(name = "aseguradora_id_aseguradora", referencedColumnName = "id_aseguradora")
    @ManyToOne(optional = true)
    private Aseguradora aseguradora;
    
	public PolizaContractual() {
		super();
	}

	public Integer getIdPolizaContractual() {
		return idPolizaContractual;
	}

	public void setIdPolizaContractual(Integer idPolizaContractual) {
		this.idPolizaContractual = idPolizaContractual;
	}

	public String getFechaVencimientoPolizaContractual() {
		return fechaVencimientoPolizaContractual;
	}

	public void setFechaVencimientoPolizaContractual(String fechaVencimientoPolizaContractual) {
		this.fechaVencimientoPolizaContractual = fechaVencimientoPolizaContractual;
	}

	public String getNumeroPolizaContractual() {
		return numeroPolizaContractual;
	}

	public void setNumeroPolizaContractual(String numeroPolizaContractual) {
		this.numeroPolizaContractual = numeroPolizaContractual;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public Aseguradora getAseguradora() {
		return aseguradora;
	}

	public void setAseguradora(Aseguradora aseguradora) {
		this.aseguradora = aseguradora;
	}

	public String getFechaInicioPolizaContractual() {
		return fechaInicioPolizaContractual;
	}

	public void setFechaInicioPolizaContractual(String fechaInicioPolizaContractual) {
		this.fechaInicioPolizaContractual = fechaInicioPolizaContractual;
	}

	public String getPolizaContractual() {
		return polizaContractual;
	}

	public void setPolizaContractual(String polizaContractual) {
		this.polizaContractual = polizaContractual;
	}

	
	
	
    
}
