package dominio.processadores.eventos;

import dominio.processadores.Processador;
import models.eventos.Time;
import models.vo.Tenant;
import repositories.TimeRepository;
import dominio.validadores.Validador;
import dominio.validadores.exceptions.ValidadorExcpetion;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class TimeInserirProcessador implements Processador<Tenant, Time> {

    public static final String REGRA = "time.inserir";

    TimeRepository repository;

    @Inject
    public TimeInserirProcessador(TimeRepository repository) {
        this.repository = repository;
    }

    @Override
    public CompletableFuture<Time> executar(Tenant tenant, Time time, List<Validador> validadores) throws ValidadorExcpetion {
        for (Validador validador : validadores) {
            validador.validate(time);
        }

        return repository.inserir(tenant, time);
    }
}
