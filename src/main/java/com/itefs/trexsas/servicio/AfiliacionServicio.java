package com.itefs.trexsas.servicio;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.Afiliacion;
import com.itefs.trexsas.repositorio.AfiliacionRepositorio;

@Service
public class AfiliacionServicio {
	
	@Autowired
	private AfiliacionRepositorio afiliacionRepositorio;
	//Metodo para crear una nueva vinculación
	public void crear(Afiliacion afiliacion) {
		Short na=0;
		afiliacion.setIdAfiliacion(null);
		//pendiente=0
		//aprobada=1
		//en revision=2
		afiliacion.setEstadoAfiliacion(0);
		afiliacion.setNotificarAfiliacion(na);
		afiliacionRepositorio.save(afiliacion);
	}
	
	public void actualizar(Afiliacion afiliacion) {
		afiliacionRepositorio.save(afiliacion);
	}
	
	public List<Object[]> obtenerEstado(Long id) {
		return afiliacionRepositorio.obtenerEstadoAfiliacion(id);
	}
	
	/*public Page<Object[]> listar(int inicio) {
		Page<Object[]> page= afiliacionRepositorio.listarUsuariosAfiliados(PageRequest.of(inicio, 10));
		return page;
	}*/
	
	public List<Object[]> listarAfiliados() {
		List<Object[]> list= afiliacionRepositorio.listarUsuariosAfiliados();
		return list;
	}
	
	/*public List<HashMap<String, Object>> listarUsuariosAfiliados() 
	{
		Page<Object[]> page= afiliacionRepositorio.listarUsuariosAfiliados();
		List<HashMap<String, Object>> salida = new ArrayList<>();
		for(Object[] array:page.getContent()) {
			HashMap<String, Object> afiliacion = new HashMap<String, Object>();
			afiliacion.put("idPersona", array[0]);
			afiliacion.put("documentoPersona", array[1]);
			afiliacion.put("nombrePersona", array[2]);
			afiliacion.put("apellidoPersona", array[3]);
			afiliacion.put("direccionPersona", array[4]);
			afiliacion.put("idperfil", array[5]);
			salida.add(afiliacion);
		}
		return salida;
	}*/
	
	
	public Afiliacion obtenerPorId(long id) {
		Optional<Afiliacion> opAfiliacion=afiliacionRepositorio.findById(id);
		if(opAfiliacion.isPresent()) {
			return opAfiliacion.get();
		}
		return null;
	}
	
	/*public HashMap<String,Object> listarPorPlaca(int inicio, String placa) 
	{
		System.out.println("buscando por voucher");
		//long total = reservaRepositorio.count();
		//Page<Object[]> page= reservaRepositorio.listarReservasPaginadoVoucher(PageRequest.of(inicio, (int)total));
		Page<Object[]> page= afiliacionRepositorio.listarPorPlaca(PageRequest.of(inicio, 0),placa);
		System.out.println(page.getSize());
		List<HashMap<String, Object>> obs = new ArrayList<HashMap<String,Object>>();
		HashMap<String, Object> response = new HashMap<String, Object>();
		for (Object[] objects : page.getContent()) 
		{
			System.out.println("encontrado : "+objects[9].toString());
			if(objects[9].equals(pl))
			{
				System.out.println("encontrado : "+objects[9].toString());
				HashMap<String, Object> ob = new HashMap<String, Object>();
				ob.put("idReserva", objects[0]);
				ob.put("nombrePasajero", objects[1]);
				ob.put("apellidoPasajero", objects[2]);
				ob.put("fechaInicioReserva", objects[3]);
				ob.put("direccionOrigenReserva", objects[4]);
				ob.put("direccionDestinoReserva", objects[5]);
				ob.put("distanciaReserva", objects[6]);
				ob.put("duracionReserva", objects[7]);
				ob.put("estadoReserva", objects[8]);
				ob.put("numeroVoucher", objects[9]);
				ob.put("nombreConductor", objects[10]);
				ob.put("apellidoConductor", objects[11]);
				ob.put("placaVehiculo", objects[12]);
				ob.put("idOrdenServicio", objects[13]);
				obs.add(ob);
			}
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("reservas",obs);
		return response;
	}*/
	
