package com.itefs.trexsas.servicio;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.Conductor;
import com.itefs.trexsas.modelo.Persona;
import com.itefs.trexsas.repositorio.ConductorRepositorio;
import com.itefs.trexsas.repositorio.PersonaRepositorio;

@Service
public class ConductorServicio {
	
	@Autowired
	private ConductorRepositorio conductorRepositorio;
	
	@Autowired
	private PersonaRepositorio personaRepositorio;
	
	public void crear(Conductor conductor) {
		conductor.setIdConductor(null);
		conductorRepositorio.save(conductor);
	}
	
	public Conductor crearConductor(Conductor conductor) {
		conductor.setIdConductor(null);
		return conductorRepositorio.save(conductor);
	}
	
	public Conductor crearMasivo(Conductor conductor) {
		conductor.setIdConductor(null);
		return conductorRepositorio.save(conductor);
	}
	
	public void actualizar(Conductor conductor) {
		conductorRepositorio.save(conductor);
	}
	
	public List<Object[]> obtenerTodos() {
		return conductorRepositorio.obtenerConductoresNotificacion();
	}
	
	public Page<Object[]> listar(int inicio) {
		Page<Object[]> page= conductorRepositorio.listarConductoresPaginadoPer(PageRequest.of(inicio, 10));
		return page;
	}
	
	public Conductor obtenerPorId(int id) {
		Optional<Conductor> opConductor=conductorRepositorio.findById(id);
		if(opConductor.isPresent()) {
			return opConductor.get();
		}
		return null;
	}
	
	public void eliminar(Conductor conductor) {
		conductorRepositorio.delete(conductor);
	}
	
	public List<Object[]> listarPersonasNoConductores() {
		List<Object[]> list= conductorRepositorio.listarPersonasNoConductores();
		return list;
	}
	
	public List<Object[]> conductoresDeFuec(Long id) {
		List<Object[]> list= conductorRepositorio.conductoresDeFuec(id);
		return list;
	}
	
	public List<Object[]> infoBasicaDeVehiculo(int id) {
		return conductorRepositorio.infoBasicaDeVehiculo(id);
	}
	
	public List<Object[]> infoBasica() {
		List<Object[]> list= conductorRepositorio.obtenerInfoBasicaTodos();
		return list;
	}
	
	public List<Object[]> correoConductor(int id) {
		List<Object[]> opCorreo=conductorRepositorio.correoConductor(id);
		return opCorreo;
	}
	
