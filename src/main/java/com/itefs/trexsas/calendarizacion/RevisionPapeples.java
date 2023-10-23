package com.itefs.trexsas.calendarizacion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.itefs.trexsas.modelo.Conductor;
import com.itefs.trexsas.modelo.RevisionTecnicomecanica;
import com.itefs.trexsas.modelo.Vehiculo;
import com.itefs.trexsas.repositorio.ConductorRepositorio;
import com.itefs.trexsas.repositorio.PolizaContractualRepositorio;
import com.itefs.trexsas.repositorio.PolizaExtracontractualRepositorio;
import com.itefs.trexsas.repositorio.PolizaTodoRiesgoRepositorio;
import com.itefs.trexsas.repositorio.RevisionPreventivaRepositorio;
import com.itefs.trexsas.repositorio.RevisionTecnicomecanicaRepositorio;
import com.itefs.trexsas.repositorio.SoatRepositorio;
import com.itefs.trexsas.repositorio.TarjetaOperacionRepositorio;
import com.itefs.trexsas.repositorio.VehiculoRepositorio;
import com.itefs.trexsas.utilidades.Correo;

@Component
public class RevisionPapeples 
{
	
	@Autowired
	private ConductorRepositorio conductorRepositorio;
	
	@Autowired
	private RevisionTecnicomecanicaRepositorio revisionTecnicomecanicaRepositorio;
	
	@Autowired
	private RevisionPreventivaRepositorio revisionPreventivaRepositorio;
	
	@Autowired
	private PolizaContractualRepositorio polizaContractualRepositorio;
	
	@Autowired
	private PolizaExtracontractualRepositorio polizaExtracontractualRepositorio;
	
	@Autowired
	private PolizaTodoRiesgoRepositorio polizaTodoRiesgoRepositorio;
	
	@Autowired
	private TarjetaOperacionRepositorio tarjetaOperacionRepositorio;
	
	@Autowired
	private SoatRepositorio soatRepositorio;
	
	@Autowired
	private VehiculoRepositorio vehiculoRepositorio;
	
