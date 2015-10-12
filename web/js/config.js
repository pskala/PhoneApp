var config = {
    request_mapping: "api/",
    
    // hash links in url
    user_url: "user",
    userList_url: "userslist",
    station_url: "station",
    stationList_url: "stationslist",
    
    data_url_separator: "?",
    readdatalist: ["stationslist", "userslist"],
    
    // url requests for operations with records
    getDeleteUserReq: function () {
        return this.request_mapping + "deleteUser";
    },
    getSaveUserReq: function () {
        return this.request_mapping + "saveUser";
    },
    getDeleteStationReq: function () {
        return this.request_mapping + "deleteStation";
    },
    getSaveStationReq: function () {
        return this.request_mapping + "saveStation";
    },
    // return request url 
    getReqFromState: function (state) {
        switch (state) {
            case "user":
                return this.request_mapping + this.user_url;
                break;
            case "station":
                return this.request_mapping + this.station_url;
                break;
            case "userslist":
                return this.request_mapping + this.userList_url;
                break;
            case "stationslist":
                return this.request_mapping + this.stationList_url;
                break;
        }
    }
};


