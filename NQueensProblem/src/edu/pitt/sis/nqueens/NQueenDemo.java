package edu.pitt.sis.nqueens;


import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Places n queens on a n*n board so that no two queens are in the same column, row or diagonal
 * (no queen attacks each other)
 * @author Liu
 *
 */
public class NQueenDemo {
	
	public static BufferedWriter output = null;
	public static Integer[] rows;
	public static ArrayList<Integer[]> results;

	public static void main(String[] args)
	{
		int maxSize = 15;
		
		String path_output = "nQueen_output.txt";	
		
		try {
			// Initiate the output writer ...
			output = new BufferedWriter( new OutputStreamWriter( new FileOutputStream(path_output), "UTF-8" ) );
			
			for(int n = 1; n <= maxSize; n++)
			{
				rows = new Integer[n];
				results = new ArrayList<Integer[]>();
				
				//for calculating running time
				long startTime = System.currentTimeMillis();
				placeQueen(n, 0);
				//calculating running time
				long endTime = System.currentTimeMillis();
				output.write("Board size of " + rows.length + ", number of solutions = " + results.size() + "\n");
				output.write("*Running Time: "+(endTime - startTime) + " milliseconds.\n\n");				
				printBoard();
				System.out.println("Compeleted computing " + n + " queens!");
			}//end of for
		}catch(IOException e){
			System.out.println("ERROR: cannot write solutions to output file.");
			e.printStackTrace();
		}finally {
			if (output != null) { 
			        System.out.println("Closing BufferWriter");
			        try {
						output.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
			    } else { 
			        System.out.println("BufferWriter not open");
			    } 
		}
		
	}//end of main
	
	/**
	 * 
	 * @param n: board size
	 * @param column: the column to place queen
	 * @param rows: the array that stores queen placements
	 * @param results: the ArrayList that stores non-attack placements' results
	 */
	public static void placeQueen(int n, int column)
	{
		if(column == n) //if last column on board, stop condition of recursion
			results.add(rows.clone());
		else
		{
			//attempt to place another queen in this column, try each row one by one
			for(int row = 0; row < n; row++)
			{
				if(nonAttack(column, row)) //if a non-attack placement
				{
					rows[column] = row;
					placeQueen(n, column + 1); //place another queen in the next column
				}//end of if
			}//end of for
		}//end of else	
	}//end of placeQueen
	
	public static boolean nonAttack(int column, int row)
	{
		//check if any attack exist between queen in this column and queens in placed columns
		for(int placedCol = 0; placedCol < column; placedCol++)
		{
			if(rows[placedCol] == row) //if same row
				return false; 
			
			if(column - placedCol == Math.abs(row - rows[placedCol]))
				return false;
		}
		return true;
	}
	
	public static void printBoard() throws IOException
	{
		if(results != null) //if has result
		{
			for(int cnt = 1; cnt <= results.size(); cnt++)
			{
				
					Integer[] rows = results.get(cnt-1);
					output.write("n = " + rows.length + ", solution " + cnt + ":");
					for(int row : rows)
					{
						for(int i = 0; i < rows.length; i++)
						{
							if(i == row)
								output.write("X");
							else
								output.write("-");
						}//end of for1
						output.write("\n");
					}//end of for2			
			}//end of outtest for
		}//end of if
	}//end of printBoard
}
