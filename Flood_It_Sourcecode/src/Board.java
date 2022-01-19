import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

public class Board 
{
	private int g;
	public int board[][];
	private Board parent = null;
	
	ArrayList<Integer> Neighbor_List = new ArrayList<Integer>();
	 
	public Board(int[][] colors, Board parent,int g) 
	{	
		this.board = colors;											
		this.parent=parent;						 					
		this.g = g;
	}
	
	
	public int f() 
	{
		return g+heuristic1()+heuristic2();
	}
	
	
	public int heuristic1() 
	{
		Set<Integer> colors = new HashSet<Integer>();
		for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                colors.add(board[i][j]);
            }
        }
		return colors.size()-1;	
	}
	
	public int heuristic2()  
	{
		   ArrayList<pair> visited;
		   int max =0;
		   int mx = 0;
		   int my =0;
		   
		   visited = neighbors2(0,1);
		   
		   pair p = null;
		   for(int i=0;i<visited.size();i++){
			   
			   p = visited.get(i);
			   if(p.x > mx){
				   mx = p.x;
			   }
			   if(p.y > my){
				   my = p.y;
			   }
		   }
		   
		   if(mx > my){
			   max = mx;
		   }else{
			   max = my;
		   }
		   
		   
		return (this.board.length - max);
	}

	public boolean isGoal() 
	{
		int temp = board[0][0];
		
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (temp != board[i][j]) {
                    return false;
                }
            }
        }
        
        return true;
	}
	
	public int[][] getCopy(int board[][])
	{
		int [] [] nxtBoard = new int[board.length][board[0].length];
		for (int i=0; i<board.length;i++)
		{
			for(int j=0;j<board[i].length;j++)
			{
					nxtBoard[i][j]  = board[i][j];
			}
		}
		return nxtBoard;
	}
	
	public ArrayList<Board> neighbors() 
	{

	   ArrayList<Board> Neighbor_Board = new ArrayList<Board>();
	   ArrayList<pair> visited = new ArrayList<pair>();
	   Set<Integer> Neighbor = new HashSet<Integer>();
	   
	   int temp  = board[0][0];
	   
	   Stack<pair> stack = new Stack<pair>();
	   
	   pair cur_pos = new pair();
	   pair pos = new pair(0,0);
	   stack.push(pos);
	
	   while(!stack.empty()){
		   
		   cur_pos = (pair)stack.pop();
		   
		   boolean flag = false;
		   for(int i=0;i<visited.size();i++){
			   
			   if( visited.get(i).x == cur_pos.x && visited.get(i).y == cur_pos.y  ){
				   flag = true;
			   }
		   }
		   
		   if(flag){
			   continue;
		   }
		   
		   visited.add(cur_pos);
		   
		   if (cur_pos.x > 0) {  
               if (board[cur_pos.x - 1][cur_pos.y] == temp) {
                   pair value = new pair(cur_pos.x - 1, cur_pos.y);
                   stack.push(value);

               }else {

                   int value = board[cur_pos.x - 1][cur_pos.y];
                   Neighbor.add(value);
               }
           }
		   
		   if(cur_pos.x + 1 < board.length){
			   if (board[cur_pos.x + 1][cur_pos.y] == temp) {
                   pair value = new pair(cur_pos.x + 1, cur_pos.y);
                   stack.push(value);

               }else {

                   int value = board[cur_pos.x + 1][cur_pos.y];
                   Neighbor.add(value);
               }  
		   }
		   
		   if(cur_pos.y  > 0 ){
			   if (board[cur_pos.x][cur_pos.y - 1] == temp) {
                   pair value = new pair(cur_pos.x, cur_pos.y - 1);
                   stack.push(value);

               }else {

                   int value = board[cur_pos.x][cur_pos.y-1];
                   Neighbor.add(value);
               }  
		   }
		   
		   if(cur_pos.y + 1 < board.length){
			   if (board[cur_pos.x][cur_pos.y + 1] == temp) {
                   pair value = new pair(cur_pos.x, cur_pos.y + 1);
                   stack.push(value);

               }else {

                   int value = board[cur_pos.x][cur_pos.y + 1];
                   Neighbor.add(value);
               }  
		   }
	   }
	   
	   int nbr_value;
	   
	   Iterator<Integer> iter = Neighbor.iterator();
	   while (iter.hasNext()) {
	       
		   nbr_value = (int)iter.next();
		   
		   int newBoard[][] = getCopy(this.board);
		   
		   for(int i=0;i< visited.size();i++){
			   
			   pair p = visited.get(i);
			   newBoard[p.x][p.y] = nbr_value;
		   }
		   
		   newBoard[0][0] = nbr_value;
	       
	       Board n = new Board(newBoard, this, this.g + 1);
	       Neighbor_Board.add(n);
	   }

	   return Neighbor_Board;		
	}
	
	
	
	
	public ArrayList<pair> neighbors2(int index,int role) 
	{
	   ArrayList<pair> visited = new ArrayList<pair>();
	   
	   int temp  = board[0][0];
	   
	   Stack<pair> stack = new Stack<pair>();
	   
	   pair cur_pos = new pair();
	   pair pos = new pair(0,0);
	   stack.push(pos);
	
	   while(!stack.empty()){
		   
		   cur_pos = (pair)stack.pop();
		   
		   boolean flag = false;
		   for(int i=0;i<visited.size();i++){
			   
			   if( visited.get(i).x == cur_pos.x && visited.get(i).y == cur_pos.y  ){
				   flag = true;
			   }
		   }
		   
		   if(flag){
			   continue;
		   }
		   
		   visited.add(cur_pos);
		   
		   if (cur_pos.x > 0) {  
               if (board[cur_pos.x - 1][cur_pos.y] == temp) {
                   pair value = new pair(cur_pos.x - 1, cur_pos.y);
                   stack.push(value);

               }
           }
		   
		   if(cur_pos.x + 1 < board.length){
			   if (board[cur_pos.x + 1][cur_pos.y] == temp) {
                   pair value = new pair(cur_pos.x + 1, cur_pos.y);
                   stack.push(value);

               } 
		   }
		   
		   if(cur_pos.y  > 0 ){
			   if (board[cur_pos.x][cur_pos.y - 1] == temp) {
                   pair value = new pair(cur_pos.x, cur_pos.y - 1);
                   stack.push(value);

               } 
		   }
		   
		   if(cur_pos.y + 1 < board.length){
			   if (board[cur_pos.x][cur_pos.y + 1] == temp) {
                   pair value = new pair(cur_pos.x, cur_pos.y + 1);
                   stack.push(value);

               }
		   }
	   }
	   
	   if(role==1){
		   return visited;
	   }else{
		   
		   for(int i=0;i< visited.size();i++){
			   
			   pair p = visited.get(i);
			   board[p.x][p.y] = index;
		   }
		   
		   return null;
	   }
	   		
	}
	
	
	
	
	public class pair{
		
		public int x;
		public int y;
		
		public pair(int x , int y){
			
			this.x = x;
			this.y = y;
		} 
		
		public pair(){
			x = -1;
			y = -1;
		}
		
	}
	
	
	public int get_g()
	{
		return g;
	}
	
	public Board getParent() {
		return parent;
	}
	
	public String toString() 
	{
		String str="\ng: "+g+" h:"+heuristic1()+"\n";
		for (int i=0; i<board.length;i++)
		{
			for(int j=0;j<board[i].length;j++)
			{
				str += (board[i][j]+" ");
			}
			str +="\n";
		}
		return str;
	}

	
}



