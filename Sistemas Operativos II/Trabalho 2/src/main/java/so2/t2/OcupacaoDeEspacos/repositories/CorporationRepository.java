package so2.t2.OcupacaoDeEspacos.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import so2.t2.OcupacaoDeEspacos.models.Corporation;

/* As declaracoes feitas na interface sao implementadas 
automaticamente pelo JPA */
public interface CorporationRepository extends CrudRepository<Corporation, Long>{
    
    List<Corporation> findAll();

    Corporation findByName(String Name);
    
    /* Previne Null Excepetion */
    Optional<Corporation> findById(Long id);

}