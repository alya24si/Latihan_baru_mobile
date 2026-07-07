package com.example.alya_love.news

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("posts")
    fun getPosts(): Call<NewsResponse>
}

//[START]
//│
//▼
//[MainActivity / NewsFragment.onViewCreated()]
//│
//▼
//[RetrofitClient.getRetrofitInstance()]
//│
//▼
//[ApiService.getPosts().enqueue(Callback)]
//│
//▼
//<onResponse() dipanggil?> ──[NO]──▶ [onFailure()] ──▶ [Toast "Gagal koneksi"] ──▶ [END]
//│
//[YES]
//│
//▼
//<response.isSuccessful() (Kode 200)?> ──[NO]──▶ [Toast "Server Error"] ──▶ [END]
//│
//[YES]
//│
//▼
//[Parse JSON: response.body() ke List<Post>]
//│
//▼
//[adapter.submitList(data)]
//│
//▼
//[RecyclerView.notifyDataSetChanged()]
//│
//▼
//[END]