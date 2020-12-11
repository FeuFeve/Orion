package game.models;

import com.google.gson.reflect.TypeToken;
import game.utilities.Date;
import game.utilities.FileManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Resource extends Quantifiable {

    public String type;
    public int maxAmount;


    public Resource(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Resource(Resource resource) {
        this(resource.name, resource.type);
    }

    public int add(int amount) {
        this.amount += amount;
        if (maxAmount != 0) {
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
    public static List<Resource> init(String filePath) {
        System.out.print("(" + Date.getRealDate() + ") Loading resources...");

        Type type = new TypeToken<ArrayList<Resource>>(){}.getType();
        List<Resource> resources = (List<Resource>) FileManager.loadArrayFromJson(filePath, type);

        System.out.println(" Done.");
        return resources;
    }

    @Override
    public String toString() {
        return type + ": " + amount + "/" + maxAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return name.equals(resource.name) && type.equals(resource.type);
    }
}
