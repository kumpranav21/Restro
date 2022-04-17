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

import com.google.android.material.textfield.TextInputLayout;


public class addressPage_frag extends Fragment {
    TextView txtaddress;
    TextInputLayout txtHouse_no;
    TextInputLayout txtPincode;
    TextInputLayout txtlocality;
    TextInputLayout txtlandmark;
    Button btnAdd;
    String Name;
    String Pass;
    String Phone;
    AppDatabase db;
    String Email;
    String Add;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.frag_address, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        Bundle bundle = this.getArguments();

        Name = bundle.getString("Name");
        Pass = bundle.getString("Pass");
        Phone = bundle.getString("PH");
        Email = bundle.getString("Email");

        txtaddress = view.findViewById(R.id.txtaddress);
        txtHouse_no = view.findViewById(R.id.txtHouse_no);
        txtPincode = view.findViewById(R.id.txtPincode);
        txtlocality = view.findViewById(R.id.txtlocality);
        txtlandmark = view.findViewById(R.id.txtlandmark);
        setUnderLineText(txtaddress, "Confirm your address");
        btnAdd = view.findViewById(R.id.btnAddress);
        db = dbBuild();
        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (txtHouse_no.getEditText().getText().toString().equals("") || txtPincode.getEditText().getText().toString().equals("") || txtlocality.getEditText().getText().toString().equals("") || txtlandmark.getEditText().getText().toString().equals("")) {
                    Toast.makeText(getContext(), "INVALID ENTRY", Toast.LENGTH_SHORT).show();
                } else {
                    Add = txtHouse_no.getEditText().getText().toString() + txtlocality.getEditText().getText().toString() + txtlandmark.getEditText().getText().toString() + txtPincode.getEditText().getText().toString();
                    Intent i = new Intent(getActivity(), HomeFragmentParent.class);
                    UserEntities entities = new UserEntities();
                    entities.U_name = Name;
                    entities.U_Email = Email;
                    entities.U_Ph = Phone;
                    entities.U_Pass = Pass;
                    entities.U_ADD = Add;
                    db.U_Dao().insertAll(entities);
                    startActivity(i);
                    getActivity().onBackPressed();
                }

            }
        });

        return view;


    }


    public void setUnderLineText(TextView tv, String textToUnderLine) {
        String tvt = tv.getText().toString();
        int ofe = tvt.indexOf(textToUnderLine, 0);

        UnderlineSpan underlineSpan = new UnderlineSpan();
        SpannableString wordToSpan = new SpannableString(tv.getText());
        for (int ofs = 0; ofs < tvt.length() && ofe != -1; ofs = ofe + 1) {
            ofe = tvt.indexOf(textToUnderLine, ofs);
            if (ofe == -1)
                break;
            else {
                wordToSpan.setSpan(underlineSpan, ofe, ofe + textToUnderLine.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv.setText(wordToSpan, TextView.BufferType.SPANNABLE);
            }
        }
    }
    public AppDatabase dbBuild() {
       return restaurant_info.buildDB(getContext());
    }


}
