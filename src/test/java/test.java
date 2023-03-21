import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class test {
    @Test
    public void test() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS); // implicit wait time
        driver.get("https://www.cargurus.com/"); // navigate to the website
        String actualTitle = driver.getTitle(); // returns the Title of the page
        String expectedTitle = "Buy & Sell Cars: Reviews, Prices, and Financing - CarGurus";
        Assert.assertEquals(actualTitle, expectedTitle, "Titles are not matching, this may be not the desired page.");

        driver.findElement(By.xpath("//*[@id=\"heroSearch\"]/label[2]")).click(); // clicking on "Buy Used" button

        String allMakes = String.valueOf(driver.findElement(By.xpath("//*[@id=\"carPickerUsed_makerSelect\"]/option[1]" )).getText()); // checking if the default value of "By Make/Model" is "All Makes"
        Assert.assertEquals(allMakes, "All Makes", "Expected: \"All Makes\"" + ", Actual: "  + allMakes + ".");

        driver.findElement(By.xpath("//*[@id=\"carPickerUsed_makerSelect\"]/optgroup[2]/option[52]")).click(); // selecting Lamborgini

        String allModels = String.valueOf(driver.findElement(By.xpath("//*[@id=\"carPickerUsed_modelSelect\"]/option[1]" )).getText()); // checking if the default value of "By Make/Model" is "All Makes"
        Assert.assertEquals(allModels, "All Models", "Expected: \"All Models\"" + ", Actual: "  + allModels + ".");

        List<String> expectedListOfModels = Arrays.asList("All Models", "Aventador", "Huracan", "Urus",
                "400GT", "Centenario", "Countach", "Diablo", "Espada", "Gallardo", "Murcielago");

        List<WebElement> listOfModels = driver.findElements(By.xpath("//*[@id=\"carPickerUsed_modelSelect\"]/option"));
        List<String> list1 = new ArrayList<>();
        for (WebElement element : listOfModels){
            list1.add(element.getText());}
        Assert.assertEquals(List.of(list1), List.of(expectedListOfModels),"The lists are not matching.");

        driver.findElement(By.cssSelector("#carPickerUsed_modelSelect > optgroup.activeModelGroup > option:nth-child(2)")).click(); // selecting Gallardo
        driver.findElement(By.id("dealFinderZipUsedId_dealFinderForm")).sendKeys("22031");
        driver.findElement(By.id("dealFinderForm_0")).click();



    }


}
