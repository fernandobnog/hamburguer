package br.com.hamburguer.entidades;

public class HamburguerOpcionais {
    int id;
    Hamburguer hamburguer;
    Opcionais opcionais;

    public HamburguerOpcionais() {}

    public HamburguerOpcionais(int id, Hamburguer hamburguer, Opcionais opcionais) {
        this.id = id;
        this.hamburguer = hamburguer;
        this.opcionais = opcionais;
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

    public Opcionais getOpcionais() {
        return opcionais;
    }

    public void setOpcionais(Opcionais opcionais) {
        this.opcionais = opcionais;
    }
}
