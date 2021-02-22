$(document).ready(function() {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
        
    $('#occupationForm').submit(function(e) {
        // impede o comportamento default de dar refresh no submit
        e.preventDefault();
        var postData = $('#occupationForm').serialize();
        $.ajax({
            type: 'POST',
            data: postData,
            url: '/api/registar-ocupacao',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success:function() {
                $('#requestResponse').html("<div class='alert alert-success' role='alert'>Registo de ocupação efetuado com sucesso!</div>");
            },
            error:function() {
                $('#requestResponse').html("<div class='alert alert-danger' role='alert'>Registo falhou!</div>");
            }
        })

    });

});