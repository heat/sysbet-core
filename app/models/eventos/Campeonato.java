package models.eventos;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "campeonatos")
public class Campeonato implements Serializable{

    @Id
    @SequenceGenerator(name="campeonatos_idcampeonato_seq", sequenceName = "campeonatos_idcampeonato_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "campeonatos_idcampeonato_seq")
    @Column(name = "campeonato_id",updatable = false)
    private Long id;

    @Column(name="tenant_id")
    private Long tenant;

    @Column
    private String nome;

    @OneToMany(fetch=FetchType.EAGER)
    @JoinColumn(name="eventos_campeonato_id", foreignKey = @ForeignKey(name = "fk_eventos_campeonato_id"))
    private List<Evento> eventos;

    protected Campeonato() {
    }

    public Campeonato(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getTenant() {
        return tenant;
    }

    public void setTenant(Long tenant) {
        this.tenant = tenant;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Campeonato that = (Campeonato) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (tenant != null ? !tenant.equals(that.tenant) : that.tenant != null) return false;
        return nome != null ? nome.equals(that.nome) : that.nome == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (tenant != null ? tenant.hashCode() : 0);
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        return result;
    }


}
