package com.itefs.trexsas.servicio;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.Encuesta;
import com.itefs.trexsas.modelo.EncuestaRespondida;
import com.itefs.trexsas.modelo.Opcion;
import com.itefs.trexsas.modelo.Pregunta;
import com.itefs.trexsas.repositorio.EncuestaRepositorio;
import com.itefs.trexsas.repositorio.EncuestaRespondidaRepository;
import com.itefs.trexsas.repositorio.OpcionRepositorio;
import com.itefs.trexsas.repositorio.PreguntaRepositorio;

@Service
public class EncuestaServicio {

	@Autowired
	private EncuestaRepositorio encuentaRepositorio;
	
	@Autowired
	private PreguntaRepositorio preguntaRepositorio;
	
	@Autowired
	private OpcionRepositorio opcionRepositorio;
	
	@Autowired
	private EncuestaRespondidaRepository encuestaRespondidaRepository;
	
	
	public Encuesta crear(Encuesta encuesta)
	{
		System.out.println("1. total : "+encuesta.toString());
		//encuesta.setId(null);
		Encuesta guardada = encuentaRepositorio.save(encuesta);
		System.out.println("sisas");
		return guardada;
	}
	
	
	public void actualizar(Encuesta encuesta)
	{
		List<Pregunta> preguntasAntiguas = preguntaRepositorio.encontrarPorEncuesta(encuesta.getId());
		List<Pregunta> preguntasNuevasModificadas = encuesta.getPreguntasEncuesta();
		
		List<Pregunta> preguntasModificadas = new ArrayList<Pregunta>();
		
		List<Pregunta> preguntasEliminar = new ArrayList<Pregunta>();
		
		List<Pregunta> preguntasNuevas = new ArrayList<Pregunta>();
		
		
		List<Opcion> opcionesModificadas = new ArrayList<Opcion>();
		
		List<Opcion> opcionesNuevas = new ArrayList<Opcion>();
		
		List<Opcion> opcionesEliminar = new ArrayList<Opcion>();
		
		
		System.out.println("tamaño preguntas nuevas : "+preguntasNuevasModificadas.size());
		
		for (Pregunta pregunta : preguntasAntiguas)
		{
			System.out.println("descripción : "+pregunta.getDescripcion());
			System.out.println("id : "+pregunta.getId());
			System.out.println("1");
			boolean preguntaEncontrada = false;
			for (Pregunta preguntaNuevaModificada : preguntasNuevasModificadas) 
			{
				System.out.println("2");
				if(preguntaNuevaModificada.getId()==pregunta.getId())
				{
					System.out.println("3");
					System.out.println("modificada : "+preguntaNuevaModificada.getId());
					preguntasNuevasModificadas.remove(preguntaNuevaModificada);
					System.out.println("tamaño preguntas nuevas : "+preguntasNuevasModificadas.size());
					preguntaEncontrada = true;
					
					List<Opcion> opcionesAntiguas = pregunta.getOpcionesPregunta();
					List<Opcion> opcionesNuevasModificadas = preguntaNuevaModificada.getOpcionesPregunta();
					for(Opcion opcionAntigua: opcionesAntiguas)
					{
						System.out.println("4");
						boolean opcionEncontrada = false;
						for(Opcion opcionNuevaModificada: opcionesNuevasModificadas )
						{
							System.out.println("5");
							System.out.println("opcion . "+opcionNuevaModificada.getDescripcion());
							if(opcionAntigua.getId() == opcionNuevaModificada.getId())
							{
								System.out.println("6");
								System.out.println("opcion modificada : "+opcionNuevaModificada.getDescripcion());
								opcionAntigua.setDescripcion(opcionNuevaModificada.getDescripcion());
								opcionesModificadas.add(opcionAntigua);
								//pregunta.eliminarOpcion(opcionAntigua);
								//pregunta.agregarOpcion(opcionNuevaModificada);
								opcionEncontrada = true;
								break;
							}else if(opcionNuevaModificada.getId()==null)
							{
								System.out.println("7");
								opcionNuevaModificada.setId(Long.valueOf("0"));
								opcionNuevaModificada.setPregunta(pregunta);
								System.out.println("opcion nueva : "+opcionNuevaModificada.getDescripcion());
								opcionesNuevas.add(opcionNuevaModificada);
								opcionEncontrada = true;
								break;
							}
						}
						if(opcionEncontrada == false)
						{
							System.out.println("8 \n 8 \t "+opcionAntigua.getPregunta().getEncuesta()!=null);
							pregunta.eliminarOpcion(opcionAntigua);
							opcionesEliminar.add(opcionAntigua);
							//opcionRepositorio.delete(opcionAntigua);
							System.out.println("opcion eliminada "+opcionAntigua.getDescripcion());
						}
					}
					pregunta.setDescripcion(preguntaNuevaModificada.getDescripcion());
					preguntasModificadas.add(pregunta);
					preguntaEncontrada = true;
					break;
				}
			}
			
			System.out.println("pregunta eliminada \n "+preguntaEncontrada);
			if(preguntaEncontrada==false)
			{
				System.out.println("pregunta eliminada "+pregunta.getDescripcion());
				preguntasEliminar.add(pregunta);
			}
			
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
		}
		if(preguntasNuevasModificadas.size()>0)
		{
			System.out.println("existen preguntas nuevas");
			for (Pregunta preguntaNuevaModificada : preguntasNuevasModificadas) 
			{
				System.out.println("pregunta nueva "+preguntaNuevaModificada.getDescripcion());
				preguntaNuevaModificada.setId(null);
				preguntaNuevaModificada.setEncuesta(encuesta);
				preguntasNuevas.add(preguntaNuevaModificada);
				preguntaNuevaModificada.getOpcionesPregunta().forEach((opcion)->{
					opcion.setId(null);
					opcion.setPregunta(preguntaNuevaModificada);
				});
				
			}
		}
		
		for (Opcion opcion : opcionesEliminar) {
			System.out.println("opcion eliminada "+opcion.getDescripcion()+" \n estado : "+opcion.getPregunta()==null);
			
			
		}
		
		for (Opcion opcion : opcionesModificadas) {
			System.out.println("opcion modificada "+opcion.getDescripcion());
			opcionRepositorio.save(opcion);
		}
		
		for (Opcion opcion : opcionesNuevas) {
			
			System.out.println("opcion nueva "+opcion.getDescripcion());
			opcion.setId(null);
			opcionRepositorio.save(opcion);
		}
		
		for (Pregunta pregunta : preguntasEliminar)
		{
			System.out.println("preguntas eliminadas "+pregunta.getDescripcion());
			for(Opcion opcion : pregunta.getOpcionesPregunta())
			{
				opcionRepositorio.delete(opcion);
			}
			preguntaRepositorio.delete(pregunta);
		}
		
		for (Pregunta pregunta : preguntasModificadas) {
			System.out.println("preguntas modificadas "+pregunta.getDescripcion());
			preguntaRepositorio.save(pregunta);
		}
		
		for (Pregunta pregunta : preguntasNuevas) {
			System.out.println("pregunta nueva "+pregunta.getDescripcion());
			pregunta.getOpcionesPregunta().forEach((opcion)->{
				opcion.setId(null);
				opcion.setPregunta(pregunta);
				
			});
			preguntaRepositorio.save(pregunta);
		}
	}
	
