package br.com.githubsummaryapp.ui.dashboard;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.net.URL;

import br.com.githubsummaryapp.databinding.FragmentDashboardBinding;
import br.com.githubsummaryapp.domain.User;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private User user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onResume() {
        super.onResume();
        Object taskArguments = null;
        if (getArguments() != null){
            taskArguments = getArguments().getSerializable("user");
        }
        if (taskArguments != null){
            binding.tableLayoutUser.setVisibility(View.VISIBLE);
            user = (User) taskArguments;
            ImageView imageViewUser = binding.imageViewUser;
            TextView textView_user_name = binding.textViewUserName;
            TextView textView_user_location = binding.textViewUserLocation;
            TextView textView_user_email = binding.textViewUserEmail;
            TextView textView_user_company = binding.textViewUserCompany;
            Picasso.get().load(user.getAvatar_url()).into(imageViewUser);
            textView_user_name.setText(user.getName());
            textView_user_location.setText(user.getLocation());
            textView_user_email.setText(user.getEmail());
            textView_user_company.setText(user.getCompany());
        }


    }
}