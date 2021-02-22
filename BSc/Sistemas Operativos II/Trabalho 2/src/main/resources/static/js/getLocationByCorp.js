
$(document).ready(function() {
        
    $('#corpId').change(function() {
        var categoryId = $(this).val();
        $.ajax({
            type: 'GET',
            url: '/api/localizacao/' + categoryId,
            success:function(result) {
                var s = '';
                for(var i = 0; i < result.length; i++) {
                    s += '<option value="' + result[i] + '">' + result[i] + '</option>';
                }
                $('#storeLocation').html(s);
            }
        })
    });

});