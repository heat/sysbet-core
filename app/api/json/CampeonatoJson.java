package api.json;

import models.eventos.Campeonato;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class CampeonatoJson implements Serializable, Convertable<Campeonato>, Jsonable {

    public static final String TIPO = "campeonatos";

    public final String id;

    public final String nome;
    
    public CampeonatoJson(String id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public CampeonatoJson() {
        id = null;
        nome = null;
    }


    @Override
    public String type() {
        return TIPO;
    }

    public static CampeonatoJson of(Campeonato campeonato) {

        return new CampeonatoJson(String.valueOf(campeonato.getId()), campeonato.getNome());
    }

    public static List<Jsonable> of(List<Campeonato> campeonatos) {
        return campeonatos.stream().map( c -> CampeonatoJson.of(c) ).collect(Collectors.toList());
    }
    public Campeonato to() {
        return new Campeonato(null, nome);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CampeonatoJson that = (CampeonatoJson) o;

        if (!id.equals(that.id)) return false;
        return nome.equals(that.nome);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + nome.hashCode();
        return result;
    }
}
