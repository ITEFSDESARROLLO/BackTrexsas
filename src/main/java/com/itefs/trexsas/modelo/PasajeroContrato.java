package com.itefs.trexsas.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="pasajero_contrato")
public class PasajeroContrato 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_pasajero_contrato")
	private Long id;
	
	@OneToOne
	@JoinColumn
	private Pasajero pasajero;
	@NotNull
	@Column(name="contrato_id_contrato")
	private Long contrato;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Pasajero getPasajero() {
		return pasajero;
	}
	public void setPasajero(Pasajero pasajero) {
		this.pasajero = pasajero;
	}
	public Long getContrato() {
		return contrato;
	}
	public void setContrato(Long contrato) {
		this.contrato = contrato;
	}
	
}
