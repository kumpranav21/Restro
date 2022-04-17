package com.example.restro;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputLayout;

public class registrationPage_frag extends Fragment {
    Button btnreg;
    TextView txtWelcome;
    TextInputLayout txtName;
    TextInputLayout textpassword;
    TextInputLayout textconfirmedpassword;
    TextInputLayout textemail;
    TextInputLayout textph;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.frag_registration_pg, container, false);


        btnreg = view.findViewById(R.id.btnreg);
        txtWelcome = view.findViewById(R.id.txtWelcome);
        txtName = view.findViewById(R.id.txtName);
        textconfirmedpassword = view.findViewById(R.id.textconfirmedpassword);
        textph = view.findViewById(R.id.textph);
        textpassword = view.findViewById(R.id.textpassword);
        textemail = view.findViewById(R.id.textemail);

        txtName.getEditText().setText(getArguments().getString("Name"));
        textph.getEditText().setText(getArguments().getString("Phno"));
        textemail.getEditText().setText(getArguments().getString("Email"));

        Login_page_praent.setUnderLineText(txtWelcome, "Welcome to resto!");

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtName.getEditText().getText().toString().equals("") || textemail.getEditText().getText().toString().equals("") || textph.getEditText().getText().toString().equals("") || textpassword.getEditText().getText().toString().equals("") || textconfirmedpassword.getEditText().getText().toString().equals("")) {
                    Toast.makeText(getContext(), "INVALID ENTRY", Toast.LENGTH_SHORT).show();
                } else {
                    if(textpassword.getEditText().getText().toString().equals(textconfirmedpassword.getEditText().getText().toString()))
                    {
                        Bundle bundle = new Bundle();
                        bundle.putString("Name",txtName.getEditText().getText().toString()); // Put anything what you want
                        bundle.putString("Email",textemail.getEditText().getText().toString()); // Put anything what you want
                        bundle.putString("PH",textph.getEditText().getText().toString()); // Put anything what you want
                        bundle.putString("Pass",textpassword.getEditText().getText().toString()); // Put anything what you want


                        Fragment fragment = new addressPage_frag();
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.Login_parent, fragment).addToBackStack(String.valueOf(fragmentManager)).commit();


                    }else {
                        Toast.makeText(getContext(), "PASSWORD MISMATCH", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
        return view;


    }

}
