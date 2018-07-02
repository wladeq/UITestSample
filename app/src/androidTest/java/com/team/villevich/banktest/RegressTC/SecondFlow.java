package com.team.villevich.banktest.RegressTC;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

//
//KLIENT MA BYĆ ZWERYFIKOWANY I POSIADAĆ ŚRODKI NA CONAJMNIEJ JEDNYM KONCIE
//MIEĆ CO NAJMNIEJ DWA KONTA (DLA TESTU OWN TRANSFERA)
//

@RunWith(AndroidJUnit4.class)
public class SecondFlow {
    private UiDevice mDevice;
    List<String> personalAccIbans = new ArrayList<String>();
    List<String> savingsAccIbans = new ArrayList<String>();


    private String LOGIN_ENTER_PASSCODE = "Enter your passcode",
            DASHBOARD_BAR_TITLE = "GOLDEN SAND BANK",
            TRANSFER_BAR_TITLE = "TRANSFER",
            PAYMENT_BAR_TITLE = "PAYMENT",
            FILTERS_BAR_TITLE = "FILTERS",
            PRODUCT_BAR_TITLE = "PRODUCT",
            TRANSACTIONS_BAR_TITLE = "TRANSACTIONS",
            TRANSACTION_TYPE_TITLE_BAR_TITLE = "TRANSACTION TYPE",
            GBP_PERSONAL_NUMBER = "61 NWBK 0000 0100 0059 120",
            USD_PERSONAL_NUMBER = "08 NWBK 0000 0127 9782 660",

         // One of the accounts must be without any transactions (For one of the TC)
            EUR_SAVING_NUMBER = "06 NWBK 0000 0100 0059 140",

         // USE ACC NUMBER FOR PAYMENT ONLY WITHOUT SPACES OR CHANGE TEST CODE //
            ACC_NUMBER_PAYMENT = "GI35NWBK000001263422930",

            TITLE_PAYMENT = "Napiwki",
            NAME_PAYMENT = "Pawlik Morozov";
    private String balanceFieldValue, fromAccNrFieldValue;
    private boolean isExchangeExists=false;
    private int currentBalance, moneyAmount = 10;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    //Logowanie do appki. Brak obsługiwania pop-upów, klient ma wszystko odklikać wcześniej
    @Test
    public void test1() throws UiObjectNotFoundException, InterruptedException {
        mDevice.pressHome();
        try {
            UiObject appButton1 = mDevice.findObject(new UiSelector().text("Golden Sand Bank"));
            appButton1.click();
        } catch (UiObjectNotFoundException e){
            mDevice.pressHome();
            UiObject appButton1 = mDevice.findObject(new UiSelector().text("Golden Sand Bank"));
            appButton1.click();
        }

        UiObject2 element = mDevice.wait(Until.findObject(By.res("com.ailleron.longbank.gtest:id/tv_passcode_hint")),6000);
        TimeUnit.SECONDS.sleep(1);
        mDevice.pressKeyCode(0x00000091);
        mDevice.pressKeyCode(0x00000091);
        mDevice.pressKeyCode(0x00000092);
        mDevice.pressKeyCode(0x00000092);
        mDevice.pressKeyCode(0x00000093);
        mDevice.pressKeyCode(0x00000093);
        mDevice.pressKeyCode(0x00000094);
        mDevice.pressKeyCode(0x00000094);
        UiObject2 element1 = mDevice.wait(Until.findObject(By.text("PERSONAL ACCOUNTS")),3000);
    }

    //TODO
    // Get account numbers to make test much more flexible
    // The best way to get account numbers are Dashboard -> Product -> Slide to left
    // For personal and saving product must be separate actions

    @Test
    public void test2() throws UiObjectNotFoundException {

        //Get personal acc Ibans
        UiObject2 personalAccounts = mDevice.findObject(By.text("PERSONAL ACCOUNTS"));
        personalAccounts.click();
        UiObject2 elementPersonal = mDevice.wait(Until.findObject(By.text("Personal Account")),3000);
        mDevice.swipe(0,720,720,720,20);
        List<UiObject2> personalAccIbansList = mDevice.findObjects(By.res("com.ailleron.longbank.gtest:id/tv_subtitle"));
        for (int i = 0; i < personalAccIbansList.size(); i++) {
            personalAccIbans.add(personalAccIbansList.get(i).getText());
            Log.e("Number is ",personalAccIbans.get(i));
        }
        UiObject2 bottomDashboardBtn = mDevice.findObject(By.res("com.ailleron.longbank.gtest:id/tab_home"));
        bottomDashboardBtn.click();

        //Get seving Ibans
        UiObject2 element1 = mDevice.wait(Until.findObject(By.text("SAVINGS")),3000);
        element1.click();
        UiObject2 elementSavings = mDevice.wait(Until.findObject(By.text("Savings Account")),3000);
        mDevice.swipe(0,720,720,720,20);
        List<UiObject2> savignsAccIbansList = mDevice.findObjects(By.res("com.ailleron.longbank.gtest:id/tv_subtitle"));
        for (int i = 0; i < savignsAccIbansList.size(); i++) {
            savingsAccIbans.add(savignsAccIbansList.get(i).getText());
            Log.e("Number is ",savingsAccIbans.get(i));
        }
        UiObject2 bottomDashboardBtn1 = mDevice.findObject(By.res("com.ailleron.longbank.gtest:id/tab_home"));
        bottomDashboardBtn1.click();
    }

