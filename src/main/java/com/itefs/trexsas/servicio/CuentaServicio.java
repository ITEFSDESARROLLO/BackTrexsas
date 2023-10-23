package com.itefs.trexsas.servicio;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.Conductor;
import com.itefs.trexsas.modelo.Cuenta;
import com.itefs.trexsas.modelo.CuentaPerfil;
import com.itefs.trexsas.modelo.Licencia;
import com.itefs.trexsas.modelo.POJOCuenta;
import com.itefs.trexsas.modelo.Perfil;
import com.itefs.trexsas.modelo.Persona;
import com.itefs.trexsas.repositorio.ConductorRepositorio;
import com.itefs.trexsas.repositorio.CuentaPerfilRepositorio;
import com.itefs.trexsas.repositorio.CuentaRepositorio;
import com.itefs.trexsas.repositorio.LicenciaRepositorio;
import com.itefs.trexsas.repositorio.PerfilRepositorio;
import com.itefs.trexsas.repositorio.PersonaRepositorio;
import com.itefs.trexsas.utilidades.Cifrador;
import com.itefs.trexsas.utilidades.Correo;
import com.itefs.trexsas.utilidades.GeneradorCredenciales;

@Service
public class CuentaServicio {
	
	@Autowired
	private CuentaRepositorio cuentaRepositorio;
	
	@Autowired
	private PerfilRepositorio perfilRepositorio;
	
	@Autowired
	private CuentaPerfilRepositorio cuentaPerfilRepositorio;
	
	@Autowired
	private PersonaRepositorio personaRepositorio;
	
	@Autowired
	private ConductorRepositorio conductorRepositorio;
	
	@Autowired
	private LicenciaRepositorio licenciaRepositorio;
	
	
	public void crear(Cuenta cuenta) {
		cuenta.setIdCuenta(null);
		cuentaRepositorio.save(cuenta);
	}
	
	public void actualizar(Cuenta cuenta) {
		cuentaRepositorio.save(cuenta);
	}
	
	public Cuenta obtenerPorUsuario(String usuario) {
		Optional<Cuenta>opCuenta=cuentaRepositorio.encontrarPorUsername(usuario);
		if(opCuenta.isPresent()) {
			System.out.println(opCuenta.get().getUsuarioCuenta());
			return opCuenta.get();
		}
		return null;
	}
	
	@Async
	public void crearCuentasMasivas(List<POJOCuenta> arreglo, Long idAutor) throws Exception
	{
		Persona personaCreadora = personaRepositorio.findById(idAutor).get();
			List<POJOCuenta> cuentas = arreglo;
			for (POJOCuenta pojoCuenta : cuentas) {
				Cuenta cuenta=new Cuenta();
				Persona persona = pojoCuenta.getPersona();
				Perfil perfil1=new Perfil();
				perfil1.setIdPerfil(2);
				
				System.out.println("persona : "+persona.toString());
				Cifrador cifrador=new Cifrador();
				personaRepositorio.save(persona);
				Persona autor=new Persona();
				
				GeneradorCredenciales credenciales = new GeneradorCredenciales();
				String usuario = credenciales.generarUsuario(persona.getNombrePersona(), persona.getApellidoPersona());
				String password = credenciales.generarContraseña();
				cuenta.setUsuarioCuenta(usuario);
				autor.setIdPersona(idAutor);
				cuenta.setClaveCuenta(cifrador.cifrar(password));
				cuenta.setPersona(persona);
				cuenta.addPerfil(perfil1);
				cuenta.setRegistradoPorCuenta(autor);
				cuenta.setEstadoCuenta(0);
				cuentaRepositorio.save(cuenta);
				Conductor conductorNuevo = new Conductor();
				conductorNuevo.setPersona(persona);
				Conductor guardado = conductorRepositorio.save(conductorNuevo);
				Licencia licenciaNueva = pojoCuenta.getLicencia();
				licenciaNueva.setConductor(guardado);
				licenciaRepositorio.save(licenciaNueva);
			}
		Correo correo = new Correo();
		correo.enviarCorreoConfirmacionSubidaConductores(personaCreadora.getCorreoPersona(), personaCreadora.getNombrePersona()+" "+personaCreadora.getApellidoPersona());
		
	}
	
