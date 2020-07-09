$(document).ready(function() {
        
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $('#records tbody').on('click', '.rmvBtn', function () {
        var btn = this;
        var id = $(btn).val();
        console.log(id);
        $.ajax({
            type: 'DELETE',
            url: '/api/remover-registo/'+id,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success:function() {
                $(btn).closest('tr').remove();
            }
        })
    });

});