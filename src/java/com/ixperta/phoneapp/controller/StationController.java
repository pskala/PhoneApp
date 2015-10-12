package com.ixperta.phoneapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;

import com.ixperta.phoneapp.domain.Station;
import com.ixperta.phoneapp.service.StationService;

@Controller
@RequestMapping("api")
public class StationController {

    StationService stationService;

    @Autowired
    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @RequestMapping("station/{id}")
    @ResponseBody
    public Station getById(@PathVariable String id) {
        return stationService.getById(id);
    }

    /* same as above method, but is mapped to
     * /api/station?id= rather than /api/station/{id}
     */
    @RequestMapping(value = "station", params = "id")
    @ResponseBody
    public Station getByIdFromParam(@RequestParam("id") String id) {
        return stationService.getById(id);
    }

    @RequestMapping("deleteStation/{id}")
    @ResponseBody
    public HashMap deleteStationById(@PathVariable String id) {
        return stationService.deleteStationById(id);
    }

    /* same as above method, but is mapped to
     * /api/deleteStation?id= rather than /api/deleteStation/{id}
     */
    @RequestMapping(value = "deleteStation", params = "id")
    @ResponseBody
    public HashMap deleteStationByIdFromParam(@RequestParam("id") String id) {
        return stationService.deleteStationById(id);
    }

    @RequestMapping(value = "stationslist")
    @ResponseBody
    public HashMap getStations() {
        return stationService.getStations();
    }

    /**
     * Saves new station.
     *
     * @param station
     * @return String indicating success or failure of save
     */
    @RequestMapping(value = "saveStation", method = RequestMethod.POST)
    @ResponseBody
    public HashMap saveStation(Station station) {
        return stationService.save(station);
    }
}