    //TRANSFER
    @Test
    public void test3() throws InterruptedException, UiObjectNotFoundException {
        String datePayment;
        UiObject topBarDashboard = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/toolbar_title"));
        UiObject sideMenu = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tab_menu"));
        UiObject transferSideMenu = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/nav_transfer"));

        //WCHODZIMY DO TRANSFER PRZEZ BOCZNE MENU
        UiObject2 element1 = mDevice.wait(Until.findObject(By.res("com.ailleron.longbank.gtest:id/toolbar_title")),3000);
        assertThat(DASHBOARD_BAR_TITLE, is(equalTo(topBarDashboard.getText())));
        UiObject2 element2 = mDevice.wait(Until.findObject(By.res("com.ailleron.longbank.gtest:id/tab_menu")),2000);
        sideMenu.click();

        transferSideMenu.click();

        //Sprawdzamy, czy wyśweitłił się właścziwy ekran i czy są środki na koncie
        try{
            UiObject transfer = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/toolbar_title"));
            assertThat(TRANSFER_BAR_TITLE, is(equalTo(transfer.getText())));
        } catch (UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(2);
            UiObject transfer = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/toolbar_title"));
            assertThat(TRANSFER_BAR_TITLE, is(equalTo(transfer.getText())));
        }
        UiObject balanceView = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_account_balance"));
        balanceFieldValue = balanceView.getText();
        currentBalance = Integer.parseInt(balanceFieldValue.substring(4, balanceFieldValue.length()-3).replaceAll(",", ""));
        if(currentBalance<=0)throw new IllegalArgumentException("CAN NOT MAKE A TRANSFER WITH OUT MONEY");

        UiObject fromAccNrField = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_account_number_text"));
        fromAccNrFieldValue = fromAccNrField.getText();

        UiObject toAccountViewGroup = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/ell_to_account"));
        toAccountViewGroup.click();
        TimeUnit.SECONDS.sleep(1);

        UiObject toAccountPersonal1 = mDevice.findObject(new UiSelector().classNameMatches("android.view.ViewGroup").index(1));
        toAccountPersonal1.click();
        TimeUnit.SECONDS.sleep(1);

        UiObject datePaymentField = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_dropdown_item_text")
                .textContains("20"));
        datePayment = datePaymentField.getText();

        //Check if 0 is acceptable
        UiObject amountZero = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_enter_amount"));
        amountZero.setText("0");
        TimeUnit.SECONDS.sleep(1);
        UiObject confrimTransfer = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_confirm"));
        confrimTransfer.click();
        TimeUnit.SECONDS.sleep(2);

        try{
            assertThat(TRANSFER_BAR_TITLE, is(equalTo(topBarDashboard.getText())));
        } catch( UiObjectNotFoundException e){
            throw new IllegalArgumentException("SYSTEM ALLOWS TRANSACTION WITH 0 AMOUNT");

        }

        UiObject amount = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_enter_amount"));
        amount.setText(moneyAmount+"");
        TimeUnit.SECONDS.sleep(1);
        UiObject exchangeExists = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_exchange_rate"));
        try{
            exchangeExists.isFocusable();
            isExchangeExists = true;
        }
        catch(UiObjectNotFoundException e){
            isExchangeExists = false;
        }
        confrimTransfer.click();
        TimeUnit.SECONDS.sleep(2);

        //Summary activity, Account currency check
        //
        //Case with two currencies
        if(isExchangeExists == true) {
            UiObject currencyAmountField = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_account_currency_amount"));
            if(Integer.parseInt(currencyAmountField.getText().substring(4, currencyAmountField.getText().length()-3).replaceAll(",", ""))
                    == moneyAmount)throw new IllegalArgumentException("Account currency amount equals transfer amount (Exchange rate error)");
        } else {
            //If there are only ony currency - just check field 'Transaction amount'
            UiObject summaryTransactionAmount = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_transaction_amount"));
            summaryTransactionAmount.setText(moneyAmount + "");
            if ((Integer.parseInt(summaryTransactionAmount.getText().substring(4, summaryTransactionAmount.getText().length()-3).replaceAll(",", ""))
                    != moneyAmount))throw new IllegalArgumentException("Transfer amount from transfer and summary screens not equals");
        }

        UiObject sumDate = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_date"));
        assertThat(datePayment, is(equalTo(sumDate.getText())));
        UiObject summaryConfirm = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_confirm"));
        summaryConfirm.click();

