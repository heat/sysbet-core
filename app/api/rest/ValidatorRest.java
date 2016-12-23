package api.rest;

import dominio.processadores.eventos.CampeonatoInserirProcessador;
import dominio.processadores.eventos.EventoInserirProcessador;
import models.vo.Tenant;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.play.PlayWebContext;
import org.pac4j.play.java.Secure;
import org.pac4j.play.store.PlaySessionStore;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.CampeonatoRepository;
import repositories.ValidadorRepository;
import validators.Validador;
import validators.eventos.InsereEventoValidator;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Optional;

public class ValidatorRest extends Controller {


    CampeonatoRepository campeonatoRepository;
    PlaySessionStore playSessionStore;
    CampeonatoInserirProcessador campeonatoInserirProcessador;
    ValidadorRepository validadorRepository;

    @Inject
    public ValidatorRest(CampeonatoRepository campeonatoRepository, PlaySessionStore playSessionStore,
                         CampeonatoInserirProcessador campeonatoInserirProcessador, ValidadorRepository validadorRepository) {
        this.campeonatoRepository = campeonatoRepository;
        this.playSessionStore = playSessionStore;
        this.campeonatoInserirProcessador = campeonatoInserirProcessador;
        this.validadorRepository = validadorRepository;
    }

    @Secure(clients = "headerClient")
    @Transactional
    public Result inserir() {

        Validador validator = new InsereEventoValidator(100L, 10L, true, new BigDecimal(0.2), EventoInserirProcessador.REGRA);
//        Validator validator = new AtualizaEventoValidator(100L, 10L, true, new BigDecimal(0.2), EventoAtualizarProcessador.REGRA);
        validadorRepository.inserir(getTenant().get(), validator);
        return ok("Validator cadastrado! ");
    }

    private Optional<Tenant> getTenant() {

        Optional<CommonProfile> commonProfile = getProfile();
        CommonProfile profile = commonProfile.get();
        return Optional.ofNullable(Tenant.of((Long) profile.getAttribute("TENANT_ID")));
    }

    private Optional<CommonProfile> getProfile() {
        final PlayWebContext context = new PlayWebContext(ctx(), playSessionStore);
        final ProfileManager<CommonProfile> profileManager = new ProfileManager(context);
        return profileManager.get(true);
    }

}