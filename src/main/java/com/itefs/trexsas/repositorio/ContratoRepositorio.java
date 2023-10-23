package com.itefs.trexsas.repositorio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.Contrato;

public interface ContratoRepositorio extends JpaRepository<Contrato, Long> {

	@Query("select c.idContrato,"
			+ "c.numeracionContrato,"
			+ "c.estadoContrato,"
			+ "c.valorContrato, "
			+ "c.contadorContrato,"
			+ "c.consecutivoContrato,"
			+ "c.responsable,"
			+ "c.telefonoResponsable,"
			+ "c.consecutivoContrato,"
			+ "c.objetoContrato,"
			+ "c.fechaInicioContrato,"
			+ "c.fechaFinContrato,"
			+ "c.tipoContrato,"
			+ "c.cliente.razonSocialCliente,"
			+ "c.cliente.nitCliente,"
			+ "c.ciudad,"
			+ "c.cliente.idCliente,"
			+ "c.noContrato "
			+ "from Contrato c order by c.noContrato desc")
	List<Object[]> listarContratosPaginado();
	
}
