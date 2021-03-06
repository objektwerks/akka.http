$(document).ready(function() {
    $("#button-now").click(function() {
        var request = $.ajax({
            url: "/api/v1/now",
            dataType: "json",
            cache: false
        });
        request.done(function(now, status, xhr) {
            $("#text-now").text(now.time);
        });
        request.fail(function(xhr, status, error) {
            console.log("xhr: " + JSON.stringify(xhr) + " status: " + status + " error: " + error);
        });
    });
})