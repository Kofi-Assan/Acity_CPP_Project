import java.util.Scanner;
import javax.swing.JOptionPane;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class Test{

    

    public static void main(String[] args){

        
    }

    //Group 1
    public class NameLock {
        public static String combinePasswords(String group3, String group5) {
            return group3 + group5;
        }

        public static String getPasswordInput() {
            return JOptionPane.showInputDialog(null,"Please enter the name password:");
        }

        public static void checkPassword(String inputPassword, String fullPassword) {
            if (inputPassword.equals(fullPassword)) {

                JOptionPane.showMessageDialog(null, "Access granted! The door opens.");
            } else {

                JOptionPane.showMessageDialog(null, "Access denied. Incorrect password.");
            }
        }
    }

public class the_quiz {
    public static String combinePasswords(String group3, String group5) {
        return group3 + group5;
    }

    public static String getPasswordInput() {
        return JOptionPane.showInputDialog(null,"Please enter the name password:");
    }

    public static void checkPassword(String inputPassword, String fullPassword) {
        if (inputPassword.equals(fullPassword)) {

            JOptionPane.showMessageDialog(null, "Access granted! The door opens.");
        } else {

            JOptionPane.showMessageDialog(null, "Access denied. Incorrect password.");
        }
    }
}


    public class NameCodeLock {
    public static String combinePasswords(String group3, String group5) {
        return group3 + group5;
    }

    public static String getPasswordInput() {
        return JOptionPane.showInputDialog(null,"Please enter the name password:");
    }

    public static void checkPassword(String inputPassword, String fullPassword) {
        if (inputPassword.equals(fullPassword)) {

            JOptionPane.showMessageDialog(null, "Access granted! The door opens.");
        } else {

            JOptionPane.showMessageDialog(null, "Access denied. Incorrect password.");
        }
    }
}

    //Group 2
    public class CaseSensitivePuzzle {

        public static void group2() {
        
            Scanner cin = new Scanner(System.in);
            Random random = new Random();
            

            // Assuming Group 1 provides the word, Group 4 provides correct capitalization
            String correctPassword = "PaSsWoRd"; 
            
            //  a lower bound word
            String lowerBoundWord = "password"; 
            
            int firstDigit = random.nextInt(51) * 2; // Random even number between 0 and 100
            int secondDigit = random.nextInt(51) * 2; // Random even number between 0 and 100
            String firstTwoDigits = String.valueOf(firstDigit) + String.valueOf(secondDigit);
            
            System.out.println("Enter the password:");
            String userInput = cin.nextLine();
            
            if (correctPassword.equalsIgnoreCase(userInput)) {
                System.out.println("You have made it you can proceed.");
                System.out.println("The lower bound word is '" + lowerBoundWord + "'.");
                System.out.println(" The first two digits of the escape code are '" + firstTwoDigits + "'.");
            } else {
                System.out.println("Incorrect password. Try again!");
            }
            
        cin.close();
        }
    }



    //Group 3
public class Groupc{
    
        String halfpassw ="agsja";
        String group2="";
        String group5="";

        List<String> numberofwords = Arrays.asList("one","ONE","TWO","two","three","THREE","FOUR","four","five","FIVE");
     
        if (!numberofwords.contains(group2)){ 

        
       JOptionPane.showMessageDialog(null,"Number is accepted");

        }

        else if (!numberofwords.contains(group5)){
            JOptionPane.showMessageDialog(null,"Number is accepted");


        }

        else
        {
            JOptionPane.showMessageDialog(null,"Number is invalid"); 

        }

        if(group2.compareTo(group5) > 0){
            System.out.println("High");

        }
        else 
        {
            System.out.println("Low");

              
        }
            

        if(group5.compareTo(group2) < 0){
            System.out.println("Low");

        }
        else 
        {
            System.out.println("High");

        }


            

        


        

    


}


    //Group 4



    //Group 5


    import java.util.Random;
    import javax.swing.JOptionPane; // Import JOptionPane

public class Escape101 {
    public static void main(String[]args) {
        String otherPassword = JOptionPane.showInputDialog("Enter the other password:");
        
        Random random = new Random();
        int First_2_Digits = random.nextInt(90) + 10; // Random number between 10 and 99
        int last_2_Digits = (random.nextInt(50) * 2); // Ensures even number
        int finalCode = (First_2_Digits * 100) + last_2_Digits;
        
        JOptionPane.showMessageDialog(null, "Group 2 gives the first two digits: " + First_2_Digits);
        JOptionPane.showMessageDialog(null, "Group 4 confirms the number is even or odd.");
        
        int guess;
        do {
            String input = JOptionPane.showInputDialog("Enter your guess for the 4-digit escape code:");
            guess = Integer.parseInt(input); // Convert input to integer
            
            if (guess < finalCode) {
                JOptionPane.showMessageDialog(null, "Too low! Try again.");
            } else if (guess > finalCode) {
                JOptionPane.showMessageDialog(null, "Too high! Try again.");
            }
        } while (guess != finalCode);
        
        JOptionPane.showMessageDialog(null, "You have unlocked the final escape code.");
    }
}




    //Group Challeng 1,2,3,4,5


    
}