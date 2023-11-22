import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MazeGUI extends JFrame {
    private static final int CELL_SIZE = 32;
    private static final int MAZE_PADDING = 50;

    private int[][] maze;
    private int targetX, targetY;
private JTextField resultField;
    private JButton calculateButton;
    public MazeGUI(int[][] maze) {
        this.maze = maze;

        setTitle("Maze Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        MazePanel mazePanel = new MazePanel();
        add(mazePanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        
        // Create and configure the button
        calculateButton = new JButton("Calculate Route");
        calculateButton.addActionListener(e -> calculateRoute());

        // Create a text field to display the result
        resultField = new JTextField(20);
        resultField.setEditable(false); // So that the user can't edit the result

        // Add components to the GUI
        JPanel controlPanel = new JPanel();
        controlPanel.add(calculateButton);
        controlPanel.add(resultField);

        add(controlPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void calculateRoute() {
        // Logic for finding the shortest path...
        // Update the resultField with the calculated result
        resultField.setText("Your calculated route ");
        findShortestPath();
        
    }

  private void findShortestPath() {
        List<Integer> path = new ArrayList<>();
        boolean[][] visited = new boolean[maze.length][maze[0].length];

        // Find the start and target positions
        int startX = -1, startY = -1;
        for (int y = 1; y < maze.length; y++) {
            for (int x = 1; x < maze[0].length; x++) {
                if (maze[y][x] == 'S') {
                    startX = x;
                    startY = y;
                } else if (maze[y][x] == 'T') {
                    targetX = x;
                    targetY = y;
                }
            }
        }

        if (findShortestPathUtil(startX, startY, visited, path)) {
            markPath(path);
        }
    }

    private boolean findShortestPathUtil(int x, int y, boolean[][] visited, List<Integer> path) {
        if (x == targetX && y == targetY) {
            return true;
        }

        if (isValidMove(x, y, visited)) {
            visited[y][x] = true;
            path.add(x);
            path.add(y);

            if (findShortestPathUtil(x + 1, y, visited, path) ||
                    findShortestPathUtil(x - 1, y, visited, path) ||
                    findShortestPathUtil(x, y + 1, visited, path) ||
                    findShortestPathUtil(x, y - 1, visited, path)) {
                return true;
            }

            path.remove(path.size() - 1);
            path.remove(path.size() - 1);
        }

        return false;
    }

    private boolean isValidMove(int x, int y, boolean[][] visited) {
        return (x >= 0 && x < maze[0].length && y >= 0 && y < maze.length &&
                maze[y][x] != '1' && !visited[y][x]);
    }

    private void markPath(List<Integer> path) {
    ImageIcon pathIcon = new ImageIcon("C:\\test\\blue.png");


    for (int i = 0; i < path.size(); i += 2) {
        int x = path.get(i);
        int y = path.get(i + 1);

        maze[y][x] = 0; // To mark this cell as having an icon

        // Repaint the specific cell using the icon
        repaintCellWithIcon(x, y, pathIcon);
    }
}

private void repaintCellWithIcon(int x, int y, ImageIcon icon) {
    maze[y][x] = 0; // To mark this cell as having an icon
    // Get the graphics context
    Graphics g = getGraphics();

    // Draw the icon at the specific coordinates
    if (g != null) {
        icon.paintIcon(this, g, x * CELL_SIZE + MAZE_PADDING, y * CELL_SIZE + MAZE_PADDING);
        g.dispose();
    }
}

class MazePanel extends JPanel {
    private static final int CELL_SIZE = 32;
    private static final int MAZE_PADDING = 50;

    private ImageIcon wallIcon;
    private ImageIcon pathIcon;
    private ImageIcon startIcon;
    private ImageIcon targetIcon; 
    // You can add more icons for 'S', 'T', etc., if needed.

    public MazePanel() {
        wallIcon = new ImageIcon("C:\\test\\wall.png");
        pathIcon = new ImageIcon("C:\\test\\grass.jpg");
        startIcon = new ImageIcon ("C:\\test\\start.jpg");
        targetIcon = new ImageIcon ("C:\\test\\target.jpg");
        
        // Load more icons if necessary
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int y = 0; y < maze.length; y++) {
            for (int x = 0; x < maze[0].length; x++) {
                int cellValue = maze[y][x];
                ImageIcon iconToDraw = null;
                Color color;

                if (cellValue == '1') {
                    iconToDraw = wallIcon;
                } else if (cellValue == '0') {
                    iconToDraw = pathIcon;
                } else if (cellValue == 'S') {
                    iconToDraw = startIcon;
                } else if (cellValue == 'T') {
                    iconToDraw = targetIcon;
                } else {
                    color = Color.BLACK;
                }
            
                // Draw the icon at the specific coordinates
                if (iconToDraw != null) {
                    iconToDraw.paintIcon(this, g, x * CELL_SIZE + MAZE_PADDING, y * CELL_SIZE + MAZE_PADDING);
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        int width = maze[0].length * CELL_SIZE + MAZE_PADDING * 2;
        int height = maze.length * CELL_SIZE + MAZE_PADDING * 2;
        return new Dimension(width, height);
    }
}


}
