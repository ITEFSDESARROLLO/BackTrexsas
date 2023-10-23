package com.itefs.trexsas.servicio;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.Persona;
import com.itefs.trexsas.modelo.Propietario;
import com.itefs.trexsas.repositorio.PersonaRepositorio;
import com.itefs.trexsas.repositorio.PropietarioRepositorio;

@Service
public class PropietarioServicio {
	
	@Autowired
	private PropietarioRepositorio propietarioRepositorio;
	
	@Autowired
	private PersonaRepositorio personaRepositorio;
	
	public void crear(Propietario propietario) {
		propietario.setIdPropietario(null);
		propietarioRepositorio.save(propietario);
	}
	
	public void actualizar(Propietario propietario) {
		propietarioRepositorio.save(propietario);
	}
	
	
	public Page<Object[]> listar(int inicio) {
		Page<Object[]> page= propietarioRepositorio.listarPropietariosPaginado(PageRequest.of(inicio, 10));
		return page;
	}
	
	public List<Object[]> listarPersonasNoPropietarios() {
		List<Object[]> list= propietarioRepositorio.listarPersonasNoPropietarios();
		return list;
	}
	
	public List<Object[]> listarPropietariosVehiculo() {
		List<Object[]> list= propietarioRepositorio.listarPropietariosVehiculo();
		return list;
	}
	
	public Propietario obtenerPorId(Long id) {
		Optional<Propietario> opPropietario=propietarioRepositorio.findById(id);
		if(opPropietario.isPresent()) {
			return opPropietario.get();
		}
		return null;
	}
	
	public List<Object[]> obtenerTodos() {
		return propietarioRepositorio.listarPropietariosNotificaciones();
	}
	
	public void eliminar(Propietario propietario) {
		propietarioRepositorio.delete(propietario);
	}
	
	public HashMap<String, Object> listarPorNombre(int inicio,String nombre)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		List<Persona> personas = personaRepositorio.findAll();
		String nombrePersonaObtenida = "";
		Long documentoPersona = Long.valueOf("0");
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
				documentoPersona = persona.getIdPersona();
				System.out.println("documento : "+documentoPersona);
				break;
			}
		}
		if(documentoPersona!=Long.valueOf("0"))
		{
			
			System.out.println("id propietario : ");
			Page<Object[]> page= propietarioRepositorio.listarPorNombre(PageRequest.of(inicio, 10),documentoPersona);
			System.out.println("tamaño  : "+page.getSize());
			List<HashMap<String, Object>> propietarios = new ArrayList<HashMap<String, Object>>();
			for(Object[] o:page.getContent()) {
				HashMap<String, Object> propietario = new HashMap<String, Object>();
				propietario.put("idPropietario", o[0]);
				propietario.put("documentoPersona", o[1]);
				propietario.put("nombrePersona", o[2]);
				propietario.put("apellidoPersona", o[3]);
				propietario.put("estadoPropietario", o[4]);
				propietario.put("telefonoPersona", o[5]);
				propietarios.add(propietario);
			}
			response.put("totalResultados", page.getTotalElements());
			response.put("totalPaginas", page.getTotalPages());
			response.put("propietarios", propietarios);
		}else
		{
			response.put("mensaje","error");
		}
		return response;
	}
	
	public HashMap<String, Object> listarPorDocumento(int inicio,String documento)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Persona persona = personaRepositorio.findByDocumentoPersona(documento);
		if(persona!=null)
		{
			
			System.out.println("id propietario : ");
			Page<Object[]> page= propietarioRepositorio.listarPorNombre(PageRequest.of(inicio, 10),persona.getIdPersona());
			System.out.println("tamaño  : "+page.getSize());
			List<HashMap<String, Object>> propietarios = new ArrayList<HashMap<String, Object>>();
			for(Object[] o:page.getContent()) {
				HashMap<String, Object> propietario = new HashMap<String, Object>();
				propietario.put("idPropietario", o[0]);
				propietario.put("documentoPersona", o[1]);
				propietario.put("nombrePersona", o[2]);
				propietario.put("apellidoPersona", o[3]);
				propietario.put("estadoPropietario", o[4]);
				propietario.put("telefonoPersona", o[5]);
				propietarios.add(propietario);
			}
			response.put("totalResultados", page.getTotalElements());
			response.put("totalPaginas", page.getTotalPages());
			response.put("propietarios", propietarios);
		}else
		{
			response.put("mensaje","error");
		}
		return response;
	}
	
	public HashMap<String, Object> listarPorEstado(int inicio,String estado)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
			System.out.println("id propietario : ");
			Page<Object[]> page= propietarioRepositorio.listarPorEstado(PageRequest.of(inicio, 10),Integer.valueOf(estado));
			System.out.println("tamaño  : "+page.getSize());
			List<HashMap<String, Object>> propietarios = new ArrayList<HashMap<String, Object>>();
			for(Object[] o:page.getContent()) {
				HashMap<String, Object> propietario = new HashMap<String, Object>();
				propietario.put("idPropietario", o[0]);
				propietario.put("documentoPersona", o[1]);
				propietario.put("nombrePersona", o[2]);
				propietario.put("apellidoPersona", o[3]);
				propietario.put("estadoPropietario", o[4]);
				propietario.put("telefonoPersona", o[5]);
				propietarios.add(propietario);
			}
			response.put("totalResultados", page.getTotalElements());
			response.put("totalPaginas", page.getTotalPages());
			response.put("propietarios", propietarios);
		
		return response;
	}
	
	public HashMap<String, Object> listarPorTelefono(int inicio,String telefono)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
			System.out.println("id propietario : ");
			Page<Object[]> page= propietarioRepositorio.listarPorTelefono(PageRequest.of(inicio, 10),telefono);
			System.out.println("tamaño  : "+page.getSize());
			List<HashMap<String, Object>> propietarios = new ArrayList<HashMap<String, Object>>();
			for(Object[] o:page.getContent()) {
				HashMap<String, Object> propietario = new HashMap<String, Object>();
				propietario.put("idPropietario", o[0]);
				propietario.put("documentoPersona", o[1]);
				propietario.put("nombrePersona", o[2]);
				propietario.put("apellidoPersona", o[3]);
				propietario.put("estadoPropietario", o[4]);
				propietario.put("telefonoPersona", o[5]);
				propietarios.add(propietario);
			}
			response.put("totalResultados", page.getTotalElements());
			response.put("totalPaginas", page.getTotalPages());
			response.put("propietarios", propietarios);
		
		return response;
	}
	
	

}
