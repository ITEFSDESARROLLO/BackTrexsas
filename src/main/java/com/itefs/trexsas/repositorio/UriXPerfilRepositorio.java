package com.itefs.trexsas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.UriXPerfil;

public interface UriXPerfilRepositorio extends JpaRepository<UriXPerfil, Long> {
	
	@Query(nativeQuery = true, value ="select uri_padre.id_uri_padre, uri_padre.uri_padre,uri_padre.descripcion_uri_padre,\r\n"
			+ "uri_hija.id_uri_hija, uri_hija.uri_hija,uri_hija.descripcion_uri_hija\r\n"
			+ "from uri_padre\r\n"
			+ "join uri_hija on uri_hija.uri_padre_id_uri_padre = uri_padre.id_uri_padre\r\n"
			+ "where uri_padre.id_uri_padre != 23 and uri_padre.id_uri_padre != 22 and uri_padre.id_uri_padre != 24")
	List<Object[]> listarUrl();
	
	@Query(nativeQuery = true, value ="select uri_padre.id_uri_padre, uri_padre.uri_padre,uri_padre.descripcion_uri_padre,\r\n"
			+ "uri_hija.id_uri_hija, uri_hija.uri_hija,uri_hija.descripcion_uri_hija,uri_x_perfil.perfil_id_perfil, uri_x_perfil.acceso_uri_x_perfil,uri_x_perfil.id_uri_x_perfil\r\n"
			+ "from uri_padre\r\n"
			+ "join uri_hija on uri_hija.uri_padre_id_uri_padre = uri_padre.id_uri_padre\r\n"
			+ "join uri_x_perfil on uri_x_perfil.uri_hija_id_uri_hija = uri_hija.id_uri_hija\r\n"
			+ "where uri_padre.id_uri_padre != 23 and uri_padre.id_uri_padre != 22 and uri_padre.id_uri_padre != 24 and uri_x_perfil.perfil_id_perfil = ?1")
	List<Object[]> listarUrlPorPerfil(Integer idPerfil);
	
}
