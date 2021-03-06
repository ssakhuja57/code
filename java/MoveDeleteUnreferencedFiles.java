import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MoveDeleteUnreferencedFiles {

	
	
	public static void main(String[] args) throws IOException {
		
		System.out.println();
		if (args.length < 3){
        	System.out.println("Usage: specify root vault folder, a substring common in vault folder names that identifies each as a vault folder, "
        			+ "and whether or not this is a Unix system (true or false)");
        	System.out.println("\nExample: java MoveDeleteUnreferencedFiles \"C:/ptc/vaults\" \"defaultmasterfolder\" false");
		System.out.println();
        	System.exit(1);
        }
		
		String vaultRoot = args[0];
        String folderPattern = args[1];
        String is_unix = args[2];
        
        boolean unix = false;
        if (is_unix.equals("true")) unix = true;      
        
	String bash1 = "/bin/bash";
        String bash2 = "-c";
        
        int curr_month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int curr_year = Calendar.getInstance().get(Calendar.YEAR);
        int last_month = curr_month - 1;
        int last_year = curr_year;
        if (last_month==0){
        	last_month = 12;
        	last_year = last_year -1;
        }
        String curr_date = Integer.toString(curr_year) + "_" + Integer.toString(curr_month);
        String last_date = Integer.toString(last_year) + "_" + Integer.toString(last_month);
        
		File root = new File(vaultRoot);
        File[] folders = root.listFiles();
        Pattern p = Pattern.compile(folderPattern);
        File last_dest = new File(vaultRoot + "/unreferenced_" + last_date);
        File curr_dest = new File(vaultRoot + "/unreferenced_" + curr_date);
        
        System.out.println("deleting folder \"" + last_dest.getAbsolutePath() + "\" ...");
        if (last_dest.exists()){
        	if (unix) Runtime.getRuntime().exec(new String[]{bash1, bash2, "rm -rf \"" + last_dest.getAbsolutePath() + "\""});
        	else Runtime.getRuntime().exec("rm -rf " + last_dest.getAbsolutePath());
        }
        else {
		System.out.println("\""+ last_dest.getAbsolutePath() + "\" does not exist, skipping");
	}
        
        if (unix) Runtime.getRuntime().exec(new String[]{bash1, bash2, "mkdir \"" + curr_dest.getAbsolutePath() + "\""});
        else Runtime.getRuntime().exec("mkdir " + "\"" + curr_dest.getAbsolutePath() + "\"");
        System.out.println();
        
        for (File folder: folders){
        	Matcher m = p.matcher(folder.getName());
        	if (m.find()) {
        		File unref = new File(folder.getAbsolutePath() + "/.unreferenced");
        		if (unref.exists()){
        			File target = new File(curr_dest.getAbsolutePath() + "/" + unref.getParentFile().getName() + "_unreferenced");
        			System.out.println("executing command: mv " + "\"" + unref.getAbsolutePath() + "\" " + "\"" + target + "\"");
        			if (unix) Runtime.getRuntime().exec(new String[]{bash1, bash2, "mv \"" + unref.getAbsolutePath() + "\" \"" + target.getAbsolutePath() + "\""});
        			else Runtime.getRuntime().exec("mv " + "\"" + unref.getAbsolutePath() + "\" " + "\"" + target + "\"");
        		}
        		else{
        			System.out.println("\"" + folder.getAbsolutePath() + "\" does not contain a .unreferenced folder, skipping");
        		}
        	}
        }
        
        System.out.println("Finished! Unreferenced files have been moved to \"" + curr_dest.getAbsolutePath() + "\"");
		
	}

}
