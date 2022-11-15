package ml.workanet.app.pharmacie.controller;

import lombok.extern.slf4j.Slf4j;
import ml.workanet.app.pharmacie.domaine.Audit;
import ml.workanet.app.pharmacie.securite.service.AccountService;
import ml.workanet.app.pharmacie.service.AuditService;
import ml.workanet.app.pharmacie.utils.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequestMapping(Endpoint.AUDIT)
public class AuditController {

    @Autowired
    private AuditService service;
    @Autowired
    private AccountService accountService;


    @GetMapping
    public String audit(@RequestParam Long id, @RequestParam(defaultValue = "0") int page, Model model) {
        log.info("Consultation Audit");

        Page<Audit> auditPage = service.lister(id, page);

        model.addAttribute("audits", auditPage.getContent());
        model.addAttribute("totalElement", auditPage.getTotalElements());
        model.addAttribute("totalPage", new int[auditPage.getTotalPages()]);
        model.addAttribute("nbTotalPage", auditPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("id", id);
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
        model.addAttribute("uAudit", accountService.rechercher(id));

        return "audit/liste";
    }
}
