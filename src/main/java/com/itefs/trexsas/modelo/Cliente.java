package com.itefs.trexsas.modelo;


import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "cliente")
public class Cliente {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cliente")
    private Integer idCliente;
	@NotNull(message = "error, campo razonSocialCliente obligatorio")
    @Size(max = 100, message = "error, campo razonSocialCliente maximo de 100 digitos")
    @Column(name = "razon_social_cliente")
    private String razonSocialCliente;
	@NotNull(message = "error, campo nitCliente obligatorio")
    
    @Column(name = "nit_cliente")
    private String nitCliente;
	@NotNull(message = "error, campo direccionCliente obligatorio")
    @Size(max = 100, message = "error, campo direccionCliente maximo de 100 digitos")
    @Column(name = "direccion_cliente")
    private String direccionCliente;
	@NotNull(message = "error, campo finalizarServicioCliente obligatorio")
    @Column(name = "finalizar_servicio_cliente")
    private Short finalizarServicioCliente;
    @Column(name = "envio_programacion_cliente")
    private Short envioProgramacionCliente;
    @NotNull(message = "error, campo ciudadCliente obligatorio")
    
    @ManyToOne
    @JoinColumn(name = "ciudad_cliente", referencedColumnName = "id_ciudad")
    private Ciudad ciudadCliente;
    
    
    @Size(max = 200, message = "error, campo logoCliente maximo de 200 digitos")
    @Column(name = "logo_cliente")
    private String logoCliente;
    @NotNull(message = "error, campo correoCliente obligatorio")
    @Email(message = "error, el campo correoCliente no corresponde a una direccion email valida")
    @Size(max = 150, message = "error, campo correoCliente maximo de 150 digitos")
    @Column(name = "correo_cliente")
    private String correoCliente;
    @NotNull(message = "error, campo celularUnoCliente obligatorio")
    @Column(name = "celular_uno_cliente")
    private String celularUnoCliente;
    @Column(name = "celular_dos_cliente")
    private String celularDosCliente;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Pasajero> pasajeroList;
    
    public Cliente() {
    	super();
    }

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getRazonSocialCliente() {
		return razonSocialCliente;
	}

	public void setRazonSocialCliente(String razonSocialCliente) {
		this.razonSocialCliente = razonSocialCliente;
	}

	public String getNitCliente() {
		return nitCliente;
	}

	public void setNitCliente(String nitCliente) {
		this.nitCliente = nitCliente;
	}

	public String getDireccionCliente() {
		return direccionCliente;
	}

	public void setDireccionCliente(String direccionCliente) {
		this.direccionCliente = direccionCliente;
	}

	public Short getEnvioProgramacionCliente() {
		return envioProgramacionCliente;
	}

	public void setEnvioProgramacionCliente(Short envioProgramacionCliente) {
		this.envioProgramacionCliente = envioProgramacionCliente;
	}

	public Ciudad getCiudadCliente() {
		return ciudadCliente;
	}

	public void setCiudadCliente(Ciudad ciudadCliente) {
		this.ciudadCliente = ciudadCliente;
	}

	public String getLogoCliente() {
		return logoCliente;
	}

	public void setLogoCliente(String logoCliente) {
		this.logoCliente = logoCliente;
	}

	public String getCorreoCliente() {
		return correoCliente;
	}

	public void setCorreoCliente(String correoCliente) {
		this.correoCliente = correoCliente;
	}

	public String getCelularUnoCliente() {
		return celularUnoCliente;
	}

	public void setCelularUnoCliente(String celularUnoCliente) {
		this.celularUnoCliente = celularUnoCliente;
	}

	public String getCelularDosCliente() {
		return celularDosCliente;
	}

	public void setCelularDosCliente(String celularDosCliente) {
		this.celularDosCliente = celularDosCliente;
	}

	public List<Pasajero> getPasajeroList() {
		return pasajeroList;
	}

	public void setPasajeroList(List<Pasajero> pasajeroList) {
		this.pasajeroList = pasajeroList;
	}

	public Short getFinalizarServicioCliente() {
		return finalizarServicioCliente;
	}

	public void setFinalizarServicioCliente(Short finalizarServicioCliente) {
		this.finalizarServicioCliente = finalizarServicioCliente;
	}
    
	
	
}
