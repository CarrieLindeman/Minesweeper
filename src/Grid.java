import java.util.Random;
/**
 * Grid class for a single instance of a game of minesweeper
 * @author carriellindeman
 *5/4/2014
 */


public class Grid {

	//get input from user and translate into squares
	protected Square[][] myGrid;
	protected int w;
	protected int h;
	protected int x;//of current square
	protected int y;//of current square
	protected int initX;
	protected int initY;
	protected int numMines;
	protected int numFlag = 0;
	protected boolean noMinesClicked = true;
	protected Random randObject = new Random();	
	
	public Grid(int W, int H, int numberOfMines){
		this.w=W;
		this.h=H;
		this.numMines = numberOfMines;
		this.numFlag=numberOfMines;
		this.myGrid = new Square[this.w][this.h];

		StdDraw.setCanvasSize(500,500);
		StdDraw.setXscale(0,W);
		StdDraw.setYscale(0,H);
		for(int x =0;x<this.w;x++){
			for(int y =0;y<this.h;y++){
				this.myGrid[x][y]= new Square(x,y,W,H);//fills entire grid with square instances
			}
		}
        run();// calls run
	}
	/**
	 * calculates number of flags and prints at top of screen
	 */
	public void topOfScreen(){
		this.numFlag=this.numMines;
		//iterates through grid and calcs num of flags
		for(int i = 0;i<this.w;i++){
			for(int j = 0;j<this.h;j++){
				if(myGrid[i][j].getIfFlag()==true){
					this.numFlag--;
				}
			}
		}
		//printing
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledRectangle(0, this.h+.5, this.w, .59);
		StdDraw.setPenColor(StdDraw.BLACK);
		String s = "Flags: "+this.numFlag;
		StdDraw.text(this.w/2, this.h+(h/22), s);
	}
	/*
	 * sets mines in random places of the grid
	 */
	public void makeMines(){
		//uses num mines and randomly fills spots in
		//avoid init x and init y
		int tempNumMines = this.numMines;
		while(tempNumMines>0){
				int i = randObject.nextInt(this.w);
				int j = randObject.nextInt(this.h);

				if((myGrid[i][j].isMine == false)){//sets mine in square only if not already a mine
					myGrid[i][j].setMine(true);
					//System.out.println(i+" "+j);
					tempNumMines--;
				}
		}
		
	}
	/**
	 * reveals and updates all mines in grid
	 */
	public void revealMines(){
		for(int i = 0; i <this.w;i++){
			for(int j = 0;j<this.h;j++){
				if(myGrid[i][j].isMine==true){
					myGrid[i][j].setShow(true);
					myGrid[i][j].update();
				}
			}
		}
	}
	/**
	 * gets first click and sets initial coordinates
	 * @return whether click happened
	 */
	public boolean initialClick(){
		boolean clickHappened = false;
		if(StdDraw.mousePressed()==true){
			this.initX=(int) StdDraw.mouseX();
			this.initY=(int) StdDraw.mouseY();
			clickHappened = true;	
		}
		
		return clickHappened;
	}
	/**
	 * Chooses an action for current square
	 */
	public void currentSquareAction(){
		//ends if a mine is clicked
		if((myGrid[this.x][this.y].getIfMine()==true)&&(myGrid[this.x][this.y].getIfFlag()==false)){
			revealMines();
			this.noMinesClicked=false;
		}else {
			int tx = myGrid[this.x][this.y].getX();
			int ty = myGrid[this.x][this.y].getY();
			
			recurseCheck(tx,ty);
		}
	}
	/**
	 * totals the number of mines each square touches and assigns those values
	 */
	public void totalNumTouch(){
		
		for(int i = 0; i <this.w;i++){//iterate thru
			for(int j = 0; j<this.h;j++){
				try{
					if(myGrid[i-1][j].getIfMine()){ // west
						myGrid[i][j].addTouch();
					}	
				} catch (ArrayIndexOutOfBoundsException e){}
				try{
					if(myGrid[i][j-1].getIfMine()){ // south
						myGrid[i][j].addTouch();
					}
				}catch (ArrayIndexOutOfBoundsException e){}
				try{
					if(myGrid[i+1][j].getIfMine()){ // east
						myGrid[i][j].addTouch();
					}	
				}catch (ArrayIndexOutOfBoundsException e){}
				try{
					if(myGrid[i][j+1].getIfMine()){ // north
						myGrid[i][j].addTouch();
					}
				}catch (ArrayIndexOutOfBoundsException e){}
				try{
					if(myGrid[i+1][j+1].getIfMine()){ // NE
						myGrid[i][j].addTouch();
					}
				}catch (ArrayIndexOutOfBoundsException e){}
				try{
					if(myGrid[i+1][j-1].getIfMine()){ // SE
						myGrid[i][j].addTouch();
					}
				}catch (ArrayIndexOutOfBoundsException e){}
				try{
					if(myGrid[i-1][j-1].getIfMine()){ // SW
						myGrid[i][j].addTouch();
					}
				}catch (ArrayIndexOutOfBoundsException e){}
				try{
					if(myGrid[i-1][j+1].getIfMine()){ // NW
						myGrid[i][j].addTouch();
					}
				}catch (ArrayIndexOutOfBoundsException e){}
			}
		}
		//option to print a suedo grid of nums
//		for(int i = 0; i < this.w;i++){
//			for(int j = 0;j<this.h;j++){
//				System.out.print(myGrid[i][j].getNumT()+"\t");
//			}
//			System.out.print("\n");
//		}
		
	}
	/**
	 * RECURSIVELY CHECKS SURROUNDING SQUARES AND UPDATES THEM
	 * @param xL int x coord
	 * @param yL int y coord
	 */
	public void recurseCheck(int xL, int yL){
		
		if((myGrid[xL][yL].getNumT()>0)){//if touching mine
			myGrid[xL][yL].setStop(true);//say it has been checked
			myGrid[xL][yL].setShow(true);//show the square
			myGrid[xL][yL].update();//redraw
		
			return;//move on
		}else if(myGrid[xL][yL].getStop()==false){//if not been checked
			myGrid[xL][yL].setStop(true);//make checked
			myGrid[xL][yL].setShow(true);//show the square
			myGrid[xL][yL].update();//redraw
		}else{
			return;//move on
		}
		try{
				recurseCheck(xL,yL+1);//N
		} catch (ArrayIndexOutOfBoundsException e){
			myGrid[xL][yL].setStop(true);//edge block!!
			}
		try{
				recurseCheck(xL, yL-1);//S
		} catch (ArrayIndexOutOfBoundsException e){
			myGrid[xL][yL].setStop(true);//edge block!!
			}
		try{
				recurseCheck(xL+1,yL);//E
		} catch (ArrayIndexOutOfBoundsException e){
			myGrid[xL][yL].setStop(true);//edge block!!
			}
		try{
			recurseCheck(xL-1,yL);//W
		} catch (ArrayIndexOutOfBoundsException e){
			myGrid[xL][yL].setStop(true);//edge block!!
			}
		try{
			recurseCheck(xL+1,yL+1);//NE
		} catch (ArrayIndexOutOfBoundsException e){
			myGrid[xL][yL].setStop(true);//edge block!!
			}
		try{
			recurseCheck(xL-1,yL+1);//NW
		} catch (ArrayIndexOutOfBoundsException e){
			myGrid[xL][yL].setStop(true);//edge block!!
			}
		try{
				recurseCheck(xL+1,yL-1);//SE
		} catch (ArrayIndexOutOfBoundsException e){
			myGrid[xL][yL].setStop(true);//edge block!!
			}
		try{
				recurseCheck(xL-1,yL-1) ;//SW
		} catch (ArrayIndexOutOfBoundsException e){
			myGrid[xL][yL].setStop(true);//edge block!!
			}
	}
	/**
	 * tells if the game is still in progress
	 * @return true won. false still playing
	 */
	public boolean checkIfWinning(){
		boolean hasWon = false;
		for(int i = 0;i<this.w;i++){
			for(int j = 0;j<this.h;j++){
				if((myGrid[i][j].getIfMine()==false)&&(myGrid[i][j].getIfShow()==false)){
					return false;
				}
			}
		}
		hasWon = true;
		return hasWon;
	}
	/**
	 * handles if the first click is mine
	 */
	public void initSquare(){
		//INITIAL SQUARE STUFF
		if(myGrid[initX][initY].getIfMine()==true){
			myGrid[initX][initY].setMine(false);//removes mine from initX and initY
			while (true){
				int rX = randObject.nextInt(this.w);//picks random x
				int rY = randObject.nextInt(this.h);//picks random y
				if((myGrid[rX][rY].getIfMine()==false)&&(rX!=this.initX)&&(rY!=this.initY)){
					//if it is not the orig square and it is not already a mine
					myGrid[rX][rY].setMine(true);//set mine
					myGrid[rX][rY].clearNumT();//clear number of mines it is touching
					myGrid[rX][rY].setShow(false);//hide it
					break;
						}
				}				
			}//END OF INITIAL STUFF
	
	}
	/**
	 * runs functions of Grid for one game
	 */
	public void run(){
		makeMines();
		boolean firstClick = false;
		while(firstClick==false){
		firstClick = initialClick();
		}//while first click end
		initSquare();
			totalNumTouch();
					myGrid[this.initX][this.initY].setShow(true);//shows first square
					myGrid[this.initX][this.initY].update();//shows first square
				
			recurseCheck(this.initX,this.initY);
			//END OF INITIAL SQUARE STUFF
			
		boolean clickOn =false;
		while (true){
			//mouse logic so there are not a bunch of rapid clicks
			if(StdDraw.isKeyPressed(70)){//flagging a square
				this.x=(int) StdDraw.mouseX();
				this.y=(int) StdDraw.mouseY();
					myGrid[this.x][this.y].setFlag();
					myGrid[this.x][this.y].update();
					topOfScreen();
			}else if((StdDraw.mousePressed()) && (clickOn == false)){//clicking a square
				clickOn = true;
			    this.x=(int) StdDraw.mouseX();
				this.y=(int) StdDraw.mouseY();
				myGrid[this.x][this.y].setShow(true);
				currentSquareAction();//checks what action to do
			 } else if (StdDraw.mousePressed() == false && clickOn == true) {
			   clickOn = false;
			 }
			
			if(checkIfWinning()){
				System.out.println("YOU'VE WON!!!");
				break;
			}
			if(noMinesClicked==false){
				System.out.println("YOU'VE LOST!!!");
				break;
			}
		}//END OF WHILE TRUE
			
	}
	
}
