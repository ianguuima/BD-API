package me.chest.database.type;

import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;

import static me.chest.Chest.getInstance;


public class SQLite extends ConnectionBase {

    /**
     * O banco de dados SQLite, estabeleceremos as conexões respectivas, abrir e fechar.
     * O metódo GET já está embutido no ConnectionBase, afinal de contas, SQLite e MySQL tem a mesma base.
     */

    private int query;

    public SQLite() {
        this.query = 0;
    }

  @Override
    public void openConnection() {
        try {
            query++;
            if ((connection != null) && (!connection.isClosed()))
                return;

            Class.forName("org.sqlite.JDBC");
            File dataFolder = new File(getInstance().getDataFolder(), "playerstorage.db");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
        } catch(SQLException | ClassNotFoundException e) {
            query--;
            e.getStackTrace();
            e.printStackTrace();
            System.out.println(
                    "Ocorreu um erro ao abrir a conexão!");
        }
    }

    @Override
    public void closeConnection() {
        query--;
        if (query <= 0) {
            try {
                if (connection != null && !connection.isClosed())
                    connection.close();
            } catch (Exception e) {
                System.out.println(
                        "Houve um erro ao fechar a conexão!");
            }
        }
    }
}
