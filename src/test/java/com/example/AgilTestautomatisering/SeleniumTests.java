package com.example.AgilTestautomatisering;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SeleniumTests {
    static ChromeDriver driver;
    String SearchField = "input[data-rt*='combobox-input']";
    String SearchButton = "button[title*='Sök på svtplay.se']";

    public SeleniumTests()
    {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }


    private static void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            System.out.print("Fel uppstod, se exception: " + e.getCause());
        }
    }

    void WaitForReady(By by)
    {
        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        webDriverWait.until(driver -> { return IsElementPresent(by);});
    }

    private boolean IsElementPresent(By by)
    {
        try{
            driver.findElement(by);
            return true;
        }
        catch(NoSuchElementException e)
        {
            return false;
        }
    }

    @BeforeAll
    void AcceptCookies(){
        driver.manage().window().maximize();
        driver.get("https://www.svtplay.se/");
        WaitForReady(By.xpath("//*[@id=\"__next\"]/div[2]/div/div[2]/button[3]"));
        driver.findElement(By.xpath("//*[@id=\"__next\"]/div[2]/div/div[2]/button[3]")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@data-rt=\"cookie-consent-modal\"]")));
    }

    @BeforeEach
    void GoToStartPage(){
        driver.get("https://www.svtplay.se/");
    }

    @AfterAll
    void CloseDriver(){
        driver.close();
    }


    @Test
    @DisplayName("Check web title")
    void WhenNavigateToWebPage_ShouldOpenWebPage() {
        String title = driver.getTitle();
        assertEquals("SVT Play", title, "Appapp! Fel titel.");
    }
    @Test
    @DisplayName("Logotype should be visible")
    void WhenNavigateToWebPage_ShouldLogotypeBeVisible() {
        WaitForReady(By.cssSelector("a[data-rt*='play-logo']"));
        boolean test = driver.findElement(By.cssSelector("a[data-rt*='play-logo']")).isDisplayed();
        assertTrue(test);
    }
    @Test
    @DisplayName("Main link names should be correct")
    void WhenNavigateToWebPage_ShouldMainLinkNamesBeCorrect() {
        String programs = driver.findElement(By.xpath("//li[@type='programs']")).getText();
        assertEquals("PROGRAM", programs);
        String start = driver.findElement(By.xpath("//li[@type='start']")).getText();
        assertEquals("START", start);
        String channels = driver.findElement(By.xpath("//li[@type='channels']")).getText();
        assertEquals("KANALER", channels);
    }
    @Test
    @DisplayName("Accessibility link should be correct")
    void WhenNavigateToWebPage_ShouldAccessibilityLinkBeCorrect() {
        var footer = driver.findElement(By.tagName("footer"));
        var link = footer.findElement(By.partialLinkText("Tillgänglighet"));
        assertTrue(link.isDisplayed());
        assertEquals(true, link.getText().contains("Tillgänglighet i SVT Play"));
    }
    @Test
    @DisplayName("Accessibility link should lead to correct web page")
    void WhenClickOnAccessibilityLink_ShouldNavigateToCorrectWebPage() {
        var footer = driver.findElement(By.tagName("footer"));
        footer.findElement(By.partialLinkText("Tillgänglighet")).click();
        var h1 = driver.findElement(By.tagName("h1")).getText();
        assertEquals("Så arbetar SVT med tillgänglighet", h1);
    }
    @Test
    @DisplayName("Programs link should show correct number of categories")
    void WhenClickOnProgramsLink_ShouldShowCorrectNumberOfCategories() {
        driver.findElement(By.xpath("//li[@type='programs']")).click();
        WaitForReady(By.className("sc-3b830fc0-0"));
        var section = driver.findElement(By.className("sc-3b830fc0-0"));
        var articles = section.findElements(By.className("dEXIAv")).toArray().length;
        assertEquals(17, articles);
    }
    @Test
    @DisplayName("'Mobil & platta' link should lead to correct web page")
    void WhenClickOnListLink_ShouldNavigateToCorrectWebPage() {
        var footer = driver.findElement(By.tagName("footer"));
        footer.findElements(By.className("sc-1eacba3e-3")).get(1).findElement(By.tagName("a")).click();
        var title = driver.getTitle();
        assertEquals("SVT Play: Mobil och surfplattor | SVT Kontakt", title);
    }
    @Test
    @DisplayName("Search for program should show search result page")
    void WhenSearchProgram_ShouldShowSearchResultsPage() {
        String searchtext = "bäst i test";
        WaitForReady(By.cssSelector(SearchField));
        driver.findElement(By.cssSelector(SearchField)).sendKeys(searchtext);
        driver.findElement(By.cssSelector(SearchButton)).click();
        WaitForReady(By.cssSelector("h2[data-rt*='header-search-result']"));
        var h2 = driver.findElement(By.cssSelector("h2[data-rt*='header-search-result']")).getText();
        assertEquals(true, h2.contains("Din sökning på "+searchtext+" gav"));
    }
    @Test
    @DisplayName("Check web title after reload")
    void WhenReloadWebPage_ShouldBeOnStartPage() {
        driver.navigate().refresh();
        String title = driver.getTitle();
        assertEquals("SVT Play", title, "Appapp! Fel titel.");
    }
    @Test
    @DisplayName("Left button should be enabled after right button click")
    void WhenClickRightButton_ShouldLeftButtonBeEnabled() {
        var btn = driver.findElement(By.className("sc-74f4c525-1"));
        assertFalse(btn.isEnabled());
        driver.findElement(By.xpath("//*[@id=\"play_main-content\"]/div/section[1]/div/div/div[1]/div[2]/button")).click();
        pause(1000);
        assertTrue(btn.isEnabled());
    }
    @Test
    @DisplayName("Search for program that does not exist should show search result page")
    void WhenSearchProgramThatNotExists_ShouldShowSearchResultsPage() {
        String searchtext = "felaktig söktext";
        WaitForReady(By.cssSelector(SearchField));
        driver.findElement(By.cssSelector(SearchField)).sendKeys(searchtext);
        driver.findElement(By.cssSelector(SearchButton)).click();
        WaitForReady(By.className("sc-da02705a-0"));
        var text = driver.findElement(By.className("sc-da02705a-0")).getText();
        assertEquals("Inget resultat för sökningen \"felaktig söktext\".", text);
    }
    @Test
    @DisplayName("Channels link should show correct URL")
    void WhenClickOnChannelsLink_ShouldBeAtCorrectURL() {
        WaitForReady(By.xpath("//li[@type='channels']"));
        driver.findElement(By.xpath("//li[@type='channels']")).click();
        WaitForReady(By.xpath("//*[text()='På SVT just nu']"));
        assertEquals("https://www.svtplay.se/kanaler", driver.getCurrentUrl());
    }

    //VG-krav nedan
    @Test
    @DisplayName("Search for program Agenda should show up first on search result page")
    void WhenSearchProgramAgenda_ShouldShowFirstOnSearchResultsPage() {
        String searchtext = "agenda";
        WaitForReady(By.cssSelector(SearchField));
        driver.findElement(By.cssSelector(SearchField)).sendKeys(searchtext);
        driver.findElement(By.cssSelector(SearchButton)).click();
        WaitForReady(By.className("sc-248598e9-1"));
        var list = driver.findElement(By.className("sc-248598e9-1"));
        var programText = list.findElements(By.className("sc-8ddc32bb-1")).get(0).findElement(By.tagName("h2")).getText();
        assertEquals("Agenda", programText);
    }
    @Test
    @DisplayName("Search for program Pistvakt should show up first on search result page")
    void WhenSearchProgramPistvakt_ShouldShowFirstOnSearchResultsPage() {
        String searchtext = "pistvakt";
        WaitForReady(By.cssSelector(SearchField));
        driver.findElement(By.cssSelector(SearchField)).sendKeys(searchtext);
        driver.findElement(By.cssSelector(SearchButton)).click();
        WaitForReady(By.className("sc-248598e9-1"));
        var list = driver.findElement(By.className("sc-248598e9-1"));
        list.findElements(By.className("sc-8ddc32bb-1")).get(0).click();

        WaitForReady(By.className("sc-9d83dc5e-1"));

        var section = driver.findElements(By.className("sc-9d83dc5e-1")).get(1);
        section.findElement(By.xpath("//a[normalize-space()='Säsong 2']")).click();

        var seasonList = section.findElements(By.className("sc-9d83dc5e-5")).size();
        assertEquals(6, seasonList);

        var programfive = section.findElements(By.className("sc-9d83dc5e-5")).get(4).findElement(By.tagName("h3")).getText();
        assertEquals("5. Personalfestan", programfive);
    }
    @Test
    @DisplayName("Datepicker should show tomorrows date")
    void WhenChooseTomorrowInDatepicker_ShouldShowTableauForTomorrow() {
        driver.findElement(By.xpath("//li[@type='channels']")).click();
        var datepicker = "a[data-rt*='popup-select-date']";
        WaitForReady(By.cssSelector(datepicker));
        driver.findElement(By.cssSelector(datepicker)).click();

        pause(500);
        var todaysdate = driver.findElement(By.className("dmZdtk"));

        todaysdate.findElement(By.xpath("./following-sibling::a")).click();
        pause(500);

        var text = driver.findElement(By.className("sc-531b3a26-1")).findElement(By.tagName("h2")).getText();
        assertTrue(text.contains("IMORGON"));
    }
    @Test
    @DisplayName("Syntolkat link should navigate to correct url")
    void WhenClickOnSyntolkat_ShouldNavigateToCorrectUrl() {

        driver.findElement(By.xpath("//li[@type='programs']")).click();
        WaitForReady(By.cssSelector("li[data-rt*='a11y-audio-description']"));
        driver.findElement(By.cssSelector("li[data-rt*='a11y-audio-description']")).click();
        assertEquals("https://www.svtplay.se/syntolkat", driver.getCurrentUrl());
        WaitForReady(By.cssSelector("h1[class*='sc-fa8f3a27-1']"));
        var header = driver.findElement(By.cssSelector("h1[class*='sc-fa8f3a27-1']")).getText();
        assertEquals("Syntolkat", header);
    }
    @Test
    @DisplayName("Nyhetsbrev link should navigate to correct url")
    void WhenClickOnNyhetsbrev_ShouldNavigateToCorrectUrl() {
        JavascriptExecutor js = driver;
        var footer = driver.findElement(By.partialLinkText("nyhetsbrev"));
        js.executeScript("arguments[0].scrollIntoView();", footer);
        footer.click();
        assertTrue(driver.getCurrentUrl().contains("https://nyhetsbrev.svtplay.se/"));
    }
    @Test
    @DisplayName("Invalid email should show error message on submit")
    void WhenFillOutInvalidEmail_ShouldShowErrorMessage() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        var footer = driver.findElement(By.partialLinkText("nyhetsbrev"));
        js.executeScript("arguments[0].scrollIntoView();", footer);
        footer.click();

        driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys("wrongmail@.com");
        driver.findElement(By.xpath("//*[@id=\"terms\"]")).click();

        driver.findElement(By.xpath("//*[@id=\"submit-button\"]")).click();

        WaitForReady(By.xpath("//*[@id=\"error-messages\"]/p"));
        var errortext = driver.findElement(By.xpath("//*[@id=\"error-messages\"]/p")).getText();

        assertEquals("Du måste ange en giltig e-postaddress!", errortext);
    }
    @Test
    @DisplayName("Invalid url should return 404 status code and show error page")
    void WhenFillOutInvalidUrl_ShouldReturn404AndShowErrorPage() {
        var urlstring = "https://www.svtplay.se/kategori/oppet-arkiv/felaktigsida";
        driver.get(urlstring);
        try
        {
            URL url = new URL(urlstring);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            assertEquals(404, httpURLConnection.getResponseCode());
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        var errortext = driver.findElement(By.xpath("//*[@id='play_main-content']/div/section[1]/h1")).getText();
        assertEquals("Det finns inget på den här sidan", errortext);
    }
}
