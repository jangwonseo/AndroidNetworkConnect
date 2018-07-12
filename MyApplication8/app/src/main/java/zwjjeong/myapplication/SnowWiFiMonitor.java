package zwjjeong.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by jangwonseo on 2018. 7. 13..
 */

public class SnowWiFiMonitor extends BroadcastReceiver {

    public final static int WIFI_STATE_DISABLED         = 0x00;
    public final static int WIFI_STATE_DISABLING        = WIFI_STATE_DISABLED       + 1;
    public final static int WIFI_STATE_ENABLED          = WIFI_STATE_DISABLING      + 1;
    public final static int WIFI_STATE_ENABLING         = WIFI_STATE_ENABLED        + 1;
    public final static int WIFI_STATE_UNKNOWN          = WIFI_STATE_ENABLING       + 1;
    public final static int NETWORK_STATE_CONNECTED     = WIFI_STATE_UNKNOWN        + 1;
    public final static int NETWORK_STATE_CONNECTING    = NETWORK_STATE_CONNECTED   + 1;
    public final static int NETWORK_STATE_DISCONNECTED  = NETWORK_STATE_CONNECTING  + 1;

    public interface OnChangeNetworkStatusListener {
        public void OnChanged(int status);
    }

    private ConnectivityManager m_ConnManager = null;
    private OnChangeNetworkStatusListener m_OnChangeNetworkStatusListener = null;

    public SnowWiFiMonitor(Context context) {
        m_ConnManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public void setOnChangeNetworkStatusListener(OnChangeNetworkStatusListener listener) {
        m_OnChangeNetworkStatusListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (m_OnChangeNetworkStatusListener == null) return;

        String strAction = intent.getAction();
        if (strAction.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {     // == "android.net.conn.CONNECTIVITY_CHANGE"

            NetworkInfo networkInfo = m_ConnManager.getActiveNetworkInfo(); // 활성화된 네트워크 정보를 얻는 메소드

            if ( (networkInfo != null) && (networkInfo.isAvailable() == true) ) {   // 네트워크 활성화상태
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    m_OnChangeNetworkStatusListener.OnChanged(NETWORK_STATE_CONNECTED);
                    Log.i("리시버", "[Monitor] Network Connected");
                    // 네트워크에 연결되었을때 동작시키실 블로그 서핑작업을 이곳에 작성하시면됩니다(connectPoint2)
                    // 인터넷 연결시, connectPoint1, 2 둘다 로그에 찍히는것으로 보아, 둘 중 어느곳에나 작성하셔도 될 것같습니다
                }
            } else {                                                                // 네트워크 비활성상태  [networkinfo == null 이면 연결된 네트워크 없음(현재 네트워크 연결안됨)]
                Log.i("리시버", "[Monitor] Network Disconnected");
            }
        }
    }
}
