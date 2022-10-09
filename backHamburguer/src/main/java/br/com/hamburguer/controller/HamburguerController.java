package br.com.hamburguer.controller;

import br.com.hamburguer.dao.HamburguerDao;
import br.com.hamburguer.entidades.Hamburguer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hamburguer")
public class HamburguerController {

    @Autowired
    HamburguerDao dao;

    @CrossOrigin
    @PostMapping("/inserir")
    public Retorno inserir (@RequestBody Hamburguer obj){
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
    public Retorno editar(@RequestBody Hamburguer obj) {
        try {
            return new Retorno(dao.conEditar(obj));
        } catch (Exception ex) {
            return new Retorno(ex.getMessage());
        }
    }

    @CrossOrigin
    @PostMapping("/excluir")
    public Retorno excluir(@RequestBody Hamburguer obj) {
        try {
            return new Retorno(dao.conExcluir(obj));
        } catch (Exception ex) {
            return new Retorno(ex.getMessage());
        }
    }
}
