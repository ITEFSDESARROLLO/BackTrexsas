package com.itefs.trexsas.modelo;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "cuenta")
public class Cuenta {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cuenta")
    private Integer idCuenta;
	@NotNull(message = "error, campo usuarioCuenta obligatorio")
    @Size(max = 40, message = "error, campo usuarioCuenta maximo de 40 digitos")
    @Column(name = "usuario_cuenta")
    private String usuarioCuenta;
	@NotNull(message = "error, campo estadoCuenta obligatorio")
    @Column(name = "estado_cuenta")
    private Integer estadoCuenta;
    @NotNull(message = "error, campo registradoPorCuenta obligatorio")
    @ManyToOne
    @JoinColumn(name = "registrado_por_cuenta", referencedColumnName = "id_persona",nullable = false)
    private Persona registradoPorCuenta;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "actualizado_por_cuenta", referencedColumnName = "id_persona", nullable = true)
    private Persona actualizadoPorCuenta;
    @Column(name = "fecha_registro_cuenta", columnDefinition = "TIMESTAMP")
    private String fechaRegistroCuenta;
    @Column(name = "fecha_actualizacion_cuenta", columnDefinition = "TIMESTAMP")
    private String fechaActualizacionCuenta;
    @NotNull(message = "error, campo claveCuenta obligatorio")
    @Size(max = 64, message = "error, campo claveCuenta maximo de 64 digitos")
    @Column(name = "clave_cuenta")
    private String claveCuenta;
    @Override
	public String toString() {
		return "Cuenta [idCuenta=" + idCuenta + ", usuarioCuenta=" + usuarioCuenta + ", estadoCuenta=" + estadoCuenta
				+ ", registradoPorCuenta=" + registradoPorCuenta + ", actualizadoPorCuenta=" + actualizadoPorCuenta
				+ ", fechaRegistroCuenta=" + fechaRegistroCuenta + ", fechaActualizacionCuenta="
				+ fechaActualizacionCuenta + ", claveCuenta=" + claveCuenta + ", cliente=" + cliente + ", persona="
				+ persona + ", ultimoIngresoCuentaList=" + ultimoIngresoCuentaList + ", tokenList=" + tokenList
				+ ", perfilList=" + perfilList + "]";
	}



	@OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cliente_id_cliente", updatable = false)
    private Cliente cliente;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "persona_id_persona", updatable = false)
    private Persona persona;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuenta")
    private List<UltimoIngresoCuenta> ultimoIngresoCuentaList;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cuenta")
    private List<Token> tokenList;
    @JsonIgnore
    @JoinTable(name = "cuenta_perfil", joinColumns = 
            @JoinColumn(name = "cuenta_id_cuenta", referencedColumnName = "id_cuenta"), inverseJoinColumns = 
            @JoinColumn(name = "perfil_id_perfil", referencedColumnName = "id_perfil"))
    @ManyToMany
    private List<Perfil> perfilList;

    public Cuenta() {
    	super();
    }
    
  //metodo que asocia una cuenta a perfil
    public void addPerfil(Perfil perfil){
        if(this.perfilList == null){
            this.perfilList = new ArrayList<>();
        }
        
        this.perfilList.add(perfil);
    }
    
    //metodo que desasocia una cuenta a perfil
    public void removePerfil(Perfil perfil){
        if(this.perfilList == null){
            this.perfilList = new ArrayList<>();
        }
        
        this.perfilList.remove(perfil);
    }

	public Integer getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(Integer idCuenta) {
		this.idCuenta = idCuenta;
	}

	public String getUsuarioCuenta() {
		return usuarioCuenta;
	}

	public void setUsuarioCuenta(String usuarioCuenta) {
		this.usuarioCuenta = usuarioCuenta;
	}

	public Integer getEstadoCuenta() {
		return estadoCuenta;
	}

	public void setEstadoCuenta(Integer estadoCuenta) {
		this.estadoCuenta = estadoCuenta;
	}

	public Persona getRegistradoPorCuenta() {
		return registradoPorCuenta;
	}

	public void setRegistradoPorCuenta(Persona registradoPorCuenta) {
		this.registradoPorCuenta = registradoPorCuenta;
	}

	public Persona getActualizadoPorCuenta() {
		return actualizadoPorCuenta;
	}

	public void setActualizadoPorCuenta(Persona actualizadoPorCuenta) {
		this.actualizadoPorCuenta = actualizadoPorCuenta;
	}

	public String getFechaRegistroCuenta() {
		return fechaRegistroCuenta;
	}

	public void setFechaRegistroCuenta(String fechaRegistroCuenta) {
		this.fechaRegistroCuenta = fechaRegistroCuenta;
	}

	public String getFechaActualizacionCuenta() {
		return fechaActualizacionCuenta;
	}

	public void setFechaActualizacionCuenta(String fechaActualizacionCuenta) {
		this.fechaActualizacionCuenta = fechaActualizacionCuenta;
	}

	public String getClaveCuenta() {
		return claveCuenta;
	}

	public void setClaveCuenta(String claveCuenta) {
		this.claveCuenta = claveCuenta;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public List<UltimoIngresoCuenta> getUltimoIngresoCuentaList() {
		return ultimoIngresoCuentaList;
	}

	public void setUltimoIngresoCuentaList(List<UltimoIngresoCuenta> ultimoIngresoCuentaList) {
		this.ultimoIngresoCuentaList = ultimoIngresoCuentaList;
	}

	public List<Token> getTokenList() {
		return tokenList;
	}

	public void setTokenList(List<Token> tokenList) {
		this.tokenList = tokenList;
	}

	public List<Perfil> getPerfilList() {
		return perfilList;
	}

	public void setPerfilList(List<Perfil> perfilList) {
		this.perfilList = perfilList;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	
    
	
	
    
	
}
