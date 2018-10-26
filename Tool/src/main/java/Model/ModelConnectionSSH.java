/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author che
 */
import com.jcraft.jsch.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
//Model 1
public class ModelConnectionSSH 
{
    //creating jsch variable
    private JSch mJschSSH = null;
    //creating SSH session
    private Session mSSHSession = null;
    //creating a new channel
    private Channel mSSHChannel = null;
    //creating I/O stream, they will send/receive info
    private InputStream mSSHInput = null; 
    private OutputStream mSSHOutput = null;
    ///////////////////////////////
    private String strHost, strUserName,strPassword;
    private int iPort, iTimeout;
    ///////////////////////////////
    //Creating a new SSH connection
    public void settingsConnection(String strHost, int iPort, String strUserName,
    String strPassword, int iTimeout)
    {
        
    }
    //Creating a new SSH connection
    public boolean openConnection(String strHost, int iPort, String strUserName,
    String strPassword, int iTimeout)
    {
        boolean bResult=false;
        //creating new vale for jsch
        this.mJschSSH = new JSch();
        //java.util.Properties config = new java.util.Properties();
        //config.put("StricHostKeyChecking","no");
        //this.mJschSSH.setConfig(config);
        java.util.Properties config = new java.util.Properties(); 
        config.put("StrictHostKeyChecking", "no");
        config.put("PreferredAuthentications","publickey,keyboard-interactive,password");
        this.mJschSSH.setConfig(config);
        
        //get Session
        try 
        {
            this.mSSHSession = this.mJschSSH.getSession(strUserName,strHost, iPort);
            //set Password
            this.mSSHSession.setPassword(strPassword);
            this.mSSHSession.connect(iTimeout);
            
            //get Channel
            this.mSSHChannel = this.mSSHSession.openChannel("shell");
            
            this.mSSHChannel.connect();
            //////////////
            this.mSSHInput = this.mSSHChannel.getInputStream();
            this.mSSHOutput = this.mSSHChannel.getOutputStream();
            
            bResult = true;
            
        } 
        catch (JSchException | IOException ex) 
        {   //show error with details
            java.util.logging.Logger.getLogger(ModelConnectionSSH.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return bResult;
    }
    
    //creating method to send commands
    public boolean sendCommand(String strCommand)
    {
        boolean bResult = false;
                
        try
        {
            System.out.println(this.mSSHOutput + "\n out");
            if(this.mSSHOutput != null)
            {
                //output stream lets send data
                this.mSSHOutput.write(strCommand.getBytes());
                //clear output stream
                this.mSSHOutput.flush();
                bResult = true;
                //System.out.println("manda comando: "+this.mSSHOutput != null);
            }
            
        } 
        catch (IOException ex) {
            java.util.logging.Logger.getLogger(ModelConnectionSSH.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bResult;
    }
    
    //creating a method that let us receive data
    public String recvData()
    {
        String strData ="";
        try
        {
            if(this.mSSHInput != null )
            {
                
                
             //inhere we can check if there is data or not affter using a command   
                int iAvailable = this.mSSHInput.available();
                
                
                //receive all data
                while(iAvailable > 0)
                {
                    byte[] btBuffer = new byte[iAvailable];
                    //check byte read from input stream
                    int iByteRead = this.mSSHInput.read(btBuffer);
                    //Check byte raed from input stream
                    iAvailable = iAvailable - iByteRead;
                    
                    strData+= new String(btBuffer);
                    strData = strData.replaceAll("]0;", "");
                    strData = strData.replace("", "\n");
                }
            }
                    
        } 
        catch (IOException ex) {
            java.util.logging.Logger.getLogger(ModelConnectionSSH.class.getName()).log(Level.SEVERE, null, ex);
        }
        return strData;
    }
    //Creating close function
    public void close()
    {
        if(this.mSSHSession != null)
            this.mSSHSession.disconnect();
        if(this.mSSHChannel != null)
            this.mSSHChannel.disconnect();
        
        try 
        {
            if(this.mSSHInput != null)
                    this.mSSHInput.close();
            if(this.mSSHOutput != null)
                this.mSSHOutput.close();
        } 
        catch (IOException ex) 
        {
            java.util.logging.Logger.getLogger(ModelConnectionSSH.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.mJschSSH = null;
    }
}