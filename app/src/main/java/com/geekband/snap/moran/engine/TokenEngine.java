package com.geekband.snap.moran.engine;

import android.content.Context;
import android.content.SharedPreferences;

import com.geekband.snap.moran.R;
import com.geekband.snap.moran.util.AESEncryptor;

public class TokenEngine {

    public static TokenInfo getTokenInfo(Context context){
        SharedPreferences sp = context.getSharedPreferences("moran",Context.MODE_PRIVATE);
        String user_id = sp.getString("user_id","");
        String token = sp.getString("token","");

        try {
            user_id = AESEncryptor.decrypt(context.getString(R.string.random_seed), user_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            token = AESEncryptor.decrypt(context.getString(R.string.random_seed),token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new TokenInfo(user_id,token);
    }

     public static class TokenInfo{
        public String user_id;
        public String token;

        public TokenInfo(String user_id,String token){
            this.user_id = user_id;
            this.token = token;
        }
     }
}
