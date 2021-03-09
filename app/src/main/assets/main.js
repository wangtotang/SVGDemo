//往body中添加svg
function loadSVG(){
//    document.body.innerHTML = main.getJson();
    $("#my").html(main.getJson());
    Snap.select("svg").attr({width: "100%", height: "auto"});

   Snap.selectAll("powerdata").forEach(function(arg){
        var description = arg.attr("description");
            if(description != null){
                arg.parent().parent().click(function(e){
//                    console.log(description);
                    $(".info").html(description);
                    $(".info").css({
                        "display":"block",
                        "left":e.clientX+20+"px",
                        "top":e.clientY+10+"px"
                    });
                });
            }
   });
}

//修改状态
function editSVG(json){

//        Snap.selectAll("powerdata").forEach(function(arg){
//             if(arg.attr("stationname") == "官桥站" && arg.attr("pointname") == "GQ-3#"){
//
//             }
//        });



//    var obj = $.parseJSON(json);
//    var g = Snap.select("g");
//    var r = g.select("#RailTransitClass");
//    var t;
//    for(var item of r.children()){
//        if(item.attr("id") == obj.id){
//            t = item;
//        }
//    }
//
//    var use = t.select("use");
//    var href = use.attr("xlink:href");
//
//    href = href.split("@")[0] + "@" + obj.newValue;
//
//    use.attr({ "xlink:href": href});
}
