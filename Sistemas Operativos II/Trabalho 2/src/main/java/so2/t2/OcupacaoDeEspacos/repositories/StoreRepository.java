package so2.t2.OcupacaoDeEspacos.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import so2.t2.OcupacaoDeEspacos.models.Corporation;
import so2.t2.OcupacaoDeEspacos.models.Store;

public interface StoreRepository extends CrudRepository<Store, String> {
    
    List<Store> findAll();

    List<Store> findByCorp(Optional<Corporation> corp);
    
    Store findByCorpAndLocation(Corporation corp, String locat);

}