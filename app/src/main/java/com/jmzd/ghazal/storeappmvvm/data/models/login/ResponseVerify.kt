package com.jmzd.ghazal.storeappmvvm.data.models.login


import com.google.gson.annotations.SerializedName

data class ResponseVerify(
    @SerializedName("access_token")
    val accessToken: String?, // eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI0IiwianRpIjoiZTZjODYwMDllMTNlMWM5OTdiOGNmNjg0NTFmNzRjMTI0NDZiMWNjMmFmMGRlM2ZjYjZkYmJiMDRlMmIzOGVlMmU0Y2QwNDdkNzhlODRhNzkiLCJpYXQiOjE2NzQyODg0MzUuMjcwMzYyLCJuYmYiOjE2NzQyODg0MzUuMjcwMzY3LCJleHAiOjE2ODczMzQ4MzUuMTQ0MDU0LCJzdWIiOiI2Iiwic2NvcGVzIjpbXX0.gip2MKgvB4OuYi13KZnSneHobmC2aephLRHb6xpS_EM1U1ZqEKpBCTcBJ-5qYIWTTDbGEYG4Z_GFUy1P63tGPorITr-s7pJ0jQ2qKWAiWnTMCiG6wd_u1tySBUnDpO_DsUyxXUPIo7SnkeqPVAMG4dNDBv5D-OlWrysbUJwO8mUjHDH2UrSh-nfbgUiyw0J_XshlYr-h_5pS7GloJreSmHJpmzzH_5-yarzBNXejbQWpM8Wy22Vx3p3mcc4YfQ7sa8yA_inqysbKCKLVxaDYPjZ1QYrSPuKvx7q2iIiF2dmRczYfGtBKGZm6mOJZVO9_CMuOUTzJ1Mh31tn6vbNPvWFtb4zbw0cTkOZuP2rn55h-V6o7xXUdtN56y_c9hvBG0PcN7_MI8Vhq499lNDbe7kVRS8eejacJ7we89kMAnKD7M9hXzvjGxNUDwyQOF63GrclB0vrDQz5Jiu3a4y0wUtRVmPsZ0jHb5-vit99tBwtDhpbSwTnizteswBNajQQLrSEs30B76UuujqTP0D7x8vs6t7ZlwS4vmEF1gL7myMEfB43Ra14vjgZZ4mt4QUSeL1oNgHp3n7IU0Zb_H0krB7Xr9ComniUVPKg8lbb3b86NE_vr808sJm8h4APA1UtTlKognaif4mmwS2ulZT1GxHujANjH_wMcOFtOCybNU0s
    @SerializedName("expires_in")
    val expiresIn: Int?, // 13046400
    @SerializedName("refresh_token")
    val refreshToken: String?, // def50200445668f58e07cd1fdc2c9fde17c2848623344ebd63647a1986b823526553c73e3ba3fbb9c978e1140e961412ee2ef6ccd2a86345cd5c7df3b3152ac91805ed8173a5243b0b8bce0b80b2cf2f8e95120efe0a469540b18012dacb040e01f2de64e620f81f58e21adca8f40aecb1932680093bb4666e602d91d02e596f7c5658cd619efeee022d297a2f695b1438927d0320d04d586717be8a55df720b9e2feb7fac68f2d3df18086e22a27307bb8ae42c96171ad12ad680811f60eca9b450f74722364f259df74a3ace2b0363cfd658929031db8679e36b17340a4b893580122a2b631bb70d91b3833920286bc13734a4ec9624c2217ad20f28878174f1f0a0fa832bd9e6c82aebf251a113094ef04a353249e76cb57307b33b60b4f63c7c7919e8c650a1302d747c5a4c5feade97dbc59a32a06880aac3cadb3f91547f4f70cf5ef39d838a32d7f5ab9ac1591c784c68aca080f9a93caa1b353820eb77
    @SerializedName("token_type")
    val tokenType: String?, // Bearer
    @SerializedName("user_detail")
    val userDetail: UserDetail?
) {
    data class UserDetail(
        @SerializedName("bank_account")
        val bankAccount: Any?, // null
        @SerializedName("birth_date")
        val birthDate: Any?, // null
        @SerializedName("cellphone")
        val cellphone: String?, // 09360000000
        @SerializedName("email")
        val email: Any?, // null
        @SerializedName("email_verified_at")
        val emailVerifiedAt: Any?, // null
        @SerializedName("firstname")
        val firstname: Any?, // null
        @SerializedName("id")
        val id: Int?, // 6
        @SerializedName("id_number")
        val idNumber: Any?, // null
        @SerializedName("job_title")
        val jobTitle: Any?, // null
        @SerializedName("lastname")
        val lastname: Any?, // null
        @SerializedName("type")
        val type: String?, // customer
        @SerializedName("updated")
        val updated: Any? // null
    )
}