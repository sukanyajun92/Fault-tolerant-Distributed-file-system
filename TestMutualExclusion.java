import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.joda.time.*;
import org.joda.time.base.AbstractInterval;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;



public class TestMutualExclusion {
	static Properties propConfig = new Properties();
	public static List<List<LogLine>> LogLineArray =new ArrayList<List<LogLine>>();
	public static Set<String> fileNames=new HashSet<String>();
	public static List<String> nodeNameList=new ArrayList<String>();
	  @Before 
	   public void setUp() {
		try{
		  propConfig.load(new FileInputStream("config.properties"));
		Set<String> nodeNames= propConfig.stringPropertyNames();
		
		nodeNameList.addAll(nodeNames);
	    for(int i=0;i<nodeNameList.size();i++)
	    {
	    	String file="log"+nodeNameList.get(i)+".txt";
	    	LogLineArray.add(readFile(file));
	    }  
		}catch(Exception e)
		{
			//e.printStackTrace();
		}
	   }
	
	 
	  
	public static List<LogLine> readFile(String filename)
	{
		String message="";
		String splitChar="/";
		List<LogLine> fileLines=new ArrayList<LogLine>();
		try
		{
			BufferedReader br=new BufferedReader(new FileReader(filename));
			String line="";

			while((line=br.readLine())!=null)
			{
				String splitLines[]=line.split(splitChar);
				fileLines.add(new LogLine(splitLines[0],splitLines[1], splitLines[2], Long.parseLong(splitLines[3])));
				fileNames.add(splitLines[1]);
				message = message +line + "\n";
			}
			br.close();
			
		}
		catch(IOException ex)
		{
		//	ex.printStackTrace();	
		}	
		
		
		return fileLines;
	}

	public boolean overlap(long startOne,long endOne,long startTwo,long endTwo)
	{
		Interval intervalOne=new Interval(startOne, endOne);
		Interval intervalTwo=new Interval(startTwo, endTwo);
		
		return intervalOne.overlaps(intervalTwo);
		
		
	}
	@Test
	   public void testNode() {
		
		List<String> fileNamesList=new ArrayList<String>();
		fileNamesList.addAll(fileNames);
		
	      for(int j=0;j<LogLineArray.size();j++)
	      {	  
	      boolean flag=true;
	      if(!LogLineArray.get(j).isEmpty())
	      {  
	      List<LogLine> lines= LogLineArray.get(j);
	      List<LogLine> cutLines=new ArrayList<LogLine>();
          Collections.sort(lines);
          for(int k=0;k<fileNames.size();k++)
          {
        	  List<LogLine> distinctLines =new ArrayList<LogLine>();
          
          for(int i=0;i<lines.size();i++)
          {
        //  System.out.println(lines.get(i));
          if(lines.get(i).fileName.contains(fileNamesList.get(k)))
        	  cutLines.add(lines.get(i));	  
          }
          
//          for(int i=0;i<cutLines.size();i++)
//          {
//        	  System.out.println(cutLines.get(i));
//          }
          
	     for(int i=0;i<cutLines.size()-1;i++)
	     {
	    	 if(!(cutLines.get(i).messageId.equals(cutLines.get(i+1).messageId)) && cutLines.get(i).operation.contains("Lock"))
	    	 {
	    		 if(!(cutLines.get(i).operation.contains("read") && cutLines.get(i+1).operation.contains("read")))
	    		 {
	    		 flag=false;
	    		  System.out.println("Failed here: "+ cutLines.get(i));
	    		 }
	    	 }
	     }
	    // int z=j+1;
	     if(flag)
	     {
	    	//System.out.println("Test passed!!!!!! for log file log" + nodeNameList.get(k)+".txt"); 
	     }
	     else{
	     System.out.println("Test Failed!!");
	     
	     System.out.println("Mutual exclusion violated for log file log" + nodeNameList.get(k)+".txt");
	     
	     }
	      //check for false condition
	    
	      assertTrue("Timestamps of individual messages in node :"+j+"  do overlap thus disturbing atomicity of operations",flag);
	      
	   }
	      }
	      }
	
}
}


	

