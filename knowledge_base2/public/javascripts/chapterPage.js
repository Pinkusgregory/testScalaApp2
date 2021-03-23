$( function () {
    var url = $(location).attr('href'),
        parts = url.split("/"),
        last_part = parts[parts.length-1];
    idForCurrentChapter = "chaptersMenu" + last_part
    $("#contents").append("<ol id=" + idForCurrentChapter + "></ol>")
    $.getJSON("/api/listChapter", function (chapters) {
            $.each(chapters, function (i, chapter) {
                link = "/api/chapter/get/" + chapter.id
                idForUpperChapter = "chaptersMenu" + chapter.upperChapterId
                idForChapter = "chaptersMenu" + chapter.id
                if($("#" + idForUpperChapter).length) {
                    $("#" + idForUpperChapter).prepend("<li><a href=" + link + ">" + chapter.shortName + "</a><ol id=" + idForChapter + "></ol></li>")
                }
            })
    }),

        $.getJSON("/api/listArticle", function (articles) {
            $.each(articles, function (i, article) {
                style = "list-style: none;"
                link = "/api/article/get/" + article.id
                idForUpperChapter = "chaptersMenu" + article.upperChapterId
                $("#"+idForUpperChapter).append("<li style='" + style +"'><a href=" + link + ">" + "article" + article.shortName + "</a></li>")
            })
        })
})
