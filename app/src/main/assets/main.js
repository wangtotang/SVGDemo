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
   var obj = $.parseJSON(json);
   for(var i = 0; i < obj.length; i++){
        Snap.selectAll("powerdata").forEach(function(arg){
            if(arg.attr("stationname") == obj[i]['StationName'] && arg.attr("pointname") == obj[i]['DevName']){
              var use = arg.parent().parent().select("use");
              if(use != undefined){
                  var href = use.attr("xlink:href");
                  href = href.split("@")[0] + "@" + obj[i]['DeviceStatus'];
                  use.attr({ "xlink:href": href});
              }
            }
        });
        Snap.selectAll("shaperef").forEach(function(arg){
            if(arg.attr("scriptname") == obj[i]['DevName']){
              var tspan = arg.parent().parent().select("tspan");
              if(tspan != undefined){
                  var name = obj[i]['DevName'].split("_")[1];
                  var status = obj[i]['DeviceStatus'];
                  if(name == "door"){
                     var text = status == 0 ? "关闭" : "打开";
                     tspan.attr({"#text": text});
                  }else if(name == "bolt"){
                     var text = status == 0 ? "打开" : "关闭";
                     tspan.attr({"#text": text});
                  }else if(name == "connect"){
                     var text = status == 0 ? "正常" : "异常";
                     tspan.attr({"#text": text});
                  }else if(name == "lock"){
                     if(status == 0){
                        tspan.attr({"#text": "无密码"});
                     }else if(status == 1){
                        tspan.attr({"#text": "远方闭锁"});
                     }else{
                         tspan.attr({"#text": "巡检密码"});
                     }
                 }
              }
            }
        });
   }
}
