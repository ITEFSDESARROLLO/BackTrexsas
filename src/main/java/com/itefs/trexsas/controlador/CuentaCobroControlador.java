package com.itefs.trexsas.controlador;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
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

import com.itefs.trexsas.modelo.CuentaCobro;
import com.itefs.trexsas.modelo.PersonaAutor;
import com.itefs.trexsas.servicio.ArchivoServicio;
import com.itefs.trexsas.servicio.CuentaCobroServicio;
import com.itefs.trexsas.servicio.TokenServicio;

@RestController
@RequestMapping("/cuentacobro")
public class CuentaCobroControlador {
	
	@Autowired
	private CuentaCobroServicio cuentaCobroServicio;
	@Autowired
	private ArchivoServicio archivoServicio;
	@Autowired
	private TokenServicio tokenServicio;
	@Autowired
    private Validator validator;
	
	@PostMapping("/generar/{id}")
	public ResponseEntity<?> prueba(@PathVariable("id") Long idCC,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				CuentaCobro cc=cuentaCobroServicio.obtenerPorId(idCC);
				if(cc.getCuentaCobro()==null) {
					Calendar c = Calendar.getInstance();
			    	String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
			        String nombre=spn+"_cc"+".pdf";
					cc.setCuentaCobro(nombre);
					cuentaCobroServicio.actualizar(cc);
				}
				archivoServicio.crearCuentaCobro(idCC,cc.getCuentaCobro(),cc);
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
	
	@PostMapping
	public ResponseEntity<?> crear(@RequestParam("accessToken") String accessToken,@RequestBody CuentaCobro cuentaCobro){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				PersonaAutor pa=new PersonaAutor();
				pa.setIdPersona(id);	
				
				Calendar c = Calendar.getInstance();
		    	String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
		        String nombre=spn+"_cc"+".pdf";
		        cuentaCobro.setCuentaCobro(nombre);
				cuentaCobro.setRegistradoPorCuentaCobro(pa);
				cuentaCobro.setEstadoCuentaCobro(true);
				cuentaCobro.setFechaCuentaCobro(java.time.LocalDate.now().toString());
				System.out.println("valores cuenta cobro:"+cuentaCobro.getNumeroCuentaCobro());
				CuentaCobro guardada = cuentaCobroServicio.crear2(cuentaCobro);
				
				archivoServicio.crearCuentaCobro(cuentaCobro.getIdCuentaCobro(),nombre,guardada);
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
	public ResponseEntity<?> editar(@RequestParam("accessToken") String accessToken,@RequestBody CuentaCobro auxCuentaCobro){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				
				CuentaCobro cuentaCobro=cuentaCobroServicio.obtenerPorId(auxCuentaCobro.getIdCuentaCobro());
				cuentaCobro.setNombreCuentaCobro(auxCuentaCobro.getNombreCuentaCobro());
				cuentaCobro.setNumeroCuentaCobro(auxCuentaCobro.getNumeroCuentaCobro());
				cuentaCobro.setNitCuentaCobro(auxCuentaCobro.getNitCuentaCobro());
				cuentaCobro.setValorCuentaCobro(auxCuentaCobro.getValorCuentaCobro());
				cuentaCobro.setConceptoCuentaCobro(auxCuentaCobro.getConceptoCuentaCobro());
				
				
				if(cuentaCobro.getCuentaCobro()==null) {
					Calendar c = Calendar.getInstance();
			    	String spn= ""+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+"-"+c.get(Calendar.HOUR)+"-"+c.get(Calendar.MINUTE)+"-"+c.get(Calendar.MILLISECOND);
			        String nombre=spn+"_cc"+".pdf";
			        cuentaCobro.setCuentaCobro(nombre);
				}
				cuentaCobroServicio.actualizar(cuentaCobro);
				archivoServicio.crearCuentaCobro(cuentaCobro.getIdCuentaCobro(),cuentaCobro.getCuentaCobro(),cuentaCobro);
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
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable("id") Long idC,@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			Long id=tokenServicio.existeToken(accessToken);
			if(id!=null) {
				CuentaCobro cc=cuentaCobroServicio.obtenerPorId(idC);
				cc.setEstadoCuentaCobro(false);
				archivoServicio.eliminarCuentaCobro(cc.getCuentaCobro());
				cuentaCobroServicio.eliminarPorId(cc);
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
	
	@GetMapping
	public ResponseEntity<?> obtenerTodos(@RequestParam("accessToken") String accessToken,@RequestParam("pr") Integer pr){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				Page<Object[]> page=cuentaCobroServicio.listar(pr);
				List<HashMap<String, Object>> obs = new ArrayList<HashMap<String, Object>>();
				for(Object[] o:page.getContent()) {
					if((Boolean)o[8] !=false)
					{
						HashMap<String, Object> ob = new HashMap<String, Object>();
						ob.put("idCuentaCobro", o[0]);
						ob.put("numeroCuentaCobro", o[1]);
						ob.put("fechaCuentaCobro", o[2]);
						ob.put("nombreCuentaCobro", o[3]);
						ob.put("nitCuentaCobro", o[4]);
						ob.put("conceptoCuentaCobro", o[5]);
						ob.put("valorCuentaCobro", o[6]);
						ob.put("cuentaCobro", o[7]);
						obs.add(ob);
					}
				}
				response.put("totalResultados", page.getTotalElements());
				response.put("totalPaginas", page.getTotalPages());
				response.put("cuentasCobro", obs);
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
				CuentaCobro cuentaCobro=cuentaCobroServicio.obtenerPorId(id);
				if(cuentaCobro!=null) {
					return ResponseEntity.ok().body(cuentaCobro);
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

}
