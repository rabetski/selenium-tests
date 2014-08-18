package com.wikia.webdriver.TestCases.InteractiveMapsTests;


import org.testng.annotations.Test;
import com.wikia.webdriver.Common.ContentPatterns.PageContent;
import com.wikia.webdriver.Common.ContentPatterns.InteractiveMapsContent;
import com.wikia.webdriver.Common.Properties.Credentials;
import com.wikia.webdriver.Common.Templates.NewTestTemplate;
import com.wikia.webdriver.PageObjectsFactory.ComponentObject.InteractiveMaps.AddPinComponentObject;
import com.wikia.webdriver.PageObjectsFactory.ComponentObject.InteractiveMaps.CreateACustomMapComponentObject;
import com.wikia.webdriver.PageObjectsFactory.ComponentObject.InteractiveMaps.CreateAMapComponentObject;
import com.wikia.webdriver.PageObjectsFactory.ComponentObject.InteractiveMaps.CreatePinTypesComponentObject;
import com.wikia.webdriver.PageObjectsFactory.ComponentObject.InteractiveMaps.CreateRealMapComponentObject;
import com.wikia.webdriver.PageObjectsFactory.ComponentObject.InteractiveMaps.TemplateComponentObject;
import com.wikia.webdriver.PageObjectsFactory.ComponentObject.InteractiveMaps.EmbedMapComponentObject;
import com.wikia.webdriver.PageObjectsFactory.PageObject.Special.InteractiveMaps.InteractiveMapPageObject;
import com.wikia.webdriver.PageObjectsFactory.PageObject.WikiBasePageObject;
import com.wikia.webdriver.PageObjectsFactory.PageObject.Special.InteractiveMaps.InteractiveMapsPageObject;
import com.wikia.webdriver.PageObjectsFactory.PageObject.WikiPage.EditMode.WikiArticleEditMode;
import com.wikia.webdriver.PageObjectsFactory.PageObject.Article.ArticlePageObject;



/**
 * Author: Rodrigo Molinero Gomez
 * @author: Lukasz Jedrzejczak
 * @author: Lukasz Nowak
 * Date: 20.06.14
 * Time: 16:53
    - Special:Maps page
	IM01: Creating a custom new map based on new image upload v 
	IM02: Create a custom new map based on existing template v
	IM03: Create a real map flow v
	IM04: Click on a map and verify correct redirect and URL v
	IM05: Click create a map button as anon and make sure log in modal is displayed v
	
	- PIN Creation - Add a Pin modal
	IM06: Verify it is possible to add a pin to the map and that add pin dialog has all required elements v
	IM07: Verify removing a suggestion of article with image will remove image from placeholder v
	
	-Edit PIN Types Modal       
	IM08: Check image error validation for small size and non-image extension v
	IM09: Verify clicking "Add another pin type" link will display a new line and fields for adding new information   v
	IM10: Verify saving any new data will actually update the data for that pin edited 
    
    - Other Test Cases
    IM11: Verify possibility of embedding wikia map in other wikia pages. v
    IM12: Verify following elements in map modal when a map is embedded in a wikia page: PIN description when
          clicking, zoom, add/edit features for pin types and pins, embed map button, filters box. Verify there is no branding footer v 
    IM13: Test template search works correctly for unexisting and existing templates        v
	IM14: Verify following behaviours in "Filters" left hand side column:
		All categories are checked and user clicks on single category: We uncheck clicked category and "All categories". v
		All categories but one are checked and user clicks on unchecked one: We check clicked category and "All categories". v
		All categories are checked (so "All categories" is checked too) and user clicks "All categories": We uncheck all categories and "All categories". v
		Some categories are checked (so "All categories" is unchecked) and user clicks "All categories": We check all categories and "All categories". v		
	IM15: Verify embed map code dialog works correctly from Special:Map page v
	IM16: Embed a map outside of wikia and verify there is a branding footer, zoom in/out options and filters box collapsibility
	
	Added to this list by Łukasz Nowak
	IM17: Verify zoom in and zoom out of map v
	IM18: => POIETC11: Verify pin data is correctly updated after saving (T1 priority) v
	IM19: SMPTC02 =>Check all required elements from page are displayed: create new map link, list of maps (max 10), pagination
	*/