	public Cuenta obtenerPorId(Integer idUsuario) {
		Optional<Cuenta>opCuenta=cuentaRepositorio.findById(idUsuario);
		if(opCuenta.isPresent()) {
			System.out.println(opCuenta.get().getUsuarioCuenta());
			return opCuenta.get();
		}
		return null;
	}
	
	public void quitarPerfil(Integer cuenta, Integer perfil)
	{
		CuentaPerfil cuentaPerfil = cuentaPerfilRepositorio.traerCuentaPerfil(cuenta, perfil);
		System.out.println("cuenta : "+cuenta);
		System.out.println("perfil : "+perfil);
		if(cuentaPerfil!=null)
		{
			cuentaPerfilRepositorio.delete(cuentaPerfil);
		}
		
	}
	
	
	
	public Cuenta obtenerPorIdPersona(Long id) {
		System.out.println("esae");
		Optional<Cuenta>opCuenta=cuentaRepositorio.encontrarPorIdPersona(id);
		System.out.println(opCuenta);
		if(opCuenta.isPresent()) {
			return opCuenta.get();
		}
		return null;
	}
	
	public Page<Object[]> listar(int inicio) {
		Page<Object[]> page= cuentaRepositorio.listarUsuariosPaginados(PageRequest.of(inicio, 10));
		return page;
	}
	
	public List<Object[]> listarTodos() {
		List<Object[]> page= cuentaRepositorio.listarUsuarios();
		return page;
	}
	
	public Cuenta obtenerPorId(int id) {
		Optional<Cuenta> opCuenta=cuentaRepositorio.findById(id);
		if(opCuenta.isPresent()) {
			return opCuenta.get();
		}
		return null;
	}
	
	public List<Object[]> obtenerBasicoPorIdPersona(Long id) {
		return cuentaRepositorio.encontrarBasicoPorIdPersona(id);
	}
	
	public List<String> obtenerCedulas() {
		return cuentaRepositorio.obtenerCedulas();
	}
	
	public List<String> usuariosEmpiecenPor(String palabra) {
		return cuentaRepositorio.encontrarUsernameEmpiecePor(palabra);
	}
	
	public Persona personaDeCuenta(int id) {
		Optional<Persona> opPersona=cuentaRepositorio.personaDeCuenta(id);
		if(opPersona.isPresent()) {
			return opPersona.get();
		}
		return null;
	}
	
	
	public List<Object[]> nombreDeToken(String token) {
		return cuentaRepositorio.nombreDeToken(token);
	}
	
