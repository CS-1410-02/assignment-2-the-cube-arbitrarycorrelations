
public class Cube {
	public static void main(String[] args){
    char[] colors = {'r','b','o','g','y','w'};
    //cube object will be list of Face objects
    

  }

}

class Face {
  private char padding = '|';
  private String[][] face_matrix = new String[3][5];
  Face(char color){
    for(int x = 0; x < 3; x++){ //Columns
      for (int y = 0; y < 5; y++){ //Rows
        if (y != 1 && y != 3){face_matrix[x][y] = Character.toString(color);}
        else {face_matrix[x][y] = Character.toString(padding);}
      }
    }
  
  }

  public String getFace(){

    return "";
  }

  public String modifyFace(int column, int row, char newColor){
    face_matrix[column][row] = Character.toString(newColor);
    return ""; //Set this to return as the face matrix
  }

}
