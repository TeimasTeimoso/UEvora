package so2.t2.OcupacaoDeEspacos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;

import so2.t2.OcupacaoDeEspacos.models.Corporation;
import so2.t2.OcupacaoDeEspacos.models.Store;
import so2.t2.OcupacaoDeEspacos.repositories.CorporationRepository;
import so2.t2.OcupacaoDeEspacos.repositories.StoreRepository;


@SpringBootApplication
public class OcupacaoDeEspacosApplication {

	public static void main(String[] args) {
		SpringApplication.run(OcupacaoDeEspacosApplication.class, args);
	}


	/*
	@Bean
	public CommandLineRunner demo(CorporationRepository repository, StoreRepository StoreRep) {
		return (args) -> {
			Corporation p = new Corporation("Pingo Doce");
			Corporation c = new Corporation("Continente");
			Corporation a = new Corporation("Auchan");

			repository.save(p);
			repository.save(c);
			repository.save(a);

			StoreRep.save(new Store("Praça Angra do Heroísmo 1, 7000-121 Évora", "Bairro Senhora da Glória", p, 38.56936,-7.92148));
			StoreRep.save(new Store("Av. Bento Jesus Caraça 1 Lote 1, Bairro Dos Álamos, 7000-334 Évora", "Álamos", p, 38.5741, -7.89973));
			StoreRep.save(new Store("Rua Armando Antunes da Silva 16, 7005-145 Évora, Portugal", "Évora", c, 38.55776, -7.92104));
			StoreRep.save(new Store("R. da Horta das Figueiras 2, 7000-790 Évora", "Horta das Figueiras", p, 38.56026, -7.91392));
			StoreRep.save(new Store("R. Luís Adelino Fonseca nº 2 piso 0, 7005-345 Évora", "Évora", a, 38.54959, -7.90533));
		};
	}
	*/
}