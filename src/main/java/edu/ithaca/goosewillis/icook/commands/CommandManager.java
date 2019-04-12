package edu.ithaca.goosewillis.icook.commands;

import edu.ithaca.goosewillis.icook.CLI;
import edu.ithaca.goosewillis.icook.State;
import edu.ithaca.goosewillis.icook.cookbook.CookBook;
import edu.ithaca.goosewillis.icook.fridge.Fridge;
import edu.ithaca.goosewillis.icook.recipes.Recipe;
import edu.ithaca.goosewillis.icook.recipes.ingredients.DietType;
import edu.ithaca.goosewillis.icook.recipes.ingredients.Ingredient;
import edu.ithaca.goosewillis.icook.user.User;

import java.io.File;
import java.util.*;

public class CommandManager {

    private CookBook cookBook;
    private User user;
    private Boolean loggedIn = false;

    public CommandManager(CookBook cookBook) {
        this.cookBook = cookBook;
    }

    public void setStatus(Boolean status){
        this.loggedIn = status;
    }

    public void setUser(User user){
        this.user = user;
    }

    private Map<String, Command> commands = new HashMap<>();

    public Command getCommand(String commandName) {
        Command command = commands.get(commandName);
        if (command == null) return command;
        return command;
    }

    public boolean executeCommand(String commandName, List<String> arguments) {
        Command toExecute = getCommand(commandName);
        if (toExecute == null) return false;
        toExecute.execute(arguments);
        return true;
    }

