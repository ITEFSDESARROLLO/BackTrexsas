package com.itefs.trexsas.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.Cuenta;
import com.itefs.trexsas.repositorio.CuentaRepositorio;
import com.itefs.trexsas.utilidades.Cifrador;

@Service
public class IngresoServicio {;
	
	@Autowired
	private CuentaRepositorio cuentaRepositorio;
	
	public Cuenta validarCuentayClave(String usuario, String clave){
		Cifrador cif=new Cifrador();
		Optional<Cuenta>opCuenta=cuentaRepositorio.validarCuentayClave(usuario, cif.cifrar(clave));
		if(opCuenta.isPresent()) {
			return opCuenta.get();
		}
		return null;
	}
	
	public String obtenerCorreo(String usuario) {
		Optional<String> opCorreo=cuentaRepositorio.encontrarCorreoPorUsername(usuario);
		if(opCorreo.isPresent()) {	
			return opCorreo.get();
		}
		return null;
	}
	
	public List<Object[]> obtenerNombreYCorreo(String usuario) {
		List<Object[]> opCorreo=cuentaRepositorio.encontrarNombreCorreoPorUsername(usuario);
		return opCorreo;
	}
	
	public List<Cuenta> cuentas(){
		
		return cuentaRepositorio.findAll();
	}
}
