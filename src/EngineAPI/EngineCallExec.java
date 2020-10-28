package EngineAPI;

import QlikSenseTicket.Ticket;
import Shared.Interfaces.ChannelListener;
import Shared.QlikAuthCertificate;

import java.io.File;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.Arrays;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class EngineCallExec implements ChannelListener {

    public void callAPI() {

        String txtClientPassword = "midas";
        String txtClientCertPath = "c:\\javaTicket\\client.pfx";
        String txtRootCertPath = "c:\\javaTicket\\root.cer";
        String txtAddress = "wss://qliksensei/midas/app/c9138e09-bdcf-4bc9-8c1c-1f7976b890fa?qlikTicket="+ Ticket.HTTPSCall();

        char[] clientPass = txtClientPassword.toCharArray();

        QlikAuthCertificate cert = new QlikAuthCertificate(txtClientCertPath, clientPass,
                txtRootCertPath);

        QlikWebSocketClient _qlikClient;

        {
            try {
                JSONObject engineAPIResponse = new JSONObject();
                JSONBuilder jsonBuilder = new JSONBuilder();
                _qlikClient = new QlikWebSocketClient(txtAddress, cert);
                System.out.println("Adding current endpoint as listener");
                _qlikClient.addListener(this);
                System.out.println("Trying to connect...");
                _qlikClient.connectBlockingCommand();

                System.out.println("--Sending getDocList to Engine API:\n Response:");
                engineAPIResponse = _qlikClient.sendCommand(jsonBuilder.getDocList().toString());

                System.out.println("--Sending openDoc to Engine API:\n Response:");
                engineAPIResponse = _qlikClient.sendCommand(jsonBuilder.openDoc(
                        engineAPIResponse.getJSONObject("result").getJSONArray("qDocList").getJSONObject(0).getString("qDocId")
                ).toString());

                System.out.println("--Sending getAllInfos to Engine API:\n Response:");
                engineAPIResponse = _qlikClient.sendCommand(jsonBuilder.getAllInfos(
                        engineAPIResponse.getJSONObject("result").getJSONObject("qReturn").getInt("qHandle")
                ).toString());

                System.out.println("--Sending getObject to Engine API:\n Response:");
                engineAPIResponse = _qlikClient.sendCommand(jsonBuilder.getObject(
                        engineAPIResponse.getJSONObject("result").getJSONArray("qInfos").getJSONObject(4).getString("qId")
                ).toString());

                System.out.println("--Sending exportData to Engine API:\n Response:");
                engineAPIResponse = _qlikClient.sendCommand(jsonBuilder.exportData(
                        engineAPIResponse.getJSONObject("result").getJSONObject("qReturn").getInt("qHandle")
                ).toString());

                System.out.println("Desired object export CSV: \nhttps://qliksensei" + engineAPIResponse.getJSONObject("result").getString("qUrl"));

                _qlikClient.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                //System.out.println("Connection could not be established.");
            }
        }
    }


    @Override
    public void responseReceived(String message) {
        System.out.println(message);
    }
}
