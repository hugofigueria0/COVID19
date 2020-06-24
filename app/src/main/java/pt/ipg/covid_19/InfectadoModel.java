package pt.ipg.covid_19;

public class InfectadoModel {

    private long id = -1;
    private String infectado;
    private long id_pessoa = -1;
    private String nome_pessoa = null;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInfectado() {
        return infectado;
    }

    public void setInfectado(String infectado) {
        this.infectado = infectado;
    }

    public long getId_pessoa() {
        return id_pessoa;
    }

    public void setId_pessoa(long id_pessoa) {
        this.id_pessoa = id_pessoa;
    }

    public String getNome_pessoa() {
        return nome_pessoa;
    }

    public void setNome_pessoa(String nome_pessoa) {
        this.nome_pessoa = nome_pessoa;
    }


}
