/*注册sub*/
function sumbmitRegister(){
    $.ajax({
        type:"POST",
        url:"/register/submit",
        async:false,
        data:$("#registerform").serialize(),
        success: function (data){
            alert(data.msg);
            if(data.code == 1){  //成功便刷新
                window.location.reload();
            }
        }
    });

}

function submitLogin() {
    $.ajax({
        type: "POST",
        url: "/login/submit",
        async: false,
        data: $("#loginform").serialize(),
        success: function (data) {
            alert(data.msg);
            if (data.code == 1) {  //成功便刷新
                window.location.reload();
            }
        }
    });
}

function submitHouse() {
    $.ajax({
        type: "POST",
        url: "/admin/house/publish/submit",
        async: false,
        data: $("#houseForm").serialize(),
        success: function (data) {
            alert(data.msg);
            if (data.code == 1) {  //成功便刷新
                window.location.href="/admin/house";
            }
        }
    });
}

function downHouse(id){
    $.ajax({
        type: "POST",
        url: "/admin/house/down",
        async: false,
        data:{
            "id":id
        },
        success: function (data) {
            alert(data.msg);
            if (data.code == 1) {  //成功便刷新
                window.location.reload();
            }
        }
    });
}

function upHouse(id){
    $.ajax({
        type: "POST",
        url: "/admin/house/up",
        async: false,
        data:{
            "id":id
        },
        success: function (data) {
            alert(data.msg);
            if (data.code == 1) {  //成功便刷新
                window.location.reload();
            }
        }
    });
}

function checkPassHouse(id){
    $.ajax({
        type: "POST",
        url: "/admin/house/checkPass",
        async: false,
        data:{
            "id":id
        },
        success: function (data) {
            alert(data.msg);
            if (data.code == 1) {  //成功便刷新
                window.location.reload();
            }
        }
    });
}

function checkRejectHouse(id){
    $.ajax({
        type: "POST",
        url: "/admin/house/checkReject",
        async: false,
        data:{
            "id":id
        },
        success: function (data) {
            alert(data.msg);
            if (data.code == 1) {  //成功便刷新
                window.location.reload();
            }
        }
    });
}

function deleteHouse(id){
    $.ajax({
        type: "POST",
        url: "/admin/house/delete",
        async: false,
        data:{
            "id":id
        },
        success: function (data) {
            alert(data.msg);
            if (data.code == 1) {  //成功便刷新
                window.location.reload();
            }
        }
    });
}

function submitMark(id){
    $.ajax({
        type: "POST",
        url: "/mark/submit",
        async: false,
        data:{
            "houseId":id
        },
        success: function (data) {
            alert(data.msg);
            if(data.msg == "请先登录"){
                this.location.href="/";
            }
        }
    });
}

function createOrder(){
    var houseId = $("#houseId").val();
    var endDate = $("#endDate").val();
    $.ajax({
        type: "POST",
        url: "/order/create",
        async: false,
        data:{
            "houseId": houseId,
            "endDate": endDate
        },
        success: function (data) {
            alert(data.msg);
            if(data.msg == "请先登录"){
                this.location.href="/";
            }
            if(data.code == 1){
                window.location.href="/order/agreement/view?orderId="+data.result;
            }

        }
    });
}

function confirmAgreement(orderId){

    $.ajax({
        type: "POST",
        url: "/order/agreement/submit",
        async: false,
        data:{
            "orderId": orderId
        },
        success: function (data) {
            alert(data.msg);
            if(data.msg == "请先登录"){
                this.location.href="/";
            }
            if(data.code == 1){
                window.location.href="/order/pay?orderId="+data.result;
            }

        }
    });
}

function submitPay(orderId){

    $.ajax({
        type: "POST",
        url: "/order/pay/submit",
        async: false,
        data:{
            "orderId": orderId
        },
        success: function (data) {
            alert(data.msg);
            if(data.msg == "请先登录"){
                this.location.href="/";
            }
            if(data.code == 1){
                window.location.href="/admin/home";
            }

        }
    });
}

function submitContact(){

    $.ajax({
        type: "POST",
        url: "/house/contact",
        async: false,
        data: $('#contactForm').serialize(),
        success: function (data) {
            alert(data.msg);
            if(data.msg == "请先登录"){
                this.location.href="/";
            }
            if(data.code == 1){
                window.location.reload();
            }

        }
    });
}

function submitProfile(){
    $.ajax({
        type: "POST",
        url: "/admin/profile/submit",
        async: false,
        data: $('#profileForm').serialize(),
        success: function (data) {
            alert(data.msg);

            if(data.code == 1){
                window.location.reload();
            }

        }
    });
}

function cancelOrder(orderId){

    $.ajax({
        type: "POST",
        url: "/admin/order/cancel",
        async: false,
        data:{
            "orderId": orderId
        },
        success: function (data) {
            alert(data.msg);

            if(data.code == 1){
                window.location.reload();
            }

        }
    });
}

function endOrder(orderId){

    $.ajax({
        type: "POST",
        url: "/admin/order/end",
        async: false,
        data:{
            "orderId": orderId
        },
        success: function (data) {
            alert(data.msg);

            if(data.code == 1){
                window.location.reload();
            }

        }
    });
}

function endOrderPass(orderId){

    $.ajax({
        type: "POST",
        url: "/admin/order/endPass",
        async: false,
        data:{
            "orderId": orderId
        },
        success: function (data) {
            alert(data.msg);

            if(data.code == 1){
                window.location.reload();
            }

        }
    });
}

function endOrderReject(orderId){

    $.ajax({
        type: "POST",
        url: "/admin/order/endReject",
        async: false,
        data:{
            "orderId": orderId
        },
        success: function (data) {
            alert(data.msg);

            if(data.code == 1){
                window.location.reload();
            }

        }
    });
}


function cancelMark(id){

    $.ajax({
        type: "POST",
        url: "/admin/mark/cancel",
        async: false,
        data:{
            "id": id
        },
        success: function (data) {
            alert(data.msg);

            if(data.code == 1){
                window.location.reload();
            }

        }
    });
}


function deleteNews(id){

    $.ajax({
        type: "POST",
        url: "/admin/news/delete",
        async: false,
        data:{
            "id": id
        },
        success: function (data) {
            alert(data.msg);

            if(data.code == 1){
                window.location.reload();
            }

        }
    });
}