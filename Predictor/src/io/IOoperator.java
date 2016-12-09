package io;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class IOoperator {
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
