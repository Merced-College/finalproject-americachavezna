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

    //mutators
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public void setIsDeath(boolean isDeath) {
        this.isDeath = isDeath;
    }

    public void setType(String type) {
        this.type = type;
    }

    //accessors
    public String getPrompt() {
        return prompt;
    }

    public boolean getIsDeath() {
        return isDeath;
    }

    public String getType() {
        return type;
    }
    
}