public class InteractiveMapsTests extends NewTestTemplate{

	Credentials credentials = config.getCredentials();
	
	@Test(groups = {"InteractiveMaps_001", "InteractiveMapTests", "InteractiveMaps"},
	enabled = false)
	public void InteractiveMaps_001_CreateCustomMapNewImageUpload() {
		WikiBasePageObject base = new WikiBasePageObject(driver);
		base.logInCookie(credentials.userName, credentials.password, wikiURL);
		InteractiveMapsPageObject specialMap = base.openSpecialInteractiveMaps(wikiURL);
		CreateAMapComponentObject map = specialMap.clickCreateAMap();
		CreateACustomMapComponentObject customMap = map.clickCustomMap();
		TemplateComponentObject template = customMap.selectFileToUpload(PageContent.file);
		template.verifyTemplateImagePreview();
		template.typeMapName(InteractiveMapsContent.mapName);
		InteractiveMapsContent.templateName = base.getTimeStamp();
		template.typeTemplateName(InteractiveMapsContent.templateName);
		CreatePinTypesComponentObject pinDialog = template.clickNext();
		pinDialog.typePinTypeTitle(InteractiveMapsContent.pinTypeName,InteractiveMapsContent.pinTypeIndex);
		InteractiveMapPageObject createdMap = pinDialog.clickSave();
		createdMap.verifyCreatedMapTitle(InteractiveMapsContent.mapName);
		createdMap.verifyMapOpened();
		createdMap.verifyCreatedPinTypesForNewMap();
		createdMap.verifyControButtonsAreVisible();
	}

	@Test(groups = {"InteractiveMaps_002", "InteractiveMapTests", "InteractiveMaps"})
	public void InteractiveMaps_002_CreateCustomMapWithExistingTemplate() {
		WikiBasePageObject base = new WikiBasePageObject(driver);
		base.logInCookie(credentials.userName, credentials.password, wikiURL);
		InteractiveMapsPageObject specialMap = base.openSpecialInteractiveMaps(wikiURL);
		CreateAMapComponentObject map = specialMap.clickCreateAMap();
		CreateACustomMapComponentObject customMap = map.clickCustomMap();
		String selectedImageName = customMap.getSelectedTemplateImageName(InteractiveMapsContent.selectedTemplateIndex);
		TemplateComponentObject template = customMap.selectTemplate(InteractiveMapsContent.selectedTemplateIndex);
		template.verifyTemplateImage(selectedImageName);
		template.typeMapName(InteractiveMapsContent.mapName);
		CreatePinTypesComponentObject pinDialog = template.clickNext();
		pinDialog.typePinTypeTitle(InteractiveMapsContent.pinTypeName,InteractiveMapsContent.pinTypeIndex);
		InteractiveMapPageObject createdMap = pinDialog.clickSave();
		createdMap.verifyCreatedMapTitle(InteractiveMapsContent.mapName);
		createdMap.verifyMapOpened();
		createdMap.verifyCreatedPinTypesForNewMap();
		createdMap.verifyControButtonsAreVisible();
	}
	
	@Test(groups = {"InteractiveMaps_003", "InteractiveMapTests", "InteractiveMaps"})
	public void InteractiveMaps_003_CreateRealMap() {
		WikiBasePageObject base = new WikiBasePageObject(driver);
		base.logInCookie(credentials.userName, credentials.password, wikiURL);
		InteractiveMapsPageObject specialMap = base.openSpecialInteractiveMaps(wikiURL);
		CreateAMapComponentObject map = specialMap.clickCreateAMap();
		CreateRealMapComponentObject realMap = map.clickRealMap();	
		realMap.verifyRealMapPreviewImage();
		realMap.typeMapName(InteractiveMapsContent.mapName);
		CreatePinTypesComponentObject pinDialog = realMap.clickNext();
		pinDialog.typePinTypeTitle(InteractiveMapsContent.pinTypeName,InteractiveMapsContent.pinTypeIndex);
		InteractiveMapPageObject createdMap = pinDialog.clickSave();
		createdMap.verifyMapOpened();
		createdMap.verifyControButtonsAreVisible();
	}

