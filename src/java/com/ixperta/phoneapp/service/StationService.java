package com.ixperta.phoneapp.service;

import com.ixperta.phoneapp.domain.Station;
import java.util.HashMap;

public interface StationService {

    public Station getById(String id);

    public HashMap deleteStationById(String id);

    public HashMap save(Station station);

    public HashMap getStations();
}
