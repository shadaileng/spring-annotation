$(function () {
    console.log("begin ... ")
    var len = document.querySelectorAll("#carousel_main img").length
    var width = 300
    document.querySelectorAll("#carousel_main img").forEach((el, i)=>{el.style.left=(i*width)+"px"})
    setInterval(()=>{
        document.querySelectorAll("#carousel_main img").forEach((el, i)=>{
            $(el).animate({left: (parseInt($(el).css("left")) - width)+"px"}, ()=> {
                if(parseInt($(el).css("left")) < 0) {
                    $(el).css("left", (len - 1) * width + "px")
                }
            })

        })
    }, 1000)
})