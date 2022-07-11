package br.com.githubsummaryapp.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;

import br.com.githubsummaryapp.R;
import br.com.githubsummaryapp.config.RetrofitConfig;
import br.com.githubsummaryapp.databinding.FragmentHomeBinding;
import br.com.githubsummaryapp.db.SearchHistoryDAO;
import br.com.githubsummaryapp.domain.SearchHistory;
import br.com.githubsummaryapp.domain.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private SearchHistory searchHistory;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        SharedPreferences preferences = getContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        Boolean connected_internet = preferences.getBoolean("connected_internet", false);

        if (connected_internet == false){
            binding.editTextUserGitHub.setVisibility(View.INVISIBLE);
            binding.buttonSearch.setVisibility(View.INVISIBLE);
            binding.textViewHome.setVisibility(View.VISIBLE);
        }
        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editTextUserGitHub = binding.editTextUserGitHub;

                String user = editTextUserGitHub.getText().toString();
                user = user != null ? user : "";

                if(user.equals("")){
                    Toast.makeText(getContext(), R.string.user_input_empty,
                            Toast.LENGTH_SHORT).show();
                } else {
                    SearchHistory searchHistory = new SearchHistory(0, user);
                    searchHistory.setUser(user);
                    SearchHistoryDAO dao = new SearchHistoryDAO(getContext());
                    dao.save(searchHistory);

                    System.out.println(dao.listAll());
                }

                Call<User> call = new RetrofitConfig().getGitHubService().getUser(user);

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            User user = response.body();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("user", user);
                            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                            navController.navigate(R.id.action_navigation_home_to_navigation_user, bundle);
                        } else if (response.code() == 404 ) {
                            Snackbar snackbarOnFailureRequest = Snackbar.make(getView(), R.string.user_not_found, Snackbar.LENGTH_LONG);
                            snackbarOnFailureRequest.show();
                        } else {
                            Log.d("ErrorInRequest", "StatusCode: " +response.code()+ "Error: " + response.body() );
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e("FailureInRequest", "Error: " + t.getMessage());
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

}