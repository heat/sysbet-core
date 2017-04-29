package api.rest;

import actions.TenantAction;
import api.json.ObjectJson;
import api.json.TaxaJogoJson;
import api.json.TaxaJson;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import controllers.ApplicationController;
import dominio.processadores.apostas.TaxaAtualizarProcessador;
import dominio.processadores.apostas.TaxaInserirProcessador;
import dominio.validadores.Validador;
import dominio.validadores.exceptions.ValidadorExcpetion;
import models.apostas.EventoAposta;
import models.apostas.Odd;
import models.apostas.Taxa;
import models.seguranca.RegistroAplicativo;
import models.vo.Tenant;
import org.pac4j.play.java.Secure;
import org.pac4j.play.store.PlaySessionStore;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import repositories.*;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@With(TenantAction.class)
public class TaxaJogoController extends ApplicationController {

    TaxaRepository taxaRepository;
    TaxaInserirProcessador inserirProcessador;
    TaxaAtualizarProcessador atualizarProcessador;
    ValidadorRepository validadorRepository;
    EventoApostaRepository eventoApostaRepository;
    OddRepository oddRepository;
    TenantRepository tenantRepository;

    @Inject

    public TaxaJogoController(PlaySessionStore playSessionStore, TaxaRepository taxaRepository,
                              TaxaInserirProcessador inserirProcessador, TaxaAtualizarProcessador atualizarProcessador,
                              ValidadorRepository validadorRepository, EventoApostaRepository eventoApostaRepository,
                              OddRepository oddRepository, TenantRepository tenantRepository) {
        super(playSessionStore);
        this.taxaRepository = taxaRepository;
        this.inserirProcessador = inserirProcessador;
        this.atualizarProcessador = atualizarProcessador;
        this.validadorRepository = validadorRepository;
        this.eventoApostaRepository = eventoApostaRepository;
        this.oddRepository = oddRepository;
        this.tenantRepository = tenantRepository;

    }

    @Transactional
    public Result buscar(Long id) {

        Optional<String> appKeyOptional = Optional.ofNullable(request().getHeader("X-AppCode"));

        if (!appKeyOptional.isPresent()){
            return badRequest("Key not found.");
        }

        Optional<RegistroAplicativo> registroAplicativoOptional = tenantRepository.buscar(appKeyOptional.get());

        if (!registroAplicativoOptional.isPresent()){
            return notFound("Aplicativo não registrado.");
        }

        Tenant tenant = Tenant.of(registroAplicativoOptional.get().getTenant());


        Optional<Taxa> taxaOptional = taxaRepository.buscar(tenant, id);

        if(!taxaOptional.isPresent())
            return badRequest("Taxa não encontrada!");

        ObjectJson.JsonBuilder<TaxaJogoJson> builder = ObjectJson.build(TaxaJogoJson.TIPO, ObjectJson.JsonBuilderPolicy.OBJECT);
        Taxa taxa = taxaOptional.get();
        builder.comEntidade(TaxaJogoJson.of(taxa, taxa.getEventoAposta()));
        JsonNode retorno = builder.build();
        return created(retorno);

    }

}