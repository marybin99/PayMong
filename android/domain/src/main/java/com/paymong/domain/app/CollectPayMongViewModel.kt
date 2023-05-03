package com.paymong.domain.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CollectPayMongViewModel : ViewModel() {

    private lateinit var load : Job
    var mongList = mutableListOf<String>()

    init {
        if(::load.isInitialized) load.cancel()

        load = viewModelScope.launch {

            for(i in 0..5){
                val index = i.toString()
                mongList.add("CH00${index}")
            }
            for(i in 0..2){
                val index = i.toString()
                mongList.add("CH10${index}")
            }
            for(i in 0..2){
                val index = i.toString()
                for(j in 0..3){
                    val middle = j.toString()
                    mongList.add("CH2${middle}${index}")
                }
            }
            for(i in 0..2){
                val index = i.toString()
                for(j in 0..3){
                    val middle = j.toString()
                    mongList.add("CH3${middle}${index}")
                }
            }
            mongList.add("CH203")
            mongList.add("CH303")
            // 가지고 있는 게 아니면 none사진 add CH444
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}