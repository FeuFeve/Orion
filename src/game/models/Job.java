package game.models;

import com.google.gson.reflect.TypeToken;
import game.utilities.Date;
import game.utilities.FileManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Job extends Quantifiable {

    public int currentWorkers;


    public Job(Job job) {
        name = job.name;
        amount = job.amount;
        currentWorkers = job.currentWorkers;
    }

    @SuppressWarnings("unchecked")
    public static List<Job> init(String filePath) {
        System.out.print("(" + Date.getRealDate() + ") Loading jobs...");

        Type type = new TypeToken<ArrayList<Job>>(){}.getType();
        List<Job> resources = (List<Job>) FileManager.loadArrayFromJson(filePath, type);

        System.out.println(" Done.");
        return resources;
    }
}
