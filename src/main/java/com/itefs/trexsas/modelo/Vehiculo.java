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

import org.springframework.format.annotation.DateTimeFormat;import ch.qos.logback.core.subst.Token.Type;


@Entity
@Table(name = "vehiculo")
public class Vehiculo {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Basic(optional = false)
    @Column(name = "id_vehiculo")
    private Integer idVehiculo;
	
    @Size(max = 20, message = "error, campo codigoInternoVehiculo maximo de 20 digitos")
    @Column(name = "codigo_interno_vehiculo")
    private String codigoInternoVehiculo;
    @NotNull(message = "error, campo placaVehiculo obligatorio")
    @Size(max = 6, message = "error, campo placaVehiculo maximo de 10 digitos")
    @Column(name = "placa_vehiculo")
    private String placaVehiculo;
    @Column(name = "estado_vehiculo")
    private Integer estadoVehiculo;//para ver si se le dio de baja
    @Column(name = "disponibilidad")
    private Short disponibilidad;
    @Size(max = 80)
    @Column(name = "pignorado_vehiculo")
    private String pignoradoVehiculo;
    
    @Size(max = 4, message = "error, campo modelo maximo de 4 digitos")
    @Column(name = "modelo")
    private String modelo;
    
    @Column(name = "cilindraje")
    private Integer cilindraje;
    
    @Column(name = "numero_pasajeros_vehiculo")
    private Integer numeroPasajerosVehiculo;
    @Size(max = 30)
    @Column(name = "numero_motor_vehiculo")
    private String numeroMotorVehiculo;
    @Size(max = 30)
    @Column(name = "serie_motor_vehiculo")
    private String serieMotorVehiculo;
    @Size(max = 30)
    @Column(name = "chasis_vehiculo")
    private String chasisVehiculo;
    @Column(name="numero_vin")
    private String numeroVin;
    @DateTimeFormat(style="S-")
    @Column(name = "fecha_registro_vehiculo")
    private String fechaRegistroVehiculo;
    @DateTimeFormat(style="S-")
    @Column(name = "fecha_actualizacion_vehiculo")
    private String fechaActualizacionVehiculo;
    @Column(name = "color_vehiculo")
    private String colorVehiculo;
    @Column(name = "numero_serie")
    private String numeroSerie;
    @NotNull(message = "error, campo registradoPorVehiculo obligatorio")
    @OneToOne
    @JoinColumn(name = "registrado_por_vehiculo", referencedColumnName = "id_persona")
    private Persona registradoPorVehiculo;
    @OneToOne
    @JoinColumn(name = "actualizado_por_vehiculo", referencedColumnName = "id_persona", nullable = true)
    private Persona actualizadoPorVehiculo;
    @Size(max = 50)
    @Column(name = "linea_vehiculo")
    private String lineaVehiculo;
    @Size(max = 200)
    @Column(name = "tarjeta_propiedad_uno_vehiculo")
    private String tarjetaPropiedadUnoVehiculo;
    @Size(max = 200)
    @Column(name = "frente_vehiculo")
    private String frenteVehiculo;
    @Size(max = 200)
    @Column(name = "lado_vehiculo")
    private String ladoVehiculo;
    @Size(max = 200)
    @Column(name = "trasera_vehiculo")
    private String traseraVehiculo;
  
    @Column(name = "en_convenio_vehiculo")
    private Short enConvenioVehiculo;
   
    @ManyToOne
    @JoinColumn(name = "clase_id_clase")
    private Clase clase;
   
    @ManyToOne
    @JoinColumn(name = "marca_id_marca")
    private Marca marca;
    
    @ManyToOne
    @JoinColumn(name = "tipo_combustible_id_tipo_combustible")
    private TipoCombustible tipoCombustible;