	public HashMap<String, Object> filtrarCuentaPorDocumento(String documento)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Page<Object[]> page=cuentaRepositorio.listarUsuariosPaginadosDocumento(PageRequest.of(0, 10), documento);
		List<HashMap<String, Object>> usuarios = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> cuenta = new HashMap<String, Object>();
			List<Object[]> perfilList=perfilRepositorio.encontrarPerfilesDeCuenta((int)o[0]);
			List<HashMap<String, Object>> perfiles = new ArrayList<HashMap<String, Object>>();
			for(Object[] p:perfilList) {
				HashMap<String, Object> perfil = new HashMap<String, Object>();
				perfil.put("idPerfil", p[0]);
				perfil.put("nombrePerfil", p[1]);
				perfiles.add(perfil);
			}
			cuenta.put("idCuenta", o[0]);
			cuenta.put("documentoPersona", o[1]);
			cuenta.put("nombrePersona", o[2]);
			cuenta.put("apellidoPersona", o[3]);
			cuenta.put("usuarioCuenta", o[4]);
			cuenta.put("celularUnoPersona", o[5]);
			cuenta.put("estadoCuenta", o[6]);
			cuenta.put("correoPersona", o[7]);
			cuenta.put("perfilList", perfiles);
			usuarios.add(cuenta);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("usuarios", usuarios);
		return response;
	}
	
	public HashMap<String, Object> filtrarCuentaPorCorreo(String correo)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Page<Object[]> page=cuentaRepositorio.listarUsuariosPaginadosCorreo(PageRequest.of(0, 10), correo);
		List<HashMap<String, Object>> usuarios = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> cuenta = new HashMap<String, Object>();
			List<Object[]> perfilList=perfilRepositorio.encontrarPerfilesDeCuenta((int)o[0]);
			List<HashMap<String, Object>> perfiles = new ArrayList<HashMap<String, Object>>();
			for(Object[] p:perfilList) {
				HashMap<String, Object> perfil = new HashMap<String, Object>();
				perfil.put("idPerfil", p[0]);
				perfil.put("nombrePerfil", p[1]);
				perfiles.add(perfil);
			}
			cuenta.put("idCuenta", o[0]);
			cuenta.put("documentoPersona", o[1]);
			cuenta.put("nombrePersona", o[2]);
			cuenta.put("apellidoPersona", o[3]);
			cuenta.put("usuarioCuenta", o[4]);
			cuenta.put("celularUnoPersona", o[5]);
			cuenta.put("estadoCuenta", o[6]);
			cuenta.put("correoPersona", o[7]);
			cuenta.put("perfilList", perfiles);
			usuarios.add(cuenta);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("usuarios", usuarios);
		return response;
	}
	
	public HashMap<String, Object> filtrarCuentaPorCuenta(String cuentaBusqueda)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Page<Object[]> page=cuentaRepositorio.listarUsuariosPaginadosCuenta(PageRequest.of(0, 10), cuentaBusqueda);
		List<HashMap<String, Object>> usuarios = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> cuenta = new HashMap<String, Object>();
			List<Object[]> perfilList=perfilRepositorio.encontrarPerfilesDeCuenta((int)o[0]);
			List<HashMap<String, Object>> perfiles = new ArrayList<HashMap<String, Object>>();
			for(Object[] p:perfilList) {
				HashMap<String, Object> perfil = new HashMap<String, Object>();
				perfil.put("idPerfil", p[0]);
				perfil.put("nombrePerfil", p[1]);
				perfiles.add(perfil);
			}
			cuenta.put("idCuenta", o[0]);
			cuenta.put("documentoPersona", o[1]);
			cuenta.put("nombrePersona", o[2]);
			cuenta.put("apellidoPersona", o[3]);
			cuenta.put("usuarioCuenta", o[4]);
			cuenta.put("celularUnoPersona", o[5]);
			cuenta.put("estadoCuenta", o[6]);
			cuenta.put("correoPersona", o[7]);
			cuenta.put("perfilList", perfiles);
			usuarios.add(cuenta);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("usuarios", usuarios);
		return response;
	}
	
	public HashMap<String, Object> filtrarCuentaPorEstado(int estadoCuenta)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		Page<Object[]> page=cuentaRepositorio.listarUsuariosPaginadosEstadoCuenta(PageRequest.of(0, 10), estadoCuenta);
		List<HashMap<String, Object>> usuarios = new ArrayList<HashMap<String, Object>>();
		for(Object[] o:page.getContent()) {
			HashMap<String, Object> cuenta = new HashMap<String, Object>();
			List<Object[]> perfilList=perfilRepositorio.encontrarPerfilesDeCuenta((int)o[0]);
			List<HashMap<String, Object>> perfiles = new ArrayList<HashMap<String, Object>>();
			for(Object[] p:perfilList) {
				HashMap<String, Object> perfil = new HashMap<String, Object>();
				perfil.put("idPerfil", p[0]);
				perfil.put("nombrePerfil", p[1]);
				perfiles.add(perfil);
			}
			cuenta.put("idCuenta", o[0]);
			cuenta.put("documentoPersona", o[1]);
			cuenta.put("nombrePersona", o[2]);
			cuenta.put("apellidoPersona", o[3]);
			cuenta.put("usuarioCuenta", o[4]);
			cuenta.put("celularUnoPersona", o[5]);
			cuenta.put("estadoCuenta", o[6]);
			cuenta.put("correoPersona", o[7]);
			cuenta.put("perfilList", perfiles);
			usuarios.add(cuenta);
		}
		response.put("totalResultados", page.getTotalElements());
		response.put("totalPaginas", page.getTotalPages());
		response.put("usuarios", usuarios);
		return response;
	}
	
	

}
