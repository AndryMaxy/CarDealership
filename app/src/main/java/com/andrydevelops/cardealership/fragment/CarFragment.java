package com.andrydevelops.cardealership.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrydevelops.cardealership.R;
import com.andrydevelops.cardealership.activity.CarActivity;
import com.andrydevelops.cardealership.bean.Car;
import com.andrydevelops.cardealership.database.Query;
import com.andrydevelops.cardealership.callback.CarCallBack;
import com.squareup.picasso.Picasso;

public class CarFragment extends Fragment {

    private Car mCar;
    private CarCallBack mCarCallBack;


    public static CarFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt(CarActivity.ARG_CAR_ID, id);

        CarFragment fragment = new CarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCarCallBack = (CarCallBack) context;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        int id = getArguments().getInt(CarActivity.ARG_CAR_ID);
        mCar = Query.getQuery(getActivity()).getCarById(id);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car, container, false);

        ImageView carImageView = view.findViewById(R.id.carImageButton);
        if (mCar.getImageFileName() != null) {
            Picasso.get()
                    .load(Query.getQuery(getActivity()).getImageFile(mCar,getActivity()))
                    .into(carImageView);
        }

        TextView producerTextView = view.findViewById(R.id.producerTextView);
        producerTextView.setText(getString(R.string.producer_params, mCar.getProducer().getName()));

        TextView modelTextView = view.findViewById(R.id.modelTextView);
        modelTextView.setText(getString(R.string.model_params, mCar.getModel().getName()));

        TextView priceTextView = view.findViewById(R.id.priceTextView);
        priceTextView.setText(getString(R.string.price_params, mCar.getPrice()));

        TextView bodyTextView = view.findViewById(R.id.bodyTypeTextView);
        bodyTextView.setText(getString(R.string.body_type_params, mCar.getBodyType()));

        TextView yearTextView = view.findViewById(R.id.yearTextView);
        yearTextView.setText(getString(R.string.year_of_issue_params, mCar.getYearOfIssue()));

        TextView powerTextView = view.findViewById(R.id.powerTextView);
        powerTextView.setText(getString(R.string.power_params, mCar.getPower()));

        TextView colorTextView = view.findViewById(R.id.colorTextView);
        colorTextView.setText(getString(R.string.color_params, mCar.getColor()));

        TextView transmissionTextView = view.findViewById(R.id.transmissionTextView);
        String transmission = mCar.isAutoTransmission() ? getString(R.string.transmission_variant_yes) : getString(R.string.transmission_variant_no);
        transmissionTextView.setText(getString(R.string.transmission_params, transmission));
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.car_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_edit_car:
                mCarCallBack.replaceFragment(0, mCar.getId());
                return true;
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mCarCallBack = null;
    }
}
