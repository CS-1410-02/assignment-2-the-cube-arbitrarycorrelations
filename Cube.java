import java.util.HashMap;
import java.util.Map;
public class Cube {
	public static void main(String[] args){
    //must take in arguments
    char[] colors_list = {'r','b','o','g','y','w'};
    Map<Integer, Face> cube = new HashMap<Integer, Face>(); //cube hashmap of int :: Face
    
    for(int i = 0; i < 6; i++){
      cube.put(i, new Face(colors_list[i]));
    }
    cube.get(1).getFace();
    
  }

  public static void printCube(Map<Integer, Face> cube){
    for(Face value: cube.values()){
      value.getFace();
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

  public void getFace(){
    String[][] target_data = this.face_matrix; 
    //add padding here instead of clogging up the actual face_matrix
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

  public String modifyFace(int column, int row, char newColor){
    face_matrix[column][row] = Character.toString(newColor);
    return ""; //Set this to return as the face matrix
  }

}
