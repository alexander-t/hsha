var updater = {
    sensorMapping: [
        {id: 136, class: {temp: "outdoor-temp"}},
        {id: 135, class: {temp: "indoor-temp", humidity: "humidity"}}],

    startPolling: function (endpointUrl) {
        this.sensorMapping.forEach(function refresh(mapping) {
            var sensorId = mapping.id;
            $(document).ready(function refresh() {
                $.ajax({
                    url: "http://" + endpointUrl + "/sensor/" + sensorId
                }).then(function (data) {
                    $("#updateTime").html(moment().format("hh:mm:ss"));
                    $('.' + mapping.class.temp).html(data.temp + '&deg;');
                    if ("humidity" in mapping.class) {
                        $('.' + mapping.class.humidity).html(data.humidity + '%');
                    }
                });
                setTimeout(refresh, 5000);
            })
        });
    }
};
