const previewLoad = (serviceName, ownerName, imgFile, address, phone, description) => {
        let reader = new FileReader();
        reader.readAsDataURL(imgFile);
        reader.onload = async () => {
            $('#info-store-name', $('#previewModal')).innerText = serviceName;
            $('#info-img').src = reader.result;
            $('#info-description', $('#previewModal')).innerText = description;
            $('#info-name', $('#previewModal')).innerText = ownerName;
            $('#info-address', $('#previewModal')).innerText = address;
            $('#info-phone', $('#previewModal')).innerText = phone;
            await loadMap($('#info-map'), $("#info-address").innerText, $("#info-store-name").innerText);
            const elems = document.querySelectorAll('.parallax');
            const instances = M.Parallax.init(elems);
        }

};

const storeViewLoad = (serviceName, ownerName, imgPath, address, phone, description) => {
    return new Promise( async (resolve) => {
        $('#info-store-name', $('#infoModal')).innerText = serviceName;
        $('#info-img').src = imgPath;
        $('#info-description', $('#infoModal')).innerText = description;
        $('#info-name', $('#infoModal')).innerText = ownerName;
        $('#info-address', $('#infoModal')).innerText = address;
        $('#info-phone', $('#infoModal')).innerText = phone;
<<<<<<< Updated upstream
        await loadMap($('#info-map'), $("#info-address").innerText, $("#info-store-name").innerText);
=======
        const centerGeo = await loadMap();
        console.log(centerGeo);
>>>>>>> Stashed changes
        const elems = document.querySelectorAll('.parallax');
        const instances = M.Parallax.init(elems);
        resolve(instances);
    });

// const previewLoad = (serviceName, ownerName, imgFile, address, phone, description) => {
//     return new Promise( async (resolve, reject) => {
//         const filePath = await convertFile(imgFile);
//         $('#info-store-name', $('#previewModal')).innerText = serviceName;
//         $('#info-img').src = filePath.result;
//         $('#info-description', $('#previewModal')).innerText = description;
//         $('#info-name', $('#previewModal')).innerText = ownerName;
//         $('#info-address', $('#previewModal')).innerText = address;
//         $('#info-phone', $('#previewModal')).innerText = phone;
//         await loadMap($('#info-map'), $("#info-address").innerText);
//         var elems = document.querySelectorAll('.parallax');
//         var instances = M.Parallax.init(elems);
//         resolve(true);
//     });
// };
//
// const convertFile = (imgFile) => {
//     return new Promise((resolve, reject) => {
//         let reader = new FileReader();
//         reader.readAsDataURL(imgFile);
//         reader.onload = resolve;
//     });
// };

const loadMap = (container, address, mapName) => {
    return new Promise((resolve, reject) => {
        const mapContainer = container; //$('#info-map');
        const mapOption = {
            center: new daum.maps.LatLng(126.95004243985817, 37.37283698073943), // 지도의 중심좌표
            level: 3, // 지도의 확대 레벨
            scrollwheel: false
        };
        let map = new daum.maps.Map(mapContainer, mapOption);
        map.addControl(new daum.maps.ZoomControl(), daum.maps.ControlPosition.TOPRIGHT);
        map.setMaxLevel(5);
<<<<<<< Updated upstream
        const addressToGeo = new daum.maps.services.Geocoder();
        addressToGeo.addressSearch(address,//$("#info-address").innerText,
        function (result, status) {

            if (status === daum.maps.services.Status.OK) {

                var coords = new daum.maps.LatLng(result[0].y, result[0].x);

                var marker = new daum.maps.Marker({
                    map: map,
                    position: coords
                });

                // 인포윈도우로 장소에 대한 설명을 표시합니다
                var name = mapName; //$("#info-store-name").innerText
                var infowindow = new daum.maps.InfoWindow({
                    content: '<div style="width:150px;text-align:center;padding:5px 0;">' + name + '</div>'
                });
                infowindow.open(map, marker);
                // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
                map.setCenter(coords);
                resolve(true);
            }
        });
=======
        resolve();
        // var name = $("#info-store-name").innerText
        // var infowindow = new daum.maps.InfoWindow({
        //     content: '<div style="width:150px;text-align:center;padding:5px 0;">' + name + '</div>'
        // });
        // infowindow.open(map, new daum.maps.Marker({
        //     map: map,
        //     position: new daum.maps.LatLng(126.95004243985817, 37.37283698073943)
        // }));

        // const addressToGeo = new daum.maps.services.Geocoder();
        // addressToGeo.addressSearch($("#info-address").innerText, function (result, status) {
        //
        //     if (status === daum.maps.services.Status.OK) {
        //
        //         var coords = new daum.maps.LatLng(result[0].y, result[0].x);
        //
        //         var marker = new daum.maps.Marker({
        //             map: map,
        //             position: coords
        //         });
        //
        //         // 인포윈도우로 장소에 대한 설명을 표시합니다
        //         var name = $("#info-store-name").innerText
        //         var infowindow = new daum.maps.InfoWindow({
        //             content: '<div style="width:150px;text-align:center;padding:5px 0;">' + name + '</div>'
        //         });
        //         infowindow.open(map, marker);
        //         // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
        //         map.setCenter(coords);
        //         resolve(map.getCenter());
        //     }
        // });
>>>>>>> Stashed changes
    });
};