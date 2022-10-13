import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class Cube {

  //static keyword indicates the variable is defined throughout the namespace of the class Cube
  static Map<Integer, Face> cube_object = new HashMap<Integer, Face>(); 
  static Map<Integer, Face> cubeState = new HashMap<Integer, Face>(); 
  // static Face pivotFace = cube_object.get(1);
  static Map<Integer, Character> colors_list = new HashMap<>();
  static ArrayList<Character> colorOrder = new ArrayList<Character>(List.of('r','b','o','g','y','w')); //Starts at 1

  public static void main(String[] args){
    //must take in arguments
    for (char color: colorOrder){
      colors_list.put(colorOrder.indexOf(color) + 1, color);
    }
    for(int i = 1; i < 7; i++){
      cube_object.put(i, new Face(colorOrder.get(i-1)));
      cubeState.put(i, new Face(colorOrder.get(i-1)));
    }

    cube_object.get(1).transform("u");
    printCube(cube_object);
    System.out.println("--------------");

    cube_object.get(1).transform("u");
    printCube(cube_object);

  }

  public static void printCube(Map<Integer, Face> cube){
    for(Map.Entry<Integer, Face> entry : cube.entrySet()){
      entry.getValue().printFace();
      System.out.println();
    }
  }

  public static void updateCubeState(){
    for (Map.Entry<Integer, Face> pair : cube_object.entrySet()){
      String[][] matrix_to_use = cube_object.get(pair.getKey()).getMatrix();
      cubeState.get(pair.getKey()).setMatrix(matrix_to_use); 
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

  public char getFaceColor(){return this.facecolor;}

  public String[][] getMatrix(){
    return this.face_matrix;
  }

  public void setMatrix(String[][] matrix){
    for(int x = 0; x < 3; x++){ //Columns
      for (int y = 0; y < 3; y++){ //Rows
        this.face_matrix[x][y] = matrix[x][y];
      }
    }
  }

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

  // public Map<Integer, Character> createColorList(ArrayList<Character> order){
  //   Map<Integer, Character> specificOrder = new HashMap<Integer, Character>();
  //   for (char color : order){
  //     specificOrder.put(order.indexOf(color) + 1, color);
  //   }
  //   return specificOrder;
  // }

  public String cellColor(Face face, int column, int row){ //called on a face
    
    String color_to_return = face.face_matrix[row][column];
    return color_to_return;
  }

  public void transform(String move){

    String[] validMoves = {"u", "d", "r", "l", "f", "b","u'", "d'", "r'", "l'", "f'", "b'"}; 
    boolean move_is_valid = Arrays.asList(validMoves).contains(move);

    // for(String testMove: validMoves){
      // if(move == testMove || move.equals(testMove + "'")){
        // move_is_valid = true;
        // break;

      // }else{move_is_valid = false;}
    // }

    if (!move_is_valid){
      System.out.println("That isn't a valid move.");

    }else{
      switch (move){

        case "u", "u'": {
          //r,b,o,g,y,w
          if (move.equals("u")){
            for (Map.Entry<Integer, Face> entry : Cube.cube_object.entrySet()){
              int faceKey = entry.getKey();
              if (!List.of(5,6).contains(faceKey)){
                int keyToUse = (faceKey != 1) ? (faceKey - 1) : 4;
                for (int column = 0; column < 3; column++){
                  Face faceToSee = Cube.cubeState.get(keyToUse);
                  System.out.println("COLUMN: "+column);
                  String storedColor = cellColor(faceToSee, column, 0);
                  System.out.println("CTU:"+ storedColor+"\n"+faceKey+":"+keyToUse+"\n");
                  entry.getValue().modifyCell(column, 0, storedColor);
                }
              }
            }
          }
          else if (move.equals("u'")){
            for (Map.Entry<Integer, Face> entry : Cube.cube_object.entrySet()){
              int faceKey = entry.getKey();
              if (!List.of(5,6).contains(faceKey)){
                int keyToUse = (faceKey != 4) ? (faceKey + 1) : 1;
                for (int column = 0; column < 3; column++){
                  Face faceToSee = Cube.cubeState.get(keyToUse);
                  String storedColor = cellColor(faceToSee, column, 0);
                  entry.getValue().modifyCell(column, 0, storedColor);
                }
              }
            }
          }
          Cube.updateCubeState();
          break;
          /* 
          if(move == "u"){
            this.modifyRow(0, 'g');
            for (int i = 2; i < 5; i++){ //starts at 4 as yellow and white are not modified in this operation
              Cube.cube_object.get(i).modifyRow(0, Cube.colors_list.get(i-1));;
            } 
          }else if (move.equals("u'")){
            for (int i = 1; i < 5; i++){ //starts at 4 as yellow and white are not modified in this operation
              if (i == 4){Cube.cube_object.get(i).modifyRow(0, Cube.colors_list.get(1));}
              else{Cube.cube_object.get(i).modifyRow(0, Cube.colors_list.get(i+1));}
            } 
          }
          break; //breaks to avoid entering other cases
          //These two cases ^ and v are functionally identical, they just get called on different rows.
        }
      */}
         //(top row) u: left, u': right
        case "d", "d'": {
          if(move == "d"){
            this.modifyRow(2, 'g');
            for (int i = 2; i < 5; i++){ //starts at 4 as yellow and white are not modified in this operation
              Cube.cube_object.get(i).modifyRow(2, Cube.colors_list.get(i-1));;
            } 
          }else if (move.equals("d'")){
            for (int i = 1; i < 5; i++){ //starts at 4 as yellow and white are not modified in this operation
              if (i == 4){Cube.cube_object.get(i).modifyRow(2, Cube.colors_list.get(1));}
              else{Cube.cube_object.get(i).modifyRow(2, Cube.colors_list.get(i+1));}
            } 
          }
          break;
        } //(bottom row) d: right, d': left

        case "r", "r'": {
          if (move == "r"){
            //if face not 2 or 4
            this.modifyColumn(2, 'w');
            Cube.cube_object.get(3).modifyColumn(2, 'y');
            Cube.cube_object.get(5).modifyColumn(2, 'r');
            Cube.cube_object.get(6).modifyColumn(2, 'o');
            break;

          }else if (move.equals("r'")){
            this.modifyColumn(2, 'y');
            Cube.cube_object.get(3).modifyColumn(2, 'w');
            Cube.cube_object.get(5).modifyColumn(2, 'o');
            Cube.cube_object.get(6).modifyColumn(2, 'r');
            break;
          }
        } //(right column) r: up, r': down
        //These two cases ^ and v are functionally identical, they just get called on different columns.
        case "l", "l'": {
          if (move == "l"){
            //if face not 2 or 4
            this.modifyColumn(2, 'w');
            Cube.cube_object.get(3).modifyColumn(0, 'y');
            Cube.cube_object.get(5).modifyColumn(0, 'r');
            Cube.cube_object.get(6).modifyColumn(0, 'o');
            break;

          }else if (move.equals("l'")){
            this.modifyColumn(2, 'y');
            Cube.cube_object.get(3).modifyColumn(0, 'w');
            Cube.cube_object.get(5).modifyColumn(0, 'o');
            Cube.cube_object.get(6).modifyColumn(0, 'r');
            break;

          }
        } //(left column) l: down, l': up

        case "f", "f'": {
          //('r','b','o','g','y','w')
          if (move.equals("f")){
            Cube.cube_object.get(5).modifyColumn(2, 'b');
            Cube.cube_object.get(2).modifyColumn(2, 'w');
            Cube.cube_object.get(6).modifyColumn(2, 'g');
            Cube.cube_object.get(4).modifyColumn(2, 'y');
            break;
          }
          else if (move.equals("f'")){
            Cube.cube_object.get(5).modifyColumn(2, 'g');
            Cube.cube_object.get(2).modifyColumn(2, 'y');
            Cube.cube_object.get(6).modifyColumn(2, 'b');
            Cube.cube_object.get(4).modifyColumn(2, 'w');
            break;
          }
        } //(front) f: right turn, f': left turn

        case "b", "b'": {
          if (move.equals("b")){
            Cube.cube_object.get(5).modifyColumn(0, 'b');
            Cube.cube_object.get(2).modifyColumn(0, 'w');
            Cube.cube_object.get(6).modifyColumn(0, 'g');
            Cube.cube_object.get(4).modifyColumn(0, 'y');
            break;
          }
          else if (move.equals("b'")){
            Cube.cube_object.get(5).modifyColumn(0, 'g');
            Cube.cube_object.get(2).modifyColumn(0, 'y');
            Cube.cube_object.get(6).modifyColumn(0, 'b');
            Cube.cube_object.get(4).modifyColumn(0, 'w');
            break;
          }
        } //(back) b: right turn, b': left turn

      }

    }
  }

  public void modifyCell(int column, int row, String newColor){
    if (row < 3 && column < 3){
      this.face_matrix[row][column] = newColor;
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
