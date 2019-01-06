package com.andrydevelops.cardealership.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import com.andrydevelops.cardealership.R;
import com.andrydevelops.cardealership.activity.CarActivity;
import com.andrydevelops.cardealership.adapter.BodyTypeAdapter;
import com.andrydevelops.cardealership.adapter.ModelSpinnerAdapter;
import com.andrydevelops.cardealership.adapter.ProducerSpinnerAdapter;
import com.andrydevelops.cardealership.bean.CarBodyType;
import com.andrydevelops.cardealership.bean.Model;
import com.andrydevelops.cardealership.bean.Producer;
import com.andrydevelops.cardealership.database.Query;
import com.squareup.picasso.Picasso;

public class EditCarFragment extends NewCarFragment implements View.OnClickListener {

    private ModelSpinnerAdapter mModelSpinnerAdapter;
    private Spinner mProducerSpinner;
    private Spinner mModelSpinner;

    public static EditCarFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(CarActivity.ARG_CAR_ID, id);

        EditCarFragment fragment = new EditCarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        int id = getArguments().getInt(CarActivity.ARG_CAR_ID);
        mCar = Query.getQuery(getActivity()).getCarById(id);
    }


    private void initImage(){
        if (mCar.getImageFileName() == null) {
            mImageFile = Query.getQuery(getActivity()).getImageFile(mCar, getActivity());
        } else {
            Picasso.get()
                    .load(mImageFile)
                    .into(mCarImageView);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_car, container, false);
        mCarImageView = view.findViewById(R.id.carEditImageButton);
        mCarImageView.setOnClickListener(this);

        initImage();

        mProducerSpinner = view.findViewById(R.id.producerSpinner);
        mModelSpinner = view.findViewById(R.id.modelSpinner);
        mModelSpinner.setVisibility(View.VISIBLE);
        mProducers = Query.getQuery(getActivity()).getProducers();
        mProducerSpinner.setAdapter(new ProducerSpinnerAdapter(getActivity(), mProducers));
        mProducerSpinner.setSelection(mCar.getProducer().getId() - 1);

        mModels = Query.getQuery(getActivity()).getModelsByProducerId(mCar.getProducer().getId());
        mModelSpinnerAdapter = new ModelSpinnerAdapter(getActivity(), mModels);
        mModelSpinner.setAdapter(mModelSpinnerAdapter);
        mModelSpinnerAdapter.selectModel(mCar.getModel().getId());

        mProducerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Producer producer = mProducers.get(position);
                mCar.setProducer(producer);

                mModels = Query.getQuery(getActivity()).getModelsByProducerId(producer.getId());
                mModelSpinnerAdapter = new ModelSpinnerAdapter(getActivity(), mModels);
                mModelSpinner.setAdapter(mModelSpinnerAdapter);
                mModelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Model model = mModels.get(position);
                        mCar.setModel(model);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        EditText priceEditText = view.findViewById(R.id.priceEditText);
        priceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCar.setPrice(Integer.parseInt(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Spinner bodyType = view.findViewById(R.id.bodyTypeSpinner);
        bodyType.setAdapter(new BodyTypeAdapter(getActivity()));
        bodyType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CarBodyType body = CarBodyType.values()[position];
                mCar.setBodyType(body);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        EditText yearEditText = view.findViewById(R.id.yearEditText);
        yearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCar.setYearOfIssue(Integer.parseInt(s.toString()));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        EditText powerEditText = view.findViewById(R.id.powerEditText);
        powerEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCar.setPower(Integer.parseInt(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        CheckBox transmissionCheckBox = view.findViewById(R.id.transmissionCheckBox);
        transmissionCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCar.setAutoTransmission(isChecked);
            }
        });

        EditText colorEditText = view.findViewById(R.id.colorEditText);
        colorEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCar.setColor(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        priceEditText.setText(String.valueOf(mCar.getPrice()));
        bodyType.setSelection(mCar.getBodyType().ordinal());
        yearEditText.setText(String.valueOf(mCar.getYearOfIssue()));
        powerEditText.setText(String.valueOf((mCar.getPower())));
        transmissionCheckBox.setChecked(mCar.isAutoTransmission());
        colorEditText.setText(mCar.getColor());
        return view;
    }

    @Override
    protected void action() {
        Query.getQuery(getActivity()).updateCar(mCar);
        mCarCallBack.replaceFragment(CarActivity.FRAGMENT_CAR, mCar.getId());
    }
}

