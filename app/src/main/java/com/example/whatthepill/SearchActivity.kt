package com.example.whatthepill

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.SearchView

class SearchActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val actionBar = supportActionBar
        actionBar!!.title = "의약품 정보 검색"
        actionBar.setDisplayHomeAsUpEnabled(true)

        var arrays = intent.getStringArrayExtra("arrays")



//        val search = findViewById<SearchView>(R.id.search_view)
//        val names: Array<String> = arrayOf( "타이레놀", "가나릴정", "가나메드정", "가나슨캡슐")
//        val adapter: ArrayAdapter<String> = ArrayAdapter(
//                this, android.R.layout.simple_list_item_1, names
//        )
//
////          search.setOnSearchClickListener(object : View.OnClickListener {
////             override fun onClick(v: View?) {
////                Log.e(":test", "aa")
////            }
////        })
//
//        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(p0: String): Boolean {
//                search.clearFocus()
//
//                var searchPill = names.filter { it.contains(p0.trim()) }
//
//                if(searchPill.isNotEmpty()){
//                    adapter.filter.filter(p0)
//                    val nextIntent = Intent(this@MainActivity, SearchActivity::class.java)
//                    nextIntent.putExtra("arrays", searchPill.toTypedArray())
//
//                    startActivity(nextIntent)
//                }
//                else{
//                    Toast.makeText(applicationContext, "조회된 데이터가 없습니다.", Toast.LENGTH_LONG).show()
//                }
//                return false
//            }
//
//            override fun onQueryTextChange(p0: String): Boolean {
//                adapter.filter.filter(p0)
//                return false
//            }
//        })

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}