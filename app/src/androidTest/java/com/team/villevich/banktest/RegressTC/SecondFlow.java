package com.team.villevich.banktest.RegressTC;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

import com.team.villevich.banktest.TestClass;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;
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
    //@Test
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
        try {
            UiObject passcodeTV = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_passcode_hint"));
            assertThat(LOGIN_ENTER_PASSCODE, is(equalTo(passcodeTV.getText())));

        } catch(UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(6);
            UiObject passcodeTV = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_passcode_hint"));
            assertThat(LOGIN_ENTER_PASSCODE, is(equalTo(passcodeTV.getText())));
        }
        TimeUnit.SECONDS.sleep(1);
        mDevice.pressKeyCode(0x00000091);
        mDevice.pressKeyCode(0x00000091);
        mDevice.pressKeyCode(0x00000092);
        mDevice.pressKeyCode(0x00000092);
        mDevice.pressKeyCode(0x00000093);
        mDevice.pressKeyCode(0x00000093);
        mDevice.pressKeyCode(0x00000094);
        mDevice.pressKeyCode(0x00000094);

        TimeUnit.SECONDS.sleep(1);
    }

    //TRANSFER
    //@Test
    public void test2() throws InterruptedException, UiObjectNotFoundException {
        String datePayment;

        //WCHODZIMY DO TRANSFER PRZEZ BOCZNE MENU
        try{
            UiObject topBarDashboard = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/toolbar_title"));
            assertThat(DASHBOARD_BAR_TITLE, is(equalTo(topBarDashboard.getText())));
        } catch( UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(2);
            UiObject topBarDashboard = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/toolbar_title"));
            assertThat(DASHBOARD_BAR_TITLE, is(equalTo(topBarDashboard.getText())));
        }
        try {
            UiObject sideMenu = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tab_menu"));
            sideMenu.click();
        }catch (UiObjectNotFoundException e){
            TimeUnit.SECONDS.sleep(2);
            UiObject sideMenu = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tab_menu"));
            sideMenu.click();
         }

        UiObject transferSideMenu = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/nav_transfer"));
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
            UiObject topBarDashboard = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/toolbar_title"));
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
    //@Test
    public void test3() throws UiObjectNotFoundException, InterruptedException {
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


        UiObject coutry = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_dropdown_item_text"));
        coutry.click();

        TimeUnit.SECONDS.sleep(1);
        mDevice.click(360,635);

        UiScrollable test = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
        test.scrollForward();
        TimeUnit.SECONDS.sleep(1);

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
    @Test
    public void test4() throws InterruptedException, UiObjectNotFoundException {

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
        String FORMATED_ACC_NUMBER = GBP_PERSONAL_NUMBER.replace(" ","").substring(0,4) + " "
                + GBP_PERSONAL_NUMBER.replace(" ","").substring(4,8) + " "
                + GBP_PERSONAL_NUMBER.replace(" ","").substring(8,12) + " "
                + GBP_PERSONAL_NUMBER.replace(" ","").substring(12,16) + " "
                + GBP_PERSONAL_NUMBER.replace(" ","").substring(16,20) + " "
                + GBP_PERSONAL_NUMBER.replace(" ","").substring(20);
        UiObject ibanField = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_account_number_from")
                .text(FORMATED_ACC_NUMBER));
        do {
            Log.i("SAMPLELOG","Beginning");
            UiObject filteredTransaction = mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(transactionsIndex));
            filteredTransaction.click();
            Log.i("SAMPLELOG","Clicked on transaction");
            ibanField.isEnabled();
            Log.i("SAMPLELOG","Checked iban field");
            Log.i("SAMPLELOG","before back press");
            mDevice.pressKeyCode(0x00000004);
            Log.i("SAMPLELOG","after back press");

            ++transactionsIndex;


        } while (mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(transactionsIndex)).isEnabled());
    }
}
