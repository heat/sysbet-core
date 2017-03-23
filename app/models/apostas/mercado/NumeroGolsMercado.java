package models.apostas.mercado;

public class NumeroGolsMercado extends Mercado {

    private static final String NOME = "Número de Gols";

    private static final TipoMercado TIPO = TipoMercado.L;

    public NumeroGolsMercado() {
        super(NOME, TIPO);
    }

    public enum Posicao {
        ABAIXO, ACIMA
    }
}
