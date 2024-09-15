package com.impulsart.ImpulsArtApp.repositories;

import com.impulsart.ImpulsArtApp.entities.Obra;
import com.impulsart.ImpulsArtApp.entities.Oferta;
import com.impulsart.ImpulsArtApp.entities.Subasta;
import com.impulsart.ImpulsArtApp.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository

public interface SubastaRepository extends JpaRepository<Subasta, Long> {

    //Historial de obras en subasta
    @Query("SELECT s FROM Subasta s " +
            "INNER JOIN s.obras o " +
            "INNER JOIN o.usuarios u " +
            "WHERE u.identificacion = :identificacion")
    List<Subasta> findHistorialObrasSubasta(@Param("identificacion") Integer identificacion);
    //Historial de obras en subasta

    //Buscar obras con subasta por id
    @Query("SELECT s FROM Subasta s INNER JOIN s.obras o WHERE s.pkCodSubasta = :pkCodSubasta")
    List<Subasta> findSubastaByIdWithObras(@Param("pkCodSubasta") Long pkCodSubasta);
    //Buscar obras con subasta por id

    //BUSCAR EL USUARIO QUE CREO LA SUBASTA
    @Query("SELECT u FROM Usuario u " +
            "JOIN u.obras o " +
            "JOIN o.subastas s " +
            "WHERE s.pkCodSubasta = :pkCodSubasta")
    Usuario findUsuarioBySubastaId(@Param("pkCodSubasta") Long pkCodSubasta);
    //BUSCAR EL USUARIO QUE CREO LA SUBASTA

    //Buscar subastas en estado activo y con fecha de finalizacion vencidas
    @Query("SELECT s FROM Subasta s WHERE s.estadoSubasta = 'Activo' AND s.fechaFinalizacion <= :ahora")
    List<Subasta> findSubastasActivasParaFinalizar(@Param("ahora") LocalDateTime ahora);
    //Buscar subastas en estado activo y con fecha de finalizacion vencidas

    List<Subasta> findByEstadoSubastaContainingIgnoreCase(String estadoSubasta);

}
