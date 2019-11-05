package me.chest.database.models;

import br.com.infernalia.flat.Flat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @AllArgsConstructor
public class ResultSetContents {

    private Flat<Param> params;
    private boolean containsSearch;

}
