var phoneApp = phoneApp || {};

phoneApp = {
    templates: [],
    stationList: [],
    init: function () {
        var that = this;
        if (!window.location.hash) {
            window.location.hash = "#" + config.user_url;
        }
        that.read_templates("common").done(function () {
            that.bind();
            that.showContent();
        });
    },
    showContent: function () {
        var that = this;
        that.readData(function (data) {
            that.display_template("mainContent", that.getTemplateName(), data);
            if (data.stationsId) {
                that.display_template("inputStations", "stationlistforuser-template", data);
            }
            that.bindUI();
        });
    },
    getTemplateName: function () {
        return url_modul.getState() + "-template";
    },
    readData: function (callback, sources) {
        var
                that = this,
                sourceid = (sources && sources.sourceid) || url_modul.getSourceID(),
                state = (sources && sources.state) || url_modul.getState(),
                url = sources && sources.url;

        if (sourceid || ($.inArray(state, config.readdatalist) !== -1)) {
            $.getJSON(url || config.getReqFromState(state), {id: sourceid}, function (json) {
                if (json && json.stationsId) {
                    that.stationList = json.stationsId;
                }
                callback(json);
            }).fail(function () {
                that.showMsg(messages.error_msg);
            });
        } else {
            callback({});
        }
    },
    saveData: function (url, data, callback) {
        var that = this;
        $.ajax({
            method: "POST",
            url: url,
            data: data
        }).done(function (response) {
            if (response.error) {
                that.showMsg(response.error.msg);
                $("#" + response.error.inputId).focus();
            } else {
                callback(messages.saved_msg);
            }
        }).fail(function () {
            that.showMsg(messages.error_msg);
        });
    },
    bind: function () {
        var that = this;
        url_modul.setSource();
        window.onhashchange = function () {
            url_modul.setSource();
            that.showContent();
        };
    },
    bindUI: function () {
        var that = this, content = $("#mainContent");
        /**
         * binding for UI elements (buttons etc.)
         */
        content.off("click", ".editUserButton").on("click", ".editUserButton", function (e) {
            e.preventDefault();
            window.location.hash = "#" + config.user_url + config.data_url_separator + $(this).attr("data-id");
        });

        content.off("click", ".deleteUserButton").on("click", ".deleteUserButton", function (e) {
            e.preventDefault();
            if (confirm("Delete: Are you sure?")) {
                var id = $(this).attr("data-id");
                that.saveData(config.getDeleteUserReq(), {"id": id}, function (msg) {
                    that.showContent();
                    that.showMsg(msg);
                });
            }
        });

        content.off("click", ".editStationButton").on("click", ".editStationButton", function (e) {
            e.preventDefault();
            window.location.hash = "#" + config.station_url + config.data_url_separator + $(this).attr("data-id");
        });

        content.off("click", ".deleteStationButton").on("click", ".deleteStationButton", function (e) {
            e.preventDefault();
            if (confirm("Delete: Are you sure?")) {
                var id = $(this).attr("data-id");
                that.saveData(config.getDeleteStationReq(), {"id": id}, function (msg) {
                    that.showContent();
                    that.showMsg(msg);
                });
            }
        });
        content.off("click", "#saveUser").on("click", "#saveUser", function (e) {
            var form = $("#userForm");
            if (form[0].checkValidity()) {
                e.preventDefault();
                var data = that.getinputdata(form);
                data.stationsId = that.stationList;
                that.saveData(config.getSaveUserReq(), data, function (msg) {
                    form.find("input").attr("value", "");
                    form[0].reset();
                    $("#inputStations").html("");
                    that.stationList = [];
                    that.showMsg(msg);
                });
            }
        });
        content.off("click", ".removeFromList").on("click", ".removeFromList", function (e) {
            var id = $(this).attr("data-id");
            var index = that.stationList.indexOf(id);
            if (index > -1) {
                that.stationList.splice(index, 1);
            }
            // that.stationList = that.stationList.filter(item => item !== id);
            $(this).parents(".userrow").remove();
        });
        content.off("click", "#addStation").on("click", "#addStation", function (e) {
            var stationlist = that.stationList;
            that.readData(function (data) {
                if (data) {
                    // remove added stations from data 
                    if (stationlist.length > 0) {
                        data.stationslist.forEach(function (elem, index) {
                            if ($.inArray(elem["number"], stationlist) !== -1) {
                                delete data.stationslist[index];
                            }
                        });
                    }
                    that.display_template("modalContent", "stationslist-template", data);
                    $("#modalContent button").hide();
                }
            }, {
                url: config.getReqFromState("stationslist"),
                sourceid: "",
                state: "stationslist"
            });
        });
        content.off("click", "#modalContent tr").on("click", "#modalContent tr", function (e) {
            e.preventDefault();
            var dOwner = $(this).attr("data-owner"),
                    dId = $("#formId").attr("value");

            if (dOwner && dOwner !== dId) {
                alert("Station cannot be added! Station can own only one user.");
            } else {
                that.stationList.push($(this).attr("data-id"));
                $(this).remove();
                that.display_template("inputStations", "stationlistforuser-template", {"stationsId": that.stationList});
            }
        });
        content.off("click", "#saveStation").on("click", "#saveStation", function (e) {
            var form = $("#stationForm");
            if (form[0].checkValidity()) {
                e.preventDefault();
                var data = that.getinputdata(form);
                that.saveData(config.getSaveStationReq(), data, function (msg) {
                    form.find("input").attr("value", "");
                    form[0].reset();
                    that.showMsg(msg);
                });
            }
        });
        content.off("click", "#resetButton").on("click", "#resetButton", function (e) {
            e.preventDefault();
            var resetForm = $("form");
            resetForm.find("input").attr("value", "");
            resetForm[0].reset();
            $("#inputStations").html("");
            that.stationList = [];
        });
    },
    showMsg: function (msg) {
        var alertDiv = $("#alertsMsg");
        alertDiv.html(msg);
        alertDiv.show();
        alertDiv.fadeOut(5000);
    },
    getinputdata: function (form) {
        var data = {}, nameField;
        form.find("input").each(function (counter, elem) {
            nameField = $(elem).attr("name");
            if (nameField)
                data[nameField] = $(elem).val();
        });
        return data;
    },
    read_templates: function (tmpl_file) {
        var that = this;
        var response = $.get("templates/" + tmpl_file + "_templates.html", function (resp) {
            $(resp).each(function (item, val) {
                if (val.id) {
                    that.templates[val.id] = Handlebars.compile(val.innerHTML);
                }
            });
        });
        return response;
    },
    display_template: function (html_id, tmpl_id, data) {
        var template = this.templates[tmpl_id];
        if (template) {
            $("#" + html_id).html(template(data));
        }
    }
};