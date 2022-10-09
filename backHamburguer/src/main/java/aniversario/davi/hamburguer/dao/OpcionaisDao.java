package aniversario.davi.hamburguer.dao;

import aniversario.davi.hamburguer.entidades.Opcionais;
import aniversario.davi.hamburguer.interfaces.ChecarDuplicidade;
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
public class OpcionaisDao {
    String NomeEntidade = "Opcionais";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public OpcionaisDao(){}

    public Object conInserir(Opcionais opcionais) throws Exception {

        try (Connection con = jdbcTemplate.getDataSource().getConnection()){

            Object obj = inserir(con, opcionais);
            if(obj.getClass().getSimpleName().equals("Retorno")){
                return obj;
            }

            con.commit();
        } catch (Exception ex) {
            String erro = "Connection - Erro ao inserir" + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        System.out.println(NomeEntidade + " de ID " + opcionais.getId() + " inserido com sucesso");
        return opcionais;
    }

    public Object inserir(Connection con, Opcionais opcionais) throws Exception {

        Object duplicidade = ChecarDuplicidade.opcionais(con, opcionais);
        if(!duplicidade.equals("cadastrar")){
            return duplicidade;
        }

        String sql = "INSERT INTO OPCIONAIS (TIPO, QUANTIDADE, TEMQUANTIDADE) VALUES (?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            con.setAutoCommit(false);

            ps.setString(1, opcionais.getTipo());
            ps.setInt(2, opcionais.getQuantidade());
            ps.setBoolean(3, opcionais.isTemQuantidade());
            ps.execute();

            try(ResultSet tableKeys = ps.getGeneratedKeys()){
                tableKeys.next();
                opcionais.setId(tableKeys.getInt(1));
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

        return opcionais;
    }

    public List<Opcionais> conListar() throws Exception {

        List<Opcionais> lista = new ArrayList<>();

        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {

            lista = listar(con);

        } catch (Exception ex) {
            String erro = "Connection - Erro ao listar " + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        return lista;
    }

    public List<Opcionais> listar(Connection con) throws Exception {

        String sql = "SELECT * FROM OPCIONAIS\n";

        Map<Integer, Opcionais> map = new TreeMap<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.execute();

            try (ResultSet rs = ps.getResultSet()){
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    Opcionais opcionais = map.get(id);
                    if(opcionais == null){
                        opcionais = new Opcionais();

                        opcionais.setId(rs.getInt("ID"));
                        opcionais.setTipo(rs.getString("TIPO"));
                        opcionais.setQuantidade(rs.getInt("QUANTIDADE"));
                        opcionais.setTemQuantidade(rs.getBoolean("TEMQUANTIDADE"));

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

    public Object conEditar(Opcionais opcionais) throws Exception {

        try (Connection con = jdbcTemplate.getDataSource().getConnection()){

            editar(con, opcionais);

            con.commit();
        } catch (Exception ex) {
            String erro = "Connection - Erro ao editar " + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        System.out.println(NomeEntidade + " de ID" + opcionais.getId() + " editado com sucesso.");
        return opcionais;
    }

    public Opcionais editar(Connection con, Opcionais opcionais) throws Exception {

        String sql = "UPDATE OPCIONAIS SET TIPO=?, QUANTIDADE=?, TEMQUANTIDADE=? WHERE ID=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            con.setAutoCommit(false);

            ps.setString(1, opcionais.getTipo());
            ps.setInt(2, opcionais.getQuantidade());
            ps.setBoolean(3, opcionais.isTemQuantidade());
            ps.setInt(4, opcionais.getId());
            ps.execute();

        } catch (Exception ex) {
            String erro = "PreparedStatement - Erro ao editar " + NomeEntidade + " de ID" + opcionais.getId() + " no banco: " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        return opcionais;
    }

    public boolean excluir(int id) throws Exception {

        String sql = "DELETE FROM OPCIONAIS WHERE ID = ?";

        try (Connection con = jdbcTemplate.getDataSource().getConnection()){


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
        } catch (Exception ex) {
            String erro = "Connection - Erro ao excluir " + NomeEntidade + " de ID" + id + " no banco: " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        System.out.println(NomeEntidade + " de id. " + id + " excluido com sucesso.");
        return true;
    }
}
