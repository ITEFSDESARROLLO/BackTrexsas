package com.itefs.trexsas.servicio;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.ConductorFuec;
import com.itefs.trexsas.modelo.Fuec;
import com.itefs.trexsas.modelo.PasajeroFuec;
import com.itefs.trexsas.repositorio.ConductorFuecRepositorio;
import com.itefs.trexsas.repositorio.FuecRepositorio;
import com.itefs.trexsas.repositorio.PasajeroFuecRepositorio;

@Service
public class FuecServicio {
	
	@Autowired
	private FuecRepositorio fuecRepositorio;
	
	@Autowired
	private ConductorServicio conductorServicio;
	
	@Autowired
	private ConductorFuecRepositorio conductorFuecRepositorio;
	
	@Autowired
	private PasajeroFuecRepositorio pasajeroFuecRepositorio;
	
	
	public void crear(Fuec fuec) {
		fuec.setIdFuec(null);
		fuecRepositorio.save(fuec);
	}
	
	public void actualizar(Fuec fuec) {
		fuecRepositorio.save(fuec);
	}
	
	public Object[] obtenerPorId(long id) {
		Optional<Fuec> op=fuecRepositorio.findById(id);
		if(op.isPresent())
		{
			Object[] ob = {op.get().getIdFuec(),
						   op.get().getContrato(),
						   op.get().getContrato().getNumeracionContrato(),
						   op.get().getContrato().getCliente().getRazonSocialCliente(),
						   op.get().getContrato().getObjetoContrato(),
						   op.get().getCiudadOrigen(),
						   op.get().getCiudadDestino(),
						   op.get().getVehiculo(),
						   op.get().getFechaInicioFuec(),
						   op.get().getFechaFinFuec(),
						   op.get().getCodigoFuec(),
						   op.get().getCupoMaximo(),
						   op.get().getCupoDisponible()};
			return ob;
		}
		return null;
	}
	
	public Fuec traerPorId(long id) {
		Optional<Fuec> op=fuecRepositorio.findById(id);
		if(op.isPresent())
		{
			return op.get();
		}
		return null;
	}
	
	public void eliminarConductorFuec(Integer conductor,Long fuec)
	{
		List<ConductorFuec> lista = conductorFuecRepositorio.findAll();
		for (ConductorFuec conductorFuec : lista)
		{
			if(conductorFuec.getFuec() == fuec && conductorFuec.getConductor() == conductor)
			{
				conductorFuecRepositorio.delete(conductorFuec);
				break;
			}
		}
		
	}
	
	public void eliminarConductorFuec(Integer conductor)
	{
		List<ConductorFuec> lista = conductorFuecRepositorio.findAll();
		for (ConductorFuec conductorFuec : lista)
		{
			if(conductorFuec.getConductor() == conductor)
			{
				conductorFuecRepositorio.delete(conductorFuec);
				break;
			}
		}
		
	}
	
	public void eliminarPasajeroFuec(Integer pasajero,Long fuec)
	{
		List<PasajeroFuec> lista = pasajeroFuecRepositorio.findAll();
		for (PasajeroFuec pasajeroFuec : lista)
		{
			if(pasajeroFuec.getFuec() == fuec && pasajeroFuec.getPasajero().getIdPasajero() == pasajero)
			{
				pasajeroFuecRepositorio.delete(pasajeroFuec);
				break;
			}
		}
		
	}
	
	public List<HashMap<String, Object>> listaPasajeroFuec(Long fuec)
	{
		List<PasajeroFuec> lista = pasajeroFuecRepositorio.findAll();
		List<HashMap<String, Object>> pasajeros = new ArrayList<HashMap<String,Object>>();
		for (PasajeroFuec pasajeroFuec : lista)
		{
			if(pasajeroFuec.getFuec()==fuec)
			{
				HashMap<String, Object> pasajero = new HashMap<String, Object>();
				pasajero.put("idPasajero", pasajeroFuec.getPasajero().getIdPasajero());
				pasajero.put("nombrePasajero",pasajeroFuec.getPasajero().getPersona().getNombrePersona()+" "+pasajeroFuec.getPasajero().getPersona().getApellidoPersona());
				pasajero.put("documentoPasajero",pasajeroFuec.getPasajero().getPersona().getDocumentoPersona());
				pasajeros.add(pasajero);
			}
		}
		return pasajeros;
	}
	
