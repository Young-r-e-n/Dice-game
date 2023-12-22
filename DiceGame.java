import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class DiceGame {
    private int[][] scoreTable; // 2D array for score table
    private int currentPlayer;  // 0 for Player 1, 1 for Player 2
    private ArrayList<Integer> setAsideDiceList; // List to store set-aside dice
    private int consecutiveSkips; // count of consecutive
     boolean[][] categorySelected = new boolean[2][7]; // 2 players, 7 categories
    public DiceGame() {
        // Initialize the score table
        scoreTable = new int[8][2]; // 8 rows for categories, 2 columns for players
        currentPlayer = 0; // Start with Player 1
        setAsideDiceList = new ArrayList<>();

        // Other initialization if needed
        
    }

    public void playGame() {
        // Main game loop
        displayScoreTable();
        
        for (int turn = 0; turn < 7; turn++) {
            System.out.println("Turn " + (turn + 1));
            
            for (int playerTurn = 0; playerTurn < 2; playerTurn++) {
                playTurn(turn);
                switchPlayer();
            }
        }
        
        declareWinner();
        // Display the winner and final scores
    }
    
    private void playTurn(int turn) {
        // Add this line to inform the user about the current turn
    System.out.println("Turn " + (turn + 1));
        System.out.println("Player " + (currentPlayer + 1) + "'s turn.");
        Scanner scanner = new Scanner(System.in);
    
        System.out.println("Do you want to forfeit(f) the turn or play (p)?.");
        String playOrForfeit = scanner.nextLine();
    
        if (playOrForfeit.equals("f")) {
            forfeitTurn();
        } else if (playOrForfeit.equals("p"))
        
        {
            int numDiceToThrow = 5; // Initially, throw 5 dice
       // Initialize selectedNumber, isNumberSet, and setAsideTotal
        int selectedNumber = 0;
        boolean isNumberSet = false;
        int setAsideTotal = 0;
        int sequenceCount = 0;
        int cumulativeSetAsideDice = 0; // Cumulative count of set aside dice
            // Logic for throwing dice, setting aside dice, deferring, category selection, scoring
            for (int throwCount = 1; throwCount <= 3; throwCount++) {
                System.out.println("Throw " + throwCount + ":");
    

                System.out.println("Do you want to throw the dice (t) or defer the throw (d)?");
                String throwOrDefer;
                boolean validInput = false;

                // Keep asking for input until a valid input is provided
                while (!validInput) {
                    throwOrDefer = scanner.nextLine();
            
                    if (throwOrDefer.equals("t") || throwOrDefer.equals("d")) {
                        validInput = true;
                        if (throwOrDefer.equals("d")) {
                            continue;
                        }
                    } else {
                        System.out.println("Invalid input. Please enter 't' to throw or 'd' to defer.");
                    }
                }
                // Logic for throwing dice
                int[] diceValues = throwDice(5 - cumulativeSetAsideDice);
    
                // Get selected number from the user only if it hasn't been set before
                do {
                    if (!isNumberSet || throwCount == 4) {
                        selectedNumber = selectDiceNumber();
                        if (selectedNumber != 0 && !categorySelected[currentPlayer][selectedNumber - 1]) {
                            isNumberSet = true;
                            categorySelected[currentPlayer][selectedNumber - 1] = true; // Mark the category as selected
        
                            if (selectedNumber == 7) {
                                // Sort the diceValues array
                                Arrays.sort(diceValues);
        
                                // Check if the diceValues array forms a sequence
                                if (Arrays.equals(diceValues, new int[]{1, 2, 3, 4, 5}) || Arrays.equals(diceValues, new int[]{2, 3, 4, 5, 6})) {
                                    scoreTable[6][currentPlayer]++; // Increment the sequence score
                                } else {
                                    System.out.println("Error: The dice do not form a sequence. Please select a different number.");
                                    selectedNumber = 0;
                                    isNumberSet = false;
                                }
                            }
                        } else if (selectedNumber != 0 && categorySelected[currentPlayer][selectedNumber - 1]) {
                            System.out.println("Error: You have already selected this category. Please select a different number.");
                            selectedNumber = 0;
                            isNumberSet = false;
                        }
                    }
                } while (!isNumberSet && throwCount == 3);
                // Use selected number to set aside dice
                setAsideDiceList.addAll(setAsideDice(diceValues, selectedNumber));
    
                // Logic for scoring
                int lengthOfSetAsideDice = setAsideDiceList.size();
                int sumOfSetAsideDice = sumOfList(setAsideDiceList);
                setAsideTotal += lengthOfSetAsideDice;
                cumulativeSetAsideDice += lengthOfSetAsideDice;

    
                System.out.println("Length of set aside dice list: " + lengthOfSetAsideDice);
                System.out.println("Sum of set aside dice: " + sumOfSetAsideDice);
    
                // Add your scoring logic here
    
                // Update the score table (replace this with your actual logic)
                //scoreTable[0][currentPlayer] = sumOfSetAsideDice;
    


                // Calculate the number of dice to throw in the next turn
                numDiceToThrow = (throwCount < 3) ? 5 - lengthOfSetAsideDice : 0;

                // Clear set aside dice list for the next throw
                setAsideDiceList.clear();
    
            }
    
              // Update the score table for the corresponding category
        if (selectedNumber >= 1 && selectedNumber <= 6) {
            scoreTable[selectedNumber - 1][currentPlayer] = setAsideTotal;
        } else if (selectedNumber == 7) {
            // Add your scoring logic for the "Sequence" category here
        }
            displayScoreTable();
        }
        else{
            System.out.println("Please enter the correct input either p or f");
            playTurn(turn);
        }
    }
    
    private int[] throwDice(int numDice) {
        int[] diceValues = new int[numDice];
        Random rand = new Random();
    
        System.out.println("Rolling dice...");
    
        for (int i = 0; i < numDice; i++) {
            diceValues[i] = rand.nextInt(6) + 1; // Random number between 1 and 6 for each die
        }
    
        System.out.println("Dice values: " + Arrays.toString(diceValues));
    
        return diceValues;
    }

    private void processTurn(int[] diceValues) {
        // Logic for setting aside dice, deferring, category selection, scoring
        int selectedNumber = 0;  // Initialize selectedNumber outside the loop
        boolean isNumberSet = false; 
        for (int throwCount = 1; throwCount <= 3; throwCount++) {
            System.out.println("Throw " + throwCount + ":");
        
            // Get selected number from the user only if it hasn't been set before
            if (!isNumberSet) {
                selectedNumber = selectDiceNumber();
                isNumberSet = true; 
            }
        
            // Use selected number to set aside dice
            setAsideDiceList.addAll(setAsideDice(diceValues, selectedNumber));
        
            // Logic for scoring
            int lengthOfSetAsideDice = setAsideDiceList.size();
            int sumOfSetAsideDice = sumOfList(setAsideDiceList);
        
            System.out.println("Length of set aside dice list: " + lengthOfSetAsideDice);
            System.out.println("Sum of set aside dice: " + sumOfSetAsideDice);
        
            // Add your scoring logic here
        
            // Update the score table (replace this with your actual logic)
            scoreTable[0][currentPlayer] = sumOfSetAsideDice;
        
            // Clear set aside dice list for the next throw
            setAsideDiceList.clear();
        
            // Calculate the number of dice to throw in the next turn
            int numDiceToThrow = (throwCount < 3) ? 5 - lengthOfSetAsideDice : 0;
        
            // Throw dice with the calculated number
            diceValues = throwDice(numDiceToThrow);
        }
        
    }
    
    
   
    private int selectDiceNumber() {
        Scanner scanner = new Scanner(System.in);
        int selectedNumber = -1; // Initialize to an invalid value
    
        while (selectedNumber < 0 || selectedNumber > 7) {
            System.out.println("Select a category (1)ones (2)twos (3)threes (4)fours (5)fives (6)sixes (7)sequence or (0)defer selection ");
            
            // Check if the input is an integer
            if (scanner.hasNextInt()) {
                selectedNumber = scanner.nextInt();
    
                if (selectedNumber < 0 || selectedNumber > 7) {
                    System.out.println("Invalid category. Please enter a number between 0 and 7.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Consume the invalid input to avoid an infinite loop
            }
        }
    
        return selectedNumber;
    }
    
    
    private ArrayList<Integer> setAsideDice(int[] diceValues, int selectedNumber) {
        ArrayList<Integer> setAsideDiceList = new ArrayList<>();
    
        for (int die : diceValues) {
            if (die == selectedNumber) {
                setAsideDiceList.add(selectedNumber);
            }
        }
    
        System.out.println("Dice set aside: " + setAsideDiceList);
        return setAsideDiceList;
    }
    private int sumOfList(ArrayList<Integer> list) {
        int sum = 0;
        for (int value : list) {
            sum += value;
        }
        return sum;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer + 1) % 2;
    }

    private void forfeitTurn() {
        System.out.println("Player " + (currentPlayer + 1) + " forfeited the turn.");
        displayScoreTable();
    }

    private void displayScoreTable() {
        // Define the categories
        String[] categories = {"Ones", "Twos", "Threes", "Fours", "Fives", "Sixes", "Sequence"};
    
        // Print the header
        System.out.printf("%10s %10s %10s\n", "Category", "Player 1", "Player 2");
    
        // Calculate the total score for each player
        int[] player1Total = new int[categories.length];
        int[] player2Total = new int[categories.length];
        for (int i = 0; i < categories.length; i++) {
            player1Total[i] = scoreTable[i][0] * (i + 1);
            player2Total[i] = scoreTable[i][1] * (i + 1);
        }
        player1Total[6] = scoreTable[6][0] * 20; // For "Sequence" category, multiply by 20
        player2Total[6] = scoreTable[6][1] * 20; // For "Sequence" category, multiply by 20
    
        // Calculate the grand total for each player
        int player1GrandTotal = Arrays.stream(player1Total).sum();
        int player2GrandTotal = Arrays.stream(player2Total).sum();
    
        // Print the scores
        for (int i = 0; i < categories.length; i++) {
            System.out.printf("%10s %10d %10d\n", categories[i], scoreTable[i][0], scoreTable[i][1]);
        }
    
        // Print the grand total
        System.out.printf("%10s %10d %10d\n", "Total", player1GrandTotal, player2GrandTotal);
    }
    
    private void declareWinner() {
        // Calculate total scores for both players
        int player1Total = Arrays.stream(scoreTable).mapToInt(row -> row[0]).sum();
        int player2Total = Arrays.stream(scoreTable).mapToInt(row -> row[1]).sum();
    
        // Declare the winner
        if (player1Total > player2Total) {
            System.out.println("Player 1 is the winner!");
        } else if (player1Total < player2Total) {
            System.out.println("Player 2 is the winner!");
        } else {
            System.out.println("It's a tie!");
        }
    }
    
    
    public static void main(String[] args) {
        DiceGame diceGame = new DiceGame();
        diceGame.playGame();
    }
}
