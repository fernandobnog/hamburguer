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
public class PedidoDao {
    String NomeEntidade = "Pedido";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public PedidoDao(){}

    public Object conInserir(Pedido pedido) throws Exception {

        try (Connection con = jdbcTemplate.getDataSource().getConnection()){

            inserir(con, pedido);

            con.commit();
        } catch (Exception ex) {
            String erro = "Connection - Erro ao inserir" + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        System.out.println(NomeEntidade + " de ID " + pedido.getId() + " inserido com sucesso");
        return pedido;
    }

    public Object inserir(Connection con, Pedido pedido) throws Exception {

        pedido.setHamburguer((Hamburguer) new HamburguerDao().inserir(con, pedido.getHamburguer()));

        String sql = "INSERT INTO PEDIDO (IDHAMBURGUER, IDPESSOA, STATUSPEDIDO) VALUES (?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            con.setAutoCommit(false);

            ps.setInt(1, pedido.getHamburguer().getId());
            ps.setInt(2, pedido.getPessoa().getId());
            ps.setBoolean(3, false);
            ps.execute();

            try(ResultSet tableKeys = ps.getGeneratedKeys()){
                tableKeys.next();
                pedido.setId(tableKeys.getInt(1));
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

        return pedido;
    }

    public List<Pedido> conListar() throws Exception {

        List<Pedido> lista = new ArrayList<>();

        try (Connection con = jdbcTemplate.getDataSource().getConnection()) {

            lista = listar(con);

        } catch (Exception ex) {
            String erro = "Connection - Erro ao listar " + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        return lista;
    }

    public List<Pedido> listar(Connection con) throws Exception {

        String sql = "SELECT *\n" +
                "FROM PEDIDO\n" +
                "JOIN PESSOA G ON G.ID=IDPESSOA\n" +
                "JOIN HAMBURGUER H ON IDHAMBURGUER = H.ID";

        Map<Integer, Pedido> map = new TreeMap<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.execute();

            try (ResultSet rs = ps.getResultSet()){
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    Pedido pedido = map.get(id);
                    if(pedido == null){
                        pedido = new Pedido();

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

                        Hamburguer hamburguer = new Hamburguer();
                        hamburguer.setId(rs.getInt("IDHAMBURGUER"));
                        hamburguer.setPao(pao);
                        hamburguer.setCarne(carne);
                        hamburguer.setQueijo(queijo);

                        HamburguerOpcionaisDao hmd = new HamburguerOpcionaisDao();
                        List<Opcionais> opcionais = hmd.listarOpcionaisPorHamburguer(con, hamburguer);

                        HamburguerSaladaDao hsd = new HamburguerSaladaDao();
                        List<Salada> saladas = hsd.listarSaladasPorHamburguer(con, hamburguer);

                        hamburguer.setSaladas(saladas);
                        hamburguer.setOpcionais(opcionais);

                        Pessoa pessoa = new Pessoa();
                        pessoa.setId(rs.getInt("IDPESSOA"));
                        pessoa.setNome(rs.getString("NOME"));

                        pedido.setId(rs.getInt("ID"));
                        pedido.setPessoa(pessoa);
                        pedido.setHamburguer(hamburguer);
                        pedido.setStatusPedido(rs.getBoolean("STATUSPEDIDO"));

                        map.put(pedido.getId(), pedido);
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

    public Object conEditar(Pedido pedido) throws Exception {

        try (Connection con = jdbcTemplate.getDataSource().getConnection()){

            editar(con, pedido);

            con.commit();
        } catch (Exception ex) {
            String erro = "Connection - Erro ao editar " + NomeEntidade + ": " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        System.out.println(NomeEntidade + " de ID" + pedido.getId() + " editado com sucesso.");
        return pedido;
    }

    public Pedido editar(Connection con, Pedido pedido) throws Exception {

        String sql = "UPDATE PEDIDO SET IDHAMBURGUER=?, IDPESSOA=?, STATUSPEDIDO=? WHERE ID=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            con.setAutoCommit(false);

            ps.setInt(1, pedido.getHamburguer().getId());
            ps.setInt(2, pedido.getPessoa().getId());
            ps.setBoolean(3, pedido.isStatusPedido());
            ps.setInt(4, pedido.getId());

            ps.execute();

        } catch (Exception ex) {
            String erro = "PreparedStatement - Erro ao editar " + NomeEntidade + " de ID" + pedido.getId() + " no banco: " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }

        return pedido;
    }

    public boolean excluir(Pedido pedido) throws Exception {

        String sql = "DELETE FROM PEDIDO WHERE ID = ?";

        try (Connection con = jdbcTemplate.getDataSource().getConnection()){

            try(PreparedStatement ps = con.prepareStatement(sql)){
                con.setAutoCommit(false);

                ps.setInt(1, pedido.getId());
                ps.executeUpdate();

                new HamburguerDao().excluir(con, pedido.getHamburguer());

                con.commit();
            } catch (Exception ex) {
                String erro = "PreparedStatement - Erro ao excluir " + NomeEntidade + " de ID" + pedido.getId() + " no banco: " + ex;
                System.err.println(erro);
                throw new Exception(erro);
            }
        } catch (Exception ex) {
            String erro = "Connection - Erro ao excluir " + NomeEntidade + " de ID" + pedido.getId() + " no banco: " + ex;
            System.err.println(erro);
            throw new Exception(erro);
        }



        System.out.println(NomeEntidade + " de id. " + pedido.getId() + " excluido com sucesso.");
        return true;
    }
}
