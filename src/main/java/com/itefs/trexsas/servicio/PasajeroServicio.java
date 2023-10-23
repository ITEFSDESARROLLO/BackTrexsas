package com.itefs.trexsas.servicio;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.Pasajero;
import com.itefs.trexsas.repositorio.PasajeroRepositorio;

@Service
public class PasajeroServicio {
	
	@Autowired
	private PasajeroRepositorio pasajeroRepositorio;
	
	public void crear(Pasajero pasajero) {
		pasajero.setIdPasajero(null);
		pasajeroRepositorio.save(pasajero);
	}
	
	public void eliminarPasajero(Long idPersona)
	{
		Pasajero pasajero = pasajeroRepositorio.traerPasajeroPorPersona(idPersona);
		pasajeroRepositorio.delete(pasajero);
	}
	
	public void eliminarPasajero(Pasajero pasajero)
	{
		pasajeroRepositorio.delete(pasajero);
	}
	
	public Pasajero traerPasajeroPorPersona(Long idPersona)
	{
		Pasajero pasajero = pasajeroRepositorio.traerPasajeroPorPersona(idPersona);
		return pasajero;
	}
	
	public void actualizar(Pasajero pasajero) {
		pasajeroRepositorio.save(pasajero);
	}
	
	public List<Object[]> obtenerTodos()
	{
		return pasajeroRepositorio.listarPasajerosNotificacion();
	}
	
	public Page<Object[]> listar(int inicio,int id) {
		Page<Object[]> page= pasajeroRepositorio.listarPasajerosPaginado(PageRequest.of(inicio, 10),id);
		return page;
	}
	
	public List<Object[]> listarPersonasNoPasajeros() {
		List<Object[]> list= pasajeroRepositorio.listarPersonasNoPasajeros();
		return list;
	}
	
	public List<Pasajero> infoBasica() {
		List<Pasajero> lista = pasajeroRepositorio.findAll();
		//List<Object[]> lista = pasajeroRepositorio.infoBasica();
		//List<HashMap<String, Object>> personas = new ArrayList<HashMap<String,Object>>();
		//List<Pasajero> personas = new ArrayList<HashMap<String,Object>>();
		///for (Object[] objects : lista) 
		/*{
			HashMap<String, Object> persona = new HashMap<String, Object>();
			persona.put("idPasajero", objects[0]);
			persona.put("documentoPersona",objects[1]);
			persona.put("nombrePersona", objects[2]);
			persona.put("apellidoPersona", objects[3]);
			personas.add(persona1);
		}*/
		return lista;
	}
	
	public Pasajero obtenerPorId(int id) {
		Optional<Pasajero> opPropietario=pasajeroRepositorio.findById(id);
		if(opPropietario.isPresent()) {
			return opPropietario.get();
		}
		return null;
	}
	
	public List<Object[]> correoPasajero(int id) {
		List<Object[]> opCorreo=pasajeroRepositorio.correoPasajero(id);
		return opCorreo;
	}
	
	/*public List<Object[]> infoBasicaDeContrato(long id) {
		return pasajeroRepositorio.infoBasicaDeContrato(id);
	}*/

}
