//America Chavez
//11-25-2025
//create classes so I can make characters

public class Character {

    //create variables
    private String name;
    private String species;
    private String ability;

    //default constructor
    public Character(String name, String species, String ability) {
        this.name = name;
        this.species = species;
        this.ability = ability;
    }

    //accessors
    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public String getAbility() {
        return ability;
    }

    //mutators
    public void setName(String name) {
        this.name = name;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    //make the ability to clone a character into another value
    public Character createSameCharacter() {
        return new Character (name, species, ability);
    }

    //return the object
    @Override
    public String toString() {
        return "Name: " + name + "\nSpecies: " + species + "\nAbility: " + ability;
    }
}