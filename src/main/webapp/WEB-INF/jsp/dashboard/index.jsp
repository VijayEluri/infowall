<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  ~ Licensed to the Apache Software Foundation (ASF) under one
  ~ or more contributor license agreements.  See the NOTICE file
  ~ distributed with this work for additional information
  ~ regarding copyright ownership.  The ASF licenses this file
  ~ to you under the Apache License, Version 2.0 (the
  ~ "License"); you may not use this file except in compliance
  ~ with the License.  You may obtain a copy of the License at
  ~
  ~  http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing,
  ~ software distributed under the License is distributed on an
  ~ "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  ~ KIND, either express or implied.  See the License for the
  ~ specific language governing permissions and limitations
  ~ under the License.
  --%>

<html>
<head>
    <title>${dashboard.title}</title>
    <!-- force reload after 6 hours -->
    <meta http-equiv="refresh" content="21600;">

    <link href='http://fonts.googleapis.com/css?family=Droid+Serif' rel='stylesheet' type='text/css'>

    <link href="<c:url value='/static/css/reset.css'/>" rel="stylesheet" type="text/css">
    <link href="<c:url value='/static/css/text.css'/>" rel="stylesheet" type="text/css">
    <link href="<c:url value='/static/css/default.css'/>" rel="stylesheet" type="text/css">

    <script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/require.js/2.1.4/require.min.js"></script>
</head>
<body class="dashboard <c:if test='${not empty dashboard.theme}'>dashboard-theme-${dashboard.theme}</c:if>">


<h1 id="title"></h1>
<div id="container">
  <div class="view"></div>
</div>
<div id="bottom">
    <div class="control-panel">
        <a href="<c:url value="/"/>">Home</a> |
        <a href="<c:url value="/app/configure/dashboard/${dashboard.id}"/>">Configure</a>
    </div>
</div>
</body>
<script type="text/javascript">

    require.config({
        baseUrl:'<c:url value='/static/js'/>',
        paths : {
            jquery     : '//cdnjs.cloudflare.com/ajax/libs/jquery/1.8.3/jquery.min',
            d3         : '//cdnjs.cloudflare.com/ajax/libs/d3/3.0.1/d3.v3.min',
            'text'     : '//cdnjs.cloudflare.com/ajax/libs/require-text/2.0.3/text',
            underscore : '//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.4.4/underscore-min'
        },
        shim : {
            'jquery.mustache' : {
                deps : ['jquery']
            },
            d3 : {
                exports : 'd3'
            },
            underscore : {
                exports : '_'
            }
        }
    });
    requirejs([
            'jquery',
            'dashboard/RenderEngine',
            'dashboard/SlideShow',
            'dashboard/views/TableValueView',
            'dashboard/views/SingleValueView',
            'dashboard/views/HtmlView',
            'dashboard/views/UrlView',
            'dashboard/views/ChartView',
        ], function (
            $,
            RenderEngine,
            SlideShow,
            TableValueView,
            SingleValueView,
            HtmlView,
            UrlView,
            ChartView
        ) {
        $(document).ready(function () {
            var dashboard = ${json};
            var renderEngine = new RenderEngine({
                items:dashboard.items,
                views:[new SingleValueView(),new TableValueView(),new HtmlView(),new UrlView(),new ChartView()],
                baseUrl:"<c:url value='/app/item/${dashboard.id}'/>"
            });
            var slideShow = new SlideShow({
                items:dashboard.items,
                renderEngine:renderEngine,
                container:'#container',
                fxDuration:600,
                animation:dashboard.animation,
                delay:dashboard.delay
            });
            slideShow.start();

            $('#bottom').hover(function () {
                $('.control-panel').fadeIn();
            }, function () {
                $('.control-panel').fadeOut();
            });
            $('.control-panel').delay(1200).fadeOut();
        });
    });

</script>
</html>
