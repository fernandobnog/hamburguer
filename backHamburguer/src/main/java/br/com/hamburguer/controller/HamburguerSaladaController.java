package br.com.hamburguer.controller;

import br.com.hamburguer.dao.HamburguerSaladaDao;
import br.com.hamburguer.entidades.HamburguerSalada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hamburguersalada")
public class HamburguerSaladaController {

    @Autowired
    HamburguerSaladaDao dao;

    @CrossOrigin
    @PostMapping("/inserir")
    public Retorno inserir (@RequestBody HamburguerSalada obj){
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
    public Retorno editar(@RequestBody HamburguerSalada obj) {
        try {
            return new Retorno(dao.conEditar(obj));
        } catch (Exception ex) {
            return new Retorno(ex.getMessage());
        }
    }

    @CrossOrigin
    @PostMapping("/excluir")
    public Retorno excluir(@RequestBody HamburguerSalada obj) {
        int id = obj.getId();
        try {
            return new Retorno(dao.conExcluir(id));
        } catch (Exception ex) {
            return new Retorno(ex.getMessage());
        }
    }
}
