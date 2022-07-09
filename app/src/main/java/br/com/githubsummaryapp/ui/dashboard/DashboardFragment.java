package br.com.githubsummaryapp.ui.dashboard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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
            TextView textView_user_blog = binding.textViewUserBlog;
            TextView textView_user_repositories = binding.textViewUserRepositories;
            TextView textView_user_gists = binding.textViewUserGists;
            TextView textView_user_followers = binding.textViewUserFollowers;
            TextView textView_user_following = binding.textViewUserFollowing;
            TextView textView_user_more_info = binding.textViewUserMoreInfo;
            Picasso.get().load(user.getAvatar_url()).into(imageViewUser);
            textView_user_name.setText(user.getName());
            textView_user_location.setText(user.getLocation());
            textView_user_email.setText(user.getEmail());
            textView_user_company.setText(user.getCompany());
            textView_user_repositories.setText(String.valueOf(user.getPublic_repos()));
            textView_user_blog.setText(user.getBlog());
            textView_user_gists.setText(String.valueOf(user.getPublic_gists()));
            textView_user_followers.setText(String.valueOf(user.getFollowers()));
            textView_user_following.setText(String.valueOf(user.getFollowing()));
            textView_user_more_info.setText(user.getHtml_url());
        }


    }
}