package mobapply.newzgamification.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import mobapply.newzgamification.R;

public class LoginActivity extends Activity implements View.OnClickListener {

    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                Intent a = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(a);
                break;
        }

    }
}
