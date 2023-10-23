package com.itefs.trexsas.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.itefs.trexsas.modelo.Token;
import com.itefs.trexsas.repositorio.TokenRepositorio;

@Service
public class TokenServicio {

	@Autowired
	private TokenRepositorio tokenRepositorio;
	
	public void crearToken(Token token) {
		token.setIdToken(null);
		tokenRepositorio.save(token);
	}
	
	public void actualizarToken(Token token) {
		tokenRepositorio.save(token);
	}
	public Token obtenerPorid(long accessToken) {
		Optional<Token> opToken=tokenRepositorio.findById(accessToken);
		if(opToken.isPresent()) {
			return opToken.get();
		}
		return null;
	}
	public Token obtenerPorToken(String accessToken) {
		Optional<Token> opToken=tokenRepositorio.encontrarPorToken(accessToken);
		if(opToken.isPresent()) {
			return opToken.get();
		}
		return null;
	}
	
	public Long existeToken(String accessToken) {
		Optional<Long> opId=tokenRepositorio.encontrarPorToken2(accessToken);
		if(opId.isPresent()) {
			return opId.get();
		}
		return null;
	}
	
	public void eliminarToken(Token token) {
		tokenRepositorio.delete(token);
	}
	
	//permisos
	
	//vehiculo
	
	public List<Integer> permisoListarVehiculo(String token) {
		List<Integer> permisos=tokenRepositorio.permisoListarVehiculo(token);
		return permisos;
	}
	
	
	public Boolean esPropietario(String token) {
		List<Long> op=tokenRepositorio.esPropietario(token);
		if(op.size()>0) {
			return true;
		}else {
			return false;
		}
	}
	
	
	
	@Async
    public void caducarToken(Token token) {
        try {
            Thread.sleep(30*60*1000);
            tokenRepositorio.delete(token);
            System.out.println("sesion cerrada");
        } catch (InterruptedException ex) {
        	System.out.println("paso algo con el servicio asincrono: "+ex.getMessage());
        }
    }
}
