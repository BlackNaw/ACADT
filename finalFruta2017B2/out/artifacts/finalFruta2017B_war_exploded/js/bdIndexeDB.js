/**
 * Created by Juangra on 06/03/2017.
 */
(function (g) {
    'use strict';
    var ajax = FRUTA.Ajax;
    var llamada;
    var bd = {
        indexedDB: window.indexedDB || window.mozIndexedDB || window.webkitIndexedDB || window.msIndexedDB,
        IDBTransaction: window.IDBTransaction || window.webkitIDBTransaction || window.msIDBTransaction,
        IDBKeyRange: window.IDBKeyRange || window.webkitIDBKeyRange || window.msIDBKeyRange,
        version: 2,
        crearBdFruta: function () {
            localStorage.crearBdFruta = true;
            var db = null;
            const dbNombre = "Fruta";

            var request = bd.indexedDB.open(dbNombre, bd.version);

            request.onerror = function (event) {
                swal("crearFruta"+event.target.message, "", "error");
            };

            request.onupgradeneeded = function (event) {
                db = event.target.result;
                var store = db.createObjectStore("fruta", {keyPath: "idFruta", autoIncrement: true});

                store.createIndex("nombreFruta", "nombreFruta", {unique: false});
                store.createIndex("codFruta", "codFruta", {unique: false});
                // store.createIndex("nombreVariedad", "nombreVariedad", {unique: false});
                // store.createIndex("nombreAlmacen", "nombreAlmacen", {unique: false});
                // store.createIndex("nombreMedida", "nombreMedida", {unique: false});
                // store.createIndex("stock", "stock", {unique: false});
                // store.createIndex("precio", "precio", {unique: false});

                llamada = new ajax.CargadorContenidos("/ServletFruta", addListaFruta, "");
            };
            var addListaFruta = function () {

                var db = null;
                const dbNombre = "Fruta";
                var request = bd.indexedDB.open(dbNombre, bd.version);
                request.onerror = function (event) {
                    swal("addLista"+event.target.message, "", "error");
                };
                request.onsuccess = function (event) {
                    db = event.target.result;
                    var mensaje = llamada.req.responseText;
                    if (mensaje.startsWith("Error")) {
                        swal("addLista2"+mensaje, "", "error");
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
                swal("lista fruta"+event.target.message, "", "error");
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
                        var frutas = [];
                        for (var i = 0; i < data.length; i++) {
                            var option = document.createElement("option");
                            if (frutas.indexOf(data[i].nombreFruta) == -1) {
                                frutas.push(data[i].nombreFruta);
                                option.text = data[i].nombreFruta;
                                option.value = data[i].id;
                                listaFruta.add(option, null);
                            }
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