	public HashMap<String, Object> listarPorPlaca(int inicio, String placa) 
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Page<Object[]> page= afiliacionRepositorio.listarPorPlaca(PageRequest.of(inicio, 10),placa);
		List<HashMap<String, Object>> afiliaciones = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> afiliacion = new HashMap<String, Object>();
			afiliacion.put("idAfiliacion", o[0]);
			afiliacion.put("placaAfiliacion", o[1]);
			afiliacion.put("claseVehiculoAfiliacion", o[2]);
			afiliacion.put("documentoAfiliacion", o[3]);
			afiliacion.put("nombreAfiliacion", o[4]);
			afiliacion.put("apellidoAfiliacion", o[5]);
			afiliacion.put("estadoAfiliacion", o[6]);
			afiliaciones.add(afiliacion);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("afiliaciones", afiliaciones);
		return response;
	}
	
	public HashMap<String, Object> listarPorTipoVehiculo(int inicio, String tipoVehiculo) 
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Page<Object[]> page= afiliacionRepositorio.listarPorTipoVehiculo(PageRequest.of(inicio, 10),Integer.valueOf(tipoVehiculo));
		List<HashMap<String, Object>> afiliaciones = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> afiliacion = new HashMap<String, Object>();
			afiliacion.put("idAfiliacion", o[0]);
			afiliacion.put("placaAfiliacion", o[1]);
			afiliacion.put("claseVehiculoAfiliacion", o[2]);
			afiliacion.put("documentoAfiliacion", o[3]);
			afiliacion.put("nombreAfiliacion", o[4]);
			afiliacion.put("apellidoAfiliacion", o[5]);
			afiliacion.put("estadoAfiliacion", o[6]);
			afiliaciones.add(afiliacion);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("afiliaciones", afiliaciones);
		return response;
	}
	
	public HashMap<String, Object> listarPorDocumentoPersona(int inicio, String documentoPersona) 
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		//Persona persona = personaRepositorio.findByDocumentoPersona(documentoPersona);
		Page<Object[]> page= afiliacionRepositorio.listarPorDocumentoPropietario(PageRequest.of(inicio, 10),documentoPersona);
		List<HashMap<String, Object>> afiliaciones = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> afiliacion = new HashMap<String, Object>();
			afiliacion.put("idAfiliacion", o[0]);
			afiliacion.put("placaAfiliacion", o[1]);
			afiliacion.put("claseVehiculoAfiliacion", o[2]);
			afiliacion.put("documentoAfiliacion", o[3]);
			afiliacion.put("nombreAfiliacion", o[4]);
			afiliacion.put("apellidoAfiliacion", o[5]);
			afiliacion.put("estadoAfiliacion", o[6]);
			afiliaciones.add(afiliacion);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("afiliaciones", afiliaciones);
		//.put("persona", persona);
		return response;
	}
	
	public HashMap<String, Object> listarPorNombrePersona(int inicio, String nombrePersona) 
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		//List<Persona> persona = personaRepositorio.findAll();
		List<Afiliacion> afiliaciones = afiliacionRepositorio.findAll();
		String nombrePersonaObtenida = "";
		String documentoPersona = "";
		for (Afiliacion afiliacion: afiliaciones)
		{
			nombrePersonaObtenida = afiliacion.getNombreAfiliacion()+" "+afiliacion.getApellidoAfiliacion();
			System.out.println(nombrePersonaObtenida);
			if(nombrePersonaObtenida.equals(nombrePersona))
			{
				System.out.println("encontrado : "+nombrePersonaObtenida);
				documentoPersona = afiliacion.getDocumentoAfiliacion();
				System.out.println("documento : "+documentoPersona);
				break;
			}
		}
		if(!documentoPersona.equals(""))
		{
			Page<Object[]> page= afiliacionRepositorio.listarPorDocumentoPropietario(PageRequest.of(inicio, 10),documentoPersona);
			List<HashMap<String, Object>> afiliaciones2 = new ArrayList<HashMap<String, Object>>();
			for(Object[] o:page.getContent()) {
				HashMap<String, Object> afiliacion = new HashMap<String, Object>();
				afiliacion.put("idAfiliacion", o[0]);
				afiliacion.put("placaAfiliacion", o[1]);
				afiliacion.put("claseVehiculoAfiliacion", o[2]);
				afiliacion.put("documentoAfiliacion", o[3]);
				afiliacion.put("nombreAfiliacion", o[4]);
				afiliacion.put("apellidoAfiliacion", o[5]);
				afiliacion.put("estadoAfiliacion", o[6]);
				afiliaciones2.add(afiliacion);
			}
			response.put("totalResultados", page.getTotalElements());
			response.put("totalPaginas", page.getTotalPages());
			response.put("afiliaciones", afiliaciones2);
		}else
		{
			response.put("mensaje","error");
		}
		
		//.put("persona", persona);
		return response;
	}
	
	public HashMap<String, Object> listarPorEstado(int inicio, String estado) 
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		//Persona persona = personaRepositorio.findByDocumentoPersona(documentoPersona);
		Page<Object[]> page= afiliacionRepositorio.listarPorEstado(PageRequest.of(inicio, 10),Integer.valueOf(estado));
		List<HashMap<String, Object>> afiliaciones = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> afiliacion = new HashMap<String, Object>();
			afiliacion.put("idAfiliacion", o[0]);
			afiliacion.put("placaAfiliacion", o[1]);
			afiliacion.put("claseVehiculoAfiliacion", o[2]);
			afiliacion.put("documentoAfiliacion", o[3]);
			afiliacion.put("nombreAfiliacion", o[4]);
			afiliacion.put("apellidoAfiliacion", o[5]);
			afiliacion.put("estadoAfiliacion", o[6]);
			afiliaciones.add(afiliacion);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("afiliaciones", afiliaciones);
		//.put("persona", persona);
		return response;
	}
	

	

}
