package com.itefs.trexsas.servicio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.Cuenta;
import com.itefs.trexsas.modelo.Notificacion;
import com.itefs.trexsas.modelo.NotificacionIndividual;
import com.itefs.trexsas.modelo.NotificacionPerfil;
import com.itefs.trexsas.modelo.Perfil;
import com.itefs.trexsas.modelo.Persona;
import com.itefs.trexsas.repositorio.CuentaRepositorio;
import com.itefs.trexsas.repositorio.NotificacionIndividualRepositorio;
import com.itefs.trexsas.repositorio.NotificacionPerfilRepositorio;
import com.itefs.trexsas.repositorio.NotificacionRepository;
import com.itefs.trexsas.repositorio.PersonaRepositorio;
import com.itefs.trexsas.utilidades.Correo;

@Service
public class NotificacionServicio {

	@Autowired
	private NotificacionRepository notificacionRepository;
	
	@Autowired
	private NotificacionPerfilRepositorio notificacionPerfilRepositorio;
	
	@Autowired
	private NotificacionIndividualRepositorio notificacionIndividualRepositorio;
	
	@Autowired
	private PersonaRepositorio personaRepositorio;
	
	@Autowired
	private CuentaRepositorio cuentaRepositorio;
	
	public Notificacion crear(Notificacion notificacion)
	{
		notificacion.setId(null);
		return notificacionRepository.save(notificacion);
	}
	
	public void crearNotificacionPerfil(NotificacionPerfil notificacion)
	{
		notificacion.setId(null);
		NotificacionPerfil np = notificacionPerfilRepositorio.save(notificacion);
		System.out.println(np.getNotificacion());
	}
	
	public NotificacionPerfil traerNotificacionPerfil(long notificacion)
	{
		System.out.println("yes");
		NotificacionPerfil objetoNotificacion = notificacionPerfilRepositorio.findByNotificacion(notificacion);
		return objetoNotificacion;
		/*if(objetoNotificacion.isPresent())
		{
			System.out.println("si");
			return objetoNotificacion.get();
		}else
		{
			System.out.println("no");
			return null;
		}*/
	}
	
	public NotificacionIndividual traerNotificacionIndividual(long notificacion)
	{
		System.out.println("yes");
		NotificacionIndividual objetoNotificacion = notificacionIndividualRepositorio.findByIdNotificacion(notificacion);
		return objetoNotificacion;
		/*if(objetoNotificacion.isPresent())
		{
			System.out.println("si");
			return objetoNotificacion.get();
		}else
		{
			System.out.println("no");
			return null;
		}*/
	}
	
	public Notificacion crearIndividual(Notificacion notificacion)
	{
		notificacion.setId(null);
		return notificacionRepository.save(notificacion);
	}
	
	public String obtenerCorreoNotificacion(Long cuenta)
	{
		return notificacionRepository.correoUsuario(cuenta);
	}
	
	public String obtenerCorreoNotificacionCliente(Integer cuenta)
	{
		return notificacionRepository.correoCliente(cuenta);
	}
	
	
	public List<HashMap<String, String>> obtenerRemitentes(List<NotificacionPerfil> perfiles)
	{
		System.out.println("buscando correos");
		List<Cuenta> listaCuentas = cuentaRepositorio.findAll();
		List<HashMap<String, String>> correos = new ArrayList<HashMap<String, String>>();
		for (Cuenta cuenta : listaCuentas)
		{
			
			List<Perfil> perfilesCuenta = cuenta.getPerfilList();
			for (Perfil perfil:perfilesCuenta)
			{
				
				for(NotificacionPerfil perfilN: perfiles)
				{
					if(String.valueOf(perfil.getIdPerfil()).equals(String.valueOf(perfilN.getPerfil())))
					{
						System.out.println("cuenta : "+cuenta.getUsuarioCuenta());
						HashMap<String, String> remitente = new HashMap<>();
						if(cuenta.getPersona()!=null)
						{
							System.out.println("cuenta : "+cuenta.getUsuarioCuenta()+", perfil : "+perfil.getNombrePerfil()+", correo : "+cuenta.getPersona().getCorreoPersona());
							
							remitente.put("perfil", perfil.getNombrePerfil());
							remitente.put("cuenta", cuenta.getUsuarioCuenta());
							remitente.put("correo",cuenta.getPersona().getCorreoPersona());
							
						}else
						{
							System.out.println("cuenta sin correo : "+cuenta.getUsuarioCuenta()+", perfil : "+perfil.getNombrePerfil()+", correo: "+cuenta.getCliente().getCorreoCliente());
							
							remitente.put("perfil", perfil.getNombrePerfil());
							remitente.put("cuenta", cuenta.getUsuarioCuenta());
							remitente.put("correo",cuenta.getCliente().getCorreoCliente());
						}
						
						correos.add(remitente);
						
						break;
					}
				}
				
			}
		}
		
		
		return correos;
	}
	
