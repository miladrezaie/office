$(document).ready(function () {
    $('.ch').on('change', function (event) {
        event.preventDefault();
        var value = $('.ch').val();
        console.log("salam" + value);

        console.log("salam" + event.ip);
        $.getJSON('/findbyjob/' + value, function (data) {

            console.log("****************** data :" + data);
            console.log("****************** data :" + data.ip);
            console.log("milad shorooooooooooooooooooooooooooooooo");
            var gencheckboxes = "";
            for (var i = 0; i < data.length; i++) {
                console.log(data[i].fullname);
                gencheckboxes = gencheckboxes + '<input type="checkbox" name="users" value="' + data[i].id + '">' + data[i].fullname + '<br></input>';
            }
            var dwrap = document.getElementById('wrapperfortestnames');
            dwrap.innerHTML = gencheckboxes;
        });

        $.getJSON('http://localhost:8085/findbyjob/' + value, function (data) {

            console.log("****************** data :" + data);
            console.log("****************** data :" + data.ip);
            console.log("milad shorooooooooooooooooooooooooooooooo");
            var gencheckboxes = "";
            for (var i = 0; i < data.length; i++) {
                console.log(data[i].fullname);
                gencheckboxes = gencheckboxes + '<input type="checkbox" name="users" value="' + data[i].id + '">' + data[i].fullname + '<br></input>';
            }
            var dwrap = document.getElementById('wrapperfortestnames');
            dwrap.innerHTML = gencheckboxes;
        });

    });
    $('#txtsearch').keyup(function (e) {
        e.preventDefault();
        var v = $('#txtsearch').val();
        window.location.replace('http://localhost:8085/members/?keyword=' + v);


    });
});





















