
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>自助选课系统</title>
    <link rel="stylesheet" href="/css/styles.css">
    <link rel="stylesheet" href="/css/colors.css">
    <script src="/js/jquery.min.js"></script>
    <script src="/js/popper.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <script src="/js/custom.js"></script>
    <script src="/js/moment.min.js"></script>
    <script src="/js/daterangepicker.js"></script>
    <script src="/js/dropzone.js"></script>
    <script src="/js/imagesloaded.js"></script>
    <script src="/js/ion.rangeSlider.min.js"></script>
    <script src="/js/jquery.magnific-popup.min.js"></script>
    <script src="/js/lightbox.js"></script>
    <script src="/js/markerclusterer.js"></script>

    <script src="/js/morris.min.js"></script>
    <script src="/js/raphael.min.js"></script>
    <script src="/js/select2.min.js"></script>
    <script src="/js/slick.js"></script>
    <script src="/js/slider-bg.js"></script>
    <script src="/js/script.js"></script>
</head>
<body class="blue-skin">
    <div class="header header-blue dark-text">
        <div class="container">
            <nav class="navigation nav-menu-centered">


                    <ul class="nav-menu align-self-center" >
                        <li><a href="/" style="font-size:25px;">首页</a></li>
                        <li><a href="/" style="font-size:25px;">留言(fb)</a></li>
                        <li><a href="/" style="font-size:25px;">咨询</a></li>
                        <li><a href="/house?" style="font-size: 25px;"></a></li>
                    </ul>


                <c:choose>
                    <c:when test="${sessionScope.user == null}">
                <ul class="nev-menu nav-menu-social align-to-right" style="vertical-align: center">
                    <li>
                        <a href="#" style=" font-size: 16px; color:darkgreen;"  data-toggle="modal" data-target="#login">
                            <i class="fas fa-user mr-1">
                            LOGIN
                            </i>
                        </a>
                    </li>
                    <br>
                    <li class="add-listing dark-bg ">
                        <a href="#" style=" font-size: 16px;color:darkgreen;"  data-toggle="modal" data-target="#signup">
                            <i class="fas fa-registered mr-1">
                                REGISTER
                            </i>
                        </a>
                    </li>
                </ul>
                    </c:when>
                    <c:otherwise>
                        <ul class="nev-menu nav-menu-social align-to-right">
                            <li>
                                <div class="btn-group account-drop">
                                    <button type="button" class="btn btn-order-by-filt" data-toggle="dropdown">
                                        <img src="/img/default-avatar.jpg" class="avater-img">
                                            ${sessionScope.user.userDisplayName}[
                                        <c:choose>
                                            <c:when test="${sessionScope.user.role==0}">ADMIN</c:when>
                                            <c:when test="${sessionScope.user.role==2}">TEACHER</c:when>
                                            <c:when test="${sessionScope.user.role==1}">STU</c:when>

                                        </c:choose>
                                        ]
                                    </button>
                                    <c:choose>
                                        <c:when test="${sessionScope.user.role == '0'}">
                                            <div class="dropdown-menu pull-right animated flipInY">
                                                <a href="/admin/house">
                                                    <i class="ti-layers"></i>用户管理
                                                </a>
                                                <a href="/admin/order">
                                                    <i class="ti-gift"></i>课程管理
                                                </a>
                                                <a href="#">
                                                    <i class="ti-info"></i>新闻管理
                                                </a>
                                                <a href="/login/logout">
                                                    <i class="ti-power-off"></i>退出
                                                </a>
                                            </div>
                                        </c:when>
                                        <c:when test="${sessionScope.user.role == 1}">
                                            <div class="dropdown-menu pull-right animated flipInY">
                                                <a href="/admin/profile">
                                                    <i class="ti-user"></i>个人信息
                                                </a>
                                                <a href="/admin/home">
                                                    <i class="ti-hummer"></i>选择课程
                                                </a>
                                                <a href="/admin/mark">
                                                    <i class="ti-bookmark"></i>已选课程
                                                </a>
                                                <a href="/login/logout">
                                                    <i class="ti-power-off"></i>退出
                                                </a>
                                            </div>
                                        </c:when>
                                        <c:when test="${sessionScope.user.role == 2}">
                                            <div class="dropdown-menu pull-right animated flipInY">
                                                <a href="/admin/profile">
                                                    <i class="ti-user"></i>个人信息
                                                </a>
                                                <a href="/admin/house">
                                                    <i class="ti-layers"></i>课程管理
                                                </a>

                                                <a href="/login/logout">
                                                    <i class="ti-power-off"></i>退出
                                                </a>
                                            </div>
                                        </c:when>
                                    </c:choose>
                                </div>
                            </li>
                        </ul>

                    </c:otherwise>
                </c:choose>
            </nav>
        </div>
    </div>



