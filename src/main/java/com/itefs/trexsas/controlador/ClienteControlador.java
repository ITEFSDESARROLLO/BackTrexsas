package com.itefs.trexsas.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itefs.trexsas.modelo.Cliente;
import com.itefs.trexsas.modelo.Cuenta;
import com.itefs.trexsas.modelo.POJOCliente;
import com.itefs.trexsas.modelo.Perfil;
import com.itefs.trexsas.modelo.Persona;
import com.itefs.trexsas.servicio.ClienteServicio;
import com.itefs.trexsas.servicio.CuentaServicio;
import com.itefs.trexsas.servicio.TokenServicio;
import com.itefs.trexsas.utilidades.Cifrador;
import com.itefs.trexsas.utilidades.GeneradorCredenciales;

@RestController
@RequestMapping("/cliente")
public class ClienteControlador {
	
	@Autowired
	private ClienteServicio clienteServicio;
	@Autowired
	private CuentaServicio cuentaServicio;
	@Autowired
	private TokenServicio tokenServicio;
	@Autowired
    private Validator validator;
	
	@PostMapping
	public ResponseEntity<?> crear(@RequestParam("accessToken") String accessToken,@RequestBody Map<String,Object> map){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				Persona autor=new Persona();
				autor.setIdPersona(id);
				Perfil perfil=new Perfil();
				perfil.setIdPerfil(8);
				Cifrador cifrador=new Cifrador();
				ObjectMapper mapper=new ObjectMapper();
				Cliente cliente=mapper.convertValue(map.get("cliente"), Cliente.class);
				Cuenta cuenta=mapper.convertValue(map.get("cuenta"), Cuenta.class);
				GeneradorCredenciales gc = new GeneradorCredenciales();
				String usuario = gc.generarUsuario(cliente.getRazonSocialCliente(), cliente.getRazonSocialCliente());
				String password = gc.generarContraseña();
				cuenta.setEstadoCuenta(1);
				cuenta.setRegistradoPorCuenta(autor);
				cuenta.setClaveCuenta(cifrador.cifrar(password));
				cuenta.setUsuarioCuenta(usuario);
				Set<ConstraintViolation<Cliente>> validateCliente = validator.validate(cliente);
				Set<ConstraintViolation<Cuenta>> validateCuenta = validator.validate(cuenta);
				if(validateCliente.size()>0 || validateCuenta.size()>0) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Cliente> item : validateCliente) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					for (ConstraintViolation<Cuenta> item : validateCuenta) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				
				clienteServicio.crear(cliente);
				cuenta.setCliente(cliente);
				cuenta.addPerfil(perfil);
				cuentaServicio.crear(cuenta);
				
				
				
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
	
