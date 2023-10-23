package com.itefs.trexsas.repositorio;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.CuentaCobro;

public interface CuentaCobroRepositorio extends JpaRepository<CuentaCobro, Long> {
	
	
	@Query("select c.idCuentaCobro,c.numeroCuentaCobro,c.fechaCuentaCobro,c.nombreCuentaCobro,c.nitCuentaCobro,c.conceptoCuentaCobro,c.valorCuentaCobro,c.cuentaCobro,c.estadoCuentaCobro "
			+ "from CuentaCobro c "
			+ "order by c.idCuentaCobro desc")
	Page<Object[]> listarCuentasCobroPaginado(Pageable pageable);
	
	@Query(nativeQuery = true, value = 
			"select \r\n"
			+ "	DATE_FORMAT(cc.fecha_cuenta_cobro,'%d de %M de %Y'),"
			+ "cc.numero_cuenta_cobro,"
			+ "cc.valor_cuenta_cobro,"
			+ "cc.nombre_cuenta_cobro,"
			+ "cc.nit_cuenta_cobro,"
			+ "cc.concepto_cuenta_cobro\r\n"
			+ "	from cuenta_cobro cc where cc.id_cuenta_cobro=?1")
	List<Object[]> datosPdf(Long id);
	
	@Query(nativeQuery=true, value="select * from trexdb_prod.cuenta_cobro order by id_cuenta_cobro desc limit 1;")
	CuentaCobro traerUltimoRegistro();
	
}
