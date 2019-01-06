package com.andrydevelops.cardealership.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.andrydevelops.cardealership.R;
import com.andrydevelops.cardealership.adapter.ModelSpinnerAdapter;
import com.andrydevelops.cardealership.adapter.ProducerSpinnerAdapter;
import com.andrydevelops.cardealership.bean.Car;
import com.andrydevelops.cardealership.bean.Model;
import com.andrydevelops.cardealership.bean.Producer;
import com.andrydevelops.cardealership.database.Query;
import com.andrydevelops.cardealership.callback.ListCallBack;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CarListFragment extends Fragment {

    public static final String TAG = "TAG";
    private RecyclerView mRecyclerView;
    private CarAdapter mAdapter;
    private TextView mProducerTextView;
    private TextView mModelTextView;
    private TextView mPriceTextView;
    private ImageView mCarImageView;
    private ConstraintLayout mConstraintLayout;
    private Spinner mProducerFilterSpinner;
    private Spinner mModelFilterSpinner;
    private ListCallBack mListCallBack;
    private List<Car> mCars;
    private List<Producer> mProducers;
    private List<Model> mModels;
    boolean mIsSortedDown;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListCallBack = (ListCallBack) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mCars = Query.getQuery(getActivity()).getCars();
    }

    @Override
    public void onResume() {
        super.onResume();
        mCars = Query.getQuery(getActivity()).getCars();
        updateUI(mCars);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_list, container, false);
        mRecyclerView = view.findViewById(R.id.car_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.getRecycledViewPool().setMaxRecycledViews(0, 0);
        mConstraintLayout = view.findViewById(R.id.filter_layout);
        mProducerFilterSpinner = view.findViewById(R.id.producer_filter_spinner);
        mModelFilterSpinner = view.findViewById(R.id.model_filter_spinner);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floating_add_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListCallBack.onCarCreated();
            }
        });
        updateUI(mCars);
        return view;
    }


    private class CarHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Car mCar;

        public CarHolder(@NonNull final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mProducerTextView = itemView.findViewById(R.id.producerCardTextView);
            mModelTextView = itemView.findViewById(R.id.modelCardTextView);
            mPriceTextView = itemView.findViewById(R.id.priceCardTextView);
            mCarImageView = itemView.findViewById(R.id.carImageCardView);
        }

        public void bind(Car car) {
            mCar = car;
            mProducerTextView.setText(getString(R.string.producer_params, car.getProducer().getName()));
            mModelTextView.setText(getString(R.string.model_params, car.getModel().getName()));
            mPriceTextView.setText(getString(R.string.price_params, car.getPrice()));

            if (mCar.getImageFileName() != null) {
                Picasso.get()
                        .load(Query.getQuery(getActivity()).getImageFile(mCar, getActivity()))
                        .into(mCarImageView);

            }
        }

        @Override
        public void onClick(View v) {
            mListCallBack.onCarSelected(mCar);
        }
    }

    private class CarAdapter extends RecyclerView.Adapter<CarHolder> {

        private List<Car> mCars;

        public CarAdapter(List<Car> cars) {
            mCars = cars;
        }

        @NonNull
        @Override
        public CarHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_car, viewGroup, false);
            return new CarHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CarHolder carHolder, int i) {
            Car car = mCars.get(i);
            carHolder.bind(car);
        }

        @Override
        public int getItemCount() {
            return mCars.size();
        }

        public void setCars(List<Car> cars) {
            this.mCars = cars;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.car_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.list_filter:
                if (mConstraintLayout.getVisibility() == View.VISIBLE) {
                    mConstraintLayout.setVisibility(View.GONE);
                    updateUI(mCars);
                    mProducerFilterSpinner.setAdapter(null);
                } else {
                    mConstraintLayout.setVisibility(View.VISIBLE);
                    if (mProducers == null) {
                        mProducers = new ArrayList<>();
                        mProducers.add(new Producer(0, getString(R.string.empty_producer)));
                        mProducers.addAll(Query.getQuery(getActivity()).getProducers());
                    }
                    filter();
                }
                return true;
            case R.id.sort_list_by_price:
                mCars = Query.getQuery(getActivity()).getCarsByPrice(mIsSortedDown);
                updateUI(mCars);
                mIsSortedDown = !mIsSortedDown;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListCallBack = null;
    }

    private void filter() {
        mProducerFilterSpinner.setAdapter(new ProducerSpinnerAdapter(getActivity(), mProducers));
        mProducerFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final Producer producer = mProducers.get(position);
                if (!producer.getName().equals(getString(R.string.empty_producer))) {
                    final List<Car> carsByProducer = Query.getQuery(getActivity()).getCarsByProducerId(producer.getId());
                    updateUI(carsByProducer);
                    mModels = new ArrayList<>();
                    mModels.add(new Model(0, getString(R.string.empty_model)));
                    mModels.addAll(Query.getQuery(getActivity()).getModelsByProducerId(producer.getId()));
                    mModelFilterSpinner.setAdapter(new ModelSpinnerAdapter(getActivity(), mModels));
                    mModelFilterSpinner.setVisibility(View.VISIBLE);
                    mModelFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Model model = mModels.get(position);
                            List<Car> cars;
                            if (!model.getName().equals(getString(R.string.empty_model))) {
                                cars = Query.getQuery(getActivity()).getCarsByModelId(model.getId());
                                Log.i(TAG, "size " + cars.size());
                                updateUI(cars);
                            } else {
                                updateUI(carsByProducer);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else {
                    updateUI(mCars);
                    mModelFilterSpinner.setAdapter(null);
                    mModelFilterSpinner.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void updateUI(List<Car> cars) {
        if (mAdapter == null) {
            mAdapter = new CarAdapter(cars);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setCars(cars);
            mAdapter.notifyDataSetChanged();
        }
    }
}
