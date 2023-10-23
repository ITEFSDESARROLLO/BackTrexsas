package com.itefs.trexsas.modelo;


import java.sql.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "fuec")
public class Fuec {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_fuec")
    private Long idFuec;
    @Column(name = "codigo_fuec")
    private String codigoFuec;
    @NotNull(message = "error, campo fechaInicioFuec obligatorio")
    @Column(name = "fecha_inicio_fuec")
    private String fechaInicioFuec;
    @NotNull(message = "error, campo fechaFinFuec obligatorio")
    @Column(name = "fecha_fin_fuec")
    private String fechaFinFuec;
    @Column(name = "fuec")
    private String fuec;
    @Column(name = "estado_fuec")
    private Integer estadoFuec;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_contrato", referencedColumnName = "id_contrato")
	private Contrato contrato;

	@ManyToOne
    @JoinColumn(name = "vehiculo_id_vehiculo", referencedColumnName = "id_vehiculo")
	private Vehiculo vehiculo;
	
	@NotNull
	@Column(name="cupo_maximo")
	private Integer cupoMaximo;
	
	@NotNull
	@Column(name="cupo_disponible")
	private Integer cupoDisponible;
	
	
	@NotNull
	@OneToOne
	@JoinColumn(name="ciudad_origen", referencedColumnName = "id_ciudad")
	private Ciudad ciudadOrigen;
	
	@NotNull
	@OneToOne
	@JoinColumn(name="ciudad_destino", referencedColumnName = "id_ciudad")
	private Ciudad ciudadDestino;

	@JoinTable(name = "conductor_fuec", joinColumns = {
        @JoinColumn(name = "fuec_id_fuec", referencedColumnName = "id_fuec")}, 
    		inverseJoinColumns = {
        @JoinColumn(name = "conductor_id_conductor", referencedColumnName = "id_conductor")})
    @ManyToMany
    private List<Conductor> conductorList;
	@JoinTable(name = "pasajero_fuec", joinColumns = {
		@JoinColumn(name = "fuec_id_fuec", referencedColumnName = "id_fuec") }, 
			inverseJoinColumns = {
		@JoinColumn(name = "pasajero_id_pasajero", referencedColumnName = "id_pasajero") })
	@ManyToMany
	private List<Pasajero> pasajeroList;
    
    public Fuec() {
    	super();
    }
    
    //metodo que asocia un conductor a fuec
    public void addConductor(Conductor conductor){
        if(this.conductorList == null){
            this.conductorList = new ArrayList<>();
        }
        
        this.conductorList.add(conductor);
    }
    
    //metodo que desasocia una conductor a fuec
    public void removeConductor(Conductor conductor){
        /*if(this.conductorList == null){
            this.conductorList = new ArrayList<>();
        }*/
        
        this.conductorList.remove(conductor);
    }

	public Long getIdFuec() {
		return idFuec;
	}

	public void setIdFuec(Long idFuec) {
		this.idFuec = idFuec;
	}

	public String getCodigoFuec() {
		return codigoFuec;
	}

	public void setCodigoFuec(String codigoFuec) {
		this.codigoFuec = codigoFuec;
	}

	public String getFechaInicioFuec() {
		return fechaInicioFuec;
	}

	public void setFechaInicioFuec(String fechaInicioFuec) {
		this.fechaInicioFuec = fechaInicioFuec;
	}

	public String getFechaFinFuec() {
		return fechaFinFuec;
	}

	public void setFechaFinFuec(String fechaFinFuec) {
		this.fechaFinFuec = fechaFinFuec;
	}

	public String getFuec() {
		return fuec;
	}

	public void setFuec(String fuec) {
		this.fuec = fuec;
	}

	public Integer getEstadoFuec() {
		return estadoFuec;
	}

	public void setEstadoFuec(Integer estadoFuec) {
		this.estadoFuec = estadoFuec;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public List<Conductor> getConductorList() {
		return conductorList;
	}

	public void setConductorList(List<Conductor> conductorList) {
		this.conductorList = conductorList;
	}
	
	public void addPasajero(Pasajero pasajero){
        if(this.pasajeroList == null){
            this.pasajeroList = new ArrayList<>();
        }
        
        this.pasajeroList.add(pasajero);
    }

	public List<Pasajero> getPasajeroList() {
		return pasajeroList;
	}

	public void setPasajeroList(List<Pasajero> pasajeroList) {
		this.pasajeroList = pasajeroList;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public Ciudad getCiudadOrigen() {
		return ciudadOrigen;
	}

	public void setCiudadOrigen(Ciudad ciudadOrigen) {
		this.ciudadOrigen = ciudadOrigen;
	}

	public Ciudad getCiudadDestino() {
		return ciudadDestino;
	}

	public void setCiudaDestino(Ciudad ciudaDestino) {
		this.ciudadDestino = ciudaDestino;
	}

	public Integer getCupoMaximo() {
		return cupoMaximo;
	}

	public void setCupoMaximo(Integer cupoMaximo) {
		this.cupoMaximo = cupoMaximo;
	}

	public Integer getCupoDisponible() {
		return cupoDisponible;
	}

	public void setCupoDisponible(Integer cupoDisponible) {
		this.cupoDisponible = cupoDisponible;
	}

	public void setCiudadDestino(Ciudad ciudadDestino) {
		this.ciudadDestino = ciudadDestino;
	}

	@Override
	public String toString() {
		return "Fuec [idFuec=" + idFuec + ", codigoFuec=" + codigoFuec + ", fechaInicioFuec=" + fechaInicioFuec
				+ ", fechaFinFuec=" + fechaFinFuec + ", fuec=" + fuec + ", estadoFuec=" + estadoFuec + ", contrato="
				+ contrato + ", vehiculo=" + vehiculo + ", cupoMaximo=" + cupoMaximo + ", cupoDisponible="
				+ cupoDisponible + ", ciudadOrigen=" + ciudadOrigen + ", ciudadDestino=" + ciudadDestino
				+ ", conductorList=" + conductorList + ", pasajeroList=" + pasajeroList + "]";
	}

	
	
	
	
	
}
