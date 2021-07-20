package com.example.hashfunction

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.hashfunction.databinding.FragmentSuccessBinding
import com.example.hashfunction.models.HomeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope


class SuccessFragment : Fragment() {
    private val homeViewModel :HomeViewModel by activityViewModels()
    private  var _binding: FragmentSuccessBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentSuccessBinding.inflate(layoutInflater,container,false)
        binding.hashField.text=(homeViewModel.hashText.toString())
        binding.copyButton.setOnClickListener {
            copyHash();
            lifecycleScope.launch {
                copyAnimation();
            }
        }
        binding.shareButton.setOnClickListener {
            shareText();
        }
        return binding.root
    }

     private suspend fun copyAnimation() {
        binding.textView.animate().translationYBy(90f).duration=200L
        binding.view1.animate().translationYBy(90f).duration=200L
        delay(2000L)
         binding.textView.animate().translationYBy(-90f).duration=200L
         binding.view1.animate().translationYBy(-90f).duration=200L
    }

    private fun shareText() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, homeViewModel.hashText.toString())
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null).also {
            startActivity(it)
        }
    }


    private fun copyHash() {
        val clipboardManager= requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipdata= ClipData.newPlainText("Encrypted Text: ",homeViewModel.hashText)
        clipboardManager.setPrimaryClip(clipdata)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}