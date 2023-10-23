package com.itefs.trexsas.repositorio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.Perfil;

public interface PerfilRepositorio extends JpaRepository<Perfil, Integer> {
	
	@Query(nativeQuery = true, value = 
			"select pr.id_perfil, pr.nombre_perfil, pr.observaciones_perfil from perfil pr "
			+ "join cuenta_perfil cp on pr.id_perfil=cp.perfil_id_perfil "
			+ "join cuenta cu on cp.cuenta_id_cuenta=cu.id_cuenta "
			+ "where cu.id_cuenta = ?1")
	List<Object[]> encontrarPerfilesDeCuenta(int id);
	
	@Query(nativeQuery = true, value = "select distinct uh.uri_hija, up.uri_padre,perm.permiso from "
			+ "token t "
			+ "join cuenta cu on t.cuenta_id_cuenta=cu.id_cuenta "
			+ "join cuenta_perfil cp on cu.id_cuenta=cp.cuenta_id_cuenta "
			+ "join perfil per on cp.perfil_id_perfil=per.id_perfil "
			+ "join uri_x_perfil uxp on per.id_perfil=uxp.perfil_id_perfil "
			+ "join permiso perm on uxp.acceso_uri_x_perfil=perm.id_permiso "
			+ "join uri_hija uh on uxp.uri_hija_id_uri_hija=uh.id_uri_hija "
			+ "join uri_padre up on uh.uri_padre_id_uri_padre=up.id_uri_padre "
			+ "where t.token = ?1 and uxp.acceso_uri_x_perfil!=0")
	List<Object[]>  encontrarAccesosDeCuenta(String token);
	
	@Query("select p.idPerfil,p.nombrePerfil,p.observacionesPerfil,p.fechaRegistroPerfil,p.estadoPerfil from Perfil p where p.estadoPerfil!=2")
	Page<Object[]> listarPerfilesPaginado(Pageable pageable);
	
	@Query("select p.idPerfil,p.nombrePerfil "
			+ "from Perfil p "
			+ "where p.estadoPerfil=1")
	List<Object[]> obtenerInfoBasica();
	
	@Query(nativeQuery=true, value="select uri_padre.id_uri_padre, uri_padre.uri_padre,uri_padre.descripcion_uri_padre,\r\n"
			+ "uri_hija.id_uri_hija, uri_hija.uri_hija,uri_hija.descripcion_uri_hija\r\n"
			+ "from uri_padre\r\n"
			+ "join uri_hija on uri_hija.uri_padre_id_uri_padre = uri_padre.id_uri_padre\r\n"
			+ "where uri_padre.id_uri_padre != 23 and uri_padre.id_uri_padre != 22 and uri_padre.id_uri_padre != 24")
	List<Object[]> traerRutasPagina();

}