package butcher_shop.test.services;

import butcher_shop.repositories.MeatRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import butcher_shop.models.Meat;
import butcher_shop.models.User;
import butcher_shop.repositories.UserRepository;
import butcher_shop.services.BasketService;

import java.util.*;

import static org.mockito.Mockito.*;


@SpringBootTest
@RunWith(SpringRunner.class)
public class BasketServiceTest {
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private MeatRepository meatRepository;

    @Captor
    private ArgumentCaptor<Meat> captor;

    private BasketService basketService;

    @Autowired
    public void setBasketService(BasketService basketService) {
        this.basketService = basketService;
    }

    @BeforeEach
    public void init(){
        User user = new User();
        user.setLogin("test");
        user.setPassword("password");
        user.setId(1L);
        user.setMeats(new ArrayList<>());

        when(userRepository.findByLogin(user.getLogin())).thenReturn(user);
    }

    @Test
    public void getMeats() {
        Meat meat1 = new Meat();
        meat1.setId(1);
        meat1.setName("Мясо");
        meat1.setQuantity(5);
        meat1.setPrice(158);
        meat1.setSort("mutton");
        meat1.setWeight(2);

        Meat meat2 = new Meat();
        meat2.setId(1);
        meat2.setName("Мясо");
        meat2.setQuantity(5);
        meat2.setPrice(158);
        meat2.setSort("mutton");
        meat2.setWeight(2);

        basketService.addMeat(meat1);
        basketService.addMeat(meat2);

        List<Meat> fetched = basketService.getMeats();

        User user = userRepository.findByLogin("test");

        Assertions.assertEquals(user.getMeats().size(), fetched.size());
    }

    @Test
    public void addMeat() {
        Meat meat = new Meat();
        meat.setId(1);
        meat.setName("Мясо");
        meat.setQuantity(5);
        meat.setPrice(158);
        meat.setSort("mutton");
        meat.setWeight(2);

        basketService.addMeat(meat);

        verify(meatRepository).save(captor.capture());
        Meat captured = captor.getValue();
        Assertions.assertEquals(meat.getId(), captured.getId());
        Assertions.assertEquals(meat.getName(), captured.getName());
        Assertions.assertEquals(meat.getQuantity(), captured.getQuantity());
        Assertions.assertEquals(meat.getPrice(), captured.getPrice());
        Assertions.assertEquals(meat.getSort(), captured.getSort());
        Assertions.assertEquals(meat.getWeight(), captured.getWeight());

        User user = userRepository.findByLogin("test");
        Assertions.assertNotEquals(user.getMeats().indexOf(meat),-1);
    }

    @Test
    public void deleteMeat(){
        Meat meat = new Meat();
        meat.setId(1);
        meat.setName("Мясо");
        meat.setQuantity(5);
        meat.setPrice(158);
        meat.setSort("mutton");
        meat.setWeight(2);

        basketService.addMeat(meat);

        verify(meatRepository).save(captor.capture());
        Meat captured = captor.getValue();

        when(meatRepository.findByName(meat.getName()))
                .thenReturn(meat);

        basketService.deleteMeat(meat.getName());

        Assertions.assertEquals(basketService.getMeats().indexOf(captured), -1);
    }

    @Test
    public void buy(){
        Meat meat = new Meat();
        meat.setId(1);
        meat.setName("Мясо");
        meat.setQuantity(5);
        meat.setPrice(158);
        meat.setSort("mutton");
        meat.setWeight(2);

        basketService.addMeat(meat);

        verify(meatRepository).save(captor.capture());
        Meat captured = captor.getValue();

        basketService.buy();

        User user = userRepository.findByLogin("test");

        Assertions.assertEquals(basketService.getMeats().indexOf(captured), -1);
        Assertions.assertFalse(meat.getBaskets().contains(user));
    }


}
