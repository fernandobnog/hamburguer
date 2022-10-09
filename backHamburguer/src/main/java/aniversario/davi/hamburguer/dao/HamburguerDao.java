package aniversario.davi.hamburguer.dao;

import aniversario.davi.hamburguer.entidades.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Repository
public class HamburguerDao {
    String NomeEntidade = "Hamburguer";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public HamburguerDao(){}

    public Object conInserir(Hamburguer hamburguer) throws Exception {

        try (Connection con = jdbcTemplate.getDataSource().getConnection()){

            inserir(con, hamburguer);

            con.commit();
        } catch (Exception ex) {
            String erro = "Connection - Erro ao inserir" + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        System.out.println(NomeEntidade + " de ID " + hamburguer.getId() + " inserido com sucesso");
        return hamburguer;
    }

    public Object inserir(Connection con, Hamburguer hamburguer) throws Exception {

        String variavelSql = "";
        String variavelSqlIndex = "";

        if(hamburguer.getPao()==null && hamburguer.getCarne()==null && hamburguer.getQueijo()==null){
            throw new Exception("Hamburguer deve conter ou pão, ou carne, ou queijo");
        }

        if(hamburguer.getPao()==null){
        }else {
            variavelSql += "IDPAO, ";
            variavelSqlIndex += "?, ";

            Pao paoAtual = hamburguer.getPao();
            paoAtual.setEstoque(hamburguer.getPao().getEstoque()-1);
            new PaoDao().editar(con, paoAtual);
        }

        if(hamburguer.getCarne()==null){
        } else {
            variavelSql += "IDCARNE, ";
            variavelSqlIndex += "?, ";

            Carne carneAtual = hamburguer.getCarne();
            carneAtual.setEstoque(hamburguer.getCarne().getEstoque()-1);
            new CarneDao().editar(con, carneAtual);
        }

        if(hamburguer.getQueijo()==null){
        }else {
            variavelSql += "IDQUEIJO, ";
            variavelSqlIndex += "?, ";

            Queijo queijoAtual = hamburguer.getQueijo();
            queijoAtual.setEstoque(hamburguer.getQueijo().getEstoque()-1);
            new QueijoDao().editar(con, queijoAtual);
        }

        variavelSql = variavelSql.substring(0, variavelSql.length()-2);
        variavelSqlIndex = variavelSqlIndex.substring(0, variavelSqlIndex.length()-2);

        String sql = "INSERT INTO HAMBURGUER ("+ variavelSql +") VALUES ("+ variavelSqlIndex +")";

        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            con.setAutoCommit(false);

            if(hamburguer.getPao()==null){
            } else{
                ps.setInt(1, hamburguer.getPao().getId());
            }
            if(hamburguer.getCarne()==null){
            } else{
                int index = 2;
                if(hamburguer.getPao()==null){
                    index = index-1;
                }
                ps.setInt(index, hamburguer.getCarne().getId());
            }
            if(hamburguer.getQueijo()==null){
            }else {
                int index = 3;
                if(hamburguer.getPao()==null){
                    index = index-1;
                }
                if (hamburguer.getCarne()==null){
                    index = index-1;
                }
                ps.setInt(index, hamburguer.getQueijo().getId());
            }
            ps.execute();

            try(ResultSet tableKeys = ps.getGeneratedKeys()){
                tableKeys.next();
                hamburguer.setId(tableKeys.getInt(1));
            } catch (Exception ex) {
                String erro = "ResultSet - Erro ao inserir " + NomeEntidade + " no banco: " + ex;
                System.err.println(erro);
                throw new Exception(erro);
            }

        } catch (Exception ex) {
            String erro = "PreparedStatement - Erro ao inserir " + NomeEntidade + " no banco: " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        if(!hamburguer.getOpcionais().isEmpty()){
            for (Opcionais opcionais : hamburguer.getOpcionais()) {

                if(opcionais.isTemQuantidade()){
                    opcionais.setQuantidade(opcionais.getQuantidade()-1);
                    new OpcionaisDao().editar(con, opcionais);
                }

                HamburguerOpcionais hm = new HamburguerOpcionais();
                hm.setOpcionais(opcionais);
                hm.setHamburguer(hamburguer);

                HamburguerOpcionaisDao hmd = new HamburguerOpcionaisDao();
                hmd.inserir(con, hm);
            }
        }

        if(!hamburguer.getSaladas().isEmpty()){
            for (Salada salada : hamburguer.getSaladas()) {
                HamburguerSalada hs = new HamburguerSalada();
                hs.setSalada(salada);
                hs.setHamburguer(hamburguer);

                HamburguerSaladaDao hsd = new HamburguerSaladaDao();
                hsd.inserir(con, hs);
            }
        }

        return hamburguer;
    }

    public List<Hamburguer> conListar() throws Exception {

        List<Hamburguer> lista = new ArrayList<>();

        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {

            lista = listar(con);

        } catch (Exception ex) {
            String erro = "Connection - Erro ao listar " + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        return lista;
    }

    public List<Hamburguer> listar(Connection con) throws Exception {

        String sql = "SELECT *\n" +
                     "FROM HAMBURGUER\n" ;

        Map<Integer, Hamburguer> map = new TreeMap<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.execute();

            try (ResultSet rs = ps.getResultSet()){
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    Hamburguer hamburguer = map.get(id);
                    if(hamburguer == null){
                        hamburguer = new Hamburguer();

                        Pao pao = new Pao();
                        pao.setId(rs.getInt("IDPAO"));
                        if(pao.getId()!=0){
                            pao = new PaoDao().obterPorId(con, pao.getId());
                        }

                        Carne carne = new Carne();
                        carne.setId(rs.getInt("IDCARNE"));
                        if(carne.getId()!=0){
                            carne = new CarneDao().obterPorId(con, carne.getId());
                        }

                        Queijo queijo = new Queijo();
                        queijo.setId(rs.getInt("IDQUEIJO"));
                        if(queijo.getId()!=0){
                            queijo = new QueijoDao().obterPorId(con, queijo.getId());
                        }

                        hamburguer.setId(rs.getInt("ID"));
                        hamburguer.setPao(pao);
                        hamburguer.setCarne(carne);
                        hamburguer.setQueijo(queijo);

                        HamburguerOpcionaisDao hmd = new HamburguerOpcionaisDao();
                        List<Opcionais> opcionais = hmd.listarOpcionaisPorHamburguer(con, hamburguer);

                        HamburguerSaladaDao hsd = new HamburguerSaladaDao();
                        List<Salada> saladas = hsd.listarSaladasPorHamburguer(con, hamburguer);

                        hamburguer.setSaladas(saladas);
                        hamburguer.setOpcionais(opcionais);

                        map.put(hamburguer.getId(), hamburguer);
                    }
                }
            } catch (Exception ex) {
                String erro = "ResultSet - Erro ao listar " + NomeEntidade + ": " + ex;
                System.err.println(erro);
                throw new Exception(erro);
            }
        } catch (Exception ex) {
            String erro = "PreparedStatement - Erro ao listar " + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        return new ArrayList<>(map.values());
    }

    public List<Hamburguer> listarPorIdHamburguer(Connection con, int id) throws Exception {

        String sql = "SELECT H.ID, H.IDPAO, P.TIPO AS TIPOPAO, P.ESTOQUE AS ESTOQUEPAO, IDCARNE, PESOGRAMAS, C.ESTOQUE AS ESTOQUECARNE, IDQUEIJO, Q.TIPO AS TIPOQUEIJO, Q.ESTOQUE AS ESTOQUEQUEIJO\n" +
                "FROM HAMBURGUER H\n" +
                "JOIN PAO P ON IDPAO = P.ID\n" +
                "JOIN CARNE C ON IDCARNE = C.ID\n" +
                "JOIN QUEIJO Q ON IDQUEIJO = Q.ID\n" +
                "WHERE H.ID = " + id;

        Map<Integer, Hamburguer> map = new TreeMap<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.execute();

            try (ResultSet rs = ps.getResultSet()){
                while (rs.next()) {
                    id = rs.getInt("ID");
                    Hamburguer hamburguer = map.get(id);
                    if(hamburguer == null){
                        hamburguer = new Hamburguer();

                        Pao pao = new Pao();
                        pao.setId(rs.getInt("IDPAO"));
                        pao.setTipo(rs.getString("TIPOPAO"));
                        pao.setEstoque((rs.getInt("ESTOQUEPAO")));

                        Carne carne = new Carne();
                        carne.setId(rs.getInt("IDCARNE"));
                        carne.setPesoGramas(rs.getInt("PESOGRAMAS"));
                        carne.setEstoque((rs.getInt("ESTOQUECARNE")));

                        Queijo queijo = new Queijo();
                        queijo.setId(rs.getInt("IDQUEIJO"));
                        queijo.setTipo(rs.getString("TIPOQUEIJO"));
                        queijo.setEstoque((rs.getInt("ESTOQUEQUEIJO")));

                        hamburguer.setId(rs.getInt("ID"));
                        hamburguer.setPao(pao);
                        hamburguer.setCarne(carne);
                        hamburguer.setQueijo(queijo);

                        HamburguerOpcionaisDao hmd = new HamburguerOpcionaisDao();
                        List<Opcionais> opcionais = hmd.listarOpcionaisPorHamburguer(con, hamburguer);

                        HamburguerSaladaDao hsd = new HamburguerSaladaDao();
                        List<Salada> saladas = hsd.listarSaladasPorHamburguer(con, hamburguer);

                        hamburguer.setSaladas(saladas);
                        hamburguer.setOpcionais(opcionais);

                        map.put(hamburguer.getId(), hamburguer);
                    }
                }
            } catch (Exception ex) {
                String erro = "ResultSet - Erro ao listar " + NomeEntidade + ": " + ex;
                System.err.println(erro);
                throw new Exception(erro);
            }
        } catch (Exception ex) {
            String erro = "PreparedStatement - Erro ao listar " + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        return new ArrayList<>(map.values());
    }

    public Object conEditar(Hamburguer hamburguer) throws Exception {

        try (Connection con = jdbcTemplate.getDataSource().getConnection()){

            editar(con, hamburguer);

            con.commit();
        } catch (Exception ex) {
            String erro = "Connection - Erro ao editar " + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        System.out.println(NomeEntidade + " de ID" + hamburguer.getId() + " editado com sucesso.");
        return hamburguer;
    }

    public Hamburguer editar(Connection con, Hamburguer hamburguer) throws Exception {

        if(hamburguer.getPao()==null){
            Pao pao = new Pao();
            pao.setId(0);
            hamburguer.setPao(pao);
        }
        if(hamburguer.getCarne()==null){
            Carne carne = new Carne();
            carne.setId(0);
            hamburguer.setCarne(carne);
        }
        if(hamburguer.getQueijo()==null){
            Queijo queijo = new Queijo();
            hamburguer.setQueijo(queijo);
        }

        List<Hamburguer> ListaHamburguerAntigo = listarPorIdHamburguer(con, hamburguer.getId());
        for (Hamburguer hamburguerAntigo : ListaHamburguerAntigo){

           if(hamburguer.getId()==hamburguerAntigo.getId()){
                if(hamburguer.getPao().getId()!=hamburguerAntigo.getPao().getId()){

                    Pao paoAntigo = hamburguerAntigo.getPao();
                    paoAntigo.setEstoque(hamburguerAntigo.getPao().getEstoque()+1);
                    new PaoDao().editar(con, paoAntigo);

                    Pao paoAtual = hamburguer.getPao();
                    paoAtual.setEstoque(hamburguer.getPao().getEstoque()-1);
                    new PaoDao().editar(con, paoAtual);

               }
               if(hamburguer.getCarne().getId()!=hamburguerAntigo.getCarne().getId()){

                   Carne carneAntiga = hamburguerAntigo.getCarne();
                   carneAntiga.setEstoque(hamburguerAntigo.getCarne().getEstoque()+1);
                   new CarneDao().editar(con, carneAntiga);

                   Carne carneAtual = hamburguer.getCarne();
                   carneAtual.setEstoque(hamburguer.getCarne().getEstoque()-1);
                   new CarneDao().editar(con, carneAtual);

               }
               if(hamburguer.getQueijo().getId()!=hamburguerAntigo.getQueijo().getId()){

                   Queijo queijoAntigo = hamburguerAntigo.getQueijo();
                   queijoAntigo.setEstoque(hamburguerAntigo.getQueijo().getEstoque()+1);
                   new QueijoDao().editar(con, queijoAntigo);

                   Queijo queijoAtual = hamburguer.getQueijo();
                   queijoAtual.setEstoque(hamburguer.getQueijo().getEstoque()-1);
                   new QueijoDao().editar(con, queijoAtual);
               }

           }
       }

        String variavelSql = "";

        if(hamburguer.getPao()==null || hamburguer.getPao().getId()==0){
            variavelSql += "IDPAO=NULL, ";
        } else {
            variavelSql += "IDPAO=?, ";
        }
        if (hamburguer.getCarne()==null || hamburguer.getCarne().getId()==0){
            variavelSql += "IDCARNE=NULL, ";
        }else {
            variavelSql += "IDCARNE=?, ";
        }
        if (hamburguer.getQueijo()==null || hamburguer.getQueijo().getId()==0){
            variavelSql += "IDQUEIJO=NULL, ";
        }else {
            variavelSql += "IDQUEIJO=?, ";
        }

        variavelSql = variavelSql.substring(0, variavelSql.length()-2);

        //String sql = "UPDATE HAMBURGUER SET IDPAO=?, IDCARNE=?, IDQUEIJO=? WHERE ID=?";
        String sql = "UPDATE HAMBURGUER SET " + variavelSql + " WHERE ID=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            con.setAutoCommit(false);


            if(hamburguer.getPao()==null || hamburguer.getPao().getId()==0){
            } else{
                int indexPao = 1;
                ps.setInt(indexPao, hamburguer.getPao().getId());
            }
            if(hamburguer.getCarne()==null || hamburguer.getCarne().getId()==0){
            } else{
                int indexCarne = 2;
                if(hamburguer.getPao()==null || hamburguer.getPao().getId()==0){
                    indexCarne = indexCarne-1;
                }
                ps.setInt(indexCarne, hamburguer.getCarne().getId());
            }
            if(hamburguer.getQueijo()==null || hamburguer.getQueijo().getId()==0){
            }else {
                int indexQueijo = 3;
                if(hamburguer.getPao()==null || hamburguer.getPao().getId()==0){
                    indexQueijo = indexQueijo-1;
                }
                if (hamburguer.getCarne()==null || hamburguer.getCarne().getId()==0){
                    indexQueijo = indexQueijo-1;
                }
                ps.setInt(indexQueijo, hamburguer.getQueijo().getId());
            }
            int indexId = 4;

            if(hamburguer.getPao()==null || hamburguer.getPao().getId()==0){
                indexId = indexId-1;
            }
            if (hamburguer.getCarne()==null || hamburguer.getCarne().getId()==0){
                indexId = indexId-1;
            }
            if (hamburguer.getQueijo()==null || hamburguer.getQueijo().getId()==0){
                indexId = indexId-1;
            }
            ps.setInt(indexId, hamburguer.getId());
            ps.execute();

        } catch (Exception ex) {
            String erro = "PreparedStatement - Erro ao editar " + NomeEntidade + " de ID" + hamburguer.getId() + " no banco: " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        HamburguerSaladaDao hsd = new HamburguerSaladaDao();
        hsd.excluirPorHamburguer(con, hamburguer.getId());
        List<Salada> listaSAtual = hamburguer.getSaladas();

        for(Salada saladaAtual : listaSAtual) {

            HamburguerSalada hs = new HamburguerSalada();
            hs.setSalada(saladaAtual);
            hs.setHamburguer(hamburguer);

            hsd.inserir(con, hs);
        }



        HamburguerOpcionaisDao hmd = new HamburguerOpcionaisDao();
        for(Opcionais opcionaisVelho : hmd.listarOpcionaisPorHamburguer(con, hamburguer)){
            if(opcionaisVelho.isTemQuantidade()){
                opcionaisVelho.setQuantidade(opcionaisVelho.getQuantidade()+1);
                new OpcionaisDao().editar(con, opcionaisVelho);
            }
        }

        hmd.excluirPorHamburguer(con, hamburguer.getId());
        List<Opcionais> listaOpAtual = hamburguer.getOpcionais();

        for(Opcionais opcionaisAtual : listaOpAtual){

            if(opcionaisAtual.isTemQuantidade()){
                opcionaisAtual.setQuantidade(opcionaisAtual.getQuantidade()-1);
                new OpcionaisDao().editar(con, opcionaisAtual);
            }

            HamburguerOpcionais hm = new HamburguerOpcionais();
            hm.setOpcionais(opcionaisAtual);
            hm.setHamburguer(hamburguer);

            hmd.inserir(con, hm);
        }
            return hamburguer;
    }

    public boolean conExcluir(Hamburguer hamburguer) throws Exception {

        try (Connection con = jdbcTemplate.getDataSource().getConnection()){

            excluir(con, hamburguer);

            con.commit();
        } catch (Exception ex) {
            String erro = "Connection - Erro ao editar " + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        System.out.println(NomeEntidade + " de ID" + hamburguer.getId() + " editado com sucesso.");
        return true;
    }

    public boolean excluir(Connection con, Hamburguer hamburguer) throws Exception {

        Pao paoAtual = hamburguer.getPao();
        paoAtual.setEstoque(hamburguer.getPao().getEstoque()+1);
        new PaoDao().editar(con, paoAtual);

        Carne carneAtual = hamburguer.getCarne();
        carneAtual.setEstoque(hamburguer.getCarne().getEstoque()+1);
        new CarneDao().editar(con, carneAtual);

        Queijo queijoAtual = hamburguer.getQueijo();
        queijoAtual.setEstoque(hamburguer.getQueijo().getEstoque()+1);
        new QueijoDao().editar(con, queijoAtual);


        String sql = "DELETE FROM HAMBURGUER WHERE ID = ?";

            new HamburguerOpcionaisDao().excluirPorHamburguer(con, hamburguer.getId());

            new HamburguerSaladaDao().excluirPorHamburguer(con, hamburguer.getId());

            try(PreparedStatement ps = con.prepareStatement(sql)){
                con.setAutoCommit(false);

                ps.setInt(1, hamburguer.getId());
                ps.executeUpdate();


        } catch (Exception ex) {
            String erro = "Connection - Erro ao excluir " + NomeEntidade + " de ID" + hamburguer.getId() + " no banco: " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        System.out.println(NomeEntidade + " de id. " + hamburguer.getId() + " excluido com sucesso.");
        return true;
    }
}
