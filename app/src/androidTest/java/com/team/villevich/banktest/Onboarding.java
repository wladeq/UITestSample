package com.team.villevich.banktest;

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
public class Onboarding {
    private UiDevice mDevice;
    private String emailGen = generateString()+"@test.net";

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

    }

    @Test
    public void register() throws UiObjectNotFoundException, InterruptedException {
        UiObject sideMenu = mDevice.findObject(new UiSelector().resourceId("com.ailleron.longbank.gtest:id/btn_register"));
        sideMenu.clickAndWaitForNewWindow();

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
        countryTitle.clickAndWaitForNewWindow();
        UiObject genderTitle = mDevice.findObject(new UiSelector().text("Mr"));
        genderTitle.click();
        UiObject firstName = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_FIRST_NAME"));
        firstName.setText(generateString());
        UiObject middleName = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_SECOND_NAME"));
        middleName.setText(generateString());
        UiObject lastName = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_LAST_NAME"));
        lastName.setText(generateString());
        UiScrollable scroll1 = new UiScrollable(new UiSelector().className("android.webkit.WebView"));
        scroll1.scrollForward();
        UiObject email = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_EMAIL"));
        email.setText(emailGen);
        UiObject emailConfirm = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_EMAIL_REPEATED"));
        emailConfirm.setText(emailGen);
        TimeUnit.SECONDS.sleep(1);
        UiObject callingCode = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_MOBILE_PHONE_CODE"));
        callingCode.click();
        UiObject callingCodeSelect = mDevice.findObject(new UiSelector().text("Poland (+48)"));
        callingCodeSelect.click();
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
        messages.clickAndWaitForNewWindow();
        UiObject messages1 = mDevice.findObject(new UiSelector().textContains("Golden Sand").className("android.widget.TextView"));
        messages1.click();
        TimeUnit.SECONDS.sleep(2);
        mDevice.click(529,964);
        UiObject codeField = mDevice.findObject(new UiSelector().resourceId("android:id/alertTitle"));
        String copyText = codeField.getText();

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
        UiObject addresCountry = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_RES_COUNTRY"));
        addresCountry.click();
        UiObject addresCountryChoice = mDevice.findObject(new UiSelector().text("United Kingdom").index(4));
        addresCountryChoice.click();
        TimeUnit.SECONDS.sleep(1);
        UiObject addresCity = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_RES_CITY"));
        addresCity.setText(generateString());
        UiObject addresCountry2 = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_RES_DISTRICT"));
        addresCountry2.setText(generateString());
        UiObject addresStreet = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_RES_STREET"));
        addresStreet.setText(generateString());
        UiObject addresStreetNumber = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_RES_STREET_NO"));
        addresStreetNumber.setText("13");
        UiScrollable scroll7 = new UiScrollable(new UiSelector().className("android.webkit.WebView"));
        scroll7.scrollForward();
        TimeUnit.SECONDS.sleep(1);
        UiObject postalCode = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_Content_E_RES_POSTAL_CODE"));
        postalCode.setText("77777");
        UiScrollable scroll8 = new UiScrollable(new UiSelector().className("android.webkit.WebView"));
        scroll8.scrollForward();
        TimeUnit.SECONDS.sleep(1);
        UiObject next4 = mDevice.findObject(new UiSelector().text("Next").index(2));
        next4.click();

        //FOURTH PAGE------------------------------------------------------------------------------------------------------------
        UiObject sourceOfWealth = mDevice.findObject(new UiSelector().resourceId("ctl00_CPH_UI__V_E_SOW_IS_SAVINGS__Q__Text"));
        sourceOfWealth.click();
    }
    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("[0-9]","").replaceAll("-","");
    }

}
