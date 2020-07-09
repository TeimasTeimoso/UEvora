package so2.t2.OcupacaoDeEspacos.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/* Entidade que representa uma Empresa/Grupo */
@Entity
public class Corporation {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    /* Valor da entrada é unico */
    @Column(unique = true, nullable = false)
    private String name;

    /* Delega o trabalho para o atributo da loja
    Quando uma corporation é eliminado tmb a loja é */
    @OneToMany(mappedBy = "corp", cascade = CascadeType.REMOVE)
    List<Store> stores;

    @OneToMany(mappedBy = "corpId", cascade = CascadeType.REMOVE)
    List<Occupation> ocup;

    protected Corporation() {}

    public Corporation(String name) {
        this.name = name;
        this.stores = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}