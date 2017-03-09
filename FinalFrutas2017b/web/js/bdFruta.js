/**
 * Created by Luciano on 04/03/2017.
 */

(function(g){
    'use strict';
    var ajax = FRUTA.Ajax;
    var llamada;
    var bd = {
           indexedDB: window.indexedDB || window.mozIndexedDB || window.webkitIndexedDB || window.msIndexedDB,
           IDBTransaction: window.IDBTransaction || window.webkitIDBTransaction || window.msIDBTransaction,
           IDBKeyRange: window.IDBKeyRange || window.webkitIDBKeyRange || window.msIDBKeyRange,
           version: 3,
           crearBdFruta: function () {
               localStorage.crearBdFruta = true;
               var db = null;
               const dbNombre = "Fruta";

               var request = bd.indexedDB.open(dbNombre, bd.version);

               request.onerror = function (event) {
                   swal('Fallo en la apertura: 1 ' + event.target.message,"","error");
               };

               request.onupgradeneeded = function (event) {
                   db = event.target.result;

                   var store = db.createObjectStore("fruta", {
                       keyPath: "idFruta",
                       autoIncrement: true
                   });
                   store.createIndex("codigoFruta", "codigoFruta", {unique: false});
                   store.createIndex("nombreFruta", "nombreFruta", {unique: false});

                   llamada = new ajax.CargadorContenidos("/ServletFruta", addListaFruta, "");
               };
               var addListaFruta = function () {
                   var db = null;
                   const dbNombre = "Fruta";
                   var request = bd.indexedDB.open(dbNombre, bd.version);
                   request.onerror = function (event) {
                       swal('Fallo en la apertura: 2 ' + event.target.message,"","error");
                   };
                   request.onsuccess = function(event) {
                       db = event.target.result;
                       var mifruta = {
                           codigoFruta:"",
                           nombreFruta:""
                       };
                       var mensaje=llamada.req.getResponseHeader("frutas");
                       console.log(mensaje);
                       var frutas = JSON.parse(mensaje);

                       var transaction = db.transaction(["fruta"], "readwrite");
                       var store = transaction.objectStore("fruta");

                       frutas.forEach(function (valor) {
                           store.add(valor);
                       });

                       swal("Creado y completada Las Frutas","","success");
                   };
               };
           },

          listaFruta:function (){
                var db = null;
                const dbNombre = "Fruta";
                  var request = bd.indexedDB.open(dbNombre,bd.version);

                  request.onerror = function (event) {
                  swal('Fallo No puedo acceder a Fruta: ' + event.target.message,"","error");
                };

                request.onsuccess = function(event) {
                     db = event.target.result;
                   // alert("db" + db ); //ok
                     var data = [];
                    var transaction = db.transaction(["fruta"], "readwrite");
                    var objectStore = transaction.objectStore("fruta");
                    var request2 = objectStore.openCursor();
                    request2.onsuccess = function(event2) {

                        var cursor = event2.target.result;
                      //alert("cursor " + cursor );  // null

                        if (cursor) {
                            //alert("cursor " + cursor );
                            data.push(cursor.value);
                            cursor.continue();
                        }
                        else {
                            var listaFruta= document.getElementById('selectListaFruta');
                            if(localStorage.fruta == undefined)
                            {
                                localStorage.orden = 0;
                                // localStorage.fruta = data[0].codigoFruta;
                            }
                            for (var i = 0; i < data.length; i++) {
                                var option=document.createElement("option");
                                option.text=data[i].nombreFruta;
                                option.value=data[i].codigoFruta;
                                listaFruta.add(option,null);
                            }
                            // document.getElementById('selectListaFruta').selectedIndex = Number(localStorage.orden);
                           // bd.medida();
                        }
                    };
                };
          }
        };
    bd.crearBdFruta();
    bd.listaFruta();
})(window);
