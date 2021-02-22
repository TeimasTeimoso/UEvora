package so2.t2.OcupacaoDeEspacos.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import so2.t2.OcupacaoDeEspacos.models.Corporation;
import so2.t2.OcupacaoDeEspacos.models.Occupation;
import so2.t2.OcupacaoDeEspacos.models.RecordPOJO;
import so2.t2.OcupacaoDeEspacos.services.UserService;


@Controller
/* Base de todas as paginas*/
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userServ;
    
    @GetMapping("/registar-ocupacao")
    public String registerForm(Model model) {

        List<Corporation> corps = userServ.findAllCorps();
        Occupation o = new Occupation();

        model.addAttribute("corps", corps);
        model.addAttribute("occupation", o);
        model.addAttribute("title", "Registar Ocupação");

        return "registerOccupation";

    }

    @GetMapping("/meus-registos")
    public String getMyRecords(Model model, HttpServletRequest hServletRequest) {
        
        List<RecordPOJO> records = userServ.findRecordsByUsername(hServletRequest.getRemoteUser());

        model.addAttribute("title", "Meus Registos");
        model.addAttribute("records", records);

        return "myRecords";
    }

}