package com.itefs.trexsas.servicio;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.CuentaCobro;
import com.itefs.trexsas.repositorio.CuentaCobroRepositorio;

@Service
public class CuentaCobroServicio {
	
	@Autowired
	private CuentaCobroRepositorio cuentaCobroRepositorio;
	
	public void crear(CuentaCobro cuentaCobro) {
		cuentaCobro.setIdCuentaCobro(null);
		cuentaCobroRepositorio.save(cuentaCobro);
	}
	
	public CuentaCobro crear2(CuentaCobro cuentaCobro) {
		//CuentaCobro cc = cuentaCobroRepositorio.traerUltimoRegistro();
		//System.out.println("total : "+cc.getIdCuentaCobro());
		//cuentaCobro.setNumeroCuentaCobro(cc.getNumeroCuentaCobro()+1);
		cuentaCobro.setIdCuentaCobro(null);
		CuentaCobro nueva = cuentaCobroRepositorio.save(cuentaCobro);
		return nueva;
	}
	
	public void actualizar(CuentaCobro cuentaCobro) {
		cuentaCobroRepositorio.save(cuentaCobro);
	}
	
	public List<CuentaCobro> obtenerTodos() {
		return cuentaCobroRepositorio.findAll();
	}
	
	public Page<Object[]> listar(int inicio) {
		Page<Object[]> page= cuentaCobroRepositorio.listarCuentasCobroPaginado(PageRequest.of(inicio, 10));
		return page;
	}
	
	public CuentaCobro obtenerPorId(Long id) {
		Optional<CuentaCobro> op=cuentaCobroRepositorio.findById(id);
		if(op.isPresent()) {
			return op.get();
		}
		return null;
	}
	/*
	 * public void eliminarPorId(Long id)
		{
			cuentaCobroRepositorio.deleteById(id);
		}
	 * */
	public void eliminarPorId(CuentaCobro cc)
	{
		cuentaCobroRepositorio.save(cc);
	}
	
	//consulta los datos necesarios para la creacion del pdf
	public List<Object[]> pdf(Long id) {
		return cuentaCobroRepositorio.datosPdf(id);
	}

}