	public void actualizarNotificacionPerfil(NotificacionPerfil notificacion)
	{
		System.out.println("3");
		notificacionPerfilRepositorio.save(notificacion);
	}
	
	public void actualizarNotificacionIndividual(NotificacionIndividual notificacion)
	{
		System.out.println("3");
		notificacionIndividualRepositorio.save(notificacion);
	}
	
	public void actualizar(Notificacion notificacion)
	{
		System.out.println("1 trayendo np anteriores");
		List<NotificacionPerfil> notificacionPerfilBD = notificacionPerfilRepositorio.buscarNotificaciones(notificacion.getId());
		System.out.println("2");
		List<NotificacionPerfil> notificacionesPerfilNuevo = notificacion.getPerfilesNotificacion();
		//System.out.println("4 "+notificacion==null);
		for (NotificacionPerfil notificacionPerfil : notificacionPerfilBD) 
		{
			System.out.println("3 "+notificacionPerfil.toString());
			boolean encontrado = false;
			
			for (NotificacionPerfil notificacionPerfil2 : notificacionesPerfilNuevo) 
			{
				System.out.println("4 "+notificacionPerfil2==null);
				if(notificacionPerfil.getId() == notificacionPerfil2.getId())
				{
					System.out.println("4.5 : ");
					notificacionPerfil.setPerfil(notificacionPerfil2.getPerfil());
					notificacionPerfil.setPersona(notificacionPerfil2.getPersona());
					notificacionPerfilRepositorio.save(notificacionPerfil);
					notificacionesPerfilNuevo.remove(notificacionPerfil2);
					encontrado=true;
					break;
				}
			}
			if(encontrado == false)
			{
				System.out.println("5");
				notificacionPerfilRepositorio.delete(notificacionPerfil);
			}
			
		}
		if(notificacionesPerfilNuevo.size()>0)
		{
			System.out.println("6");
			for (NotificacionPerfil notificacionPerfil : notificacionesPerfilNuevo)
			{
				System.out.println("7");
				notificacionPerfil.setNotificacion(notificacion);
				notificacionPerfilRepositorio.save(notificacionPerfil);
			}
		}
		notificacionRepository.save(notificacion);
	}
	
	public void eliminar(long id)
	{
		Optional<Notificacion> objetoNotificacion = notificacionRepository.findById(id);
		if(objetoNotificacion.isPresent())
		{
			Notificacion notificacion = objetoNotificacion.get();
			notificacion.setEstadoNotificacion(Long.valueOf(3));
			notificacionRepository.save(notificacion);
		}
		
	}
	
	public Notificacion obtenerPorId(long id)
	{
		Optional<Notificacion> objetoNotificacion = notificacionRepository.findById(id);
		if(objetoNotificacion.isPresent())
		{
			return objetoNotificacion.get();
		}else
		{
			return null;
		}
	}
	
