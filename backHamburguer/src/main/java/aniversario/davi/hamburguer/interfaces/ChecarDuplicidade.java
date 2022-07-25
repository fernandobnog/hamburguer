package aniversario.davi.hamburguer.interfaces;

import aniversario.davi.hamburguer.controller.Retorno;
import aniversario.davi.hamburguer.dao.*;
import aniversario.davi.hamburguer.entidades.*;

import java.sql.Connection;
import java.util.List;

public interface ChecarDuplicidade {

    static Object pao(Connection con, Pao pao) {
        PaoDao dao = new PaoDao();
        Retorno retorno = new Retorno();

        List<Pao> lista;
        try{
            lista = dao.listar(con);
        } catch (Exception ex){
            return new Retorno(ex.getMessage());
        }
        if(!lista.isEmpty()){

            for (Pao obj : lista) {

                if(pao.getTipo()==obj.getTipo()){

                    retorno.setObject(obj);
                    retorno.setMensagem("Pão já existia no banco");

                    return retorno;
                }
            }
        }
        return "cadastrar";
    }

    static Object carne(Connection con, Carne carne) {
        CarneDao dao = new CarneDao();
        Retorno retorno = new Retorno();

        List<Carne> lista;
        try{
            lista = dao.listar(con);
        } catch (Exception ex){
            return new Retorno(ex.getMessage());
        }
        if(!lista.isEmpty()){

            for (Carne obj : lista) {

                if(carne.getPesoGramas()==obj.getPesoGramas()){

                    retorno.setObject(obj);
                    retorno.setMensagem("Carne já existia no banco");

                    return retorno;
                }
            }
        }
        return "cadastrar";
    }

    static Object molho(Connection con, Opcionais opcionais) {
        OpcionaisDao dao = new OpcionaisDao();
        Retorno retorno = new Retorno();

        List<Opcionais> lista;
        try{
            lista = dao.listar(con);
        } catch (Exception ex){
            return new Retorno(ex.getMessage());
        }
        if(!lista.isEmpty()){

            for (Opcionais obj : lista) {

                if(opcionais.getTipo()==obj.getTipo()){

                    retorno.setObject(obj);
                    retorno.setMensagem("Opcionais já existia no banco");

                    return retorno;
                }
            }
        }
        return "cadastrar";
    }

    static Object pessoa(Connection con, Pessoa pessoa) {
        PessoaDao dao = new PessoaDao();
        Retorno retorno = new Retorno();

        List<Pessoa> lista;
        try{
            lista = dao.listar(con);
        } catch (Exception ex){
            return new Retorno(ex.getMessage());
        }
        if(!lista.isEmpty()){

            for (Pessoa obj : lista) {

                if(pessoa.getNome()==obj.getNome()){

                    retorno.setObject(obj);
                    retorno.setMensagem("Pessoa já existia no banco");

                    return retorno;
                }
            }
        }
        return "cadastrar";
    }

    static Object queijo(Connection con, Queijo queijo) {
        QueijoDao dao = new QueijoDao();
        Retorno retorno = new Retorno();

        List<Queijo> lista;
        try{
            lista = dao.listar(con);
        } catch (Exception ex){
            return new Retorno(ex.getMessage());
        }
        if(!lista.isEmpty()){

            for (Queijo obj : lista) {

                if(queijo.getTipo()==obj.getTipo()){

                    retorno.setObject(obj);
                    retorno.setMensagem("Queijo já existia no banco");

                    return retorno;
                }
            }
        }
        return "cadastrar";
    }

    static Object salada(Connection con, Salada salada) {
        SaladaDao dao = new SaladaDao();
        Retorno retorno = new Retorno();

        List<Salada> lista;
        try{
            lista = dao.listar(con);
        } catch (Exception ex){
            return new Retorno(ex.getMessage());
        }
        if(!lista.isEmpty()){

            for (Salada obj : lista) {

                if(salada.getTipo()==obj.getTipo()){

                    retorno.setObject(obj);
                    retorno.setMensagem("Salada já existia no banco");

                    return retorno;
                }
            }
        }
        return "cadastrar";
    }
}
