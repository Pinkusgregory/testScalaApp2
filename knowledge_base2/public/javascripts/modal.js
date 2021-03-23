$( function (){
    var btn, modal, span;

    modal = $('#myModal');
    modal_content = $(".modal-content")
    span = $('.close-modal');

    $('#articleCreateBtn').click(function() {
        $("#articleCreate").css('display', 'block')
        modal.css('display', 'block');
    });

    $('#articleDeleteBtn').click(function() {
        $("#articleDelete").css('display', 'block')
        modal.css('display', 'block');
    });

    $('#articleUpdateBtn').click(function() {
        $("#articleUpdate").css('display', 'block')
        modal.css('display', 'block');
    });

    $('#chapterCreateBtn').click(function() {
        $("#chapterCreate").css('display', 'block')
        modal.css('display', 'block');
    });

    $('#chapterDeleteBtn').click(function() {
        $("#chapterDelete").css('display', 'block')
        modal.css('display', 'block');
    });

    $('#chapterUpdateBtn').click(function() {
        $("#chapterUpdate").css('display', 'block')
        modal.css('display', 'block');
    });

    span.click(function() {
        modal.css('display', 'none');
        modal_content.css('display', 'none');
    });

    $('window').click(function(event) {
        if (event.target === modal) {
            modal.css('display', 'none');
            modal_content.css('display', 'none');
        }
    });
}
)