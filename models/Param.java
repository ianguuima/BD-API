package me.chest.database.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class Param {

    private String key;
    private String value;

    @Override
    public String toString() {
        return key+"="+value;
    }

    public String toStringEncoded(){
        return key+"=?";
    }
}
