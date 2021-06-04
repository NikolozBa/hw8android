package com.example.myapplication.api.dto

import com.google.gson.annotations.SerializedName

/*
 *  Created by Nikoloz Katsitadze on 25.05.21
 */

data class User(

    val id: Long?,

    var email: String?,

    @SerializedName("first_name")
    var firstName: String?,

    @SerializedName("last_name")
    var lastName: String?,

    var avatar: String?

)

data class ReqResData<T>(

    val page: Int?,

    val data: T

)