import java.awt.Color;

/**
 * Instance of a single minesweeper square
 * @author carriellindeman
 *5/04/14
 */
public class Square {
	protected int xCoord;
	protected int yCoord;
	protected int W;
	protected int H;
	protected boolean isFlag=false;
	protected boolean isMine=false;
	protected Color color;
	protected int numMinesTouch=0;
	protected boolean isShown;
	protected boolean isStop = false;
	
	public Square(int x, int y,int w, int h){//initialize instance of a square
		this.xCoord=x;
		this.yCoord=y;
		this.W=w;
		this.H=h;
		this.isShown=false;
		this.color = Color.BLUE;
		StdDraw.setPenColor(this.color);
		StdDraw.filledSquare(this.xCoord+.45, this.yCoord+.45, .45);
	}
	/**
	 * gets if this square has been recursively checked
	 * @return boolean checked or not
	 */
	public boolean getStop(){
		return this.isStop;
	}
	/**
	 * sets whether a square has been recursively checked or not
	 * @param a checked or not
	 */
	public void setStop(boolean a){
		this.isStop = a;
	}
	/**
	 * draws a square a based on the instances color
	 */
	public void redrawSquare(){
		StdDraw.setPenColor(this.color);
		StdDraw.filledSquare(this.xCoord+.45, this.yCoord+.45, .45);
	}
	/**
	 * adds to the numbers of mines a square is touching
	 */
	public void addTouch(){
		this.numMinesTouch=this.numMinesTouch+1;
	}
	/**
	 * resets number of mines one square is touching
	 */
	public void clearNumT(){
		this.numMinesTouch=0;
	}
	/**
	 * returns number of mines a square is touching
	 * @return int number of mines a square is touching
	 */
	public int getNumT(){
		return this.numMinesTouch;
	}
	/**
	 * returns if a square is a mine
	 * @return true is a mine
	 */
	public boolean getIfMine(){
		return this.isMine;
	}
	/**
	 * returns x
	 * @return int x coord
	 */
	public int getX(){
		return this.xCoord;
	}
	/**
	 * returns y
	 * @return int y coord
	 */
	public int getY(){
		return this.yCoord;
	}
	/**
	 * sets a square to a flag
	 */
	public void setFlag(){
		this.isFlag=true;
	}
	/**
	 * change if mine
	 * @param tOf boolean true mine false not mine
	 */
	public void setMine(boolean tOf){
		this.isMine=tOf;
	}
	/**
	 * change if shows
	 * @param isOrIsNot boolean showing
	 */
	public void setShow(boolean isOrIsNot){
		this.isShown=isOrIsNot;
	}
	/**
	 * returns if the square should be showing
	 * @return boolean if it is showing or not
	 */
	public boolean getIfShow(){
		return this.isShown;
	}
	/**
	 * returns if it is a flag square
	 * @return boolean if it is a flag
	 */
	public boolean getIfFlag(){
		return this.isFlag;
	}
	/**
	 * actually change the graphic based on an if else nest
	 */
	public void update(){
		if((this.getIfFlag()==true)){//flag
			this.color= StdDraw.PINK;
			this.redrawSquare();
			StdDraw.setPenColor(StdDraw.BLACK);
			String s = "F";
			StdDraw.text(this.xCoord+.5,this.yCoord+.5,s);
			StdDraw.setPenColor(this.color);
		}
		if(this.isShown==true){
			if(this.isMine==true){//mine
				this.color=StdDraw.RED;
				this.redrawSquare();
			} else if(this.numMinesTouch>0){//numberSquare
				this.color=StdDraw.ORANGE;
				this.redrawSquare();
				StdDraw.setPenColor(StdDraw.BLACK);
				String s = "" + this.numMinesTouch;
				StdDraw.text(this.xCoord+.5, this.yCoord+.5, s);
				StdDraw.setPenColor(this.color);
			} else{//blank square but clicked
				this.color=StdDraw.BOOK_LIGHT_BLUE;
				this.redrawSquare();
			}	
			
		}
	}

}
