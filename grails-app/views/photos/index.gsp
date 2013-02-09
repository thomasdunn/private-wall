<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Photos</title>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport" />
    <meta name="apple-mobile-web-app-capable" content="yes" />

    <link href="../css/photoswipe.css" type="text/css" rel="stylesheet" />

    <script type="text/javascript" src="../js/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="../js/photoswipe/lib/klass.min.js"></script>
    <script type="text/javascript" src="../js/photoswipe/code.photoswipe.jquery-3.0.5.min.js"></script>

    <script type="text/javascript">
        (function(window, $, PhotoSwipe){
            $(document).ready(function(){
                var options = { preventHide: true };
                var photoSwipe = $("#Gallery a").photoSwipe(options);
                photoSwipe.show(0)
            });
        }(window, window.jQuery, window.Code.PhotoSwipe));
    </script>

</head>
<body>
<ul id="Gallery" class="gallery">
<g:each var="photo" in="${photos}">
    <li><a href="show/${photo.id}"><img src="show/${photo.id}" height="100" alt="${photo.fileName}" /></a></li>
</g:each>
</ul>
</body>
</html>