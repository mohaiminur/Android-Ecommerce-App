package com.example.user.salesapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_order.*

class OrderAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        var url="http://192.168.8.100/SalesWeb/get_temp.php?mobile="+UserInfo.mobile

        var list=ArrayList<String>()
        var rq:RequestQueue=Volley.newRequestQueue(this)
        var jar=JsonArrayRequest(Request.Method.GET,url,null,Response.Listener { response ->

            for(x in 0..response.length()-1)
                list.add("Name : " + response.getJSONObject(x).getString("name") + "\n" +
                      "Pirce : "+  response.getJSONObject(x).getString("price")
                +"\n" + "Qty : "+ response.getJSONObject(x).getString("qty"))

            var adp=ArrayAdapter(this,android.R.layout.simple_list_item_1,list)
            order_lv.adapter=adp

        },Response.ErrorListener { error ->
            Toast.makeText(this,error.message,Toast.LENGTH_LONG).show()
        })
        rq.add(jar)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.my_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId==R.id.item_menu)
        {
            var i=Intent(this,HomeAct::class.java)
            startActivity(i)
        }

        if(item?.itemId==R.id.item_cancel)
        {
            var url="http://192.168.8.100/SalesWeb/cancel_order.php?mobile=" + UserInfo.mobile

            var rq:RequestQueue=Volley.newRequestQueue(this)
            var sr= StringRequest(Request.Method.GET,url,Response.Listener { response ->

                var i=Intent(this,HomeAct::class.java)
                startActivity(i)

            },Response.ErrorListener { error ->
                Toast.makeText(this,error.message,Toast.LENGTH_LONG).show()
            })

            rq.add(sr)
        }

        if(item?.itemId==R.id.item_confirm)
        {
            var url="http://192.168.8.100/SalesWeb/confirm_order.php?mobile=" + UserInfo.mobile
            var rq:RequestQueue=Volley.newRequestQueue(this)
            var sr= StringRequest(Request.Method.GET,url,Response.Listener { response ->

                var i=Intent(this,TotalAct::class.java)
                i.putExtra("bno",response)
                startActivity(i)

            },Response.ErrorListener { error ->
                Toast.makeText(this,error.message,Toast.LENGTH_LONG).show()
            })

            rq.add(sr)
        }

        return super.onOptionsItemSelected(item)
    }
}
