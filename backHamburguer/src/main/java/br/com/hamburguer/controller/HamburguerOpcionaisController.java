package br.com.hamburguer.controller;

import br.com.hamburguer.dao.HamburguerOpcionaisDao;
import br.com.hamburguer.entidades.HamburguerOpcionais;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hamburgueropcionais")
public class HamburguerOpcionaisController {

    @Autowired
    HamburguerOpcionaisDao dao;

    @CrossOrigin
    @PostMapping("/inserir")
    public Retorno inserir (@RequestBody HamburguerOpcionais obj){
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
    public Retorno editar(@RequestBody HamburguerOpcionais obj) {
        try {
            return new Retorno(dao.conEditar(obj));
        } catch (Exception ex) {
            return new Retorno(ex.getMessage());
        }
    }

    @CrossOrigin
    @PostMapping("/excluir")
    public Retorno excluir(@RequestBody HamburguerOpcionais obj) {
        int id = obj.getId();
        try {
            return new Retorno(dao.conExcluir(id));
        } catch (Exception ex) {
            return new Retorno(ex.getMessage());
        }
    }
}
