package com.example.cyclingapp.ui.register;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.cyclingapp.R;
import com.example.cyclingapp.data.model.Role;
import com.example.cyclingapp.databinding.FragmentRegisterBinding;
import com.example.cyclingapp.ui.login.LoginFragment;
import com.example.cyclingapp.ui.welcome.WelcomeFragment;

public class RegisterFragment extends Fragment {

    private RegisterViewModel registerViewModel;
    private FragmentRegisterBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerViewModel = new ViewModelProvider(this, new RegisterViewModelFactory())
                .get(RegisterViewModel.class);

        final Spinner roleSelector = binding.role;
        final EditText emailEditText = binding.email;
        final EditText firstNameEditText = binding.firstName;
        final EditText lastNameEditText = binding.lastName;
        final EditText passwordEditText = binding.password;
        final Button registerButton = binding.register;
        final ProgressBar loadingProgressBar = binding.loading;

        registerViewModel.getRegisterFormState().observe(getViewLifecycleOwner(), new Observer<RegisterFormState>() {
            @Override
            public void onChanged(@Nullable RegisterFormState registerFormState) {
                if (registerFormState == null) {
                    return;
                }
                registerButton.setEnabled(registerFormState.isDataValid());
                if (registerFormState.getEmailError() != null) {
                    emailEditText.setError(getString(registerFormState.getEmailError()));
                }
                if (registerFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(registerFormState.getPasswordError()));
                }
            }
        });

        registerViewModel.getRegisterResult().observe(getViewLifecycleOwner(), new Observer<RegisterResult>() {
            @Override
            public void onChanged(@Nullable RegisterResult registerResult) {
                if (registerResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (registerResult.getError() != null) {
                    showRegisterFailed(registerResult.getError());
                }
                if (registerResult.getSuccess() != null) {
                    updateUiWithUser(registerResult.getSuccess());
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                registerViewModel.registerDataChanged(Role.valueOf(roleSelector.getSelectedItem().toString().toUpperCase()),
                        emailEditText.getText().toString(),
                        firstNameEditText.getText().toString(),
                        lastNameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        emailEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    registerViewModel.register(Role.valueOf(roleSelector.getSelectedItem().toString().toUpperCase()),
                            emailEditText.getText().toString(),
                            firstNameEditText.getText().toString(),
                            lastNameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                registerViewModel.register(Role.valueOf(roleSelector.getSelectedItem().toString().toUpperCase()),
                        emailEditText.getText().toString(),
                        firstNameEditText.getText().toString(),
                        lastNameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }

    private void updateUiWithUser(RegisteredUserView model) {
        String welcome = getString(R.string.welcome) + " " + model.getDisplayName();

        if (getContext() != null && getContext().getApplicationContext() != null) {
            Toast.makeText(getContext().getApplicationContext(), welcome, Toast.LENGTH_LONG).show();

            ViewGroup fragmentContainerView = getActivity().findViewById(R.id.fragment_container_view);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(fragmentContainerView.getId(), LoginFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void showRegisterFailed(@StringRes Integer errorString) {
        if (getContext() != null && getContext().getApplicationContext() != null) {
            Toast.makeText(
                    getContext().getApplicationContext(),
                    errorString,
                    Toast.LENGTH_LONG).show();
        }
        System.out.println("Registration failed" + errorString);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}