	@Autowired
	private Correo enviadorCorreo;
	
	
	@Scheduled(cron = "0 0 2 ? * 3 ")
	public void notificarRenovacionRevisionTecnicoMecanica() throws ParseException, AddressException, MessagingException
	{
		System.out.println("REVISIÓN TÉCNICO MECÁNICA : ");
		
		List<Object[]> lista = revisionTecnicomecanicaRepositorio.traerRevisionesTecnicoMecanicas();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fechaActual = java.time.LocalDate.now().toString();
		System.out.println("fecha de hoy "+fechaActual);
		Date hoy = sdf.parse(fechaActual);
		System.out.println("fecha de hoy "+hoy);
		
		for (Object[] revision : lista)
		{
			System.out.println();
		    System.out.println();
		    System.out.println();
		    System.out.println();
			Date fechaLimite = sdf.parse(revision[1].toString());
		    Calendar c = Calendar.getInstance();
		    c.setTime(fechaLimite);
		    c.add(Calendar.DATE, -15); 
		    Date currentDatePlusOne = c.getTime();
		    Date fechaPreviaAviso = currentDatePlusOne;
		    System.out.println("idVehiculo  "+revision[4]);
			System.out.println("Revisando plava  : "+revision[2]+" de propietario : "+revision[3]);
		    System.out.println("fecha de hoy "+hoy);
		    System.out.println("fecha vencimiento : " + sdf.format(fechaLimite));
		    System.out.println("fecha aviso inicio : " + sdf.format(fechaPreviaAviso));
		    if(hoy.after(fechaPreviaAviso) && hoy.before(fechaLimite))
		      {
		    	  System.out.println("a punto de vencerse : "+fechaActual);
		    	  enviadorCorreo.enviarCorreoAvisoPapelesVehiculo(revision[6].toString(), "REVISIÓN TECNICO MECANICA", revision[2].toString(), sdf.format(fechaLimite));
		      }else if(hoy.after(fechaLimite))
		      {
		    	  System.out.println("vencida : "+fechaActual);
		    	  if(Integer.valueOf(revision[5].toString())==1)
		    	  {
		    		  
		    		  Optional<Vehiculo> vehiculo= vehiculoRepositorio.findById(Integer.valueOf(revision[4].toString()));
		    		  if(vehiculo.isPresent())
		    		  {
		    			  Vehiculo vehiculoBloquear = vehiculo.get();
		    			  vehiculoBloquear.setEstadoVehiculo(0);
				    	  vehiculoRepositorio.save(vehiculoBloquear);
				    	  enviadorCorreo.enviarCorreoAvisoBloqueoVehiculoVencimientoPapeles(revision[6].toString(), "REVISIÓN TECNICO MECANICA", revision[2].toString());
		    		  }
			    	  
		    	  }else
		    	  {
		    		  System.out.println("vencida y bloqueado : ");
		    	  }
		    	  
		    	  
		      }else
		      {
		    	  System.out.println("a tiempo : "+fechaActual);
		      }
		    System.out.println();
		    System.out.println();
		    System.out.println();
		    System.out.println();
		}
	}
	
	
	@Scheduled(cron = "0 5 2 ? * 3 ")
	public void notificarRenovacionRevisionPreventiva() throws ParseException, AddressException, MessagingException
	{
		System.out.println("REVISIÓN PREVENTIVA : ");
		
		List<Object[]> lista = revisionPreventivaRepositorio.traerRevisionesPreventivas();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fechaActual = java.time.LocalDate.now().toString();
		System.out.println("fecha de hoy "+fechaActual);
		Date hoy = sdf.parse(fechaActual);
		System.out.println("fecha de hoy "+hoy);
		
		for (Object[] revision : lista)
		{
			System.out.println();
		    System.out.println();
		    System.out.println();
		    System.out.println();
			Date fechaLimite = sdf.parse(revision[1].toString());
		    Calendar c = Calendar.getInstance();
		    c.setTime(fechaLimite);
		    c.add(Calendar.DATE, -15); 
		    Date currentDatePlusOne = c.getTime();
		    Date fechaPreviaAviso = currentDatePlusOne;
		    System.out.println("idVehiculo  "+revision[4]);
			System.out.println("Revisando plava  : "+revision[2]+" de propietario : "+revision[3]);
		    System.out.println("fecha de hoy "+hoy);
		    System.out.println("fecha vencimiento : " + sdf.format(fechaLimite));
		    System.out.println("fecha aviso inicio : " + sdf.format(fechaPreviaAviso));
		    if(hoy.after(fechaPreviaAviso) && hoy.before(fechaLimite))
		      {
		    	  System.out.println("a punto de vencerse : "+fechaActual);
		    	  enviadorCorreo.enviarCorreoAvisoPapelesVehiculo(revision[6].toString(), "REVISIÓN PREVENTIVA", revision[2].toString(), sdf.format(fechaLimite));
		      }else if(hoy.after(fechaLimite))
		      {
		    	  System.out.println("vencida : "+fechaActual);
		    	  if(Integer.valueOf(revision[5].toString())==1)
		    	  {
		    		  
		    		  Optional<Vehiculo> vehiculo= vehiculoRepositorio.findById(Integer.valueOf(revision[4].toString()));
		    		  if(vehiculo.isPresent())
		    		  {
		    			  Vehiculo vehiculoBloquear = vehiculo.get();
		    			  vehiculoBloquear.setEstadoVehiculo(0);
				    	  vehiculoRepositorio.save(vehiculoBloquear);
				    	  enviadorCorreo.enviarCorreoAvisoBloqueoVehiculoVencimientoPapeles(revision[6].toString(), "REVISIÓN PREVENTIVA", revision[2].toString());
		    		  }
			    	  
		    	  }else
		    	  {
		    		  System.out.println("vencida y bloqueado : ");
		    	  }
		    	  
		    	  
		      }else
		      {
		    	  System.out.println("a tiempo : "+fechaActual);
		      }
		    System.out.println();
		    System.out.println();
		    System.out.println();
		    System.out.println();
		}
	}
	
