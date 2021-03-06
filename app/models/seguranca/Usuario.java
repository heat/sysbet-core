package models.seguranca;

import com.fasterxml.jackson.annotation.JsonIgnore;
import models.financeiro.comissao.PlanoComissao;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable{

    public enum Status {

        /**
         * Ususário com acesso ao sistema
         */
        ATIVO("ATIVO"),

        /**
         * Usuário com cadastro cancelado
         */
        CANCELADO("CANCELADO");

        private String string;

        Status(String string) {

            this.string = string;
        }
    }
    @Id
    @SequenceGenerator(name="usuarios_usuario_id_seq", sequenceName = "usuarios_usuario_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuarios_usuario_id_seq")
    @Column(name = "usuario_id",updatable = false)
    private Long id;

    @Column(name="tenant_id")
    private Long tenant;

    @Column(name="login")
    private String login;

    @Column(name="senha")
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private Status status = Status.ATIVO;

    @OneToOne
    @JoinColumn(name="papel_id")
    private Papel papel;

    @OneToOne(mappedBy = "usuario")
    private Perfil perfil;

    @OneToOne
    @JoinColumn(name = "plano_comissao_id")
    private PlanoComissao planoComissao;

    public Usuario(){

    }

    public Usuario(String login, String senha, Long tenant, Status status) {
        this.login = login;
        this.senha = senha;
        this.tenant = tenant;
        this.status = status;
    }

    public Usuario(String login, String senha, Papel papel) {
        this.login = login;
        this.senha = senha;
        this.papel = papel;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Papel getPapel() {
        return papel;
    }

    public void setPapel(Papel papel) {
        this.papel = papel;
    }

    public PlanoComissao getPlanoComissao() {
        return planoComissao;
    }

    public void setPlanoComissao(PlanoComissao planoComissao) {
        this.planoComissao = planoComissao;
    }

    @JsonIgnore
    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public List<Permissao> getPermissoes() {
        return getPapel().getPermissoes();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;

        if (id != null ? !id.equals(usuario.id) : usuario.id != null) return false;
        return tenant != null ? tenant.equals(usuario.tenant) : usuario.tenant == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (tenant != null ? tenant.hashCode() : 0);
        return result;
    }
}