	@Test(groups = {"InteractiveMaps_004", "InteractiveMapTests", "InteractiveMaps"})
	public void InteractiveMaps_004_ClickMapAndVerifyCorrectRedirect() {
		WikiBasePageObject base = new WikiBasePageObject(driver);
		base.logInCookie(credentials.userName, credentials.password, wikiURL);
		InteractiveMapsPageObject specialMap = base.openSpecialInteractiveMaps(wikiURL);
		String mapUrl = specialMap.getMapLink(InteractiveMapsContent.selectedMapIndex);
		String mapTitle = specialMap.getMapTitle(InteractiveMapsContent.selectedMapIndex);
		InteractiveMapPageObject selectedMap = specialMap.clickMapWithIndex(InteractiveMapsContent.selectedMapIndex);
		selectedMap.verifyMapOpened();
		selectedMap.verifyURL(mapUrl);
		selectedMap.verifyCreatedMapTitle(mapTitle);
	}

	@Test(groups = {"InteractiveMaps_005", "InteractiveMapTests", "InteractiveMaps"})
	public void InteractiveMaps_005_VerifyLoginModalWhenAnon() {
		WikiBasePageObject base = new WikiBasePageObject(driver);
		base.logOut(wikiURL);
		InteractiveMapsPageObject specialMap = base.openSpecialInteractiveMaps(wikiURL);
		CreateAMapComponentObject map = specialMap.clickCreateAMap();
		map.verifyLoginModal();
	}
	
	@Test(groups = {"InteractiveMaps_006", "InteractiveMapTests", "InteractiveMaps"})
	public void InteractiveMaps_006_VerifyPinModalContent() {
		WikiBasePageObject base = new WikiBasePageObject(driver);
		base.logInCookie(credentials.userName, credentials.password, wikiURL);
		InteractiveMapsPageObject specialMap = base.openSpecialInteractiveMaps(wikiURL);
		InteractiveMapPageObject selectedMap = specialMap.clickMapWithIndex(InteractiveMapsContent.selectedMapIndex);
		AddPinComponentObject pinDialog = selectedMap.placePinInMap();
		pinDialog.verifyPinTitleFieldIsDisplayed();
		pinDialog.verifyAssociatedArticleFieldIsDisplayed();
		pinDialog.verifyAssociatedArticleImagePlaceholderIsDisplayed();
		pinDialog.verifyPinCategorySelectorIsDisplayed();
		pinDialog.verifyDescriptionFieldIsDisplayed();
		selectedMap = pinDialog.clickCancelButton();
	}
	
	@Test(groups = {"InteractiveMaps_007", "InteractiveMapTests", "InteractiveMaps"})
	public void InteractiveMaps_007_VerifySuggestionsAndAssociatedImage() {
		WikiBasePageObject base = new WikiBasePageObject(driver);
		base.logInCookie(credentials.userName, credentials.password, wikiURL);
		InteractiveMapsPageObject specialMap = base.openSpecialInteractiveMaps(wikiURL);
		InteractiveMapPageObject selectedMap = specialMap.clickMapWithIndex(InteractiveMapsContent.selectedMapIndex);
		selectedMap.verifyMapOpened();
		AddPinComponentObject pinDialog = selectedMap.placePinInMap();
		pinDialog.verifyPinTitleFieldIsDisplayed();
		pinDialog.typePinName(InteractiveMapsContent.pinName);
		String placeholderSrc = pinDialog.getAssociatedArticleImageSrc();
		pinDialog.typeAssociatedArticle(InteractiveMapsContent.associatedArticleName);
		pinDialog.clickSuggestion(0);
		pinDialog.verifyAssociatedImageIsVisible(placeholderSrc);
	}
	
