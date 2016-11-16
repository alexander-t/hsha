var sensorMapping = [
    {id: 136, class: {temp: "outdoor-temp"}},
    {id: 135, class: {temp: "indoor-temp", humidity: "humidity"}}];

sensorMapping.forEach(function refresh(mapping) {
    var sensorId = mapping.id;
    $(document).ready(function refresh() {
        $.ajax({
            url: "http://cluster1:8090/sensor/" + sensorId
        }).then(function (data) {
            $('.' + mapping.class.temp).html(data.temp + '&deg;');
            if ("humidity" in mapping.class) {
                $('.' + mapping.class.humidity).html(data.humidity + '%');
            }
        });
        setTimeout(refresh, 5000);
    })
});



