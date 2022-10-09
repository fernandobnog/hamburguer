package br.com.hamburguer.entidades;

public class Queijo {
    int id;
    String tipo;
    int estoque;

    public Queijo() {}

    public Queijo(int id, String tipo, int estoque) {
        this.id = id;
        this.tipo = tipo;
        this.estoque = estoque;
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

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }
}
