package com.mogui.yufactory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.sm.packet.StreamManagement;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.jid.DomainBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprep;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.jivesoftware.smackx.filetransfer.FileTransfer.Error.connection;

public class YuFactoryConnection extends AppCompatActivity {

//    private static final int packetReplyTimeout = 500; // millis

    private String mServiceName;
    private int mPort;
    private String mUsername;
    private String mPassword;

    private XMPPTCPConnection mConnection;
    //private XMPPTCPConnectionConfiguration config;

    private ChatManager chatManager;
    private MessageListener messageListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String jid = getIntent().getExtras().getString("xmpp_jid");
        mPassword = getIntent().getExtras().getString("xmpp_password");

        if (jid != null) {
            mUsername = jid.split("@")[0];
            mServiceName = jid.split("@")[1];
        } else {
            mUsername = "";
            mServiceName = "";
        }

        Log.d("YuFactoryConnection", "create method was started!");
        Log.d("YuFactoryConnection", "mUserName: " + mUsername);
        Log.d("YuFactoryConnection", "mserviceName: " + mServiceName);
        Log.d("YuFactoryConnection", "password: " + mPassword);


        try {
            this.connect();
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public YuFactoryConnection() { }

    public void connect() throws XmppStringprepException, InterruptedException {
    //        SmackConfiguration.setDefaultPacketReplyTimeout(packetReplyTimeout);

//        InetAddress addr = null;
//
//        // inter your ip4address now checking it
//        try {
//            addr = InetAddress.getByName("192.168.43.83");
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//        DomainBareJid serviceName = null;
//        try {
//            serviceName = JidCreate.domainBareFrom("192.168.43.83");
//        } catch (XmppStringprepException e) {
//            e.printStackTrace();
//        }

        XMPPTCPConnectionConfiguration.Builder config = XMPPTCPConnectionConfiguration
                .builder();
        config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        config.setXmppDomain(mServiceName);
        config.setHost("192.168.43.83");
        config.setPort(5222);
        config.setDebuggerEnabled(true);

        mConnection = new XMPPTCPConnection(config.build());

//        try {
//            mConnection.connect();
//        } catch (SmackException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (XMPPException e) {
//            e.printStackTrace();
//        }
//        try {
//            mConnection.login(mUsername, mPassword);
//        } catch (XMPPException e) {
//            e.printStackTrace();
//        } catch (SmackException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
//                .setXmppDomain("127.0.0.1")
//                .setHost("127.0.0.1")
//                .setPort(5222)
//                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
//                .setDebuggerEnabled(true).build();
//
//        mConnection = new XMPPTCPConnection(config);
//
        try {
            Log.d("YuFactoryConnection", "before connected");
            mConnection.connect();
            Log.d("YuFactoryConnection", "after connected");

            if (mConnection.isConnected()) {
                Log.d("YuFactoryConnection", "run: connected successfully!");
            }
            // all these proceedure also thrown error if you does not seperate this thread now we seperate thread create
            mConnection.login(mUsername, mPassword);

            if (mConnection.isAuthenticated() && mConnection.isConnected()) {
                //now send message and receive message code here

                Log.d("YuFactoryConnection", "run: auth done and connected successfully!");

                //    chatManager = connection.getChatManager();
                //    messageListener = new MyMessageListener();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        }
    }
}
