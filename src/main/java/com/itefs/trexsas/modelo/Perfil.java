package com.itefs.trexsas.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "perfil")
public class Perfil {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_perfil")
    private Integer idPerfil;
	@NotNull(message = "error, campo nombrePerfil obligatorio")
	@Size(max = 45, message = "error, campo nombrePerfil maximo de 45 digitos")
    @Column(name = "nombre_perfil")
    private String nombrePerfil;
    @Size(max = 1000, message = "error, campo observacionesPerfil maximo de 1000 digitos")
    @Column(name = "observaciones_perfil")
    private String observacionesPerfil;
    @NotNull(message = "error, campo estadoPerfil obligatorio")
    @Column(name = "estado_perfil")
    private Integer estadoPerfil;
    @Column(name = "fecha_registro_perfil", columnDefinition = "TIMESTAMP")
    private String fechaRegistroPerfil;
    @NotNull(message = "error, campo registradoPorPerfil obligatorio")
    @ManyToOne
    @JoinColumn(name = "registrado_por_perfil", referencedColumnName = "id_persona")
    private PersonaAutor registradoPorPerfil;
    @Column(name = "fecha_actualizacion_perfil", columnDefinition = "TIMESTAMP")
    private String fechaActualizacionPerfil;
    @ManyToOne
    @JoinColumn(name = "actualizado_por_perfil", referencedColumnName = "id_persona")
    private PersonaAutor actualizadoPorPerfil;
    @JsonIgnore
    @JoinTable(name = "cuenta_perfil", joinColumns = {
        @JoinColumn(name = "perfil_id_perfil", referencedColumnName = "id_perfil")}, inverseJoinColumns = {
        @JoinColumn(name = "cuenta_id_cuenta", referencedColumnName = "id_cuenta")})
    @ManyToMany
    private List<Cuenta> cuentaList;
    @OneToMany(mappedBy = "perfil")
    private List<UriXPerfil> uriXPerfilList;

    public Perfil() {
    	super();
    }
    
  //metodo que asocia una cuenta a perfil
    public void addCuenta(Cuenta cuenta){
        if(this.cuentaList == null){
            this.cuentaList = new ArrayList<>();
        }
        
        this.cuentaList.add(cuenta);
    }
    
    //metodo que desasocia una cuenta a perfil
    public void removeCuenta(Cuenta cuenta){
        if(this.cuentaList == null){
            this.cuentaList = new ArrayList<>();
        }
        
        this.cuentaList.remove(cuenta);
    }

	public Integer getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Integer idPerfil) {
		this.idPerfil = idPerfil;
	}

	public String getNombrePerfil() {
		return nombrePerfil;
	}

	public void setNombrePerfil(String nombrePerfil) {
		this.nombrePerfil = nombrePerfil;
	}

	public String getObservacionesPerfil() {
		return observacionesPerfil;
	}

	public void setObservacionesPerfil(String observacionesPerfil) {
		this.observacionesPerfil = observacionesPerfil;
	}

	public String getFechaRegistroPerfil() {
		return fechaRegistroPerfil;
	}

	public void setFechaRegistroPerfil(String fechaRegistroPerfil) {
		this.fechaRegistroPerfil = fechaRegistroPerfil;
	}

	public PersonaAutor getRegistradoPorPerfil() {
		return registradoPorPerfil;
	}

	public void setRegistradoPorPerfil(PersonaAutor registradoPorPerfil) {
		this.registradoPorPerfil = registradoPorPerfil;
	}

	public String getFechaActualizacionPerfil() {
		return fechaActualizacionPerfil;
	}

	public void setFechaActualizacionPerfil(String fechaActualizacionPerfil) {
		this.fechaActualizacionPerfil = fechaActualizacionPerfil;
	}

	public PersonaAutor getActualizadoPorPerfil() {
		return actualizadoPorPerfil;
	}

	public void setActualizadoPorPerfil(PersonaAutor actualizadoPorPerfil) {
		this.actualizadoPorPerfil = actualizadoPorPerfil;
	}

	public List<Cuenta> getCuentaList() {
		return cuentaList;
	}

	public void setCuentaList(List<Cuenta> cuentaList) {
		this.cuentaList = cuentaList;
	}

	public List<UriXPerfil> getUriXPerfilList() {
		return uriXPerfilList;
	}

	public void setUriXPerfilList(List<UriXPerfil> uriXPerfilList) {
		this.uriXPerfilList = uriXPerfilList;
	}

	public Integer getEstadoPerfil() {
		return estadoPerfil;
	}

	public void setEstadoPerfil(Integer estadoPerfil) {
		this.estadoPerfil = estadoPerfil;
	}

}
