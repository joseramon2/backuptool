/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;



import Model.ModelConnectionSSH;
import View.MainView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.logging.Level;
import java.util.logging.Logger;

import tool.nomaven.ToolNoMaven;

/**
 *
 * @author jmartin91
 */
public class ControllerConnection implements ActionListener
{
    private ModelConnectionSSH Conn = null;
    private MainView Vi = null;
    
    public boolean isOpen=false;
    public ControllerConnection(ModelConnectionSSH C, MainView v)
    {
        this.Conn =  C;
        this.Vi = v;
        this.Vi.btnSend.addActionListener(this);
        //this.Vi.btnClose.doClick();
        this.Vi.btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseCon();
            }
        });
        
        this.Vi.btnConnect.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ez) {
                getConnection();
            }
        });
    }
    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        this.Conn.close();
    } 
    
    public void getConnection()
    {
        //System.out.println("serverName: "+this.Vi.getServerName());
        String s2 = (this.Vi.getServerName()=="") ? "lel" : this.Vi.getServerName();
        //System.out.println(s2);
        this.Conn.openConnection(s2,22,"che","jose_21",120000);
                
        try {
            //Thread.sleep(2500);
            //this.Conn.sendCommand("/opt/omni/bin/omnidb -ses -data "+ "\"B1_G9_IBM_APP2_FI\"" +" -last 45 \n\r");
            //this.Conn.sendCommand("uname -a  \n");
            Thread.sleep(1500);
            String s = this.Conn.recvData();
            System.out.println("Result2: \n"+ s);
            this.Vi.txtOutput.setText(s);
        } catch (InterruptedException ex) {
            Logger.getLogger(ControllerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
                
                
                //closing connection
                //this.Conn.close();     
    }
    
    public void actionPerformed(ActionEvent e)
    {
        //System.out.println(e.getID());
        if(this.Conn.sendCommand("ls -l \n"))
        {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
                Logger.getLogger(ControllerConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
            String s = this.Conn.recvData();
            System.out.println(s);
            this.Vi.txtOutput.setText(s);
            //ViewgetView_TextArea().setText(this.Conn.recvData());
        }      
    }
    
    public void CloseCon()
    {
        System.out.println("cerrando ando\n");
        this.Conn.close();
    }
}
