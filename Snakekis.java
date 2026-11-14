//Jayvee De Guzman
//BSIT 1-A

//import the necessary function needed 
//Array list for mutiple objects
//random is so the fruit is randomly spawned
//Scanner so it detects your movement
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
//The name of the program
public class Snakekis {
    //The declaration of the scanner so taht it detects inputs
    static Scanner input = new Scanner(System.in);
    //This creates the random spawn of fruits 
    static Random random = new Random();
    //The game board or size, I did 20x20 so its more big
    static final int WIDTH = 20;
    static final int HEIGHT = 15;
    //The snakes who gradually grows when eating fruits
    static ArrayList<Position> snake;
    //The position of the fruit
    static Position food;
    //Each eaten fruits gives score
    static int score;
    //When you lose the game is over
    static boolean gameOver;

    // Position class
    //The inner class name
    static class Position {
        //Int is used to path find x and y
        // x is horizontal
        //y is vertical
        int x;
        int y;
//This is where you see where your current snake is
//W, S vertical and A, D for horizontal
        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // Main Method
    //This is where the game begins, because on the top is just the 
    //how the program detects and should function
    public static void main(String[] args) {
//This is so that the game keeps flashing as long as
//You havent lost yes, thats why its always true
        boolean running = true;
//basically a loop until defeated
        while (running) {
//This entire part right is is just the visuals, and the interaction
//menu, either play, instructions or leave
     System.out.println("\n================================");
     System.out.println("          SNAKEKIS");
        System.out.println("================================");
            System.out.println("1. Start Game");
            System.out.println("2. Instructions");
            System.out.println("3. Exit");
            System.out.println("================================");
            //this is where you put your choice from 1,2,3
            System.out.print("Enter your choice: ");
//This is the one who detecs it
            String choice = input.nextLine();
//Switch is who detecs on what to do, either play, instructions or leave
            switch (choice) {

                case "1":
                    startGame();
                    break;

                case "2":
                    showInstructions();
                    break;

                case "3":
                    System.out.println("\nThank you for playing Snakekis!");
                    running = false;
                    break;
//this is the breaker when a user put wrong number like 4,5 etc
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
//This closes the part of this perogram
        input.close();
    }

    // Game Instructions
    static void showInstructions() {
//This is just a visual guide on how to play it.
//It will only show up when you press number 2
        System.out.println("\n========== INSTRUCTIONS ==========");
        System.out.println("W - Move Up");
        System.out.println("S - Move Down");
        System.out.println("A - Move Left");
        System.out.println("D - Move Right");
        System.out.println("X - Quit Current Game");
        System.out.println("----------------------------------");
        System.out.println("Collect food (*) to earn points.");
        System.out.println("Each food gives you 10 points.");
        System.out.println("Avoid hitting the walls and yourself.");
        System.out.println("==================================");
//This is the option to return to menu and click other number
        System.out.println("\nPress ENTER to return to menu.");
        input.nextLine();
    }

    // Start Game
    //Where it begins
    static void startGame() {
//this will store the position of the next for every flash of the code
        snake = new ArrayList<>();
        //This gives the snake an outline, where its head are
        snake.add(new Position(WIDTH / 2, HEIGHT / 2));
        snake.add(new Position(WIDTH / 2 - 1, HEIGHT / 2));
        snake.add(new Position(WIDTH / 2 - 2, HEIGHT / 2));
//This give the score board
        score = 0;
        gameOver = false;
//this generate foods in random locations
        generateFood();

        while (!gameOver) {
//This is the score board
            displayBoard();
//Each point earned will update the score board
            System.out.println("Score: " + score);
            //This detects entered movement to follow
            System.out.print("Enter direction (W/A/S/D) or X to quit: ");
//So that even lower case or upper case it will still detec it
            String move = input.nextLine().toLowerCase();
//checks if whats entered where right
            if (move.equals("x")) {
                System.out.println("You left the game.");
                //another break for this part of the code
                break;
            }
//This are the valid movements
            if (!move.equals("w") &&
                !move.equals("a") &&
                !move.equals("s") &&
                !move.equals("d")) {
//if not true then it goes here
                System.out.println("Invalid movement!");
                //This staement is contiue so even if its invalid
                //It will still continue until defated
                continue;
            }
//updates the snake movements
            moveSnake(move);
        }
//This is when the game is over
        if (gameOver) {
            //This is the display borard after the game, and total score
            displayBoard();
      System.out.println("\n========== GAME OVER ==========");
           System.out.println("Final Score: " + score);
            System.out.println("===============================");
        }
//This is to play again
        System.out.println("\nPress ENTER to return to menu.");
        input.nextLine();
    }

    // Display Game Board

    // entire code is for showing the visual represenatation of
    //The boared, and the snake interface
    //also the wall
    static void displayBoard() {
//This one prints it
        System.out.println("\n");

        for (int y = 0; y < HEIGHT; y++) {

            for (int x = 0; x < WIDTH; x++) {

                if (x == 0 || x == WIDTH - 1 ||
                    y == 0 || y == HEIGHT - 1) {

                    System.out.print("# ");

                } else if (x == snake.get(0).x &&
                           y == snake.get(0).y) {

                    System.out.print("O ");

                } else if (x == food.x && y == food.y) {

                    System.out.print("* ");

                } else {

                    boolean body = false;

                    for (int i = 1; i < snake.size(); i++) {

                        if (x == snake.get(i).x &&
                            y == snake.get(i).y) {

                            System.out.print("o ");
                            body = true;
                            break;
                        }
                    }

                    if (!body) {
                        System.out.print("  ");
                    }
                }
            }

            System.out.println();
        }
    }

    // Move Snake
    static void moveSnake(String direction) {

        Position head = snake.get(0);

        int newX = head.x;
        int newY = head.y;

        switch (direction) {

            case "w":
                newY--;
                break;

            case "s":
                newY++;
                break;

            case "a":
                newX--;
                break;

            case "d":
                newX++;
                break;
        }

        // Check wall collision
        if (newX <= 0 || newX >= WIDTH - 1 ||
            newY <= 0 || newY >= HEIGHT - 1) {

            gameOver = true;
            return;
        }

        boolean eating = (newX == food.x && newY == food.y);

        // Check collision with snake body
        int checkLength = eating ? snake.size() : snake.size() - 1;

        for (int i = 0; i < checkLength; i++) {

            Position part = snake.get(i);

            if (newX == part.x && newY == part.y) {
                gameOver = true;
                return;
            }
        }

        // Add new head
        snake.add(0, new Position(newX, newY));

        // Check if food is collected
        if (eating) {

            score += 10;
            generateFood();

        } else {

            // Remove tail if no food is collected
            snake.remove(snake.size() - 1);
        }
    }

    // Generate Food
    static void generateFood() {

        boolean occupied;

        do {

            occupied = false;

            int foodX = random.nextInt(WIDTH - 2) + 1;
            int foodY = random.nextInt(HEIGHT - 2) + 1;

            food = new Position(foodX, foodY);

            for (Position part : snake) {

                if (part.x == food.x && part.y == food.y) {
                    occupied = true;
                    break;
                }
            }

        } while (occupied);
    }
}