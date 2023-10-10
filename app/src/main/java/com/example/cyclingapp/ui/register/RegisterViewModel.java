package com.example.cyclingapp.ui.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cyclingapp.R;
import com.example.cyclingapp.data.Register;
import com.example.cyclingapp.data.Result;
import com.example.cyclingapp.data.model.Role;
import com.example.cyclingapp.data.Validation;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>();
    private MutableLiveData<RegisterResult> registerResult = new MutableLiveData<>();

    LiveData<RegisterFormState> getRegisterFormState() {
        return registerFormState;
    }

    LiveData<RegisterResult> getRegisterResult() {
        return registerResult;
    }

    public void register(Role role, String email, String firstName, String lastName, String password) {
        // can be launched in a separate asynchronous job
        Result<Register> result = new Register().register(role, email, firstName, lastName, password);

        if (result instanceof Result.Success) {
            registerResult.setValue(new RegisterResult(new RegisteredUserView(((Result.Success<Register>) result).getData().getDisplayName())));
        } else {
            registerResult.setValue(new RegisterResult(R.string.register_failed));
        }
    }

    public void registerDataChanged(Role role, String email, String firstName, String lastName, String password) {
        if (!Validation.isEmailValid(email)) {
            registerFormState.setValue(new RegisterFormState(R.string.invalid_email, null));
        } else if (!Validation.isPasswordValid(password)) {
            registerFormState.setValue(new RegisterFormState(null, R.string.invalid_password));
        } else {
            registerFormState.setValue(new RegisterFormState(true));
        }
    }
}