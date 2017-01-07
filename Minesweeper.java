import java.util.Scanner;

public class Minesweeper {
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);//REMEMBER THIS IS THE INITIALIZED SCANNER!!!
    System.out.println("How many rows/columns? (enter one number, it is a square grid)");
    int inp = s.nextInt();//for all inputs, non-menu
    MSGrid layers = new MSGrid(inp);//3D would be the extra cred part, if done
    //Possible gui in the future?
    String mI = "";//to avoid errors with non-int inputs
    boolean b = false;//use for completion cheeck
    layers.runMSGame(b, mI);
  }
} 