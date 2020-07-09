package so2.t2.OcupacaoDeEspacos.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import so2.t2.OcupacaoDeEspacos.models.Corporation;
import so2.t2.OcupacaoDeEspacos.models.Occupation;
import so2.t2.OcupacaoDeEspacos.models.Store;
import so2.t2.OcupacaoDeEspacos.repositories.CorporationRepository;
import so2.t2.OcupacaoDeEspacos.repositories.OccupationRepository;
import so2.t2.OcupacaoDeEspacos.repositories.StoreRepository;

@Service
public class ApiService {
    
    @Autowired
    OccupationRepository occupRep;

    @Autowired
    StoreRepository storeRep;

    @Autowired
    CorporationRepository corpRep;

    /* Remove um registo e devolve um codigo de resposta ao pedido http */
    public ResponseEntity<Void> removeRegister(Long id, HttpServletRequest httpServletRequest) {
        Occupation toBeRemoved = occupRep.findByUserIdAndEntryId(httpServletRequest.getRemoteUser(), id);

        if(toBeRemoved != null) {
            occupRep.delete(toBeRemoved);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    /* Procura as localizacoes onde existe uma loja de uma empresa */
    public List<String> loadLocation(Long id) {
        
        List<String> result = new ArrayList<>();
        List<Store> s = storeRep.findByCorp(corpRep.findById(id));

        for(Store store : s) {
            result.add(store.getLocation());
        }

        return result;
    }   

    /* Recupera os valores dos ultimos 2 dias da tabela e verifica se são da ultima hora
     Esta a ser feito com complexidade linear, o que não o torna mt escalavel... */
    public HashMap<String, Integer> getOccupation(Corporation id, String locat) {

        /* Query que devolve todos os registos dos ultimos 2 dias (atual e anterior) de uma determinada loja */
        List<Occupation> occupList = occupRep.findByCorpIdAndLocatAndDayBetween(id, locat, LocalDate.now().minusDays(1), LocalDate.now());

        LocalDateTime currentTime = LocalDateTime.now();

        HashMap<String, Integer> registers = new HashMap<>();

        for (Occupation occupation : occupList) {
            LocalDateTime registerTime =  LocalDateTime.of(occupation.getDay(), occupation.getHours());

            /* Se o tempo do registo +1h for depois do tempo atual, então o registo foi feito há menos de uma hora */
            if(registerTime.plusHours(1).isAfter(currentTime)) {
                int nmbOfOccur = registers.containsKey(occupation.getLevel()) ? registers.get(occupation.getLevel()) : 0;
                registers.put(occupation.getLevel(), nmbOfOccur+1);
            }

        }

        return registers;
    }

    /* Retorna uma lista com as coordenadas de uma loja */
    public List<Double> getcoordinates(Corporation id, String locat){
        List<Double> locatCoord= new ArrayList<Double>();
        Store store = storeRep.findByCorpAndLocation(id, locat);

        locatCoord.add(store.getLatidude());
        locatCoord.add(store.getLongitude());

        return locatCoord;
    }


    /* Dado um objecto pre-populado com info do form, e' colocada a hora e o dia
    e necessario saber tmb quem fez o pedido */
    public void registerOccupation(Occupation occupation, HttpServletRequest hServletRequest) {

        occupation.setDay(LocalDate.now());
        occupation.setHours(LocalTime.now());
        occupation.setUserId(hServletRequest.getRemoteUser());

        occupRep.save(occupation);
    }

}