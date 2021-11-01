package br.com.jose.hydrationreminder.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.jose.hydrationreminder.core.createDialog
import br.com.jose.hydrationreminder.core.createProgressDialog
import br.com.jose.hydrationreminder.databinding.FragmentHistoryBinding
import br.com.jose.hydrationreminder.presentation.HistoryViewModel
import br.com.jose.hydrationreminder.ui.history.adapter.HistoryAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {

    private val viewModel by viewModel<HistoryViewModel>()
    private var _binding: FragmentHistoryBinding? = null
    private val adapter by lazy { HistoryAdapter() }
    private val dialog by lazy { requireActivity().createProgressDialog() }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycle.addObserver(viewModel)
        setupObservers()
        setupRecyclerView()
    }

    private fun setupObservers() {
        viewModel.state.observe(viewLifecycleOwner){
            when (it){
                is HistoryViewModel.State.Error -> {
                    dialog.dismiss()
                    requireActivity().createDialog {
                        setMessage(it.error.message)
                    }.show()
                }
                HistoryViewModel.State.Loading -> dialog.show()
                is HistoryViewModel.State.Success -> {
                    adapter.submitList(it.list)
                    dialog.dismiss()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistory.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}