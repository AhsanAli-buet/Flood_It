import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Map extends JLabel {

	public static final Color WHITE = new Color(255,255,255);
    public static final Color RED = new Color(255,0,0);
    public static final Color GREEN = new Color(0,255,0);
    public static final Color YELLOW = new Color(255,255,0);
    public static final Color BLUE = new Color(0,0,255);
    public static final Color PINK = new Color(255,0,255);
    public static final Color GREY = new Color(128,128,128);
    public JLabel Label;
    public Board Initial_Board;
    public int Click_count = 0;
    public int Barier = 0;
    public boolean click_valid;

    public static final Color[] TERRAIN = {
    	WHITE,RED,GREEN,YELLOW,BLUE,PINK,GREY
    };

    public  int NUM_ROWS;
    public int NUM_COLS;
    public final int preferredWidth = 300;
    public final int preferredHeight = 300;

    public int PREFERRED_GRID_SIZE_PIXELS;

    private Color[][] terrainGrid;

    public Map(int n,Board initial,JLabel label){
    	this.Label = label;
    	this.NUM_ROWS = n;
    	this.NUM_COLS = n;
    	click_valid = false;
    	if(NUM_COLS == 6){
    		this.Barier = 10;
    	}else if(NUM_COLS == 10){
    		this.Barier = 17;
    	}else if(NUM_COLS == 14){
    		this.Barier = 25;
    	}
    	this.PREFERRED_GRID_SIZE_PIXELS = this.preferredWidth / this.NUM_ROWS;
    	this.Initial_Board = initial;
        this.terrainGrid = new Color[NUM_ROWS][NUM_COLS];
        // Randomize the terrain
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
            	this.terrainGrid[j][i] = TERRAIN[Initial_Board.board[i][j]];
            }
        }
        
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        setBounds(300,100,300,300);
		setBorder(BorderFactory.createLineBorder(new Color(255,255,255), 5));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	if(click_valid)
            	{
            		Click_count++;
                	Label.setText(Integer.toString(Click_count));
                    update(e.getX(),e.getY());
                    if(Initial_Board.isGoal()){
                    	showMessage_win();
                    	click_valid = false;
                    	return;
                    }
                    if(Click_count >= Barier){
                    	showMessage_fail();
                    	click_valid = false;
                    }
            	}
            }
        });
    }
    
    public void Redraw_map(Board initial,int n){
    	
    	this.NUM_ROWS = n;
    	this.NUM_COLS = n;
    	click_valid = true;
    	Click_count=0;
    	Label.setText(Integer.toString(Click_count));
    	if(NUM_COLS == 6){
    		this.Barier = 10;
    	}else if(NUM_COLS == 10){
    		this.Barier = 17;
    	}else if(NUM_COLS == 14){
    		this.Barier = 25;
    	}
    	this.PREFERRED_GRID_SIZE_PIXELS = this.preferredWidth / this.NUM_ROWS;
    	this.Initial_Board = initial;
    	
        this.terrainGrid = new Color[NUM_ROWS][NUM_COLS];
        // Randomize the terrain
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
            	this.terrainGrid[j][i] = TERRAIN[Initial_Board.board[i][j]];
            }
        }
        
        repaint();
    }

    
    @Override
    public void paintComponent(Graphics g) {
        // Important to call super class method
        super.paintComponent(g);
        // Clear the board
        g.clearRect(0, 0, getWidth(), getHeight());
        // Draw the grid
        int rectWidth = getWidth() / NUM_COLS;
        int rectHeight = getHeight() / NUM_ROWS;

        for (int i = 0; i < NUM_COLS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                // Upper left corner of this terrain rect
                int x = i * rectWidth;
                int y = j * rectHeight;
                Color terrainColor = terrainGrid[i][j];
                g.setColor(terrainColor);
                g.fillRect(x, y, rectWidth, rectHeight);
            }
        }
    }
    
    
    public void showMessage_fail(){
    	JOptionPane.showMessageDialog(this, "  You Loose !!!", "Oops", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("rz_dislike.png"));
    }
    
    public void showMessage_win(){
    	JOptionPane.showMessageDialog(this, "  You Win !!!", "Yahoo", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("rz_thumb.png"));
    }
    
    public void update(int sx , int sy){
    	
    	int x = sx/this.PREFERRED_GRID_SIZE_PIXELS;
    	int y = sy/this.PREFERRED_GRID_SIZE_PIXELS;
    	
    	Color clr = terrainGrid[x][y];
    	int index = 0;
    	for(int i=0;i<TERRAIN.length;i++){
    		if(clr==TERRAIN[i]){
    			index = i;
    			break;
    		}
    	}
    	
    	Initial_Board.neighbors2(index,0);
    	
    	for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {

            	this.terrainGrid[j][i] = TERRAIN[Initial_Board.board[i][j]];
            }
        }
    	
    	repaint();
    }

}
