package api.rest;

import actions.TenantAction;
import api.json.CampeonatoJson;
import api.json.Jsonable;
import api.json.ObjectJson;
import api.json.SolicitacaoSaldoJson;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Strings;
import controllers.ApplicationController;
import dominio.processadores.eventos.CampeonatoAtualizarProcessador;
import dominio.processadores.eventos.CampeonatoInserirProcessador;
import dominio.processadores.financeiro.AdicionarSaldoProcessador;
import dominio.validadores.Validador;
import dominio.validadores.exceptions.ValidadorExcpetion;
import filters.Paginacao;
import models.eventos.Campeonato;
import models.financeiro.Conta;
import models.financeiro.Saldo;
import models.financeiro.SolicitacaoSaldo;
import models.financeiro.debito.AdicionarSaldoDebito;
import models.vo.Chave;
import models.vo.Tenant;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.play.PlayWebContext;
import org.pac4j.play.java.Secure;
import org.pac4j.play.store.PlaySessionStore;
import play.db.jpa.Transactional;
import play.mvc.BodyParser;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import repositories.CampeonatoRepository;
import repositories.ContaRepository;
import repositories.TenantRepository;
import repositories.ValidadorRepository;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static play.libs.Json.toJson;

@With(TenantAction.class)
public class SaldoController extends ApplicationController {


    ValidadorRepository validadorRepository;
    TenantRepository tenantRepository;
    ContaRepository contaRepository;
    AdicionarSaldoProcessador adicionarSaldoProcessador;

    @Inject
    public SaldoController(PlaySessionStore playSessionStore, ValidadorRepository validadorRepository, TenantRepository tenantRepository,
                           ContaRepository contaRepository, AdicionarSaldoProcessador adicionarSaldoProcessador) {
        super(playSessionStore);
        this.validadorRepository = validadorRepository;
        this.tenantRepository = tenantRepository;
        this.contaRepository = contaRepository;
        this.adicionarSaldoProcessador = adicionarSaldoProcessador;
    }

    @Secure(clients = "headerClient")
    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result inserir() {

        JsonNode json = request().body().asJson();
        Long usuario = json.findPath("usuario").longValue();
        BigDecimal valor = json.findPath("valor").decimalValue();

        Optional<Conta> contaOptional = contaRepository.buscar(getTenant(), usuario);

        if (!contaOptional.isPresent()){
            return badRequest("Conta não encontrada.");
        }

        Chave chave = Chave.of(getTenant(), contaOptional.get().getId());

        SolicitacaoSaldo solicitacaoSaldo = new SolicitacaoSaldo(chave.getId(), valor);

        List<Validador> validadores = validadorRepository.todos(getTenant(), AdicionarSaldoProcessador.REGRA);

        try {
            adicionarSaldoProcessador.executar(chave, solicitacaoSaldo, validadores);
        } catch (ValidadorExcpetion validadorExcpetion) {
            return status(Http.Status.UNPROCESSABLE_ENTITY, validadorExcpetion.getMessage());
        }

        JsonNode retorno = ObjectJson.build(SolicitacaoSaldoJson.TIPO, ObjectJson.JsonBuilderPolicy.OBJECT)
                .comEntidade(SolicitacaoSaldoJson.of(solicitacaoSaldo))
                .build();

        return created(retorno);
    }
}