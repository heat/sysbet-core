package models.apostas.odd.resultados.intervalofinal;

import models.apostas.Calculadora;
import models.apostas.Odd;
import models.apostas.Taxa;
import models.apostas.mercado.Mercado;
import models.apostas.mercado.ResultadoIntervaloFinalMercado;
import models.eventos.futebol.ResultadoFutebol;

import javax.persistence.Entity;

@Entity
public class ForaEmpateIntervaloFinalOdd extends Odd<ResultadoIntervaloFinalMercado.Posicao> {

    private static final String DESCRICAO = "Ganha quando casa perde no primeiro e empata no segundo tempo";

    private static final String NOME = "Fora - Empate";

    private static final String ABREVIACAO = "F-E";

    public ForaEmpateIntervaloFinalOdd() {
    }

    public ForaEmpateIntervaloFinalOdd(String codigo) {
        super(codigo);
    }

    @Override
    public String getNome() {
        return NOME;
    }

    @Override
    public Mercado getMercado() {
        return Mercado.ResultadoIntervaloFinal;
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
    public ResultadoIntervaloFinalMercado.Posicao getPosicao() {
        return ResultadoIntervaloFinalMercado.Posicao.FORA_EMPATE;
    }

    @Override
    public Calculadora getCalculadora(Taxa taxa) {

        return new CalculadoraM();
    }

    public class CalculadoraM implements Calculadora<ResultadoFutebol> {
        @Override
        public boolean calcular(ResultadoFutebol resultado) {
            Long pontosCasaPrimeiro = resultado.casaPrimeiroTempo.getPontos();
            Long pontosForaPrimeiro = resultado.foraPrimeiroTempo.getPontos();
            Long pontosCasaSegundo = resultado.casaSegundoTempo.getPontos();
            Long pontosForaSegundo = resultado.foraSegundoTempo.getPontos();
            return (pontosCasaPrimeiro < pontosForaPrimeiro) && (pontosCasaSegundo == pontosForaSegundo);
        }
    }
}
