package it.polito.dma.ponyexpressandroid;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.WindowManager;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import it.polito.dma.ponyexpressandroid.library.CookiesManager;
import it.polito.dma.ponyexpressandroid.request.LoginRequest;

public class LoginActivity extends Activity {

	private Button btnLogin;
	private Button btnLinkToRegister;
	private EditText inputEmail;
	private EditText inputPassword;
	private TextView loginErrorMsg;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);


		CookiesManager cookiesManager = new CookiesManager(this);

		//Verifies if the user is logged in and starts the Dashboard activity
		if(cookiesManager.isLoggedIn()){
			Intent dashboard = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(dashboard);
			finish();
			return;
		}


		// Importing all assets like buttons, text fields
		inputEmail = (EditText) findViewById(R.id.loginEmail);
		inputPassword = (EditText) findViewById(R.id.loginPassword);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
		loginErrorMsg = (TextView) findViewById(R.id.login_error);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		// Login button Click Event
		btnLogin.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				login();
			}
		});

		// Link to Register Screen
		btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
	
			}
		});
	}

	protected void login() {
		LoginRequest loginRequest;
		String email, password;
		email = inputEmail.getText().toString();
		password = inputPassword.getText().toString();
		if (validate(email, password)) {
			loginRequest = new LoginRequest(this);
			loginRequest.login(email, password);
		}
	}

	//Validates the user's input
	private boolean validate(String email, String password) {
		boolean valid = true;
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern;
		Matcher matcher;
		String message = "";

		if (email.equals("")) {
			message = message + "Please insert your email\n";
			valid = false;
		} else {
			pattern = Pattern.compile(EMAIL_PATTERN);
			matcher = pattern.matcher(email);
			if (!matcher.matches()) {
				message = message + "Please insert a valid email\n";
				valid = false;
			}
		}

		if (password.equals("")) {
			message = message + "Please insert your password\n";
			valid = false;
		}
		if (!valid) {
			onFailure(message);
		}
		return valid;

	}

	//Shows the error messages
	public void onFailure(String message) {
		loginErrorMsg.setText(message);
		loginErrorMsg.setVisibility(View.VISIBLE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
