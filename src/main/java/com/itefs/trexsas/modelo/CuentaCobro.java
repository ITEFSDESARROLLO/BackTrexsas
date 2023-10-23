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
import javax.validation.constraints.Size;

@Entity
@Table(name = "cuenta_cobro")
public class CuentaCobro {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cuenta_cobro")
    private Long idCuentaCobro;
	@NotNull(message = "error, campo nombreCuentaCobro obligatorio")
    @Size(max = 200, message = "error, campo nombreCuentaCobro maximo de 200 digitos")
    @Column(name = "nombre_cuenta_cobro")
    private String nombreCuentaCobro;
    @Column(name = "fecha_cuenta_cobro")
    private String fechaCuentaCobro;
    @ManyToOne
    @JoinColumn(name = "registrado_por_cuenta_cobro", referencedColumnName = "id_persona")
    private PersonaAutor registradoPorCuentaCobro;
	@NotNull(message = "error, campo numeroCuentaCobro obligatorio")
    @Column(name = "numero_cuenta_cobro")
    private Integer numeroCuentaCobro;
    @Size(max = 45, message = "error, campo nitCuentaCobro maximo de 45 digitos")
	@NotNull(message = "error, campo nitCuentaCobro obligatorio")
    @Column(name = "nit_cuenta_cobro")
    private String nitCuentaCobro;
    @NotNull(message = "error, campo valorCuentaCobro obligatorio")
    @Column(name = "valor_cuenta_cobro")
    private Long valorCuentaCobro;
    @NotNull(message = "error, campo conceptoCuentaCobro obligatorio")
    @Column(name = "concepto_cuenta_cobro")
    private String conceptoCuentaCobro;
    @Column(name = "cuenta_cobro")
    private String cuentaCobro;
    @Column(name="vigente")
    private boolean estadoCuentaCobro;

    public CuentaCobro() {
    	super();
    }

	public Long getIdCuentaCobro() {
		return idCuentaCobro;
	}

	public void setIdCuentaCobro(Long idCuentaCobro) {
		this.idCuentaCobro = idCuentaCobro;
	}

	public String getNombreCuentaCobro() {
		return nombreCuentaCobro;
	}

	public void setNombreCuentaCobro(String nombreCuentaCobro) {
		this.nombreCuentaCobro = nombreCuentaCobro;
	}

	public String getFechaCuentaCobro() {
		return fechaCuentaCobro;
	}

	public void setFechaCuentaCobro(String fechaCuentaCobro) {
		this.fechaCuentaCobro = fechaCuentaCobro;
	}

	public PersonaAutor getRegistradoPorCuentaCobro() {
		return registradoPorCuentaCobro;
	}

	public void setRegistradoPorCuentaCobro(PersonaAutor registradoPorCuentaCobro) {
		this.registradoPorCuentaCobro = registradoPorCuentaCobro;
	}

	public Integer getNumeroCuentaCobro() {
		return numeroCuentaCobro;
	}

	public void setNumeroCuentaCobro(Integer numeroCuentaCobro) {
		this.numeroCuentaCobro = numeroCuentaCobro;
	}

	public String getNitCuentaCobro() {
		return nitCuentaCobro;
	}

	public void setNitCuentaCobro(String nitCuentaCobro) {
		this.nitCuentaCobro = nitCuentaCobro;
	}

	public Long getValorCuentaCobro() {
		return valorCuentaCobro;
	}

	public void setValorCuentaCobro(Long valorCuentaCobro) {
		this.valorCuentaCobro = valorCuentaCobro;
	}

	public String getConceptoCuentaCobro() {
		return conceptoCuentaCobro;
	}

	public void setConceptoCuentaCobro(String conceptoCuentaCobro) {
		this.conceptoCuentaCobro = conceptoCuentaCobro;
	}

	public String getCuentaCobro() {
		return cuentaCobro;
	}

	public void setCuentaCobro(String cuentaCobro) {
		this.cuentaCobro = cuentaCobro;
	}

	public boolean isEstadoCuentaCobro() {
		return estadoCuentaCobro;
	}

	public void setEstadoCuentaCobro(boolean estadoCuentaCobro) {
		this.estadoCuentaCobro = estadoCuentaCobro;
	}
	
}
