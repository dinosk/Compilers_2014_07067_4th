/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import syntaxtree.*;
import visitor.*;

import java.io.*;
import java.util.*;
/**
 *
 * @author dinossimpson
 */
public class Main {
    public static void main(String[] args){
        for(String filename : args){
            try{
                File file = new File(filename+".spg");
                if ( !file.exists() ) 
                  file.createNewFile();
                else {
                    boolean success = file.delete();
                    if (!success)
                        throw new IllegalArgumentException("Delete: deletion of older file failed");
                    file.createNewFile();
                }
                  
                FileWriter fstream = new FileWriter(filename+".spg");
                BufferedWriter out = new BufferedWriter(fstream);
                PigletParser parser = new PigletParser( new FileReader(filename) );

                HashMap<String, Integer> tempCount = new HashMap<String, Integer>();
                Goal root = parser.Goal();
                root.accept( new TempCounting(tempCount,filename), filename );
                for(String procName : tempCount.keySet()){
                    // System.out.println(procName+" max: "+tempCount.get(procName));
                }
                root.accept( new SpigletVisitor(tempCount,out), filename );
            }
            catch(Exception e){
                System.out.println(e.toString());
            }
        }
    }
}
