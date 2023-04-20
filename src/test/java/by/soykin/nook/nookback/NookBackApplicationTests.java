package by.soykin.nook.nookback;

import by.soykin.nook.nookback.jpa.entities.*;
import by.soykin.nook.nookback.jpa.entities.enums.*;
import by.soykin.nook.nookback.jpa.repository.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class NookBackApplicationTests {

    private List<String> urls = new ArrayList<>();

//    @Autowired
//    private ItemRepository itemRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private NookRepository nookRepository;
//    @Autowired
//    private CostRepository costRepository;
//    @Autowired
//    private OwnerRepository ownerRepository;
//
//    @Autowired
//    private OperationRepository operationRepository;

    @Test
    void contextLoads() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);

        driver.get("https://r.onliner.by/ak/");

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
            WebElement addressElement= driver.findElement(By.cssSelector(".apartment-info__sub-line.apartment-info__sub-line_large"));
            String addressString=addressElement.getText();

            WebElement nookDescriptionElement=driver.findElement(By.className("apartment-options"));
            String nookDescription=nookDescriptionElement.getText();

            String quantityRooms = span.get(4).getText();
            Nook nook = new Nook();
            nook.setId(UUID.randomUUID().toString());
            setQuantityRooms(nook, quantityRooms);
            nook.setDescription(nookDescription);
            nook.setType(NookType.FLAT);

            Address address = getAddress(addressString, nook);
            addressRepository.save(address);
            nook.setAddress(address);

            nookRepository.save(nook);

//            Cost costBYN=new Cost();
//            String costInBy = span.get(0).getText();
//            stringToCostByn(costBYN, costInBy);
//            costRepository.save(costBYN);
//
//            Cost costUSD=new Cost();
//            String costInUsd = span.get(2).getText();
//            stringToCostUsd(costUSD, costInUsd);
//            costRepository.save(costUSD);
//
//            String quantityRooms = span.get(4).getText();
//            System.out.println(quantityRooms);
//            Nook nook = new Nook();
//            nook.setId(UUID.randomUUID().toString());
//            setQuantityRooms(nook, quantityRooms);
//           // nookRepository.save(nook);
//
//
//            Owner owner = new Owner();
//            String ownerType = span.get(5).getText();
//            System.out.println(ownerType);
//            owner.setId(UUID.randomUUID().toString());
//            setOwnerType(owner, ownerType);
//            //ownerRepository.save(owner);
//
//
//
//            System.out.println(costInBy);
//            System.out.println(costInUsd);
//            System.out.println(quantityRooms);
//            System.out.println(owner);
//            WebElement description = driver.findElement(By.className("apartment-info"));
//            List<String> existItems = description.findElements(By.className("apartment-options__item")).stream().map(WebElement::getText).toList();
//            String descriptionText = description.findElement(By.className("apartment-info__sub-line_extended-bottom")).getText();
//            String fullAddress = description.findElement(By.className("apartment-info__sub-line_large")).getText();
//
//
//            Operation operation = new Operation();
//            operation.setId(UUID.randomUUID().toString());
//            operation.setDescription(descriptionText);
//          //  operation.setNook(nook);
//          //  operation.setOwner(owner);
//            List<Cost> costs=new ArrayList<>();
//            costs.add(costBYN);
//            costs.add(costUSD);
//           // operation.setCosts(costs);
//            operation.setType(OperationType.RENT);
//            operationRepository.save(operation);
//
//            System.out.println(fullAddress);
//            Address address = new Address();
//            address.setId(fullAddress);
//
//            existItems.forEach(s -> {
//                Item item = new Item();
//                item.setValue(s);
//                itemRepository.save(item);
//            });


        });
        driver.quit();

    }

    @NotNull
    private Address getAddress(String addressString, Nook nook) {
        Address address;
        if(!addressString.equals(addressRepository.findByValue(addressString))) {
            address = new Address();
            address.setId(UUID.randomUUID().toString());
            address.setValue(addressString);
            List<Nook> addressOfNooks=new ArrayList<>();
            addressOfNooks.add(nook);
            address.setNooks(addressOfNooks);
        }
        else {
           address=addressRepository.findByValue(addressString).orElseThrow();
           address.getNooks().add(nook);

        }
        return address;
    }

    //    private static void stringToCostByn(Cost costBYN, String costInByn) {
//
//        costBYN.setId(UUID.randomUUID().toString());
//        costBYN.setCurrency(Currency.BYN);
//        String[] words = costInByn.split("\\s");
//        String number = words[0];
//        BigDecimal Byn = new BigDecimal(number.replace(",", "."));
//        costBYN.setCost(Byn);
//
//
//    }
//
//    private static void stringToCostUsd(Cost costUSD, String costInUsd) {
//        costUSD.setCurrency(Currency.USD);
//        String[] words = costInUsd.split("\\s");
//        String number = words[0];
//        BigDecimal Usd = new BigDecimal(number);
//        costUSD.setCost(Usd);
//        costUSD.setId(UUID.randomUUID().toString());
//
//    }
//
    private static void setQuantityRooms(Nook nook, String quantityRooms) {
        switch (quantityRooms) {
            case "1-комнатная квартира":
                nook.setQuantityRooms(Room.ONE);
                break;
            case "2-комнатная квартира":
                nook.setQuantityRooms(Room.TWO);
                break;
            case "3-комнатная квартира":
                nook.setQuantityRooms(Room.THREE);
                break;
            case "4+-комнатная квартира":
                nook.setQuantityRooms(Room.FOUR_AND_MORE);
                break;
            case "Комната":
                nook.setQuantityRooms(Room.ROOM);
                break;
        }

    }

    public static void setNooksToAddress(Address address, Nook nook){

    }
//
//    private static void setOwnerType(Owner owner, String ownerType){
//        switch (ownerType){
//            case "Собственник": owner.setOwnerType(OwnerType.OWNER);
//            break;
//            case "Агентство": owner.setOwnerType(OwnerType.REALTOR);
//            break;
//        }
//    }


}