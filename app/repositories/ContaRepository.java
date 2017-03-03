package repositories;


import com.google.inject.Inject;
import models.apostas.Odd;
import models.financeiro.Conta;
import models.vo.Confirmacao;
import models.vo.Tenant;
import play.db.jpa.JPAApi;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ContaRepository implements Repository<Long, Conta>{

    JPAApi jpaApi;

    @Inject
    public ContaRepository(JPAApi jpaApi) {

        this.jpaApi = jpaApi;
    }

    @Override
    public List<Conta> todos(Tenant tenant) {
        return null;
    }

    @Override
    public Optional<Conta> buscar(Tenant tenant, Long idUsuario) {

        try {
            EntityManager em = jpaApi.em();
            TypedQuery<Conta> query = em.createQuery("SELECT c FROM Conta c WHERE c.proprietario.id = :idUsuario ", Conta.class);
            query.setParameter("idUsuario", idUsuario);
            return Optional.ofNullable(query.getSingleResult());

        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public CompletableFuture<Conta> atualizar(Tenant tenant, Long id, Conta updetable) {
        return null;
    }

    @Override
    public CompletableFuture<Conta> inserir(Tenant tenant, Conta novo) {
        return null;
    }

    @Override
    public CompletableFuture<Confirmacao> excluir(Tenant tenant, Long id) {
        return null;
    }

}