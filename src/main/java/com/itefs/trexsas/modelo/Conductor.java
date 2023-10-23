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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "conductor")
@JsonIgnoreProperties(value={ "vehiculo" }, allowSetters= true)
public class Conductor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id_conductor")
	private Integer idConductor;
	@Column(name = "rh_conductor")
	private String rhConductor;
	@Column(name = "estado_conductor")
	private Integer estadoConductor;
	@Column(name = "planilla_aportes_conductor")
	private String planillaAportesConductor;
	@Column(name = "examenes_medicos_conductor")
	private String examenesMedicosConductor;
	@Column(name = "inicio_eps_conductor")
	private String inicioEpsConductor;
	@Column(name = "fin_eps_conductor")
	private String finEpsConductor;
	@Column(name = "inicio_arl_conductor")
	private String inicioArlConductor;
	@Column(name = "fin_arl_conductor")
	private String finArlConductor;
	@Column(name = "fecha_registro_conductor")
	private String fechaRegistroConductor;
	@Column(name = "fecha_actualizacion_conductor")
	private String fechaActualizacionConductor;
	@ManyToOne
    @JoinColumn(name = "registrado_por_conductor", referencedColumnName = "id_persona")
	private Persona registradoPorConductor;
	@ManyToOne
    @JoinColumn(name = "actualizado_por_conductor", referencedColumnName = "id_persona")
	private Persona actualizadoPorConductor;
	@Column(name = "genero_conductor")
	private String generoConductor;
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "conductor")
	private DocumentoConductor documentoConductor;
	@JoinColumn(name = "caja_compensacion_id_caja_compensacion")
	@OneToOne
	private CajaCompensacion cajaCompensacion;
	@JoinColumn(name = "fondo_pensiones_id_fondo_pensiones")
	@OneToOne
	private FondoPensiones fondoPensiones;
	@JoinColumn(name = "eps_id_eps")
	@OneToOne
	private Eps eps;
	@JoinColumn(name = "arl_id_arl", updatable = true, nullable = true)
	@OneToOne
	private Arl arl;
	@JoinColumn(name = "estado_civil_id_estado_civil")
	@OneToOne
	private EstadoCivil estadoCivil;
	@OneToOne
    @JoinColumn(name = "persona_id_persona", nullable = true, updatable = true)
	private Persona persona;
	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "conductor")
	private List<Licencia> licenciaList;
	@JsonProperty("vehiculo")
	@JoinTable(name = "vehiculo_conductor", 
			joinColumns = {
        @JoinColumn(name = "conductor_id_conductor", referencedColumnName = "id_conductor")}, 
	    	inverseJoinColumns = {
        @JoinColumn(name = "vehiculo_id_vehiculo", referencedColumnName = "id_vehiculo")})
	@ManyToMany
	private List<Vehiculo> vehiculoList;
	@JsonIgnore
	@JoinTable(name = "conductor_fuec", joinColumns = {
        @JoinColumn(name = "conductor_id_conductor", referencedColumnName = "id_conductor")}, 
    		inverseJoinColumns = {
        @JoinColumn(name = "fuec_id_fuec", referencedColumnName = "id_fuec")})
    @ManyToMany
    private List<Fuec> fuecList;
	
	public Conductor() {
		super();
	}
	
	//metodo que asocia un conductor a vehiculo
    public void addVehiculo(Vehiculo vehiculo){
        if(this.vehiculoList == null){
            this.vehiculoList = new ArrayList<>();
        }
        
        this.vehiculoList.add(vehiculo);
    }
    
    //metodo que desasocia una conductor a vehiculo
    public void removeVehiculo(Vehiculo vehiculo){
        if(this.vehiculoList == null){
            this.vehiculoList = new ArrayList<>();
        }
        
        this.vehiculoList.remove(vehiculo);
    }

	public Integer getIdConductor() {
		return idConductor;
	}

	public void setIdConductor(Integer idConductor) {
		this.idConductor = idConductor;
	}


	public Integer getEstadoConductor() {
		return estadoConductor;
	}

	public void setEstadoConductor(Integer estadoConductor) {
		this.estadoConductor = estadoConductor;
	}

	public String getPlanillaAportesConductor() {
		return planillaAportesConductor;
	}

	public void setPlanillaAportesConductor(String planillaAportesConductor) {
		this.planillaAportesConductor = planillaAportesConductor;
	}

	public String getExamenesMedicosConductor() {
		return examenesMedicosConductor;
	}

	public void setExamenesMedicosConductor(String examenesMedicosConductor) {
		this.examenesMedicosConductor = examenesMedicosConductor;
	}

	public String getInicioEpsConductor() {
		return inicioEpsConductor;
	}

	public void setInicioEpsConductor(String inicioEpsConductor) {
		this.inicioEpsConductor = inicioEpsConductor;
	}

	public String getFinEpsConductor() {
		return finEpsConductor;
	}

	public void setFinEpsConductor(String finEpsConductor) {
		this.finEpsConductor = finEpsConductor;
	}

	public String getInicioArlConductor() {
		return inicioArlConductor;
	}

	public void setInicioArlConductor(String inicioArlConductor) {
		this.inicioArlConductor = inicioArlConductor;
	}

	public String getFinArlConductor() {
		return finArlConductor;
	}

	public void setFinArlConductor(String finArlConductor) {
		this.finArlConductor = finArlConductor;
	}

	public String getFechaRegistroConductor() {
		return fechaRegistroConductor;
	}

	public void setFechaRegistroConductor(String fechaRegistroConductor) {
		this.fechaRegistroConductor = fechaRegistroConductor;
	}

	public String getFechaActualizacionConductor() {
		return fechaActualizacionConductor;
	}

	public void setFechaActualizacionConductor(String fechaActualizacionConductor) {
		this.fechaActualizacionConductor = fechaActualizacionConductor;
	}

	public Persona getRegistradoPorConductor() {
		return registradoPorConductor;
	}

	public void setRegistradoPorConductor(Persona registradoPorConductor) {
		this.registradoPorConductor = registradoPorConductor;
	}

	public Persona getActualizadoPorConductor() {
		return actualizadoPorConductor;
	}

	public void setActualizadoPorConductor(Persona actualizadoPorConductor) {
		this.actualizadoPorConductor = actualizadoPorConductor;
	}

	public DocumentoConductor getDocumentoConductor() {
		return documentoConductor;
	}

	public void setDocumentoConductor(DocumentoConductor documentoConductor) {
		this.documentoConductor = documentoConductor;
	}

	public CajaCompensacion getCajaCompensacion() {
		return cajaCompensacion;
	}

	public void setCajaCompensacion(CajaCompensacion cajaCompensacion) {
		this.cajaCompensacion = cajaCompensacion;
	}

	public FondoPensiones getFondoPensiones() {
		return fondoPensiones;
	}

	public void setFondoPensiones(FondoPensiones fondoPensiones) {
		this.fondoPensiones = fondoPensiones;
	}

	public Eps getEps() {
		return eps;
	}

	public void setEps(Eps eps) {
		this.eps = eps;
	}

	public Arl getArl() {
		return arl;
	}

	public void setArl(Arl arl) {
		this.arl = arl;
	}

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public List<Licencia> getLicenciaList() {
		return licenciaList;
	}

	public void setLicenciaList(List<Licencia> licenciaList) {
		this.licenciaList = licenciaList;
	}

	public List<Vehiculo> getVehiculoList() {
		return vehiculoList;
	}

	public void setVehiculoList(List<Vehiculo> vehiculoList) {
		this.vehiculoList = vehiculoList;
	}

	public String getRhConductor() {
		return rhConductor;
	}

	public void setRhConductor(String rhConductor) {
		this.rhConductor = rhConductor;
	}

	public String getGeneroConductor() {
		return generoConductor;
	}

	public void setGeneroConductor(String generoConductor) {
		this.generoConductor = generoConductor;
	}

	public List<Fuec> getFuecList() {
		return fuecList;
	}

	public void setFuecList(List<Fuec> fuecList) {
		this.fuecList = fuecList;
	}

	
	
	
}
