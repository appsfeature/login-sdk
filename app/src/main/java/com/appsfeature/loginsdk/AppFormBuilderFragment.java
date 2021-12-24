package com.appsfeature.loginsdk;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.formbuilder.fragment.BaseFormBuilderFragment;
import com.formbuilder.util.FBUtility;
import com.formbuilder.util.FieldValidation;

public class AppFormBuilderFragment extends BaseFormBuilderFragment {

    private static final String FIELD_LOCATION = "location";
    private EditText etAddress;

    @Override
    public int getLayoutContentView() {
        return R.layout.pre_registration_list;
    }


    @Override
    public void onInitViews(View view) {
        etAddress = view.findViewById(R.id.et_input_text);
    }

    @Override
    public void onLoadData() {
        etAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FBUtility.showToastCentre(activity, "Update later");
            }
        });
    }

    @Override
    public boolean onValidationCheck() {
        boolean isValidAllFields = true;
        if(!TextUtils.isEmpty(getInputField(FIELD_LOCATION).getValidation())) {
            isValidAllFields = FieldValidation.check(activity, etAddress, getInputField(FIELD_LOCATION).getValidation());
        }
        return isValidAllFields;
    }
}
