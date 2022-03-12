package com.virtusatask.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.virtusatask.R
import com.virtusatask.databinding.FragmentProgressDialogBinding

class LoaderFragment : DialogFragment() {
    private var _binding: FragmentProgressDialogBinding? = null

    companion object {
        private const val TITLE = "title"
        private const val TAG = "LoaderFragment"
        fun newInstance(title: String, fragmentManager: FragmentManager) {
            dismissLoader(fragmentManager)
            kotlin.runCatching {
                val args = Bundle()
                args.putString(TITLE, title)

                val fragment = LoaderFragment()
                fragment.arguments = args
                fragment.show(fragmentManager, TAG)
            }
        }

        fun dismissLoader(fragmentManager: FragmentManager) {
            try {
                val frag = fragmentManager.findFragmentByTag(TAG)
                if (frag is LoaderFragment && frag.isVisible)
                    frag.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_progress_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentProgressDialogBinding.bind(view)
        _binding = binding
        arguments?.let {
            it.getString(TITLE).let {
                when {
                    it.isNullOrEmpty() -> _binding?.tvTitle?.text = getString(R.string.loading)
                    else -> _binding?.tvTitle?.text = it

                }
            }
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        kotlin.runCatching {
           _binding = null
        }
    }

}