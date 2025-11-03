package com.example.demo.services;

import com.example.demo.util.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

@Service
public class AemService {
    public String getAemSearchService(){

        JsonObject object = new JsonObject();
        JsonObject trendingSearch = new JsonObject();
        JsonObject popularProducts = new JsonObject();

        JsonArray trendingSearchesArray = new JsonArray();
        trendingSearchesArray.add(Constants.SearchBar.firstSearch);
        trendingSearchesArray.add(Constants.SearchBar.secondSearch);
        trendingSearchesArray.add(Constants.SearchBar.thirdSearch);
        trendingSearchesArray.add(Constants.SearchBar.fourthSearch);
        trendingSearch.add(Constants.SearchBar.trendingSearches,trendingSearchesArray);

        object.add(Constants.SearchBar.trendingSearch,trendingSearch);

        JsonObject homeLoan = new JsonObject();
        homeLoan.addProperty(Constants.SearchBar.value,Constants.SearchBar.homeLoanValue);
        homeLoan.addProperty(Constants.SearchBar.logo,Constants.SearchBar.homeLoanLogo);

        JsonObject savingAccount = new JsonObject();
        savingAccount.addProperty(Constants.SearchBar.value,Constants.SearchBar.savingAccountValue);
        savingAccount.addProperty(Constants.SearchBar.logo,Constants.SearchBar.savingAccountLogo);

        JsonObject personalLoan = new JsonObject();
        personalLoan.addProperty(Constants.SearchBar.value,Constants.SearchBar.personalLoanValue);
        personalLoan.addProperty(Constants.SearchBar.logo,Constants.SearchBar.personalLogo);

        JsonObject creditCard = new JsonObject();
        creditCard.addProperty(Constants.SearchBar.value,Constants.SearchBar.creditCardLoan);
        creditCard.addProperty(Constants.SearchBar.logo,Constants.SearchBar.creditCardLogo);

        JsonObject twoWheelerLoan = new JsonObject();
        twoWheelerLoan.addProperty(Constants.SearchBar.value,Constants.SearchBar.twoWheelerLoanValue);
        twoWheelerLoan.addProperty(Constants.SearchBar.logo,Constants.SearchBar.twoWheelerLogo);

        JsonObject fixedDeposite = new JsonObject();
        fixedDeposite.addProperty(Constants.SearchBar.value,Constants.SearchBar.fixedDepositeValue);
        fixedDeposite.addProperty(Constants.SearchBar.logo,Constants.SearchBar.fixedDepositeLogo);

        JsonObject carLoan = new JsonObject();
        carLoan.addProperty(Constants.SearchBar.value,Constants.SearchBar.carLoanValue);
        carLoan.addProperty(Constants.SearchBar.logo,Constants.SearchBar.carLoanLogo);

        popularProducts.add(Constants.SearchBar.homeLoan,homeLoan);
        popularProducts.add(Constants.SearchBar.savingAccount,savingAccount);
        popularProducts.add(Constants.SearchBar.personalLoan,personalLoan);
        popularProducts.add(Constants.SearchBar.creditCard,creditCard);
        popularProducts.add(Constants.SearchBar.twoWheelerLoan,twoWheelerLoan);
        popularProducts.add(Constants.SearchBar.fixedDeposite,fixedDeposite);
        popularProducts.add(Constants.SearchBar.carLaon,carLoan);


        object.add(Constants.SearchBar.popularProducts,popularProducts);
        return object.toString();
    }
}
