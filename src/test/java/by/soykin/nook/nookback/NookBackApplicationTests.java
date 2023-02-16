package by.soykin.nook.nookback;

import by.soykin.nook.nookback.jpa.entities.*;
import by.soykin.nook.nookback.jpa.repository.AddressRepository;
import by.soykin.nook.nookback.jpa.repository.ItemRepository;
import by.soykin.nook.nookback.jpa.repository.NookRepository;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class NookBackApplicationTests {

    private List<String> urls = new ArrayList<>();

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private NookRepository nookRepository;

    @Test
    void contextLoads() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://r.onliner.by/ak/");
        WebDriver.Options manage = driver.manage();
        WebDriver.Navigation navigate = driver.navigate();
        WebElement element = driver.findElement(By.className("classifieds-list"));
        WebElement paginationPanel = driver.findElement(By.className("pagination"));
        List<WebElement> pagination__secondary = paginationPanel.findElements(By.className("pagination__secondary"));
        List<WebElement> classified = element.findElements(By.className("classified"));
        for (WebElement webElement : classified) {
            String href = webElement.getAttribute("href");
            urls.add(href);
        }
        urls.forEach(url -> {
            navigate.to(url);
            WebElement panelWithCost = driver.findElement(By.className("apartment-bar"));
            List<WebElement> span = panelWithCost.findElements(By.tagName("span"));
            String costInBy = span.get(0).getText();
            String costInUsd = span.get(2).getText();
            String quantityRoms = span.get(4).getText();
            String owner = span.get(5).getText();
            System.out.println(costInBy);
            System.out.println(costInUsd);
            System.out.println(quantityRoms);
            System.out.println(owner);
            WebElement description = driver.findElement(By.className("apartment-info"));
            List<String> existItems = description.findElements(By.className("apartment-options__item")).stream().map(WebElement::getText).toList();
            String descriptionText = description.findElement(By.className("apartment-info__sub-line_extended-bottom")).getText();
            String fullAddress = description.findElement(By.className("apartment-info__sub-line_large")).getText();
            Nook nook =new Nook();
            Operation operation =new Operation();
            operation.setId(UUID.randomUUID().toString());
            operation.setDescription(descriptionText);
            Address address=new Address();
            address.setId(fullAddress);
            existItems.forEach(s -> {
                Item item = new Item();
                item.setValue(s);
                itemRepository.save(item);
            });
        });
        driver.quit();

    }

}
