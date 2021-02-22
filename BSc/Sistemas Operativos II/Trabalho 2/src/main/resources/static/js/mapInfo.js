var coords = {lat: 38.818311, lon: -9.041748}; // lisboa
var zoom = 6;
var map;

//https://www.w3schools.com/html/html5_geolocation.asp
function getlocation() {
    if(navigator.geolocation){
        navigator.geolocation.getCurrentPosition(showPosition, showError);
    }
    else{
        alert("Sorry! your browser is not supporting");
    }
}
 
function showPosition(position){
//    console.log("Latitude: " + position.coords.latitude + ", \n" + "Longitude: " + position.coords.longitude + ")");

    coords.lat = position.coords.latitude;
    coords.lon = position.coords.longitude;
    zoom = 14;
}

function showError(error) {
    //default = evora
    coords.lat = 38.568164;
    coords.lon = -7.909883;
    zoom = 13;

    switch(error.code){
        case error.PERMISSION_DENIED:
        alert("User denied the request for Geolocation API.");
        break;
    case error.POSITION_UNAVAILABLE:
        alert("User location information is unavailable.");
        break;
    case error.TIMEOUT:
        alert("The request to get user location timed out.");
        break;
    case error.UNKNOWN_ERROR:
        alert("An unknown error occurred.");
        break;
    }
}


// calcular a distancia em linha reta entre dois pontos no mapa
// https://stackoverflow.com/questions/18883601/function-to-calculate-distance-between-two-coordinates
function getDistanceFromLatLonInKm(lat1, lon1, lat2, lon2) {
    var R = 6371; // Radius of the earth in km
    var dLat = deg2rad(lat2-lat1);  // deg2rad below
    var dLon = deg2rad(lon2-lon1); 
    var a = 
      Math.sin(dLat/2) * Math.sin(dLat/2) +
      Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * 
      Math.sin(dLon/2) * Math.sin(dLon/2)
      ; 
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
    var d = R * c; // Distance in km
    return Math.round(d * 100) / 100;
}

function deg2rad(deg) {
    return deg * (Math.PI/180)
}


//
$(document).ready(function() {
    $("#searchForm").submit(function(e) {
        // impede o comportamento default de dar refresh no submit
        e.preventDefault();
        var corpId = $("#corpId").val();
        var storeLocation = $("#storeLocation").val();

        ////
        $.ajax({
            type: 'GET',
            url: '/api/coordenadas?id=' + corpId + "&locat=" + storeLocation,
            success:function(result) {
                var resutlArr = Object.entries(result);
                console.log("coord: " + result);
                var storeLat = result[0];
                var storeLon = result[1];

                var myLat = coords.lat;
                var myLon = coords.lon;

                if (resutlArr.length > 0) {
                    document.getElementById("distancia").innerHTML = "Distancia (em linha reta): " + getDistanceFromLatLonInKm(myLat, myLon, storeLat, storeLon) + " kms";
                    iconANDpopupsStore(storeLat, storeLon);
                }
                else {
                    document.getElementById("getDistBtn").style.display = "none";
                }

                document.getElementById("distancia").style.display = "none";
            }
        })
    });
});




// icons && popups Stores
function iconANDpopupsStore(lat, lon){
    var sIcon = L.icon({
        iconUrl: '../img/imhereicon.png',
        iconSize:     [100, 100] // size of the icon
    });

    // adicionar icon
    var storeIcon = L.marker([lat, lon]).addTo(map);

    // adicionar popup nos icons
    storeIcon.bindPopup("<b>Loja!</b>  <br> Latitude: " + coords.lat + "<br> Longitude: " + coords.lon);  
}

// icons && popups
function iconANDpopups(){
    var ImHereIcon = L.icon({
        iconUrl: '../img/imhereicon.png',
        iconSize:     [75, 75] // size of the icon
    });
    
    // adicionar icon
    var ImHere = L.marker([coords.lat, coords.lon], {icon: ImHereIcon}).addTo(map);

    // adicionar popup nos icons
    ImHere.bindPopup("<b>I'm Here!</b>  <br> Latitude: " + coords.lat + "<br> Longitude: " + coords.lon);  
}

// gerar o mapa
function buildMap(){
    map = L.map('mapid').setView([coords.lat, coords.lon], zoom); 
    
    L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
        maxZoom: 19,
        attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' + '<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' + 'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
        id: 'mapbox/streets-v11',
        tileSize: 512,
        zoomOffset: -1
    }).addTo(map);

    iconANDpopups();

    // clique esquerdo escreve as coord no log
    map.on("contextmenu", function(event){console.log("Coordinates: " + event.latlng.toString());});
}




// mostra a distancia na pagina html e esconde o btn
function mostrarDist(){
    document.getElementById("distancia").style.display = "block";
    document.getElementById("getDistBtn").style.display = "none";
}

// print no log as coord
function sendToConsole(){
    console.log(" Latitude: " + coords.lat);
    console.log("Longitude: " + coords.lon);
}

getlocation();
// delay nessessario para encontrar as coordenada do user
setTimeout(() => { 
    sendToConsole();
    buildMap();
}, 1500);