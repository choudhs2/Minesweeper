import java.util.Scanner;
public class Minesweeper {
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);//REMEMBER THIS IS THE INITIALIZED SCANNER!!!
    System.out.println("How many rows/columns?");
    int inp = s.nextInt();//for all inputs, non-menu
    String[][][] layers = new String[2][inp][inp];//3D would be the extra cred part
    //Possible gui in the future
    System.out.println("Select the number next to the difficulty you want\n1. Easy\n2. Medium\n3. Hard\nOther inputs will 'Quit'");
    int menuInp = s.nextInt();//if 1, put 15% mines, if 2 => 20%, if 3 => 25%, else quit loop
    layers = setUpMinesweeper(inp, menuInp); //takes difficulty, makes mines, uses rows/cols to make the final result
    String str = "";
    for (int i = 0; i<layers[0].length; i++) {
      str="|";
      for (int j = 0; j<layers[0][0].length; j++) {
        str+=layers[1][i][j] + "|";
      }
      System.out.println(str);
    }
  }
  public static String[][][] setUpMinesweeper(int rowCol, int dif) {
    String[][][] res = new String[2][rowCol][rowCol];//final result
    int numMInes = (int) (Math.pow(rowCol, 2) * (.15 + ((((double) dif - 1)*5)/100))); // makes number of mines from percent in
    // one step, complicated, but it should work
    for (int i = 0; i < res[0].length; i++) {
      for (int j = 0; j < res[0][0].length; j++) {
        res[0][i][j] = " ";//shown layer, nothing but spaces at first
        res[1][i][j] = " ";// will be changed later, filled with numbers or mines
      }
    }
    int placedMines = 0;
    int row = 0;
    int col = 0;
    while (placedMines != numMInes) {//places mines randomly
      row = (int) Math.floor(Math.random()*rowCol);
      col= (int) Math.floor(Math.random()*rowCol);
      if (res[1][row][col].equals(" ")) {
        res[1][row][col] = "X";//mines placed
        placedMines+=1;
      }
    }
    return res;
  }
}
