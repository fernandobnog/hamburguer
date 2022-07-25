package aniversario.davi.hamburguer.entidades;

public class Pedido {
    int id;
    Pessoa pessoa;
    Hamburguer hamburguer;

    boolean statusPedido;

    public Pedido() {}

    public Pedido(int id, Pessoa pessoa, Hamburguer hamburguer, boolean statusPedido) {
        this.id = id;
        this.pessoa = pessoa;
        this.hamburguer = hamburguer;
        this.statusPedido = statusPedido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Hamburguer getHamburguer() {
        return hamburguer;
    }

    public void setHamburguer(Hamburguer hamburguer) {
        this.hamburguer = hamburguer;
    }

    public boolean isStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(boolean statusPedido) {
        this.statusPedido = statusPedido;
    }
}
