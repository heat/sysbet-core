package models.apostas;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table( name = "odds")
public class Odd implements Serializable{

    @Id
    @SequenceGenerator(name="odds_odd_id_seq", sequenceName = "odds_odd_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "odds_odd_id_seq")
    @Column(name = "odd_id",updatable = false)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "mercado")
    private String mercado;

    @Column(name = "tipo_linha")
    private char tipoLinha;

    @Column(name = "abreviacao")
    private String abreviacao;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "prioridade")
    private Long prioridade;

    @Column(name = "posicao")
    private Long posicao;

    public Odd() {
    }

    public Odd(String nome, String mercado, char tipoLinha,
               String abreviacao, String descricao, Long prioridade, Long posicao) {

        this.nome = nome;
        this.mercado = mercado;
        this.tipoLinha = tipoLinha;
        this.abreviacao = abreviacao;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.posicao = posicao;
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

    public String getMercado() {
        return mercado;
    }

    public void setMercado(String mercado) {
        this.mercado = mercado;
    }

    public char getTipoLinha() {
        return tipoLinha;
    }

    public void setTipoLinha(char tipoLinha) {
        this.tipoLinha = tipoLinha;
    }

    public String getAbreviacao() {
        return abreviacao;
    }

    public void setAbreviacao(String abreviacao) {
        this.abreviacao = abreviacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Long prioridade) {
        this.prioridade = prioridade;
    }

    public Long getPosicao() {
        return posicao;
    }

    public void setPosicao(Long posicao) {
        this.posicao = posicao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Odd odd = (Odd) o;

        return id != null ? id.equals(odd.id) : odd.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}