	@Test(groups = {"InteractiveMaps_008", "InteractiveMapTests", "InteractiveMaps"})
	public void InteractiveMaps_008_VerifyImageValidationInPinTypeModal() {
		WikiBasePageObject base = new WikiBasePageObject(driver);
		base.logInCookie(credentials.userName, credentials.password, wikiURL);
		InteractiveMapsPageObject specialMap = base.openSpecialInteractiveMaps(wikiURL);
		InteractiveMapPageObject selectedMap = specialMap.clickMapWithIndex(InteractiveMapsContent.selectedMapIndex);
		selectedMap.verifyMapOpened();
		selectedMap.clickEditPinTypesButton();
		CreatePinTypesComponentObject pinTypeModal = new CreatePinTypesComponentObject(driver);
		pinTypeModal.verifyPinTypesDialog();
		pinTypeModal.selectFileToUpload(PageContent.smallFile, "Small image");
		pinTypeModal.verifyErrorsExist();
		pinTypeModal.selectFileToUpload(PageContent.brokenExtensionFile, "Image with wrong extension");
		pinTypeModal.verifyErrorsExist();
	}
		
	@Test(groups = {"InteractiveMaps_009", "InteractiveMapTests", "InteractiveMaps"})
	public void InteractiveMaps_009_VerifyClickingAddAnotherPinType() {
		WikiBasePageObject base = new WikiBasePageObject(driver);
		base.logInCookie(credentials.userName, credentials.password, wikiURL);
		InteractiveMapsPageObject specialMap = base.openSpecialInteractiveMaps(wikiURL);
		InteractiveMapPageObject selectedMap = specialMap.clickMapWithIndex(InteractiveMapsContent.selectedMapIndex);
		selectedMap.verifyMapOpened();
		selectedMap.clickEditPinTypesButton();
		CreatePinTypesComponentObject pinTypesDialog = new CreatePinTypesComponentObject(driver); 
		pinTypesDialog.verifyPinTypesDialog();
		pinTypesDialog.savePinTypesListState();
		pinTypesDialog.clickAddAnotherPinType();
		pinTypesDialog.verifyAddAnotherPinType();
	}
	
	@Test(groups = {"InteractiveMaps_011", "InteractiveMapTests", "InteractiveMaps"})
	public void InteractiveMaps_011_VerifyEmbedMapInWikiaPage() {
		WikiBasePageObject base = new WikiBasePageObject(driver);
		base.logInCookie(credentials.userName, credentials.password, wikiURL);
		InteractiveMapsPageObject specialMap = base.openSpecialInteractiveMaps(wikiURL);
		InteractiveMapPageObject selectedMap = specialMap.clickMapWithIndex(InteractiveMapsContent.selectedMapIndex);
		selectedMap.verifyMapOpened();
		String wikiEmbedCode = selectedMap.getEmbedMapWikiCode();
		String mapID = selectedMap.getEmbedMapID();
		WikiArticleEditMode EditMode = new WikiArticleEditMode(driver);
		EditMode.editArticleByName(InteractiveMapsContent.embedMapArticleName);
		EditMode.clickSourceButton();
		EditMode.clearSource();
		EditMode.verifySourceEditorContentIsEmpty();
		EditMode.typeContentInSourceMode(wikiEmbedCode);
		EditMode.clickOnPublish();
		EditMode.verifyEmbededMap(mapID);		
	}

