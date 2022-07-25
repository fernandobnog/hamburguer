package aniversario.davi.hamburguer.entidades;

public class Carne {
    int id;
    int pesoGramas;

    int estoque;

    public Carne() {}

    public Carne(int id, int pesoGramas, int estoque) {
        this.id = id;
        this.pesoGramas = pesoGramas;
        this.estoque = estoque;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPesoGramas() {
        return pesoGramas;
    }

    public void setPesoGramas(int pesoGramas) {
        this.pesoGramas = pesoGramas;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }
}
