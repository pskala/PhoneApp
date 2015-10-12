package com.ixperta.phoneapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ixperta.phoneapp.dao.MyPersistenceDAO;
import com.ixperta.phoneapp.domain.Station;
import java.util.HashMap;

@Service
public class StationServiceImpl implements StationService {

    @Autowired
    private MyPersistenceDAO myDAO;

    @Override
    public Station getById(String id) {
        return myDAO.findByStationId(id);
    }

    @Override
    public HashMap deleteStationById(String id) {
        return myDAO.deleteStation(id);
    }

    @Override
    public HashMap getStations() {
        return myDAO.listStations();
    }

    @Override
    public HashMap save(Station station) {
        return myDAO.insert(station);
    }
}
