package ru.spbau.mit;

import java.util.HashMap;
import java.util.Map;


/**
 * The Environment class maintain and changes variable values
 */
public class Environment {
    private Map<String, String> variables = new HashMap<>();

    /**
     * Function assigns the value of the environment variable value
     *
     * @param name  assigns value to variable `name`
     * @param value the desired value of the environment variable `name`
     */

    public void assign(String name, String value) {
        variables.put(name, value);
    }

    /**
     * Function gets the value of the environment variable
     *
     * @param name - name of the env variable
     * @return string value of variable `name`
     */

    public String getValue(String name) {
        return variables.get(name);
    }

}