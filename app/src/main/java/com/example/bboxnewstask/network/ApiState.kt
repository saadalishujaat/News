package com.example.bboxnewstask.network

import com.example.bboxnewstask.models.Headlines


sealed class ApiState {
    object Loading : ApiState()
    object Empty : ApiState()
    class Success(val headlines: Headlines) : ApiState()
    class Failure(val error: Error) : ApiState()
}
