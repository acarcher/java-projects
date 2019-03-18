package hello.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.springframework.stereotype.Component;

import hello.entity.Role;
import hello.entity.User;

@Component
public class UserDao {

    // users table in "database"
    private static Map<Integer, Triplet<String, String, String>> usersTable = new HashMap<>();
    static {
        usersTable.put(0, new Triplet<>("Alex Archer", "alex", "{noop}foo"));
        usersTable.put(1, new Triplet<>("Snuggly Archer", "snuggly", "{noop}bar"));
    }

    // users <-> roles table in "database"
    private static List<Pair<Integer, Integer>> usersRolesTable = new ArrayList<>();
    static {
        usersRolesTable.add(new Pair<>(0, 1));
        usersRolesTable.add(new Pair<>(0, 4));
        usersRolesTable.add(new Pair<>(1, 3));
    }

    public User getById(Integer id) {

        // ORM: SELECT * FROM users WHERE id = id
        User user;
        try {
            Triplet<String, String, String> userRow = usersTable.get(id);
            user = new User(id, userRow.getValue0(), userRow.getValue1(), userRow.getValue2());
        } catch (NullPointerException e) {
            user = null;
        }

        List<Role> userRoles = new ArrayList<>();
        // if the user exists, look up roles for that user
        if (user != null) {
            // ORM: SELECT * FROM usersRoles WHERE user_id = id
            for(Pair<Integer, Integer> userRoleRow: usersRolesTable) {
                if (userRoleRow.getValue0() == id) {
                    String roleName = RoleDao.roles.get(userRoleRow.getValue1());
                    userRoles.add(new Role(userRoleRow.getValue1(), roleName));
                }
            }
            user.setRoles(userRoles);
        }

        // return user instance with held roles attached
        return user; // source session.get(Label.class, id)
    }

    public User getByUsername(String name) {
        User user = null;
        for (Entry<Integer, Triplet<String, String, String>> userRow : usersTable.entrySet()) {
            if (userRow.getValue().getValue1().equals(name)) {
                user = getById(userRow.getKey());
                break;
            }
        }

        // return a user with matched username
        return user;
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        for (Integer id: usersTable.keySet()) {
            users.add(getById(id)); // reuse getById logic
        }

        // return user instances for all users with held roles attached
        return users;
    }
}