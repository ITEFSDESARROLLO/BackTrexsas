package com.itefs.trexsas.servicio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.PolizaTodoRiesgo;
import com.itefs.trexsas.repositorio.PolizaTodoRiesgoRepositorio;

@Service
public class PolizaTodoRiesgoServicio 
{
	@Autowired
	private PolizaTodoRiesgoRepositorio polizaTodoRiesgoRepositorio;
	
	public void guardarPolizaTodoRiesgo(PolizaTodoRiesgo poliza)
	{
		poliza.setId(null);
		polizaTodoRiesgoRepositorio.save(poliza);
	}
	
	public void actualizarPolizaTodoRiesgo(PolizaTodoRiesgo poliza)
	{
		polizaTodoRiesgoRepositorio.save(poliza);
	}
	
	public PolizaTodoRiesgo obtenerPorId(Long id)
	{
		Optional<PolizaTodoRiesgo> objetoPoliza = polizaTodoRiesgoRepositorio.findById(id);
		if(objetoPoliza.isPresent())
		{
			return objetoPoliza.get();
		}else
		{
			return null;
		}
	}

}
