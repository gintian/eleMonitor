package com.bairock.eleMonitor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bairock.eleMonitor.data.Station;
import com.bairock.eleMonitor.repository.StationRepository;

@Controller
public class MapController {

	@Autowired
	private StationRepository stationRepository;
	
	@GetMapping("/map")
	public String map(Model model) {
		List<Station> list = stationRepository.findAll();
		model.addAttribute("listStation", list);
		return "map";
	}
}
