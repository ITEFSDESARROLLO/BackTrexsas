package com.itefs.trexsas.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.ManyToAny;

@Entity
@Table(name="pasajero_fuec")
public class PasajeroFuec 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_pasajero_fuec")
	private Long id;
	@NotNull
	@Column(name="fuec_id_fuec")
	private Long fuec;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "pasajero_id_pasajero", referencedColumnName = "id_pasajero")
	private Pasajero pasajero;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFuec() {
		return fuec;
	}

	public void setFuec(Long fuec) {
		this.fuec = fuec;
	}

	public Pasajero getPasajero() {
		return pasajero;
	}

	public void setPasajero(Pasajero pasajero) {
		this.pasajero = pasajero;
	}
	
	
}
