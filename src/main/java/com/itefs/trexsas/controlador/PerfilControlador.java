package com.itefs.trexsas.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

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

import com.itefs.trexsas.modelo.Perfil;
import com.itefs.trexsas.modelo.PersonaAutor;
import com.itefs.trexsas.modelo.UriXPerfil;
import com.itefs.trexsas.servicio.PerfilServicio;
import com.itefs.trexsas.servicio.TokenServicio;
import com.itefs.trexsas.servicio.UriHijaServicio;
import com.itefs.trexsas.servicio.UriXPerfilServicio;

@RestController
@RequestMapping("/perfil")
public class PerfilControlador {
	
	@Autowired
	private TokenServicio tokenServicio;
	@Autowired
	private UriXPerfilServicio uriXPerfilServicio;
	@Autowired
	private PerfilServicio perfilServicio;
	@Autowired
	private UriHijaServicio uriHijaServicio;
	@Autowired
    private Validator validator;
	
	@PostMapping
	public ResponseEntity<?> crear(@RequestParam("accessToken") String accessToken,@RequestBody Perfil perfil){
		HashMap<String, Object> response = new HashMap<String, Object>();
		System.out.println("llamando : ");
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				perfil.setRegistradoPorPerfil(autor);
				Set<ConstraintViolation<Perfil>> validatePerfil = validator.validate(perfil);
				if(validatePerfil.size()>0) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Perfil> item : validatePerfil) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				perfil.setFechaRegistroPerfil(String.valueOf(java.time.LocalDate.now()));
				if(perfil.getUriXPerfilList().size()<33) {
					response.put("mensaje", "faltan permisos, son 33 en total");
					return ResponseEntity.ok().body(response);
				}
				perfilServicio.crear(perfil);
				for(UriXPerfil uxp:perfil.getUriXPerfilList()) {
					uxp.setPerfil(perfil);
					uriXPerfilServicio.crear(uxp);
				}
				response.put("mensaje", 1);
				return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
		}catch(Exception ex) {
			System.out.println("error");
			ex.printStackTrace();
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	
	@GetMapping
	public ResponseEntity<?> obtenerTodos(@RequestParam("accessToken") String accessToken,@RequestParam("pr") Integer pr){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			System.out.println("id : "+accessToken);
			if(tokenServicio.existeToken(accessToken)!=null) {
				Page<Object[]> page=perfilServicio.listar(pr);
				List<HashMap<String, Object>> perfiles = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page.getContent()) {
					HashMap<String, Object> perfil = new HashMap<String, Object>();
					perfil.put("idPerfil", o[0]);
					perfil.put("nombrePerfil", o[1]);
					perfil.put("observacionesPerfil", o[2]);
					perfil.put("fechaRegistroPerfil", o[3]);
					perfil.put("estadoPerfil", o[4]);
					perfiles.add(perfil);
				}
				response.put("totalResultados", page.getTotalElements());
				response.put("totalPaginas", page.getTotalPages());
				response.put("perfiles", perfiles);
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
	public ResponseEntity<?> obtenerPorId(@PathVariable("id") int id,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			
			if(tokenServicio.existeToken(accessToken)!=null) {
				Perfil perfil=perfilServicio.obtenerPorId(id);
				List<HashMap<String, Object>> permisos = uriXPerfilServicio.listarURLPerfil(id);
				HashMap<String, Object> objetoPerfil = new HashMap<String, Object>();
				objetoPerfil.put("perfil", perfil);
				objetoPerfil.put("nombrePerfil",perfil.getNombrePerfil());
				objetoPerfil.put("estadoPerfil",perfil.getEstadoPerfil());
				objetoPerfil.put("permisos", permisos);
				return ResponseEntity.ok().body(objetoPerfil);
				/*if(perfil!=null) {
					
				}else {
					response.put("mensaje", 3);
					return ResponseEntity.ok().body(response);
				}*/
				
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
	public ResponseEntity<?> editar(@RequestParam("accessToken") String accessToken,@RequestBody Perfil auxPerfil){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				Perfil perfil=perfilServicio.obtenerPorId(auxPerfil.getIdPerfil());
				//perfil.setActualizadoPorPerfil(autor);
				perfil.setEstadoPerfil(auxPerfil.getEstadoPerfil());
				perfil.setNombrePerfil(auxPerfil.getNombrePerfil());
				perfil.setObservacionesPerfil(auxPerfil.getObservacionesPerfil());
				Set<ConstraintViolation<Perfil>> validatePerfil = validator.validate(perfil);
				if(validatePerfil.size()>0) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Perfil> item : validatePerfil) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				if(auxPerfil.getUriXPerfilList().size()<33) {
					response.put("mensaje", "faltan permisos, son 33 en total");
					return ResponseEntity.ok().body(response);
				}
				perfilServicio.actualizar(perfil);
				for(int i=0;i<auxPerfil.getUriXPerfilList().size();i++) {
					System.out.println("elemento : "+i);
					System.out.println(auxPerfil.getUriXPerfilList().get(i).getIdUriXPerfil()==null);
					UriXPerfil uriXPerfil=uriXPerfilServicio.obtenerPorId(auxPerfil.getUriXPerfilList().get(i).getIdUriXPerfil());
					uriXPerfil.setAccesoUriXPerfil(auxPerfil.getUriXPerfilList().get(i).getAccesoUriXPerfil());
					uriXPerfilServicio.actualizar(uriXPerfil);
				}
				response.put("mensaje", 1);
				return ResponseEntity.ok().body(response);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PutMapping("/estado")
	public ResponseEntity<?> editarEstado(@RequestParam("accessToken") String accessToken,@RequestBody Perfil auxPerfil){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				PersonaAutor autor=new PersonaAutor();
				autor.setIdPersona(id);
				Perfil perfil=perfilServicio.obtenerPorId(auxPerfil.getIdPerfil());
				perfil.setActualizadoPorPerfil(autor);
				perfil.setEstadoPerfil(auxPerfil.getEstadoPerfil());
				perfilServicio.actualizar(perfil);
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
	
	@GetMapping("/urihija")
	public ResponseEntity<?> obtenerTodosUrisHijas(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		Long id=tokenServicio.existeToken(accessToken);
		System.out.println("id : "+accessToken);
		if(tokenServicio.existeToken(accessToken)!=null) {
			try {
				return ResponseEntity.ok().body(uriHijaServicio.obtenerTodos());
			}catch(Exception ex) {
				response.put("error", ex.toString());
				return ResponseEntity.badRequest().body(response);
			}
		}else {
			response.put("mensaje", 2);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		}
	}
	
	@GetMapping("/accesos")
	public ResponseEntity<?> obtenerAccesos(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		if(tokenServicio.existeToken(accessToken)!=null) {
			try {
				List<Object[]> accesosList=perfilServicio.obtenerAccesosPorToken(accessToken);
				List<HashMap<String, Object>> accesos = new ArrayList<HashMap<String, Object>>();
				for(Object[] p:accesosList) {
					HashMap<String, Object> acceso = new HashMap<String, Object>();
					acceso.put("uriHija", p[0]);
					acceso.put("uriPadre", p[1]);
					acceso.put("permiso", p[2]);
					accesos.add(acceso);
				}
				return ResponseEntity.ok().body(accesos);
			}catch(Exception ex) {
				response.put("error", ex.toString());
				return ResponseEntity.badRequest().body(response);
			}
		}else {
			response.put("mensaje", 2);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		}
	}
	
	@GetMapping("/infobasica")
	public ResponseEntity<?> obtenerInfoBasica(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				List<Object[]> list=null;
				list=perfilServicio.infoBasica();
				List<HashMap<String, Object>> perfiles = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:list) {
					HashMap<String, Object> perfil = new HashMap<String, Object>();
					perfil.put("idPerfil", o[0]);
					perfil.put("nombrePerfil", o[1]);
					perfiles.add(perfil);
				}
				return ResponseEntity.ok().body(perfiles);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
				
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("/urlSistema")
	public ResponseEntity<?> obtenerURLSistema(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				List<HashMap<String, Object>> perfiles = uriXPerfilServicio.listarURL();
				return ResponseEntity.ok().body(perfiles);
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
