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
@Table(name = "tarjeta_operacion")
@JsonIgnoreProperties(value={ "vehiculo" }, allowSetters= true)
public class TarjetaOperacion {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tarjeta_operacion")
    private Integer idTarjetaOperacion;
	@Size(max = 16, message = "error, campo numeroTarjetaOperacion maximo de 16 digitos")
	
	@Column(name = "numero_tarjeta_operacion")
    private String numeroTarjetaOperacion;
	
	@Column(name = "fecha_vencimiento_tarjeta_operacion")
    private String fechaVencimientoTarjetaOperacion;
	
	@Column(name = "fecha_expedicion_tarjeta_operacion")
    private String fechaExpedicionTarjetaOperacion;
	
	@Column(name = "uno_tarjeta_operacion")
    private String unoTarjetaOperacion;
	
    @JsonProperty("vehiculo")
    @JoinColumn(name = "vehiculo_id_vehiculo", referencedColumnName = "id_vehiculo")
    @OneToOne(optional = false)
    private Vehiculo vehiculo;

    public TarjetaOperacion() {
    	super();
    }

	public Integer getIdTarjetaOperacion() {
		return idTarjetaOperacion;
	}

	public void setIdTarjetaOperacion(Integer idTarjetaOperacion) {
		this.idTarjetaOperacion = idTarjetaOperacion;
	}

	public String getNumeroTarjetaOperacion() {
		return numeroTarjetaOperacion;
	}

	public void setNumeroTarjetaOperacion(String numeroTarjetaOperacion) {
		this.numeroTarjetaOperacion = numeroTarjetaOperacion;
	}

	public String getFechaVencimientoTarjetaOperacion() {
		return fechaVencimientoTarjetaOperacion;
	}

	public void setFechaVencimientoTarjetaOperacion(String fechaVencimientoTarjetaOperacion) {
		this.fechaVencimientoTarjetaOperacion = fechaVencimientoTarjetaOperacion;
	}

	public String getFechaExpedicionTarjetaOperacion() {
		return fechaExpedicionTarjetaOperacion;
	}

	public void setFechaExpedicionTarjetaOperacion(String fechaExpedicionTarjetaOperacion) {
		this.fechaExpedicionTarjetaOperacion = fechaExpedicionTarjetaOperacion;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public String getUnoTarjetaOperacion() {
		return unoTarjetaOperacion;
	}

	public void setUnoTarjetaOperacion(String unoTarjetaOperacion) {
		this.unoTarjetaOperacion = unoTarjetaOperacion;
	}
    
}
