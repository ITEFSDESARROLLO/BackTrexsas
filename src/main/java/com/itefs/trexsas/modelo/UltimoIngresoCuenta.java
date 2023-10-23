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
import javax.validation.constraints.Size;

@Entity
@Table(name = "ultimo_ingreso_cuenta")
public class UltimoIngresoCuenta {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ultimo_ingreso_cuenta")
    private Long idUltimoIngresoCuenta;
    @Column(name = "fecha_ultimo_ingreso_cuenta", columnDefinition = "TIMESTAMP")
    private String fechaUltimoIngresoCuenta;
    @Size(max = 20, message = "error, campo maximo de 20 digitos")
    @Column(name = "ip_ultimo_ingreso_cuenta")
    private String ipUltimoIngresoCuenta;
    @Size(max = 300, message = "error, campo maximo de 300 digitos")
    @Column(name = "navegador_ultimo_ingreso_cuenta")
    private String navegadorUltimoIngresoCuenta;
    @ManyToOne
    @JoinColumn(name = "cuenta_id_cuenta", nullable = false, updatable = false)
    private Cuenta cuenta;

    public UltimoIngresoCuenta() {
    	super();
    }

	public Long getIdUltimoIngresoCuenta() {
		return idUltimoIngresoCuenta;
	}

	public void setIdUltimoIngresoCuenta(Long idUltimoIngresoCuenta) {
		this.idUltimoIngresoCuenta = idUltimoIngresoCuenta;
	}

	public String getFechaUltimoIngresoCuenta() {
		return fechaUltimoIngresoCuenta;
	}

	public void setFechaUltimoIngresoCuenta(String fechaUltimoIngresoCuenta) {
		this.fechaUltimoIngresoCuenta = fechaUltimoIngresoCuenta;
	}

	public String getIpUltimoIngresoCuenta() {
		return ipUltimoIngresoCuenta;
	}

	public void setIpUltimoIngresoCuenta(String ipUltimoIngresoCuenta) {
		this.ipUltimoIngresoCuenta = ipUltimoIngresoCuenta;
	}

	public String getNavegadorUltimoIngresoCuenta() {
		return navegadorUltimoIngresoCuenta;
	}

	public void setNavegadorUltimoIngresoCuenta(String navegadorUltimoIngresoCuenta) {
		this.navegadorUltimoIngresoCuenta = navegadorUltimoIngresoCuenta;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}
}
