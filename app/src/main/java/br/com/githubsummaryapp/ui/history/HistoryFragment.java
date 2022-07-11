package br.com.githubsummaryapp.ui.history;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.List;

import br.com.githubsummaryapp.R;
import br.com.githubsummaryapp.config.RetrofitConfig;
import br.com.githubsummaryapp.databinding.FragmentHistoryBinding;
import br.com.githubsummaryapp.db.SearchHistoryDAO;
import br.com.githubsummaryapp.domain.SearchHistory;
import br.com.githubsummaryapp.domain.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registerForContextMenu(binding.ListViewSearchHistory);

        binding.ListViewSearchHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                SearchHistory searchHistory = (SearchHistory) binding.ListViewSearchHistory.getItemAtPosition(position);
                Call<User> call = new RetrofitConfig().getGitHubService().getUser(searchHistory.getUser());

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            User user = response.body();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("user", user);
                            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                            navController.navigate(R.id.action_navigation_history_to_navigation_user, bundle);
                        } else {
                            Log.d("ErrorInRequest", "StatusCode: " +response.code()+ "Error: " + response.body() );
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e("FailureInRequest ", "Error: " + t.getMessage());
                    }
                });

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

        listSearchHistory();
    }

    private void listSearchHistory() {
        SearchHistoryDAO dao = new SearchHistoryDAO(getContext());
        List<SearchHistory> searchHistoryList = dao.listAll();

        if(!searchHistoryList.isEmpty()){
            binding.ListViewSearchHistory.setVisibility(View.VISIBLE);
            ArrayAdapter  adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, searchHistoryList);
            binding.ListViewSearchHistory.setAdapter(adapter);
        } else {
            binding.textViewHistory.setVisibility(View.VISIBLE);
        }
        
    }

}