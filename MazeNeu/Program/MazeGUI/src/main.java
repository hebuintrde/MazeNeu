
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;


public class main {
     public static void main(String[] args) {
        // Replace this path with the correct file path of your maze text file
        String filePath = "C:\\test\\maze.txt";

        int[][] maze = readMazeFromFile(filePath);

        if (maze != null) {
            SwingUtilities.invokeLater(() -> new MazeGUI(maze));
        } else {
            System.err.println("Failed to read the maze from the file.");
        }
    }

    private static int[][] readMazeFromFile(String filePath) {
       try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            List<String> lines = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();

            int height = lines.size();
            int width = lines.get(0).length();
            int[][] maze = new int[height][width];

            for (int i = 0; i < height; i++) {
                line = lines.get(i);
                for (int j = 0; j < width; j++) {
                    maze[i][j] = line.charAt(j);
                }
            }

            return maze;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
   }
