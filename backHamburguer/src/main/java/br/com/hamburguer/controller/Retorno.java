package br.com.hamburguer.controller;


public class Retorno {
    public String status;
    public String mensagem;
    public Object object;

    public Retorno(){}

    public Retorno(Object obj) {

        if(obj.getClass().getSimpleName().equals("Retorno")){
            Retorno retorno = (Retorno) obj;
            this.status = "Alerta";
            this.mensagem = retorno.getMensagem();
            this.object = retorno.getObject();
            return;
        }
        this.status = "OK";
        this.object = obj;
    }

    public Retorno(String erro) {
        this.status = "Erro";
        this.mensagem = erro;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
