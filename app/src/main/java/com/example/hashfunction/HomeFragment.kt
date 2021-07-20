package com.example.hashfunction

import android.annotation.SuppressLint
import android.icu.util.TimeUnit
import android.os.Bundle

import android.view.*
import android.widget.ArrayAdapter
import androidx.core.os.HandlerCompat.postDelayed
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.hashfunction.databinding.FragmentHomeBinding
import com.example.hashfunction.models.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import kotlinx.coroutines.runBlocking
import java.util.logging.Handler
import javax.xml.datatype.DatatypeConstants.SECONDS


class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding?=null
    private val binding get()= _binding!!
    override fun onResume() {
        super.onResume()
        val algo =resources.getStringArray(R.array.Algorithms)
        var arrayAdapter= ArrayAdapter(requireContext(), R.layout.drop_down_menu, algo)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        _binding= FragmentHomeBinding.inflate(layoutInflater, container, false)
        binding.generateButton.setOnClickListener{
            onGeneratedClicked()
        }
        return binding.root
    }
    private fun onGeneratedClicked(){
        if(binding.editText.text.isEmpty()){
            showSnackbar("Empty Field");
        }
        else{

            homeViewModel.getHash(binding.autoCompleteTextView.text.toString(),binding.editText.text.toString())
            //showSnackbar(homeViewModel.hashText)
            lifecycleScope.launch {
                showAnimation();
                toSuccessfragment();
            }

        }
    }

    @SuppressLint("ResourceAsColor")
    private fun showSnackbar(message: String) {
        val snackbar= Snackbar.make(binding.root,message,Snackbar.LENGTH_SHORT)

        snackbar.setAction("Okay"){}
        snackbar.show()
    }

    private fun toSuccessfragment() {
        findNavController().navigate(R.id.action_homeFragment_to_successFragment)
    }

    private suspend fun showAnimation() {
        binding.generateButton.isClickable=false
        binding.generateButton.animate().alpha(0f).duration=400L
        binding.titleTextView.animate().alpha(0f).duration=400L
        binding.textInputLayout.animate()
                .alpha(0f)
                .translationXBy(1200f)
                .duration=400L
        binding.editText.animate()
                .alphaBy(-1f)
                .translationXBy(1400f)
                .duration=400L


        delay(300)


        binding.view.animate().alpha(1f).duration=500L
        binding.view.animate().rotationBy(720f).duration=500L
        binding.view.animate().scaleXBy(900f).duration=800L
        binding.view.animate().scaleYBy(900f).duration=800L

        delay(400)
        binding.imageView.animate().alpha(1f).duration=700L
        //binding.imageView.animate().rotationBy(360f).duration=300L
        delay(1200)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.ClearMenu){
            clearEditText()
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
    fun clearEditText(){
        binding.editText.text.clear();
        showSnackbar("Cleared.")
    }
}