package com.geekband.snap.moran.engine;


import android.util.Log;

import com.geekband.snap.moran.model.Node;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeEngine {
    private static final String TAG = "网络请求路径";
    public static List<Node> getNodes(String path,TokenEngine.TokenInfo tokenInfo){
        Map<String,String> params = new HashMap<>();
        params.put("token",tokenInfo.token);
        params.put("user_id",tokenInfo.user_id);
        params.put("distance","500");
        params.put("longitude","121.47749");
        params.put("latitude","31.22516");

        try {
            return doGETRequest(path,params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<Node> doGETRequest(String path, Map<String, String> params) throws Exception {
        StringBuffer urlBuilder = new StringBuffer(path);
        urlBuilder.append("?");
        for(Map.Entry<String,String> entry : params.entrySet()){
            urlBuilder.append(entry.getKey()).append("=");
            urlBuilder.append(entry.getValue()).append("&");
        }
        urlBuilder.deleteCharAt(urlBuilder.length() - 1);
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        Log.i(TAG,String.valueOf(url));
        if (conn.getResponseCode() == 200){
 //           return parseJSON(conn.getInputStream());
        }else {
            //TODO
        }
        return null;
    }

}
