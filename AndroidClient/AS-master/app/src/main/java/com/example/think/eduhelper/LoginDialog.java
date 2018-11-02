package com.example.think.eduhelper;

import android.accounts.Account;
import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.think.eduhelper.HttpClient.HttpCallback;
import com.example.think.eduhelper.HttpClient.HttpHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

import com.example.think.eduhelper.UserLoginDB.User;

import org.json.JSONObject;
import java.util.List;

public class LoginDialog extends DialogFragment {
    private EditText name,password;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.login_dialog,null));
;
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Dialog dialogView = (Dialog) dialog;
                name = dialogView.findViewById(R.id.editText_LoginName);
                password = dialogView.findViewById(R.id.editText_LoginPassword);
                String userName = name.getText().toString();
                String userPassword = password.getText().toString();

                Log.d("name",name.getText().toString());
                Map<String, String> params = new HashMap<String, String>();
                params.put("userName", userName);
                params.put("userPassword", userPassword);
                JSONObject parameter = new JSONObject((params));
                //Start OKHTTP REQUEST
                HttpHelper.getInstance().postData(parameter, "login", new HttpCallback() {
                    @Override
                    public void postDataCallback(String Data) {
                    }

                });
                name.setText("");
                password.setText("");

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(),"Return to the main page", Toast.LENGTH_SHORT).show();

            }
        });
        return builder.create();
    }

    public Boolean ifMatch(String name, String password){
        List<User> users= MainActivity.userInfoDatabase.userDao().getUsers();

        for(User user:users){
            if (user.getName().equals(name)){
                if (user.getPassword().equals(password)) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}
