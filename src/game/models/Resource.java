package game.models;

import com.google.gson.reflect.TypeToken;
import game.utilities.Date;
import game.utilities.FileManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Resource extends GameObject {

    public String type;

    public boolean hasMaxAmount;
    public int amount;
    public int maxAmount;


    public Resource(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public int add(int amount) {
        this.amount += amount;
        if (hasMaxAmount) {
            if (this.amount > maxAmount) {
                int overAmount = this.amount - maxAmount;
                this.amount = maxAmount;
                return overAmount;
            }
        }
        return 0;
    }

    public int take(int amount) {
        if (amount > this.amount) {
            int takenAmount = this.amount;
            this.amount = 0;
            return takenAmount;
        }
        else {
            this.amount -= amount;
            return amount;
        }
    }

    @SuppressWarnings("unchecked")
    public static HashMap<String, Resource> init(String filePath) {
        System.out.print("(" + Date.getRealDate() + ") Loading resources...");

        Type type = new TypeToken<ArrayList<Resource>>(){}.getType();
        List<Resource> list = (List<Resource>) FileManager.loadArrayFromJson(filePath, type);

        HashMap<String, Resource> resources = new HashMap<>();
        for (Resource resource : list) {
            resources.put(resource.name, resource);
        }

        System.out.println(" Done.");
        return resources;
    }
}
