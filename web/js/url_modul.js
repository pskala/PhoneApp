var url_modul = (function() {
    var state,
        sourceID; 
    return {
        setSource: function() {
            var hash = window.location.hash,
                state_source = hash.split(config.data_url_separator);
            state = state_source[0].substr(1);
            if (state_source[1]) {
                sourceID = state_source[1];
            } else {
                sourceID = "";
            }
            return sourceID;
        },
        getState: function() {
            return state;
        },
        getSourceID: function() {
            return sourceID;
        }
    };
})();

