package so2.t2.OcupacaoDeEspacos.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import so2.t2.OcupacaoDeEspacos.models.Corporation;
import so2.t2.OcupacaoDeEspacos.models.Occupation;
import so2.t2.OcupacaoDeEspacos.models.UserPOJO;
import so2.t2.OcupacaoDeEspacos.services.HomeService;

/* Controlador de operacoes acessiveis a todos os users 
 Retorna páginas HTML renderizadas no servidor */
@Controller
public class HomeController {

    @Autowired
    private HomeService homeService;

    /* Anotacao GetMapping faz um getRequest para que 
    a diretoria especificada */
    @GetMapping({"/", "/home"})
    public String home(Model model) {

        model.addAttribute("title", "Overcrowded");
        
        return "home";  // Retorna o nome da view
    }

    @GetMapping("/login")
    public String login(Model model) {

        model.addAttribute("title", "Login");
        
        return "login";
    }
    
    @GetMapping("/registar")
    public String getRegister(Model model) {

        model.addAttribute("title", "Registar Utilizador");
        model.addAttribute("userForm", new UserPOJO());


        return "registerUser";
    }

    @PostMapping("/registar")
    public String postUserRegister(@Valid @ModelAttribute UserPOJO userForm, BindingResult result, HttpServletRequest httpServletRequest, RedirectAttributes ra) {

        // Se o nome de utilizador ou pass forem vazios
        if(result.hasErrors()) {
            return "redirect:/registar";
        }

        if(homeService.registerUser(userForm.getUserName(), userForm.getPassword())) {
            ra.addFlashAttribute("userExists", true);
            ra.addFlashAttribute("loginName", userForm.getUserName());
            return "redirect:/registar";
        }

        homeService.authServletRequest(httpServletRequest, userForm.getUserName(), userForm.getPassword());

        return "redirect:/home";
    }

    @GetMapping("/procurar-ocupacao")
    public String searchOccupation(Model model) {
        
        List<Corporation> corps = homeService.findAllCorps();
        Occupation o = new Occupation();

        model.addAttribute("corps", corps);
        model.addAttribute("occupation", o);
        model.addAttribute("title", "Procurar Ocupação");

        return "searchOccupation";
    }

}