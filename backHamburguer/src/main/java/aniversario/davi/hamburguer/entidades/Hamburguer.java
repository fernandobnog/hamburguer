package aniversario.davi.hamburguer.entidades;

import java.util.ArrayList;
import java.util.List;

public class Hamburguer {
    int id;
    Pao pao;
    Carne carne;
    Queijo queijo;
    List<Salada> saladas = new ArrayList<>();
    List<Opcionais> opcionais = new ArrayList<>();

    public Hamburguer() {}

    public Hamburguer(int id, Pao pao, Carne carne, Queijo queijo, List<Salada> saladas, List<Opcionais> opcionais) {
        this.id = id;
        this.pao = pao;
        this.carne = carne;
        this.queijo = queijo;
        this.saladas = saladas;
        this.opcionais = opcionais;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pao getPao() {
        return pao;
    }

    public void setPao(Pao pao) {
        this.pao = pao;
    }

    public Carne getCarne() {
        return carne;
    }

    public void setCarne(Carne carne) {
        this.carne = carne;
    }

    public Queijo getQueijo() {
        return queijo;
    }

    public void setQueijo(Queijo queijo) {
        this.queijo = queijo;
    }

    public List<Salada> getSaladas() {
        return saladas;
    }

    public void setSaladas(List<Salada> saladas) {
        this.saladas = saladas;
    }

    public List<Opcionais> getOpcionais() {
        return opcionais;
    }

    public void setOpcionais(List<Opcionais> opcionais) {
        this.opcionais = opcionais;
    }
}
