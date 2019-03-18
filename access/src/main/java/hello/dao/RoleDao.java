package hello.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import hello.entity.Role;

@Component
public class RoleDao {

    // fake roles table in "database"
    static Map<Integer, String> roles = new HashMap<>();
    static {
        roles.put(0, "CAT");
        roles.put(1, "HUMAN");
        roles.put(2, "DOG");
        roles.put(3, "RABBIT");
        roles.put(4, "DRIVER");
    }

    public Role getById(Integer id) {
        try {
            return new Role(id, roles.get(id));
        } catch (NullPointerException e) {
            return null;
        }
    }

    public List<Role> getAll() {
        List<Role> allRoles = new ArrayList<>();
        for(Entry<Integer, String> roleRow: roles.entrySet()) {
            allRoles.add(new Role(roleRow.getKey(), roleRow.getValue()));
        }
        return allRoles;
    }
}