    public void loadCommands() {
        Command quitCommand = new Command("quit") {
            @Override
            public boolean execute(List<String> arguments) {
                CLI.cliState = State.STOPPING;
                if(loggedIn){
                    CLI.logout(user);
                    setUser(null);
                    setStatus(false);
                }
                return true;
            }
        };
        commands.put(quitCommand.getCommandName(), quitCommand);

        //Search Recipe By Time
        Command searchRecipeByTime = new Command("time") {
            @Override
            public boolean execute(List<String> arguments) {
                if(loggedIn){
                    double minutes = Double.valueOf(arguments.get(1));
                    Set<Recipe> recipes = cookBook.getRecipesByTime(minutes);
                    for (Recipe r : recipes){
                        System.out.println(r.getName());
                    }
                }else{
                    System.out.println("Please log in to access this function. use 'help'");
                }

                return true;
            }
        };
        commands.put(searchRecipeByTime.getCommandName(), searchRecipeByTime);

        //Search recipe by name
        Command searchRecipeByName = new Command("name") {
            @Override
            public boolean execute(List<String> arguments) {
                if (loggedIn){
                    //arguments.remove(0);
                    List<String> name = new ArrayList<>();
                    for (int i=1; i<arguments.size(); i++){
                        name.add(arguments.get(i));
                    }
                    String recipeName = String.join(" ", name);
                    Recipe recipe = cookBook.getRecipe(recipeName);
                    System.out.println(recipe.display());
                }
                else{
                    System.out.println("Please log in to access this function. use 'help'");
                }
                return false;
            }
        };
        commands.put(searchRecipeByName.getCommandName(), searchRecipeByName);

        //Search recipes by tag
        Command searchRecipeByTag = new Command("tag") {
            @Override
            public boolean execute(List<String> arguments) {
                if (loggedIn) {
                    //arguments.remove(0);
                    String arg = arguments.get(1);
                    //String tag = arg.substring(0, 1).toUpperCase() + arg.substring(1);
                    String tag = arg;
                    DietType restriction = null;
                    if (tag.equals("Vegetarian")) {
                        restriction = DietType.Vegetarian;
                    } else if (tag.equals("Vegan")) {
                        restriction = DietType.Vegan;
                    } else if (tag.equals("Gluten")) {
                        restriction = DietType.GlutenFree;
                    } else if (tag.equals("Dairy")) {
                        restriction = DietType.NonDairy;
                    }
                    System.out.println(restriction);
                    Set<Recipe> recipes = cookBook.getRecipesByTag(restriction);
                    for (Recipe r : recipes) {
                        System.out.println(r.getName());
                    }
                }else{
                    System.out.println("Please log in to access this function. use 'help'");
                }
                return false;
            }
        };
        commands.put(searchRecipeByTag.getCommandName(), searchRecipeByTag);


        Command recommendRecipe = new Command("recommend") {
            @Override
            public boolean execute(List<String> arguments) {
                if (loggedIn) {
                    List<Recipe> recommended = cookBook.getRecipeRecommendations(user.getFridge());
                    for (Recipe r : recommended) {
                        System.out.println(r.getName());
                    }
                }else{
                    System.out.println("Please log in to access this function. use 'help'");
                }
                return false;
            }
        };
        commands.put(recommendRecipe.getCommandName(), recommendRecipe);


        Command register = new Command("register") {
            @Override
            public boolean execute(List<String> arguments) {
                if (!loggedIn) {
                    //arguments.remove(0);
                    try {
                        User u = CLI.createUser(arguments.get(1), arguments.get(2));
                        setUser(u);
                        setStatus(true);
                        System.out.println("You have created an account and been logged in, Checkout your fridge!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    System.out.println("You are already logged in");
                }
                return false;
            }
        };
        commands.put(register.getCommandName(), register);


        Command login = new Command("login") {
            @Override
            public boolean execute(List<String> arguments) {
                if (!loggedIn){
                    //arguments.remove(0);
                    User u = CLI.login(arguments.get(1), arguments.get(2));
                    if (u!=null){
                        setUser(u);
                        setStatus(true);
                        System.out.println("Successfully logged in");
                    }
                }else{
                    System.out.println("You are already logged in");
                }
                return false;
            }
        };
        commands.put(login.getCommandName(), login);

        Command logout = new Command("logout") {
            @Override
            public boolean execute(List<String> arguments) {
                if (loggedIn){
                    CLI.logout(user);
                    setUser(null);
                    setStatus(false);
                    System.out.println("You have been logged out");
                }
                return false;
            }
        };
        commands.put(logout.getCommandName(), logout);

        Command displayFridge = new Command("display") {
            @Override
            public boolean execute(List<String> arguments) {
                if (loggedIn){
                    Fridge f = user.getFridge();
                    for (Ingredient i : f.getIngredients()){
                        System.out.println(i.toString());
                    }
                }else{
                    System.out.println("Please log in to access this function. use 'help'");
                }
                return false;
            }
        };
        commands.put(displayFridge.getCommandName(), displayFridge);

        Command addToFridge = new Command("add") {
            @Override
            public boolean execute(List<String> arguments) {
                if (loggedIn){
                    for (int i=1; i<arguments.size(); i++){
                        user.addToFridge(new Ingredient(arguments.get(i), 1, 10));
                    }
                    System.out.println("Ingredients Added");
                }else {
                    System.out.println("Please log in to access this function. use 'help'");
                }
                return false;
            }
        };
        commands.put(addToFridge.getCommandName(), addToFridge);

        Command help = new Command("help") {
            @Override
            public boolean execute(List<String> arguments) {
                System.out.println("Commands:\n" +
                        "quit                           logs user out and exits program\n"+
                        "login [username] [password]    logs user in\n" +
                        "register [username] [password] creates a new account\n"+
                        "logout                         logs user out\n" +
                        "name [recipe name]             search for a recipe by name, displays all recipe info\n" +
                        "time [minutes]                 searches for recipes based on time, returns a list of suitable recipes\n" +
                        "tag [type]                     (only 4 tags exist, 'vegetarian' 'vegan' 'gluten' 'dairy') searches for recipe based on restrictions\n" +
                        "recommend                      returns a list of recipes\n" +
                        "add [ingredient] [....]        adds ingredients to your fridge\n" +
                        "display                        displays the contents of your fridge");

                return false;
            }
        };
        commands.put(help.getCommandName(), help);


        Command oneTray = new Command("generate") {
            @Override
            public boolean execute(List<String> arguments) {
                //TODO
                return false;
            }
        };
    }
}



