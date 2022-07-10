package br.com.githubsummaryapp.ui.favorite;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.List;

import br.com.githubsummaryapp.R;
import br.com.githubsummaryapp.config.RetrofitConfig;
import br.com.githubsummaryapp.databinding.FragmentFavoriteBinding;
import br.com.githubsummaryapp.databinding.FragmentFavoriteBinding;
import br.com.githubsummaryapp.db.FavoriteUsersDAO;
import br.com.githubsummaryapp.domain.FavoriteUsers;
import br.com.githubsummaryapp.domain.SearchHistory;
import br.com.githubsummaryapp.domain.User;
import br.com.githubsummaryapp.utils.InternetVerification;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteFragment extends Fragment {

    private FragmentFavoriteBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerForContextMenu(binding.ListViewFavorite);

        binding.ListViewFavorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                FavoriteUsers favoriteUser = (FavoriteUsers) binding.ListViewFavorite.getItemAtPosition(position);
                FavoriteUsersDAO dao = new FavoriteUsersDAO(getContext());
                FavoriteUsers userFavorite = dao.findByUser(favoriteUser.getLogin());

                Bundle bundle = new Bundle();
                User user =new User(
                        userFavorite.getLogin(),
                        null,
                        null,
                        userFavorite.getAvatar_url(),
                        null,
                        userFavorite.getUrl(),
                        userFavorite.getHtml_url(),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        userFavorite.getName(),
                        userFavorite.getCompany(),
                        userFavorite.getBlog(),
                        userFavorite.getLocation(),
                        userFavorite.getEmail(),
                        null,
                        userFavorite.getBio(),
                        null,
                        userFavorite.getPublic_repos(),
                        userFavorite.getPublic_gists(),
                        userFavorite.getFollowers(),
                        userFavorite.getFollowing(),
                        null,
                        null
                );

                bundle.putSerializable("user", user);
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.action_navigation_favorite_to_navigation_dashboard, bundle);

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();

        listFavoriteUsers();
    }

    private void listFavoriteUsers() {
        FavoriteUsersDAO dao = new FavoriteUsersDAO(getContext());
        List<FavoriteUsers> favoriteUsersList = dao.listAll();

        if(!favoriteUsersList.isEmpty()){
            binding.ListViewFavorite.setVisibility(View.VISIBLE);
            ArrayAdapter  adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, favoriteUsersList);
            binding.ListViewFavorite.setAdapter(adapter);
        } else {
            binding.textViewFavorite.setVisibility(View.VISIBLE);
        }


    }
}