	@Test(groups = {"InteractiveMaps_012A", "InteractiveMaps_012", "InteractiveMapTests", "InteractiveMaps"})
	public void InteractiveMaps_012A_VerifyEmbedMapElements(){
		ArticlePageObject article = new ArticlePageObject(driver);
		article.openArticleByName(wikiURL, InteractiveMapsContent.embedMapArticleName);
		EmbedMapComponentObject embedMapDialog = article.clickViewEmbedMap();
		embedMapDialog.verifyEmbedMapModalOpened();
		embedMapDialog.verifyMapTitlePresented();
		embedMapDialog.verifyCloseButtonPresented();
		embedMapDialog.verifyMapElementsPresented();
	}
	
	@Test(groups = {"InteractiveMaps_012B", "InteractiveMaps_012", "InteractiveMapTests", "InteractiveMaps"})
	public void InteractiveMaps_012B_VerifyPins(){
		WikiBasePageObject base = new WikiBasePageObject(driver);
		base.logInCookie(credentials.userName, credentials.password, wikiURL);
		ArticlePageObject article = new ArticlePageObject(driver);
		article.openArticleByName(wikiURL, InteractiveMapsContent.embedMapArticleName);
		EmbedMapComponentObject embedMapDialog = article.clickViewEmbedMap();
		embedMapDialog.verifyEmbedMapModalOpened();
		AddPinComponentObject pinModal = embedMapDialog.placePinInMap();
		String pinTitle = base.getTimeStamp();
		String pinDescription = base.getTimeStamp();
		pinModal.typePinName(pinTitle);
		pinModal.typeAssociatedArticle(InteractiveMapsContent.associatedArticleName);
		pinModal.clickSuggestion(0);
		pinModal.selectPinType();
		pinModal.typePinDescription(pinDescription);
		pinModal.clickSaveButton();
		embedMapDialog.verifyPinPopupImageIsVisible();
		embedMapDialog.verifyPinPopUp();
	}
	
	@Test(groups = {"InteractiveMaps_013", "InteractiveMapTests", "InteractiveMaps"})
	public void InteractiveMaps_013_VerifyTemplateSearch() {
		WikiBasePageObject base = new WikiBasePageObject(driver);
		base.logInCookie(credentials.userName, credentials.password, wikiURL);
		InteractiveMapsPageObject specialMap = base.openSpecialInteractiveMaps(wikiURL);
		CreateAMapComponentObject createMapDialog = specialMap.clickCreateAMap();
		CreateACustomMapComponentObject customMapDialog = createMapDialog.clickCustomMap();
		customMapDialog.typeSearchTile(InteractiveMapsContent.templateNameToSearchShouldBeFound);
		customMapDialog.verifyTemplateWasFound(InteractiveMapsContent.templateNameToSearchShouldBeFound);
		customMapDialog.verifyThereIsNoError();
		customMapDialog.clearSearchTitle();
		customMapDialog.typeSearchTile(InteractiveMapsContent.templateNameToSearchShouldNotBeFound);
		customMapDialog.verifyThereIsError();
	}
	
	@Test(groups = {"InteractiveMaps_014A", "InteractiveMaps_014", "InteractiveMapTests", "InteractiveMaps"})
	public void InteractiveMaps_014A_VerifyUncheckedSingleCategory() {
		WikiBasePageObject base = new WikiBasePageObject(driver);
		base.logInCookie(credentials.userName,credentials.password,wikiURL);
		InteractiveMapsPageObject specialMap = base.openSpecialInteractiveMaps(wikiURL);
		InteractiveMapPageObject selectedMap = specialMap.clickMapWithIndex(InteractiveMapsContent.selectedMapIndex);
		selectedMap.verifyMapOpened();
		selectedMap.clickOnSingleEnabledCategory();
		selectedMap.verifyAllPinTypesIsUncheck();
	}
	