	public Persona obtenerNotificacionPerfil(Long id)
	{
		System.out.println("buscar ");
		Optional<NotificacionPerfil> objetoNotificacion = Optional.of(notificacionPerfilRepositorio.findByNotificacion(id));
		if(objetoNotificacion.isPresent())
		{
			System.out.println("id notificacion encontrada :"+objetoNotificacion.get().toString());
			Optional<Persona> objetoPersona = personaRepositorio.findById(objetoNotificacion.get().getPersona());
			if(objetoPersona.isPresent())
			{
				return objetoPersona.get();
			}else
			{
				return null;
			}
			
		}else
		{
			System.out.println("buscar 2");
			return null;
		}
	}
	
	public Page<Object[]> listar(int inicio) {
		Page<Object[]> page= notificacionRepository.listarNotificacionPaginado(PageRequest.of(inicio,10));
		return page;
	}
	
	public List<HashMap<String, Object>> listarCapacitacionesFiltradas(int inicio, String criterio, String valor) {
		System.out.println("llamando listarPasajero");
		Page<Object[]> page= notificacionRepository.listarNotificacionPaginado(PageRequest.of(inicio, 10));
		List<HashMap<String, Object>> obs = new ArrayList<HashMap<String,Object>>();
		int indiceBusquedaArreglo = 0;
		if(criterio.equals("tp"))
		{
			indiceBusquedaArreglo = 3;
		}else if(criterio.equals("es"))
		{
			indiceBusquedaArreglo = 4;
		}
		System.out.println("indice "+indiceBusquedaArreglo);
		for (Object[] objects : page.getContent()) 
		{
			System.out.println("identificacion 2: "+objects[1].toString());
			System.out.println("identificacion 2: "+objects[1].toString().equals("2021-06-24"));
			//System.out.println("identificacion : "+objects[indiceBusquedaArreglo]);
			/*
			 * en caso de que la columna sea diferente a 7, buscar por estado, buscará haciendo
			 * la verificación si la factura se encuentra en 1, osea vigente
			 */
			if(indiceBusquedaArreglo!=4)
			{
				/*
				 *se verifica que la factura esté vigente, es decir, que
				 *tenga el estado en 1, si lo tiene en 2 no se tiene en cuenta a menos
				 */
				if(Integer.valueOf(objects[4].toString())!=2)
				{
					if(objects[indiceBusquedaArreglo].toString().equals(valor))
					{
						System.out.println("identificacion 1: "+objects[indiceBusquedaArreglo]);
						System.out.println("identificacion 2: "+objects[1].toString());
						HashMap<String, Object> ob = new HashMap<String, Object>();
						ob.put("idNotificacion", objects[0]);
						ob.put("tituloNotificacion", objects[1]);
						ob.put("descripcionNotificacion", objects[2]);
						ob.put("tipoNotificacion", objects[3]);
						ob.put("estadoNotificacion", objects[4]);
						obs.add(ob);
					}
				}
			}else
			{
				if(objects[indiceBusquedaArreglo].toString().equals(valor))
				{
					System.out.println("identificacion : "+objects[indiceBusquedaArreglo]);
					HashMap<String, Object> ob = new HashMap<String, Object>();
					ob.put("idNotificacion", objects[0]);
					ob.put("tituloNotificacion", objects[1]);
					ob.put("descripcionNotificacion", objects[2]);
					ob.put("tipoNotificacion", objects[3]);
					ob.put("estadoNotificacion", objects[4]);
					obs.add(ob);
				}
			}
			
			
		}
		return obs;
	}

	public NotificacionIndividual traerNotificacionIndividual(Long id) 
	{
		System.out.println("yes");
		NotificacionIndividual objetoNotificacion = notificacionIndividualRepositorio.findByIdNotificacion(id);
		return objetoNotificacion;
	}
	
	public void actualizarNotificacionIndividual2(NotificacionIndividual notificacion)
	{
		notificacionIndividualRepositorio.save(notificacion);
	}
}
