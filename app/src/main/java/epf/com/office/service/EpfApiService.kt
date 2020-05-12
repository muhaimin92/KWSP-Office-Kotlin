package epf.com.office.service

import epf.com.office.Constant.Companion.EPF_BRANCH_URL
import epf.com.office.model.EpfOfficeRequest
import epf.com.office.model.EpfOfficeResponse
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface EpfApiService {
    @POST("m2/postBranchLocation")
    fun requestOfficeLoacation(@Body epfOfficeRequest: EpfOfficeRequest) : Observable<EpfOfficeResponse>

    companion object {
        fun create(): EpfApiService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(EPF_BRANCH_URL)
                .build()

            return retrofit.create(EpfApiService::class.java)
        }
    }
}