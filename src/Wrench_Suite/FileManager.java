package Wrench_Suite;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileManager {
	public static String destPath;
	public static String filepatherror;
    public boolean copy_file(String sourceFilePath, String destinationFolderPath){
    	boolean bool = true;
    	File sourceFile = new File(sourceFilePath);
    	if(sourceFile.exists() && !sourceFile.isDirectory()) {
    		destPath = destinationFolderPath+"\\"+new SimpleDateFormat("MMM, dd yyyy @ hh.mm.ss").format(new Date())+"_"+ sourceFile.getName();
    		File destinationPathObject = new File(destPath);
    		try {
    			Files.copy(sourceFile.toPath(), destinationPathObject.toPath());
    		} catch (IOException e) {
    			System.out.println("Error: Fail to copy file to destination. "+e.getMessage());
    			bool = false;
    		}
    	}
    	else{
    		bool = false;
    		System.out.println("error: "+"Source file path is not found");
    		filepatherror="Source file path is not found";
    		
    	}
    		return bool; 
    }
	public static String getDestinationPath() {
		return destPath;
	}
}
