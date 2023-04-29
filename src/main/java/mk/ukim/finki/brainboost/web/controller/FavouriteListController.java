package mk.ukim.finki.brainboost.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.brainboost.domain.FavouritesList;
import mk.ukim.finki.brainboost.service.FavouritesListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/favourite_list")
public class FavouriteListController {
    private final FavouritesListService favouritesListService;

    public FavouriteListController(FavouritesListService favouritesListService) {
        this.favouritesListService = favouritesListService;
    }
    @GetMapping
    public String getFavouritesListPage(@RequestParam(required = false) String error, HttpServletRequest request, Model model){
        if(error!=null && !error.isEmpty()){
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        String username=request.getRemoteUser();
        FavouritesList favouritesList =this.favouritesListService.getActiveFavouriteList(username);
        model.addAttribute("courses", this.favouritesListService.listAllCoursesInFavouritesList(favouritesList.getId()));
        return "favourites_list";
    }
}
