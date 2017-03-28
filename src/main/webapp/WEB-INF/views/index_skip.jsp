<script>
    var hash = window.location.hash;
    if(hash){
        window.location.href = $("base").attr("href") + hash;
    }else{
        window.location.href = "web/index#/home";
    }
</script>