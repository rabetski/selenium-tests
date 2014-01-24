package com.wikia.webdriver.TestCases.Mobile;

import org.testng.annotations.Test;

import com.wikia.webdriver.Common.Templates.NewTestTemplate;
import com.wikia.webdriver.PageObjectsFactory.PageObject.Mobile.MobileArticlePageObject;

/**
 * @author PMG
 *
 *Below test cases are executed against mobileregressiontesting wikiName with CHROMEMOBILE browser
 *1. Verify that user is able to open all menus from topbar
 *2. Verify that when user click on wikia wordmark he goes to wikia main page
 *3. Verify that when user open search and click outside of search then he is back to page (courtain disappear)
 *4. Verify that back buttons on topbar menu work correctly
 */

public class MobileTopbarTests extends NewTestTemplate{

	@Test(groups={"MobileTopbar_001", "MobileTopbar", "Mobile"})
	public void MobileTopbarTests_001_topbarButtons() {
		MobileArticlePageObject topbar = new MobileArticlePageObject(driver);
		topbar.openTopbarPage(wikiURL);
		topbar.verifyAllTopbarButtons();
	}

	@Test(groups={"MobileTopbar_002", "MobileTopbar", "Mobile"})
	public void TopbarTests_002_wordmark() {
		MobileArticlePageObject topbar = new MobileArticlePageObject(driver);
		topbar.openTopbarPage(wikiURL);
		topbar.clickWordmark();
		topbar.verifyMainPageOpened(wikiURL);
	}

	@Test(groups={"MobileTopbar_003", "MobileTopbar", "Mobile"})
	public void TopbarTests_003_topbarPosition() {
		MobileArticlePageObject topbar = new MobileArticlePageObject(driver);
		topbar.openTopbarPage(wikiURL);
		Long startPosition = topbar.getPosition();
		topbar.triggerSearch();
		topbar.verifyCurtainOpened();
		topbar.clickOutsideSearchField();
		topbar.verifyCurtainClosed();
		topbar.verifyPositionDifferent(startPosition);
	}

	@Test(groups={"MobileTopbar_004", "MobileTopbar", "Mobile"}, enabled = false)
	public void TopbarTests_004_menuPagination_MOB_676() {
		MobileArticlePageObject topbar = new MobileArticlePageObject(driver);
		topbar.openTopbarPage(wikiURL);
		topbar.verifyMenuPagination();
	}

}
