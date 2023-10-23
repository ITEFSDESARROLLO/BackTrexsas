package com.itefs.trexsas.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.PQR;

public interface PQRRepository extends JpaRepository<PQR,Long>
{
	@Query("select p.id,p.descripcion,p.tipo,p.fechaPublicacion,p.redactor "
			+ "from PQR p "
			+ "order by p.id desc")
	Page<Object[]> listarPQRPaginado(Pageable pageable);

}
