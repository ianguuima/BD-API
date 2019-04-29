package me.imoedas.connections;

import lombok.Getter;
import me.imoedas.connections.type.ConnectionBase;
import me.imoedas.connections.type.MySQL;
import me.imoedas.connections.type.SQLite;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static me.imoedas.iMoedas.*;

public class ConnectionManager {

    @Getter
    private ConnectionBase principalConnection;

    public ConnectionManager() {
        principalConnection = getMainConnection();
        criarTabela("PlayerMoedas", "nome varchar(32), moedas int, compras int");

        metodoExemplo();
    }



    public void metodoExemplo(){
        try {
            principalConnection.openConnection();
            Connection connection = principalConnection.getConnection();
            connection.createStatement().executeUpdate("UPDATE...");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            principalConnection.closeConnection();
        }
    }


    /**
     * A API precisa descobrir que tipo de armazenamento a pessoa que está utilizando o seu plugin quer.
     * Aqui a verificação será feita. Verifique a documentação que está no Github.
     * Ela te ensinará a como configurar essa área!
     */

    private ConnectionBase getMainConnection(){
        ConfigurationSection storageSection = getInstance().getConfig().getConfigurationSection("Storage");

        String connectionType = storageSection.getString("TYPE");


        // Serão feitas as configurações devidas para o armazenamento específico selecionado.

        if (connectionType.equalsIgnoreCase("MYSQL")){


            String host = storageSection.getString("Host");
            int port = storageSection.getInt("Port");
            String username = storageSection.getString("Username");
            String password = storageSection.getString("Password");
            String database = storageSection.getString("Database");

            return new MySQL(host, port, username, password, database);
            // A conexão escolhida foi o MySQL, então a conexão base adotará o MySQL como principal!
        }


        if (connectionType.equalsIgnoreCase("SQLITE")){

            return new SQLite();
            // A conexão escolhida foi o SQLite, então a conexão base adotará o SQLite como principal!

        }


        return null;
    }


    /**
     * O metódo fará o metódo de criação de tabelas, não mexa nele, a não ser que saiba o que está fazendo.
     * Crie suas tabelas no constructor acima! (A partir da linha 23)
     */

    private void criarTabela(String tabela, String colunas) {
        principalConnection.openConnection();
        try {
            Connection connection = principalConnection.getConnection();
            if ((principalConnection.getConnection() != null) && (!connection.isClosed())) {
                Statement stm = connection.createStatement();
                stm.executeUpdate("CREATE TABLE IF NOT EXISTS " + tabela + " (" + colunas + ");");
            }
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao salvar o MYSQL!");
        } finally {
            if (principalConnection.getConnection() != null) principalConnection.closeConnection();
        }
    }

}
