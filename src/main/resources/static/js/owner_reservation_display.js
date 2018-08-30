
document.addEventListener('DOMContentLoaded', function () {
    $All('.determinate').forEach( e => {
        const newVal = (e.getAttribute('data-available-count') / e.getAttribute('data-max-count')) * 100;
        e.style.width = newVal+'%';
        });

    let socket = new SockJS('/orderCondition');
    let stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log("소켓 연결 성공", frame);
        const url = "/topic/stores/orders/" + $('#storeId').value;
        stompClient.subscribe(url, function (response) {
            console.log(JSON.parse(response.body));
            const {orderItems} = JSON.parse(response.body);
            updateCurrentReservations(orderItems);
        });
    });
});

const renderReservation = ({reservationId, itemCount}) => {
    const target = $('.collection-item[data-id="'+reservationId+'"]');
    if(!target){
        M.toast({html: '데이터 정합성을 위해 페이지를 리로드 합니다'})
        setTimeout( location.reload(), 2000);
        return;
    }
    const countTarget = $('.availableCount', target);
    const updatedValue = Number( countTarget.innerHTML ) - itemCount;

    countTarget.innerHTML = updatedValue;

    const percentTarget = $('.determinate', target);
    const newPercentage = updatedValue / percentTarget.getAttribute('data-max-count') * 100;
    percentTarget.style.width = newPercentage+'%';

    addClass( countTarget, 'opacity-hidden');
    setTimeout(() => removeClass(countTarget, 'opacity-hidden'), 1000);

        if( updatedValue == 0 ){
            addClass( countTarget, 'materialize-red-text');
            addClass( countTarget, 'text-darken-1');
            removeClass( $('.sold-out', target), 'off');
        }
    };
const updateCurrentReservations = (orderItems) => {
    orderItems.forEach((e) => renderReservation(e));
};