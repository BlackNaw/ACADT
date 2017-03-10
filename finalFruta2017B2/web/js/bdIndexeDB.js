/**
 * Created by Juangra on 06/03/2017.
 */
FRUTA.namespace('FRUTA.Cliente.bdIndexedDB');
(function (g) {
    'use strict';
    var ajax = FRUTA.Ajax;
    var llamada;
    var bd = {
        indexedDB: window.indexedDB || window.mozIndexedDB || window.webkitIndexedDB || window.msIndexedDB,
        IDBTransaction: window.IDBTransaction || window.webkitIDBTransaction || window.msIDBTransaction,
        IDBKeyRange: window.IDBKeyRange || window.webkitIDBKeyRange || window.msIDBKeyRange,
        version: 1,
        crearBdFruta: function () {
            localStorage.crearBdFruta = true;
            var db = null;
            const dbNombre = "Fruta";

            var request = bd.indexedDB.open(dbNombre, bd.version);

            request.onerror = function (event) {
                swal(event.target.message, "", "error");
            };

            request.onupgradeneeded = function (event) {
                db = event.target.result;
                var store = db.createObjectStore("fruta", {keyPath: "idFruta", autoIncrement: true});

                store.createIndex("codigoFruta", "codigoFruta", {unique: false});
                store.createIndex("nombreFruta", "nombreFruta", {unique: true});


                llamada = new ajax.CargadorContenidos("/ServletFruta", addListaFruta, "");
            };
            var addListaFruta = function () {

                var db = null;
                const dbNombre = "Fruta";
                var request = bd.indexedDB.open(dbNombre, bd.version);
                request.onerror = function (event) {
                    swal(event.target.message, "", "error");
                };
                request.onsuccess = function (event) {
                    db = event.target.result;
                    var mensaje = llamada.req.responseText;
                    if (mensaje.startsWith("Error")) {
                        swal(mensaje, "", "error");
                    } else {
                        var frutas = JSON.parse(mensaje);
                        var transaction = db.transaction(["fruta"], "readwrite");
                        var store = transaction.objectStore("fruta");
                        frutas.forEach(function (valor) {
                            store.add(valor);
                        });
                        bd.listaFruta();
                    }
                };
            };
        },

        listaFruta: function () {
            var db = null;
            const dbNombre = "Fruta";
            var request = bd.indexedDB.open(dbNombre, bd.version);

            request.onerror = function (event) {
                swal(event.target.message, "", "error");
            };

            request.onsuccess = function (event) {
                db = event.target.result;
                var data = [];
                var transaction = db.transaction(["fruta"], "readwrite");
                var objectStore = transaction.objectStore("fruta");
                var request2 = objectStore.openCursor();
                request2.onsuccess = function (event2) {
                    var cursor = event2.target.result;
                    if (cursor) {
                        data.push(cursor.value);
                        cursor.continue();
                    }
                    else {
                        var listaFruta = document.getElementById('seleccionFruta');
                        if (localStorage.fruta == undefined) {
                            localStorage.orden = 0;
                        }

                        for (var i = 0; i < data.length; i++) {
                            var option = document.createElement("option");
                                option.text = data[i].nombreFruta;
                                option.value = data[i].codigoFruta;
                                listaFruta.add(option, null);
                        }
                        document.getElementById('seleccionFruta').selectedIndex = Number(localStorage.orden);
                    }
                };
            };
        }
    };
    bd.crearBdFruta();
    bd.listaFruta();
})(window);
