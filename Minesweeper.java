import java.util.Scanner;
public class Minesweeper {
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);//REMEMBER THIS IS THE INITIALIZED SCANNER!!!
    System.out.println("How many rows/columns?");
    int inp = s.nextInt();//for all inputs, non-menu
    String[][][] layers = new String[2][inp][inp];//this is the extra cred part
    System.out.println("Select the number next to the difficulty you want\n1. Easy\n2. Medium\n3. Hard\nOther inputs will 'Quit'");
    int menuInp = s.nextInt();//if 1, put 15% mines, if 2, 20%, if 3, 25%, else quit loop
  }
}
