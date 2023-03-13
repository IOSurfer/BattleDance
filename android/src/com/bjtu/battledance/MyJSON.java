package com.bjtu.battledance;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

public class MyJSON {
    private String Level;
    private String Isfinish;
    private String Score;

    public MyJSON(String level, String isfinish, String score) {
        Level = level;
        Isfinish = isfinish;
        Score = score;
    }

    public MyJSON() {
    }

    public void setLevel(String level) {
        this.Level = level;
    }

    public void setIsfinish(String isfinish) {
        this.Isfinish = isfinish;
    }

    public void setScore(String score) {
        this.Score = score;
    }

    public String getLevel() {
        return Level;
    }

    public String getIsfinish() {
        return Isfinish;
    }

    public String getScore() {
        return Score;
    }

    static private ArrayList<MyJSON> parseJSONWithJSONObject(String jsonData) {
        ArrayList<MyJSON> Array_level = new ArrayList<MyJSON>();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                MyJSON myJSON = new MyJSON();
                myJSON.setLevel(jsonObject.getString("Level"));
                myJSON.setIsfinish(jsonObject.getString("Isfinish"));
                myJSON.setScore(jsonObject.getString("Score"));
                Array_level.add(myJSON);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Array_level;
    }

    static private boolean saveToLocal(ArrayList<MyJSON> Array_level) {
        String path = "";//文件保存路径
        String filename = "LevelSave.json";

        String fileoutput = "[\n";
        for (int i = 0; i < Array_level.size(); i++) {
            MyJSON myJSON = Array_level.get(i);
            fileoutput = fileoutput
                    + "{ \"Level\":\"" + myJSON.getLevel() + "\""
                    + " , \"Isfinish\":\"" + myJSON.getIsfinish() + "\""
                    + " , \"Score\":\"" + myJSON.getScore() + "\" }\n";
        }
        fileoutput = fileoutput + "]";

        try {
            File dirFile = new File(path);
            File file = new File(path + filename);

            if (!dirFile.exists()) {
                dirFile.mkdirs();
                try {
                    file.createNewFile();
                } catch (Exception e) {
                    Log.e("CreateFile", "------创建异常------");
                    return false;
                }
            }

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(path + filename)));
            bufferedWriter.write(fileoutput);
            bufferedWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}

