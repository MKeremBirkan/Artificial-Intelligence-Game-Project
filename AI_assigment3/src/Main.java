import java.util.Scanner;

public class Main {
    //PLAYER - 1 - START
    public static int Place_X1 = 2;
    public static int Place_Y1 = 3;
    public static int exX1=2,exY1=3;
    //PLAYER - 2 AI - START
    public static int Place_X2 = 4;
    public static int Place_Y2 = 3;
    public static int exX2=4,exY2=3;
    
    public static Scanner newPlace;
    
    public static String board[][] = new String[7][7];
    
    public static final int heuristicDegree = 8;//Wave degree
    public static int heuristic[][] = new int[7][7];
    public static int heuristicBoard[][] = new int[][]{ 
    											   {6,5,4,3,4,5,6},
												   {5,4,3,2,3,4,5},
												   {4,3,2,1,2,3,4},
												   {3,2,1,1,1,2,3},
												   {4,3,2,1,2,3,4},
												   {5,4,3,2,3,4,5},
												   {6,5,4,3,4,5,6}
												   };
     									       
    public static void main(String args[]) throws Exception
    {
    	int game=1;
    	CreateGame();
    	
    	////////TEST//////////
    	//createHeuristic(1, 1);
    	//PrintHeuristic(heuristic);
    	//PrintHeuristic(heuristicBoard);
    	
        while(game==1){ 
        	
            game=Move(0);//0 for player moving on the board
            Move(1);//1 for player removing a square from board
            if(game==1){
            	game=AImove();//AI moving on the board
                AIremove();//AI removing a square from board
            }  	      
        }
        
        System.out.printf("GAME OVER");
    }
    
    public static void CreateGame()
    {
        for(int i = 0; i < 7; i++) {
            for(int j = 0; j < 7; j++) {
        
                board[i][j] = "-";
            }
        }
        board[3][2] = "1";
        board[3][4] = "2";
    }
    
