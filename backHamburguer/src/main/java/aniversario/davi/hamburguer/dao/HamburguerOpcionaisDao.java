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
public class HamburguerOpcionaisDao {
    String NomeEntidade = "HamburguerOpcionais";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public HamburguerOpcionaisDao(){}

    public Object conInserir(HamburguerOpcionais hamburguerOpcionais) throws Exception {

        try (Connection con = jdbcTemplate.getDataSource().getConnection()){

            inserir(con, hamburguerOpcionais);

            con.commit();
        } catch (Exception ex) {
            String erro = "Connection - Erro ao inserir" + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        System.out.println(NomeEntidade + " de ID " + hamburguerOpcionais.getId() + " inserido com sucesso");
        return hamburguerOpcionais;
    }

    public Object inserir(Connection con, HamburguerOpcionais hamburguerOpcionais) throws Exception {

        String sql = "INSERT INTO HAMBURGUEROPCIONAIS (IDHAMBURGUER, IDOPCIONAIS) VALUES (?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            con.setAutoCommit(false);

            ps.setInt(1, hamburguerOpcionais.getHamburguer().getId());
            ps.setInt(2, hamburguerOpcionais.getOpcionais().getId());
            ps.execute();

            try(ResultSet tableKeys = ps.getGeneratedKeys()){
                tableKeys.next();
                hamburguerOpcionais.setId(tableKeys.getInt(1));
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

        return hamburguerOpcionais;
    }

    public List<HamburguerOpcionais> conListar() throws Exception {

        List<HamburguerOpcionais> lista = new ArrayList<>();

        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {

            lista = listar(con);

        } catch (Exception ex) {
            String erro = "Connection - Erro ao listar " + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        return lista;
    }

    public List<HamburguerOpcionais> listar(Connection con) throws Exception {

        String sql = "SELECT * FROM HAMBURGUEROPCIONAIS\n";

        Map<Integer, HamburguerOpcionais> map = new TreeMap<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.execute();

            try (ResultSet rs = ps.getResultSet()){
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    HamburguerOpcionais hamburguerOpcionais = map.get(id);
                    if(hamburguerOpcionais == null){
                        hamburguerOpcionais = new HamburguerOpcionais();

                        Hamburguer hamburguer = new Hamburguer();
                        hamburguer.setId(rs.getInt("IDHAMBURGUER"));

                        Opcionais opcionais = new Opcionais();
                        opcionais.setId(rs.getInt("IDOPCIONAIS"));

                        hamburguerOpcionais.setId(rs.getInt("ID"));
                        hamburguerOpcionais.setHamburguer(hamburguer);
                        hamburguerOpcionais.setOpcionais(opcionais);

                        map.put(hamburguerOpcionais.getId(), hamburguerOpcionais);
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

    public List<Opcionais> listarOpcionaisPorHamburguer(Connection con, Hamburguer hamburguer) throws Exception {

        String sql = "Select idOpcionais, opcionais.tipo\n" +
                     "from hamburguerOpcionais\n" +
                     "join opcionais on idOpcionais = opcionais.id\n" +
                     "where idHamburguer = " + hamburguer.getId();

        Map<Integer, Opcionais> map = new TreeMap<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.execute();

            try (ResultSet rs = ps.getResultSet()){
                while (rs.next()) {
                    int id = rs.getInt("IDOPCIONAIS");
                    Opcionais opcionais = map.get(id);
                    if(opcionais == null){
                        opcionais = new Opcionais();

                        opcionais.setId(rs.getInt("IDOPCIONAIS"));
                        opcionais.setTipo(rs.getString("TIPO"));

                        map.put(opcionais.getId(), opcionais);
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

    public Object conEditar(HamburguerOpcionais hamburguerOpcionais) throws Exception {

        try (Connection con = jdbcTemplate.getDataSource().getConnection()){

            editar(con, hamburguerOpcionais);

            con.commit();
        } catch (Exception ex) {
            String erro = "Connection - Erro ao editar " + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        System.out.println(NomeEntidade + " de ID" + hamburguerOpcionais.getId() + " editado com sucesso.");
        return hamburguerOpcionais;
    }

    public HamburguerOpcionais editar(Connection con, HamburguerOpcionais hamburguerOpcionais) throws Exception {

        String sql = "UPDATE HAMBURGUEROPCIONAIS SET IDHAMBURGUER=?, IDOPCIONAIS=? WHERE ID=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            con.setAutoCommit(false);

            ps.setInt(1, hamburguerOpcionais.getHamburguer().getId());
            ps.setInt(2, hamburguerOpcionais.getOpcionais().getId());
            ps.setInt(3, hamburguerOpcionais.getId());
            ps.execute();

        } catch (Exception ex) {
            String erro = "PreparedStatement - Erro ao editar " + NomeEntidade + " de ID" + hamburguerOpcionais.getId() + " no banco: " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        return hamburguerOpcionais;
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

        String sql = "DELETE FROM HAMBURGUEROPCIONAIS WHERE ID = ?";

        try(PreparedStatement ps = con.prepareStatement(sql)){
            con.setAutoCommit(false);

            ps.setInt(1, id);
            ps.executeUpdate();

            con.commit();
        } catch (Exception ex) {
            String erro = "PreparedStatement - Erro ao excluir " + NomeEntidade + " de ID" + id + " no banco: " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }
        return true;
    }

    public boolean excluirPorHamburguer(Connection con, int id) throws Exception {

        String sql = "DELETE FROM HAMBURGUEROPCIONAIS WHERE IDHAMBURGUER = ?";

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
