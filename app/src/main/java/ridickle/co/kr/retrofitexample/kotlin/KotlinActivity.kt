package ridickle.co.kr.retrofitexample.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ridickle.co.kr.retrofitexample.R

class KotlinActivity : AppCompatActivity(), View.OnClickListener {

    var nInterface: KotlinNetworkInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.get -> {
                nInterface = NetworkUtil.getInstance()
                val get_userLogin = nInterface?.get_userLogin(
                        "code",             // id
                        "login")      // password

                get_userLogin?.enqueue(object : Callback<NetworkModel> {
                    override fun onResponse(call: Call<NetworkModel>, response: Response<NetworkModel>) {
                        Log.d("Login getId : ", response.body()!!.id)
                        Toast.makeText(applicationContext, "비밀번호는 " + response.body()!!.passowrd, Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: Call<NetworkModel>, t: Throwable) {
                        Toast.makeText(applicationContext, "실패 : $t", Toast.LENGTH_SHORT).show()
                    }
                })
            }


            R.id.post -> {
                nInterface = NetworkUtil.getInstance()
                val post_userLogin = nInterface?.post_userLogin(
                        "code",         // id
                        "login")  // password

                post_userLogin?.enqueue(object : Callback<NetworkModel> {
                    override fun onResponse(call: Call<NetworkModel>, response: Response<NetworkModel>) {
                        Log.d("Login getId : ", response.body()!!.id)
                        Toast.makeText(applicationContext, "비밀번호는 " + response.body()!!.passowrd, Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: Call<NetworkModel>, t: Throwable) {
                        Toast.makeText(applicationContext, "실패 : $t", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }
}
