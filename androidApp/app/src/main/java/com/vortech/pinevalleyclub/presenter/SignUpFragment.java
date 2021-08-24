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
import com.vortech.pinevalleyclub.view.HomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment implements View.OnClickListener{

    private EditText firstNameTextField;
    private EditText lastNameTextField;
    private EditText signUpEmailTextField;
    private EditText signUpPasswordTextField;
    private EditText signUpConfirmPasswordTextField;

    private Button signUpButton;
    private ProgressBar signUpProgress;

    public SignUpFragment() {

    }

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
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
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        firstNameTextField = view.findViewById(R.id.firstNameTextField);
        lastNameTextField = view.findViewById(R.id.lastNameTextField);
        signUpEmailTextField = view.findViewById(R.id.signUpEmailTextField);
        signUpPasswordTextField = view.findViewById(R.id.signUpPasswordTextField);
        signUpConfirmPasswordTextField = view.findViewById(R.id.signUpConfirmPasswordTextField);
        signUpButton = view.findViewById(R.id.signUpButton);
        signUpProgress = view.findViewById(R.id.signUpProgress);
        signUpButton.setOnClickListener(this);
        return view;

    }

    private void registerUser() {
        String firstName = firstNameTextField.getText().toString().trim();
        String lastName = lastNameTextField.getText().toString().trim();
        String email = signUpEmailTextField.getText().toString().trim();
        String password = signUpPasswordTextField.getText().toString().trim();
        String confirmPassword = signUpConfirmPasswordTextField.getText().toString().trim();

        if (firstName.isEmpty()) {
            firstNameTextField.setError("First name is required");
            firstNameTextField.requestFocus();
            return;
        }

        if (lastName.isEmpty()) {
            lastNameTextField.setError("Last name is required");
            lastNameTextField.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            signUpEmailTextField.setError("Email is required");
            signUpEmailTextField.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signUpEmailTextField.setError("Enter a valid email");
            signUpEmailTextField.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            signUpPasswordTextField.setError("Password is required");
            signUpPasswordTextField.requestFocus();
            return;
        }

        if (confirmPassword.isEmpty()) {
            signUpConfirmPasswordTextField.setError("Password is required");
            signUpConfirmPasswordTextField.requestFocus();
            return;
        }

        if (password.length() < 6) {
            signUpPasswordTextField.setError("Password should be at least 6 character long");
            signUpPasswordTextField.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            signUpConfirmPasswordTextField.setError("Passwords do not match");
            signUpConfirmPasswordTextField.requestFocus();
            return;
        }

        Call<LoginResponse> call = RetrofitClient
                .getInstance().getApi().createUser(email, password, firstName, lastName);

        signUpButton.setVisibility(View.INVISIBLE);
        signUpProgress.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                signUpButton.setVisibility(View.VISIBLE);
                signUpProgress.setVisibility(View.INVISIBLE);

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
                showMessage(t.getMessage(), true);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpButton:
                registerUser();
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