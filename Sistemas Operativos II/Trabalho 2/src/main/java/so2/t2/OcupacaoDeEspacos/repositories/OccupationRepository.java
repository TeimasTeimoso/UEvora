package so2.t2.OcupacaoDeEspacos.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import so2.t2.OcupacaoDeEspacos.models.Corporation;
import so2.t2.OcupacaoDeEspacos.models.Occupation;
import so2.t2.OcupacaoDeEspacos.models.RecordPOJO;

public interface OccupationRepository extends CrudRepository<Occupation, Long> {

    /* Anotacao @Query permite descrever querys usando jpql
     Query para fazer join entre a tabela dos registos e das empresas */
    @Query("select new so2.t2.OcupacaoDeEspacos.models.RecordPOJO(o.entryId, o.userId, c.name, o.locat, o.level, o.day, o.hours) from Occupation o join o.corpId c where o.userId = ?1")
    List<RecordPOJO> findByUserWithName(String userId);

    List<Occupation> findByCorpIdAndLocatAndDayBetween(Corporation corpId, String storeLocation, LocalDate before, LocalDate afer);

    List<Occupation> findByCorpIdAndLocat(Corporation corpId, String storeLocation);

    List<Occupation> findAll();

    Occupation findByUserIdAndEntryId(String userId, Long entryId);

}