package aniversario.davi.hamburguer.controller;

import aniversario.davi.hamburguer.dao.QueijoDao;
import aniversario.davi.hamburguer.entidades.Queijo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/queijo")
public class QueijoController {

    @Autowired
    QueijoDao dao;

    @CrossOrigin
    @PostMapping("/inserir")
    public Retorno inserir (@RequestBody Queijo obj){
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
    public Retorno editar(@RequestBody Queijo obj) {
        try {
            return new Retorno(dao.conEditar(obj));
        } catch (Exception ex) {
            return new Retorno(ex.getMessage());
        }
    }

    @CrossOrigin
    @PostMapping("/excluir")
    public Retorno excluir(@RequestBody Queijo obj) {
        int id = obj.getId();
        try {
            return new Retorno(dao.excluir(id));
        } catch (Exception ex) {
            return new Retorno(ex.getMessage());
        }
    }
}
