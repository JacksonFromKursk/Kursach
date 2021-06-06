package butcher_shop.controllers;

import butcher_shop.models.Meat;
import butcher_shop.services.BasketService;
import butcher_shop.services.MeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/shop")
public class MeatController {

    @Autowired
    private MeatService meatService;

    @Autowired
    private BasketService basketService;

    @GetMapping("/{sort}")
    @ResponseBody
    public ModelAndView getMeats(@PathVariable String sort){
        ModelAndView mav = new ModelAndView("shop");
        List<Meat> meats= meatService.getBySort(sort);
        mav.addObject("meat", meats);
        return mav;
    }

    @GetMapping("/{sort}/get/{name}")
    @ResponseBody
    public ModelAndView getMeat(@PathVariable String sort, @PathVariable String name){
        List<Meat> meats = meatService.getBySort(sort);
        Meat meat = meatService.getByName(meats, name);
        ModelAndView mav = new ModelAndView("meat");
        mav.addObject("meat", meat);
        return mav;
    }

    @GetMapping("{6\s" +
            "ort}/add/{name}")
    @ResponseBody
    public ModelAndView addMeat(@PathVariable String sort, @PathVariable String name){
        List<Meat> meats = meatService.getBySort(sort);
        Meat meat = meatService.getByName(meats, name);
        ModelAndView mav = new ModelAndView("shop");
        basketService.addMeat(meat);
        mav.addObject("meat", meats);
        mav.addObject("product", meat.getName());
        mav.addObject("purchase", true);
        return mav;
    }

}
