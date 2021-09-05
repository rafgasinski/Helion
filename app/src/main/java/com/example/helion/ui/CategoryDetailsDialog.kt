package com.example.helion.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.helion.R
import com.example.helion.databinding.DialogCategoryDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CategoryDetailsDialog: BottomSheetDialogFragment() {

    private val tagId = "tagId"
    private val tagName = "tagName"
    private val tagBookCount = "bookCount"

    private var _binding: DialogCategoryDetailsBinding? = null
    private val binding get() = _binding!!

    fun newInstance(id: Int, name: String, bookCount: String): CategoryDetailsDialog {
        val args = Bundle().apply {
            putInt(tagId, id)
            putString(tagName, name)
            putString(tagBookCount, bookCount)
        }
        return CategoryDetailsDialog().apply {
            arguments = args
        }
    }

    override fun onResume() {
        super.onResume()
        val behavior = BottomSheetBehavior.from(requireView().parent as View)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.dialog_category_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            categoryName.text = arguments?.getString(tagName)
            categoryId.text = getString(R.string.id_value, arguments?.getInt(tagId))
            categoryBookCount.text = getString(R.string.book_count_value, arguments?.getString(tagBookCount))

            closeButton.setOnClickListener {
                dialog?.dismiss()
            }
        }
    }
}