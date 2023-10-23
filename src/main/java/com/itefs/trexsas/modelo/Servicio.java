/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itefs.trexsas.modelo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author JVO
 */
@Entity
@Table(name = "reserva")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_reserva")
    private Long idReserva;
    @Column(name = "fecha_solicitud_reserva")
    private String fechaSolicitudReserva;
    @NotNull(message = "error, campo fechaInicioReserva obligatorio")
    @Column(name = "fecha_inicio_reserva")
    private String fechaInicioReserva;
    @NotNull(message ="error, campo horaInicioReserva obligatorio")
    @Column(name="hora_inicio_reserva")
    private String horaInicioReserva;
    @Size(max = 15, message = "error, campo numeroVoucher maximo de 15 digitos")
    @Column(name = "numero_voucher")
    private String numeroVoucher;
    @NotNull(message = "error, campo longitudOrigenReserva obligatorio")
    @Column(name = "longitud_origen_reserva")
    private Double longitudOrigenReserva;
    @NotNull(message = "error, campo latitudOrigenReserva obligatorio")
    @Column(name = "latitud_origen_reserva")
    private Double latitudOrigenReserva;
    @NotNull(message = "error, campo longitudDestinoReserva obligatorio")
    @Column(name = "longitud_destino_reserva")
    private Double longitudDestinoReserva;
    @NotNull(message = "error, campo latitudDestinoReserva obligatorio")
    @Column(name = "latitud_destino_reserva")
    private Double latitudDestinoReserva;
    @NotNull(message = "error, campo direccionOrigenReserva obligatorio")
    @Size(max = 100, message = "error, campo direccionOrigenReserva maximo de 100 digitos")
    @Column(name = "direccion_origen_reserva")
    private String direccionOrigenReserva;
    @NotNull(message = "error, campo direccionDestinoReserva obligatorio")
    @Size(max = 100, message = "error, campo direccionDestinoReserva maximo de 100 digitos")
    @Column(name = "direccion_destino_reserva")
    private String direccionDestinoReserva;
    @Column(name = "duracion_reserva")
    private String duracionReserva;
    @Column(name = "distancia_reserva")
    private Float distanciaReserva;
    @Column(name = "observaciones_reserva")
    private String observacionesReserva;
    @Column(name = "estado_reserva_id_estado_reserva")
    private Integer estadoReserva;
    @ManyToOne
    @JoinColumn(name = "cliente_id_cliente", referencedColumnName = "id_cliente")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "pasajero_id_pasajero", referencedColumnName = "id_pasajero")
    private Pasajero pasajero;
    @JsonIgnore
    @OneToOne(mappedBy = "reserva", fetch = FetchType.LAZY)
    private OrdenServicio ordenServicio;
    
    @Column(name="fecha_modificacion")
    private String fechaModificado;
    
    @OneToOne
    @JoinColumn(name = "modificado", referencedColumnName = "id_persona")
    private Persona modificado;
    
    @Column(name="accion")
    private String accion;

    public Servicio() {
    	super();
    }

	public Long getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(Long idReserva) {
		this.idReserva = idReserva;
	}

	public String getFechaSolicitudReserva() {
		return fechaSolicitudReserva;
	}

	public void setFechaSolicitudReserva(String fechaSolicitudReserva) {
		this.fechaSolicitudReserva = fechaSolicitudReserva;
	}

	public String getFechaInicioReserva() {
		return fechaInicioReserva;
	}

	public void setFechaInicioReserva(String fechaInicioReserva) {
		this.fechaInicioReserva = fechaInicioReserva;
	}

	public String getHoraInicioReserva() {
		return horaInicioReserva;
	}

	public void setHoraInicioReserva(String horaInicioReserva) {
		this.horaInicioReserva = horaInicioReserva;
	}

	public String getNumeroVoucher() {
		return numeroVoucher;
	}

	public void setNumeroVoucher(String numeroVoucher) {
		this.numeroVoucher = numeroVoucher;
	}

	public Double getLongitudOrigenReserva() {
		return longitudOrigenReserva;
	}

	public void setLongitudOrigenReserva(Double longitudOrigenReserva) {
		this.longitudOrigenReserva = longitudOrigenReserva;
	}

	public Double getLatitudOrigenReserva() {
		return latitudOrigenReserva;
	}

	public void setLatitudOrigenReserva(Double latitudOrigenReserva) {
		this.latitudOrigenReserva = latitudOrigenReserva;
	}

	public Double getLongitudDestinoReserva() {
		return longitudDestinoReserva;
	}

	public void setLongitudDestinoReserva(Double longitudDestinoReserva) {
		this.longitudDestinoReserva = longitudDestinoReserva;
	}

	public Double getLatitudDestinoReserva() {
		return latitudDestinoReserva;
	}

	public void setLatitudDestinoReserva(Double latitudDestinoReserva) {
		this.latitudDestinoReserva = latitudDestinoReserva;
	}

	public String getDireccionOrigenReserva() {
		return direccionOrigenReserva;
	}

	public void setDireccionOrigenReserva(String direccionOrigenReserva) {
		this.direccionOrigenReserva = direccionOrigenReserva;
	}

	public String getDireccionDestinoReserva() {
		return direccionDestinoReserva;
	}

	public void setDireccionDestinoReserva(String direccionDestinoReserva) {
		this.direccionDestinoReserva = direccionDestinoReserva;
	}

	public String getDuracionReserva() {
		return duracionReserva;
	}

	public void setDuracionReserva(String duracionReserva) {
		this.duracionReserva = duracionReserva;
	}

	public Float getDistanciaReserva() {
		return distanciaReserva;
	}

	public void setDistanciaReserva(Float distanciaReserva) {
		this.distanciaReserva = distanciaReserva;
	}

	public Integer getEstadoReserva() {
		return estadoReserva;
	}

	public void setEstadoReserva(Integer estadoReserva) {
		this.estadoReserva = estadoReserva;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Pasajero getPasajero() {
		return pasajero;
	}

	public void setPasajero(Pasajero pasajero) {
		this.pasajero = pasajero;
	}

	public OrdenServicio getOrdenServicio() {
		return ordenServicio;
	}

	public void setOrdenServicio(OrdenServicio ordenServicio) {
		this.ordenServicio = ordenServicio;
	}

	public String getObservacionesReserva() {
		return observacionesReserva;
	}

	public void setObservacionesReserva(String observacionesReserva) {
		this.observacionesReserva = observacionesReserva;
	}
	
	

	public String getFechaModificado() {
		return fechaModificado;
	}

	public void setFechaModificado(String fechaModificado) {
		this.fechaModificado = fechaModificado;
	}

	public Persona getModificado() {
		return modificado;
	}

	public void setModificado(Persona modificado) {
		this.modificado = modificado;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	@Override
	public String toString() {
		return "Reserva [idReserva=" + idReserva + ", fechaSolicitudReserva=" + fechaSolicitudReserva
				+ ", fechaInicioReserva=" + fechaInicioReserva + ", horaInicioReserva=" + horaInicioReserva
				+ ", numeroVoucher=" + numeroVoucher + ", longitudOrigenReserva=" + longitudOrigenReserva
				+ ", latitudOrigenReserva=" + latitudOrigenReserva + ", longitudDestinoReserva="
				+ longitudDestinoReserva + ", latitudDestinoReserva=" + latitudDestinoReserva
				+ ", direccionOrigenReserva=" + direccionOrigenReserva + ", direccionDestinoReserva="
				+ direccionDestinoReserva + ", duracionReserva=" + duracionReserva + ", distanciaReserva="
				+ distanciaReserva + ", observacionesReserva=" + observacionesReserva + ", estadoReserva="
				+ estadoReserva + ", cliente=" + cliente + ", pasajero=" + pasajero + ", ordenServicio=" + ordenServicio
				+ "]";
	}
	
	
	
    
}
