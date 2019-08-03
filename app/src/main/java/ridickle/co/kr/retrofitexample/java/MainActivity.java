package ridickle.co.kr.retrofitexample.java;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ridickle.co.kr.retrofitexample.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button get, post;
    NetworkInterface nInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        get = (Button) findViewById(R.id.get);
        post = (Button) findViewById(R.id.post);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.get:
                nInterface = NetworkHelper.getInstance();
                Call<Network_Authorize> get_userLogin = nInterface.get_userLogin(
                        "code",                                 // id
                        "login");                               // password

                get_userLogin.clone().enqueue(new Callback<Network_Authorize>() {
                    @Override
                    public void onResponse(Call<Network_Authorize> call, Response<Network_Authorize> response) {
                        Log.d("Login getId : ", response.body().getId());
                        Toast.makeText(getApplicationContext(), "비밀번호는 " + response.body().getPassowrd(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Network_Authorize> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "실패 : " + t, Toast.LENGTH_SHORT).show();
                    }
                });
                break;


            case R.id.post:
                nInterface = NetworkHelper.getInstance();
                Call<Network_Authorize> post_userLogin = nInterface.post_userLogin(
                        "code",                                 // id
                        "login");                               // password

                post_userLogin.clone().enqueue(new Callback<Network_Authorize>() {
                    @Override
                    public void onResponse(Call<Network_Authorize> call, Response<Network_Authorize> response) {
                        Log.d("Login getId : ", response.body().getId());
                        Toast.makeText(getApplicationContext(), "비밀번호는 " + response.body().getPassowrd(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Network_Authorize> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "실패 : " + t, Toast.LENGTH_SHORT).show();
                    }
                });
                break;

        }
    }
}