	@Scheduled(cron = "0 10 2 ? * 3 ")
	public void notificarRenovacionPolizaContractual() throws ParseException, AddressException, MessagingException
	{
		System.out.println("PÓLIZAS CONTRACTUALES : ");
		
		List<Object[]> lista = polizaContractualRepositorio.traerPolizasContractuales();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fechaActual = java.time.LocalDate.now().toString();
		System.out.println("fecha de hoy "+fechaActual);
		Date hoy = sdf.parse(fechaActual);
		System.out.println("fecha de hoy "+hoy);
		
		for (Object[] poliza : lista)
		{
			System.out.println();
		    System.out.println();
		    System.out.println();
		    System.out.println();
			Date fechaLimite = sdf.parse(poliza[1].toString());
		    Calendar c = Calendar.getInstance();
		    c.setTime(fechaLimite);
		    c.add(Calendar.DATE, -15); 
		    Date currentDatePlusOne = c.getTime();
		    Date fechaPreviaAviso = currentDatePlusOne;
		    System.out.println("idVehiculo  "+poliza[4]);
			System.out.println("Revisando plava  : "+poliza[2]+" de propietario : "+poliza[3]);
		    System.out.println("fecha de hoy "+hoy);
		    System.out.println("fecha vencimiento : " + sdf.format(fechaLimite));
		    System.out.println("fecha aviso inicio : " + sdf.format(fechaPreviaAviso));
		    if(hoy.after(fechaPreviaAviso) && hoy.before(fechaLimite))
		      {
		    	  System.out.println("a punto de vencerse : "+fechaActual);
		    	  enviadorCorreo.enviarCorreoAvisoPapelesVehiculo(poliza[6].toString(), "PÓLIZA CONTRACTUAL", poliza[2].toString(), sdf.format(fechaLimite));
		      }else if(hoy.after(fechaLimite))
		      {
		    	  System.out.println("vencida : "+fechaActual);
		    	  if(Integer.valueOf(poliza[5].toString())==1)
		    	  {
		    		  
		    		  Optional<Vehiculo> vehiculo= vehiculoRepositorio.findById(Integer.valueOf(poliza[4].toString()));
		    		  if(vehiculo.isPresent())
		    		  {
		    			  Vehiculo vehiculoBloquear = vehiculo.get();
		    			  vehiculoBloquear.setEstadoVehiculo(0);
				    	  vehiculoRepositorio.save(vehiculoBloquear);
				    	  enviadorCorreo.enviarCorreoAvisoBloqueoVehiculoVencimientoPapeles(poliza[6].toString(), "PÓLIZA CONTRACTUAL", poliza[2].toString());
		    		  }
			    	  
		    	  }else
		    	  {
		    		  System.out.println("vencida y bloqueado : ");
		    	  }
		    	  
		    	  
		      }else
		      {
		    	  System.out.println("a tiempo : "+fechaActual);
		      }
		    System.out.println();
		    System.out.println();
		    System.out.println();
		    System.out.println();
		}
	}
	
