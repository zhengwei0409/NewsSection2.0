package com.mycompany.newssection;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class NewsSection {

    public static void main(String[] args) {
        
        // create an 2D arrayList for storing the news (title + url + date)
        // make it global
        ArrayList<ArrayList<String>> newsList = new ArrayList<ArrayList<String>>();
        
        
        // try catch block use to handle exception
        try {
            // create a file object
            File newsFile = new File("NewsSample.txt");
            // create a scanner object
            Scanner sc = new Scanner(newsFile);
            // define variable for counting the number of news (for basic feature)
            // int cnt = 0;

            while(sc.hasNextLine()) {

                // create a temporary ArrayList for storing news
                ArrayList<String> news = new ArrayList<>();

                // scan through each line of NewsSample.txt
                for(int i = 0; i< 4; i++) {
                    if (sc.hasNextLine()) {
                        news.add(sc.nextLine());  
                    }
                }

                String title = news.get(0);

                // add the news that contain "nature" to the 2D array
                if(title.toLowerCase().contains("nature")) {
                    newsList.add(news);
                }
            }

            // sort the news from latest to oldest
            sortNews(newsList);

            // ----------display the news (basic feature)----------
            /*
            System.out.println("Top 5 News about Nature");
            for(int i = 0; i < newsList.size(); i++) {
                cnt++;
                System.out.print("[" + cnt + "]");
                System.out.println(" " + newsList.get(i).get(0));
                System.out.println("    " + newsList.get(i).get(1));
                System.out.println("    " + newsList.get(i).get(2)); 
                System.out.println();             
            }
            */

            sc.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        // ---------GUI Part-----------
        
        // create a frame
        JFrame frame = new JFrame();
        
        JEditorPane title = new JEditorPane("text/html","<html>"+"<body style='font-family: tahoma, sans-serif; font-size: 30pt; margin: 10px;'>" + "<b>Top 5 News about Nature</b>" + "</body>" + "</html>");
        frame.add(title);
        
        //Display the news in the frame
        displayNews(newsList, frame);
        
        // designing the frame
        frame.setTitle("NewsSection");
        frame.setSize(760,700);
        frame.setLayout(new FlowLayout(FlowLayout.LEFT, 20,20));
        frame.getContentPane().setBackground(new Color(255,255,255));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // define a sorting method
    public static void sortNews(ArrayList<ArrayList<String>> arr) {

        // create an ArrayList for storing dates
        ArrayList<Date> datesList = new ArrayList<Date>();
        Date date;
        SimpleDateFormat formatDate = new SimpleDateFormat("dd MMM yyyy");

        try {

            // parse the string into date and store it into ArrayList
            for(int i = 0; i < arr.size(); i++) {
            date = formatDate.parse(arr.get(i).get(2));
            datesList.add(date);
            }

            // Using buble sort to sort the news from latest to oldest
            for(int i = 0; i < arr.size() - 1; i++) {
                for(int j = 0; j < arr.size() - i - 1; j++) {
                    if(datesList.get(j+1).after(datesList.get(j))) {
                        ArrayList<Date> datesListTemp = new ArrayList<Date>();
                        datesListTemp.add(datesList.get(j));
                        datesList.set(j, datesList.get(j+1));
                        datesList.set(j+1, datesListTemp.get(0));
                        ArrayList<ArrayList<String>> arrTemp = new ArrayList<ArrayList<String>>();
                        arrTemp.add(arr.get(j));
                        arr.set(j, arr.get(j+1));
                        arr.set(j+1,arrTemp.get(0));
                    }
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    
    // define a displayNews method
    public static void displayNews(ArrayList<ArrayList<String>> newsList, JFrame frame) {
        // count the number of news
        int count = 1;
        
        // display all the news inside the newsList
        for(int i = 0; i < newsList.size(); i++) {
            JEditorPane textArea = new JEditorPane("text/html","<html>"+ "<body style='font-family: tahoma, sans-serif; font-size: 15pt; margin: 10px;'>" + 
            "<b>[" + count + "]&nbsp</b>" + 
            "<b>"+ newsList.get(i).get(0) + "</b><br>" +
            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\""+ newsList.get(i).get(1) + "\">Read more</a><br>" +
            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + newsList.get(i).get(2) + "<br>" +
            "</body>" + "</html>");
            
            textArea.setEditable(false);
            count++;
            frame.add(textArea);  
            
            // add a hyperlinkListener so that the link can be click and direct to the url given
            textArea.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    try {
                        // Open the link in the default browser
                        Desktop.getDesktop().browse(new URI(e.getURL().toString()));
                    } catch (IOException | URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            });
        }
    }
}
    

