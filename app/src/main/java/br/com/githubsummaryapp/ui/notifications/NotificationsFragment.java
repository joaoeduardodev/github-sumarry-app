package br.com.githubsummaryapp.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.githubsummaryapp.R;
import br.com.githubsummaryapp.databinding.FragmentNotificationsBinding;
import br.com.githubsummaryapp.db.SearchHistoryDAO;
import br.com.githubsummaryapp.domain.SearchHistory;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
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

        listSearchHistory();
    }

    private void listSearchHistory() {
        SearchHistoryDAO dao = new SearchHistoryDAO(getContext());
        List<SearchHistory> searchHistoryList = dao.listAll();

        ArrayAdapter  adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, searchHistoryList);

        binding.ListViewSearchHistory.setAdapter(adapter);
    }

}