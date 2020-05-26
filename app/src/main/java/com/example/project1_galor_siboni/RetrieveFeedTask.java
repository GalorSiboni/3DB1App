package com.example.project1_galor_siboni;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RetrieveFeedTask extends AsyncTask<String , Void, String> {
    private String modifiedSentence = "";


    protected String doInBackground(String... urls) {

        String   message = urls[0];
        Socket clientSocket = null;
        try {
            clientSocket = new Socket("10.0.0.24", 10000);
            Log.d( "startSocket","client socket start" );
            DataOutputStream outToServer =
                    new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer =
                    new BufferedReader( new
                            InputStreamReader( clientSocket.getInputStream() ) );
            Log.d( "writeBytes",message );
            outToServer.writeBytes( message + "\n");
            Log.d( "outToServer",message );

            modifiedSentence = inFromServer.readLine();
            Log.d( "inFromServer",modifiedSentence );

        } catch (IOException e) {
            e.printStackTrace();
        }
        return modifiedSentence;
    }
    @Override
    protected void onPostExecute(String result) {
    }

}