	@Test(groups = {"InteractiveMaps_014B", "InteractiveMaps_014", "InteractiveMapTests", "InteractiveMaps"})
	public void InteractiveMaps_014B_VerifyClickOnUncheckedCategory() {
		WikiBasePageObject base = new WikiBasePageObject(driver);
		base.logInCookie(credentials.userName,credentials.password,wikiURL);
		InteractiveMapsPageObject specialMap = base.openSpecialInteractiveMaps(wikiURL);
		InteractiveMapPageObject selectedMap = specialMap.clickMapWithIndex(InteractiveMapsContent.selectedMapIndex);
		selectedMap.verifyMapOpened();
		selectedMap.clickOnSingleEnabledCategory();
		selectedMap.verifyAllPinTypesIsUncheck();
		selectedMap.clickOnSingleDisabledCategory();
		selectedMap.verifyAllPinTypesIsCheck();
	}
	
	@Test(groups = {"InteractiveMaps_014C", "InteractiveMaps_014", "InteractiveMapTests", "InteractiveMaps"})
	public void InteractiveMaps_014C_VerifyClickAllCategoriesUncheckCategories() {
		WikiBasePageObject base = new WikiBasePageObject(driver);
		base.logInCookie(credentials.userName,credentials.password,wikiURL);
		InteractiveMapsPageObject specialMap = base.openSpecialInteractiveMaps(wikiURL);
		InteractiveMapPageObject selectedMap = specialMap.clickMapWithIndex(InteractiveMapsContent.selectedMapIndex);
		selectedMap.verifyMapOpened();
		selectedMap.clickOnAllPinTypes();
		selectedMap.verifyAllPinTypesIsUncheck();
		selectedMap.verifyPinTypesAreUncheck();
	}
	
	@Test(groups = {"InteractiveMaps_014D", "InteractiveMaps_014", "InteractiveMapTests", "InteractiveMaps"})
	public void InteractiveMaps_014D_VerifyClickAllCategoriesCheckAllPinTypes() {
		WikiBasePageObject base = new WikiBasePageObject(driver);
		base.logInCookie(credentials.userName,credentials.password,wikiURL);
		InteractiveMapsPageObject specialMap = base.openSpecialInteractiveMaps(wikiURL);
		InteractiveMapPageObject selectedMap = specialMap.clickMapWithIndex(InteractiveMapsContent.selectedMapIndex);
		selectedMap.verifyMapOpened();
		selectedMap.clickOnSingleEnabledCategory();
		selectedMap.verifyAllPinTypesIsUncheck();
		selectedMap.clickOnAllPinTypes();
		selectedMap.verifyAllPinTypesIsCheck();
		selectedMap.verifyPinTypesAreCheck();
		selectedMap.verifyPinTypesAreUncheck();
	}
	
	@Test(groups = {"InteractiveMaps_015", "InteractiveMapTests", "InteractiveMaps"})
	public void InteractiveMaps_015_VerifyEmbedMapCodeButton() {
		WikiBasePageObject base = new WikiBasePageObject(driver);
		base.logInCookie(credentials.userName, credentials.password, wikiURL);
		InteractiveMapsPageObject specialMap = base.openSpecialInteractiveMaps(wikiURL);
		InteractiveMapPageObject selectedMap = specialMap.clickMapWithIndex(InteractiveMapsContent.selectedMapIndex);
		selectedMap.verifyMapOpened();
		selectedMap.clickEmbedMapCodeButton();
		selectedMap.verifyEmbedMapDialog();
		selectedMap.clickEmbedMapCodeButton(InteractiveMapPageObject.embedMapDialogButtons.small);
		selectedMap.verifyEmbedMapCode(InteractiveMapPageObject.embedMapDialogButtons.small);
		selectedMap.clickEmbedMapCodeButton(InteractiveMapPageObject.embedMapDialogButtons.medium);
		selectedMap.verifyEmbedMapCode(InteractiveMapPageObject.embedMapDialogButtons.medium);
		selectedMap.clickEmbedMapCodeButton(InteractiveMapPageObject.embedMapDialogButtons.large);
		selectedMap.verifyEmbedMapCode(InteractiveMapPageObject.embedMapDialogButtons.large);
	}
	
