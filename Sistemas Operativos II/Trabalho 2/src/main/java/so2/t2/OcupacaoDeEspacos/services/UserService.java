package so2.t2.OcupacaoDeEspacos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import so2.t2.OcupacaoDeEspacos.models.Corporation;
import so2.t2.OcupacaoDeEspacos.models.RecordPOJO;
import so2.t2.OcupacaoDeEspacos.repositories.CorporationRepository;
import so2.t2.OcupacaoDeEspacos.repositories.OccupationRepository;

@Service
public class UserService {
    @Autowired
    private CorporationRepository corpRep;

    @Autowired
    private OccupationRepository occupRep;

    public List<Corporation> findAllCorps() {
        return corpRep.findAll();
    }

    public List<RecordPOJO> findRecordsByUsername(String userName) {
        return occupRep.findByUserWithName(userName);
    }
}