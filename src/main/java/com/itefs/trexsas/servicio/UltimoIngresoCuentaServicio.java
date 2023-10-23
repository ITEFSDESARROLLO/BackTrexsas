package com.itefs.trexsas.servicio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.UltimoIngresoCuenta;
import com.itefs.trexsas.repositorio.UltimoIngresoCuentaRepositorio;

@Service
public class UltimoIngresoCuentaServicio {
	
	@Autowired
	private UltimoIngresoCuentaRepositorio cuentaRepositorio;
	
	public void crear(UltimoIngresoCuenta ultimoIngresoCuenta) {
		ultimoIngresoCuenta.setIdUltimoIngresoCuenta(null);
		cuentaRepositorio.save(ultimoIngresoCuenta);
	}
	
	public void actualizar(UltimoIngresoCuenta ultimoIngresoCuenta) {
		cuentaRepositorio.save(ultimoIngresoCuenta);
	}
	
	public Page<Object[]> listar(int inicio) {
		Page<Object[]> page= cuentaRepositorio.listarPaginado(PageRequest.of(inicio, 10));
		return page;
	}
	
	public UltimoIngresoCuenta obtenerPorId(long id) {
		Optional<UltimoIngresoCuenta> op=cuentaRepositorio.findById(id);
		if(op.isPresent()) {
			return op.get();
		}
		return null;
	}

}
