package com.team.villevich.banktest;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class End2end {
    private UiDevice mDevice;
    private String KLIENT_NAME = "ADAM";
    private String KLIENT_SURNAME = "ADAMMM";
    private String KLIENT_EMAIL = "hjhgfs23fg@gf.asf";
    private String KLIENT_LOGIN = "usejj4gj99";

    @Before
    public void startMainActivityFromHomeScreen() {

        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

    }

    //@Test
    public void cleanCache() throws UiObjectNotFoundException, InterruptedException {
        mDevice.pressHome();
        UiObject sideMenu = mDevice.findObject(new UiSelector().textMatches("Ustawienia"));
        sideMenu.clickAndWaitForNewWindow();
        UiScrollable scroll = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
        scroll.scrollForward();
        UiObject appClick = mDevice.findObject(new UiSelector().textMatches("Aplikacje"));
        appClick.clickAndWaitForNewWindow();
        TimeUnit.SECONDS.sleep(1);
        UiScrollable scroll2 = new UiScrollable(new UiSelector().resourceId("android:id/decor_content_parent"));
        scroll2.scrollForward();
        UiScrollable scroll3 = new UiScrollable(new UiSelector().resourceId("android:id/decor_content_parent"));
        scroll3.scrollForward();

        try {
            UiObject bankAppSet = mDevice.findObject(new UiSelector().textMatches("Golden Sand Bank"));
            bankAppSet.click();
        } catch (UiObjectNotFoundException e) {
            UiScrollable scroll20 = new UiScrollable(new UiSelector().resourceId("android:id/decor_content_parent"));
            scroll20.scrollForward();
            UiObject bankAppSet = mDevice.findObject(new UiSelector().textMatches("Golden Sand Bank"));
            bankAppSet.click();
        }

        UiObject memory = mDevice.findObject(new UiSelector().resourceId("androidhwext:id/preference_emui_root"));
        memory.click();
        UiObject cleanDate = mDevice.findObject(new UiSelector().resourceId("com.android.settings:id/button_2"));
        cleanDate.click();
        UiObject accept = mDevice.findObject(new UiSelector().resourceId("android:id/button1"));
        accept.click();
        mDevice.pressHome();
    }

    @Test
    public void register() throws UiObjectNotFoundException, InterruptedException, RemoteException {
        UiObject appButton1 = mDevice.findObject(new UiSelector().text("Golden Sand Bank"));
        appButton1.click();
        TimeUnit.SECONDS.sleep(6);
        UiObject register = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_register"));
        register.clickAndWaitForNewWindow();

        //FIRST PAGE------------------------------------------------------------------------------------------------------------
        UiObject selectAll = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_UI__V_E_SELECT_ALL_PRODUCTS__Q__Text"));
        selectAll.clickAndWaitForNewWindow();
        UiScrollable scroll = new UiScrollable(new UiSelector().className("android.webkit.WebView"));
        scroll.scrollForward();
        TimeUnit.SECONDS.sleep(3);
        UiObject next1 = mDevice.findObject(new UiSelector().text("NEXT"));
        next1.clickAndWaitForNewWindow();

        //SECOND PAGE------------------------------------------------------------------------------------------------------------
        UiObject citizenship = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_NATIONALITY_"));
        citizenship.clickAndWaitForNewWindow();
        UiObject countryTitle = mDevice.findObject(new UiSelector().text("United Kingdom").index(4));
        countryTitle.click();
        TimeUnit.SECONDS.sleep(2);
        UiObject genderTitle = mDevice.findObject(new UiSelector().text("Mr"));
        genderTitle.click();
        UiObject firstName = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_FIRST_NAME"));
        firstName.setText(KLIENT_NAME);
        UiObject lastName = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_LAST_NAME"));
        lastName.setText(KLIENT_SURNAME);
        UiScrollable scroll1 = new UiScrollable(new UiSelector().className("android.webkit.WebView"));
        scroll1.scrollForward();
        UiObject email = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_EMAIL"));
        email.setText(KLIENT_EMAIL);
        UiObject emailConfirm = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_EMAIL_REPEATED"));
        emailConfirm.setText(KLIENT_EMAIL);
        TimeUnit.SECONDS.sleep(1);
        UiObject callingCode = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_MOBILE_PHONE_CODE"));
        callingCode.click();

        try {
            UiObject callingCodeSelect = mDevice.findObject(new UiSelector().text("Poland (+48)"));
            callingCodeSelect.click();
        } catch (UiObjectNotFoundException e) {
            UiScrollable scroll2 = new UiScrollable(new UiSelector().className("android.webkit.WebView"));
            scroll2.scrollForward();
            callingCode.click();
            UiObject callingCodeSelect = mDevice.findObject(new UiSelector().text("Poland (+48)"));
            callingCodeSelect.click();
        }

        UiObject phoneNumber = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_MOBILE_PHONE_NUMBER"));
        phoneNumber.setText("501464633");
        UiScrollable scroll2 = new UiScrollable(new UiSelector().className("android.webkit.WebView"));
        scroll2.scrollForward();
        TimeUnit.SECONDS.sleep(1);

        //SMS
        UiObject sendSms = mDevice.findObject(new UiSelector().textContains("SMS").className("android.view.View"));
        sendSms.click();
        mDevice.pressHome();
        UiObject messages = mDevice.findObject(new UiSelector().textMatches("Wiadomości"));
        messages.click();
        TimeUnit.SECONDS.sleep(2);
        UiObject messages1 = mDevice.findObject(new UiSelector().textContains("Golden Sand").className("android.widget.TextView"));
        messages1.click();
        TimeUnit.SECONDS.sleep(3);
        mDevice.click(529,964);

        String copyText = null;
        try {
            UiObject codeField = mDevice.findObject(new UiSelector().resourceId("android:id/alertTitle"));
            copyText = codeField.getText();
        } catch (UiObjectNotFoundException e) {
            TimeUnit.SECONDS.sleep(1);
            mDevice.click(529,964);
            UiObject codeField = mDevice.findObject(new UiSelector().resourceId("android:id/alertTitle"));
            copyText = codeField.getText();
        }
        mDevice.pressBack();
        mDevice.pressBack();
        mDevice.pressHome();
        UiObject appButton = mDevice.findObject(new UiSelector().text("Golden Sand Bank"));
        appButton.click();
        TimeUnit.SECONDS.sleep(1);
        UiScrollable scroll3 = new UiScrollable(new UiSelector().className("android.webkit.WebView"));
        scroll3.scrollForward();
        TimeUnit.SECONDS.sleep(1);
        UiObject codeFieldPaste = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_PASSWORD_TYPED"));
        codeFieldPaste.setText(copyText);
        UiObject next3 = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_nav__Next_CONTACT_DETAILS"));
        next3.clickAndWaitForNewWindow();

        //THIRD PAGE------------------------------------------------------------------------------------------------------------

        UiObject nationalInsurance = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_UNIQUE_ID_NUMBER"));
        UKdoks uKdoks = new UKdoks();
        nationalInsurance.setText(uKdoks.counterNum());
        UiObject typeDok = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_DOCUMENT_TYPE").text("Select"));
        typeDok.click();
        TimeUnit.SECONDS.sleep(1);
        UiObject typeDok1 = mDevice.findObject(new UiSelector().resourceId("android:id/text1").text("Passport").index(1));
        typeDok1.clickAndWaitForNewWindow();
        TimeUnit.SECONDS.sleep(7);
        UiObject passportNr = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_DOCUMENT_NUMBER"));
        passportNr.setText(uKdoks.countPassport());
        UiObject dateOfIssue = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_DATE_OF_ISSUE"));
        dateOfIssue.setText("24/08/2017");
        UiObject placeBirth = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_PLACE_OF_BIRTH"));
        placeBirth.click();
        UiObject placeBirthChoice = mDevice.findObject(new UiSelector().text("United Kingdom").index(4));
        placeBirthChoice.click();
        UiScrollable scroll5 = new UiScrollable(new UiSelector().className("android.webkit.WebView"));
        scroll5.scrollForward();
        TimeUnit.SECONDS.sleep(1);
        UiObject birthDate = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_BIRTH_DATE"));
        birthDate.setText("24/08/1991");
        UiObject taxesCountry = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content__V_E_FATCA__Q____ABL__0__ABR____Q__TAXES_COUNTRY"));
        taxesCountry.click();
        TimeUnit.SECONDS.sleep(1);
        UiObject taxesCountryChoice = mDevice.findObject(new UiSelector().text("United Kingdom"));
        taxesCountryChoice.click();
        TimeUnit.SECONDS.sleep(6);
        UiObject taxesNumber = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content__V_E_FATCA__Q____ABL__0__ABR____Q__TIN_NUMBER"));
        taxesNumber.setText(uKdoks.counterNum(10));
        UiObject motherMN = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_MOTHERS_NAME"));
        motherMN.setText(generateString());
        UiScrollable scroll6 = new UiScrollable(new UiSelector().className("android.webkit.WebView"));
        scroll6.scrollForward();
        TimeUnit.SECONDS.sleep(1);
        UiObject addresCity = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_RES_CITY"));
        addresCity.setText(generateString());
        UiObject addresCountry2 = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_RES_DISTRICT"));
        addresCountry2.setText(generateString());
        UiObject addresStreet = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_RES_STREET"));
        addresStreet.setText(generateString());

        try {
            UiObject addresStreetNumber = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_RES_STREET_NO"));
            addresStreetNumber.setText("13");
        } catch (UiObjectNotFoundException e){
            UiScrollable scroll8 = new UiScrollable(new UiSelector().className("android.webkit.WebView"));
            scroll8.scrollForward();
            UiObject addresStreetNumber = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_RES_STREET_NO"));
            addresStreetNumber.setText("13");
        }

        UiScrollable scroll7 = new UiScrollable(new UiSelector().className("android.webkit.WebView"));
        scroll7.scrollForward();
        TimeUnit.SECONDS.sleep(1);


        try {
            UiObject postalCode = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_RES_POSTAL_CODE"));
            postalCode.setText("77777");
        } catch (UiObjectNotFoundException e) {
            UiScrollable scroll8 = new UiScrollable(new UiSelector().className("android.webkit.WebView"));
            scroll8.scrollForward();
            TimeUnit.SECONDS.sleep(1);
            UiObject postalCode = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_RES_POSTAL_CODE"));
            postalCode.setText("77777");
        }

        UiScrollable scroll9 = new UiScrollable(new UiSelector().className("android.webkit.WebView"));
        scroll9.scrollForward();
        TimeUnit.SECONDS.sleep(1);
        UiObject next4 = mDevice.findObject(new UiSelector().text("Next").index(2));
        next4.click();

        //FOURTH PAGE------------------------------------------------------------------------------------------------------------
        UiObject sourceOfWealth = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_UI__V_E_SOW_IS_SAVINGS__Q__Text"));
        sourceOfWealth.click();
        UiScrollable scroll11 = new UiScrollable(new UiSelector().className("android.webkit.WebView"));
        scroll11.scrollForward();
        TimeUnit.SECONDS.sleep(1);
        UiObject sourceOfFunds = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_UI__V_E_SOF_IS_SALARY__Q__Text"));
        sourceOfFunds.click();
        UiScrollable scroll18 = new UiScrollable(new UiSelector().className("android.webkit.WebView"));
        scroll18.scrollForward();
        TimeUnit.SECONDS.sleep(1);
        UiObject gbp = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_ESTIMATED_MONTHLY_INCOME_GBP"));
        gbp.setText("100");
        UiObject loginName = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_LOGIN_CLIENT"));
        loginName.setText(KLIENT_LOGIN);
        UiScrollable scroll10 = new UiScrollable(new UiSelector().className("android.webkit.WebView"));
        scroll10.scrollForward();
        TimeUnit.SECONDS.sleep(1);
        UiObject contactLeng = mDevice.findObject(new UiSelector().text("English"));
        contactLeng.click();
        UiObject next5 = mDevice.findObject(new UiSelector().text("Next"));
        next5.click();
        TimeUnit.SECONDS.sleep(5);

        //FIFTH PAGE------------------------------------------------------------------------------------------------------------
        UiScrollable scroll12 = new UiScrollable(new UiSelector().className("android.webkit.WebView"));
        scroll12.scrollForward();
        scroll12.scrollForward();
        UiObject checkBox = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_UI__V_E_STATEMENT1__Q__Text"));
        checkBox.click();
        UiObject checkBox2 = mDevice.findObject(new UiSelector().index(0).text("No"));
        checkBox2.click();
        UiScrollable scroll13 = new UiScrollable(new UiSelector().className("android.webkit.WebView"));
        scroll13.scrollForward();
        UiObject checkBox3 = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_UI__V_E_STATEMENT_CRS__Q__Text"));
        checkBox3.click();
        UiScrollable scroll14 = new UiScrollable(new UiSelector().className("android.webkit.WebView"));
        scroll14.scrollForward();

        UiObject checkBox4 = mDevice.findObject(new UiSelector().text("I accept all of the following statements"));
        checkBox4.click();

        UiScrollable scroll15 = new UiScrollable(new UiSelector().className("android.webkit.WebView"));
        scroll15.scrollForward();
        UiObject checkBox5 = mDevice.findObject(new UiSelector().textStartsWith("In the event that the Bank intends"));
        checkBox5.click();
        UiScrollable scroll16 = new UiScrollable(new UiSelector().className("android.webkit.WebView"));
        scroll16.scrollForward();
        UiObject checkBox6 = mDevice.findObject(new UiSelector().textContains("No").className("android.view.View"));
        checkBox6.click();
        UiObject checkBox7 = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_UI__V_E_STATEMENT_PER__Q__Text"));
        checkBox7.click();
        mDevice.click(420,550);
        UiObject finish = mDevice.findObject(new UiSelector().text("SEND"));
        finish.click();
        TimeUnit.SECONDS.sleep(5);
        mDevice.click(52,103);
        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    public void login() throws InterruptedException, UiObjectNotFoundException {
        mDevice.pressHome();
        UiObject appButton1 = mDevice.findObject(new UiSelector().text("Golden Sand Bank"));
        appButton1.click();
        TimeUnit.SECONDS.sleep(6);
        UiObject loginBtn = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_login"));
        loginBtn.click();

        UiObject loginInput = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/passcode_EditText"));
        loginInput.setText(KLIENT_LOGIN);
        UiObject next = mDevice.findObject(new UiSelector().textMatches("NEXT STEP"));
        next.click();

        mDevice.pressHome();
        UiObject messages = mDevice.findObject(new UiSelector().textMatches("Wiadomości"));
        messages.click();
        TimeUnit.SECONDS.sleep(2);
        UiObject messages1 = mDevice.findObject(new UiSelector().textContains("Golden Sand").className("android.widget.TextView"));
        messages1.click();
        TimeUnit.SECONDS.sleep(3);
        mDevice.click(529,964);

        String copyText = null;
        try {
            UiObject codeField = mDevice.findObject(new UiSelector().resourceId("android:id/alertTitle"));
            copyText = codeField.getText();
        } catch (UiObjectNotFoundException e) {
            TimeUnit.SECONDS.sleep(1);
            mDevice.click(529,964);
            UiObject codeField = mDevice.findObject(new UiSelector().resourceId("android:id/alertTitle"));
            copyText = codeField.getText();
        }

        mDevice.pressHome();
        UiObject appButton = mDevice.findObject(new UiSelector().text("Golden Sand Bank"));
        appButton.click();
        TimeUnit.SECONDS.sleep(1);

        UiObject smsCodeView = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/passcode_EditText"));
        smsCodeView.setText(copyText);
        TimeUnit.SECONDS.sleep(1);

        UiObject next2 = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_next"));
        next2.click();

        TimeUnit.SECONDS.sleep(1);
        //Press 1
        mDevice.click(220,830);
        mDevice.click(220,830);
        //Press 2
        mDevice.click(360,830);
        mDevice.click(360,830);
        //Press 3
        mDevice.click(505,835);
        mDevice.click(505,835);
        //Press 4
        mDevice.click(214,937);
        mDevice.click(214,937);

        TimeUnit.SECONDS.sleep(1);

        //Press 1
        mDevice.click(220,830);
        mDevice.click(220,830);
        //Press 2
        mDevice.click(360,830);
        mDevice.click(360,830);
        //Press 3
        mDevice.click(505,835);
        mDevice.click(505,835);
        //Press 4
        mDevice.click(214,937);
        mDevice.click(214,937);

        mDevice.pressBack();

        UiObject login = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_next"));
        login.click();
        TimeUnit.SECONDS.sleep(3);

    }

    //@Test
    public void ownTransfer() throws UiObjectNotFoundException, InterruptedException {

        UiObject sideMenu = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tab_menu"));
        sideMenu.clickAndWaitForNewWindow();

        UiObject payment = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/nav_transfer"));
        payment.clickAndWaitForNewWindow();

        UiObject to = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/ell_to_account"));
        to.click();

        UiScrollable test = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
        test.scrollForward();

        UiObject name = mDevice.findObject(new UiSelector().className("android.view.ViewGroup").index(4));
        name.click();


        UiObject amount = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_enter_amount"));
        amount.setText("50");
        TimeUnit.SECONDS.sleep(1);

        UiObject confirm = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_confirm"));
        confirm.click();

        UiObject confirm1 = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_confirm"));
        confirm1.click();

        UiObject okBtn = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_dialog_ok"));
        okBtn.click();
        TimeUnit.SECONDS.sleep(1);
    }
    //@Test
    public void Payment() throws UiObjectNotFoundException, InterruptedException {

        UiObject sideMenu = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tab_menu"));
        sideMenu.clickAndWaitForNewWindow();

        UiObject payment = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/nav_payment"));
        payment.clickAndWaitForNewWindow();

        UiObject nameEdit = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/et_name"));
        nameEdit.setText(generateString());

        UiObject adress1 = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/et_address_first_line"));
        adress1.setText("Some adress");


        UiObject adress2 = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/et_address_second_line"));
        adress2.setText("Some second adress");

        UiScrollable test = new UiScrollable(new UiSelector().className("android.widget.ScrollView"));
        test.scrollForward();

        UiObject coutry = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_dropdown_item_text"));
        coutry.click();

        TimeUnit.SECONDS.sleep(1);

        mDevice.click(300,800);

        UiObject amount = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_enter_amount"));
        amount.setText("25");


        UiObject ibanNumber = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/et_account_number"));
        ibanNumber.setText("GI35NWBK000001263422930");

        UiObject title = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/tv_payment_title"));
        title.setText(generateString());

        UiObject confirm = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_confirm"));
        confirm.click();

        UiObject confirm2 = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_confirm"));
        confirm2.click();

        TimeUnit.SECONDS.sleep(1);

        //Press 1
        mDevice.click(100,800);
        mDevice.click(100,800);
        //Press 2
        mDevice.click(300,800);
        mDevice.click(300,800);
        //Press 3
        mDevice.click(600,800);
        mDevice.click(600,800);
        //Press 4
        mDevice.click(100,900);
        mDevice.click(100,900);

        //temporary
        UiObject okBtn = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_dialog_ok"));
        okBtn.click();
        TimeUnit.SECONDS.sleep(1);

    }
    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("[0-9]","").replaceAll("-","");
    }

}
