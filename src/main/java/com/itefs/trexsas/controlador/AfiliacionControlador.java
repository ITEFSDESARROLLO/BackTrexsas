package com.itefs.trexsas.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itefs.trexsas.servicio.AfiliacionServicio;
import com.itefs.trexsas.servicio.TokenServicio;

@RestController
@RequestMapping("/afiliacion")
public class AfiliacionControlador {
	
	@Autowired
	private AfiliacionServicio afiliacionServicio;
	@Autowired
	private TokenServicio tokenServicio;
	
	@GetMapping
	public ResponseEntity<?> obtenerTodos(@RequestParam("accessToken") String accessToken){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			if(tokenServicio.existeToken(accessToken)!=null) {
				System.out.println("token recibido"+accessToken);
				List<Object[]> page=afiliacionServicio.listarAfiliados();
				List<HashMap<String, Object>> afiliados = new ArrayList<HashMap<String, Object>>();
				for(Object[] array:page) {
					HashMap<String, Object> afiliacion = new HashMap<String, Object>();
					afiliacion.put("idPersona", array[0]);
					afiliacion.put("documentoPersona", array[1]);
					afiliacion.put("nombrePersona", array[2]);
					afiliacion.put("apellidoPersona", array[3]);
					afiliacion.put("direccionPersona", array[4]);
					afiliacion.put("idperfil", array[5]);
					afiliados.add(afiliacion);
				}
				/*response.put("totalResultados", page.getTotalElements());
				response.put("totalPaginas", page.getTotalPages());*/
				response.put("afiliados", afiliados);
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
