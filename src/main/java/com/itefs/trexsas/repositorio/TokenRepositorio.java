package com.itefs.trexsas.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.Token;

public interface TokenRepositorio extends JpaRepository<Token, Long> {
	
	@Query("select per.idPersona from Token t join t.cuenta cu join cu.persona per where t.token = ?1")
	Optional<Long> encontrarPorToken2(String token);
	
	@Query("select t from Token t where t.token = ?1")
	Optional<Token> encontrarPorToken(String token);
	
	
	
	//permisos
	
	//vehiculo
	@Query("select uxp.accesoUriXPerfil from Token t "
			+ "join t.cuenta cu "
			+ "join cu.perfilList pf "
			+ "join pf.uriXPerfilList uxp "
			+ "join uxp.uriHija uh "
			+ "where t.token = ?1 and uh.idUriHija=17 and uxp.accesoUriXPerfil!=0")
	List<Integer> permisoListarVehiculo(String token);
	
	@Query("select pr.idPropietario from Token t "
			+ "join t.cuenta cu "
			+ "join cu.persona p "
			+ "join p.propietario pr "
			+ "where t.token = ?1")
	List<Long> esPropietario(String token);
}
