package file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class CSV {

	public static void writeCSV(String content) throws IOException{
		FileWriter fw = new FileWriter("C:\\users\\Shubham\\desktop\\write_test.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(content);
		bw.close();
	}
	
	public static BufferedReader readCSV(String filename) throws FileNotFoundException{
		FileReader fr = new FileReader(filename);
		BufferedReader br = new BufferedReader(fr);
		return br;
	}

	public static void main(String[] args){
		
		//write
		try {
			writeCSV("this,is,a,test\nto,see,if,new\nlines,work,781,781");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//read
		try {
			BufferedReader br = readCSV("C:\\users\\Shubham\\desktop\\write_test.txt");
			while (br.ready()){
				String line = br.readLine();
				StringTokenizer tkn = new StringTokenizer(line, ",");
				System.out.println(tkn.nextToken() + " " + tkn.nextToken() + " " + tkn.nextToken() + " " + tkn.nextToken());
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
