package edu.ithaca.goosewillis.icook.user;

import edu.ithaca.goosewillis.icook.util.FileUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserManager {

    private static UserManager instance = new UserManager();

    private Map<String, User> userMap;
    private File userDirectory;

    private UserManager() {
        this.userMap = new HashMap<>();
        this.userDirectory = new File("/users");
        if (!this.userDirectory.exists()) {
            this.userDirectory.mkdir();
        }
    }

    public User getUser(String username) {
        User user = userMap.get(username);
        if (user == null)
            return null;
        return user;
    }

    public void loadUsers() throws Exception {
        this.userMap = new HashMap<>();
        for (File file : userDirectory.listFiles()) {
            User user = new UserSerializer().deserialize(FileUtil.readFromJson(file));
            userMap.put(user.getUsername(), user);
        }
    }

    public void saveUser(User user) throws IOException {
        File file = new File(userDirectory, user.getUsername() + "_" + user.getPassword() + ".json");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter writer = new FileWriter(file);
        writer.write(FileUtil.gson.toJson(new UserSerializer().serialize(user)));
        writer.flush();
        writer.close();
    }

    public void deleteUser(String username) {
        User user = getUser(username);
        if (user != null) {
            //TODO:
        }
    }

    public Map<String, User> getUserMap() {
        return userMap;
    }

    public static UserManager getInstance() {
        return instance;
    }
}
