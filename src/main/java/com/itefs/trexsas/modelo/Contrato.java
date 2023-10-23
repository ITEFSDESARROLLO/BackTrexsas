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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;


@Entity
@Table(name = "contrato")
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_contrato")
    private Long idContrato;

    @NotNull(message = "error, campo numeracionContrato obligatorio")
    @Column(name = "numeracion_contrato")
    private String numeracionContrato;
    @NotNull(message = "error, campo estadoContrato obligatorio")
    @Column(name = "estado_contrato")
    
    private Integer estadoContrato; 
    
    @Column(name = "valor_contrato")
    private Long valorContrato;
    @Column(name = "contador_contrato")
    private Integer contadorContrato;
    @Column(name = "consecutivo_contrato")
    private Long consecutivoContrato;
    @NotNull
	@Column(name="responsable_contrato")
    private String responsable;
    
    @NotNull
	@Column(name="documento_responsable_contrato")
    private String documentoResponsable;
    
    @NotNull
    @Column(name="telefono_responsable")
    private String telefonoResponsable;
    
    @NotNull
    @Column(name="direccion_responsable")
    private String direccionResponsable;
    
    @NotNull
	@Column(name="objeto_contrato")
    private String objetoContrato;
    
	@Column(name="fecha_inicio_contrato")
    private String fechaInicioContrato;
    
    
	@Column(name="fecha_fin_contrato")
    private String fechaFinContrato;
    
    
    @OneToOne
    @JoinColumn(name = "tipo_contrato", referencedColumnName = "id_tipo_contrato")
	private TipoContrato tipoContrato;
    
    
    @ManyToOne
    @JoinColumn(name = "cliente_id_cliente", referencedColumnName = "id_cliente")
	private Cliente cliente;
    
    
    @OneToOne
    @JoinColumn(name = "ciudad", referencedColumnName = "id_ciudad")
	private Ciudad ciudad;
    
    
	
	@JoinTable(name = "pasajero_contrato", joinColumns = {
		@JoinColumn(name = "contrato_id_contrato", referencedColumnName = "id_contrato") }, 
			inverseJoinColumns = {
		@JoinColumn(name = "pasajero_id_pasajero", referencedColumnName = "id_pasajero") })
	@ManyToMany
	private List<Pasajero> pasajeroList;
	
	
	@NotNull
	@NumberFormat(style = Style.DEFAULT)
	@Column(name="no_contrato")
	private Integer noContrato;
    
    public Contrato() {
    	super();
    }
    
 // metodo que asocia un pasajero a contrato 
 	public void addPasajero(Pasajero pasajero) {
 		if (this.pasajeroList == null) {
 			this.pasajeroList = new ArrayList<>();
 		}

 		this.pasajeroList.add(pasajero);
 	}

 	// metodo que desasocia un pasajero a contrato 
 	public void removePasajero(Pasajero pasajero) {
 		if (this.pasajeroList == null) {
 			this.pasajeroList = new ArrayList<>();
 		}

 		this.pasajeroList.remove(pasajero);
 	}

	public Long getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}

	public String getNumeracionContrato() {
		return numeracionContrato;
	}

	public void setNumeracionContrato(String numeracionContrato) {
		this.numeracionContrato = numeracionContrato;
	}

	public Long getValorContrato() {
		return valorContrato;
	}

	public void setValorContrato(Long valorContrato) {
		this.valorContrato = valorContrato;
	}

	public List<Pasajero> getPasajeroList() {
		return pasajeroList;
	}

	public void setPasajeroList(List<Pasajero> pasajeroList) {
		this.pasajeroList = pasajeroList;
	}

	public Integer getEstadoContrato() {
		return estadoContrato;
	}

	public void setEstadoContrato(Integer estadoContrato) {
		this.estadoContrato = estadoContrato;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Integer getContadorContrato() {
		return contadorContrato;
	}

	public void setContadorContrato(Integer contadorContrato) {
		this.contadorContrato = contadorContrato;
	}

	public Long getConsecutivoContrato() {
		return consecutivoContrato;
	}

	public void setConsecutivoContrato(Long consecutivoContrato) {
		this.consecutivoContrato = consecutivoContrato;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getDocumentoResponsable() {
		return documentoResponsable;
	}

	public void setDocumentoResponsable(String documentoResponsable) {
		this.documentoResponsable = documentoResponsable;
	}

	public String getObjetoContrato() {
		return objetoContrato;
	}

	public void setObjetoContrato(String objetoContrato) {
		this.objetoContrato = objetoContrato;
	}

	public TipoContrato getTipoContrato() {
		return tipoContrato;
	}

	public void setTipoContrato(TipoContrato tipoContrato) {
		this.tipoContrato = tipoContrato;
	}

	public String getFechaInicioContrato() {
		return fechaInicioContrato;
	}

	public void setFechaInicioContrato(String fechaInicioContrato) {
		this.fechaInicioContrato = fechaInicioContrato;
	}

	public String getFechaFinContrato() {
		return fechaFinContrato;
	}

	public void setFechaFinContrato(String fechaFinContrato) {
		this.fechaFinContrato = fechaFinContrato;
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = ciudad;
	}

	public String getTelefonoResponsable() {
		return telefonoResponsable;
	}

	public void setTelefonoResponsable(String telefonoResponsable) {
		this.telefonoResponsable = telefonoResponsable;
	}

	public String getDireccionResponsable() {
		return direccionResponsable;
	}

	public void setDireccionResponsable(String direccionResponsable) {
		this.direccionResponsable = direccionResponsable;
	}

	public Integer getNoContrato() {
		return noContrato;
	}

	public void setNoContrato(Integer noContrato) {
		this.noContrato = noContrato;
	}

	
	
	
	
	
}
