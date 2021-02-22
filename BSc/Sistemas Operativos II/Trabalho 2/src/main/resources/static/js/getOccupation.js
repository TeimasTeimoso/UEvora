$(document).ready(function() {
        
    $("#searchForm").submit(function(e) {
        // impede o comportamento default de dar refresh no submit
        e.preventDefault();
        var corpId = $("#corpId").val();
        var storeLocation = $("#storeLocation").val();



        ////
        $.ajax({
            type: 'GET',
            url: '/api/procurar-ocupacao?id=' + corpId + "&locat=" + storeLocation,
            success:function(result) {
                console.log(result);
                var resutlArr = Object.entries(result);
                var s = '';
                if (resutlArr.length > 0) {
                    for(var i = 0; i < resutlArr.length; i++) {
                        s += '<li>' + resutlArr[i][0] + ": " + resutlArr[i][1] + '</li>';
                    }
                    document.getElementById("getDistBtn").style.display = "block";
                }
                else {
                    s = "Não existem registos na ultima hora!"
                }
                document.getElementById("distancia").style.display = "none";

                $('#occupationResult').html(s);
            }
        })
    });

});

