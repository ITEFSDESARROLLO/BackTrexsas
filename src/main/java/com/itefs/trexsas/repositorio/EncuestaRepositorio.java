package com.itefs.trexsas.repositorio;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.Encuesta;

public interface EncuestaRepositorio extends JpaRepository<Encuesta, Long> 
{
	@Query("select e.id,e.nombreEncuesta, u.nombrePerfil "
			+ "from Encuesta e "
			+ "right join Perfil u "
			+ "on e.perfilUsuario = u.idPerfil "
			+ "order by e.id desc")
	Page<Object[]> listarEncuestasPaginado(Pageable pageable);
	
	@Query("select e.id,e.nombreEncuesta, u.nombrePerfil "
			+ "from Encuesta e "
			+ "right join Perfil u "
			+ "on e.perfilUsuario = u.idPerfil "
			+ "order by e.id desc")
	List<Object[]> listarEncuestas();
	

}
