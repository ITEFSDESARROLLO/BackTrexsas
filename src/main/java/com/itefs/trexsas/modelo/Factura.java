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
@Table(name = "factura")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_factura")
    private Long idFactura;
    @NotNull(message = "error, campo fechaFinFactura obligatorio")
    @Column(name = "fecha_fin_factura")
    private String fechaFinFactura;
    @NotNull(message = "error, campo fechaInicioFactura obligatorio")
    @Column(name = "fecha_inicio_factura")
    private String fechaInicioFactura;
    @NotNull(message = "error, campo fechaFactura obligatorio")
    @Column(name = "fecha_factura")
    private String fechaFactura;
    @NotNull(message = "error, campo totalFactura obligatorio")
    @Column(name = "total_factura")
    private Long totalFactura;
    @Column(name = "factura")
    private String factura;
    @NotNull(message = "error, campo conceptoFactura obligatorio")
    @Column(name = "concepto_factura")
    private String conceptoFactura;
    @Column(name = "estado_factura")
    private Integer estadoFactura;
    @ManyToOne
    @JoinColumn(name = "cliente_id_cliente", referencedColumnName = "id_cliente")
    private Cliente cliente;

    public Factura() {
    	super();
    }

	public Long getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(Long idFactura) {
		this.idFactura = idFactura;
	}

	public String getFechaFinFactura() {
		return fechaFinFactura;
	}

	public void setFechaFinFactura(String fechaFinFactura) {
		this.fechaFinFactura = fechaFinFactura;
	}

	public String getFechaInicioFactura() {
		return fechaInicioFactura;
	}

	public void setFechaInicioFactura(String fechaInicioFactura) {
		this.fechaInicioFactura = fechaInicioFactura;
	}

	public String getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(String fechaFactura) {
		this.fechaFactura = fechaFactura;
	}

	public Long getTotalFactura() {
		return totalFactura;
	}

	public void setTotalFactura(Long totalFactura) {
		this.totalFactura = totalFactura;
	}

	public String getConceptoFactura() {
		return conceptoFactura;
	}

	public void setConceptoFactura(String conceptoFactura) {
		this.conceptoFactura = conceptoFactura;
	}

	public Integer getEstadoFactura() {
		return estadoFactura;
	}

	public void setEstadoFactura(Integer estadoFactura) {
		this.estadoFactura = estadoFactura;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getFactura() {
		return factura;
	}

	public void setFactura(String factura) {
		this.factura = factura;
	}

	
	
    
}
