package com.mshukshina.besthoteldemoproject.presentation

import android.animation.LayoutTransition
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.mshukshina.besthoteldemoproject.R
import com.mshukshina.besthoteldemoproject.databinding.TouristRecyclerviewItemBinding
import com.mshukshina.besthoteldemoproject.presentation.models.Tourist


class TouristRecyclerViewAdapter(
    private val context: Context,
    private val nameValidator: (String) -> Boolean,
    private val surnameValidator: (String) -> Boolean,
    private val birthDayValidator: (String) -> Boolean,
    private val citizenshipValidator: (String) -> Boolean,
    private val passportNumberValidator: (String) -> Boolean,
    private val passportValidValidator: (String) -> Boolean
) :
    RecyclerView.Adapter<TouristRecyclerViewAdapter.ViewHolder>() {

    private var touristList: ArrayList<Tourist> = arrayListOf()
    private var isPayButtonClicked = false

    fun setList(touristList: ArrayList<Tourist>) {
        this.touristList = touristList

        notifyDataSetChanged()
    }

    fun setPayButtonClicked(isClicked: Boolean) {
        isPayButtonClicked = isClicked
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TouristRecyclerviewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return touristList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position in 0..9) {
            holder.touristNumber.text = touristNumbersMap[position]
        } else {
            holder.touristNumber.text = context.getString(R.string.tourist_number, position + 1)
        }
            holder.nameEditText.setText(touristList[position].name)
            holder.surnameEditText.setText(touristList[position].surname)
            holder.birthday.setText(touristList[position].birthDay)
            holder.citizenship.setText( touristList[position].citizenship)
            holder.passportNumber.setText(touristList[position].passportNumber)
            holder.passportValid.setText(touristList[position].passportValidTill)

        holder.rootView.setOnClickListener {
            holder.nameEditText.clearFocus()
            holder.surnameEditText.clearFocus()
            holder.birthday.clearFocus()
            holder.citizenship.clearFocus()
            holder.passportNumber.clearFocus()
            holder.passportValid.clearFocus()
        }

        holder.nameEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                changeColor(holder.nameEditText, nameValidator
                    .invoke(holder.nameEditText.text.toString()))
            }
        }
        holder.surnameEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                changeColor(holder.surnameEditText, surnameValidator
                    .invoke(holder.surnameEditText.text.toString()))
            }
        }
        holder.birthday.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                changeColor(holder.birthday, birthDayValidator
                    .invoke(holder.birthday.text.toString()))
            }
        }
        holder.citizenship.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                changeColor(holder.citizenship, citizenshipValidator
                    .invoke(holder.citizenship.text.toString()))
            }
        }
        holder.passportNumber.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                changeColor(holder.passportNumber, passportNumberValidator
                    .invoke(holder.passportNumber.text.toString()))
            }
        }
        holder.passportValid.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                changeColor(holder.passportValid, passportValidValidator
                    .invoke(holder.passportValid.text.toString()))
            }
        }

        holder.touristInfoLayout.layoutTransition.enableTransitionType(
            LayoutTransition.CHANGING
        )

        holder.arrowUp.setOnClickListener {
            TransitionManager.beginDelayedTransition(holder.touristInfoLayout, AutoTransition())

            holder.touristInfoLayout.visibility = View.GONE
            holder.arrowDown.visibility = View.VISIBLE
            holder.arrowUp.visibility = View.GONE
        }
        holder.arrowDown.setOnClickListener {
            TransitionManager.beginDelayedTransition(holder.touristInfoLayout, AutoTransition())

            holder.touristInfoLayout.visibility = View.VISIBLE
            holder.arrowDown.visibility = View.GONE
            holder.arrowUp.visibility = View.VISIBLE
        }

        if (isPayButtonClicked) {
            changeColor(holder.nameEditText, nameValidator
                .invoke(holder.nameEditText.text.toString()))
            changeColor(holder.surnameEditText,surnameValidator
                .invoke(holder.surnameEditText.text.toString()))
            changeColor(holder.birthday, birthDayValidator
                .invoke(holder.birthday.text.toString()))
            changeColor(holder.citizenship, citizenshipValidator
                .invoke(holder.citizenship.text.toString()))
            changeColor(holder.passportNumber, passportNumberValidator
                .invoke(holder.passportNumber.text.toString()))
            changeColor(holder.passportValid, passportValidValidator
                .invoke(holder.passportValid.text.toString()))
        }
    }

    class ViewHolder(binding: TouristRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val rootView = binding.root
        val touristNumber = binding.nextTouristTextView
        val arrowUp = binding.nextTouristArrowUp
        val arrowDown = binding.nextTouristArrowDown
        val touristInfoLayout = binding.nextTouristInfoLayout
        val nameEditText = binding.etNextName
        val surnameEditText = binding.etNextSurname
        val birthday = binding.etNextBirthDay
        val citizenship = binding.etCitizenship
        val passportNumber = binding.etNextPassportNumber
        val passportValid = binding.etNextPassportValidTill
    }

    private val invalidColor = ContextCompat.getColor(context, R.color.email_invalid)
    private val validColor = ContextCompat.getColor(context, R.color.valid_input)

    private fun changeColor(editText: AppCompatEditText, valid: Boolean) {
        if (valid) {
            editText.setBackgroundColor(validColor)
        } else {
            editText.setBackgroundColor(invalidColor)
        }
    }

    override fun getItemViewType(position: Int) = position

    override fun getItemId(position: Int) = position.toLong()

    companion object {
        private val touristNumbersMap = mapOf(
            0 to "Первый турист",
            1 to "Второй турист",
            2 to "Третий турист",
            3 to "Четвертый турист",
            4 to "Пятый турист",
            5 to "Шестой турист",
            6 to "Седьмой турист",
            7 to "Восьмой турист",
            8 to "Девятый турист",
            9 to "Десятый турист",
        )
    }
}