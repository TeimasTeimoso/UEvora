package so2.t2.OcupacaoDeEspacos.services;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import so2.t2.OcupacaoDeEspacos.models.Corporation;
import so2.t2.OcupacaoDeEspacos.repositories.CorporationRepository;

@Service
public class HomeService {
    
    @Autowired
    private CorporationRepository corpRep;

    @Autowired
    private JdbcUserDetailsManager jdbcUserDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // retorna todas as empresas
    public List<Corporation> findAllCorps() {
        
        List<Corporation> corps = corpRep.findAll();

        return corps;
    }

    // Se utilizador existir retorna true 
    // Senao insere o user e retorna false
    public Boolean registerUser(String username, String password) {

        try {
            jdbcUserDetailsManager.loadUserByUsername(username);
        } catch (Exception e) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("user"));

            String encodedPwd = passwordEncoder.encode(password);

            User user = new User(username, encodedPwd, authorities);
            jdbcUserDetailsManager.createUser(user);
            return false;
        }

        return true;
    }


    // Metodo usado para logar o utilizador com o pedido
    public void authServletRequest(HttpServletRequest request, String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) {
            System.out.println("Error while logging");
        }
    }
}