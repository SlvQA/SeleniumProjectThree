import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class test {
    @Test
    public void test() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://www.cargurus.com/"); // navigate to the website
        String actualTitle = driver.getTitle(); // returns the Title of the page
        String expectedTitle = "Buy & Sell Cars: Reviews, Prices, and Financing - CarGurus";
        Assert.assertEquals(actualTitle, expectedTitle, "Titles are not matching, this may be not the desired page.");

        driver.findElement(By.xpath("//*[@id=\"heroSearch\"]/label[2]")).click(); // clicking on "Buy Used" button

        String allMakes = String.valueOf(driver.findElement(By.xpath("//*[@id=\"carPickerUsed_makerSelect\"]/option[1]")).getText()); // checking if the default value of "By Make/Model" is "All Makes"
        Assert.assertEquals(allMakes, "All Makes", "Expected: \"All Makes\"" + ", Actual: " + allMakes + ".");

        driver.findElement(By.xpath("//*[@id=\"carPickerUsed_makerSelect\"]/optgroup[2]/option[52]")).click(); // selecting Lamborgini

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

        driver.findElement(By.cssSelector("#carPickerUsed_modelSelect > optgroup.activeModelGroup > option:nth-child(2)")).click(); // selecting Gallardo
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

        driver.findElement(By.xpath("//*[@id=\"sort-listing\"]/option[3]")).click();

        List<WebElement> resultsToSort = driver.findElements(By.cssSelector("span[class='JzvPHo']"));
        List<String> list1 = new ArrayList<>();
        for (WebElement strings : resultsToSort) {
            list1.add(strings.getText());
            }
        System.out.println("List: " + list1);


    }
}