	@Scheduled(cron = "0 15 2 ? * 3 ")
	public void notificarRenovacionPolizaTodoRiesgo() throws ParseException, AddressException, MessagingException
	{
		System.out.println("PÓLIZAS TODO RIESGO : ");
		List<Object[]> lista = polizaTodoRiesgoRepositorio.traerPolizasExtraContractuales();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fechaActual = java.time.LocalDate.now().toString();
		System.out.println("fecha de hoy "+fechaActual);
		Date hoy = sdf.parse(fechaActual);
		System.out.println("fecha de hoy "+hoy);
		
		for (Object[] poliza : lista)
		{
			System.out.println();
		    System.out.println();
		    System.out.println();
		    System.out.println();
			Date fechaLimite = sdf.parse(poliza[1].toString());
		    Calendar c = Calendar.getInstance();
		    c.setTime(fechaLimite);
		    c.add(Calendar.DATE, -15); 
		    Date currentDatePlusOne = c.getTime();
		    Date fechaPreviaAviso = currentDatePlusOne;
		    System.out.println("idVehiculo  "+poliza[4]);
			System.out.println("Revisando plava  : "+poliza[2]+" de propietario : "+poliza[3]);
		    System.out.println("fecha de hoy "+hoy);
		    System.out.println("fecha vencimiento : " + sdf.format(fechaLimite));
		    System.out.println("fecha aviso inicio : " + sdf.format(fechaPreviaAviso));
		    if(hoy.after(fechaPreviaAviso) && hoy.before(fechaLimite))
		      {
		    	  System.out.println("a punto de vencerse : "+fechaActual);
		    	  enviadorCorreo.enviarCorreoAvisoPapelesVehiculo(poliza[6].toString(), "PÓLIZA TODO RIESGO", poliza[2].toString(), sdf.format(fechaLimite));
		      }else if(hoy.after(fechaLimite))
		      {
		    	  System.out.println("vencida : "+fechaActual);
		    	  if(Integer.valueOf(poliza[5].toString())==1)
		    	  {
		    		  
		    		  Optional<Vehiculo> vehiculo= vehiculoRepositorio.findById(Integer.valueOf(poliza[4].toString()));
		    		  if(vehiculo.isPresent())
		    		  {
		    			  Vehiculo vehiculoBloquear = vehiculo.get();
		    			  vehiculoBloquear.setEstadoVehiculo(0);
				    	  vehiculoRepositorio.save(vehiculoBloquear);
				    	  enviadorCorreo.enviarCorreoAvisoBloqueoVehiculoVencimientoPapeles(poliza[6].toString(), "PÓLIZA TODO RIESGO", poliza[2].toString());
		    		  }
			    	  
		    	  }else
		    	  {
		    		  System.out.println("vencida y bloqueado : ");
		    	  }
		    	  
		    	  
		      }else
		      {
		    	  System.out.println("a tiempo : "+fechaActual);
		      }
		    System.out.println();
		    System.out.println();
		    System.out.println();
		    System.out.println();
		}
	}
	
