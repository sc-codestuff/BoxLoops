import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class PrisonerBoxProblem {
    // array to represent the boxes to be searched by the participants
    static int[] boxes;
    //number of boxes/participants
    static int numOfBoxes = 100;
    //how many times to run the simulation
    static int numOfRuns = 100000;

    public static void main(String[] args) {
        //create a box with the desired number of numbers inside
        createBox();

        //counter to track number of successful attempts (all numbers found)
        int successCount = 0;
        //counter to track number of failed attempts (at least one number not found)
        int failureCount = 0;

        //loop to run the simulation desired number of times
        for (int i = 0; i < numOfRuns; i++) {
            //start loop by shuffling content of the box
            shuffleBox();

            //check to track progress where the number of runs is very high
            if ((i + 1) % 1000 == 0)
                System.out.println("Run number: " + (i + 1));

            //check if all participants find their number and increment counters accordingly
            if (checkIfAllFoundNumber())
                successCount++;
            else
                failureCount++;
        }

        //print total successes/failured
        System.out.println("Total successes: " + successCount);
        System.out.println("Total failures: " + failureCount);

        //calculate and print averages for each over the course of all the attempts
        System.out.println("Average rate of success: " +
                ((Double.valueOf(successCount) / Double.valueOf(numOfRuns)) * 100));
        System.out.println("Average rate of failure: " +
                ((Double.valueOf(failureCount) / Double.valueOf(numOfRuns)) * 100));
    }

    //check for any loop greater that half the number of boxes (which would indicate a failure to find all numbers)
    private static boolean checkIfAllFoundNumber() {

        //loop through all participants/number of boxes
        for (int i = 1; i <= numOfBoxes; i++) {

            //find loop for specific participant
            int n = findLoop(i);

            //break out of loop and report failure if single participant fails
            if (n > numOfBoxes / 2)
                return false;
        }

        //submit success where all participants have loops less than 50
        return true;
    }

    //find the size of a particular loop for a specific number
    private static int findLoop(int participant) {

        //counter to track size of loop
        int count = 0;

        //adjust start number to account for arryays starting at 0
        int startNumber = participant - 1;

        //loop while count equal to number of boxes
        while (count < numOfBoxes) {

            //start with the participants number
            int number = boxes[startNumber];

            //increment count
            count++;

            //check if number in box matches that of the participant
            if (number == participant)
                return count;

            //amend start number to reflect value in the box (adjusted for arrays starting at 0)
            startNumber = number - 1;
        }
        return 0;
    }

    //creates a box of supplied size
    private static void createBox() {
        boxes = new int[numOfBoxes];

        //simple loop from 1 to num of desired boxes
        for (int i = 1; i <= numOfBoxes; i++)
            //box values assigned for each element
            boxes[i - 1] = i;
    }

    //shuffle the contents of the boxes array
    static void shuffleBox() {
        Random rnd = ThreadLocalRandom.current();
        for (int i = boxes.length - 1; i > 0; i--) {

            //pick a random index location within bounds of the box
            int index = rnd.nextInt(i + 1);

            //assign value of box at random index to temporary variable
            int tmp = boxes[index];

            //assign the random index of box value to the current index in the loop
            boxes[index] = boxes[i];

            //assign the current index of box value to the temporary (randomly selected) value
            boxes[i] = tmp;
        }
    }
}
