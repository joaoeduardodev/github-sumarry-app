package br.com.githubsummaryapp.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import br.com.githubsummaryapp.databinding.FragmentFavoriteBinding;
import br.com.githubsummaryapp.databinding.FragmentFavoriteBinding;
import br.com.githubsummaryapp.db.FavoriteUsersDAO;
import br.com.githubsummaryapp.domain.FavoriteUsers;

public class FavoriteFragment extends Fragment {

    private FragmentFavoriteBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
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

        listFavoriteUsers();
    }

    private void listFavoriteUsers() {
        FavoriteUsersDAO dao = new FavoriteUsersDAO(getContext());
        List<FavoriteUsers> favoriteUsersList = dao.listAll();

        ArrayAdapter  adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, favoriteUsersList);

        binding.ListViewFavorite.setAdapter(adapter);
    }
}