package com.example.kuwait.Model

data class IBANResponse(
    val bank_data: BankData,
    val country_iban_example: String,
    val iban: String,
    val iban_data: IbanData,
    val message: String,
    val valid: Boolean
)

data class IbanData(
    val BBAN: String,
    val account_number: String,
    val bank_code: String,
    val branch: String,
    val checksum: String,
    val country: String,
    val country_code: String,
    val country_iban_format_as_regex: String,
    val country_iban_format_as_swift: String,
    val national_checksum: String,
    val sepa_country: Boolean
)

data class BankData(
    val bank_code: String,
    val bic: String,
    val city: String,
    val name: String,
    val zip: String
)