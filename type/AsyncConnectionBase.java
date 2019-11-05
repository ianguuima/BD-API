package me.chest.database.type;

import me.chest.database.models.Param;
import me.chest.database.models.ResultSetContents;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static me.chest.Chest.getInstance;

public class AsyncConnectionBase {

    private ConnectionBase base;
    private String tabela;

    public AsyncConnectionBase(ConnectionBase connectionBase) {
        tabela = getInstance().getConnectionManager().getTabela();
        this.base = connectionBase;
    }

    /**
     * Essa é a base do sistema.
     * Aqui iremos facilitar a criação de qualquer nova database para o nosso sistema, incluindo as existentes.
     * Não MEXA se não sabe o que está fazendo.
     */

    public void insert(List<Param> insertValues, Consumer<Throwable> error){
        CompletableFuture.runAsync(
                () -> base.insert(insertValues)
        ).whenComplete((aVoid, throwable) -> error.accept(throwable));
    }

    public void delete(List<Param> whereParams, Consumer<Throwable> error){
        CompletableFuture.runAsync(
                () -> base.delete(whereParams)
        ).whenComplete((aVoid, throwable) -> error.accept(throwable));
    }

    public void update(List<Param> paramsList, List<Param> whereParams, Consumer<Throwable> error){
        CompletableFuture.runAsync(
                () -> base.update(paramsList, whereParams)
        ).whenComplete((aVoid, throwable) -> error.accept(throwable));
    }

    public void select(List<Param> paramsList, Consumer<Optional<ResultSetContents>> consumer) {
        CompletableFuture.supplyAsync(
                () -> base.select(paramsList)
        ).whenComplete((resultSetContents, throwable) -> consumer.accept(resultSetContents));
    }


}