	@Scheduled(cron = "0 20 2 ? * 3 ")
	public void notificarRenovacionPolizaExtraContractual() throws ParseException, AddressException, MessagingException
	{
		System.out.println("PÓLIZAS EXTRA CONTRACTUALES : ");
		List<Object[]> lista = polizaExtracontractualRepositorio.traerPolizasExtraContractuales();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fechaActual = java.time.LocalDate.now().toString();
		System.out.println("fecha de hoy "+fechaActual);
		Date hoy = sdf.parse(fechaActual);
		System.out.println("fecha de hoy "+hoy);
		
		for (Object[] poliza : lista)
		{
			System.out.println();
		    System.out.println();
		    System.out.println();
		    System.out.println();
			Date fechaLimite = sdf.parse(poliza[1].toString());
		    Calendar c = Calendar.getInstance();
		    c.setTime(fechaLimite);
		    c.add(Calendar.DATE, -15); 
		    Date currentDatePlusOne = c.getTime();
		    Date fechaPreviaAviso = currentDatePlusOne;
		    System.out.println("idVehiculo  "+poliza[4]);
			System.out.println("Revisando plava  : "+poliza[2]+" de propietario : "+poliza[3]);
		    System.out.println("fecha de hoy "+hoy);
		    System.out.println("fecha vencimiento : " + sdf.format(fechaLimite));
		    System.out.println("fecha aviso inicio : " + sdf.format(fechaPreviaAviso));
		    if(hoy.after(fechaPreviaAviso) && hoy.before(fechaLimite))
		      {
		    	  System.out.println("a punto de vencerse : "+fechaActual);
		    	  enviadorCorreo.enviarCorreoAvisoPapelesVehiculo(poliza[6].toString(), "PÓLIZA EXTRA CONTRACTUAL", poliza[2].toString(), sdf.format(fechaLimite));
		      }else if(hoy.after(fechaLimite))
		      {
		    	  System.out.println("vencida : "+fechaActual);
		    	  if(Integer.valueOf(poliza[5].toString())==1)
		    	  {
		    		  
		    		  Optional<Vehiculo> vehiculo= vehiculoRepositorio.findById(Integer.valueOf(poliza[4].toString()));
		    		  if(vehiculo.isPresent())
		    		  {
		    			  Vehiculo vehiculoBloquear = vehiculo.get();
		    			  vehiculoBloquear.setEstadoVehiculo(0);
				    	  vehiculoRepositorio.save(vehiculoBloquear);
				    	  enviadorCorreo.enviarCorreoAvisoBloqueoVehiculoVencimientoPapeles(poliza[6].toString(), "PÓLIZA EXTRA CONTRACTUAL", poliza[2].toString());
		    		  }
			    	  
		    	  }else
		    	  {
		    		  System.out.println("vencida y bloqueado : ");
		    	  }
		    	  
		    	  
		      }else
		      {
		    	  System.out.println("a tiempo : "+fechaActual);
		      }
		    System.out.println();
		    System.out.println();
		    System.out.println();
		    System.out.println();
		}
	}
	
	
	@Scheduled(cron = "0 25 2 ? * 3 ")
	public void notificarRenovacionSoat() throws ParseException, AddressException, MessagingException
	{
		System.out.println("SOATS : ");
		List<Object[]> lista = soatRepositorio.traerSoats();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fechaActual = java.time.LocalDate.now().toString();
		System.out.println("fecha de hoy "+fechaActual);
		Date hoy = sdf.parse(fechaActual);
		System.out.println("fecha de hoy "+hoy);
		
		for (Object[] soat : lista)
		{
			System.out.println();
		    System.out.println();
		    System.out.println();
		    System.out.println();
			Date fechaLimite = sdf.parse(soat[1].toString());
		    Calendar c = Calendar.getInstance();
		    c.setTime(fechaLimite);
		    c.add(Calendar.DATE, -15); 
		    Date currentDatePlusOne = c.getTime();
		    Date fechaPreviaAviso = currentDatePlusOne;
		    System.out.println("idVehiculo  "+soat[4]);
			System.out.println("Revisando plava  : "+soat[2]+" de propietario : "+soat[3]);
		    System.out.println("fecha de hoy "+hoy);
		    System.out.println("fecha vencimiento : " + sdf.format(fechaLimite));
		    System.out.println("fecha aviso inicio : " + sdf.format(fechaPreviaAviso));
		    if(hoy.after(fechaPreviaAviso) && hoy.before(fechaLimite))
		      {
		    	  System.out.println("a punto de vencerse : "+fechaActual);
		    	  enviadorCorreo.enviarCorreoAvisoPapelesVehiculo(soat[6].toString(), " SOAT ", soat[2].toString(), sdf.format(fechaLimite));
		      }else if(hoy.after(fechaLimite))
		      {
		    	  System.out.println("vencida : "+fechaActual);
		    	  if(Integer.valueOf(soat[5].toString())==1)
		    	  {
		    		  
		    		  Optional<Vehiculo> vehiculo= vehiculoRepositorio.findById(Integer.valueOf(soat[4].toString()));
		    		  if(vehiculo.isPresent())
		    		  {
		    			  Vehiculo vehiculoBloquear = vehiculo.get();
		    			  vehiculoBloquear.setEstadoVehiculo(0);
				    	  vehiculoRepositorio.save(vehiculoBloquear);
				    	  enviadorCorreo.enviarCorreoAvisoBloqueoVehiculoVencimientoPapeles(soat[6].toString(), " SOAT ", soat[2].toString());
		    		  }
			    	  
		    	  }else
		    	  {
		    		  System.out.println("vencida y bloqueado : ");
		    	  }
		    	  
		    	  
		      }else
		      {
		    	  System.out.println("a tiempo : "+fechaActual);
		      }
		    System.out.println();
		    System.out.println();
		    System.out.println();
		    System.out.println();
		}
	}
	
