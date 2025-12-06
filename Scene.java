//America Chavez
//12-8-2025
//create classes so I can make scenes

public class Scene {
    
    //create variables
    public String prompt;
    public boolean isDeath;
    public String type;
    public Scene next;
    public Scene altNext;

    //default constructor
    public Scene (String prompt, boolean isDeath, String type) {
        this.prompt = prompt;
        this.isDeath = isDeath;
        this.type = type;
    }
    
}