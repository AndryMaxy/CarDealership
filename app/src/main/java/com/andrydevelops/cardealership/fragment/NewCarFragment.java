package com.andrydevelops.cardealership.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.andrydevelops.cardealership.R;
import com.andrydevelops.cardealership.activity.CarActivity;
import com.andrydevelops.cardealership.adapter.BodyTypeAdapter;
import com.andrydevelops.cardealership.adapter.ModelSpinnerAdapter;
import com.andrydevelops.cardealership.adapter.ProducerSpinnerAdapter;
import com.andrydevelops.cardealership.bean.Car;
import com.andrydevelops.cardealership.bean.CarBodyType;
import com.andrydevelops.cardealership.bean.Model;
import com.andrydevelops.cardealership.bean.Producer;
import com.andrydevelops.cardealership.callback.CarCallBack;
import com.andrydevelops.cardealership.database.Query;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class NewCarFragment extends Fragment implements View.OnClickListener {

    private static final int GALLERY_REQUEST = 1;
    protected ImageView mCarImageView;
    protected List<Producer> mProducers;
    protected List<Model> mModels;
    protected CarCallBack mCarCallBack;
    protected Car mCar;
    protected File mImageFile;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCarCallBack = (CarCallBack) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mCar = new Car();
        mImageFile = Query.getQuery(getActivity()).getImageFile(mCar, getActivity());
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_car, container, false);
        mCarImageView = view.findViewById(R.id.carEditImageButton);
        mCarImageView.setOnClickListener(this);

        final Spinner producerSpinner = view.findViewById(R.id.producerSpinner);
        final Spinner modelSpinner = view.findViewById(R.id.modelSpinner);
        initLists();
        producerSpinner.setAdapter(new ProducerSpinnerAdapter(getActivity(), mProducers));
        producerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Producer producer = mProducers.get(position);
                mCar.setProducer(producer);

                mModels.clear();
                initModelList();
                mModels.addAll(Query.getQuery(getActivity()).getModelsByProducerId(producer.getId()));
                if (!producer.getName().equals(getString(R.string.empty_producer))) {
                    modelSpinner.setVisibility(View.VISIBLE);
                }
                modelSpinner.setAdapter(new ModelSpinnerAdapter(getActivity(), mModels));
                modelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.carEditImageButton:
                Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
                photoPickerIntent.setType("image/*");
                //FileProvider.getUriForFile(getActivity(), PROVIDER_AUTHORITY, mImageFile);
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {
                    setImage(data);
                    break;
                }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.car_edit_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.apply_edit:
                if (mCar.getProducer().getId() == 0 || mCar.getModel().getId() == 0 || mCar.getPrice() == 0) {
                    Toast.makeText(getActivity(), R.string.toast_fill, Toast.LENGTH_SHORT).show();
                } else {
                    action();
                }
                return true;
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void action(){
        if (mCar.getColor() == null) {
            mCar.setColor(getString(R.string.text_empty));
        }
        int id = Query.getQuery(getActivity()).addCar(mCar);
        mCarCallBack.replaceFragment(CarActivity.FRAGMENT_CAR, id);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCarCallBack = null;
    }

    private void initLists(){
        mProducers = new ArrayList<>();
        mProducers.add(new Producer(0, getString(R.string.empty_producer)));
        mProducers.addAll(Query.getQuery(getActivity()).getProducers());
        initModelList();
    }

    private void initModelList(){
        mModels = new ArrayList<>();
        mModels.add(new Model(0, getString(R.string.empty_model)));
    }


    private void setImage(Intent data) {
        final int chunkSize = 1024;
        byte[] imageData = new byte[chunkSize];
        try {
            try (InputStream in = getActivity().getContentResolver().openInputStream(data.getData());
                 OutputStream out = new FileOutputStream(mImageFile)) {
                int bytesRead;
                while ((bytesRead = in.read(imageData)) > 0) {
                    out.write(Arrays.copyOfRange(imageData, 0, Math.max(0, bytesRead)));
                }
            }
        } catch (IOException exception) {
            Log.e("", "Something went wrong.", exception);
        }
        Picasso.get().load(mImageFile).into(mCarImageView);
    }

}

