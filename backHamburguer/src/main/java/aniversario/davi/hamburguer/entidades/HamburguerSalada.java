package aniversario.davi.hamburguer.entidades;

public class HamburguerSalada {

    int id;
   Hamburguer hamburguer;
   Salada salada;

    public HamburguerSalada() {}

    public HamburguerSalada(int id, Hamburguer hamburguer, Salada salada) {
        this.id = id;
        this.hamburguer = hamburguer;
        this.salada = salada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Hamburguer getHamburguer() {
        return hamburguer;
    }

    public void setHamburguer(Hamburguer hamburguer) {
        this.hamburguer = hamburguer;
    }

    public Salada getSalada() {
        return salada;
    }

    public void setSalada(Salada salada) {
        this.salada = salada;
    }
}
