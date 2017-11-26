package com.lavekush.telstra.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.lavekush.telstra.R;
import com.lavekush.telstra.networking.NetworkApiInterface;
import com.lavekush.telstra.networking.RetrofitClient;
import com.lavekush.telstra.util.Util;
import com.lavekush.telstra.vo.DataContainer;
import com.lavekush.telstra.vo.RowItem;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemFragment extends Fragment {

    private static final String TAG = ItemFragment.class.getSimpleName();
    private OnListFragmentInteractionListener mListener;
    private static final int CONTAINER_ERROR = 1;
    private static final int CONTAINER_LOADER = 0;
    private static final int CONTAINER_DATA = 2;
    private ViewFlipper mViewFlipper;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private TextView mTextError;

    /**
     * Empty public constructor require if we are going to give factory method (But its optional)
     */
    public ItemFragment() {
    }


    /**
     * Factory for activity class in order easily apply basic validation
     * @param args
     * @return
     */
    public static ItemFragment newInstance(Bundle args) {
        ItemFragment fragment = new ItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        //UI component init
        mTextError = view.findViewById(R.id.text_error_message);
        mViewFlipper = view.findViewById(R.id.main_view_flipper);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_to_list);
        mRecyclerView = view.findViewById(R.id.list);


        //showing loader
        mViewFlipper.setDisplayedChild(CONTAINER_LOADER);

        //adding  swipe to refresh listener
        addSwipeToRefresh();

        //checking for internet
        if (Util.isConnectedWithInternet(view.getContext())) {

            //init api call
            NetworkApiInterface apiService =
                    RetrofitClient.getNetworkClient().create(NetworkApiInterface.class);

            Call<ResponseBody> call = apiService.getRowData();
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    // API success case
                    if (response.code() == 200) { // checking for http error code
                        try {
                            String data = response.body().string();
                            DataContainer dataContainer = new Gson().fromJson(data, DataContainer.class);
                            Log.d("DataContainer", dataContainer.toString());

                            // Set the adapter
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                            mRecyclerView.setAdapter(new ItemRecyclerViewAdapter(dataContainer.getItems(), mListener));
                            mViewFlipper.setDisplayedChild(CONTAINER_DATA);

                            //set actionbar title
                            ((Toolbar) getActivity().findViewById(R.id.toolbar)).setTitle(dataContainer.getTitle());

                        } catch (IOException e) {
                            //Error condition
                            e.printStackTrace();
                            mViewFlipper.setDisplayedChild(CONTAINER_ERROR);
                            mTextError.setError(e.getMessage());
                            mTextError.setText(e.getMessage());
                        }
                    } else {
                        //Error condition
                        mViewFlipper.setDisplayedChild(CONTAINER_ERROR);
                        mTextError.setError("Some error occurred, please try later. ");
                        mTextError.setText("Some error occurred, please try later. ");

                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e(TAG, t.toString());
                    mViewFlipper.setDisplayedChild(CONTAINER_ERROR);
                    mTextError.setError("Some error occurred, please try later. ");
                    mTextError.setText("Some error occurred, please try later. ");
                }
            });
        } else {
            mViewFlipper.setDisplayedChild(CONTAINER_ERROR);
            mTextError.setError(getString(R.string.error_offline_text));
            mTextError.setText(R.string.error_offline_text);
        }


        //adding re-load button
        view.findViewById(R.id.btn_load_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewFlipper.setDisplayedChild(CONTAINER_LOADER);
                refreshItems();
            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * Interface for propagating the click listener to activity
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(RowItem item);
    }


    /**
     * Adding swipe-to-refresh listener for list items
     */
    private void addSwipeToRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });
    }


    /**
     * Refreshing the item using swipe-to-refresh android built in componant
     */
    private void refreshItems() {

        if (Util.isConnectedWithInternet(getActivity().getApplicationContext())) {

            //init api call
            NetworkApiInterface apiService =
                    RetrofitClient.getNetworkClient().create(NetworkApiInterface.class);

            Call<ResponseBody> call = apiService.getRowData();
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    /* Load complete updating the data*/

                    // Stop refresh animation
                    mSwipeRefreshLayout.setRefreshing(false);

                    // API success case
                    if (response.code() == 200) { // checking for http error code
                        try {
                            String data = response.body().string();
                            DataContainer dataContainer = new Gson().fromJson(data, DataContainer.class);
                            Log.d("DataContainer", dataContainer.toString());

                            if ( mRecyclerView.getAdapter() != null) {

                                // Updating the adapter values
                                ((ItemRecyclerViewAdapter) mRecyclerView.getAdapter()).setItems(dataContainer.getItems());

                                //Notifying the views to update thierself
                                mRecyclerView.getAdapter().notifyDataSetChanged();
								mViewFlipper.setDisplayedChild(CONTAINER_DATA);


                            } else {
                                // No adapter set yet, setting it
                                mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
                                mRecyclerView.setAdapter(new ItemRecyclerViewAdapter(dataContainer.getItems(), mListener));
                                mViewFlipper.setDisplayedChild(CONTAINER_DATA);

                            }
                            //Updating actionbar title
                            ((Toolbar) getActivity().findViewById(R.id.toolbar)).setTitle(dataContainer.getTitle());

                            Util.showSnackBarIndefinite(getActivity().getApplicationContext(), getView(), "List updated successfully.");

                        } catch (IOException e) {
                            //Error condition
                            Log.e(TAG, "Exception,e");
                            if (getActivity() != null) {
                                Util.showSnackBarIndefinite(getActivity().getApplicationContext(), getView(), e.getMessage());
                                mViewFlipper.setDisplayedChild(CONTAINER_ERROR);
                                mTextError.setError(e.getMessage());
                                mTextError.setText(e.getMessage());
                            }
                        }
                    } else {
                        //Error condition
                        if (getActivity() != null) {
                            Util.showSnackBarIndefinite(getActivity().getApplicationContext(), getView(), "Some error occurred, please try later.");
                            mViewFlipper.setDisplayedChild(CONTAINER_ERROR);
                            mTextError.setError("Some error occurred, please try later.");
                            mTextError.setText("Some error occurred, please try later.");
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    // Stop refresh animation
                    mSwipeRefreshLayout.setRefreshing(false);

                    Log.e(TAG, t.toString());
                    if (getActivity() != null) {
                        Util.showSnackBarIndefinite(getActivity().getApplicationContext(), getView(), t.getMessage());
                        mViewFlipper.setDisplayedChild(CONTAINER_ERROR);
                        mTextError.setError(t.getMessage());
                        mTextError.setText(t.getMessage());
                    }
                }
            });
        } else {

            // Stop refresh animation
            mSwipeRefreshLayout.setRefreshing(false);

            if (getActivity() != null) {
                Util.showSnackBarIndefinite(getActivity().getApplicationContext(), getView(), getString(R.string.error_offline_text));
                mViewFlipper.setDisplayedChild(CONTAINER_ERROR);
                mTextError.setError(getString(R.string.error_offline_text));
                mTextError.setText(R.string.error_offline_text);
            }
        }
    }
}
