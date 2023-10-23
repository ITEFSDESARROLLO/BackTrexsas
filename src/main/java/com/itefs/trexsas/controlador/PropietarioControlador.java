package com.itefs.trexsas.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itefs.trexsas.modelo.Cuenta;
import com.itefs.trexsas.modelo.Perfil;
import com.itefs.trexsas.modelo.Persona;
import com.itefs.trexsas.modelo.PersonaAutor;
import com.itefs.trexsas.modelo.Propietario;
import com.itefs.trexsas.servicio.CuentaServicio;
import com.itefs.trexsas.servicio.PersonaServicio;
import com.itefs.trexsas.servicio.PropietarioServicio;
import com.itefs.trexsas.servicio.TokenServicio;

@RestController
@RequestMapping("/propietario")
public class PropietarioControlador {
	
	@Autowired
	private PropietarioServicio propietarioServicio;
	@Autowired
	private CuentaServicio cuentaServicio;
	@Autowired
	private TokenServicio tokenServicio;
	@Autowired
	private PersonaServicio personaServicio;
	
	@PostMapping
	public ResponseEntity<?> crear(@RequestParam("accessToken") String accessToken,@RequestBody Propietario propietario){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				Persona autor=new Persona();
				autor.setIdPersona(id);
				Perfil perfil=new Perfil();
				perfil.setIdPerfil(3);
				propietario.setRegistradoPorPropietario(autor);
				Persona persona=personaServicio.obtenerPorId(propietario.getPersona().getIdPersona());
				persona.setDocumentoUnoPersona(propietario.getPersona().getDocumentoUnoPersona());
				//persona.setDocumentoDosPersona(propietario.getPersona().getDocumentoDosPersona());
				personaServicio.actualizar(persona);
				propietarioServicio.crear(propietario);
				Cuenta cuenta=cuentaServicio.obtenerPorIdPersona(propietario.getPersona().getIdPersona());
				cuenta.addPerfil(perfil);
				cuentaServicio.actualizar(cuenta);
				
				response.put("mensaje", 1);
				return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PutMapping
	public ResponseEntity<?> editar(@RequestParam("accessToken") String accessToken,@RequestBody Propietario auxPropietario){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				Persona autor=new Persona();
				autor.setIdPersona(id);
				Persona persona=personaServicio.obtenerPorId(auxPropietario.getPersona().getIdPersona());
				Propietario propietario= propietarioServicio.obtenerPorId(auxPropietario.getIdPropietario());
				personaServicio.actualizar(persona);
				propietario.setEstadoPropietario(auxPropietario.getEstadoPropietario());
				//propietario.setActualizadoPorPropietario(autor);
				propietarioServicio.actualizar(propietario);
				
				response.put("mensaje", 1);
				return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PutMapping("/estado")
	public ResponseEntity<?> editarEstado(@RequestParam("accessToken") String accessToken,@RequestBody Propietario auxPropietario){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				Persona autor=new Persona();
				autor.setIdPersona(id);
				Propietario propietario= propietarioServicio.obtenerPorId(auxPropietario.getIdPropietario());
				propietario.setEstadoPropietario(auxPropietario.getEstadoPropietario());
				//propietario.setActualizadoPorPropietario(autor);
				propietarioServicio.actualizar(propietario);
				
				response.put("mensaje", 1);
				return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping
	public ResponseEntity<?> obtenerTodos(@RequestParam("accessToken") String accessToken,@RequestParam("pr") Integer pr){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Page<Object[]> page=propietarioServicio.listar(pr);
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
				return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
				
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> obtenerPorId(@PathVariable("id") Long id,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Propietario propietario=propietarioServicio.obtenerPorId(id);
				if(propietario!=null) {
					return ResponseEntity.ok().body(propietario);
				}else {
					response.put("mensaje", 3);
					return ResponseEntity.ok().body(response);
				}
				
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
				
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("propietario/{id}")
	public ResponseEntity<?> obtenerDatosUsuarioPorIdPropietario(@PathVariable("id") Long id,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Propietario propietario=propietarioServicio.obtenerPorId(id);
				if(propietario!=null) {
					HashMap<String, Object> cuenta = new HashMap<String, Object>();
					List<Object[]> consulta=cuentaServicio.obtenerBasicoPorIdPersona(propietario.getPersona().getIdPersona());
					cuenta.put("idCuenta", consulta.get(0)[0]);
					cuenta.put("usuarioCuenta", consulta.get(0)[1]);
					cuenta.put("estadoCuenta", consulta.get(0)[2]);
					cuenta.put("registradoPorCuenta", consulta.get(0)[3]);
					cuenta.put("fechaRegistroCuenta", consulta.get(0)[4]);
					cuenta.put("actualizadoPorCuenta", consulta.get(0)[5]);
					cuenta.put("fechaActualizacionCuenta", consulta.get(0)[6]);
					response.put("propietario", propietario);
					response.put("cuenta", cuenta);
					return ResponseEntity.ok().body(response);
				}else {
					response.put("mensaje", 3);
					return ResponseEntity.ok().body(response);
				}
				
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
				
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("nopropietarios")
	public ResponseEntity<?> obtenerPersonasNoPropietarios(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				List<Object[]> list=propietarioServicio.listarPersonasNoPropietarios();
				List<HashMap<String, Object>> noPropietarios = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:list) {
					HashMap<String, Object> noPropietario = new HashMap<String, Object>();
					noPropietario.put("idPersona", o[0]);
					noPropietario.put("nombrePersona", o[1]);
					noPropietario.put("apellidoPersona", o[2]);
					noPropietario.put("documentoPersona", o[3]);
					noPropietarios.add(noPropietario);
				}
				return ResponseEntity.ok().body(noPropietarios);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
				
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("propietariosvehiculo")
	public ResponseEntity<?> obtenerPropietariosParaVehiculo(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				List<Object[]> list=propietarioServicio.listarPropietariosVehiculo();
				List<HashMap<String, Object>> propietarios = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:list) {
					HashMap<String, Object> propietario = new HashMap<String, Object>();
					propietario.put("idPersona", o[0]);
					propietario.put("nombrePersona", o[1]);
					propietario.put("apellidoPersona", o[2]);
					propietario.put("documentoPersona", o[3]);
					propietarios.add(propietario);
				}
				return ResponseEntity.ok().body(propietarios);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
				
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("propietariosvehiculo2")
	public ResponseEntity<?> obtenerPropietariosParaVehiculo2(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				List<Object[]> list=propietarioServicio.listarPropietariosVehiculo();
				List<HashMap<String, Object>> propietarios = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:list) {
					HashMap<String, Object> propietario = new HashMap<String, Object>();
					propietario.put("idPersona", o[0]);
					propietario.put("nombre",o[3]+" - "+ o[1]+" "+o[2]);
					propietario.put("nombrePersona",o[1]);
					propietario.put("apellidoPersona", o[2]);
					propietario.put("documentoPersona", o[3]);
					propietarios.add(propietario);
				}
				return ResponseEntity.ok().body(propietarios);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
				
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("/propietarios")
	public ResponseEntity<?> traerTodos(@RequestParam("accessToken") String accessToken)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				List<Object[]> page=propietarioServicio.obtenerTodos();
				List<HashMap<String, Object>> propietarios = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page) {
					HashMap<String, Object> propietario = new HashMap<String, Object>();
					propietario.put("idPersona", o[0]);
					propietario.put("nombrePersona", o[1]);
					propietario.put("apellidoPersona",o[2]);
					propietario.put("correo", o[3]);
					propietarios.add(propietario);
				}
				response.put("totalResultados", page.size());
				response.put("propietarios", propietarios);
				return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("/filtrar")
	public ResponseEntity<?> filtrarPropietarios(@RequestParam("accessToken") String accessToken, @RequestParam("criterio") String criterio, @RequestParam("valor") String valor)
	//public ResponseEntity<?> filtrarReservas(@RequestParam("accessToken") String accessToken, @PathVariable("criterio") String criterio, @PathVariable("valor") String valor)
	{
		System.out.println("en servicio filtro afiliacion");
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
				if(tokenServicio.existeToken(accessToken)!=null)
				{
					System.out.println("criterio :"+criterio);
					System.out.println("valor :"+valor);
					HashMap<String,Object> lista = new HashMap<String,Object>();

					switch (criterio) 
					{
					case "np":
						System.out.println("buscando por placa : ");
						lista = propietarioServicio.listarPorNombre(0,valor);
						return ResponseEntity.ok().body(lista);
					case "dp":
						System.out.println("buscando por placa : ");
						lista = propietarioServicio.listarPorDocumento(0,valor);
						return ResponseEntity.ok().body(lista);
					case "es":
						System.out.println("buscando por placa : ");
						lista = propietarioServicio.listarPorEstado(0,valor);
						return ResponseEntity.ok().body(lista);
					case "tf":
						System.out.println("buscando por placa : ");
						lista = propietarioServicio.listarPorTelefono(0,valor);
						return ResponseEntity.ok().body(lista);
				default:
					return ResponseEntity.badRequest().body("malo");
				
					}
				}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
			
		}catch (Exception e)
		{
			response.put("error", e.toString());
			return ResponseEntity.badRequest().body(response);
		}
		

	}

}
