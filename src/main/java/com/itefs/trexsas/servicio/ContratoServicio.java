package com.itefs.trexsas.servicio;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.controlador.PasajeroControlador;
import com.itefs.trexsas.modelo.ConductorFuec;
import com.itefs.trexsas.modelo.Contrato;
import com.itefs.trexsas.modelo.Pasajero;
import com.itefs.trexsas.modelo.PasajeroContrato;
import com.itefs.trexsas.modelo.PersonaAutor;
import com.itefs.trexsas.repositorio.ContratoRepositorio;
import com.itefs.trexsas.repositorio.PasajeroContratoRepositorio;
import com.itefs.trexsas.repositorio.PasajeroRepositorio;

@Service
public class ContratoServicio {
	
	@Autowired
	private ContratoRepositorio contratoRepositorio;
	
	@Autowired
	private PasajeroContratoRepositorio pasajeroContratoRepositorio;
	
	@Autowired
	private PasajeroRepositorio pasajeroRepositorio;
	
	public void crear(Contrato contrato) {
		contrato.setIdContrato(null);
		contratoRepositorio.save(contrato);
	}
	
	@Async
	public void crearMasivos(List<Contrato> contratos, Long creador)
	{
		List<Contrato> lista = contratos;
		for (Contrato contrato : lista) 
		{
			PersonaAutor autor=new PersonaAutor();
			autor.setIdPersona(creador);
			int totales = this.totalContratos();
			contrato.setEstadoContrato(0);
			contrato.setNoContrato(totales+1);
			contrato.setConsecutivoContrato(Long.valueOf(contrato.getNumeracionContrato()));
			contrato.setContadorContrato(1);
			contratoRepositorio.save(contrato);
		}
	}
	
	public int totalContratos()
	{
		return (int) contratoRepositorio.count();
	}
	
	public void actualizar(Contrato contrato,List<Pasajero> pasajeros)
	{
		contratoRepositorio.save(contrato);
	}
	
	public void actualizar(Contrato contrato)
	{
		contratoRepositorio.save(contrato);
	}
	
	public void eliminarPasajeroContrato(Integer pasajero,Long contrato)
	{
		List<PasajeroContrato> lista = pasajeroContratoRepositorio.findAll();
		for (PasajeroContrato pasajero1 : lista)
		{
			if(pasajero1.getPasajero().getIdPasajero()==pasajero && pasajero1.getContrato() == contrato)
			{
				pasajeroContratoRepositorio.delete(pasajero1);
				break;
			}
		}
		
	}
	
	public void eliminarPasajeroContrato(Integer pasajero)
	{
		List<PasajeroContrato> lista = pasajeroContratoRepositorio.findAll();
		for (PasajeroContrato pasajero1 : lista)
		{
			if(pasajero1.getPasajero().getIdPasajero()==pasajero)
			{
				pasajeroContratoRepositorio.delete(pasajero1);
				break;
			}
		}
		
	}
	
	public Contrato obtenerPorId(long id) {
		Optional<Contrato> op=contratoRepositorio.findById(id);
		if(op.isPresent()) {
			
			return op.get();
		}
		return null;
	}
	
	public HashMap<String, Object> obtenerContratoPorId(long id) {
		Optional<Contrato> op=contratoRepositorio.findById(id);
		List<Pasajero> pasajeros = pasajeroRepositorio.findAll();
		
		if(op.isPresent()) {
			Contrato contrato = op.get();
			List<Pasajero> pasajerosContrato = contrato.getPasajeroList();
			List<Pasajero> pasajerosLibres = new ArrayList<Pasajero>();
			System.out.println("pasajeros libres : "+pasajeros.size());
			for (Pasajero pasajero : pasajeros) 
			{
				
				if(!pasajerosContrato.contains(pasajero))
				{
					pasajerosLibres.add(pasajero);
				}
			}
			HashMap<String, Object> salida = new HashMap<String, Object>();
			salida.put("contrato", contrato);
			salida.put("libres",pasajerosLibres);
			
			return salida;
		}
		return null;
	}
	
	public List<HashMap<String, Object>> listar() {
		List<Contrato> contratos = contratoRepositorio.findAll();
		List<HashMap<String, Object>> lista = new ArrayList<HashMap<String, Object>>();
		for(Contrato o:contratos) {
			System.out.println("es nulo "+o.getIdContrato());
			System.out.println("es nulo "+o.getCliente().getIdCliente()==null);
			HashMap<String, Object> objeto = new HashMap<String, Object>();
			objeto.put("idContrato", o.getIdContrato());
			objeto.put("numeracionContrato", o.getNumeracionContrato());
			objeto.put("estadoContrato", o.getEstadoContrato());
			objeto.put("valorContrato", o.getValorContrato());
			objeto.put("contadorContrato", o.getContadorContrato());
			objeto.put("consecutivo", o.getConsecutivoContrato());
			objeto.put("responsable", o.getResponsable());
			objeto.put("documentoResponsable", o.getDocumentoResponsable());
			objeto.put("telefonoResponsable", o.getTelefonoResponsable());
			objeto.put("objetoContrato", o.getObjetoContrato());
			objeto.put("fechaInicioContrato", o.getFechaInicioContrato());
			objeto.put("fechaFinContrato", o.getFechaFinContrato());
			objeto.put("tipoContrato", o.getTipoContrato());
			objeto.put("razonSocialCliente",o.getCliente()==null?"pendiete":o.getCliente().getRazonSocialCliente());
			objeto.put("nitCliente",o.getCliente()==null?"pendiete":o.getCliente().getNitCliente());
			objeto.put("ciudad",o.getCiudad()==null?"pendiente":o.getCiudad());
			objeto.put("idCliente",o.getCliente().getIdCliente());
			lista.add(objeto);
		}
		
		System.out.println("total registros "+contratos.size());
		List<Object[]> page= contratoRepositorio.listarContratosPaginado();
		return lista;
	}
	
	public List<Contrato> obtenerTodos() {
		return contratoRepositorio.findAll();
	}
	
	public void eliminar(Contrato contrato) {
		contratoRepositorio.delete(contrato);
	}
	
	public List<HashMap<String, Object>> traerPasajerosContrato(Long id)
	{
		List<PasajeroContrato> listaPasajerosContrato = pasajeroContratoRepositorio.listarPasajeros(id);
		List<HashMap<String, Object>> pasajeros = new ArrayList<>();
		for (PasajeroContrato pasajeroContrato : listaPasajerosContrato) 
		{
			HashMap<String, Object> pasajero = new HashMap<String, Object>();
			pasajero.put("idPasajero",pasajeroContrato.getId());
			pasajero.put("documentoPasajero", pasajeroContrato.getPasajero().getPersona().getDocumentoPersona());
			pasajero.put("nombrePasajero", pasajeroContrato.getPasajero().getPersona().getNombrePersona()+" "+pasajeroContrato.getPasajero().getPersona().getApellidoPersona());
			pasajeros.add(pasajero);
		}
		
		return pasajeros;
	}

}
