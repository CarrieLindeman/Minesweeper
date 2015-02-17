/***
 * This is a Minesweeper Game with three levels
 * @author carriellindeman
 *5/4/14
 */
public class Minesweeper {
	protected Grid game;//initial grid game
	
	/*
	 * initializes a single game
	 */
	public void startGame(){
		printMenu();//prints menu
		int answer = getMenuOption(1,3);//cleaner get menu from auto
		int myX=0;
		int myY=0;
		int myMines=0;
		switch(answer){//switch case for each level
			case 1: 
				myX=9;
				myY=9;
				myMines=10;
				break;
			case 2:
				myX = 16;
				myY = 16;
				myMines = 40;
				break;
			case 3:
				myX = 20;
				myY = 20;
				myMines = 80;
				break;
		}
		game = new Grid(myX,myY,myMines);//creates game instance
		
	}
	/*
	 * prints menu
	 */
	public void printMenu(){
		System.out.print("Welcome to Minesweeper!\n----------------------\nControls:\n");
		System.out.print("Click - Select Square\nF Key - Flag Square\n----------------------\n");
		System.out.print("\t1) Beginner\n\t2) Intermediate\n\t3) Expert\nPlease select a difficulty: ");
	}
	
	/**
	 * gets user input
	 * @param min int user lowest
	 * @param max int user highest
	 * @return user choice
	 */
	public int getMenuOption(int min, int max) {
		
		if (min > max){ throw new IllegalArgumentException();}
		
		int option = min;
		String input;
		while (true){
			try{
			  input = StdIn.readLine();
			  option = Integer.parseInt(input);
			} catch (java.lang.NumberFormatException e){
				System.out.println("(Please enter a valid integer.)");
				continue;
			}
			
			if ( (option >= min) && (option <= max)){ 
				 return option;
			}
			System.out.println("You have not made a valid selection.");
			System.out.print("Please select option between "+min+" and "+max+": ");
		}
	}
	/*
	 * runs through menu selection
	 */
	public void run(){
		String ans ="";
		
		while(!ans.equals("y")&&!ans.equals("n")){
		System.out.print("Would you like to play Minesweeper (y/n): ");//only takes good answers
		ans = StdIn.readString();
		}
		
		if (ans.equals("y")){
			startGame();//calls start game
			ans="";
		}else{
			System.out.println("DONE");//else finish game
			
		}
		
	}

	public static void main(String[] args){
		Minesweeper ms = new Minesweeper();
		while(true){
		ms.run();
		}
	}
}
