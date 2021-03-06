package models.apostas.odd.resultados.intervalo;

import models.apostas.Calculadora;
import models.apostas.Odd;
import models.apostas.Taxa;
import models.apostas.mercado.Mercado;
import models.apostas.mercado.ResultadoIntervaloMercado;
import models.eventos.futebol.ResultadoFutebol;

import javax.persistence.Entity;

@Entity
public class ForaIntervaloOdd extends Odd<ResultadoIntervaloMercado.Posicao> {

    private static final String DESCRICAO = "Ganha quando casa perde no primeiro tempo";

    private static final String NOME = "Fora1T";

    private static final String ABREVIACAO = "F1T";

    public ForaIntervaloOdd() {
    }

    public ForaIntervaloOdd(String codigo) {
        super(codigo);
    }

    @Override
    public String getNome() {
        return NOME;
    }

    @Override
    public Mercado getMercado() {
        return Mercado.ResultadoIntervalo;
    }

    @Override
    public String getAbreviacao() {
        return ABREVIACAO;
    }

    @Override
    public String getDescricao() {
        return DESCRICAO;
    }

    @Override
    public ResultadoIntervaloMercado.Posicao getPosicao() {
        return ResultadoIntervaloMercado.Posicao.FORA;
    }

    @Override
    public Calculadora getCalculadora(Taxa taxa) {

        return new CalculadoraM();
    }

    public class CalculadoraM implements Calculadora<ResultadoFutebol> {
        @Override
        public boolean calcular(ResultadoFutebol resultado) {
            Long pontosCasa = resultado.casaPrimeiroTempo.getPontos();
            Long pontosFora = resultado.foraPrimeiroTempo.getPontos();
            return pontosCasa < pontosFora;
        }
    }
}
