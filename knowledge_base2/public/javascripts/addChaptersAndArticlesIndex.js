// (function () {
//     $.getJSON("/api/listChapter", function (chapters) {
//         length = chapters.length
//         console.log(length)
//         $.each(chapters, function (i, chapter) {
//             link = "/api/chapter/get/" + chapter.id
//             idForChapter = "chaptersMenu" + chapter.id
//             if (chapter.upperChapterId == "0") {
//                 $("#contents").append("<li><a href=" + link + ">" + "chapter" + chapter.shortName + "</a><ol id=" + idForChapter + "></ol></li>")
//                 length--
//             }
//         })
//     }),
//
//     $.getJSON("/api/listChapter", function (chapters) {
//         while (length > 0){
//             $.each(chapters, function (i, chapter) {
//                 link = "/api/chapter/get/" + chapter.id
//                 idForUpperChapter = "chaptersMenu" + chapter.upperChapterId
//                 idForChapter = "chaptersMenu" + chapter.id
//                 if ($("#" + idForUpperChapter).length){
//                     $("#" + idForUpperChapter).prepend("<li><a href=" + link + ">" + "chapter" + chapter.shortName + "</a><ol id=" + idForChapter + "></ol></li>")
//                     length--
//                 }
//                 if(length == 0) {
//                     return false;
//                 }
//             })
//         }
//     })
//         $.getJSON("/api/listArticle", function (articles) {
//             $.each(articles, function (i, article) {
//                 style = "list-style: none;"
//                 link = "/api/article/get/" + article.id
//                 idForUpperChapter = "chaptersMenu" + article.upperChapterId
//                 $("#"+idForUpperChapter).append("<li style='" + style +"'><a href=" + link + ">" + "article" + article.shortName + "</a></li>")
//             })
//         })
// }).call(this);

(function (){
    $.getJSON("api/list", function (chapters){
        console.log(chapters)
    })
}).call(this)