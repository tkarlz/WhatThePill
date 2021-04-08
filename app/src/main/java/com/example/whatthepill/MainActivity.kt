package com.example.whatthepill

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val search = findViewById<SearchView>(R.id.search_view)
        val names: Array<String> = arrayOf( "타이레놀", "가나릴정", "가나메드정", "가나슨캡슐")
        val adapter: ArrayAdapter<String> = ArrayAdapter(
                this, android.R.layout.simple_list_item_1, names
        )

//          search.setOnSearchClickListener(object : View.OnClickListener {
//             override fun onClick(v: View?) {
//                Log.e(":test", "aa")
//            }
//        })

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String): Boolean {
                search.clearFocus()

                var searchPill = names.filter { it.contains(p0) }

                if(searchPill.isNotEmpty()){
                    adapter.filter.filter(p0)
                    val nextIntent = Intent(this@MainActivity, SearchActivity::class.java)
                    nextIntent.putExtra("arrays", searchPill.toTypedArray())

                    startActivity(nextIntent)
                }
                else{
                    Toast.makeText(applicationContext, "조회된 데이터가 없습니다.", Toast.LENGTH_LONG).show()
                }
                return false
            }

            override fun onQueryTextChange(p0: String): Boolean {
                adapter.filter.filter(p0)
                return false
            }
        })

    }
    fun onButton1Clicked(view: View){
        val  intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent)
    }

    fun onButton2Clicked(view: View) { //약학 정보원 이동
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.health.kr/"))
        startActivity(intent)
    }
}