	@PutMapping
	public ResponseEntity<?> editar(@RequestParam("accessToken") String accessToken,@RequestBody Cuenta auxCuenta){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				
				Cuenta cuenta=cuentaServicio.obtenerPorId(auxCuenta.getIdCuenta());
				
				Persona autor=new Persona();
				autor.setIdPersona(id);
//				cuenta.setActualizadoPorCuenta(autor);
				cuenta.setEstadoCuenta(auxCuenta.getEstadoCuenta());
				cuenta.getCliente().setRazonSocialCliente(auxCuenta.getCliente().getRazonSocialCliente());
				cuenta.getCliente().setNitCliente(auxCuenta.getCliente().getNitCliente());
				cuenta.getCliente().setDireccionCliente(auxCuenta.getCliente().getDireccionCliente());
				cuenta.getCliente().setEnvioProgramacionCliente(auxCuenta.getCliente().getEnvioProgramacionCliente());
				cuenta.getCliente().setFinalizarServicioCliente(auxCuenta.getCliente().getFinalizarServicioCliente());
				cuenta.getCliente().setCiudadCliente(auxCuenta.getCliente().getCiudadCliente());
				cuenta.getCliente().setLogoCliente(auxCuenta.getCliente().getLogoCliente());
				cuenta.getCliente().setCorreoCliente(auxCuenta.getCliente().getCorreoCliente());
				cuenta.getCliente().setCelularUnoCliente(auxCuenta.getCliente().getCelularUnoCliente());
				cuenta.getCliente().setCelularDosCliente(auxCuenta.getCliente().getCelularDosCliente());
				Cliente cliente=cuenta.getCliente();
				Set<ConstraintViolation<Cuenta>> validateCuenta = validator.validate(cuenta);
				Set<ConstraintViolation<Cliente>> validateCliente = validator.validate(cliente);
				if(validateCuenta.size()>0 || validateCliente.size()>0) {
					List<HashMap<String, Object>> responses = new ArrayList<HashMap<String, Object>>();
					for (ConstraintViolation<Cuenta> item : validateCuenta) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					for (ConstraintViolation<Cliente> item : validateCliente) {
						HashMap<String, Object> responseError = new HashMap<String, Object>();
				        responseError.put("mensaje", item.getMessage());
						responses.add(responseError);
				    }
					return ResponseEntity.ok().body(responses);
				}
				
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
	
	@PutMapping("/estado")
	public ResponseEntity<?> editarEstado(@RequestParam("accessToken") String accessToken,@RequestBody Cuenta auxCuenta){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				Cuenta cuenta=cuentaServicio.obtenerPorId(auxCuenta.getIdCuenta());
				Persona autor=new Persona();
				autor.setIdPersona(id);
				cuenta.setActualizadoPorCuenta(autor);
				cuenta.setEstadoCuenta(auxCuenta.getEstadoCuenta());
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
	
	@GetMapping
	public ResponseEntity<?> obtenerTodos(@RequestParam("accessToken") String accessToken,@RequestParam("pr") Integer pr){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Page<Object[]> page=clienteServicio.listar(pr);
				List<HashMap<String, Object>> clientes = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page.getContent()) {
					HashMap<String, Object> cliente = new HashMap<String, Object>();
					cliente.put("idCuenta", o[0]);
					cliente.put("idCliente", o[8]);
					cliente.put("razonSocialCliente", o[1]);
					cliente.put("nitCliente", o[2]);
					cliente.put("direccionCliente", o[3]);
					cliente.put("celularUnoCliente", o[4]);
					cliente.put("finalizarServicioCliente", o[5]);
					cliente.put("logoCliente", o[6]);
					cliente.put("estadoCuenta", o[7]);
					clientes.add(cliente);
				}
				response.put("totalResultados", page.getTotalElements());
				response.put("totalPaginas", page.getTotalPages());
				response.put("clientes", clientes);
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
	
	@GetMapping("/listarClientes")
	public ResponseEntity<?> obtenerClientesFacturas(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				List<Object[]> page=clienteServicio.listarClientesFacturas();
				List<HashMap<String, Object>> clientes = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page) {
					HashMap<String, Object> cliente = new HashMap<String, Object>();
					cliente.put("idCuenta", o[0]);
					cliente.put("idCliente", o[8]);
					cliente.put("razonSocialCliente", o[1]);
					cliente.put("nitCliente", o[2]);
					cliente.put("direccionCliente", o[3]);
					cliente.put("celularUnoCliente", o[4]);
					cliente.put("finalizarServicioCliente", o[5]);
					cliente.put("logoCliente", o[6]);
					cliente.put("estadoCuenta", o[7]);
					clientes.add(cliente);
				}
				
				response.put("clientes", clientes);
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
	
	@GetMapping("/obtenerClientesOrdenes")
	public ResponseEntity<?> traerClientesOrdenes(@RequestParam("accessToken") String accessToken)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				List<Cliente> clientes=clienteServicio.listarClientesOrdenes();
				response.put("clientes", clientes);
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
	
	@GetMapping("/infobasica")
	public ResponseEntity<?> obtenerInfoBasica(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				List<Object[]> page=clienteServicio.obtenerInfoBasica();
				List<HashMap<String, Object>> clientes = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page) {
					HashMap<String, Object> cliente = new HashMap<String, Object>();
					cliente.put("idCliente", o[0]);
					cliente.put("razonSocialCliente", o[1]);
					cliente.put("nitCliente", o[2]);
					clientes.add(cliente);
				}
				return ResponseEntity.ok().body(clientes);
			}else {
				response.put("mensaje", 2);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@GetMapping("/clientes")
	public ResponseEntity<?> traerTodos(@RequestParam("accessToken") String accessToken)
	{
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				List<Object[]> page=clienteServicio.obtenerTodos();
				List<HashMap<String, Object>> clientes = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page){
					HashMap<String, Object> cliente = new HashMap<String, Object>();
					cliente.put("idCliente", o[0]);
					cliente.put("razonSocialCliente", o[1]);
					cliente.put("correo", o[2]);
					clientes.add(cliente);
				}
				response.put("totalResultados", page.size());
				response.put("clientes", clientes);
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
	public ResponseEntity<?> filtrarCliente(@RequestParam("accessToken") String accessToken, @RequestParam("criterio") String criterio, @RequestParam("valor") String valor)
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
					case "rsc":
						System.out.println("buscando por placa : ");
						lista = clienteServicio.listarPorRazonSocialCliente(0, valor);
						return ResponseEntity.ok().body(lista);
					case "nc":
						System.out.println("buscando por placa : ");
						lista = clienteServicio.listarPorNitCliente(0,valor);
						return ResponseEntity.ok().body(lista);
					case "tpf":
						System.out.println("buscando por placa : ");
						lista = clienteServicio.listarPorTipoFinalizacion(0, valor);
						return ResponseEntity.ok().body(lista);
					case "tf":
						System.out.println("buscando por placa : ");
						lista = clienteServicio.listarPorTelefono(0,valor);
						return ResponseEntity.ok().body(lista);
					case "dc":
						System.out.println("buscando por placa : ");
						lista = clienteServicio.listarPorDireccion(0, valor);
						return ResponseEntity.ok().body(lista);
					case "es":
						System.out.println("buscando por placa : ");
						lista = clienteServicio.listarPorEstado(0, valor);
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
	
	
	@PostMapping("/crearClientesMasivos")
	public ResponseEntity<?> crearMasivos(@RequestParam("accessToken") String accessToken,@RequestBody POJOCliente clientes){
		System.out.println("creando clientes masivos");
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				Persona autor=new Persona();
				autor.setIdPersona(id);
				Perfil perfil=new Perfil();
				perfil.setIdPerfil(8);
				Cifrador cifrador=new Cifrador();
				GeneradorCredenciales gc = new GeneradorCredenciales();
				List<Cliente> listaCliente = clientes.getClientes();
				for(Cliente clienteConcreto : listaCliente)
				{
					System.out.println("celular : "+clienteConcreto.getCelularUnoCliente());
					Cuenta cuentaCliente = new Cuenta();
					String usuario = gc.generarUsuario(clienteConcreto.getRazonSocialCliente(), clienteConcreto.getRazonSocialCliente());
					String password = gc.generarContraseña();
					cuentaCliente.setEstadoCuenta(1);
					cuentaCliente.setRegistradoPorCuenta(autor);
					cuentaCliente.setClaveCuenta(cifrador.cifrar(password));
					cuentaCliente.setUsuarioCuenta(usuario);
					clienteConcreto.setFinalizarServicioCliente(Short.valueOf("1"));
					clienteServicio.crear(clienteConcreto);
					cuentaCliente.setCliente(clienteConcreto);
					cuentaCliente.addPerfil(perfil);
					cuentaServicio.crear(cuentaCliente);
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
	
	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<?> eliminarCliente(@PathVariable int id, @RequestParam("accessToken") String accessToken) {
	    HashMap<String, Object> response = new HashMap<String, Object>();
	    try {
	        if(tokenServicio.existeToken(accessToken) != null) {
	            Cliente cl= clienteServicio.obtenerPorId(id);
	            System.out.println("eliminado: ");
				response.put("mensaje", "1");
				return ResponseEntity.ok().body(response);
				
	}else {
		System.out.println("error 1 : ");
		response.put("mensaje", 2);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}
			
	}catch(Exception ex) {
		System.out.println("error 2 : ");
		ex.printStackTrace();
		response.put("error", ex.toString());
		return ResponseEntity.badRequest().body(response);
	}

	}
	

}
