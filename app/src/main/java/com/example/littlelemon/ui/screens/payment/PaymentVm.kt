package com.example.littlelemon.ui.screens.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlelemon.ui.screens.reservation.ReservationVm
import com.example.littlelemon.ui.viewmodel.UserVm
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PaymentVm(
    userVm: UserVm,
    private val reservationVm: ReservationVm
) : ViewModel() {

    // Event channel for showing toasts
    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    // User Information State
    private val _firstName = MutableStateFlow(if (userVm.isLoggedIn()) userVm.getFirstName() ?: "" else "")
    val firstName = _firstName.asStateFlow()

    private val _lastName = MutableStateFlow(if (userVm.isLoggedIn()) userVm.getLastName() ?: "" else "")
    val lastName = _lastName.asStateFlow()

    private val _email = MutableStateFlow(if (userVm.isLoggedIn()) userVm.getEmail() ?: "" else "")
    val email = _email.asStateFlow()

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber = _phoneNumber.asStateFlow()

    // Payment Method State
    private val _paymentMethod = MutableStateFlow(reservationVm.selectedPaymentMethod.value)
    val paymentMethod = _paymentMethod.asStateFlow()

    // Card Information State
    private val _cardNumber = MutableStateFlow("")
    val cardNumber = _cardNumber.asStateFlow()

    private val _cardMonth = MutableStateFlow("")
    val cardMonth = _cardMonth.asStateFlow()

    private val _cardYear = MutableStateFlow("")
    val cardYear = _cardYear.asStateFlow()

    private val _cardCvv = MutableStateFlow("")
    val cardCvv = _cardCvv.asStateFlow()

    fun onFirstNameChange(value: String) { _firstName.update { value } }
    fun onLastNameChange(value: String) { _lastName.update { value } }
    fun onEmailChange(value: String) { _email.update { value } }
    fun onPhoneNumberChange(value: String) {
        if (value.all(Char::isDigit) && value.length <= 11) {
            _phoneNumber.update { value }
        }
    }
    fun onPaymentMethodChange(value: String) { _paymentMethod.update { value } }

    fun onCardNumberChange(value: String) {
        if (value.all(Char::isDigit) && value.length <= 16) {
            _cardNumber.update { value }
        }
    }

    fun onCardMonthChange(value: String) {
        if (value.all(Char::isDigit) && value.length <= 2) {
            _cardMonth.update { value }
        }
    }

    fun onCardYearChange(value: String) {
        if (value.all(Char::isDigit) && value.length <= 4) {
            _cardYear.update { value }
        }
    }

    fun onCardCvvChange(value: String) {
        if (value.all(Char::isDigit) && value.length <= 3) {
            _cardCvv.update { value }
        }
    }

    fun onNextClicked(onSuccess: () -> Unit) {
        viewModelScope.launch {
            if (_firstName.value.isBlank() || _lastName.value.isBlank() || _email.value.isBlank()) {
                _toastMessage.emit("Please fill all user information")
                return@launch
            }

            if (!isPhoneNumberValid()) {
                _toastMessage.emit("Please enter a valid phone number")
                return@launch
            }

            if (!isPaymentMethodSelected()) {
                _toastMessage.emit("Please select a payment method")
                return@launch
            }

            if (_paymentMethod.value != "Cash On Delivery") {
                if (!areCardDetailsValid()) {
                    _toastMessage.emit("Please enter all card details")
                    return@launch
                }
            }

            reservationVm.orderId.value = (100000..999999).random().toString()
            reservationVm.selectedPaymentMethod.value = _paymentMethod.value
            reservationVm.phoneNumber.value = _phoneNumber.value

            onSuccess()
        }
    }

    private fun isPhoneNumberValid(): Boolean {
        val phone = _phoneNumber.value
        return phone.length == 11 &&
                (phone.startsWith("010") ||
                        phone.startsWith("011") ||
                        phone.startsWith("012") ||
                        phone.startsWith("015"))
    }

    private fun isPaymentMethodSelected(): Boolean =
        _paymentMethod.value?.isNotBlank() ?: false

    private fun areCardDetailsValid(): Boolean =
        _cardNumber.value.isNotBlank() && _cardMonth.value.isNotBlank() && _cardYear.value.isNotBlank() && _cardCvv.value.isNotBlank()
}
