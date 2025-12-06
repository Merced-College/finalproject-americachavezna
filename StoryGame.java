//America Chavez
//12-11-2025
//Final Project

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class StoryGame {

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        //Call the method rollDice()
        StoryGame luck = new StoryGame();
        int diced = luck.rollDice();

        //list the array of characters available using the method createCharacters
        System.out.println("Available Characters: ");
        Character[] characters =  createCharacters();
        
        for (Character c : characters) {
            System.out.println(c);
        }

        //Create a chosenCharacter variable which will be filled in later
        Character chosenCharacter = null;

        //while loop prompts the user to choose a character
        boolean unAnswered = true;

        while (unAnswered) {
            String newMessage = ("Which character will you play as? (Enter their name only)");
            
            //Call method printSlowly for easier readability
            printSlowly(newMessage);
            System.out.println();
            String chosen = scnr.next();

            //Search for the matching character as the user has entered
            for (int i=0; i < characters.length; i++) {
                if (characters[i].getName().equals(chosen)) {
                    chosenCharacter = characters[i].createSameCharacter();
                    System.out.println("You chose: " + "\n" + chosenCharacter.getName());
                    unAnswered = false;
                    break;
                }

                //Throw back error if user wrote anything else
                else if (characters[i].equals(characters[characters.length -1])) {
                    System.out.println("Invalid Option. Write only the character's name as it is printed.");
                }

        }
    }

        //Create an array which holds the information found in method createScenes
        Scene [] scenes = createScenes();

        //Link Scenes in linear method
        for (int i=0; i < scenes.length-1; i++) {
            scenes[i].next = scenes[i+1];
        }
        scenes[scenes.length-1].next = null;
        
        //Link branching scenes
        scenes[1].altNext = scenes[3];
        scenes[5].altNext = scenes[8];
        scenes[9].altNext = scenes[12];

        //Play out the main storyline
        Scene current = scenes[0];
        //While loop runs as long as there are scenes
        while (current != null) {
            //call method printSlowly for easier readability
            printSlowly(current.prompt);
            System.out.println();

            //End the loop if the current scene is a death
            if (current.isDeath) {
                System.out.println("Play again next time.");
                return;
            }

            //if there is an alternate choice, method rollDice is called
            if (current.altNext != null) {
                diced = luck.rollDice();

                //weight variable initialized to 0, will be filled later
                int weight = 0;

                //if the scene corresponds with a certain type of species the code runs
                if (!current.type.equals("none")) {
                    //checks if the user's chosenCharacter is the same as the scene's type
                    if (chosenCharacter.getSpecies().equals(current.type)) {
                        weight = 8;
                        System.out.println("Since you chose " + chosenCharacter.getName() + " who is a " 
                        + chosenCharacter.getSpecies() + " your chances have increased to 80/20.");
                    }
                    //otherwise the weight is the base weight
                    else {
                        weight = 5;
                    }
                }

                //inform user of their roll
                System.out.println("You rolled: " + diced);
                //check if user won, if the user rolled less than or equal to the weight, they won
                if (diced <= weight) {
                    System.out.println("You survived.");
                    //if they win, the following link is to altNext
                    current = current.altNext;
                }
                else {
                    current = current.next;
                }
            }

            //if the scene has no type of species, scene simply jumps over to next scene
            else if (current.next != null) {
                current = current.next;
            }

            //if there is some form of error, loop breaks
            else {
                return;
            }

            System.out.println();
        }

       scnr.close();
}//end of main class

    //following method was written with brother's help
    //method splits the text lines into forms that the object Scene can understand
    public static Scene loadSceneFromTxt(String wholeLine) {
        
        //immediately throw back error if the line does not exist
        if (wholeLine.equals(null)) {
            System.out.println("Invalid line. Can not create scene.");
            return null;
        }

        //otherwise, make an array which holds the current string in parts
        String[] splitLine = wholeLine.split(";");

        //given that the lines should always have 3 parts, throw back error if not
        int count = splitLine.length;

        if(count != 3){
            System.out.println("Invalid text length of " + count);
            return null;
        }

        //turn the index splitLine[1] into boolean, as its meant to be
        boolean death;
        if(splitLine[1].equals("true")) {
            death = true;
        }

        else if (splitLine[1].equals("false")) {
            death = false;
        }

        //throw back error if there is anything else in splitLine[1]
        else {
            System.out.println("Invalid boolean value: '" + splitLine[1]+ "'");
            return null;
        }

        //create a Scene object with the parts we created
        Scene scene_ = new Scene(splitLine[0], death, splitLine[2]);
        return scene_;
    }//end of method

    //method reads in scenes from the scenes file
    public static Scene[] createScenes() {
        Scanner read = null;
        //create a list which will hold the scenes
        List<Scene> scenes = new ArrayList<Scene>();

        //read in the lines and add them to the list
        try{
            read = new Scanner(new File("scenes.txt"));
            while (read.hasNextLine()) {
                String line = read.nextLine();
                //call previous method 
                Scene scene = loadSceneFromTxt(line);
                if(scene != null) {
                    scenes.add(scene);
                }
            }
        }

        //throw back error if the file is missing
        catch (FileNotFoundException e) {
            System.out.println ("File not found: " + e.getMessage());
        }

        //create an array with the values inside of the list
        Scene[] scns = new Scene[scenes.size()];
        for(int i = 0; i < scenes.size(); i++){
            scns[i] = scenes.get(i);
        }

        read.close();
        return scns;
    }//end of method

    //following method was written using brother's help
    //method splits the lines into parts that can be made into Character objects
    public static Character loadCharacterFromTxt(String wholeLine) {

        //immediately throw back error if line is empty
        if (wholeLine.equals(null)) {
            System.out.println("Invalid line. Can not create character.");
            return null;
        }

        //make an array which holds the parts of the line
        String[] splitLine = wholeLine.split(";");
        int count = splitLine.length;
        
        //given that Character objects have 3 parts, any other text length than 3 is wrong
        if(count != 3){
            System.out.println("Invalid text length of " + count);
            return null;
        }

        //make a new Character object using the parts of the array
        Character char_ = new Character(splitLine[0], splitLine[1], splitLine[2]);
        return char_;
    }//end of method

    //method reads in chracters from the characters file
    public static Character[] createCharacters() {
        Scanner read = null;

        //create a list which will hold all the characters
        List<Character> chars = new ArrayList<Character>();

        //read in the lines
        try{
            read = new Scanner(new File("characters.txt"));
            while (read.hasNextLine()) {
                String line = read.nextLine();
                //call previous method to create object
                Character character = loadCharacterFromTxt(line);
                if(character != null) {
                    chars.add(character);
                }
            }
        }

        //throw back error if file is missing
        catch (FileNotFoundException e) {
            System.out.println ("File not found: " + e.getMessage());
        }

        //make an array that holds the same values as list
        Character[] characters = new Character[chars.size()];
        for(int i = 0; i < chars.size(); i++){
            characters[i] = chars.get(i);
        }

        read.close();
        return characters;
    }//end of method

    //following method 'rolls the dice'
    public int rollDice() {
    
        //make a new array which will hold the dice values
        int [] numbered = new int[10];

        //fill in dice values
        for (int i = 0; i < numbered.length; i++) {
            if (numbered[i] == 0) {
            numbered[i] = 1;
        }

        else {
        numbered[i] = numbered[i-1] + 1;
        }

    }

    //create an instance of 'Random'
    Random random = new Random();

    //roll the dice
    int rolled = random.nextInt(numbered.length);

    return rolled;

}//end of method

    //following code found on StackOverflow and improved for this program
    //method to print slowly for better readability
    public static void printSlowly(String toPrint) {

        //separate the given string into characters and fill array
        char[] chars = toPrint.toCharArray();

        //print each character slowly
        for (int i = 0; i < chars.length; i++) {
            System.out.print(chars[i]);
            try {
        Thread.sleep(20);
    } 

        //override the error that happens when thread is interrupted
        catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        return;
    }
    }
}//end of method
//end StackOverFlow code

}//end class