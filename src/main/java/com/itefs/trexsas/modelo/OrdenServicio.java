/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author JVO
 */
@Entity
@Table(name = "orden_servicio")
public class OrdenServicio{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_orden_servicio")
    private Long idOrdenServicio;
    @Column(name = "estado_orden_servicio")
    private Integer estadoOrdenServicio;
    @Column(name = "observaciones_orden_servicio")
    private String observacionesOrdenServicio;
    @NotNull(message = "error, campo valorConductorOrdenServicio obligatorio")
    @Column(name = "valor_conductor_orden_servicio")
    private Long valorConductorOrdenServicio;
    @NotNull(message = "error, campo valorFacturarOrdenServicio obligatorio")
    @Column(name = "valor_facturar_orden_servicio")
    private Long valorFacturarOrdenServicio;
    @Column(name = "orden_servicio")
    private String ordenServicio;
    @ManyToOne
    @JoinColumn(name = "reserva_id_reserva", referencedColumnName = "id_reserva")
    private Servicio reserva;
    @ManyToOne
    @JoinColumn(name = "vehiculo_id_vehiculo", referencedColumnName = "id_vehiculo")
    private Vehiculo vehiculo;
    @ManyToOne
    @JoinColumn(name = "conductor_id_conductor", referencedColumnName = "id_conductor")
    private Conductor conductor;

    public OrdenServicio() {
    	super();
    }

	public Long getIdOrdenServicio() {
		return idOrdenServicio;
	}

	public void setIdOrdenServicio(Long idOrdenServicio) {
		this.idOrdenServicio = idOrdenServicio;
	}

	public Integer getEstadoOrdenServicio() {
		return estadoOrdenServicio;
	}

	public void setEstadoOrdenServicio(Integer estadoOrdenServicio) {
		this.estadoOrdenServicio = estadoOrdenServicio;
	}

	public String getObservacionesOrdenServicio() {
		return observacionesOrdenServicio;
	}

	public void setObservacionesOrdenServicio(String observacionesOrdenServicio) {
		this.observacionesOrdenServicio = observacionesOrdenServicio;
	}

	public Long getValorConductorOrdenServicio() {
		return valorConductorOrdenServicio;
	}

	public void setValorConductorOrdenServicio(Long valorConductorOrdenServicio) {
		this.valorConductorOrdenServicio = valorConductorOrdenServicio;
	}

	public Long getValorFacturarOrdenServicio() {
		return valorFacturarOrdenServicio;
	}

	public void setValorFacturarOrdenServicio(Long valorFacturarOrdenServicio) {
		this.valorFacturarOrdenServicio = valorFacturarOrdenServicio;
	}

	public String getOrdenServicio() {
		return ordenServicio;
	}

	public void setOrdenServicio(String ordenServicio) {
		this.ordenServicio = ordenServicio;
	}

	public Servicio getReserva() {
		return reserva;
	}

	public void setReserva(Servicio reserva) {
		this.reserva = reserva;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public Conductor getConductor() {
		return conductor;
	}

	public void setConductor(Conductor conductor) {
		this.conductor = conductor;
	}
    
}