	public HashMap<String, Object> listarPorTelefono(int inicio,String telefono)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
			System.out.println("id propietario : ");
			Page<Object[]> page= conductorRepositorio.listarPorTelefono(PageRequest.of(inicio, 10),telefono);
			System.out.println("tamaño  : "+page.getSize());
			List<HashMap<String, Object>> conductores = new ArrayList<HashMap<String, Object>>();
			for(Object[] o:page.getContent()) {
				HashMap<String, Object> conductor = new HashMap<String, Object>();
				conductor.put("idConductor", o[0]);
				conductor.put("documentoPersona", o[1]);
				conductor.put("nombrePersona", o[2]);
				conductor.put("apellidoPersona", o[3]);
				conductor.put("estadoConductor", o[4]);
				conductor.put("telefonoPersona", o[5]);
				conductor.put("correoPersona", o[6]);
				conductores.add(conductor);
			}
			response.put("totalResultados", page.getTotalElements());
			response.put("totalPaginas", page.getTotalPages());
			response.put("conductores", conductores);
		return response;
	}
	
	public HashMap<String, Object> listarPorEstado(int inicio,String estado)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
			System.out.println("id propietario : ");
			Page<Object[]> page= conductorRepositorio.listarPorEstado(PageRequest.of(inicio, 10),Integer.valueOf(estado));
			System.out.println("tamaño  : "+page.getSize());
			List<HashMap<String, Object>> conductores = new ArrayList<HashMap<String, Object>>();
			for(Object[] o:page.getContent()) {
				HashMap<String, Object> conductor = new HashMap<String, Object>();
				conductor.put("idConductor", o[0]);
				conductor.put("documentoPersona", o[1]);
				conductor.put("nombrePersona", o[2]);
				conductor.put("apellidoPersona", o[3]);
				conductor.put("estadoConductor", o[4]);
				conductor.put("telefonoPersona", o[5]);
				conductor.put("correoPersona", o[6]);
				conductores.add(conductor);
			}
			response.put("totalResultados", page.getTotalElements());
			response.put("totalPaginas", page.getTotalPages());
			response.put("conductores", conductores);
		return response;
	}
	
	public HashMap<String, Object> listarPorDocumento(int inicio,String documento)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Persona persona = personaRepositorio.findByDocumentoPersona(documento);
		if(persona!=null)
		{
			
			System.out.println("id propietario : "+persona.getDocumentoPersona());
			Page<Object[]> page= conductorRepositorio.listarPorDocumento(PageRequest.of(inicio, 10),persona.getDocumentoPersona());
			System.out.println("tamaño  : "+page.getSize());
			List<HashMap<String, Object>> conductores = new ArrayList<HashMap<String, Object>>();
			for(Object[] o:page.getContent()) {
				HashMap<String, Object> conductor = new HashMap<String, Object>();
				conductor.put("idConductor", o[0]);
				conductor.put("documentoPersona", o[1]);
				conductor.put("nombrePersona", o[2]);
				conductor.put("apellidoPersona", o[3]);
				conductor.put("estadoConductor", o[4]);
				conductor.put("telefonoPersona", o[5]);
				conductor.put("correoPersona", o[6]);
				conductores.add(conductor);
			}
			response.put("totalResultados", page.getTotalElements());
			response.put("totalPaginas", page.getTotalPages());
			response.put("conductores", conductores);
		}else
		{
			response.put("mensaje","error");
		}
		return response;
	}
	
	public HashMap<String, Object> listarPorCorreo(int inicio,String correo)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Page<Object[]> page= conductorRepositorio.listarPorCorreo(PageRequest.of(inicio, 10),correo);
		if(page!=null)
		{
			System.out.println("id propietario : ");
			System.out.println("tamaño  : "+page.getSize());
			List<HashMap<String, Object>> conductores = new ArrayList<HashMap<String, Object>>();
			for(Object[] o:page.getContent()) {
				HashMap<String, Object> conductor = new HashMap<String, Object>();
				conductor.put("idConductor", o[0]);
				conductor.put("documentoPersona", o[1]);
				conductor.put("nombrePersona", o[2]);
				conductor.put("apellidoPersona", o[3]);
				conductor.put("estadoConductor", o[4]);
				conductor.put("telefonoPersona", o[5]);
				conductor.put("correoPersona", o[6]);
				conductores.add(conductor);
			}
			response.put("totalResultados", page.getTotalElements());
			response.put("totalPaginas", page.getTotalPages());
			response.put("conductores", conductores);
		}else
		{
			response.put("mensaje","error");
		}
		return response;
	}

	public HashMap<String, Object> listarPorNombre(int inicio,String nombre)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		List<Persona> personas = personaRepositorio.findAll();
		String nombrePersonaObtenida = "";
		String documentoPersona = "";
		System.out.println("tamaño "+personas.size());
		for (Persona persona: personas)
		{
			nombrePersonaObtenida = persona.getNombrePersona()+" "+persona.getApellidoPersona();
			System.out.println("persona : "+nombrePersonaObtenida);
			System.out.println("persona : "+nombrePersonaObtenida.equals(nombre));
			;
			System.out.println(nombrePersonaObtenida);
			if(nombrePersonaObtenida.toUpperCase().equals(nombre.toUpperCase()))
			{
				System.out.println("encontrado : "+nombrePersonaObtenida);
				documentoPersona = persona.getDocumentoPersona();
				System.out.println("documento : "+documentoPersona);
				break;
			}
		}
		if(documentoPersona!="")
		{
			
			System.out.println("id propietario : ");
			Page<Object[]> page= conductorRepositorio.listarPorDocumento(PageRequest.of(inicio, 10),documentoPersona);
			System.out.println("tamaño  : "+page.getSize());
			List<HashMap<String, Object>> conductores = new ArrayList<HashMap<String, Object>>();
			for(Object[] o:page.getContent()) {
				HashMap<String, Object> conductor = new HashMap<String, Object>();
				conductor.put("idConductor", o[0]);
				conductor.put("documentoPersona", o[1]);
				conductor.put("nombrePersona", o[2]);
				conductor.put("apellidoPersona", o[3]);
				conductor.put("estadoConductor", o[4]);
				conductor.put("telefonoPersona", o[5]);
				conductor.put("correoPersona", o[6]);
				conductores.add(conductor);
			}
			response.put("totalResultados", page.getTotalElements());
			response.put("totalPaginas", page.getTotalPages());
			response.put("conductores", conductores);
		}else
		{
			response.put("mensaje","error");
		}
		return response;
	}
	
	public List<Conductor> buscarTodos()
	{
		return conductorRepositorio.findAll();
	}
	
	public Conductor obtenerConductorPorPersona(Long id)
	{
		Conductor conductor = conductorRepositorio.obtenerConductorPorPersona(id);
		//System.out.println("conductor a devolver : "+conductor.toString());
		return conductor;
	}
}
