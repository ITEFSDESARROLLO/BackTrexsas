package com.itefs.trexsas.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.UltimoIngresoCuenta;

public interface UltimoIngresoCuentaRepositorio extends JpaRepository<UltimoIngresoCuenta, Long> {
	
	@Query("select c.idUltimoIngresoCuenta,per.nombrePersona,per.apellidoPersona,c.ipUltimoIngresoCuenta,c.fechaUltimoIngresoCuenta "
			+ "from UltimoIngresoCuenta c "
			+ "join c.cuenta cu "
			+ "join cu.persona per ")
	Page<Object[]> listarPaginado(Pageable pageable);
	
}
 