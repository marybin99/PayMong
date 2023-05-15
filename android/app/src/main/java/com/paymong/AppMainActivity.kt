package com.paymong

import android.app.ActivityManager
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.wear.remote.interactions.RemoteActivityHelper
import com.google.android.gms.games.GamesSignInClient
import com.google.android.gms.games.PlayGames
import com.google.android.gms.games.PlayGamesSdk
import com.google.android.gms.games.PlayersClient
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.CapabilityInfo
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.NodeClient
import com.google.android.gms.wearable.Wearable
import com.paymong.data.repository.DataApplicationRepository
import com.paymong.domain.app.AppLandingViewModelFactory
import com.paymong.domain.app.AppLandinglViewModel
import com.paymong.ui.app.AppMain
import com.paymong.ui.theme.PaymongTheme


class AppMainActivity : ComponentActivity(), CapabilityClient.OnCapabilityChangedListener {

    companion object {
        private const val CAPABILITY_WEAR_APP = "watch_paymong"
    }
    // 모바일-워치 간 연결
    private lateinit var capabilityClient: CapabilityClient
    private lateinit var nodeClient: NodeClient
    private lateinit var remoteActivityHelper: RemoteActivityHelper
    private lateinit var messageClient: MessageClient
    // google-play-games
    private lateinit var gamesSignInClient : GamesSignInClient
    private lateinit var playersClient: PlayersClient
    // 로그인을 위한 viewModel
    private lateinit var appLandingViewModelFactory : AppLandingViewModelFactory
    private lateinit var appLandinglViewModel : AppLandinglViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 모바일-워치 간 연결
        capabilityClient = Wearable.getCapabilityClient(this)
        nodeClient = Wearable.getNodeClient(this)
        remoteActivityHelper = RemoteActivityHelper(this)
        messageClient = Wearable.getMessageClient(this)
        // google-play-games
        PlayGamesSdk.initialize(this)
        gamesSignInClient = PlayGames.getGamesSignInClient(this)
        playersClient = PlayGames.getPlayersClient(this)
        // 로그인을 위한 viewModel
        appLandingViewModelFactory = AppLandingViewModelFactory(capabilityClient, nodeClient, remoteActivityHelper, messageClient, gamesSignInClient, playersClient, this.application)
        appLandinglViewModel = ViewModelProvider(this@AppMainActivity, appLandingViewModelFactory)[AppLandinglViewModel::class.java]

        val serviceIntent = Intent(this, ForegroundService::class.java)
        this.startForegroundService(serviceIntent)

        // 필요 권한 동의 확인
        if (!isNotificationPermissionGranted()) startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
        
        setContent {
            PaymongTheme {
                AppMain(appLandinglViewModel)
            }
        }
    }
    
    // 웨어러블 연결 여부 확인해서 연결 때마다 앱 설치 여부 확인
    override fun onCapabilityChanged(capabilityInfo: CapabilityInfo) {
        appLandinglViewModel.wearNodesWithApp = capabilityInfo.nodes
        appLandinglViewModel.installCheck()
    }
    // 앱 중지 마다 리스너 해제
    override fun onPause() {
        super.onPause()
        capabilityClient.removeListener(this, CAPABILITY_WEAR_APP)
    }
    // 앱 중지 해제 시 마다 리스너 추가
    override fun onResume() {
        super.onResume()
        capabilityClient.addListener(this, CAPABILITY_WEAR_APP)
    }
    // 필요 권한 동의 확인 함수
    private fun isNotificationPermissionGranted(): Boolean {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            return notificationManager.isNotificationListenerAccessGranted(ComponentName(application, NotificationListener::class.java))
        }
        else {
            return NotificationManagerCompat.getEnabledListenerPackages(applicationContext).contains(applicationContext.packageName)
        }
    }
    // 포그라운드 서비스 동작 확인 함수
    private fun isForegroundServiceRunning(serviceClass: Class<*>): Boolean {
        try {
            val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (service in manager.getRunningServices(Int.MAX_VALUE)) {
                if (serviceClass.name == service.service.className) {
                    return true
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

        return false
    }
}
