package zwjjeong.myapplication;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SnowWiFiMonitor m_SnowWifiMonitor = null;

    // 앱 생명주기 관련 - 앱 처음 켜졌을때(메인 메소드라고 생각하시면 좋습니다)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_SnowWifiMonitor = new SnowWiFiMonitor(this);
        m_SnowWifiMonitor.setOnChangeNetworkStatusListener(SnowChangedListener);    // 리스너 등록

        registerReceiver(m_SnowWifiMonitor, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    // 앱 생명주기 관련 - 앱 종료시
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(m_SnowWifiMonitor);
    }

    // 리스너 정의
    SnowWiFiMonitor.OnChangeNetworkStatusListener SnowChangedListener = new SnowWiFiMonitor.OnChangeNetworkStatusListener() {
        @Override
        public void OnChanged(int status) {

            switch(status) {
                case SnowWiFiMonitor.NETWORK_STATE_CONNECTED:

                    // Toast를 통해, 잠깐 나타났다 사라지는 alert 창에 메시지를 담아 띄울수 있습니다
                    Toast.makeText(getApplicationContext(), "[Monitor] NETWORK_STATE_CONNECTED", Toast.LENGTH_SHORT).show();
                    // Log.i를 통해, Android Monitor -> logcat에 info 레벨의 로그를 찍을 수 있습니다
                    Log.i("리스너", "[Monitor] NETWORK_STATE_CONNECTED");
                    // 네트워크에 연결되었을때 동작시키실 블로그 서핑작업을 이곳에 작성하시면됩니다(connectPoint1)
                    break;

                case SnowWiFiMonitor.NETWORK_STATE_DISCONNECTED:

                    Toast.makeText(getApplicationContext(), "[Monitor] NETWORK_STATE_DISCONNECTED", Toast.LENGTH_SHORT).show();
                    Log.i("리스너", "[Monitor] NETWORK_STATE_DISCONNECTED");
                    break;
            }
        }
    };
}
