import java.util.Scanner;

public class Minesweeper {
  
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);//REMEMBER THIS IS THE INITIALIZED SCANNER!!!
    System.out.println("How many rows/columns? (enter one number, it is a square grid)");
    int inp = s.nextInt();//for all inputs, non-menu
    String[][][] layers = new String[2][inp][inp];//3D would be the extra cred part
    //Possible gui in the future
    System.out.println("Select the number next to the difficulty you want\n1. Easy\n2. Medium\n3. Hard\nOther inputs will 'Quit'");
    int m = s.nextInt();//if 1, put 15% mines, if 2 => 20%, if 3 => 25%, else quit
    if (m>0 && m<4) {
      layers = setUpMinesweeperMines(inp, m); //takes difficulty, makes mines, uses rows/cols to make the final result
      layers = setUpNumbers(layers);//takes the minefilled thing and gets the numbers for the empty spaces
      boolean check = true; //while this is true, it will run, so we can change this instead of using break (simplicity reasons)
      String menuInput = "";//to avoid errors with non-int inputs
      int[] coords = new int[2];//will store the coordinates
      while (check) {
        printMinefield(layers);
        System.out.println("What will you do?\n1. Flag\n2. Unflag\n3. Choose a place to step\nAnything else will quit the game");
        menuInput = s.next();//main menu that asks if it should flag/unflag, click, or quit game
        if (menuInput.equals("1")) {
          coords = inpCoords(s);//sends scnner in, gets 2 coords out
          if (!(layers[0][coords[0]][coords[1]].equals("SHOW"))) {//if it isnt shown
            layers[0][coords[0]][coords[1]] = "F";//flagged
          }
        }
        else if (menuInput.equals("2")) {
          coords = inpCoords(s);//sends scnner in, gets 2 coords out
          if (!(layers[0][coords[0]][coords[1]].equals("SHOW"))) {//if it isnt shown
            layers[0][coords[0]][coords[1]] = " ";//unflagged
          }
        }
        else if (menuInput.equals("3")) {
          coords = inpCoords(s);//sends scnner in, gets 2 coords out
          if (layers[1][coords[0]][coords[1]].equals("X")) {
            loss(layers);
            check=false;
          }
          else if (!(layers[0][coords[0]][coords[1]].equals("SHOW")) && !(layers[1][coords[0]][coords[1]].equals("0"))) {
            layers[0][coords[0]][coords[1]] = "SHOW";
          }
          else if (!(layers[0][coords[0]][coords[1]].equals("SHOW"))) {
            layers = showZeroes(layers, coords);
          } 
        }
        else {
          System.out.println("you quit");
          loss(layers);
          check = false;
        }
      }
    }
  }
  
  public static String[][][] showZeroes(String[][][] l, int[] c) {//does not work yet     DOES NOT WORK YET
    if (c[0] > 0 && c[0] < l[1].length && c[1] > 0 && c[1] < l[1][0].length) {
      l[0][c[0]][c[1]] = "SHOW";
      for (int i = c[0]-1; i < c[0]+2; i++) {
        for (int j = c[1]-1; j < c[1]+2; j++) {
          int[] a = {i, j};
          if (l[1][i][j].equals("0") && i!=c[0] && j!=c[1]) {
            l = showZeroes(l, a);
          }
        }
      }
    }
  return l;
  }
  
  public static String[][][] setUpNumbers(String[][][] l) {//make more efficient
    for (int i = 1; i < l[1].length-1; i++) {//mid
      for (int z = 1; z < l[1][1].length-1; z++) {
        if (!(l[1][i][z].equals("X"))) {
          int count = 0;
          for (int j = i-1; j < i+2; j++) {
            for (int k = z-1; k <z+2; k++) {
              if (!(k==z && j==i) && (l[1][j][k].equals("X"))) {
                count += 1;
              }
            }
          }
          l[1][i][z] = "" + count;
        }
      }
    }
    for (int z = 1; z < l[1][0].length-1; z++) {//top
      if (!(l[1][0][z].equals("X"))) {
        int count = 0;
        for (int j = 0; j < 2; j++) {
          for (int k = z-1; k <z+2; k++) {
            if (!(k==z && j==0) && (l[1][j][k].equals("X"))) {
              count += 1;
            }
          }
        }
        l[1][0][z] = "" + count;
      }
    }
    for (int z = 1; z < l[1][0].length-1; z++) {//bottom
      if (!(l[1][(l[1][0].length-1)][z].equals("X"))) {
        int count = 0;
        for (int j = l[1][0].length-2; j < l[1][0].length; j++) {
          for (int k = z-1; k <z+2; k++) {
            if (!(k==z && j==l[1][0].length-1) && (l[1][j][k].equals("X"))) {
              count += 1;
            }
          }
        }
        l[1][l[1][0].length-1][z] = "" + count;
      }
    }
    for (int z = 1; z < l[1].length-1; z++) {//left
      if (!(l[1][z][0].equals("X"))) {
        int count = 0;
        for (int j = 0; j < 2; j++) {
          for (int k = z-1; k <z+2; k++) {
            if (!(k==0 && j==z) && (l[1][k][j].equals("X"))) {
              count += 1;
            }
          }
        }
        l[1][z][0] = "" + count;
      }
    }
    for (int z = 1; z < l[1].length-1; z++) {//left
      if (!(l[1][z][l[1].length-1].equals("X"))) {
        int count = 0;
        for (int j = l[1].length-2; j < l[1].length; j++) {
          for (int k = z-1; k <z+2; k++) {
            if (!(k==l[1].length-1 && j==z) && (l[1][k][j].equals("X"))) {
              count += 1;
            }
          }
        }
        l[1][z][l[1].length-1] = "" + count;
      }
    }
    //corners now MAKE THIS METHOD MUCH MORE EFFICIENT i hate my life its 3 am
    if (!(l[1][0][0].equals("X"))) {
      int count = 0;
      if (l[1][0][1].equals("X")) {count+=1;} if (l[1][1][0].equals("X")) {count+=1;} if (l[1][1][1].equals("X")) {count +=1;}
      l[1][0][0] = "" +count;
    }
    if (!(l[1][0][l[1][0].length-1].equals("X"))) {
      int count = 0;
      if (l[1][0][l[1][0].length-2].equals("X")) {count+=1;} if (l[1][1][l[1][0].length-1].equals("X")) {count+=1;} if (l[1][1][l[1][0].length-2].equals("X")) {count +=1;}
      l[1][0][l[1][0].length-1] = "" +count;
    }
    if (!(l[1][l[1].length-1][0].equals("X"))) {
      int count = 0;
      if (l[1][l[1].length-1][1].equals("X")) {count+=1;} if (l[1][l[1].length-2][0].equals("X")) {count+=1;} if (l[1][l[1].length-2][1].equals("X")) {count +=1;}
      l[1][l[1].length-1][0] = "" +count;
    }
    if (!(l[1][l[1].length-1][l[1][0].length-1].equals("X"))) {
      int count = 0;
      if (l[1][l[1].length-1][l[1][0].length-2].equals("X")) {count+=1;} if (l[1][l[1].length-2][l[1][0].length-1].equals("X")) {count+=1;} if (l[1][l[1].length-2][l[1][0].length-2].equals("X")) {count +=1;}
      l[1][l[1].length-1][l[1][0].length-1] = "" +count;
    }
    return l;//i hate everything
  }
  
  public static int[] inpCoords(Scanner s) {
    System.out.println("Enter row number (0 to number of rows/columns you chose-1) then the column (same rule)");
    int[] n = {s.nextInt(), s.nextInt()};
    return n;
  }
  
  public static String[][][] setUpMinesweeperMines(int rowCol, int dif) {
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
  
  public static void printMinefield(String[][][] l) {
    String s = "";
    for (int i = 0; i<l[0].length; i++) {
      s ="|";
      for (int j = 0; j<l[0][0].length; j++) {
        if (l[0][i][j].equals("SHOW")) {//if value is show, it shows the bottom
          s+=l[1][i][j] + "|";
        }
        else { //otherwise show top
          s+=l[0][i][j] + "|";
        }
      }
      System.out.println(s);
    }
  }
  
  public static void loss(String[][][] l) {
    for (int i = 0; i < l[0].length; i++) {
      for (int j = 0; j<l[0][i].length; j++) {
        l[0][i][j] = "SHOW";
      }
    }
    printMinefield(l);
  }
}
