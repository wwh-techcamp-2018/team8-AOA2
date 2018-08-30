 document.addEventListener('DOMContentLoaded', function () {
     if($('#storeId')){
        let socket = new SockJS('/orderCondition');
                let stompClient = Stomp.over(socket);
                    stompClient.connect({},
                    success_error_callback,
                    error_callback);

                 stompClient.debug = function(str) {
                    // append the debug log to a #debug div somewhere in the page using JQuery:
                    console.log(str + "\n");
                  };
                  var error_callback = function(error) {
                      // display the error's message header:
                      alert(error.headers.message);
                    };
                  var success_error_callback =  function(frame) {
                                                                   console.log("소켓 연결 성공", frame);
                                                                   const url = "/topic/stores/toasts/" + $('#storeId').value;
                                                                   stompClient.subscribe(url, function (response) {
                                                                       console.log(JSON.parse(response.body));
                                                                       const data = JSON.parse(response.body);
                                                                       M.toast({html: toastHtmlTmp(data)});
                                                                   });
                                                               };
     }

 });
 const toastHtmlTmp = ({name, phoneNumber, pickupTime}) => {
 return `${name} [ ${phoneNumber} ] 님이 주문 완료 ( 픽업 예정 시간 : ${pickupTime} ) `;
 }