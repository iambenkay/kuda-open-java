package com.iambenkay.kuda.types

enum class Service {
    ADMIN_CREATE_VIRTUAL_ACCOUNT, // Create a virtual account under your main account
    ADMIN_MAIN_ACCOUNT_TRANSACTIONS, // Get all the transactions on your  account
    ADMIN_MAIN_ACCOUNT_FILTERED_TRANSACTIONS, // Get a date filtered range of transactions on your account
    ADMIN_VIRTUAL_ACCOUNT_TRANSACTIONS, // Get all the transactions on a virtual account
    ADMIN_VIRTUAL_ACCOUNT_FILTERED_TRANSACTIONS, // Get a date filtered range of transactions on a virtual account
    BANK_LIST, // Get a list of all banks
    FUND_VIRTUAL_ACCOUNT, // Transfer money from your main account to a virtual account
    NAME_ENQUIRY, // Retrieve the name linked to a bank account
    ADMIN_RETRIEVE_SINGLE_VIRTUAL_ACCOUNT, // Retrieve the details on a created virtual account
    RETRIEVE_VIRTUAL_ACCOUNT_BALANCE, // Retrieve the account balance on a virtual account
    SINGLE_FUND_TRANSFER, // Transfer money from your main account
    TRANSACTION_STATUS_QUERY, // Get the status of a bank transfer
    VIRTUAL_ACCOUNT_FUND_TRANSFER, // Transfer money from a virtual account
    WITHDRAW_VIRTUAL_ACCOUNT, // Transfer money from a virtual account to your main account
}