package com.ixperta.phoneapp.dao;

import com.ixperta.phoneapp.domain.Station;
import com.ixperta.phoneapp.domain.User;
import java.util.HashMap;

public interface MyPersistenceDAO {

    public HashMap insert(User user);

    public HashMap deleteUser(String id);

    public User findByUserId(String userId);

    public HashMap listUsers();

    public HashMap insert(Station station);

    public HashMap deleteStation(String id);

    public Station findByStationId(String stationId);

    public HashMap listStations();
}
