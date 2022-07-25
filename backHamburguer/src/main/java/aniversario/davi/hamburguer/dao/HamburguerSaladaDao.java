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
public class HamburguerSaladaDao {
    String NomeEntidade = "HamburguerSalada";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public HamburguerSaladaDao(){}

    public Object conInserir(HamburguerSalada hamburguerSalada) throws Exception {

        try (Connection con = jdbcTemplate.getDataSource().getConnection()){

            inserir(con, hamburguerSalada);

            con.commit();
        } catch (Exception ex) {
            String erro = "Connection - Erro ao inserir" + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        System.out.println(NomeEntidade + " de ID " + hamburguerSalada.getId() + " inserido com sucesso");
        return hamburguerSalada;
    }

    public Object inserir(Connection con, HamburguerSalada hamburguerSalada) throws Exception {

        String sql = "INSERT INTO HAMBURGUERSALADA (IDHAMBURGUER, IDSALADA) VALUES (?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            con.setAutoCommit(false);

            ps.setInt(1, hamburguerSalada.getHamburguer().getId());
            ps.setInt(2, hamburguerSalada.getSalada().getId());
            ps.execute();

            try(ResultSet tableKeys = ps.getGeneratedKeys()){
                tableKeys.next();
                hamburguerSalada.setId(tableKeys.getInt(1));
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

        return hamburguerSalada;
    }

    public List<HamburguerSalada> conListar() throws Exception {

        List<HamburguerSalada> lista = new ArrayList<>();

        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {

            lista = listar(con);

        } catch (Exception ex) {
            String erro = "Connection - Erro ao listar " + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        return lista;
    }

    public List<HamburguerSalada> listar(Connection con) throws Exception {

        String sql = "SELECT * FROM HABURGUERSALADA\n";

        Map<Integer, HamburguerSalada> map = new TreeMap<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.execute();

            try (ResultSet rs = ps.getResultSet()){
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    HamburguerSalada hamburguerSalada = map.get(id);
                    if(hamburguerSalada == null){
                        hamburguerSalada = new HamburguerSalada();

                        Hamburguer hamburguer = new Hamburguer();
                        hamburguer.setId(rs.getInt("IDHAMBURGUER"));

                        Salada salada = new Salada();
                        salada.setId(rs.getInt("IDSALADA"));

                        hamburguerSalada.setId(rs.getInt("ID"));
                        hamburguerSalada.setHamburguer(hamburguer);
                        hamburguerSalada.setSalada(salada);

                        map.put(hamburguerSalada.getId(), hamburguerSalada);
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

    public List<HamburguerSalada> listarporHamburguerESalada(Connection con, HamburguerSalada hs) throws Exception {

        String sql = "SELECT * FROM HAMBURGUERSALADA\n" +
                "join salada on idSalada = salada.id\n" +
                "where idHamburguer = " + hs.getHamburguer().getId() + " && idSalada = " + hs.getSalada().getId();

        Map<Integer, HamburguerSalada> map = new TreeMap<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.execute();

            try (ResultSet rs = ps.getResultSet()){
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    HamburguerSalada hamburguerSalada = map.get(id);
                    if(hamburguerSalada == null){
                        hamburguerSalada = new HamburguerSalada();

                        Hamburguer hamburguer = new Hamburguer();
                        hamburguer.setId(rs.getInt("IDHAMBURGUER"));

                        Salada salada = new Salada();
                        salada.setId(rs.getInt("IDSALADA"));

                        hamburguerSalada.setId(rs.getInt("ID"));
                        hamburguerSalada.setHamburguer(hamburguer);
                        hamburguerSalada.setSalada(salada);

                        map.put(hamburguerSalada.getId(), hamburguerSalada);
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

    public List<Salada> listarSaladasPorHamburguer(Connection con, Hamburguer hamburguer) throws Exception {

        String sql = "Select idsalada, salada.tipo\n" +
                     "from hamburguersalada\n" +
                     "join salada on idSalada = salada.id\n" +
                     "where idHamburguer = " + hamburguer.getId();

        Map<Integer, Salada> map = new TreeMap<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.execute();

            try (ResultSet rs = ps.getResultSet()){
                while (rs.next()) {
                    int id = rs.getInt("IDSALADA");
                    Salada salada = map.get(id);
                    if(salada == null){
                        salada = new Salada();

                        salada.setId(rs.getInt("IDSALADA"));
                        salada.setTipo(rs.getString("TIPO"));

                        map.put(salada.getId(), salada);
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

    public Object conEditar(HamburguerSalada hamburguerSalada) throws Exception {

        try (Connection con = jdbcTemplate.getDataSource().getConnection()){

            editar(con, hamburguerSalada);

            con.commit();
        } catch (Exception ex) {
            String erro = "Connection - Erro ao editar " + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        System.out.println(NomeEntidade + " de ID" + hamburguerSalada.getId() + " editado com sucesso.");
        return hamburguerSalada;
    }

    public HamburguerSalada editar(Connection con, HamburguerSalada hamburguerSalada) throws Exception {

        String sql = "UPDATE HAMBURGUERSALADA SET IDHAMBURGUER=?, IDSALADA=? WHERE ID=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            con.setAutoCommit(false);

            ps.setInt(1, hamburguerSalada.getHamburguer().getId());
            ps.setInt(2, hamburguerSalada.getSalada().getId());
            ps.setInt(3, hamburguerSalada.getId());
            ps.execute();

        } catch (Exception ex) {
            String erro = "PreparedStatement - Erro ao editar " + NomeEntidade + " de ID" + hamburguerSalada.getId() + " no banco: " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        return hamburguerSalada;
    }

    public boolean conExcluir(int id) throws Exception {

        try (Connection con = jdbcTemplate.getDataSource().getConnection()){

            excluir(con, id);

            con.commit();
        } catch (Exception ex) {
            String erro = "Connection - Erro ao editar " + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        System.out.println(NomeEntidade + " de ID" + id + " editado com sucesso.");
        return true;
    }

    public boolean excluir(Connection con, int id) throws Exception {

        String sql = "DELETE FROM HAMBURGUERSALADA WHERE ID = ?";
        try(PreparedStatement ps = con.prepareStatement(sql)){
            con.setAutoCommit(false);

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception ex) {
            String erro = "PreparedStatement - Erro ao excluir " + NomeEntidade + " de ID" + id + " no banco: " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }
        return true;
    }

    public boolean excluirPorHamburguer(Connection con, int id) throws Exception {

        String sql = "DELETE FROM HAMBURGUERSALADA WHERE IDHAMBURGUER = ?";

            try(PreparedStatement ps = con.prepareStatement(sql)){
                con.setAutoCommit(false);

                ps.setInt(1, id);
                ps.executeUpdate();

            } catch (Exception ex) {
                String erro = "PreparedStatement - Erro ao excluir " + NomeEntidade + " de ID" + id + " no banco: " + ex;
                System.err.println(erro);
                throw new Exception(erro);
            }

        System.out.println(NomeEntidade + " de id. " + id + " excluido com sucesso.");
        return true;
    }
}