	public void actualizar2(Encuesta encuesta)
	{
		encuentaRepositorio.save(encuesta);
	}
	
	public void eliminar(long id)
	{
		encuentaRepositorio.deleteById(id);
		
	}
	
	public void eliminarRespuesta(Integer conductor)
	{
		List<EncuestaRespondida> er = encuestaRespondidaRepository.traerEncuestaConductor(conductor);
		if(er!=null)
		{
			for (EncuestaRespondida encuestaRespondida : er)
			{
				encuestaRespondidaRepository.delete(encuestaRespondida);
			}
		}
		
	}
	
	public Encuesta obtenerPorId(long id)
	{
		Optional<Encuesta> objetoEncuesta = encuentaRepositorio.findById(id);
		if(objetoEncuesta.isPresent())
		{
			return objetoEncuesta.get();
		}else
		{
			return null;
		}
	}
	
	
	public List<Pregunta> buscarPreguntasEncuesta(Long encuesta)
	{
		List<Pregunta> lista = preguntaRepositorio.findAll();
		List<Pregunta> listaSalida = new ArrayList<Pregunta>();
		for (Pregunta pregunta : lista) 
		{	
			if(pregunta.getEncuesta().getId() == encuesta)
			{
				listaSalida.add(pregunta);
			}
			
		}
		
		
		return listaSalida;
	}
	
	public Page<Object[]> listar(int inicio) {
		Page<Object[]> page= encuentaRepositorio.listarEncuestasPaginado(PageRequest.of(inicio,10));
		return page;
	}
	
	public List<Object[]> listarEncuestas() {
		List<Object[]> page= encuentaRepositorio.listarEncuestas();
		return page;
	}
	
	public Pregunta buscarPregunta(Long id)
	{
		Optional<Pregunta> pregunta = preguntaRepositorio.findById(id);
		if(pregunta.isPresent())
		{
			return pregunta.get();
		}else
		{
			return null;
		}
	}
	
	public Opcion buscarOpcion(Long id)
	{
		Optional<Opcion> opcion = opcionRepositorio.findById(id);
		if(opcion.isPresent())
		{
			return opcion.get();
		}else
		{
			return null;
		}
	}
	
	public Pregunta guardarPreguntas(Pregunta pregunta)
	{
		pregunta.setId(null);
		return preguntaRepositorio.save(pregunta);
	}
	
	public void guardarOpciones(Opcion opcion)
	{
		opcion.setId(null);
		opcionRepositorio.save(opcion);
	}
	
	public void eliminarPregunta(Long id)
	{
		preguntaRepositorio.deleteById(id);
	}
	
	public void eliminarOpcion(Long id)
	{
		opcionRepositorio.deleteById(id);
	}
	
	public void editarPregunta(Pregunta pregunta)
	{
		List<Opcion> opcionesPregunta = opcionRepositorio.findByPregunta(pregunta.getId());
		List<Opcion> opciones = pregunta.getOpcionesPregunta();
		System.out.println("guardando pregunta : ");
		for (Opcion opcion : opcionesPregunta)
		{
			if(opciones.contains(opcion))
			{
				System.out.println("contenido");
				int indice = opciones.indexOf(opcion);
				Opcion opcionPregunta = opciones.get(indice);
				opcion.setDescripcion(opcionPregunta.getDescripcion());
				opcionRepositorio.save(opcion);
				opciones.remove(opcionPregunta);
			}else
			{
				System.out.println("no contenido");
				opcionRepositorio.delete(opcion);
			}
		}
		if(opciones.size()>0)
		{
			System.out.println("sobran");
			for (Opcion opcion2 : opciones)
			{
				System.out.println("guardando sobrante");
				opcion2.setId(null);
				opcion2.setPregunta(pregunta);
				opcionRepositorio.save(opcion2);
			}
		}else
		{
			System.out.println("no sobran");
		}
		preguntaRepositorio.save(pregunta);
	}
	
	public void editarOpcion(Opcion opcion)
	{
		opcionRepositorio.save(opcion);
	}
}
