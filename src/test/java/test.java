import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.*;

public class test {
    @Test
    public void test() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://www.cargurus.com/"); // navigate to the website
        String actualTitle = driver.getTitle(); // returns the Title of the page
        String expectedTitle = "Buy & Sell Cars: Reviews, Prices, and Financing - CarGurus";
        Assert.assertEquals(actualTitle, expectedTitle, "Titles are not matching, this may be not the desired page.");

        driver.findElement(By.xpath("//*[@id=\"heroSearch\"]/label[2]")).click(); // clicking on "Buy Used" button

        String allMakes = String.valueOf(driver.findElement(By.xpath("//*[@id=\"carPickerUsed_makerSelect\"]/option[1]")).getText()); // checking if the default value of "By Make/Model" is "All Makes"
        Assert.assertEquals(allMakes, "All Makes", "Expected: \"All Makes\"" + ", Actual: " + allMakes + ".");

        new Select(driver.findElement(By.id("carPickerUsed_makerSelect"))).selectByVisibleText("Lamborghini"); // selecting Lamborghini

        String allModels = String.valueOf(driver.findElement(By.xpath("//*[@id=\"carPickerUsed_modelSelect\"]/option[1]")).getText()); // checking if the default value of "By Make/Model" is "All Makes"
        Assert.assertEquals(allModels, "All Models", "Expected: \"All Models\"" + ", Actual: " + allModels + ".");

        List<String> expectedListOfModels = Arrays.asList("All Models", "Aventador", "Gallardo", "Huracan", "Urus",
                "400GT", "Centenario", "Countach", "Diablo", "Espada", "Murcielago");

        WebElement dropDownModel = driver.findElement(By.id("carPickerUsed_modelSelect")); // getting the list of options in the dropdown with getOptions()
        Select s = new Select(dropDownModel);

        List<WebElement> op = s.getOptions();
        List<String> all = new ArrayList<>();
        for (WebElement e : op) {
            all.add(e.getText());
        }
        //System.out.println("--ACTUAL-- " + all + "\n--EXPECTED-- " + expectedListOfModels);
        Assert.assertEquals(all, expectedListOfModels, "The lists are not equal.");

        new Select(driver.findElement(By.id("carPickerUsed_modelSelect"))).selectByVisibleText("Gallardo"); // selecting Gallardo
        driver.findElement(By.id("dealFinderZipUsedId_dealFinderForm")).sendKeys("22031");
        driver.findElement(By.id("dealFinderForm_0")).click();

        List<WebElement> resultsAmount = driver.findElements(By.xpath("//a[@data-cg-ft='car-blade-link'][not(contains(@href, 'FEATURED'))]"));
        int resultsN = resultsAmount.size();
        int expN = 15;
        System.out.println("Expected amount of results: " + expN + ". \nActual amount of results: " + resultsN + ".");
        Assert.assertEquals(expN, resultsN, "Error");

        List<String> eachResult = new ArrayList<>(); // list of results sorted by default
        for (WebElement element : resultsAmount) {
            eachResult.add(element.getText());}
        String textLGexpected = "Lamborghini Gallardo";
        String textLG = "" + eachResult;
        Assert.assertTrue(textLG.contains(textLGexpected)); // checking if the name matches on all the search results

        new Select(driver.findElement(By.id("sort-listing"))).selectByVisibleText("Lowest price first"); // sort by lowest price first
        Thread.sleep(500);

        List <WebElement> resultsToSortPrices = driver.findElements(By.xpath("//span[@class='JzvPHo'][not(contains(@href, 'FEATURED'))]"));

        List <Double> prices = new ArrayList<>(); // making a sorting list
        for (int i = 1; i < resultsToSortPrices.size(); i++){
            String price = resultsToSortPrices.get(i).getText();
            price = price.split(" ")[0];
            prices.add(Double.parseDouble(price.replace("$","").replace(",", "")));
        }

        for (int i = 1; i < prices.size(); i++){
            Assert.assertTrue(prices.get(i-1) <= prices.get(i), "Ascending order is incorrect.");
        }

        new Select(driver.findElement(By.id("sort-listing"))).selectByVisibleText("Highest mileage first"); // sorting by highest mileage
        Thread.sleep(500);
        List <WebElement> resultsToSortMileage = driver.findElements(By.xpath("//p[@class='JKzfU4 umcYBP'][not(contains(@href, 'FEATURED'))]"));
        List <Double> mileageList = new ArrayList<>(); // making a sorting list
        for (int i = 1; i < resultsToSortMileage.size(); i++){
            String mileage = resultsToSortMileage.get(i).getText();
            mileageList.add(Double.parseDouble(mileage.replace(" mi","").replace(",", "")));
        }

        for (int i = 1; i < mileageList.size(); i++){
            Assert.assertTrue(mileageList.get(i-1) >= mileageList.get(i), "Descending order is incorrect."); // checking if descending order is correct
        }

        Thread.sleep(500);
        WebElement coupeAWD = driver.findElement(By.xpath("//input[@value='Coupe AWD']")); // finding coupeAWD checkBox
        if (!(coupeAWD.isSelected())){
            driver.findElement(By.xpath("//input[@value='Coupe AWD']/following-sibling::p")).click(); // if it is not marked, mark it
        }

        Thread.sleep(500);
        List <WebElement> awd = driver.findElements(By.xpath("//h4[@class='vO42pn'][not(contains(@href, 'FEATURED'))]")); // getting a list of results
        for (WebElement webElement : awd) {
            String awdString = webElement.getText();
            //System.out.println(awdString);
            Assert.assertTrue(awdString.contains("Coupe AWD"), "The titles don't contain Coupe AWD"); // checking if the results contain Coule AWD text
        }
        Thread.sleep(500);
        awd.get(awd.size()-1).click(); // clicking on the last result on the results page
        Thread.sleep(700);
        driver.findElement(By.xpath("//button[@class='r1inOn']")).click(); // going back to the results page

        Thread.sleep(300);

        String viewed = driver.findElement(By.xpath("//parent ::div/following-sibling::div/p")).getText(); // checking if the viewed result is marked as viewed
        Assert.assertEquals(viewed, "Viewed", "The link is not marked as viewed");
        Thread.sleep(500);
        driver.quit();
    }
}