    @ManyToOne
    @JoinColumn(name = "tipo_vehiculo_id_tipo_vehiculo")
    private TipoVehiculo tipoVehiculo;
    @ManyToOne
    @JoinColumn(name = "propietario_id_propietario")
    private Persona propietario;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "vehiculo")
    private DocumentoVehiculo documentoVehiculo;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "vehiculo")
    private RevisionTecnicomecanica revisionTecnicomecanicaList;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "vehiculo")
    private PolizaContractual polizaContractualList;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "vehiculo")
    private TarjetaOperacion tarjetaOperacionList;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "vehiculo")
    private RevisionPreventiva revisionPreventivaList;
    
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "vehiculo")
    private Soat soatList;
    
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "vehiculo")
    private PolizaExtracontractual polizaExtracontractualList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "vehiculo")
    private Convenio convenioList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "vehiculo")
    private PolizaTodoRiesgo polizaTodoRiesgoList;
    
    @JoinTable(name = "vehiculo_conductor", joinColumns = {
        @JoinColumn(name = "vehiculo_id_vehiculo", referencedColumnName = "id_vehiculo")}, 
    		inverseJoinColumns = {
        @JoinColumn(name = "conductor_id_conductor", referencedColumnName = "id_conductor")})
    @ManyToMany
    private List<Conductor> conductorList;
    
    @Column(name="fecha_modificacion")
    private String fechaModificado;
    
    @OneToOne
    @JoinColumn(name = "modificado", referencedColumnName = "id_persona")
    private Persona modificado;
    
    @NotNull
    @Column(name="accion")
    private String accion;
    
    @Column(name="carroceria_vehiculo")
    private String carroceriaVehiculo;
    @Column(name="entidad_transito")
    private String entidadTransito;
    @Column(name="tipo_servicio")
    private String tipoServicio;
    
    
    @Column(name="linea_marca")
    private String linea;
    
    @Column(name="numero_licencia_transito")
    private String numeroLicenciaTransito;
    
    @Column(name="fecha_matricula")
    private String fechaMatricula;
    
    
    public Vehiculo() {
    	super();
    }
    
  //metodo que asocia un conductor a vehiculo
    public void addConductor(Conductor conductor){
        if(this.conductorList == null){
            this.conductorList = new ArrayList<>();
        }
        
        this.conductorList.add(conductor);
    }
    
    //metodo que desasocia una conductor a vehiculo
    public void removeConductor(Conductor conductor){
        if(this.conductorList == null){
            this.conductorList = new ArrayList<>();
        }
        
        this.conductorList.remove(conductor);
    }

	public Integer getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(Integer idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public String getCodigoInternoVehiculo() {
		return codigoInternoVehiculo;
	}

	public void setCodigoInternoVehiculo(String codigoInternoVehiculo) {
		this.codigoInternoVehiculo = codigoInternoVehiculo;
	}

	public String getPlacaVehiculo() {
		return placaVehiculo;
	}

	public void setPlacaVehiculo(String placaVehiculo) {
		this.placaVehiculo = placaVehiculo;
	}

	public Integer getEstadoVehiculo() {
		return estadoVehiculo;
	}

	public void setEstadoVehiculo(Integer estadoVehiculo) {
		this.estadoVehiculo = estadoVehiculo;
	}

	public Short getDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(Short disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	public String getPignoradoVehiculo() {
		return pignoradoVehiculo;
	}

	public void setPignoradoVehiculo(String pignoradoVehiculo) {
		this.pignoradoVehiculo = pignoradoVehiculo;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Integer getCilindraje() {
		return cilindraje;
	}

	public void setCilindraje(Integer cilindraje) {
		this.cilindraje = cilindraje;
	}

	public Integer getNumeroPasajerosVehiculo() {
		return numeroPasajerosVehiculo;
	}

	public void setNumeroPasajerosVehiculo(Integer numeroPasajerosVehiculo) {
		this.numeroPasajerosVehiculo = numeroPasajerosVehiculo;
	}

	public String getNumeroMotorVehiculo() {
		return numeroMotorVehiculo;
	}

	public void setNumeroMotorVehiculo(String numeroMotorVehiculo) {
		this.numeroMotorVehiculo = numeroMotorVehiculo;
	}

	public String getSerieMotorVehiculo() {
		return serieMotorVehiculo;
	}

	public void setSerieMotorVehiculo(String serieMotorVehiculo) {
		this.serieMotorVehiculo = serieMotorVehiculo;
	}

	public String getChasisVehiculo() {
		return chasisVehiculo;
	}

	public void setChasisVehiculo(String chasisVehiculo) {
		this.chasisVehiculo = chasisVehiculo;
	}

	public String getFechaRegistroVehiculo() {
		return fechaRegistroVehiculo;
	}

	public void setFechaRegistroVehiculo(String fechaRegistroVehiculo) {
		this.fechaRegistroVehiculo = fechaRegistroVehiculo;
	}

	public Persona getRegistradoPorVehiculo() {
		return registradoPorVehiculo;
	}

	public void setRegistradoPorVehiculo(Persona registradoPorVehiculo) {
		this.registradoPorVehiculo = registradoPorVehiculo;
	}

	public String getLineaVehiculo() {
		return lineaVehiculo;
	}

	public void setLineaVehiculo(String lineaVehiculo) {
		this.lineaVehiculo = lineaVehiculo;
	}

	public Clase getClase() {
		return clase;
	}

	public void setClase(Clase clase) {
		this.clase = clase;
	}

	public String getColorVehiculo() {
		return colorVehiculo;
	}

	public void setColorVehiculo(String colorVehiculo) {
		this.colorVehiculo = colorVehiculo;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public TipoCombustible getTipoCombustible() {
		return tipoCombustible;
	}

	public void setTipoCombustible(TipoCombustible tipoCombustible) {
		this.tipoCombustible = tipoCombustible;
	}

	public TipoVehiculo getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public Persona getPropietario() {
		return propietario;
	}

	public void setPropietario(Persona propietario) {
		this.propietario = propietario;
	}

	public DocumentoVehiculo getDocumentoVehiculo() {
		return documentoVehiculo;
	}

	public void setDocumentoVehiculo(DocumentoVehiculo documentoVehiculo) {
		this.documentoVehiculo = documentoVehiculo;
	}

	public RevisionTecnicomecanica getRevisionTecnicomecanicaList() {
		return revisionTecnicomecanicaList;
	}

	public void setRevisionTecnicomecanicaList(RevisionTecnicomecanica revisionTecnicomecanicaList) {
		this.revisionTecnicomecanicaList = revisionTecnicomecanicaList;
	}

	public PolizaContractual getPolizaContractualList() {
		return polizaContractualList;
	}

	public void setPolizaContractualList(PolizaContractual polizaContractualList) {
		this.polizaContractualList = polizaContractualList;
	}

	public TarjetaOperacion getTarjetaOperacionList() {
		return tarjetaOperacionList;
	}

	public void setTarjetaOperacionList(TarjetaOperacion tarjetaOperacionList) {
		this.tarjetaOperacionList = tarjetaOperacionList;
	}

	public RevisionPreventiva getRevisionPreventivaList() {
		return revisionPreventivaList;
	}

	public void setRevisionPreventivaList(RevisionPreventiva revisionPreventivaList) {
		this.revisionPreventivaList = revisionPreventivaList;
	}

	public Soat getSoatList() {
		return soatList;
	}

	public void setSoatList(Soat soatList) {
		this.soatList = soatList;
	}

	public PolizaExtracontractual getPolizaExtracontractualList() {
		return polizaExtracontractualList;
	}

	public void setPolizaExtracontractualList(PolizaExtracontractual polizaExtracontractualList) {
		this.polizaExtracontractualList = polizaExtracontractualList;
	}

	public List<Conductor> getConductorList() {
		return conductorList;
	}

	public void setConductorList(List<Conductor> conductorList) {
		this.conductorList = conductorList;
	}

	public String getTarjetaPropiedadUnoVehiculo() {
		return tarjetaPropiedadUnoVehiculo;
	}

	public void setTarjetaPropiedadUnoVehiculo(String tarjetaPropiedadUnoVehiculo) {
		this.tarjetaPropiedadUnoVehiculo = tarjetaPropiedadUnoVehiculo;
	}

	public String getFrenteVehiculo() {
		return frenteVehiculo;
	}

	public void setFrenteVehiculo(String frenteVehiculo) {
		this.frenteVehiculo = frenteVehiculo;
	}

	public String getLadoVehiculo() {
		return ladoVehiculo;
	}

	public void setLadoVehiculo(String ladoVehiculo) {
		this.ladoVehiculo = ladoVehiculo;
	}

	public String getTraseraVehiculo() {
		return traseraVehiculo;
	}

	public void setTraseraVehiculo(String traseraVehiculo) {
		this.traseraVehiculo = traseraVehiculo;
	}

	public String getFechaActualizacionVehiculo() {
		return fechaActualizacionVehiculo;
	}

	public void setFechaActualizacionVehiculo(String fechaActualizacionVehiculo) {
		this.fechaActualizacionVehiculo = fechaActualizacionVehiculo;
	}

	public Persona getActualizadoPorVehiculo() {
		return actualizadoPorVehiculo;
	}

	public void setActualizadoPorVehiculo(Persona actualizadoPorVehiculo) {
		this.actualizadoPorVehiculo = actualizadoPorVehiculo;
	}

	public Short getEnConvenioVehiculo() {
		return enConvenioVehiculo;
	}
	public String getNumeroVin() {
		return numeroVin;
	}

	public void setNumeroVin(String numero_vin) {
		this.numeroVin = numero_vin;
	}

	

	public void setEnConvenioVehiculo(Short enConvenioVehiculo) {
		this.enConvenioVehiculo = enConvenioVehiculo;
	}

	public Convenio getConvenioList() {
		return convenioList;
	}

	public void setConvenioList(Convenio convenioList) {
		this.convenioList = convenioList;
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

	public String getCarroceriaVehiculo() {
		return carroceriaVehiculo;
	}

	public void setCarroceriaVehiculo(String carroceriaVehiculo) {
		this.carroceriaVehiculo = carroceriaVehiculo;
	}

	public String getEntidadTransito() {
		return entidadTransito;
	}

	public void setEntidadTransito(String entidadTransito) {
		this.entidadTransito = entidadTransito;
	}

	public String getTipoServicio() {
		return tipoServicio;
	}

	public void setTipoServicio(String tipoServicio) {
		this.tipoServicio = tipoServicio;
	}

	public String getLinea() {
		return linea;
	}

	public void setLinea(String linea) {
		this.linea = linea;
	}
	
	public String getNumeroLicenciaTransito() {
		return numeroLicenciaTransito;
	}

	public void setNumeroLicenciaTransito(String numeroLicenciaTransito) {
		this.numeroLicenciaTransito = numeroLicenciaTransito;
	}
	
	public String getFechaMatricula() {
		return fechaMatricula;
	}

	public void setFechaMatricula(String fechaMatricula) {
		this.fechaMatricula = fechaMatricula;
	}

	public String getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	public PolizaTodoRiesgo getPolizaTodoRiesgoList() {
		return polizaTodoRiesgoList;
	}

	public void setPolizaTodoRiesgoList(PolizaTodoRiesgo polizaTodoRiesgoList) {
		this.polizaTodoRiesgoList = polizaTodoRiesgoList;
	}
}
