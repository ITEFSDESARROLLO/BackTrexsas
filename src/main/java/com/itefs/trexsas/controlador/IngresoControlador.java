package com.itefs.trexsas.controlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itefs.trexsas.modelo.Cuenta;
import com.itefs.trexsas.modelo.Token;
import com.itefs.trexsas.modelo.UltimoIngresoCuenta;
import com.itefs.trexsas.servicio.CuentaServicio;
import com.itefs.trexsas.servicio.IngresoServicio;
import com.itefs.trexsas.servicio.TokenServicio;
import com.itefs.trexsas.servicio.UltimoIngresoCuentaServicio;
import com.itefs.trexsas.utilidades.Correo;
import com.itefs.trexsas.utilidades.GeneradorToken;

@RestController
@RequestMapping("/login")
@CrossOrigin("*")
public class IngresoControlador {
	
	@Autowired
	private IngresoServicio ingresoServicio;
	@Autowired
	private UltimoIngresoCuentaServicio ultimoIngresoCuentaServicio;
	@Autowired
	private CuentaServicio cuentaServicio;
	@Autowired
	private TokenServicio tokenServicio;
	@Autowired
	private Correo correo;
	
	@PostMapping
	public ResponseEntity<?> autenticacion(@RequestBody Map<String, String> map,HttpServletRequest request){
		System.out.println("en login");
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			
			String usuario=map.get("username");
			String clave=map.get("password");
			System.out.println("usuario "+usuario+" _ contraseña : "+clave);
			Cuenta cuenta= ingresoServicio.validarCuentayClave(usuario,clave);
			if(cuenta==null) {
				response.put("mensaje", 1);
				return ResponseEntity.ok().body(response);
			}
			if(cuenta.getEstadoCuenta()!=1) {
				response.put("mensaje", 2);
				return ResponseEntity.ok().body(response);
			}
			GeneradorToken generadorToken=new GeneradorToken();
			String jwt=generadorToken.generarToken4(cuenta.getUsuarioCuenta(),cuenta.getPersona().getIdPersona(),cuenta.getIdCuenta());
			Token token= new Token();
            token.setToken(jwt);
            token.setCuenta(cuenta);
            tokenServicio.crearToken(token);
            UltimoIngresoCuenta ultimoIngresoCuenta=new UltimoIngresoCuenta();
            ultimoIngresoCuenta.setCuenta(cuenta);
            ultimoIngresoCuenta.setIpUltimoIngresoCuenta(request.getRemoteAddr());
            ultimoIngresoCuenta.setNavegadorUltimoIngresoCuenta(request.getHeader("User-Agent"));
            ultimoIngresoCuentaServicio.crear(ultimoIngresoCuenta);
            response.put("accessToken", jwt);
			return ResponseEntity.ok().body(response);
		}catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> salir(@RequestBody Map<String, String> map){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			String accessToken=map.get("accessToken");
			Token token = tokenServicio.obtenerPorToken(accessToken);
			if(token==null) {
				response.put("mensaje", 2);
				return ResponseEntity.ok().body(response);
			}
			tokenServicio.eliminarToken(token);
			response.put("mensaje", 1);
			return ResponseEntity.ok().body(response);
			
			
		}catch(Exception ex) {
			response.put("error", "servicio mal consumido "+ex.hashCode());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PostMapping("/recuperacionclave")
    //metodo que envia el correo para la recuperacion de la clave y tambien envia el link
    public ResponseEntity<?> enviarCorreo(@RequestBody Map<String, String> map){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			String usuario=map.get("username");
			List<Object[]> nombreYcorreo=ingresoServicio.obtenerNombreYCorreo(usuario);
			Cuenta cuenta= cuentaServicio.obtenerPorUsuario(usuario);
			if(cuenta==null) {
				response.put("mensaje", 2);
				return ResponseEntity.ok().body(response);
			}
			if(nombreYcorreo.isEmpty()) {
				response.put("mensaje", 3);
				return ResponseEntity.ok().body(response);
			}
			GeneradorToken generadorToken=new GeneradorToken();
			String jwt=generadorToken.generarToken(cuenta.getUsuarioCuenta());
			Token token= new Token();
            token.setToken(jwt);
            token.setCuenta(cuenta);
            tokenServicio.crearToken(token);
            tokenServicio.caducarToken(token);
	        String link= "http://181.143.139.108:4200/changepass/";
	        link+=jwt;
	        correo.enviarCorreoRecuperacion(nombreYcorreo.get(0)[0].toString(), nombreYcorreo.get(0)[1].toString(), link);
	        response.put("mensaje", 1);
	        return ResponseEntity.ok().body(response);
		} catch (AddressException ex) {
            response.put("error", "el correo no existe: "+ex.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (MessagingException ex) {
            response.put("error", "no se envio el correo: "+ex.getMessage());
            return ResponseEntity.badRequest().body(response);
        }catch(Exception ex) {
			response.put("error", ex.toString());
			return ResponseEntity.badRequest().body(response);
		}
    }
	
	@PostMapping("/token")
	public ResponseEntity<?> verificarToken(@RequestBody Map<String, String> map){
		HashMap<String, Object> response = new HashMap<String, Object>();
		try {
			String accessToken=map.get("accessToken");
			Token token = tokenServicio.obtenerPorToken(accessToken);
			if(token==null) {
				response.put("mensaje", 2);
				return ResponseEntity.ok().body(response);
			}else{
				response.put("mensaje", 1);
				return ResponseEntity.ok().body(response);
			}
		}catch(Exception ex) {
			response.put("error", "servicio mal consumido "+ex.hashCode());
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	
}


