package com.riramzy.littlelemon.ui.screens.payment

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riramzy.littlelemon.data.repos.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentVm @Inject constructor(
    private val userRepo: UserRepo,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    private val _firstName = MutableStateFlow(savedStateHandle.get<String>("first_name") ?: "")
    val firstName = _firstName.asStateFlow()

    private val _lastName = MutableStateFlow(savedStateHandle.get<String>("last_name") ?: "")
    val lastName = _lastName.asStateFlow()

    private val _email = MutableStateFlow(savedStateHandle.get<String>("email") ?: "")
    val email = _email.asStateFlow()

    private val _phoneNumber = MutableStateFlow(savedStateHandle.get<String>("phone_number") ?: "")
    val phoneNumber = _phoneNumber.asStateFlow()

    private val _paymentMethod = MutableStateFlow(savedStateHandle.get<String>("payment_method"))
    val paymentMethod = _paymentMethod.asStateFlow()

    private val _cardNumber = MutableStateFlow(savedStateHandle.get<String>("card_number") ?: "")
    val cardNumber = _cardNumber.asStateFlow()

    private val _cardMonth = MutableStateFlow(savedStateHandle.get<String>("card_month") ?: "")
    val cardMonth = _cardMonth.asStateFlow()

    private val _cardYear = MutableStateFlow(savedStateHandle.get<String>("card_year") ?: "")
    val cardYear = _cardYear.asStateFlow()

    private val _cardCvv = MutableStateFlow(savedStateHandle.get<String>("card_cvv") ?: "")
    val cardCvv = _cardCvv.asStateFlow()

    init {
        viewModelScope.launch {
            if (userRepo.isLoggedIn()) {
                if (_firstName.value.isBlank()) {
                    val fName = userRepo.getFirstName() ?: ""
                    _firstName.value = fName
                    savedStateHandle["first_name"] = fName
                }
                if (_lastName.value.isBlank()) {
                    val lName = userRepo.getLastName() ?: ""
                    _lastName.value = lName
                    savedStateHandle["last_name"] = lName
                }
                if (_email.value.isBlank()) {
                    val mail = userRepo.getEmail() ?: ""
                    _email.value = mail
                    savedStateHandle["email"] = mail
                }
            }
        }
    }

    fun onFirstNameChange(value: String) {
        _firstName.update { value }
        savedStateHandle["first_name"] = value
    }

    fun onLastNameChange(value: String) {
        _lastName.update { value }
        savedStateHandle["last_name"] = value
    }

    fun onEmailChange(value: String) {
        _email.update { value }
        savedStateHandle["email"] = value
    }

    fun onPhoneNumberChange(value: String) {
        if (value.all(Char::isDigit) && value.length <= 15) {
            _phoneNumber.update { value }
            savedStateHandle["phone_number"] = value
        }
    }

    fun onPaymentMethodChange(value: String) {
        _paymentMethod.update { value }
        savedStateHandle["payment_method"] = value
    }

    fun onCardNumberChange(value: String) {
        if (value.all(Char::isDigit) && value.length <= 16) {
            _cardNumber.update { value }
            savedStateHandle["card_number"] = value
        }
    }

    fun onCardMonthChange(value: String) {
        if (value.all(Char::isDigit) && value.length <= 2) {
            _cardMonth.update { value }
            savedStateHandle["card_month"] = value
        }
    }

    fun onCardYearChange(value: String) {
        if (value.all(Char::isDigit) && value.length <= 4) {
            _cardYear.update { value }
            savedStateHandle["card_year"] = value
        }
    }

    fun onCardCvvChange(value: String) {
        if (value.all(Char::isDigit) && value.length <= 3) {
            _cardCvv.update { value }
            savedStateHandle["card_cvv"] = value
        }
    }

    fun onNextClicked(onSuccess: (orderId: String, phoneNumber: String) -> Unit) {
        viewModelScope.launch {
            if (_firstName.value.isBlank() || _lastName.value.isBlank() || _email.value.isBlank()) {
                _toastMessage.emit("Please fill all user information")
                return@launch
            }

            if (!isPhoneNumberValid()) {
                _toastMessage.emit("Please enter a valid phone number (7 to 15 digits)")
                return@launch
            }

            if (!isPaymentMethodSelected()) {
                _toastMessage.emit("Please select a payment method")
                return@launch
            }

            if (paymentMethod.value != "Cash On Delivery") {
                if (!areCardDetailsValid()) {
                    _toastMessage.emit("Please enter valid card details (16-digit card number, month 1-12, future expiry, CVV 3-4 digits)")
                    return@launch
                }
            }

            val orderId = (100000..999999).random().toString()
            onSuccess(orderId, _phoneNumber.value)
        }
    }

    private fun isPhoneNumberValid(): Boolean {
        val phone = _phoneNumber.value
        return phone.length in 7..15 && phone.all { it.isDigit() }
    }

    private fun isPaymentMethodSelected(): Boolean =
        paymentMethod.value?.isNotBlank() ?: false

    private fun areCardDetailsValid(): Boolean {
        val num = _cardNumber.value
        val monthStr = _cardMonth.value
        val yearStr = _cardYear.value
        val cvvStr = _cardCvv.value

        if (num.length != 16) return false

        val month = monthStr.toIntOrNull() ?: return false
        if (month !in 1..12) return false

        val year = yearStr.toIntOrNull() ?: return false
        val currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
        if (year < currentYear || year > currentYear + 20) return false

        if (year == currentYear) {
            val currentMonth = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1
            if (month < currentMonth) return false
        }

        if (cvvStr.length !in 3..4) return false

        return true
    }
}