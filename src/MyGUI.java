
import java.awt.Frame;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
public class MyGUI  extends JFrame implements ActionListener {
JFrame guiFrame;
JPanel mycontrolpanel;
JButton mybutton,convertbutton,germanbutton,frenchbutton,checklanguage;
JLabel mylabel;
JList mylist;
JProgressBar myprogressbar;
JTextArea  outputtext;
JTextArea inputtextarea;
JScrollPane myscrollpane;
JMenuBar menubar;

public  String selectedfilename_english;
public  String selectedfilename_german;
public  String selectedfilename_french;
Map<String, String> trainingFiles = new HashMap<>();

NaiveBayes nb;

public static String[] readLines(String filename) throws IOException {

    // Reader fileReader = new InputStreamReader(url.openStream(), Charset.forName("UTF-8"));
     //Reader fileReader = new InputStreamReader(null, null);
 	
 	FileReader file= new FileReader(filename);
     List<String> lines;
     try (BufferedReader bufferedReader = new BufferedReader(file)) {
         lines = new ArrayList<>();
         String line;
         while ((line = bufferedReader.readLine()) != null) {
             lines.add(line);
         }
     }
     return lines.toArray(new String[lines.size()]);
 }
 


	public MyGUI (){
		
		//Menu bar
	    menubar= new JMenuBar();
		JMenu file= new JMenu("File");
		JMenuItem eMenuItem= new JMenuItem("New");
		setJMenuBar(menubar);
        menubar.setVisible(true);
        setTitle("Simple menu");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        menubar.setName("Test menu");
        file.add(eMenuItem);
		menubar.add(file);
		
		JMenu editMenu= new JMenu("Edit");
		menubar.add(editMenu);
		JMenuItem copymenuitem= new JMenuItem("Copy");
		editMenu.add(copymenuitem);
		JMenuItem cutmenuitem= new JMenuItem("Cut");
		editMenu.add(cutmenuitem);
		
		
		//Main frame
		 guiFrame= new JFrame();
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Language Detection Software");
        guiFrame.setSize(300,250);
        guiFrame.setVisible(true);
        
               
        
        //Control Panel
        mycontrolpanel=new JPanel();
        mycontrolpanel.setVisible(true);
       
        mybutton= new JButton("Browse English Training File");
        germanbutton=new JButton("Browse German Training File");
       // germanbutton.setAlignmentX(100);
        //germanbutton.setAlignmentY(100);
        germanbutton.setVisible(true);
        frenchbutton= new JButton("Browse Fench Training File");
        mybutton.addActionListener(this);
        //convertbutton.addActionListener(this);
        //convertbutton.addMouseListener();
        convertbutton= new JButton("Train Software");
        checklanguage= new JButton("Check Language");
        mylabel= new JLabel("Hello World");
                
      /*  final DefaultListModel fruitsName = new DefaultListModel();

        fruitsName.addElement("Apple");
        fruitsName.addElement("Grapes");
        fruitsName.addElement("Mango");
        fruitsName.addElement("Peer");
        fruitsName.addElement("Apple");
        fruitsName.addElement("Grapes");
        fruitsName.addElement("Mango");
        fruitsName.addElement("Peer");
        mylist= new JList(fruitsName);
        mylist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mylist.setSelectedIndex(0);
        mylist.setVisibleRowCount(2);
        JScrollPane fruitListScrollPane = new JScrollPane(mylist);    */
       
        //Progress Bar
        myprogressbar= new JProgressBar(0,3);
        myprogressbar.setValue(0);
        
        outputtext= new JTextArea(" " ,5,20);
        inputtextarea= new JTextArea(" ",5,10);
        myscrollpane= new JScrollPane(outputtext);
     
        
        mycontrolpanel.add(mybutton);
        mycontrolpanel.add(convertbutton);
        mycontrolpanel.add(germanbutton);
        mycontrolpanel.add(frenchbutton);
        mycontrolpanel.add(checklanguage);
        // mycontrolpanel.add(mylist);
        mycontrolpanel.add(myprogressbar);
        mycontrolpanel.add(outputtext);
        mycontrolpanel.add(inputtextarea);
        mycontrolpanel.add(myscrollpane);
        mycontrolpanel.add(mylabel);
        
        
        final Task task= new Task();
        
        
        
        germanbutton.addActionListener(new ActionListener()
        {
           public void actionPerformed(ActionEvent event)
           {
        	   JFileChooser chooser = new JFileChooser();
               chooser.showOpenDialog(null);
               File f = chooser.getSelectedFile();
               selectedfilename_german = f.getName();
               // System.out.println("You have chosen: " + filename);
       		   mylabel.setText("Button clicked"+selectedfilename_german);
       	       trainingFiles.put("German", selectedfilename_german);
           }
        });
        
        frenchbutton.addActionListener(new ActionListener()
        {
           public void actionPerformed(ActionEvent event)
           {
        	   JFileChooser chooser = new JFileChooser();
               chooser.showOpenDialog(null);
               File f = chooser.getSelectedFile();
               selectedfilename_french = f.getName();
              // System.out.println("You have chosen: " + filename);
       		   mylabel.setText("Button clicked"+selectedfilename_french);
       		   trainingFiles.put("French", selectedfilename_french);
        	   
           }
        });
        
        
        checklanguage.addActionListener(new ActionListener()
        {
           public void actionPerformed(ActionEvent event)
           {
        	   
        	   String teststring= inputtextarea.toString();
		    
		        if(teststring==null)
		        	JOptionPane.showMessageDialog(guiFrame,"Input String is empty");
		        
		        else{
		           
		     
		        System.out.println(teststring);
		        
		        String testoutput=nb.predict(teststring);
		        outputtext.setText(testoutput);
		       
		        
		        String exampleEn = "I am English";
		        String outputEn = nb.predict(exampleEn);
		        System.out.format("The sentense \"%s\" was classified as \"%s\".%n", exampleEn, outputEn);
		        
		        String exampleFr = "Je suis Français";
		        String outputFr = nb.predict(exampleFr);
		        System.out.format("The sentense \"%s\" was classified as \"%s\".%n", exampleFr, outputFr);
		        
		        String exampleDe = "Ich bin German";
		        String outputDe = nb.predict(exampleDe);
		        System.out.format("The sentense \"%s\" was classified as \"%s\".%n", exampleDe, outputDe);
		        }
        	   
           }
        });
        
        convertbutton.addActionListener(new ActionListener()
        {
           public void actionPerformed(ActionEvent event)
           {
        	   task.start();
              //do the conversion task here
           }
        });
              
        guiFrame.add(mycontrolpanel);       
        //guiFrame.add(menubar);
        guiFrame.setJMenuBar(menubar);
	}
    	
	
	private class Task extends Thread 
	{
		public Task()
		{
			
		}
		public void run() 
		{	//for(int i=0;i<=100;i++)
			{
				int i=0;
			myprogressbar.setValue(i);
		//	outputtext.setText(outputtext.getText()+String.format("completed %d%% of task\n", i));
			  try{
				  //perform your task here
				  Thread.sleep(0);
				//loading examples in memory
			        Map<String, String[]> trainingExamples = new HashMap<>();
			        for(Map.Entry<String, String> entry : trainingFiles.entrySet()) {
			        	try{
			            trainingExamples.put(entry.getKey(), readLines(entry.getValue()));
			            myprogressbar.setValue(i);
						outputtext.setText(outputtext.getText()+String.format("completed %d%% of task\n", i));
						i++;
			        	}catch(IOException e){}
			        	
			        }
			        
			        //train classifier
			       nb = new NaiveBayes();
			        nb.setChisquareCriticalValue(6.63); //0.01 pvalue
			        nb.train(trainingExamples);
			        
			      //get trained classifier knowledgeBase
				       NaiveBayesKnowledgeBase knowledgeBase = nb.getKnowledgeBase();
				        
				        nb = null;
				        trainingExamples = null;
				        
				        
				        //Use classifier
				        nb = new NaiveBayes(knowledgeBase);
				
					  
			  }catch(InterruptedException e){}
			}
		
		}
		
	}
		
	
	public void actionPerformed (ActionEvent event)
	{
		JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        selectedfilename_english = f.getName();
       // System.out.println("You have chosen: " + filename);
		mylabel.setText("Button clicked"+selectedfilename_english);
		trainingFiles.put("English", selectedfilename_english);
		
		
		
		
	}
}//end of the class