    public static void PrintBoard(String Array[][])
    {
    	char row = 'a';
    	System.out.printf("  ");
    	for(int column=1;column<=7;column++){
    		System.out.printf(" " + column + " ");
    	}
    	System.out.println();
        for(int i = 0; i < 7; i++) {
        	System.out.printf(row + " ");
            for(int j = 0; j < 7; j++) {
        
                System.out.printf(" %s ", Array[i][j]);
                
            }
            System.out.println();
            row++;
        }
    }
    public static void PrintHeuristic(int Array[][]){
    	for(int i = 0; i < 7; i++) {
        	
            for(int j = 0; j < 7; j++) {
        
                System.out.printf(" %d ", Array[i][j]);
                
            }
            System.out.println();
            
        }
    	System.out.println();
    }
    public static void createHeuristic(int x , int y ){
    	int UP,DOWN,RIGHT,LEFT;
    	UP = y-1;
    	DOWN = y+1;
    	RIGHT = x+1;
    	LEFT = x-1;
    	
    	heuristic[y][x]=heuristicDegree;
    	
    	for(int i=heuristicDegree;i<heuristicDegree*6;i+=heuristicDegree){//Waves Degree
    		
    		if(DOWN <7){
    			heuristic[DOWN][x]=i;
    			if(0<=LEFT){
    				for(int m = LEFT; m<=x;m++)
        				heuristic[DOWN][m]=i;
    			}else{
    				for(int n =x;0<=n;n--)
    					heuristic[DOWN][n]=i;
    			}
    			if(RIGHT<7){
    				for(int v = RIGHT; x<=v;v--)
    					heuristic[DOWN][v]=i;
    			}else{
    				for(int u = x; u<=6;u++)
    					heuristic[DOWN][u]=i;
    			}
    						   				
    		}
    		if(0<=UP){
    			heuristic[UP][x]=i;
    			if(0<=LEFT){
    				for(int l = LEFT; l<=x;l++)
    					heuristic[UP][l]=i;
    			}else{
    				for(int h = x; 0<=h;h--)
    					heuristic[UP][h]=i;
    			}			
    			if(RIGHT<7){
    				for(int r = RIGHT; x<=r;r--)
    					heuristic[UP][r]=i;			
    			}else{
    				for(int b = x; b<=6;b++)
    					heuristic[UP][b]=i;
    			}
    				
    		}
    		if(RIGHT<7){
    			if(0<=UP){
    				for(int c= UP; c<=y;c++)
    					heuristic[c][RIGHT]=i;
    			}else{
    				for(int z=y; 0<=z;z--)
    					heuristic[z][RIGHT]=i;
    			}
    			if(DOWN<7){
    				for(int t= DOWN; y<=t;t--)
    					heuristic[t][RIGHT]=i;
    			}else{
    				for(int g= y; g<=6;g++)
    					heuristic[g][RIGHT]=i;
    			}
    		}	
    		if(0<=LEFT){
    			if(0<=UP){
    				for(int q= UP; q<=y;q++)
    					heuristic[q][LEFT]=i;
    			}else{
    				for(int s= y; 0<=s;s--)
    					heuristic[s][LEFT]=i;
    			}
    			if(DOWN<7){
    				for(int t= DOWN; y<=t;t--)
    					heuristic[t][LEFT]=i;
    			}else{
    				for(int g= y; g<=6;g++)
    					heuristic[g][LEFT]=i;
    			}
    		}
    			
    		UP--;
    		DOWN++;
    		RIGHT++;
    		LEFT--;
    	}
    	 
    	for(int k=0;k<7;k++){
    		for(int p=0;p<7;p++){
    			heuristic[k][p] = heuristicBoard[k][p] + heuristic[k][p];
    		}
    	}
    	
    }
    public static void AIremove(){
    	int min=100,minX=0,minY=0;
    	
    	createHeuristic(Place_X1 , Place_Y1 );
    	for(int k=0;k<7;k++){
    		for(int p=0;p<7;p++){
    			if(board[k][p]=="X"||board[k][p]=="1"||board[k][p]=="2"||board[k][p]=="O"){
    				heuristic[k][p]+=100; 				
    			}    			
    		}
    	}
    	
    	heuristic[exY2][exX2] = heuristic[exY2][exX2] + 100;
    	
    	for(int k=0;k<7;k++){
    		for(int p=0;p<7;p++){
    			if(heuristic[k][p] <=min){
    				min=heuristic[k][p]; 
    				minY=k;
    				minX=p;
    			}    			
    		}
    	}
    	
	   /*PrintHeuristic(heuristic);
	   System.out.println("Remove ");
	   System.out.println("after for!" + " MinX : " + minX + " MinY " + minY);
	   System.out.println("remove END!" + " Place_x2 : " + Place_X2 + " Place_y2 " + Place_Y2);*/
	    		
	   board[minY][minX] = "O";
	   
	   for(int k=0;k<7;k++){
   			for(int p=0;p<7;p++){
   				if(heuristic[k][p] <=min){
   					heuristic[k][p]=0; 		
   				}    			
   			}
   		}
    	//System.out.println("Minx : " + minX + " Miny : " + minY + " heu : " + heuristicBoard[minY][minX] + "board " + board[minY][minX]);
    	
    }
    public static int AImove(){
    	
    	int min=100,minX=0,minY=0;
    	if(gameOver(Place_X2,Place_Y2,1)){
    		System.out.println("AI lose !");
    		return 0;
    	}
    	createHeuristic(Place_X2,Place_Y2);
    	//PrintHeuristic(heuristic);
    	for(int k=0;k<7;k++){
    		for(int p=0;p<7;p++){
    			if(board[k][p]=="X"||board[k][p]=="1"||board[k][p]=="2"){
    				heuristic[k][p]+=100; 				
    			}    			
    		}
    	}
    	
    	for(int k=0;k<7;k++){
    		for(int p=0;p<7;p++){
    			if(heuristic[k][p] <=min){
    				min=heuristic[k][p]; 
    				minY=k;
    				minX=p;
    			}    			
    		}
    	}
    	while(true){
	    	if((board[minY][minX]=="-" || board[minY][minX]=="O" )&& ((Math.abs(Place_X2-minX)==0 && Math.abs(Place_Y2-minY)==1)||(Math.abs(Place_X2-minX)==1 && Math.abs(Place_Y2-minY)==0))){//
	    		board[minY][minX] = "2";   
	            board[Place_Y2][Place_X2] = "-";
	            exX2=Place_X2;
	            exY2=Place_Y2;
	            Place_X2 = minX;
	            Place_Y2 = minY;
	            break;
	    	}else {
	    		heuristic[minY][minX] = heuristic[minY][minX] + 100;
	    		//System.out.println("before for!" + " MinX : " + minX + " MinY " + minY);	
	    		for(int k=0;k<7;k++){
	        		for(int p=0;p<7;p++){
	        			if(heuristic[k][p] <=min){
	        				min=heuristic[k][p]; 
	        				minY=k;
	        				minX=p;
	        			}    			
	        		}
	        	}
	    		/*PrintHeuristic(heuristic);
	    		System.out.println("after for!" + " MinX : " + minX + " MinY " + minY);
	    		System.out.println("END!" + " Place_x2 : " + Place_X2 + " Place_y2 " + Place_Y2);*/
	    	}
    	
    	}
    	for(int k=0;k<7;k++){
    		for(int p=0;p<7;p++){
    			if(heuristic[k][p] <=min){
    				heuristic[k][p]=0; 		
    			}    			
    		}
    	}
    	return 1;
    	
    }
    public static boolean gameOver(int x,int y,int player){
    	String bomb;
    	if(player==2){
    		bomb="O";
    	}else
    		bomb="X";
    	
    	//System.out.println("player : "+ player + "\n X : " + x + " Y " + y);
    	
    	if(x==0 && y==0){
    		if((board[1][0]==bomb && board[0][1]==bomb)||(board[1][0]==String.format("s", player) && board[0][1]==bomb)||(board[1][0]==bomb && board[0][1]==String.format("s", player)))
    			return true;					
    	}else if(x==6 && y==0){
    		if(board[1][6]==bomb && board[0][5]==bomb||(board[1][6]==String.format("s", player) && board[0][5]==bomb)||(board[1][6]==bomb && board[0][5]==String.format("s", player)))
    			return true;
    	}else if(x==6 && y==6){
    		if(board[5][6]==bomb && board[6][5]==bomb||(board[5][6]==String.format("s", player) && board[6][5]==bomb)||(board[5][6]==bomb && board[6][5]==String.format("s", player)))
    			return true;
    	}else if(x==0 && y==6){
    		if(board[5][0]==bomb && board[6][1]==bomb||(board[5][0]==String.format("s", player) && board[6][1]==bomb)||(board[5][0]==bomb && board[6][1]==String.format("s", player)))
    			return true;
    	}else if(x==0){
    		if((board[y-1][0]=="bomb"||board[y-1][0]==String.format("s", player))||(board[y][1]=="bomb"||board[y][1]==String.format("s", player))||(board[y][1]=="bomb"||board[y+1][0]==String.format("s", player)))
    			return true;
    	}else if(x==6){
    		if((board[y-1][6]=="bomb"||board[y-1][6]==String.format("s", player))||(board[y][5]=="bomb"||board[y][5]==String.format("s", player))||(board[y+1][6]=="bomb"||board[y+1][6]==String.format("s", player)))
    			return true;
    	}else if(y==0){
    		if((board[0][x-1]=="bomb"||board[0][x-1]==String.format("s", player))||(board[1][x]=="bomb"||board[1][x]==String.format("s", player))||(board[0][x+1]=="bomb"||board[0][x+1]==String.format("s", player)))
    			return true;
    	}else if(y==6){
    		if((board[6][x-1]=="bomb"||board[6][x-1]==String.format("s", player))||(board[5][x]=="bomb"||board[5][x]==String.format("s", player))||(board[6][x+1]=="bomb"||board[6][x+1]==String.format("s", player)))
    			return true;
    	}else{
    		if((board[y-1][x]=="bomb"||board[y-1][x]==String.format("s", player))||(board[y][x+1]=="bomb"||board[y][x+1]==String.format("s", player))||(board[y][x-1]=="bomb"||board[y][x-1]==String.format("s", player))||(board[y+1][x]=="bomb"||board[y+1][x]==String.format("s", player)))
    			return true;
    	}
    	return false;
    }
    public static int Move(int setting)
    {
    	PrintBoard(board);
        
        int newPlace_X,newP_Y;
        char newPlace_Y,charnewPlace_X;
        if(gameOver(Place_X1,Place_Y1,2)){
        	System.out.println("You Lose !");
        	return 0;
        }
        if(setting==0){
        	System.out.println("You can move one space!");
        	System.out.print("Enter move like 'b3' : ");
        }
        else if(setting==1){
        	 System.out.println("You can delete one space!");
        	 System.out.print("Enter delete like 'b3' : ");
        }
        newPlace = new Scanner(System.in);
        String newPlace_XY = newPlace.nextLine();
        
        if(newPlace_XY.length() != 2)
        {
        	System.out.println("Wrong size input , Enter again !");
        	Move(setting);
        	return 1;  
        }
        else{
        	String[] splitNewPlace_XY = newPlace_XY.split("");
        	       
            newPlace_Y = splitNewPlace_XY[0].charAt(0);
            charnewPlace_X = splitNewPlace_XY[1].charAt(0);
            
            if(Character.isLetter(newPlace_Y) && Character.isDigit(charnewPlace_X)){
            	
            	newPlace_X = Integer.parseInt(splitNewPlace_XY[1])-1;
                
                newP_Y = Character.getNumericValue(newPlace_Y)-10;
                
                if(setting==0){//Move player
                	
                	if(0<=newP_Y &&newP_Y<=6 && newPlace_X<=6 && 0<=newPlace_X && (board[newP_Y][newPlace_X]=="-"||board[newP_Y][newPlace_X]=="X")){
                		
	                	if((Math.abs(Place_X1-newPlace_X)==0 && Math.abs(Place_Y1-newP_Y)==1)||(Math.abs(Place_X1-newPlace_X)==1 && Math.abs(Place_Y1-newP_Y)==0)){
	        	        	board[newP_Y][newPlace_X] = "1";   
	        	            board[Place_Y1][Place_X1] = "-";
	        	            exX1=Place_X1;
	        	            exY1=Place_Y1;
	        	            Place_X1 = newPlace_X;
	        	            Place_Y1 = newP_Y;
	        	            return 1;
	        	        }else {
	        	        	System.out.println("You can move up/down/right or left one time! , Enter again !");
	        	        	Move(setting);
	        	        	return 1;
	        	        }
	                }else{
	                	System.out.println("Out of board or the area is full ! , Enter again !");
	                	Move(setting);
	    	        	return 1;
	                }
                	
                }else if(setting==1){//Delete a area
                	
                	if(0<=newP_Y &&newP_Y<=6 && newPlace_X<=6 && 0<=newPlace_X && board[newP_Y][newPlace_X]=="-"){
    		        	
    		        	if(Main.exX1==newPlace_X && newP_Y==Main.exY1){
    		        		System.out.println("You can not remove last moved point!, Enter again !");
    		        		Move(setting);
    			        	return 1;
    		        	}else{
    		        		//System.out.println("newP_Y : " + newP_Y + " newPlace_X : " + newPlace_X +" exX : " + Main.exX1 + " exY : " + Main.exY1);
    			        	board[newP_Y][newPlace_X] = "X";
    		        	}	
    		        }else{
    		        	System.out.println("Out of board or the area is full ! , Enter again !");
    		        	Move(setting);
    		        	return 1;
    		        }
                }
		                        
            }else{
            	System.out.println("Wrong input ! Enter again !");
            	Move(setting);
	        	return 1;
            }       
        }
        return 1;
    }
        
}