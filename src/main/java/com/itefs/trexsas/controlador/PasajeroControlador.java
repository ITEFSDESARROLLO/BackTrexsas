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
import com.itefs.trexsas.modelo.Pasajero;
import com.itefs.trexsas.modelo.Perfil;
import com.itefs.trexsas.modelo.PersonaAutor;
import com.itefs.trexsas.servicio.CuentaServicio;
import com.itefs.trexsas.servicio.PasajeroServicio;
import com.itefs.trexsas.servicio.TokenServicio;

@RestController
@RequestMapping("/pasajero")
public class PasajeroControlador {
	
	@Autowired
	private PasajeroServicio pasajeroServicio;
	@Autowired
	private CuentaServicio cuentaServicio;
	@Autowired
	private TokenServicio tokenServicio;
	
	@PostMapping
	public ResponseEntity<?> crear(@RequestParam("accessToken") String accessToken,@RequestBody Pasajero pasajero){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				Perfil perfil=new Perfil();
				perfil.setIdPerfil(7);
				pasajero.setRegistradoPorPasajero(autor);
				pasajeroServicio.crear(pasajero);
				Cuenta cuenta=cuentaServicio.obtenerPorIdPersona(pasajero.getPersona().getIdPersona());
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
	public ResponseEntity<?> editar(@RequestParam("accessToken") String accessToken,@RequestBody Pasajero auxPasajero){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				Pasajero pasajero= pasajeroServicio.obtenerPorId(auxPasajero.getIdPasajero());
				pasajero.setCliente(auxPasajero.getCliente());
				pasajero.setEstadoPasajero(auxPasajero.getEstadoPasajero());
				pasajero.setActualizadoPorPasajero(autor);
				pasajeroServicio.actualizar(pasajero);
				
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
	public ResponseEntity<?> editarEstado(@RequestParam("accessToken") String accessToken,@RequestBody Pasajero auxPasajero){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				Pasajero pasajero= pasajeroServicio.obtenerPorId(auxPasajero.getIdPasajero());
				pasajero.setEstadoPasajero(auxPasajero.getEstadoPasajero());
				pasajero.setActualizadoPorPasajero(autor);
				pasajeroServicio.actualizar(pasajero);
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
	
	@GetMapping("listar/{id}")
	public ResponseEntity<?> obtenerTodos(@PathVariable("id") int id,@RequestParam("accessToken") String accessToken,@RequestParam("pr") Integer pr){
		HashMap<String, Object> response = new HashMap<String, Object>();
		//try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Page<Object[]> page=pasajeroServicio.listar(pr,id);
				List<HashMap<String, Object>> pasajeros = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page.getContent()) {
					HashMap<String, Object> pasajero = new HashMap<String, Object>();
					pasajero.put("idPasajero", o[0]);
					pasajero.put("documentoPersona", o[1]);
					pasajero.put("nombrePersona", o[2]);
					pasajero.put("apellidoPersona", o[3]);
					pasajero.put("estadoPasajero", o[4]);
					pasajero.put("telefonoPersona", o[5]);
					pasajeros.add(pasajero);
				}
				response.put("totalResultados", page.getTotalElements());
				response.put("totalPaginas", page.getTotalPages());
				response.put("pasajeros", pasajeros);
				return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
				
		/*}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}*/
	}
	
	@GetMapping("infobasica")
	public ResponseEntity<?> obtenerInfoBasica(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				System.out.println("llamando servicio de pasajeros");
				//List<HashMap<String, Object>> pasajeros = pasajeroServicio.infoBasica();
				List<Pasajero> pasajeros = pasajeroServicio.infoBasica();
				return ResponseEntity.ok().body(pasajeros);
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
	public ResponseEntity<?> obtenerPorId(@PathVariable("id") int id,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Pasajero pasajero=pasajeroServicio.obtenerPorId(id);
				if(pasajero!=null) {
					return ResponseEntity.ok().body(pasajero);
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
	
	@GetMapping("pasajero/{id}")
	public ResponseEntity<?> obtenerDatosUsuarioPorIdPropietario(@PathVariable("id") int id,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Pasajero propietario=pasajeroServicio.obtenerPorId(id);
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
	
	@GetMapping("nopasajeros")
	public ResponseEntity<?> obtenerPersonasNoPropietarios(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				List<Object[]> list=pasajeroServicio.listarPersonasNoPasajeros();
				List<HashMap<String, Object>> noPasajeros = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:list) {
					HashMap<String, Object> noPasajero = new HashMap<String, Object>();
					noPasajero.put("idPersona", o[0]);
					noPasajero.put("nombrePersona", o[1]);
					noPasajero.put("apellidoPersona", o[2]);
					noPasajero.put("documentoPersona", o[3]);
					noPasajeros.add(noPasajero);
				}
				return ResponseEntity.ok().body(noPasajeros);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
				
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("/pasajeros")
	public ResponseEntity<?> traerTodos(@RequestParam("accessToken") String accessToken)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				List<Object[]> page=pasajeroServicio.obtenerTodos();
				List<HashMap<String, Object>> pasajeros = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page) {
					HashMap<String, Object> pasajero = new HashMap<String, Object>();
					pasajero.put("id",o[0]);
					pasajero.put("nombrePersona",o[1]);
					pasajero.put("apellidoPersona",o[2]);
					pasajero.put("correo",o[3]);
					pasajeros.add(pasajero);
				}
				response.put("totalResultados", page.size());
				response.put("pasajeros", pasajeros);
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
	
	

}
