package so2.t2.OcupacaoDeEspacos.controllers;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import so2.t2.OcupacaoDeEspacos.models.Corporation;
import so2.t2.OcupacaoDeEspacos.models.Occupation;
import so2.t2.OcupacaoDeEspacos.services.ApiService;

/* Endpoint da API tem o prefixo /api
E utiliza o serviço da API */
@RestController
@RequestMapping("/api")
public class ApiController {
    
    @Autowired
    ApiService apiService;

    @GetMapping(value = "/localizacao/{id}", produces="application/json")
    public List<String> loadLocationAPI(@PathVariable Long id) {
        
        List<String> result = apiService.loadLocation(id);

        return result;
    }   

    @GetMapping(value = "/procurar-ocupacao", params = {"id", "locat"}, produces="application/json")
    public HashMap<String, Integer> getOccupationRegistersAPI(@RequestParam Corporation id, @RequestParam String locat) {

        HashMap<String, Integer> result = apiService.getOccupation(id, locat);

        return result;

    }

    @GetMapping(value = "/coordenadas", produces="application/json")
    public List<Double> getcoordinatesAPI(@RequestParam Corporation id, @RequestParam String locat) {
        
        List<Double> result = apiService.getcoordinates(id, locat);

        return result;
    }   


    @PostMapping("/registar-ocupacao")
    public ResponseEntity<Void> registerOccupationAPI(@ModelAttribute Occupation occupation, HttpServletRequest hServletRequest) {

        apiService.registerOccupation(occupation, hServletRequest);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @DeleteMapping("/remover-registo/{id}")
    public ResponseEntity<Void> removeAPI(@PathVariable Long id, HttpServletRequest httpServletRequest) {

        ResponseEntity<Void> result = apiService.removeRegister(id, httpServletRequest);

        return result;

    }
    

}