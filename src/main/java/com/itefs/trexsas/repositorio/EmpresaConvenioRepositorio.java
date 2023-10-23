package com.itefs.trexsas.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.EmpresaConvenio;

public interface EmpresaConvenioRepositorio extends JpaRepository<EmpresaConvenio, Integer> {
	
	@Query("select c.idEmpresaConvenio,c.nombreEmpresaConvenio,c.nitEmpresaConvenio,c.estadoEmpresaConvenio "
			+ "from EmpresaConvenio c "
			+ "where c.estadoEmpresaConvenio!=2")
	Page<Object[]> listarEmpresasPaginado(Pageable pageable);
	
	@Query("select c.idEmpresaConvenio,c.nombreEmpresaConvenio,c.nitEmpresaConvenio,c.estadoEmpresaConvenio "
			+ "from EmpresaConvenio c "
			+ "where c.estadoEmpresaConvenio!=2 and c.idEmpresaConvenio =?1 ")
	Page<Object[]> listarEmpresasPaginadoCodigo(Pageable pageable,Integer codigo);
	
	@Query("select c.idEmpresaConvenio,c.nombreEmpresaConvenio,c.nitEmpresaConvenio,c.estadoEmpresaConvenio "
			+ "from EmpresaConvenio c "
			+ "where c.estadoEmpresaConvenio =?1 ")
	Page<Object[]> listarEmpresasPaginadoEstado(Pageable pageable, Integer estado);
	
	@Query("select c.idEmpresaConvenio,c.nombreEmpresaConvenio,c.nitEmpresaConvenio,c.estadoEmpresaConvenio "
			+ "from EmpresaConvenio c "
			+ "where c.estadoEmpresaConvenio!=2 and c.nitEmpresaConvenio =?1 ")
	Page<Object[]> listarEmpresasPaginadoNit(Pageable pageable,String codigo);
}
