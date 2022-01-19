import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class Solver 
{

	ArrayList<Board> solution;
	int minMove; 
	public static JFrame frame;
	public static JButton new_game;
	public static JButton solve_btn;
	public static JLabel label3;
	public static JLabel label6;
	public static int [][] present_colors;
	public static int global_dimens;
	public static JButton B_About = new JButton("About");
	
	public static JFrame AboutWindow = new JFrame();
	public static JLabel L_About = new JLabel("Flood It Game");
	public static JLabel T_About = new JLabel("",JLabel.CENTER);
	
	public static ArrayList<int[][]> Input_data = new ArrayList<int[][]>();
	public static Map map;
	 public static final String[] COLORS = {
	    	"WHITE","RED","GREEN","YELLOW","BLUE","PINK","GREY"
	    };
	 
	class MyComparator implements Comparator<Board>
	{

		@Override
		public int compare(Board o1, Board o2) {
			return o1.f()-o2.f();
		}
		
	}
	public int nodesExpanded=0;
	public Solver(Board initial) 
	{							

		PriorityQueue<Board> PQ = new PriorityQueue<>(10, new MyComparator());
		
		PQ.add(initial);
		while (!PQ.isEmpty())
		{
			
			nodesExpanded++;
			Board node = PQ.poll();
			
			if(node.isGoal())
			{
				minMove = node.get_g();
				solution = new ArrayList<Board>();
				solution.add(node);
				return;
			}
			
			ArrayList<Board> neighbors = node.neighbors();
			
			for(int i=0;i<neighbors.size();i++)
			{
				PQ.add(neighbors.get(i));
			}

		}

	}


	public int moves() 			 
	{
		return minMove;
	}
	public ArrayList<Board> solution() 
	{		
		return solution;
	}
	
	public static void Build_game_window(int dimension){
		
            frame = new JFrame("Flood It Game");
            JLabel label = new JLabel();
            JLabel label1 = new JLabel();
            JLabel label2 = new JLabel();
            label3 = new JLabel();
            label6 = new JLabel();
            JLabel label5 = new JLabel();
            JLabel label4 = new JLabel();
            frame.setBounds(100, 100, 900, 530);
            label1.setText("Flood It");
            label1.setFont(new Font("Lucida Calligraphy", Font.BOLD, 30));
            label1.setForeground(Color.RED);
            label1.setBounds(380,30,300,50);
            
            new_game = new JButton("New Game");
            new_game.setBounds(90,250,150,30);
            new_game.setBackground(Color.GRAY);
            new_game.setForeground(new Color(70,200,255));
            new_game.setFont(new Font("Lucida Console", Font.BOLD|Font.ITALIC, 20));
            new_game.setHorizontalTextPosition(SwingConstants.CENTER);
            new_game.setBorder(new LineBorder(Color.CYAN,3));
            
            solve_btn = new JButton("Solve Game");
            solve_btn.setBounds(700,40,150,30);
            solve_btn.setBackground(Color.GRAY);
            solve_btn.setForeground(new Color(70,200,255));
            solve_btn.setFont(new Font("Lucida Console", Font.BOLD, 16));
            solve_btn.setHorizontalTextPosition(SwingConstants.CENTER);
            solve_btn.setBorder(new LineBorder(Color.CYAN,3));
            solve_btn.setEnabled(false);
            
            B_About.setBounds(20,450,100,30);
            B_About.setBackground(Color.GRAY);
            B_About.setForeground(new Color(70,200,255));
            B_About.setFont(new Font("Lucida Console", Font.BOLD, 16));
            B_About.setHorizontalTextPosition(SwingConstants.CENTER);
            B_About.setBorder(new LineBorder(Color.CYAN,3));
    		
            label.setText("Moves :");
            label.setFont(new Font("Lucida Calligraphy", Font.BOLD, 30));
            label.setForeground(new Color(255,255,255));
            label.setBounds(330,420,150,50);
            
            label2.setText("0");
            label2.setFont(new Font("Lucida Calligraphy", Font.BOLD, 30));
            label2.setForeground(new Color(255,255,255));
            label2.setBounds(480,420,50,50);
            
            label3.setText("0");
            
            label3.setFont(new Font("Lucida Calligraphy", Font.BOLD, 30));
            label3.setForeground(new Color(255,255,255));
            label3.setBounds(560,420,50,50);
            
            label6.setFont(new Font("Lucida Console", Font.BOLD, 14));
            label6.setForeground(new Color(255,255,255));
            label6.setBounds(700,10,100,450);
            label6.setText("");
            
            label4.setText("|");
            label4.setFont(new Font("Lucida Calligraphy", Font.BOLD, 30));
            label4.setForeground(new Color(255,255,255));
            label4.setBounds(530,420,10,50);
            
            label5.setText("<html>CLICK CELLS.<br>FILL THE BOARD<br>WITH A SINGLE COLOR.<br><br><br>6X6 10X10 14X14 GRID<br>&nbsp;GENERTAES RANDOMLY</html>");
            label5.setHorizontalAlignment(SwingConstants.CENTER);
            label5.setFont(new Font("Lucida Console", Font.BOLD, 15));
            label5.setForeground(new Color(255,255,255));
            label5.setBounds(50,90,250,150);
            
    		frame.setResizable(false);
    		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		
    		
    		JLabel lblNewLabel = new JLabel(new ImageIcon("theme.bmp"));
    		frame.setContentPane(lblNewLabel);
    		frame.getContentPane().setLayout(null);
    		
    		int [][] colors = new int[dimension][dimension];
    		for (int i = 0; i < dimension; i++)
				for (int j = 0; j < dimension; j++)
					colors[i][j] = 0;
    		
    		Board bdr  = new Board(colors,null,0);
    		map = new Map(dimension,bdr,label2);
    		
    		frame.getContentPane().add(map);
    		frame.getContentPane().add(label);
    		frame.getContentPane().add(label1);
    		frame.getContentPane().add(label2);
    		frame.getContentPane().add(label3);
    		frame.getContentPane().add(label4);
    		frame.getContentPane().add(new_game);
    		frame.getContentPane().add(solve_btn);
    		frame.getContentPane().add(B_About);
    		frame.getContentPane().add(label5);
    		frame.getContentPane().add(label6);
    		//frame.pack();
           
            frame.setVisible(true);

	}
	
	public static void Build_About_Window(){
		
		AboutWindow.setBounds(0, 0, 350, 500);
		AboutWindow.setLocationRelativeTo(frame);
		AboutWindow.setContentPane(new JLabel(new ImageIcon("theme.bmp")));
		AboutWindow.getContentPane().setLayout(null);
		AboutWindow.setTitle("About Flood It Game");
		AboutWindow.setResizable(false);
		
		T_About.setForeground(Color.BLACK);
		T_About.setFont(new Font("Lucida Console", Font.BOLD | Font.ITALIC, 13));
		T_About.setBackground(new Color(0,0,0,0));
		T_About.setEnabled(true);
		AboutWindow.getContentPane().add(T_About);
		T_About.setBounds(20,10,325,400);
		T_About.setText("<html>Flood It Game is an assignment of <br>AI LAB BUET CSE Level-4 Term-1, developed by Ahsan Ali 1105083. This is developed in java jdk7." +
				"<br><br>Flood It Game is a simple strategy game where you have to flood the whole game board with one color in less than the allowed steps." +
				"There are 6X6, 10X10 and 14X14 Grids in this game with 6 colors." +
				"<br><br>The solution of any game board is generated by clicking solve game button, which shows the steps of solution." +
				"<br><br>Thank you.<br>Ahsan Ali(1105083),BUET,CSE</html>");
		
		L_About.setForeground(Color.RED);
		L_About.setHorizontalAlignment(SwingConstants.CENTER);
		L_About.setFont(new Font("Lucida Calligraphy", Font.BOLD, 15));
		L_About.setBounds(20,20, 280,25);
		AboutWindow.getContentPane().add(L_About);
		
		AboutWindow.setVisible(false);
	}
            
	public static void main(String args[]) 
	{
		Scanner in=null;
		try 
		{
			in = new Scanner(new File("input.txt"));
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		Build_game_window(6);
		Build_About_Window();
		SetActionListener();
		while(true)
		{
			int N = in.nextInt();
			if(N==0) return;

			int[][] colors = new int[N][N];
			for (int i = 0; i < N; i++)
				for (int j = 0; j < N; j++)
					colors[i][j] = in.nextInt();
			
			Input_data.add(colors);
		}
		
		
	}
	
	public static void SetActionListener(){
		
		new_game.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				solve_btn.setEnabled(true);
				label6.setText("");
				Random rnd = new Random();
				int it = rnd.nextInt(3);
				int dimens=0;
				if(it==0){dimens=6;global_dimens=6;label3.setText("10");}
				if(it==1){dimens=10;global_dimens=10;label3.setText("17");}
				if(it==2){dimens=14;global_dimens=14;label3.setText("25");}
				
				int [][] colors = new int[dimens][dimens];
				present_colors = new int[dimens][dimens];
	    		for (int i = 0; i < dimens; i++){
	    			for (int j = 0; j < dimens; j++){
	    				colors[i][j] = Input_data.get(it)[i][j];
	    				present_colors[i][j] = Input_data.get(it)[i][j];
	    			}	
	    		}
	    		
				Board bdr  = new Board(colors,null,0);
				map.Redraw_map(bdr,dimens);
			}
		});
		
		solve_btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				
				if(global_dimens==14){
					JOptionPane.showMessageDialog(frame,"14X14 Grid takes too many time to solve so we skipped it, try another Grid 6X6 or 10X10 Grid");
				}else{
					solve_game(present_colors,global_dimens);
				}
			}
		});
		
		B_About.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				AboutWindow.setVisible(true);
			}
		});
		
		
		frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
            	System.exit(0);
            }
        });
		
		AboutWindow.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
            	AboutWindow.setVisible(false);
            }
        });
		
		
		
	}
	
	public static void solve_game(int [][]colors,int N){
		
		Board initial = new Board(colors,null,0);

		Solver solver = new Solver(initial);
	    //System.out.println("Nodes Expanded: "+ solver.nodesExpanded);
		//System.out.println("Minimum number of moves = " + solver.moves());
		ArrayList<Board> solution = solver.solution();
		
		 Board solve = solution.remove(0);
		 while(solve!=null)
         {
             solution.add(solve);
             solve=solve.getParent();
         }
		 
		 //System.out.print("Solution Sequence : ");
		 //for (int i=solution.size()-2;i>=0;i--){
		//	 	System.out.print(solution.get(i).board[0][0]+" ");
		// }
		 
		// System.out.println("\n");
		// System.out.println("Initial Board:");
		// System.out.println(solution.get(solution.size()-1));
		 String str="";
		 str+="<html>Moves: "+solver.moves()+"<br><br>";
		 int j=0;		 
		 for (int i=solution.size()-2;i>=0;i--){
			 	j++;
			 	str+=j+". "+COLORS[solution.get(i).board[0][0]]+"<br>";
		//	 	System.out.println("Step : "+j+" , "+ "Select : "+ solution.get(i).board[0][0] +" , Color : "+COLORS[solution.get(i).board[0][0]]);
		//		System.out.println(solution.get(i));
				
		 }
		 str+="</html>";
		//final Board soln = solution.get(solution.size()-1);
		//final int dimension = N;
		
		label6.setText(str);
		solve_btn.setEnabled(false);
		
	}
	
	
}

