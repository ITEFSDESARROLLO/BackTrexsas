package com.itefs.trexsas.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itefs.trexsas.modelo.TarjetaOperacion;

public interface TarjetaOperacionRepositorio extends JpaRepository<TarjetaOperacion, Integer> {

	@Query(nativeQuery=true,value="select top.id_tarjeta_operacion, top.fecha_vencimiento_tarjeta_operacion, v.id_vehiculo, p.nombre_persona, v.estado_vehiculo, p.correo_persona, v.placa_vehiculo "
			+ "from tarjeta_operacion top "
			+ "join vehiculo v on top.vehiculo_id_vehiculo = v.id_vehiculo "
			+ "join persona p on v.propietario_id_propietario = p.id_persona ")
	List<Object[]> traerTarjetasOperacion();
}
