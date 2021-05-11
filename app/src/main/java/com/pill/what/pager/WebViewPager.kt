package com.pill.what.pager

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import com.pill.what.R


class WebViewPager(val data: String, private val title: String) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_webview, container, false)
        val titleView: TextView = view.findViewById(R.id.textView)
        val webView: WebView = view.findViewById(R.id.webView)
        titleView.text = title
        webView.loadDataWithBaseURL(null, "<font color=\"#808080\">$data</font>", "text/html","UTF-8", null)
        webView.setBackgroundColor(Color.TRANSPARENT)
        return view
    }
    fun newInstant() : WebViewPager
    {
        val args = Bundle()
        val frag = WebViewPager(data, title)
        frag.arguments = args
        return frag
    }
}