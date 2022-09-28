import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
public class Cube {
  //static keyword indicates the variable is defined throughout the namespace of the class Cube
  static Map<Integer, Face> cube_object = new HashMap<Integer, Face>(); //cube hashmap of char :: Face
  static Face pivotFace = cube_object.get('r');
  static Map<Integer, Character> colors_list = new HashMap<>();
  static ArrayList<Character> colorOrder = new ArrayList<Character>(List.of('r','b','o','g','y','w'));
	public static void main(String[] args){
    //must take in arguments
    for (char color: colorOrder){
      colors_list.put(colorOrder.indexOf(color) + 1, color);
    }
    for(int i = 1; i < 7; i++){
      cube_object.put(i, new Face(colorOrder.get(i-1)));
    }
    //for (Map.Entry<Character, Face> entry: Cube.cube_object.entrySet()){entry.getValue().printFace();}
    System.out.println(colors_list);
    cube_object.get(1).transform("u");
    printCube(cube_object);
  }

  public static void printCube(Map<Integer, Face> cube){
    for(Face value: cube.values()){
      value.printFace();
      System.out.println();
    }
  }
  
}

class Face {
  private char padding = '|';
  private String[][] face_matrix = new String[3][5]; 
  private char facecolor;
  /*This class implementation works because every created Face object starts with a blank
   * face_matrix that is then set on instantiation
   */
  Face(char color){
    this.facecolor = color;
    for(int x = 0; x < 3; x++){ //Columns
      for (int y = 0; y < 3; y++){ //Rows
        face_matrix[x][y] = Character.toString(color);
      }
    }
  
  }

  //public char getFaceColor(){return this.facecolor;}

  public void printFace(){
    String[][] target_data = this.face_matrix; 
    String blank = "";
    for(int row = 0; row < 3; row++){
      if(row > 0){blank += '\n';}
      for(int column = 0; column < 3; column++){
        blank += target_data[row][column];
        if(column == 0 || column == 1){blank += '|';}
      }
    }
    System.out.println(blank);
    
  }

  public void transform(String move){
    String[] validMoves = {"u", "d", "r", "l", "f", "b"}; 
    boolean move_is_valid = false;
    for(String testMove: validMoves){
      if(move == testMove || move == (testMove + "'")){
        move_is_valid = true;
        break;
      }else{move_is_valid = false;}
    }
    if (!move_is_valid){
      System.out.println("That isn't a valid move.");
    }else{
      switch (move){
        case "u", "u'": {
          if(move == "u"){
            this.modifyRow(0, 'g');
            for (int i = 2; i < 5; i++){ //starts at 4 as yellow and white are not modified in this operation
              Cube.cube_object.get(i).modifyRow(0, Cube.colors_list.get(i-1));;
            } 
          }else{

          }
          
        } //(top row) u: left, u': right
        case "d", "d'": {} //(bottom row) d: right, d': left
        case "r", "r'": {} //(right column) r: up, r': down
        case "l", "l'": {} //(left column) l: down, l': up
        case "f", "f'": {} //(front) f: right turn, f': left turn
        case "b", "b'": {} //(back) b: right turn, b': left turn

      }

    }
  }

  public void modifyFace(int column, int row, char newColor){
    if (row < 3 && column < 3){
      this.face_matrix[column][row] = Character.toString(newColor);
    }else{System.out.println("Invalid row and / or column.");}
  }
  public void modifyRow(int row, char newColor){
    for (int column = 0; column < 3; column ++){
      this.face_matrix[row][column] = Character.toString(newColor); //static row, then column jumps
    }
  }
  public void modifyColumn(int column, char newColor){
    for (int row = 0; row < 3; row ++){
      this.face_matrix[row][column] = Character.toString(newColor); //static column, then row jumps
    }
  }

}
