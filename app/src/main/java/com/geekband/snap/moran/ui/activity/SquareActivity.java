package com.geekband.snap.moran.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.geekband.snap.moran.ApplicationContext;
import com.geekband.snap.moran.R;
import com.geekband.snap.moran.engine.NodeEngine;
import com.geekband.snap.moran.engine.TokenEngine;
import com.geekband.snap.moran.model.Node;
import com.geekband.snap.moran.util.NetworkStatus;

import java.util.List;

public class SquareActivity extends AppCompatActivity {
    private Spinner mSpinner;
    private ListView mListView;
    private List<Node> mNodes;

    private ApplicationContext mAppContext;
    private String mPath = "/node/list";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square);

        mSpinner = (Spinner) findViewById(R.id.distance_spinner_square);
        mListView = (ListView) findViewById(R.id.square_list_view);

        final TokenEngine.TokenInfo tokenInfo = TokenEngine.getTokenInfo(SquareActivity.this);
        mAppContext = (ApplicationContext) getApplication();
       final String path = mAppContext.getUrl(mPath);

        if(NetworkStatus.isNetworkConnected(SquareActivity.this)){
            new Thread(){
                @Override
                public void run() {
                    NodeEngine.getNodes(path, tokenInfo);
                }
            }.start();

        }else {
            Toast.makeText(SquareActivity.this,R.string.unavailable_network_connection,Toast.LENGTH_SHORT).show();
        }

//        NodeAdapter adapter = new NodeAdapter(SquareActivity.this,mNodes);
//        mListView.setAdapter(adapter);


    }
//
//    private void initData() {
//        mNodes = new ArrayList<>();
//        Node node = new Node();
//        node.setAddress("地点一");
//        List<ImageItem> imageItems = new ArrayList<>();
//        imageItems.add(new ImageItem(R.drawable.sign_logo, "真好吃"));
//        imageItems.add(new ImageItem(R.drawable.sign_logo, "好吃你就多吃点"));
//        imageItems.add(new ImageItem(R.drawable.sign_logo, "不能浪费！"));
//        imageItems.add(new ImageItem(R.drawable.sign_logo, "真好吃"));
//        imageItems.add(new ImageItem(R.drawable.sign_logo, "好吃你就多吃点"));
//        imageItems.add(new ImageItem(R.drawable.sign_logo, "不能浪费！"));
//        node.setImages(imageItems);
//        mNodes.add(node);
//
//        node = new Node();
//        node.setAddress("地点二");
//        imageItems = new ArrayList<>();
//        imageItems.add(new ImageItem(R.drawable.sign_logo, "真好吃"));
//        imageItems.add(new ImageItem(R.drawable.sign_logo, "好吃你就多吃点"));
//        imageItems.add(new ImageItem(R.drawable.sign_logo, "不能浪费！"));
//
//        node.setImages(imageItems);
//        mNodes.add(node);
//
//        node = new Node();
//        node.setAddress("地点三");
//        imageItems = new ArrayList<>();
//        imageItems.add(new ImageItem(R.drawable.sign_logo, "真好吃"));
//        imageItems.add(new ImageItem(R.drawable.sign_logo, "好吃你就多吃点"));
//        imageItems.add(new ImageItem(R.drawable.sign_logo, "不能浪费！"));
//        node.setImages(imageItems);
//        mNodes.add(node);
//
//        node = new Node();
//        node.setAddress("地点四");
//        imageItems = new ArrayList<>();
//        imageItems.add(new ImageItem(R.drawable.sign_logo, "真好吃"));
//        imageItems.add(new ImageItem(R.drawable.sign_logo, "好吃你就多吃点"));
//        imageItems.add(new ImageItem(R.drawable.sign_logo, "不能浪费！"));
//        node.setImages(imageItems);
//        mNodes.add(node);
//
//        node = new Node();
//        node.setAddress("地点五");
//        imageItems = new ArrayList<>();
//        imageItems.add(new ImageItem(R.drawable.sign_logo, "真好吃"));
//        imageItems.add(new ImageItem(R.drawable.sign_logo, "好吃你就多吃点"));
//        imageItems.add(new ImageItem(R.drawable.sign_logo, "不能浪费！"));
//        node.setImages(imageItems);
//        mNodes.add(node);
//    }

}
