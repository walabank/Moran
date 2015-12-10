package com.geekband.snap.moran;


public class ApplicationContext extends android.app.Application{
    private TokenInfo mTokenInfo;
    private static String baseUrl = "http://moran.chinacloudapp.cn/moran/web";
    public String getUrl(String path){
        return baseUrl+path;
    }

    public void setTokenInfo(int user_id,String token){
        mTokenInfo = new TokenInfo(user_id,token);
    }
    public TokenInfo getmTokenInfo(){
        return mTokenInfo == null ? null:mTokenInfo;
    }
    public static class  TokenInfo{
        public int user_id;
        public String token;

        public TokenInfo(int user_id,String token){
            this.user_id = user_id;
            this.token = token;
        }
    }
}
