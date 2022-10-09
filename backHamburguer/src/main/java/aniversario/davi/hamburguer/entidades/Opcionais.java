package aniversario.davi.hamburguer.entidades;

public class Opcionais {
    int id;
    String tipo;

    int quantidade;
    boolean temQuantidade;


    public Opcionais() {}

    public Opcionais(int id, String tipo, int quantidade, boolean temQuantidade) {
        this.id = id;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.temQuantidade = temQuantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public boolean isTemQuantidade() {
        return temQuantidade;
    }

    public void setTemQuantidade(boolean temQuantidade) {
        this.temQuantidade = temQuantidade;
    }
}
