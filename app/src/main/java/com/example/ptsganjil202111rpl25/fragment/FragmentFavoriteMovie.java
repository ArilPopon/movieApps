package com.example.ptsganjil202111rpl25.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ptsganjil202111rpl25.R;
import com.example.ptsganjil202111rpl25.activities.DetailMovieActivity;
import com.example.ptsganjil202111rpl25.adapter.MovieAdapter;
import com.example.ptsganjil202111rpl25.model.ModelMovie;
import com.example.ptsganjil202111rpl25.realm.RealmHelper;

import java.util.ArrayList;
import java.util.List;

public class FragmentFavoriteMovie extends Fragment implements MovieAdapter.onSelectData {

    private RecyclerView rvMovieFav;
    private List<ModelMovie> modelMovie = new ArrayList<>();
    private RealmHelper helper;
    private TextView txtNoData;

    public FragmentFavoriteMovie() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_favorite_film, container, false);

        helper = new RealmHelper(getActivity());

        txtNoData = rootView.findViewById(R.id.tvNotFound);
        rvMovieFav = rootView.findViewById(R.id.rvMovieFav);
        rvMovieFav.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovieFav.setAdapter(new MovieAdapter(getActivity(), modelMovie, this));
        rvMovieFav.setHasFixedSize(true);

        setData();

        return rootView;
    }

    private void setData() {
        modelMovie = helper.showFavoriteMovie();
        if (modelMovie.size() == 0) {
            txtNoData.setVisibility(View.VISIBLE);
            rvMovieFav.setVisibility(View.GONE);
        } else {
            txtNoData.setVisibility(View.GONE);
            rvMovieFav.setVisibility(View.VISIBLE);
            rvMovieFav.setAdapter(new MovieAdapter(getActivity(), modelMovie, this));
        }
    }

    @Override
    public void onSelected(ModelMovie modelMovie) {
        Intent intent = new Intent(getActivity(), DetailMovieActivity.class);
        intent.putExtra("detailMovie", modelMovie);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }
}
