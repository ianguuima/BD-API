package me.chest.database.type;

import br.com.infernalia.flat.Flat;
import br.com.infernalia.flat.impl.FlatArrayList;
import lombok.val;
import me.chest.database.models.Param;
import me.chest.database.models.ResultSetContents;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static me.chest.Chest.getInstance;

public abstract class ConnectionBase {

    private String tabela;
    private String[] colunas;

    public ConnectionBase() {
        tabela = getInstance().getConnectionManager().getTabela();
        colunas = getInstance().getConnectionManager().getColunas();
    }

    /**
     * Essa é a base do sistema.
     * Aqui iremos facilitar a criação de qualquer nova database para o nosso sistema, incluindo as existentes.
     * Não MEXA se não sabe o que está fazendo.
     */


    Connection connection;

    abstract public void openConnection();

    abstract public void closeConnection();

    public Connection getConnection() {
        return connection;
    }

    public void insert(List<Param> insertValues) {

        final List<String> paramKeys = insertValues.stream().map(Param::getKey).collect(Collectors.toList());
        final List<String> paramValues = insertValues.stream().map(Param::getValue).map(t -> t = "?").collect(Collectors.toList());

        final val paramKeysSplit = String.join(",", paramKeys);
        final val paramValuesSplit = String.join(",", paramValues);


        String statment = "INSERT INTO " + tabela + " (" + paramKeysSplit + ") VALUES (" + paramValuesSplit + ")";
        executeAction(statment, insertValues, null);
    }

    public void delete(List<Param> whereParams) {
        final List<String> paramKeys = whereParams.stream().map(Param::toStringEncoded).collect(Collectors.toList());

        String statment = "DELETE FROM " + tabela + " WHERE " + paramKeys;
        executeAction(statment, whereParams, null);
    }

    public void update(List<Param> paramsList, List<Param> whereParams) {
        final List<String> whereParamKeys = whereParams.stream().map(Param::toStringEncoded).collect(Collectors.toList());
        final List<String> paramListKeys = paramsList.stream().map(Param::toStringEncoded).collect(Collectors.toList());

        final val paramListKeysFiltered = String.join(",", paramListKeys);
        final val paramWhereKeysFiltered = String.join(",", whereParamKeys);


        String statment = "UPDATE " + tabela + " SET " + paramListKeysFiltered + " WHERE " + paramWhereKeysFiltered;
        executeAction(statment, paramsList, whereParams);
    }

    public Optional<ResultSetContents> select(List<Param> paramsList) {
        final List<String> paramListKeys = paramsList.stream().map(Param::toStringEncoded).collect(Collectors.toList());
        final val paramListKeysFiltered = String.join(",", paramListKeys);

        String statment = "SELECT * FROM " + tabela + " WHERE " + paramListKeysFiltered;
        return executeQuery(statment, paramsList);
    }

    private void executeAction(String statment, List<Param> paramsList, @Nullable List<Param> conditions) {

        final val principalConnection = getInstance().getConnectionManager().getPrincipalConnection();
        principalConnection.openConnection();

        try (PreparedStatement ps = principalConnection.getConnection().prepareStatement(statment)) {

            convertValue(ps, paramsList, conditions);

            ps.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            principalConnection.closeConnection();
        }

    }

    private Optional<ResultSetContents> executeQuery(String statment, List<Param> paramsList) {

        final val principalConnection = getInstance().getConnectionManager().getPrincipalConnection();
        principalConnection.openConnection();

        try (PreparedStatement ps = principalConnection.getConnection().prepareStatement(statment)) {

            convertValue(ps, paramsList, null);
            final val resultSet = ps.executeQuery();

            if (resultSet.next()) {

                Flat<Param> dataParams = new FlatArrayList<>();

                for (String column : colunas) {

                    String columnRefactored = column.split(" ")[0];

                    final val resultData = resultSet.getString(columnRefactored);
                    dataParams.add(new Param(columnRefactored, resultData));
                }

                return Optional.of(new ResultSetContents(dataParams, true));
            } else return Optional.empty();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return Optional.empty();
    }


    private void convertValue(PreparedStatement ps, List<Param> paramsList, @Nullable List<Param> conditions) throws SQLException {
        for (Param v : paramsList) {
            final int index = paramsList.indexOf(v) + 1;
            ps.setString(index, v.getValue());
        }

        if (conditions != null) {
            int lastValue = paramsList.size();
            for (Param condition : conditions) {
                int index = lastValue + 1;
                ps.setString(index, condition.getValue());
            }
        }
    }

    public AsyncConnectionBase async() {
        return new AsyncConnectionBase(this);
    }


}
