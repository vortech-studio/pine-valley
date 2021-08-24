package com.vortech.pinevalleyclub.presenter;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vortech.pinevalleyclub.R;
import com.vortech.pinevalleyclub.api.RetrofitClient;
import com.vortech.pinevalleyclub.model.LoginResponse;
import com.vortech.pinevalleyclub.storage.SharedPrefManager;
import com.vortech.pinevalleyclub.view.AuthenticationActivity;
import com.vortech.pinevalleyclub.view.HomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private EditText signInEmailEditText;
    private EditText signInPasswordEditText;

    private Button signInButton;
    private ProgressBar signInProgress;

    public LoginFragment() {

    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        signInEmailEditText = view.findViewById(R.id.signInEmailTextField);
        signInPasswordEditText = view.findViewById(R.id.signInPasswordTextField);
        signInButton = view.findViewById(R.id.signInButton);
        signInProgress = view.findViewById(R.id.signInProgress);
        signInButton.setOnClickListener(this);
        return view;

    }

    private void loginUser() {
        String email = signInEmailEditText.getText().toString().trim();
        String password = signInPasswordEditText.getText().toString().trim();

        if (email.isEmpty()) {
            signInEmailEditText.setError("Email is required");
            signInEmailEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signInEmailEditText.setError("Enter a valid email");
            signInEmailEditText.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            signInPasswordEditText.setError("Password required");
            signInPasswordEditText.requestFocus();
            return;
        }

        if (password.length() < 6) {
            signInPasswordEditText.setError("Password should be at least 6 character long");
            signInPasswordEditText.requestFocus();
            return;
        }

        Call<LoginResponse> call = RetrofitClient
                .getInstance().getApi().loginUser(email, password);

        signInButton.setVisibility(View.INVISIBLE);
        signInProgress.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                signInButton.setVisibility(View.VISIBLE);
                signInProgress.setVisibility(View.INVISIBLE);

                LoginResponse loginResponse = response.body();

                if (!loginResponse.isError()) {
                    showMessage("Welcome " + loginResponse.getUser().getFirstName(), false);

                    SharedPrefManager.getInstance(getContext())
                            .saveUser(loginResponse.getUser());

                    Intent intent = new Intent(getContext(), HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);


                } else {
                    showMessage(loginResponse.getMessage(), true);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                signInButton.setVisibility(View.VISIBLE);
                signInProgress.setVisibility(View.INVISIBLE);
                showMessage(t.getMessage(), true);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signInButton:
                loginUser();
                break;
        }
    }

    public void showMessage(String message, Boolean error) {
        Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_LONG);
        View view = toast.getView();

        view.getBackground().setColorFilter(error ? getResources().getColor(R.color.error) : getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(getResources().getColor(R.color.colorOnPrimary));

        toast.show();
    }
}