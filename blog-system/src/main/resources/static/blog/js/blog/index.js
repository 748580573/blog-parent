
$(function () {
    nav_tab_a();
    cloce_scoll();
});

var nav_tab_a = function () {
    $(".nav-tab-a").unbind().click(function () {
        var sub_i = $(this).children(".nav-arrow");
        var parent = $(this).parent();
        if ($(sub_i).hasClass("active")){
            $(sub_i).removeClass("glyphicon glyphicon-menu-down active");
            $(sub_i).addClass("glyphicon glyphicon-menu-right");
        } else {
            nav_tab_a_inactive();
            $(sub_i).removeClass("glyphicon glyphicon-menu-right");
            $(sub_i).addClass("glyphicon glyphicon-menu-down active");
        }
        var ul = $(parent).children(".nav-tab-ul");
        $(ul).fadeToggle();
    })
};

//将所有被active的a标签inactive
var nav_tab_a_inactive = function () {
    var a = $(".nav-tab-a");
    for (var i = 0;i < a.length;i++){
        var sub_i = $(a[i]).children(".nav-arrow");
        if ($(sub_i).hasClass("active")){
            var parent = $(a[i]).parent();
            var ul = $(parent).children(".nav-tab-ul");
            $(sub_i).removeClass("glyphicon glyphicon-menu-down active");
            $(sub_i).addClass("glyphicon glyphicon-menu-right");
            $(ul).hide();
        }
    }
};

var cloce_scoll = function () {
    $(document.body).css({
        "overflow-x":"hidden",
        "overflow-y":"hidden"
    });
}