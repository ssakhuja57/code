import java.io.BufferedWriter;

import java.io.File;

import java.io.FileWriter;

import java.io.IOException;





public class ExtractDirMeta {



	private String targetFile;

	

	private ExtractDirMeta(String targetFile){

		this.targetFile = targetFile;

	}

	

	private void extract(String rootDir){

        File root = new File(rootDir);

        File[] list = root.listFiles();



        if (list == null) return;

        

		FileWriter fw;

		BufferedWriter bw;

        

		try {

			

			fw = new FileWriter(this.targetFile, true);

			bw = new BufferedWriter(fw);

        

	        for ( File f : list ) {

	            if ( f.isDirectory() ) {

	                extract( f.getAbsolutePath() );

	            }

	            else {

	            	String path = f.getParent();

	            	String name = f.getName();
	            	
	            	String ext = "none";
	            	
	            	int i = name.lastIndexOf('.');
	            	if (i > 0) {
	            	    ext = name.substring(i+1);
	            	}

	                bw.write(path + "\t" + name + "\t" + ext);

	                bw.newLine();

	            }

	        }

        

	        bw.close();

		}

		catch (IOException e) {

			e.printStackTrace();

		}

	}

	

	public static void main(String[] args) {

		try {

			ExtractDirMeta extractor = new ExtractDirMeta(args[0]);

			extractor.extract(args[1]);

		}

		catch (ArrayIndexOutOfBoundsException e){

			System.out.println("Two arguments are taken, target file to which metadata will be printed" +

					" and source root folder from which metadata will be extracted.\n\n" +

					"Example: \"C:\\test\\file.txt\" \"C:\\root directory\" \n\n");

		}



	}



}