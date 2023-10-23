package com.itefs.trexsas.modelo;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "afiliacion")
public class Afiliacion {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_afiliacion")
    private Long idAfiliacion;
    @Size(max = 10, message = "error, campo codigoInternoAfiliacion maximo de 10 digitos")
    @Column(name = "codigo_interno_afiliacion")
    private String codigoInternoAfiliacion;
    @NotNull(message = "error, campo placaAfiliacion obligatorio")
    @Size(max = 6, message = "error, campo placaAfiliacion maximo de 6 digitos")
    @Column(name = "placa_afiliacion")
    private String placaAfiliacion;
    @NotNull(message = "error, campo pasajerosAfiliacion obligatorio")
    @Column(name = "pasajeros_afiliacion")
    private Integer pasajerosAfiliacion;
    @NotNull(message = "error, campo tipoCombustibleAfiliacion obligatorio")
    @Column(name = "tipo_combustible_afiliacion")
    private Integer tipoCombustibleAfiliacion;
    @NotNull(message = "error, campo tipoVehiculoAfiliacion obligatorio")
    @Column(name = "tipo_vehiculo_afiliacion")
    private Integer tipoVehiculoAfiliacion;
    @NotNull(message = "error, campo claseVehiculoAfiliacion obligatorio")
    @Column(name = "clase_vehiculo_afiliacion")
    private Integer claseVehiculoAfiliacion;
    @NotNull(message = "error, campo marcaAfiliacion obligatorio")
    @Column(name = "marca_afiliacion")
    private Integer marcaAfiliacion;
    @NotNull(message = "error, campo obligatorio")
    @Size(max = 100, message = "error, campo colorAfiliacion maximo de 100 digitos")
    @Column(name = "color_afiliacion")
    private String colorAfiliacion;
    @NotNull(message = "error, campo obligatorio")
    @Size(max = 4, message = "error, campo modeloAfiliacion maximo de 4 digitos")
    @Column(name = "modelo_afiliacion")
    private String modeloAfiliacion;
    @NotNull(message = "error, campo cilindrajeAfiliacion obligatorio")
    @Column(name = "cilindraje_afiliacion")
    private Integer cilindrajeAfiliacion;
    @Column(name = "empresa_convenio")
    private Integer empresaConvenio;
    @NotNull(message = "error, campo nombreAfiliacion obligatorio")
    @Size(max = 45, message = "error, campo nombreAfiliacion maximo de 45 digitos")
    @Column(name = "nombre_afiliacion")
    private String nombreAfiliacion;
    @NotNull(message = "error, campo apellidoAfiliacion obligatorio")
    @Size(max = 45, message = "error, campo apellidoAfiliacion maximo de 45 digitos")
    @Column(name = "apellido_afiliacion")
    private String apellidoAfiliacion;
    @NotNull(message = "error, campo tipoDocAfiliacion obligatorio")
    @Size(max = 20, message = "error, campo tipoDocAfiliacion maximo de 20 digitos")
    @Column(name = "tipo_doc_afiliacion")
    private String tipoDocAfiliacion;
    @NotNull(message = "error, campo documentoAfiliacion obligatorio")
    @Size(max = 20, message = "error, campo documentoAfiliacion maximo de 20 digitos")
    @Column(name = "documento_afiliacion")
    private String documentoAfiliacion;
    @NotNull(message = "error, campo obligatorio")
    @Email(message = "error, el campo correoAfiliacion no corresponde a una direccion email valida")
    @Size(max = 100, message = "error, campo correoAfiliacion maximo de 100 digitos")
    @Column(name = "correo_afiliacion")
    private String correoAfiliacion;
    @NotNull(message = "error, campo telefonoUnoAfiliacion obligatorio")
    @Size(max = 10, message = "error, campo telefonoUnoAfiliacion maximo de 10 digitos")
    @Column(name = "telefono_uno_afiliacion")
    private String telefonoUnoAfiliacion;
    @Size(max = 10, message = "error, campo telefonoDosAfiliacion maximo de 10 digitos")
    @Column(name = "telefono_dos_afiliacion")
    private String telefonoDosAfiliacion;
    @NotNull(message = "error, campo direccionAfiliacion obligatorio")
    @Size(max = 150, message = "error, campo direccionAfiliacion maximo de 150 digitos")
    @Column(name = "direccion_afiliacion")
    private String direccionAfiliacion;
    @NotNull(message = "error, campo ciudadAfiliacion obligatorio")
    @Column(name = "ciudad_afiliacion")
    private Integer ciudadAfiliacion;
    @NotNull(message = "error, campo esConductorAfiliacion obligatorio")
    @Column(name = "es_conductor_afiliacion")
    private Short esConductorAfiliacion;
    @NotNull(message = "error, campo obligatorio")
    @Size(max = 200, message = "error, campo fotoFrenteAfiliacion maximo de 200 digitos")
    @Column(name = "foto_frente_afiliacion")
    private String fotoFrenteAfiliacion;
    @NotNull(message = "error, campo obligatorio")
    @Size(max = 200, message = "error, campo fotoLadoAfiliacion maximo de 200 digitos")
    @Column(name = "foto_lado_afiliacion")
    private String fotoLadoAfiliacion;
    @NotNull(message = "error, campo fotoTraseraAfiliacion obligatorio")
    @Size(max = 200, message = "error, campo fotoTraseraAfiliacion maximo de 200 digitos")
    @Column(name = "foto_trasera_afiliacion")
    private String fotoTraseraAfiliacion;
    @Column(name = "inicio_convenio_afiliacion", columnDefinition = "TIMESTAMP")
    private String inicioConvenioAfiliacion;
    @Column(name = "fin_convenio_afiliacion", columnDefinition = "TIMESTAMP")
    private String finConvenioAfiliacion;
    @Size(max = 200, message = "error, campo maximo de 200 digitos")
    @Column(name = "convenio_afiliacion")
    private String convenioAfiliacion;
    @NotNull(message = "error, campo tarjetaPropiedadUnoAfiliacion obligatorio")
    @Size(max = 200, message = "error, campo tarjetaPropiedadUnoAfiliacion maximo de 200 digitos")
    @Column(name = "tarjeta_propiedad_uno_afiliacion")
    private String tarjetaPropiedadUnoAfiliacion;
    @NotNull(message = "error, campo tarjetaPropiedadDosAfiliacion obligatorio")
    @Size(max = 200, message = "error, campo tarjetaPropiedadDosAfiliacion maximo de 200 digitos")
    @Column(name = "tarjeta_propiedad_dos_afiliacion")
    private String tarjetaPropiedadDosAfiliacion;
    @NotNull(message = "error, campo numeroTarjetaOperacionAfiliacion obligatorio")
    @Size(max = 20, message = "error, campo numeroTarjetaOperacionAfiliacion maximo de 20 digitos")
    @Column(name = "numero_tarjeta_operacion_afiliacion")
    private String numeroTarjetaOperacionAfiliacion;
    @NotNull(message = "error, campo inicioTarjetaOperacionAfiliacion obligatorio")
    @Column(name = "inicio_tarjeta_operacion_afiliacion", columnDefinition = "TIMESTAMP")
    private String inicioTarjetaOperacionAfiliacion;
    @NotNull(message = "error, campo finTarjetaOperacionAfiliacion obligatorio")
    @Column(name = "fin_tarjeta_operacion_afiliacion", columnDefinition = "TIMESTAMP")
    private String finTarjetaOperacionAfiliacion;
    @NotNull(message = "error, campo tarjetaOperacionUnoAfiliacion obligatorio")
    @Size(max = 200, message = "error, campo tarjetaOperacionUnoAfiliacion maximo de 200 digitos")
    @Column(name = "tarjeta_operacion_uno_afiliacion")
    private String tarjetaOperacionUnoAfiliacion;
    @NotNull(message = "error, campo tarjetaOperacionDosAfiliacion obligatorio")
    @Size(max = 200, message = "error, campo tarjetaOperacionDosAfiliacion maximo de 200 digitos")
    @Column(name = "tarjeta_operacion_dos_afiliacion")
    private String tarjetaOperacionDosAfiliacion;
    @NotNull(message = "error, campo aseguradoraSoatAfiliacion obligatorio")
    @Column(name = "aseguradora_soat_afiliacion")
    private Integer aseguradoraSoatAfiliacion;
    @NotNull(message = "error, campo numeroSoatAfiliacion obligatorio")
    @Size(max = 20, message = "error, campo numeroSoatAfiliacion maximo de 20 digitos")
    @Column(name = "numero_soat_afiliacion")
    private String numeroSoatAfiliacion;
    @NotNull(message = "error, campo inicioSoatAfiliacion obligatorio")
    @Column(name = "inicio_soat_afiliacion", columnDefinition = "TIMESTAMP")
    private String inicioSoatAfiliacion;
    @NotNull(message = "error, campo finSoatAfiliacion obligatorio")
    @Column(name = "fin_soat_afiliacion", columnDefinition = "TIMESTAMP")
    private String finSoatAfiliacion;
    @NotNull(message = "error, campo soatAfiliacion obligatorio")
    @Size(max = 200, message = "error, campo soatAfiliacion maximo de 200 digitos")
    @Column(name = "soat_afiliacion")
    private String soatAfiliacion;
    @NotNull(message = "error, campo inicioTecnicomecanicaAfiliacion obligatorio")
    @Column(name = "inicio_tecnicomecanica_afiliacion", columnDefinition = "TIMESTAMP")
    private String inicioTecnicomecanicaAfiliacion;
    @NotNull(message = "error, campo finTecnicomecanicaAfiliacion obligatorio")
    @Column(name = "fin_tecnicomecanica_afiliacion", columnDefinition = "TIMESTAMP")
    private String finTecnicomecanicaAfiliacion;
    @NotNull(message = "error, campo tecnicomecanicaAfiliacion obligatorio")
    @Size(max = 200, message = "error, campo tecnicomecanicaAfiliacion maximo de 200 digitos")
    @Column(name = "tecnicomecanica_afiliacion")
    private String tecnicomecanicaAfiliacion;
    @NotNull(message = "error, campo aseguradoraContractualAfiliacion obligatorio")
    @Column(name = "aseguradora_contractual_afiliacion")
    private Integer aseguradoraContractualAfiliacion;
    @NotNull(message = "error, campo numeroContractualAfiliacion obligatorio")
    @Size(max = 16, message = "error, campo numeroContractualAfiliacion maximo de 16 digitos")
    @Column(name = "numero_contractual_afiliacion")
    private String numeroContractualAfiliacion;
    @NotNull(message = "error, campo inicioContractualAfiliacion obligatorio")
    @Column(name = "inicio_contractual_afiliacion", columnDefinition = "TIMESTAMP")
    private String inicioContractualAfiliacion;
    @NotNull(message = "error, campo finContractualAfiliacion obligatorio")
    @Column(name = "fin_contractual_afiliacion", columnDefinition = "TIMESTAMP")
    private String finContractualAfiliacion;
    @NotNull(message = "error, campo contractualAfiliacion obligatorio")
    @Size(max = 200, message = "error, campo contractualAfiliacion maximo de 200 digitos")
    @Column(name = "contractual_afiliacion")
    private String contractualAfiliacion;
    @NotNull(message = "error, campo aseguradoraExtracontractualAfiliacion obligatorio")
    @Column(name = "aseguradora_extracontractual_afiliacion")
    private Integer aseguradoraExtracontractualAfiliacion;
    @NotNull(message = "error, campo numeroExtracontractualAfiliacion obligatorio")
    @Size(max = 16, message = "error, campo numeroExtracontractualAfiliacion maximo de 16 digitos")
    @Column(name = "numero_extracontractual_afiliacion")
    private String numeroExtracontractualAfiliacion;
    @NotNull(message = "error, campo inicioExtracontractualAfiliacion obligatorio")
    @Column(name = "inicio_extracontractual_afiliacion", columnDefinition = "TIMESTAMP")
    private String inicioExtracontractualAfiliacion;
    @NotNull(message = "error, campo finExtracontractualAfiliacion obligatorio")
    @Column(name = "fin_extracontractual_afiliacion", columnDefinition = "TIMESTAMP")
    private String finExtracontractualAfiliacion;
    @NotNull(message = "error, campo extracontractualAfiliacion obligatorio")
    @Size(max = 200, message = "error, campo extracontractualAfiliacion maximo de 200 digitos")
    @Column(name = "extracontractual_afiliacion")
    private String extracontractualAfiliacion;
    @NotNull(message = "error, campo inicioPreventivaAfiliacion obligatorio")
    @Column(name = "inicio_preventiva_afiliacion", columnDefinition = "TIMESTAMP")
    private String inicioPreventivaAfiliacion;
    @NotNull(message = "error, campo finPreventivaAfiliacion obligatorio")
    @Column(name = "fin_preventiva_afiliacion", columnDefinition = "TIMESTAMP")
    private String finPreventivaAfiliacion;
    @NotNull(message = "error, campo preventivaAfiliacion obligatorio")
    @Size(max = 200, message = "error, campo preventivaAfiliacion maximo de 200 digitos")
    @Column(name = "preventiva_afiliacion")
    private String preventivaAfiliacion;
    
