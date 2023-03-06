package org.example.controller;


import org.example.model.Player;
import org.example.model.Team;
import org.example.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class TeamPageController {
    @Autowired
    TeamService teamService;

    @GetMapping("/teams")
    public ModelAndView showTeamListPage() {
        ModelAndView modelAndView = new ModelAndView();
        List<Team> teamList = teamService.fetchTeamList();
        modelAndView.setViewName("teamList");
        modelAndView.addObject("teams", teamList);
        return modelAndView;
    }

    @GetMapping("/team/add")
    public ModelAndView addNewTeam() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("teamAdd");
        modelAndView.addObject("team", new Team());
        //modelAndView.addObject("positions", positionService.fetchPositionList());
        return modelAndView;
    }

    @PostMapping("/team/save")
    public ModelAndView saveTeam(@ModelAttribute("team") Team team) throws Exception {
        teamService.saveTeam(team);
        return showTeamListPage();
    }

    @GetMapping("/team/edit/{id}")
    public ModelAndView updateTeamInfo(@PathVariable(value = "id") long id, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        Team team = teamService.getById(id);
        modelAndView.setViewName("teamEdit");
        modelAndView.addObject("team", team);
        return modelAndView;
    }

    @GetMapping("/team/cancel/{id}")
    public ModelAndView cancelTeam(@PathVariable(value = "id") long id) {
        teamService.setPassiveTeam(id);
        return showTeamListPage();

    }

    @GetMapping("/team/activate/{id}")
    public ModelAndView activate(@PathVariable(value = "id") long id) {
        teamService.setActivateTeam(id);
        return showTeamListPage();

    }


}