	@Scheduled(cron = "0 30 2 ? * 3 ")
	public void notificarRenovacionTarjetaOperacion() throws ParseException, AddressException, MessagingException
	{
		System.out.println("TARJETAS DE OPERACIÓN: ");
		List<Object[]> lista = tarjetaOperacionRepositorio.traerTarjetasOperacion();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fechaActual = java.time.LocalDate.now().toString();
		System.out.println("fecha de hoy "+fechaActual);
		Date hoy = sdf.parse(fechaActual);
		System.out.println("fecha de hoy "+hoy);
		
		for (Object[] tarjeta : lista)
		{
			System.out.println();
		    System.out.println();
		    System.out.println();
		    System.out.println();
			Date fechaLimite = sdf.parse(tarjeta[1].toString());
		    Calendar c = Calendar.getInstance();
		    c.setTime(fechaLimite);
		    c.add(Calendar.DATE, -60); 
		    Date currentDatePlusOne = c.getTime();
		    Date fechaPreviaAviso = currentDatePlusOne;
		    System.out.println("idVehiculo  "+tarjeta[3]);
			System.out.println("Revisando plava  : "+tarjeta[2]+" de propietario : "+tarjeta[3]);
		    System.out.println("fecha de hoy "+hoy);
		    System.out.println("fecha vencimiento : " + sdf.format(fechaLimite));
		    System.out.println("fecha aviso inicio : " + sdf.format(fechaPreviaAviso));
		    if(hoy.after(fechaPreviaAviso) && hoy.before(fechaLimite))
		      {
		    	  System.out.println("a punto de vencerse : "+fechaActual);
		    	  enviadorCorreo.enviarCorreoAvisoPapelesVehiculo(tarjeta[5].toString(), " TARJETA DE OPERACIÓN ", tarjeta[6].toString(), sdf.format(fechaLimite));
		      }else if(hoy.after(fechaLimite))
		      {
		    	  System.out.println("vencida : "+fechaActual);
		    	  if(Integer.valueOf(tarjeta[4].toString())==1)
		    	  {
		    		  
		    		  Optional<Vehiculo> vehiculo= vehiculoRepositorio.findById(Integer.valueOf(tarjeta[2].toString()));
		    		  if(vehiculo.isPresent())
		    		  {
		    			  Vehiculo vehiculoBloquear = vehiculo.get();
		    			  vehiculoBloquear.setEstadoVehiculo(0);
				    	  vehiculoRepositorio.save(vehiculoBloquear);
				    	  enviadorCorreo.enviarCorreoAvisoBloqueoVehiculoVencimientoPapeles(tarjeta[5].toString(), " TARJETA DE OPERACIÓN ", tarjeta[6].toString());
		    		  }
			    	  
		    	  }else
		    	  {
		    		  System.out.println("vencida y bloqueado : ");
		    	  }
		    	  
		    	  
		      }else
		      {
		    	  System.out.println("a tiempo : "+fechaActual);
		      }
		    System.out.println();
		    System.out.println();
		    System.out.println();
		    System.out.println();
		}
	}
	
	
	@Scheduled(cron = "0 35 2 ? * 3 ")
	//@Scheduled(fixedRate = 5000)
	public void notificarRenovacionArl() throws ParseException, AddressException, MessagingException
	{
		System.out.println("ARL´S: ");
		List<Object[]> lista = conductorRepositorio.traerARLConductores();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fechaActual = java.time.LocalDate.now().toString();
		System.out.println("fecha de hoy "+fechaActual);
		Date hoy = sdf.parse(fechaActual);
		System.out.println("fecha de hoy "+hoy);
		System.out.println("cantidad objetos  "+lista.size());
		for (Object[] conductor : lista)
		{
			System.out.println("idConductor : "+conductor[0].toString());
			System.out.println("fecha : "+conductor[2].toString());
			System.out.println();
		    System.out.println();
		    System.out.println();
		    System.out.println();
			Date fechaLimite = sdf.parse(conductor[1].toString());
		    Calendar c = Calendar.getInstance();
		    c.setTime(fechaLimite);
		    c.add(Calendar.DATE, -2); 
		    Date currentDatePlusOne = c.getTime();
		    Date fechaPreviaAviso = currentDatePlusOne;
		    System.out.println("idVehiculo  "+conductor[4]);
			System.out.println("Revisando plava  : "+conductor[2]+" de propietario : "+conductor[3]);
		    System.out.println("fecha de hoy "+hoy);
		    System.out.println("fecha vencimiento : " + sdf.format(fechaLimite));
		    System.out.println("fecha aviso inicio : " + sdf.format(fechaPreviaAviso));
		    if(hoy.after(fechaPreviaAviso) && hoy.before(fechaLimite))
		      {
		    	  System.out.println("a punto de vencerse : "+fechaActual);
		    	  enviadorCorreo.enviarCorreoAvisoPapelesVehiculo(conductor[4].toString(), " ARL ", conductor[2].toString(), sdf.format(fechaLimite));
		      }else if(hoy.after(fechaLimite))
		      {
		    	  System.out.println("vencida : "+fechaActual);
		    	  if(Integer.valueOf(conductor[5].toString())==1)
		    	  {
		    		  
		    		  Optional<Conductor> conductorPorBloquear= conductorRepositorio.findById(Integer.valueOf(conductor[0].toString()));
		    		  if(conductorPorBloquear.isPresent())
		    		  {
		    			  Conductor conductorBloquear = conductorPorBloquear.get();
		    			  conductorBloquear.setEstadoConductor(0);
				    	  conductorRepositorio.save(conductorBloquear);
				    	  enviadorCorreo.enviarCorreoAvisoBloqueoVehiculoVencimientoPapeles(conductor[4].toString(), " ARL ", conductor[1].toString());
		    		  }
			    	  
		    	  }else
		    	  {
		    		  System.out.println("vencida y bloqueado : ");
		    	  }
		    	  
		    	  
		      }else
		      {
		    	  System.out.println("a tiempo : "+fechaActual);
		      }
		    System.out.println();
		    System.out.println();
		    System.out.println();
		    System.out.println();
		}
	}
	
	
	@Scheduled(cron = "0 40 2 ? * 3 ")
	//@Scheduled(fixedRate = 5000)
	public void notificarRenovacionEPS() throws ParseException, AddressException, MessagingException
	{
		System.out.println("EPS´S: ");
		List<Object[]> lista = conductorRepositorio.traerEPSConductores();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fechaActual = java.time.LocalDate.now().toString();
		System.out.println("fecha de hoy "+fechaActual);
		Date hoy = sdf.parse(fechaActual);
		System.out.println("fecha de hoy "+hoy);
		System.out.println("cantidad objetos : "+lista.size());
		
		for (Object[] conductor : lista)
		{
			
			System.out.println("idConductor : "+conductor[0].toString());
			System.out.println("fecha : "+conductor[2].toString());
			System.out.println();
		    System.out.println();
		    System.out.println();
		    System.out.println();
			Date fechaLimite = sdf.parse(conductor[1].toString());
		    Calendar c = Calendar.getInstance();
		    c.setTime(fechaLimite);
		    c.add(Calendar.DATE, -2); 
		    Date currentDatePlusOne = c.getTime();
		    Date fechaPreviaAviso = currentDatePlusOne;
		    System.out.println("idVehiculo  "+conductor[4]);
			System.out.println("Revisando plava  : "+conductor[2]+" de propietario : "+conductor[3]);
		    System.out.println("fecha de hoy "+hoy);
		    System.out.println("fecha vencimiento : " + sdf.format(fechaLimite));
		    System.out.println("fecha aviso inicio : " + sdf.format(fechaPreviaAviso));
		    if(hoy.after(fechaPreviaAviso) && hoy.before(fechaLimite))
		      {
		    	  System.out.println("a punto de vencerse : "+fechaActual);
		    	  enviadorCorreo.enviarCorreoAvisoPapelesVehiculo(conductor[4].toString(), " EPS ", conductor[2].toString(), sdf.format(fechaLimite));
		      }else if(hoy.after(fechaLimite))
		      {
		    	  System.out.println("vencida : "+fechaActual);
		    	  if(Integer.valueOf(conductor[5].toString())==1)
		    	  {
		    		  
		    		  Optional<Conductor> conductorPorBloquear= conductorRepositorio.findById(Integer.valueOf(conductor[0].toString()));
		    		  if(conductorPorBloquear.isPresent())
		    		  {
		    			  Conductor conductorBloquear = conductorPorBloquear.get();
		    			  conductorBloquear.setEstadoConductor(0);
				    	  conductorRepositorio.save(conductorBloquear);
				    	  enviadorCorreo.enviarCorreoAvisoBloqueoVehiculoVencimientoPapeles(conductor[4].toString(), " EPS ", conductor[1].toString());
		    		  }
			    	  
		    	  }else
		    	  {
		    		  System.out.println("vencida y bloqueado : ");
		    	  }
		    	  
		    	  
		      }else
		      {
		    	  System.out.println("a tiempo : "+fechaActual);
		      }
		    System.out.println();
		    System.out.println();
		    System.out.println();
		    System.out.println();
		}
	}
	
