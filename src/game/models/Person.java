package game.models;

import java.util.Random;

public class Person extends GameObject {

    public String firstName;
    public String lastName;
    public String sex;
    public int age = -1;
    public Job job;

    // Dev variable, needs to be true
    boolean properlyBuilt;


    public Person() {
        pickRandomSex();
        firstName = GameData.getRandomName(sex);
        lastName = GameData.getRandomName("Last");
        updateName();
    }

    public Person setSex(boolean sex) {
        if (sex)
            this.sex = "Male";
        else
            this.sex = "Female";
        return this;
    }

    public Person setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Person setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Person setAge(int age) {
        this.age = age;
        return this;
    }

    public Person build() {
        if (sex.isEmpty())
            pickRandomSex();
        if (firstName.isEmpty())
            firstName = GameData.getRandomName(sex);
        if (lastName.isEmpty())
            lastName = GameData.getRandomName("Last");
        if (age == -1)
            age = new Random().nextInt(51);

        updateName();
        properlyBuilt = true;
        return this;
    }

    private void pickRandomSex() {
        Random random = new Random();
        boolean randomSex = random.nextBoolean();
        if (randomSex)
            this.sex = "Male";
        else
            this.sex = "Female";
    }

    private void updateName() {
        String jobTitle = "";
        if (job != null) {
            jobTitle = job.name + " ";
        }
        name = jobTitle + firstName + " " + lastName;
    }
}
