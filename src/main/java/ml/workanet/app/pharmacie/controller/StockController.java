package ml.workanet.app.pharmacie.controller;

import lombok.extern.slf4j.Slf4j;
import ml.workanet.app.pharmacie.domaine.Stock;
import ml.workanet.app.pharmacie.securite.service.AccountService;
import ml.workanet.app.pharmacie.service.StockService;
import ml.workanet.app.pharmacie.utils.Endpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequestMapping(Endpoint.STOCK)
public class StockController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    @Autowired
    private StockService service;
    @Autowired
    private AccountService accountService;


    @GetMapping(Endpoint.SUPPRESSION + Endpoint.ID)
    public String delete(@PathVariable Long id) {
        log.info("Suppression Stock" + service.rechercher(id));
        service.suppression(id);
        return "redirect:/stock";

    }

    @GetMapping(Endpoint.DETAILS)
    public String rechercher(@RequestParam(defaultValue = "0") int page, @RequestParam String nom, Model model) {
        log.info("Liste Stock par nom : " + nom);

        Page<Stock> stockPage = service.listerProdNom(nom, page);
        model.addAttribute("stocks", stockPage.getContent());

        model.addAttribute("totalElement", stockPage.getTotalElements());
        model.addAttribute("totalPage", new int[stockPage.getTotalPages()]);
        model.addAttribute("nbTotalPage", stockPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
        return "stock/liste";
    }

    @GetMapping
    public String all(Model model, @RequestParam(defaultValue = "0") int page) {
        log.info("Liste Stock");
        Page<Stock> stockPage = service.liste(page);
        model.addAttribute("stocks", stockPage.getContent());

        model.addAttribute("totalElement", stockPage.getTotalElements());
        model.addAttribute("totalPage", new int[stockPage.getTotalPages()]);
        model.addAttribute("nbTotalPage", stockPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
        return "stock/liste";
    }
}