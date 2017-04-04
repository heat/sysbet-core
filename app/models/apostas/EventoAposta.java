package models.apostas;

import models.eventos.Evento;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Entity
@Table( name = "evento_apostas")
public class EventoAposta implements Apostavel<Evento>, Serializable{

    @Id
    @Column(name = "evento_aposta_id",updatable = false)
    private Long id;

    @Column(name = "tenant_id")
    private Long tenant;

    @OneToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;

    @Column(name = "permitir")
    private Boolean permitir;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao")
    private Situacao situacao;

    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name = "evento_aposta_id", nullable = false, updatable = true, insertable = true)
    private List<Taxa> taxas = new ArrayList<>();

    public EventoAposta() {
        this.taxas = new ArrayList<>();
    }

    public EventoAposta(Evento evento) {
        this(evento, true, new ArrayList<>());
    }

    public EventoAposta(Evento evento, boolean permitir, List<Taxa> taxas) {
        this.id = evento.getId();
        this.evento = evento;
        this.permitir = permitir;
        this.taxas = taxas;
    }

    public Long getId() {
        return id;
    }

    public Long getTenant() {
        return tenant;
    }

    public void setTenant(Long tenant) {
        this.tenant = tenant;
    }

    public boolean isPermitir() {
        return permitir;
    }

    public void setPermitir(boolean permitir) {
        this.permitir = permitir;
    }

    public List<Taxa> getTaxas() {
        return taxas;
    }

    public void setTaxas(List<Taxa> taxas) {
        this.taxas = taxas;
    }

    public void addTaxa(Taxa taxa){
        int idx = taxas.indexOf(taxa);

        if (idx >= 0){
            Taxa t = taxas.get(idx);
            t.setVisivel(taxa.isVisivel());
            t.setTaxa(taxa.getTaxa());
            t.setAlteradoEm(Calendar.getInstance());
        } else {
            taxas.add(taxa);
        }

    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventoAposta that = (EventoAposta) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return tenant != null ? tenant.equals(that.tenant) : that.tenant == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (tenant != null ? tenant.hashCode() : 0);
        return result;
    }

    public static EventoAposta of(Evento e) {
        return new EventoAposta(e);
    }
}
