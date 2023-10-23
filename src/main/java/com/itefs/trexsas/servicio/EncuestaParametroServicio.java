package com.itefs.trexsas.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.EncuestaParametrizacion;
import com.itefs.trexsas.modelo.TipoParametro;
import com.itefs.trexsas.repositorio.EncuestaParametroRepositorio;
import com.itefs.trexsas.repositorio.TipoParametroRepositorio;

@Service
public class EncuestaParametroServicio 
{
	@Autowired
	private EncuestaParametroRepositorio encuestaParametroRepositorio;
	
	@Autowired
	private TipoParametroRepositorio tipoParametroRepositorio;

	public void crearEncuestaParametro(EncuestaParametrizacion encuestaParametro)
	{
		encuestaParametro.setId(null);
		encuestaParametroRepositorio.save(encuestaParametro);
	}
	
	public void actualizarEncuestaParametro(EncuestaParametrizacion encuestaParametro)
	{
		encuestaParametroRepositorio.save(encuestaParametro);
	}
	
	public void eliminarEncuesta(Long encuestaParametro)
	{
		encuestaParametroRepositorio.deleteById(encuestaParametro);
	}
	
	public List<EncuestaParametrizacion> traerEncuestasParametrizadas()
	{
		return encuestaParametroRepositorio.findAll();
	}
	
	public EncuestaParametrizacion traerEncuesta(Long id)
	{
		Optional<EncuestaParametrizacion> encuesta = encuestaParametroRepositorio.findById(id);
		if(encuesta.isPresent())
		{
			return encuesta.get();
		}else
		{
			return null;
		}
	}
	
	public TipoParametro buscarPorId(Long id)
	{
		Optional<TipoParametro> encuesta = tipoParametroRepositorio.findById(id);
		if(encuesta.isPresent())
		{
			return encuesta.get();
		}else
		{
			return null;
		}
	}
	
	public List<TipoParametro> traerTodosParametros()
	{
		return tipoParametroRepositorio.findAll();
	}
}