	@Test(groups = {"InteractiveMaps_016", "InteractiveMapTests", "InteractiveMaps"})
	public void InteractiveMaps_016_VerifyEmbedMapOutsideWikia() {
		WikiBasePageObject base = new WikiBasePageObject(driver);
		base.logInCookie(credentials.userName, credentials.password, wikiURL);
		InteractiveMapsPageObject specialMap = base.openSpecialInteractiveMaps(wikiURL);
		InteractiveMapPageObject selectedMap = specialMap.clickMapWithIndex(InteractiveMapsContent.selectedMapIndex);
		selectedMap.verifyMapOpened();
		selectedMap.clickEmbedMapCodeButton();
		selectedMap.verifyEmbedMapDialog();
		selectedMap.clickEmbedMapCodeButton(InteractiveMapPageObject.embedMapDialogButtons.small);
		String mapCode = selectedMap.getEmbedMapCode();
		WikiArticleEditMode EditMode = new WikiArticleEditMode(driver);
		EditMode.editArticleByName(InteractiveMapsContent.embedMapOutOfWikia);
		EditMode.clickSourceButton();
		EditMode.clearSource();
		EditMode.verifySourceEditorContentIsEmpty();
		EditMode.typeContentInSourceMode(mapCode);
		EditMode.clickOnPublish();
	}
	
	@Test(groups = {"InteractiveMaps_017", "InteractiveMapTests", "InteractiveMaps"})
	public void InteractiveMaps_017_VerifyMapZoomOptions() {
		WikiBasePageObject base = new WikiBasePageObject(driver);
		base.logInCookie(credentials.userName, credentials.password, wikiURL);
		InteractiveMapsPageObject specialMap = base.openSpecialInteractiveMaps(wikiURL);
		InteractiveMapPageObject selectedMap = specialMap.clickMapWithIndex(InteractiveMapsContent.selectedMapIndex);
		selectedMap.verifyMapOpened();
		selectedMap.clickZoomInButton();
		selectedMap.clickZoomOutButton();
	}
	
	@Test(groups = {"InteractiveMaps_018", "InteractiveMapTests", "InteractiveMaps"})
	public void InteractiveMaps_018_VerifyChangePinData() {
		WikiBasePageObject base = new WikiBasePageObject(driver);
		base.logInCookie(credentials.userName, credentials.password, wikiURL);
		InteractiveMapsPageObject specialMap = base.openSpecialInteractiveMaps(wikiURL);
		InteractiveMapPageObject selectedMap = specialMap.clickMapWithIndex(InteractiveMapsContent.selectedMapIndex);
		selectedMap.verifyMapOpened();
		AddPinComponentObject pinModal = selectedMap.placePinInMap();
		String pinTitle = base.getTimeStamp();
		String pinDescription = base.getTimeStamp();
		pinModal.typePinName(pinTitle);
		pinModal.selectPinType();
		pinModal.typePinDescription(pinDescription);
		selectedMap = pinModal.clickSaveButton();
		pinModal = selectedMap.editLastAddedPin();
		pinModal.clearPinName();
		pinModal.typePinName(base.getTimeStamp());
		pinModal.clearPinDescription();
		pinModal.typePinDescription(base.getTimeStamp());
		selectedMap = pinModal.clickSaveButton();
		selectedMap.verifyPinDataWasChanged(pinTitle, pinDescription);
	}
	
	@Test(groups = {"InteractiveMaps_019", "InteractiveMapTests", "InteractiveMaps"})
	public void InteractiveMaps_019_VerifyMapListElements() {
		WikiBasePageObject base = new WikiBasePageObject(driver);
		base.logInCookie(credentials.userName, credentials.password, wikiURL);
		InteractiveMapsPageObject specialMap = base.openSpecialInteractiveMaps(wikiURL);
		specialMap.verifyAmountMapOnTheList();
		specialMap.verifyCreateMapButtonExist();
		specialMap.verifyCorrectPagination();
	}
}