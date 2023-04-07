package com.zhq.devtools.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.zhq.devtools.databinding.FragmentTwoBinding;
import com.zhq.devtools.ui.jetpack.view_model.TestViewModel;


public class TwoFragment extends Fragment {

    private com.zhq.devtools.databinding.FragmentTwoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTwoBinding.inflate(inflater);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        TestViewModel testViewModel = new ViewModelProvider(this.getActivity()).get(TestViewModel.class);
        MutableLiveData<Integer> mutableLiveDataProgress = testViewModel.getProgress();
        mutableLiveDataProgress.observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.seekbar.setProgress(integer);
            }
        });

        binding.seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mutableLiveDataProgress.setValue(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}