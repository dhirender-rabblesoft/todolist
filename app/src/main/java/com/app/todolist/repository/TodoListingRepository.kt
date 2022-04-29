package com.app.todolist.repository

import android.app.Application
import android.util.Log
import com.app.todolist.base.KotlinBaseActivity
import com.app.todolist.model.TodoJson2
import com.app.todolist.model.TodoListJson
import com.app.todolist.network.APIInterface
import com.app.todolist.network.RetrofitClient
import com.app.todolist.utils.Keys
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodoListingRepository(private val baseActivity: Application)  {
    var retrofitClient: APIInterface? = null
    fun todolisting(
        baseActivity: KotlinBaseActivity,
        itemClick: (TodoJson2) -> Unit
    ) {
        if (!baseActivity.networkcheck.isNetworkAvailable()) {
            baseActivity.nointernershowToast()
        } else {
            retrofitClient =
                RetrofitClient.with(this.baseActivity)?.client?.create(APIInterface::class.java)
            retrofitClient?.todolisting()!!
                .enqueue(object : Callback<TodoJson2> {
                    override fun onResponse(
                        call: Call<TodoJson2?>,
                        response: Response<TodoJson2?>
                    ) {
                        baseActivity.stopProgressDialog()
                        when (response.code()) {
                            Keys.RESPONSE_SUCESS -> {
                                response.body()?.let {
                                    itemClick(it)
                                    Log.e("deeeeeeeeeeeeeee", it.toString())

                                }
                            }
                            Keys.ERRORCODE -> {

                                baseActivity.parseError(response)
                            }
                            Keys.UNAUTHoRISE -> {

                                //signupmutableLiveData.setValue(response.body())
                            }
                        }

                    }

                    override fun onFailure(call: Call<TodoJson2>, t: Throwable) {
                        baseActivity.stopProgressDialog()
                    }
                })
        }


    }
}