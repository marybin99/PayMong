package com.paymong.domain.watch

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.paymong.common.code.MongCode
import com.paymong.common.code.MapCode
import com.paymong.common.code.MongStateCode
import com.paymong.data.repository.InformationRepository
import com.paymong.data.repository.ManagementRepository
import com.paymong.data.repository.MemberRepository
import com.paymong.domain.entity.Mong
import com.paymong.domain.entity.MongInfo
import com.paymong.domain.entity.MongStats
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class WatchViewModel (
    application: Application
): AndroidViewModel(application) {    
    // 포인트 정보
    var point by mutableStateOf(0)
    // 몽 기본정보 (이름, 아이디, 몽 코드)
    var mong by mutableStateOf(Mong())
    // 몽 지수
    var mongStats by mutableStateOf(MongStats())
    // 몽 정보 (몸무게, 태어난 일자)
    var undomong by mutableStateOf(MongCode.CH000)
    var mongInfo by mutableStateOf(MongInfo())
    var age by mutableStateOf("")
    // 몽 상태, 똥 갯수, 맵 코드
    var stateCode by mutableStateOf(MongStateCode.CD000)
    var poopCount by mutableStateOf(0)
    var mapCode by mutableStateOf(MapCode.MP000)

    var isHappy by mutableStateOf(false)
    var isClicked by mutableStateOf(false)

    // 몽 진화
    var evolutionisClick by mutableStateOf(false)

    private val memberRepository: MemberRepository = MemberRepository()
    private var informationRepository: InformationRepository = InformationRepository()
    private val managementRepository: ManagementRepository = ManagementRepository()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            findMong()
            findMongCondition()
            findMongInfo()
            findPayPoint()

        }
    }

    private fun findPayPoint(){
        viewModelScope.launch(Dispatchers.IO) {
            memberRepository.findMember()
                .catch {
                    it.printStackTrace()
                }
                .collect{ data ->
                    delay(1000)
                    point = data.point.toInt()
                }
        }
    }
    
    private fun findMong() {
        viewModelScope.launch(Dispatchers.IO) {
            informationRepository.findMong()
                .catch {
                    it.printStackTrace()
                }
                .collect { data ->
                    mong = Mong(
                        data.mongId,
                        data.name,
                        MongCode.valueOf(data.mongCode)
                    )
                    stateCode = MongStateCode.valueOf(data.stateCode)
                    poopCount = data.poopCount
                    mapCode = MapCode.valueOf(data.mapCode ?: "MP000")
                }
        }
    }

    private fun findMongCondition() {
        viewModelScope.launch(Dispatchers.IO) {
            informationRepository.findMongStats()
                .catch {
                    it.printStackTrace()
                }
                .collect { data ->
                    mongStats = MongStats(
                        data.mongId,
                        data.name,
                        data.health,
                        data.satiety,
                        data.strength,
                        data.sleep,
                    )
                }
        }
    }

    private fun findMongInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            informationRepository.findMongInfo()
                .catch {
                    it.printStackTrace()
                }
                .collect { data ->
                    mongInfo = MongInfo(
                        data.weight,
                        data.born
                    )
                }
            while(true) {
                age = calcAge()
                delay(6000)
            }
        }
    }

    private fun calcAge(): String {
        val day = ChronoUnit.DAYS.between(mongInfo.born, LocalDateTime.now())
        val hours = ChronoUnit.HOURS.between(mongInfo.born.plusDays(day), LocalDateTime.now())
        val minutes = ChronoUnit.MINUTES.between(mongInfo.born.plusDays(day).plusHours(hours), LocalDateTime.now())
        return String.format("%d일 %d시간 %d분", day, hours, minutes)
    }

    fun stroke(){
        viewModelScope.launch(Dispatchers.IO) {
            managementRepository.stroke()
                .catch {
                    it.printStackTrace()
                }
                .collect{
                    data ->
                    isHappy = data.code=="200"
                }
        }
    }

    fun sleep(){
        viewModelScope.launch(Dispatchers.IO) {
            managementRepository.sleep()
                .catch {
                    it.printStackTrace()
                }
                .collect{}
        }
    }

    fun poop(){
        viewModelScope.launch(Dispatchers.IO) {
            managementRepository.poop()
                .catch {
                    it.printStackTrace()
                }
                .collect{}
        }
    }

    fun evolution(){
        viewModelScope.launch(Dispatchers.IO) {
            managementRepository.evolution()
                .catch {
                    it.printStackTrace()
                }
                .collect{
                        data->
                    undomong = mong.mongCode
                    stateCode = MongStateCode.valueOf(data.stateCode)
                    mong.mongCode = MongCode.valueOf(data.mongCode)
                }
        }
    }
}