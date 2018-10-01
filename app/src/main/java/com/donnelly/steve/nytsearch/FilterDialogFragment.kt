package com.donnelly.steve.nytsearch

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.donnelly.steve.nytsearch.services.NewsService
import com.donnelly.steve.nytsearch.services.models.SearchParameters
import com.jakewharton.rxbinding2.view.clicks
import kotlinx.android.synthetic.main.fragment_filter.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class FilterDialogFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private var initialParams: SearchParameters? = null
    private var calendar: Calendar? = null
    var listener: OnCompleteListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initialParams = arguments?.getParcelable(PARAMS)
        return inflater.inflate(R.layout.fragment_filter, container)
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendar = if (initialParams?.beginDate.isNullOrEmpty()) {
            tvDateText.text = getString(R.string.enter_begin_date)
            null
        }
        else {
            val cal = Calendar.getInstance()
            cal.time = sdf.parse(initialParams?.beginDate)
            tvDateText.text = displayFormat.format(cal.time)
            cal
        }

        tvDateText.text = if (calendar == null)
            getString(R.string.enter_begin_date)
        else {
            displayFormat.format(calendar?.time)
        }

        tvDateText.clicks()
                .throttleFirst(500L, TimeUnit.MILLISECONDS)
                .subscribe {
                    calendar?.let{
                        DatePickerDialog(context!!, this, it.get(Calendar.YEAR), it.get(Calendar.MONTH), it.get(Calendar.DAY_OF_MONTH)).show()
                    } ?: run {
                        val currentCal = Calendar.getInstance()
                        DatePickerDialog(context!!, this, currentCal.get(Calendar.YEAR), currentCal.get(Calendar.MONTH), currentCal.get(Calendar.DAY_OF_MONTH)).show()
                    }
                }

        ArrayAdapter.createFromResource(
                context!!,
                R.array.sort_array,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        if (initialParams?.sort == NewsService.SORT_TYPE_NEWEST) {
            spinner.setSelection(1)
        }

        initialParams?.newsDeskValues?.let {
            cbArts.isChecked = it.contains(NewsService.NEWS_DESK_ARTS)
            cbEducation.isChecked = it.contains(NewsService.NEWS_DESK_EDUCATION)
            cbSports.isChecked = it.contains(NewsService.NEWS_DESK_SPORTS)
        }

        btnSave.clicks()
                .throttleFirst(500L, TimeUnit.MILLISECONDS)
                .subscribe {
                    saveAndDismiss()
                }
    }

    private fun saveAndDismiss() {
        var calendarString : String? = null
        calendar?.let{
            calendarString = sdf.format(it.time)
        }
        val sortString =  if (spinner.selectedItemPosition == 0)
            NewsService.SORT_TYPE_OLDEST
        else NewsService.SORT_TYPE_NEWEST

        val newsDeskValues = ArrayList<String>()
        if (cbArts.isChecked)
            newsDeskValues.add(NewsService.NEWS_DESK_ARTS)
        if (cbEducation.isChecked)
            newsDeskValues.add(NewsService.NEWS_DESK_EDUCATION)
        if (cbSports.isChecked)
            newsDeskValues.add(NewsService.NEWS_DESK_SPORTS)

        val outParams = SearchParameters(
                initialParams?.query,
                calendarString,
                sortString,
                null,
                newsDeskValues
        )
        if (newsDeskValues.isNotEmpty()){
            outParams.convertList()
        }

        listener?.onComplete(outParams)
        dismiss()
    }

    override fun onDateSet(dp: DatePicker?, year: Int, month: Int, day: Int) {
        val currentCal = Calendar.getInstance()
        currentCal?.set(year, month, day)
        tvDateText.text = displayFormat.format(currentCal?.time)
        calendar = currentCal
    }

    interface OnCompleteListener {
        fun onComplete(searchParameters: SearchParameters)
    }

    companion object {
        private const val PARAMS = "parameters"
        private val sdf : SimpleDateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        private val displayFormat : SimpleDateFormat  = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

        fun newInstance(searchParameters: SearchParameters): FilterDialogFragment {
            val fragment = FilterDialogFragment()
            val bundle = Bundle()
            bundle.putParcelable(PARAMS, searchParameters)
            fragment.arguments = bundle
            return fragment
        }
    }
}