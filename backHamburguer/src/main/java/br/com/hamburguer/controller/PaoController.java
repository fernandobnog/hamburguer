package br.com.hamburguer.controller;

import br.com.hamburguer.entidades.Pao;
import br.com.hamburguer.dao.PaoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pao")
public class PaoController {

    @Autowired
    PaoDao dao;

    @CrossOrigin
    @PostMapping("/inserir")
    public Retorno inserir (@RequestBody Pao obj){
        try{
            return new Retorno(dao.conInserir(obj));
        } catch (Exception ex) {
            return new Retorno(ex.getMessage());
        }
    }

    @CrossOrigin
    @GetMapping("/listar")
    public Retorno listar() {
        try {
            return new Retorno(dao.conListar());
        } catch (Exception ex) {
            return new Retorno(ex.getMessage());
        }
    }

    @CrossOrigin
    @PostMapping ("/editar")
    public Retorno editar(@RequestBody Pao obj) {
        try {
            return new Retorno(dao.conEditar(obj));
        } catch (Exception ex) {
            return new Retorno(ex.getMessage());
        }
    }

    @CrossOrigin
    @PostMapping("/excluir")
    public Retorno excluir(@RequestBody Pao obj) {
        int id = obj.getId();
        try {
            return new Retorno(dao.excluir(id));
        } catch (Exception ex) {
            return new Retorno(ex.getMessage());
        }
    }
}