	@Scheduled(cron = "0 45 2 ? * 3 ")
	//@Scheduled(fixedRate = 5000)
	public void notificarRenovacionLicencia() throws ParseException, AddressException, MessagingException
	{
		System.out.println("LICENCIAS : ");
		List<Object[]> lista = conductorRepositorio.traerLicenciaConductores();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fechaActual = java.time.LocalDate.now().toString();
		System.out.println("fecha de hoy "+fechaActual);
		Date hoy = sdf.parse(fechaActual);
		System.out.println("fecha de hoy "+hoy);
		
		for (Object[] conductor : lista)
		{
			System.out.println();
		    System.out.println();
		    System.out.println();
		    System.out.println();
			Date fechaLimite = sdf.parse(conductor[1].toString());
		    Calendar c = Calendar.getInstance();
		    c.setTime(fechaLimite);
		    c.add(Calendar.DATE, -15); 
		    Date currentDatePlusOne = c.getTime();
		    Date fechaPreviaAviso = currentDatePlusOne;
		    System.out.println("idVehiculo  "+conductor[2]);
			System.out.println("Revisando plava  : "+conductor[2]);
		    System.out.println("fecha de hoy "+hoy);
		    System.out.println("fecha vencimiento : " + sdf.format(fechaLimite));
		    System.out.println("fecha aviso inicio : " + sdf.format(fechaPreviaAviso));
		    System.out.println("CORREO  : " + conductor[4]);
		    if(hoy.after(fechaPreviaAviso) && hoy.before(fechaLimite))
		      {
		    	  System.out.println("a punto de vencerse : "+fechaActual);
		    	  enviadorCorreo.enviarCorreoAvisoPapelesVehiculo(conductor[4].toString(), " LICENCIA ", conductor[2].toString(), sdf.format(fechaLimite));
		      }else if(hoy.after(fechaLimite))
		      {
		    	  System.out.println("vencida : "+fechaActual);
		    	  if(Integer.valueOf(conductor[3].toString())==1)
		    	  {
		    		  
		    		  Optional<Conductor> conductorPorBloquear= conductorRepositorio.findById(Integer.valueOf(conductor[0].toString()));
		    		  if(conductorPorBloquear.isPresent())
		    		  {
		    			  Conductor conductorBloquear = conductorPorBloquear.get();
		    			  conductorBloquear.setEstadoConductor(0);
				    	  conductorRepositorio.save(conductorBloquear);
				    	  enviadorCorreo.enviarCorreoAvisoBloqueoVehiculoVencimientoLicencia(conductor[4].toString(), " LICENCIA ");
		    		  }
			    	  
		    	  }else
		    	  {
		    		  System.out.println("vencida y bloqueado : ");
		    	  }
		    	  
		    	  
		      }else
		      {
		    	  System.out.println("a tiempo : "+fechaActual);
		      }
		    System.out.println();
		    System.out.println();
		    System.out.println();
		    System.out.println();
		}
	}
}
