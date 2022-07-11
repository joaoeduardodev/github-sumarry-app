package br.com.githubsummaryapp.ui.user;

import br.com.githubsummaryapp.databinding.FragmentUserBinding;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import br.com.githubsummaryapp.R;
import br.com.githubsummaryapp.db.FavoriteUsersDAO;
import br.com.githubsummaryapp.domain.FavoriteUsers;
import br.com.githubsummaryapp.domain.User;

public class UserFragment extends Fragment {

    private FragmentUserBinding binding;
    private User user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Object taskArguments = null;
        if (getArguments() != null){
            taskArguments = getArguments().getSerializable("user");
        }
        if (taskArguments != null) {
            binding.tableLayoutUser.setVisibility(View.VISIBLE);
            user = (User) taskArguments;
            FavoriteUsersDAO dao = new FavoriteUsersDAO(getContext());
            FavoriteUsers userFavorite = dao.findByUser(user.getLogin());
            Boolean userIsFavorite = userFavorite != null;

            if (userIsFavorite) {
                binding.floatingActionButtonAddFavoriteSelected.setVisibility(View.VISIBLE);
            } else {
                binding.floatingActionButtonAddFavorite.setVisibility(View.VISIBLE);
            }

            binding.floatingActionButtonAddFavorite.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {


                     FavoriteUsersDAO dao = new FavoriteUsersDAO(getContext());
                     FavoriteUsers favoriteUsers = new FavoriteUsers(0,
                             user.getLogin(),
                             user.getAvatar_url(),
                             user.getUrl(),
                             user.getHtml_url(),
                             user.getName(),
                             user.getCompany(),
                             user.getBlog(),
                             user.getLocation(),
                             user.getEmail(),
                             user.getBio(),
                             user.getPublic_repos(),
                             user.getPublic_gists(),
                             user.getFollowers(),
                             user.getFollowing());
                     System.out.println(favoriteUsers.toString());
                     try {
                         dao.save(favoriteUsers);
                         Snackbar snackbarOnSuccessAddToFavorite = Snackbar.make(getView(), R.string.added_user_to_favorite, Snackbar.LENGTH_LONG);
                         snackbarOnSuccessAddToFavorite.show();
                         binding.floatingActionButtonAddFavoriteSelected.setVisibility(View.VISIBLE);
                         binding.floatingActionButtonAddFavorite.setVisibility(View.INVISIBLE);
                     } catch (Exception exception) {
                         Log.d("ErrorInAddToFavorite", " Error: " + exception);
                         Snackbar snackbarOnFailureAddToFavorite = Snackbar.make(getView(), R.string.error_in_add_user_to_favorite, Snackbar.LENGTH_LONG);
                         snackbarOnFailureAddToFavorite.show();
                     }
                 }
             });

            binding.floatingActionButtonAddFavoriteSelected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        dao.delete(userFavorite);
                        Snackbar snackbarOnSuccessAddToFavorite = Snackbar.make(getView(), R.string.user_removed_from_favorite, Snackbar.LENGTH_LONG);
                        snackbarOnSuccessAddToFavorite.show();
                        binding.floatingActionButtonAddFavoriteSelected.setVisibility(View.INVISIBLE);
                        binding.floatingActionButtonAddFavorite.setVisibility(View.VISIBLE);
                    } catch (Exception exception) {
                        Log.d("ErrorInRemoveToFavorite", " Error: " + exception);
                        Snackbar snackbarOnFailureAddToFavorite = Snackbar.make(getView(), R.string.error_removing_user_from_favorite, Snackbar.LENGTH_LONG);
                        snackbarOnFailureAddToFavorite.show();
                    }
                }
            });
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences preferences = getContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        Boolean connected_internet = preferences.getBoolean("connected_internet", false);

        Object taskArguments = null;
        if (getArguments() != null){
            taskArguments = getArguments().getSerializable("user");
        }
        if (taskArguments != null){
            binding.tableLayoutUser.setVisibility(View.VISIBLE);
            user = (User) taskArguments;
            ImageView imageViewUser = binding.imageViewUser;
            TextView textView_user_login = binding.textViewUserLogin;
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
            if (connected_internet){
                Picasso.get().load(user.getAvatar_url()).into(imageViewUser);
            }
            textView_user_login.setText(user.getLogin());
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
        } else {
            binding.textViewDashboard.setVisibility(View.VISIBLE);
        }

    }
}