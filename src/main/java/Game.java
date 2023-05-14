package main.java;
/*
 * Conway's Game of Life
 * by Luka Scott started on 27/04/2023
 * https://www.github.com/SkySourced/GameOfLife
 */

import java.util.Scanner;

public class Game {
    // Constants
    public static final int WIDTH = 10; // Width of the grid
    public static final int HEIGHT = 10; // Height of the grid
    // Public variables
    public static Cell[][] cells; // 2D array of cells
    // Private variables
    //private int rate; // Rate of updates per second
    // Library implementations
    public static Scanner kb; // Keyboard input

    // Initialises the grid
    public void gridInit() {
        cells = new Cell[HEIGHT][WIDTH]; // Initialise the 2D array
        for (int i = 0; i < HEIGHT; i++) { // For each row
            for (int j = 0; j < WIDTH; j++) { // For each column
                cells[i][j] = new Cell(i, j, this); // Create a new cell
            }
        }
        newTurn(); // Start first turn
    }
    private static void newTurn() { // Effectively the main loop
        render(); // Render the grid
        System.out.println("Enter the x coordinate of a cell you would like to toggle, or enter 'next <no. of turns>' to evaluate turns.");
        String[] input = kb.nextLine().split(" "); // Get the user's input
        int x = 0;
        int y = 0;
        int turns;

        if(input[0].equalsIgnoreCase("next")) {
            try {
                turns = Integer.parseInt(input[1]); // Get the number of turns
            } catch (ArrayIndexOutOfBoundsException oob) { // No specified turn number, run one turn
                turns = 1;
            }
            runTurns(turns); // Run the turns
        } else {
            try { // Try to parse the input as an integer
                x = Integer.parseInt(input[0]);
                if (x > WIDTH || x < 1) {
                    System.out.println("Coordinate must be within 1-" + WIDTH);
                    newTurn(); // Check if the input is within the grid
                }
            } catch (NumberFormatException notInt) { // If it fails, check if the input is "next"
                System.out.println("Invalid input. Please enter a number or 'next'.");
                newTurn(); // Start a new turn
            }
            System.out.println("Now enter the y coordinate of the cell to toggle.");
            input = kb.nextLine().split(" "); // Get the user's input
            try {
                y = Integer.parseInt(input[0]);
                if (y > HEIGHT || y < 1) {
                    System.out.println("Coordinate must be within 1-" + HEIGHT);
                    newTurn(); // Check if the input is within the grid
                }
            } catch (NumberFormatException notInt) { // If it fails, check if the input is "next"
                System.out.println("Invalid input. Please enter a number or 'next'.");
                newTurn(); // Start a new turn
            }
            x--; // Convert the coordinates to array indices
            y--;
            cells[y][x].state = !cells[y][x].state; // Toggle the cell's state
            newTurn(); // Start a new turn
        }
    }
    /*private static void newTurn() { // Effectively the main loop
        System.out.println("Enter the x coordinate of a cell you would like to toggle, or enter 'next <no. of turns>' to evaluate turns.");
        String input = kb.nextLine(); // Get the user's input
        int x = 0;
        int y;
        int turns;
        try { // Try to parse the input as an integer
            x = Integer.parseInt(input);
            if(x > WIDTH || x < 1) System.out.println("Coordinate must be within 1-"+WIDTH);
        } catch (NumberFormatException notInt) { // If it fails, check if the input is "next"
            String word = input.split(" ")[0]; // Split the input into words
            if (word.equalsIgnoreCase("next")) {
                try {
                    turns = Integer.parseInt(input.split(" ")[1]); // Get the number of turns
                } catch (ArrayIndexOutOfBoundsException oob) { // No specified turn number, run one turn
                    runTurns(1);
                    throw oob;
                }
                runTurns(turns); // Run the turns
            } else { // If it's not "next", then the input is invalid
                System.out.println("Invalid input. Please enter a number or 'next'.");
                newTurn(); // Start a new turn
            }
        }
        System.out.println("Now enter the y coordinate of the cell to toggle.");
        y = kb.nextInt(); // Get the user's input
        if (y > HEIGHT || y < 1) System.out.println("Coordinate must be within 1-"+HEIGHT);
        x--;
        y--;
        cells[y][x].state = !cells[y][x].state; // Toggle the cell's state
        newTurn(); // Start a new turn
    }*/
    public static void runTurns(int turns){
        for(int i = 0; i < turns; i++){
            update();
            if(i+1 < turns) {
                render();
                System.out.println("----------"); // buffer between multiple turns
            }
        }
        newTurn();
    }
    // Update every cell
    public static void update() {
        for (Cell[] row : cells) { // For each row
            for (Cell cell : row) { // For each column
                cell.update(); // Update the cell
            }
        }
    }
    // Render the grid
    public static void render() {
        for (Cell[] row : cells) { // For each row
            for (Cell cell : row) { // For each column
                System.out.print(cell.render()); // Print the rendered cell
            }
            System.out.println(); // Print a new line after each row is complete
        }
    }
}