        try{
            TimeUnit.SECONDS.sleep(2);
            UiObject finishTransaction = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_dialog_ok"));
            finishTransaction.click();
        } catch (UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(2);
            UiObject finishTransaction = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_dialog_ok"));
            finishTransaction.click();
        }
    }

    //PAYMENT
    @Test
    public void test4() throws UiObjectNotFoundException, InterruptedException {
        String datePayment;
        UiObject topBarDashboard = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/toolbar_title"));
        try{
            assertThat(DASHBOARD_BAR_TITLE, is(equalTo(topBarDashboard.getText())));
        } catch( UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(2);
            assertThat(DASHBOARD_BAR_TITLE, is(equalTo(topBarDashboard.getText())));
        }

        UiObject sideMenu = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tab_menu"));

        try {
            sideMenu.click();
        }catch (UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(2);
            sideMenu.click();
        }
        UiObject payment = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/nav_payment"));
        try {
            payment.clickAndWaitForNewWindow();
        }catch (UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(1);
            payment.clickAndWaitForNewWindow();
        }

        //Payment screen
        int fromAccCompareValue;
        UiObject fromAccBalanceField = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_account_balance"));
        fromAccCompareValue = Integer.parseInt(fromAccBalanceField.getText().
                substring(4, fromAccBalanceField.getText().length()-3).replaceAll(",", ""))+1;

        UiObject nameEdit = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/et_name"));
        nameEdit.setText(NAME_PAYMENT);

        UiObject adress1 = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/et_address_first_line"));
        adress1.setText("Some adress");


        UiObject adress2 = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/et_address_second_line"));
        adress2.setText("Some second adress");


        UiScrollable test = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
        test.scrollForward();
        TimeUnit.SECONDS.sleep(1);


        UiObject coutry = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_dropdown_item_text"));
        coutry.click();

        TimeUnit.SECONDS.sleep(1);
        mDevice.click(360,635);



        //Here we will test validation of following fields:
        Pattern p = Pattern.compile("(([A-Z].*[0-9]))");
        UiObject amount = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_enter_amount"));
        UiObject ibanNumber = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/et_account_number"));
        UiObject title = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_payment_title"));
        UiObject confirm = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_confirm"));
        UiObject topBarPaymet = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/toolbar_title"));

        UiObject datePaymentField = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_dropdown_item_text")
                .textContains("20"));
        datePayment = datePaymentField.getText();

        //Firstly we insert wrong payment amount
        amount.setText(fromAccCompareValue+"");
        ibanNumber.setText(ACC_NUMBER_PAYMENT);
        ibanNumber.click();
        topBarPaymet.click();
        ibanNumber.click();
        topBarPaymet.click();

        title.setText(TITLE_PAYMENT);

        //And try to make a payment
        confirm.click();
        TimeUnit.SECONDS.sleep(1);
        try{
            assertThat(PAYMENT_BAR_TITLE, is(equalTo(topBarPaymet.getText())));
        } catch( UiObjectNotFoundException e){
            throw new IllegalArgumentException("SYSTEM ALLOWS TRANSACTION WITH 0 AMOUNT");
        }

        //Then with empty amount
        amount.setText("");

        //And try again
        confirm.click();
        TimeUnit.SECONDS.sleep(1);
        try{
            assertThat(PAYMENT_BAR_TITLE, is(equalTo(topBarPaymet.getText())));
        } catch( UiObjectNotFoundException e){
            throw new IllegalArgumentException("SYSTEM ALLOWS TRANSACTION WITH 0 AMOUNT");
        }

        //Then we insert zero as amount and try again
        amount.setText(0+"");
        confirm.click();
        TimeUnit.SECONDS.sleep(1);
        try{
            assertThat(PAYMENT_BAR_TITLE, is(equalTo(topBarPaymet.getText())));
        } catch( UiObjectNotFoundException e){
            throw new IllegalArgumentException("SYSTEM ALLOWS TRANSACTION WITH 0 AMOUNT");
        }

        //Ok, field amount works correctly, insert Correct amount
        amount.setText(moneyAmount+"");

        //Now field 'Account number'
        ibanNumber.setText("someTestTest");
        confirm.click();
        TimeUnit.SECONDS.sleep(1);
        try{
            assertThat(PAYMENT_BAR_TITLE, is(equalTo(topBarPaymet.getText())));
        } catch( UiObjectNotFoundException e){
            throw new IllegalArgumentException("SYSTEM ALLOWS TRANSACTION WITH 0 AMOUNT");
        }

        //Insert normal acc number
        ibanNumber.setText(ACC_NUMBER_PAYMENT);

        //Than empty title
        title.setText("");
        confirm.click();
        TimeUnit.SECONDS.sleep(1);
        try{
            assertThat(PAYMENT_BAR_TITLE, is(equalTo(topBarPaymet.getText())));
        } catch( UiObjectNotFoundException e){
            throw new IllegalArgumentException("SYSTEM ALLOWS TRANSACTION WITH 0 AMOUNT");
        }

        //Than correct title and try to make a payment correctly
        title.setText(TITLE_PAYMENT);
        confirm.click();
        TimeUnit.SECONDS.sleep(1);

        // Summary screen
        // We will compare every values of every field with our input
        // Firstly find all necessary fields:
        UiObject sumAccNrTo = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_confirm_to_account_number"));
        UiObject sumTitle = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_transfer_title"));
        UiObject sumAmount = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_transaction_amount"));
        UiObject sumDate = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_date"));
        UiObject sumToName = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_confirm_to_account_name"));
        assertThat(ACC_NUMBER_PAYMENT, is(equalTo(sumAccNrTo.getText().replace(" ", ""))));
        assertThat(moneyAmount+"", is(equalTo(sumAmount.getText()
                .substring(4, sumAmount.getText().length()-3).replaceAll(",", ""))));
        assertThat(datePayment, is(equalTo(sumDate.getText())));
        assertThat(TITLE_PAYMENT, is(equalTo(sumTitle.getText())));
        assertThat(NAME_PAYMENT, is(equalTo(sumToName.getText())));

        //Finish payment
        try {
            confirm.click();
        } catch (UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(1);
            confirm.click();
        }
        TimeUnit.SECONDS.sleep(1);

        //Wrong pin input
        mDevice.pressKeyCode(0x00000091);
        mDevice.pressKeyCode(0x00000091);
        mDevice.pressKeyCode(0x00000092);
        mDevice.pressKeyCode(0x00000092);
        mDevice.pressKeyCode(0x00000093);
        mDevice.pressKeyCode(0x00000093);
        mDevice.pressKeyCode(0x00000094);
        mDevice.pressKeyCode(0x00000093);
        TimeUnit.SECONDS.sleep(1);

        UiObject wrongPinPopup = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_dialog_retry"));
        try {
            wrongPinPopup.click();
        } catch (UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(1);
            wrongPinPopup.click();
        }
        TimeUnit.SECONDS.sleep(1);
        try {
            confirm.click();
        } catch (UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(1);
            confirm.click();
        }

        //Check summary screen one more time
        assertThat(ACC_NUMBER_PAYMENT, is(equalTo(sumAccNrTo.getText().replace(" ", ""))));
        assertThat(moneyAmount+"", is(equalTo(sumAmount.getText()
                .substring(4, sumAmount.getText().length()-3).replaceAll(",", ""))));
        assertThat(datePayment, is(equalTo(sumDate.getText())));
        assertThat(TITLE_PAYMENT, is(equalTo(sumTitle.getText())));
        assertThat(NAME_PAYMENT, is(equalTo(sumToName.getText())));
        try {
            confirm.click();
        } catch (UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(1);
            confirm.click();
        }

        //Pin input
        mDevice.pressKeyCode(0x00000091);
        mDevice.pressKeyCode(0x00000091);
        mDevice.pressKeyCode(0x00000092);
        mDevice.pressKeyCode(0x00000092);
        mDevice.pressKeyCode(0x00000093);
        mDevice.pressKeyCode(0x00000093);
        mDevice.pressKeyCode(0x00000094);
        mDevice.pressKeyCode(0x00000094);

        //Success pop-up
        TimeUnit.SECONDS.sleep(1);
        UiObject successPopup = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_dialog_ok"));

        try {
            successPopup.click();
        } catch (UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(1);
            successPopup.click();
        }

        try{
            assertThat(DASHBOARD_BAR_TITLE, is(equalTo(topBarDashboard.getText())));
        } catch( UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(2);
            assertThat(DASHBOARD_BAR_TITLE, is(equalTo(topBarDashboard.getText())));
        }
    }

    //Filter test
    //Part one: filter transactions by each product

    @Test
    public void test5() throws InterruptedException, UiObjectNotFoundException {

        UiObject toolbarTitle = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/toolbar_title"));
        try{
            assertThat(DASHBOARD_BAR_TITLE, is(equalTo(toolbarTitle.getText())));
        } catch( UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(2);
            assertThat(DASHBOARD_BAR_TITLE, is(equalTo(toolbarTitle.getText())));
        }

        //Transactions screen
        UiObject transactionBottomMenu = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tab_transactions"));
        transactionBottomMenu.click();

        TimeUnit.SECONDS.sleep(3);
        try{
            assertThat(TRANSACTIONS_BAR_TITLE, is(equalTo(toolbarTitle.getText())));
            toolbarTitle.isFocusable();
        } catch (UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(2);
            toolbarTitle.isFocusable();
        }

        //Filters screen
        UiObject topBarFilerIcon = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/menu_filter"));
        topBarFilerIcon.click();
        TimeUnit.SECONDS.sleep(1);

        try{
            assertThat(FILTERS_BAR_TITLE, is(equalTo(toolbarTitle.getText())));
        } catch( UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(2);
            assertThat(FILTERS_BAR_TITLE, is(equalTo(toolbarTitle.getText())));
        }

        //Find all necessary objects on the filters screen
        UiObject filterProductsBtn = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/iv_filter_product_arrow"));
        UiObject filterDate1M = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/cb_radio_1m"));
        UiObject filterDate3M = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/cb_radio_3m"));
        UiObject filterDate6M = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/cb_radio_6m"));
        UiObject filterDate12M = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/cb_radio_12m"));
        UiObject filterDateOther = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/cb_radio_other"));
        UiObject filterDateOtherFrom = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_date_from_content"));
        UiObject filterDateOtherTo = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_date_to_content"));
        UiObject filterFundsIncoming = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/cb_radio_incoming"));
        UiObject filterFundsOutcoming = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/cb_radio_outcoming"));
        UiObject filterAmountFrom = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/et_amount_from_content"));
        UiObject filterAmountTo = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/et_amount_to_content"));
        UiObject filterTransType = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/cirv_type"));
        UiObject filterClearFilters = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/ll_clear"));
        UiObject productPersonalGBP = mDevice.findObject(new UiSelector().text(GBP_PERSONAL_NUMBER));
        UiObject productPersonalUSD = mDevice.findObject(new UiSelector().text(USD_PERSONAL_NUMBER));
        UiObject productSavingEmptyEUR = mDevice.findObject(new UiSelector().text(EUR_SAVING_NUMBER));
        UiObject showResultBtn = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_show_results"));
        UiObject emptyListTv = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_empty"));

        //Firsty we will search transactions for fully empty account
        filterProductsBtn.click();
        TimeUnit.SECONDS.sleep(1);

        try{
            assertThat(PRODUCT_BAR_TITLE, is(equalTo(toolbarTitle.getText())));
        } catch( UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(2);
            assertThat(PRODUCT_BAR_TITLE, is(equalTo(toolbarTitle.getText())));
        }
        productSavingEmptyEUR.click();
        showResultBtn.click();
        try{
            assertThat(FILTERS_BAR_TITLE, is(equalTo(toolbarTitle.getText())));
        } catch( UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(2);
            assertThat(FILTERS_BAR_TITLE, is(equalTo(toolbarTitle.getText())));
        }
        showResultBtn.click();
        try{
            assertThat(TRANSACTIONS_BAR_TITLE, is(equalTo(toolbarTitle.getText())));
        } catch( UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(2);
            assertThat(TRANSACTIONS_BAR_TITLE, is(equalTo(toolbarTitle.getText())));
        }
        emptyListTv.isFocusable();

        //Now we will do the same, but for account with transactions
        topBarFilerIcon.click();
        TimeUnit.SECONDS.sleep(1);

        try{
            assertThat(FILTERS_BAR_TITLE, is(equalTo(toolbarTitle.getText())));
        } catch( UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(2);
            assertThat(FILTERS_BAR_TITLE, is(equalTo(toolbarTitle.getText())));
        }
        filterProductsBtn.click();
        TimeUnit.SECONDS.sleep(1);

        try{
            assertThat(PRODUCT_BAR_TITLE, is(equalTo(toolbarTitle.getText())));
        } catch( UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(2);
            assertThat(PRODUCT_BAR_TITLE, is(equalTo(toolbarTitle.getText())));
        }
        productSavingEmptyEUR.click();

        // Now we should have no filters selected
        // and we choose account with few transactions

        productPersonalGBP.click();
        showResultBtn.click();
        try{
            assertThat(FILTERS_BAR_TITLE, is(equalTo(toolbarTitle.getText())));
        } catch( UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(1);
            assertThat(FILTERS_BAR_TITLE, is(equalTo(toolbarTitle.getText())));
        }
        showResultBtn.click();
        try{
            assertThat(TRANSACTIONS_BAR_TITLE, is(equalTo(toolbarTitle.getText())));
        } catch( UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(1);
            assertThat(TRANSACTIONS_BAR_TITLE, is(equalTo(toolbarTitle.getText())));
        }
        //We have screen with filtered transactions and we we will check if all of transactions are related with chose account

        int transactionsIndex=1;
        String FORMATED_ACC_NUMBER_GBP = GBP_PERSONAL_NUMBER.replace(" ","").substring(0,4) + " "
                + GBP_PERSONAL_NUMBER.replace(" ","").substring(4,8) + " "
                + GBP_PERSONAL_NUMBER.replace(" ","").substring(8,12) + " "
                + GBP_PERSONAL_NUMBER.replace(" ","").substring(12,16) + " "
                + GBP_PERSONAL_NUMBER.replace(" ","").substring(16,20) + " "
                + GBP_PERSONAL_NUMBER.replace(" ","").substring(20);
        UiScrollable scrollable = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));

        do{
            while(mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(transactionsIndex)).exists()){
                UiObject onTransactionClick = mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(transactionsIndex));
                onTransactionClick.click();
                try {
                    UiObject ibanField = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_account_number_from")
                            .text(FORMATED_ACC_NUMBER_GBP));
                    ibanField.isEnabled();
                    mDevice.pressKeyCode(0x00000004);
                    ++transactionsIndex;
                } catch (UiObjectNotFoundException e) {
                    UiObject ibanField = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_account_number_to")
                            .text(FORMATED_ACC_NUMBER_GBP));
                    ibanField.isEnabled();

                    mDevice.pressKeyCode(0x00000004);
                    ++transactionsIndex;
                }
            }
            if (scrollable.scrollForward())
            transactionsIndex =0;
            else break;
        } while(scrollable.isScrollable());

        //After check of every transaction we will choose another account and do the same thing again

        topBarFilerIcon.click();
        filterProductsBtn.click();
        productPersonalGBP.click();
        productPersonalUSD.click();
        showResultBtn.click();
        try{
            assertThat(FILTERS_BAR_TITLE, is(equalTo(toolbarTitle.getText())));
        } catch( UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(2);
            assertThat(FILTERS_BAR_TITLE, is(equalTo(toolbarTitle.getText())));
        }
        showResultBtn.click();
        try{
            assertThat(TRANSACTIONS_BAR_TITLE, is(equalTo(toolbarTitle.getText())));
        } catch( UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(2);
            assertThat(TRANSACTIONS_BAR_TITLE, is(equalTo(toolbarTitle.getText())));
        }

    transactionsIndex=1;
    String FORMATED_ACC_NUMBER_USD = USD_PERSONAL_NUMBER.replace(" ","").substring(0,4) + " "
            + USD_PERSONAL_NUMBER.replace(" ","").substring(4,8) + " "
            + USD_PERSONAL_NUMBER.replace(" ","").substring(8,12) + " "
            + USD_PERSONAL_NUMBER.replace(" ","").substring(12,16) + " "
            + USD_PERSONAL_NUMBER.replace(" ","").substring(16,20) + " "
            + USD_PERSONAL_NUMBER.replace(" ","").substring(20);
        do{
        while(mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(transactionsIndex)).exists()){
            UiObject onTransactionClick = mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(transactionsIndex));
            onTransactionClick.click();
            try {
                UiObject ibanField = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_account_number_from")
                        .text(FORMATED_ACC_NUMBER_USD));
                ibanField.isEnabled();
                mDevice.pressKeyCode(0x00000004);
                ++transactionsIndex;
            } catch (UiObjectNotFoundException e) {
                UiObject ibanField = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_account_number_to")
                        .text(FORMATED_ACC_NUMBER_USD));
                ibanField.isEnabled();

                mDevice.pressKeyCode(0x00000004);
                ++transactionsIndex;
            }
        }
        if (scrollable.scrollForward())
            transactionsIndex =0;
        else break;
        } while(scrollable.isScrollable());
            topBarFilerIcon.click();
            filterProductsBtn.click();
            productPersonalUSD.click();
            showResultBtn.click();
            //At the end we are on the screen with all filter options
    }

    public ArrayList<UiObject2> retrieveTopLevelUiObjects(BySelector uiSelector) throws UiObjectNotFoundException {
        int i = 0;
        ArrayList<UiObject2> uiObjects = (ArrayList<UiObject2>) mDevice.findObjects(uiSelector);
        return uiObjects;
    }

    //Next we will test date filter
    @Test
    public void test6() throws Exception {

        //Get dates for each of the filters (1M, 3M, 6M, 12M)
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

        // Firstly for 1M
        cal.roll(Calendar.MONTH, -1);
        String date1M = format.format(cal.getTime()).toUpperCase();
        Date date1MD = format.parse(date1M);

        // For 3M
        cal.roll(Calendar.MONTH,-2);
        String date3M = format.format(cal.getTime()).toUpperCase();
        Date date3MD = format.parse(date3M);

        // For 6M
        cal.roll(Calendar.MONTH,-3);
        String date6M = format.format(cal.getTime()).toUpperCase();
        Date date6MD = format.parse(date6M);

        // For 12M
        cal.roll(Calendar.MONTH,6);
        cal.roll(Calendar.YEAR,-1);
        String date12M = format.format(cal.getTime()).toUpperCase();
        Date date12MD = format.parse(date12M);

        UiObject topBarFilerIcon = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/menu_filter"));
        UiObject filterProductsBtn = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/iv_filter_product_arrow"));
        UiObject filterDate1M = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/cb_radio_1m"));
        UiObject filterDate3M = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/cb_radio_3m"));
        UiObject filterDate6M = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/cb_radio_6m"));
        UiObject filterDate12M = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/cb_radio_12m"));
        UiObject filterDateOther = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/cb_radio_other"));
        UiObject filterDateOtherFrom = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_date_from_content"));
        UiObject calendarNextIcon = mDevice.findObject(new UiSelector().resourceId("android:id/next"));
        UiObject filterDateOtherTo = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_date_to_content"));
        UiObject toolbarTitle = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/toolbar_title"));
        UiObject calendarOK = mDevice.findObject(new UiSelector().resourceId("android:id/button1"));
        UiObject filterFundsIncoming = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/cb_radio_incoming"));
        UiObject filterFundsOutcoming = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/cb_radio_outcoming"));
        UiObject filterAmountFrom = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/et_amount_from_content"));
        UiObject filterAmountTo = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/et_amount_to_content"));
        UiObject filterTransType = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/cirv_type"));
        UiObject filterClearFilters = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/ll_clear"));
        UiObject productPersonalGBP = mDevice.findObject(new UiSelector().text(GBP_PERSONAL_NUMBER));
        UiObject productPersonalUSD = mDevice.findObject(new UiSelector().text(USD_PERSONAL_NUMBER));
        UiObject productSavingEmptyEUR = mDevice.findObject(new UiSelector().text(EUR_SAVING_NUMBER));
        UiObject showResultBtn = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_show_results"));
        UiScrollable scrollable = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));
        UiObject emptyListTv = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_empty"));



        //Firstly filter transactions for one month
        filterDate1M.click();
        showResultBtn.click();

        Calendar calendar = Calendar.getInstance();
        String currentDateString = format.format(calendar.getTime());
        Date currentDate = format.parse(currentDateString);

        do {
            List<UiObject2> dateField = mDevice.findObjects(By.res("com.ailleron.longbank.gtest:id/tv_transaction_date"));
            for (int i = 0; i < dateField.size(); i++) {
                Date transactionDate = format.parse(dateField.get(i).getText());
                if (transactionDate.before(currentDate) && transactionDate.after(date1MD)) {
                    Log.i("Date", "is Ok");
                } else if (transactionDate.equals(currentDate) || transactionDate.equals(date1MD)) {
                    Log.i("Date", "is Ok");
                } else {
                    throw new Exception("Wrong date of the transaction");
                }
            }
           if( scrollable.scrollForward()){
                Log.i("Page ","scrolled forward");
           }
            else break;
        } while(scrollable.isScrollable());

        //Look's like for 1 month filter works well, now we will check it for 3M
        topBarFilerIcon.click();
        filterDate3M.click();
        showResultBtn.click();

        do {
            List<UiObject2> dateField = mDevice.findObjects(By.res("com.ailleron.longbank.gtest:id/tv_transaction_date"));
            for (int i = 0; i < dateField.size(); i++) {
                Date transactionDate = format.parse(dateField.get(i).getText());
                if (transactionDate.before(currentDate) && transactionDate.after(date3MD)) {
                    Log.i("Date", "is Ok");
                } else if (transactionDate.equals(currentDate) || transactionDate.equals(date3MD)) {
                    Log.i("Date", "is Ok");
                } else {
                    throw new Exception("Wrong date of the transaction");
                }
            }
            if( scrollable.scrollForward()){
                Log.i("Page ","scrolled forward");
            }
            else break;
        } while(scrollable.isScrollable());

        //For 6M
        topBarFilerIcon.click();
        filterDate6M.click();
        showResultBtn.click();

        do {
            List<UiObject2> dateField = mDevice.findObjects(By.res("com.ailleron.longbank.gtest:id/tv_transaction_date"));
            for (int i = 0; i < dateField.size(); i++) {
                Date transactionDate = format.parse(dateField.get(i).getText());
                if (transactionDate.before(currentDate) && transactionDate.after(date3MD)) {
                    Log.i("Date", "is Ok");
                } else if (transactionDate.equals(currentDate) || transactionDate.equals(date3MD)) {
                    Log.i("Date", "is Ok");
                } else {
                    throw new Exception("Wrong date of the transaction");
                }
            }
            if( scrollable.scrollForward()){
                Log.i("Page ","scrolled forward");
            }
            else break;
        } while(scrollable.isScrollable());

        //For 1 year
        topBarFilerIcon.click();
        filterDate12M.click();
        showResultBtn.click();

        do {
            List<UiObject2> dateField = mDevice.findObjects(By.res("com.ailleron.longbank.gtest:id/tv_transaction_date"));
            for (int i = 0; i < dateField.size(); i++) {
                Date transactionDate = format.parse(dateField.get(i).getText());
                if (transactionDate.before(currentDate) && transactionDate.after(date12MD)) {
                    Log.i("Date", "is Ok");
                } else if (transactionDate.equals(currentDate) || transactionDate.equals(date12MD)) {
                    Log.i("Date", "is Ok");
                } else {
                    throw new Exception("Wrong date of the transaction");
                }
            }
            if( scrollable.scrollForward()){
                Log.i("Page ","scrolled forward");
            }
            else break;
        } while(scrollable.isScrollable());

        topBarFilerIcon.click();


        //Filter with correct from date
        filterDateOther.click();
        filterDateOtherFrom.click();
        UiObject dateHeader = mDevice.findObject(new UiSelector().resourceId("android:id/date_picker_header_date"));
        UiObject dateHeaderYear = mDevice.findObject(new UiSelector().resourceId("android:id/date_picker_header_year"));
        int dateToChoose = Integer.parseInt(dateHeader.getText().substring(5,7))-5;
        String headerDate = dateHeader.getText().substring(5) + " " + dateHeaderYear.getText();
        UiObject calendarDayNumberFrom = mDevice.findObject(new UiSelector().text(Integer.toString(dateToChoose)));
        calendarDayNumberFrom.click();
        calendarOK.click();
        showResultBtn.click();
        Date headerDateToCompareFrom = format.parse(headerDate);

        do {
            List<UiObject2> dateField = mDevice.findObjects(By.res("com.ailleron.longbank.gtest:id/tv_transaction_date"));
            for (int i = 0; i < dateField.size(); i++) {
                Date transactionDate = format.parse(dateField.get(i).getText());
                if (transactionDate.before(headerDateToCompareFrom) || transactionDate.equals(headerDateToCompareFrom)) {
                    Log.i("Date", "is Ok");
                } else {
                    throw new Exception("Wrong date of the transaction");
                }
            }
            if( scrollable.scrollForward()){
                Log.i("Page ","scrolled forward");
            }
            else break;
        } while(scrollable.isScrollable());
        topBarFilerIcon.click();

        //From date and To date check
        filterDateOtherTo.click();
        int dateToChoose2 = Integer.parseInt(dateHeader.getText().substring(5,7))-1;
        UiObject calendarDayNumberTo = mDevice.findObject(new UiSelector().text(Integer.toString(dateToChoose2)));
        calendarDayNumberTo.click();
        calendarOK.click();
        showResultBtn.click();
        Date headerDateToCompareTo = format.parse(headerDate);

        do {
            List<UiObject2> dateField = mDevice.findObjects(By.res("com.ailleron.longbank.gtest:id/tv_transaction_date"));
            for (int i = 0; i < dateField.size(); i++) {
                Date transactionDate = format.parse(dateField.get(i).getText());
                if (transactionDate.before(headerDateToCompareTo) || transactionDate.equals(headerDateToCompareTo)&&
                        transactionDate.after(headerDateToCompareFrom)||transactionDate.equals(headerDateToCompareFrom)) {
                    Log.i("Date", "is Ok");
                } else {
                    throw new Exception("Wrong date of the transaction");
                }
            }
            if( scrollable.scrollForward()){
                Log.i("Page ","scrolled forward");
            }
            else break;
        } while(scrollable.isScrollable());
        topBarFilerIcon.click();


        //Filter with future date, must be empty list
        filterDateOther.click();
        filterDateOther.click();
        filterDateOtherFrom.click();
        calendarNextIcon.click();
        calendarNextIcon.click();
        calendarDayNumberFrom.click();
        calendarOK.click();
        showResultBtn.click();
        emptyListTv.exists();
        topBarFilerIcon.click();
        filterDateOther.click();


        // Only incoming transactions filter

        filterFundsIncoming.click();
        showResultBtn.click();
        TimeUnit.SECONDS.sleep(2);
        do {
            List<UiObject2> transactionValue = mDevice.findObjects(By.res("com.ailleron.longbank.gtest:id/tv_transaction_value"));
            for (int i = 0; i < transactionValue.size(); i++) {
                char transactionSign = (transactionValue.get(i).getText()).charAt(4);
                if (transactionSign == '+'){
                    Log.i("Date", "is Ok");
                } else {
                    throw new Exception("Wrong date of the transaction");
                }
            }
            if( scrollable.scrollForward()){
                Log.i("Page ","scrolled forward");
            }
            else break;
        } while(scrollable.isScrollable());
        topBarFilerIcon.click();

        filterFundsOutcoming.click();
        showResultBtn.click();
        TimeUnit.SECONDS.sleep(2);
        do {
            List<UiObject2> transactionValue = mDevice.findObjects(By.res("com.ailleron.longbank.gtest:id/tv_transaction_value"));
            for (int i = 0; i < transactionValue.size(); i++) {
                char transactionSign = (transactionValue.get(i).getText()).charAt(4);
                if (transactionSign == '-'){
                    Log.i("Date", "is Ok");
                } else {
                    throw new Exception("Wrong date of the transaction");
                }
            }
            if( scrollable.scrollForward()){
                Log.i("Page ","scrolled forward");
            }
            else break;
        } while(scrollable.isScrollable());
    topBarFilerIcon.click();
    filterFundsOutcoming.click();


        // From - To amount check
        // Firstly check filter for 'to 0' - empty list

        filterAmountTo.setText("0");
        showResultBtn.click();
        try{
            assertThat(TRANSACTIONS_BAR_TITLE, is(equalTo(toolbarTitle.getText())));
        } catch( UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(2);
            assertThat(TRANSACTIONS_BAR_TITLE, is(equalTo(toolbarTitle.getText())));
        }
        emptyListTv.isFocusable();
        topBarFilerIcon.click();
        filterAmountTo.setText("");

        // Filter from 150
        filterAmountFrom.setText("150");
        showResultBtn.click();
        TimeUnit.SECONDS.sleep(2);

        do {
            List<UiObject2> transactionValue = mDevice.findObjects(By.res("com.ailleron.longbank.gtest:id/tv_transaction_value"));
            for (int i = 0; i < transactionValue.size(); i++) {
                int transactionIntValue = Integer.parseInt(transactionValue.get(i).getText().substring(5,transactionValue.get(i)
                        .getText().length()-3).replaceAll(",",""));
                if (transactionIntValue>=150){
                    Log.i("Transaction value", "is Ok");
                } else {
                    throw new Exception("Wrong transaction value");
                }
            }
            if( scrollable.scrollForward()){
                Log.i("Page ","scrolled forward");
            }
            else break;
        } while(scrollable.isScrollable());
        topBarFilerIcon.click();
        filterAmountFrom.setText("");

        //Filter to 150
        filterAmountTo.setText("150");
        showResultBtn.click();
        TimeUnit.SECONDS.sleep(2);

        do {
            List<UiObject2> transactionValue = mDevice.findObjects(By.res("com.ailleron.longbank.gtest:id/tv_transaction_value"));
            for (int i = 0; i < transactionValue.size(); i++) {
                int transactionIntValue = Integer.parseInt(transactionValue.get(i).getText().substring(5,transactionValue.get(i)
                        .getText().length()-3).replaceAll(",",""));
                if (transactionIntValue<=150){
                    Log.i("Transaction value", "is Ok");
                } else {
                    throw new Exception("Wrong transaction value");
                }
            }
            if( scrollable.scrollForward()){
                Log.i("Page ","scrolled forward");
            }
            else break;
        } while(scrollable.isScrollable());
        topBarFilerIcon.click();
        filterAmountFrom.setText("");

        //Filter from 150 to 1000
        filterAmountFrom.setText("150");
        filterAmountTo.setText("1000");
        showResultBtn.click();
        TimeUnit.SECONDS.sleep(2);

        do {
            List<UiObject2> transactionValue = mDevice.findObjects(By.res("com.ailleron.longbank.gtest:id/tv_transaction_value"));
            for (int i = 0; i < transactionValue.size(); i++) {
                int transactionIntValue = Integer.parseInt(transactionValue.get(i).getText().substring(5,transactionValue.get(i)
                        .getText().length()-3).replaceAll(",",""));
                if (transactionIntValue>=150&&transactionIntValue<=1000){
                    Log.i("Transaction value", "is Ok");
                } else {
                    throw new Exception("Wrong transaction value");
                }
            }
        } while(scrollable.scrollForward());
        topBarFilerIcon.click();
        filterAmountFrom.setText("");
        filterAmountTo.setText("");
    }

    //Transaction type filter check
    @Test
    public void test7() throws Exception {

        UiObject topBarFilerIcon = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/menu_filter"));
        UiObject filterTransType = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/cirv_type"));
        UiObject filterTransStatusContent = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_status_content"));
        UiObject transactionTypeContent = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_transaction_type_content"));
        UiObject showResultBtn = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_show_results"));
        UiScrollable scrollable = new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView"));
        UiScrollable scrollTransactionDetails = new UiScrollable(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/sv_transaction_details_data"));
        UiObject typeTransfers = mDevice.findObject(new UiSelector().text("Transfers"));
        UiObject typePayments = mDevice.findObject(new UiSelector().text("Payments"));
        UiObject typeFees = mDevice.findObject(new UiSelector().text("Fees"));
        UiObject typeExecuted = mDevice.findObject(new UiSelector().text("Executed"));
        UiObject typePlanned = mDevice.findObject(new UiSelector().text("Planned"));
        UiObject typeBlockades = mDevice.findObject(new UiSelector().text("Blockades"));
        UiObject btnFilter = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_filter"));
        int transactionsIndex = 1;
        filterTransType.click();

        //Firstly we will check if all necessary options exists
        typeBlockades.exists();
        typeExecuted.exists();
        typeFees.exists();
        typePayments.exists();
        typeTransfers.exists();
        typePlanned.exists();

/*

        // Filter check for transactions with transfer type
        typeTransfers.click();
        btnFilter.click();
        showResultBtn.click();
        do {
            List<UiObject2> titles = mDevice.findObjects(By.res("com.ailleron.longbank.gtest:id/tv_transaction_name"));
            for (int i = 0; i < titles.size(); i++) {
                if (titles.get(i).getText().equals("Own transfer")){
                    Log.i("Transaction title", "is Ok");
                } else {
                    throw new Exception("Wrong transaction title");
                }
            }
            if( scrollable.scrollForward()){
                Log.i("Page ","scrolled forward");
            }
            else break;
        } while(scrollable.isScrollable());
        topBarFilerIcon.click();
        filterTransType.click();
        typeTransfers.click();


        // Check transaction type for payment
        typePayments.click();
        btnFilter.click();
        showResultBtn.click();

        do {
            while(mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(transactionsIndex)).exists()){
                UiObject onTransactionClick = mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(transactionsIndex));
                onTransactionClick.click();
                TimeUnit.SECONDS.sleep(2);
                scrollTransactionDetails.scrollForward();
                try {
                    transactionTypeContent.exists();
                    assertThat(transactionTypeContent.getText(), is(equalTo(PAYMENT_BAR_TITLE)));
                    mDevice.pressKeyCode(0x00000004);
                    ++transactionsIndex;
                    } catch (UiObjectNotFoundException e) {
                    TimeUnit.SECONDS.sleep(1);
                    transactionTypeContent.exists();
                    assertThat(transactionTypeContent.getText(), is(equalTo(PAYMENT_BAR_TITLE)));
                    mDevice.pressKeyCode(0x00000004);
                    ++transactionsIndex;
                    }
            }
            transactionsIndex =0;
            } while(scrollable.scrollForward());

        topBarFilerIcon.click();
        filterTransType.click();
        typePayments.click();
*/


        // Check transaction type for fees

        typeFees.click();
        btnFilter.click();
        showResultBtn.click();
        TimeUnit.SECONDS.sleep(2);
        do {
            while(mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(transactionsIndex)).exists()){
                //TimeUnit.SECONDS.sleep(2);
                UiObject onTransactionClick = mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(transactionsIndex));
                //UiObject test = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_transaction_date"));
                onTransactionClick.click();


                TimeUnit.SECONDS.sleep(2);
                scrollTransactionDetails.scrollForward();
                try {
                    transactionTypeContent.exists();
                    assertThat(transactionTypeContent.getText(), is(equalTo("FEE")));
                    mDevice.pressKeyCode(0x00000004);
                    ++transactionsIndex;
                } catch (UiObjectNotFoundException e) {
                    TimeUnit.SECONDS.sleep(1);
                    transactionTypeContent.exists();
                    assertThat(transactionTypeContent.getText(), is(equalTo("FEE")));
                    mDevice.pressKeyCode(0x00000004);
                    ++transactionsIndex;
                }
            }
            transactionsIndex =0;
        } while(scrollable.scrollForward());

        topBarFilerIcon.click();
        filterTransType.click();

        // Check transaction type for blockades

        typeFees.click();
        typeBlockades.click();
        btnFilter.click();
        showResultBtn.click();
        TimeUnit.SECONDS.sleep(2);
        do {
            while(mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(transactionsIndex)).exists()){
                UiObject onTransactionClick = mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(transactionsIndex));
                TimeUnit.SECONDS.sleep(2);
                onTransactionClick.click();
                TimeUnit.SECONDS.sleep(2);
                scrollTransactionDetails.scrollForward();
                try {
                    transactionTypeContent.exists();
                    filterTransStatusContent.exists();
                    assertThat(transactionTypeContent.getText(), is(equalTo("OTHER")));
                    assertThat(filterTransStatusContent.getText(), is(equalTo("LOCKED FUNDS")));
                    mDevice.pressKeyCode(0x00000004);
                    ++transactionsIndex;
                } catch (UiObjectNotFoundException e) {
                    transactionTypeContent.exists();
                    filterTransStatusContent.exists();
                    assertThat(transactionTypeContent.getText(), is(equalTo("OTHER")));
                    assertThat(filterTransStatusContent.getText(), is(equalTo("LOCKED FUNDS")));
                    mDevice.pressKeyCode(0x00000004);
                    ++transactionsIndex;
                }
            }
            transactionsIndex =0;
        } while(scrollable.scrollForward());

        topBarFilerIcon.click();

        // Check transaction type for executed
        filterTransType.click();
        typeBlockades.click();
        typeExecuted.click();
        btnFilter.click();
        showResultBtn.click();
        do {
            while(mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(transactionsIndex)).exists()){
                UiObject onTransactionClick = mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(transactionsIndex));
                onTransactionClick.click();
                TimeUnit.SECONDS.sleep(2);
                scrollTransactionDetails.scrollForward();
                try {
                    filterTransStatusContent.exists();
                    assertThat(filterTransStatusContent.getText(), is(equalTo("EXECUTED")));
                    mDevice.pressKeyCode(0x00000004);
                    ++transactionsIndex;
                } catch (UiObjectNotFoundException e) {
                    filterTransStatusContent.exists();
                    assertThat(filterTransStatusContent.getText(), is(equalTo("EXECUTED")));
                    mDevice.pressKeyCode(0x00000004);
                    ++transactionsIndex;
                }
            }
            transactionsIndex =0;
        } while(scrollable.scrollForward());

        topBarFilerIcon.click();

        //Checking filters for multiple types of transaction
        //Firstly Blockades + Executed
        filterTransType.click();
        typeExecuted.click();
        typeBlockades.click();
        btnFilter.click();
        showResultBtn.click();
        TimeUnit.SECONDS.sleep(2);
        do {
            while(mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(transactionsIndex)).exists()){
                UiObject onTransactionClick = mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(transactionsIndex));
                onTransactionClick.click();
                TimeUnit.SECONDS.sleep(2);
                scrollTransactionDetails.scrollForward();
                transactionTypeContent.exists();
                filterTransStatusContent.exists();
                if(filterTransStatusContent.getText().equals("LOCKED FUNDS")){
                    Log.e("Transaction", "blockade");
                } else if (filterTransStatusContent.getText().equals("EXECUTED")) {
                        Log.e("Transaction","executed");
                } else {
                    throw new IllegalArgumentException("Wrong filter result");
                }
                mDevice.pressKeyCode(0x00000004);
                ++transactionsIndex;
            }
            transactionsIndex =0;
        } while(scrollable.scrollForward());

        topBarFilerIcon.click();
    }
}



