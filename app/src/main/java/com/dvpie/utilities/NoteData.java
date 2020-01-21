package com.dvpie.utilities;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

public class NoteData {
    private String Header, Body, Tags, Description;
    private Calendar date_created;
    private final static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/utilities/notes/";


    public NoteData() {
        this.Header = "header:{Untitled%20Note}";
        this.Body = "body:{}";
        this.Tags = "tags:{}";
    }

    public void saveHeader(String content) {
        String h = "header:{";
        content = formatData(content);

        h += content;
        h += "}";

        this.Header = h;
    }

    public void saveBody(String content) {
        String b = "body:{";
        content = formatData(content);
        b += content;
        b += "}";

        this.Body = b;
    }

    public void saveTags(String tags) {
        String t = "tags:{" + tags + "}";

        this.Tags = t;
    }

    public void saveNote(Context mContext) {
        String save = this.Header + this.Body + this.Tags;
        try {
            new File(path).mkdir();
            File file = new File(path + this.Header + ".txt");
            if(!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file, true);
            fos.write((save + System.getProperty("line.separator")).getBytes());
        } catch(FileNotFoundException e) {
            Log.d(NoteData.class.getName(), e.getMessage());
        } catch(IOException e) {
            Log.d(NoteData.class.getName(), e.getMessage());
        }
    }

    public void ReadFile(Context mContext) {
        String line = null;
        try {
            FileInputStream fis = new FileInputStream(new File(path + this.Header + ".txt"));
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder data = new StringBuilder();

            while( (line = br.readLine()) != null)
            {
                data.append(line + System.getProperty("line.separator"));
            }
            fis.close();
            line = data.toString();
            br.close();
        } catch(FileNotFoundException e) {
            Log.d(NoteData.class.getName(), e.getMessage());
        } catch(IOException e) {
            Log.d(NoteData.class.getName(), e.getMessage());
        }
        parseData(line);

    }

    private void parseData(String data) {
        if(data == null) {

        } else {
            //Get Header
            String head = "";
            int headPos = data.indexOf("header:{") + 8;
            head = data.substring(headPos);
            int headEnd = data.indexOf("}");
            head = head.substring(0,headEnd);
            this.Header = "header:{" + head + "}";

            String body = "";
            int bodyPos = data.indexOf("body:{") + 7;
            body = data.substring(bodyPos);
            int bodyEnd = body.indexOf("}");
            body = body.substring(0,bodyEnd);
            this.Body = "body:{" + body + "}";

            int tagPos = data.indexOf("tags:{");
            this.Tags = data.substring(tagPos);
        }
    }

    public String getHeader() {
        return this.Header;
    }
    public String getBody() {
        return this.Body;
    }
    public String getTags() {
        return this.Tags;
    }
    public String read(String data) {
        String newData;

        newData = data;
        if(newData.contains("%20"))
            newData = newData.replaceAll("%20"," ");
        if(newData.contains("&&OCB"))
            newData = newData.replaceAll("&&OCB","{");
        if(newData.contains("&&CCB"))
            newData = newData.replaceAll("&&CCB","}");

        return newData;
    }
    private String formatData(String data) {
        @SuppressWarnings("all")
        String OCB = new String("{");
        @SuppressWarnings("all")
        String CCB = new String("}");
        if(data.contains(" "))
            data = data.replaceAll(" ", "%20");
        if(data.contains("{"))
            data = data.replaceAll(OCB, "&&OCB");
        if(data.contains("}"))
            data = data.replaceAll(CCB, "&&CCB");

        return data;

    }
}