    @Column(name = "numero_motor_afiliacion")
    private String numeroMotorAfiliacion;
    @Column(name = "numero_chasis_afiliacion")
    private String numeroChasisAfiliacion;
    @Column(name="numero_vin_afiliacion")
    private String numeroVinAfiliacion;
    @Size(max = 2, message = "error, campo categoriaLicenciaAfiliacion maximo de 2 digitos")
    @Column(name = "categoria_licencia_afiliacion")
    private String categoriaLicenciaAfiliacion;
    @Column(name = "inicio_licencia_afiliacion", columnDefinition = "TIMESTAMP")
    private String inicioLicenciaAfiliacion;
    @Column(name = "fin_licencia_afiliacion", columnDefinition = "TIMESTAMP")
    private String finLicenciaAfiliacion;
    @Size(max = 200, message = "error, campo licenciaUnoAfiliacion maximo de 200 digitos")
    @Column(name = "licencia_uno_afiliacion")
    private String licenciaUnoAfiliacion;
    @Size(max = 200, message = "error, campo licenciaDosAfiliacion maximo de 200 digitos")
    @Column(name = "licencia_dos_afiliacion")
    private String licenciaDosAfiliacion;
    @NotNull(message = "error, campo documentoUnoAfiliacion obligatorio")
    @Size(max = 200, message = "error, campo documentoUnoAfiliacion maximo de 200 digitos")
    @Column(name = "documento_uno_afiliacion")
    private String documentoUnoAfiliacion;
    @NotNull(message = "error, campo documentoDosAfiliacion obligatorio")
    @Size(max = 200, message = "error, campo documentoDosAfiliacion maximo de 200 digitos")
    @Column(name = "documento_dos_afiliacion")
    private String documentoDosAfiliacion;
    @Column(name = "estado_afiliacion")
    private Integer estadoAfiliacion;
    @Column(name = "notificar_afiliacion")
    private Short notificarAfiliacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "afiliacion")
    private List<ObservacionAfiliacion> observacionAfiliacionList;
    
    
	public List<ObservacionAfiliacion> getObservacionAfiliacionList() {
		return observacionAfiliacionList;
	}

	public void setObservacionAfiliacionList(List<ObservacionAfiliacion> observacionAfiliacionList) {
		this.observacionAfiliacionList = observacionAfiliacionList;
	}

	public Afiliacion() {
		super();
	}

	public Long getIdAfiliacion() {
		return idAfiliacion;
	}

	public void setIdAfiliacion(Long idAfiliacion) {
		this.idAfiliacion = idAfiliacion;
	}

	public String getCodigoInternoAfiliacion() {
		return codigoInternoAfiliacion;
	}

	public void setCodigoInternoAfiliacion(String codigoInternoAfiliacion) {
		this.codigoInternoAfiliacion = codigoInternoAfiliacion;
	}

	public String getPlacaAfiliacion() {
		return placaAfiliacion;
	}

	public void setPlacaAfiliacion(String placaAfiliacion) {
		this.placaAfiliacion = placaAfiliacion;
	}

	public Integer getPasajerosAfiliacion() {
		return pasajerosAfiliacion;
	}

	public void setPasajerosAfiliacion(Integer pasajerosAfiliacion) {
		this.pasajerosAfiliacion = pasajerosAfiliacion;
	}

	public Integer getTipoCombustibleAfiliacion() {
		return tipoCombustibleAfiliacion;
	}

	public void setTipoCombustibleAfiliacion(Integer tipoCombustibleAfiliacion) {
		this.tipoCombustibleAfiliacion = tipoCombustibleAfiliacion;
	}

	public Integer getClaseVehiculoAfiliacion() {
		return claseVehiculoAfiliacion;
	}

	public void setClaseVehiculoAfiliacion(Integer claseVehiculoAfiliacion) {
		this.claseVehiculoAfiliacion = claseVehiculoAfiliacion;
	}

	public Integer getMarcaAfiliacion() {
		return marcaAfiliacion;
	}

	public void setMarcaAfiliacion(Integer marcaAfiliacion) {
		this.marcaAfiliacion = marcaAfiliacion;
	}

	public String getColorAfiliacion() {
		return colorAfiliacion;
	}

	public void setColorAfiliacion(String colorAfiliacion) {
		this.colorAfiliacion = colorAfiliacion;
	}

	public String getModeloAfiliacion() {
		return modeloAfiliacion;
	}

	public void setModeloAfiliacion(String modeloAfiliacion) {
		this.modeloAfiliacion = modeloAfiliacion;
	}

	public Integer getCilindrajeAfiliacion() {
		return cilindrajeAfiliacion;
	}

	public void setCilindrajeAfiliacion(Integer cilindrajeAfiliacion) {
		this.cilindrajeAfiliacion = cilindrajeAfiliacion;
	}

	public Integer getEmpresaConvenio() {
		return empresaConvenio;
	}

	public void setEmpresaConvenio(Integer empresaConvenio) {
		this.empresaConvenio = empresaConvenio;
	}

	public String getNombreAfiliacion() {
		return nombreAfiliacion;
	}

	public void setNombreAfiliacion(String nombreAfiliacion) {
		this.nombreAfiliacion = nombreAfiliacion;
	}

	public String getApellidoAfiliacion() {
		return apellidoAfiliacion;
	}

	public void setApellidoAfiliacion(String apellidoAfiliacion) {
		this.apellidoAfiliacion = apellidoAfiliacion;
	}

	public String getTipoDocAfiliacion() {
		return tipoDocAfiliacion;
	}

	public void setTipoDocAfiliacion(String tipoDocAfiliacion) {
		this.tipoDocAfiliacion = tipoDocAfiliacion;
	}

	public String getDocumentoAfiliacion() {
		return documentoAfiliacion;
	}

	public void setDocumentoAfiliacion(String documentoAfiliacion) {
		this.documentoAfiliacion = documentoAfiliacion;
	}

	public String getCorreoAfiliacion() {
		return correoAfiliacion;
	}

	public void setCorreoAfiliacion(String correoAfiliacion) {
		this.correoAfiliacion = correoAfiliacion;
	}

	public String getTelefonoUnoAfiliacion() {
		return telefonoUnoAfiliacion;
	}

	public void setTelefonoUnoAfiliacion(String telefonoUnoAfiliacion) {
		this.telefonoUnoAfiliacion = telefonoUnoAfiliacion;
	}

	public String getTelefonoDosAfiliacion() {
		return telefonoDosAfiliacion;
	}

	public void setTelefonoDosAfiliacion(String telefonoDosAfiliacion) {
		this.telefonoDosAfiliacion = telefonoDosAfiliacion;
	}

	public String getDireccionAfiliacion() {
		return direccionAfiliacion;
	}

	public void setDireccionAfiliacion(String direccionAfiliacion) {
		this.direccionAfiliacion = direccionAfiliacion;
	}

	public Integer getCiudadAfiliacion() {
		return ciudadAfiliacion;
	}

	public void setCiudadAfiliacion(Integer ciudadAfiliacion) {
		this.ciudadAfiliacion = ciudadAfiliacion;
	}

	public Short getEsConductorAfiliacion() {
		return esConductorAfiliacion;
	}

	public void setEsConductorAfiliacion(Short esConductorAfiliacion) {
		this.esConductorAfiliacion = esConductorAfiliacion;
	}

	public String getFotoFrenteAfiliacion() {
		return fotoFrenteAfiliacion;
	}

	public void setFotoFrenteAfiliacion(String fotoFrenteAfiliacion) {
		this.fotoFrenteAfiliacion = fotoFrenteAfiliacion;
	}

	public String getFotoLadoAfiliacion() {
		return fotoLadoAfiliacion;
	}

	public void setFotoLadoAfiliacion(String fotoLadoAfiliacion) {
		this.fotoLadoAfiliacion = fotoLadoAfiliacion;
	}

	public String getFotoTraseraAfiliacion() {
		return fotoTraseraAfiliacion;
	}

	public void setFotoTraseraAfiliacion(String fotoTraseraAfiliacion) {
		this.fotoTraseraAfiliacion = fotoTraseraAfiliacion;
	}

	public String getInicioConvenioAfiliacion() {
		return inicioConvenioAfiliacion;
	}

	public void setInicioConvenioAfiliacion(String inicioConvenioAfiliacion) {
		this.inicioConvenioAfiliacion = inicioConvenioAfiliacion;
	}

	public String getFinConvenioAfiliacion() {
		return finConvenioAfiliacion;
	}

	public void setFinConvenioAfiliacion(String finConvenioAfiliacion) {
		this.finConvenioAfiliacion = finConvenioAfiliacion;
	}

	public String getConvenioAfiliacion() {
		return convenioAfiliacion;
	}

	public void setConvenioAfiliacion(String convenioAfiliacion) {
		this.convenioAfiliacion = convenioAfiliacion;
	}

	public String getNumeroTarjetaOperacionAfiliacion() {
		return numeroTarjetaOperacionAfiliacion;
	}

	public void setNumeroTarjetaOperacionAfiliacion(String numeroTarjetaOperacionAfiliacion) {
		this.numeroTarjetaOperacionAfiliacion = numeroTarjetaOperacionAfiliacion;
	}

	public String getInicioTarjetaOperacionAfiliacion() {
		return inicioTarjetaOperacionAfiliacion;
	}

	public void setInicioTarjetaOperacionAfiliacion(String inicioTarjetaOperacionAfiliacion) {
		this.inicioTarjetaOperacionAfiliacion = inicioTarjetaOperacionAfiliacion;
	}

	public String getFinTarjetaOperacionAfiliacion() {
		return finTarjetaOperacionAfiliacion;
	}

	public void setFinTarjetaOperacionAfiliacion(String finTarjetaOperacionAfiliacion) {
		this.finTarjetaOperacionAfiliacion = finTarjetaOperacionAfiliacion;
	}

	public String getTarjetaOperacionUnoAfiliacion() {
		return tarjetaOperacionUnoAfiliacion;
	}

	public void setTarjetaOperacionUnoAfiliacion(String tarjetaOperacionUnoAfiliacion) {
		this.tarjetaOperacionUnoAfiliacion = tarjetaOperacionUnoAfiliacion;
	}

	public String getTarjetaOperacionDosAfiliacion() {
		return tarjetaOperacionDosAfiliacion;
	}

	public void setTarjetaOperacionDosAfiliacion(String tarjetaOperacionDosAfiliacion) {
		this.tarjetaOperacionDosAfiliacion = tarjetaOperacionDosAfiliacion;
	}

	public String getNumeroSoatAfiliacion() {
		return numeroSoatAfiliacion;
	}

	public void setNumeroSoatAfiliacion(String numeroSoatAfiliacion) {
		this.numeroSoatAfiliacion = numeroSoatAfiliacion;
	}

	public String getInicioSoatAfiliacion() {
		return inicioSoatAfiliacion;
	}

	public void setInicioSoatAfiliacion(String inicioSoatAfiliacion) {
		this.inicioSoatAfiliacion = inicioSoatAfiliacion;
	}

	public String getFinSoatAfiliacion() {
		return finSoatAfiliacion;
	}

	public void setFinSoatAfiliacion(String finSoatAfiliacion) {
		this.finSoatAfiliacion = finSoatAfiliacion;
	}

	public String getSoatAfiliacion() {
		return soatAfiliacion;
	}

	public void setSoatAfiliacion(String soatAfiliacion) {
		this.soatAfiliacion = soatAfiliacion;
	}

	public String getInicioTecnicomecanicaAfiliacion() {
		return inicioTecnicomecanicaAfiliacion;
	}

	public void setInicioTecnicomecanicaAfiliacion(String inicioTecnicomecanicaAfiliacion) {
		this.inicioTecnicomecanicaAfiliacion = inicioTecnicomecanicaAfiliacion;
	}

	public String getFinTecnicomecanicaAfiliacion() {
		return finTecnicomecanicaAfiliacion;
	}

	public void setFinTecnicomecanicaAfiliacion(String finTecnicomecanicaAfiliacion) {
		this.finTecnicomecanicaAfiliacion = finTecnicomecanicaAfiliacion;
	}

	public String getTecnicomecanicaAfiliacion() {
		return tecnicomecanicaAfiliacion;
	}

	public void setTecnicomecanicaAfiliacion(String tecnicomecanicaAfiliacion) {
		this.tecnicomecanicaAfiliacion = tecnicomecanicaAfiliacion;
	}

	public String getNumeroContractualAfiliacion() {
		return numeroContractualAfiliacion;
	}

	public void setNumeroContractualAfiliacion(String numeroContractualAfiliacion) {
		this.numeroContractualAfiliacion = numeroContractualAfiliacion;
	}

	public String getInicioContractualAfiliacion() {
		return inicioContractualAfiliacion;
	}

	public void setInicioContractualAfiliacion(String inicioContractualAfiliacion) {
		this.inicioContractualAfiliacion = inicioContractualAfiliacion;
	}

	public String getFinContractualAfiliacion() {
		return finContractualAfiliacion;
	}

	public void setFinContractualAfiliacion(String finContractualAfiliacion) {
		this.finContractualAfiliacion = finContractualAfiliacion;
	}

	public String getContractualAfiliacion() {
		return contractualAfiliacion;
	}

	public void setContractualAfiliacion(String contractualAfiliacion) {
		this.contractualAfiliacion = contractualAfiliacion;
	}

	public String getNumeroExtracontractualAfiliacion() {
		return numeroExtracontractualAfiliacion;
	}

	public void setNumeroExtracontractualAfiliacion(String numeroExtracontractualAfiliacion) {
		this.numeroExtracontractualAfiliacion = numeroExtracontractualAfiliacion;
	}

	public String getInicioExtracontractualAfiliacion() {
		return inicioExtracontractualAfiliacion;
	}

	public void setInicioExtracontractualAfiliacion(String inicioExtracontractualAfiliacion) {
		this.inicioExtracontractualAfiliacion = inicioExtracontractualAfiliacion;
	}

	public String getFinExtracontractualAfiliacion() {
		return finExtracontractualAfiliacion;
	}

	public void setFinExtracontractualAfiliacion(String finExtracontractualAfiliacion) {
		this.finExtracontractualAfiliacion = finExtracontractualAfiliacion;
	}

	public String getExtracontractualAfiliacion() {
		return extracontractualAfiliacion;
	}

	public void setExtracontractualAfiliacion(String extracontractualAfiliacion) {
		this.extracontractualAfiliacion = extracontractualAfiliacion;
	}

	public String getInicioPreventivaAfiliacion() {
		return inicioPreventivaAfiliacion;
	}

	public void setInicioPreventivaAfiliacion(String inicioPreventivaAfiliacion) {
		this.inicioPreventivaAfiliacion = inicioPreventivaAfiliacion;
	}

	public String getFinPreventivaAfiliacion() {
		return finPreventivaAfiliacion;
	}

	public void setFinPreventivaAfiliacion(String finPreventivaAfiliacion) {
		this.finPreventivaAfiliacion = finPreventivaAfiliacion;
	}

	public String getPreventivaAfiliacion() {
		return preventivaAfiliacion;
	}

	public void setPreventivaAfiliacion(String preventivaAfiliacion) {
		this.preventivaAfiliacion = preventivaAfiliacion;
	}

	public String getNumeroMotorAfiliacion() {
		return numeroMotorAfiliacion;
	}

	public void setNumeroMotorAfiliacion(String numeroMotorAfiliacion) {
		this.numeroMotorAfiliacion = numeroMotorAfiliacion;
	}

	public String getNumeroChasisAfiliacion() {
		return numeroChasisAfiliacion;
	}

	public void setNumeroChasisAfiliacion(String numeroChasisAfiliacion) {
		this.numeroChasisAfiliacion = numeroChasisAfiliacion;
	}

	public String getNumeroVinAfiliacion() {
		return numeroVinAfiliacion;
	}

	public void setNumeroVinAfiliacion(String numeroVinAfiliacion) {
		this.numeroVinAfiliacion = numeroVinAfiliacion;
	}

	public String getCategoriaLicenciaAfiliacion() {
		return categoriaLicenciaAfiliacion;
	}

	public void setCategoriaLicenciaAfiliacion(String categoriaLicenciaAfiliacion) {
		this.categoriaLicenciaAfiliacion = categoriaLicenciaAfiliacion;
	}

	public String getInicioLicenciaAfiliacion() {
		return inicioLicenciaAfiliacion;
	}

	public void setInicioLicenciaAfiliacion(String inicioLicenciaAfiliacion) {
		this.inicioLicenciaAfiliacion = inicioLicenciaAfiliacion;
	}

	public String getFinLicenciaAfiliacion() {
		return finLicenciaAfiliacion;
	}

	public void setFinLicenciaAfiliacion(String finLicenciaAfiliacion) {
		this.finLicenciaAfiliacion = finLicenciaAfiliacion;
	}

	public String getLicenciaUnoAfiliacion() {
		return licenciaUnoAfiliacion;
	}

	public void setLicenciaUnoAfiliacion(String licenciaUnoAfiliacion) {
		this.licenciaUnoAfiliacion = licenciaUnoAfiliacion;
	}

	public String getLicenciaDosAfiliacion() {
		return licenciaDosAfiliacion;
	}

	public void setLicenciaDosAfiliacion(String licenciaDosAfiliacion) {
		this.licenciaDosAfiliacion = licenciaDosAfiliacion;
	}

	public String getDocumentoUnoAfiliacion() {
		return documentoUnoAfiliacion;
	}

	public void setDocumentoUnoAfiliacion(String documentoUnoAfiliacion) {
		this.documentoUnoAfiliacion = documentoUnoAfiliacion;
	}

	public String getDocumentoDosAfiliacion() {
		return documentoDosAfiliacion;
	}

	public void setDocumentoDosAfiliacion(String documentoDosAfiliacion) {
		this.documentoDosAfiliacion = documentoDosAfiliacion;
	}

	public Integer getEstadoAfiliacion() {
		return estadoAfiliacion;
	}

	public void setEstadoAfiliacion(Integer estadoAfiliacion) {
		this.estadoAfiliacion = estadoAfiliacion;
	}

	public Integer getAseguradoraSoatAfiliacion() {
		return aseguradoraSoatAfiliacion;
	}

	public void setAseguradoraSoatAfiliacion(Integer aseguradoraSoatAfiliacion) {
		this.aseguradoraSoatAfiliacion = aseguradoraSoatAfiliacion;
	}

	public Integer getAseguradoraContractualAfiliacion() {
		return aseguradoraContractualAfiliacion;
	}

	public void setAseguradoraContractualAfiliacion(Integer aseguradoraContractualAfiliacion) {
		this.aseguradoraContractualAfiliacion = aseguradoraContractualAfiliacion;
	}

	public Integer getAseguradoraExtracontractualAfiliacion() {
		return aseguradoraExtracontractualAfiliacion;
	}

	public void setAseguradoraExtracontractualAfiliacion(Integer aseguradoraExtracontractualAfiliacion) {
		this.aseguradoraExtracontractualAfiliacion = aseguradoraExtracontractualAfiliacion;
	}

	public Short getNotificarAfiliacion() {
		return notificarAfiliacion;
	}

	public void setNotificarAfiliacion(Short notificarAfiliacion) {
		this.notificarAfiliacion = notificarAfiliacion;
	}

	public Integer getTipoVehiculoAfiliacion() {
		return tipoVehiculoAfiliacion;
	}

	public void setTipoVehiculoAfiliacion(Integer tipoVehiculoAfiliacion) {
		this.tipoVehiculoAfiliacion = tipoVehiculoAfiliacion;
	}

	public String getTarjetaPropiedadUnoAfiliacion() {
		return tarjetaPropiedadUnoAfiliacion;
	}

	public void setTarjetaPropiedadUnoAfiliacion(String tarjetaPropiedadUnoAfiliacion) {
		this.tarjetaPropiedadUnoAfiliacion = tarjetaPropiedadUnoAfiliacion;
	}

	public String getTarjetaPropiedadDosAfiliacion() {
		return tarjetaPropiedadDosAfiliacion;
	}

	public void setTarjetaPropiedadDosAfiliacion(String tarjetaPropiedadDosAfiliacion) {
		this.tarjetaPropiedadDosAfiliacion = tarjetaPropiedadDosAfiliacion;
	}

    
    
}
