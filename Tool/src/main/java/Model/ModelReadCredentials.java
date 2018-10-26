/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author che
 */
public class ModelReadCredentials {
    
    //System.out.println (new File (".").getAbsolutePath ());
    public void ReadFile()
    {
        File f = new File("./Credentials.txt");

        try 
        {
            BufferedReader readFile = new BufferedReader(new FileReader(f));
            String line="";
            String[] credentials = new String[5];
            int i=0;
            do
            {
                line = readFile.readLine();
                credentials[i]=line;
                i++;
            }
            while(line != null);
            String[] s= credentials[0].split(",");
            for(String a : s)
            {
                System.out.println(a);
            }
        } 
        catch (FileNotFoundException ex) {
            Logger.getLogger(ModelReadCredentials.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ModelReadCredentials.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) 
    {
        ModelReadCredentials m = new ModelReadCredentials();
        m.ReadFile();
    }
}
