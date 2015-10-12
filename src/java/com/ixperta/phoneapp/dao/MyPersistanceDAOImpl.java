package com.ixperta.phoneapp.dao;

import java.util.HashMap;
import com.ixperta.phoneapp.domain.Station;
import com.ixperta.phoneapp.domain.User;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class MyPersistanceDAOImpl implements MyPersistenceDAO {

    private HashMap repositoryUsers = new HashMap();
    private HashMap repositoryStations = new HashMap();

    @Override
    public HashMap insert(User user) {
        if (user.getId().isEmpty() && repositoryUsers.containsKey(user.getEmail())) {
            return errorMsg("inputEmail", "You cannot create user with same email:" + user.getEmail());
        } else {
            
            // check duplicite ID for change email
            if ( (user.getId().compareTo(user.getEmail()) != 0) &&
                    repositoryUsers.containsKey(user.getEmail())) {
                return errorMsg("inputEmail", "You cannot change email! This email has different user!");
            }
            
            ArrayList stations = user.getStationsId();

            // check if station own some one else and update station
            if (stations != null && !stations.isEmpty()) {

                // check station - owner
                Station station;
                for (int i = 0; i != stations.size(); i++) {
                    station = ((Station) repositoryStations.get(stations.get(i)));
                    if (station.getOwnerID() != null && station.getOwnerID().length() > 0 && station.getOwnerID().compareTo(user.getId()) != 0) {
                        return errorMsg("addStation", "Station " + station.getNumber() + " own user with email " + station.getOwnerID());
                    }
                }
                // update stations - owner
                updateOwnerStations(stations, user);
                for (int i = 0; i != stations.size(); i++) {
                    updateOwnerStation(stations.get(i), user.getEmail());
                }
            } else {
                updateOwnerStations(new ArrayList(), user);
            }
            repositoryUsers.remove(user.getId());
            user.setId(user.getEmail());
            repositoryUsers.put(user.getEmail(), user);

            return successMsg();
        }

    }
    /**
     * success message for json response
     * @return HashMap
     */
    private HashMap successMsg() {
        return new HashMap();
    }
    /**
     * error message for json response
     * @param inputForm
     * @param msg
     * @return HashMap
     */
    private HashMap errorMsg(String inputForm, String msg) {
        HashMap returnMsg = new HashMap();
        HashMap body = new HashMap();
        body.put("msg", msg);
        body.put("inputId", inputForm);
        returnMsg.put("error", body);
        return returnMsg;
    }
    /**
     * update all stations where is user as owner
     * @param stations
     * @param user 
     */
    private void updateOwnerStations(ArrayList stations, User user) {
        if (!user.getId().isEmpty()) { // user update
            User olddataUser = (User) repositoryUsers.get(user.getId());
            ArrayList oldStations = (ArrayList) olddataUser.getStationsId();
            if (oldStations != null && !oldStations.isEmpty()) {
                HashMap newStations = new HashMap();
                Object b;
                for (Object number : stations) {
                    newStations.put(number, "");
                }
                for (Object number : oldStations) {
                    b = newStations.get(number);
                    if (b == null) {
                        updateOwnerStation(number, "");
                    }
                }
            }
        }
    }

    private void updateOwnerStation(Object id, String owner) {
        Station station;
        station = (Station) repositoryStations.get(id);
        station.setOwnerID(owner);
        repositoryStations.put(station.getNumber(), station);
    }

    @Override
    public HashMap deleteUser(String id) {
        // remove owner from all stations
        User usr = (User) repositoryUsers.get(id);
        ArrayList stations = usr.getStationsId();
        if (stations != null && !stations.isEmpty()) {
            for (int i = 0; i != stations.size(); i++) {
                updateOwnerStation(stations.get(i), "");
            }
        }
        repositoryUsers.remove(id);
        return successMsg();
    }

    @Override
    public User findByUserId(String userId) {
        return (User) repositoryUsers.get(userId);
    }

    @Override
    public HashMap listUsers() {
        List<Object> list = new ArrayList<>(repositoryUsers.values());
        HashMap ret = new HashMap();
        ret.put("userslist", list);
        return ret;
    }

    @Override
    public HashMap insert(Station station) {
        if (station.getId().isEmpty() && repositoryStations.containsKey(station.getNumber())) {
            /// record is present in DB
            return errorMsg("inputPhone", "It's not possible add same number twice.");
        } else {
            
            // check duplicite ID for change number
            if ( (station.getId().compareTo(station.getNumber()) != 0) &&
                    repositoryStations.containsKey(station.getNumber())) {
                return errorMsg("inputPhone", "You cannot change number! This number has different user!");
            }
            repositoryStations.remove(station.getId());
            station.setId(station.getNumber());
            repositoryStations.put(station.getNumber(), station);
            return successMsg();
        }
    }

    @Override
    public HashMap deleteStation(String id) {
        Station station = findByStationId(id);
        String owner = station.getOwnerID();
        if (owner != null && owner.length() > 0) {
            User usr = (User) repositoryUsers.get(owner);
            ArrayList stations = usr.getStationsId();

            Iterator it = stations.iterator();
            String number;
            while (it.hasNext()) {
                number = (String) it.next();
                if (number.equals(id)) {
                    it.remove();
                }
            }

        }
        repositoryStations.remove(id);
        return successMsg();
    }

    @Override
    public Station findByStationId(String stationId) {
        return (Station) repositoryStations.get(stationId);
    }

    @Override
    public HashMap listStations() {
        List<Object> list = new ArrayList<>(repositoryStations.values());
        HashMap ret = new HashMap();
        ret.put("stationslist", list);
        return ret;
    }
}
