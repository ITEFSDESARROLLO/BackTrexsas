package com.itefs.trexsas.servicio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.Encuesta;
import com.itefs.trexsas.modelo.EncuestaRespondida;
import com.itefs.trexsas.modelo.Opcion;
import com.itefs.trexsas.modelo.Persona;
import com.itefs.trexsas.modelo.Pregunta;
import com.itefs.trexsas.modelo.RespuestaEncuesta;
import com.itefs.trexsas.repositorio.ConductorRepositorio;
import com.itefs.trexsas.repositorio.EncuestaRepositorio;
import com.itefs.trexsas.repositorio.EncuestaRespondidaRepository;
import com.itefs.trexsas.repositorio.OpcionRepositorio;
import com.itefs.trexsas.repositorio.PreguntaRepositorio;
import com.itefs.trexsas.repositorio.RespuestaEncuestaRepository;

@Service
public class RespuestaEncuestasServicio 
{
	@Autowired
	private EncuestaRespondidaRepository encuestaRespondidaRepository;
	@Autowired
	private RespuestaEncuestaRepository respuestaEncuestaRepository;
	@Autowired
	private EncuestaRepositorio encuestaRepository;
	
	@Autowired
	private PreguntaRepositorio preguntaRepository;
	
	@Autowired
	private OpcionRepositorio opcionRepositorio;
	
	@Autowired
	private ConductorRepositorio conductorRepositorio;
	
	
	public EncuestaRespondida responderEncuesta(EncuestaRespondida encuestaRespondida)
	{
		return encuestaRespondidaRepository.save(encuestaRespondida);
	}
	
	public void guardarRespuesEncuesta(RespuestaEncuesta respuestaEncuesta)
	{
		respuestaEncuestaRepository.save(respuestaEncuesta);
	}
	
	public boolean verificarExistenciaRespuestaEncuesta(Long encuesta, Long servicio)
	{
		EncuestaRespondida respuesta = encuestaRespondidaRepository.traerEncuesta(encuesta, servicio);
		if(respuesta==null)
		{
			return false;
		}else
		{
			return true;
		}
	}
	
	public HashMap<String,Object> obtenerCalificacionesConductor(String conductor)
	{
		System.out.println("1");
		Persona personaConductor = conductorRepositorio.obtenerConductorPersona(conductor);
		System.out.println("2");
		Integer idConductor = conductorRepositorio.obtenerConductor(conductor);
		System.out.println("3");
		List<EncuestaRespondida> listaRespuestasConductor = encuestaRespondidaRepository.traerEncuestaConductor(idConductor);
		System.out.println("4");
		System.out.println("encuestas encontradas : "+listaRespuestasConductor.size());
		Optional<Encuesta>encuesta = encuestaRepository.findById(listaRespuestasConductor.get(0).getEncuesta());
		encuesta.get().getPreguntasEncuesta();
		List<Pregunta> preguntasSistema = preguntaRepository.findAll();
		List<Opcion> opcionesSistema = opcionRepositorio.findAll();
		
		List<HashMap<String, Object>> salida = new ArrayList<HashMap<String, Object>>();
		for (EncuestaRespondida encuestaRespondida : listaRespuestasConductor)
		{
			List<HashMap<String, String>> preguntasRespuestaSalida = new ArrayList<HashMap<String, String>>();
			System.out.println("respuestas "+encuestaRespondida.getRespuestaEncuesta().size());
			List<RespuestaEncuesta> respuestas = encuestaRespondida.getRespuestaEncuesta();
			for(RespuestaEncuesta respuesta : respuestas)
			{
				String descripcionPregunta = "";
				String descripcionOpcion = "";
				for (Pregunta pregunta : preguntasSistema) {
					if(pregunta.getId() == respuesta.getPregunta())
					{
						descripcionPregunta = pregunta.getDescripcion();
						break;
					}
				}
				
				for (Opcion opcion : opcionesSistema) {
					if(opcion.getId() == respuesta.getOpcion())
					{
						descripcionOpcion = opcion.getDescripcion();
						break;
					}
				}
				
				HashMap<String, String> preguntaRespuesta = new HashMap<String, String>();
				new HashMap<String, String>();
				preguntaRespuesta.put("pregunta",descripcionPregunta);
				preguntaRespuesta.put("opcion", descripcionOpcion);
				preguntasRespuestaSalida.add(preguntaRespuesta);
			}
			HashMap<String, Object> servicio = new HashMap<String, Object>();
			new HashMap<String, Long>();
			new HashMap<String, List<HashMap<String, String>>>();
			servicio.put("fechaServicio", encuestaRespondida.getFechaRespuesta());
			servicio.put("numeroServicio", encuestaRespondida.getServicio());
			servicio.put("encuestaAsignada", encuestaRespondida.getEncuesta());
			servicio.put("arreglo", preguntasRespuestaSalida);
			//Object[] encuestaSalida = {servicio,arreglo,encuestaAsignada};
			salida.add(servicio);
		}
		
		
		HashMap<String, Object> salidaFinal = new HashMap<String, Object>();
		salidaFinal.put("Conductor",personaConductor.getNombrePersona()+" "+personaConductor.getApellidoPersona());
		salidaFinal.put("calificaciones", salida);
		return salidaFinal;
	}

}
