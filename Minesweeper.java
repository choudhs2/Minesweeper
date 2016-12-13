import java.util.Scanner;
public class Minesweeper {
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);//REMEMBER THIS IS THE INITIALIZED SCANNER!!!
    System.out.println("How many rows/columns?");
    int n = s.nextInt();
    String[][][] layers = new String[2][n][n];//this is the extra cred part
  }
}
