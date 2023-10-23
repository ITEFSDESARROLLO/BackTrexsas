package com.itefs.trexsas.modelo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "correo_pqr")
public class CorreoPQR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_correo")
    private Integer idCorreo;
    @NotNull(message = "error, campo correo obligatorio")
    @Email(message = "error, el campo correo no corresponde a una direccion email valida")
    @Size(max = 150, message = "error, campo correo maximo de 150 digitos")
    @Column(name = "correo")
    private String correo;
	
	public Integer getIdCorreo() {
		return idCorreo;
	}

	public void setIdCorreo(Integer idCorreo) {
		this.idCorreo = idCorreo;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
}
