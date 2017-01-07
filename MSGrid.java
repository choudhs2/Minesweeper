import java.util.Scanner;
public class MSGrid {
  private String[][][] l;
  private Scanner s = new Scanner(System.in);
  private int[] coords =  new int[2];
  
  public MSGrid(int k) {
    l = new String[2][k+2][k+2];
    setup();
  }
  
  public void runMSGame(boolean b, String menuInput) {
    printMinefield();
    b = completionCheck();
    if (b) {
      System.out.println("YOU WIN!!!");
    }
    else {
      System.out.println("What will you do?\n1. Flag\n2. Unflag\n3. Choose a place to step");
      menuInput = s.next();//main menu that asks if it should flag/unflag, click, or quit game
      if (menuInput.equals("1")) {
        coords = inpCoords();//gets 2 coords
        if (!(l[0][coords[0]][coords[1]].equals("SHOW"))) {//if it isnt shown
          l[0][coords[0]][coords[1]] = "F";//flagged
        }
        runMSGame(b, menuInput);
      }
      else if (menuInput.equals("2")) {
        coords = inpCoords();//gets 2 coords
        if (!(l[0][coords[0]][coords[1]].equals("SHOW"))) {//if it isnt shown
          l[0][coords[0]][coords[1]] = " ";//unflagged
        }
        runMSGame(b, menuInput);
      }
      else if (menuInput.equals("3")) {
        coords = inpCoords();//gets 2 coords
        if (l[1][coords[0]][coords[1]].equals("X")) {
          System.out.println("You lose :(");
          loss();
        }
        else if (!(l[0][coords[0]][coords[1]].equals("SHOW")) && !(l[1][coords[0]][coords[1]].equals("0"))) {
          l[0][coords[0]][coords[1]] = "SHOW";
          runMSGame(b, menuInput);
        }
        else if (!(l[0][coords[0]][coords[1]].equals("SHOW"))) {
          showZeroes(coords);
          runMSGame(b, menuInput);
        }
      }
      else {
        runMSGame(b, menuInput);
      }
    }
  }
  
  public void setup() {
    System.out.println("Select the number next to the difficulty you want\n1. Easy\n2. Medium\n3. Hard\n");
    int m = s.nextInt();//if 1, put 15% mines, if 2 => 20%, if 3 => 25%, else quit
    if (m>0 && m<4) {
      setUpMinesweeperAndMines(m); //takes difficulty, makes mines, uses rows/cols to make the final result
      setUpNumbers();//takes the minefilled thing and gets the numbers for the empty spaces
    }
    else {
      setup();
    }
  }
  
  public void showZeroes(int[] c) {
    l[0][c[0]][c[1]] = "SHOW";
    if (c[0] > 0 && c[0] < l[0].length-1 && c[1] > 0 && c[1] <l[0][0].length-1 && l[1][c[0]][c[1]].equals("0")) {
      int[] a = {c[0]-1, c[1]};
      if (!(l[0][a[0]][a[1]].equals("SHOW"))) {
        showZeroes(a);
      }
      int[] b = {c[0], c[1]-1};
      if (!(l[0][b[0]][b[1]].equals("SHOW"))) {
        showZeroes(b);
      }
      int[] d = {c[0]+1, c[1]};
      if (!(l[0][d[0]][d[1]].equals("SHOW"))) {
        showZeroes(d);
      }
      int[] e = {c[0], c[1]+1};
      if (!(l[0][e[0]][e[1]].equals("SHOW"))) {
        showZeroes(e);
      }
      int[] f = {c[0]-1, c[1]-1};
      if (!(l[0][f[0]][f[1]].equals("SHOW"))) {
        showZeroes(f);
      }
      int[] g = {c[0]+1, c[1]-1};
      if (!(l[0][g[0]][g[1]].equals("SHOW"))) {
        showZeroes(g);
      }
      int[] h = {c[0]+1, c[1]+1};
      if (!(l[0][h[0]][h[1]].equals("SHOW"))) {
        showZeroes(h);
      }
      int[] i = {c[0]-1, c[1]+1};
      if (!(l[0][i[0]][i[1]].equals("SHOW"))) {
        showZeroes(i);
      }
    }
  }
  
  public boolean completionCheck() {
    for (int i = 1; i <l[0].length-1; i++) {
      for (int j = 1; j <l[0][0].length-1; j++) {
        if (!((l[1][i][j].equals("X") && l[0][i][j].equals("F")) || l[0][i][j].equals("SHOW"))) {
          return false;
        }
      }
    }
    return true;
  }
  
  public void setUpNumbers() {
    for (int i = 1; i < l[1].length-1; i++) {
      for (int z = 1; z < l[1][1].length-1; z++) {
        if (!(l[1][i][z].equals("X"))) {
          int count = 0;
          for (int j = i-1; j < i+2; j++) { //checks rows above, below, and on the same row
            for (int k = z-1; k <z+2; k++) { //checks the columns next to it and the same col
              if (!(k==z && j==i) && (l[1][j][k].equals("X"))) { 
                count += 1;
              }
            }
          }
          l[1][i][z] = "" + count; //where we put the numbers
        }
      }
    }
  }
  
  public int[] inpCoords() {
    System.out.println("Enter row number (1 to number of rows/columns you chose) then the column in the second box (same rule)");
    int[] n = {s.nextInt(), s.nextInt()};
    if (n[0] < 1 || n[0] > l[0].length-2 || n[1] < 1 || n[1] > l[0].length-2) {
      n = inpCoords();
    }
    return n;
  }
  
  public void setUpMinesweeperAndMines(int dif) {
    int numMInes = (int) (Math.pow(l[0].length-2, 2) * (.15 + ((((double) dif - 1)*5)/100))); // makes number of mines from percent in
    // one step, complicated, but it should work
    for (int i = 0; i < l[0].length; i++) {
      for (int j = 0; j < l[0][0].length; j++) {
        l[0][i][j] = " ";//shown layer, nothing but spaces at first
        l[1][i][j] = " ";// will be changed later, filled with numbers or mines
      }
    }
    int placedMines = 0;
    int row = 0;
    int col = 0;
    while (placedMines != numMInes) {//places mines randomly
      row = (int) Math.floor(Math.random()*l[1].length-1) + 1;
      col= (int) Math.floor(Math.random()*l[1].length-1) + 1;
      if (row > 0 && row < l[0].length-1 && col > 0 && col < l[0][0].length-1 && l[1][row][col].equals(" ")) {
        l[1][row][col] = "X";//mines placed
        placedMines+=1;
      }
    }
  }
  
  public void printMinefield() {
    String x = "    ";
    for (int i = 1; i<l[0][0].length-1; i++) {
      if (i < 11) {x += " " + i;}
      else {x += "" + i;}
    }
    System.out.println(x);
    for (int i = 1; i<l[0].length-1; i++) {
      x ="|";
      for (int j = 1; j<l[0][0].length-1; j++) {
        if (l[0][i][j].equals("SHOW")) {//if value is show, it shows the bottom
          x+=l[1][i][j] + "|";
        }
        else { //otherwise show top
          x+=l[0][i][j] + "|";
        }
      }
      if (i < 10) {System.out.println("" + i + ":  " + x);}
      else {System.out.println("" + i + ": " + x);}
    }
  }
  
  public void loss() {
    for (int i = 1; i < l[0].length-1; i++) {
      for (int j = 1; j<l[0][i].length-1; j++) {
        l[0][i][j] = "SHOW";
      }
    }
    printMinefield();
  }
}