package ru.spbau.mit;

import java.util.HashMap;
import java.util.Map;


/**
 * The Environment class maintain and changes variable values
 */
public class Environment {
    private Map<String, String> variables = new HashMap<>();

    /**
     *
     * @param name assigns value to variable `name`
     * @param value
     */

    public void assign(String name, String value) {
        variables.put(name, value);
    }

    /**
     *
     * @param name
     * @return string value of variable ''name
     */

    public String getValue(String name) {
        return variables.get(name);
    }

}