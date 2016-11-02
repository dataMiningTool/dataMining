/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author THEDE
 */
public class IOOperators {
    private PrintWriter writer;
    
    public void setPATH(String path) throws FileNotFoundException, UnsupportedEncodingException{
        writer = new PrintWriter(path, "UTF-8" );
    }
    
    public  void WriteFile(String content){
        writer.println(content);
    }
    public void close(){
        writer.close();
    }
    
}