	public Page<Object[]> listar(int inicio) {
		Page<Object[]> page= fuecRepositorio.listarPaginado(PageRequest.of(inicio, 10));
		return page;
	}
	
	/*public List<Object[]> pdf(Long id) {
		List<Object[]> consulta1=fuecRepositorio.datosPdf(id);
		if(consulta1.size()>0)
			return consulta1;
		return fuecRepositorio.datosPdf2(id);
	}*/
	
	public List<Fuec> obtenerTodos() {
		return fuecRepositorio.findAll();
	}
	
	public void eliminar(Fuec fuec) {
		fuecRepositorio.delete(fuec);
	}
	
	public HashMap<String, Object> listarPorContrato(int inicio,String contrato)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
			System.out.println("id propietario : ");
			Page<Object[]> page= fuecRepositorio.listarPorContrato(PageRequest.of(inicio, 10),contrato);
			System.out.println("tamaño  : "+page.getSize());
			List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
			for(Object[] o:page.getContent()) {
				List<HashMap<String, Object>> Conductores = new ArrayList<HashMap<String, Object>>();
				List<Object[]> listaConductores=conductorServicio.conductoresDeFuec((Long)o[0]);
				for(Object[] ob:listaConductores) {
					HashMap<String, Object> conductor = new HashMap<String, Object>();
					conductor.put("nombrePersona", ob[0]);
					conductor.put("apellidoPersona", ob[1]);
					Conductores.add(conductor);
				}
				HashMap<String, Object> objeto = new HashMap<String, Object>();
				objeto.put("idFuec", o[0]);
				if(o[1]!=null) {
					objeto.put("numeracionContrato", o[1]);
					objeto.put("razonSocialCliente", o[2]);
					objeto.put("objetoContrato", o[3]);
				}else {
					objeto.put("numeracionContrato", o[4]);
					objeto.put("razonSocialCliente", o[5]);
					objeto.put("objetoContrato", o[6]);
				}
				objeto.put("codigoRuta", o[7]);
				objeto.put("ciudadOrigen", o[8]);
				objeto.put("ciudadDestino", o[9]);
				objeto.put("placaVehiculo", o[10]);
				objeto.put("fechaInicioFuec", o[11]);
				objeto.put("fechaFinFuec", o[12]);
				objeto.put("codigoFuec", o[13]);
				objeto.put("fuec", o[14]);
				objeto.put("conductorList", Conductores);
				lista.add(objeto);
			}
			response.put("totalResultados", page.getTotalElements());
			response.put("totalPaginas", page.getTotalPages());
			response.put("fuecs", lista);
		return response;
	}
	
	public HashMap<String, Object> listarPorFuec(int inicio,String fuec)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
			System.out.println("id propietario : ");
			Page<Object[]> page= fuecRepositorio.listarPorCodigoFuec(PageRequest.of(inicio, 10),fuec);
			System.out.println("tamaño  : "+page.getSize());
			List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
			for(Object[] o:page.getContent()) {
				List<HashMap<String, Object>> Conductores = new ArrayList<HashMap<String, Object>>();
				List<Object[]> listaConductores=conductorServicio.conductoresDeFuec((Long)o[0]);
				for(Object[] ob:listaConductores) {
					HashMap<String, Object> conductor = new HashMap<String, Object>();
					conductor.put("nombrePersona", ob[0]);
					conductor.put("apellidoPersona", ob[1]);
					Conductores.add(conductor);
				}
				HashMap<String, Object> objeto = new HashMap<String, Object>();
				objeto.put("idFuec", o[0]);
				if(o[1]!=null) {
					objeto.put("numeracionContrato", o[1]);
					objeto.put("razonSocialCliente", o[2]);
					objeto.put("objetoContrato", o[3]);
				}else {
					objeto.put("numeracionContrato", o[4]);
					objeto.put("razonSocialCliente", o[5]);
					objeto.put("objetoContrato", o[6]);
				}
				objeto.put("codigoRuta", o[7]);
				objeto.put("ciudadOrigen", o[8]);
				objeto.put("ciudadDestino", o[9]);
				objeto.put("placaVehiculo", o[10]);
				objeto.put("fechaInicioFuec", o[11]);
				objeto.put("fechaFinFuec", o[12]);
				objeto.put("codigoFuec", o[13]);
				objeto.put("fuec", o[14]);
				objeto.put("conductorList", Conductores);
				lista.add(objeto);
			}
			response.put("totalResultados", page.getTotalElements());
			response.put("totalPaginas", page.getTotalPages());
			response.put("fuecs", lista);
		return response;
	}
	
	public HashMap<String, Object> listarPorVehiculo(int inicio,String placa)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
			System.out.println("id propietario : ");
			Page<Object[]> page= fuecRepositorio.listarPorPlaca(PageRequest.of(inicio, 10),placa);
			System.out.println("tamaño  : "+page.getSize());
			List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
			for(Object[] o:page.getContent()) {
				List<HashMap<String, Object>> Conductores = new ArrayList<HashMap<String, Object>>();
				List<Object[]> listaConductores=conductorServicio.conductoresDeFuec((Long)o[0]);
				for(Object[] ob:listaConductores) {
					HashMap<String, Object> conductor = new HashMap<String, Object>();
					conductor.put("nombrePersona", ob[0]);
					conductor.put("apellidoPersona", ob[1]);
					Conductores.add(conductor);
				}
				HashMap<String, Object> objeto = new HashMap<String, Object>();
				objeto.put("idFuec", o[0]);
				if(o[1]!=null) {
					objeto.put("numeracionContrato", o[1]);
					objeto.put("razonSocialCliente", o[2]);
					objeto.put("objetoContrato", o[3]);
				}else {
					objeto.put("numeracionContrato", o[4]);
					objeto.put("razonSocialCliente", o[5]);
					objeto.put("objetoContrato", o[6]);
				}
				objeto.put("codigoRuta", o[7]);
				objeto.put("ciudadOrigen", o[8]);
				objeto.put("ciudadDestino", o[9]);
				objeto.put("placaVehiculo", o[10]);
				objeto.put("fechaInicioFuec", o[11]);
				objeto.put("fechaFinFuec", o[12]);
				objeto.put("codigoFuec", o[13]);
				objeto.put("fuec", o[14]);
				objeto.put("conductorList", Conductores);
				lista.add(objeto);
			}
			response.put("totalResultados", page.getTotalElements());
			response.put("totalPaginas", page.getTotalPages());
			response.put("fuecs", lista);
		return response;
	}
	
	public HashMap<String, Object> listarPorRuta(int inicio,String origen, String destino)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
			System.out.println("id propietario : ");
			Page<Object[]> page= fuecRepositorio.listarPorRuta(PageRequest.of(inicio, 10),origen,destino);
			System.out.println("tamaño  : "+page.getSize());
			List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
			for(Object[] o:page.getContent()) {
				List<HashMap<String, Object>> Conductores = new ArrayList<HashMap<String, Object>>();
				List<Object[]> listaConductores=conductorServicio.conductoresDeFuec((Long)o[0]);
				for(Object[] ob:listaConductores) {
					HashMap<String, Object> conductor = new HashMap<String, Object>();
					conductor.put("nombrePersona", ob[0]);
					conductor.put("apellidoPersona", ob[1]);
					Conductores.add(conductor);
				}
				HashMap<String, Object> objeto = new HashMap<String, Object>();
				objeto.put("idFuec", o[0]);
				if(o[1]!=null) {
					objeto.put("numeracionContrato", o[1]);
					objeto.put("razonSocialCliente", o[2]);
					objeto.put("objetoContrato", o[3]);
				}else {
					objeto.put("numeracionContrato", o[4]);
					objeto.put("razonSocialCliente", o[5]);
					objeto.put("objetoContrato", o[6]);
				}
				objeto.put("codigoRuta", o[7]);
				objeto.put("ciudadOrigen", o[8]);
				objeto.put("ciudadDestino", o[9]);
				objeto.put("placaVehiculo", o[10]);
				objeto.put("fechaInicioFuec", o[11]);
				objeto.put("fechaFinFuec", o[12]);
				objeto.put("codigoFuec", o[13]);
				objeto.put("fuec", o[14]);
				objeto.put("conductorList", Conductores);
				lista.add(objeto);
			}
			response.put("totalResultados", page.getTotalElements());
			response.put("totalPaginas", page.getTotalPages());
			response.put("fuecs", lista);
		return response;
	}

}
